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
import scratch.spring.mustache.test.page.BaseUrl;
import scratch.spring.mustache.test.page.HomePage;
import scratch.spring.mustache.test.page.UserPage;
import scratch.spring.mustache.test.page.UserRow;
import scratch.user.Users;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static scratch.spring.mustache.test.UserConstants.containsAll;
import static scratch.spring.mustache.test.UserConstants.userOne;
import static scratch.spring.mustache.test.UserConstants.userThree;
import static scratch.spring.mustache.test.UserConstants.userTwo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringMustacheServlet.class)
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

        baseUrl.setPort(port);
    }

    @Test
    public void I_can_view_the_home_page() {

        when(this.users.retrieve()).thenReturn(asList(userOne(), userTwo(), userThree()));

        homePage.visit();

        assertThat("the correct users should be displayed.", homePage.users(),
                containsAll(asList(new UserRow(userOne()), new UserRow(userTwo()), new UserRow(userThree()))));
    }

    @Test
    public void I_can_view_a_user() {

        when(this.users.retrieve()).thenReturn(asList(userOne(), userTwo(), userThree()));

        homePage.visit();

        homePage.users().get(0).clickEmail();
    }
}
