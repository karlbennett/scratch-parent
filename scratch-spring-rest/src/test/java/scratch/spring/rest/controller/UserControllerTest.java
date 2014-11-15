package scratch.spring.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import scratch.ScratchSpringBootServlet;
import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static scratch.spring.rest.test.Users.user;
import static scratch.spring.rest.test.Users.userOne;
import static scratch.spring.rest.test.Users.userThree;
import static scratch.spring.rest.test.Users.userTwo;
import static scratch.spring.rest.test.Utils.assertBadRequest;
import static scratch.spring.rest.test.Utils.assertDataError;
import static scratch.spring.rest.test.Utils.assertMissingBody;
import static scratch.spring.rest.test.Utils.assertNoFound;
import static scratch.spring.rest.test.Utils.equalTo;
import static scratch.spring.rest.test.Utils.json;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringBootServlet.class)
@WebAppConfiguration("classpath:")
public class UserControllerTest {

    private static final String REST_USERS = "/rest/users";
    private static final String REST_USERS_DIGIT = REST_USERS + "/%d";
    private static final String REST_USERS_INVALID = REST_USERS + "/invalid";

    @Autowired
    private Users users;

    @Autowired
    private WebApplicationContext wac;

    private User user;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        reset(users);

        user = user();

        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void I_can_create_a_user() throws Exception {

        final Id id = new Id(user.getId());
        when(users.create(user)).thenReturn(id);

        assertUserCreated(post(REST_USERS), user, id);
    }

    @Test
    public void I_cannot_create_an_invalid_user() throws Exception {

        final IllegalStateException expected = new IllegalStateException("test invalid create");
        when(users.create(user)).thenThrow(expected);

        assertBadRequest(mockMvc.perform(async(post(REST_USERS).content(json(user)))), expected);
    }

    @Test
    public void I_cannot_create_a_user_with_no_data() throws Exception {

        assertMissingBody(mockMvc.perform(post(REST_USERS).contentType(APPLICATION_JSON)));

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_create_an_empty_user() throws Exception {

        assertValidationError(post(REST_USERS), new User(null, null, null, null, null), "email.null");

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_create_a_user_with_no_email() throws Exception {

        user.setEmail(null);

        assertValidationError(post(REST_USERS), user, "email.null");

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_create_a_user_with_no_first_name() throws Exception {

        user.setFirstName(null);

        assertValidationError(post(REST_USERS), user, "firstName.null");

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_create_a_user_with_no_last_name() throws Exception {

        user.setLastName(null);

        assertValidationError(post(REST_USERS), user, "lastName.null");

        verifyZeroInteractions(users);
    }

    @Test
    public void I_can_create_a_user_with_no_phone_number() throws Exception {

        user.setPhoneNumber(null);

        final Id id = new Id(user.getId());
        when(users.create(user)).thenReturn(id);

        assertUserCreated(post(REST_USERS), user, id);
    }

    @Test
    public void I_can_create_a_user_with_no_address() throws Exception {

        user.setAddress(null);

        final Id id = new Id(user.getId());
        when(users.create(user)).thenReturn(id);

        assertUserCreated(post(REST_USERS), user, id);
    }

    @Test
    public void I_can_retrieve_a_user() throws Exception {

        when(users.retrieve(user.getId())).thenReturn(user);

        mockMvc.perform(async(get(format(REST_USERS_DIGIT, user.getId()))))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").value(equalTo(user)))
                .andExpect(jsonPath("$.address").value(equalTo(user.getAddress())))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void I_cannot_retrieve_a_user_with_an_unknown_id() throws Exception {

        final IllegalArgumentException expected = new IllegalArgumentException("test update with unknown id");
        when(users.retrieve(user.getId())).thenThrow(expected);

        assertNoFound(mockMvc.perform(async(get(format(REST_USERS_DIGIT, user.getId())))), expected);
    }

    @Test
    public void I_cannot_retrieve_a_user_with_an_invalid_id() throws Exception {

        assertInvalidId(get(REST_USERS_INVALID));

        verifyZeroInteractions(users);
    }

    @Test
    public void I_can_retrieve_all_the_persisted_users() throws Exception {

        final User userOne = userOne();
        final User userTwo = userTwo();
        final User userThree = userThree();

        when(users.retrieve()).thenReturn(asList(user, userOne, userTwo, userThree));

        mockMvc.perform(async(get(REST_USERS)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").value(hasSize(4)))
                .andExpect(jsonPath("$[0]").value(equalTo(user)))
                .andExpect(jsonPath("$[0].address").value(equalTo(user.getAddress())))
                .andExpect(jsonPath("$[1]").value(equalTo(userOne)))
                .andExpect(jsonPath("$[1].address").value(equalTo(userOne.getAddress())))
                .andExpect(jsonPath("$[2]").value(equalTo(userTwo)))
                .andExpect(jsonPath("$[2].address").value(equalTo(userTwo.getAddress())))
                .andExpect(jsonPath("$[3]").value(equalTo(userThree)))
                .andExpect(jsonPath("$[3].address").value(equalTo(userThree.getAddress())));
    }

    @Test
    public void I_can_update_a_user() throws Exception {

        assertUserUpdated(put(format(REST_USERS_DIGIT, user.getId())), user);

        verify(users).update(user);
    }

    @Test
    public void I_cannot_update_a_user_with_no_data() throws Exception {

        assertMissingBody(mockMvc.perform(put(format(REST_USERS_DIGIT, user.getId())).contentType(APPLICATION_JSON)));

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_update_a_user_with_invalid_data() throws Exception {

        final IllegalStateException expected = new IllegalStateException("test invalid update");
        doThrow(expected).when(users).update(user);

        assertBadRequest(mockMvc.perform(async(put(format(REST_USERS_DIGIT, user.getId())).content(json(this.user)))),
                expected);
    }

    @Test
    public void I_cannot_update_a_user_with_an_unknown_id() throws Exception {

        final IllegalArgumentException exception = new IllegalArgumentException("test update with unknown id");
        doThrow(exception).when(users).update(user);

        assertNoFound(mockMvc.perform(async(put(format(REST_USERS_DIGIT, user.getId())).content(json(user)))),
                exception);
    }

    @Test
    public void I_cannot_update_a_user_with_an_invalid_id() throws Exception {

        assertInvalidId(put(REST_USERS_INVALID).contentType(APPLICATION_JSON));

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_update_a_user_with_no_id() throws Exception {

        mockMvc.perform(put(REST_USERS).content(json(user)))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(isEmptyString()));

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_update_a_user_with_no_email() throws Exception {

        user.setEmail(null);

        assertValidationError(put(format(REST_USERS_DIGIT, user.getId())), user, "email.null");

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_update_a_user_with_no_first_name() throws Exception {

        user.setFirstName(null);

        assertValidationError(put(format(REST_USERS_DIGIT, user.getId())), user, "firstName.null");

        verifyZeroInteractions(users);
    }

    @Test
    public void I_cannot_update_a_user_with_no_last_name() throws Exception {

        user.setLastName(null);

        assertValidationError(put(format(REST_USERS_DIGIT, user.getId())), user, "lastName.null");

        verifyZeroInteractions(users);
    }

    @Test
    public void I_can_delete_a_user() throws Exception {

        assertUserNoContent(delete(format(REST_USERS_DIGIT, user.getId())));

        verify(users).delete(user.getId());
    }

    @Test
    public void I_cannot_delete_a_user_with_an_unknown_id() throws Exception {

        final IllegalArgumentException exception = new IllegalArgumentException("test delete with unknown id");
        doThrow(exception).when(users).delete(user.getId());

        assertNoFound(mockMvc.perform(async(delete(format(REST_USERS_DIGIT, user.getId())))), exception);
    }

    @Test
    public void I_cannot_delete_a_user_with_an_invalid_id() throws Exception {

        assertInvalidId(delete(REST_USERS_INVALID));

        verifyZeroInteractions(users);
    }

    @Test
    public void I_can_delete_all_users() throws Exception {

        assertUserNoContent(delete(REST_USERS));

        verify(users).deleteAll();
    }

    private void assertUserCreated(MockHttpServletRequestBuilder builder, User user, Id id) throws Exception {

        mockMvc.perform(async(builder.content(json(user))))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").value(equalTo(id)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    public void assertValidationError(MockHttpServletRequestBuilder builder, User user, String message) throws Exception {

        assertDataError(mockMvc.perform(builder.accept(APPLICATION_JSON).contentType(APPLICATION_JSON)
                .content(json(user))), containsString(message));
    }

    private void assertUserUpdated(MockHttpServletRequestBuilder builder, User user) throws Exception {

        assertUserNoContent(builder.content(json(user)));
    }

    private void assertInvalidId(MockHttpServletRequestBuilder builder) throws Exception {

        assertBadRequest(mockMvc.perform(builder), equalTo("TypeMismatchException"),
                containsString("NumberFormatException"));
    }

    private void assertUserNoContent(MockHttpServletRequestBuilder builder) throws Exception {

        mockMvc.perform(async(builder))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(isEmptyString()));
    }

    private RequestBuilder async(MockHttpServletRequestBuilder requestBuilder)
            throws Exception {

        final MvcResult result = mockMvc.perform(requestBuilder.accept(APPLICATION_JSON).contentType(APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(notNullValue()))
                .andReturn();

        return asyncDispatch(result);
    }
}
