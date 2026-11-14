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
_i18n_hash: 771ef18b5a680d5b9260f06a2704f877
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

**craftforJ** ist die visuelle Entwicklungsumgebung, die mit webforJ ausgeliefert wird. Sie läuft im Entwicklungsmodus innerhalb deiner App und zeigt dir live die Komponenten, die dein Java-Code erstellt hat. Du kannst eine Komponente auswählen, ihre Eigenschaften ändern, die Änderungen sofort in der laufenden App sehen und die gewünschten Änderungen zurück in die Java-Datei schreiben, in der die Komponente erstellt wurde.

<!-- INTRO_END -->

Da craftforJ die App über webforJ selbst ausliest, beschreibt es die App anhand der Begriffe, mit denen du sie geschrieben hast. Der Baum listet deine Komponenten statt des Markups auf, das der Browser gerendert hat, die Eigenschaften sind die von deinen Komponenten deklarierten und die Routen sind die von deinem Router registrierten – zusammen mit den Zugriffsregeln, die du ihnen als Annotationen hinzugefügt hast.

<VideoPlayer video="craftforj/intro" width={1920} height={1080} title="Intro" />

## Was du damit tun kannst {#what-you-can-do-with-it}

- **[Komponenten untersuchen](/docs/craftforj/inspector)** – Durchsuche den Komponentenbaum, wähle eine Komponente durch Anklicken auf der Seite aus und ändere ihre Eigenschaften, während die App läuft.
- **[Änderungen in den Quellcode schreiben](/docs/craftforj/source-changes)** – Überprüfe deine Live-Änderungen als Diff und übernimm sie in deine Java-Dateien.
- **[Mit Routen arbeiten](/docs/craftforj/routes)** – Sieh dir die Routing-Tabelle an, navigiere zu jeder Route und ändere die dafür deklarierten Zugriffsregeln.
- **[Das App-Design anpassen](/docs/craftforj/theme)** – Passe die Design-Tokens an, auf denen deine App basiert, und speichere das Ergebnis in deinem Stylesheet.
- **[Den KI-Agenten verwenden](/docs/craftforj/ai)** – Ein Programmieragent in der laufenden App, der uneingeschränkt Java schreibt, den geschriebenen Code kompiliert und ihn nach deiner Bestätigung anwendet.

## Wie es sich von einem Debugger unterscheidet {#how-it-differs-from-a-debugger}

Ein Debugger hält deinen Code an und zeigt dir den Zustand deiner Variablen zu diesem Zeitpunkt. craftforJ lässt die App weiterlaufen und zeigt dir die von deinem Code erzeugte Benutzeroberfläche. So arbeitest du mit dem Ergebnis statt mit der Ausführung. Beide beantworten unterschiedliche Fragen und werden häufig gemeinsam verwendet.

## Nur im Entwicklungsmodus {#development-mode-only}

Für craftforJ müssen zwei separate Einstellungen aktiviert sein. Standardmäßig reagiert es außerdem nur auf den Browser, der auf demselben Rechner wie die App läuft. Bei Projekten, die mit [startforJ](https://docs.webforj.com/startforj) oder anhand eines webforJ-[Archetyps](/docs/building-ui/archetypes/overview) erstellt wurden, ist craftforJ bereits aktiviert und steht dir somit beim ersten Start zur Verfügung. Unter [Sicherheit](/docs/craftforj/security) erfährst du, worauf craftforJ zugreifen kann und wie du bestätigst, dass es in der Produktion deaktiviert ist.

## Themen {#topics}

<DocCardList className="topics-section" />
