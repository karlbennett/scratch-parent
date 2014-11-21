package scratch.spring.mustache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import scratch.ScratchSpringBootServlet;
import scratch.spring.mustache.test.page.BaseUrl;
import scratch.spring.mustache.test.page.HomePage;
import scratch.spring.mustache.test.page.UserEditPage;
import scratch.spring.mustache.test.page.UserViewPage;
import scratch.user.User;
import scratch.user.Users;

import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static scratch.spring.mustache.test.UserConstants.userOne;
import static scratch.spring.mustache.test.UserConstants.userThree;
import static scratch.spring.mustache.test.UserConstants.userTwo;
import static scratch.spring.mustache.test.UserConstants.users;
import static scratch.spring.mustache.test.page.PageAsserts.The;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringBootServlet.class)
@WebAppConfiguration("classpath:")
@IntegrationTest({"server.port=0", "management.port=0"})
public class ITScratchSpringMustache {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private Users users;

    @Autowired
    private HomePage homePage;

    @Autowired
    private UserViewPage userViewPage;

    @Autowired
    private UserEditPage userEditPage;

    @Autowired
    private BaseUrl baseUrl;

    private User userOne;
    private User userTwo;
    private User userThree;
    private List<User> usersList;


    @Before
    public void setUp() {

        reset(users);

        baseUrl.setPort(port);

        userOne = userOne();
        userTwo = userTwo();
        userThree = userThree();

        usersList = users();
    }

    @Test
    public void I_can_got_to_the_home_page() {

        // Given
        when(users.retrieve()).thenReturn(usersList);

        // When
        homePage.visit();

        // Then
        The(userViewPage).should_have_a_title_of("All Users");
        The(homePage).should_contain_rows_for(userOne, userTwo, userThree);
    }

    @Test
    public void I_can_got_to_a_users_page_from_the_home_page() {

        // Given
        when(users.retrieve()).thenReturn(usersList);
        when(users.retrieve(userOne.getId())).thenReturn(userOne);
        homePage.visit();

        // When
        homePage.users().get(0).clickView();

        // Then
        The(userViewPage).should_have_a_title_containing_the_name_of(userOne);
        The(userViewPage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_page() {

        // Given
        some_users_exist();

        for (User user : usersList) {

            // When
            userViewPage.visit(user);

            // Then
            The(userViewPage).should_have_a_title_containing_the_name_of(user);
            The(userViewPage).should_contain_the_data_from(user);
        }
    }

    @Test
    public void I_can_got_to_a_users_edit_page_from_the_home_page() {

        // Given
        when(users.retrieve()).thenReturn(usersList);
        when(users.retrieve(userOne.getId())).thenReturn(userOne);
        homePage.visit();

        // When
        homePage.users().get(0).clickEdit();

        // Then
        The(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        The(userEditPage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_edit_page_from_their_view_page() {

        // Given
        when(users.retrieve(userOne.getId())).thenReturn(userOne);
        userViewPage.visit(userOne);

        // When
        userViewPage.clickEdit();

        // Then
        The(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        The(userEditPage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_edit_page() {

        // Given
        some_users_exist();

        for (User user : usersList) {

            // When
            userEditPage.visit(user);

            // Then
            The(userEditPage).should_have_a_title_containing_the_name_of(user);
            The(userEditPage).should_contain_the_data_from(user);
        }
    }

    @Test
    public void I_can_save_an_edited_user() {

        // Given
        userTwo.setId(userOne.getId());
        userTwo.getAddress().setId(null);

        when(users.retrieve(userOne.getId())).thenReturn(userOne, userTwo);
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(userTwo);
        userEditPage.clickSave();

        // Then
        verify(users).update(userTwo);
        The(userViewPage).should_have_a_title_containing_the_name_of(userTwo);
        The(userViewPage).should_contain_the_data_from(userTwo);
    }

    private void some_users_exist() {
        when(this.users.retrieve(userOne.getId())).thenReturn(userOne);
        when(this.users.retrieve(userTwo.getId())).thenReturn(userTwo);
        when(this.users.retrieve(userThree.getId())).thenReturn(userThree);
    }
}
