package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebElement;

public class AddressElement extends EqualityAddress {

    private final WebElement element;

    public AddressElement(WebElement element) {
        this.element = element;
    }

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

    private String findValue(String id) {
        return Pages.findTextByClassName(findById(id), "value");
    }

    private WebElement findById(String id) {
        return Pages.findById(element, id);
    }
}
