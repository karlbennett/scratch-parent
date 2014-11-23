package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import scratch.user.Id;
import scratch.user.User;
import scratch.user.Users;

import javax.validation.Valid;
import java.util.concurrent.Callable;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * This controller contains the mappings for all the user CRUD pages.
 *
 * @author Karl Bennett
 */
@Controller
@RequestMapping("/view/users")
public class UserController {

    @Autowired
    private Users users;

    @RequestMapping(value = "/create", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<String> createUser() {

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "user-create.mustache";
            }
        };
    }

    @RequestMapping(value = "/create", method = POST, produces = TEXT_HTML_VALUE)
    public Callable<String> createUser(@Valid final User user) {

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                final Id id = users.create(user);

                return "redirect:/view/users/" + id.getId();
            }
        };
    }

    @RequestMapping(value = "/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> user(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("user.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> users() {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("users.mustache", "users", users.retrieve());
            }
        };
    }

    @RequestMapping(value = "/edit/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> editUser(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("user-edit.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/edit/{id}", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public Callable<String> editUser(@PathVariable final Long id, @Valid final User user) {

        user.setId(id);

        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                users.update(user);

                return "redirect:/view/users/" + id;
            }
        };
    }

    @RequestMapping(value = "/delete/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> deleteUserPage(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("user-delete.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/delete/{id}", method = POST)
    public Callable<String> deleteUser(@PathVariable final Long id) {

        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                users.delete(id);

                return "redirect:/view/users";
            }
        };
    }

    @ExceptionHandler(IllegalStateException.class)
    public String error() {

        return "redirect:/view/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(NOT_FOUND)
    public String notFound() {

        return "not-found.mustache";
    }
}
