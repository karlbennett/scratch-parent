package scratch.spring.rest.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.user.Users;

import static org.mockito.Mockito.mock;

@Configuration
public class TestScratchConfiguration {

    @Bean
    public Users users() {
        return mock(Users.class);
    }
}
