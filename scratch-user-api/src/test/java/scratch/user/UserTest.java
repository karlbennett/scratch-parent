package scratch.user;

import org.junit.Test;
import scratch.user.test.Equals;
import scratch.user.test.UserConstants;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static scratch.user.test.AddressConstants.addressOne;
import static scratch.user.test.UserConstants.user;

public class UserTest {

    @Test
    public void I_can_copy_a_user() {

        final User user = user();

        assertEquals("the copied user should be correct.", user, new User(user));
    }

    @Test
    public void I_can_check_the_equality_of_a_user() {

        final Equals<User> eq = new Equals<User>() {
            @Override
            public boolean equal(User left, Object right) {
                return left.equals(right);
            }
        };

        I_can_check_the_equality_of_a_user(2L, "different", addressOne(), new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(null, null, null, new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(2L, "different", addressOne(), new CreateWithNull(), eq);
    }

    @Test
    public void I_can_check_the_hash_code_of_a_user() {

        final Equals<User> eq = new Equals<User>() {
            @Override
            public boolean equal(User left, Object right) {

                if (null == right) {
                    return false;
                }

                return left.hashCode() == right.hashCode();
            }
        };

        I_can_check_the_equality_of_a_user(2L, "different", addressOne(), new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(null, null, null, new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(2L, "different", addressOne(), new CreateWithNull(), eq);
    }

    public static void I_can_check_the_equality_of_a_user(Long differentId, String differentValue,
                                                          Address differentAddress, Create create, Equals<User> eq) {

        final User left = create.user();
        final User right = create.user();

        assertTrue("a user is equal to it's self.", eq.equal(left, left));
        assertTrue("a user is equal to another user with the same data.", eq.equal(left, right));

        final User differentIdUser = create.user();
        differentIdUser.setId(differentId);
        assertFalse("a user is not equal to a user with a different id.", eq.equal(left, differentIdUser));

        final User differentEmailUser = create.user();
        differentEmailUser.setEmail(differentValue);
        assertFalse("a user is not equal to a user with a different email.", eq.equal(left, differentEmailUser));

        final User differentFirstNameUser = create.user();
        differentFirstNameUser.setFirstName(differentValue);
        assertFalse("a user is not equal to a user with a different first name.",
                eq.equal(left, differentFirstNameUser));

        final User differentLastNameUser = create.user();
        differentLastNameUser.setLastName(differentValue);
        assertFalse("a user is not equal to a user with a different last name.", eq.equal(left, differentLastNameUser));

        final User differentPhoneNumberUser = create.user();
        differentPhoneNumberUser.setPhoneNumber(differentValue);
        assertFalse("a user is not equal to a user with a different phone number.",
                eq.equal(left, differentPhoneNumberUser));

        final User differentAddressUser = create.user();
        differentAddressUser.setAddress(differentAddress);
        assertFalse("a user is not equal to a user with a different address.", eq.equal(left, differentAddressUser));

        assertFalse("a user is not equal to an object.", eq.equal(left, new Object()));

        assertFalse("a user is not equal to null.", eq.equal(left, null));
    }

    @Test
    public void I_can_to_string_a_user() {

        final User user = user();
        user.setId(1L);

        assertEquals("the user should produce the correct toString value.",
                format(
                /**/"User {id = %d, email = '%s', firstName = '%s', lastName = '%s', phoneNumber = '%s', address = %s}",
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhoneNumber(),
                        user.getAddress()
                ),
                user.toString());
    }

    private static interface Create {

        public User user();
    }

    private static class CreateWithId implements Create {

        @Override
        public User user() {

            final User user = UserConstants.user();
            user.setId(1L);

            return user;
        }
    }

    private static class CreateWithNull implements Create {

        @Override
        public User user() {

            return new User();
        }
    }
}
