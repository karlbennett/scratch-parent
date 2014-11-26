package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.user.User;
import scratch.user.Users;

import javax.validation.Valid;
import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/view/users/edit")
public class UpdateUserController {

    @Autowired
    private Users users;

    @RequestMapping(value = "/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> editUser(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("user-edit.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/{id}", method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
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
}
