package scratch.spring.mustache.test.page;

import scratch.user.User;

public class DataUser extends EqualityUser {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final DataAddress address;

    public DataUser(User user) {
        this(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(),
                new DataAddress(user.getAddress()));
    }

    public DataUser(String email, String firstName, String lastName, String phoneNumber, DataAddress address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public DataAddress getAddress() {
        return address;
    }
}
