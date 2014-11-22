package scratch.user;

/**
 * This class represents a null or empty {@link User}. All it's values are set to their empty representation.
 *
 * @author Karl Bennett
 */
public class NullUser extends User {

    public NullUser() {
        super(0L, "", "", "", "", new NullAddress());
    }
}
