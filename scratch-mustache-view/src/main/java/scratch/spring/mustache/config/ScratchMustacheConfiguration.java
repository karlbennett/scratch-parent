package scratch.spring.mustache.config;

import org.msgpack.MessagePack;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.mustache.MustacheViewResolver;
import org.springframework.web.servlet.view.mustache.java.MustacheJTemplateFactory;
import scratch.user.Address;
import scratch.user.User;

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
        factory.setPrefix("classpath:");

        final MustacheViewResolver resolver = new MustacheViewResolver();
        resolver.setTemplateFactory(factory);
        resolver.setCache(true);

        return resolver;
    }

    @Bean
    public MessagePack messagePack() {

        final MessagePack msgePack = new MessagePack();
        msgePack.register(Address.class);
        msgePack.register(User.class);

        return msgePack;
    }
}
