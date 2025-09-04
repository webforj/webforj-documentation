---
sidebar_position: 4
_i18n_hash: a41d592f84a4dcd32f5398f3e57621a4
---
# Lokale Installation

Dieser Abschnitt der Dokumentation behandelt die erforderlichen Schritte nur für Benutzer, die webforJ für die Web- und/oder App-Entwicklung mit einer lokalen BBj-Instanz auf ihrem Computer verwenden möchten. Diese Installation ermöglicht es den Benutzern nicht, zum webforJ-Foundation-Code selbst beizutragen.
<br/>

:::info
Dieses Tutorial behandelt die Installation auf einem Windows-System – die Installationsschritte können für Mac/Linux-Betriebssysteme abweichen.
:::
<br/>

Die Installation wird in die folgenden Schritte unterteilt:

1. BBj herunterladen und installieren
2. Verwendung des BBj Plugin Managers zum Erstellen Ihrer App
3. Starten Ihrer App

:::tip Voraussetzungen
Bevor Sie beginnen, stellen Sie sicher, dass Sie die notwendigen [Voraussetzungen](../../introduction/prerequisites) für die Einrichtung und Verwendung von webforJ überprüft haben. Dies stellt sicher, dass Sie alle erforderlichen Werkzeuge und Konfigurationen bereit haben, bevor Sie mit Ihrem Projekt beginnen.
:::


## 1. BBj herunterladen und installieren {#1-bbj-download-and-installation}

<b>Während Sie diesen Schritt ausführen, stellen Sie sicher, dass Sie die BBj-Version installieren, die der gleichen webforJ-Version entspricht.</b><br/><br/>

[Dieses Video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) kann Ihnen bei der Installation von BBj helfen, falls Sie Unterstützung bei der Einrichtung benötigen. Der Installationsbereich der BASIS-Website ist [unter diesem Link](https://basis.cloud/download-product) zu finden.

:::tip
Es wird empfohlen, die neueste stabile Überarbeitungsbuild von BBj zu verwenden und "BBj" aus der Liste der Optionen auszuwählen, ohne Barista oder Addon.
:::


<a name='section3'></a>

## 2. Installieren und Konfigurieren des webforJ-Plugins

Sobald BBj installiert ist, kann der Plugin Manager aufgerufen werden, um die Werkzeuge zu installieren, die zur Konfiguration von webforJ benötigt werden. Als erstes geben Sie "Plugin Manager" in das Startmenü oder Finder ein.

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Nachdem der Plugin-Manager geöffnet wurde, navigieren Sie zur Registerkarte "Verfügbare Plugins" oben.

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

Sobald Sie sich in diesem Abschnitt befinden, aktivieren Sie das Kontrollkästchen "Zeige Versionen in Entwicklung".

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

Der DWCJ-Eintrag sollte nun in der Liste der verfügbaren Plugins zum Herunterladen sichtbar sein. Klicken Sie auf diesen Eintrag in der Liste, um ihn auszuwählen.

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Mit dem ausgewählten DWCJ-Eintrag klicken Sie auf die Schaltfläche "Installieren".

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

Sobald das Plugin installiert ist, klicken Sie auf die Registerkarte "Installierte Plugins" oben.

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

Diese Registerkarte zeigt die installierten Plugins an, die nun den DWCJ-Eintrag enthalten sollten. Klicken Sie auf den Eintrag in der Liste.

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

Mit dem ausgewählten DWCJ-Eintrag klicken Sie auf die Schaltfläche "Konfigurieren".

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

Im geöffneten Fenster klicken Sie auf die Schaltfläche "Maven Remote Install aktivieren" unten links im Fenster.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip 

Alternativ können Sie zum Verzeichnis `bin` innerhalb Ihres `bbx`-Ordners navigieren und den folgenden Befehl ausführen:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Ein Dialog sollte anzeigen, dass die Remote-Installation aktiviert wurde. Klicken Sie auf "OK", um diesen Dialog zu schließen.

## 3. Verwendung des Starter-Projekts
Sobald BBj und das erforderliche webforJ-Plugin installiert und konfiguriert sind, können Sie ein neues, strukturiertes Projekt über die Kommandozeile erstellen. Dieses Projekt enthält die notwendigen Werkzeuge, um Ihr erstes webforJ-Programm auszuführen.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Die App starten

Sobald dies erledigt ist, führen Sie `mvn install` in Ihrem Projektverzeichnis aus. Dies wird das webforJ-Installations-Plugin ausführen und Ihnen Zugriff auf Ihre App ermöglichen. Um die App zu sehen, gehen Sie zu folgender URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Hostport, den Sie mit Docker konfiguriert haben, und `YourPublishName` wird durch den Text innerhalb des `<publishname>`-Tags der POM ersetzt. 
Wenn Sie alles korrekt gemacht haben, sollten Sie Ihre App sehen können.
