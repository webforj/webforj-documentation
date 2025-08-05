---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4d70b1e894fa3ca05afb5a4bc6ed982d
---
Dieses Tutorial soll Sie Schritt für Schritt durch den Prozess der Erstellung der App führen. Diese App, die zur Verwaltung von Kundendaten entwickelt wurde, demonstriert, wie man webforJ verwendet, um eine funktionale und benutzerfreundliche Oberfläche mit Funktionen zum Anzeigen, Hinzufügen und Bearbeiten von Kundendaten zu erstellen. Jeder Abschnitt baut auf dem vorherigen auf, aber fühlen Sie sich frei, bei Bedarf voranzuschreiten.

Jeder Schritt im Tutorial wird zu einem Programm führen, das in eine WAR-Datei kompiliert, die auf jedem Java-Webanwendungsserver bereitgestellt werden kann. Für dieses Tutorial wird das Maven Jetty-Plugin verwendet, um die App lokal bereitzustellen. Dieses leichtgewichtige Setup stellt sicher, dass die App schnell ausgeführt werden kann und dass Änderungen während der Entwicklung in Echtzeit angezeigt werden.

## Funktionen der Tutorial-App {#tutorial-app-features}

 - Arbeiten mit Daten in einer Tabelle.
 - Verwendung der [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) und Asset-Management.
 - [Routing](../../routing/overview) und [Navigation](../../routing/route-navigation)
 - [Datenbindungen](../../data-binding/overview) und [Validierung](../../data-binding/validation/overview)

## Voraussetzungen {#prerequisites}

Um das Beste aus diesem Tutorial herauszuholen, wird vorausgesetzt, dass Sie ein grundlegendes Verständnis der Java-Programmierung haben und mit Tools wie Maven vertraut sind. Wenn Sie neu bei webforJ sind, machen Sie sich keine Sorgen - die Grundlagen des Frameworks werden im Laufe des Tutorials behandelt.

Die folgenden Tools/Ressourcen sollten auf Ihrem Entwicklungsrechner vorhanden sein:

<!-- vale off -->
- Java 17 oder höher
- Maven
- Eine Java-IDE
- Ein Webbrowser
- Git (empfohlen, aber nicht erforderlich)
<!-- vale on -->

:::tip webforJ Voraussetzungen
Siehe [diesen Artikel](../prerequisites) für eine detailliertere Übersicht über die erforderlichen Tools.
:::

## Abschnitte {#sections}

Das Tutorial ist in die folgenden Abschnitte unterteilt. Gehen Sie sequenziell vor, um eine umfassende Anleitung zu erhalten, oder springen Sie vor, um spezifische Informationen zu erhalten.

:::tip Projektsetup
Für diejenigen, die zu bestimmten Themen voranspringen möchten, wird empfohlen, zunächst den Abschnitt Projektsetup zu lesen, bevor Sie fortfahren.
:::

<DocCardList className="topics-section" />
