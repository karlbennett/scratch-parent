package scratch.user.data;

import org.springframework.data.repository.CrudRepository;
import scratch.user.User;

/**
 * This is a Spring Data {@link scratch.user.User} repository that is used to test that the {@code User} can actually be persisted
 * with an ORM.
 *
 * @author Karl Bennett
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
