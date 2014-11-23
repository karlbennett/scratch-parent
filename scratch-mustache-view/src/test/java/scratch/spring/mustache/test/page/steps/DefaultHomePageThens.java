package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.DataUserRow;
import scratch.spring.mustache.test.page.EqualityUserRow;
import scratch.spring.mustache.test.page.HomePage;
import scratch.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static scratch.spring.mustache.test.UserConstants.containsAll;

class DefaultHomePageThens implements HomePageThens {

    private final PageTitleThens pageTitleThens;
    private final HomePage page;

    DefaultHomePageThens(HomePage page) {
        pageTitleThens = new DefaultPageTitleThens(page);
        this.page = page;
    }

    @Override
    public void should_have_a_title_of(String title) {
        pageTitleThens.should_have_a_title_of(title);
    }

    @Override
    public void should_contain_a_row_for_each_user_in(List<User> users) {

        final List<EqualityUserRow> rows = new ArrayList<>(users.size());

        for (User user : users) {
            rows.add(new DataUserRow(user));
        }

        should_contain_all(rows);
    }

    private void should_contain_all(List<EqualityUserRow> rows) {

        assertThat("The correct users should be listed.", page.users(), containsAll(rows));
    }
}
