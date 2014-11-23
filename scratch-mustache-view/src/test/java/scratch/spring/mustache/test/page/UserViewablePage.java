package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import scratch.user.User;

import static scratch.spring.mustache.test.page.Finders.findTextByClassName;

public class UserViewablePage extends UserPage implements UserVisitPage {

    private final Finders finders;
    private final UserVisitPage userVisitPage;

    public UserViewablePage(WebDriver driver, UserVisitPage userVisitPage) {
        super(driver);
        this.userVisitPage = userVisitPage;
        this.finders = new Finders(driver);
    }

    @Override
    public void visit(User user) {
        userVisitPage.visit(user);
    }

    @Override
    protected String findValue(String id) {
        return findTextByClassName(finders.findById(id), "value");
    }

    @Override
    public AddressViewElement getAddress() {
        return new AddressViewElement(finders.findById("address"));
    }
}
