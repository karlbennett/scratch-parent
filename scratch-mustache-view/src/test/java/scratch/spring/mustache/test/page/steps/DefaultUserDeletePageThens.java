package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.UserDeletePage;
import scratch.user.User;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

class DefaultUserDeletePageThens implements UserDeletePageThens {

    private final UserPageTitleThens userPageTitleThens;
    private final UserPageThens userPageThens;
    private final UserDeletePage page;

    DefaultUserDeletePageThens(UserDeletePage page) {
        userPageTitleThens = new DefaultUserPageTitleThens(page, "Delete User");
        userPageThens = new DefaultUserPageThens(page);
        this.page = page;
    }

    @Override
    public void should_have_a_title_containing_the_name_of(User user) {
        userPageTitleThens.should_have_a_title_containing_the_name_of(user);
    }

    @Override
    public void should_contain_the_data_from(User user) {
        userPageThens.should_contain_the_data_from(user);
    }

    @Override
    public void should_contain_a_warning_for(User user) {
        assertEquals("The delete page warning should be correct.",
                format("Are you sure you wish to delete user: %s %s", user.getFirstName(), user.getLastName()),
                page.getWarning()
        );
    }
}
