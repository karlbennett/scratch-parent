package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import javax.validation.Valid;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/view/users/create")
public class CreateUserController {

    @Autowired
    private Users users;

    @RequestMapping(method = GET, produces = TEXT_HTML_VALUE)
    public Callable<String> createUser() {

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "user-create.mustache";
            }
        };
    }

    @RequestMapping(method = POST, produces = TEXT_HTML_VALUE)
    public Callable<String> createUser(@Valid final User user) {

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                final Id id = users.create(user);

                return "redirect:/view/users/" + id.getId();
            }
        };
    }
}
