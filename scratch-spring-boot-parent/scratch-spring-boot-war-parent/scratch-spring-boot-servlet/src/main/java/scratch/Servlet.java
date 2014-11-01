package scratch;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This is the bootstrap that starts up the whole Spring Boot framework.
 *
 * @author Karl Bennett
 */
@Configuration
@Import(Application.class)
public class Servlet extends SpringBootServletInitializer {

    /**
     * This override will start up Spring Boot if this class is instantiated inside of a JEE
     * {@link javax.servlet.Servlet}.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Servlet.class);
    }
}
