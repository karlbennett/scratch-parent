package scratch.spring.persistence.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.spring.persistence.test.UserSteps;
import scratch.spring.persistence.test.data.DBUnitUserRepository;

import javax.sql.DataSource;

@Configuration
public class TestScratchConfiguration {

    @Bean
    @Autowired
    public UserSteps userSteps(DataSource dataSource) {

        final DBUnitUserRepository dbUnitUserRepository = new DBUnitUserRepository(dataSource);

        return new UserSteps(dbUnitUserRepository);
    }
}
