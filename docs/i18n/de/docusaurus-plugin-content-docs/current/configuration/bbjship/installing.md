---
title: BBjShip installieren
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: b5b4d6d8d79017066eecfcda86557633
---
# BBjShip installieren <DocChip chip='since' label='26.03' />

BBjShip läuft innerhalb von BBjServices und ist deshalb immer auf dem Rechner installiert, auf dem BBjServices ausgeführt wird. Sobald BBjShip installiert und sein Deployment-Endpunkt aktiviert ist, werden Ihre Builds über die Adresse `/webforj-bbjship` auf dem BBjServices-Webserver dorthin deployed – entweder von diesem Rechner oder von einem anderen.

Wie Sie BBjShip installieren, hängt davon ab, wo BBjServices ausgeführt wird:

| Wo BBjServices ausgeführt wird | Wer BBjShip installiert | Wie |
|---|---|---|
| Auf Ihrem eigenen Rechner | Das webforJ-Build-Plugin | Automatisch mit `mvn` oder einmalig mit `mvn webforj:bbj-setup` |
| Auf einem gemeinsam genutzten oder Produktionsserver | Ein Administrator dieses Servers | Mit dem BBj Plugin Manager, danach `bbjship remote enable` |

Installieren Sie in beiden Fällen die BBjShip-Version, die zur webforJ-Version Ihrer Apps passt. Bei abweichenden Versionen schlagen Deployments fehl.

## Auf Ihrem eigenen Rechner {#on-your-own-machine}

Wenn BBj auf demselben Rechner wie Ihr Build ausgeführt wird, müssen Sie BBjShip nicht selbst installieren. Beim ersten [Entwicklungslauf](/docs/configuration/deploy-reload/bbjservices) eines für BBjShip konfigurierten Projekts wird BBjShip installiert und sein Deployment-Endpunkt aktiviert:

```bash
mvn
```

### Über das Maven-Plugin {#through-the-maven-plugin}

Wenn Sie mit `mvn install` deployen möchten, bevor Sie einen Entwicklungslauf starten, installieren Sie BBjShip einmalig aus dem Ordner Ihres Projekts:

```bash
mvn webforj:bbj-setup
```

Das Ziel installiert die BBjShip-Version, die zum `webforj-maven-plugin` passt, und aktiviert den Deployment-Endpunkt auf dem Port Ihrer Deployment-URL. Dafür ist Folgendes erforderlich:

- BBjServices muss laufen und auf dem Port der Deployment-URL Anfragen entgegennehmen.
- Eine Deployment-URL, die auf Ihren Rechner verweist, zum Beispiel `http://localhost:8888/webforj-bbjship`
- Benutzername und Passwort des BBj-Administrators aus `<admin>` oder den Eigenschaften `webforj.bbj.admin.*`. Fehlen diese Angaben, verwendet das Ziel das Standardkonto `admin` mit dem Passwort `admin123` und gibt eine Warnung aus.
- Der Installationsordner von BBj aus `<home>`, `webforj.bbj.home` oder `BBJ_HOME`. Fehlen diese Angaben, wird der Ordner automatisch ermittelt.

Ersetzen Sie nach einem webforJ-Upgrade das installierte BBjShip durch die neue Version:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Ihre deployten Apps bleiben erhalten, wenn Sie das Ziel erneut ausführen.

:::tip Unter Windows verwendete Dateien
Unter Windows kann das Ersetzen fehlschlagen, während BBjServices die Dateien des installierten BBjShip geöffnet hält. Starten Sie BBjServices neu und führen Sie das Ziel erneut aus. Siehe [Fehlerbehebung](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Auf einem Server {#on-a-server}

Auf einem gemeinsam genutzten oder Produktionsserver mit BBj installiert ein Administrator BBjShip einmalig auf diesem Server. Entwickler und CI-Pipelines deployen anschließend über HTTPS von ihren eigenen Rechnern aus dorthin.

1. Installieren Sie BBjShip mit dem [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Aktivieren Sie den Deployment-Endpunkt](#enabling-the-deployment-endpoint) mit dem HTTPS-Port des BBjServices-Webservers.
3. Erstellen Sie im Enterprise Manager ein Autorisierungstoken für die Builds, die deployen. Siehe [Von einem anderen Rechner deployen](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Über den BBj Plugin Manager {#through-the-bbj-plugin-manager}

Installieren Sie BBjShip im [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) aus dem Repository `webforj-bbjship` der Organisation `BBj-Plugins`. Ein Tag installiert ein webforJ-Release, `master` installiert den Entwicklungs-Snapshot. Aktualisieren Sie BBjShip später auf der Registerkarte „Installed“.

### Deployment-Endpunkt aktivieren {#enabling-the-deployment-endpoint}

Der Deployment-Endpunkt ist die Adresse, an die Builds deployen. Aktivieren Sie ihn auf dem Server mit Ihrem BBj-Administratorkonto und den Ports, auf denen der BBjServices-Webserver Anfragen entgegennimmt. `--password` erwartet das Passwort als Wert. Während der Befehl ausgeführt wird, können andere Benutzer des Rechners das Passwort in der Prozessliste sehen. Unter macOS und Linux liest `--password-stdin` das Passwort aus der Standardeingabe. Mit `WEBFORJ_BBJ_PASSWORD` bleibt es auf allen Systemen außerhalb der Befehlszeile:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Ohne `--port` verwendet der Endpunkt Port 8888. Ohne `--ssl-port` hat er keine HTTPS-Adresse. Builds auf anderen Rechnern deployen an die HTTPS-Adresse. Der Endpunkt antwortet auf ihre Anfragen über HTTP mit dem Status HTTP 403, es sei denn, `--allow-unencrypted` wird angegeben. BBjServices muss laufen. Alle Befehle finden Sie unter [Befehlszeile](/docs/configuration/bbjship/command-line).

:::tip Passwort unter Windows
Führen Sie unter Windows `bbjship.cmd` aus und übergeben Sie das Passwort mit `--password` oder `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` ist dort nicht verfügbar und beendet den Befehl mit dem Exitcode `2`.
:::

## Deinstallieren {#uninstalling}

Führen Sie zuerst `bbjship reset` aus. Dadurch werden alle Apps undeployt und alle von BBjShip im Enterprise Manager vorgenommenen Einrichtungsschritte entfernt. Deinstallieren Sie BBjShip anschließend im BBj Plugin Manager.

:::warning Erstellen Sie zuerst eine Sicherung
`bbjship reset` kann nicht rückgängig gemacht werden. Sichern Sie zuerst `<BBj home>/cfg/BBj.properties` und bewahren Sie das Deployment-Archiv jeder App auf. Siehe [Zurücksetzen](/docs/configuration/bbjship/command-line#reset).
:::
