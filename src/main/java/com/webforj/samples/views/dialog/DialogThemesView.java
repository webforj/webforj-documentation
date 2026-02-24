package com.webforj.samples.views.dialog;

import com.webforj.component.Theme;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.Composite;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Themes")
public class DialogThemesView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Dialog dialog = new Dialog();

  public DialogThemesView() {
    self.add(dialog);

    ChoiceBox options = new ChoiceBox();

    for (Theme theme : Theme.values()) {
      options.add(theme, theme.name());
    }

    options.setLabel("Select Theme")
            .setStyle("flex", "1")
            .selectIndex(1)
            .onSelect(e -> dialog.setTheme(((Theme) e.getSelectedItem().getKey())));

    Div header = new Div("Themes");
    dialog.addToHeader(header)
            .addToContent(options)
            .setStyle("display", "flex")
            .setStyle("justify-content", "center")
            .open()
            .setCloseable(false);
  }
}
