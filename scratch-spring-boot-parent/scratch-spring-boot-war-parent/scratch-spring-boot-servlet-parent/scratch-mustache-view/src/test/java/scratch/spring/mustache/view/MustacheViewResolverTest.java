package scratch.spring.mustache.view;

import com.github.mustachejava.MustacheFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.io.Reader;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MustacheViewResolverTest {

    @Mock
    private MustacheFactory factory;

    @Mock
    private ResourceLoader loader;

    @Mock
    private InputStream inputStream;

    @Mock
    private Resource resource;

    @Test
    public void I_can_resolve_a_view() throws Exception {

        final String viewName = "test";

        when(resource.getInputStream()).thenReturn(inputStream);

        when(loader.getResource(viewName)).thenReturn(resource);

        assertNotNull("a view should be resolved.", new MustacheViewResolver(factory, loader).buildView(viewName));

        verify(factory).compile(any(Reader.class), eq(viewName));
    }
}
