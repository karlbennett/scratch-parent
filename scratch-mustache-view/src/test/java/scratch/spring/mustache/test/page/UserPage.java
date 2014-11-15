package scratch.spring.mustache.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@Component
public class UserPage {

    @Autowired
    private WebDriver driver;

    @Autowired
    private BaseUrl baseUrl;

    public void visit(Long id) {

        driver.get(baseUrl + "/view/users/" + id);

        assertThat("The title of the current page should be correct.", driver.getTitle(), startsWith("User"));
    }

    public Long getId() {

        return Long.valueOf(driver.findElement(By.id("id")).getText());
    }

    public String getEmail() {

        return findTextById("email");
    }

    public String getFirstName() {

        return findTextById("firstName");
    }

    public String getLastName() {

        return findTextById("firstName");
    }

    public String getPhoneNumber() {

        return findTextById("phoneNumber");
    }

    public AddressElement getAddress() {

        return new AddressElement(findById("address"));
    }

    private WebElement findById(String id) {
        return Pages.findById(driver, id);
    }

    private String findTextById(String id) {
        return Pages.findTextById(driver, id);
    }
}
