package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.Page;

import static org.junit.Assert.assertEquals;

public class DefaultPageThens implements PageThens {

    private final Page page;

    public DefaultPageThens(Page page) {
        this.page = page;
    }

    @Override
    public void should_have_a_title_of(String title) {
        assertEquals("The title should be correct.", title, page.getTitle());
    }
}
