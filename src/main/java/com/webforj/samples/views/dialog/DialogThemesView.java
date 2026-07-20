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
    options.add(Theme.DEFAULT, "Default");
    options.add(Theme.PRIMARY, "Primary");
    options.add(Theme.SUCCESS, "Success");
    options.add(Theme.WARNING, "Warning");
    options.add(Theme.DANGER, "Danger");
    options.add(Theme.INFO, "Info");
    options.add(Theme.GRAY, "Gray");

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
