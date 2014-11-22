package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebElement;

public class AddressEditElement extends AddressElement {

    private final Finders finders;

    public AddressEditElement(WebElement element) {

        if (null == element) {
            throw new IllegalArgumentException("A AddressElement must contain an element.");
        }

        this.finders = new Finders(element);
    }

    public void setValues(EqualityAddress address) {
        setNumber(address.getNumber());
        setStreet(address.getStreet());
        setSuburb(address.getSuburb());
        setCity(address.getCity());
        setPostcode(address.getPostcode());
    }

    public void setNumber(Integer number) {
        setValue("number", null == number ? "" : number.toString());
    }

    public void setStreet(String street) {
        setValue("street", street);
    }

    public void setSuburb(String suburb) {
        setValue("suburb", suburb);
    }

    public void setCity(String city) {
        setValue("city", city);
    }

    public void setPostcode(String postcode) {
        setValue("postcode", postcode);
    }

    @Override
    protected String findValue(String id) {
        return finders.findValue(id);
    }

    private void setValue(String id, String value) {
        Finders.setValue(finders.findById(id), value);
    }
}
