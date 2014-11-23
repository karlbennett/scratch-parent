package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;

public class VisitablePage implements VisitPage {

    private final WebDriver driver;
    private final BaseUrl baseUrl;
    private final String path;

    public VisitablePage(WebDriver driver, BaseUrl baseUrl, String path) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.path = path;
    }

    @Override
    public void visit() {

        driver.get(baseUrl + path);
    }
}