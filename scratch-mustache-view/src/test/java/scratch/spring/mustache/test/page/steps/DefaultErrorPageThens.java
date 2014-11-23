package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.ErrorPage;

import static org.junit.Assert.assertEquals;

class DefaultErrorPageThens implements ErrorPageThens {

    private final ErrorPage page;
    private final PageTitleThens pageTitleThens;

    DefaultErrorPageThens(ErrorPage page) {
        pageTitleThens = new DefaultPageTitleThens(page);
        this.page = page;
    }

    @Override
    public void should_have_a_title_of(String title) {
        pageTitleThens.should_have_a_title_of(title);
    }

    @Override
    public void should_contain_the_message(String message) {
        assertEquals("The error message should be correct.", message, page.getErrorMessage());
    }
}
