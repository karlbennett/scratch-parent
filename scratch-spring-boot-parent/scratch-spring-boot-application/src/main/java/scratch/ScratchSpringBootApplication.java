package scratch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This is the bootstrap that starts up the whole Spring Boot framework.
 *
 * @author Karl Bennett
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ScratchSpringBootApplication {

    /**
     * The standard Spring Boot main method. It is used when the packaged jar is executed with {@code java -jar}
     */
    public static void main(String[] args) {

        SpringApplication.run(ScratchSpringBootApplication.class, args);
    }
}
