package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

import java.util.List;

public interface UsersGivens {

    void will_return_the_list_of_users_in(List<User> userList);

    void will_create(User user);

    void will_return(User user);

    UserAndThen will_first_return(User user);

    void will_return_each_of_the_users_in(List<User> userList);

    interface UserAndThen {

        UserAndThen and_then(User user);

        void for_an_id_of(Long id);
    }
}
