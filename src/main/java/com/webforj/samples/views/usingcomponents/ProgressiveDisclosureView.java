package com.webforj.samples.views.usingcomponents;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Fieldset;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.H4;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progressive Disclosure View")
@BundleEntry("usingcomponents/progressivedisclosure.css")
public class ProgressiveDisclosureView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Card card = new Card();
  private final CheckBox emailNotifications = new CheckBox("Email notifications");
  private final CheckBox pushNotifications = new CheckBox("Push notifications");
  private final CheckBox marketingEmails = new CheckBox("Marketing emails");
  private final CheckBox autoSave = new CheckBox("Auto-save changes");
  private final CheckBox debugMode = new CheckBox("Debug mode");
  private final CheckBox analytics = new CheckBox("Send analytics");
  private final FlexLayout form = new FlexLayout();
  private final Fieldset advancedSettings = new Fieldset();
  private final Button enableAdvanced = new Button("Show advanced settings");
  private final Button saveButton = new Button("Save Settings", ButtonTheme.PRIMARY);

  private boolean advancedVisible = false;

  public ProgressiveDisclosureView() {
    configComponents();
    setupLayout();
    setupHandlers();
  }

  private void configComponents() {
    emailNotifications.setValue(true);
    pushNotifications.setValue(false);
    marketingEmails.setValue(false);
    autoSave.setValue(true);
    enableAdvanced.setTheme(ButtonTheme.OUTLINED_DEFAULT);
    saveButton.setEnabled(false);
  }

  private void setupLayout() {

    card.setWidth(400);
    card.setMaxWidth("100%");
    card.setExpanse(Expanse.XLARGE);
    card.addToTitle(new H3("Preferences"));

    H4 notificationsTitle = new H4("Notifications");
    H4 generalTitle = new H4("General");

    advancedSettings
        .setStyle("display", "none")
        .setStyle("gap", "var(--dwc-space-s)")
        .add(debugMode, analytics);

    form.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-s)")
        .add(
            notificationsTitle,
            emailNotifications,
            pushNotifications,
            marketingEmails,
            generalTitle,
            autoSave,
            enableAdvanced,
            advancedSettings,
            saveButton);

    card.addToBody(form);

    enableAdvanced.setStyle("margin-top", "var(--dwc-space-m)");
    self.setAlignment(FlexAlignment.CENTER);
    self.setJustifyContent(FlexJustifyContent.CENTER);
    self.setHeight("100vh");
    self.setMargin("auto");
    self.setStyle("overflow-y", "auto");

    self.add(card);
  }

  private void setupHandlers() {
    emailNotifications.addValueChangeListener(e -> enableSaveButton());
    pushNotifications.addValueChangeListener(e -> enableSaveButton());
    marketingEmails.addValueChangeListener(e -> enableSaveButton());
    autoSave.addValueChangeListener(e -> enableSaveButton());
    debugMode.addValueChangeListener(e -> enableSaveButton());
    analytics.addValueChangeListener(e -> enableSaveButton());

    enableAdvanced.onClick(
        event -> {
          advancedVisible = !advancedVisible;

          if (advancedVisible) {
            advancedSettings.setStyle("display", "grid");
            enableAdvanced.setText("Hide advanced settings");
          } else {
            advancedSettings.setStyle("display", "none");
            enableAdvanced.setText("Show advanced settings");
          }
        });

    saveButton.onClick(
        e -> {
          Toast.show("Settings saved successfully");
          saveButton.setEnabled(false);
        });
  }

  private void enableSaveButton() {
    saveButton.setEnabled(true);
  }
}
