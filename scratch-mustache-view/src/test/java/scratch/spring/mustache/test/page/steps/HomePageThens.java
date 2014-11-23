package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

import java.util.List;

public interface HomePageThens extends PageTitleThens {

    void should_contain_a_row_for_each_user_in(List<User> users);
}
