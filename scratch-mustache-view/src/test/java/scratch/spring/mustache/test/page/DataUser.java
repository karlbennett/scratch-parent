package scratch.spring.mustache.test.page;

import scratch.user.Address;
import scratch.user.User;

public class DataUser extends EqualityUser {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final EqualityAddress address;

    public DataUser(User user) {
        this(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(),
                setAddress(user.getAddress()));
    }

    private static EqualityAddress setAddress(Address address) {

        if (null == address) {
            return new NullAddress();
        }

        return new DataAddress(address);
    }

    public DataUser(String email, String firstName, String lastName, String phoneNumber, EqualityAddress address) {
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

    public EqualityAddress getAddress() {
        return address;
    }
}
