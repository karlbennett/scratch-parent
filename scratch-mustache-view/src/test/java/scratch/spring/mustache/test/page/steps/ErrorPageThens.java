package scratch.spring.mustache.test.page.steps;

public interface ErrorPageThens extends PageTitleThens {

    void should_contain_the_message(String message);
}
