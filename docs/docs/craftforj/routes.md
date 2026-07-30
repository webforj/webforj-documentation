---
title: Routes
sidebar_position: 5
description: See every registered route in a running webforJ app, navigate to it from craftforJ, and change the access rules declared on it.
---

The Routes tab shows the routing table of the running app in the [hierarchy](/docs/routing/route-hierarchy/overview) the router holds it in, with the active route marked. Routes registered [dynamically](/docs/routing/routes-registration) appear alongside annotated ones.

<MediaPlaceholder type="image" file="routes/tree.png">
  The route tree with the active route marked
</MediaPlaceholder>

## Route details {#route-details}

Selecting a route shows what the router knows about it, including its path, the class behind it, the lifecycle observers attached to it, and its configuration. You can open that class in the source viewer from here.

## Navigating from craftforJ {#navigating-from-craftforj}

You can navigate to any route directly from craftforJ. Routes that take parameters offer a field for each one and resolve the path as you fill them in, so you can confirm where you'll land before you go.

Navigating this way is a real navigation, so your app's [lifecycle observers](/docs/routing/navigation-lifecycle/observers) run exactly as they would for a user. The tree also follows the app, so navigating in the app itself moves the marker.

<MediaPlaceholder type="video" file="craftforJ/route-navigate.mp4" length="20s">
  Filling in a route parameter and navigating to the resolved path
</MediaPlaceholder>

## Access rules {#access-rules}

Each route carries a badge for the [security annotation](/docs/security/annotations) declared on it, and you can narrow the tree to public or protected routes from the toolbar.

Only `@RolesAllowed` and `@DenyAll` count as protected. `@PermitAll` names no roles and requires only that somebody is signed in, so the filter treats it as public. Keep that in mind when you're checking which routes restrict access by role.

<MediaPlaceholder type="image" file="routes/access-badges.png">
  The route tree with an access badge on each route
</MediaPlaceholder>

You can also change a route's access rule from craftforJ. craftforJ writes the annotation into the route's class and the app restarts, so the change goes through the same review as any other [source change](./source-changes.md). The option is unavailable when craftforJ isn't allowed to write Java.

<MediaPlaceholder type="video" file="craftforJ/route-security.mp4" length="25s">
  Changing a route's access rule and the resulting source change
</MediaPlaceholder>
