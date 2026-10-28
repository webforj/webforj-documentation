---
title: Fehlerbehandlung
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: d9235c64dc2d56c5acc41e60b2a2f7df
---
# Fehlersuche <DocChip chip='since' label='26.03' /> {#troubleshooting}

Diese Seite listet die Meldungen auf, die Sie bei der Einrichtung von BBjShip, beim Bereitstellen einer App, beim Start eines Entwicklungsdurchlaufs oder bei der Verwendung des `bbjship` Befehlszeilenwerkzeugs antreffen können. Für jede Meldung gibt es die Ursache und was zu tun ist.

Ein fehlgeschlagenes Maven-Ziel beendet den Build mit einer kurzen Nachricht über das Ziel, gefolgt von dem Grund. Der Grund ist der Teil, den diese Seite auflistet:

```text
BBj-Bereitstellung fehlgeschlagen. Überprüfen Sie das Anwendungs-Paket, die Abhängigkeiten und die Bereitstellungseinstellungen: BBj-Bereitstellung fehlgeschlagen mit HTTP 409. Einstiegsklasse com.example.Application ist bereits als Inventar veröffentlicht. Entfernen Sie zuerst das Inventar.
```

## Anmeldeinformationen und Verbindung {#credentials-and-connection}

Diese Meldungen stammen von jedem Ziel, das mit BBjShip kommuniziert: `bbj-setup`, `bbj-deploy`, `bbj-undeploy` und `watch`.

| Meldung | Ursache | Was zu tun ist |
|---|---|---|
| `Warnung: Das BBj-Fabrikadministratorkonto admin wird mit seinem Fabrikpasswort verwendet...` | Weder ein Benutzername noch ein Passwort sind konfiguriert, sodass das Ziel sich als `admin` mit `admin123` anmeldet. | Ändern Sie das Passwort im Enterprise Manager und konfigurieren Sie die neuen Anmeldeinformationen, siehe [Einstellungen](/docs/configuration/bbjship/deploying#settings). |
| `Der Benutzername des BBj-Administrators fehlt`, `Das Passwort des BBj-Administrators fehlt` | Nur einer von Benutzername und Passwort ist konfiguriert. | Konfigurieren Sie beide oder keines, um das Fabrik-Konto zu verwenden. `bbj-deploy` und `bbj-undeploy` akzeptieren auch ein Token. |
| `BBj-Bereitstellung erfordert bbj.deployment.url...` | Es ist keine Bereitstellungs-URL konfiguriert. | Setzen Sie `<bbj><deployment><url>` oder übergeben Sie `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj-Bereitstellungs-URL muss eine HTTP- oder HTTPS-URL ohne Anmeldeinformationen, Abfrage oder Fragment sein` | Die URL hat ein anderes Schema oder enthält einen Benutzernamen, eine Abfrage oder ein Fragment. | Verwenden Sie `http://<host>:<port>/webforj-bbjship` oder `https://<host>:<port>/webforj-bbjship`, und geben Sie die Anmeldeinformationen in `<admin>` an. |
| `Verbindung zu ... fehlgeschlagen: Verbindung abgelehnt` | Nichts beantwortet auf dem Host und Port der Bereitstellungs-URL. | Starten Sie BBjServices und überprüfen Sie den Host und den Port der URL gegen den BBjServices-Webserver. |
| `konnte die BBjShip-Version unter <URL> nicht lesen, HTTP 404...` | Die URL führt nicht zu BBjShip, oder BBjShip ist nicht installiert oder sein Endpunkt ist auf diesem Port nicht aktiviert. | Überprüfen Sie den Pfad `/webforj-bbjship` und den Port. Führen Sie auf der BBj-Maschine `mvn webforj:bbj-setup` aus oder verwenden Sie `bbjship remote enable` auf einem Server. |
| `fehlgeschlagen mit HTTP 401. Überprüfen Sie die BBj-Administrationsanmeldeinformationen oder das Token` | BBjServices hat den Benutzernamen und das Passwort oder das Token abgelehnt. | Überprüfen Sie die Anmeldeinformationen. Erneuern Sie ein Token, das abgelaufen oder zurückgezogen wurde im Enterprise Manager. |
| `BBjShip <version> unter <URL> stimmt nicht mit webforJ <version> überein...` | Die installierte BBjShip-Version und das `webforj-maven-plugin` haben unterschiedliche Versionen. | Führen Sie auf Ihrem eigenen Rechner `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. Auf einem Server installieren Sie das passende BBjShip mit dem BBj Plugin Manager. |
| `konnte keine vertrauenswürdige HTTPS-Verbindung zu <URL> herstellen...` | Java vertraut dem Zertifikat des BBjServices-Webservers nicht. | Installieren Sie ein Zertifikat für den Hostnamen der URL auf der BBj-Maschine oder fügen Sie dessen Zertifikat dem Java-Truststore des Builds hinzu. |
| `BBj-Bereitstellungs-URL <URL> sendet die Administrationsanmeldeinformationen lesbar über das Netzwerk...` | Eine Warnung für eine `http://`-URL zu einem anderen Rechner. | Verwenden Sie eine `https://`-URL mit dem SSL-Port, siehe [Bereitstellung von einem anderen Rechner](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Einrichtung {#setup}

Diese Meldungen stammen von `mvn webforj:bbj-setup` und vom ersten [Entwicklungsdurchlauf](/docs/configuration/deploy-reload/bbjservices) eines Projekts, der BBjShip auf die gleiche Weise einrichtet.

| Meldung | Ursache | Was zu tun ist |
|---|---|---|
| `BBj-Einrichtung fehlgeschlagen. Überprüfen Sie das BBj-Plugin-Artefakt und die Einrichtungseinstellungen` | Das Ziel ist fehlgeschlagen. Der Grund folgt ihm. Ein Grund zu Artefaktauflösung bedeutet, dass Maven `webforj-bbjship-plugin` in der Version des `webforj-maven-plugin` nicht herunterladen konnte. | Lesen Sie den Grund. Überprüfen Sie bei der Artefaktauflösung die Repositories und den Netzwerkzugang des Builds. |
| `BBj-Einrichtung ist nur lokal. Führen Sie die Einrichtung auf dem BBj-Host aus` | Die Bereitstellungs-URL nennt einen anderen Rechner. | Führen Sie die Einrichtung auf der BBj-Maschine durch oder installieren Sie BBjShip dort mit dem [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj-Einrichtung kann den Host der Bereitstellungs-URL <host> nicht auflösen...` | Der Hostname der Bereitstellungs-URL ist unbekannt. | Verwenden Sie `localhost` oder einen Namen dieses Computers. |
| `BBj-Einrichtung erfordert das Administrationspasswort. Konfigurieren Sie bbj.admin.password anstelle eines Tokens` | Ein Entwicklungsdurchlauf hat ein Token konfiguriert. Entwicklungsdurchläufe melden sich nur mit Benutzername und Passwort an. | Konfigurieren Sie `<admin><username>` und `<admin><password>`. |
| `Kann keine BBj-Installation erkennen. Konfigurieren Sie bbj.home oder BBJ_HOME` | Das Ziel hat auf diesem Rechner keine BBj-Installation gefunden. | Setzen Sie `<bbj><home>`, `-Dwebforj.bbj.home` oder die Variable `BBJ_HOME` auf den Ordner der BBj-Installation. |
| `Ungültiges BBj-Home. Konfigurieren Sie eine lokale BBj-Installation`, `BBj ausführbare Datei fehlt oder ist nicht ausführbar. Überprüfen Sie bbj.home` | Der konfigurierte Ordner ist keine vollständige BBj-Installation. | Zeigen Sie die Einstellung auf den Ordner, der `bin` und `cfg` von BBj enthält. |
| `Installierte BBjShip-Version <version> stimmt nicht mit <version> überein...` | Eine andere BBjShip-Version ist installiert, zum Beispiel nach einem Upgrade von webforJ. | Führen Sie `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. Bereitgestellte Apps bleiben bereitgestellt. |
| `Installierte BBjShip-Version fehlt...` | Der installierte BBjShip-Ordner ist unvollständig. | Führen Sie `mvn webforj:bbj-setup -Dwebforj.bbj.force=true` aus. |
| `Das Plugin-Verzeichnis ist ein symbolischer Link...`, `Das Ziel des BBj-Plugins darf keine symbolischen Links enthalten` | Der Ordner `plugins` von BBj oder der BBjShip-Ordner darin ist ein symbolischer Link oder enthält einen. | Installieren Sie in einen regulären Ordner. |
| `BBj-Einrichtungsbefehl fehlgeschlagen. Überprüfen Sie die Anmeldeinformationen und lokale BBjServices.` gefolgt von der Ausgabe des Befehls | Der Befehl, der den Bereitstellung-Endpunkt aktiviert, ist fehlgeschlagen. Die Ausgabe nennt den Grund, zum Beispiel eine abgelehnte Anmeldung oder einen Port, auf den der Webserver nicht hört. | Überprüfen Sie die Anmeldeinformationen, dass BBjServices läuft, und dass der Port der Bereitstellungs-URL ein Port des BBjServices-Webservers ist. |
| `BBj-Einrichtungsbefehl hat zeitlich überschritten. Überprüfen Sie lokale BBjServices` | Der Befehl hat nicht innerhalb von 120 Sekunden geferiggestellt. | Überprüfen Sie, dass BBjServices läuft und antwortet, und führen Sie dann das Ziel erneut aus. |
| `Frühere BBjShip-Dateien sind unter <folder> erhalten` | Ein Ersatz ist fehlgeschlagen, und die Dateien der vorherigen Version wurden in diesem Ordner aufbewahrt. | Beheben Sie den Grund des Fehlers und führen Sie das Ziel erneut aus. |

## Bereitstellung und Abmeldung {#deploy-and-undeploy}

### Vor dem Upload {#before-the-upload}

Diese Meldungen stammen von `bbj-package`, `bbj-deploy` und `bbj-undeploy`, bevor irgendetwas BBjShip erreicht.

| Meldung | Ursache | Was zu tun ist |
|---|---|---|
| `BBj-Bereitstellung erfordert JAR-Paketierung, dieses Projekt ist als <packaging> verpackt` | Das Projekt ist nicht als JAR verpackt. | Setzen Sie `<packaging>jar</packaging>`. |
| `BBj-Bereitstellung erfordert die verpackte Anwendungs-JAR...` | Das Ziel wurde ohne die `package`-Phase im selben Build ausgeführt. | Führen Sie `mvn install` oder `mvn package webforj:bbj-deploy` aus. |
| `BBj-Bereitstellung darf webforj-engine nicht enthalten, BBj ist die Engine...` | `webforj-engine` ist im Laufzeit-Classpath der App. | Schließen Sie es von der `webforj`-Abhängigkeit aus, siehe [Ohne das Installationsplugin](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj-Bereitstellung erfordert webforj-foundation im Laufzeit-Classpath` | Die App hat keine webforJ-Abhängigkeit zur Laufzeit. | Fügen Sie die `webforj`-Abhängigkeit im `compile`-Bereich hinzu. |
| `BBj-Bereitstellungsabhängigkeit ist ungelöst...` | Eine Abhängigkeit der App hat keine Datei. | Überprüfen Sie, dass Maven jede Abhängigkeit des Projekts auflöst. |
| `Veröffentlichungsname <name> ist ungültig...` | Der Veröffentlichungsname verletzt die Regeln in [Veröffentlichungsnamen](/docs/configuration/bbjship/deploying#publish-names). | Wählen Sie einen anderen `<publishName>`. |
| `Der Veröffentlichungsname <name> ist von BBjServices für eigene Web-Pfade reserviert...` | BBjServices verwendet diesen Namen für seine eigenen Adressen. | Wählen Sie einen anderen `<publishName>`. |
| `BBj-Bereitstellungsarchiv konnte nicht geschrieben werden...` | `bbj-package` konnte `target/bbj/<publish name>.zip` nicht schreiben. Der Grund folgt. | Lesen Sie den Grund. |

### Antworten von BBjShip {#answers-of-bbjship}

Wenn BBjShip eine Bereitstellung oder Abmeldung verweigert, beginnt die Meldung mit `BBj-Bereitstellung fehlgeschlagen mit HTTP <status>` oder `BBj-Abmeldung fehlgeschlagen mit HTTP <status>`, gefolgt von dem Grund.

| Status | Grund | Ursache | Was zu tun ist |
|---|---|---|---|
| 400 | `Veröffentlichungsname <name> ist ungültig...` oder `...ist von BBjServices reserviert...` | Der Veröffentlichungsname wird abgelehnt. | Wählen Sie einen anderen Veröffentlichungsnamen. |
| 400 | `BBj-Bereitstellung webforj.conf ist ungültig. <detail>` | Das `webforj.conf` der App kann nicht gelesen werden oder eine [App-Einstellung](/docs/configuration/bbjship/deploying#app-settings) hat den falschen Typ. | Beheben Sie die Datei wie im Detail angegeben. |
| 400 | `Der hochgeladene Inhalt ist kein BBj-Bereitstellungsarchiv...`, `Ungültiger BBj-Bereitstellungsarchivseintrag: <entry>` oder ein anderer Grund über das Archiv | Das Archiv wurde nicht vom Build-Plugin geschrieben oder wurde danach geändert. | Stellen Sie das Archiv bereit, das der Build schreibt, mit derselben webforJ-Version wie BBjShip. |
| 401 | `Überprüfen Sie die BBj-Administrationsanmeldeinformationen oder das Token` | Die Anmeldung wurde abgelehnt. | Überprüfen Sie die Anmeldeinformationen oder erneuern Sie das Token. |
| 403 | `Entwicklungsdurchläufe sind auf dieser BBj-Maschine nicht aktiviert. Führen Sie bbjship development enable aus` | Ein Entwicklungsdurchlauf hat eine Maschine erreicht, auf der Entwicklungsdurchläufe deaktiviert sind. | Führen Sie `bbjship development enable` auf der BBj-Maschine aus. |
| 404 | `Keine BBj-Anwendung namens <name> wurde von BBjShip bereitgestellt. Überprüfen Sie den Veröffentlichungsnamen` | Eine Abmeldung nannte eine App, die BBjShip nicht bereitgestellt hat. | Überprüfen Sie den Veröffentlichungsnamen mit `bbjship apps list`. |
| 409 | `Einstiegsklasse <class> ist bereits als <name> veröffentlicht. Entfernen Sie zuerst <name>` | Die Einstiegsklasse der App läuft bereits unter einem anderen Veröffentlichungsnamen. | Abmelden der anderen App oder unter ihrem Namen bereitstellen. |
| 409 | `Die veröffentlichte BBj-Anwendung <name> gehört nicht zu BBjShip...` | Eine App, die im Enterprise Manager registriert ist, verwendet bereits den Namen. | Wählen Sie einen anderen Veröffentlichungsnamen oder entfernen Sie diese App im Enterprise Manager. |
| 409 | `Die veröffentlichte BBj-Anwendung <other> unterscheidet sich nur durch die Groß-/Kleinschreibung von <name>...` | Eine andere App verwendet denselben Namen in einer anderen Schreibweise. | Wählen Sie einen anderen Veröffentlichungsnamen. |
| 409 | `<name> ist ein Entwicklungsdurchlauf. Stoppen Sie zuerst den Watch` | Ein Entwicklungsdurchlauf der App ist aktiv. | Stoppen Sie den Watch mit `Ctrl+C`, und führen Sie dann die Bereitstellung durch. |
| 409 | `<name> wird aus einem Archiv bereitgestellt. Entfernen Sie es zuerst` | Ein Entwicklungsdurchlauf verwendet den Namen einer bereitgestellten App. | Abmelden der App oder einen anderen `<publishName>` für den Entwicklungsdurchlauf festlegen. |
| 413 | `BBj-Bereitstellungsarchiv überschreitet die zulässige Größe von 1 GiB` | Das Archiv ist größer als von BBjShip akzeptiert. | Entfernen Sie Abhängigkeiten, die die App zur Laufzeit nicht benötigt. |
| 500 | `BBj-Bereitstellung fehlgeschlagen. Überprüfen Sie die BBjServices-Protokolle` | BBjServices ist fehlgeschlagen, während es die App installiert oder entfernt hat. | Lesen Sie die BBjServices-Protokolle. |

Eine Bereitstellung, die mit `hat die angeforderte Anwendung nicht bestätigt. Überprüfen Sie die BBjShip-Version und die BBjServices-Protokolle` endet, hat BBjShip erreicht, aber keine Bestätigung für die App erhalten. Überprüfen Sie, dass BBjShip und das `webforj-maven-plugin` dieselbe Version haben, und lesen Sie dann die BBjServices-Protokolle.

### Die laufende App {#the-running-app}

| Meldung | Ursache | Was zu tun ist |
|---|---|---|
| `Das Icons-Protokoll erfordert webforj.assetsSource = packaged, wenn es mit BBj Services ausgeführt wird` | Die App verwendet `icons://` ohne verpackte Assets. | Fügen Sie `webforj.assetsSource = packaged` zu `webforj.conf` hinzu und stellen Sie erneut bereit. |

## Entwicklungsdurchläufe {#development-runs}

Diese Meldungen stammen von `mvn` oder `mvn compile webforj:watch` in einem für BBjShip konfigurierten Projekt. Ein fehlgeschlagener Entwicklungsdurchlauf beginnt mit `BBj Entwicklungsdurchlauf fehlgeschlagen. Überprüfen Sie die Bereitstellungseinstellungen und dass Entwicklungsdurchläufe auf der BBj-Maschine aktiviert sind`, gefolgt von dem Grund aus [Setup](#setup), [Anmeldeinformationen und Verbindung](#credentials-and-connection) oder [Antworten von BBjShip](#answers-of-bbjship).

| Meldung | Ursache | Was zu tun ist |
|---|---|---|
| `Der Klassenordner <folder> existiert nicht. Führen Sie Compile vor webforj:watch aus` | Der Watch wurde gestartet, bevor das Projekt kompiliert wurde. | Führen Sie `mvn compile webforj:watch` oder `mvn` in einem Archetypenprojekt aus. |
| `BBj Entwicklungs-Klassenordner muss ein absoluter Ordner auf der BBj-Maschine sein` | `<development><classFolder>` benennt einen Ordner, den BBjServices nicht finden kann. | Legen Sie den absoluten Pfad des Klassenordners auf der BBj-Maschine fest. |
| `BBjServices-Protokolldatei nicht gefunden unter <file>. Konfigurieren Sie bbj.properties` | Die BBjServices-Protokolldatei befindet sich nicht dort, wo der Durchlauf danach sucht. | Setzen Sie `<bbj><properties>` oder `-Dwebforj.bbj.properties` auf die Datei. |
| `BBjServices hat innerhalb von 180 Sekunden nicht gestoppt` | Der Durchlauf hat BBjServices gebeten, zu stoppen, um die Hotswap-Einstellungen anzuwenden, und es lief weiter. | Stoppen Sie BBjServices selbst, starten Sie es erneut und führen Sie den Watch erneut aus. |
| `Startprogramm von BBjServices fehlt: <program>` | Die BBj-Installation hat kein Programm, um BBjServices zu starten. | Starten Sie BBjServices selbst und führen Sie den Watch erneut aus. |
| `BBjServices hat sich nicht gestartet. Das Programm endete mit Exit-Code <code>`, `BBjServices hat sich nicht innerhalb von 120 Sekunden gestartet` | BBjServices ist nach dem Neustart nicht zurückgekommen. | Starten Sie BBjServices selbst, und führen Sie dann den Watch erneut aus. |
| `BBjShip hat innerhalb von 120 Sekunden nicht geantwortet` | BBjServices wurde gestartet, aber der Bereitstellung-Endpunkt hat nicht geantwortet. | Überprüfen Sie das BBjServices-Protokoll und führen Sie dann den Watch erneut aus. |
| `BBjServices wurde ohne die Hotswap-Einstellungen neu gestartet. Überprüfen Sie <file>` | BBjServices wurde mit Java-Argumenten gestartet, die nicht in der Protokolldatei geschrieben wurden. | Überprüfen Sie die Java-Einstellungen von BBjServices im Enterprise Manager und in dieser Datei. |
| `webforJ watch: Entwicklungsdurchlauf von <name> wurde nicht gestoppt. <reason>` | Die App konnte nicht entfernt werden, als der Build gestoppt wurde. | Entfernen Sie sie mit `bbjship apps undeploy <name>`, oder starten Sie den Watch erneut, was sie ersetzt. |

Der erste Entwicklungsdurchlauf, der die Hotswap-Einstellungen hinzufügt, startet BBjServices neu. Er startet BBjServices mit `bin/bbjservices` unter macOS und Linux. Unter Windows startet er den `BBjServices`-Windows-Dienst, wenn einer registriert ist, und führt sonst `bin\BBjServices.exe -noservice` aus. Ein per `bin/bbjservices` oder `-noservice` gestartetes BBjServices läuft außerhalb des Dienstmanagers des Betriebssystems, bis der Computer neu gestartet wird.

## Befehlszeile {#command-line}

Jeder `bbjship`-Befehl wird innerhalb von BBjServices ausgeführt, sodass BBjServices laufen muss. Ohne es kann der Befehl nicht gestartet werden.

Ungültige Argumente enden mit Exit-Code `2` und einer Nachricht, die angibt, was zu spezifizieren ist:

| Meldung | Was zu tun ist |
|---|---|
| `Geben Sie einen Befehl an. Verwenden Sie --help für verfügbare Befehle`, `Unbekannter Befehl <command>...` | Nennen Sie einen Befehl, siehe [Befehlszeile](/docs/configuration/bbjship/command-line). |
| `Unbekannte Option <option>...`, `Option <option> ist für <command> nicht verfügbar...` | Verwenden Sie eine Option dieses Befehls, wie es `--help` auflistet. |
| `Geben Sie einen Benutzernamen nach --username an`, `Geben Sie ein Passwort nach --password an`, `Geben Sie einen Port nach --port an` | Geben Sie der Option einen Wert. |
| `Geben Sie --port von 1 bis 65535 an`, `Geben Sie --ssl-port von 1 bis 65535 an` | Geben Sie eine Portnummer an. |
| `Geben Sie <option> nur einmal an` | Entfernen Sie die wiederholte Option. |
| `Geben Sie nur eines von --password und --password-stdin an` | Behalten Sie eines der beiden. |
| `Das Passwort des BBj-Administrators fehlt. Geben Sie --username und --password <value> oder --password-stdin an, oder setzen Sie WEBFORJ_BBJ_USERNAME und WEBFORJ_BBJ_PASSWORD` | Geben Sie den Benutzernamen und das Passwort zusammen an. Die Nachricht nennt das fehlende. |
| `Geben Sie einen Anwendungsnamen oder --all an`, `Geben Sie einen Anwendungsnamen an` | Geben Sie `apps undeploy` einen Veröffentlichungsnamen oder `--all` an. |
| `Geben Sie den Pfad eines Bereitstellungsarchivs an, das auf .zip endet`, `Geben Sie ein Bereitstellungsarchiv an` | Geben Sie `apps deploy` eine `.zip`-Datei an. |
| `Bereitstellungsarchiv <path> existiert nicht`, `Bereitstellungsarchiv <path> kann nicht gelesen werden` | Überprüfen Sie den Pfad. BBjServices liest die Datei, sodass der Betriebssystembenutzer von BBjServices Lesezugriff darauf benötigt. |
| `Entwicklungsarchive werden durch den Watch bereitgestellt, nicht durch apps deploy` | Stellen Sie das Archiv von `bbj-package` bereit, nicht das von einem Entwicklungsdurchlauf. |
| `Veröffentlichungsname <name> ist ungültig...`, `Der Veröffentlichungsname <name> ist reserviert...` | Benennen Sie das Archiv um. Sein Dateiname ohne `.zip` ist der Veröffentlichungsname. |

Ein fehlgeschlagener Befehl endet mit Exit-Code `1` und `Befehl fehlgeschlagen.` gefolgt vom Grund:

| Grund | Ursache | Was zu tun ist |
|---|---|---|
| Ein Anmeldefehler von BBjServices | Der Benutzername oder das Passwort ist falsch. | Überprüfen Sie die Anmeldeinformationen. |
| `Der BBjServices-Webserver antwortet nicht auf Port <port>. Verwenden Sie den HTTP-Port des BBjServices-Webservers` | `--port` oder `--ssl-port` ist kein Port, auf den der BBjServices-Webserver hört. Der vorherige Endpunkt bleibt bestehen. | Verwenden Sie die für den Webserver im Enterprise Manager konfigurierten Ports. |
| `Anwendung <name> ist nicht von BBjShip bereitgestellt` | `apps undeploy` nannte eine App, die BBjShip nicht bereitgestellt hat. | Überprüfen Sie den Namen mit `bbjship apps list`. |
| `Eine andere Bereitstellung ist in Bearbeitung` | Eine andere Bereitstellung oder Abmeldung hat die Maschine 30 Sekunden lang blockiert. | Warten Sie, bis sie abgeschlossen ist, und führen Sie dann den Befehl erneut aus. |
| `Der BBjShip-Kontext gehört zu einer anderen Anwendung`, `Der Assets-Kontext gehört zu einer anderen Anwendung` | Eine andere App im Enterprise Manager verwendet den Webkontext `webforj-bbjship` oder `webforj-bbjship-assets`. | Benennen Sie diesen Kontext im Enterprise Manager um oder entfernen Sie ihn. |
| `Der BBjShip-Befehlsklassenpfad gehört zu einer anderen Anwendung`, `Der BBjShip-Basis-Classpath enthält eine nicht zugeordnete Bibliothek` | Die Klassenpfade `_webforj_bbjship_cli` oder `_webforj_bbjship` im Enterprise Manager wurden geändert. | Entfernen Sie beide Klassenpfade im Enterprise Manager und führen Sie den Befehl erneut aus, was sie erneut erstellt. |
| `Der BBjShip-Servlet fehlt. Überprüfen Sie die plugin-Installation`, `Die BBjShip-Bibliothek fehlt. Überprüfen Sie die plugin-Installation` | Der BBjShip-Ordner ist unvollständig. | Installieren Sie BBjShip erneut. |
| `BBjServices verwaltet keine Web-Kontexte. Aktualisieren Sie BBj` | Die BBj-Version ist zu alt für BBjShip. | Installieren Sie die BBj-Version, die zu webforJ passt. |

`reset` endet mit Exit-Code `1` und `Zurücksetzen abgebrochen`, wenn die Antwort auf `Fortfahren? [y/N]` nicht `y` oder `Y` ist. Wenn es die Java-Einstellungen von BBjServices wiederhergestellt hat, endet es mit `Starten Sie BBjServices jetzt neu, um die wiederhergestellten Java-Einstellungen anzuwenden`. Starten Sie BBjServices neu, um es ohne die Hotswap-Einstellungen auszuführen.

## Windows {#windows}

| Situation | Was zu tun ist |
|---|---|
| `bbj.exe` mit dem BBjShip-Programm gibt nichts aus. | Führen Sie `bbjship.cmd` in `<BBj home>\plugins\webforj-bbjship` aus, um die Ausgabe des Befehls anzuzeigen. |
| `--password-stdin` endet mit Exit-Code `2` und `Standardinput ist auf Windows nicht verfügbar. Verwenden Sie --password <value> oder WEBFORJ_BBJ_PASSWORD`. | Übergeben Sie das Passwort mit `--password` oder setzen Sie `WEBFORJ_BBJ_USERNAME` und `WEBFORJ_BBJ_PASSWORD`. |
| `reset` endet mit Exit-Code `2` und `Standardinput ist auf Windows nicht verfügbar. Führen Sie reset --yes aus, um zu bestätigen`. | Führen Sie `bbjship.cmd reset --yes` aus. |
| `apps undeploy` oder `reset` gibt `Nicht entfernt: <folder>. <reason>` aus und endet mit Exit-Code `0`. | BBjServices hält noch Dateien der App offen. Die App wurde aus dem Enterprise Manager entfernt. Starten Sie BBjServices neu. Der Ordner wird bei der nächsten Bereitstellung dieses Namens entfernt oder löschen Sie ihn nach dem Neustart. |
| Die Installation von BBjShip über eine installierte Version schlägt fehl, da ihre Dateien in Verwendung sind. | BBjServices hält noch die Dateien des installierten BBjShip offen. Starten Sie BBjServices neu und installieren Sie erneut. |
| BBjServices wird nach einem Entwicklungsdurchlauf, der es neu gestartet hat, nicht als Windows-Dienst ausgeführt. | Der Entwicklungsdurchlauf hat es mit `BBjServices.exe -noservice` gestartet, weil kein `BBjServices`-Dienst registriert ist. Es läuft bis zum nächsten Neustart des Computers außerhalb des Windows-Dienstmanagers. |
| Ein `bbjship.cmd` Befehl kann nicht gestartet werden. | Starten Sie BBjServices. Jeder `bbjship`-Befehl benötigt, dass es läuft. |
