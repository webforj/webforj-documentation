---
title: SideMenu
sidebar_position: 3
hide_table_of_contents: true
_i18n_hash: 0d0c302e47e1711d573c9bf6860547ae
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# SideMenu-Archetyp
<!-- vale on -->

Für Projekte, die ein strukturiertes Navigationssystem benötigen, ist das `sidemenu`-Archetyp ein großartiger Ausgangspunkt. Dieses Archetyp enthält ein Seitenmenü und einen Inhaltsbereich und ist darauf ausgelegt, Ihnen zu helfen, Apps mit einer klaren und intuitiven Navigationsstruktur zu erstellen, wodurch es für Benutzer einfacher wird, verschiedene Teile Ihrer App zu finden und darauf zuzugreifen.

:::tip Verwendung von startforJ
Für mehr Kontrolle über die Anpassung und Konfiguration können Sie [startforJ](https://docs.webforj.com/startforj/) verwenden, um Ihr Projekt zu erstellen - wählen Sie einfach das `SideMenu`-Archetyp aus, wenn Sie die Konfigurationsoptionen auswählen.
:::

## Verwendung des `sidemenu`-Archetyps {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
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
