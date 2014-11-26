package scratch.spring.mustache.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import scratch.user.Users;

import java.util.concurrent.Callable;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/view/users/delete")
public class DeleteUserController {

    @Autowired
    private Users users;

    @RequestMapping(value = "/{id}", method = GET, produces = TEXT_HTML_VALUE)
    public Callable<ModelAndView> deleteUserPage(@PathVariable final Long id) {

        return new Callable<ModelAndView>() {
            @Override
            public ModelAndView call() throws Exception {
                return new ModelAndView("user-delete.mustache", "user", users.retrieve(id));
            }
        };
    }

    @RequestMapping(value = "/{id}", method = POST)
    public Callable<String> deleteUser(@PathVariable final Long id) {

        return new Callable<String>() {
            @Override
            public String call() throws Exception {

                users.delete(id);

                return "redirect:/view/users";
            }
        };
    }
}
