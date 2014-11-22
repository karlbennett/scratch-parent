package scratch.spring.mustache.test.page.steps;

import scratch.user.User;
import scratch.user.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class DefaultUsersGivens implements UsersGivens {

    private final Users users;

    DefaultUsersGivens(Users users) {
        this.users = users;
    }

    @Override
    public void will_return_the_list_of_users_in(List<User> userList) {
        when(users.retrieve()).thenReturn(userList);
    }

    @Override
    public void will_return(User user) {
        when(users.retrieve(user.getId())).thenReturn(user);
    }

    @Override
    public DefaultAndThen will_first_return(User user) {
        return new DefaultAndThen(user);
    }

    @Override
    public void will_return_each_of_the_users_in(List<User> userList) {

        for (User user : userList) {
            when(users.retrieve(user.getId())).thenReturn(user);
        }
    }

    public class DefaultAndThen implements AndThen {

        private final User user;
        private final List<User> userList;

        private DefaultAndThen(User user) {
            this.user = user;
            this.userList = new ArrayList<>();
        }

        @Override
        public DefaultAndThen and_then(User user) {
            userList.add(user);
            return this;
        }

        @Override
        public void for_the_id_from(User idUser) {
            when(users.retrieve(idUser.getId())).thenReturn(user, userList.toArray(new User[userList.size()]));
        }
    }
}
