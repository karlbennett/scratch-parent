package scratch.user;

/**
 * The minimum {@link User} CRUD API that must be implemented.
 *
 * @author Karl Bennett
 */
public interface Users {

    /**
     * @param user the user to be created, any contained ID will be ignored.
     * @return the ID that was generated for the new user.
     * @throws IllegalStateException if the user cannot be added.
     */
    Id create(User user) throws IllegalStateException;

    /**
     * @param id the ID of the user.
     * @return the required user.
     * @throws IllegalArgumentException if the user does not exist.
     */
    User retrieve(Long id) throws IllegalArgumentException;

    /**
     * @return all the currently persisted users.
     */
    Iterable<User> retrieve();

    /**
     * @param user the user to be updated.
     * @throws IllegalArgumentException if the user does not exist.
     * @throws IllegalStateException    if an attempt is made to update the user with invalid data..
     */
    void update(User user) throws IllegalArgumentException, IllegalStateException;

    /**
     * @param id the ID of the user.
     * @throws IllegalArgumentException if the user does not exist.
     */
    void delete(Long id) throws IllegalArgumentException;

    /**
     * Delete all the currently persisted users.
     */
    void deleteAll();
}
