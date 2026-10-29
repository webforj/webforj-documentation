package com.webforj.samples.unit.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.table.Table;
import com.webforj.data.repository.event.RepositoryCommitEvent;
import com.webforj.samples.views.table.Person;
import com.webforj.samples.views.table.TableDynamicStylingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableDynamicStylingViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldCommitAliceAndRestyleHerAgeCellEachTimeTheUserTogglesHerAge(WebforjTestbed context) {
    context.add(new TableDynamicStylingView());

    Table<Person> table = ComponentLocator.of(Table.class).locate();
    var alice = table.getItems().iterator().next();
    var age = table.getColumnById("Age");
    var button = ComponentLocator.of(Button.class).byText("Increase Alice Age").locate();
    List<RepositoryCommitEvent<Person>> commits = new ArrayList<>();
    table.getRepository().onCommit(commits::add);

    assertEquals(List.of("cell-junior"), table.getCellPartProvider().apply(alice, age));

    UserInteraction.click(button);

    assertEquals(31, alice.getAge());
    assertEquals(List.of(alice), commits.get(0).getCommits());
    assertEquals(List.of("cell-senior"), table.getCellPartProvider().apply(alice, age));
    assertEquals("Decrease Alice Age", button.getText());

    UserInteraction.click(button);

    assertEquals(28, alice.getAge());
    assertEquals(List.of(alice), commits.get(1).getCommits());
    assertEquals(List.of("cell-junior"), table.getCellPartProvider().apply(alice, age));
    assertEquals("Increase Alice Age", button.getText());
  }
}
