---
sidebar_position: 1
title: Docker
_i18n_hash: 49f4e9eb5470926c186e323e4d67377f
---
# Docker-Installation

Dieser Abschnitt der Dokumentation behandelt die Schritte, die erforderlich sind, um mit Docker zu entwickeln. Änderungen an Ihrem Code werden auf Ihrem Entwicklungsrechner vorgenommen, und die resultierende Anwendung wird in Docker ausgeführt.

## 1. Docker herunterladen {#1-downloading-docker}

Der Installationsprozess für Docker variiert leicht zwischen Windows-, Mac- und Linux-Benutzern. Siehe den untenstehenden Abschnitt, der Ihrem Betriebssystem entspricht.

### Windows {#windows}

:::info
Es wird empfohlen, die neueste Version des Windows Subsystem für Linux herunterzuladen. Weitere Informationen finden Sie [unter diesem Link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Windows: [Docker Desktop für Windows](https://www.docker.com/products/docker-desktop/)
>- Klicken Sie auf die Schaltfläche "Docker Desktop für Windows herunterladen", um den Installer herunterzuladen.

**2. Docker Desktop installieren:**
>- Führen Sie den heruntergeladenen Installer aus.
>- Folgen Sie dem Installationsassistenten und stellen Sie sicher, dass Sie Hyper-V aktivieren (falls aufgefordert), da Docker für Windows Hyper-V für die Virtualisierung verwendet.
>- Nach Abschluss der Installation wird Docker Desktop automatisch gestartet.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker installiert und korrekt funktioniert.

### Mac {#mac}

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Mac: [Docker Desktop für Mac](https://www.docker.com/products/docker-desktop/)

**2. Docker Desktop installieren:**
>- Führen Sie den heruntergeladenen Installer aus.
>- Nach Abschluss der Installation wird Docker Desktop automatisch gestartet.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker installiert und korrekt funktioniert.

## 2. Konfiguration {#2-configuration}

Nachdem Docker Desktop heruntergeladen wurde, suchen Sie nach dem neuesten webforJ-Image, das derzeit unter dem Namen `webforj/sandbox` geführt wird.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klicken Sie auf die Liste der Tags, um die verfügbaren Optionen zu sehen

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Wählen Sie für den aktuellsten Build "rc"

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Ziehen Sie das Image, um Ihren Container zu starten

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Sobald der Download abgeschlossen ist, klicken Sie auf die Schaltfläche "Ausführen", um die Konfigurationseinstellungen zu öffnen

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Öffnen Sie das Menü "Optionale Einstellungen"

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Wählen Sie einen gewünschten Host-Port, unter dem Sie Ihre Anwendung in Docker sehen können

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klicken Sie auf "Ausführen", um den Container zu starten

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Wichtig
Stellen Sie sicher, dass Sie sich an die angegebene Hostportnummer erinnern, da Sie diese später benötigen.
:::

## 3. Ihre App ausführen {#3-running-your-app}

Sobald der Container erstellt wurde, können webforJ-Anwendungen innerhalb des Containers anstelle von lokal ausgeführt werden. Zunächst ist es notwendig, die POM-Datei Ihres Projekts korrekt zu konfigurieren. Nachdem dies geschehen ist, wird das Aufrufen einer bestimmten URL im Browser die App anzeigen.

### Konfigurieren Ihrer POM-Datei {#configuring-your-pom-file}

Das Ausführen eines webforJ-Projekts im Docker-Container erfordert die Verwendung des webforJ Install-Plugins, das über Ihre POM-Datei konfiguriert werden kann:

Erstellen Sie einen neuen `<plugin>`-Eintrag im `<plugins>`-Bereich der POM. Der folgende Code zeigt einen Startbeitrag, der verwendet und nach Bedarf für Ihr Projekt angepasst werden kann:

:::important
Wenn Ihre POM-Datei keinen Abschnitt `<plugins>` enthält, erstellen Sie einen.
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

Nachdem ein ähnlicher Eintrag wie der oben dargestellte erstellt wurde, passen Sie die folgenden Informationen an:

- Ändern Sie den `<deployurl>`-Eintrag, um die Portnummer zu verwenden, die mit dem **Host-Port** übereinstimmt, den Sie für Ihren Container im vorherigen Schritt konfiguriert haben.

- Stellen Sie sicher, dass der `<classname>`-Eintrag dem Namen der Anwendung entspricht, die Sie ausführen möchten.

- Wenn Ihre `<username>`- und `<password>`-Anmeldeinformationen für Ihre Installation von BBj unterschiedlich sind, ändern Sie diese.

### Verwendung des Starterprojekts {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Starten der App {#launching-the-app}

Sobald dies erledigt ist, führen Sie in Ihrem Projektverzeichnis `mvn install` aus. Dies wird das webforJ Install-Plugin ausführen und es Ihnen ermöglichen, auf Ihre App zuzugreifen. Um die App zu sehen, gehen Sie zu folgender URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Host-Port, den Sie mit Docker konfiguriert haben, und `YourPublishName` wird durch den Text im `<publishname>`-Tag der POM ersetzt. Wenn alles korrekt gemacht wurde, sollten Sie Ihre App sehen können.
