package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

public interface UserPageThens {

    void should_contain_the_data_from(User user);
}
