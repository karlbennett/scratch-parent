package scratch.user.test;

import scratch.user.User;

import static scratch.user.test.AddressConstants.address;

/**
 * Data for user persistence tests.
 *
 * @author Karl Bennett
 */
public class UserConstants {

    public static final Long USER_ID = 1L;
    public static final String EMAIL = "test@email.com";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "User";
    public static final String PHONE_NUMBER = "5551234";

    public static User user() {

        return new User(EMAIL, FIRST_NAME, LAST_NAME, PHONE_NUMBER, address());
    }
}
