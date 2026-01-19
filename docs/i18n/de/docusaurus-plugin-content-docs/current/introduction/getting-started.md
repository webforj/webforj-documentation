---
title: Getting Started
sidebar_position: 2
_i18n_hash: 5a051bf7c5a9494b21ba5df3629c35b4
---
Dieser Artikel beschreibt die Schritte zur Erstellung einer neuen webforJ-App mit webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Starter-Code, sodass Sie ein Projekt schnell zum Laufen bringen können. Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Befehlszeile](#using-the-command-line) verwenden.

:::tip Voraussetzungen
Bevor Sie beginnen, überprüfen Sie die notwendigen [Voraussetzungen](./prerequisites) für die Einrichtung und Nutzung von webforJ.
:::


## Verwendung von startforJ {#using-startforj}

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Aufbau beginnen können.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>


### Anpassen mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie diese anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Themenfarbe und -symbol
- Archetyp
- Flavor

Es gibt zwei Flavor-Optionen zur Auswahl, wobei "webforJ Only" die Standardoption ist:
  - **webforJ Only**: Standard-webforJ-App
  - **webforJ + Spring Boot**: webforJ-App mit Spring Boot-Unterstützung

:::tip Verfügbare Archetypen
webforJ verfügt über mehrere vordefinierte Archetypen, die Ihnen helfen, schnell zu starten. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
:::

Mit diesen Informationen erstellt startforJ ein grundlegendes Projekt aus Ihrem gewählten Archetyp mit Ihren gewählten Anpassungen. Sie können Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen.

Sobald Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE.

## Verwendung der Befehlszeile {#using-the-command-line}


Wenn Sie die Befehlszeile bevorzugen, können Sie ein Projekt direkt mit dem Maven-Archetyp generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
