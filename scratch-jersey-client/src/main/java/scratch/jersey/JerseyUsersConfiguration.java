package scratch.jersey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * This configuration just allows the Jersey client base URL to be configurable.
 *
 * @author Karl Bennett
 */
@Configuration
public class JerseyUsersConfiguration {

    @Bean
    public static WebTarget target(@Value("${restUrl:http://localhost:8080/rest/}") String restUrl) {
        return ClientBuilder.newClient().target(restUrl);
    }
}
