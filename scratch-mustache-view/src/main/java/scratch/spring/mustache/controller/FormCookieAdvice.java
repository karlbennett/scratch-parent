package scratch.spring.mustache.controller;

import org.msgpack.MessagePack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import scratch.user.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@ControllerAdvice
public class FormCookieAdvice {

    @Autowired
    private MessagePack messagePack;

    @ModelAttribute
    public void user(@RequestBody(required = false) MultiValueMap<String, Object> form, HttpServletResponse response)
            throws IOException {

        if (null == form) {
            return;
        }

        final byte[] userMsg = messagePack.write(new MultiValueMapUser(form));

        response.addCookie(new Cookie("form", DatatypeConverter.printBase64Binary(userMsg)));
    }

    @ModelAttribute
    public void user(@CookieValue(value = "form", required = false) String userBase64Msg, Model model)
            throws IOException {

        if (null == userBase64Msg) {
            return;
        }

        final byte[] userMsg = DatatypeConverter.parseBase64Binary(userBase64Msg);

        final User user = messagePack.read(userMsg, User.class);

        model.addAttribute("user", user);
    }
}
