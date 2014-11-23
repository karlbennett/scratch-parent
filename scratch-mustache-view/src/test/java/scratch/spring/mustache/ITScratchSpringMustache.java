package scratch.spring.mustache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import scratch.ScratchSpringBootServlet;
import scratch.spring.mustache.test.page.BaseUrl;
import scratch.spring.mustache.test.page.ErrorPage;
import scratch.spring.mustache.test.page.HomePage;
import scratch.spring.mustache.test.page.UserCreatePage;
import scratch.spring.mustache.test.page.UserDeletePage;
import scratch.spring.mustache.test.page.UserEditPage;
import scratch.spring.mustache.test.page.UserViewPage;
import scratch.user.User;
import scratch.user.Users;

import java.util.List;

import static org.mockito.Mockito.reset;
import static scratch.spring.mustache.test.UserConstants.emptyAddress;
import static scratch.spring.mustache.test.UserConstants.emptyUser;
import static scratch.spring.mustache.test.UserConstants.userOne;
import static scratch.spring.mustache.test.UserConstants.userTwo;
import static scratch.spring.mustache.test.UserConstants.users;
import static scratch.spring.mustache.test.page.steps.Given.Given_the_mock;
import static scratch.spring.mustache.test.page.steps.Then.Then_the;
import static scratch.spring.mustache.test.page.steps.Then.Then_the_mock;
import static scratch.spring.mustache.test.page.steps.Then.copyData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringBootServlet.class)
@WebAppConfiguration("classpath:")
@IntegrationTest({"server.port=0", "management.port=0"})
public class ITScratchSpringMustache {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private WebDriver driver;

    @Autowired
    private Users users;

    @Autowired
    private HomePage homePage;

    @Autowired
    private UserCreatePage userCreatePage;

    @Autowired
    private UserViewPage userViewPage;

    @Autowired
    private UserEditPage userEditPage;

    @Autowired
    private UserDeletePage userDeletePage;

    @Autowired
    private ErrorPage errorPage;

    @Autowired
    private BaseUrl baseUrl;

    private User userOne;
    private User userTwo;
    private List<User> userList;

    @Before
    public void setUp() {

        driver.manage().deleteAllCookies();

        reset(users);

        baseUrl.setPort(port);

        userOne = userOne();
        userTwo = userTwo();

        userList = users();
    }

    @Test
    public void I_can_got_to_the_home_page() {

        Given_the_mock(users).will_return_the_list_of_users_in(userList);

        // When
        homePage.visit();

        Then_the(homePage).should_have_a_title_of("All Users");
        Then_the(homePage).should_contain_a_row_for_each_user_in(userList);
    }

    @Test
    public void I_can_go_to_the_create_user_page_from_the_home_page() {

        // Given
        homePage.visit();

        // When
        homePage.clickCreate();

        //Then
        Then_the(userCreatePage).should_have_a_title_of("Create User");
        Then_the(userCreatePage).should_contain_the_data_from(emptyUser());
    }

    @Test
    public void I_can_create_a_user() {

        Given_the_mock(users).will_create(userOne);
        Given_the_mock(users).will_return(userOne);
        userCreatePage.visit();

        // When
        userCreatePage.setValues(userOne);
        userCreatePage.clickSave();

        Then_the(userViewPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userViewPage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_create_a_user_with_no_phone_number() {

        final User noPhoneNumberUser = new User(userOne);
        noPhoneNumberUser.setPhoneNumber("");
        Given_the_mock(users).will_create(noPhoneNumberUser);
        Given_the_mock(users).will_return(noPhoneNumberUser);
        userCreatePage.visit();

        // When
        userCreatePage.setValues(noPhoneNumberUser);
        userCreatePage.clickSave();

        Then_the(userViewPage).should_have_a_title_containing_the_name_of(noPhoneNumberUser);
        Then_the(userViewPage).should_contain_the_data_from(noPhoneNumberUser);
    }

    @Test
    public void I_can_create_a_user_with_no_address() {

        final User noAddressUser = new User(userOne);
        noAddressUser.setAddress(emptyAddress(userOne.getAddress().getId()));
        Given_the_mock(users).will_create(noAddressUser);
        Given_the_mock(users).will_return(noAddressUser);
        userCreatePage.visit();

        // When
        userCreatePage.setValues(noAddressUser);
        userCreatePage.clickSave();

        Then_the(userViewPage).should_have_a_title_containing_the_name_of(noAddressUser);
        Then_the(userViewPage).should_contain_the_data_from(noAddressUser);
    }

    @Test
    public void I_cannot_create_a_user_with_no_data() {

        final User emptyUser = emptyUser();
        userCreatePage.visit();

        // When
        userCreatePage.setValues(emptyUser);
        userCreatePage.clickSave();

        Then_the_mock(users).should_not_have_received_a_create();
        Then_the(userCreatePage).should_have_a_title_of("Create User");
        Then_the(userCreatePage).should_contain_the_data_from(emptyUser);
        Then_the(userCreatePage).should_contain_an_email_error_of("A users email cannot be empty.");
        Then_the(userCreatePage).should_contain_a_first_name_error_of("A users first name cannot be empty.");
        Then_the(userCreatePage).should_contain_a_last_name_error_of("A users last name cannot be empty.");
    }

    @Test
    public void I_cannot_create_a_user_with_no_email() {

        userOne.setEmail("");
        userCreatePage.visit();

        // When
        userCreatePage.setValues(userOne);
        userCreatePage.clickSave();

        Then_the_mock(users).should_not_have_received_a_create();
        Then_the(userCreatePage).should_have_a_title_of("Create User");
        Then_the(userCreatePage).should_contain_the_data_from(userOne);
        Then_the(userCreatePage).should_contain_an_email_error_of("A users email cannot be empty.");
        Then_the(userCreatePage).should_not_contain_a_first_name_error();
        Then_the(userCreatePage).should_not_contain_a_last_name_error();
    }

    @Test
    public void I_cannot_create_a_user_with_no_first_name() {

        userOne.setFirstName("");
        userCreatePage.visit();

        // When
        userCreatePage.setValues(userOne);
        userCreatePage.clickSave();

        Then_the_mock(users).should_not_have_received_a_create();
        Then_the(userCreatePage).should_have_a_title_of("Create User");
        Then_the(userCreatePage).should_contain_the_data_from(userOne);
        Then_the(userCreatePage).should_not_contain_an_email_error();
        Then_the(userCreatePage).should_contain_a_first_name_error_of("A users first name cannot be empty.");
        Then_the(userCreatePage).should_not_contain_a_last_name_error();
    }

    @Test
    public void I_cannot_create_a_user_with_no_last_name() {

        userOne.setLastName("");
        userCreatePage.visit();

        // When
        userCreatePage.setValues(userOne);
        userCreatePage.clickSave();

        Then_the_mock(users).should_not_have_received_a_create();
        Then_the(userCreatePage).should_have_a_title_of("Create User");
        Then_the(userCreatePage).should_contain_the_data_from(userOne);
        Then_the(userCreatePage).should_not_contain_an_email_error();
        Then_the(userCreatePage).should_not_contain_a_first_name_error();
        Then_the(userCreatePage).should_contain_a_last_name_error_of("A users last name cannot be empty.");
    }

    @Test
    public void I_see_an_error_page_when_the_user_cannot_be_created() {

        Given_the_mock(users).will_not_create(userOne).because_the_operation_failed();
        userCreatePage.visit();

        // When
        userCreatePage.setValues(userOne);
        userCreatePage.clickSave();

        Then_the(errorPage).should_have_a_title_of("Error");
        Then_the(errorPage).should_contain_the_message("Unfortunately the operation you attempted has failed.");
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
    public void I_see_an_error_page_when_I_cannot_go_to_a_users_page() {

        Given_the_mock(users).will_not_return(userOne).because_the_operation_failed();

        // When
        userViewPage.visit(userOne);

        Then_the(errorPage).should_have_a_title_of("Error");
        Then_the(errorPage).should_contain_the_message("Unfortunately the operation you attempted has failed.");
    }

    @Test
    public void I_cannot_got_to_a_users_page_that_does_not_exist() {

        Given_the_mock(users).will_not_return(userOne).because_it_does_not_exist();

        // When
        userViewPage.visit(userOne);

        Then_the(errorPage).should_have_a_title_of("Not Found");
        Then_the(errorPage).should_contain_the_message("The page you have requested cannot be found.");
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
    public void I_can_edited_a_user() {

        Given_the_mock(users).will_first_return(userOne).and_then(userTwo).for_an_id_of(userOne.getId());
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(userTwo);
        userEditPage.clickSave();

        Then_the_mock(users).should_receive_an_update_with_data_from(copyData(userTwo, userOne));
        Then_the(userViewPage).should_have_a_title_containing_the_name_of(userTwo);
        Then_the(userViewPage).should_contain_the_data_from(userTwo);
    }

    @Test
    public void I_can_edited_a_user_with_no_phone_number() {

        final User noPhoneNumberUser = new User(userOne);
        noPhoneNumberUser.setPhoneNumber("");
        Given_the_mock(users).will_first_return(userOne).and_then(noPhoneNumberUser).for_an_id_of(userOne.getId());
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(noPhoneNumberUser);
        userEditPage.clickSave();

        Then_the_mock(users).should_receive_an_update_with_data_from(noPhoneNumberUser);
        Then_the(userViewPage).should_have_a_title_containing_the_name_of(noPhoneNumberUser);
        Then_the(userViewPage).should_contain_the_data_from(noPhoneNumberUser);
    }

    @Test
    public void I_can_edited_a_user_with_no_address() {

        final User noAddressUser = new User(userOne);
        noAddressUser.setAddress(emptyAddress(userOne.getAddress().getId()));
        Given_the_mock(users).will_first_return(userOne).and_then(noAddressUser).for_an_id_of(userOne.getId());
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(noAddressUser);
        userEditPage.clickSave();

        Then_the_mock(users).should_receive_an_update_with_data_from(noAddressUser);
        Then_the(userViewPage).should_have_a_title_containing_the_name_of(noAddressUser);
        Then_the(userViewPage).should_contain_the_data_from(noAddressUser);
    }

    @Test
    public void I_cannot_edited_a_user_with_no_data() {

        Given_the_mock(users).will_return(userOne);
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(emptyUser());
        userEditPage.clickSave();

        Then_the_mock(users).should_not_have_received_an_update();
        Then_the(userEditPage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userEditPage).should_contain_the_data_from(userOne);
        Then_the(userEditPage).should_contain_an_email_error_of("A users email cannot be empty.");
        Then_the(userEditPage).should_contain_a_first_name_error_of("A users first name cannot be empty.");
        Then_the(userEditPage).should_contain_a_last_name_error_of("A users last name cannot be empty.");
    }

    @Test
    public void I_cannot_edited_a_user_with_no_email() {

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
    public void I_cannot_edited_a_user_with_no_first_name() {

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
    public void I_cannot_edited_a_user_with_no_last_name() {

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

    @Test
    public void I_see_an_error_page_when_the_user_cannot_be_edited() {

        Given_the_mock(users).will_return(userOne);
        Given_the_mock(users).will_not_update(userOne).because_the_operation_failed();
        userEditPage.visit(userOne);

        // When
        userEditPage.setValues(userOne);
        userEditPage.clickSave();

        Then_the(errorPage).should_have_a_title_of("Error");
        Then_the(errorPage).should_contain_the_message("Unfortunately the operation you attempted has failed.");
    }

    @Test
    public void I_see_an_error_page_when_editing_a_user_that_does_not_exist() {

        Given_the_mock(users).will_not_return(userOne).because_it_does_not_exist();

        // When
        userEditPage.visit(userOne);

        Then_the(errorPage).should_have_a_title_of("Not Found");
        Then_the(errorPage).should_contain_the_message("The page you have requested cannot be found.");
    }

    @Test
    public void I_can_got_to_a_users_delete_page_from_the_home_page() {

        Given_the_mock(users).will_return_the_list_of_users_in(userList);
        Given_the_mock(users).will_return(userOne);
        homePage.visit();

        // When
        homePage.users().get(0).clickDelete();

        Then_the(userDeletePage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userDeletePage).should_contain_a_warning_for(userOne);
        Then_the(userDeletePage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_delete_page_from_their_view_page() {

        Given_the_mock(users).will_return(userOne);
        userViewPage.visit(userOne);

        // When
        userViewPage.clickDelete();

        Then_the(userDeletePage).should_have_a_title_containing_the_name_of(userOne);
        Then_the(userDeletePage).should_contain_a_warning_for(userOne);
        Then_the(userDeletePage).should_contain_the_data_from(userOne);
    }

    @Test
    public void I_can_got_to_a_users_delete_page() {

        Given_the_mock(users).will_return_each_of_the_users_in(userList);

        for (User user : userList) {

            // When
            userDeletePage.visit(user);

            Then_the(userDeletePage).should_have_a_title_containing_the_name_of(user);
            Then_the(userDeletePage).should_contain_a_warning_for(user);
            Then_the(userDeletePage).should_contain_the_data_from(user);
        }
    }

    @Test
    public void I_can_cancel_the_deletion_of_a_user() {

        Given_the_mock(users).will_return_the_list_of_users_in(userList);
        Given_the_mock(users).will_return(userOne);
        userDeletePage.visit(userOne);

        // When
        userDeletePage.clickCancel();

        Then_the(homePage).should_have_a_title_of("All Users");
        Then_the(homePage).should_contain_a_row_for_each_user_in(userList);
    }

    @Test
    public void I_can_delete_a_user() {

        userList.remove(userTwo);
        Given_the_mock(users).will_return_the_list_of_users_in(userList);
        Given_the_mock(users).will_return(userOne);
        userDeletePage.visit(userTwo);

        // When
        userDeletePage.clickDelete();

        Then_the_mock(users).should_receive_a_delete_with_data_from(userTwo);
        Then_the(homePage).should_have_a_title_of("All Users");
        Then_the(homePage).should_contain_a_row_for_each_user_in(userList);
    }

    @Test
    public void I_see_an_error_page_when_the_user_cannot_be_deleted() {

        Given_the_mock(users).will_return(userOne);
        Given_the_mock(users).will_not_delete(userOne).because_the_operation_failed();
        userDeletePage.visit(userOne);

        // When
        userDeletePage.clickDelete();

        Then_the(errorPage).should_have_a_title_of("Error");
        Then_the(errorPage).should_contain_the_message("Unfortunately the operation you attempted has failed.");
    }

    @Test
    public void I_see_an_error_page_when_deleting_a_user_that_does_not_exist() {

        Given_the_mock(users).will_not_return(userOne).because_it_does_not_exist();

        // When
        userDeletePage.visit(userOne);

        Then_the(errorPage).should_have_a_title_of("Not Found");
        Then_the(errorPage).should_contain_the_message("The page you have requested cannot be found.");
    }
}
