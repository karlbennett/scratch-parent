package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.user.User;
import scratch.user.Users;

import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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

    @RequestMapping(value = "/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> user(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("classpath:user.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/edit/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> editUser(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("classpath:user-edit.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/edit/{id}", method = POST,
            consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = TEXT_HTML_VALUE)
    public Callable<String> editUser(@PathVariable final Long id, final User user) {

        user.setId(id);

        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                users.update(user);

                return "redirect:/view/users/" + id;
            }
        };
    }
}
