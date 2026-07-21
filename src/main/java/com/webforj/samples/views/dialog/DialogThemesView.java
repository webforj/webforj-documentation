package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Themes")
public class DialogThemesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogThemesView() {
    self.add(dialog);

    ChoiceBox options = new ChoiceBox();
    for (Theme theme : Theme.values()) {
          options.add(theme, theme.name());
        }

    options
        .setLabel("Dialog theme")
        .selectIndex(0)
        .onSelect(e -> dialog.setTheme((Theme) e.getSelectedItem().getKey()));

    FlexLayout content =
        FlexLayout.create(new Paragraph("Your changes have been saved."), options)
            .vertical()
            .build()
            .setSpacing("var(--dwc-space-m)");

    dialog
        .addToHeader(new Div("Dialog theme"))
        .addToContent(content)
        .setTheme(Theme.DEFAULT)
        .setCloseable(false)
        .setMaxWidth("28rem")
        .open();
  }
}
