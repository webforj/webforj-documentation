---
sidebar_position: 3
title: Production Hardening
description: Practical steps for running a webforJ app safely in production, from transport encryption and dependency upkeep to server-side checks and disclosure.
---

webforJ's [server-driven model](/docs/architecture/client-server) and built-in safeguards against  [common threats](/docs/security/application-security/common-threats) cover a lot, but a secure deployment still depends on how you operate the app. The steps below round out the picture.

## Encrypt every connection {#encrypt-every-connection}

Run production traffic over HTTPS only. Terminate TLS at the container, proxy, or load balancer in front of the app, and bounce any plain-HTTP request to its secure equivalent so credentials and session identifiers never travel unencrypted.

## Trust nothing from the browser {#trust-nothing-from-the-browser}

A manipulated client can send anything. Re-validate every value your code receives, even values your interface already constrained, before you persist or act on them. The [Client/Server Interaction](/docs/architecture/client-server) article explains why the server is the only place a rule can truly hold.

webforJ's [data binding and validation](/docs/data-binding/validation/overview) helps here: because binding runs in Java on the server, the constraints you attach to a model, including [Jakarta validation](/docs/data-binding/validation/jakarta-validation), are enforced server-side rather than only in the browser. Treat that as your integrity layer, not as a defense against injection or markup attacks, which still need the handling described in the [Common Threats](/docs/security/application-security/common-threats) article.

## Disabled and hidden aren't security {#disabled-and-hidden-arent-security}

`setEnabled(false)` and `setVisible(false)` are interface cues, not access controls. webforJ mirrors a control's disabled state to the client, but it doesn't stop a manipulated client from re-enabling that control and triggering its action. Never lean on a disabled or hidden control to keep something from happening.

Put the real rule in the server-side handler instead: confirm the user is allowed and the preconditions hold before performing the action, exactly as you would if the control had been enabled the whole time. The disabled state guides honest users; the server-side rule stops dishonest ones.

## Lock down your views {#lock-down-your-views}

Gate views with [route security](/docs/security/overview) so each one demands the right authentication and roles. Give people the narrowest access that lets them work, and prefer a secure-by-default posture where an unmarked route still requires sign-in.

## Keep secrets external {#keep-secrets-external}

Credentials, keys, and tokens don't belong in code or in your repository. Pull them from the environment or an external source instead, as shown in [Managing Secrets](/docs/security/application-security/managing-secrets).

## Stay current on dependencies {#stay-current-on-dependencies}

The libraries you pull in are a larger source of risk than your own code. Track advisories, update webforJ and your other dependencies regularly, and when a patched version of a transitive library ships ahead of the library that pulls it in, pin the fixed version in your `pom.xml`.

## Fail quietly {#fail-quietly}

Don't let stack traces, file paths, or internal identifiers reach end users. Record the detail in your server logs and present a plain, generic message in the interface. Register a custom handler through webforJ's [error handling](/docs/advanced/error-handling) so that uncaught exceptions surface a controlled page instead of raw diagnostics.

## Disclose responsibly {#disclose-responsibly}

Found a possible flaw in webforJ itself? Report it privately through GitHub's [private vulnerability reporting](https://github.com/webforj/webforj/security/advisories) rather than opening a public issue or pull request, so a fix can land before the details are known.
