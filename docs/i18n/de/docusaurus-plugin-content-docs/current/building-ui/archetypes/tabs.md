---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: ba161760eed1006a71d42f2d566aff54
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Tabs-Archetyp

Das `tabs` Startprojekt generiert eine App mit einer einfachen Tab-Oberfläche. Ideal für Projekte, die mehrere Ansichten oder Bereiche erfordern, die über Tabs zugänglich sind, bietet dieses Archetyp eine saubere und organisierte Möglichkeit, verschiedene Teile Ihrer App zu verwalten, was das Navigieren zwischen verschiedenen Bereichen erleichtert, ohne die Benutzeroberfläche zu überladen.

:::tip Verwendung von startforJ
Für mehr Kontrolle über Anpassungen und Konfigurationen können Sie [startforJ](https://docs.webforj.com/startforj/) verwenden, um Ihr Projekt zu erstellen - wählen Sie einfach das `Tabs` Archetyp, wenn Sie die Konfigurationsoptionen auswählen.
:::

## Verwendung des `tabs` Archetyps {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](../../introduction/prerequisites), falls Sie dies noch nicht getan haben. 
Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für eine Standard webforJ-App
mvn jetty:run

# für webforJ + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App anzuzeigen.
