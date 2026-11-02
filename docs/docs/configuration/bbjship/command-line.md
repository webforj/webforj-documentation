---
title: Command Line
sidebar_position: 7
description: The bbjship commands a BBj administrator runs on the BBj machine to manage the deployment endpoint, deployed apps, development runs and BBjServices.
---

# Command line <DocChip chip='since' label='26.03' /> {#command-line}

The `bbjship` command line manages BBjShip on the BBj machine. It's in `<BBj home>/plugins/webforj-bbjship`, as `bbjship.cmd` on Windows. Every command runs inside BBjServices, so BBjServices must be running for every command.

```text
bbjship <group> <command> [options]
```

## Commands {#commands}

| Command | Description |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]`](#remote-enable) | Enables or updates the deployment endpoint and prints its addresses. |
| [`remote disable`](#remote-disable) | Removes the deployment endpoint. Deployed apps keep running. |
| [`remote status`](#remote-status) | Prints the endpoint addresses, whether development runs are enabled and whether unencrypted requests from another machine are allowed. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Deploys a deployment archive. |
| [`apps list [--long]`](#apps-list) | Lists the apps BBjShip deployed. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Removes one or every app BBjShip deployed. |
| [`development enable`, `development disable`](#development) | Turns [development runs](/docs/configuration/deploy-reload/bbjservices) on or off on this machine. |
| [`reset [--yes] [--properties FILE]`](#reset) | Removes every app and everything BBjShip set up. |

## Shared options {#options}

| Setting | Default | Description |
|---|---|---|
| `--username USER` | Unset | BBj administrator user name. |
| `--password PASSWORD` | Unset | BBj administrator password. |
| `--password-stdin` | Off | Reads the password from the first line of standard input. Not available on Windows. |
| `--help`, `-h` | | Prints the usage of the command line or of one command. |
| `--version`, `-V` | | Prints the BBjShip version. |

A password given with `--password` is visible to other users of the machine in the process list while the command runs. On macOS and Linux, a script passes the password on standard input instead:

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

On every system, the variables keep the password out of the command line:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

On Windows, standard input isn't available, so `--password-stdin` ends with exit code `2`. Pass the password as a value or through the variables:

```text
bbjship.cmd remote enable --username admin --password <value>
```

Giving both `--password` and `--password-stdin`, a user name without a password, or a password without a user name ends with exit code `2` and a message that names what to specify.

Without credentials, the command uses the factory account `admin` with `admin123` and prints a warning.

## Environment variables {#environment-variables}

| Variable | Default | Description |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Unset | User name when `--username` is absent. |
| `WEBFORJ_BBJ_PASSWORD` | Unset | Password when neither `--password` nor `--password-stdin` is given. |
| `NO_COLOR` | Unset | Turns off colored output. |

## Remote {#remote}

The `remote` commands manage the deployment endpoint.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]
```

Enables or updates the deployment endpoint and prints its addresses.

| Setting | Default | Description |
|---|---|---|
| `--port PORT` | `8888` | HTTP port of the BBjServices web server for the endpoint. |
| `--ssl-port PORT` | None | SSL port of the BBjServices web server for the endpoint. Without it, the endpoint has no HTTPS address. |
| `--allow-unencrypted` | Off | Accepts the administration credentials over HTTP from another machine. Without it, a request from another machine must use the SSL port, and the endpoint answers HTTP 403 to it. Requests from the BBj machine itself use HTTP either way. |

The ports must be ports the BBjServices web server already listens on. Otherwise `remote enable` ends with exit code `1`, names the port that doesn't answer, and keeps the previous endpoint.

Every `remote enable` records the ports and the `--allow-unencrypted` setting anew. A run without the option turns the setting off and prints `Refused unencrypted requests from another machine`. A run with it prints `Allowed unencrypted requests from another machine`. The build that deploys over HTTP from another machine sets [`allowUnencrypted`](/docs/configuration/bbjship/deploying#deploying-from-another-machine) as well.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Removes the deployment endpoint. Deployed apps keep running.

<!-- vale Google.WordListCase = NO -->
:::warning Builds can't deploy
`remote disable` removes the deployment endpoint. Builds and CI jobs that deploy to it fail until you run `remote enable` again. Note the ports and the endpoint address before you disable it. Deployed apps keep running.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Prints the endpoint addresses, whether development runs are enabled and whether unencrypted requests from another machine are allowed.

```text
$ bbjship remote status
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
Development runs  disabled
Unencrypted requests  refused
```

## Apps {#apps}

The `apps` commands list and remove only apps BBjShip deployed. Apps registered in Enterprise Manager aren't affected.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Deploys a deployment archive. `apps deploy` installs an archive written by [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), without the deployment endpoint. The filename without `.zip` is the [publish name](/docs/configuration/bbjship/deploying#publish-names). BBjServices reads the archive, so the operating system user of BBjServices needs read access to it.

```text
$ bbjship apps deploy inventory.zip
Deployed inventory /webapp/inventory
```

:::warning Replaces the app
Deploying an archive under the name of an app that's already deployed replaces that app. The previous deployment folder is removed and the running sessions of the app end. Keep the previous archive if you may need to go back.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Lists the apps BBjShip deployed.

| Setting | Default | Description |
|---|---|---|
| `--long`, `-l` | Off | Adds the deployment folder and the class folders of each app. |

```text
$ bbjship apps list
NAME       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

Removes one or every app BBjShip deployed.

| Setting | Default | Description |
|---|---|---|
| `--all` | Off | Undeploys every app BBjShip deployed. |

:::warning Can't be undone
`apps undeploy` removes the app from Enterprise Manager, ends its running sessions, and deletes its deployment folder. `apps undeploy --all` does this for every app BBjShip deployed. This can't be undone. Keep the deployment archive of each app, for example `target/bbj/<publish name>.zip`, so you can deploy it again.
:::

## Development {#development}

Development runs are off on a new installation, and the first `webforj:watch` on the same machine turns them on. `bbjship development disable` turns them off until the next `webforj:watch` on this machine turns them on again. A development run from another machine is refused, because the setup it runs first works on the BBj machine only.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Turns on [development runs](/docs/configuration/deploy-reload/bbjservices) on this machine.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Turns off [development runs](/docs/configuration/deploy-reload/bbjservices) on this machine.

## Reset {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` undeploys every app BBjShip deployed, removes the deployment endpoint, the development switch and the `--allow-unencrypted` setting, restores the BBjServices properties file a development run changed, and deletes the files BBjShip wrote. The command never stops BBjServices. When it restored the properties file, restart BBjServices to apply it. The plugin folder stays.

| Setting | Default | Description |
|---|---|---|
| `--yes` | Off | Skips the confirmation. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | BBjServices properties file that `reset` restores. |

The command prints what it does and asks for confirmation unless `--yes` is given. On Windows, `reset` without `--yes` ends with exit code `2` before it logs in.

:::warning Back up first
`reset` can't be undone. It removes the folder of every app BBjShip deployed and ends their running sessions. Before you run it, keep the deployment archive of each app, for example the `target/bbj/<publish name>.zip` of the build, and back up `<BBj home>/cfg/BBj.properties`. When a copy of the file was kept in the BBjShip runtime folder, `reset` puts that copy back over `cfg/BBj.properties`, so any change you made to the file after the first development run is lost. Restart BBjServices afterwards to apply the restored Java settings.
:::

## Exit codes {#exit-codes}

| Code | Meaning |
|---|---|
| `0` | The command succeeded. |
| `1` | The command failed and printed the cause, or `reset` was cancelled. |
| `2` | The arguments are invalid, or the command needs standard input on Windows. The message shows what to specify. |

[Troubleshooting](/docs/configuration/bbjship/troubleshooting#command-line) lists the messages and what to do about each.
