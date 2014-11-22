package scratch.spring.mustache.test.page;

public class NullUser extends EqualityUser {

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

    public NullAddress getAddress() {
        return new NullAddress();
    }
}
