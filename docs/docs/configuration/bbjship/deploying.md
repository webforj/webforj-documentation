---
title: Deploying
sidebar_position: 5
description: Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package, bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
---

# Deploying <DocChip chip='since' label='26.03' /> {#deploying}

The webforJ Maven plugin deploys an app to [BBjShip](/docs/configuration/bbjship/overview) with three goals:

| Goal | Runs | What it does |
|---|---|---|
| `bbj-package` | with the `package` phase | Writes the deployment archive `target/bbj/<publish name>.zip` |
| `bbj-deploy` | with the `install` phase | Writes the archive and deploys it to BBjShip |
| `bbj-undeploy` | by hand | Removes the app from BBjServices |

The phases apply when the plugin is declared with `<extensions>true</extensions>` and a `<bbj>` block, see [Configuring a project](/docs/configuration/bbjship/existing-projects).

## Deploy {#deploy}

```bash
mvn install
```

Without the extension, run `mvn package webforj:bbj-deploy`. The goal prints the address of the app when the deploy completes:

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

When a deploy fails, the previous version keeps running.

:::warning Replaces the app
A deploy replaces the app that already has the same publish name. The previous deployment folder is removed and the running sessions of that app end. Keep the archive of the previous version if you may need to go back.
:::

[Troubleshooting](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) lists the messages of a failed deploy and what to do about each.

## Package without deploying {#package-without-deploying}

`bbj-package` writes the same archive without a deployment URL or credentials. A CI job can attach `target/bbj/<publish name>.zip` to the build, and a BBj administrator deploys it on the BBj machine with [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Undeploy {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

The app stops and is removed from Enterprise Manager, together with its files.

:::warning Can't be undone
`bbj-undeploy` ends the running sessions of the app and deletes its deployment folder. This can't be undone. Keep the archive from `target/bbj/<publish name>.zip` if you may deploy the app again.
:::

## Publish names {#publish-names}

The publish name defaults to the final name of the build and appears in the app address. It starts with a letter or digit, holds only letters, digits, `_`, `.` and `-`, and doesn't end with a dot. Reserved names, such as the BBjServices paths `webapp` and `files` or the Windows device name `con`, are refused before the archive is written.

Each entry class belongs to one deployed app. Deploying it under a second name fails with:

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Deploying from another machine {#deploying-from-another-machine}

A deploy from another machine, such as a CI server, uses an authorization token over HTTPS:

1. On the BBj machine, [enable the endpoint](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) with an SSL port.
2. In Enterprise Manager, create a token under Security, Authentication Tokens. Renew and revoke it there as well.
3. Pass the token to the build:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

The certificate of the BBjServices web server must match the host name of the URL, or be in the Java truststore of the build. Java doesn't trust the BBj default certificate, which is self signed. An `http://` URL to another machine sends the credentials readable over the network, and the goals print a warning for it.

## App settings {#app-settings}

BBjShip reads the `webforj.conf` of the app on every deploy and applies these properties to the app in Enterprise Manager. A redeploy picks up a changed value. A property that isn't set keeps the default of BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Property | Default | Description |
|---|---|---|
| `webforj.assetsSource` | Unset | `packaged` serves the `ws://` static files and the `icons://` icons from the app, at `/webforj/<publish name>/`. Without it, an app that uses `icons://` fails to start. |
| `webforj.showClientConfirmationDialog` | `false` | Whether the browser asks for confirmation before the page closes. |
| `webforj.disallowConsole` | `true` | Whether the console is disallowed in the app. |
| `webforj.secure` | BBjServices default | Whether the app runs over a secure connection. |
| `webforj.configFile` | BBjServices default | Path of the BBj configuration file the app runs with, as BBjServices reads it on the BBj machine. The path is passed on as written, so it may name a file only the server has. |
| `webforj.programArguments` | None | Program arguments of the app, as a list of strings or as one string. |

An invalid `webforj.conf` fails the deploy with HTTP 400 and the reason.

## Settings {#settings}

The settings live in the `<bbj>` block of the `webforj-maven-plugin` configuration, as shown in [Build plugin](/docs/configuration/bbjship/existing-projects#the-build-plugin). Each property overrides its element on the command line.

| Maven element | Maven property | Default | Description |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Detected | BBj installation folder, used by `bbj-setup` and `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Final name of the build | Name the app is published under, see [Publish names](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | None, required | Address of BBjShip, `http://<host>:<HTTP port>/webforj-bbjship` or `https://<host>:<SSL port>/webforj-bbjship`. |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin` when user name and password are both absent | BBj administrator user name. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123` when user name and password are both absent | BBj administrator password. |
| `<admin><token>` | `webforj.bbj.admin.token` | Empty | Authorization token for `bbj-deploy` and `bbj-undeploy`. Takes precedence over the user name and password. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServices properties file a [development run](/docs/configuration/deploy-reload/bbjservices) writes the hotswap settings into. |
| `<development><classFolder>` | — | Build output directory | Class folder of a development run as BBjServices reads it. |
| — | `webforj.bbj.force` | `false` | Lets `bbj-setup` replace an installed BBjShip of another version. Property only. |

A user name without a password, or a password without a user name, stops the goal.
