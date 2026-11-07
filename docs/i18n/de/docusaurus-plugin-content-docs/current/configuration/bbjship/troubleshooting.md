---
title: Fehlerbehebung
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: 2db5dad70f56c06a6aab75752a9c260f
---
# Fehlerbehebung <DocChip chip='since' label='26.03' /> {#troubleshooting}

Auf dieser Seite sind die Meldungen aufgeführt, die beim Einrichten von BBjShip, beim Bereitstellen einer App, beim Starten eines Entwicklungslaufs oder bei der Verwendung der Befehlszeile `bbjship` auftreten können. Für jede Meldung werden die Ursache und die erforderlichen Maßnahmen beschrieben.

Bei einem fehlschlagenden Maven-Ziel endet der Build mit einer kurzen Meldung zum Ziel, gefolgt von der Ursache. Die Ursache ist der Teil, der auf dieser Seite aufgeführt ist:

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Zugangsdaten und Verbindung {#credentials-and-connection}

Diese Meldungen stammen von allen Zielen, die mit BBjShip kommunizieren: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` und `watch`.

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Weder Benutzername noch Passwort sind konfiguriert. Daher meldet sich das Ziel mit `admin` und `admin123` an. | Ändern Sie das Passwort im Enterprise Manager und konfigurieren Sie die neuen Zugangsdaten. Siehe [Einstellungen](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Es ist nur der Benutzername oder das Passwort konfiguriert. | Konfigurieren Sie beides oder keines, um das Standardkonto zu verwenden. `bbj-deploy` und `bbj-undeploy` akzeptieren auch ein Token. |
| `BBj deployment requires bbj.deployment.url...` | Es ist keine Bereitstellungs-URL konfiguriert. | Legen Sie `<bbj><deployment><url>` fest oder übergeben Sie `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | Die URL verwendet ein anderes Schema oder enthält einen Benutzernamen, eine Abfrage oder ein Fragment. | Verwenden Sie `http://<host>:<port>/webforj-bbjship` oder `https://<host>:<port>/webforj-bbjship` und tragen Sie die Zugangsdaten unter `<admin>` ein. |
| `Connect to ... failed: Connection refused` | Auf dem Host und Port der Bereitstellungs-URL nimmt nichts Verbindungen entgegen. | Starten Sie BBjServices und prüfen Sie, ob Host und Port der URL mit dem Webserver von BBjServices übereinstimmen. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | Die URL verweist nicht auf BBjShip, BBjShip ist nicht installiert oder der Endpunkt ist auf diesem Port nicht aktiviert. | Prüfen Sie den Pfad `/webforj-bbjship` und den Port. Führen Sie auf dem BBj-Rechner `mvn webforj:bbj-setup` oder auf einem Server `bbjship remote enable` aus. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices hat den Benutzernamen und das Passwort oder das Token abgelehnt. | Prüfen Sie die Zugangsdaten. Erneuern Sie ein abgelaufenes oder im Enterprise Manager widerrufenes Token. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | Die installierte Version von BBjShip und die Version von `webforj-maven-plugin` unterscheiden sich. | Führen Sie auf Ihrem Rechner `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. Installieren Sie auf einem Server die passende BBjShip-Version mit dem BBj Plugin Manager. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java vertraut dem Zertifikat des BBjServices-Webservers nicht. | Installieren Sie auf dem BBj-Rechner ein Zertifikat für den Hostnamen der URL oder fügen Sie dessen Zertifikat zum Java-Truststore des Builds hinzu. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | Die Bereitstellungs-URL verwendet `http://` und verweist auf einen anderen Rechner. Das Ziel wird beendet, bevor es etwas sendet. Mit `allowUnencrypted` wird derselbe Text als Warnung ausgegeben und das Ziel fortgesetzt. | Verwenden Sie eine `https://`-URL mit dem SSL-Port oder erlauben Sie HTTP auf beiden Seiten. Siehe [Bereitstellung von einem anderen Rechner](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Einrichtung {#setup}

Diese Meldungen stammen von `mvn webforj:bbj-setup` und vom ersten [Entwicklungslauf](/docs/configuration/deploy-reload/bbjservices) eines Projekts, bei dem BBjShip auf dieselbe Weise eingerichtet wird.

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | Das Ziel ist fehlgeschlagen. Die Ursache folgt danach. Weist die Ursache auf eine Artefaktauflösung hin, konnte Maven `webforj-bbjship-plugin` in der Version von `webforj-maven-plugin` nicht herunterladen. | Lesen Sie die Ursache. Prüfen Sie bei Problemen mit der Artefaktauflösung die Repositories und den Netzwerkzugriff des Builds. |
| `BBj setup is local only. Run setup on the BBj host` | Die Bereitstellungs-URL verweist auf einen anderen Rechner. | Führen Sie die Einrichtung auf dem BBj-Rechner aus oder installieren Sie BBjShip dort mit dem [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | Der Hostname der Bereitstellungs-URL ist unbekannt. | Verwenden Sie `localhost` oder einen Namen dieses Rechners. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Für einen Entwicklungslauf ist ein Token konfiguriert. Entwicklungsläufe melden sich nur mit Benutzername und Passwort an. | Konfigurieren Sie `<admin><username>` und `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | Das Ziel hat auf diesem Rechner keine BBj-Installation gefunden. | Legen Sie `<bbj><home>`, `-Dwebforj.bbj.home` oder die Variable `BBJ_HOME` auf den Ordner der BBj-Installation fest. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | Der konfigurierte Ordner ist keine vollständige BBj-Installation. | Verweisen Sie mit der Einstellung auf den Ordner, der die BBj-Verzeichnisse `bin` und `cfg` enthält. |
| `Installed BBjShip version <version> does not match <version>...` | Es ist eine andere BBjShip-Version installiert, beispielsweise nach einem webforJ-Upgrade. | Führen Sie `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. Bereitgestellte Apps bleiben bereitgestellt. |
| `Installed BBjShip version is missing...` | Der installierte BBjShip-Ordner ist unvollständig. | Führen Sie `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | Der BBj-Ordner `plugins` oder der darin enthaltene BBjShip-Ordner ist ein symbolischer Link oder enthält einen solchen. | Installieren Sie in einem regulären Ordner. |
| `BBj setup command failed. Check credentials and local BBjServices.` gefolgt von der Ausgabe des Befehls | Der Befehl zum Aktivieren des Bereitstellungsendpunkts ist fehlgeschlagen. Die Ausgabe nennt die Ursache, beispielsweise eine abgelehnte Anmeldung oder einen Port, auf dem der Webserver nicht lauscht. | Prüfen Sie die Zugangsdaten, ob BBjServices läuft und ob der Port der Bereitstellungs-URL ein Port des BBjServices-Webservers ist. |
| `BBj setup command timed out. Check local BBjServices` | Der Befehl wurde nicht innerhalb von 120 Sekunden abgeschlossen. | Prüfen Sie, ob BBjServices läuft und antwortet, und führen Sie das Ziel anschließend erneut aus. |
| `Previous BBjShip files are preserved at <folder>` | Das Ersetzen ist fehlgeschlagen, und die Dateien der vorherigen Version wurden in diesem Ordner beibehalten. | Beheben Sie die Fehlerursache und führen Sie das Ziel erneut aus. |

## Bereitstellen und Entfernen {#deploy-and-undeploy}

### Vor dem Hochladen {#before-the-upload}

Diese Meldungen stammen von `bbj-package`, `bbj-deploy` und `bbj-undeploy`, bevor irgendetwas BBjShip erreicht.

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | Das Projekt ist nicht als JAR gepackt. | Legen Sie `<packaging>jar</packaging>` fest. |
| `BBj deployment requires the packaged application jar...` | Das Ziel wurde ohne die Phase `package` im selben Build ausgeführt. | Führen Sie `mvn install` oder `mvn package webforj:bbj-deploy` aus. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` befindet sich im Laufzeit-Classpath der App. | Schließen Sie es aus der Abhängigkeit `webforj` aus. Siehe [Ohne das Installations-Plugin](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | Die App hat zur Laufzeit keine webforJ-Abhängigkeit. | Fügen Sie die Abhängigkeit `webforj` mit dem Gültigkeitsbereich `compile` hinzu. |
| `BBj deployment dependency is unresolved...` | Für eine Abhängigkeit der App fehlt eine Datei. | Prüfen Sie, ob Maven alle Abhängigkeiten des Projekts auflösen kann. |
| `Publish name <name> is invalid...` | Der Bereitstellungsname verstößt gegen die Regeln unter [Bereitstellungsnamen](/docs/configuration/bbjship/deploying#publish-names). | Wählen Sie einen anderen `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices verwendet diesen Namen für eigene Adressen. | Wählen Sie einen anderen `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` konnte `target/bbj/<publish name>.zip` nicht schreiben. Die Ursache folgt danach. | Lesen Sie die Ursache. |

### Antworten von BBjShip {#answers-of-bbjship}

Wenn BBjShip eine Bereitstellung oder das Entfernen einer App ablehnt, beginnt die Meldung mit `BBj deployment failed with HTTP <status>` oder `BBj undeployment failed with HTTP <status>`, gefolgt von der Ursache.

| Status | Ursache | Grund | Maßnahme |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` oder `...is reserved by BBjServices...` | Der Bereitstellungsname wird abgelehnt. | Wählen Sie einen anderen Bereitstellungsnamen. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | Die `webforj.conf` der App kann nicht gelesen werden oder eine [App-Einstellung](/docs/configuration/bbjship/deploying#app-settings) hat den falschen Typ. | Korrigieren Sie die Datei entsprechend der angegebenen Einzelheiten. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` oder eine andere Ursache zum Archiv | Das Archiv wurde nicht vom Build-Plugin erstellt oder nachträglich geändert. | Stellen Sie das vom Build erstellte Archiv mit derselben webforJ-Version wie BBjShip bereit. |
| 401 | `Check the BBj administration credentials or token` | Die Anmeldung wurde abgelehnt. | Prüfen Sie die Zugangsdaten oder erneuern Sie das Token. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | Die Anfrage kam über HTTP von einem anderen Rechner. | Verwenden Sie die HTTPS-Adresse des Endpunkts oder führen Sie auf dem BBj-Rechner `bbjship remote enable --allow-unencrypted` aus. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Ein Entwicklungslauf hat einen Rechner erreicht, auf dem Entwicklungsläufe deaktiviert sind. | Führen Sie auf dem BBj-Rechner `bbjship development enable` aus. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | Mit dem Befehl zum Entfernen wurde eine App angegeben, die nicht von BBjShip bereitgestellt wurde. | Prüfen Sie den Bereitstellungsnamen mit `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | Die Einstiegsklasse der App läuft bereits unter einem anderen Bereitstellungsnamen. | Entfernen Sie die andere App oder stellen Sie die App unter deren Namen bereit. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Eine im Enterprise Manager registrierte App verwendet bereits diesen Namen. | Wählen Sie einen anderen Bereitstellungsnamen oder entfernen Sie die App im Enterprise Manager. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Eine andere App verwendet denselben Namen mit anderer Groß-/Kleinschreibung. | Wählen Sie einen anderen Bereitstellungsnamen. |
| 409 | `<name> is a development run. Stop the watch first` | Ein Entwicklungslauf der App ist aktiv. | Beenden Sie den Watch-Prozess mit <kbd>Ctrl</kbd>+<kbd>C</kbd> und stellen Sie die App anschließend bereit. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Ein Entwicklungslauf verwendet den Namen einer bereitgestellten App. | Entfernen Sie die App oder legen Sie für den Entwicklungslauf einen anderen `<publishName>` fest. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | Das Archiv ist größer als die von BBjShip akzeptierte Größe. | Entfernen Sie Abhängigkeiten, die die App zur Laufzeit nicht benötigt. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices ist beim Installieren oder Entfernen der App fehlgeschlagen. | Lesen Sie die BBjServices-Protokolle. |

Eine Bereitstellung, die mit `did not confirm the requested application. Check the BBjShip version and BBjServices log` endet, hat BBjShip erreicht, aber keine Bestätigung für die App erhalten. Prüfen Sie, ob BBjShip und `webforj-maven-plugin` dieselbe Version haben, und lesen Sie anschließend das BBjServices-Protokoll.

### Die laufende App {#the-running-app}

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | Die App verwendet `icons://` ohne gepackte Assets. | Fügen Sie `webforj.assetsSource = packaged` zu `webforj.conf` hinzu und stellen Sie die App erneut bereit. |

## Entwicklungsläufe {#development-runs}

Diese Meldungen stammen von `mvn` oder `mvn compile webforj:watch` in einem für BBjShip konfigurierten Projekt. Ein fehlgeschlagener Entwicklungslauf beginnt mit `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, gefolgt von der Ursache aus [Einrichtung](#setup), [Zugangsdaten und Verbindung](#credentials-and-connection) oder [Antworten von BBjShip](#answers-of-bbjship).

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | Der Watch-Prozess wurde gestartet, bevor das Projekt kompiliert wurde. | Führen Sie `mvn compile webforj:watch` oder in einem Archetype-Projekt `mvn` aus. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` gibt einen Ordner an, den BBjServices nicht finden kann. | Legen Sie den absoluten Pfad des Klassenordners auf dem BBj-Rechner fest. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | Die BBjServices-Properties-Datei befindet sich nicht an dem vom Lauf erwarteten Speicherort. | Legen Sie `<bbj><properties>` oder `-Dwebforj.bbj.properties` auf den Dateipfad fest. |
| `BBjServices did not stop within 180 seconds` | Der Lauf hat BBjServices zum Anwenden der Hotswap-Einstellungen angehalten, aber BBjServices lief weiter. | Beenden Sie BBjServices selbst, starten Sie es erneut und führen Sie den Watch-Prozess erneut aus. |
| `BBjServices start program is missing: <program>` | In der BBj-Installation fehlt ein Programm zum Starten von BBjServices. | Starten Sie BBjServices selbst und führen Sie den Watch-Prozess erneut aus. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices wurde nach dem Neustart nicht wieder gestartet. | Starten Sie BBjServices selbst und führen Sie den Watch-Prozess erneut aus. |
| `BBjShip did not answer within 120 seconds` | BBjServices wurde gestartet, aber der Bereitstellungsendpunkt antwortete nicht. | Prüfen Sie das BBjServices-Protokoll und führen Sie den Watch-Prozess anschließend erneut aus. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices wurde mit anderen Java-Argumenten als den in der Properties-Datei gespeicherten gestartet. | Prüfen Sie die Java-Einstellungen von BBjServices im Enterprise Manager und in dieser Datei. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | Die App konnte beim Beenden des Builds nicht entfernt werden. | Entfernen Sie sie mit `bbjship apps undeploy <name>` oder starten Sie den Watch-Prozess erneut, der die App dann ersetzt. |

Beim ersten Entwicklungslauf, der die Hotswap-Einstellungen hinzufügt, wird BBjServices neu gestartet. Unter macOS und Linux wird BBjServices mit `bin/bbjservices` gestartet. Unter Windows wird der Windows-Dienst `BBjServices` gestartet, sofern er registriert ist; andernfalls wird `bin\BBjServices.exe -noservice` ausgeführt. Ein mit `bin/bbjservices` oder `-noservice` gestartetes BBjServices läuft bis zum Neustart des Rechners außerhalb der Dienstverwaltung des Betriebssystems.

## Befehlszeile {#command-line}

Jeder `bbjship`-Befehl wird innerhalb von BBjServices ausgeführt. Daher muss BBjServices laufen. Ohne BBjServices kann der Befehl nicht gestartet werden.

Ungültige Argumente führen zum Beendigungscode `2` und zu einer Meldung, die angibt, was angegeben werden muss:

| Meldung | Maßnahme |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Geben Sie einen Befehl an. Siehe [Befehlszeile](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Verwenden Sie eine Option des Befehls, wie unter `--help` aufgeführt. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Geben Sie einen Wert für die Option an. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Geben Sie eine Portnummer an. |
| `Specify <option> only once` | Entfernen Sie die wiederholte Option. |
| `Specify only one of --password and --password-stdin` | Verwenden Sie nur eine der beiden Optionen. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Geben Sie Benutzername und Passwort zusammen an. Die Meldung nennt den fehlenden Wert. |
| `Specify an application name or --all`, `Specify one application name` | Geben Sie für `apps undeploy` einen Bereitstellungsnamen oder `--all` an. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Geben Sie für `apps deploy` eine `.zip`-Datei an. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Prüfen Sie den Pfad. BBjServices liest die Datei. Daher benötigt der Betriebssystembenutzer von BBjServices Leseberechtigung dafür. |
| `Development archives are deployed by the watch, not by apps deploy` | Stellen Sie das Archiv von `bbj-package` bereit, nicht das eines Entwicklungslaufs. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Benennen Sie das Archiv um. Der Dateiname ohne `.zip` ist der Bereitstellungsname. |

Ein fehlschlagender Befehl endet mit dem Beendigungscode `1` und `Command failed.`, gefolgt von der Ursache:

| Ursache | Grund | Maßnahme |
|---|---|---|
| Eine fehlgeschlagene Anmeldung bei BBjServices | Benutzername oder Passwort ist falsch. | Prüfen Sie die Zugangsdaten. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` oder `--ssl-port` ist kein Port, auf dem der BBjServices-Webserver lauscht. Der vorherige Endpunkt bleibt bestehen. | Verwenden Sie die im Enterprise Manager für den Webserver konfigurierten Ports. |
| `Application <name> is not deployed by BBjShip` | Mit `apps undeploy` wurde eine App angegeben, die nicht von BBjShip bereitgestellt wurde. | Prüfen Sie den Namen mit `bbjship apps list`. |
| `Another deployment is in progress` | Eine andere Bereitstellung oder ein anderer Entfernungsbefehl hat den Rechner 30 Sekunden lang belegt. | Warten Sie, bis der Vorgang abgeschlossen ist, und führen Sie den Befehl erneut aus. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Eine andere App im Enterprise Manager verwendet den Webkontext `webforj-bbjship` oder `webforj-bbjship-assets`. | Benennen Sie diesen Kontext im Enterprise Manager um oder entfernen Sie ihn. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Die Classpaths `_webforj_bbjship_cli` oder `_webforj_bbjship` im Enterprise Manager wurden geändert. | Entfernen Sie beide Classpaths im Enterprise Manager und führen Sie den Befehl erneut aus, damit sie neu erstellt werden. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | Der BBjShip-Ordner ist unvollständig. | Installieren Sie BBjShip erneut. |
| `BBjServices does not manage web contexts. Update BBj` | Die BBj-Version ist zu alt für BBjShip. | Installieren Sie die BBj-Version, die zu webforJ passt. |

`reset` endet mit dem Beendigungscode `1` und `Reset cancelled`, wenn die Antwort auf `Continue? [y/N]` nicht `y` oder `Y` lautet. Wenn die Java-Einstellungen von BBjServices wiederhergestellt wurden, endet der Befehl mit `Restart BBjServices now to apply the restored Java settings`. Starten Sie BBjServices neu, damit es ohne die Hotswap-Einstellungen ausgeführt wird.

## Windows {#windows}

| Situation | Maßnahme |
|---|---|
| Bei Ausführung von `bbj.exe` mit dem BBjShip-Programm wird nichts ausgegeben. | Führen Sie `bbjship.cmd` in `<BBj home>\plugins\webforj-bbjship` aus. Dieser Befehl zeigt die Ausgabe des Befehls an. |
| `--password-stdin` endet mit dem Beendigungscode `2` und `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Übergeben Sie das Passwort mit `--password` oder legen Sie `WEBFORJ_BBJ_USERNAME` und `WEBFORJ_BBJ_PASSWORD` fest. |
| `reset` endet mit dem Beendigungscode `2` und `Standard input is not available on Windows. Run reset --yes to confirm`. | Führen Sie `bbjship.cmd reset --yes` aus. |
| `apps undeploy` oder `reset` gibt `Not removed: <folder>. <reason>` aus und endet mit dem Beendigungscode `0`. | BBjServices hält noch Dateien der App geöffnet. Die App wird aus dem Enterprise Manager entfernt. Starten Sie BBjServices neu. Der Ordner wird bei der nächsten Bereitstellung mit diesem Namen entfernt. Alternativ können Sie ihn nach dem Neustart löschen. |
| Die Installation von BBjShip über eine vorhandene Version schlägt fehl, weil deren Dateien verwendet werden. | BBjServices hält noch Dateien der installierten BBjShip-Version geöffnet. Starten Sie BBjServices neu und installieren Sie BBjShip anschließend erneut. |
| BBjServices läuft nach einem Neustart durch einen Entwicklungslauf nicht als Windows-Dienst. | Der Entwicklungslauf hat BBjServices mit `BBjServices.exe -noservice` gestartet, weil kein Dienst `BBjServices` registriert ist. BBjServices läuft bis zum Neustart des Rechners außerhalb der Windows-Dienstverwaltung. |
| Ein Befehl mit `bbjship.cmd` kann nicht gestartet werden. | Starten Sie BBjServices. Jeder `bbjship`-Befehl benötigt einen laufenden BBjServices-Prozess. |
