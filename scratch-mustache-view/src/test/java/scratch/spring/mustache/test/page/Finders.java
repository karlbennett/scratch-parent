package scratch.spring.mustache.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

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

    public void clickByClassName(String className) {
        clickByClassName(context, className);
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

    public static void clickByClassName(SearchContext context, String className) {
        findByClassName(context, className).click();
    }

    public static WebElement findByClassName(SearchContext context, String className) {
        return context.findElement(By.className(className));
    }

    public static String findValue(SearchContext context, String id) {
        return findById(context, id).getAttribute("value");
    }

    public static void setValue(WebElement element, String value) {
        element.clear();

        if (null == value) {
            return;
        }

        element.sendKeys(value);
    }

    public static List<String> findTextsByClassName(SearchContext context, String className) {
        final List<String> texts = new ArrayList<>();

        for (WebElement element : context.findElements(By.className(className))) {
            texts.add(element.getText());
        }

        return texts;
    }
}
