package com.webforj.samples.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.table.event.renderer.RendererClickEvent;
import com.webforj.data.repository.event.RepositoryCommitEvent;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableEditDataViewTest {
  @Test
  void shouldOpenTheEditorWithTheTitleOfTheClickedRecord(WebforjTestbed context) {
    var view = new TableEditDataView();
    context.add(view);

    var table = view.table;
    var record = table.getRepository().findAll().skip(2).findFirst().orElseThrow();

    clickRenderer(table, record, table.getColumns().get(4));

    assertTrue(ComponentLocator.of(Dialog.class).locate().isOpened());
    assertEquals(record.getTitle(), ComponentLocator.of(TextField.class).locate().getText());
  }

  @Test
  void shouldCommitTheNewTitleToTheRepositoryWhenTheUserSaves(WebforjTestbed context) {
    var view = new TableEditDataView();
    context.add(view);

    var table = view.table;
    var record = table.getRepository().findAll().findFirst().orElseThrow();
    List<RepositoryCommitEvent<MusicRecord>> commits = new ArrayList<>();
    table.getRepository().onCommit(commits::add);

    clickRenderer(table, record, table.getColumns().get(4));
    UserInteraction.type(ComponentLocator.of(TextField.class).locate(), "Updated title");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Save").locate());

    assertEquals("Updated title", record.getTitle());
    assertEquals(List.of(record), commits.get(0).getCommits());
    assertFalse(ComponentLocator.of(Dialog.class).locate().isOpened());
  }

  @Test
  void shouldCommitTheNewTitleWhenTheUserPressesEnter(WebforjTestbed context) {
    var view = new TableEditDataView();
    context.add(view);

    var table = view.table;
    var record = table.getRepository().findAll().findFirst().orElseThrow();
    List<RepositoryCommitEvent<MusicRecord>> commits = new ArrayList<>();
    table.getRepository().onCommit(commits::add);

    clickRenderer(table, record, table.getColumns().get(4));
    var field = ComponentLocator.of(TextField.class).locate();
    UserInteraction.type(field, "Entered title");
    UserInteraction.keypress(field, KeypressEvent.Key.ENTER);

    assertEquals("Entered title", record.getTitle());
    assertEquals(List.of(record), commits.get(0).getCommits());
  }

  @Test
  void shouldKeepTheTitleAndCommitNothingWhenTheUserCancels(WebforjTestbed context) {
    var view = new TableEditDataView();
    context.add(view);

    var table = view.table;
    var record = table.getRepository().findAll().findFirst().orElseThrow();
    String title = record.getTitle();
    List<RepositoryCommitEvent<MusicRecord>> commits = new ArrayList<>();
    table.getRepository().onCommit(commits::add);

    clickRenderer(table, record, table.getColumns().get(4));
    UserInteraction.type(ComponentLocator.of(TextField.class).locate(), "Discard me");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Cancel").locate());

    assertEquals(title, record.getTitle());
    assertTrue(commits.isEmpty());
    assertFalse(ComponentLocator.of(Dialog.class).locate().isOpened());
  }

  private static void clickRenderer(
      Table<MusicRecord> table, MusicRecord record, Column<MusicRecord, ?> column) {
    UserInteraction.fireEvent(
        new RendererClickEvent<>(
            table,
            Map.of(
                "itemKey",
                table.getItemKeysRegistry().getKey(record),
                "columnKey",
                column.getId())));
  }
}
