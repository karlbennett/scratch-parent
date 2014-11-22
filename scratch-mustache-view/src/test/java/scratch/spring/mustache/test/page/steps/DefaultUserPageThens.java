package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.DataUser;
import scratch.spring.mustache.test.page.EqualityUser;
import scratch.spring.mustache.test.page.UserPage;
import scratch.user.User;

import static org.junit.Assert.assertEquals;

class DefaultUserPageThens implements UserPageThens {

    private final UserPage page;

    DefaultUserPageThens(UserPage page) {
        this.page = page;
    }

    @Override
    public void should_contain_the_data_from(User user) {
        should_contain_the_data_from(new DataUser(user));
    }

    public void should_contain_the_data_from(EqualityUser user) {
        assertEquals("The page should contain the correct user data.", user, page);
    }
}
