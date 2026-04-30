package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
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
    self.add(dialog);

    RadioButton autoWidth = RadioButton.Switch("Auto width", true);
    autoWidth.onToggle(e -> dialog.setAutoWidth(e.isToggled()));

    Paragraph body = new Paragraph("Toggle Auto width to resize this dialog to fit its content.");
    body.setStyle("max-width", "26ch");

    FlexLayout content = FlexLayout.create(body).vertical().build().setPadding("20px");

    Button save = new Button("Save", ButtonTheme.PRIMARY);

    FlexLayout actions =
        FlexLayout.create(autoWidth, save)
            .horizontal()
            .justify()
            .between()
            .align()
            .center()
            .build()
            .setStyle("width", "100%");

    dialog
        .addToHeader(new Div("Save changes"))
        .addToContent(content)
        .addToFooter(actions)
        .setCloseable(false)
        .setAutoWidth(true)
        .open();
  }
}
