---
sidebar_position: 1
title: Docker
_i18n_hash: 642936b8f7fd836ca4510eab19087a8c
---
# Docker-Installation

Dieser Abschnitt der Dokumentation behandelt die Schritte, die erforderlich sind, damit Benutzer, die mit Docker entwickeln möchten, ihre Umgebung einrichten können. Änderungen an Ihrem Code werden auf Ihrem Entwicklungssystem vorgenommen, und die resultierende App wird in Docker ausgeführt.

## 1. Docker herunterladen {#1-downloading-docker}

Der Installationsprozess für Docker unterscheidet sich geringfügig zwischen Windows-, Mac- und Linux-Nutzern. Siehe den Abschnitt weiter unten, der Ihrem Betriebssystem entspricht.

### Windows {#windows}

:::info
Es wird empfohlen, die neueste Version des Windows Subsystem for Linux herunterzuladen. Weitere Informationen finden Sie [unter diesem Link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Windows: [Docker Desktop für Windows](https://www.docker.com/products/docker-desktop/)
>- Klicken Sie auf die Schaltfläche "Get Docker Desktop for Windows", um den Installer herunterzuladen.

**2. Docker Desktop installieren:**
>- Führen Sie den heruntergeladenen Installer aus.
>- Folgen Sie dem Installationsassistenten und stellen Sie sicher, dass Hyper-V aktiviert ist (falls Sie dazu aufgefordert werden), da Docker für Windows Hyper-V zur Virtualisierung verwendet.
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

Sobald Docker Desktop heruntergeladen wurde, suchen Sie nach dem neuesten webforJ-Image, das derzeit unter dem Namen `webforj/sandbox` verfügbar ist.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klicken Sie auf die Liste der Tags, um die verfügbaren Optionen zu sehen.

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Wählen Sie für den aktuellsten Build "rc".

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Ziehen Sie das Image, um Ihren Container zu starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Sobald der Download abgeschlossen ist, klicken Sie auf die Schaltfläche "Ausführen", die die Konfigurationseinstellungen öffnet.

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Öffnen Sie das Menü "Optionale Einstellungen".

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Wählen Sie einen gewünschten Host-Port aus, unter dem Sie Ihre App innerhalb von Docker ausführen können.

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klicken Sie auf "Ausführen", um den Container zu starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Wichtig
Stellen Sie sicher, dass Sie die benutzerdefinierte Host-Port-Nummer notieren, die Sie angegeben haben, da diese später benötigt wird.
:::

## 3. Ihre App ausführen {#3-running-your-app}

Sobald der Container erstellt wurde, können webforJ-Anwendungen innerhalb des Containers anstelle von lokal ausgeführt werden. Zuerst ist es erforderlich, die POM-Datei Ihres Projekts korrekt zu konfigurierten. Nach Abschluss dieser Aufgabe zeigt ein spezifischer URL im Browser die App an.

### Ihre POM-Datei konfigurieren {#configuring-your-pom-file}

Um ein webforJ-Projekt im Docker-Container auszuführen, ist die Verwendung des webforJ Install-Plugins erforderlich, das über Ihre POM-Datei konfiguriert werden kann:

Erstellen Sie einen neuen `<plugin>`-Eintrag im `<plugins>`-Abschnitt der POM. Der folgende Code zeigt einen starting entry, der verwendet und angepasst werden kann, je nach Bedarf für Ihr Projekt:

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

Sobald ein Eintrag ähnlich dem oben erstellten wurde, passen Sie die folgenden Informationen an:

- Ändern Sie den `<deployurl>`-Eintrag, um die Portnummer zu verwenden, die dem **Host-Port** entspricht, den Sie für Ihren Container in dem vorherigen Schritt konfiguriert haben.

- Stellen Sie sicher, dass der `<classname>`-Eintrag mit dem Namen der App übereinstimmt, die Sie ausführen möchten.

- Wenn Ihre `<username>`- und `<password>`-Anmeldeinformationen für Ihre Installation von BBj unterschiedlich sind, ändern Sie diese.

### Verwendung des Starter-Projekts {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Die App starten {#launching-the-app}

Nachdem dies erledigt wurde, führen Sie ein `mvn install` in Ihrem Projektverzeichnis aus. Dies führt das webforJ-Install-Plugin aus und ermöglicht es Ihnen, auf Ihre App zuzugreifen. Um die App zu sehen, möchten Sie die folgende URL aufrufen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Host-Port, den Sie mit Docker konfiguriert haben, und `YourPublishName` wird durch den Text im `<publishname>`-Tag der POM ersetzt. Wenn alles korrekt gemacht wurde, sollten Sie Ihre App sehen.
