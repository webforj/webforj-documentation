---
title: Getting Started
sidebar_position: 2
description: Add the devtools dependency, enable craftforJ in your configuration, and open craftforJ over a running webforJ app.
---

<DocChip chip='since' label='26.02' />

craftforJ ships with webforJ, so there's nothing to download separately. This page covers what your app needs before craftforJ appears, and how to open it.

:::tip Already enabled in generated projects
Projects created with [startforJ](https://docs.webforj.com/startforj) or from a webforJ [archetype](/docs/building-ui/archetypes/overview) come with craftforJ enabled. If you started from one, run your app and skip ahead to [Opening craftforJ](#opening-craftforj).
:::

## Requirements {#requirements}

craftforJ attaches to an app only when all of the following are true. If one of them isn't met, nothing appears on the page.

### Add the dependency {#add-the-dependency}

Add `webforj-devtools` to your project if it isn't there already:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Debug mode and the craftforJ flag {#debug-mode-and-the-craftforj-flag}

Enable both properties. Neither one does anything on its own, so an app that reaches production with debug mode left on still doesn't expose your source tree.

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

:::tip Spring configuration
On [Spring](/docs/integrations/spring/overview), set the same property names in `application.properties`.
:::

### A local browser and a developer license {#a-local-browser-and-a-developer-license}

Open the app from the machine that runs it, and make sure you have a valid developer license. To reach craftforJ from another machine, add its address to [`hosts-allowed`](./configuration.md#access).

Once these are in place, restart the app and reload the page.

## Opening craftforJ {#opening-craftforj}

When craftforJ is active, a trigger button appears over your app. Click it to open craftforJ, or press <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> from anywhere in the app. The same shortcut closes craftforJ again, and you can drag the trigger to whichever corner suits you.

<MediaPlaceholder type="video" file="craftforJ/first-open.mp4" length="15s">
  Pressing the shortcut and craftforJ arriving over a running app
</MediaPlaceholder>

Its tabs cover the [component tree](./inspector.md), [routes](./routes.md), the [theme](./theme.md), and the [assistant](./ai.md), with settings and app information alongside them.

:::info Shortcuts on macOS
craftforJ writes each shortcut using the modifiers of the platform you're on, so <kbd>Alt</kbd> appears as <kbd>⌥</kbd> and <kbd>Ctrl</kbd> as <kbd>⌘</kbd>. Press <kbd>Shift</kbd> + <kbd>?</kbd> in craftforJ to see the current list.
:::

## Where craftforJ sits {#where-craftforj-sits}

craftforJ floats over your app by default. Drag it anywhere on the page, resize it from any edge, and minimize it back to its trigger when you want the app to yourself. Dragging it onto an edge of the page docks it there, full height or full width, and each edge keeps the size you gave it. Dragging it away from the edge floats it again.

:::info Docking covers the app, it doesn't reflow it
craftforJ is drawn on top of the page. Your app doesn't resize and nothing in it moves out of the way, so whatever sits under craftforJ is hidden while it's there. To see what's underneath, move craftforJ to another edge or take it out of the page.
:::

<MediaPlaceholder type="image" file="getting-started/docked.png">
  craftforJ docked to the right of an app page, covering that edge of the app
</MediaPlaceholder>

To stop covering the app at all, move craftforJ out of the page and into a browser window or tab of its own, which suits a second monitor. It still inspects your app through the page that opened it, so leave that page open. Navigate it away or close it and craftforJ has nothing left to inspect until you open the app again.

<MediaPlaceholder type="video" file="craftforJ/undock-window.mp4" length="20s">
  Moving craftforJ into a window of its own and bringing it back into the page
</MediaPlaceholder>

Choose a tab rather than a window if you use Chrome's split view, which puts your app and craftforJ side by side and accepts only real tabs. Right-click your app's tab, add it to a new split view, then pick the craftforJ tab.

:::info Split view is a Chrome feature
Chrome provides the side-by-side arrangement, not craftforJ. Other browsers have no equivalent, so there craftforJ opens in an ordinary tab you switch to. craftforJ itself works the same either way.
:::

<MediaPlaceholder type="video" file="craftforJ/split-view.mp4" length="20s">
  Opening craftforJ in a tab, then placing both tabs side by side with the browser's split view
</MediaPlaceholder>

:::tip Moving while the assistant is writing
Moving craftforJ into another window ends a reply that's still streaming. craftforJ asks first, and everything written up to that point stays in the chat.
:::

## Making a first change {#making-a-first-change}

1. Press <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> to start picking a component.
2. Hover over something in your app and click it.
3. The tree selects that component, and the sidebar fills with its properties.
4. Change a property. The running app updates immediately.

The change affects the app in front of you only. Your files stay untouched until you review the change and apply it, which is covered in [Writing changes to source](./source-changes.md).

<MediaPlaceholder type="image" file="getting-started/first-open.png">
  craftforJ open beside a running app with a component selected
</MediaPlaceholder>

If nothing appears at all, work through [Troubleshooting](./troubleshooting.md).
