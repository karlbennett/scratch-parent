package scratch.spring.mustache.test.page;

public class NullEqualityUser extends EqualityUser {

    public String getEmail() {
        return "";
    }

    public String getFirstName() {
        return "";
    }

    public String getLastName() {
        return "";
    }

    public String getPhoneNumber() {
        return "";
    }

    public NullEqualityAddress getAddress() {
        return new NullEqualityAddress();
    }
}
