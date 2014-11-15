package scratch.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private Users users;

    @Test
    public void I_can_persist_a_user() {

        final User expected = user();

        final Id id = users.create(expected);

        final Long userId = id.getId();

        final User actual = users.retrieve(userId);

        expected.setId(userId);
        expected.getAddress().setId(actual.getAddress().getId());

        assertEquals("the persisted user should be correct.", expected, actual);
    }

    @Test(expected = ConstraintViolationException.class)
    public void I_cannot_persist_an_invalid_user() {

        final User user = user();
        user.setEmail(null);

        users.create(user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void I_cannot_persist_a_user_with_an_existing_email() {

        final User user = user();

        users.create(user);
        users.create(user);
    }
}
