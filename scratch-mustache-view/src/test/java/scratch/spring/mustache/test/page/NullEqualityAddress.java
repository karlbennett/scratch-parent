package scratch.spring.mustache.test.page;

public class NullEqualityAddress extends EqualityAddress {

    @Override
    public String getCity() {
        return "";
    }

    @Override
    public Integer getNumber() {
        return null;
    }

    @Override
    public String getPostcode() {
        return "";
    }

    @Override
    public String getStreet() {
        return "";
    }

    @Override
    public String getSuburb() {
        return "";
    }
}
