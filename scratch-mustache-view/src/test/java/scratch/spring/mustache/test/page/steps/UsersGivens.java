package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

import java.util.List;

public interface UsersGivens {

    void will_return_the_list_of_users_in(List<User> userList);

    void will_create(User user);

    void will_return(User user);

    UserAndThen will_first_return(User user);

    void will_return_each_of_the_users_in(List<User> userList);

    FailedBecause will_not_create(User user);

    FailedBecause will_not_return(User user);

    FailedBecause will_not_update(User user);

    FailedBecause will_not_delete(User user);

    interface UserAndThen {

        UserAndThen and_then(User user);

        void for_an_id_of(Long id);
    }

    interface FailedBecause {

        void because_the_operation_failed();

        void because_it_does_not_exist();
    }
}
