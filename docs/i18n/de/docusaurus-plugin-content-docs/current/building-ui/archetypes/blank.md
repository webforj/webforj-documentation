---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 5e7b116f0fea5cee2aa0d880d6fee05a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Leeres Archetyp

Das `blank` Archetyp ist ein grundlegendes Starterprojekt für webforJ-Anwendungen. Diese Vorlage bietet eine saubere Grundlage, von der aus Sie Ihre App von Grund auf neu erstellen können. Sie ist ideal für Entwickler, die vollständige Kontrolle über die Struktur und die Komponenten ihrer App ohne vordefinierte Einschränkungen wünschen.

:::tip Verwendung von startforJ
Für mehr Kontrolle über Anpassung und Konfiguration können Sie [startforJ](https://docs.webforj.com/startforj/) verwenden, um Ihr Projekt zu erstellen - wählen Sie einfach das `Blank` Archetyp bei der Auswahl der Konfigurationsoptionen.
:::

## Verwendung des `blank` Archetyps {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](../../introduction/prerequisites), falls Sie dies noch nicht getan haben. 
Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für Standard-webforJ-App
mvn jetty:run

# für webforJ + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App anzuzeigen.
