---
title: Übersicht
hide_giscus_comments: true
_i18n_hash: 4174ea766ba47277c5bcb607c4111e29
---
Dieses Tutorial soll Sie Schritt für Schritt durch den Prozess der Erstellung der App führen. Diese App, die zur Verwaltung von Kundeninformationen konzipiert ist, demonstriert, wie man webforJ verwendet, um eine funktionale und benutzerfreundliche Oberfläche mit Funktionen zum Anzeigen, Hinzufügen und Bearbeiten von Kundendaten zu erstellen. Jeder Abschnitt baut auf dem vorherigen auf, aber fühlen Sie sich frei, nach Bedarf vorzuspringen.

Jeder Schritt im Tutorial führt zu einem Programm, das in eine WAR-Datei kompiliert wird, die auf jedem Java-Webanwendungsserver bereitgestellt werden kann. Für dieses Tutorial wird das Maven Jetty-Plugin verwendet, um die App lokal bereitzustellen. Dieses leichte Setup stellt sicher, dass die App schnell ausgeführt werden kann und dass Änderungen während der Entwicklung in Echtzeit sichtbar sind.

## Funktionen der Tutorial-App {#tutorial-app-features}

 - Arbeiten mit Daten in einer Tabelle.
 - Verwendung von [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) und Asset-Management.
 - [Routing](../../routing/overview) und [Navigation](../../routing/route-navigation)
 - [Datenbindungen](../../data-binding/overview) und [Validierung](../../data-binding/validation/overview)

## Voraussetzungen {#prerequisites}

Um das Beste aus diesem Tutorial herauszuholen, wird vorausgesetzt, dass Sie über grundlegende Kenntnisse der Java-Programmierung verfügen und mit Tools wie Maven vertraut sind. Wenn Sie neu bei webforJ sind, keine Sorge - die Grundlagen des Frameworks werden im Verlauf behandelt.

Die folgenden Tools/Ressourcen sollten auf Ihrem Entwicklungsrechner vorhanden sein:

<!-- vale off -->
- Java 17 oder höher
- Maven
- Eine Java-IDE
- Ein Webbrowser
- Git (empfohlen, aber nicht erforderlich)
<!-- vale on -->

:::tip webforJ Voraussetzungen
Siehe [diesen Artikel](../prerequisites) für einen detaillierteren Überblick über die erforderlichen Tools.
:::

## Abschnitte {#sections}

Das Tutorial ist in die folgenden Abschnitte unterteilt. Folgen Sie den einzelnen Schritten für eine umfassende Anleitung oder springen Sie für spezifische Informationen vor.

:::tip Projektsetup
Für diejenigen, die zu spezifischen Themen vorspringen möchten, wird empfohlen, zunächst den Abschnitt Projektsetup zu lesen, bevor Sie fortfahren.
:::

<DocCardList className="topics-section" />
