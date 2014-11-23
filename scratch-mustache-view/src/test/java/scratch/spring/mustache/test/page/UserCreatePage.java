package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreatePage extends UserMutablePage implements VisitPage {

    private final VisitPage visitPage;

    @Autowired
    protected UserCreatePage(WebDriver driver, BaseUrl baseUrl) {
        super(driver);
        visitPage = new VisitablePage(driver, baseUrl, "/view/users/create");
    }

    @Override
    public void visit() {
        visitPage.visit();
    }
}
