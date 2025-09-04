---
title: Tabs
sidebar_position: 2
hide_table_of_contents: true
_i18n_hash: bd6e6de9bb8396f7926e01ac2f34cfc3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Tabs-Archetyp

Das `tabs` Startprojekt generiert eine App mit einer einfachen Tab-Oberfläche. Ideal für Projekte, die mehrere Ansichten oder Abschnitte erfordern, die über Tabs zugänglich sind, bietet dieses Archetyp eine saubere und organisierte Möglichkeit, verschiedene Teile Ihrer App zu verwalten, wodurch es einfach wird, zwischen den verschiedenen Abschnitten zu navigieren, ohne die Benutzeroberfläche zu überladen.

:::tip Verwendung von startforJ
Für mehr Kontrolle über Anpassung und Konfiguration können Sie [startforJ](https://docs.webforj.com/startforj/) verwenden, um Ihr Projekt zu erstellen - wählen Sie einfach das `Tabs` Archetyp, wenn Sie die Konfigurationsoptionen auswählen.
:::

## Verwendung des `tabs` Archetyps {#using-the-tabs-archetype}

<ComponentArchetype
project="tabs"
/>

## Ausführen der App {#running-the-app}

Bevor Sie Ihre App ausführen, installieren Sie die [Voraussetzungen](../../introduction/prerequisites), falls Sie dies noch nicht getan haben. 
Navigieren Sie dann zum Stammverzeichnis des Projekts und führen Sie den folgenden Befehl aus:

```bash
# für standard webforJ App
mvn jetty:run

# für webforJ + Spring Boot
mvn spring-boot:run
```

Sobald der Server läuft, öffnen Sie Ihren Browser und gehen Sie zu [http://localhost:8080](http://localhost:8080), um die App zu sehen.
