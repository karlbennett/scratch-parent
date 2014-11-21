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
}
