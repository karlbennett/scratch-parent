package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebElement;

public class SeleniumUserRow extends EqualityUserRow {

    private final WebElement element;

    public SeleniumUserRow(WebElement element) {

        if (null == element) {
            throw new IllegalArgumentException("A SeleniumUserRow must contain an element.");
        }

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

    public void clickView() {
        findByClassName("view").click();
    }

    private String findTextByClassName(String className) {
        return Pages.findTextByClassName(element, className);
    }

    private WebElement findByClassName(String className) {
        return Pages.findByClassName(element, className);
    }
}
