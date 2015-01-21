package scratch.spring.stand.alone.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.user.Users;

import static org.mockito.Mockito.mock;

@Configuration
public class ScratchSpringSchedulingTestConfiguration {

    @Bean
    public static Users users() {
        return mock(Users.class);
    }
}
