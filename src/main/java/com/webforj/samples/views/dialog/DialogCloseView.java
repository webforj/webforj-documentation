package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Close")
public class DialogCloseView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogCloseView() {
    Button showDialog = new Button("Open dialog", ButtonTheme.PRIMARY);
    showDialog.onClick(e -> dialog.open());

    RadioButton closeOnEscape = RadioButton.Switch("Escape key", true);
    closeOnEscape.onToggle(e -> dialog.setCancelOnEscKey(e.isToggled()));
    RadioButton closeOnOutsideClick = RadioButton.Switch("Outside click", true);
    closeOnOutsideClick.onToggle(e -> dialog.setCancelOnOutsideClick(e.isToggled()));
    FlexLayout closeOptions =
        FlexLayout.create(closeOnEscape, closeOnOutsideClick)
            .vertical()
            .build()
            .setSpacing("var(--dwc-space-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMinHeight("100vh")
        .add(showDialog, dialog);

    dialog
        .addToHeader(new Div("Close behavior"))
        .addToContent(closeOptions)
        .setCancelOnEscKey(true)
        .setCancelOnOutsideClick(true)
        .setMaxWidth("28rem")
        .open();
  }
}
