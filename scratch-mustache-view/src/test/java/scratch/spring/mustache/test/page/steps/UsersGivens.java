package scratch.spring.mustache.test.page.steps;

import scratch.user.User;

import java.util.List;

public interface UsersGivens {

    void will_return_the_list_of_users_in(List<User> userList);

    void will_create(User user);

    void will_return(User user);

    AndThen will_first_return(User user);

    void will_return_each_of_the_users_in(List<User> userList);

    public interface AndThen {

        public AndThen and_then(User user);

        public void for_the_id_from(User idUser);
    }
}
