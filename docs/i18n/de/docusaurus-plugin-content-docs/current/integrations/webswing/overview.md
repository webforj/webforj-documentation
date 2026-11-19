---
title: 'Webswing mit webforJ: Eine Swing-Anwendung Schritt für Schritt modernisieren'
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Führen Sie mit Webswing Swing-, JavaFX- oder SWT-Anwendungen im Browser aus,
  betten Sie sie in eine webforJ-App ein und ersetzen Sie sie nach und nach
  durch Weboberflächen.
_i18n_title_hash: 698c1a9e98e2ef808c204b0bc4be4669
_i18n_description_hash: 3bd3bdba7583bc1699e8d6153577eb56
_i18n_hash: 53117d208e8a0b0a2e46705f9edd1cdc
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing führt eine Swing-, JavaFX- oder SWT-Anwendung ohne Änderungen am Quellcode im Browser aus. Mit webforJ betten Sie diese Anwendung in eine Webanwendung ein und ersetzen sie Bildschirm für Bildschirm. Damit beide Nachrichten austauschen können, fügen Sie die Webswing-API zur Swing-Anwendung hinzu.

Webswing ist eine Webserver-Technologie. Sie rendert die Desktop-Anwendung auf dem Server und überträgt die Benutzeroberfläche mithilfe von HTML5 Canvas an den Browser. Dabei werden alle Benutzerinteraktionen transparent verarbeitet.

Eine Übersicht über den gesamten Ablauf – von der Ausführung der Desktop-Anwendung im Browser bis zu ihrer Ablösung – finden Sie unter [Modernisierung von Swing](/modernization).

## Welche Probleme Webswing löst {#what-webswing-solves}

Viele Unternehmen haben erheblich in Java-Desktop-Anwendungen investiert, die wichtige, über Jahre oder Jahrzehnte entwickelte Geschäftslogik enthalten. Diese Anwendungen lassen sich oft nicht ohne Weiteres neu schreiben, und zwar aus folgenden Gründen:

- Komplexe Domänenlogik, deren Nachbildung riskant wäre
- Integration mit desktop-spezifischen Bibliotheken oder Hardware
- Zeit- und Kostenaufwand einer vollständigen Neuentwicklung
- Notwendigkeit, die Funktionsgleichheit mit der vorhandenen Anwendung zu gewährleisten

Webswing macht diese Anwendungen ohne Änderungen über das Web zugänglich. Ihre ursprünglichen Funktionen und ihr Erscheinungsbild bleiben erhalten.

## Integration mit webforJ {#integration-with-webforj}

Die Webswing-Integration von webforJ stellt die Komponente `WebswingConnector` bereit. Damit können Sie von Webswing gehostete Anwendungen direkt in Ihre webforJ-App einbetten. Das eröffnet folgende Möglichkeiten:

### Schrittweise Modernisierung {#progressive-modernization}

Anstatt alles auf einmal neu zu schreiben, können Sie:

1. Zunächst Ihre vorhandene Swing-Anwendung über `WebswingConnector` einbetten
2. Neue Funktionen in webforJ rund um die eingebettete Anwendung entwickeln
3. Swing-Komponenten schrittweise durch webforJ-Entsprechungen ersetzen
4. Die Legacy-Anwendung schließlich vollständig ablösen

### Hybride Anwendungen {#hybrid-applications}

Kombinieren Sie eine moderne, mit webforJ erstellte Weboberfläche mit spezialisierten Desktop-Funktionen:

- Nutzen Sie webforJ für benutzerorientierte Oberflächen, Dashboards und Berichte
- Behalten Sie Swing für komplexe Visualisierungen oder spezialisierte Editoren bei
- Sorgen Sie für eine einheitliche, integrierte Anwendungserfahrung

:::tip Swing-Komponenten ersetzen
Unter [Der Umstieg von Swing](/docs/introduction/coming-from-swing) finden Sie Vergleiche zwischen Swing-Komponenten und ihren Entsprechungen in webforJ. Die Vergleiche enthalten relevante Referenzen, die Ihnen bei der Nutzung der jeweiligen webforJ-Funktion helfen.
:::

## Funktionsweise {#how-it-works}

Die Integration besteht aus drei Ebenen:

1. **Webswing-Server**: Führt Ihre Java-Desktop-Anwendung aus, erfasst ihre visuelle Ausgabe und verarbeitet Benutzereingaben
2. **WebswingConnector-Komponente**: Eine webforJ-Komponente, die den Webswing-Client einbettet und die Verbindung sowie die Kommunikation mit dem Server verwaltet
3. **Kommunikationsprotokoll**: Ermöglicht den bidirektionalen Nachrichtenaustausch, sodass Ihre webforJ-App Befehle an die Swing-Anwendung senden und Ereignisse von ihr empfangen kann

Wenn ein Benutzer auf Ihre webforJ-App zugreift, stellt `WebswingConnector` eine Verbindung zum Webswing-Server her. Der Server erstellt eine Anwendungsinstanz oder stellt die Verbindung zu einer vorhandenen Instanz wieder her und beginnt, den visuellen Zustand an den Browser zu übertragen. Benutzerinteraktionen wie Maus- und Tastatureingaben werden erfasst und an den Server gesendet, wo sie in der eigentlichen Swing-Anwendung nachgestellt werden.

## Themen {#topics}

<DocCardList className="topics-section" />
