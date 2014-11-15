package scratch.user;

import org.junit.Test;
import scratch.user.test.Equals;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static scratch.user.test.UserConstants.USER_ID;

public class IdTest {

    @Test
    public void I_can_copy_an_id() {

        final Id id = new Id(USER_ID);

        assertEquals("the copied id should be correct.", id, new Id(id));
    }

    @Test
    public void I_can_check_the_equality_of_an_id() {

        final Equals<Id> eq = new Equals<Id>() {
            @Override
            public boolean equal(Id left, Object right) {
                return left.equals(right);
            }
        };

        I_can_check_the_equality_of_a_user(2L, new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(null, new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(2L, new CreateWithNull(), eq);
    }

    @Test
    public void I_can_check_the_hash_code_of_an_id() {

        final Equals<Id> eq = new Equals<Id>() {
            @Override
            public boolean equal(Id left, Object right) {

                if (null == right) {
                    return false;
                }

                return left.hashCode() == right.hashCode();
            }
        };

        I_can_check_the_equality_of_a_user(2L, new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(null, new CreateWithId(), eq);
        I_can_check_the_equality_of_a_user(2L, new CreateWithNull(), eq);
    }

    public static void I_can_check_the_equality_of_a_user(Long differentId, Create create, Equals<Id> eq) {

        final Id left = create.id();
        final Id right = create.id();

        assertTrue("a user is equal to it's self.", eq.equal(left, left));
        assertTrue("a user is equal to another user with the same data.", eq.equal(left, right));

        final Id differentIdUser = create.id();
        differentIdUser.setId(differentId);
        assertFalse("a user is not equal to a user with a different id.", eq.equal(left, differentIdUser));

        assertFalse("a user is not equal to an object.", eq.equal(left, new Object()));

        assertFalse("a user is not equal to null.", eq.equal(left, null));
    }

    @Test
    public void I_can_to_string_an_id() {

        final Id id = new Id(USER_ID);
        id.setId(1L);

        assertEquals("the id should produce the correct toString value.",
                format(
                /**/"Id {id = %d}",
                        id.getId()
                ),
                id.toString());
    }

    private static interface Create {

        public Id id();
    }

    private static class CreateWithId implements Create {

        @Override
        public Id id() {

            return new Id(USER_ID);
        }
    }

    private static class CreateWithNull implements Create {

        @Override
        public Id id() {

            return new Id();
        }
    }
}
