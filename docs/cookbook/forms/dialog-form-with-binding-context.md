---
title: "Show a multi-field form in a Dialog and save it with BindingContext"
description: "Open a Dialog pre-populated with a domain object, bind fields with BindingContext, and persist changes on save."
tags: [forms, data-binding, validation, dialog]
components: [Dialog]
difficulty: intermediate
---

Build the `Dialog` as a `Composite`, create the `BindingContext` once in the constructor, and expose an `open` method that reads the entity into the form and registers a save callback.

```java
public class ContactDialog extends Composite<Dialog> {

  private final Dialog self = getBoundComponent();
  private final TextField nameField = new TextField("Name");
  private final TextField emailField = new TextField("Email");
  private final BindingContext<Contact> bindingContext;
  private Contact contact;
  private Consumer<Contact> onSave;

  public ContactDialog() {
    bindingContext = BindingContext.of(this, Contact.class, true);

    Button save = new Button("Save", ButtonTheme.PRIMARY);
    save.onClick(e -> {
      ValidationResult result = bindingContext.write(contact);
      if (result.isValid()) {
        if (onSave != null) onSave.accept(contact);
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
}
```
