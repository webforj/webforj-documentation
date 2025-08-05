---
sidebar_position: 4
_i18n_hash: 9cf48c2124910b9a10a8ec4c5cd82205
---
# Lokale Installation

Dieser Abschnitt der Dokumentation behandelt die Schritte, die nur für Benutzer erforderlich sind, die webforJ für die Web- und/oder App-Entwicklung mit einer lokalen BBj-Instanz auf ihrem Computer verwenden möchten. Diese Installation ermöglicht es den Benutzern nicht, zum Code der webforJ-Grundlage selbst beizutragen.
<br/>

:::info
Diese Anleitung behandelt die Installation auf einem Windows-System - die Installationsschritte können sich für Mac/Linux-Betriebssysteme unterscheiden.
:::
<br/>

Die Installation wird in die folgenden Schritte unterteilt:


1. BBj-Download und -Installation
2. Verwendung des BBj Plugin Managers zur Erstellung Ihrer App
3. Starten Ihrer App


:::tip Voraussetzungen
Bevor Sie beginnen, stellen Sie sicher, dass Sie die notwendigen [Voraussetzungen](../../introduction/prerequisites) für die Einrichtung und Verwendung von webforJ überprüft haben. Dies stellt sicher, dass Sie alle erforderlichen Werkzeuge und Konfigurationen vor Beginn Ihres Projekts haben.
:::


## 1. BBj-Download und -Installation {#1-bbj-download-and-installation}

<b>Beim Befolgen dieses Schrittes stellen Sie sicher, dass Sie die BBj-Version installieren, die der gleichen webforJ-Version entspricht. </b><br/><br/>

[Dieses Video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) kann Ihnen bei der Installation von BBj helfen, wenn Sie Unterstützung bei der Einrichtung benötigen. Der Installationsbereich der BASIS-Website kann [unter diesem Link](https://basis.cloud/download-product) gefunden werden.

:::tip
Es wird empfohlen, die neueste stabile Revision von BBj zu verwenden und "BBj" aus der Liste der Optionen auszuwählen, ohne Barista oder Addon.
:::

<a name='section3'></a>

## 2. Installieren und Konfigurieren des webforJ-Plugins

Sobald BBj installiert ist, kann der Plugin Manager aufgerufen werden, um die benötigten Werkzeuge zur Konfiguration von webforJ zu installieren. Um zu beginnen, geben Sie "Plugin Manager" ins Startmenü oder in den Finder ein. 

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Nachdem der Plugin-Manager geöffnet wurde, navigieren Sie zur Registerkarte "Verfügbare Plugins" oben.

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

Wählen Sie in diesem Abschnitt das Kontrollkästchen "Versionen in Entwicklung anzeigen" aus.

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

Der DWCJ-Eintrag sollte jetzt in der Liste der verfügbaren Plugins zum Download sichtbar sein. Klicken Sie auf diesen Eintrag in der Liste, um ihn auszuwählen.

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Nachdem der DWCJ-Eintrag ausgewählt wurde, klicken Sie auf die Schaltfläche "Installieren".

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

Nachdem das Plugin installiert wurde, klicken Sie oben auf die Registerkarte "Installierte Plugins".

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

Diese Registerkarte zeigt installierte Plugins an, die nun den DWCJ-Eintrag umfassen sollten. Klicken Sie auf den Eintrag in der Liste.

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

Nachdem der DWCJ-Eintrag ausgewählt wurde, klicken Sie auf die Schaltfläche "Konfigurieren".

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

Im sich öffnenden Fenster klicken Sie auf die Schaltfläche "Maven Remote Install aktivieren" unten links im Fenster.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip 

Alternativ können Sie zum Verzeichnis `bin` innerhalb Ihres `bbx`-Ordners navigieren und den folgenden Befehl ausführen:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Ein Dialog sollte anzeigen, dass die Remote-Installation aktiviert wurde. Klicken Sie auf "OK", um diesen Dialog zu schließen.

## 3. Verwendung des Starterprojekts
Sobald BBj und das erforderliche webforJ-Plugin installiert und konfiguriert sind, können Sie ein neues, scaffolded Projekt über die Befehlszeile erstellen. Dieses Projekt umfasst die notwendigen Werkzeuge, um Ihr erstes webforJ-Programm auszuführen.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Starten der App

Nachdem dies geschehen ist, führen Sie in Ihrem Projektverzeichnis `mvn install` aus. Dies wird das webforJ-Installationsplugin ausführen und Ihnen den Zugriff auf Ihre App ermöglichen. Um die App zu sehen, sollten Sie die folgende URL aufrufen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Hostport, den Sie mit Docker konfiguriert haben, und `YourPublishName` wird durch den Text innerhalb des `<publishname>`-Tags der POM ersetzt. 
Wenn alles korrekt gemacht wurde, sollten Sie Ihre App sehen.
