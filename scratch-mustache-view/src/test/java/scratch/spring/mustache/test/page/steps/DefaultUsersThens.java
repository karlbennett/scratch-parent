package scratch.spring.mustache.test.page.steps;

import scratch.user.User;
import scratch.user.Users;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class DefaultUsersThens implements UsersThens {

    private final Users users;

    DefaultUsersThens(Users users) {
        this.users = users;
    }

    @Override
    public void should_receive_an_update_with_data_from(User user) {
        verify(users).update(user);
    }

    @Override
    public void should_not_have_received_an_update() {
        verify(users, never()).update(any(User.class));
    }
}
