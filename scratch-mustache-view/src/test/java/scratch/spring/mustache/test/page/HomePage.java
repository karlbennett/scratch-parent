package scratch.spring.mustache.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HomePage extends SeleniumPage implements VisitPage {

    private final WebDriver driver;
    private final VisitablePage visitablePage;
    private final Finders finders;

    @Autowired
    public HomePage(WebDriver driver, BaseUrl baseUrl) {
        super(driver);
        this.driver = driver;
        this.visitablePage = new VisitablePage(driver, baseUrl, "/view/users");
        this.finders = new Finders(driver);
    }

    @Override
    public void visit() {

        visitablePage.visit();
    }

    public List<UserRowElement> users() {

        final List<UserRowElement> users = new ArrayList<>();

        for (WebElement element : driver.findElements(By.className("user"))) {

            users.add(new UserRowElement(element));
        }

        return users;
    }

    public void clickCreate() {
        finders.clickByClassName("create");
    }
}
