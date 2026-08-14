---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 863d36cce987eedd9b580968afadcc18
---
craftforJ enthält einen vollständigen Codierungsagenten, der innerhalb Ihrer **laufenden App** arbeitet. Er schreibt Java frei, compiliert, was er geschrieben hat, bevor Sie es je sehen, wendet die Änderung an und arbeitet weiter, nachdem Ihre App neu gestartet wurde. Alles, was er tut, geschieht gegen die App, die tatsächlich vor Ihnen läuft, und nicht gegen eine Vermutung aus Ihrem Repository.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4" type="video/mp4" />
  </video>
</div>

:::warning KI kann immer noch Fehler machen
Gegen die laufende App zu arbeiten und die eigene Ausgabe zu kompilieren macht den Agenten erheblich genauer als einen, der blind schreibt. Er kann dennoch falsch sein. Überprüfen Sie, was er getan hat, bevor Sie es übernehmen.
:::

## Er schreibt Java {#it-writes-java}

Der Agent ist nicht auf die Eigenschaftsänderungen beschränkt, die Sie per Hand vornehmen können. Beschreiben Sie ein Problem und er schreibt den Code dafür, fügt Methoden hinzu, ändert die Logik und strukturiert eine Ansicht um, je nach Bedarf der Aufgabe.

Jede Änderung, die er schreibt, wird gestaged, anstatt auf die Festplatte geschrieben zu werden. Gestagte Änderungen gehen direkt an einen echten Java-Compiler, und der Agent liest die zurückkommenden Diagnosen und korrigiert seine eigenen Fehler, bevor die Änderung Ihnen überhaupt angeboten wird. Was Ihre Überprüfung erreicht, ist Code, der bereits gegen Ihre laufende App kompiliert.

Vollständige Validierung benötigt ein JDK. Auf einem JRE greift craftforJ auf die Codeanalyse zurück, kennzeichnet die Änderung als unverified und instruierte den Agenten, dies ebenfalls zu sagen, anstatt sie als überprüft darzustellen.

Eine Änderung anzuwenden, startet Ihre App neu. Der Agent wartet auf den Neustart, verbindet sich erneut und setzt seinen Plan dort fort, wo er aufgehört hat, sodass eine Aufgabe, die sich über mehrere Änderungen und Neustarts erstreckt, bis zu ihrem Abschluss ausgeführt wird.

## Er arbeitet in Schritten {#it-works-in-steps}

Sie geben dem Agenten ein Ziel, keinen Befehl. Er plant, inspiziert, was er benötigt, handelt, überprüft das Ergebnis und korrigiert sich selbst, indem er viele Schritte in einem einzigen Durchgang ausführt, ohne dass Sie jeden einzelnen steuern. Jeder Schritt erscheint im Transkript, während es passiert, und Sie können jeden von ihnen erweitern, um genau zu sehen, was der Agent aufgerufen hat und was zurückkam.

## Was er erreichen kann {#what-it-can-reach}

Der Agent hat ein großes Werkzeugset, das alles abdeckt, was craftforJ über Ihre App weiß, einschließlich:

- **Ihre Komponenten** - der live Baum, die realen Eigenschaftswerte und das Java, das jede von ihnen erstellt hat. Er kann Eigenschaften ändern, Komponenten entfernen und eine in der Seite hervorheben.
- **Ihre Quelle** - Lesen von Dateien unter Ihrem Projektstamm, Staging von Änderungen, Anzeigen von Unterschieden und Anwenden dieser.
- **Ihre Routen** - die Routingtabelle, die aktive Route, Navigation überall und Ändern der Zugangsdaten, die für eine Route deklariert sind.
- **Ihr Thema und Stile** - Designtokens lesen und setzen, ein Thema speichern und nach verfügbaren Schriftarten und Icons suchen.
- **Die Seite selbst** - CSS und JavaScript gegen die live Seite einspeisen und einen Screenshot einer Komponente zur Ansicht erstellen.
- **Die webforJ Wissensdatenbank** - die gleiche Dokumentation, die Komponentengestaltung und `--dwc-*` Token-Tools, die der [webforJ MCP-Server](/docs/ai-tooling/mcp) Ihrem Editor bietet. Es ist integriert und immer verfügbar.

Da es all dies über craftforJ erreicht, arbeitet es mit denselben Informationen, die Sie verwenden. Es liest reale Werte, nicht die, die Ihre Quelle impliziert.

## Genehmigungen {#approvals}

Sie entscheiden im Voraus, wie viel der Agent eigenständig tun darf:

- **Fragen, bevor er handelt** - jede Aktion mit Effekt hält um Ihre Genehmigung an.
- **Änderungen automatisch anwenden** - der Agent arbeitet frei, fragt jedoch immer, bevor er etwas entfernt oder ein Skript ausführt.
- **Autonom arbeiten** - der Agent arbeitet ohne Unterbrechung.

Wenn der Agent fragt, erscheint die Anfrage inline im Transkript mit der Aktion, die er durchführen möchte, und Sie können ihm erlauben, dies einmal oder für den Rest der Konversation zu tun.

![Der Assistent fragt, bevor er handelt, inline im Transkript](/img/craftforj/ai/approval-prompt.png#rounded-border)

Wenn Sie neu bei dem Agenten sind, beginnen Sie damit, ihn um alles zu fragen. Sobald Sie gesehen haben, wie er arbeitet, wird es die meisten Unterbrechungen beseitigen, ihm zu erlauben, seine eigenen Änderungen anzuwenden, während die Entscheidungen, die wichtig sind, bei Ihnen bleiben.

## Arbeiten mit der App in einer Konversation {#working-with-the-app-in-a-conversation}

Der Agent liest, was er benötigt, während er es benötigt, anstatt Ihnen Ihre gesamte App im Voraus zu übergeben, und craftforJ zeigt Ihnen, was an die Konversation angehängt ist. Sie können ihm eine Komponente direkt vom Baum übergeben oder eine von der Seite in der Mitte einer Konversation auswählen. Bei Fragen dazu, wie etwas aussieht, kann der Agent einen Screenshot einer Komponente machen. Dies erfordert ein Modell, das Bilder akzeptiert.

:::warning Screenshots enthalten, was immer auf dem Bildschirm ist
Ein Screenshot enthält alle Daten, die Ihre App in diesem Moment anzeigt. Berücksichtigen Sie dies, bevor Sie ein gehostetes Modell an eine App weiterleiten, die gegen echte Daten arbeitet.
:::

## Konfigurieren eines Modells {#configuring-a-model}

craftforJ liefert kein eigenes Modell aus, sodass Sie dasjenige auswählen, das es ausführt. Fügen Sie einen API-Schlüssel für einen der unterstützten Anbieter hinzu oder weisen Sie craftforJ an, auf ein lokal laufendes Modell zuzugreifen. Ihr Schlüssel wird auf dem Gerät gespeichert, das Ihre App ausführt, und der Assistent hält ihn nur solange im Speicher, wie die Seite geöffnet ist, niemals im Browser-Speicher. Er spricht über den Anbieter, den Sie aus dem Browser ausgewählt haben, und mit niemand anderem.

Der Modellauswähler zeigt, was ein Modell von einem anderen unterscheidet, einschließlich wie viel von Ihrer App und Konversation gleichzeitig passt, was eine Konversation kostet und ob das Modell Bilder akzeptiert oder vor dem Antworten Gründe anführt. Ein Modell, das keine Tools aufrufen kann, kann eine Konversation führen, aber nichts inspizieren oder ändern.

![Der Modellauswähler zeigt, was die verfügbaren Modelle unterscheidet](/img/craftforj/ai/model-picker.png#rounded-border)

Ein lokal laufendes Modell hält alles auf Ihrem Gerät. Lokale Modelle haben oft standardmäßig ein kleines Kontextfenster, das eine Konversation über eine echte App schnell füllt. Geben Sie dem Modell so viel Kontext, wie Ihr Gerät tragen kann.

## Gespräche {#conversations}

Gespräche werden pro App geführt, und der Agent kann auf frühere zurückblicken, wenn eine Frage auf die Arbeit verweist, die Sie vorher ausgeführt haben. Wenn ein Gespräch die Kontextgrenzen des Modells überschreitet, fasst craftforJ die älteren Nachrichten zusammen, damit die Arbeit fortgesetzt und nicht fehlschlägt, und weist im Chat darauf hin, dass es dies getan hat.

Wenn die Arbeit craftforJ übersteigt, können Sie das Gespräch zusammenfassen und es dem Assistenten Ihres Editors übergeben. Dieser Assistent nimmt die Arbeit mit dem [webforJ AI Plugin](/docs/ai-tooling) installiert genauer auf.

## Deaktivieren {#turning-it-off}

Die [`ai.enabled`](/docs/craftforj/configuration#feature-flags) Eigenschaft entfernt den Assistenten vollständig aus craftforJ. Die [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) Eigenschaft behält den Assistenten bei, hindert ihn jedoch daran, eigenständig Java zu schreiben.
