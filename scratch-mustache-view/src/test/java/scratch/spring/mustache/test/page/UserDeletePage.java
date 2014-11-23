package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDeletePage extends UserViewablePage {

    private final Finders finders;

    @Autowired
    protected UserDeletePage(WebDriver driver, BaseUrl baseUrl) {
        super(driver, new VisitableUserPage(driver, baseUrl, "/view/users/delete/"));
        finders = new Finders(driver);
    }

    public String getWarning() {
        return finders.findTextByClassName("warning");
    }

    public void clickCancel() {
        finders.clickByClassName("cancel");
    }

    public void clickDelete() {
        finders.clickByClassName("delete");
    }
}
