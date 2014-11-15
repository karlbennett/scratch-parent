package scratch.spring.persistence.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import scratch.user.Address;
import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import java.util.concurrent.Callable;

import static java.lang.String.format;

/**
 * An implementation of {@link Users} that delegates to a Spring Data repository.
 *
 * @author Karl Bennett
 */
@Component
public class RepositoryUsers implements Users {

    @Autowired
    private UserRepository repository;

    @Override
    public Id create(final User user) {

        // Null out the ID's to make sure that an attempt is made at a create not an update.
        user.setId(null);

        final Address address = user.getAddress();
        if (null != address) {
            address.setId(null);
        }

        return withExceptionHandling(new Callable<Id>() {
            @Override
            public Id call() throws Exception {
                return new Id(repository.save(user));
            }
        });
    }

    @Override
    public User retrieve(Long id) {

        checkExists(id);

        return repository.findOne(id);
    }

    @Override
    public Iterable<User> retrieve() {

        return repository.findAll();
    }

    @Override
    public void update(final User user) {

        checkExists(user.getId());

        withExceptionHandling(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                repository.save(user);
                return null;
            }
        });
    }

    @Override
    public void delete(Long id) {

        checkExists(id);

        repository.delete(id);
    }

    @Override
    public void deleteAll() {

        repository.deleteAll();
    }

    private void checkExists(Long id) {

        if (null == id || !repository.exists(id)) {
            throw new IllegalArgumentException(format("A user with the ID (%d) could not be found.", id));
        }
    }

    private static <T> T withExceptionHandling(Callable<T> handle) {

        try {

            return handle.call();

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
