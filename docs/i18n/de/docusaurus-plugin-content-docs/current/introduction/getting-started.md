---
title: Einsteigen
sidebar_position: 2
_i18n_hash: 5c658711bfa3dc70787cccbf2dfb6d2d
---
Dieser Artikel beschreibt die Schritte zur Erstellung einer neuen webforJ-App mit den webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Starter-Code, damit Sie ein Projekt schnell einrichten und ausführen können. 
Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Befehlszeile](#using-the-command-line) verwenden.

:::tip Voraussetzungen
Bevor Sie beginnen, überprüfen Sie die notwendigen [Voraussetzungen](./prerequisites) für die Einrichtung und Nutzung von webforJ.
:::

## Verwendung von startforJ {#using-startforj}

Die einfachste Möglichkeit, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Aufbau beginnen können.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Anpassen mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version
- Farbthema und Icon
- Archetyp
- Geschmac

Es gibt zwei Geschmacksoptionen zur Auswahl, wobei "webforJ Only" die Standardoption ist:
  - **webforJ Only**: Standard-webforJ-App
  - **webforJ + Spring Boot**: webforJ-App mit Unterstützung für Spring Boot

:::tip Verfügbare Archetypen
webforJ kommt mit mehreren vordefinierten Archetypen, um Ihnen den schnellen Einstieg zu erleichtern. Für eine vollständige Liste der verfügbaren Archetypen siehe den [Archetypen-Katalog](../building-ui/archetypes/overview).
:::

Mit diesen Informationen wird startforJ ein einfaches Projekt aus dem von Ihnen gewählten Archetyp mit Ihren gewählten Anpassungen erstellen. 
Sie können Ihr Projekt als ZIP-Datei herunterladen oder es direkt auf GitHub veröffentlichen.

Nachdem Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE und fahren Sie mit [dem Ausführen der App](#running-the-app) fort.

## Verwendung der Befehlszeile {#using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, können Sie ein Projekt direkt mit dem Maven-Archetyp generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
