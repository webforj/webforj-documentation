---
title: Configuration
sidebar_position: 3
description: Configure route security and the sign in against the administration API of BBjServices in the configuration file of the app.
---

BBj security reads its settings from the `webforj.conf` file of the app. The route security settings live under `webforj.security`, and the sign in settings under `webforj.security.bbj`.

## Enable security {#enable-security}

Route security is on unless the `webforj.conf` file turns it off. The file uses a JSON-like format. The settings live under `webforj.security`.

```hocon title="webforj.conf"
webforj.security {
  enabled = true
  secure-by-default = true
  authentication-path = "/login"
  deny-path = "/denied"
}
```

| Setting | Default | Description |
|---------|---------|-------------|
| `webforj.security.enabled` | `true` | Turns route security on. When `false`, the app runs without route security. |
| `webforj.security.secure-by-default` | `true` | Routes without a security annotation require a signed-in user. When `false`, such routes are public. |
| `webforj.security.authentication-path` | `/login` | Route that visitors without a sign-in are redirected to when a route requires one. |
| `webforj.security.deny-path` | The authentication path with an empty `error` query parameter | Route that visitors are redirected to when access is denied. webforJ adds the reason as a `reason` query parameter. |

An unset path takes its default. The path must match the `@Route` value of the view that serves it. A value of `enabled` or `secure-by-default` that isn't a boolean counts as `true`, and the app logs a warning that names the setting.

## Configure the sign in {#configure-the-sign-in}

The sign in settings live under `webforj.security.bbj`.

```hocon title="webforj.conf"
webforj.security.bbj {
  admin.host = "127.0.0.1"
  admin.port = 2002
  admin.timeout-seconds = 10
  token.minutes = 30
  token.check-seconds = 30
  token.renew-before-minutes = 5
  default-success-path = "/"
  return-after-login = true
  logout-success-path = "/login"
}
```

| Setting | Default | Description |
|---------|---------|-------------|
| `webforj.security.bbj.admin.host` | `127.0.0.1` | Host of the administration API of BBjServices that checks the user name and password. A host other than `localhost` or a loopback address needs `admin.ssl` and `admin.truststore`. See [Remote setup](/docs/configuration/bbj-security/remote-setup). |
| `webforj.security.bbj.admin.port` | `2002` | Port of the administration API of BBjServices. The value is from 1 to 65535. |
| `webforj.security.bbj.admin.timeout-seconds` | `10` | Time limit in seconds of each call to the administration API of BBjServices. The value is from 1 to 9999. It bounds the connect and the reads of the sign in. This applies to a sign in with a two factor code too. A sign in whose call exceeds it ends with `UNAVAILABLE`. The page waits for the call, so a sign in, a sign out and a due session check on a navigation can take up to this limit. |
| `webforj.security.bbj.token.minutes` | `30` | Lifetime in minutes of the login token that the administration API of BBjServices issues. The value is from 1 to 2147483647. BBjServices replaces a requested lifetime that's higher than its own default token time with that default, so a larger value has no effect. |
| `webforj.security.bbj.token.check-seconds` | `30` | Seconds between two checks of the sign in against the administration API of BBjServices. The value is from 0 to 3600. With `0`, every navigation checks the sign in. See [Check and renew the sign in](/docs/configuration/bbj-security/sessions#check-and-renew-the-sign-in). |
| `webforj.security.bbj.token.renew-before-minutes` | `5` | Minutes before the end of the login token in which a check renews the token. The value is from 0 to `token.minutes`. With `0`, the token is never renewed and the sign in ends `token.minutes` after the sign in. A value of `token.minutes` or more renews the token at every navigation. |
| `webforj.security.bbj.default-success-path` | `/` | Route that opens after a successful sign in when no requested page is used. |
| `webforj.security.bbj.return-after-login` | `true` | Opens the page that the visitor requested before the sign in. When `false`, or when no page was requested, the default success path opens. |
| `webforj.security.bbj.logout-success-path` | None | Route that opens after a sign out. Without this setting the authentication path applies, and without that `/login`. |
| `webforj.security.bbj.admin.ssl` | `false` | Connects to the administration API of BBjServices over SSL. A host other than `localhost` or a loopback address needs `true`. See [Remote setup](/docs/configuration/bbj-security/remote-setup). |
| `webforj.security.bbj.admin.truststore` | None | Path of the truststore file that checks the certificate of the administration API. Used only when `admin.ssl` is `true`, otherwise the app logs a warning and ignores it. A host other than `localhost` or a loopback address needs it. See [Remote setup](/docs/configuration/bbj-security/remote-setup). |
| `webforj.security.bbj.admin.truststore-password` | None | Password of the truststore file. The app never logs it. The library resolves a `${?VAR}` substitution against the environment of BBjServices. See [Where the password comes from](/docs/configuration/bbj-security/remote-setup#where-the-password-comes-from). |

A value that is out of range or of the wrong type uses its default value, boolean values default to `true`.
