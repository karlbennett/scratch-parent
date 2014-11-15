package scratch.spring.mustache.test.page;

public abstract class AbstractUserRow {

    public abstract String getEmail();

    public abstract String getFirstName();

    public abstract String getLastName();

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (!(object instanceof AbstractUserRow)) {
            return false;
        }

        final AbstractUserRow that = (AbstractUserRow) object;

        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) {
            return false;
        }
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null) {
            return false;
        }
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = getEmail() != null ? getEmail().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);

        return result;
    }
}
