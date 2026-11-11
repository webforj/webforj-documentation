---
title: "Spring Boot login page: a Java UI with webforJ Login"
description: "Build a Spring Boot login page as a Java UI with webforJ's Login component. Handle submit, add custom fields, wire a cancel button, post to an action URL."
slug: login-form-java-web
date: 2026-06-11
authors: Eric Handtke
tags: [components, security, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-11-login-form-java-web/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-11-login-form-java-web/cover.jpg)

Almost every business app starts with the same screen: a username, a password, and a Sign in button. Building it by hand means a dialog, two required fields, an error message and a button that shouldn't be clicked twice, before you've checked a single credential.

A Spring Boot login page with a Java UI takes one component in webforJ. `Login` brings the dialog, the fields, the checks and the error message, so your code only has to decide whether the credentials are right.

<!-- truncate -->

:::tip Try it live
The [Login documentation](/docs/components/login) has live, interactive samples you can sign in to in the browser, with the full Java source next to each one.
:::

This post covers the basic setup, a custom field, a cancel button and the action URL option, using the samples from the docs.

## What the Login component gives you {#what-login-gives-you}

A hand-built login form needs a dialog, two fields, validation, a button state, and an error message. The `Login` component ships all of them. The parts are:

- Username and password fields, both required.
- A Sign in button that disables itself after a valid submit, to prevent double submissions.
- An error message you turn on from code.
- Password manager support in Chromium-based browsers through the `PasswordCredential` API.

You don't build any of this yourself. Your job is the credential check and what happens after it. Each part is set from Java, so the form's behavior lives in the same class as the rest of your view. That keeps the login flow easy to find when you change it later.

## Create a login dialog {#create-a-login-dialog}

1. Create a `Login` object.
2. Add a submit listener.
3. Call `open()`.
4. Add the component to the view.

This is the submission sample from the docs, inside a view whose bound component is `self`:

```java
Login login = new Login();
login.onSubmit(
    ev -> {
      String username = ev.getUsername();
      String password = ev.getPassword();
      // Validate credentials (admin/admin)
      if (username.equals("admin") && password.equals("admin")) {
        login.close();
        self.add(new Button("Logout", e -> Page.getCurrent().reload()));
      } else {
        login.setError(true).setEnabled(true);
      }
    });
login.open();
self.add(login);
```

`open()` shows the dialog over the view. The hard-coded admin/admin pair is only there to make the sample run. Replace it with a call to your user service before you ship.

## Handle the submit event {#handle-the-submit-event}

The submit listener receives a `LoginSubmitEvent`. Call `getUsername()` and `getPassword()` on it. Then there are two outcomes:

- Valid credentials: call `close()`. The dialog goes away.
- Invalid credentials: call `setError(true)` to show the error message, then `setEnabled(true)`.

The second call matters. The component disables the Sign in button after validation, so without `setEnabled(true)` the user can't try again.

Keep the check out of the view in production. A string comparison works for a demo. It doesn't cover locked accounts, password resets, or failed-attempt limits. Put that logic in a service, call it from the listener, and let the view decide only what to show.

## Build a custom login UI in Java {#build-a-custom-login-ui}

Many apps need one more field. A multi-tenant app might ask for a customer ID, and a shared portal might ask for a department code. Add the field with `addToBeforeForm`, give it a name with `setName`, and read it from `getData()` in the submit listener. This is the core of the docs' custom fields sample, where `customerId` is a `TextField` labeled Customer ID:

```java
customerId.setName("customer-id").setRequired(true);

Login login = new Login();
login.addToBeforeForm(customerId);

login.onSubmit(
    ev -> {
      String id = (String) ev.getData().get("customer-id");
      String username = ev.getUsername();
      String password = ev.getPassword();
      // Validate credentials: admin/admin with customer ID "Tesla"
      if (username.equals("admin") && password.equals("admin") && id.equals("Tesla")) {
        login.close();
        self.add(new Button("Logout", e -> Page.getCurrent().reload()));
      } else {
        login.setError(true).setEnabled(true);
        customerId.focus();
      }
    });
```

To show the Cancel button, give it a label through `LoginI18n` and listen for the cancel event, as in the docs' cancel sample:

```java
LoginI18n i18n = new LoginI18n();
i18n.setCancel("Cancel");
login.setI18n(i18n);
login.onCancel(
    ev -> {
      // Close the login dialog and do something else
    });
```

A few details to keep in mind:

- A custom field only reaches the submit event when it has a name. Without `setName()` the value is dropped.
- `getData()` returns a map keyed by field name. Cast the value to the type your field returns.
- The Cancel button is hidden by default. Setting a label through `LoginI18n` makes it visible. The `onCancel` listener runs when the user clicks it.

## Post to an action URL {#post-to-an-action-url}

Since 25.10, `setAction` sends the form as a standard POST request instead of firing the submit event. The browser sends `username` and `password` as form parameters, and named custom fields are included too.

```java
Login login = new Login();
login.setAction("/api/auth");
```

The submit event doesn't fire in this mode, so your Java listener never receives the password. The server endpoint takes it from there, which suits teams that already run login in a separate service.

Use this option when a server endpoint handles the POST. When your Java code checks the credentials, leave `setAction` out and use the submit listener from the first example.

:::tip See it in a full app
The [Bookstore](https://github.com/webforj/built-with-webforj/tree/main/webforj-bookstore) demo uses this pattern for its sign-in page. Its `Login` posts to `/signin` with `setAction`, and Spring Security checks the credentials and handles the roles. View the code on GitHub and run it with the test logins from its README.
:::

## Allow a username-only sign in {#username-only}

Some internal tools identify users by name alone, for example a kiosk that reads a badge ID. Call `setEmptyPassword(true)` and the password field no longer blocks the submit. Your check must then decide whether an empty password is acceptable. Don't enable it for a public page.

## Translate the labels {#translate-labels}

Titles, descriptions, labels, and messages come from `LoginI18n`. The cancel example already uses it. Set the same object with your own wording for another language, and the dialog follows. Keep one `LoginI18n` per locale in a small helper so the views stay short.

## Checklist for a login form {#checklist}

- [ ] Check credentials against a user service, not a hard-coded pair.
- [ ] Call `setEnabled(true)` after every failed check.
- [ ] Give each custom field a name with `setName()`.
- [ ] Set a cancel label only when the user has a page to return to.
- [ ] Enable `setEmptyPassword(true)` only for trusted, internal screens.
- [ ] Use `setAction()` only when an endpoint receives the POST.
- [ ] Protect the routes behind the login with the security rules.

## Next step {#next-step}

Start with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started). Add one of the views from this post to your routes and open it in the browser. The [Login component reference](/docs/components/login) lists every method on the class.

## Frequently asked questions {#faq}

### How do I build a Spring Boot login page with a Java UI? {#spring-boot}

Call a Spring service from the submit listener. webforJ views can autowire Spring beans, so the listener can hand the credentials to a service that checks them. The [Spring overview](/docs/integrations/spring/overview) explains the setup.

### Does the Login component authenticate users? {#does-login-authenticate}

No. The component collects the credentials and fires a submit event, or posts them when `setAction()` is set. Your code decides whether they're valid. For access rules on routes, read the [security overview](/docs/security/overview).
