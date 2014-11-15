package scratch.spring.mustache.config;

import com.github.mustachejava.DefaultMustacheFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import scratch.spring.mustache.view.MustacheViewResolver;

/**
 * This class configures the Mustache view.
 *
 * @author Karl Bennett
 */
@Configuration
public class ScratchMustacheConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public ViewResolver viewResolver(ResourceLoader resourceLoader) {

        return new MustacheViewResolver(new DefaultMustacheFactory(), resourceLoader);
    }
}
