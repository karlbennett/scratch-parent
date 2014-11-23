package scratch.spring.mustache.test.page.steps;

import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
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
        when(users.create(copyWithoutIds(user))).thenReturn(new Id(user.getId()));
    }

    @Override
    public void will_return(User user) {
        when(users.retrieve(user.getId())).thenReturn(user);
    }

    @Override
    public UserAndThen will_first_return(User user) {
        return new DefaultUserAndThen(user);
    }

    @Override
    public void will_return_each_of_the_users_in(List<User> userList) {

        for (User user : userList) {
            when(users.retrieve(user.getId())).thenReturn(user);
        }
    }

    @Override
    public CreateFailedBecause will_not_create(User user) {
        return new CreateFailedBecause(user);
    }

    @Override
    public RetrieveFailedBecause will_not_return(User user) {
        return new RetrieveFailedBecause(user);
    }

    @Override
    public UpdateFailedBecause will_not_update(User user) {
        return new UpdateFailedBecause(user);
    }

    @Override
    public DeleteFailedBecause will_not_delete(User user) {
        return new DeleteFailedBecause(user);
    }

    private static User copyWithoutIds(User user) {

        final User copy = new User(user);

        // Have to setup the ID's so that they are the same as wheat will be sent in the create POST request.
        copy.setId(null);
        copy.getAddress().setId(null);

        return copy;
    }

    private class DefaultUserAndThen implements UserAndThen {

        private final User user;
        private final List<User> userList;

        private DefaultUserAndThen(User user) {
            this.user = user;
            this.userList = new ArrayList<>();
        }

        @Override
        public DefaultUserAndThen and_then(User user) {
            userList.add(user);
            return this;
        }

        @Override
        public void for_an_id_of(Long id) {
            when(users.retrieve(id)).thenReturn(user, userList.toArray(new User[userList.size()]));
        }
    }

    private class CreateFailedBecause implements FailedBecause {

        private final User user;

        private CreateFailedBecause(User user) {
            this.user = user;
        }

        @Override
        public void because_the_operation_failed() {
            because_of_an_error(new IllegalStateException("User create test exception."));
        }

        @Override
        public void because_it_does_not_exist() {
            because_of_an_error(new IllegalStateException("User update test exception."));
        }

        public void because_of_an_error(Exception e) {
            when(users.create(copyWithoutIds(user))).thenThrow(e);
        }
    }

    private class RetrieveFailedBecause implements FailedBecause {

        private final User user;

        private RetrieveFailedBecause(User user) {
            this.user = user;
        }

        @Override
        public void because_the_operation_failed() {
            because_of_an_error(new IllegalStateException("User retrieve failed exception."));
        }

        @Override
        public void because_it_does_not_exist() {
            because_of_an_error(new IllegalArgumentException("User retrieve bot found exception."));
        }

        public void because_of_an_error(Exception e) {
            when(users.retrieve(user.getId())).thenThrow(e);
        }
    }

    private class UpdateFailedBecause implements FailedBecause {

        private final User user;

        private UpdateFailedBecause(User user) {
            this.user = user;
        }

        @Override
        public void because_the_operation_failed() {
            because_of_an_error(new IllegalStateException("User update failed exception."));
        }

        @Override
        public void because_it_does_not_exist() {
            because_of_an_error(new IllegalArgumentException("User update bot found exception."));
        }

        public void because_of_an_error(Exception e) {
            doThrow(e).when(users).update(user);
        }
    }

    private class DeleteFailedBecause implements FailedBecause {

        private final User user;

        private DeleteFailedBecause(User user) {
            this.user = user;
        }

        @Override
        public void because_the_operation_failed() {
            because_of_an_error(new IllegalStateException("User delete failed exception."));
        }

        @Override
        public void because_it_does_not_exist() {
            because_of_an_error(new IllegalArgumentException("User delete bot found exception."));
        }

        public void because_of_an_error(Exception e) {
            doThrow(e).when(users).delete(user.getId());
        }
    }
}
