package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebElement;

public class SeleniumUserRow extends AbstractUserRow {

    private final WebElement element;

    public SeleniumUserRow(WebElement element) {
        this.element = element;
    }

    @Override
    public String getEmail() {
        return findTextByClassName("email");
    }

    @Override
    public String getFirstName() {
        return findTextByClassName("firstName");
    }

    @Override
    public String getLastName() {
        return findTextByClassName("lastName");
    }

    public void clickEmail() {

        if (null == element) {
            throw new IllegalStateException("This UserRow does not contain an email element that can be clicked.");
        }

        element.clear();
    }

    private String findTextByClassName(String id) {
        return Pages.findTextByClassName(element, id);
    }
}
