---
title: "From Java to an installable web app in 15 minutes: the talk"
description: "Watch a Java installable web app built live in 15 minutes with webforJ: views, shared state, live updates, and a phone-ready app, all written in Java."
slug: java-installable-web-app-talk
date: 2026-11-04T12:00:00Z
authors: webforJ
tags: [community, components, events]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-11-04-java-installable-web-app-talk/cover.jpg
hide_table_of_contents: false
---

<Head>
  <script type="application/ld+json">{JSON.stringify({
    "@context": "https://schema.org",
    "@type": "VideoObject",
    "name": "From Java to Installable Web App in 15 Minutes by Stephan Wald",
    "description": "Stephan Wald builds a Java installable web app live in 15 minutes with webforJ, the full-stack Java framework: views, shared state across users, and live updates, all in Java.",
    "thumbnailUrl": "https://i.ytimg.com/vi/5MYgvCfbWRg/maxresdefault.jpg",
    "uploadDate": "2025-05-14",
    "duration": "PT19M7S",
    "embedUrl": "https://www.youtube.com/embed/5MYgvCfbWRg",
    "contentUrl": "https://www.youtube.com/watch?v=5MYgvCfbWRg"
  })}</script>
</Head>

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-11-04-java-installable-web-app-talk/cover.jpg)

How far can you get with a Java installable web app in 15 minutes? At Devoxx UK, Stephan Wald, our Head of Development at BASIS International, took that challenge live on stage, while the audience scanned a QR code to open the finished app on their phones. Everything in it is Java, written with webforJ, the full-stack Java framework.

<!-- truncate -->

<div style={{position: 'relative', paddingBottom: '56.25%', height: 0, overflow: 'hidden', marginBottom: '1.5rem'}}>
  <iframe
    src="https://www.youtube.com/embed/5MYgvCfbWRg"
    title="From Java to Installable Web App in 15 Minutes by Stephan Wald"
    style={{position: 'absolute', top: 0, left: 0, width: '100%', height: '100%', border: 0}}
    allowFullScreen
  ></iframe>
</div>

## What the talk covers {#what-the-talk-covers}

- **[00:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=0s)** The challenge: a working app in 15 minutes
- **[01:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=60s)** Starting from a webforJ archetype
- **[03:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=180s)** Shaping the tabs and routes
- **[07:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=420s)** Building the first view in Java
- **[08:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=480s)** Shared state with namespaces
- **[11:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=660s)** Responding to a click and showing a toast
- **[13:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=780s)** Filling the list and validating input
- **[17:00](https://www.youtube.com/watch?v=5MYgvCfbWRg&t=1020s)** Live updates in a second view

## The challenge {#the-challenge}

The setup is simple. The audience gets a QR code that opens a finished version of the app on their phones. On stage, Stephan builds it again, live: a screen to enter a nickname and pick a mood, and a second screen that lists everyone's entries as they arrive. Fifteen minutes, one language, no front-end project.

It is a good test for any framework, because it touches what most business apps need: navigation, a form, input handling, validation, shared data, and a screen that updates when other users act.

## Start from an archetype {#start-from-an-archetype}

Stephan doesn't start from a blank file. Before the session, following the getting started steps in our docs, he generated a project from the [tabs archetype](/docs/building-ui/archetypes/tabs), which gives a working app with a tabbed layout and a few sample views. To fit the time limit, he also prepared some of the code he pastes in along the way. You can generate the same kind of project with [startforJ](https://docs.webforj.com/startforj) or the [archetypes](/docs/building-ui/archetypes/overview).

From there, the first change is the navigation. In the main layout he renames and reorders the tabs, swaps in an icon from the bundled icon set, and points each tab at its route. A view is a Java class with a route, so the menu and the pages stay in one language.

## The first view in Java {#first-view}

The "you" view is a class that extends `Composite` with a `FlexLayout` inside it. It holds three components as fields: a `TextField` for the nickname, a `ListBox` for the mood, and a `Button` to submit. Adding them to the layout puts them on the screen.

Styling stays light on purpose: a maximum width so the form is easier on the eye, and a margin taken from the theme's spacing scale instead of a hard-coded pixel value. That keeps the view consistent with the rest of the app's design.

## Shared state across users {#shared-state}

The interesting part of the demo is the model. Instead of a database, Stephan stores the entries in a [namespace](/docs/advanced/namespaces), a webforJ feature for thread-safe, key-value state that can be shared across sessions, a bit like a store that lives on the server. A private namespace is identified by a prefix and a name, and every part of the app that asks for the same name gets the same instance:

```java
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

That one choice is what makes the second screen work. When one user submits, everyone else can see it.

## Clicks, toasts, and validation {#clicks-toasts-validation}

To respond to the button, Stephan registers a click listener and lets the IDE generate the handler method. The first version just confirms the event with a [toast](/docs/components/toast):

```java
Toast.show("Operation completed successfully!", Theme.SUCCESS);
```

The real handler then checks that the nickname isn't blank, stores the nickname and the selected mood in the namespace, and shows an error when the input is missing. The mood list is filled by iterating over the available options and selecting the first one by default.

## Live updates in a second view {#live-updates}

The users view closes the loop. It registers a change listener on the same namespace, and whenever a value changes, it reloads the list into its table. Namespaces support listeners for any change or for a specific key, so a view can react as soon as another user acts:

```java
ns.onChange(event -> {
  System.out.println("Key changed: " + event.getVariableName());
});
```

This is what turns a form into a shared, live app: anyone with the app open sees new entries appear without reloading.

## Making it installable {#installable}

A webforJ app already runs in any modern browser, including on phones. To let users install it like a native app, webforJ provides the [`@AppProfile` annotation](/docs/configuration/installable-apps) on the app class. Once installed, the app appears on the home screen or app menu, similar to native apps. There is no separate mobile codebase, and no JavaScript build to maintain.

## The same app on a split stack {#split-stack}

It helps to picture the same app built the usual way, with a Java back end and a JavaScript front end. The form needs an API endpoint to submit to and another to read the list. The live table needs a push channel, such as WebSockets or server-sent events, plus client code to receive messages and update its state. The mood list, the validation rules, and the data types exist twice, once in Java and once in TypeScript. There are two builds, two dependency trees, and a deployment that has to keep both sides in step.

None of that is hard on its own. Together it is a lot of plumbing for a nickname and a mood, and it would not fit in 15 minutes on stage. In webforJ, the click handler, the shared model, and the table update are plain Java method calls in one project, so the whole app stays small enough to build in front of an audience and to read top to bottom afterwards.

## What 15 minutes shows {#what-it-shows}

The demo is short, and that is the point. Navigation, a form, validation, shared state across users, live updates, and a phone-ready app, all in plain Java and all in one project. Stephan's development loop is the familiar one too: change the code, rebuild when the structure changes, and reload. With [live reload](/docs/configuration/deploy-reload/overview) configured, small changes show up without a restart.

## Build your own in 15 minutes {#build-your-own}

Generate a project with [startforJ](https://docs.webforj.com/startforj), add a view with a form, keep its data in a [namespace](/docs/advanced/namespaces), and add `@AppProfile` to make it installable. Then open it on your phone.

## Frequently asked questions {#faq}

### Can I build an installable web app in Java? {#faq-installable-java}

Yes. With webforJ you write the whole app in Java, and the `@AppProfile` annotation makes it installable, so users can add it to the home screen or app menu like a native app.

### Do I need JavaScript or a mobile framework? {#faq-no-javascript}

No. The app in the talk is plain Java with webforJ components. It runs in mobile browsers and can be installed without a separate mobile codebase.

### How do views share data in real time? {#faq-shared-data}

Through namespaces. A view stores values in a namespace, and other views register change listeners on the same namespace and update when a value changes.
