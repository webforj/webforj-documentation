---
title: Command Line
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: 976400b6e439ac682858351e8f9ade17
---
# Befehlszeile <DocChip chip='since' label='26.03' /> {#command-line}

Der `bbjship` Befehl verwaltet BBjShip auf der BBj-Maschine. Er befindet sich in `<BBj home>/plugins/webforj-bbjship`, als `bbjship.cmd` unter Windows. Jeder Befehl wird innerhalb von BBjServices ausgeführt, daher muss BBjServices für jeden Befehl ausgeführt werden.

```text
bbjship <gruppe> <befehl> [optionen]
```

## Befehle {#commands}

| Befehl | Beschreibung |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT]`](#remote-enable) | Aktiviert oder aktualisiert den Bereitstellungspunkt und druckt seine Adressen aus. |
| [`remote disable`](#remote-disable) | Entfernt den Bereitstellungspunkt. Bereitgestellte Apps laufen weiter. |
| [`remote status`](#remote-status) | Druckt die Adressen des Endpunkts und ob Entwicklungsdurchläufe aktiviert sind. |
| [`apps deploy ARCHIV`](#apps-deploy) | Stellt ein Bereitstellungsarchiv bereit. |
| [`apps list [--long]`](#apps-list) | Listet die Apps auf, die BBjShip bereitgestellt hat. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Entfernt eine oder alle Apps, die BBjShip bereitgestellt hat. |
| [`development enable`, `development disable`](#development) | Aktiviert oder deaktiviert [Entwicklungsdurchläufe](/docs/configuration/deploy-reload/bbjservices) auf dieser Maschine. |
| [`reset [--yes] [--properties DATEI]`](#reset) | Entfernt jede App und alles, was BBjShip eingerichtet hat. |

## Gemeinsame Optionen {#options}

| Einstellung | Standard | Beschreibung |
|---|---|---|
| `--username BENUTZER` | Nicht gesetzt | Benutzername des BBj-Administrators. |
| `--password PASSWORT` | Nicht gesetzt | Passwort des BBj-Administrators. |
| `--password-stdin` | Aus | Liest das Passwort aus der ersten Zeile der Standardeingabe. Unter Windows nicht verfügbar. |
| `--help`, `-h` | | Druckt die Verwendung der Befehlszeile oder eines Befehls aus. |
| `--version`, `-V` | | Druckt die BBjShip-Version aus. |

Ein mit `--password` angegebenes Passwort ist während der Ausführung des Befehls für andere Benutzer der Maschine in der Prozessliste sichtbar. Unter macOS und Linux übergibt ein Skript das Passwort über die Standardeingabe:

```bash
printf '%s\n' "$PASSWORT" | bbjship remote enable --username admin --password-stdin
```

Auf jedem System halten die Variablen das Passwort aus der Befehlszeile heraus:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORT" bbjship remote enable
```

Unter Windows ist die Standardeingabe nicht verfügbar, sodass `--password-stdin` mit dem Exit-Code `2` endet. Übergeben Sie das Passwort als Wert oder über die Variablen:

```text
bbjship.cmd remote enable --username admin --password <wert>
```

Wenn sowohl `--password` als auch `--password-stdin`, ein Benutzername ohne Passwort oder ein Passwort ohne Benutzernamen angegeben werden, endet dies mit dem Exit-Code `2` und einer Nachricht, die angibt, was spezifiziert werden muss.

Ohne Anmeldeinformationen verwendet der Befehl das Standardkonto `admin` mit `admin123` und gibt eine Warnung aus.

## Umgebungsvariablen {#environment-variables}

| Variable | Standard | Beschreibung |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Nicht gesetzt | Benutzername, wenn `--username` fehlt. |
| `WEBFORJ_BBJ_PASSWORD` | Nicht gesetzt | Passwort, wenn weder `--password` noch `--password-stdin` angegeben ist. |
| `NO_COLOR` | Nicht gesetzt | Deaktiviert farbige Ausgaben. |

## Remote {#remote}

Die `remote`-Befehle verwalten den Bereitstellungspunkt.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT]
```

Aktiviert oder aktualisiert den Bereitstellungspunkt und druckt seine Adressen aus.

| Einstellung | Standard | Beschreibung |
|---|---|---|
| `--port PORT` | `8888` | HTTP-Port des BBjServices-Webservers für den Endpunkt. |
| `--ssl-port PORT` | Keine | SSL-Port des BBjServices-Webservers für den Endpunkt. Ohne ihn hat der Endpunkt keine HTTPS-Adresse. |

Die Ports müssen Ports sein, auf denen der BBjServices-Webserver bereits hört. Andernfalls endet `remote enable` mit dem Exit-Code `1`, nennt den Port, der nicht antwortet, und behält den vorherigen Endpunkt.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Entfernt den Bereitstellungspunkt. Bereitgestellte Apps laufen weiter.

:::warning Builds können nicht bereitgestellt werden
`remote disable` entfernt den Bereitstellungspunkt. Builds und CI-Jobs, die darauf bereitstellen, schlagen fehl, bis Sie `remote enable` erneut ausführen. Beachten Sie die Ports und die Adresse des Endpunkts, bevor Sie ihn deaktivieren. Bereitgestellte Apps laufen weiter.
:::

### `remote status` {#remote-status}

```text
bbjship remote status
```

Druckt die Adressen des Endpunkts und ob Entwicklungsdurchläufe aktiviert sind.

```text
$ bbjship remote status
Aktiviert http://localhost:8888/webforj-bbjship
Aktiviert https://localhost:8443/webforj-bbjship
Entwicklungsdurchläufe deaktiviert
```

## Apps {#apps}

Die `apps`-Befehle listen und entfernen nur Apps, die BBjShip bereitgestellt hat. In Enterprise Manager registrierte Apps sind nicht betroffen.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIV
```

Stellt ein Bereitstellungsarchiv bereit. `apps deploy` installiert ein Archiv, das von [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying) erstellt wurde, ohne den Bereitstellungspunkt. Der Dateiname ohne `.zip` ist der [Veröffentlichungsname](/docs/configuration/bbjship/deploying#publish-names). BBjServices liest das Archiv, sodass der Betriebssystembenutzer von BBjServices Lesezugriff darauf benötigt.

```text
$ bbjship apps deploy inventory.zip
Bereitgestellt inventory /webapp/inventory
```

:::warning Überschreibt die App
Das Bereitstellen eines Archivs unter dem Namen einer bereits bereitgestellten App ersetzt diese App. Der vorherige Bereitstellungsordner wird entfernt und die laufenden Sitzungen der App werden beendet. Bewahren Sie das vorherige Archiv auf, falls Sie zurückgehen müssen.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Listet die Apps auf, die BBjShip bereitgestellt hat.

| Einstellung | Standard | Beschreibung |
|---|---|---|
| `--long`, `-l` | Aus | Fügt den Bereitstellungsordner und die Klassenordner jeder App hinzu. |

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

Entfernt eine oder alle Apps, die BBjShip bereitgestellt hat.

| Einstellung | Standard | Beschreibung |
|---|---|---|
| `--all` | Aus | Deplistet jede App, die BBjShip bereitgestellt hat. |

:::warning Kann nicht rückgängig gemacht werden
`apps undeploy` entfernt die App aus dem Enterprise Manager, beendet ihre laufenden Sitzungen und löscht ihren Bereitstellungsordner. `apps undeploy --all` macht dies für jede App, die BBjShip bereitgestellt hat. Dies kann nicht rückgängig gemacht werden. Bewahren Sie das Bereitstellungsarchiv jeder App auf, beispielsweise `target/bbj/<publish name>.zip`, damit Sie es erneut bereitstellen können.
:::

## Entwicklung {#development}

Entwicklungsdurchläufe sind bei einer Neuinstallation deaktiviert, und der erste `webforj:watch` auf derselben Maschine aktiviert sie. `bbjship development disable` schaltet sie aus, bis der nächste `webforj:watch` auf dieser Maschine sie wieder einschaltet. Ein Entwicklungsdurchlauf von einer anderen Maschine wird abgelehnt, da die Einrichtung, die er zuerst ausführt, nur auf der BBj-Maschine funktioniert.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Aktiviert [Entwicklungsdurchläufe](/docs/configuration/deploy-reload/bbjservices) auf dieser Maschine.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Deaktiviert [Entwicklungsdurchläufe](/docs/configuration/deploy-reload/bbjservices) auf dieser Maschine.

## Zurücksetzen {#reset}

```text
bbjship reset [--yes] [--properties DATEI]
```

`reset` entfernt jede App, die BBjShip bereitgestellt hat, entfernt den Bereitstellungspunkt und den Entwicklungsumschalter, stellt die BBjServices-Eigenschaftsdatei, die ein Entwicklungsdurchlauf geändert hat, wieder her und löscht die Dateien, die BBjShip geschrieben hat. Der Befehl stoppt niemals BBjServices. Wenn die Eigenschaften-Datei wiederhergestellt wurde, starten Sie BBjServices neu, um sie anzuwenden. Der Plugin-Ordner bleibt.

| Einstellung | Standard | Beschreibung |
|---|---|---|
| `--yes` | Aus | Überspringt die Bestätigung. |
| `--properties DATEI` | `<BBj home>/cfg/BBj.properties` | BBjServices-Eigenschaftsdatei, die `reset` wiederherstellt. |

Der Befehl druckt, was er tut und fragt nach einer Bestätigung, es sei denn, `--yes` wird angegeben. Unter Windows endet `reset` ohne `--yes` mit dem Exit-Code `2`, bevor es sich anmeldet.

:::warning Zuerst sichern
`reset` kann nicht rückgängig gemacht werden. Es entfernt den Ordner jeder App, die BBjShip bereitgestellt hat, und beendet deren laufende Sitzungen. Bevor Sie es ausführen, bewahren Sie das Bereitstellungsarchiv jeder App auf, beispielsweise `target/bbj/<publish name>.zip` des Builds, und sichern Sie `<BBj home>/cfg/BBj.properties`. Wenn eine Kopie der Datei im BBjShip-Laufzeitordner aufbewahrt wurde, legt `reset` diese Kopie wieder über `cfg/BBj.properties`, sodass jede Änderung, die Sie an der Datei nach dem ersten Entwicklungsdurchlauf vorgenommen haben, verloren geht. Starten Sie anschließend BBjServices neu, um die wiederhergestellten Java-Einstellungen anzuwenden.
:::

## Exit-Codes {#exit-codes}

| Code | Bedeutung |
|---|---|
| `0` | Der Befehl war erfolgreich. |
| `1` | Der Befehl ist fehlgeschlagen und hat die Ursache ausgedruckt, oder `reset` wurde abgebrochen. |
| `2` | Die Argumente sind ungültig oder der Befehl benötigt die Standardeingabe unter Windows. Die Nachricht zeigt an, was angegeben werden muss. |

Die [Fehlerbehebung](/docs/configuration/bbjship/troubleshooting#command-line) listet die Nachrichten auf und was in jedem Fall zu tun ist.
