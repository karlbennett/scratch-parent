package scratch.spring.mustache.view;

import com.github.mustachejava.MustacheFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * The resolver that will match responses with their respective {@link MustacheView}s.
 *
 * @author Karl Bennett
 */
public class MustacheViewResolver extends AbstractTemplateViewResolver {

    private final MustacheFactory factory;
    private final ResourceLoader resourceLoader;

    public MustacheViewResolver(MustacheFactory factory, ResourceLoader resourceLoader) {

        this.factory = factory;
        this.resourceLoader = resourceLoader;

        setViewClass(MustacheView.class);
    }

    @Override
    protected Class<?> requiredViewClass() {

        return MustacheView.class;
    }

    @Override
    protected MustacheView buildView(String viewName) throws Exception {

        final MustacheView view = (MustacheView) super.buildView(viewName);

        final String name = view.getUrl();
        final Reader reader = new InputStreamReader(resourceLoader.getResource(name).getInputStream());

        view.setMustache(factory.compile(reader, name));

        return view;
    }
}
