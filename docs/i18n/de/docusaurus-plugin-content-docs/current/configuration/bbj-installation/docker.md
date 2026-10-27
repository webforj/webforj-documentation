---
sidebar_position: 1
title: Docker
description: >-
  Install Docker Desktop on Windows or Mac, pull the webforj/sandbox image, and
  run BBjServices in a container for webforJ development.
_i18n_hash: 6c644b1d4f54cfa22e3b978714a5849e
---
# Docker-Installation

:::danger Veraltet und wird entfernt
Die Docker-Installation mit dem `webforj/sandbox`-Image ist veraltet und wird in einer zukünftigen Version entfernt. Starten Sie keine neuen Projekte damit. Verschieben Sie bestehende Projekte zu [BBjShip](/docs/configuration/bbjship/overview), das webforJ-Anwendungen in BBjServices installiert, bereitstellt und ausführt. [Bestehende Projekte](/docs/configuration/bbjship/existing-projects) listet die Änderungen auf, die ein Projekt benötigt.
:::

Dieser Abschnitt der Dokumentation behandelt die Schritte, die Benutzer befolgen müssen, die mit Docker entwickeln möchten. Änderungen an Ihrem Code werden auf Ihrem Entwicklungsrechner vorgenommen, und die resultierende Anwendung wird in Docker ausgeführt.

## 1. Docker herunterladen {#1-downloading-docker}

Der Installationsprozess für Docker unterscheidet sich geringfügig zwischen Windows-, Mac- und Linux-Benutzern. Siehe den Abschnitt unten, der Ihrem Betriebssystem entspricht.

### Windows {#windows}

:::info
Es wird empfohlen, die neueste Version des Windows-Subsystems für Linux herunterzuladen. Weitere Informationen finden Sie [unter diesem Link](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Windows: [Docker Desktop für Windows](https://www.docker.com/products/docker-desktop/)
>- Klicken Sie auf die Schaltfläche "Docker Desktop für Windows herunterladen", um den Installer herunterzuladen.

**2. Docker Desktop installieren:**
>- Führen Sie den heruntergeladenen Installer aus.
>- Folgen Sie dem Installationsassistenten und stellen Sie sicher, dass Hyper-V aktiviert ist (wenn Sie dazu aufgefordert werden), da Docker für Windows Hyper-V zur Virtualisierung verwendet.
>- Nach Abschluss der Installation wird Docker Desktop automatisch gestartet.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker installiert und funktionsfähig ist.

### Mac {#mac}

**1. Docker Desktop herunterladen:**
>- Besuchen Sie die Download-Seite für Docker Desktop für Mac: [Docker Desktop für Mac](https://www.docker.com/products/docker-desktop/)

**2. Docker Desktop installieren:**
>- Führen Sie den heruntergeladenen Installer aus.
>- Nach Abschluss der Installation wird Docker Desktop automatisch gestartet.

**3. Installation überprüfen:**
>- Öffnen Sie ein Terminal und führen Sie den Befehl `docker --version` aus, um zu überprüfen, ob Docker installiert und funktionsfähig ist.

## 2. Konfiguration {#2-configuration}

Sobald Docker Desktop heruntergeladen wurde, suchen Sie nach dem neuesten webforJ-Image, das derzeit unter dem Namen `webforj/sandbox` verfügbar ist.

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Klicken Sie auf die Liste der Tags, um die verfügbaren Optionen anzuzeigen

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Wählen Sie für den aktuellsten Build "rc"

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Ziehen Sie das Image, um Ihren Container zu starten

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Sobald der Download abgeschlossen ist, klicken Sie auf die Schaltfläche "Ausführen", die die Konfigurationseinstellungen öffnet

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Öffnen Sie das Menü "Optionale Einstellungen"

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Wählen Sie einen gewünschten Host-Port aus, unter dem Sie Ihre Anwendung in Docker sehen können

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Klicken Sie auf "Ausführen", um den Container zu starten

![DWCJ Bildsuche](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Wichtig
Stellen Sie sicher, dass Sie sich die benutzerdefinierte Host-Portnummer notieren, die Sie angeben, da dies später benötigt wird.
:::

## 3. Ihre Anwendung ausführen {#3-running-your-app}

Sobald der Container erstellt wurde, können webforJ-Anwendungen innerhalb des Containers anstelle lokal ausgeführt werden. Zuerst ist es notwendig, die POM-Datei Ihres Projekts korrekt zu konfigurieren. Ist dies geschehen, wird das Aufrufen einer bestimmten URL im Browser die Anwendung anzeigen.

### Konfigurieren Ihrer POM-Datei {#configuring-your-pom-file}

Um ein webforJ-Projekt im Docker-Container auszuführen, muss das webforJ Install Plugin verwendet werden, das über Ihre POM-Datei konfiguriert werden kann:

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

Sobald ein Eintrag ähnlich dem oben Erstellten erstellt wurde, passen Sie die folgenden Informationen an:

- Ändern Sie den `<deployurl>`-Eintrag, um die Portnummer zu verwenden, die Sie mit dem **Host-Port** übereinstimmen, den Sie zuvor für Ihren Container konfiguriert haben.

- Stellen Sie sicher, dass der `<classname>`-Eintrag mit dem Namen der Anwendung übereinstimmt, die Sie ausführen möchten.

- Wenn Ihre `<username>` und `<password>`-Anmeldeinformationen anders sind als für Ihre Installation von BBj, ändern Sie diese.

### Verwendung des Starterprojekts {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Die Anwendung starten {#launching-the-app}

Sobald dies erledigt ist, führen Sie `mvn install` in Ihrem Projektverzeichnis aus. Dies führt das webforJ-Installationsplugin aus und ermöglicht Ihnen den Zugriff auf Ihre Anwendung. Um die Anwendung zu sehen, möchten Sie zu folgender URL gehen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Host-Port, den Sie mit Docker konfiguriert haben, und `YourPublishName` durch den Text innerhalb des `<publishname>`-Tags der POM. Wenn alles korrekt ausgeführt wurde, sollten Sie Ihre Anwendung angezeigt bekommen.
