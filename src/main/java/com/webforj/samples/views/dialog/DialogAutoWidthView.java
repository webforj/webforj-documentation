package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Auto Width")
public class DialogAutoWidthView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogAutoWidthView() {
    self.add(dialog);

    FlexLayout content = FlexLayout.create(
        TablerIcon.create("trash").setStyle("font-size", "48px")
            .setStyle("color", "var(--dwc-color-danger-text)"),
        new Paragraph("Are you sure you want to delete this item?"))
        .vertical()
        .align().center()
        .build()
        .setPadding("20px")
        .setSpacing("10px");

    Button toggleAutoWidth = new Button("Toggle Auto Width", ButtonTheme.PRIMARY);
    Button toggleDefault = new Button("Toggle Default Width");

    toggleAutoWidth.onClick(e -> dialog.setAutoWidth(true));
    toggleDefault.onClick(e -> dialog.setAutoWidth(false));

    FlexLayout actions = FlexLayout.create(toggleDefault, toggleAutoWidth)
        .horizontal()
        .justify().end()
        .build()
        .setSpacing("10px");

    dialog.addToHeader(new Div("Delete Item"))
        .addToContent(content)
        .addToFooter(actions)
        .setCloseable(false)
        .open();
  }
}
