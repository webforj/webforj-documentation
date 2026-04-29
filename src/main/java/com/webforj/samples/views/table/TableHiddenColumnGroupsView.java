package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.ColumnGroup;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;

@Route
@FrameTitle("Table Hidden Columns in Groups")
public class TableHiddenColumnGroupsView extends Composite<Div> {

  private Div self = getBoundComponent();

  public TableHiddenColumnGroupsView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");
    table.setStriped(true);

    table.addColumn("Title", MusicRecord::getTitle).setMinWidth(120f);
    table.addColumn("Artist", MusicRecord::getArtist).setMinWidth(120f);
    table.addColumn("Genre", MusicRecord::getMusicType).setMinWidth(80f);
    Column<MusicRecord, ?> costColumn =
        table.addColumn(
            "Cost",
            record -> {
              return String.format("$%.2f", record.getCost());
            });
    costColumn.setAlignment(Column.Alignment.RIGHT).setMinWidth(70f);
    Column<MusicRecord, ?> retailColumn =
        table.addColumn(
            "Retail",
            record -> {
              return String.format("$%.2f", record.getRetail());
            });
    retailColumn.setAlignment(Column.Alignment.RIGHT).setMinWidth(70f);

    table.setColumnsToAutoFit();

    retailColumn.setHidden(true);

    ColumnGroup catalog =
        ColumnGroup.of("catalog", "Catalog").add("Title").add("Artist").add("Genre");

    ColumnGroup pricing = ColumnGroup.of("pricing", "Pricing").add("Cost").add("Retail");

    table.setColumnGroups(List.of(catalog, pricing));

    Button toggleRetail = new Button("Toggle Retail Column", ButtonTheme.PRIMARY);
    toggleRetail.setMaxWidth("200px");
    toggleRetail.setStyle("margin-top", "1rem");
    toggleRetail.setStyle("margin-left", "1rem");
    toggleRetail.onClick(
        e -> {
          retailColumn.setHidden(!retailColumn.isHidden());
          table.refreshColumns();
        });

    FlexLayout layout = FlexLayout.create(toggleRetail, table).vertical().build();
    layout.setHeight("100vh");

    table.setRepository(Service.getMusicRecords());

    self.add(layout);
  }
}
