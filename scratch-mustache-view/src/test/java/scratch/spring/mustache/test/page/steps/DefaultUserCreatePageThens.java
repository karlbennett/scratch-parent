package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.UserCreatePage;
import scratch.user.User;

class DefaultUserCreatePageThens implements UserCreatePageThens {

    private final PageTitleThens pageTitleThens;
    private final UserPageThens userPageThens;
    private final DefaultUserEditablePageThens defaultUserEditablePageThens;

    DefaultUserCreatePageThens(UserCreatePage page) {
        pageTitleThens = new DefaultPageTitleThens(page);
        userPageThens = new DefaultUserPageThens(page);
        defaultUserEditablePageThens = new DefaultUserEditablePageThens(page);
    }

    @Override
    public void should_have_a_title_of(String title) {
        pageTitleThens.should_have_a_title_of(title);
    }

    @Override
    public void should_contain_the_data_from(User user) {
        userPageThens.should_contain_the_data_from(user);
    }

    @Override
    public void should_contain_an_email_error_of(String message) {
        defaultUserEditablePageThens.should_contain_an_email_error_of(message);
    }

    @Override
    public void should_contain_a_first_name_error_of(String message) {
        defaultUserEditablePageThens.should_contain_a_first_name_error_of(message);
    }

    @Override
    public void should_contain_a_last_name_error_of(String message) {
        defaultUserEditablePageThens.should_contain_a_last_name_error_of(message);
    }

    @Override
    public void should_not_contain_an_email_error() {
        defaultUserEditablePageThens.should_not_contain_an_email_error();
    }

    @Override
    public void should_not_contain_a_first_name_error() {
        defaultUserEditablePageThens.should_not_contain_a_first_name_error();
    }

    @Override
    public void should_not_contain_a_last_name_error() {
        defaultUserEditablePageThens.should_not_contain_a_last_name_error();
    }
}
