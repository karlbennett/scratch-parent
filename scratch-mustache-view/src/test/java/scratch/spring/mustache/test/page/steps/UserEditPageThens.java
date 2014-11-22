package scratch.spring.mustache.test.page.steps;

public interface UserEditPageThens extends UserPageThens, UserPageTitleThens {

    void should_contain_an_email_error_of(String message);

    void should_contain_a_first_name_error_of(String message);

    void should_contain_a_last_name_error_of(String message);

    void should_not_contain_an_email_error();

    void should_not_contain_a_first_name_error();

    void should_not_contain_a_last_name_error();
}
