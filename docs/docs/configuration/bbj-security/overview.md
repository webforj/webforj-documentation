---
sidebar_position: 1
title: BBj Security
hide_table_of_contents: true
description: Add route security to webforJ apps that run on BBjServices without Spring, using the webforj-bbj-security dependency.
---

# Route security for BBj apps <DocChip chip='since' label='26.03' />

The `webforj-bbj-security` library adds [route security](/docs/security/overview) to webforJ apps that run on BBjServices and don't use Spring. The app protects routes with the same [security annotations](/docs/security/annotations) as every other webforJ security implementation.

Users sign in with their BBj user name and password, which the administration API of BBjServices checks. The roles of a user are the BBj security groups of the user, and the permissions are the BBj user permissions.

The sign in survives a reload and is shared by the tabs of a browser when BBjServices provides the HTTP session accessor and `com.basis.jetty.enableSessionAccessor=true` is set. Without it, the sign in and the requested location are kept for the page only, and a reload or a new tab signs the user out. See [Sessions and Lifetime](/docs/configuration/bbj-security/sessions#keep-users-signed-in-after-a-reload).

:::info Spring apps
If the app uses Spring Boot, use the [Spring Security integration](/docs/security/getting-started) instead.
:::

## Topics {#topics}

- [Getting Started](/docs/configuration/bbj-security/getting-started): add the dependency, provide the login and deny views, and protect routes.
- [Configuration](/docs/configuration/bbj-security/configuration): route security and sign in settings in `webforj.conf`.
- [Sign In and Sign Out](/docs/configuration/bbj-security/sign-in): the `BBjSecurity` methods, the login status, and the current user.
- [Roles and Permissions](/docs/configuration/bbj-security/roles-and-permissions): `@RolesAllowed` with BBj security groups and `@BBjPermission` with BBj user permissions.
- [Two Factor Authentication](/docs/configuration/bbj-security/two-factor-authentication): sign in users who need a code, with a pop up, or with a second route.
- [Custom Evaluators](/docs/configuration/bbj-security/custom-evaluators): own rules, combined rules, and the evaluator order.
- [Sessions and Lifetime](/docs/configuration/bbj-security/sessions): reloads, tabs, the HTTP session accessor, token checks and renewal, and the end of a sign in.
- [Remote Setup](/docs/configuration/bbj-security/remote-setup): connect to a remote administration API over SSL with a truststore.
