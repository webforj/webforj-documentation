---
title: Installation von BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: 1f408734cddab0f6acd5a9446a2d51e9
---
# Installation von BBjShip <DocChip chip='since' label='26.03' />

BBjShip läuft innerhalb von BBjServices, daher wird es immer auf dem Rechner installiert, der BBjServices ausführt. Sobald es installiert ist und der Bereitstellungspunkt aktiviert ist, werden Ihre Builds über die Adresse `/webforj-bbjship` auf dem BBjServices-Webserver bereitgestellt, entweder von demselben Rechner oder von einem anderen.

Wie Sie BBjShip installieren, hängt davon ab, wo BBjServices ausgeführt wird:

| Wo BBjServices ausgeführt wird | Wer installiert BBjShip | Wie |
|---|---|---|
| Ihr eigener Rechner | Das webforJ-Build-Plugin | Automatisch mit `mvn` oder einmal mit `mvn webforj:bbj-setup` |
| Ein freigegebener oder Produktionsserver | Ein Administrator dieses Servers | Der BBj Plugin Manager, dann `bbjship remote enable` |

In beiden Fällen installieren Sie die BBjShip-Version, die mit der webforJ-Version Ihrer Anwendungen übereinstimmt. Bereitstellungen schlagen fehl, wenn die beiden unterschiedlich sind.

## Auf Ihrem eigenen Rechner {#on-your-own-machine}

Wenn BBj auf demselben Rechner wie Ihr Build läuft, installieren Sie BBjShip nicht selbst. Der erste [Entwicklungsdurchlauf](/docs/configuration/deploy-reload/bbjservices) eines für BBjShip konfigurierten Projekts installiert es und aktiviert seinen Bereitstellungspunkt:

```bash
mvn
```

### Über das Maven-Plugin {#through-the-maven-plugin}

Um mit `mvn install` zu deployen, bevor Sie mit einem Entwicklungsdurchlauf beginnen, installieren Sie BBjShip einmal aus dem Ordner Ihres Projekts:

```bash
mvn webforj:bbj-setup
```

Das Ziel installiert die BBjShip-Version, die mit dem `webforj-maven-plugin` übereinstimmt, und aktiviert den Bereitstellungspunkt an dem Port Ihrer Bereitstellungs-URL. Es benötigt:

- BBjServices, das läuft und auf dem Port der Bereitstellungs-URL antwortet
- Eine Bereitstellungs-URL, die Ihren Rechner benennt, z. B. `http://localhost:8888/webforj-bbjship`
- Den Benutzernamen und das Passwort des BBj-Administrators, von `<admin>` oder den `webforj.bbj.admin.*` Eigenschaften. Ohne sie verwendet das Ziel das Standardkonto `admin` mit `admin123` und gibt eine Warnung aus.
- Den Installationsordner von BBj, von `<home>`, `webforj.bbj.home` oder `BBJ_HOME`. Ohne sie erkennt das Ziel diesen.

Nach einem Upgrade von webforJ ersetzen Sie das installierte BBjShip durch die neue Version:

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Ihre bereitgestellten Apps bleiben bereitgestellt, wenn Sie das Ziel erneut ausführen.

:::tip Dateien, die unter Windows verwendet werden
Unter Windows kann ein Ersatz fehlschlagen, während BBjServices die Dateien des installierten BBjShip geöffnet hält. Starten Sie BBjServices neu und führen Sie das Ziel erneut aus, siehe [Fehlerbehebung](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Auf einem Server {#on-a-server}

Auf einem gemeinsamen oder Produktions-BBj-Server installiert ein Administrator BBjShip einmal auf diesem Server. Entwickler und CI-Pipelines stellen dann von ihren eigenen Maschinen über HTTPS bereit.

1. Installieren Sie BBjShip mit dem [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Aktivieren Sie den Bereitstellungspunkt](#enabling-the-deployment-endpoint) mit dem HTTPS-Port des BBjServices-Webservers.
3. Erstellen Sie ein Authentifizierungstoken im Enterprise Manager für die Builds, die bereitgestellt werden, siehe [Bereitstellung von einem anderen Rechner](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Über den BBj Plugin Manager {#through-the-bbj-plugin-manager}

Im [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm) installieren Sie BBjShip aus dem `webforj-bbjship`-Repository der Organisation `BBj-Plugins`. Ein Tag installiert eine webforJ-Version, und `master` installiert den Entwicklungssnapshot. Aktualisieren Sie es später auf der Registerkarte Installierte.

### Aktivierung des Bereitstellungspunkts {#enabling-the-deployment-endpoint}

Der Bereitstellungspunkt ist die Adresse, an die Builds bereitgestellt werden. Aktivieren Sie ihn auf dem Server mit Ihrem BBj-Administrator-Konto und den Ports, auf denen der BBjServices-Webserver lauscht. `--password` nimmt das Passwort als Wert, das andere Benutzer des Rechners während der Ausführung des Befehls in der Prozessliste sehen können. Unter macOS und Linux liest `--password-stdin` es von der Standardeingabe und `WEBFORJ_BBJ_PASSWORD` hält es auf jedem System aus der Befehlszeile heraus:

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Aktiviert http://localhost:8888/webforj-bbjship
Aktiviert https://localhost:8443/webforj-bbjship
```

Ohne `--port` verwendet der Endpunkt Port 8888. Ohne `--ssl-port` hat er keine HTTPS-Adresse. BBjServices muss laufen. Siehe [Befehlszeile](/docs/configuration/bbjship/command-line) für jeden Befehl.

:::tip Passwort unter Windows
Unter Windows führen Sie `bbjship.cmd` aus und übergeben das Passwort mit `--password` oder `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` ist dort nicht verfügbar und endet mit dem Exit-Code `2`.
:::

## Deinstallation {#uninstalling}

Führen Sie zuerst `bbjship reset` aus. Es stellt jede App wieder her und entfernt alles, was BBjShip im Enterprise Manager eingerichtet hat. Deinstallieren Sie dann BBjShip im BBj Plugin Manager.

:::warning Zuerst sichern
`bbjship reset` kann nicht rückgängig gemacht werden. Sichern Sie `<BBj home>/cfg/BBj.properties` und bewahren Sie das Bereitstellungsarchiv jeder App zuerst auf, siehe [Zurücksetzen](/docs/configuration/bbjship/command-line#reset).
:::
