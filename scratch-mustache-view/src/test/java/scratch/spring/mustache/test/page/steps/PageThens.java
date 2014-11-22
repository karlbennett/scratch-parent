package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.Page;

import static org.junit.Assert.assertEquals;

public class PageThens {

    private final Page page;

    public PageThens(Page page) {
        this.page = page;
    }

    public void should_have_a_title_of(String title) {
        assertEquals("The title should be correct.", title, page.getTitle());
    }
}
