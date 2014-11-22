package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.UserPage;
import scratch.user.User;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class DefaultUserPageTitleThens implements UserPageTitleThens {

    private final UserPage page;
    private final String titlePrefix;

    public DefaultUserPageTitleThens(UserPage page, String titlePrefix) {
        this.page = page;
        this.titlePrefix = titlePrefix;
    }

    @Override
    public void should_have_a_title_containing_the_name_of(User user) {
        assertThat("The title should contain the correct user name.", page.getTitle(),
                containsString(format("%s: %s %s", titlePrefix, user.getFirstName(), user.getLastName())));
    }
}
