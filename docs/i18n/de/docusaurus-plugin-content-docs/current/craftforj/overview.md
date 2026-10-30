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
_i18n_hash: e5fd219fd4360d24535c26696d1c9d1d
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** ist die visuelle Entwicklungsumgebung, die mit webforJ geliefert wird. Sie läuft in Ihrer App im Entwicklungsmodus und bietet Ihnen eine Live-Ansicht der Komponenten, die Ihr Java-Code erstellt hat. Sie können eine Komponente auswählen, ihre Eigenschaften ändern, sehen, wie sich die laufende App sofort aktualisiert, und die Änderungen, die Sie behalten möchten, zurück in die Java-Datei schreiben, die sie erstellt hat.

<!-- INTRO_END -->

Da craftforJ die App über webforJ selbst liest, beschreibt es die App in den Begriffen, in denen Sie sie geschrieben haben. Der Baum listet Ihre Komponenten auf, anstatt das Markup anzuzeigen, das der Browser gerendert hat; die Eigenschaften sind die, die Ihre Komponenten deklarieren, und die Routen sind die, die Ihr Router registriert hat, zusammen mit den Zugriffsregeln, die Sie annotiert haben.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Was Sie damit tun können {#what-you-can-do-with-it}

- **[Komponenten inspizieren](/docs/craftforj/inspector)** - durchsuchen Sie den Komponentenbaum, wählen Sie eine Komponente aus, indem Sie darauf in der Seite klicken, und ändern Sie ihre Eigenschaften, während die App läuft.
- **[Änderungen an den Quellcode schreiben](/docs/craftforj/source-changes)** - überprüfen Sie Ihre Live-Bearbeitungen als Diff und wenden Sie sie auf Ihre Java-Dateien an.
- **[Mit Routen arbeiten](/docs/craftforj/routes)** - sehen Sie die Routing-Tabelle, navigieren Sie zu beliebigen Routen und ändern Sie die darauf deklarierten Zugriffsregeln.
- **[Das App-Thema anpassen](/docs/craftforj/theme)** - passen Sie die Designtokens an, auf denen Ihre App basiert, und speichern Sie das Ergebnis in Ihrem Stylesheet.
- **[Den KI-Agenten nutzen](/docs/craftforj/ai)** - ein Programmieragent innerhalb der laufenden App, der Java unabhängig schreibt, was er geschrieben hat kompiliert und es mit Ihrer Genehmigung anwendet.

## Wie es sich von einem Debugger unterscheidet {#how-it-differs-from-a-debugger}

Ein Debugger pausiert Ihren Code und zeigt Ihnen den Status Ihrer Variablen in diesem Moment. craftforJ lässt die App laufen und zeigt Ihnen die Benutzeroberfläche, die Ihr Code erzeugt hat, sodass Sie mit dem Ergebnis anstelle der Ausführung arbeiten. Die beiden beantworten unterschiedliche Fragen und werden oft zusammen verwendet.

## Nur im Entwicklungsmodus {#development-mode-only}

craftforJ erfordert, dass zwei separate Einstellungen aktiviert sind, und standardmäßig antwortet es nur auf den Browser, der auf demselben Rechner wie die App läuft. Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder aus einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, aktivieren es für Sie, sodass es beim ersten Ausführen verfügbar ist. Siehe [Sicherheit](/docs/craftforj/security), um zu erfahren, worauf craftforJ Zugriff hat und wie Sie bestätigen können, dass es in der Produktion deaktiviert ist.

## Themen {#topics}

<DocCardList className="topics-section" />
