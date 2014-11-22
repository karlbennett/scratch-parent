package scratch.spring.mustache.test.page.steps;

import scratch.user.Users;

public class Given {

    public static UsersGivens Given_the_mock(Users users) {
        return new UsersGivens(users);
    }
}
