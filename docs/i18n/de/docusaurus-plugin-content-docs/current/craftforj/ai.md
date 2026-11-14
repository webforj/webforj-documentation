---
title: KI-Assistent
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: e9d943043e52eae5368dfe4ba7795142
---
craftforJ enthält einen vollständigen Coding-Agenten, der in deiner **laufenden App** arbeitet. Er schreibt Java, kompiliert den geschriebenen Code, bevor du ihn überhaupt siehst, wendet die Änderung an und arbeitet nach dem Neustart deiner App weiter. Alles, was er tut, tut er anhand der App, die tatsächlich vor dir läuft, statt anhand einer Vermutung auf Basis deines Repositorys.

<VideoPlayer video="craftforj/ai-conversation" width={1920} height={1000} title="AI conversation" />

:::warning KI kann trotzdem Fehler machen
Da der Agent mit der laufenden App arbeitet und seine eigenen Ergebnisse kompiliert, ist er deutlich genauer als ein Agent, der ohne Kontext arbeitet. Trotzdem kann er Fehler machen. Prüfe seine Änderungen, bevor du sie übernimmst.
:::

## Er schreibt Java {#it-writes-java}

Der Agent ist nicht auf die Eigenschaftsänderungen beschränkt, die du von Hand vornehmen kannst. Beschreibe ein Problem, und er schreibt den dafür nötigen Code, fügt Methoden hinzu, ändert die Logik und strukturiert eine Ansicht je nach Aufgabe um.

Alle von ihm geschriebenen Änderungen werden vorgemerkt, statt direkt auf die Festplatte geschrieben zu werden. Vorgemerkte Änderungen werden an einen echten Java-Compiler übergeben. Der Agent liest die zurückgegebenen Diagnosen und behebt seine eigenen Fehler, bevor dir die Änderung überhaupt angeboten wird. Der Code, den du prüfst, lässt sich bereits mit deiner laufenden App kompilieren.

Für eine vollständige Validierung wird ein JDK benötigt. Mit einem JRE wechselt craftforJ zur Codeanalyse, kennzeichnet die Änderung als nicht verifiziert und weist den Agenten an, dies anzugeben, statt sie als geprüft darzustellen.

Beim Anwenden einer Änderung wird deine App neu gestartet. Der Agent wartet auf den Neustart, verbindet sich erneut und setzt seinen Plan dort fort, wo er ihn unterbrochen hat. So wird auch eine Aufgabe, die mehrere Änderungen und Neustarts umfasst, abgeschlossen.

## Er arbeitet in Schritten {#it-works-in-steps}

Du gibst dem Agenten ein Ziel vor, keinen Befehl. Er plant, prüft, was er benötigt, handelt, kontrolliert das Ergebnis und korrigiert sich selbst. In einem einzigen Durchlauf führt er viele Schritte aus, ohne dass du jeden einzelnen anstoßen musst. Jeder Schritt wird während der Ausführung im Transkript angezeigt. Du kannst jeden davon aufklappen, um genau zu sehen, was der Agent aufgerufen hat und welche Antwort zurückkam.

## Worauf er zugreifen kann {#what-it-can-reach}

Der Agent verfügt über eine umfangreiche Werkzeugsammlung, die alles abdeckt, was craftforJ über deine App weiß, darunter:

- **Deine Komponenten** – den Live-Baum, die tatsächlichen Eigenschaftswerte und den Java-Code, mit dem die einzelnen Komponenten erstellt wurden. Er kann Eigenschaften ändern, Komponenten entfernen und eine Komponente auf der Seite hervorheben.
- **Dein Quellcode** – beliebige Dateien im Projektverzeichnis lesen, Änderungen vormerken, Diffs anzeigen und Änderungen anwenden.
- **Deine Routen** – die Routing-Tabelle, die aktive Route, die Navigation zu beliebigen Zielen und das Ändern der für eine Route festgelegten Zugriffsregeln.
- **Dein Theme und deine Stile** – Design-Tokens lesen und festlegen, ein Theme speichern und die verfügbaren Schriftarten und Symbole durchsuchen.
- **Die Seite selbst** – CSS und JavaScript auf der Live-Seite einfügen und einen Screenshot einer Komponente aufnehmen.
- **Die webforJ-Wissensdatenbank** – dieselbe Dokumentation, dieselbe Oberfläche für die Komponentenstilgestaltung und dieselben `--dwc-*`-Token-Werkzeuge, die der [webforJ-MCP-Server](/docs/ai-tooling/mcp) deinem Editor zur Verfügung stellt. Sie ist integriert und jederzeit verfügbar.

Da der Agent über craftforJ auf all diese Bereiche zugreift, arbeitet er mit denselben Informationen wie du. Er liest die tatsächlichen Werte und nicht die Werte, die dein Quellcode vermuten lässt.

## Freigaben {#approvals}

Du legst im Voraus fest, wie viel der Agent selbstständig erledigen darf:

- **Vor jeder Aktion fragen** – jede Aktion mit Auswirkungen wird angehalten, bis du sie freigibst.
- **Änderungen automatisch anwenden** – der Agent arbeitet frei, fragt aber weiterhin nach, bevor er etwas entfernt oder ein Skript ausführt.
- **Autonom arbeiten** – der Agent arbeitet ohne Unterbrechungen.

Wenn der Agent nachfragt, erscheint die Anfrage zusammen mit der geplanten Aktion direkt im Transkript. Du kannst sie einmalig oder für den Rest der Unterhaltung freigeben.

![Der Assistent fragt direkt im Transkript um Erlaubnis, bevor er handelt](/img/craftforj/ai/approval-prompt.png#rounded-border)

Wenn du den Agenten noch nicht kennst, solltest du ihn zunächst bei jeder Aktion nachfragen lassen. Sobald du gesehen hast, wie er arbeitet, kannst du ihm erlauben, Änderungen selbst anzuwenden. Dadurch werden die meisten Unterbrechungen vermieden, während die wichtigen Entscheidungen bei dir bleiben.

## In einer Unterhaltung mit der App arbeiten {#working-with-the-app-in-a-conversation}

Der Agent liest Informationen bei Bedarf, statt zu Beginn deine gesamte App zu erhalten. craftforJ zeigt dir, welche Informationen an die Unterhaltung angehängt sind. Du kannst ihm direkt eine Komponente aus dem Baum übergeben oder während einer Unterhaltung eine Komponente auf der Seite auswählen. Bei Fragen zum Aussehen kann der Agent einen Screenshot einer Komponente aufnehmen. Dafür ist ein Modell erforderlich, das Bilder verarbeiten kann.

:::warning Screenshots enthalten alles, was auf dem Bildschirm zu sehen ist
Ein Screenshot enthält alle Daten, die deine App zu diesem Zeitpunkt anzeigt. Bedenke das, bevor du ein gehostetes Modell auf eine App zugreifen lässt, die mit echten Daten läuft.
:::

## Ein Modell konfigurieren {#configuring-a-model}

craftforJ enthält kein eigenes Modell. Du wählst daher das Modell aus, das den Agenten antreibt. Füge einen API-Schlüssel für einen der unterstützten Anbieter hinzu oder verbinde craftforJ mit einem lokal laufenden Modell. Dein Schlüssel wird auf dem Rechner gespeichert, auf dem deine App läuft. Der Assistent hält ihn nur so lange im Arbeitsspeicher, wie die Seite geöffnet ist, und speichert ihn niemals im Browserspeicher. Er kommuniziert direkt aus dem Browser mit dem ausgewählten Anbieter und mit niemand anderem.

Die Modellauswahl zeigt die Unterschiede zwischen den Modellen: wie viel von deiner App und Unterhaltung jeweils gleichzeitig in den Kontext passt, welche Kosten für eine Unterhaltung anfallen und ob das Modell Bilder verarbeiten oder vor einer Antwort Schlussfolgerungen ziehen kann. Ein Modell, das keine Tools aufrufen kann, kann zwar an einer Unterhaltung teilnehmen, aber nichts untersuchen oder ändern.

![Die Modellauswahl zeigt die Unterschiede zwischen den verfügbaren Modellen](/img/craftforj/ai/model-picker.png#rounded-border)

Wenn du ein Modell lokal ausführst, bleiben alle Daten auf deinem Rechner. Lokale Modelle verwenden oft standardmäßig ein kleines Kontextfenster. Eine Unterhaltung über eine echte App füllt es daher schnell. Gib dem Modell so viel Kontext, wie dein Rechner verarbeiten kann.

## Unterhaltungen {#conversations}

Unterhaltungen werden pro App gespeichert. Wenn sich eine Frage auf frühere Arbeiten bezieht, kann der Agent frühere Unterhaltungen heranziehen. Wenn eine Unterhaltung den Kontext des Modells übersteigt, fasst craftforJ die älteren Nachrichten zusammen, damit die Arbeit fortgesetzt werden kann, statt abzubrechen. Im Chat wird vermerkt, dass dies geschehen ist.

Wenn die Arbeit den Rahmen von craftforJ übersteigt, kannst du die Unterhaltung zusammenfassen und an den Assistenten deines Editors übergeben. Mit installiertem [webforJ-KI-Plugin](/docs/ai-tooling) kann dieser Assistent die Arbeit mit größerer Genauigkeit fortsetzen.

## Deaktivieren {#turning-it-off}

Die Eigenschaft [`ai.enabled`](/docs/craftforj/configuration#feature-flags) entfernt den Assistenten vollständig aus craftforJ. Mit der Eigenschaft [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) bleibt der Assistent verfügbar, kann aber keinen eigenen Java-Code mehr schreiben.
