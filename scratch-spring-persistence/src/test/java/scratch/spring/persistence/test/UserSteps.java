package scratch.spring.persistence.test;

import scratch.spring.persistence.test.data.DBUnitUserRepository;
import scratch.user.Address;
import scratch.user.Id;
import scratch.user.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static scratch.spring.persistence.test.Users.user;

public class UserSteps {

    private final DBUnitUserRepository dbUnitRepository;

    public UserSteps(DBUnitUserRepository dbUnitRepository) {

        this.dbUnitRepository = dbUnitRepository;
    }

    public void all_users_are_cleaned_up() {

        dbUnitRepository.deleteAll();
    }

    public void then_the_user_should_be_created(Id id, User expected) {

        final Long userId = id.getId();

        final User actual = dbUnitRepository.findOne(userId);

        expected.setId(userId);

        final Address address = expected.getAddress();
        if (null != address) {
            address.setId(actual.getAddress().getId());
        }

        assertEquals("the user should be created.", expected, actual);
    }

    public void then_the_user_should_be_updated(User actual) {

        final User expected = dbUnitRepository.findOne(actual.getId());

        // We cannot know what the addresses ID is if it is updated.
        actual.getAddress().setId(expected.getAddress().getId());

        assertEquals("the user should be updated.", expected, actual);
    }

    public User given_a_user_has_been_persisted() {

        return given_a_user_has_been_persisted(user());
    }

    public User given_a_user_has_been_persisted(User user) {

        final User persistedUser = dbUnitRepository.save(user);

        assertTrue("the user should exist.", dbUnitRepository.exists(persistedUser.getId()));

        return persistedUser;
    }

    public void then_the_user_should_no_longer_be_persisted(User user) {

        assertFalse("the user should no longer be persisted.", dbUnitRepository.exists(user.getId()));
    }
}
