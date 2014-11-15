package scratch.spring.mustache.test.page;

import scratch.user.User;

public class UserRow extends AbstractUserRow {

    private final String email;
    private final String firstName;
    private final String lastName;

    public UserRow(User user) {
        this(user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public UserRow(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
}
