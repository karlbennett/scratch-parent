package scratch.spring.mustache.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import scratch.spring.mustache.config.ScratchMustacheConfiguration;

import java.util.HashMap;

import static java.lang.String.format;
import static java.util.Collections.singletonMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MustacheViewIntegrationTest.class, ScratchMustacheConfiguration.class})
@Configuration
@ComponentScan
@WebAppConfiguration("classpath:")
public class MustacheViewIntegrationTest {

    private static final String TEST_PATH = "/test";
    private static final String TEST_WITH_PARTIAL_PATH = "/test-with-partial";
    private static final String TEST_WITH_SUPER_PATH = "/test-with-super";

    private static final String VALUE_ONE = "test value 1";
    private static final String VALUE_TWO = "test value 2";

    private static final String VALUE_THREE = "test value 3";

    private static final String RENDERED_TEMPLATE = format(
            "This test template should have %s, %s, and %s replaced.",
            VALUE_ONE, VALUE_TWO, VALUE_THREE);

    private static final String RENDERED_PARTIAL = format(
            "This partial should be injected and have %s replaced.",
            VALUE_ONE);
    private static final String RENDERED_TEMPLATE_WITH_PARTIAL = format(
            "A partial should be injected right here: %s Before this sentence.",
            RENDERED_PARTIAL);

    private static final String RENDERED_SUB = format(
            "changed and value %s injected",
            VALUE_ONE);

    private static final String RENDERED_TEMPLATE_WITH_SUPER = format(
            "This super template should have it's %s by the sub template.",
            RENDERED_SUB);

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void I_can_render_a_view() throws Exception {

        mockMvc.perform(get(TEST_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(RENDERED_TEMPLATE));
    }

    @Test
    public void I_can_render_a_view_with_a_partial() throws Exception {

        mockMvc.perform(get(TEST_WITH_PARTIAL_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(RENDERED_TEMPLATE_WITH_PARTIAL));
    }

    @Test
    public void I_can_render_a_view_with_a_super_template() throws Exception {

        mockMvc.perform(get(TEST_WITH_SUPER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(RENDERED_TEMPLATE_WITH_SUPER));
    }

    @Controller
    public static class TestController {

        @RequestMapping(TEST_PATH)
        public ModelAndView test() {

            final HashMap<String, Object> model = new HashMap<>();
            model.put("one", VALUE_ONE);
            model.put("two", VALUE_TWO);
            model.put("three", VALUE_THREE);

            return new ModelAndView("test.mustache", model);
        }

        @RequestMapping(TEST_WITH_PARTIAL_PATH)
        public ModelAndView testWithPartial() {

            return new ModelAndView("test_with_partial.mustache", singletonMap("one", VALUE_ONE));
        }

        @RequestMapping(TEST_WITH_SUPER_PATH)
        public ModelAndView testWithSuper() {

            return new ModelAndView("sub.mustache", singletonMap("one", VALUE_ONE));
        }
    }
}
