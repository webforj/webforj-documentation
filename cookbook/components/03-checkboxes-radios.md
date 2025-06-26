# Checkboxes & Radio Buttons

## Q: How do I work with checkboxes and handle their events?

**A:** Use `CheckBox` with toggle event listeners and proper state management:

```java
import com.webforj.component.optioninput.CheckBox;

public class CheckboxFormView extends Composite<FlexLayout> {
    private CheckBox enableNotifications;
    private CheckBox acceptTerms;
    private CheckBox subscribeNewsletter;
    private Button submitButton;
    
    public CheckboxFormView() {
        setupCheckboxes();
        setupLayout();
        setupEventHandlers();
    }
    
    private void setupCheckboxes() {
        enableNotifications = new CheckBox("Enable notifications")
            .setChecked(true); // Default checked
            
        acceptTerms = new CheckBox("I accept the terms and conditions")
            .setRequired(true);
            
        subscribeNewsletter = new CheckBox("Subscribe to newsletter");
    }
    
    private void setupEventHandlers() {
        // Toggle event for individual checkboxes
        enableNotifications.addToggleListener(event -> {
            boolean isChecked = event.isToggled();
            if (isChecked) {
                Toast.show("Notifications enabled", Theme.SUCCESS);
            } else {
                subscribeNewsletter.setChecked(false); // Disable newsletter if notifications off
            }
        });
        
        acceptTerms.addToggleListener(event -> {
            boolean canSubmit = event.isToggled();
            submitButton.setEnabled(canSubmit);
        });
        
        subscribeNewsletter.addToggleListener(event -> {
            if (event.isToggled() && !enableNotifications.isChecked()) {
                Toast.show("Please enable notifications first", Theme.WARNING);
                subscribeNewsletter.setChecked(false);
            }
        });
    }
}
```

## Q: How do I create indeterminate checkboxes for parent-child relationships?

**A:** Use the `setIndeterminate()` method for parent checkboxes:

```java
public class IndeterminateCheckboxView extends Composite<FlexLayout> {
    private CheckBox parentCheckbox;
    private CheckBox child1;
    private CheckBox child2;
    private CheckBox child3;
    
    public IndeterminateCheckboxView() {
        parentCheckbox = new CheckBox("Select All");
        child1 = new CheckBox("Option 1");
        child2 = new CheckBox("Option 2");
        child3 = new CheckBox("Option 3");
        
        setupLayout();
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        // Parent checkbox controls all children
        parentCheckbox.addToggleListener(event -> {
            boolean checked = event.isToggled();
            child1.setChecked(checked);
            child2.setChecked(checked);
            child3.setChecked(checked);
        });
        
        // Child checkboxes update parent state
        ToggleListener childListener = event -> updateParentState();
        child1.addToggleListener(childListener);
        child2.addToggleListener(childListener);
        child3.addToggleListener(childListener);
    }
    
    private void updateParentState() {
        boolean allChecked = child1.isChecked() && child2.isChecked() && child3.isChecked();
        boolean noneChecked = !child1.isChecked() && !child2.isChecked() && !child3.isChecked();
        
        if (allChecked) {
            parentCheckbox.setChecked(true);
            parentCheckbox.setIndeterminate(false);
        } else if (noneChecked) {
            parentCheckbox.setChecked(false);
            parentCheckbox.setIndeterminate(false);
        } else {
            parentCheckbox.setChecked(false);
            parentCheckbox.setIndeterminate(true);
        }
    }
}
```

## Q: How do I create radio button groups with proper mutual exclusivity?

**A:** Use `RadioButtonGroup` to manage radio button groups:

```java
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;
import com.webforj.component.event.ToggleEvent;

public class RadioButtonFormView extends Composite<FlexLayout> {
    private RadioButton creditCard;
    private RadioButton paypal;
    private RadioButton bankTransfer;
    
    private RadioButton standard;
    private RadioButton express;
    private RadioButton overnight;
    
    public RadioButtonFormView() {
        setupRadioButtons();
        setupEventHandlers();
        setupLayout();
    }
    
    private void setupRadioButtons() {
        // Create payment method buttons
        creditCard = new RadioButton("Credit Card");
        paypal = new RadioButton("PayPal");
        bankTransfer = new RadioButton("Bank Transfer");
        
        // Create payment method group
        RadioButtonGroup paymentGroup = new RadioButtonGroup(creditCard, paypal, bankTransfer);
        
        // Set default selection
        creditCard.setChecked(true);
        
        // Create shipping buttons
        standard = new RadioButton("Standard (5-7 days)");
        express = new RadioButton("Express (2-3 days)");
        overnight = new RadioButton("Overnight");
        
        // Create shipping group
        RadioButtonGroup shippingGroup = new RadioButtonGroup(standard, express, overnight);
        
        // Default selection
        standard.setChecked(true);
    }
    
    private void setupLayout() {
        FlexLayout paymentSection = FlexLayout.create()
            .vertical()
            .spacing("var(--dwc-space-s)")
            .build();
        paymentSection.add(new H3("Payment Method"));
        paymentSection.add(creditCard, paypal, bankTransfer);
        
        FlexLayout shippingSection = FlexLayout.create()
            .vertical()
            .spacing("var(--dwc-space-s)")
            .build();
        shippingSection.add(new H3("Shipping Options"));
        shippingSection.add(standard, express, overnight);
        
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-l)")
            .add(paymentSection, shippingSection);
    }
    
    private void setupEventHandlers() {
        // Payment method selection events
        creditCard.onToggle(e -> {
            if (e.isToggled()) {
                showCreditCardFields();
            }
        });
        
        paypal.onToggle(e -> {
            if (e.isToggled()) {
                showPayPalLogin();
            }
        });
        
        // Shipping selection affects pricing
        overnight.onToggle(e -> {
            if (e.isToggled()) {
                updateShippingCost(25.00);
                Toast.show("Overnight shipping: +$25.00", Theme.INFO);
            }
        });
        
        express.onToggle(e -> {
            if (e.isToggled()) {
                updateShippingCost(10.00);
            }
        });
        
        standard.onToggle(e -> {
            if (e.isToggled()) {
                updateShippingCost(0.00);
            }
        });
    }
    
    // Get selected values
    public String getSelectedPaymentMethod() {
        if (creditCard.isChecked()) return "CREDIT_CARD";
        if (paypal.isChecked()) return "PAYPAL";
        if (bankTransfer.isChecked()) return "BANK_TRANSFER";
        return null;
    }
    
    public String getSelectedShipping() {
        if (overnight.isChecked()) return "OVERNIGHT";
        if (express.isChecked()) return "EXPRESS";
        if (standard.isChecked()) return "STANDARD";
        return null;
    }
}
```

## Q: How do I create radio buttons that look like switches?

**A:** Use the `RadioButton.Switch()` static method:

```java
import com.webforj.component.Expanse;

public class SwitchRadioView extends Composite<FlexLayout> {
    private RadioButton lightTheme;
    private RadioButton darkTheme;
    
    public SwitchRadioView() {
        // Create switch-styled radio buttons
        lightTheme = RadioButton.Switch("Light Theme")
            .setExpanse(Expanse.XLARGE);
            
        darkTheme = RadioButton.Switch("Dark Theme")
            .setExpanse(Expanse.XLARGE);
        
        // Create group - buttons are added to group for mutual exclusivity
        RadioButtonGroup themeGroup = new RadioButtonGroup(lightTheme, darkTheme);
        
        // Set default
        lightTheme.setChecked(true);
        
        // Handle theme changes
        lightTheme.onToggle(e -> {
            if (e.isToggled()) {
                applyLightTheme();
            }
        });
        
        darkTheme.onToggle(e -> {
            if (e.isToggled()) {
                applyDarkTheme();
            }
        });
        
        // Add buttons to layout for display
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-l)")
            .add(lightTheme, darkTheme);
    }
    
    private void applyLightTheme() {
        App.setTheme("dark");
    }
    
    private void applyDarkTheme() {
        App.setTheme("light");
    }
}
```

---

## Navigation

- [← Previous: Form Controls](02-form-controls.md)
- [Next: Dialogs & Feedback →](04-dialogs-feedback.md)
- [Back to Cookbook Index](../00-index.md)