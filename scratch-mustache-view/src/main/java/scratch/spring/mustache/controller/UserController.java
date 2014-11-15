package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.user.Users;

import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * THis controller contains the mappings for all the user CRUD pages.
 *
 * @author Karl Bennett
 */
@Controller
@RequestMapping("/view/users")
public class UserController {

    @Autowired
    private Users users;

    @RequestMapping(method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> users() {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("classpath:users.mustache", "users", users.retrieve());
            }
        };
    }
}
