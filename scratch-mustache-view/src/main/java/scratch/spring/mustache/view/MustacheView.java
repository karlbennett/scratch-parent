package scratch.spring.mustache.view;

import com.github.mustachejava.Mustache;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * A Spring {@link View} that supports the Mustache templating language.
 *
 * @author Karl Bennett
 */
public class MustacheView extends AbstractTemplateView {

    private Mustache mustache;

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {

        final PrintWriter writer = response.getWriter();

        mustache.execute(writer, model);

        writer.flush();
    }

    public void setMustache(Mustache mustache) {

        this.mustache = mustache;
    }
}
