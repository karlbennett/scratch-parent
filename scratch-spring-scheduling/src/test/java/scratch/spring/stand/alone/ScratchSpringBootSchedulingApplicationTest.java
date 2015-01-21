package scratch.spring.stand.alone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scratch.ScratchSpringBootApplication;
import scratch.user.User;
import scratch.user.Users;

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringBootApplication.class)
public class ScratchSpringBootSchedulingApplicationTest {

    @Autowired
    private Users users;

    @Test
    public void All_test_users_are_deleted() throws InterruptedException {

        final User one = mock(User.class);
        final User two = mock(User.class);
        final User three = mock(User.class);

        final long idOne = 1L;
        final long idTwo = 2L;
        final long idThree = 3L;

        // Given
        when(users.retrieve()).thenReturn(asList(one, two, three));
        when(one.getId()).thenReturn(idOne);
        when(one.getFirstName()).thenReturn("[TEST] One");
        when(two.getId()).thenReturn(idTwo);
        when(two.getFirstName()).thenReturn("Two");
        when(three.getId()).thenReturn(idThree);
        when(three.getFirstName()).thenReturn("[TEST] Three");

        // When
        sleep(1100); // Sleep long enough for the scheduled task.

        // Then
        verify(users, atLeastOnce()).retrieve();
        verify(users, atLeastOnce()).delete(idOne);
        verify(users, never()).delete(idTwo);
        verify(users, atLeastOnce()).delete(idThree);
    }
}
