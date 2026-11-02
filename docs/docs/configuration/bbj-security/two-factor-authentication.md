---
title: Two Factor Authentication
sidebar_position: 6
description: Sign in BBj users who have two factor authentication with a code provider passed to BBjSecurity.login.
---

A user can have two factor authentication in BBjServices. The user then signs in with the password and a code. The library signs such users in. It doesn't enroll users and doesn't turn two factor authentication on or off. BBjServices sets it up per user, in the Enterprise Manager.

BBjServices behaves as follows:

- With the global type `email`, BBjServices mails the code to the user when the password arrives without a code. A user without an email address can't receive a code.
- With the global type `google`, the user takes the code from an authenticator app.
- A user created while two factor authentication is active has it turned on.

The sign in sends the password first. When BBjServices asks for a code, the library calls the code provider that the app passes to `login`. The provider is a `Function<Boolean, String>`. The `Boolean` is `false` at the first request and `true` when BBjServices refused the previous code. The provider returns the code, or `null` or a blank code to stop.

The library asks the provider again after a refused code. One sign in sends at most 5 codes. This ends when the sign in succeeds, when the provider returns `null` or a blank code, or after the fifth refused code.

The example asks for the code with the input dialog of webforJ. The dialog blocks until the user enters the code or cancels, and returns `null` on cancel.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Div> {
  private final Login login = new Login();

  public LoginView() {
    getBoundComponent().add(login);
    login.onSubmit(event -> BBjSecurity.login(event,
        retry -> OptionDialog.showInputDialog(
            retry ? "The code was wrong. Enter the code again." : "Enter your code.",
            "Two factor authentication")));
    login.whenAttached().thenAccept(c -> login.open());
  }
}
```

The status of the sign in is as follows:

| Case | Status |
|------|--------|
| The user has no two factor authentication, or BBjServices accepted the code | `AUTHENTICATED` |
| The provider returned `null` or a blank code | `TWO_FACTOR_REQUIRED` |
| The account requires a code and `login` has no provider | `TWO_FACTOR_REQUIRED` |
| BBjServices refused the fifth code of one sign in | `TWO_FACTOR_REQUIRED` |

A refused code doesn't end the sign in before the fifth one. The library asks the provider again with `true`. After the fifth refused code the sign in stops. The user can start a new sign in, which sends the password again.

The overloads without a provider behave as a provider that returns `null`. A user with two factor authentication who signs in through them ends with `TWO_FACTOR_REQUIRED`.

The first attempt sends the password alone. Under the global type `email`, this is the attempt that makes BBjServices mail the code. A sign in with a code goes through a separate sign in of the administration API of BBjServices, and the token is renewed to `token.minutes`. A refused code sends no new mail, and the mailed code stays the same between refused attempts. The sign in with a code is limited by `admin.timeout-seconds` like any other sign in, and the sign in with a code works the same over SSL. See [Remote setup](/docs/configuration/bbj-security/remote-setup).

Two factor authentication has no settings of its own.

The `login(String, String, Function<Boolean, String>)` overload serves custom forms.

```java title="Custom sign in with a code"
BBjLoginStatus status = BBjSecurity.login(userName, password,
    retry -> OptionDialog.showInputDialog("Enter your code.", "Two factor authentication"));
if (status == BBjLoginStatus.TWO_FACTOR_REQUIRED) {
  Toast.show("The sign in needs a code.", Theme.DANGER);
}
```
