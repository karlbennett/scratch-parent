package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserViewPage extends UserPage {

    private final Finders finders;

    @Autowired
    protected UserViewPage(WebDriver driver, BaseUrl baseUrl) {
        super(driver, baseUrl, "/view/users/", "User: ");
        this.finders = new Finders(driver);
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
