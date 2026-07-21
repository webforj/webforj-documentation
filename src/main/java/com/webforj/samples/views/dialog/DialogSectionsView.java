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
@FrameTitle("Dialog Sections")
public class DialogSectionsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Dialog dialog = new Dialog();

  public DialogSectionsView() {
    Button openDialog = new Button("Edit profile", ButtonTheme.PRIMARY);
    openDialog.onClick(e -> dialog.open());

    TextField name = new TextField("Name", "Alex Morgan");
    TextField email =
        new TextField(TextField.Type.EMAIL, "Email address", "alex.morgan@example.com");
    FlexLayout form =
        FlexLayout.create(name, email).vertical().build().setSpacing("var(--dwc-space-m)");

    Button cancel = new Button("Cancel");
    cancel.onClick(e -> dialog.close());
    Button save = new Button("Save changes", ButtonTheme.PRIMARY);
    save.onClick(e -> dialog.close());
    FlexLayout actions =
        FlexLayout.create(cancel, save)
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
        .addToHeader(new Div("Edit profile"))
        .addToContent(form)
        .addToFooter(actions)
        .setMaxWidth("28rem")
        .open();
  }
}
