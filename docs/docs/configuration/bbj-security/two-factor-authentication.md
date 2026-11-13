---
title: Two Factor Authentication
sidebar_position: 6
description: Sign in BBj users who have two factor authentication, with a code pop up or with a second route for the code.
---

A user can have two factor authentication in BBjServices. The user then signs in with the password and a code. The library signs such users in. It doesn't enroll users and doesn't turn two factor authentication on or off. BBjServices sets it up per user, in the Enterprise Manager.

BBjServices behaves as follows:

- With the global type `email`, BBjServices mails the code to the user when the password arrives without a code. A user without an email address can't receive a code. The global type is the BBj property `com.basis.security.2fa.type`, and the mail service and the validity of a code are set with the BBj properties `com.basis.security.2fa.emailService` and `com.basis.security.2fa.codeValidity`.
- With the global type `google`, the user takes the code from an authenticator app.
- A user created while two factor authentication is active has it turned on.

The library offers two flows. The pop up flow asks for the code in a dialog while `login` runs. The two step flow ends the first step with the status `TWO_FACTOR_REQUIRED` and completes the sign in on a second route with `submitCode`. Both flows share one limit of 5 refused codes per sign in.

| Flow | Calls | Pending sign in |
|------|-------|-----------------|
| Pop up | `login(..., codeProvider)` | None. The call returns when the sign in is done. |
| Two step | `login(userName, password)` or `login(event)`, then `submitCode(code)` | Kept in the app for `two-factor.pending-seconds`. |

The status of a sign in is as follows:

| Case | Status |
|------|--------|
| The user has no two factor authentication, or BBjServices accepted the code | `AUTHENTICATED` |
| The account requires a code and the sign in ended without one | `TWO_FACTOR_REQUIRED` |
| The provider returned `null` or a blank code | `TWO_FACTOR_REQUIRED` |
| BBjServices refused a code. With the pop up flow, this is the status after the fifth refused code | `TWO_FACTOR_REQUIRED` |
| `submitCode` found no pending sign in, the pending sign in was older than `two-factor.pending-seconds`, or BBjServices refused the fifth code | `CODE_EXPIRED` |

## Ask for the code in a pop up {#ask-for-the-code-in-a-pop-up}

The sign in sends the password first. When BBjServices asks for a code, the library calls the code provider that the app passes to `login`. The provider is a `Function<Boolean, String>`. The `Boolean` is `false` at the first request and `true` when BBjServices refused the previous code. The provider returns the code, or `null` or a blank code to stop.

The library asks the provider again after a refused code. One sign in sends at most 5 codes. This ends when the sign in succeeds, when the provider returns `null` or a blank code, or after the fifth refused code. The user can then start a new sign in, which sends the password again.

The example asks for the code with the input dialog of webforJ. The dialog blocks until the user enters the code or cancels, and returns `null` on cancel.

```java title="SignInView.java"
@Route("/sign-in")
@AnonymousAccess
public class SignInView extends Composite<Div> {
  private final Login login = new Login();

  public SignInView() {
    getBoundComponent().add(login);
    login.onSubmit(event -> BBjSecurity.login(event,
        retry -> OptionDialog.showInputDialog(
            retry ? "The code was wrong. Enter the code again." : "Enter your code.",
            "Two factor authentication")));
    login.whenAttached().thenAccept(c -> login.open());
  }
}
```

The overloads with a code provider keep no pending sign in and never open the code route, also when `two-factor.code-path` is set. The overloads without a provider belong to the two step flow.

The `login(String, String, Function<Boolean, String>)` overload serves custom forms.

```java title="Custom sign in with a code"
BBjLoginStatus status = BBjSecurity.login(userName, password,
    retry -> OptionDialog.showInputDialog("Enter your code.", "Two factor authentication"));
if (status == BBjLoginStatus.TWO_FACTOR_REQUIRED) {
  Toast.show("The sign in needs a code.", Theme.DANGER);
}
```

## Ask for the code on a second route {#ask-for-the-code-on-a-second-route}

The two step flow uses two views. The sign in view takes the password, and the code view takes the code.

1. The sign in view passes the submitted credentials to `BBjSecurity.login`. When the account needs a code, the status is `TWO_FACTOR_REQUIRED` and the app keeps the user name and password for `two-factor.pending-seconds`. They stay in the memory of the app and never in the HTTP session. Under the global type `email`, this password only call is the one that makes BBjServices mail the code.
2. With `two-factor.code-path` set, `login` opens the code route itself. `login(LoginSubmitEvent)` also closes the `Login` component. Without the setting, the sign in view navigates to the code route with the router.
3. The code view calls `BBjSecurity.submitCode(code)`. On `AUTHENTICATED` the app opens the requested page or the default success path, as `login` does. On `TWO_FACTOR_REQUIRED` BBjServices refused the code and the pending sign in stays for another code. On `CODE_EXPIRED` the user signs in again with the password.
4. The code view calls `BBjSecurity.cancelLogin()` when the user goes back to the password.

Set the code route in `webforj.conf`.

```hocon title="webforj.conf"
webforj.security {
  authentication-path = "/sign-in"
  bbj.two-factor.code-path = "/sign-in/code"
}
```

The sign in view needs no code of its own for the second step.

```java title="SignInView.java"
@Route("/sign-in")
@AnonymousAccess
public class SignInView extends Composite<Div> {
  private final Login login = new Login();

  public SignInView() {
    getBoundComponent().add(login);
    login.onSubmit(BBjSecurity::login);
    login.whenAttached().thenAccept(c -> login.open());
  }
}
```

The code route carries `@AnonymousAccess`, because the user has no sign in yet.

```java title="CodeView.java"
@Route("/sign-in/code")
@AnonymousAccess
public class CodeView extends Composite<FlexLayout> {
  private final Label message = new Label();
  private final TextField code = new TextField("Code");
  private final Button submit = new Button("Submit", ButtonTheme.PRIMARY);
  private final Button cancel = new Button("Cancel");

  public CodeView() {
    getBoundComponent().setDirection(FlexDirection.COLUMN);
    getBoundComponent().add(message, code, submit, cancel);
    submit.onClick(e -> handleSubmit());
    cancel.onClick(e -> {
      BBjSecurity.cancelLogin();
      Router.getCurrent().navigate(SignInView.class);
    });
  }

  private void handleSubmit() {
    BBjLoginStatus status = BBjSecurity.submitCode(code.getText());
    if (status == BBjLoginStatus.CODE_EXPIRED) {
      Router.getCurrent().navigate(SignInView.class);
      OptionDialog.showMessageDialog("The code step expired. Sign in again.", "Sign in");
    } else if (status == BBjLoginStatus.TWO_FACTOR_REQUIRED) {
      message.setText("Code refused, try again.");
    } else if (status == BBjLoginStatus.INVALID_CREDENTIALS) {
      message.setText("Wrong user name or password.");
    } else if (status != BBjLoginStatus.AUTHENTICATED) {
      message.setText("Sign in is not available.");
    }
  }
}
```

After `AUTHENTICATED` and after a navigation to the sign in view, the code view is gone. The view must not use its components after that point. The example sets the message only for the statuses that keep the view open.

### Keep the pending sign in {#keep-the-pending-sign-in}

The pending sign in lives in the app instance. The sign in view must reach the code view by router navigation, either through `two-factor.code-path` or through the router. A reload of the page or a redirect from the server between the two steps starts a new app instance without a pending sign in, so `submitCode` returns `CODE_EXPIRED` and the user starts again with the password.

The pending sign in ends after a success, after the fifth refused code, after `two-factor.pending-seconds`, after `cancelLogin()`, after `logout()` and when another `login` starts. A code that BBjServices refuses leaves it in place until then. Under the global type `email`, a refused code sends no new mail, and the BBj setting `com.basis.security.2fa.codeValidity` applies to the mailed code.

A sign in with a code goes through a separate sign in of the administration API of BBjServices, and the token is renewed to `token.minutes`. It's limited by `admin.timeout-seconds` like any other sign in, and it works the same over SSL. See [Remote setup](/docs/configuration/bbj-security/remote-setup).

## Settings {#settings}

| Setting | Default | Description |
|---------|---------|-------------|
| `webforj.security.bbj.two-factor.pending-seconds` | `300` | Seconds that the app keeps a pending sign in for the two step flow. |
| `webforj.security.bbj.two-factor.code-path` | None | Route that opens when the two step flow waits for its code. |

See [Configure the sign in](/docs/configuration/bbj-security/configuration#configure-the-sign-in) for the value ranges.
