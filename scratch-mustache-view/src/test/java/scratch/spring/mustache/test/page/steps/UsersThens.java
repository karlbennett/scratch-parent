package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

public interface UsersThens {

    void should_receive_an_update_with_data_from(User user);

    void should_not_have_received_an_update();

    void should_not_have_received_a_create();

    void should_receive_a_delete_with_data_from(User user);
}
