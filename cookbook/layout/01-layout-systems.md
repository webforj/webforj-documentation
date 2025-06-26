# Layout Systems

## Q: How do I create responsive layouts using FlexLayout?

**A:** Use `FlexLayout` with direction, spacing, and alignment properties:

```java
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexAlignment;

public class ResponsiveForm extends Composite<FlexLayout> {
    
    public ResponsiveForm() {
        // Main container
        FlexLayout container = getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-l)")
            .setAlignment(FlexAlignment.CENTER)
            .setMargin("var(--dwc-space-l)");

        // Responsive form row
        FlexLayout formRow = FlexLayout.create(firstNameField, lastNameField)
            .horizontal()
            .spacing("var(--dwc-space-m)")
            .build()
            .setWidth("100%");

        // Button row
        FlexLayout buttonRow = FlexLayout.create(cancelButton, submitButton)
            .horizontal()
            .justify().end()
            .spacing("var(--dwc-space-s)")
            .build();

        container.add(formRow, emailField, buttonRow);
    }
}
```

## Q: How do I use the FlexLayout builder pattern effectively?

**A:** Use the fluent builder API for complex layouts:

```java
// Using the builder pattern
FlexLayout mainLayout = FlexLayout.create()
    .vertical()
    .spacing("var(--dwc-space-l)")
    .align().center()
    .build();

// Row with specific justification
FlexLayout rowOne = FlexLayout.create(email, password)
    .horizontal()
    .wrap()
    .justify().between()
    .build();

// Address row with custom item sizing
FlexLayout cityStateZipRow = FlexLayout.create(city, states, zip)
    .horizontal()
    .justify().between()
    .build();

cityStateZipRow.setItemBasis("40%", city);
cityStateZipRow.setItemBasis("20%", states);
cityStateZipRow.setItemBasis("40%", zip);

mainLayout.add(rowOne, cityStateZipRow);
```

## Q: How do I create grid-like layouts using ColumnsLayout?

**A:** Use `ColumnsLayout` for column-based layouts:

```java
import com.webforj.component.layout.columnslayout.ColumnsLayout;

public class FormWithColumnsLayout extends Composite<Div> {
    
    public FormWithColumnsLayout() {
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField email = new TextField("Email");
        PasswordField password = new PasswordField("Password");
        Button submit = new Button("Submit", ButtonTheme.PRIMARY);
        Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY);
        
        ColumnsLayout layout = new ColumnsLayout(
            firstName, lastName,
            email,
            password,
            cancel, submit
        );
        
        getBoundComponent().add(layout);
    }
}
```

## Q: How do I control flex item ordering and alignment?

**A:** Use FlexLayout item-specific methods:

```java
FlexLayout buttonContainer = FlexLayout.create()
    .horizontal()
    .wrap()
    .build();

// Add buttons with specific ordering
for (int i = 1; i <= 5; i++) {
    Button newButton = new Button("Order: " + i, ButtonTheme.PRIMARY);
    buttonContainer.add(newButton);
    buttonContainer.setItemOrder(i, newButton);
}

// Set specific alignment for one item
Button specialButton = new Button("Align Me!", ButtonTheme.DANGER);
buttonContainer.add(specialButton);
buttonContainer.setItemAlignment(FlexAlignment.CENTER, specialButton);

// Set item basis (flex-basis)
buttonContainer.setItemBasis("30%", specialButton);
```

---

## Navigation

- [← Previous: Trees](../components/06-trees.md)
- [Next: Data Binding →](../data-handling/01-data-binding.md)
- [Back to Cookbook Index](../00-index.md)