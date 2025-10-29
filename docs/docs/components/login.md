---
title: Login
sidebar_position: 70
sidebar_class_name: updated-content
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

The `Login` component simplifies user authentication by providing a ready-to-use login dialog with username and password fields. It includes features like input validation, customizable labels and messages, password visibility controls, and support for additional custom fields.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Login submission {#login-submission}

When users enter their username and password, the `Login` component validates these inputs as required fields. Once the validation passes, a form submission event is triggered, delivering the entered credentials. To prevent multiple submissions, the [Sign in] button is immediately disabled.

The following illustrates a basic `Login` component. If the username and password are both set to `"admin"` respectively, the login dialog closes, and a [Logout] button appears. If the credentials don't match, the default error message is displayed.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Disabling the [Sign in] Button
By default, `Login` immediately disables the [Sign in] button once the component validates the login inputs as correct, to prevent multiple submissions. You can re-enable the [Sign in] button using the `setEnabled(true)` method.
:::

:::tip Allowing Empty Passwords
You can allow users to log in with only a username by using the `setEmptyPassword(true)` method.
:::

## Form action <DocChip chip='since' label='25.10' />{#form-action}

The `Login` component can submit form data directly to a specified URL instead of handling the submission through the submit event. When an action URL is set, the form performs a standard POST request with the username and password as form parameters.

```java
Login login = new Login();
login.setAction("/api/auth");
```

When using `setAction()`, the form submission bypasses the `LoginSubmitEvent` and instead performs a traditional HTTP POST request to the specified endpoint. The username and password are sent as form parameters named "username" and "password", respectively. Custom fields with a name attribute are also included in the POST request.

:::tip 
If no action URL is set, form submission is handled through the `LoginSubmitEvent`, allowing you to process credentials programmatically on the server side.
:::

## Internationalization (i18n) {#internationalization-i18n}

The titles, descriptions, labels, and messages within the `Login` component are fully customizable using the `LoginI18n` class. This flexibility allows you to tailor the login interface to meet specific localization requirements or personalization preferences.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Custom fields {#custom-fields}

The `Login` component includes several slots that allow you to add extra fields as needed. Custom fields are automatically collected when the form is submitted and can be accessed through the submit event's data map.

The following login has a custom field added for a customer ID. This can help you manage companies or departments with shared content across multiple users.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Name Required
Custom fields must have a name set using `setName()` to be included in the form submission. The name is used as the key to retrieve the field's value from `event.getData()`.
:::

## Cancel button {#cancel-button}

`Login` includes a [Cancel] button that's hidden by default. This is particularly useful when a user attempts to access a restricted area of the app and needs an option to return to their previous location without completing the login.

To make the cancel button visible, provide a label for it. You can also listen to cancel events to handle the cancellation appropriately.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Hiding Elements
To hide an element, set its label to an empty string. This lets you toggle visibility without removing the component from your code.
:::

## Password managers {#password-managers}

This component works with browser-based password managers to simplify the login process. On Chromium-based browsers, it integrates with the [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API, which provides:

- **Auto-fill**: The browser may automatically fill out the username and password fields if the user has saved credentials for the site.
- **Credential Management**: After logging in, the browser can prompt the user to save new credentials, making future logins faster and easier.
- **Credential Selection**: If multiple credentials are saved, the browser can offer a choice to the user to select from one of the saved sets.

## Styling {#styling}

<TableBuilder name="Login" />