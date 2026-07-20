package com.webforj.samples.views.dialog;

import com.webforj.bundle.annotation.BundleEntry;
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
@FrameTitle("Dialog Backdrop Blur")
@BundleEntry("css/dialog/dialog-backdrop-blur.css")
public class DialogBackdropBlurView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogBackdropBlurView() {
    Div scene = new Div().addClassName("dialog-backdrop-demo__scene");
    scene.add(
        new Div("Release overview").addClassName("dialog-backdrop-demo__title"),
        new Div("Planning: 4 items").addClassName("dialog-backdrop-demo__card"),
        new Div("In review: 7 items")
            .addClassName("dialog-backdrop-demo__card", "dialog-backdrop-demo__card--accent"),
        new Div("Ready: 12 items").addClassName("dialog-backdrop-demo__card"));

    Button openDialog = new Button("Open dialog", ButtonTheme.PRIMARY);
    openDialog.addClassName("dialog-backdrop-demo__trigger").onClick(e -> dialog.open());
    scene.add(openDialog);

    RadioButton blurBackdrop = RadioButton.Switch("Blur backdrop", true);
    blurBackdrop.onToggle(e -> dialog.setBlurred(e.isToggled()));
    FlexLayout content =
        FlexLayout.create(new Paragraph("The release report is ready to share."), blurBackdrop)
            .vertical()
            .build()
            .setSpacing("var(--dwc-space-m)");

    Button close = new Button("Close");
    close.onClick(e -> dialog.close());

    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .addClassName("dialog-backdrop-demo")
        .add(scene, dialog);

    dialog
        .addToHeader(new Div("Share release report"))
        .addToContent(content)
        .addToFooter(close)
        .setBackdrop(true)
        .setBlurred(true)
        .setCloseable(false)
        .setMaxWidth("28rem")
        .open();
  }
}
