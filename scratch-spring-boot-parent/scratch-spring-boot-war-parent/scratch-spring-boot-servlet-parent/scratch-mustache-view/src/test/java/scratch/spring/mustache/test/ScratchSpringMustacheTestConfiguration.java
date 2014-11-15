package scratch.spring.mustache.test;

import com.opera.core.systems.OperaDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.user.Users;

import static org.mockito.Mockito.mock;

@Configuration
public class ScratchSpringMustacheTestConfiguration {

    @Bean
    public static Users users() {
        return mock(Users.class);
    }

    @Bean(destroyMethod = "quit")
    public static WebDriver driver(@Value("${web.driver:}") String driver) {

        if ("firefox".equals(driver)) {
            return new FirefoxDriver();
        }

        if ("chrome".equals(driver)) {
            return new ChromeDriver();
        }

        if ("opera".equals(driver)) {
            return new OperaDriver();
        }

        if ("ie".equals(driver)) {
            return new InternetExplorerDriver();
        }

        if ("safari".equals(driver)) {
            return new SafariDriver();
        }

        if ("phantomjs".equals(driver)) {
            return new PhantomJSDriver();
        }

        return new HtmlUnitDriver();
    }
}
