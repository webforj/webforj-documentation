package com.webforj.samples.views.dialog;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Dialog Auto-Focus")
public class DialogAutoFocusView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogAutoFocusView() {
    Button openDialog = new Button("Invite teammate", ButtonTheme.PRIMARY);
    openDialog.onClick(e -> dialog.open());

    TextField email = new TextField(TextField.Type.EMAIL, "Email address", "");
    TextField role = new TextField("Role", "Developer");
    FlexLayout form =
        FlexLayout.create(email, role).vertical().build().setSpacing("var(--dwc-space-m)");

    Button cancel = new Button("Cancel");
    cancel.onClick(e -> dialog.close());
    Button sendInvite = new Button("Send invite", ButtonTheme.PRIMARY);
    sendInvite.onClick(e -> dialog.close());
    FlexLayout actions =
        FlexLayout.create(cancel, sendInvite)
            .horizontal()
            .justify()
            .end()
            .wrap()
            .build()
            .setSpacing("var(--dwc-space-s)")
            .setWidth("100%");

    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setMinHeight("100vh")
        .add(openDialog, dialog);

    dialog
        .addToHeader(new Div("Invite teammate"))
        .addToContent(form)
        .addToFooter(actions)
        .setAutoFocus(true)
        .setMaxWidth("28rem");
  }
}
