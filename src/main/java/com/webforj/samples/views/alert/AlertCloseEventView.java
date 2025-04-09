package com.webforj.samples.views.alert;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.alert.event.AlertCloseEvent;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Alert Close Event")
public class AlertCloseEventView extends Composite<FlexLayout> {

  private final TextField nameField = new TextField("Name");
  private final TextField emailField = new TextField("Email");

  private final Alert alert = new Alert("Your profile has been updated successfully.")
      .setTheme(Theme.SUCCESS)
      .setClosable(true)
      .setWidth("100%")
      .setMaxWidth("400px")
      .close();

  private final Paragraph statusMsg = new Paragraph();

  private final Button keepEditingButton = new Button("Keep Editing");

  public AlertCloseEventView() {
    FlexLayout layout = getBoundComponent();
    layout.setDirection(FlexDirection.COLUMN)
          .setAlignment(FlexAlignment.CENTER)
          .setJustifyContent(FlexJustifyContent.CENTER)
          .setSpacing("var(--dwc-space-l)")
          .setMargin("var(--dwc-space-xl)")
          .setWidth("100%");

    nameField.setText("Jane Doe").setWidth("100%");
    emailField.setText("jane.doe@example.com").setWidth("100%");

    Button saveButton = new Button("Save Changes", ButtonTheme.PRIMARY);
    saveButton.addClickListener(e -> {
      alert.open();
      keepEditingButton.setVisible(false);
      statusMsg.setText("Changes saved. Dismiss alert to make fields read-only.");
    });
    saveButton.setWidth("100%");

    alert.onClose((AlertCloseEvent e) -> {
      statusMsg.setText("Alert dismissed. Fields are now read-only.");
      nameField.setReadOnly(true);
      emailField.setReadOnly(true);
      keepEditingButton.setVisible(true);
    });

    keepEditingButton.setVisible(false);
    keepEditingButton.addClickListener(e -> {
      nameField.setReadOnly(false);
      emailField.setReadOnly(false);
      alert.close();
      statusMsg.setText("");
      keepEditingButton.setVisible(false);
    });
    keepEditingButton.setWidth("100%");

    FlexLayout formLayout = new FlexLayout(
        nameField,
        emailField,
        saveButton,
        alert,
        keepEditingButton,
        statusMsg
    );

    formLayout.setDirection(FlexDirection.COLUMN)
              .setSpacing("var(--dwc-space-m)")
              .setWidth("400px")
              .setAlignment(FlexAlignment.CENTER)
              .setJustifyContent(FlexJustifyContent.CENTER);

    layout.add(formLayout);
  }
}