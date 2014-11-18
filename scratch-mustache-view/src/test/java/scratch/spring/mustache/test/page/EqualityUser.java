package scratch.spring.mustache.test.page;

import static java.lang.String.format;

public abstract class EqualityUser {

    public abstract String getEmail();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getPhoneNumber();

    public abstract EqualityAddress getAddress();

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (!(object instanceof EqualityUser)) {
            return false;
        }

        final EqualityUser that = (EqualityUser) object;

        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) {
            return false;
        }
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null) {
            return false;
        }
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null) {
            return false;
        }
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(that.getPhoneNumber()) : that.getPhoneNumber() != null) {
            return false;
        }
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = getEmail() != null ? getEmail().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {

        return format("User {" +
                "email = '%s',\n" +
                "firstName = '%s',\n" +
                "lastName = '%s',\n" +
                "phoneNumber = '%s',\n" +
                "address = %s\n" +
                "}", getEmail(), getFirstName(), getLastName(), getPhoneNumber(), getAddress());
    }
}
