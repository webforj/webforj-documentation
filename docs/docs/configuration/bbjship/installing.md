---
title: Installing BBjShip
sidebar_position: 4
description: Install BBjShip on your own machine with the webforJ Maven plugin, or on a shared BBj server with the BBj Plugin Manager.
---

# Installing BBjShip <DocChip chip='since' label='26.03' />

BBjShip runs inside BBjServices, so it's always installed on the machine that runs BBjServices. Once it's installed and its deployment endpoint is enabled, your builds deploy to it through the address `/webforj-bbjship` on the BBjServices web server, either from that same machine or from another one.

How you install BBjShip depends on where BBjServices runs:

| Where BBjServices runs | Who installs BBjShip | How |
|---|---|---|
| Your own machine | The webforJ build plugin | Automatically with `mvn`, or once with `mvn webforj:bbj-setup` |
| A shared or production server | An administrator of that server | The BBj Plugin Manager, then `bbjship remote enable` |

In both cases, install the BBjShip version that matches the webforJ version of your apps. Deploys fail when the two differ.

## On your own machine {#on-your-own-machine}

When BBj runs on the same machine as your build, you don't install BBjShip yourself. The first [development run](/docs/configuration/deploy-reload/bbjservices) of a project configured for BBjShip installs it and enables its deployment endpoint:

```bash
mvn
```

### Through the Maven plugin {#through-the-maven-plugin}

To deploy with `mvn install` before you start any development run, install BBjShip once from the folder of your project:

```bash
mvn webforj:bbj-setup
```

The goal installs the BBjShip version that matches the `webforj-maven-plugin` and enables the deployment endpoint on the port of your deployment URL. It needs:

- BBjServices running and answering on the port of the deployment URL
- A deployment URL that names your machine, for example `http://localhost:8888/webforj-bbjship`
- The BBj administrator user name and password, from `<admin>` or the `webforj.bbj.admin.*` properties. Without them, the goal uses the factory account `admin` with `admin123` and prints a warning.
- The BBj installation folder, from `<home>`, `webforj.bbj.home` or `BBJ_HOME`. Without them, the goal detects it.

After a webforJ upgrade, replace the installed BBjShip with the new version:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Your deployed apps stay deployed when you run the goal again.

:::tip Files in use on Windows
On Windows, a replacement can fail while BBjServices holds the files of the installed BBjShip open. Restart BBjServices and run the goal again, see [Troubleshooting](/docs/configuration/bbjship/troubleshooting#windows).
:::

## On a server {#on-a-server}

On a shared or production BBj server, an administrator installs BBjShip once on that server. Developers and CI pipelines then deploy to it from their own machines over HTTPS.

1. Install BBjShip with the [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Enable the deployment endpoint](#enabling-the-deployment-endpoint) with the HTTPS port of the BBjServices web server.
3. Create an authorization token in Enterprise Manager for the builds that deploy, see [Deploying from another machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Through the BBj Plugin Manager {#through-the-bbj-plugin-manager}

In the [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm), install BBjShip from the `webforj-bbjship` repository of the `BBj-Plugins` organization. A tag installs a webforJ release, and `master` installs the development snapshot. Update it later on the Installed tab.

### Enabling the deployment endpoint {#enabling-the-deployment-endpoint}

The deployment endpoint is the address builds deploy to. Enable it on the server with your BBj administrator account and the ports the BBjServices web server listens on. `--password` takes the password as a value, which other users of the machine see in the process list while the command runs. On macOS and Linux, `--password-stdin` reads it from standard input, and `WEBFORJ_BBJ_PASSWORD` keeps it out of the command line on every system:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Without `--port`, the endpoint uses port 8888. Without `--ssl-port`, it has no HTTPS address. Builds on other machines deploy to the HTTPS address, the endpoint answers HTTP 403 to their requests over HTTP unless `--allow-unencrypted` is given. BBjServices must be running. See [Command line](/docs/configuration/bbjship/command-line) for every command.

:::tip Password on Windows
On Windows, run `bbjship.cmd` and pass the password with `--password` or `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` isn't available there and ends with exit code `2`.
:::

## Uninstalling {#uninstalling}

Run `bbjship reset` first. It undeploys every app and removes everything BBjShip set up in Enterprise Manager. Then uninstall BBjShip in the BBj Plugin Manager.

:::warning Back up first
`bbjship reset` can't be undone. Back up `<BBj home>/cfg/BBj.properties` and keep the deployment archive of every app first, see [Reset](/docs/configuration/bbjship/command-line#reset).
:::
