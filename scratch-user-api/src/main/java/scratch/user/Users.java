package scratch.user;

/**
 * The minimum {@link User} CRUD API that must be implemented.
 *
 * @author Karl Bennett
 */
public interface Users {

    Id create(User user);

    User retrieve(Long id);

    Iterable<User> retrieve();

    void update(User user);

    void delete(Long id);

    void deleteAll();
}
