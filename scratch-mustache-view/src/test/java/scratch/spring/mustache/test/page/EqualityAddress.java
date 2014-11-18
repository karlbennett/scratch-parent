package scratch.spring.mustache.test.page;

import static java.lang.String.format;

public abstract class EqualityAddress {

    public abstract Integer getNumber();

    public abstract String getStreet();

    public abstract String getSuburb();

    public abstract String getCity();

    public abstract String getPostcode();

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (!(object instanceof EqualityAddress)) {
            return false;
        }

        final EqualityAddress that = (EqualityAddress) object;

        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) {
            return false;
        }
        if (getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null) {
            return false;
        }
        if (getSuburb() != null ? !getSuburb().equals(that.getSuburb()) : that.getSuburb() != null) {
            return false;
        }
        if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) {
            return false;
        }
        if (getPostcode() != null ? !getPostcode().equals(that.getPostcode()) : that.getPostcode() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = getNumber() != null ? getNumber().hashCode() : 0;
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + (getSuburb() != null ? getSuburb().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getPostcode() != null ? getPostcode().hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {

        return format("Address {" +
                "number = %d,\n" +
                "street = '%s',\n" +
                "suburb = '%s',\n" +
                "city = '%s',\n" +
                "postcode = '%s'\n" +
                "}", getNumber(), getStreet(), getSuburb(), getCity(), getPostcode());
    }
}
