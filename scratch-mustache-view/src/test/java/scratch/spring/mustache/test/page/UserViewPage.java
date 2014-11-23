package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.user.User;

@Component
public class UserViewPage extends UserPage implements UserVisitPage {

    private final Finders finders;
    private final UserVisitPage userVisitPage;

    @Autowired
    protected UserViewPage(WebDriver driver, BaseUrl baseUrl) {
        super(driver);
        this.finders = new Finders(driver);
        userVisitPage = new VisitableUserPage(driver, baseUrl, "/view/users/");
    }

    @Override
    public void visit(User user) {
        userVisitPage.visit(user);
    }

    @Override
    protected String findValue(String id) {
        return Finders.findTextByClassName(finders.findById(id), "value");
    }

    @Override
    public AddressViewElement getAddress() {
        return new AddressViewElement(finders.findById("address"));
    }

    public void clickEdit() {
        finders.findByClassName("edit").click();
    }
}
