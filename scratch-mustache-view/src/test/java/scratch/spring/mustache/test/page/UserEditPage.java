package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.user.User;

@Component
public class UserEditPage extends UserMutablePage implements UserVisitPage {

    private final UserVisitPage userVisitPage;

    @Autowired
    protected UserEditPage(WebDriver driver, BaseUrl baseUrl) {
        super(driver);
        userVisitPage = new VisitableUserPage(driver, baseUrl, "/view/users/edit/");
    }

    @Override
    public void visit(User user) {
        userVisitPage.visit(user);
    }
}
