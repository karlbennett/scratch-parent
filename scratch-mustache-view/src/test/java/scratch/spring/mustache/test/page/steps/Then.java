package scratch.spring.mustache.test.page.steps;

import org.openqa.selenium.NoSuchElementException;
import scratch.spring.mustache.test.page.HomePage;
import scratch.spring.mustache.test.page.UserCreatePage;
import scratch.spring.mustache.test.page.UserDeletePage;
import scratch.spring.mustache.test.page.UserEditPage;
import scratch.spring.mustache.test.page.UserViewPage;
import scratch.user.User;
import scratch.user.Users;

import static org.junit.Assert.fail;

public class Then {

    public static HomePageThens Then_the(HomePage page) {
        return new DefaultHomePageThens(page);
    }

    public static UserCreatePageThens Then_the(UserCreatePage page) {
        return new DefaultUserCreatePageThens(page);
    }

    public static UserViewPageThens Then_the(UserViewPage page) {
        return new DefaultUserViewPageThens(page);
    }

    public static UserEditPageThens Then_the(UserEditPage page) {
        return new DefaultUserEditPageThens(page);
    }

    public static UserDeletePageThens Then_the(UserDeletePage page) {
        return new DefaultUserDeletePageThens(page);
    }

    public static UsersThens Then_the_mock(Users users) {
        return new DefaultUsersThens(users);
    }

    public static User copyData(User from, User to) {

        final User copy = new User(to);
        copy.setEmail(from.getEmail());
        copy.setFirstName(from.getFirstName());
        copy.setLastName(from.getLastName());
        copy.setPhoneNumber(from.getPhoneNumber());
        copy.getAddress().setNumber(from.getAddress().getNumber());
        copy.getAddress().setStreet(from.getAddress().getStreet());
        copy.getAddress().setSuburb(from.getAddress().getSuburb());
        copy.getAddress().setCity(from.getAddress().getCity());
        copy.getAddress().setPostcode(from.getAddress().getPostcode());

        return copy;
    }

    public static void element_should_not_exist(Runnable runnable) {
        try {
            runnable.run();
            fail("The element should not exist.");
        } catch (NoSuchElementException e) {
        }
    }
}
