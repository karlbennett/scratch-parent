package scratch.spring.mustache.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Component
public class HomePage {

    @Autowired
    private WebDriver driver;

    @Autowired
    private BaseUrl baseUrl;

    public void visit() {

        driver.get(baseUrl + "/view/users");

        assertEquals("The title of the current page should be correct.", "All Users", driver.getTitle());
    }

    public List<SeleniumUserRow> users() {

        final List<SeleniumUserRow> users = new ArrayList<>();

        for (WebElement element : driver.findElements(By.className("user"))) {

            users.add(new SeleniumUserRow(element));
        }

        return users;
    }
}
