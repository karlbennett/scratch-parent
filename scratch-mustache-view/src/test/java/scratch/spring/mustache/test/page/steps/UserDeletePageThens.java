package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

public interface UserDeletePageThens extends UserPageTitleThens, UserPageThens {

    void should_contain_a_warning_for(User user);
}
