---
title: Einführung
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: ea669974a5f1b3a02a162f89bb16c6e3
---
Dieser Artikel beschreibt die Schritte zur Erstellung einer neuen webforJ-App mit Hilfe von webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Starter-Code, sodass Sie ein Projekt schnell aufsetzen und in Betrieb nehmen können. Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Kommandozeile](#using-the-command-line) verwenden.

:::tip Voraussetzungen
Bevor Sie beginnen, überprüfen Sie die notwendigen [Voraussetzungen](./prerequisites) für die Einrichtung und Nutzung von webforJ.
:::


## Verwendung von startforJ {#using-startforj}

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetyp generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Bauen beginnen können.

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div> -->


### Anpassung mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie diese anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)
- webforJ-Version und Java-Version
- Farbthema und Symbol
- Archetyp
- Laufumgebung

Die Wahl der **Laufumgebung** bestimmt, wo die App ausgeführt wird, wobei **Servlet-Container** als Standard festgelegt ist:
  - **Servlet-Container**: eine WAR für Jetty, Tomcat oder einen anderen Servlet-Container
  - **Spring Boot**: eine ausführbare JAR mit einem eingebetteten Server
  - **BBjServices**: eine App, die über [BBjShip](/docs/configuration/bbjship/overview) in BBjServices ausgeführt wird

:::tip Verfügbare Archetypen
webforJ bietet mehrere vordefinierte Archetypen, um Ihnen den Einstieg zu erleichtern. Eine vollständige Liste der verfügbaren Archetypen finden Sie im [Archetypen-Katalog](../building-ui/archetypes/overview).
:::

Mit diesen Informationen wird startforJ ein Grundprojekt aus Ihrem gewählten Archetyp mit Ihren gewählten Anpassungen erstellen. Sie können Ihr Projekt entweder als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen.

Nachdem Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE.

## Verwendung der Kommandozeile {#using-the-command-line}

Wenn Sie die Kommandozeile bevorzugen, können Sie ein Projekt direkt mit dem Maven-Archetyp generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
