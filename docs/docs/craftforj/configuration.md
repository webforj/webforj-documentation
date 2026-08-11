---
title: Configuration
sidebar_position: 8
description: Every craftforJ configuration property, its default, and what turning each feature off changes.
---

craftforJ is configured in `webforj.conf`. The property names are the same on [Spring](/docs/integrations/spring/overview), so set them in `application.properties` if that's where your configuration lives.

## Required properties {#required-properties}

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| **`webforj.debug`** | Boolean | `false` | Enables debug mode. craftforJ requires it |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Enables craftforJ |

Both properties must be enabled. See [Security](./security.md#two-required-settings) for why craftforJ requires two settings rather than one

## Access {#access}

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | List or String | loopback only | Client addresses allowed beyond the machine running the app |

By default, only a browser on the same machine as the app can reach craftforJ. To allow other machines, list their addresses. An entry ending in `*` matches a prefix, and a single `*` removes the restriction entirely

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning A wildcard allows anyone who can reach your app
craftforJ reads and writes your project sources. Only use `*` on a network where you're certain who can reach the port, such as a container that only you use. Never use it on a shared network.
:::

## Project root {#project-root}

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| **`webforj.devtools.craftforj.project-root`** | String | detected | The directory your sources live in |

craftforJ determines where your project is from how the app was started. Unusual project layouts and some container setups defeat that detection. If [App info](./app-info.md) reports the wrong project root, set it here

## Feature flags {#feature-flags}

Each of these is enabled by default. Turning one off narrows what craftforJ is allowed to do.

| Property | Turning it off removes |
|----------|------------------------|
| **`webforj.devtools.craftforj.source-changes`** | Writing property changes back to Java, and changing route access |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Saving themes and styles into your stylesheet |
| **`webforj.devtools.craftforj.ai.enabled`** | The AI assistant |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | The assistant writing Java of its own |

Turning off a flag turns the feature off for everyone using that app. The craftforJ settings are per developer and can only narrow further, so a developer can't switch a capability back on that the app switched off.

:::info Features you turn off stay visible
When a flag is off, the control remains in craftforJ and is marked as unsupported by the connected app.
:::

:::warning In production
Leave `webforj.devtools.craftforj.enabled` unset. See [Security](./security.md#in-production) for the full checklist.
:::
