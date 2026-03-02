package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
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

  public TableHiddenColumnGroupsView() {

    Table<MusicRecord> table = new Table<>();
    table.setWidth("100vw");

    table.addColumn("Number", MusicRecord::getNumber);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);
    Column<MusicRecord, ?> tracksColumn =
        table.addColumn("Tracks", MusicRecord::getNumberOfTracks);
    Column<MusicRecord, ?> playingTimeColumn =
        table.addColumn("Playing Time", MusicRecord::getPlayingTime);
    table.addColumn("Cost", record -> {
      return String.format("$%.2f", record.getCost());
    }).setAlignment(Column.Alignment.RIGHT);
    Column<MusicRecord, ?> retailColumn = table.addColumn("Retail", record -> {
      return String.format("$%.2f", record.getRetail());
    });
    retailColumn.setAlignment(Column.Alignment.RIGHT);

    tracksColumn.setHidden(true);
    playingTimeColumn.setHidden(true);

    ColumnGroup catalog = ColumnGroup.of("catalog", "Catalog")
        .add("Title")
        .add("Artist")
        .add("Genre");

    ColumnGroup details = ColumnGroup.of("details", "Details")
        .add("Tracks")
        .add("Playing Time");

    ColumnGroup pricing = ColumnGroup.of("pricing", "Pricing")
        .add("Cost")
        .add("Retail");

    table.setColumnGroups(List.of(catalog, details, pricing));

    Button toggleDetails = new Button("Toggle Details Group");
    toggleDetails.onClick(e -> {
      tracksColumn.setHidden(!tracksColumn.isHidden());
      playingTimeColumn.setHidden(!playingTimeColumn.isHidden());
      table.refreshColumns();
    });

    Button toggleRetail = new Button("Toggle Retail Column");
    toggleRetail.onClick(e -> {
      retailColumn.setHidden(!retailColumn.isHidden());
      table.refreshColumns();
    });

    FlexLayout toolbar = FlexLayout.create(toggleDetails, toggleRetail)
        .horizontal().build();
    toolbar.setSpacing("var(--dwc-space-s)");
    toolbar.setStyle("padding", "var(--dwc-space-s)");

    FlexLayout layout = FlexLayout.create(toolbar, table)
        .vertical().build();
    layout.setHeight("100vh");

    table.setRepository(Service.getMusicRecords());

    getBoundComponent().add(layout);
  }
}