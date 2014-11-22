package scratch.spring.mustache.test.page.steps;

import scratch.spring.mustache.test.page.EqualityUserRow;
import scratch.user.User;

import java.util.List;

public interface HomePageThens extends PageThens {

    void should_contain_rows_for(User... users);

    void should_contain_a_row_for(List<EqualityUserRow> rows);
}
