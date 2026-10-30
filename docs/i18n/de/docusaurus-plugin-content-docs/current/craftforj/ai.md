---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 716e267ebccf57080f5238335670d1b7
---
craftforJ umfasst einen vollständigen Codierungsagenten, der innerhalb Ihrer **laufenden App** arbeitet. Er schreibt Java frei, kompiliert das, was er geschrieben hat, bevor Sie es jemals sehen, wendet die Änderungen an und arbeitet weiter, nachdem Ihre App neu gestartet wurde. Alles, was er tut, geschieht gegen die App, die tatsächlich vor Ihnen läuft, und nicht gegen einen Schätzwert aus Ihrem Repository.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning KI kann immer noch Fehler machen
Arbeiten gegen die laufende App und das Kompilieren eigener Ausgaben macht den Agenten erheblich genauer als einen, der blind schreibt. Er kann jedoch immer noch falsch sein. Überprüfen Sie, was er getan hat, bevor Sie es beibehalten.
:::

## Er schreibt Java {#it-writes-java}

Der Agent ist nicht auf die Eigenschaftenänderungen beschränkt, die Sie von Hand vornehmen können. Beschreiben Sie ein Problem, und er schreibt den Code dafür, fügt Methoden hinzu, ändert die Logik und restrukturiert eine Ansicht, wie es die Aufgabe erfordert.

Jede Änderung, die er schreibt, wird gestaged, anstatt auf die Festplatte geschrieben zu werden. Gestaged Änderungen gehen direkt an einen echten Java-Compiler, und der Agent liest die zurückkommenden Diagnosen und korrigiert seine eigenen Fehler, bevor die Änderung jemals Ihnen angeboten wird. Was zur Überprüfung erreicht, ist Code, der bereits gegen Ihre laufende App kompiliert.

Für eine vollständige Validierung wird ein JDK benötigt. Auf einem JRE greift craftforJ auf das Parsen des Codes zurück, markiert die Änderung als nicht verifiziert und weist den Agenten an, dies zu sagen, anstatt es als geprüft zu präsentieren.

Das Anwenden einer Änderung startet Ihre App neu. Der Agent wartet auf den Neustart, stellt die Verbindung wieder her und nimmt seinen Plan dort wieder auf, wo er aufgehört hat, sodass eine Aufgabe, die mehrere Änderungen und Neustarts umfasst, zu einem Abschluss kommt.

## Er arbeitet in Schritten {#it-works-in-steps}

Sie geben dem Agenten ein Ziel, keinen Befehl. Er plant, inspiziert alles Notwendige, handelt, prüft das Ergebnis und korrigiert sich selbst und führt viele Schritte in einem einzigen Durchgang durch, ohne dass Sie jeden einzelnen steuern müssen. Jeder Schritt erscheint im Transkript, während er geschieht, und Sie können jeden von ihnen erweitern, um genau zu sehen, was der Agent aufgerufen hat und was zurück kam.

## Was er erreichen kann {#what-it-can-reach}

Der Agent hat ein umfangreiches Werkzeugset, das alles abdeckt, was craftforJ über Ihre App weiß, einschließlich:

- **Ihre Komponenten** - den Live-Baum, die tatsächlichen Eigenschaftswerte und das Java, das jede einzelne aufgebaut hat. Er kann Eigenschaften ändern, Komponenten entfernen und eine auf der Seite hervorheben.
- **Ihre Quelle** - Lesen von Dateien unter Ihrem Projektstamm, Staging von Änderungen, Anzeigen von Diffs und deren Anwendung.
- **Ihre Routen** - die Routing-Tabelle, die aktive Route, Navigation überall und Änderung der deklarierten Zugriffsregeln auf einer Route.
- **Ihr Thema und Stil** - Lesen und Festlegen von Design-Tokens, Speichern eines Themas und Suchen nach den verfügbaren Schriften und Symbolen.
- **Die Seite selbst** - Einspeisen von CSS und JavaScript auf die live Seite und Erstellen eines Screenshots einer Komponente zur Ansicht.
- **Die webforJ Wissensdatenbank** - die gleiche Dokumentation, Komponenten-Stiloberfläche und `--dwc-*` Token-Werkzeuge, die der [webforJ MCP-Server](/docs/ai-tooling/mcp) Ihrem Editor zur Verfügung stellt. Es ist eingebaut und immer verfügbar.

Da es dies alles über craftforJ erreicht, arbeitet es mit denselben Informationen, die Sie auch nutzen. Es liest echte Werte, nicht die, die Ihre Quelle impliziert.

## Genehmigungen {#approvals}

Sie entscheiden im Voraus, wie viel der Agent selbstständig tun darf:

- **Vor der Handlung fragen** - jede Aktion mit einer Auswirkung stoppt zur Genehmigung.
- **Änderungen automatisch anwenden** - der Agent arbeitet frei, fragt jedoch immer noch, bevor er etwas entfernt oder ein Skript ausführt.
- **Autonom arbeiten** - der Agent arbeitet ohne Unterbrechung.

Wenn der Agent fragt, erscheint die Anfrage inline im Transkript mit der Aktion, die er durchführen möchte, und Sie können ihm dies einmal oder für den Rest des Gesprächs erlauben.

![Der Assistent fragt, bevor er handelt, inline im Transkript](/img/craftforj/ai/approval-prompt.png#rounded-border)

Wenn Sie neu beim Agenten sind, beginnen Sie damit, ihn um alles zu fragen. Sobald Sie gesehen haben, wie er arbeitet, entfernt das Zulassen, dass er seine eigenen Änderungen anwendet, die meisten Unterbrechungen, während die wichtigen Entscheidungen bei Ihnen bleiben.

## Arbeiten mit der App in einem Gespräch {#working-with-the-app-in-a-conversation}

Der Agent liest, was er benötigt, während er es benötigt, anstatt Ihnen Ihre gesamte App im Voraus zu übergeben, und craftforJ zeigt Ihnen, was mit dem Gespräch verbunden ist. Sie können ihm direkt eine Komponente aus dem Baum übergeben oder eine von der Seite mitten in einem Gespräch auswählen. Für Fragen dazu, wie etwas aussieht, kann der Agent einen Screenshot einer Komponente machen. Dies erfordert ein Modell, das Bilder akzeptiert.

:::warning Screenshots umfassen alles, was auf dem Bildschirm zu sehen ist
Ein Screenshot trägt alle Daten, die Ihre App in diesem Moment anzeigt. Berücksichtigen Sie das, bevor Sie ein gehostetes Modell an eine App richten, die mit echten Daten arbeitet.
:::

## Konfigurieren eines Modells {#configuring-a-model}

craftforJ liefert kein eigenes Modell mit, sodass Sie das auswählen, das es ausführt. Fügen Sie einen API-Schlüssel für einen der unterstützten Anbieter hinzu oder richten Sie craftforJ gegen ein lokal ausgeführtes Modell ein. Ihr Schlüssel wird auf dem Gerät gespeichert, auf dem Ihre App läuft, und der Assistent hält ihn nur solange im Speicher, wie die Seite geöffnet ist, niemals im Browser-Speicher. Es kommuniziert mit dem Anbieter, den Sie im Browser gewählt haben, und mit niemand anderem.

Der Modellwähler zeigt, was ein Modell von einem anderen unterscheidet, einschließlich wie viel Ihrer App und des Gesprächs auf einmal passt, was ein Gespräch kostet und ob das Modell Bilder akzeptiert oder vor dem Antworten Gründe angibt. Ein Modell, das keine Werkzeuge aufrufen kann, kann zwar ein Gespräch führen, aber nichts inspizieren oder ändern.

![Der Modellwähler zeigt, was die verfügbaren Modelle unterscheidet](/img/craftforj/ai/model-picker.png#rounded-border)

Ein lokal ausgeführtes Modell hält alles auf Ihrem Gerät. Lokale Modelle haben oft ein kleines Kontextfenster als Standard, das durch ein Gespräch über eine reale App schnell gefüllt wird, also geben Sie dem Modell so viel Kontext, wie Ihr Gerät tragen kann.

## Gespräche {#conversations}

Gespräche werden pro App gespeichert, und der Agent kann auf frühere zurückblicken, wenn eine Frage auf Arbeiten verweist, die Sie zuvor gemacht haben. Wenn ein Gespräch das Kontextlimit des Modells überschreitet, fasst craftforJ die älteren Nachrichten zusammen, sodass die Arbeit fortgesetzt werden kann, anstatt zu scheitern, und vermerkt im Chat, dass es dies getan hat.

Wenn die Arbeit das Maß von craftforJ überschreitet, können Sie das Gespräch zusammenfassen und es dem Assistenten Ihres Editors übergeben. Dieser Assistent nimmt die Arbeit genauer mit dem [webforJ AI-Plugin](/docs/ai-tooling) auf, das installiert ist.

## Deaktivieren {#turning-it-off}

Die [`ai.enabled`](/docs/craftforj/configuration#feature-flags) Eigenschaft entfernt den Assistenten vollständig aus craftforJ. Die [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) Eigenschaft erhält den Assistenten, stoppt jedoch seine eigenen Java-Schreibvorgänge.
