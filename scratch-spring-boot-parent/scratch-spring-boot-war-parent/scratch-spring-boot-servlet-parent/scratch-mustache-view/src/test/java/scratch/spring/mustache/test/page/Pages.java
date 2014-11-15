package scratch.spring.mustache.test.page;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class Pages {

    public static WebElement findById(SearchContext context, String id) {
        return context.findElement(By.id(id));
    }

    public static String findTextById(SearchContext context, String id) {
        return findById(context, id).getText();
    }

    public static String findTextByClassName(SearchContext context, String className) {
        return findByClassName(context, className).getText();
    }

    public static WebElement findByClassName(SearchContext context, String className) {
        return context.findElement(By.className(className));
    }
}
