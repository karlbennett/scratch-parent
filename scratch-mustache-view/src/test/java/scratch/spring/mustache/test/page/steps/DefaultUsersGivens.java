package scratch.spring.mustache.test.page.steps;

import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class DefaultUsersGivens implements UsersGivens {

    private final Users users;

    DefaultUsersGivens(Users users) {
        this.users = users;
    }

    @Override
    public void will_return_the_list_of_users_in(List<User> userList) {
        when(users.retrieve()).thenReturn(userList);
    }

    @Override
    public void will_create(User user) {

        final User copy = new User(user);

        final Id id = new Id(copy.getId());

        // Have to setup the ID's so that they are the same as wheat will be sent in the create POST request.
        copy.setId(null);
        copy.getAddress().setId(0L);

        when(users.create(copy)).thenReturn(id);
    }

    @Override
    public void will_return(User user) {
        when(users.retrieve(user.getId())).thenReturn(user);
    }

    @Override
    public AndThen will_first_return(User user) {
        return new DefaultAndThen(user);
    }

    @Override
    public void will_return_each_of_the_users_in(List<User> userList) {

        for (User user : userList) {
            when(users.retrieve(user.getId())).thenReturn(user);
        }
    }

    private class DefaultAndThen implements AndThen {

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
        public void for_an_id_of(Long id) {
            when(users.retrieve(id)).thenReturn(user, userList.toArray(new User[userList.size()]));
        }
    }
}
