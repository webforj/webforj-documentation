---
sidebar_position: 1
title: Docker
description: >-
  Install Docker Desktop on Windows or Mac, pull the webforj/sandbox image, and
  run BBjServices in a container for webforJ development.
_i18n_hash: 4981bf7da8b63063a781d946d29895e6
---
# Docker-Installation

Dieser Abschnitt der Dokumentation behandelt die Schritte, die Benutzer befolgen müssen, die mit Docker entwickeln möchten. Änderungen an Ihrem Code werden auf Ihrem Entwicklungsrechner vorgenommen, und die resultierende App wird in Docker ausgeführt.

## 1. Docker herunterladen {#1-downloading-docker}

Der Installationsprozess für Docker variiert leicht zwischen Windows-, Mac- und Linux-Benutzern. Sehen Sie sich den Abschnitt an, der Ihrem Betriebssystem entspricht.

### Windows {#windows}

:::info
Es wird empfohlen, die neueste Version des Windows Subsystem for Linux herunterzuladen. Weitere Informationen finden Sie [unter diesem Link](https://learn.microsoft.com/en-us/windows/wsl/install).
:::

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Windows: [Docker Desktop für Windows](https://www.docker.com/products/docker-desktop/)
>- Klicken Sie auf die Schaltfläche "Docker Desktop für Windows herunterladen", um den Installer herunterzuladen.

**2. Docker Desktop installieren:**
>- Führen Sie den Installer aus, den Sie heruntergeladen haben.
>- Folgen Sie dem Installationsassistenten und stellen Sie sicher, dass Sie Hyper-V aktivieren (wenn Sie dazu aufgefordert werden), da Docker für Windows Hyper-V für die Virtualisierung verwendet.
>- Nach Abschluss der Installation wird Docker Desktop automatisch gestartet.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker korrekt installiert und funktionsfähig ist.

### Mac {#mac}

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Mac: [Docker Desktop für Mac](https://www.docker.com/products/docker-desktop/)

**2. Docker Desktop installieren:**
>- Führen Sie den Installer aus, den Sie heruntergeladen haben.
>- Nach Abschluss der Installation wird Docker Desktop automatisch gestartet.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker korrekt installiert und funktionsfähig ist.

## 2. Konfiguration {#2-configuration}

Sobald Docker Desktop heruntergeladen wurde, suchen Sie nach dem neuesten webforJ-Image, das derzeit unter dem Namen `webforj/sandbox` verfügbar ist.

![DWCJ Image Search](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klicken Sie auf die Liste der Tags, um die verfügbaren Optionen zu sehen.

![DWCJ Image Search](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Wählen Sie für den aktuellsten Build "rc" aus.

![DWCJ Image Search](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Ziehen Sie das Image, um Ihren Container zu starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Sobald der Download abgeschlossen ist, klicken Sie auf die Schaltfläche Ausführen, um die Konfigurationseinstellungen zu öffnen.

![DWCJ Image Search](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Öffnen Sie das Menü "Optionale Einstellungen".

![DWCJ Image Search](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Wählen Sie einen gewünschten Host-Port, über den Sie Ihre App innerhalb von Docker sehen können.

![DWCJ Image Search](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klicken Sie auf "Ausführen", um den Container zu starten.

![DWCJ Image Search](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Wichtig
Stellen Sie sicher, dass Sie die von Ihnen angegebene benutzerdefinierte Host-Portnummer notieren, da diese später benötigt wird.
:::

## 3. Ihre App ausführen {#3-running-your-app}

Sobald der Container erstellt wurde, können webforJ-Anwendungen innerhalb des Containers anstelle lokal ausgeführt werden. Zunächst muss die POM-Datei Ihres Projekts korrekt konfiguriert werden. Wenn dies erledigt ist, zeigt ein Besuch einer bestimmten URL im Browser die App an.

### Konfigurieren Ihrer POM-Datei {#configuring-your-pom-file}

Das Ausführen eines webforJ-Projekts im Docker-Container erfordert die Verwendung des webforJ Install Plugin, das über Ihre POM-Datei konfiguriert werden kann:

Erstellen Sie einen neuen `<plugin>`-Eintrag im `<plugins>`-Abschnitt der POM. Der folgende Code zeigt einen Start-Eintrag, der verwendet und angepasst werden kann, wie erforderlich:

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

Sobald ein Eintrag ähnlich dem obigen erstellt wurde, passen Sie die folgenden Informationen an:

- Ändern Sie den `<deployurl>`-Eintrag so, dass er die Portnummer verwendet, die Sie mit dem **Host-Port** abgeglichen haben, den Sie in dem vorherigen Schritt konfiguriert haben.

- Stellen Sie sicher, dass der `<classname>`-Eintrag mit dem Namen der App übereinstimmt, die Sie ausführen möchten.

- Wenn Ihre `<username>`- und `<password>`-Anmeldeinformationen für Ihre Installation von BBj unterschiedlich sind, ändern Sie diese.

### Verwendung des Starterprojekts {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Starten der App {#launching-the-app}

Sobald dies erledigt ist, führen Sie `mvn install` in Ihrem Projektverzeichnis aus. Dies wird das webforJ Install Plugin ausführen und es Ihnen ermöglichen, auf Ihre App zuzugreifen. Um die App zu sehen, möchten Sie die folgende URL besuchen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Host-Port, den Sie mit Docker konfiguriert haben, und `YourPublishName` wird durch den Text im `<publishname>`-Tag der POM ersetzt. Wenn alles korrekt gemacht wurde, sollten Sie Ihre App gerendert sehen.
