---
title: Bereitstellung
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 77099210b317740da114b7881490bd09
---
# Bereitstellen <DocChip chip='since' label='26.03' /> {#deploying}

Das webforJ-Maven-Plugin stellt eine App mit drei Zielen auf [BBjShip](/docs/configuration/bbjship/overview) bereit:

| Ziel | Wird ausgeführt | Funktion |
|---|---|---|
| `bbj-package` | in der Phase `package` | Schreibt das Bereitstellungsarchiv `target/bbj/<publish name>.zip` |
| `bbj-deploy` | in der Phase `install` | Schreibt das Archiv und stellt es auf BBjShip bereit |
| `bbj-undeploy` | manuell | Entfernt die App aus BBjServices |

Die Phasen gelten, wenn das Plugin mit `<extensions>true</extensions>` und einem `<bbj>`-Block deklariert ist. Siehe [Ein Projekt konfigurieren](/docs/configuration/bbjship/existing-projects).

## Bereitstellen {#deploy}

```bash
mvn install
```

Ohne die Extension führen Sie `mvn package webforj:bbj-deploy` aus. Nach Abschluss der Bereitstellung gibt das Ziel die Adresse der App aus:

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

Wenn eine Bereitstellung fehlschlägt, wird die vorherige Version weiterhin ausgeführt.

:::warning Ersetzt die App
Eine Bereitstellung ersetzt die App mit demselben Veröffentlichungsnamen. Der vorherige Bereitstellungsordner wird entfernt und die laufenden Sitzungen dieser App werden beendet. Bewahren Sie das Archiv der vorherigen Version auf, falls Sie zurückwechseln müssen.
:::

[Fehlerbehebung](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) enthält die Meldungen bei einer fehlgeschlagenen Bereitstellung und erklärt, was jeweils zu tun ist.

## Paketieren ohne Bereitstellung {#package-without-deploying}

`bbj-package` schreibt dasselbe Archiv, jedoch ohne Bereitstellungs-URL oder Zugangsdaten. Ein CI-Job kann `target/bbj/<publish name>.zip` an den Build anhängen. Anschließend stellt ein BBj-Administrator die App auf dem BBj-Rechner mit [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps) bereit.

## Bereitstellung aufheben {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

Die App wird angehalten und zusammen mit ihren Dateien aus dem Enterprise Manager entfernt.

:::warning Kann nicht rückgängig gemacht werden
`bbj-undeploy` beendet die laufenden Sitzungen der App und löscht ihren Bereitstellungsordner. Dies kann nicht rückgängig gemacht werden. Bewahren Sie das Archiv unter `target/bbj/<publish name>.zip` auf, falls Sie die App erneut bereitstellen möchten.
:::

## Veröffentlichungsnamen {#publish-names}

Der Veröffentlichungsname entspricht standardmäßig dem endgültigen Namen des Builds und erscheint in der App-Adresse. Er beginnt mit einem Buchstaben oder einer Ziffer, enthält nur Buchstaben, Ziffern, `_`, `.` und `-` und endet nicht mit einem Punkt. Reservierte Namen wie die BBjServices-Pfade `webapp` und `files` oder der Windows-Gerätename `con` werden abgelehnt, bevor das Archiv geschrieben wird.

Jede Einstiegsklasse gehört zu genau einer bereitgestellten App. Wird sie unter einem zweiten Namen bereitgestellt, schlägt der Vorgang mit folgender Meldung fehl:

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Von einem anderen Rechner aus bereitstellen {#deploying-from-another-machine}

Für eine Bereitstellung von einem anderen Rechner aus, beispielsweise einem CI-Server, wird ein Autorisierungstoken über HTTPS verwendet:

1. [Aktivieren Sie den Endpunkt](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) auf dem BBj-Rechner mit einem SSL-Port.
2. Erstellen Sie im Enterprise Manager unter „Security“ > „Authentication Tokens“ ein Token. Dort können Sie es auch erneuern und widerrufen.
3. Übergeben Sie das Token an den Build:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

Das Zertifikat des BBjServices-Webservers muss mit dem Hostnamen der URL übereinstimmen oder im Java-Truststore des Builds enthalten sein. Java vertraut dem selbst signierten Standardzertifikat von BBj nicht.

Eine `http://`-URL zu einem anderen Rechner wird auf beiden Seiten abgelehnt. Das Ziel wird beendet, bevor es etwas sendet, und BBjShip antwortet mit HTTP 403, wenn eine Anfrage über HTTP von einem anderen Rechner eingeht. Eine URL, die den BBj-Rechner selbst bezeichnet – `localhost` oder eine seiner eigenen Adressen –, funktioniert über HTTP.

Um trotzdem über HTTP auf einem anderen Rechner bereitzustellen, beispielsweise auf einer virtuellen Maschine auf dem eigenen Computer, müssen Sie dies auf beiden Seiten erlauben. Führen Sie auf dem BBj-Rechner `bbjship remote enable --allow-unencrypted` mit den Ports des Endpunkts aus. Setzen Sie im Build `<deployment><allowUnencrypted>true</allowUnencrypted>` oder übergeben Sie die Eigenschaft:

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=http://bbj.example.com:8888/webforj-bbjship \
  -Dwebforj.bbj.deployment.allowUnencrypted=true
```

Das Ziel gibt dann eine Warnung aus und führt die Bereitstellung durch. Die Zugangsdaten werden bei dieser Verbindung im Klartext über das Netzwerk übertragen.

## App-Einstellungen {#app-settings}

BBjShip liest bei jeder Bereitstellung die `webforj.conf` der App und wendet die darin enthaltenen Eigenschaften auf die App im Enterprise Manager an. Bei einer erneuten Bereitstellung wird ein geänderter Wert übernommen. Für nicht gesetzte Eigenschaften gilt der Standardwert von BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Eigenschaft | Standardwert | Beschreibung |
|---|---|---|
| `webforj.assetsSource` | Nicht gesetzt | Mit `packaged` werden die statischen Dateien `ws://` und die Symbole `icons://` aus der App unter `/webforj/<publish name>/` bereitgestellt. Ohne diese Einstellung kann eine App, die `icons://` verwendet, nicht gestartet werden. |
| `webforj.showClientConfirmationDialog` | `false` | Legt fest, ob der Browser vor dem Schließen der Seite eine Bestätigung anfordert. |
| `webforj.disallowConsole` | `true` | Legt fest, ob die Konsole in der App deaktiviert ist. |
| `webforj.secure` | BBjServices-Standardwert | Legt fest, ob die App über eine sichere Verbindung ausgeführt wird. |
| `webforj.configFile` | BBjServices-Standardwert | Pfad der BBj-Konfigurationsdatei, mit der die App ausgeführt wird, so wie BBjServices sie auf dem BBj-Rechner liest. Der Pfad wird unverändert übergeben und kann daher auf eine Datei verweisen, auf die nur der Server zugreifen kann. |
| `webforj.programArguments` | Keine | Programmargumente der App, entweder als Liste von Zeichenfolgen oder als einzelne Zeichenfolge. |

Eine ungültige `webforj.conf` führt dazu, dass die Bereitstellung mit HTTP 400 und einer Begründung fehlschlägt.

## Einstellungen {#settings}

Die Einstellungen befinden sich im `<bbj>`-Block der Konfiguration von `webforj-maven-plugin`, wie unter [Build-Plugin](/docs/configuration/bbjship/existing-projects#the-build-plugin) gezeigt. Jede Eigenschaft überschreibt das zugehörige Element in der Befehlszeile.

| Maven-Element | Maven-Eigenschaft | Standardwert | Beschreibung |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Automatisch erkannt | BBj-Installationsordner, der von `bbj-setup` und `watch` verwendet wird. |
| `<publishName>` | `webforj.bbj.publishName` | Endgültiger Name des Builds | Name, unter dem die App veröffentlicht wird; siehe [Veröffentlichungsnamen](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Keiner, erforderlich | Adresse von BBjShip: `http://<host>:<HTTP port>/webforj-bbjship` oder `https://<host>:<SSL port>/webforj-bbjship`. |
| `<deployment><allowUnencrypted>` | `webforj.bbj.deployment.allowUnencrypted` | `false` | Erlaubt `bbj-deploy` und `bbj-undeploy`, Zugangsdaten über eine `http://`-URL an einen anderen Rechner zu senden; siehe [Von einem anderen Rechner aus bereitstellen](#deploying-from-another-machine). |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin`, wenn Benutzername und Passwort beide fehlen | Benutzername des BBj-Administrators. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123`, wenn Benutzername und Passwort beide fehlen | Passwort des BBj-Administrators. |
| `<admin><token>` | `webforj.bbj.admin.token` | Leer | Autorisierungstoken für `bbj-deploy` und `bbj-undeploy`. Hat Vorrang vor Benutzername und Passwort. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | BBjServices-Eigenschaftendatei, in die ein [Entwicklungslauf](/docs/configuration/deploy-reload/bbjservices) die Hotswap-Einstellungen schreibt. |
| `<development><classFolder>` | — | Build-Ausgabeverzeichnis | Klassenordner eines Entwicklungslaufs, so wie BBjServices ihn liest. |
| — | `webforj.bbj.force` | `false` | Erlaubt `bbj-setup`, eine installierte BBjShip-Version durch eine andere Version zu ersetzen. Nur als Eigenschaft verfügbar. |

Ein Benutzername ohne Passwort oder ein Passwort ohne Benutzernamen führt dazu, dass das Ziel beendet wird.
