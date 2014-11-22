package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.DataUser;
import scratch.spring.mustache.test.page.EqualityUser;
import scratch.spring.mustache.test.page.UserPage;
import scratch.user.User;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserPageThens {

    private final UserPage page;
    private final String titlePrefix;

    public UserPageThens(UserPage page, String titlePrefix) {
        this.page = page;
        this.titlePrefix = titlePrefix;
    }

    public void should_have_a_title_containing_the_name_of(User user) {
        assertThat("The title should contain the correct user name.", page.getTitle(),
                containsString(format("%s: %s %s", titlePrefix, user.getFirstName(), user.getLastName())));
    }

    public void should_contain_the_data_from(User user) {
        should_contain_the_data_from(new DataUser(user));
    }

    public void should_contain_the_data_from(EqualityUser user) {
        assertEquals("The page should contain the correct user data.", user, page);
    }
}
