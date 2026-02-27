package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.Renderer;
import com.webforj.annotation.StyleSheet;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.table.Column;

@StyleSheet("ws://css/table/tableRichContent.css")
@Route
@FrameTitle("Table Rich Content")
public class TableRichContentView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public TableRichContentView() {
    Table<MusicRecord> table = new Table<MusicRecord>()
        .addClassName("table");

    table.addColumn("Title", MusicRecord::getTitle).setHidden(true);
    table.addColumn("Artist", MusicRecord::getArtist).setHidden(true);
    table.addColumn("Title & Artist", new AvatarRenderer()).setFlex(1f).setMinWidth(200f);
    table.addColumn("Genre", MusicRecord::getMusicType)
        .setPinDirection(Column.PinDirection.RIGHT);
    table.addColumn("Cost", MusicRecord::getCost)
        .setRenderer(new BadgeRenderer())
        .setPinDirection(Column.PinDirection.RIGHT);

    table.setRepository(Service.getMusicRecords());
    table.setSelectionMode(Table.SelectionMode.MULTIPLE);
    table.setRowHeight(45);

    table.refreshColumns();

    self.add(table);
  }

  // Inner renderer class for avatar display
  static class AvatarRenderer extends Renderer<MusicRecord> {
    @Override
    public String build() {
      return /* html */"""
          <%
          const artist = cell.row.getValue('Artist');
          const title = cell.row.getValue('Title');
          %>
          <div part='avatar-renderer'>
            <img
              part='avatar-img'
              src='https://i.pravatar.cc/32?u=<%= encodeURIComponent(artist) %>' />
            <div part="avatar-text">
              <%= title %>
              <div part="avatar-subtext">
                <%= artist %>
              </div>
            </div>
          </div>
          """;
    }
  }

  // Inner renderer class for cost badge
  static class BadgeRenderer extends Renderer<MusicRecord> {
    @Override
    public String build() {
      return """
          <span part='badge badge-<%= cell.value > 7 ? "high" : "low" %>'>
            <%= cell.value %>
          </span>
          """;
    }
  }
}
