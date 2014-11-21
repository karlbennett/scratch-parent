package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import scratch.user.User;

public abstract class UserPage extends EqualityUser implements Page {

    private final WebDriver driver;
    private final BaseUrl baseUrl;
    private final String path;
    private final SeleniumPage page;

    protected UserPage(WebDriver driver, BaseUrl baseUrl, String path) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.path = path;
        this.page = new SeleniumPage(driver);
    }

    public void visit(User user) {

        driver.get(baseUrl + path + user.getId());
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
