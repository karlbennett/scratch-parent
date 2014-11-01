package scratch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static scratch.TestConstroller.TEST_MESSAGE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Servlet.class)
@WebAppConfiguration("classpath:")
@IntegrationTest({"server.port=0", "management.port=0"})
public class ServletTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void I_can_make_a_request_to_the_started_server() {

        assertEquals("the request gets a valid response.", TEST_MESSAGE,
                new TestRestTemplate().getForObject(String.format("http://localhost:%d/test", port), String.class));
    }
}
