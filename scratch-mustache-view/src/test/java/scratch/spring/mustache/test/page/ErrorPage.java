package scratch.spring.mustache.test.page;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorPage extends SeleniumPage implements VisitPage {

    private final VisitablePage visitablePage;
    private final Finders finders;

    @Autowired
    public ErrorPage(WebDriver driver, BaseUrl baseUrl) {
        super(driver);
        this.visitablePage = new VisitablePage(driver, baseUrl, "/view/users");
        finders = new Finders(driver);
    }

    @Override
    public void visit() {

        visitablePage.visit();
    }

    public String getErrorMessage() {
        return finders.findTextByClassName("error");
    }
}
