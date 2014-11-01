package scratch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ScratchSpringBootServlet.class)
@WebAppConfiguration("classpath:")
@IntegrationTest({"server.port=0", "management.port=0"})
public class ScratchSpringBootServletTest {

    public static final String TEST_MESSAGE = "This is the response from the test controller.";

    @Value("${local.server.port}")
    private int port;

    @Test
    public void I_can_make_a_request_to_the_started_server() {

        assertEquals("the request gets a valid response.", TEST_MESSAGE,
                new TestRestTemplate().getForObject(String.format("http://localhost:%d/test", port), String.class));
    }

    @RestController
    public static class TestController {

        @RequestMapping("/test")
        public String test() {

            return TEST_MESSAGE;
        }
    }
}
