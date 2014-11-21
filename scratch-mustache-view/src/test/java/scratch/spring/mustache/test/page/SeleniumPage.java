package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;

public class SeleniumPage implements Page {

    private final WebDriver driver;

    public SeleniumPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }
}
