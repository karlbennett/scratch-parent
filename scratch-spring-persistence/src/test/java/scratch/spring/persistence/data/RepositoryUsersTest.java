package scratch.spring.persistence.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scratch.ScratchSpringBootApplication;
import scratch.spring.persistence.test.UserSteps;
import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static scratch.spring.persistence.test.Addresses.addressOne;
import static scratch.spring.persistence.test.Users.EMAIL_ONE;
import static scratch.spring.persistence.test.Users.FIRST_NAME_ONE;
import static scratch.spring.persistence.test.Users.LAST_NAME_ONE;
import static scratch.spring.persistence.test.Users.PHONE_NUMBER_ONE;
import static scratch.spring.persistence.test.Users.userOne;
import static scratch.spring.persistence.test.Users.userThree;
import static scratch.spring.persistence.test.Users.userTwo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringBootApplication.class)
public class RepositoryUsersTest {

    @Autowired
    private UserSteps steps;

    @Autowired
    private Users users;

    private User persistedUser;

    @Before
    public void setup() {

        steps.all_users_are_cleaned_up();

        persistedUser = steps.given_a_user_has_been_persisted();
    }

    @Test
    public void I_can_create_a_user() throws Exception {

        final User user = userOne();

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test
    public void I_can_create_a_user_with_an_id_and_the_id_is_ignored() throws Exception {

        final User user = userOne();
        user.setId(2L);

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_create_a_user_with_no_data() throws Exception {

        users.create(new User(null, null, null, null, null));
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_create_an_existing_user() throws Exception {

        users.create(persistedUser);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_create_two_users_with_the_same_email() throws Exception {

        final User user = userOne();
        user.setEmail(persistedUser.getEmail());

        users.create(user);
    }

    @Test
    public void I_can_create_two_users_with_the_same_first_name() throws Exception {

        final User user = userOne();
        user.setFirstName(persistedUser.getFirstName());

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test
    public void I_can_create_two_users_with_the_same_last_name() throws Exception {

        final User user = userOne();
        user.setLastName(persistedUser.getLastName());

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test
    public void I_can_create_two_users_with_the_same_phone_number() throws Exception {

        final User user = userOne();
        user.setPhoneNumber(persistedUser.getPhoneNumber());

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test
    public void I_can_create_two_users_with_the_same_address() throws Exception {

        final User user = userOne();
        user.setAddress(persistedUser.getAddress());

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_create_a_user_with_no_email() throws Exception {

        final User user = userOne();
        user.setEmail(null);

        users.create(user);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_create_a_user_with_no_first_name() throws Exception {

        final User user = userOne();
        user.setFirstName(null);

        users.create(user);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_create_a_user_with_no_last_name() throws Exception {

        final User user = userOne();
        user.setLastName(null);

        users.create(user);
    }

    @Test
    public void I_can_create_a_user_with_no_phone_number() throws Exception {

        final User user = userOne();
        user.setPhoneNumber(null);

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test
    public void I_can_create_a_user_with_no_address() throws Exception {

        final User user = userOne();
        user.setAddress(null);

        final Id id = users.create(user);

        steps.then_the_user_should_be_created(id, user);
    }

    @Test
    public void I_can_retrieve_a_user() throws Exception {

        final User user = users.retrieve(persistedUser.getId());

        assertEquals("the retrieved user should be correct.", persistedUser, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_retrieve_a_user_with_an_invalid_id() throws Exception {

        users.retrieve(-1L);
    }

    @Test
    public void I_can_retrieve_all_the_persisted_users() throws Exception {

        final User userOne = steps.given_a_user_has_been_persisted(userOne());
        final User userTwo = steps.given_a_user_has_been_persisted(userTwo());
        final User userThree = steps.given_a_user_has_been_persisted(userThree());

        final Iterable<User> users = this.users.retrieve();

        assertEquals("all the users should be retrieved.", asList(persistedUser, userOne, userTwo, userThree), users);
    }

    @Test
    public void I_can_update_a_user() throws Exception {

        persistedUser.setEmail(EMAIL_ONE);
        persistedUser.setFirstName(FIRST_NAME_ONE);
        persistedUser.setLastName(LAST_NAME_ONE);
        persistedUser.setPhoneNumber(PHONE_NUMBER_ONE);
        persistedUser.setAddress(addressOne());

        users.update(persistedUser);

        steps.then_the_user_should_be_updated(persistedUser);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_update_a_user_with_no_data() throws Exception {

        persistedUser.setEmail(null);
        persistedUser.setFirstName(null);
        persistedUser.setLastName(null);
        persistedUser.setPhoneNumber(null);
        persistedUser.setAddress(null);

        users.update(persistedUser);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_update_a_user_to_be_equal_to_an_existing_user() throws Exception {

        final User user = steps.given_a_user_has_been_persisted(userOne());
        user.setEmail(persistedUser.getEmail());
        user.setFirstName(persistedUser.getFirstName());
        user.setLastName(persistedUser.getLastName());

        users.update(user);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_update_a_user_to_have_the_same_email_as_an_existing_user() throws Exception {

        final User user = steps.given_a_user_has_been_persisted(userOne());
        user.setEmail(persistedUser.getEmail());

        users.update(user);
    }

    @Test
    public void I_can_update_a_user_to_have_the_same_first_name_as_an_existing_user() throws Exception {

        final User user = steps.given_a_user_has_been_persisted(userOne());
        user.setFirstName(persistedUser.getFirstName());

        users.update(user);

        steps.then_the_user_should_be_updated(user);
    }

    @Test
    public void I_can_update_a_user_to_have_the_same_last_name_as_an_existing_user() throws Exception {

        final User user = steps.given_a_user_has_been_persisted(userOne());
        user.setLastName(persistedUser.getLastName());

        users.update(user);

        steps.then_the_user_should_be_updated(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_update_a_user_with_an_invalid_id() throws Exception {

        persistedUser.setId(-1L);

        users.update(persistedUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_update_a_user_with_no_id() throws Exception {

        persistedUser.setId(null);

        users.update(persistedUser);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_update_a_user_with_no_email() throws Exception {

        persistedUser.setEmail(null);

        users.update(persistedUser);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_update_a_user_with_no_first_name() throws Exception {

        persistedUser.setFirstName(null);

        users.update(persistedUser);
    }

    @Test(expected = IllegalStateException.class)
    public void I_cannot_update_a_user_with_no_last_name() throws Exception {

        persistedUser.setLastName(null);

        users.update(persistedUser);
    }

    @Test
    public void I_can_delete_a_user() throws Exception {

        users.delete(persistedUser.getId());

        steps.then_the_user_should_no_longer_be_persisted(persistedUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_delete_a_user_with_an_invalid_id() throws Exception {

        users.delete(-1L);
    }

    @Test
    public void I_cannot_delete_all_users() throws Exception {

        final User userOne = steps.given_a_user_has_been_persisted(userOne());
        final User userTwo = steps.given_a_user_has_been_persisted(userTwo());
        final User userThree = steps.given_a_user_has_been_persisted(userThree());

        users.deleteAll();

        steps.then_the_user_should_no_longer_be_persisted(persistedUser);
        steps.then_the_user_should_no_longer_be_persisted(userOne);
        steps.then_the_user_should_no_longer_be_persisted(userTwo);
        steps.then_the_user_should_no_longer_be_persisted(userThree);
    }
}
