package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@Component
public class UserPage extends EqualityUser {

    @Autowired
    private WebDriver driver;

    @Autowired
    private BaseUrl baseUrl;

    public void visit(Long id, String firstName, String lastName) {

        driver.get(baseUrl + "/view/users/" + id);

        validate(firstName, lastName);
    }

    public void validate(String firstName, String lastName) {

        assertThat("The title of the current page should be correct.", driver.getTitle(), startsWith("User"));
        assertThat("The title should contain the first name.", driver.getTitle(), containsString(firstName));
        assertThat("The title should contain the last name.", driver.getTitle(), containsString(lastName));
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

    @Override
    public AddressElement getAddress() {

        return new AddressElement(findById("address"));
    }

    private String findValue(String id) {
        return Pages.findTextByClassName(findById(id), "value");
    }

    private WebElement findById(String id) {
        return Pages.findById(driver, id);
    }
}
