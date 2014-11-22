package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.Page;

import static org.junit.Assert.assertEquals;

class DefaultPageTitleThens implements PageTitleThens {

    private final Page page;

    DefaultPageTitleThens(Page page) {
        this.page = page;
    }

    @Override
    public void should_have_a_title_of(String title) {
        assertEquals("The title should be correct.", title, page.getTitle());
    }
}
