package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

public interface UserPageTitleThens {

    void should_have_a_title_containing_the_name_of(User user);
}
