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
import scratch.spring.mustache.test.page.DataUser;
import scratch.spring.mustache.test.page.DataUserRow;
import scratch.spring.mustache.test.page.HomePage;
import scratch.spring.mustache.test.page.UserPage;
import scratch.user.User;
import scratch.user.Users;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static scratch.spring.mustache.test.UserConstants.containsAll;
import static scratch.spring.mustache.test.UserConstants.userOne;
import static scratch.spring.mustache.test.UserConstants.userThree;
import static scratch.spring.mustache.test.UserConstants.userTwo;

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
    private UserPage userPage;

    @Autowired
    private BaseUrl baseUrl;

    @Before
    public void setUp() {

        reset(users);

        baseUrl.setPort(port);
    }

    @Test
    public void I_can_view_the_home_page() {

        when(users.retrieve()).thenReturn(asList(userOne(), userTwo(), userThree()));

        homePage.visit();

        assertThat("the correct users should be displayed.", homePage.users(),
                containsAll(asList(new DataUserRow(userOne()), new DataUserRow(userTwo()),
                        new DataUserRow(userThree()))));
    }

    @Test
    public void I_can_view_a_user() {

        final User user = userOne();

        when(users.retrieve()).thenReturn(asList(user, userTwo(), userThree()));
        when(users.retrieve(user.getId())).thenReturn(user);

        homePage.visit();

        homePage.users().get(0).clickView();

        userPage.validate(user.getFirstName(), user.getLastName());

        assertEquals("the correct user page should be displayed.", new DataUser(user), userPage);
    }

    @Test
    public void I_can_got_to_a_user_page() {

        final User userOne = userOne();
        final User userTwo = userTwo();
        final User userThree = userThree();

        final List<User> users = asList(userOne, userTwo, userThree);

        when(this.users.retrieve()).thenReturn(users);
        when(this.users.retrieve(userOne.getId())).thenReturn(userOne);
        when(this.users.retrieve(userTwo.getId())).thenReturn(userTwo);
        when(this.users.retrieve(userThree.getId())).thenReturn(userThree);

        for (User user : users) {

            userPage.visit(user.getId(), user.getFirstName(), user.getLastName());

            assertEquals("the correct user page should be displayed.", new DataUser(user), userPage);
        }
    }
}
