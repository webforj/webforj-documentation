---
title: Getting Started
sidebar_position: 2
_i18n_hash: e0271a7db26a5c4b3fdc29508119aade
---
Dieser Artikel beschreibt die Schritte zum Erstellen einer neuen webforJ-App mit webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Starter-Code, damit Sie ein Projekt schnell einrichten und starten können. Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Befehlszeile](#using-the-command-line) verwenden.

:::tip Voraussetzungen
Bevor Sie beginnen, prüfen Sie die erforderlichen [Voraussetzungen](./prerequisites) für die Einrichtung und Verwendung von webforJ.
:::


## Verwendung von startforJ {#using-startforj}

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Startprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, damit Sie sofort mit dem Bau beginnen können.

### Anpassung mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie diese anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbdesign und Icon
- Archetyp
- Flavor

Es gibt zwei Flavor-Optionen zur Auswahl, wobei "webforJ Only" die Standardoption ist:
  - **webforJ Only**: Standard-webforJ-App
  - **webforJ + Spring Boot**: webforJ-App mit Unterstützung für Spring Boot

:::tip Verfügbare Archetypen
webforJ wird mit mehreren vordefinierten Archetypen geliefert, um Ihnen den Einstieg zu erleichtern. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
:::

Mit diesen Informationen erstellt startforJ ein Basisprojekt aus Ihrem gewählten Archetyp mit Ihren gewählten Anpassungen. Sie können Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen.

Sobald Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE.

## Verwendung der Befehlszeile {#using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, können Sie ein Projekt direkt mit dem Maven-Archetyp generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
