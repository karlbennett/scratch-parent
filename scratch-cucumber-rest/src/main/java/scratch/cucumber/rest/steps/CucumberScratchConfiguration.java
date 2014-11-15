package scratch.cucumber.rest.steps;

import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.WebTarget;

/**
 * Test configuration that does not have the @EnableWebMvc annotation because the tests are not running with a
 * JEE container.
 *
 * @author Karl Bennett
 */
@Configuration
@ComponentScan(basePackages = "scratch.cucumber.rest.steps")
public class CucumberScratchConfiguration {

    /**
     * Create a new property configurer that will enable the injection of any properties within the configured
     * properties files with the {@link Value} annotation.
     */
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {

        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("cucumber.properties"));

        return propertyPlaceholderConfigurer;
    }

    /**
     * AN object that helps with setting up test data.
     */
    @Bean
    public static PropertyObject user() {

        return new PropertyObject();
    }

    /**
     * A history of all the responses that has been returned during a scenario.
     */
    @Bean
    public Requests requests() {

        return new Requests();
    }

    /**
     * A history of all the responses that has been returned during a scenario.
     */
    @Bean
    public Responses responses() {

        return new Responses();
    }

    /**
     * @param url this parameters value is set from a value with a properties file.
     */
    @Bean
    public WebTarget client(@Value("${rest.baseUrl}") String url) {

        final Client client = ClientBuilder.newBuilder()
                .register(JacksonFeature.class)
                .register(new ResponsesFilter(requests(), responses()))
                .build();

        return client.target(url);
    }

    static class ResponsesFilter implements ClientResponseFilter {

        private final Responses responses;
        private final Requests requests;

        ResponsesFilter(Requests requests, Responses responses) {
            this.requests = requests;
            this.responses = responses;
        }

        @Override
        public void filter(ClientRequestContext requestContext,
                           ClientResponseContext responseContext) {

            requests.add((ClientRequest) requestContext);

            final ClientResponse response = (ClientResponse) responseContext;
            response.bufferEntity();

            responses.add(response);
        }
    }
}
