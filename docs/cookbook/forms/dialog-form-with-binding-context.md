---
title: "Show a multi-field form in a Dialog and save it with BindingContext"
description: "Open a Dialog pre-populated with a domain object, bind fields with BindingContext, and persist changes on save."
tags: [forms, data-binding, validation, dialog]
components: [Dialog]
difficulty: intermediate
---

Build the `Dialog` as a `Composite`, create the `BindingContext` once in the constructor, and expose an `open` method that reads the entity into the form and registers a save callback. The `@UseProperty` annotations connect the field names to the `Contact` bean properties.

```java
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H2;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.binding.annotation.UseProperty;
import com.webforj.data.validation.server.ValidationResult;
import java.util.function.Consumer;

public class ContactDialog extends Composite<Dialog> {

  private final Dialog self = getBoundComponent();

  @UseProperty("name")
  private final TextField nameField = new TextField("Name");

  @UseProperty("email")
  private final TextField emailField = new TextField("Email");

  private final BindingContext<Contact> bindingContext;
  private Contact contact;
  private Consumer<Contact> onSave;

  public ContactDialog() {
    bindingContext = BindingContext.of(this, Contact.class, false);

    Button save = new Button("Save", ButtonTheme.PRIMARY);
    save.onClick(e -> {
      ValidationResult result = bindingContext.write(contact);
      if (result.isValid()) {
        if (onSave != null) {
          onSave.accept(contact);
        }
        self.close();
      }
    });

    Button cancel = new Button("Cancel");
    cancel.onClick(e -> self.close());

    self.addToHeader(new H2("Edit contact"));
    self.add(nameField, emailField, save, cancel);
  }

  public void open(Contact contact, Consumer<Contact> onSave) {
    this.contact = contact;
    this.onSave = onSave;
    bindingContext.read(contact);
    self.open();
  }

  public static class Contact {
    private String name;
    private String email;

    public Contact(String name, String email) {
      this.name = name;
      this.email = email;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }
}
```
