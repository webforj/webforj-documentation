---
title: Login
---

<DocChip chip='shadow' />

<DocChip chip='name' label="dwc-login" />

<JavadocLink type="foundation" location="com/webforj/component/login/Login" top='true'/>

The Login component is designed to provide a and user-friendly interface for authentication, allowing users to log in using a username and password. It supports various customizations to enhance user experience across different devices and locales.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usages

The Login component provides a user-friendly login form interface within a dialog for entering authentication credentials. It enhances the user experience by offering:
   >- Clear input fields for username and password.
   >- Visibility toggle for password to verify input.
   >- Input validation feedback to prompt correct format before submission.

## Login submission

When users enter their username and password, the login component validates these inputs as required fields. Once the validation passes, a form submission event is triggered, delivering the entered credentials. To prevent multiple submissions, the `Signin` button is immediately disabled.

The demo below illustrates a basic form submission process. If the username and password are both set to `"admin"` respectively, the login dialog closes, and a logout button appears. If the credentials don't match, the default error message of the login form is displayed.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Disabling the Signin Button
By default, the login form immediately disables the `Signin` button once the component validates the login inputs as correct, to prevent multiple submissions. You can re-enable the `Signin` button using the `setEnabled(true)` method.
:::

:::tip Allowing Empty Passwords
In certain scenarios, empty passwords may be permissible, allowing users to log in with just a username. The login dialog can be configured to accept empty passwords by setting `setEmptyPassword(true)`.
:::

## Internationalization (i18n)

The titles, descriptions, labels, and messages within the login component are fully customizable using the `LoginI18n` class. This flexibility allows you to tailor the login interface to meet specific localization requirements or personalization preferences.

The demo below illustrates how to provide a German translation for the login dialog, ensuring that all interface elements are adapted to the German language to enhance user experience for German-speaking users.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Custom fields

The login component includes [several slots](#slots), which allow you to add extra fields if necessary. This feature provides more control over the information required for successful authentication.

In the example below, a Customer ID field is added to the login form. Users must provide a valid ID to complete authentication, enhancing security and ensuring that access is granted only after verifying all required credentials.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Submission Payload
Note that the login component doesn't automatically recognize or include extra fields added to the form in its submission payload. This means developers must explicitly retrieve the value of any additional fields from the client side and handle it according to the app's requirements to complete the authentication process.
:::

## Cancel button

In certain scenarios, it may be desirable to add a cancel button alongside the `Signin` button. This feature is useful particularly when a user attempts to access a restricted area of the app and needs an option to cancel the action and return to their previous location. The login form includes a cancel button by default, but it's hidden from view.

To make the cancel button visible, you must provide a label for it - once labeled, it will appear on the screen. You can also listen to cancel events to respond appropriately to user actions, ensuring a smooth and user-friendly experience for navigating the app.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Hiding Elements
To hide an element from the login screen, simply set its label to an empty string. This approach is particularly useful for temporarily removing interface components without permanently altering the codebase.
:::

## Password managers

The login component is designed to be compatible with browser-based password managers, enhancing the user experience by simplifying the login process. For users of Chromium-based browsers, the component integrates seamlessly with the [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API. This integration enables several convenient features:

- **Auto-fill**: The browser may automatically fill out the username and password fields if the user has saved credentials for the site.
- **Credential Management**: After logging in, the browser can prompt the user to save new credentials, making future logins faster and easier.
- **Credential Selection**: If multiple credentials are saved, the browser can offer a choice to the user to select from one of the saved sets.

## Styling

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Login} table='parts'/>

### Slots

Listed below are the slots available for utilization within the `Login` component. These slots act as placeholders within the component that control where the children of a customized element should be inserted within the shadow tree.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Login} table='slots' />

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Login} table="reflects" />