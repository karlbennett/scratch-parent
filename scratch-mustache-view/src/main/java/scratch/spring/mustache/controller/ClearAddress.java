package scratch.spring.mustache.controller;

import scratch.user.Address;

/**
 * This user contains {@code null} values for all it's fields.
 *
 * @author Karl Bennett
 */
public class ClearAddress extends Address {

    public ClearAddress() {
        super(null, null, null, null, null, null);
    }
}
