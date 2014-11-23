package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import scratch.user.User;

public class VisitableUserPage implements UserVisitPage {

    private final WebDriver driver;
    private final BaseUrl baseUrl;
    private final String path;

    public VisitableUserPage(WebDriver driver, BaseUrl baseUrl, String path) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.path = path;
    }

    @Override
    public void visit(User user) {

        driver.get(baseUrl + path + user.getId());
    }
}