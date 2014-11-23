package scratch.spring.mustache.controller;

import scratch.user.User;

/**
 * This user contains {@code null} values for all it's native typed fields.
 *
 * @author Karl Bennett
 */
public class ClearUser extends User {

    public ClearUser() {
        super(null, null, null, null, null, new ClearAddress());
    }
}
