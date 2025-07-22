package com.webforj.samples.views.dialog;

import com.webforj.component.list.ChoiceBox;
import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("dialogsections")
@FrameTitle("Dialog Themes")
public class DialogThemesView extends Composite<FlexLayout> {

  private Dialog dialog = new Dialog();
  private ChoiceBox options = new ChoiceBox();

  public DialogThemesView() {
    getBoundComponent().add(dialog);

    for (com.webforj.component.Theme theme : com.webforj.component.Theme.values()) {
      options.add(theme, Character.toUpperCase(theme.name().charAt(0)) + theme.name().substring(1));
    }

    options.setLabel("Select Theme")
        .setStyle("flex", "1")
        .selectIndex(1)
        .onSelect(e -> dialog.setTheme(((com.webforj.component.Theme) e.getSelectedItem().getKey())));

    dialog.addToHeader(new Div("Themes"));
    dialog.addToContent(options)
        .setStyle("display", "flex")
        .setStyle("justify-content", "center");

    dialog.open();
    dialog.setCloseable(false);
  }
}
