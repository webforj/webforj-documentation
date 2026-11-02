---
title: Troubleshooting
sidebar_position: 8
description: What the messages of the BBjShip goals and the bbjship command line mean, and what to do about each, for setup, deploys, development runs, the command line and Windows.
---

# Troubleshooting <DocChip chip='since' label='26.03' /> {#troubleshooting}

This page lists the messages you can meet when you set up BBjShip, deploy an app, start a development run, or use the `bbjship` command line. For each message, it gives the cause and what to do.

A failing Maven goal ends the build with a short message of the goal, followed by the reason. The reason is the part this page lists:

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Credentials and connection {#credentials-and-connection}

These messages come from every goal that talks to BBjShip: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` and `watch`.

| Message | Cause | What to do |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Neither a user name nor a password is configured, so the goal logs in as `admin` with `admin123`. | Change the password in Enterprise Manager and configure the new credentials, see [Settings](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Only one of the user name and the password is configured. | Configure both, or neither to use the factory account. `bbj-deploy` and `bbj-undeploy` also accept a token. |
| `BBj deployment requires bbj.deployment.url...` | No deployment URL is configured. | Set `<bbj><deployment><url>` or pass `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | The URL has another scheme, or carries a user name, a query, or a fragment. | Use `http://<host>:<port>/webforj-bbjship` or `https://<host>:<port>/webforj-bbjship`, and put the credentials in `<admin>`. |
| `Connect to ... failed: Connection refused` | Nothing answers on the host and port of the deployment URL. | Start BBjServices, and check the host and port of the URL against the BBjServices web server. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | The URL doesn't lead to BBjShip, or BBjShip isn't installed or its endpoint isn't enabled on that port. | Check the path `/webforj-bbjship` and the port. On the BBj machine, run `mvn webforj:bbj-setup`, or `bbjship remote enable` on a server. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices refused the user name and password, or the token. | Check the credentials. Renew a token that expired or was revoked in Enterprise Manager. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | The installed BBjShip and the `webforj-maven-plugin` have different versions. | On your own machine, run `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. On a server, install the matching BBjShip with the BBj Plugin Manager. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java doesn't trust the certificate of the BBjServices web server. | Install a certificate for the host name of the URL on the BBj machine, or add its certificate to the Java truststore of the build. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | The deployment URL is an `http://` URL to another machine. The goal stops before it sends anything. With `allowUnencrypted`, the same text is a warning and the goal continues. | Use an `https://` URL with the SSL port, or allow HTTP on both sides, see [Deploying from another machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Setup {#setup}

These messages come from `mvn webforj:bbj-setup` and from the first [development run](/docs/configuration/deploy-reload/bbjservices) of a project, which sets up BBjShip the same way.

| Message | Cause | What to do |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | The goal failed. The reason follows it. A reason about artifact resolution means Maven couldn't download `webforj-bbjship-plugin` in the version of the `webforj-maven-plugin`. | Read the reason. For artifact resolution, check the repositories and the network access of the build. |
| `BBj setup is local only. Run setup on the BBj host` | The deployment URL names another machine. | Run the setup on the BBj machine, or install BBjShip there with the [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | The host name of the deployment URL is unknown. | Use `localhost` or a name of this machine. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | A development run has a token configured. Development runs log in with a user name and password only. | Configure `<admin><username>` and `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | The goal found no BBj installation on this machine. | Set `<bbj><home>`, `-Dwebforj.bbj.home` or the `BBJ_HOME` variable to the BBj installation folder. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | The configured folder isn't a complete BBj installation. | Point the setting at the folder that holds `bin` and `cfg` of BBj. |
| `Installed BBjShip version <version> does not match <version>...` | Another BBjShip version is installed, for example after a webforJ upgrade. | Run `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Deployed apps stay deployed. |
| `Installed BBjShip version is missing...` | The installed BBjShip folder is incomplete. | Run `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | The `plugins` folder of BBj, or the BBjShip folder in it, is a symbolic link or contains one. | Install into a regular folder. |
| `BBj setup command failed. Check credentials and local BBjServices.` followed by the output of the command | The command that enables the deployment endpoint failed. The output names the reason, for example a refused login or a port the web server doesn't listen on. | Check the credentials, that BBjServices runs, and that the port of the deployment URL is a port of the BBjServices web server. |
| `BBj setup command timed out. Check local BBjServices` | The command didn't finish within 120 seconds. | Check that BBjServices runs and answers, then run the goal again. |
| `Previous BBjShip files are preserved at <folder>` | A replacement failed, and the files of the previous version were kept in that folder. | Fix the reason of the failure and run the goal again. |

## Deploy and undeploy {#deploy-and-undeploy}

### Before the upload {#before-the-upload}

These messages come from `bbj-package`, `bbj-deploy` and `bbj-undeploy` before anything reaches BBjShip.

| Message | Cause | What to do |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | The project isn't packaged as a jar. | Set `<packaging>jar</packaging>`. |
| `BBj deployment requires the packaged application jar...` | The goal ran without the `package` phase in the same build. | Run `mvn install`, or `mvn package webforj:bbj-deploy`. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` is on the runtime classpath of the app. | Exclude it from the `webforj` dependency, see [Without the install plugin](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | The app has no webforJ dependency at runtime. | Add the `webforj` dependency in `compile` scope. |
| `BBj deployment dependency is unresolved...` | A dependency of the app has no file. | Check that Maven resolves every dependency of the project. |
| `Publish name <name> is invalid...` | The publish name breaks the rules in [Publish names](/docs/configuration/bbjship/deploying#publish-names). | Choose another `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices uses that name for its own addresses. | Choose another `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` couldn't write `target/bbj/<publish name>.zip`. The reason follows. | Read the reason. |

### Answers of BBjShip {#answers-of-bbjship}

When BBjShip refuses a deploy or an undeploy, the message starts with `BBj deployment failed with HTTP <status>` or `BBj undeployment failed with HTTP <status>`, followed by the reason.

| Status | Reason | Cause | What to do |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` or `...is reserved by BBjServices...` | The publish name is refused. | Choose another publish name. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | The `webforj.conf` of the app can't be read, or an [app setting](/docs/configuration/bbjship/deploying#app-settings) has the wrong type. | Fix the file as the detail says. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` or another reason about the archive | The archive wasn't written by the build plugin, or was changed afterwards. | Deploy the archive the build writes, with the same webforJ version as BBjShip. |
| 401 | `Check the BBj administration credentials or token` | The login was refused. | Check the credentials or renew the token. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | The request arrived over HTTP from another machine. | Use the HTTPS address of the endpoint, or run `bbjship remote enable --allow-unencrypted` on the BBj machine. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | A development run reached a machine where development runs are off. | Run `bbjship development enable` on the BBj machine. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | An undeploy named an app BBjShip didn't deploy. | Check the publish name with `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | The entry class of the app already runs under another publish name. | Undeploy the other app, or deploy under its name. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | An app registered in Enterprise Manager already uses the name. | Choose another publish name, or remove that app in Enterprise Manager. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Another app uses the same name in another letter case. | Choose another publish name. |
| 409 | `<name> is a development run. Stop the watch first` | A development run of the app is active. | Stop the watch with `Ctrl+C`, then deploy. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | A development run uses the name of a deployed app. | Undeploy the app, or set another `<publishName>` for the development run. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | The archive is larger than BBjShip accepts. | Remove dependencies the app doesn't need at runtime. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices failed while it installed or removed the app. | Read the BBjServices logs. |

A deploy that ends with `did not confirm the requested application. Check the BBjShip version and BBjServices log` reached BBjShip but got no confirmation for the app. Check that BBjShip and the `webforj-maven-plugin` have the same version, then read the BBjServices log.

### The running app {#the-running-app}

| Message | Cause | What to do |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | The app uses `icons://` without packaged assets. | Add `webforj.assetsSource = packaged` to `webforj.conf` and deploy again. |

## Development runs {#development-runs}

These messages come from `mvn` or `mvn compile webforj:watch` in a project configured for BBjShip. A failed development run starts with `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, followed by the reason from [Setup](#setup), [Credentials and connection](#credentials-and-connection), or [Answers of BBjShip](#answers-of-bbjship).

| Message | Cause | What to do |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | The watch started before the project was compiled. | Run `mvn compile webforj:watch`, or `mvn` in an archetype project. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` names a folder BBjServices can't find. | Set the absolute path of the class folder on the BBj machine. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | The BBjServices properties file isn't where the run looks for it. | Set `<bbj><properties>` or `-Dwebforj.bbj.properties` to the file. |
| `BBjServices did not stop within 180 seconds` | The run asked BBjServices to stop to apply the hotswap settings, and it kept running. | Stop BBjServices yourself, start it again, and run the watch again. |
| `BBjServices start program is missing: <program>` | The BBj installation has no program to start BBjServices. | Start BBjServices yourself and run the watch again. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices didn't come back after the restart. | Start BBjServices yourself, then run the watch again. |
| `BBjShip did not answer within 120 seconds` | BBjServices started, but the deployment endpoint didn't answer. | Check the BBjServices log, then run the watch again. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices started with Java arguments other than the ones written to the properties file. | Check the Java settings of BBjServices in Enterprise Manager and in that file. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | The app couldn't be removed when the build stopped. | Remove it with `bbjship apps undeploy <name>`, or start the watch again, which replaces it. |

The first development run that adds the hotswap settings restarts BBjServices. It starts BBjServices with `bin/bbjservices` on macOS and Linux. On Windows, it starts the `BBjServices` Windows service when one is registered, and runs `bin\BBjServices.exe -noservice` otherwise. A BBjServices started by `bin/bbjservices` or `-noservice` runs outside the service manager of the operating system until the machine restarts.

## Command line {#command-line}

Every `bbjship` command runs inside BBjServices, so BBjServices must be running. Without it, the command can't start.

Invalid arguments end with exit code `2` and a message that names what to specify:

| Message | What to do |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Name a command, see [Command line](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Use an option of that command, as `--help` lists them. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Give the option a value. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Give a port number. |
| `Specify <option> only once` | Remove the repeated option. |
| `Specify only one of --password and --password-stdin` | Keep one of the two. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Give the user name and the password together. The message names the one that's missing. |
| `Specify an application name or --all`, `Specify one application name` | Give `apps undeploy` one publish name, or `--all`. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Give `apps deploy` one `.zip` file. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Check the path. BBjServices reads the file, so the operating system user of BBjServices needs read access to it. |
| `Development archives are deployed by the watch, not by apps deploy` | Deploy the archive of `bbj-package`, not the one of a development run. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Rename the archive. Its filename without `.zip` is the publish name. |

A command that fails ends with exit code `1` and `Command failed.` followed by the reason:

| Reason | Cause | What to do |
|---|---|---|
| A login failure of BBjServices | The user name or password is wrong. | Check the credentials. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` or `--ssl-port` isn't a port the BBjServices web server listens on. The previous endpoint stays. | Use the ports configured for the web server in Enterprise Manager. |
| `Application <name> is not deployed by BBjShip` | `apps undeploy` named an app BBjShip didn't deploy. | Check the name with `bbjship apps list`. |
| `Another deployment is in progress` | Another deploy or undeploy held the machine for 30 seconds. | Wait for it to finish, then run the command again. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Another app in Enterprise Manager uses the web context `webforj-bbjship` or `webforj-bbjship-assets`. | Rename or remove that context in Enterprise Manager. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | The classpaths `_webforj_bbjship_cli` or `_webforj_bbjship` in Enterprise Manager were changed. | Remove both classpaths in Enterprise Manager and run the command again, which creates them again. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | The BBjShip folder is incomplete. | Install BBjShip again. |
| `BBjServices does not manage web contexts. Update BBj` | The BBj version is too old for BBjShip. | Install the BBj version that matches webforJ. |

`reset` ends with exit code `1` and `Reset cancelled` when the answer to `Continue? [y/N]` isn't `y` or `Y`. When it restored the Java settings of BBjServices, it ends with `Restart BBjServices now to apply the restored Java settings`. Restart BBjServices to run it without the hotswap settings.

## Windows {#windows}

| Situation | What to do |
|---|---|
| Running `bbj.exe` with the BBjShip program prints nothing. | Run `bbjship.cmd` in `<BBj home>\plugins\webforj-bbjship`, which shows the output of the command. |
| `--password-stdin` ends with exit code `2` and `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Pass the password with `--password`, or set `WEBFORJ_BBJ_USERNAME` and `WEBFORJ_BBJ_PASSWORD`. |
| `reset` ends with exit code `2` and `Standard input is not available on Windows. Run reset --yes to confirm`. | Run `bbjship.cmd reset --yes`. |
| `apps undeploy` or `reset` prints `Not removed: <folder>. <reason>` and ends with exit code `0`. | BBjServices still holds files of the app open. The app is removed from Enterprise Manager. Restart BBjServices. The folder goes with the next deploy of that name, or delete it after the restart. |
| Installing BBjShip over an installed version fails because its files are in use. | BBjServices still holds the files of the installed BBjShip open. Restart BBjServices, then install again. |
| BBjServices doesn't run as a Windows service after a development run restarted it. | The development run started it with `BBjServices.exe -noservice` because no `BBjServices` service is registered. It runs outside the Windows service manager until the machine restarts. |
| A `bbjship.cmd` command can't start. | Start BBjServices. Every `bbjship` command needs it running. |
