---
title: Sign In and Sign Out
sidebar_position: 4
description: Sign users in and out with BBjSecurity, handle the Login component, and read the current user.
---

`BBjSecurity` signs users in against the administration API of BBjServices and signs them out again. This page describes the `login` and `logout` methods, the login status, and the current user.

## Sign in users {#sign-in-users}

`BBjSecurity` is the entry point for the sign in. It offers four `login` methods and one `logout` method.

| Method | Description |
|--------|-------------|
| `login(String userName, String password)` | Signs the user in and returns a `BBjLoginStatus`. Both arguments must not be `null`. |
| `login(LoginSubmitEvent event)` | Handles a submit of the `Login` component and returns the `BBjLoginStatus`. |
| `login(String userName, String password, Function<Boolean, String> codeProvider)` | Signs the user in and supplies the two factor code through the provider. See [Two factor authentication](/docs/configuration/bbj-security/two-factor-authentication). |
| `login(LoginSubmitEvent event, Function<Boolean, String> codeProvider)` | Handles a submit of the `Login` component and supplies the two factor code through the provider. |
| `logout()` | Signs the current user out and returns nothing. See [Sign out users](#sign-out-users). |

Every `login` method returns one of four `BBjLoginStatus` values.

| Status | Meaning |
|--------|---------|
| `AUTHENTICATED` | The administration API of BBjServices accepted the user name and password. |
| `INVALID_CREDENTIALS` | BBjServices refused the credentials, for example an unknown user name, a wrong password or a user name longer than 64 characters, which BBjServices never signs in. |
| `UNAVAILABLE` | The sign in couldn't be completed. The administration API of BBjServices couldn't be reached, didn't answer within `admin.timeout-seconds`, or the app couldn't keep the sign in in the HTTP session, for example because BBjServices provides no session accessor. See [Keep users signed in after a reload](/docs/configuration/bbj-security/sessions#keep-users-signed-in-after-a-reload). The status is also `UNAVAILABLE` when route security is disabled. |
| `TWO_FACTOR_REQUIRED` | The account requires a two factor code and the sign in ended without one, because no provider was given or the provider returned `null` or a blank code. See [Two factor authentication](/docs/configuration/bbj-security/two-factor-authentication). |

The methods return when the sign in has finished. When the administration API of BBjServices causes an `UNAVAILABLE` result, the app logs a warning that carries the failure detail, as in `The BBj Admin API could not complete the sign in: <detail>`.

## Handle the login component {#handle-the-login-component}

`login(LoginSubmitEvent)` works with the `Login` component of the event and does the following:

- Clears the error of the component before the sign in starts.
- Sets the error of the component when the status is `INVALID_CREDENTIALS`.
- Shows a warning toast when the status is `UNAVAILABLE`.
- Shows no error and no toast when the status is `TWO_FACTOR_REQUIRED`.
- Closes the component when the status is `AUTHENTICATED`. In every other case the form is enabled again.

A listener that passes `BBjSecurity::login` to `onSubmit` is all a login view needs. The overload with a code provider handles the component in the same way. See [Two factor authentication](/docs/configuration/bbj-security/two-factor-authentication).

## Open the target page {#open-the-target-page}

After a successful sign in, the app navigates to the page that the visitor requested before being redirected, when `return-after-login` is `true` and a page was requested. Otherwise it navigates to `default-success-path`. The requested page is consumed in both cases, so a later sign in doesn't return to it.

## Read the current user {#read-the-current-user}

`BBjSecurity.currentUser()` returns an `Optional<BBjPrincipal>`, empty until a user has signed in. After a page reload of a signed in tab, the user is known again at once. The principal gives the user name through `getUserName()`.

```java title="WelcomeView.java"
@Route("/welcome")
@PermitAll
public class WelcomeView extends Composite<Div> {

  public WelcomeView() {
    String userName = BBjSecurity.currentUser()
        .map(BBjPrincipal::getUserName)
        .orElse("guest");
    getBoundComponent().add(new Paragraph("Signed in as " + userName));
  }
}
```

The `login(String, String)` method serves custom forms and reacts to the status in the same way.

```java title="Custom sign in"
BBjLoginStatus status = BBjSecurity.login(userName, password);
if (status == BBjLoginStatus.INVALID_CREDENTIALS) {
  Toast.show("Wrong user name or password.", Theme.DANGER);
}
```

## Sign out users {#sign-out-users}

`BBjSecurity.logout()` returns nothing. When nobody is signed in, nothing happens.

Otherwise the call does the following:

- Clears the current user at once.
- Removes the sign in from the HTTP session. The other tabs of the HTTP session are signed out at their next navigation. See [Keep users signed in after a reload](/docs/configuration/bbj-security/sessions#keep-users-signed-in-after-a-reload).
- Forgets the requested location.
- Navigates to the logout location. The location is `logout-success-path`, or the authentication path when the setting is missing, or `/login` when the authentication path is missing too.
- Ends the login token by shortening its lifetime to one minute, so the token expires shortly after the sign out.

When ending the token fails, the failure is logged and the token expires on its own.

```java title="LogoutButton.java"
Button logout = new Button("Sign out");
logout.onClick(e -> BBjSecurity.logout());
```
