package com.webforj.samples.views.usingcomponents;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Progressive Disclosure View")
@StyleSheet("ws://usingcomponents/progressivedisclosure.css")
public class ProgressiveDisclosureView extends Composite<FlexLayout> {
    
  private final FlexLayout self = getBoundComponent();
  
  private CheckBox emailNotifications;
  private CheckBox pushNotifications;
  private CheckBox marketingEmails;
  private CheckBox autoSave;
  private Div advancedSettings;
  private Button enableAdvanced;
  private Button saveButton;
  private boolean advancedVisible = false;
  
  public ProgressiveDisclosureView() {
    initializeComponents();
    setupLayout();
    setupHandlers();
  }
  
  private void initializeComponents() {
    emailNotifications = new CheckBox("Email notifications");
    emailNotifications.setValue(true);
      
    pushNotifications = new CheckBox("Push notifications");
    pushNotifications.setValue(false);
      
    marketingEmails = new CheckBox("Marketing emails");
    marketingEmails.setValue(false);
      
    autoSave = new CheckBox("Auto-save changes");
    autoSave.setValue(true);
      
    enableAdvanced = new Button("Show advanced settings");
    enableAdvanced.setTheme(ButtonTheme.OUTLINED_DEFAULT);
      
    advancedSettings = new Div();
    advancedSettings.addClassName("advanced-settings");
    advancedSettings.setStyle("display", "none");
      
    CheckBox debugMode = new CheckBox("Debug mode");
    CheckBox analytics = new CheckBox("Send analytics");
      
    advancedSettings.add(debugMode, analytics);
      
    saveButton = new Button("Save Settings", ButtonTheme.PRIMARY);
    saveButton.setEnabled(false);
  }
  
  private void setupLayout() {
    Paragraph title = new Paragraph("Preferences");
    title.addClassName("settings-title");
    title.setStyle("font-size", "1.5rem");
    title.setStyle("font-weight", "600");
      
    Paragraph sectionTitle = new Paragraph("Notifications");
    sectionTitle.addClassName("section-title");
      
    Paragraph generalTitle = new Paragraph("General");
    generalTitle.addClassName("section-title");
      
    FlexLayout form = new FlexLayout();
    form.setDirection(FlexDirection.COLUMN);
    form.setSpacing("var(--dwc-space-s)");
    form.addClassName("settings-card");
    form.add(
          title,
          sectionTitle,
          emailNotifications,
          pushNotifications,
          marketingEmails,
          generalTitle,
          autoSave,
          enableAdvanced,
          advancedSettings,
          saveButton
    );

    enableAdvanced.setStyle("margin-top", "var(--dwc-space-m)");
    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.addClassName("settings-container");
    self.add(form);
  }
  
  private void setupHandlers() {
    emailNotifications.addValueChangeListener(e -> enableSaveButton());
    pushNotifications.addValueChangeListener(e -> enableSaveButton());
    marketingEmails.addValueChangeListener(e -> enableSaveButton());
    autoSave.addValueChangeListener(e -> enableSaveButton());
      
    enableAdvanced.onClick(event -> {
      advancedVisible = !advancedVisible;
          
      if (advancedVisible) {
        advancedSettings.setStyle("display", "flex");
        enableAdvanced.setText("Hide advanced settings");
      } else {
        advancedSettings.setStyle("display", "none");
        enableAdvanced.setText("Show advanced settings");
        }
      });
      
    saveButton.onClick(e -> {
      Toast.show("Settings saved successfully");
      saveButton.setEnabled(false);
      });
  }
  
  private void enableSaveButton() {
    saveButton.setEnabled(true);
  }
}