package scratch.spring.mustache.test.page;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

/**
 * This class contains the base url for the view layer web app. It is used by all the page objects to build the URL's
 * for their related pages. Because the integration tests use random ports when starting up the web app the port for the
 * base url must be set with {@link #setPort(int)} in the startup section of the test.
 *
 * @author Karl Bennett
 */
@Component
public class BaseUrl {

    @Value("${base.url:http://localhost:%d/mustache}")
    private String baseUrl;

    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return format(baseUrl, port);
    }
}
