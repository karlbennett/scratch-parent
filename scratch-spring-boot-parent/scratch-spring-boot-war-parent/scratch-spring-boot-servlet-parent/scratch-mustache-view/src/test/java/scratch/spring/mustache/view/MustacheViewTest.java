package scratch.spring.mustache.view;

import com.github.mustachejava.Mustache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MustacheViewTest {

    @Mock
    private Mustache mustache;

    @Mock
    private Map model;

    @Mock
    private HttpServletRequest request;

    @Mock
    private PrintWriter writer;

    @Mock
    private HttpServletResponse response;

    @Test
    @SuppressWarnings("unchecked")
    public void I_can_render_a_view() throws Exception {

        when(response.getWriter()).thenReturn(writer);

        final MustacheView view = new MustacheView();
        view.setMustache(mustache);
        view.renderMergedTemplateModel(model, request, response);

        verify(mustache).execute(writer, model);
    }
}
