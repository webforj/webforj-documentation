---
title: Remote Setup
sidebar_position: 9
description: Connect a BBj app to the administration API of a remote BBjServices over SSL with a truststore.
---

The administration API of BBjServices can run on another machine than the app. The library then needs SSL and a truststore, because a plain connection sends the password in clear text and the default certificate of BBjServices is the same on every installation.

The library decides from the text of `admin.host` whether the host is a loopback host. `localhost`, an address in `127.0.0.0/8` and `::1` are loopback. The library makes no name lookup for this, so the name of the own machine counts as remote.

| Host | Allowed connection |
|------|--------------------|
| Loopback | Plain TCP, or `ssl` with or without a truststore. |
| Any other host | `admin.ssl` set to `true` and `admin.truststore` set. |

```hocon title="webforj.conf"
webforj.security.bbj.admin {
  host = "bbj.example.com"
  port = 2002
  ssl = true
  truststore = "/etc/bbj-admin/truststore.p12"
  truststore-password = ${?BBJ_TRUSTSTORE_PASSWORD}
  timeout-seconds = 10
}
```

The app checks the settings at the start. See [Invalid connection values](#invalid-connection-values).

## Enable SSL on the Admin Server {#enable-ssl-on-the-admin-server}

The administration API is the Admin Server of BBjServices. It has one port and no separate SSL port. Enable SSL in the Enterprise Manager under BBj Services, Servers, Admin Server:

- Select Use SSL. BBjServices then requires SSL for every connection to the Admin Server.
- Set Keystore to the keystore with the certificate of that server, and set Keystore Password.

See the [Servers page of the Enterprise Manager help](https://documentation.basis.cloud/BASISHelp/WebHelp/em/bbjservices/EM_BS_Servers.htm). Use a certificate of that server and not the default certificate that BBjServices ships. See [Security notes](/docs/configuration/bbj-security/security-notes).

## Create the truststore {#create-the-truststore}

The truststore holds the certificate of the Admin Server. Export the certificate from the keystore of the Admin Server and import it into a new truststore with `keytool`.

```bash
keytool -exportcert -alias bbj-admin -keystore admin.p12 -file bbj-admin.crt
keytool -importcert -alias bbj-admin -file bbj-admin.crt \
  -keystore truststore.p12 -storetype PKCS12 -storepass "$TRUSTSTORE_PASSWORD" -noprompt
```

Copy `truststore.p12` to the machine of the app and set `admin.truststore` to its path.

BBjServices doesn't check the host name of the certificate. A connection succeeds when the server presents any certificate of the truststore, whatever its name. The truststore must therefore hold only the certificate of the Admin Server that the app uses, and no other certificate. A server with an unrelated certificate is refused with `No trusted certificate found`.

With `ssl` set to `true` and no truststore, BBjServices accepts only its shipped default certificate and refuses an own certificate. The default certificate is the same on every installation and authenticates no server, so a remote host never runs without a truststore.

## Where the password comes from {#where-the-password-comes-from}

Keep the truststore password out of the repository, the jar, and the build artifacts. The library never logs it and hides it in failure texts. Two sources work without code:

- An environment variable of BBjServices. Write `truststore-password = ${?BBJ_TRUSTSTORE_PASSWORD}` in `webforj.conf`. The library resolves the substitution against the environment of BBjServices. A variable that isn't set leaves the key unset.
- A protected file that `webforj.conf` includes. The file holds only `webforj.security.bbj.admin.truststore-password = "..."`, belongs to the user that runs BBjServices and has no access for others.

```hocon title="webforj.conf"
include file("/etc/bbj-admin/secret.conf")
```

A plain value in `webforj.conf` ends in the jar and in the repository. Don't use it.

## Sign in with a code over SSL {#sign-in-with-a-code-over-ssl}

The sign in with a two factor code uses the same host, SSL and truststore settings as the sign in with a password. See [Two factor authentication](/docs/configuration/bbj-security/two-factor-authentication).

## Time limit {#time-limit}

`admin.timeout-seconds` bounds the connect and the reads of the sign in. A host that doesn't answer ends the sign in with `UNAVAILABLE` after that time. See [Configure the sign in](/docs/configuration/bbj-security/configuration#configure-the-sign-in).

## Invalid connection values {#invalid-connection-values}

The app checks the connection settings once at the start. A refused setting leaves route security on and the sign in off. The app logs an error that names the keys and never a value, as in `Sign in is unavailable because <problem>`. Every `BBjSecurity.login` then returns `UNAVAILABLE`, and every protected route redirects to the authentication path. Fix the setting and restart the app.

| Problem text | Cause |
|--------------|-------|
| `config could not be resolved` | The configuration has a substitution that can't be resolved. |
| `webforj.security.bbj.admin.truststore must name a readable file` | `admin.ssl` is `true` and the path of `admin.truststore` isn't a readable file. |
| `webforj.security.bbj.admin.truststore cannot be read with webforj.security.bbj.admin.truststore-password` | The file isn't a truststore, or the password is missing or wrong. |
| `webforj.security.bbj.admin.truststore holds no certificates` | The truststore is empty. |
| `webforj.security.bbj.admin.host is not a loopback address, so webforj.security.bbj.admin.ssl must be true` | A remote host without `admin.ssl`. |
| `webforj.security.bbj.admin.host is not a loopback address, so webforj.security.bbj.admin.truststore must be set` | A remote host without a truststore. |

A truststore with `admin.ssl` set to `false` is no error. The app ignores it and logs a warning, as in `The configuration key webforj.security.bbj.admin.truststore is ignored while webforj.security.bbj.admin.ssl is false`.

With valid settings the app logs the connection at the info level, as in `BBj Admin API at bbj.example.com:2002 with ssl true and truststore set`. The text shows `set` or `none` and never the path.
