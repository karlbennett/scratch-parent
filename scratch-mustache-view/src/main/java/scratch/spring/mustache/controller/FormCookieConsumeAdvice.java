package scratch.spring.mustache.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(
        assignableTypes = {
                RetrieveUserController.class,
                UpdateUserController.class,
                DeleteUserController.class
        }
)
public class FormCookieConsumeAdvice {

    @ModelAttribute
    public void consumeCookie(HttpServletResponse response) {

        final Cookie cookie = new Cookie("form", "");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
