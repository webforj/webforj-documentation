---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 8dcd8fdee2734f6b4b243b0ea82fa1c2
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing ist eine Webserver-Technologie, die es ermöglicht, Java-Desktop-Anwendungen (Swing, JavaFX, SWT) ohne Änderungen am ursprünglichen Quellcode in einem Webbrowser auszuführen. Es rendert die Desktop-App auf dem Server und streamt die Benutzeroberfläche mithilfe von HTML5-Canvas, wobei alle Benutzerinteraktionen transparent behandelt werden.

## Was Webswing löst

Viele Organisationen haben erhebliche Investitionen in Java-Desktop-Anwendungen, die kritische Geschäftslogik enthalten, die über Jahre oder Jahrzehnte entwickelt wurde. Diese Anwendungen können oft nicht einfach neu geschrieben werden, da:

- Komplexe Domänenlogik, die riskant zu reproduzieren wäre
- Integration mit desktop-spezifischen Bibliotheken oder Hardware
- Zeit- und Kostenbeschränkungen für einen vollständigen Neuaufbau
- Bedarf, die Funktionsgleichheit mit bestehenden Funktionen aufrechtzuerhalten

Webswing ermöglicht es, diese Anwendungen webzugänglich zu machen, ohne sie zu modifizieren, und bewahrt ihre ursprüngliche Funktionalität und Erscheinung.

## Integration mit webforJ

Die Webswing-Integration in webforJ bietet die Komponente `WebswingConnector`, die es Ihnen ermöglicht, Webswing-gehostete Anwendungen direkt in Ihre webforJ-App einzubetten. Dies schafft Möglichkeiten für:

### Fortschreitende Modernisierung

Anstatt einen kompletten Neuaufbau zu machen, können Sie:

1. Beginnen Sie damit, Ihre bestehende Swing-App über `WebswingConnector` einzubetten
2. Entwickeln Sie neue Funktionen in webforJ rund um die eingebettete App
3. Ersetzen Sie schrittweise Swing-Komponenten durch webforJ-Äquivalente
4. Letztendlich die Legacy-App vollständig abbauen

### Hybride Anwendungen

Kombinieren Sie moderne Web-UIs, die mit webforJ gebaut wurden, mit spezialisierten Desktop-Funktionalitäten:

- Verwenden Sie webforJ für benutzersichtbare Schnittstellen, Dashboards und Berichte
- Nutzen Sie Swing für komplexe Visualisierungen oder spezialisierte Editoren
- Behalten Sie ein einziges integriertes App-Erlebnis bei

## Wie es funktioniert

Die Integration funktioniert über drei Schichten:

1. **Webswing-Server**: führt Ihre Java-Desktop-App aus, erfasst deren visuelle Ausgabe und verarbeitet Benutzereingaben
2. **WebswingConnector-Komponente**: eine webforJ-Komponente, die den Webswing-Client einbettet und die Verbindung sowie die Kommunikation mit dem Server verwaltet
3. **Kommunikationsprotokoll**: bidirektionale Nachrichtenübertragung, die es Ihrer webforJ-App ermöglicht, Befehle an die Swing-App zu senden und Ereignisse zurück zu empfangen

Wenn ein Benutzer Ihre webforJ-App aufruft, stellt der `WebswingConnector` eine Verbindung zum Webswing-Server her. Der Server erstellt oder stellt eine Verbindung zu einer App-Instanz her und beginnt, den visuellen Status an den Browser zu streamen. Benutzerinteraktionen (Maus, Tastatur) werden erfasst und an den Server gesendet, wo sie auf der tatsächlichen Swing-App wiedergegeben werden.

## Themen {#topics}

<DocCardList className="topics-section" />
