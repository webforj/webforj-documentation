package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Auto Width")
public class DialogAutoWidthView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogAutoWidthView() {
    Button openDialog = new Button("Open export", ButtonTheme.PRIMARY);
    openDialog.onClick(e -> dialog.open());

    RadioButton autoWidth = RadioButton.Switch("Fit content width", true);
    autoWidth.onToggle(e -> dialog.setAutoWidth(e.isToggled()));

    FlexLayout content =
        FlexLayout.create(new Paragraph("Your export is ready to download."), autoWidth)
            .vertical()
            .build()
            .setSpacing("var(--dwc-space-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMinHeight("100vh")
        .add(openDialog, dialog);

    dialog
        .addToHeader(new Div("Export complete"))
        .addToContent(content)
        .setAutoWidth(true)
        .open();
  }
}
