package scratch.spring.mustache.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HomePage extends SeleniumPage {

    private final WebDriver driver;
    private final Finders finders;
    private final BaseUrl baseUrl;

    @Autowired
    public HomePage(WebDriver driver, BaseUrl baseUrl) {
        super(driver);
        this.driver = driver;
        this.finders = new Finders(driver);
        this.baseUrl = baseUrl;
    }

    public void visit() {

        driver.get(baseUrl + "/view/users");
    }

    public List<UserRowElement> users() {

        final List<UserRowElement> users = new ArrayList<>();

        for (WebElement element : driver.findElements(By.className("user"))) {

            users.add(new UserRowElement(element));
        }

        return users;
    }

    public void clickCreate() {
        finders.clickById("create");
    }
}
