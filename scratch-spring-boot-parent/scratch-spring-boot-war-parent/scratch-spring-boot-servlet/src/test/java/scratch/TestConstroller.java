package scratch;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is just here to allow successful request to be made to the Spring Boot web application.
 *
 * @author Karl Bennett
 */
@RestController
public class TestConstroller {

    public static final String TEST_MESSAGE = "This is the response from the test controller.";

    @RequestMapping("/test")
    public String test() {

        return TEST_MESSAGE;
    }
}
