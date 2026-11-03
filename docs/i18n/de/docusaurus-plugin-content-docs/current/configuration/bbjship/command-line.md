---
title: Befehlszeile
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: a80e91a6626a5c018604f40e37a5ff00
---
# Befehlszeile <DocChip chip='since' label='26.03' /> {#command-line}

Die Befehlszeile `bbjship` verwaltet BBjShip auf dem BBj-Rechner. Sie befindet sich unter `<BBj home>/plugins/webforj-bbjship` und heißt unter Windows `bbjship.cmd`. Jeder Befehl wird innerhalb von BBjServices ausgeführt. Daher muss BBjServices für jeden Befehl laufen.

```text
bbjship <group> <command> [options]
```

## Befehle {#commands}

| Befehl | Beschreibung |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]`](#remote-enable) | Aktiviert oder aktualisiert den Bereitstellungsendpunkt und gibt seine Adressen aus. |
| [`remote disable`](#remote-disable) | Entfernt den Bereitstellungsendpunkt. Bereitgestellte Apps laufen weiter. |
| [`remote status`](#remote-status) | Gibt die Adressen des Endpunkts aus und zeigt an, ob Entwicklungsläufe aktiviert sind und ob unverschlüsselte Anfragen von einem anderen Rechner zulässig sind. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Stellt ein Bereitstellungsarchiv bereit. |
| [`apps list [--long]`](#apps-list) | Listet die von BBjShip bereitgestellten Apps auf. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Entfernt eine oder alle von BBjShip bereitgestellten Apps. |
| [`development enable`, `development disable`](#development) | Aktiviert oder deaktiviert [Entwicklungsläufe](/docs/configuration/deploy-reload/bbjservices) auf diesem Rechner. |
| [`reset [--yes] [--properties FILE]`](#reset) | Entfernt alle Apps und alles, was BBjShip eingerichtet hat. |

## Gemeinsame Optionen {#options}

| Einstellung | Standardwert | Beschreibung |
|---|---|---|
| `--username USER` | Nicht festgelegt | Benutzername des BBj-Administrators. |
| `--password PASSWORD` | Nicht festgelegt | Passwort des BBj-Administrators. |
| `--password-stdin` | Aus | Liest das Passwort aus der ersten Zeile der Standardeingabe. Unter Windows nicht verfügbar. |
| `--help`, `-h` | | Gibt die Verwendung der Befehlszeile oder eines einzelnen Befehls aus. |
| `--version`, `-V` | | Gibt die BBjShip-Version aus. |

Ein mit `--password` angegebenes Passwort ist während der Ausführung des Befehls für andere Benutzer des Rechners in der Prozessliste sichtbar. Unter macOS und Linux kann ein Skript das Passwort stattdessen über die Standardeingabe übergeben:

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

Auf jedem System halten Umgebungsvariablen das Passwort aus der Befehlszeile heraus:

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

Unter Windows ist die Standardeingabe nicht verfügbar, daher endet `--password-stdin` mit dem Exitcode `2`. Übergeben Sie das Passwort als Wert oder über die Variablen:

```text
bbjship.cmd remote enable --username admin --password <value>
```

Wenn Sie sowohl `--password` als auch `--password-stdin`, einen Benutzernamen ohne Passwort oder ein Passwort ohne Benutzernamen angeben, endet der Befehl mit dem Exitcode `2` und einer Meldung, die angibt, was festgelegt werden muss.

Ohne Anmeldedaten verwendet der Befehl das Werkskonto `admin` mit dem Passwort `admin123` und gibt eine Warnung aus.

## Umgebungsvariablen {#environment-variables}

| Variable | Standardwert | Beschreibung |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Nicht festgelegt | Benutzername, wenn `--username` nicht angegeben ist. |
| `WEBFORJ_BBJ_PASSWORD` | Nicht festgelegt | Passwort, wenn weder `--password` noch `--password-stdin` angegeben ist. |
| `NO_COLOR` | Nicht festgelegt | Deaktiviert die farbige Ausgabe. |

## Remote {#remote}

Die Befehle von `remote` verwalten den Bereitstellungsendpunkt.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]
```

Aktiviert oder aktualisiert den Bereitstellungsendpunkt und gibt seine Adressen aus.

| Einstellung | Standardwert | Beschreibung |
|---|---|---|
| `--port PORT` | `8888` | HTTP-Port des BBjServices-Webservers für den Endpunkt. |
| `--ssl-port PORT` | Keiner | SSL-Port des BBjServices-Webservers für den Endpunkt. Ohne diesen Port hat der Endpunkt keine HTTPS-Adresse. |
| `--allow-unencrypted` | Aus | Akzeptiert die Administrationsanmeldedaten über HTTP von einem anderen Rechner. Ohne diese Option muss eine Anfrage von einem anderen Rechner den SSL-Port verwenden; andernfalls antwortet der Endpunkt mit HTTP 403. Anfragen vom BBj-Rechner selbst verwenden unabhängig davon HTTP. |

Die Ports müssen bereits vom BBjServices-Webserver überwachte Ports sein. Andernfalls endet `remote enable` mit dem Exitcode `1`, nennt den Port, der nicht antwortet, und behält den vorherigen Endpunkt bei.

Bei jedem Aufruf von `remote enable` werden die Ports und die Einstellung `--allow-unencrypted` erneut gespeichert. Wird die Option weggelassen, wird die Einstellung deaktiviert und `Refused unencrypted requests from another machine` ausgegeben. Wird sie angegeben, wird `Allowed unencrypted requests from another machine` ausgegeben. Der Build, der über HTTP von einem anderen Rechner bereitstellt, muss außerdem [`allowUnencrypted`](/docs/configuration/bbjship/deploying#deploying-from-another-machine) festlegen.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Entfernt den Bereitstellungsendpunkt. Bereitgestellte Apps laufen weiter.

<!-- vale Google.WordListCase = NO -->
:::warning Builds können nicht bereitstellen
`remote disable` entfernt den Bereitstellungsendpunkt. Builds und CI-Jobs, die dort bereitstellen, schlagen fehl, bis Sie `remote enable` erneut ausführen. Notieren Sie die Ports und die Endpunktadresse, bevor Sie den Endpunkt deaktivieren. Bereitgestellte Apps laufen weiter.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Gibt die Adressen des Endpunkts aus und zeigt an, ob Entwicklungsläufe aktiviert sind und ob unverschlüsselte Anfragen von einem anderen Rechner zulässig sind.

```text
$ bbjship remote status
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
Development runs  disabled
Unencrypted requests  refused
```

## Apps {#apps}

Die Befehle von `apps` listen ausschließlich von BBjShip bereitgestellte Apps auf und entfernen sie. Apps, die im Enterprise Manager registriert sind, sind davon nicht betroffen.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Stellt ein Bereitstellungsarchiv bereit. `apps deploy` installiert ein mit [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying) erstelltes Archiv, ohne den Bereitstellungsendpunkt zu verwenden. Der Dateiname ohne `.zip` ist der [Veröffentlichungsname](/docs/configuration/bbjship/deploying#publish-names). BBjServices liest das Archiv. Daher benötigt der Betriebssystembenutzer von BBjServices Lesezugriff darauf.

```text
$ bbjship apps deploy inventory.zip
Deployed inventory /webapp/inventory
```

:::warning Ersetzt die App
Wenn Sie ein Archiv unter dem Namen einer bereits bereitgestellten App bereitstellen, wird diese App ersetzt. Der vorherige Bereitstellungsordner wird entfernt und die laufenden Sitzungen der App werden beendet. Bewahren Sie das vorherige Archiv auf, falls Sie es möglicherweise wiederherstellen müssen.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Listet die von BBjShip bereitgestellten Apps auf.

| Einstellung | Standardwert | Beschreibung |
|---|---|---|
| `--long`, `-l` | Aus | Gibt zusätzlich den Bereitstellungsordner und die Klassenordner jeder App aus. |

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

Entfernt eine oder alle von BBjShip bereitgestellten Apps.

| Einstellung | Standardwert | Beschreibung |
|---|---|---|
| `--all` | Aus | Hebt die Bereitstellung aller von BBjShip bereitgestellten Apps auf. |

:::warning Kann nicht rückgängig gemacht werden
`apps undeploy` entfernt die App aus dem Enterprise Manager, beendet ihre laufenden Sitzungen und löscht ihren Bereitstellungsordner. `apps undeploy --all` führt dies für jede von BBjShip bereitgestellte App aus. Dieser Vorgang kann nicht rückgängig gemacht werden. Bewahren Sie das Bereitstellungsarchiv jeder App auf, zum Beispiel `target/bbj/<publish name>.zip`, damit Sie sie erneut bereitstellen können.
:::

## Entwicklung {#development}

Bei einer Neuinstallation sind Entwicklungsläufe deaktiviert. Der erste Aufruf von `webforj:watch` auf demselben Rechner aktiviert sie. `bbjship development disable` deaktiviert sie, bis der nächste Aufruf von `webforj:watch` auf diesem Rechner sie wieder aktiviert. Ein Entwicklungslauf von einem anderen Rechner wird abgelehnt, da die dabei zuerst ausgeführte Einrichtung nur auf dem BBj-Rechner funktioniert.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Aktiviert [Entwicklungsläufe](/docs/configuration/deploy-reload/bbjservices) auf diesem Rechner.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Deaktiviert [Entwicklungsläufe](/docs/configuration/deploy-reload/bbjservices) auf diesem Rechner.

## Zurücksetzen {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` hebt die Bereitstellung aller von BBjShip bereitgestellten Apps auf, entfernt den Bereitstellungsendpunkt, den Entwicklungsmodus und die Einstellung `--allow-unencrypted`, stellt die durch einen Entwicklungslauf geänderte BBjServices-Properties-Datei wieder her und löscht die von BBjShip geschriebenen Dateien. Der Befehl hält BBjServices niemals an. Nach dem Wiederherstellen der Properties-Datei müssen Sie BBjServices neu starten, damit die Änderungen wirksam werden. Der Plugin-Ordner bleibt erhalten.

| Einstellung | Standardwert | Beschreibung |
|---|---|---|
| `--yes` | Aus | Überspringt die Bestätigungsabfrage. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | BBjServices-Properties-Datei, die `reset` wiederherstellt. |

Der Befehl gibt seine Aktionen aus und fordert zur Bestätigung auf, sofern `--yes` nicht angegeben ist. Unter Windows endet `reset` ohne `--yes` mit dem Exitcode `2`, bevor die Anmeldung erfolgt.

:::warning Zuerst eine Sicherung erstellen
`reset` kann nicht rückgängig gemacht werden. Der Befehl entfernt den Ordner jeder von BBjShip bereitgestellten App und beendet deren laufende Sitzungen. Bewahren Sie vor der Ausführung das Bereitstellungsarchiv jeder App auf, zum Beispiel die Datei `target/bbj/<publish name>.zip` des Builds, und sichern Sie `<BBj home>/cfg/BBj.properties`. Wenn eine Kopie der Datei im BBjShip-Laufzeitordner aufbewahrt wurde, ersetzt `reset` damit `cfg/BBj.properties`. Dadurch gehen alle Änderungen verloren, die Sie nach dem ersten Entwicklungslauf an der Datei vorgenommen haben. Starten Sie BBjServices anschließend neu, damit die wiederhergestellten Java-Einstellungen wirksam werden.
:::

## Exitcodes {#exit-codes}

| Code | Bedeutung |
|---|---|
| `0` | Der Befehl wurde erfolgreich ausgeführt. |
| `1` | Der Befehl ist fehlgeschlagen und hat die Ursache ausgegeben, oder `reset` wurde abgebrochen. |
| `2` | Die Argumente sind ungültig oder der Befehl benötigt unter Windows die Standardeingabe. Die Meldung gibt an, was festgelegt werden muss. |

Unter [Fehlerbehebung](/docs/configuration/bbjship/troubleshooting#command-line) finden Sie die Meldungen und Hinweise dazu, was jeweils zu tun ist.
