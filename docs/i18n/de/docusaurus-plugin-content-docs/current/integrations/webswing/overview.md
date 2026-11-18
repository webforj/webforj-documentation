---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Embed legacy Swing, JavaFX, or SWT desktop apps inside a webforJ UI through
  Webswing for progressive modernization and hybrid experiences.
_i18n_hash: 300c45aa8cc9f51831219f75d74d31b6
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing ist eine Webserver-Technologie, mit der Java-Desktopanwendungen (Swing, JavaFX, SWT) ohne Änderungen am ursprünglichen Quellcode in einem Webbrowser ausgeführt werden können. Die Desktopanwendung wird auf dem Server gerendert und ihre Benutzeroberfläche über einen HTML5-Canvas an den Browser übertragen. Dabei werden alle Benutzerinteraktionen transparent verarbeitet.

## Was Webswing löst {#what-webswing-solves}

Viele Unternehmen haben beträchtlich in Java-Desktopanwendungen investiert, die wichtige Geschäftslogik enthalten, die über Jahre oder Jahrzehnte hinweg entwickelt wurde. Diese Anwendungen lassen sich oft nicht ohne Weiteres neu schreiben, und zwar aus folgenden Gründen:

- Komplexe Domänenlogik, deren Neuentwicklung riskant wäre
- Integration mit desktop-spezifischen Bibliotheken oder Hardware
- Zeit- und Kostenaufwand einer vollständigen Neuentwicklung
- Notwendigkeit, den Funktionsumfang bestehender Anwendungen beizubehalten

Mit Webswing lassen sich diese Anwendungen ohne Änderungen über das Web bereitstellen, wobei ihre ursprüngliche Funktionalität und ihr Erscheinungsbild erhalten bleiben.

## Integration mit webforJ {#integration-with-webforj}

Die webforJ-Webswing-Integration stellt die Komponente `WebswingConnector` bereit, mit der Sie von Webswing gehostete Anwendungen direkt in Ihre webforJ-App einbetten können. Das eröffnet folgende Möglichkeiten:

### Schrittweise Modernisierung {#progressive-modernization}

Anstatt alles auf einmal neu zu schreiben, können Sie:

1. Ihre bestehende Swing-App zunächst mit `WebswingConnector` einbetten
2. Neue Funktionen in webforJ rund um die eingebettete App entwickeln
3. Swing-Komponenten schrittweise durch webforJ-Äquivalente ersetzen
4. Die Legacy-App schließlich vollständig ablösen

### Hybride Anwendungen {#hybrid-applications}

Kombinieren Sie eine moderne, mit webforJ erstellte Weboberfläche mit spezialisierten Desktop-Funktionen:

- Verwenden Sie webforJ für Benutzeroberflächen, Dashboards und Berichte
- Nutzen Sie Swing für komplexe Visualisierungen oder spezialisierte Editoren
- Sorgen Sie für ein einheitliches und integriertes App-Erlebnis

:::tip Swing-Komponenten ersetzen
Unter [Von Swing kommend](/docs/introduction/coming-from-swing) finden Sie Vergleiche zwischen Swing-Komponenten und ihren webforJ-Äquivalenten. Die Vergleiche enthalten hilfreiche Referenzen zur Verwendung der jeweiligen webforJ-Funktion.
:::

## Funktionsweise {#how-it-works}

Die Integration besteht aus drei Ebenen:

1. **Webswing-Server**: Führt Ihre Java-Desktopanwendung aus, erfasst deren visuelle Ausgabe und verarbeitet Benutzereingaben.
2. **WebswingConnector-Komponente**: Eine webforJ-Komponente, die den Webswing-Client einbettet und die Verbindung sowie die Kommunikation mit dem Server verwaltet.
3. **Kommunikationsprotokoll**: Ermöglicht den bidirektionalen Nachrichtenaustausch, sodass Ihre webforJ-App Befehle an die Swing-App senden und Ereignisse von ihr empfangen kann.

Wenn ein Benutzer auf Ihre webforJ-App zugreift, stellt `WebswingConnector` eine Verbindung zum Webswing-Server her. Der Server erstellt eine App-Instanz oder verbindet sich erneut mit einer bestehenden Instanz und beginnt, den visuellen Zustand an den Browser zu übertragen. Benutzerinteraktionen wie Maus- und Tastatureingaben werden erfasst und an den Server gesendet, wo sie in der eigentlichen Swing-App wiedergegeben werden.

## Themen {#topics}

<DocCardList className="topics-section" />
