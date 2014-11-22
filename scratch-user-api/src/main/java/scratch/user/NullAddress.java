package scratch.user;

/**
 * This class represents a null or empty {@link Address}. All it's values are set to their empty representation.
 *
 * @author Karl Bennett
 */
public class NullAddress extends Address {

    public NullAddress() {
        super(0L, 0, "", "", "", "");
    }
}
