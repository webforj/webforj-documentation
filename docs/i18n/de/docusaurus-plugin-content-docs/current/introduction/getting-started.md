---
title: Getting Started
sidebar_position: 2
_i18n_hash: 37b65c983d6210a89474156b10af1e93
---
Dieser Artikel beschreibt die Schritte zur Erstellung einer neuen webforJ-App mithilfe von webforJ [Archetypen](../building-ui/archetypes/overview.md). Archetypen bieten vorkonfigurierte Projektstrukturen und Startcode, sodass Sie ein Projekt schnell einrichten und starten können.  
Um eine neue webforJ-App aus einem Archetyp zu erstellen, können Sie [startforJ](#using-startforj) oder die [Befehlszeile](#using-the-command-line) verwenden. 

:::tip Voraussetzungen  
Bevor Sie beginnen, überprüfen Sie die erforderlichen [Voraussetzungen](./prerequisites) für die Einrichtung und Nutzung von webforJ.  
:::

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

## Verwendung von startforJ {#using-startforj}

Der einfachste Weg, eine neue webforJ-App zu erstellen, ist [startforJ](https://docs.webforj.com/startforj), das ein minimales Starterprojekt basierend auf einem gewählten webforJ-Archetypen generiert. Dieses Starterprojekt enthält alle erforderlichen Abhängigkeiten, Konfigurationsdateien und ein vorgefertigtes Layout, sodass Sie sofort mit dem Aufbau beginnen können.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Anpassung mit startforJ {#customizing-with-startforj}

Wenn Sie eine App mit [startforJ](https://docs.webforj.com/startforj) erstellen, können Sie sie anpassen, indem Sie die folgenden Informationen bereitstellen:

- Grundlegende Projektdaten (App-Name, Gruppen-ID, Artefakt-ID)  
- webforJ-Version und Java-Version  
- Farbthema und Symbol  
- Archetyp  
- Geschmacksrichtung  

Es gibt zwei Geschmacksrichtungen zur Auswahl, wobei "webforJ Only" die Standardoption ist:  
  - **webforJ Only**: Standard-webforJ-App  
  - **webforJ + Spring Boot**: webforJ-App mit Spring Boot-Unterstützung  

:::caution Unterstützung für Spring Boot  
Die Spring Boot-Geschmacksrichtung ist nur in der webforJ-Version 25.02 und höher verfügbar. Wenn Sie diese Option wählen, stellen Sie sicher, dass Sie eine kompatible Version auswählen.  
:::

:::tip Verfügbare Archetypen  
webforJ wird mit mehreren vordefinierten Archetypen geliefert, um Ihnen den schnellen Einstieg zu erleichtern. Eine vollständige Liste der verfügbaren Archetypen finden Sie im [Archetypen-Katalog](../building-ui/archetypes/overview).  
:::

Mit diesen Informationen wird startforJ ein grundlegendes Projekt aus Ihrem gewählten Archetyp mit Ihren gewünschten Anpassungen erstellen.  
Sie können wählen, ob Sie Ihr Projekt als ZIP-Datei herunterladen oder direkt auf GitHub veröffentlichen möchten.  

Sobald Sie Ihr Projekt heruntergeladen haben, öffnen Sie den Projektordner in Ihrer IDE und fahren Sie fort mit [der Ausführung der App](#running-the-app).

## Verwendung der Befehlszeile {#using-the-command-line}

Wenn Sie die Befehlszeile bevorzugen, können Sie ein Projekt direkt mit dem Maven-Archetyp generieren:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](./prerequisites.md), falls Sie dies noch nicht getan haben.  
Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für Standard-webforj-App
mvn jetty:run

# für webforj + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App anzuzeigen.

:::info Lizenzierung und Wasserzeichen  
Für Informationen über das Wasserzeichen in nicht lizenzierten Projekten siehe [Lizenzierung und Wasserzeichen](../configuration/licensing-and-watermark).  
:::
