---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 853a4bb057c1a3499c26d4714120170f
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing ist eine Webserver-Technologie, die es ermöglicht, Java-Desktop-Anwendungen (Swing, JavaFX, SWT) in einem Webbrowser auszuführen, ohne dass Änderungen am ursprünglichen Quellcode erforderlich sind. Es rendert die Desktop-App auf dem Server und streamt die Benutzeroberfläche über das HTML5-Canvas, wobei alle Benutzerinteraktionen transparent gehandhabt werden.

## Was Webswing löst {#what-webswing-solves}

Viele Organisationen haben umfangreiche Investitionen in Java-Desktop-Anwendungen, die kritische Geschäftslogik enthalten, die über Jahre oder Jahrzehnte entwickelt wurde. Diese Anwendungen können oft nicht einfach neu geschrieben werden aufgrund von:

- Komplexer Fachlogik, deren Neuerstellung riskant wäre
- Integration mit desktop-spezifischen Bibliotheken oder Hardware
- Zeit- und Kosteneinschränkungen eines vollständigen Neuschreibens
- Notwendigkeit, die Funktionsparität mit bestehenden Funktionen aufrechtzuerhalten

Webswing ermöglicht es, diese Anwendungen webzugänglich zu machen, ohne Modifikationen, und bewahrt deren ursprüngliche Funktionalität und Erscheinung.

## Integration mit webforJ {#integration-with-webforj}

Die Webswing-Integration von webforJ bietet die Komponente `WebswingConnector`, die es ermöglicht, Webswing-gehostete Anwendungen direkt innerhalb Ihrer webforJ-App einzubetten. Dies schafft Möglichkeiten für:

### Progressive Modernisierung {#progressive-modernization}

Anstatt einen vollständigen Neuschreibungsansatz zu wählen, können Sie:

1. Beginnen Sie mit der Einbettung Ihrer bestehenden Swing-App über `WebswingConnector`
2. Neue Funktionen in webforJ rund um die eingebettete App entwickeln
3. Allmählich Swing-Komponenten durch webforJ-Äquivalente ersetzen
4. Schließlich die Legacy-App vollständig ausmustern

### Hybride Anwendungen {#hybrid-applications}

Kombinieren Sie moderne Web-Benutzeroberflächen, die mit webforJ erstellt wurden, mit spezialisierter Desktop-Funktionalität:

- Verwenden Sie webforJ für benutzerorientierte Schnittstellen, Dashboards und Berichte
- Nutzen Sie Swing für komplexe Visualisierungen oder spezialisierte Editoren
- Erhalten Sie ein einziges integriertes App-Erlebnis

## Wie es funktioniert {#how-it-works}

Die Integration funktioniert durch drei Schichten:

1. **Webswing-Server**: führt Ihre Java-Desktop-App aus, erfasst deren visuelle Ausgabe und verarbeitet Benutzereingaben
2. **WebswingConnector-Komponente**: eine webforJ-Komponente, die den Webswing-Client einbettet und die Verbindung und Kommunikation mit dem Server verwaltet
3. **Kommunikationsprotokoll**: bidirektionale Nachrichtenübertragung, die es Ihrer webforJ-App ermöglicht, Befehle an die Swing-App zu senden und Ereignisse zurück zu empfangen

Wenn ein Benutzer auf Ihre webforJ-App zugreift, stellt der `WebswingConnector` eine Verbindung zum Webswing-Server her. Der Server erstellt oder stellt eine Verbindung zu einer Anwendungsinstanz her und beginnt, den visuellen Status an den Browser zu streamen. Benutzerinteraktionen (Maus, Tastatur) werden erfasst und an den Server gesendet, wo sie auf der tatsächlichen Swing-App wiedergegeben werden.

## Themen {#topics}

<DocCardList className="topics-section" />
