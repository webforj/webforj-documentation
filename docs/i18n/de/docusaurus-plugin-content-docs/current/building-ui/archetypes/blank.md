---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 135ed95be60a01a6a5ccb297c6bcce8f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Leeres Archetyp

Das `blank` Archetyp ist ein grundlegendes Starterprojekt für webforJ-Anwendungen. Diese Vorlage bietet eine saubere Grundlage, um Ihre App von Grund auf neu zu erstellen. Es ist ideal für Entwickler, die vollständige Kontrolle über die Struktur und Komponenten ihrer App ohne vordefinierte Einschränkungen wünschen.

:::tip Verwendung von startforJ
Für mehr Kontrolle über Anpassung und Konfiguration können Sie [startforJ](https://docs.webforj.com/startforj/) verwenden, um Ihr Projekt zu erstellen – wählen Sie einfach das `Blank` Archetyp, wenn Sie die Konfigurationsoptionen auswählen.
:::

## Verwendung des `blank` Archetyps {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](../../introduction/prerequisites), falls Sie dies noch nicht getan haben. Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für standard webforJ app
mvn jetty:run

# für webforJ + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App anzuzeigen.
