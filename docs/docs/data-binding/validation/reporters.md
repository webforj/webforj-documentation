---
sidebar_position: 3
title: Reporters
---

<!-- vale off -->

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<!-- vale on -->

Validation reporters are used for providing feedback about the validation process to the user interface. This feature is essential for informing users about the results of their input validation, particularly in complex forms or data-intensive applications.

## What's a validation reporter?

A validation reporter is a component that processes and displays the outcomes of validations to users. It acts as a bridge between the validation logic and the user interface, ensuring that validation results are communicated effectively and clearly.

:::tip Core Components Default Reporter
webforJ includes the `DefaultBindingReporter`, a default bindings reporter designed to work seamlessly with all core webforJ components. This built-in reporter automatically displays validation errors, eliminating the need for custom implementation in many cases. Depending on the component's configuration, the `DefaultBindingReporter` displays validation errors directly as a popover or inline, right beneath the component. This feature simplifies the error reporting process significantly, ensuring clear and direct communication of validation errors, and enhances the user experience by providing immediate, context-sensitive feedback on input validation.
:::

## Configuring validation reporters

You can configure validation reporters within the binding context to customize how messages are presented. Typically, you would implement a validation reporter to aggregate validation results and then display them in a user-friendly manner, such as highlighting incorrect fields, displaying error messages, or updating status indicators.

Here’s an example of how to set up a validation reporter for a field

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("Email Address");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Custom message for invalid email address"))
        .useReporter((validationResult, binding) -> {
          errors.setVisible(!validationResult.isValid());

          if (!validationResult.isValid()) {
            errors.setText(validationResult.getMessages().stream().findFirst().orElse(""));
          }
        })
        .add();

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="User" label="User.java">

```java showLineNumbers
public class User {
  private String name;
  private String email;
  private String password;

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

  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
}
```

</TabItem>
<TabItem value="styles" label="styles.css">

```css showLineNumbers
.error {
  border: 1px solid #f1aeb5;
  border-radius: 5px;
  background-color: #f8d7da;
  color: #58151c;
  padding: 5px;
}

.form {
  margin: 20px auto;
  max-width: 400px;
}
```

</TabItem>
</Tabs>

In the above code, the email binding incorporates a custom reporter that directly displays validation messages beneath the input field. This setup utilizes the `useReporter` method, which configures how the binding handles and presents validation results. This method effectively links the validation logic to the user interface, ensuring that any validation issues are immediately visible to the user, enhancing the form's interactivity and user experience.
