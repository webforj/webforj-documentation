---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

The `PasswordField` component allows users to input a password securely. It's displayed as a single-line text editor where the entered text is obscured, typically replaced with symbols like asterisks (”*”) or dots (”•”). The exact symbol may vary based on the browser and operating system.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Field value

The `PasswordField` component stores and retrieves its value as a plain `String`, similar to a `TextField`, but with obscured visual rendering to hide the characters from view.

You can retrieve the current value using:

```java
String password = passwordField.getValue();
```

To set or reset the value programmatically:

```java
passwordField.setValue("MySecret123!");
```

If no value has been entered by the user and no default is set, the field will return an empty string (`""`).

This behavior mimics that of the native HTML `<input type="password">`, where the `.value` property holds the current input.


:::tip
Although the field visually masks the content, the returned value from `getValue()` is still stored as a plain string. Be mindful of this when handling sensitive data.
:::


## Usages

The `PasswordField` is best used in scenarios where capturing or handling sensitive information, such as passwords or other confidential data, is essential to your app. Here are some examples of when to use the `PasswordField`:

1. **User Authentication and Registration**: Password fields are crucial in apps that involve user authentication or registration processes, where secure password input is required.

2. **Secure Form Inputs**: When designing forms that require input of sensitive information, such as credit card details or personal identification numbers (PINs), using a `PasswordField` ensures the secure entry of such data.

3. **Account Management and Profile Settings**: Password fields are valuable in apps that involve account management or profile settings, allowing users to change or update their passwords securely.

## Password visibility

Users can reveal the value of the `PasswordField` by clicking on the reveal icon. This allows users to verify what they’ve entered, or copy the information to their clipboard. However, for high-security environments, you can use `setPasswordReveal()` to remove the reveal icon and prevent users from seeing the value. You can verify if a user can use the reveal icon to show the value with the `isPasswordReveal()` method.

## Placeholder text

You can set placeholder text for the `PasswordField` using the `setPlaceholder()` method. The placeholder text is displayed when the field is empty, helping to prompt the user to enter appropriate input into the `PasswordField`.

## Pattern matching

You can apply a regular expression pattern to the `PasswordField` using the `setPattern()` method. This is especially useful when enforcing strong password rules, such as requiring a mix of uppercase and lowercase letters, numbers, and symbols.

The pattern must follow the syntax of a [JavaScript regular expression](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), as interpreted by the browser. The `u` (Unicode) flag is used internally to ensure accurate validation across all Unicode code points. Do **not** include forward slashes (`/`) around the pattern.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

In this example, the pattern requires at least one lowercase letter, one uppercase letter, one number, and a minimum length of 8 characters.

If the pattern is missing or invalid, no validation will be applied.

:::tip
You can provide a tooltip with `setTitle()` to help users understand the password requirements. Consider also placing helper text nearby to visually communicate what makes a valid password.
:::

:::note
Using a pattern is strongly recommended for password fields to ensure users create secure and compliant credentials. Patterns allow you to enforce character rules and structural requirements. See the [Validation](#best-practices) section for more context.
:::

## Minimum and maximum length

You can control the allowed length of the password input by using `setMinLength()` and `setMaxLength()` on the `PasswordField`.

The `setMinLength()` method defines the minimum number of characters a user must enter in the field to pass validation. This value must be a non-negative integer and should not exceed the maximum length if one is set.

```java
passwordField.setMinLength(8); // Minimum 8 characters
```

If the user enters fewer characters than the minimum, the input fails constraint validation. This validation is only applied when the field's value is modified by the user.

The `setMaxLength()` method sets the maximum number of characters allowed in the field. The value must be 0 or greater. If it's not defined or is set to an invalid value, the field has no upper character limit.

```java
passwordField.setMaxLength(20); // Maximum 20 characters
```

If the input exceeds the maximum character limit, the field fails constraint validation. Like the minimum, this rule only applies when the user updates the field’s value.

:::tip
Use both `setMinLength()` and `setMaxLength()` together to create effective input boundaries. See the [HTML length constraints documentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) for more reference.
:::


## Best practices

As the `PasswordField` component is often associated with sensitive information, consider the following best practices when using the `PasswordField`:

- **Provide Password Strength Feedback**: Incorporate password strength indicators or feedback mechanisms to help users create strong and secure passwords. Evaluate factors such as length, complexity, and a mix of uppercase and lowercase letters, numbers, and special characters.

- **Ensure Secure Password Storage**: Never store passwords in plain text. Instead, implement proper security measures to handle and store passwords securely in your app. Utilize industry-standard encryption algorithms for passwords and other sensitive data.

- **Password Confirmation**: Include an additional confirmation field when a user changes or creates a password. This measure helps minimize the likelihood of typos and ensures that users accurately enter their desired password.

- **Allow Password Reset**: If your app involves user accounts, provide an option for users to reset their password. This could be in the form of a "Forgot Password" feature that initiates a password recovery process.

- **Accessibility**: Utilize the `PasswordField` with accessibility in mind, ensuring it meets accessibility standards such as providing proper labels, and compatibility with assistive technologies.