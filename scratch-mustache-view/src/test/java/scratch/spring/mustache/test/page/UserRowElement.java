package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebElement;

public class UserRowElement extends EqualityUserRow {

    private final Finders finders;

    public UserRowElement(WebElement element) {

        if (null == element) {
            throw new IllegalArgumentException("A UserRowElement must contain an element.");
        }

        this.finders = new Finders(element);
    }

    @Override
    public String getEmail() {
        return finders.findTextByClassName("email");
    }

    @Override
    public String getFirstName() {
        return finders.findTextByClassName("firstName");
    }

    @Override
    public String getLastName() {
        return finders.findTextByClassName("lastName");
    }

    public void clickView() {
        finders.clickByClassName("view");
    }

    public void clickEdit() {
        finders.clickByClassName("edit");
    }

    public void clickDelete() {
        finders.clickByClassName("delete");
    }
}
