---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: e8f61966c5b7d0745f65f23172dd114a
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing ist eine Webserver-Technologie, die es Java-Desktop-Anwendungen (Swing, JavaFX, SWT) ermöglicht, ohne Änderungen am ursprünglichen Quellcode in einem Webbrowser auszuführen. Es rendert die Desktop-Anwendung auf dem Server und streamt die Benutzeroberfläche über HTML5-Canvas zum Browser, wobei alle Benutzerinteraktionen transparent behandelt werden.

## Was Webswing löst

Viele Organisationen haben erhebliche Investitionen in Java-Desktop-Anwendungen, die kritische Geschäftslogik enthalten, die über Jahre oder Jahrzehnte entwickelt wurde. Diese Anwendungen können oft nicht einfach neu geschrieben werden, aufgrund von:

- Komplexer Domänenlogik, die riskant zu rekonstruieren wäre
- Integration mit desktopspezifischen Bibliotheken oder Hardware
- Zeit- und Kostenbeschränkungen für einen vollständigen Neuaufbau
- Der Notwendigkeit, die Funktionsfähigkeit mit der vorhandenen Funktionalität aufrechtzuerhalten

Webswing ermöglicht es, diese Anwendungen webbasiert und ohne Modifikationen zugänglich zu machen und somit ihre ursprüngliche Funktionalität und Erscheinung zu bewahren.

## Integration mit webforJ

Die Webswing-Integration von webforJ bietet die `WebswingConnector`-Komponente, die es Ihnen ermöglicht, Webswing-gehostete Anwendungen direkt in Ihre webforJ-Anwendung einzubetten. Dies schafft Möglichkeiten für:

### Fortschrittliche Modernisierung

Anstatt einen vollständigen Neuaufbau durchzuführen, können Sie:

1. Beginnen Sie damit, Ihre bestehende Swing-Anwendung über `WebswingConnector` einzubetten
2. Neue Funktionen in webforJ um die eingebettete Anwendung herum entwickeln
3. Nach und nach Swing-Komponenten durch webforJ-Äquivalente ersetzen
4. Letztendlich die Legacy-Anwendung vollständig auslaufen lassen

### Hybride Anwendungen

Kombinieren Sie moderne Web-UIs, die mit webforJ erstellt wurden, mit spezialisierten Desktop-Funktionen:

- Verwenden Sie webforJ für benutzerorientierte Schnittstellen, Dashboards und Berichte
- Nutzen Sie Swing für komplexe Visualisierungen oder spezialisierte Editoren
- Bewahren Sie ein einheitliches integriertes Anwendungserlebnis

## Wie es funktioniert

Die Integration funktioniert über drei Schichten:

1. **Webswing-Server**: führt Ihre Java-Desktop-Anwendung aus, erfasst ihre visuelle Ausgabe und verarbeitet Benutzereingaben
2. **WebswingConnector-Komponente**: eine webforJ-Komponente, die den Webswing-Client einbettet und die Verbindung und Kommunikation mit dem Server verwaltet
3. **Kommunikationsprotokoll**: bidirektionale Nachrichtenübertragung, die es Ihrer webforJ-Anwendung ermöglicht, Befehle an die Swing-Anwendung zu senden und Ereignisse zurück zu erhalten

Wenn ein Benutzer auf Ihre webforJ-Anwendung zugreift, stellt der `WebswingConnector` eine Verbindung zum Webswing-Server her. Der Server erstellt oder stellt eine Verbindung zu einer Anwendungsinstanz her und beginnt, den visuellen Zustand an den Browser zu streamen. Benutzerinteraktionen (Maus, Tastatur) werden erfasst und an den Server gesendet, wo sie auf der tatsächlichen Swing-Anwendung wiedergegeben werden.

## Themen {#topics}

<DocCardList className="topics-section" />
