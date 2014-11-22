package scratch.spring.mustache.test.page;

public class NullAddress extends EqualityAddress {

    @Override
    public String getCity() {
        return "";
    }

    @Override
    public Integer getNumber() {
        return 0;
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
