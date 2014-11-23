package scratch.spring.mustache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * This controller contains the mappings for all the view error pages..
 *
 * @author Karl Bennett
 */
@Controller
@RequestMapping("/view")
public class ErrorController {

    @RequestMapping(value = "/error", method = GET, produces = TEXT_HTML_VALUE)
    public ModelAndView error(HttpServletResponse response) {

        response.setStatus(BAD_REQUEST.value());

        return new ModelAndView("error.mustache");
    }
}
