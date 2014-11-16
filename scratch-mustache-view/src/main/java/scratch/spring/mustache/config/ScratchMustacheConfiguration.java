package scratch.spring.mustache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.mustache.MustacheViewResolver;
import org.springframework.web.servlet.view.mustache.java.MustacheJTemplateFactory;

/**
 * This class configures the Mustache view.
 *
 * @author Karl Bennett
 */
@Configuration
public class ScratchMustacheConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public ViewResolver viewResolver(ResourceLoader resourceLoader) {

        final MustacheJTemplateFactory factory = new MustacheJTemplateFactory();
        factory.setResourceLoader(resourceLoader);

        final MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setTemplateFactory(factory);
        resolver.setCache(true);

        return resolver;
    }
}
