---
sidebar_position: 1
title: Docker
_i18n_hash: 8cc797ca5ca7e8ba3a8cd9f3aec41d74
---
# Docker-Installation

Dieser Abschnitt der Dokumentation behandelt die Schritte, die erforderlich sind, um mit Docker zu entwickeln. Änderungen an Ihrem Code werden auf Ihrem Entwicklungsrechner vorgenommen, und die resultierende App wird in Docker ausgeführt. 

## 1. Docker herunterladen {#1-downloading-docker}

Der Installationsprozess für Docker unterscheidet sich geringfügig zwischen Windows-, Mac- und Linux-Benutzern. Siehe den Abschnitt unten, der Ihrem Betriebssystem entspricht.


### Windows {#windows}

:::info
Es wird empfohlen, die neueste Version des Windows Subsystem for Linux herunterzuladen. Weitere Informationen finden Sie [an diesem Link](https://learn.microsoft.com/en-us/windows/wsl/install).
:::

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Windows: [Docker Desktop für Windows](https://www.docker.com/products/docker-desktop/)
>- Klicken Sie auf die Schaltfläche "Get Docker Desktop for Windows", um den Installer herunterzuladen.

**2. Docker Desktop installieren:**
>- Führen Sie den heruntergeladenen Installer aus.
>- Folgen Sie dem Installationsassistenten und stellen Sie sicher, dass Hyper-V aktiviert ist (wenn Sie dazu aufgefordert werden), da Docker für Windows Hyper-V für die Virtualisierung verwendet.
>- Nach Abschluss der Installation startet Docker Desktop automatisch.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker installiert und korrekt funktioniert.

### Mac {#mac}

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Mac: [Docker Desktop für Mac](https://www.docker.com/products/docker-desktop/)

**2. Docker Desktop installieren:**
>- Führen Sie den heruntergeladenen Installer aus.
>- Nach Abschluss der Installation startet Docker Desktop automatisch.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker installiert und korrekt funktioniert.

## 2. Konfiguration {#2-configuration}

Sobald Docker Desktop heruntergeladen wurde, suchen Sie nach dem neuesten webforJ-Image, das derzeit unter dem Namen `webforj/sandbox` verfügbar ist.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klicken Sie auf die Liste der Tags, um die verfügbaren Optionen zu sehen.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Wählen Sie für den aktuellsten Build "rc".

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Ziehen Sie das Image, um Ihren Container zu starten.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Sobald der Download abgeschlossen ist, klicken Sie auf die Schaltfläche "Run", die die Konfigurationseinstellungen öffnet.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Öffnen Sie das Menü "Optionale Einstellungen".

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Wählen Sie einen gewünschten Host-Port, auf dem Sie Ihre App innerhalb von Docker sehen können.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klicken Sie auf "Run", um den Container zu starten.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Wichtig
Stellen Sie sicher, dass Sie sich die benutzerdefinierte Host-Portnummer notieren, die Sie angegeben haben, da diese später benötigt wird.
:::

## 3. Ihre App ausführen {#3-running-your-app}

Nachdem der Container erstellt wurde, können webforJ-Anwendungen innerhalb des Containers anstelle lokal ausgeführt werden. Zuerst ist es notwendig, die POM-Datei Ihres Projekts korrekt zu konfigurieren. Nachdem dies erledigt ist, wird das Aufrufen einer bestimmten URL im Browser die App anzeigen.

### Konfigurieren Ihrer POM-Datei {#configuring-your-pom-file}

Um ein webforJ-Projekt im Docker-Container auszuführen, ist die Verwendung des webforJ Install Plugins erforderlich, das über Ihre POM-Datei konfiguriert werden kann:

Erstellen Sie einen neuen `<plugin>`-Eintrag im `<plugins>`-Abschnitt der POM. Der folgende Code zeigt einen Starteintrag, der verwendet und nach Bedarf angepasst werden kann:

:::important
Wenn Ihre POM-Datei keinen `<plugins>`-Abschnitt hat, erstellen Sie einen.
:::

```xml
<plugin>
<groupId>com.webforj</groupId>
<artifactId>webforj-install-maven-plugin</artifactId>
<version>${webforj.version}</version>
<executions>
  <execution>
  <goals>
    <goal>install</goal>
  </goals>
  </execution>
</executions>
<configuration>
  <deployurl>http://localhost:8888/webforj-install</deployurl>
  <classname>samples.HelloWorldApp</classname>
  <publishname>hello-world</publishname>
  <debug>true</debug>
</configuration>
</plugin>
```

Sobald ein Eintrag ähnlich dem oben stehenden erstellt wurde, passen Sie die folgenden Informationen an:

- Ändern Sie den `<deployurl>`-Eintrag, um die Portnummer zu verwenden, die mit dem **Host-Port** übereinstimmt, den Sie für Ihren Container in den vorherigen Schritten konfiguriert haben.

- Stellen Sie sicher, dass der `<classname>`-Eintrag mit dem Namen der App übereinstimmt, die Sie ausführen möchten.

- Wenn Ihre `<username>`- und `<password>`-Anmeldeinformationen für Ihre Installation von BBj unterschiedlich sind, ändern Sie diese.

### Verwendung des Starterprojekts {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### App starten {#launching-the-app}

Nachdem dies erledigt ist, führen Sie `mvn install` in Ihrem Projektverzeichnis aus. Dies wird das webforJ Install Plugin ausführen und Ihnen ermöglichen, auf Ihre App zuzugreifen. Um die App anzuzeigen, möchten Sie die folgende URL aufrufen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Host-Port, den Sie mit Docker konfiguriert haben, und `YourPublishName` wird durch den Text im `<publishname>`-Tag der POM ersetzt. Wenn alles richtig gemacht wurde, sollten Sie Ihre App angezeigt bekommen.
