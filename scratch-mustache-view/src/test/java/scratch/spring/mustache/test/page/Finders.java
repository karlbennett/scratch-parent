package scratch.spring.mustache.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class Finders {

    private final SearchContext context;

    public Finders(SearchContext context) {
        this.context = context;
    }

    public WebElement findById(String id) {
        return findById(context, id);
    }

    public String findTextByClassName(String className) {
        return findByClassName(className).getText();
    }

    public WebElement findByClassName(String className) {
        return findByClassName(context, className);
    }

    public String findValue(String id) {
        return findValue(context, id);
    }

    public static WebElement findById(SearchContext context, String id) {
        return context.findElement(By.id(id));
    }

    public static String findTextByClassName(SearchContext context, String className) {
        return findByClassName(context, className).getText();
    }

    public static WebElement findByClassName(SearchContext context, String className) {
        return context.findElement(By.className(className));
    }

    public static String findValue(SearchContext context, String id) {
        return findById(context, id).getAttribute("value");
    }

    public static void setValue(WebElement element, String email) {
        element.clear();
        element.sendKeys(email);
    }
}
