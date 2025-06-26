# Form Controls & Fields

## Q: How do I create and validate text fields with proper error handling?

**A:** Use `TextField` with validation events and helper text:

```java
import com.webforj.component.field.TextField;

TextField nameField = new TextField("Name")
    .setPlaceholder("Enter your name")
    .setRequired(true)
    .setInvalidMessage("Name cannot be empty");

nameField.onBlur(event -> {
    String value = event.getText();
    nameField.setInvalid(value.isEmpty());
});
```

## Q: How do I create numeric input fields with validation?

**A:** Use `NumberField` with min/max constraints and value change listeners:

```java
import com.webforj.component.field.NumberField;

NumberField ageField = new NumberField("Age")
    .setMin(0)
    .setMax(150)
    .setStep(1)
    .setValue(25.0)
    .setInvalidMessage("Age is required");

ageField.addValueChangeListener(event -> {
    Double value = event.getValue();
    if (value != null && value > 65) {
        // Handle senior citizen logic
        applyDiscounts();
    }
});

// Validation on blur
ageField.onBlur(event -> {
    String text = event.getText();
    ageField.setInvalid(text.isEmpty());
});
```

## Q: How do I create password fields with strength validation?

**A:** Use `PasswordField` with custom validation logic:

```java
import com.webforj.component.field.PasswordField;

PasswordField passwordField = new PasswordField("Password")
    .setPlaceholder("Enter your password")
    .setRequired(true)
    .setMinLength(8)
    .setInvalidMessage("Password must be at least 8 characters");

passwordField.onBlur(event -> {
    String password = event.getText();
    passwordField.setInvalid(password.length() < 8);
    
    // Optional: Additional feedback for password strength
    if (password.length() >= 8 && !isStrongPassword(password)) {
        Toast.show("Consider using numbers and special characters", Theme.INFO);
    }
});

private boolean isStrongPassword(String password) {
    return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
}
```

## Q: How do I create dropdown lists and handle selections?

**A:** Use `ChoiceBox` for single selection and `ComboBox` for searchable options:

```java
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.ComboBox;

// ChoiceBox
ChoiceBox<String> countryBox = new ChoiceBox<>("Country");
countryBox.insert("USA", "Canada", "Mexico", "UK", "Germany");
countryBox.selectIndex(0); // Select first item

countryBox.onSelectionChange(event -> {
    String selected = event.getSelectedItem().getText();
    System.out.println("Selected: " + selected);
    updateStatesList(selected);
});

// ComboBox
ComboBox<String> stateBox = new ComboBox<>("State");
stateBox.setMaxRowCount(7); // Limit visible rows

// Populate based on country selection
private void updateStatesList(String country) {
    stateBox.removeAll();
    if ("USA".equals(country)) {
        stateBox.insert("California", "Texas", "New York", "Florida");
    } else if ("Canada".equals(country)) {
        stateBox.insert("Ontario", "Quebec", "British Columbia");
    }
}
```

## Q: How do I create date fields with proper validation?

**A:** Use `DateField` with min/max constraints and value change listeners:

```java
import com.webforj.component.field.DateField;
import java.time.LocalDate;

DateField birthDate = new DateField("Birth Date")
    .setMax(LocalDate.now())
    .setMin(LocalDate.now().minusYears(100))
    .setValue(LocalDate.now().minusYears(30))
    .setInvalidMessage("Please enter a valid birth date");

birthDate.addValueChangeListener(event -> {
    LocalDate date = event.getValue();
    if (date != null) {
        int age = LocalDate.now().getYear() - date.getYear();
        ageField.setValue((double) age);
        
        // Validate reasonable age
        birthDate.setInvalid(age < 0 || age > 150);
    }
});
```

---

## Navigation

- [← Previous: Component Basics](01-component-basics.md)
- [Next: Checkboxes & Radio Buttons →](03-checkboxes-radios.md)
- [Back to Cookbook Index](../00-index.md)