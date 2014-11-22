package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreatePage extends UserMutablePage {

    @Autowired
    protected UserCreatePage(WebDriver driver, BaseUrl baseUrl) {
        super(driver, baseUrl, "/view/users/create");
    }
}
