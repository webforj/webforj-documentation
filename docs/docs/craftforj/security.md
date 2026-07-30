---
title: Security
sidebar_position: 9
description: What craftforJ can reach in your project, how it restricts access, and how to confirm it's disabled in production.
---

craftforJ reads and writes the source of the project it's attached to. This page describes the boundaries around that, and how to confirm craftforJ is off in the builds you deploy.

## Two required settings {#two-required-settings}

craftforJ requires both of the following to be enabled:

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

Neither one does anything on its own. An app that reaches production with debug mode left on doesn't expose craftforJ, and an app carrying the craftforJ property in a shared configuration file doesn't expose it outside debug mode.

Projects created with [startforJ](https://docs.webforj.com/startforj) or from a webforJ [archetype](/docs/building-ui/archetypes/overview) have both enabled, so craftforJ works from the first run. Before you deploy, work through the [production checklist](#in-production) below.

## Local access by default {#local-access-by-default}

Only a browser on the machine running the app can reach craftforJ. Everything else is refused, and this applies without any configuration on your part. To reach craftforJ from another machine, name that machine in [`hosts-allowed`](./configuration.md#access). Addresses are matched literally, so a client can't get through by claiming to be something else.

:::warning The wildcard removes the restriction completely
Setting `hosts-allowed = "*"` means anyone who can reach your app's port can read and write your project sources. It exists for sealed environments such as a container only you can reach. Don't use it anywhere else.
:::

## No added HTTP surface {#no-added-http-surface}

craftforJ adds no HTTP endpoint, servlet, or filter to your app. It works over the connection your app already has, so your app answers exactly the same set of requests with craftforJ enabled as it does without it.

## Requests come from your page {#requests-come-from-your-page}

craftforJ acts only on requests that came from the page your server actually served. A script that finds its way into the page from somewhere else, such as a compromised dependency or something pasted into a console, can't drive craftforJ.

## API keys {#api-keys}

Your key is stored on the machine running your app. The [AI assistant](./ai.md) runs in the browser, so craftforJ has to hand it the key to work with, and it holds that key in memory for as long as the page is open. Nothing is written to browser storage, and closing the page leaves nothing behind.

The assistant then talks to your provider from the browser rather than through your server. There's no relay, no proxy, no telemetry, and no third party in between.

What does reach your provider is the conversation itself, which includes the parts of your app the assistant looked at and any screenshots it took. Consider that before pointing a hosted model at an app running against real data. A model running locally keeps everything on your machine.

## What craftforJ can change {#what-craftforj-can-change}

With every feature enabled, craftforJ can:

- Read any source file under your project root
- Write Java source files, including route access annotations
- Write your app's stylesheet
- Change and remove components in the running app
- Navigate the running app

Each of these can be [switched off](./configuration.md#feature-flags) independently, and every write to disk goes through a diff you approve.

## In production {#in-production}

Leave craftforJ disabled. It's off unless you turned it on, so in most cases there's nothing to do. To confirm:

1. `webforj.devtools.craftforj.enabled` is unset or `false` in the configuration you actually deploy.
2. `webforj.debug` is unset or `false` in that same configuration.
3. Neither property is set by an environment variable or by a profile that applies only in production.
4. Load the deployed app and confirm there's no craftforJ trigger on the page.

For the wider picture, see [Production hardening](/docs/security/application-security/production-hardening).

## Reporting a security issue {#reporting-a-security-issue}

If you find a security issue in craftforJ, report it through the [webforJ security policy](https://github.com/webforj/webforj/security) rather than in a public issue.
