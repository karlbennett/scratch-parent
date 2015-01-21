package scratch.spring.stand.alone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import scratch.user.User;
import scratch.user.Users;

@Configuration
@EnableScheduling
public class ScratchSpringBootSchedulingApplication {

    @Autowired
    private Users users;

    @Scheduled(cron = "${scratch.schedule.cron:0 0 0 * * *}" /* Default to running at midnight. */)
    public void scheduled() {

        for (User user : users.retrieve()) {
            if (user.getFirstName().startsWith("[TEST]")) {
                users.delete(user.getId());
            }
        }
    }
}
