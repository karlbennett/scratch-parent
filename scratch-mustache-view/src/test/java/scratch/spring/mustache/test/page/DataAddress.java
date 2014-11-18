package scratch.spring.mustache.test.page;

import scratch.user.Address;

public class DataAddress extends EqualityAddress {

    private final Integer number;
    private final String street;
    private final String suburb;
    private final String city;
    private final String postcode;

    public DataAddress(Address address) {
        this(address.getNumber(), address.getStreet(), address.getSuburb(), address.getCity(), address.getPostcode());
    }

    public DataAddress(Integer number, String street, String suburb, String city, String postcode) {
        this.number = number;
        this.street = street;
        this.suburb = suburb;
        this.postcode = postcode;
        this.city = city;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public String getPostcode() {
        return postcode;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getSuburb() {
        return suburb;
    }
}
