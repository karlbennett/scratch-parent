package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebElement;

import static scratch.spring.mustache.test.page.Finders.findTextByClassName;

public class AddressViewElement extends AddressElement {

    private final Finders finders;

    public AddressViewElement(WebElement element) {

        if (null == element) {
            throw new IllegalArgumentException("A AddressElement must contain an element.");
        }

        this.finders = new Finders(element);
    }

    @Override
    protected String findValue(String id) {
        return findTextByClassName(finders.findById(id), "value");
    }
}
