package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.UserViewPage;
import scratch.user.User;

public class DefaultUserViewPageThens implements UserViewPageThens {

    private final UserPageTitleThens userPageTitleThens;
    private final UserPageThens userPageThens;

    public DefaultUserViewPageThens(UserViewPage page) {
        userPageTitleThens = new DefaultUserPageTitleThens(page, "User");
        userPageThens = new DefaultUserPageThens(page);
    }

    @Override
    public void should_have_a_title_containing_the_name_of(User user) {
        userPageTitleThens.should_have_a_title_containing_the_name_of(user);
    }

    @Override
    public void should_contain_the_data_from(User user) {
        userPageThens.should_contain_the_data_from(user);
    }
}
