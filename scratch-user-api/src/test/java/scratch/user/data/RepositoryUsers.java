package scratch.user.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

/**
 * An implementation of the {@link scratch.user.Users} interface that uses a spring data repository.
 *
 * @author Karl Bennett
 */
@Component
public class RepositoryUsers implements Users {

    private final UserRepository repository;

    @Autowired
    public RepositoryUsers(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Id create(User user) {

        // Null out the ID's so that a create is actually attempted not an update.
        user.setId(null);
        user.getAddress().setId(null);

        return new Id(repository.save(user));
    }

    @Override
    public User retrieve(Long id) {

        return repository.findOne(id);
    }

    @Override
    public Iterable<User> retrieve() {

        return repository.findAll();
    }

    @Override
    public void update(User user) {

        if (null == user.getId()) {
            throw new EntityNotFoundException(format("User %s has not been persisted.", user));
        }

        repository.save(user);
    }

    @Override
    public void delete(Long id) {

        repository.delete(id);
    }

    @Override
    public void deleteAll() {

        repository.deleteAll();
    }
}
