package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebElement;

public class AddressElement {

    private final WebElement element;

    public AddressElement(WebElement element) {
        this.element = element;
    }

    public Integer getNumber() {
        return Integer.valueOf(findTextById("number"));
    }

    public String getStreet() {
        return findTextById("street");
    }

    public String getSuburb() {
        return findTextById("suburb");
    }

    public String getCity() {
        return findTextById("city");
    }

    public String getPostcode() {
        return findTextById("postcode");
    }

    private String findTextById(String id) {
        return Pages.findTextById(element, id);
    }
}
