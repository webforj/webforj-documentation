---
title: Fehlerbehebung
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: c517c84b00b3b7eb6bb9d485c528c4f7
---
# Fehlerbehebung <DocChip chip='since' label='26.03' /> {#troubleshooting}

Auf dieser Seite sind die Meldungen aufgeführt, die bei der Einrichtung von BBjShip, beim Bereitstellen einer App, beim Starten eines Entwicklungslaufs oder bei Verwendung der Befehlszeile `bbjship` auftreten können. Für jede Meldung werden die Ursache und die erforderlichen Maßnahmen beschrieben.

Bei einem Maven-Ziel, das fehlschlägt, wird der Build mit einer kurzen Meldung zum Ziel beendet, gefolgt von der Ursache. Die Ursache ist der Teil, der auf dieser Seite aufgeführt ist:

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Anmeldedaten und Verbindung {#credentials-and-connection}

Diese Meldungen stammen von allen Zielen, die mit BBjShip kommunizieren: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` und `watch`.

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Es sind weder ein Benutzername noch ein Passwort konfiguriert. Daher meldet sich das Ziel als `admin` mit dem Passwort `admin123` an. | Ändern Sie das Passwort im Enterprise Manager und konfigurieren Sie die neuen Anmeldedaten. Siehe [Einstellungen](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Es ist nur der Benutzername oder das Passwort konfiguriert. | Konfigurieren Sie beide oder keines, um das Standardkonto zu verwenden. `bbj-deploy` und `bbj-undeploy` akzeptieren außerdem ein Token. |
| `BBj deployment requires bbj.deployment.url...` | Es ist keine Bereitstellungs-URL konfiguriert. | Legen Sie `<bbj><deployment><url>` fest oder übergeben Sie `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | Die URL verwendet ein anderes Schema oder enthält einen Benutzernamen, eine Abfrage oder ein Fragment. | Verwenden Sie `http://<host>:<port>/webforj-bbjship` oder `https://<host>:<port>/webforj-bbjship` und geben Sie die Anmeldedaten unter `<admin>` an. |
| `Connect to ... failed: Connection refused` | Auf dem Host und Port der Bereitstellungs-URL antwortet nichts. | Starten Sie BBjServices und überprüfen Sie Host und Port der URL anhand des Webservers von BBjServices. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | Die URL verweist nicht auf BBjShip, BBjShip ist nicht installiert oder der Endpunkt ist auf diesem Port nicht aktiviert. | Überprüfen Sie den Pfad `/webforj-bbjship` und den Port. Führen Sie auf dem BBj-Computer `mvn webforj:bbj-setup` aus oder auf einem Server `bbjship remote enable`. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices hat den Benutzernamen und das Passwort oder das Token zurückgewiesen. | Überprüfen Sie die Anmeldedaten. Erneuern Sie ein abgelaufenes oder im Enterprise Manager widerrufenes Token. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | Die installierte Version von BBjShip und die Version von `webforj-maven-plugin` unterscheiden sich. | Führen Sie auf Ihrem eigenen Computer `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. Installieren Sie auf einem Server die passende BBjShip-Version mit dem BBj Plugin Manager. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java vertraut dem Zertifikat des BBjServices-Webservers nicht. | Installieren Sie auf dem BBj-Computer ein Zertifikat für den Hostnamen der URL oder fügen Sie das Zertifikat zum Java-Truststore des Builds hinzu. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | Die Bereitstellungs-URL verwendet `http://` und verweist auf einen anderen Computer. Das Ziel wird beendet, bevor es Daten sendet. Mit `allowUnencrypted` wird derselbe Text als Warnung ausgegeben und das Ziel wird fortgesetzt. | Verwenden Sie eine `https://`-URL mit dem SSL-Port oder erlauben Sie HTTP auf beiden Seiten. Siehe [Bereitstellung von einem anderen Computer](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Einrichtung {#setup}

Diese Meldungen stammen von `mvn webforj:bbj-setup` und vom ersten [Entwicklungslauf](/docs/configuration/deploy-reload/bbjservices) eines Projekts, bei dem BBjShip auf dieselbe Weise eingerichtet wird.

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | Das Ziel ist fehlgeschlagen. Die Ursache folgt darauf. Bei einer Ursache zur Artefaktauflösung konnte Maven `webforj-bbjship-plugin` nicht in der Version von `webforj-maven-plugin` herunterladen. | Lesen Sie die Ursache. Überprüfen Sie bei Problemen mit der Artefaktauflösung die Repositories und den Netzwerkzugriff des Builds. |
| `BBj setup is local only. Run setup on the BBj host` | Die Bereitstellungs-URL verweist auf einen anderen Computer. | Führen Sie die Einrichtung auf dem BBj-Computer aus oder installieren Sie BBjShip dort mit dem [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | Der Hostname der Bereitstellungs-URL ist unbekannt. | Verwenden Sie `localhost` oder einen Namen dieses Computers. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Für einen Entwicklungslauf ist ein Token konfiguriert. Entwicklungsläufe melden sich nur mit Benutzername und Passwort an. | Konfigurieren Sie `<admin><username>` und `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | Das Ziel hat auf diesem Computer keine BBj-Installation gefunden. | Legen Sie `<bbj><home>`, `-Dwebforj.bbj.home` oder die Variable `BBJ_HOME` auf den Ordner der BBj-Installation fest. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | Der konfigurierte Ordner ist keine vollständige BBj-Installation. | Verweisen Sie mit der Einstellung auf den Ordner, der die BBj-Verzeichnisse `bin` und `cfg` enthält. |
| `Installed BBjShip version <version> does not match <version>...` | Es ist eine andere BBjShip-Version installiert, etwa nach einem webforJ-Upgrade. | Führen Sie `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. Bereitgestellte Apps bleiben bereitgestellt. |
| `Installed BBjShip version is missing...` | Der installierte BBjShip-Ordner ist unvollständig. | Führen Sie `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | Der BBj-Ordner `plugins` oder der darin enthaltene BBjShip-Ordner ist ein symbolischer Link oder enthält einen. | Installieren Sie in einem normalen Ordner. |
| `BBj setup command failed. Check credentials and local BBjServices.` gefolgt von der Ausgabe des Befehls | Der Befehl zum Aktivieren des Bereitstellungsendpunkts ist fehlgeschlagen. Die Ausgabe nennt die Ursache, zum Beispiel eine verweigerte Anmeldung oder einen Port, auf dem der Webserver nicht lauscht. | Überprüfen Sie die Anmeldedaten, ob BBjServices ausgeführt wird und ob der Port der Bereitstellungs-URL ein Port des BBjServices-Webservers ist. |
| `BBj setup command timed out. Check local BBjServices` | Der Befehl wurde nicht innerhalb von 120 Sekunden abgeschlossen. | Überprüfen Sie, ob BBjServices ausgeführt wird und antwortet, und führen Sie das Ziel dann erneut aus. |
| `Previous BBjShip files are preserved at <folder>` | Ein Austausch ist fehlgeschlagen, und die Dateien der vorherigen Version wurden in diesem Ordner aufbewahrt. | Beheben Sie die Ursache des Fehlers und führen Sie das Ziel erneut aus. |

## Bereitstellen und Entfernen der Bereitstellung {#deploy-and-undeploy}

### Vor dem Upload {#before-the-upload}

Diese Meldungen stammen von `bbj-package`, `bbj-deploy` und `bbj-undeploy`, bevor etwas BBjShip erreicht.

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | Das Projekt ist nicht als JAR gepackt. | Legen Sie `<packaging>jar</packaging>` fest. |
| `BBj deployment requires the packaged application jar...` | Das Ziel wurde ausgeführt, ohne dass sich die Phase `package` im selben Build befand. | Führen Sie `mvn install` oder `mvn package webforj:bbj-deploy` aus. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` befindet sich im Laufzeitklassenpfad der App. | Schließen Sie es aus der Abhängigkeit `webforj` aus. Siehe [Ohne das Install-Plugin](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | Die App hat zur Laufzeit keine webforJ-Abhängigkeit. | Fügen Sie die Abhängigkeit `webforj` mit dem Gültigkeitsbereich `compile` hinzu. |
| `BBj deployment dependency is unresolved...` | Für eine Abhängigkeit der App ist keine Datei vorhanden. | Überprüfen Sie, ob Maven alle Abhängigkeiten des Projekts auflösen kann. |
| `Publish name <name> is invalid...` | Der Veröffentlichungsname verstößt gegen die Regeln unter [Veröffentlichungsnamen](/docs/configuration/bbjship/deploying#publish-names). | Wählen Sie einen anderen `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices verwendet diesen Namen für eigene Adressen. | Wählen Sie einen anderen `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` konnte `target/bbj/<publish name>.zip` nicht schreiben. Die Ursache folgt darauf. | Lesen Sie die Ursache. |

### Antworten von BBjShip {#answers-of-bbjship}

Wenn BBjShip eine Bereitstellung oder das Entfernen einer Bereitstellung ablehnt, beginnt die Meldung mit `BBj deployment failed with HTTP <status>` oder `BBj undeployment failed with HTTP <status>`, gefolgt von der Ursache.

| Status | Ursache | Grund | Maßnahme |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` oder `...is reserved by BBjServices...` | Der Veröffentlichungsname wird abgelehnt. | Wählen Sie einen anderen Veröffentlichungsnamen. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | Die `webforj.conf` der App kann nicht gelesen werden oder eine [App-Einstellung](/docs/configuration/bbjship/deploying#app-settings) hat den falschen Typ. | Korrigieren Sie die Datei entsprechend den Angaben in der Detailmeldung. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` oder eine andere Ursache zum Archiv | Das Archiv wurde nicht vom Build-Plugin geschrieben oder nachträglich geändert. | Stellen Sie das vom Build erzeugte Archiv mit derselben webforJ-Version wie BBjShip bereit. |
| 401 | `Check the BBj administration credentials or token` | Die Anmeldung wurde abgelehnt. | Überprüfen Sie die Anmeldedaten oder erneuern Sie das Token. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | Die Anfrage kam über HTTP von einem anderen Computer. | Verwenden Sie die HTTPS-Adresse des Endpunkts oder führen Sie auf dem BBj-Computer `bbjship remote enable --allow-unencrypted` aus. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Ein Entwicklungslauf hat einen Computer erreicht, auf dem Entwicklungsläufe deaktiviert sind. | Führen Sie auf dem BBj-Computer `bbjship development enable` aus. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | Beim Entfernen der Bereitstellung wurde eine App angegeben, die nicht von BBjShip bereitgestellt wurde. | Überprüfen Sie den Veröffentlichungsnamen mit `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | Die Einstiegsklasse der App wird bereits unter einem anderen Veröffentlichungsnamen ausgeführt. | Entfernen Sie die Bereitstellung der anderen App oder stellen Sie die App unter deren Namen bereit. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Eine im Enterprise Manager registrierte App verwendet bereits diesen Namen. | Wählen Sie einen anderen Veröffentlichungsnamen oder entfernen Sie die App im Enterprise Manager. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Eine andere App verwendet denselben Namen mit anderer Groß- und Kleinschreibung. | Wählen Sie einen anderen Veröffentlichungsnamen. |
| 409 | `<name> is a development run. Stop the watch first` | Ein Entwicklungslauf der App ist aktiv. | Beenden Sie den Watch mit `Ctrl+C` und stellen Sie die App anschließend bereit. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Ein Entwicklungslauf verwendet den Namen einer bereitgestellten App. | Entfernen Sie die Bereitstellung der App oder legen Sie für den Entwicklungslauf einen anderen `<publishName>` fest. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | Das Archiv ist größer, als BBjShip zulässt. | Entfernen Sie Abhängigkeiten, die die App zur Laufzeit nicht benötigt. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices ist beim Installieren oder Entfernen der App fehlgeschlagen. | Lesen Sie die BBjServices-Protokolle. |

Eine Bereitstellung, die mit `did not confirm the requested application. Check the BBjShip version and BBjServices log` endet, hat BBjShip erreicht, aber keine Bestätigung für die App erhalten. Überprüfen Sie, ob BBjShip und `webforj-maven-plugin` dieselbe Version haben, und lesen Sie anschließend das BBjServices-Protokoll.

### Die laufende App {#the-running-app}

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | Die App verwendet `icons://`, ohne dass Assets gepackt sind. | Fügen Sie `webforj.assetsSource = packaged` zu `webforj.conf` hinzu und stellen Sie die App erneut bereit. |

## Entwicklungsläufe {#development-runs}

Diese Meldungen stammen von `mvn` oder `mvn compile webforj:watch` in einem für BBjShip konfigurierten Projekt. Ein fehlgeschlagener Entwicklungslauf beginnt mit `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, gefolgt von der Ursache aus [Einrichtung](#setup), [Anmeldedaten und Verbindung](#credentials-and-connection) oder [Antworten von BBjShip](#answers-of-bbjship).

| Meldung | Ursache | Maßnahme |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | Der Watch wurde gestartet, bevor das Projekt kompiliert wurde. | Führen Sie `mvn compile webforj:watch` oder in einem Archetype-Projekt `mvn` aus. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` gibt einen Ordner an, den BBjServices nicht finden kann. | Legen Sie den absoluten Pfad des Klassenordners auf dem BBj-Computer fest. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | Die BBjServices-Eigenschaftendatei befindet sich nicht an dem Ort, an dem der Lauf sie erwartet. | Legen Sie `<bbj><properties>` oder `-Dwebforj.bbj.properties` auf die Datei fest. |
| `BBjServices did not stop within 180 seconds` | Der Lauf hat BBjServices zum Anwenden der Hotswap-Einstellungen angehalten, aber BBjServices lief weiter. | Beenden Sie BBjServices selbst, starten Sie es erneut und führen Sie den Watch nochmals aus. |
| `BBjServices start program is missing: <program>` | In der BBj-Installation fehlt ein Programm zum Starten von BBjServices. | Starten Sie BBjServices selbst und führen Sie den Watch nochmals aus. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices wurde nach dem Neustart nicht wieder gestartet. | Starten Sie BBjServices selbst und führen Sie den Watch nochmals aus. |
| `BBjShip did not answer within 120 seconds` | BBjServices wurde gestartet, aber der Bereitstellungsendpunkt antwortete nicht. | Überprüfen Sie das BBjServices-Protokoll und führen Sie den Watch nochmals aus. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices wurde mit anderen Java-Argumenten gestartet als denen, die in die Eigenschaftendatei geschrieben wurden. | Überprüfen Sie die Java-Einstellungen von BBjServices im Enterprise Manager und in dieser Datei. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | Die App konnte beim Beenden des Builds nicht entfernt werden. | Entfernen Sie sie mit `bbjship apps undeploy <name>` oder starten Sie den Watch erneut, der sie dann ersetzt. |

Der erste Entwicklungslauf, bei dem die Hotswap-Einstellungen hinzugefügt werden, startet BBjServices neu. Unter macOS und Linux wird BBjServices mit `bin/bbjservices` gestartet. Unter Windows wird der Windows-Dienst `BBjServices` gestartet, sofern er registriert ist; andernfalls wird `bin\BBjServices.exe -noservice` ausgeführt. Ein mit `bin/bbjservices` oder `-noservice` gestartetes BBjServices läuft außerhalb des Dienst-Managers des Betriebssystems, bis der Computer neu gestartet wird.

## Befehlszeile {#command-line}

Jeder `bbjship`-Befehl wird innerhalb von BBjServices ausgeführt. Daher muss BBjServices laufen. Ohne BBjServices kann der Befehl nicht gestartet werden.

Bei ungültigen Argumenten wird der Vorgang mit dem Exitcode `2` und einer Meldung beendet, die angibt, was angegeben werden muss:

| Meldung | Maßnahme |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Geben Sie einen Befehl an. Siehe [Befehlszeile](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Verwenden Sie eine Option für diesen Befehl, wie in `--help` aufgeführt. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Geben Sie einen Wert für die Option an. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Geben Sie eine Portnummer an. |
| `Specify <option> only once` | Entfernen Sie die wiederholte Option. |
| `Specify only one of --password and --password-stdin` | Verwenden Sie nur eine der beiden Optionen. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Geben Sie Benutzername und Passwort zusammen an. Die Meldung nennt den fehlenden Wert. |
| `Specify an application name or --all`, `Specify one application name` | Geben Sie für `apps undeploy` einen Veröffentlichungsnamen oder `--all` an. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Geben Sie für `apps deploy` eine `.zip`-Datei an. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Überprüfen Sie den Pfad. BBjServices liest die Datei. Daher benötigt der Betriebssystembenutzer von BBjServices Leseberechtigung dafür. |
| `Development archives are deployed by the watch, not by apps deploy` | Stellen Sie das von `bbj-package` erzeugte Archiv bereit, nicht das eines Entwicklungslaufs. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Benennen Sie das Archiv um. Der Dateiname ohne `.zip` ist der Veröffentlichungsname. |

Ein fehlgeschlagener Befehl wird mit dem Exitcode `1` und `Command failed.` beendet, gefolgt von der Ursache:

| Ursache | Grund | Maßnahme |
|---|---|---|
| Ein Anmeldefehler von BBjServices | Der Benutzername oder das Passwort ist falsch. | Überprüfen Sie die Anmeldedaten. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` oder `--ssl-port` ist kein Port, auf dem der BBjServices-Webserver lauscht. Der vorherige Endpunkt bleibt bestehen. | Verwenden Sie die im Enterprise Manager für den Webserver konfigurierten Ports. |
| `Application <name> is not deployed by BBjShip` | Für `apps undeploy` wurde eine App angegeben, die nicht von BBjShip bereitgestellt wurde. | Überprüfen Sie den Namen mit `bbjship apps list`. |
| `Another deployment is in progress` | Eine andere Bereitstellung oder das Entfernen einer Bereitstellung hat den Computer 30 Sekunden lang belegt. | Warten Sie, bis der Vorgang abgeschlossen ist, und führen Sie den Befehl erneut aus. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Eine andere App im Enterprise Manager verwendet den Webkontext `webforj-bbjship` oder `webforj-bbjship-assets`. | Benennen Sie diesen Kontext im Enterprise Manager um oder entfernen Sie ihn dort. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Die Klassenpfade `_webforj_bbjship_cli` oder `_webforj_bbjship` im Enterprise Manager wurden geändert. | Entfernen Sie beide Klassenpfade im Enterprise Manager und führen Sie den Befehl erneut aus, wodurch sie neu erstellt werden. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | Der BBjShip-Ordner ist unvollständig. | Installieren Sie BBjShip erneut. |
| `BBjServices does not manage web contexts. Update BBj` | Die BBj-Version ist für BBjShip zu alt. | Installieren Sie die BBj-Version, die zu webforJ passt. |

`reset` wird mit dem Exitcode `1` und `Reset cancelled` beendet, wenn die Antwort auf `Continue? [y/N]` nicht `y` oder `Y` lautet. Wenn die Java-Einstellungen von BBjServices wiederhergestellt wurden, wird der Vorgang mit `Restart BBjServices now to apply the restored Java settings` beendet. Starten Sie BBjServices neu, damit es ohne die Hotswap-Einstellungen ausgeführt wird.

## Windows {#windows}

| Situation | Maßnahme |
|---|---|
| Bei Ausführung von `bbj.exe` mit dem BBjShip-Programm wird nichts ausgegeben. | Führen Sie `bbjship.cmd` in `<BBj home>\plugins\webforj-bbjship` aus. Damit wird die Befehlsausgabe angezeigt. |
| `--password-stdin` wird mit dem Exitcode `2` und `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD` beendet. | Übergeben Sie das Passwort mit `--password` oder legen Sie `WEBFORJ_BBJ_USERNAME` und `WEBFORJ_BBJ_PASSWORD` fest. |
| `reset` wird mit dem Exitcode `2` und `Standard input is not available on Windows. Run reset --yes to confirm` beendet. | Führen Sie `bbjship.cmd reset --yes` aus. |
| `apps undeploy` oder `reset` gibt `Not removed: <folder>. <reason>` aus und wird mit dem Exitcode `0` beendet. | BBjServices hält noch Dateien der App geöffnet. Die App wird aus dem Enterprise Manager entfernt. Starten Sie BBjServices neu. Der Ordner wird bei der nächsten Bereitstellung mit diesem Namen entfernt oder kann nach dem Neustart gelöscht werden. |
| Die Installation von BBjShip über eine vorhandene Version schlägt fehl, weil deren Dateien verwendet werden. | BBjServices hält noch Dateien der installierten BBjShip-Version geöffnet. Starten Sie BBjServices neu und installieren Sie BBjShip anschließend erneut. |
| BBjServices wird nach einem Neustart durch einen Entwicklungslauf nicht als Windows-Dienst ausgeführt. | Der Entwicklungslauf hat BBjServices mit `BBjServices.exe -noservice` gestartet, weil kein Dienst `BBjServices` registriert ist. Es läuft außerhalb des Windows-Dienst-Managers, bis der Computer neu gestartet wird. |
| Ein Befehl von `bbjship.cmd` kann nicht gestartet werden. | Starten Sie BBjServices. Jeder `bbjship`-Befehl benötigt einen laufenden BBjServices-Prozess. |
