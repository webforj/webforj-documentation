---
title: "Spring Boot progressive web app: make webforJ installable"
description: "Build a Spring Boot progressive web app with webforJ. One @AppProfile annotation adds the manifest, icons and install prompt. Covers testing and offline limits."
slug: java-progressive-web-app
date: 2026-10-24
authors: Eric Handtke
tags: [tutorial, annotations]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-24-java-progressive-web-app/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-24-java-progressive-web-app/cover.jpg)

Users ask for your Spring Boot web app on their desktop or home screen, with its own icon, and no browser bar. A Spring Boot progressive web app needs a manifest, icons and HTTPS, and webforJ covers the first two for you. To turn a webforJ app into a Java progressive web app, add the `@AppProfile` annotation to your `App` class. webforJ generates the Web App Manifest, the meta tags, and the icon links, and browsers then offer to install the app on the desktop or the home screen. You write no JSON and no JavaScript.

Installable is the part of a PWA that webforJ covers directly. Offline use is different, because a webforJ UI is rendered by Java on the server and needs a live connection. This guide shows what to configure, how to test it, and where the limits are.

<!-- truncate -->

## What makes a Spring Boot app installable {#what-makes-a-web-app-installable}

An installable web app is a normal site plus metadata that tells the operating system how to present it. Chromium browsers need a manifest with a name, a start URL, a display mode, and icons of 192 and 512 pixels, and the page must be served over HTTPS. [MDN lists the full criteria](https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps/Guides/Making_PWAs_installable). A service worker is not on that list, so a manifest alone makes an app installable.

HTTPS is the only requirement that depends on your deployment. During development, `localhost` counts as a secure origin, so you can try the install flow on your own machine before you deploy.

## Add a manifest with @AppProfile {#add-a-manifest-with-appprofile}

Annotate the class that extends `App`. Only `name` and `shortName` are required:

```java
@AppProfile(name = "Field Orders", shortName = "Orders")
public class Application extends App {
}
```

With the annotation present, webforJ sets up the meta tags, generates the manifest, and serves the icons and screenshots it references. The `shortName` is for tight spaces such as the label under a home screen icon, so keep it to 12 characters or fewer.

## Configure colors, display mode, and icons {#configure-colors-display-mode-and-icons}

The remaining properties control how the installed app looks and launches. This version sets the launch colors, the start URL, standalone display, and screenshots for the install dialog:

```java
@AppTheme("dark")
@AppProfile(
    name = "Field Orders",
    shortName = "Orders",
    description = "Review and approve orders from any device",
    themeColor = "#2563eb",
    backgroundColor = "#0f172a",
    startUrl = "/orders",
    display = ProfileDescriptor.Display.STANDALONE,
    categories = {"business", "productivity"},
    screenshots = {
        @AppProfile.Screenshot(src = "ws://img/screenshots/wide.png",
            sizes = "1280x720", formFactor = "wide"),
        @AppProfile.Screenshot(src = "ws://img/screenshots/narrow.png",
            sizes = "1080x1920", formFactor = "narrow")})
public class Application extends App {
}
```

| Property | What it controls |
| --- | --- |
| `themeColor` | Color of the browser or operating system UI around the app |
| `backgroundColor` | Splash background while the app loads |
| `startUrl` | The URL opened when the app launches, `.` by default |
| `display` | `STANDALONE` by default, with `FULLSCREEN`, `MINIMAL_UI`, and `BROWSER` as alternatives |
| `orientation` | `NATURAL` by default, or lock to portrait or landscape |
| `screenshots` | Previews shown in install dialogs |

Use hex values for the two colors. The manifest is a JSON file, so a CSS variable such as `var(--dwc-color-primary)` can't be resolved. Match `backgroundColor` to your app background so the splash doesn't flash a different color before the first view appears.

### Icons {#icons}

Icons are the most common reason an install prompt doesn't appear. By default, `@AppProfile` points at `icons://icon.png` and generates entries for 144, 192, and 512 pixel sizes. With the `icons://` protocol, you supply one `icon.png` in the `icons` folder and webforJ serves the other sizes on demand. If you use the `ws://` protocol for static files instead, you must provide `icon-144x144.png`, `icon-192x192.png`, and `icon-512x512.png` yourself. The [Installable Apps page](/docs/configuration/installable-apps) and the [assets protocols page](/docs/managing-resources/assets-protocols) describe both layouts.

If you need a maskable icon for Android, set `purpose = "maskable"` on an explicit `@AppProfile.Icon`.

## Test installability {#test-installability}

Run the app over HTTPS or on `localhost` and test in a Chromium browser, since Firefox on desktop doesn't install web apps from a manifest.

1. Open DevTools, go to the `Application` panel, and select **Manifest**. The identity, presentation, and icon sections show what webforJ generated.
2. If something is wrong, an **Installability** section on the same tab names the problem, such as an icon that fails to load. [Chrome's DevTools guide](https://developer.chrome.com/docs/devtools/progressive-web-apps) describes the panel.
3. Look for the install icon in the address bar and complete an install. The app opens in its own window.

Lighthouse is the tool most people reach for here, and it is worth running for performance and accessibility. Chrome has deprecated the PWA category in Lighthouse, though, so use the `Application` panel for installability and Lighthouse for everything else. See [Chrome's Lighthouse PWA page](https://developer.chrome.com/docs/lighthouse/pwa/) for the current status.

On iOS 16.4 and later, web apps install from the Share menu in Safari, Chrome, Edge, Firefox, and Orion. On Android, Chrome, Edge, Firefox, Opera, and Samsung Internet install them. On macOS, Safari 17 and later adds apps through **File > Add to Dock**.

## What works offline {#what-works-offline}

Be clear about this before you promise anything to users. A webforJ app is a server-driven UI. Java on the server builds the components and sends instructions to the browser, and the browser sends events back. The [client/server interaction page](/docs/architecture/client-server) describes that exchange. If the device has no connection, there is no server to render the next view.

`@AppProfile` generates a manifest and meta tags. It doesn't configure a page cache, and installing the app doesn't change how it connects to your server. Plan on the installed app needing a network connection to run.

What the installed app can do without being open is receive notifications:

- **Push notifications.** The `webforj-push` module lets a view call `Push.getCurrent().subscribe()` to ask for permission, and your server then sends messages through `PushSender`. The browser shows the notification whether or not the app is open. It needs HTTPS and a servlet deployment, and you generate a key pair with `mvn webforj:push-keys`. See the [push notifications page](/docs/advanced/push-notifications).
- **App badges.** `App.setBadge(3)` paints a count on the app icon in the dock, taskbar, or home screen, and `App.setBadge(null)` clears it. It works only after the app is installed and in browsers that support it, which excludes Firefox. See the [app badges page](/docs/advanced/app-badges).

Together these give an installed webforJ app a presence outside the browser: an icon, a window, a badge, and notifications. Another post compares [a web app and a native app](/blog/web-app-vs-native-app-java) for Java business teams.

## Deploy and verify {#deploy-and-verify}

Run the same checks against the deployed URL, because HTTPS termination, reverse proxies, and path prefixes can change what the browser sees. Confirm that the manifest and every icon return a 200 response from your public host.

For a worked example from the webforJ team, read [Your web app deserves a spot on the home screen](/blog/installable-apps), which builds a small habit tracker with the same annotation.

## Frequently asked questions {#faq}

### Can a Java web app be a PWA? {#faq-java-pwa}

Yes. In webforJ, the `@AppProfile` annotation generates the manifest and meta tags, so a Java app becomes installable without a hand-written manifest file. The app is served over HTTPS like any other PWA.

### Do I need a service worker to install a PWA? {#faq-service-worker}

Not for installation. Chromium's install criteria require a manifest, icons, a start URL, a display mode, and HTTPS, and the MDN guide notes that a service worker is not a requirement. webforJ push notifications use their own browser-side worker, which the `Push` class manages for you.

### Does a webforJ PWA work offline? {#faq-offline}

No offline mode is configured by `@AppProfile`. The UI is rendered by Java on the server, so the installed app needs a connection to your server to run. Push notifications and app badges still reach the device while the app is closed.

### Why doesn't the install prompt appear? {#faq-no-install-prompt}

The usual causes are a plain HTTP origin, a missing 192 or 512 pixel icon, or a browser that doesn't install web apps, such as Firefox on desktop. Open the Manifest tab in the Chrome DevTools `Application` panel to see the specific error.
