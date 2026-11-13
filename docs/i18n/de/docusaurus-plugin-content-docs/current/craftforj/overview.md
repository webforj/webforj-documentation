---
sidebar_position: 1
title: craftforJ
slug: /craftforj
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Inspect the component tree of a running webforJ app, change components live,
  and write the changes you keep back into your Java source.
sidebar_class_name: new-content
_i18n_hash: 727e01ac5960e204363665aba672d6c7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# craftforJ <DocChip chip='since' label='26.02' />
<!-- vale on -->

**craftforJ** ist die visuelle Entwicklungsumgebung, die mit webforJ ausgeliefert wird. Sie läuft im Entwicklungsmodus innerhalb Ihrer App und zeigt live die Komponenten an, die Ihr Java-Code erstellt hat. Sie können eine Komponente auswählen, ihre Eigenschaften ändern, die sofortige Aktualisierung der laufenden App sehen und die gewünschten Änderungen in die Java-Datei zurückschreiben, in der die Komponente erstellt wurde.

<!-- INTRO_END -->

Da craftforJ die App über webforJ selbst ausliest, beschreibt es die App anhand der Begriffe, mit denen Sie sie geschrieben haben. Der Komponentenbaum zeigt Ihre Komponenten statt des Markups, das der Browser gerendert hat. Die Eigenschaften entsprechen den von Ihren Komponenten deklarierten Eigenschaften, und die Routen entsprechen den von Ihrem Router registrierten Routen, einschließlich der Zugriffskontrollen, die Sie dafür festgelegt haben.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Was Sie damit tun können {#what-you-can-do-with-it}

- **[Komponenten untersuchen](/docs/craftforj/inspector)** – Durchsuchen Sie den Komponentenbaum, wählen Sie eine Komponente durch Anklicken auf der Seite aus und ändern Sie ihre Eigenschaften, während die App läuft.
- **[Änderungen in den Quellcode schreiben](/docs/craftforj/source-changes)** – Überprüfen Sie Ihre Live-Änderungen als Diff und übernehmen Sie sie in Ihre Java-Dateien.
- **[Mit Routen arbeiten](/docs/craftforj/routes)** – Sehen Sie sich die Routing-Tabelle an, navigieren Sie zu einer beliebigen Route und ändern Sie die dafür festgelegten Zugriffskontrollen.
- **[Die App gestalten](/docs/craftforj/theme)** – Passen Sie die Design-Tokens an, aus denen Ihre App aufgebaut ist, und speichern Sie das Ergebnis in Ihrem Stylesheet.
- **[Den KI-Agenten verwenden](/docs/craftforj/ai)** – Ein Coding-Agent innerhalb der laufenden App, der beliebigen Java-Code schreibt, diesen kompiliert und nach Ihrer Freigabe anwendet.

## Unterschiede zu einem Debugger {#how-it-differs-from-a-debugger}

Ein Debugger hält Ihren Code an und zeigt Ihnen den Zustand Ihrer Variablen zu diesem Zeitpunkt. craftforJ lässt die App weiterlaufen und zeigt Ihnen die von Ihrem Code erzeugte Benutzeroberfläche. Sie arbeiten also mit dem Ergebnis statt mit der Ausführung. Die beiden beantworten unterschiedliche Fragen und werden häufig gemeinsam verwendet.

## Nur im Entwicklungsmodus {#development-mode-only}

Für craftforJ müssen zwei separate Einstellungen aktiviert sein. Standardmäßig antwortet es nur dem Browser, der auf demselben Rechner wie die App läuft. Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder einem webforJ-[Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, aktivieren craftforJ automatisch, sodass es bereits beim ersten Start verfügbar ist. Unter [Sicherheit](/docs/craftforj/security) erfahren Sie, worauf craftforJ zugreifen kann und wie Sie überprüfen, dass es in der Produktionsumgebung deaktiviert ist.

## Themen {#topics}

<DocCardList className="topics-section" />
