package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import scratch.user.User;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public abstract class UserPage extends EqualityUser {

    private final WebDriver driver;
    private final BaseUrl baseUrl;
    private final String path;
    private final String titlePrefix;

    protected UserPage(WebDriver driver, BaseUrl baseUrl, String path, String titlePrefix) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.path = path;
        this.titlePrefix = titlePrefix;
    }

    public void visit(User user) {

        driver.get(baseUrl + path + user.getId());

        assertPage(user);
    }

    public void assertPage(User user) {

        assertPage(new DataUser(user));
    }

    public void assertPage(EqualityUser user) {

        assertThat("The title of the current page should be correct.", driver.getTitle(), startsWith(titlePrefix));
        assertThat("The title should contain the first name.", driver.getTitle(), containsString(user.getFirstName()));
        assertThat("The title should contain the last name.", driver.getTitle(), containsString(user.getLastName()));
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
