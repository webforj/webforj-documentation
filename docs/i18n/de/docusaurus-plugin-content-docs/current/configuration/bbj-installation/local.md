---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: c6cfdc6b07db675d741ea4a096f286ca
---
# Lokale Installation

Dieser Abschnitt der Dokumentation behandelt die Schritte, die nur für Benutzer erforderlich sind, die webforJ für die Web- und/oder App-Entwicklung mit einer lokalen BBj-Instanz auf ihrem Rechner verwenden möchten. Diese Installation ermöglicht es den Benutzern nicht, zum Quellcode der webforJ Foundation selbst beizutragen.
<br/>

:::info
Diese Anleitung behandelt die Installation auf einem Windows-System - die Installationsschritte können für Mac-/Linux-Betriebssysteme abweichen.
:::
<br/>

Die Installation wird in die folgenden Schritte unterteilt:


1. BBj herunterladen und installieren
2. Verwendung des BBj Plugin Managers zur Erstellung Ihrer App
3. Starten Ihrer App


:::tip Voraussetzungen
Bevor Sie beginnen, stellen Sie sicher, dass Sie die notwendigen [Voraussetzungen](../../introduction/prerequisites) für die Einrichtung und Verwendung von webforJ überprüft haben. Dies stellt sicher, dass Sie alle erforderlichen Tools und Konfigurationen bereitgestellt haben, bevor Sie mit Ihrem Projekt beginnen.
:::


## 1. BBj herunterladen und installieren {#1-bbj-download-and-installation}

<b>Während Sie diesen Schritt ausführen, stellen Sie sicher, dass Sie die BBj-Version installieren, die der gleichen webforJ-Version entspricht.</b><br/><br/>

[Dieses Video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) kann Ihnen bei der Installation von BBj helfen, wenn Sie Unterstützung bei der Einrichtung benötigen. Der Installationsbereich der BASIS-Website ist [unter diesem Link](https://basis.cloud/download-product) zu finden.

:::tip
Es wird empfohlen, die neueste stabile Build-Version von BBj zu verwenden und "BBj" aus der Liste der Optionen auszuwählen, ohne Barista oder Addon.
:::

<a name='section3'></a>

## 2. Installieren und Konfigurieren des webforJ-Plugins

Sobald BBj installiert ist, kann der Plugin Manager aufgerufen werden, um die benötigten Werkzeuge zur Konfiguration von webforJ zu installieren. Geben Sie zunächst „Plugin Manager“ in das Startmenü oder den Finder ein.

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Nachdem der Plugin-Manager geöffnet wurde, navigieren Sie zum Tab „Verfügbare Plugins“ oben.

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

Aktivieren Sie in diesem Abschnitt das Kontrollkästchen „Versionen in Entwicklung anzeigen“.

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

Der DWCJ-Eintrag sollte jetzt in der Liste der verfügbaren Plugins zum Herunterladen sichtbar sein. Klicken Sie auf diesen Eintrag in der Liste, um ihn auszuwählen.

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Mit dem ausgewählten DWCJ-Eintrag klicken Sie auf die Schaltfläche „Installieren“.

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

Sobald das Plugin installiert ist, klicken Sie auf den Tab „Installierte Plugins“ oben.

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

Dieser Tab zeigt die installierten Plugins an, zu denen jetzt der DWCJ-Eintrag gehören sollte. Klicken Sie auf den Eintrag in der Liste.

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

Mit dem ausgewählten DWCJ-Eintrag klicken Sie auf die Schaltfläche „Konfigurieren“.

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

Im sich öffnenden Fenster klicken Sie auf die Schaltfläche „Maven Remote Install aktivieren“ unten links im Fenster.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip

Alternativ können Sie zum Verzeichnis `bin` innerhalb Ihres `bbx`-Ordners navigieren und den folgenden Befehl ausführen:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Ein Dialogfeld sollte anzeigen, dass die Remote-Installation aktiviert wurde. Klicken Sie auf „OK“, um dieses Dialogfeld zu schließen.

## 3. Verwendung des Starterprojekts
Sobald BBj und das erforderliche webforJ-Plugin installiert und konfiguriert sind, können Sie ein neues, scaffolded Projekt über die Befehlszeile erstellen. Dieses Projekt wird mit den notwendigen Tools ausgeliefert, um Ihr erstes webforJ-Programm auszuführen.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Starten der App

Sobald dies erledigt ist, führen Sie in Ihrem Projektverzeichnis `mvn install` aus. Dies wird das webforJ-Installationsplugin ausführen und Ihnen den Zugriff auf Ihre App ermöglichen. Um die App zu sehen, sollten Sie die folgende URL aufrufen:

`http://localhost:YourHostPort/webapp/YourPublishName`

Ersetzen Sie `YourHostPort` durch den Hostport, den Sie mit Docker konfiguriert haben, und `YourPublishName` durch den Text im `<publishname>`-Tag der POM. Wenn alles korrekt ausgeführt wurde, sollten Sie Ihre App sehen können.
