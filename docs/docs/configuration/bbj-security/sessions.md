---
title: Sessions and Lifetime
sidebar_position: 8
description: How BBj security keeps a sign in across reloads and tabs, checks and renews the login token, and ends the sign in.
---

A sign in lives in the HTTP session and lasts as long as its login token. This page describes how the sign in survives a reload, how navigations check and renew it, and when it ends.

## Keep users signed in after a reload {#keep-users-signed-in-after-a-reload}

A reload of a signed in tab keeps the user signed in. The user opens the same page again and needs no new sign in. All tabs that belong to one HTTP session share the sign in. A new tab of the same HTTP session starts signed in, and a sign in or a sign out in one tab reaches the other tabs at their next navigation. See [Check and renew the sign in](#check-and-renew-the-sign-in).

The sign in is kept in the HTTP session of the browser. This needs a BBjServices build that provides the HTTP session accessor, and the accessor must be switched on. It was tried on the BBj 26.10 development build. BBj 26.03 doesn't provide it. Without the accessor the sign in is kept for the page only, see [Without the session accessor](#without-the-session-accessor).

Switch the accessor on with the property `com.basis.jetty.enableSessionAccessor=true`. Add it as the line `com.basis.jetty.enableSessionAccessor=true` to `BBj.properties` in the configuration folder of BBjServices, then restart BBjServices.

The sign in is kept as follows:

- The sign in lives in the HTTP session on the server. The browser never holds the login token.
- The sign in expires with the login token. After `webforj.security.bbj.token.minutes` without a renewal, a reload no longer restores the user.
- A sign out removes the sign in from the HTTP session.

### Without the session accessor {#without-the-session-accessor}

Without the session accessor, for example on a BBjServices release without it or with the property not set, the app keeps the sign in and the requested location for the page only. A sign in works and route checks, renewal, and sign out behave as before. A reload or a new tab starts without a sign in, so the user signs in again. Each tab holds its own login token, and a sign out in one tab ends only the token of that tab. Each page that starts without the accessor logs `No HTTP session accessor, the sign in is kept for this page only` at the info level.

The settings need no change for this behavior, and no setting turns it off apart from the property of the accessor.

## Sign in lifetime {#sign-in-lifetime}

A sign in lasts as long as its login token. The token is valid for `webforj.security.bbj.token.minutes` from the sign in. A navigation in the last `token.renew-before-minutes` before the token ends renews the token, so a user who keeps navigating stays signed in. See [Check and renew the sign in](#check-and-renew-the-sign-in).

The sign in ends when one of the following happens:

- The token reaches its end without a renewal.
- BBjServices no longer accepts the token, for example because the token was ended in BBjServices, or BBjServices knows the token for another user.
- The user signs out in any tab of the HTTP session.

The app notices an ended token or a sign out in another tab at the next navigation, and a token that BBjServices refuses at the next due check. A route that requires a sign in then redirects to the authentication path.

## Check and renew the sign in {#check-and-renew-the-sign-in}

The app checks the sign in at each navigation, before webforJ decides whether the route may open. The layout and the view of one navigation share one check. A check that fails never stops the navigation.

The check reads the sign in of the HTTP session first, so it picks up what other tabs changed.

- When the HTTP session holds no sign in, the user is signed out without a call to BBjServices.
- When another tab signed in with a new token, that sign in takes the place of the current one and is checked at once.
- When the token has reached its end, the user is signed out without a call to BBjServices.

Then the check calls the administration API of BBjServices when the check is due. A check is due in these cases:

- At the first navigation after a reload, and after another tab changed the sign in.
- When `token.check-seconds` have passed since the last check.
- At every navigation inside the last `token.renew-before-minutes` before the token ends. This is the renewal window.

A due check asks BBjServices for the user name of the token. The page waits for the call, up to `admin.timeout-seconds`.

| Answer of BBjServices | Result |
|-----------------------|--------|
| The user name of the signed in user | The user stays signed in. Inside the renewal window, BBjServices renews the token for `token.minutes` from the renewal, and the app keeps the renewed token for all tabs of the HTTP session. |
| The token is refused, or belongs to another user | The user is signed out, unless another tab has stored a sign in with a new token in the meantime. That sign in then takes the place of the refused one. |
| No answer, or any other failure | The user stays signed in until the token ends, and the app logs a warning. The next check is due after `token.check-seconds`, inside the renewal window at the next navigation. |

When the renewed token can't be kept in the HTTP session, the user is signed out and the renewed token is ended.

A check that signs the user out opens no location by itself. The navigation continues, and a route that requires a sign in redirects to the authentication path. The app remembers the requested page as for any other redirect. See [Open the target page](/docs/configuration/bbj-security/sign-in#open-the-target-page).

## Idle behavior {#idle-behavior}

Only a navigation checks and renews the sign in. A page that stays open without a navigation isn't checked, and nothing signs it out when the token ends. The page keeps its content, and `BBjSecurity.currentUser()` still returns the user.

The next navigation after the end of the token finds no valid sign in. A route that requires a sign in then redirects to the authentication path.
