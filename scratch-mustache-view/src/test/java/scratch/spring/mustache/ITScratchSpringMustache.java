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
import static scratch.spring.mustache.test.UserConstants.emptyAddress;
import static scratch.spring.mustache.test.UserConstants.emptyUser;
import static scratch.spring.mustache.test.UserConstants.userOne;
import static scratch.spring.mustache.test.UserConstants.userThree;
import static scratch.spring.mustache.test.UserConstants.userTwo;
import static scratch.spring.mustache.test.UserConstants.users;
import static scratch.spring.mustache.test.page.Given.Given_the_mock;
import static scratch.spring.mustache.test.page.Then.Then_the;
import static scratch.spring.mustache.test.page.Then.Then_the_mock;
import static scratch.spring.mustache.test.page.Then.copyData;

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
    private List<User> userList;


    @Before
    public void setUp() {

        reset(users);

        baseUrl.setPort(port);

        userOne = userOne();
        userTwo = userTwo();
        userThree = userThree();

        userList = users();
    }

    @Test
    public void I_can_got_to_the_home_page() {

        Given_the_mock(users).will_return_the_list_of_users_in(userList);

        // When
        homePage.visit();

        Then_the(userViewPage).should_have_a_title_of("All Users");
        Then_the(homePage).should_contain_rows_for(userOne, userTwo, userThree);
    }

    @Test
    public void I_can_got_to_a_users_page_from_the_home_page() {

        Given_the_mock(users).will_return_the_list_of_users_in(userList);
        Given_the_mock(users).will_return(userOne);
        homePage.visit();

        // When
        homePage.users().get(0).clickView();

        Then_the(userViewPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userViewPage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_page() {

        Given_the_mock(users).will_return_each_of_the_users_in(userList);

        for (User user : userList) {

            // When
            userViewPage.visit(user);

            Then_the(userViewPage).should_have_a_title_containing_the_name_of(user);
            Then_the(userViewPage).should_contain_the_data_from(user);
        }
    }

    @Test
    public void I_can_got_to_a_users_edit_page_from_the_home_page() {

        Given_the_mock(users).will_return_the_list_of_users_in(userList);
        Given_the_mock(users).will_return(userOne);
        homePage.visit();

        // When
        homePage.users().get(0).clickEdit();

        Then_the(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userEditPage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_edit_page_from_their_view_page() {

        Given_the_mock(users).will_return(userOne);
        userViewPage.visit(userOne);

        // When
        userViewPage.clickEdit();

        Then_the(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userEditPage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_edit_page() {

        Given_the_mock(users).will_return_each_of_the_users_in(userList);

        for (User user : userList) {

            // When
            userEditPage.visit(user);

            Then_the(userEditPage).should_have_a_title_containing_the_name_of(user);
            Then_the(userEditPage).should_contain_the_data_from(user);
        }
    }

    @Test
    public void I_can_save_an_edited_user() {

        Given_the_mock(users).will_first_return(userOne).and_then(userTwo).for_the_id_from(userOne);
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(userTwo);
        userEditPage.clickSave();

        Then_the_mock(users).should_receive_an_update_with_data_from(copyData(userTwo, userOne));
        Then_the(userViewPage).should_have_a_title_containing_the_name_of(userTwo);
        Then_the(userViewPage).should_contain_the_data_from(userTwo);
    }

    @Test
    public void I_can_save_an_edited_user_with_no_phone_number() {

        final User noPhoneNumberUser = new User(userOne);
        noPhoneNumberUser.setPhoneNumber("");
        Given_the_mock(users).will_first_return(userOne).and_then(noPhoneNumberUser).for_the_id_from(userOne);
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(noPhoneNumberUser);
        userEditPage.clickSave();

        Then_the_mock(users).should_receive_an_update_with_data_from(noPhoneNumberUser);
        Then_the(userViewPage).should_have_a_title_containing_the_name_of(noPhoneNumberUser);
        Then_the(userViewPage).should_contain_the_data_from(noPhoneNumberUser);
    }

    @Test
    public void I_can_save_an_edited_user_with_no_address() {

        final User noAddressUser = new User(userOne);
        noAddressUser.setAddress(emptyAddress(userOne.getAddress().getId()));
        Given_the_mock(users).will_first_return(userOne).and_then(noAddressUser).for_the_id_from(userOne);
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(noAddressUser);
        userEditPage.clickSave();

        Then_the_mock(users).should_receive_an_update_with_data_from(noAddressUser);
        Then_the(userViewPage).should_have_a_title_containing_the_name_of(noAddressUser);
        Then_the(userViewPage).should_contain_the_data_from(noAddressUser);
    }

    @Test
    public void I_cannot_save_an_edited_user_with_no_data() {

        Given_the_mock(users).will_return(userOne);
        userEditPage.visit(userOne);
        final User empty = emptyUser();

        // When
        userEditPage.setValues(empty);
        userEditPage.clickSave();

        Then_the_mock(users).should_not_have_received_an_update();
        Then_the(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userEditPage).should_contain_the_data_from(userOne);
        Then_the(userEditPage).should_contain_an_email_error_of("A users email cannot be empty.");
        Then_the(userEditPage).should_contain_a_first_name_error_of("A users first name cannot be empty.");
        Then_the(userEditPage).should_contain_a_last_name_error_of("A users last name cannot be empty.");
    }

    @Test
    public void I_cannot_save_an_edited_user_with_no_email() {

        Given_the_mock(users).will_return(userOne);
        userEditPage.visit(userOne);

        // When
        userEditPage.setEmail(null);
        userEditPage.clickSave();

        Then_the_mock(users).should_not_have_received_an_update();
        Then_the(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userEditPage).should_contain_the_data_from(userOne);
        Then_the(userEditPage).should_contain_an_email_error_of("A users email cannot be empty.");
        Then_the(userEditPage).should_not_contain_a_first_name_error();
        Then_the(userEditPage).should_not_contain_a_last_name_error();
    }

    @Test
    public void I_cannot_save_an_edited_user_with_no_first_name() {

        Given_the_mock(users).will_return(userOne);
        userEditPage.visit(userOne);

        // When
        userEditPage.setFirstName(null);
        userEditPage.clickSave();

        Then_the_mock(users).should_not_have_received_an_update();
        Then_the(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userEditPage).should_contain_the_data_from(userOne);
        Then_the(userEditPage).should_not_contain_an_email_error();
        Then_the(userEditPage).should_contain_a_first_name_error_of("A users first name cannot be empty.");
        Then_the(userEditPage).should_not_contain_a_last_name_error();
    }

    @Test
    public void I_cannot_save_an_edited_user_with_no_last_name() {

        Given_the_mock(users).will_return(userOne);
        userEditPage.visit(userOne);

        // When
        userEditPage.setLastName(null);
        userEditPage.clickSave();

        Then_the_mock(users).should_not_have_received_an_update();
        Then_the(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userEditPage).should_contain_the_data_from(userOne);
        Then_the(userEditPage).should_not_contain_an_email_error();
        Then_the(userEditPage).should_not_contain_a_first_name_error();
        Then_the(userEditPage).should_contain_a_last_name_error_of("A users last name cannot be empty.");
    }
}
