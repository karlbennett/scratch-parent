package scratch.spring.mustache.test.page;

public abstract class AddressElement extends EqualityAddress {

    @Override
    public Integer getNumber() {
        return Integer.valueOf(findValue("number"));
    }

    @Override
    public String getStreet() {
        return findValue("street");
    }

    @Override
    public String getSuburb() {
        return findValue("suburb");
    }

    @Override
    public String getCity() {
        return findValue("city");
    }

    @Override
    public String getPostcode() {
        return findValue("postcode");
    }

    protected abstract String findValue(String id);
}
