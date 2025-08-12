---
title: Getting Started
sidebar_position: 2
_i18n_hash: 32a742a43fe6dd9e983eaf428e04e06d
---
Dieser Artikel beschreibt die Schritte zur Erstellung einer neuen webforJ-App mit webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Startercode, sodass Sie ein Projekt schnell einrichten und ausführen können. Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Befehlszeile](#using-the-command-line) verwenden.

:::tip Voraussetzungen
Bevor Sie beginnen, überprüfen Sie die erforderlichen [Voraussetzungen](./prerequisites) für die Einrichtung und Nutzung von webforJ.
:::


## Verwendung von startforJ {#using-startforj}

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort damit beginnen können, darauf aufzubauen.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Anpassung mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektmetadaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- Geschmacksrichtung

Es gibt zwei Geschmacksrichtungen zur Auswahl, wobei "webforJ Only" die Standardoption ist:
  - **webforJ Only**: Standard-webforJ-App
  - **webforJ + Spring Boot**: webforJ-App mit Unterstützung für Spring Boot

:::caution Unterstützung für Spring Boot
Die Spring Boot-Geschmacksrichtung ist nur in webforJ-Version 25.02 und höher verfügbar. Wenn Sie diese Option auswählen, stellen Sie sicher, dass Sie eine kompatible Version wählen.
:::

:::tip Verfügbare Archetypen
webforJ kommt mit mehreren vordefinierten Archetypen, um Ihnen einen schnellen Einstieg zu ermöglichen. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
:::

Mit diesen Informationen wird startforJ ein grundlegendes Projekt aus Ihrem gewählten Archetyp mit Ihren bevorzugten Anpassungen erstellen. Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen möchten.

Sobald Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE und fahren Sie mit [dem Ausführen der App](#running-the-app) fort.

## Verwendung der Befehlszeile {#using-the-command-line}


Wenn Sie die Befehlszeile bevorzugen, können Sie ein Projekt direkt mit dem Maven-Archetyp generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](./prerequisites.md), falls Sie dies noch nicht getan haben. Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für Standard-webforj-App
mvn jetty:run

# für webforj + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App anzuzeigen.

:::info Lizenzierung und Wasserzeichen
Für Informationen über das Wasserzeichen, das in unlizenzierten Projekten vorhanden ist, siehe [Lizenzierung und Wasserzeichen](../configuration/licensing-and-watermark).
:::
