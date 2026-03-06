package com.webforj.samples.views.table;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import java.util.List;
import java.util.stream.Collectors;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Multiple Selection")
public class TableMultiSelectionView extends Composite<Div> {
  // self field enables fluent method chaining from the bound component
  private final Div self = getBoundComponent();

  public TableMultiSelectionView() {
    Table<MusicRecord> table = new Table<MusicRecord>()
        .setWidth("100vw")
        .setHeight("100vh");

    table.addColumn("Number", MusicRecord::getNumber);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    table.addColumn("Cost", MusicRecord::getCost);

    table.setRepository(Service.getMusicRecords());
    table.setSelectionMode(Table.SelectionMode.MULTIPLE);

    table.onItemSelectionChange(ev -> {
      List<MusicRecord> records = ev.getSelectedItems();
      String msg = "There are no records selected";

      if (!records.isEmpty()) {
        // Use text block for multi-line HTML
        msg = """
            <html> You have selected the following records
            %s
            </html>""".formatted(
            records.stream()
                .map(r -> "<li>" + r.getTitle() + "</li>")
                .collect(Collectors.joining("", "<ul>", "</ul>"))
        );
      }

      showMessageDialog(msg, "Record Selection");
    });

    self.add(table);
  }
}
