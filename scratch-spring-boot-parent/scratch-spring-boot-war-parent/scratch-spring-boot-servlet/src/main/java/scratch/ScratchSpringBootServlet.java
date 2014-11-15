package scratch;

import org.springframework.boot.SpringApplication;
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
@Import(ScratchSpringBootApplication.class)
public class ScratchSpringBootServlet extends SpringBootServletInitializer {

    /**
     * This override will start up Spring Boot if this class is instantiated inside of a JEE
     * {@link javax.servlet.Servlet}.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ScratchSpringBootServlet.class);
    }

    /**
     * The standard Spring Boot main method. It is used when the packaged war is executed with {@code java -jar}
     */
    public static void main(String[] args) {

        SpringApplication.run(ScratchSpringBootApplication.class, args);
    }
}
