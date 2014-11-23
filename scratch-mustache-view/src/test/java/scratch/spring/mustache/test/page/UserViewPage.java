package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserViewPage extends UserViewablePage {

    private final Finders finders;

    @Autowired
    protected UserViewPage(WebDriver driver, BaseUrl baseUrl) {
        super(driver, new VisitableUserPage(driver, baseUrl, "/view/users/"));
        finders = new Finders(driver);
    }

    public void clickEdit() {
        finders.findByClassName("edit").click();
    }

    public void clickDelete() {
        finders.findByClassName("delete").click();
    }
}
