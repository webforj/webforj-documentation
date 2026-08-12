---
title: craftforJ Assistant
sidebar_position: 2
sidebar_class_name: new-content
description: >-
  A coding agent inside your running webforJ app that writes Java freely,
  compiles it, and applies it with your approval.
_i18n_hash: 2c2a04b29b7b6de57e5689628cd659d0
---
Der craftforJ-Assistent ist ein Programmieragent, der innerhalb Ihrer **laufenden App** arbeitet. Er schreibt Java frei, kompiliert, was er geschrieben hat, bevor Sie es jemals sehen, wendet die Änderungen an und arbeitet weiter, nachdem Ihre App neu gestartet wurde. Er wird mit webforJ als Teil von [craftforJ](/docs/craftforj) geliefert, der Entwicklungsumgebung, die Ihnen den Komponentenbaum, Routen, Live-Eigenschaften und das Design einer App während des Betriebs bereitstellt.

## Wie die beiden sich vergleichen {#how-the-two-compare}

| | [webforJ AI-Plugin](/docs/ai-tooling) | craftforJ-Assistent |
|---|---|---|
| **Lebt in** | Ihrem Editor | Der laufenden App |
| **Liest** | Ihre Quell-dateien | Ihre App, live, mit ihren realen Werten |
| **Tut** | Schreibt Code | Schreibt Code und überprüft, ändert, navigiert und gestaltet die laufende App |
| **Überprüft durch** | Ihren nächsten Build | Kompiliert jede Änderung, bevor Sie sie sehen, und zeigt Ihnen dann das Ergebnis in der Ausführung |
| **Geeignet für** | Etwas Neues von Grund auf neu zu erstellen | Verstehen, Reparieren, Bauen und Prototyping der App vor Ihnen |

Die beiden sind komplementär und können einander Arbeiten übergeben. Sobald die Arbeit craftforJ übersteigt, können Sie eine [craftforJ-Konversation übergeben](/docs/craftforj/ai#conversations) an Ihren Editor.

## Was es tun kann {#what-it-can-do}

Sie geben dem Agenten ein Ziel anstatt einen Befehl. Er plant, untersucht, was er benötigt, handelt, überprüft das Ergebnis und korrigiert sich selbst über viele Schritte in einem einzigen Zug.

Er schreibt Java frei, sodass er nicht auf die Eigenschaftenänderungen beschränkt ist, die Sie von Hand vornehmen können. Jede Änderung wird gestaffelt, statt auf die Festplatte geschrieben, zu einem echten Java-Compiler gesendet und vom Agenten anhand der Rückmeldungen korrigiert, sodass das, was Sie überprüfen, bereits gegen Ihre laufende App kompiliert. Die Anwendung startet die App neu, und der Agent setzt seinen Plan fort, sobald sie zurück ist.

Darüber hinaus hat er Zugriff auf alles, was craftforJ weiß: den Live-Komponentenbaum und die echten Eigenschaftswerte, Ihre Java-Quell-datei, die Routentabelle und die Zugriffsregeln für Routen, das Design und das Stylesheet, die Seite selbst für CSS und Skripte, Screenshots eines Komponentens und die webforJ-Wissensdatenbank sowie die integrierten `--dwc-*` Token-Tools. Siehe [AI-Assistent](/docs/craftforj/ai) für die Details.

## Konfigurieren eines Modells {#configuring-a-model}

craftforJ liefert kein eigenes Modell, sodass Sie das auswählen, das es betreibt. Fügen Sie einen API-Schlüssel für einen der unterstützten Anbieter hinzu oder verweisen Sie craftforJ auf ein lokal laufendes Modell mit Ollama. Ihr Schlüssel wird auf dem Computer gespeichert, der Ihre App ausführt, und nur im Browser gehalten, solange die Seite geöffnet ist, und der Assistent kommuniziert mit Ihrem Anbieter über den Browser, anstatt über Ihren Server. Siehe [Konfigurieren eines Modells](/docs/craftforj/ai#configuring-a-model).

:::warning KI kann immer noch Fehler machen
Die Arbeit mit der laufenden App und das Kompilieren ihrer eigenen Ausgaben machen den Agenten erheblich genauer als einen, der im Blindflug schreibt. Er kann immer noch falsch sein. Überprüfen Sie, was er getan hat, bevor Sie es speichern.
:::

## Erste Schritte {#getting-started}

craftforJ ist deaktiviert, bis Sie es aktivieren, und läuft nur in der Entwicklung:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Öffnen Sie craftforJ mit <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> und wechseln Sie zum Tab AI-Assistent. Für die vollständige Einrichtung siehe [Erste Schritte](/docs/craftforj/getting-started).
