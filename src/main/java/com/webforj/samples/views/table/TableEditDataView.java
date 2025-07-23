package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.VoidElementRenderer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Table Edit Data")
public class TableEditDataView extends Composite<Div> {
  TitleEditorComponent editor = new TitleEditorComponent();
  Table<MusicRecord> table = new Table<>();

  public TableEditDataView() {

    table.setWidth("100vw");
    table.setHeight("100vh");
    table.addColumn("Number", MusicRecord::getNumber).setPinDirection(Column.PinDirection.LEFT);
    table.addColumn("Title", MusicRecord::getTitle);
    table.addColumn("Artist", MusicRecord::getArtist);
    table.addColumn("Genre", MusicRecord::getMusicType);

    VoidElementRenderer<MusicRecord> editRenderer = new VoidElementRenderer<>("dwc-icon-button",
        ev -> editor.edit(ev.getItem()));
    editRenderer.setAttribute("name", "pencil-pin");

    table.addColumn(editRenderer).setAlignment(Column.Alignment.CENTER)
        .setPinDirection(Column.PinDirection.RIGHT);

    table.setRepository(Service.getMusicRecords());
    table.setRowHeight(42);

    editor.onSave(ev -> table.getRepository().commit(ev.getItem()));

    getBoundComponent().add(table, editor);
  }
}
