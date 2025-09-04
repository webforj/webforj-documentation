---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: 145d1e89a5f688fa0c912b87056a35d1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# HelloWorld-Archetyp
<!-- vale on -->

Dieses Archetyp erstellt eine einfache Hello World-App, um die Grundlagen des Aufbaus einer Benutzeroberfläche mit webforJ zu demonstrieren. Diese Vorlage ist großartig für Anfänger, um schnell zu starten. Sie bietet ein einfaches Beispiel dafür, wie man eine grundlegende webforJ-App einrichtet und ausführt, was sie zu einem ausgezeichneten Ausgangspunkt für neue Entwickler macht.

:::tip Von Grund auf neu starten
Dieses Archetyp erstellt eine minimalistischen App mit einigen Komponenten und etwas Styling. Für Entwickler, die ein Projekt mit minimaler Struktur erstellen möchten, siehe das [`blank` Archetyp](./blank).
:::

:::tip Verwendung von startforJ
Für mehr Kontrolle über Anpassungen und Konfigurationen können Sie [startforJ](https://docs.webforj.com/startforj/) verwenden, um Ihr Projekt zu erstellen - wählen Sie einfach das `HelloWorld` Archetyp, wenn Sie Konfigurationsoptionen auswählen.
:::

## Verwendung des `hello-world` Archetyps {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](../../introduction/prerequisites), falls Sie dies noch nicht getan haben. 
Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für die standardmäßige webforJ-App
mvn jetty:run

# für webforJ + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App anzuzeigen.
