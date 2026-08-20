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
_i18n_hash: 6b642a9d173c5943acbb99934542e3a3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** ist die visuelle Entwicklungsumgebung, die mit webforJ geliefert wird. Es läuft innerhalb Ihrer Anwendung im Entwicklungsmodus und gibt Ihnen eine Live-Ansicht der Komponenten, die Ihr Java-Code erstellt hat. Sie können eine Komponente auswählen, deren Eigenschaften ändern, die laufende Anwendung sofort aktualisieren und die Änderungen, die Sie beibehalten möchten, in die Java-Datei zurückschreiben, die sie erstellt hat.

<!-- INTRO_END -->

Da craftforJ die Anwendung durch webforJ selbst liest, beschreibt es die Anwendung mit den Begriffen, in denen Sie sie geschrieben haben. Der Baum listet Ihre Komponenten auf, anstatt das Markup, das der Browser gerendert hat. Die Eigenschaften sind die, die Ihre Komponenten deklarieren, und die Routen sind die, die Ihr Router registriert hat, zusammen mit den Zugriffsregeln, die Sie mit Annotationen versehen haben.

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4" type="video/mp4" />
      </video>
</div>

## Was Sie damit tun können {#what-you-can-do-with-it}

- **[Komponenten inspizieren](/docs/craftforj/inspector)** - durchsuchen Sie den Komponentenbaum, wählen Sie eine Komponente aus, indem Sie darauf klicken, und ändern Sie deren Eigenschaften, während die Anwendung läuft.
- **[Änderungen am Quellcode schreiben](/docs/craftforj/source-changes)** - überprüfen Sie Ihre Live-Bearbeitungen als Diff und wenden Sie sie auf Ihre Java-Dateien an.
- **[Mit Routen arbeiten](/docs/craftforj/routes)** - sehen Sie die Routing-Tabelle, navigieren Sie zu einer beliebigen Route und ändern Sie die deklarierten Zugriffsregeln.
- **[Die App gestalten](/docs/craftforj/theme)** - passen Sie die Design-Token an, auf denen Ihre App basiert, und speichern Sie das Ergebnis in Ihrem Stylesheet.
- **[Den KI-Agenten verwenden](/docs/craftforj/ai)** - ein Programmieragent innerhalb der laufenden Anwendung, der Java frei schreibt, das Geschriebene kompiliert und es mit Ihrer Zustimmung anwendet.

## Wie es sich von einem Debugger unterscheidet {#how-it-differs-from-a-debugger}

Ein Debugger pausiert Ihren Code und zeigt Ihnen den Zustand Ihrer Variablen in diesem Moment. craftforJ lässt die Anwendung laufen und zeigt Ihnen die Benutzeroberfläche, die Ihr Code erstellt hat, sodass Sie mit dem Ergebnis und nicht mit der Ausführung arbeiten. Die beiden beantworten unterschiedliche Fragen und werden häufig zusammen verwendet.

## Nur im Entwicklungsmodus {#development-mode-only}

craftforJ erfordert zwei separate Einstellungen, die aktiviert sein müssen, und standardmäßig antwortet es nur auf den Browser, der auf demselben Computer wie die Anwendung läuft. Projekte, die mit [startforJ](https://docs.webforj.com/startforj) oder von einem webforJ [Archetyp](/docs/building-ui/archetypes/overview) erstellt wurden, aktivieren es für Sie, sodass es beim ersten Ausführen verfügbar ist. Weitere Informationen zu dem, was craftforJ erreichen kann, und wie Sie bestätigen können, dass es in der Produktion deaktiviert ist, finden Sie unter [Sicherheit](/docs/craftforj/security).

## Themen {#topics}

<DocCardList className="topics-section" />
