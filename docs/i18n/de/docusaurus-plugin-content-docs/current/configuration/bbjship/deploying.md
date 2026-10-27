---
title: Bereitstellung
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 19fc6feafc7d9e17580d710ced7b962b
---
# Bereitstellung von <DocChip chip='since' label='26.03' /> {#deploying}

Das webforJ Maven-Plugin stellt eine App bei [BBjShip](/docs/configuration/bbjship/overview) mit drei Zielen bereit:

| Ziel | Ausführung | Was es tut |
|---|---|---|
| `bbj-package` | mit der Phase `package` | Schreibt das Bereitstellungsarchiv `target/bbj/<publish name>.zip` |
| `bbj-deploy` | mit der Phase `install` | Schreibt das Archiv und stellt es bei BBjShip bereit |
| `bbj-undeploy` | manuell | Entfernt die App von BBjServices |

Die Phasen gelten, wenn das Plugin mit `<extensions>true</extensions>` und einem `<bbj>`-Block deklariert wird, siehe [Konfiguration eines Projekts](/docs/configuration/bbjship/existing-projects).

## Bereitstellen {#deploy}

```bash
mvn install
```

Ohne die Erweiterung führen Sie `mvn package webforj:bbj-deploy` aus. Das Ziel gibt die Adresse der App nach Abschluss der Bereitstellung aus:

```text
[INFO] BBj-Bereitstellung von inventory abgeschlossen unter http://localhost:8888/webapp/inventory
```

Wenn eine Bereitstellung fehlschlägt, wird die vorherige Version weiter ausgeführt.

:::warning Ersetzt die App
Eine Bereitstellung ersetzt die App, die bereits denselben Veröffentlichungsnamen hat. Der vorherige Bereitstellungsordner wird entfernt und die laufenden Sitzungen dieser App enden. Bewahren Sie das Archiv der vorherigen Version auf, falls Sie zurückgehen müssen.
:::

[Fehlerbehebung](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) listet die Nachrichten einer fehlgeschlagenen Bereitstellung und was in jedem Fall zu tun ist.

## Paket ohne Bereitstellung {#package-without-deploying}

`bbj-package` schreibt dasselbe Archiv ohne eine Bereitstellungs-URL oder Anmeldedaten. Ein CI-Job kann `target/bbj/<publish name>.zip` an den Build anhängen, und ein BBj-Administrator stellt es auf dem BBj-Computer mit [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps) bereit.

## Ungültig machen {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

Die App wird gestoppt und zusammen mit ihren Dateien aus dem Enterprise Manager entfernt.

:::warning Kann nicht rückgängig gemacht werden
`bbj-undeploy` beendet die laufenden Sitzungen der App und löscht deren Bereitstellungsordner. Dies kann nicht rückgängig gemacht werden. Bewahren Sie das Archiv aus `target/bbj/<publish name>.zip` auf, falls Sie die App erneut bereitstellen möchten.
:::

## Veröffentlichungsnamen {#publish-names}

Der Veröffentlichungsname wird standardmäßig auf den endgültigen Namen des Builds gesetzt und erscheint in der Adresse der App. Er beginnt mit einem Buchstaben oder einer Ziffer, enthält nur Buchstaben, Ziffern, `_`, `.` und `-` und endet nicht mit einem Punkt. Reservierte Namen, wie die BBjServices-Pfade `webapp` und `files` oder der Windows-Gerätename `con`, werden abgelehnt, bevor das Archiv geschrieben wird.

Jede Eintragsklasse gehört zu einer bereitgestellten App. Eine Bereitstellung unter einem zweiten Namen schlägt fehl mit:

```text
BBj-Bereitstellung fehlgeschlagen mit HTTP 409. Eintragsklasse com.example.Application ist bereits als inventory veröffentlicht. Zuerst inventory ungültig machen
```

## Bereitstellung von einem anderen Computer {#deploying-from-another-machine}

Eine Bereitstellung von einem anderen Computer, wie einem CI-Server, verwendet einen Autorisierungstoken über HTTPS:

1. Aktivieren Sie auf dem BBj-Computer [den Endpunkt](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) mit einem SSL-Port.
2. Erstellen Sie im Enterprise Manager unter Sicherheit einen Token für Authentifizierungstoken. Erneuern und widerrufen Sie ihn dort ebenfalls.
3. Übergeben Sie den Token an den Build:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

Das Zertifikat des BBjServices-Webservers muss mit dem Hostnamen der URL übereinstimmen oder im Java-Truststore des Builds vorhanden sein. Java vertraut nicht dem Standardzertifikat von BBj, das selbstsigniert ist. Eine `http://`-URL zu einem anderen Computer sendet die Anmeldedaten lesbar über das Netzwerk, und die Ziele drucken eine Warnung dafür.

## App-Einstellungen {#app-settings}

BBjShip liest die `webforj.conf` der App bei jeder Bereitstellung und wendet diese Eigenschaften auf die App im Enterprise Manager an. Eine erneute Bereitstellung übernimmt einen geänderten Wert. Eine nicht gesetzte Eigenschaft behält den Standardwert von BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Eigenschaft | Standard | Beschreibung |
|---|---|---|
| `webforj.assetsSource` | Ungesetzt | `packaged` dient den `ws://` statischen Dateien und den `icons://` Icons der App, unter `/webforj/<publish name>/`. Ohne dies schlägt eine App, die `icons://` verwendet, beim Starten fehl. |
| `webforj.showClientConfirmationDialog` | `false` | Ob der Browser vor dem Schließen der Seite um Bestätigung bittet. |
| `webforj.disallowConsole` | `true` | Ob die Konsole in der App nicht erlaubt ist. |
| `webforj.secure` | BBjServices-Standard | Ob die App über eine sichere Verbindung ausgeführt wird. |
| `webforj.configFile` | BBjServices-Standard | Pfad der BBj-Konfigurationsdatei, mit der die App ausgeführt wird, wie BBjServices sie auf dem BBj-Computer liest. Der Pfad wird so übergeben, wie er geschrieben ist, sodass er eine Datei benennen kann, die nur der Server hat. |
| `webforj.programArguments` | Keine | Programmargumente der App, als Liste von Strings oder als ein String. |

Eine ungültige `webforj.conf` lässt die Bereitstellung mit HTTP 400 und dem Grund scheitern.

## Einstellungen {#settings}

Die Einstellungen befinden sich im `<bbj>`-Block der Konfiguration des `webforj-maven-plugin`, wie in [Build-Plugin](/docs/configuration/bbjship/existing-projects#the-build-plugin) gezeigt. Jede Eigenschaft überschreibt ihr Element in der Befehlszeile.

| Maven-Element | Maven-Eigenschaft | Standard | Beschreibung |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Erkannt | BBj-Installationsordner, verwendet von `bbj-setup` und `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Endgültiger Name des Builds | Name, unter dem die App veröffentlicht wird, siehe [Veröffentlichungsnamen](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Keine, erforderlich | Adresse von BBjShip, `http://<host>:<HTTP port>/webforj-bbjship` oder `https://<host>:<SSL port>/webforj-bbjship`. |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin`, wenn sowohl Benutzername als auch Passwort fehlen | Benutzername des BBj-Administrators. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123`, wenn sowohl Benutzername als auch Passwort fehlen | Passwort des BBj-Administrators. |
| `<admin><token>` | `webforj.bbj.admin.token` | Leer | Autorisierungstoken für `bbj-deploy` und `bbj-undeploy`. Hat Vorrang vor Benutzername und Passwort. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServices-Eigenschaften-Datei, in der eine [Entwicklungsausführung](/docs/configuration/deploy-reload/bbjservices) die Hot-Swap-Einstellungen schreibt. |
| `<development><classFolder>` | — | Build-Ausgabeverzeichnis | Klassenordner einer Entwicklungsausführung, wie BBjServices ihn liest. |
| — | `webforj.bbj.force` | `false` | Erlaubt `bbj-setup`, eine installierte BBjShip einer anderen Version zu ersetzen. Nur Eigenschaft. |

Ein Benutzername ohne Passwort oder ein Passwort ohne Benutzername stoppt das Ziel.
