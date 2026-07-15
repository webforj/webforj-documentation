---
title: Getting Started
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: c1867c61e2072cb6657bad9492f22083
---
Dieser Artikel beschreibt die Schritte zur Erstellung einer neuen webforJ-App mithilfe von webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Startercode, damit Sie ein Projekt schnell einrichten und starten können. Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Eingabeaufforderung](#using-the-command-line) verwenden.

:::tip Voraussetzungen
Bevor Sie beginnen, überprüfen Sie die erforderlichen [Voraussetzungen](./prerequisites) für die Einrichtung und Nutzung von webforJ.
:::


## Verwendung von startforJ {#using-startforj}

Die einfachste Möglichkeit, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, damit Sie sofort mit dem Aufbau beginnen können.

### Anpassen mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektemetadaten (App-Name, Gruppen-ID, Artefakt-ID)
- webforJ-Version und Java-Version
- Themenfarbe und Icon
- Archetyp
- Geschmack

Es gibt zwei Geschmacksoptionen zur Auswahl, wobei "webforJ Only" die Standardoption ist:
  - **webforJ Only**: Standard-webforJ-App
  - **webforJ + Spring Boot**: webforJ-App mit Unterstützung für Spring Boot

:::tip Verfügbare Archetypen
webforJ kommt mit mehreren vordefinierten Archetypen, um Ihnen den Einstieg zu erleichtern. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
:::

Mit diesen Informationen erstellt startforJ ein grundlegendes Projekt aus dem von Ihnen gewählten Archetyp mit Ihren gewählten Anpassungen. Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen möchten.

Sobald Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE.

## Verwendung der Eingabeaufforderung {#using-the-command-line}

Wenn Sie die Eingabeaufforderung bevorzugen, können Sie ein Projekt direkt mit dem Maven-Archetyp generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
