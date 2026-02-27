package com.webforj.samples.views.table;

import java.util.EventObject;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.event.KeypressEvent;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H3;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

/**
 * A component that allows the user to edit a MusicRecord title.
 * Uses fluent API for component configuration.
 */
public class TitleEditorComponent extends Composite<Dialog> {
  private final Dialog self = getBoundComponent();
  private final EventDispatcher dispatcher = new EventDispatcher();
  private final TextField titleField = new TextField("New Title");
  private final FlexLayout footer = FlexLayout.create()
    .align().end()
    .build();
  private MusicRecord item;

  /**
   * Creates a new TitleEditorComponent with fluent configuration.
   */
  public TitleEditorComponent() {
    self.setMaxWidth("400px")
      .onOpen(ev -> titleField.focus());

    Button saveButton = new Button("Save", ButtonTheme.PRIMARY, ev -> save());
    Button cancelButton = new Button("Cancel", ev -> self.close());

    footer.add(saveButton, cancelButton);

    self.addToHeader(new H3("Edit Title"))
      .addToFooter(footer)
      .add(titleField);

    titleField.onKeypress(ev -> {
      if (ev.getKeyCode() == KeypressEvent.Key.ENTER) {
        save();
      }
    });
  }

  /**
   * Edit the title of the given MusicRecord.
   *
   * @param item the MusicRecord to edit
   */
  public void edit(MusicRecord item) {
    this.item = item;
    titleField.setText(item.getTitle());
    self.open();
  }

  /**
   * Adds a listener to the save event.
   *
   * @param listener the listener to add
   * @return a registration that can be used to remove the listener
   */
  public ListenerRegistration<SaveEvent> onSave(EventListener<SaveEvent> listener) {
    return dispatcher.addListener(SaveEvent.class, listener);
  }

  private void save() {
    item.setTitle(titleField.getText());
    self.close();
    dispatcher.dispatchEvent(new SaveEvent(this));
  }

  /**
   * An event that is dispatched when the user saves the title.
   */
  public static class SaveEvent extends EventObject {

    private final MusicRecord record;

    public SaveEvent(Object source) {
      super(source);
      this.record = ((TitleEditorComponent) source).item;
    }

    public MusicRecord getItem() {
      return record;
    }
  }
}
