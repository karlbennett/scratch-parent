package scratch.spring.mustache.test.page;

import scratch.user.User;
import scratch.user.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class Given {

    public static UsersGivens Given_the_mock(Users users) {
        return new UsersGivens(users);
    }

    public static class UsersGivens {

        private final Users users;

        private UsersGivens(Users users) {
            this.users = users;
        }

        public void will_return_the_list_of_users_in(List<User> userList) {
            when(users.retrieve()).thenReturn(userList);
        }

        public void will_return(User user) {
            when(users.retrieve(user.getId())).thenReturn(user);
        }

        public AndThen will_first_return(User user) {
            return new AndThen(user);
        }

        public void will_return_each_of_the_users_in(List<User> userList) {

            for (User user : userList) {
                when(users.retrieve(user.getId())).thenReturn(user);
            }
        }

        public class AndThen {

            private final User user;
            private final List<User> userList;

            private AndThen(User user) {
                this.user = user;
                this.userList = new ArrayList<>();
            }

            public AndThen and_then(User user) {
                userList.add(user);
                return this;
            }

            public void for_the_id_from(User idUser) {
                when(users.retrieve(idUser.getId())).thenReturn(user, userList.toArray(new User[userList.size()]));
            }
        }
    }
}
