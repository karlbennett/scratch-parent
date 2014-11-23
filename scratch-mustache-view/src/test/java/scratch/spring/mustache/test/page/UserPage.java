package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;

public abstract class UserPage extends EqualityUser implements Page {

    private final SeleniumPage page;

    protected UserPage(WebDriver driver) {
        this.page = new SeleniumPage(driver);
    }

    @Override
    public String getTitle() {
        return page.getTitle();
    }

    @Override
    public String getEmail() {

        return findValue("email");
    }

    @Override
    public String getFirstName() {

        return findValue("firstName");
    }

    @Override
    public String getLastName() {

        return findValue("lastName");
    }

    @Override
    public String getPhoneNumber() {

        return findValue("phoneNumber");
    }

    protected abstract String findValue(String id);
}
