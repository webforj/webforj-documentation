---
title: BBjShip
sidebar_position: 1
hide_table_of_contents: true
description: Deploy webforJ apps to BBjServices with BBjShip and the webforJ Maven plugin, and run them on your local BBj during development.
---

# BBjShip <DocChip chip='since' label='26.03' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

BBjShip is a BBj plugin that deploys webforJ apps to BBjServices. You build and deploy your app with the [webforJ build plugin](/docs/configuration/build-plugin), either on the BBj machine or from another machine such as a CI server, and the app runs in BBjServices like any other app you manage in Enterprise Manager.

BBjShip replaces the webforJ install plugin described in [Running with BBjServices](/docs/configuration/bbj-installation/overview), which is deprecated and will be removed in a future release.

:::info Maven only
BBjShip works with Maven projects through the `webforj-maven-plugin`. The webforJ Gradle plugin doesn't support BBjShip.
:::

## How it works {#how-it-works}

A deployment passes through three parts:

1. **webforJ build plugin**: packages your app with its dependencies and uploads it to BBjShip
2. **BBjShip**: installs the app in BBjServices and publishes it under `/webapp/<publish name>`
3. **BBjServices**: runs the app, which you manage in Enterprise Manager like any other app

During development, the same build plugin runs your app from your project in the BBjServices of your own machine. Changes you compile reach the running app without a deploy, and the open pages update, see [BBjServices](/docs/configuration/deploy-reload/bbjservices) in Redeployment and Live Reload.

## Topics {#topics}

<DocCardList className="topics-section" />
