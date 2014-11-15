package scratch.spring.mustache.test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import scratch.user.Address;
import scratch.user.User;

import java.util.Collection;

import static java.util.Arrays.asList;

public class UserConstants {

    public static User userOne() {

        final User user = new User("test1@email.com", "Test1", "User1", "5551234",
                new Address(3, "This Road1", "That Suburb1", "Your City1", "ABC123"));
        user.setId(1L);
        user.getAddress().setId(2L);

        return user;
    }

    public static User userTwo() {

        final User user = new User("test2@email.com", "Test2", "User2", "5551235",
                new Address(6, "This Road2", "That Suburb2", "Your City2", "ABC124"));
        user.setId(4L);
        user.getAddress().setId(5L);

        return user;
    }

    public static User userThree() {

        final User user = new User("test3@email.com", "Test3", "User3", "5551236",
                new Address(9, "This Road3", "That Suburb3", "Your City3", "ABC125"));
        user.setId(7L);
        user.getAddress().setId(8L);

        return user;
    }

    public static Iterable<User> users() {

        return asList(userOne(), userTwo(), userThree());
    }

    public static Matcher<? super Collection> containsAll(final Iterable expected) {

        return new TypeSafeMatcher<Collection>() {

            @Override
            protected boolean matchesSafely(Collection actual) {

                int count = 0;

                for (Object element : expected) {

                    if (!actual.contains(element)) {
                        return false;
                    }

                    count++;
                }

                return actual.size() == count;
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(expected);
            }
        };
    }
}
