package scratch.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scratch.user.data.UserRepository;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;
import static scratch.user.test.UserConstants.user;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserPersistenceTest.class)
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class UserPersistenceTest {

    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void I_can_persist_a_user() {

        final User expected = user();

        final Id id = repository.save(expected);

        final Long userId = id.getId();

        final User actual = repository.findOne(userId);

        expected.setId(userId);
        expected.getAddress().setId(actual.getAddress().getId());

        assertEquals("the persisted user should be correct.", expected, actual);
    }

    @Test
    public void I_can_persist_a_user_with_no_address() {

        final User expected = user();
        expected.setAddress(null);

        final Id id = repository.save(expected);

        final Long userId = id.getId();

        final User actual = repository.findOne(userId);

        expected.setId(userId);

        assertEquals("the persisted user should be correct.", expected, actual);
    }

    @Test(expected = ConstraintViolationException.class)
    public void I_cannot_persist_an_empty_user() {

        repository.save(new User(null, null, null, null, null, null));
    }

    @Test(expected = ConstraintViolationException.class)
    public void I_cannot_persist_a_user_with_no_email() {

        final User user = user();
        user.setEmail(null);

        repository.save(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void I_cannot_persist_a_user_with_no_first_name() {

        final User user = user();
        user.setFirstName(null);

        repository.save(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void I_cannot_persist_a_user_with_no_last_name() {

        final User user = user();
        user.setLastName(null);

        repository.save(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void I_cannot_persist_a_user_with_an_existing_email() {

        repository.save(user());
        repository.save(user());
    }
}
