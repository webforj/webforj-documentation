---
title: Getting Started
sidebar_position: 2
_i18n_hash: 24c0a494b270fb4ea83106005e173ae8
---
Dieser Artikel beschreibt die Schritte zur Erstellung einer neuen webforJ-App mithilfe von webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Startercode, sodass Sie ein Projekt schnell einrichten und ausführen können. 
Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Befehlszeile](#using-the-command-line) verwenden.

:::tip Voraussetzungen
Bevor Sie beginnen, überprüfen Sie die erforderlichen [Voraussetzungen](./prerequisites) für die Einrichtung und Verwendung von webforJ.
:::

## Verwendung von startforJ {#using-startforj}

Die einfachste Möglichkeit, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort darauf aufbauen können.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Anpassung mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie diese anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektmetadaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Themenfarbe und Symbol
- Archetyp
- Geschmack

Es gibt zwei Geschmacksoptionen zur Auswahl, wobei "webforJ Only" die Standardoption ist:
  - **webforJ Only**: Standard-webforJ-App
  - **webforJ + Spring Boot**: webforJ-App mit Spring Boot-Unterstützung

:::caution Unterstützung für Spring Boot
Die Spring Boot-Option ist nur in webforJ-Version 25.02 und höher verfügbar. Wenn Sie diese Option auswählen, stellen Sie sicher, dass Sie eine kompatible Version wählen.
:::

:::tip Verfügbare Archetypen
webforJ wird mit mehreren vordefinierten Archetypen geliefert, um Ihnen einen schnellen Start zu ermöglichen. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
:::

Mit diesen Informationen erstellt startforJ ein grundlegendes Projekt aus Ihrem gewählten Archetyp mit Ihren gewählten Anpassungen. 
Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen möchten.

Sobald Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE und fahren Sie mit dem [Ausführen der App](#running-the-app) fort.

## Verwendung der Befehlszeile {#using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, können Sie ein Projekt direkt mithilfe des Maven-Archetyps generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](./prerequisites.md), falls Sie dies noch nicht getan haben. 
Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für die Standard-webforJ-App
mvn jetty:run

# für webforJ + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App anzuzeigen.
