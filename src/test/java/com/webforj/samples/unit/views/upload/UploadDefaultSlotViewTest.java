package com.webforj.samples.unit.views.upload;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.upload.UploadInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.upload.Upload;
import com.webforj.samples.views.upload.UploadDefaultSlotView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class UploadDefaultSlotViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new UploadDefaultSlotView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldProjectTheTableIntoTheHeadlessUpload(WebforjTestbed context) {
    context.add(new UploadDefaultSlotView());

    var upload = ComponentLocator.of(Upload.class).locate();
    var table = ComponentLocator.of(Table.class).locate();

    assertEquals(Upload.Preset.HEADLESS, upload.getPreset());
    assertTrue(Arrays.stream(Upload.Part.values()).noneMatch(upload::isVisible));
    verifyThat(upload).hasComponent(table);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldRenderTheRowsOfTheDroppedCsvWithColumnsFromItsHeader(WebforjTestbed context) {
    context.add(new UploadDefaultSlotView());

    var upload = ComponentLocator.of(Upload.class).locate();
    Table<List<String>> table = ComponentLocator.of(Table.class).locate();

    context.pickFile(
        "contacts.csv",
        """
        Name,Email
        Alice,alice@example.com
        Bob,bob@example.com
        """
            .getBytes(StandardCharsets.UTF_8));
    UserInteraction.perform(upload, UploadInteraction.upload("contacts.csv"));

    assertEquals(List.of("Name", "Email"), table.getColumns().stream().map(Column::getId).toList());
    assertEquals(
        List.of(List.of("Alice", "alice@example.com"), List.of("Bob", "bob@example.com")),
        table.getItems().stream().toList());
    assertEquals(
        "alice@example.com",
        table.getColumnById("Email").getValue(List.of("Alice", "alice@example.com")));
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldReplaceTheColumnsAndRowsWhenAnotherCsvIsDropped(WebforjTestbed context) {
    context.add(new UploadDefaultSlotView());

    var upload = ComponentLocator.of(Upload.class).locate();
    Table<List<String>> table = ComponentLocator.of(Table.class).locate();

    context.pickFile(
        "contacts.csv", "Name,Email\nAlice,alice@example.com\n".getBytes(StandardCharsets.UTF_8));
    UserInteraction.perform(upload, UploadInteraction.upload("contacts.csv"));
    context.pickFile("companies.csv", "Company\nTesla\n".getBytes(StandardCharsets.UTF_8));
    UserInteraction.perform(upload, UploadInteraction.upload("companies.csv"));

    assertEquals(List.of("Company"), table.getColumns().stream().map(Column::getId).toList());
    assertEquals(List.of(List.of("Tesla")), table.getItems().stream().toList());
  }
}
