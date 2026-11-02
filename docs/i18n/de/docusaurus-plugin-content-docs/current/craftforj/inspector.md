---
title: Inspektion von Komponenten
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 93bf0d91de1f942373082ccebd0bab6d
---
Der Inspector zeigt den Komponentenbaum, den Ihr Java-Code erstellt hat. Ein `Composite` erscheint als die Klasse, die Sie geschrieben haben, und hält die Kinder, die Sie ihm in der Reihenfolge gegeben haben, wie webforJ sie hält. So entspricht die Struktur in craftforJ der Struktur in Ihrem Quellcode.

![Der Komponentenbaum mit einer ausgewählten und hervorgehobenen Komponente in der laufenden App](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Auswählen einer Komponente {#selecting-a-component}

Um eine Komponente von der Seite auszuwählen, drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> und klicken Sie darauf. craftforJ wählt den entsprechenden Knoten im Baum aus. Wenn Sie mit der Maus über einen Knoten im Baum fahren, wird die dazugehörige Komponente auf der Seite hervorgehoben, sodass Sie in beide Richtungen zwischen dem Bildschirm und dem Baum wechseln können.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Um im Baum zu suchen, drücken Sie <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Ein Begriff, der in Schrägstriche eingeschlossen ist, wird als regulärer Ausdruck behandelt. Ein Rechtsklick auf einen Knoten öffnet die verfügbaren Aktionen dafür. Sie können seine Quelle öffnen oder sie dem [Assistenten](/docs/craftforj/ai) übergeben.

## Eigenschaften lesen und ändern {#reading-and-changing-properties}

Das Auswählen einer Komponente füllt die Seitenleiste mit ihren Eigenschaften, gruppiert nach dem, was sie beeinflussen. Welche Eigenschaften eine Komponente bietet, hängt von der Komponente ab, und einige von ihnen sind nur lesbar. Eigenschaften, die sich nicht gut als Klartext lesen lassen, erhalten stattdessen einen geeigneten Editor für ihren Wert. Eine Änderung eines Wertes hat sofortige Auswirkungen in der laufenden App.

:::info Live-Änderungen ändern Ihre Dateien nicht
Eine Eigenschaftsänderung verändert die App vor Ihnen und sonst nichts. Um es in Ihren Quellcode zu übernehmen, ist ein separater Schritt erforderlich, den Sie absichtlich durchführen, wie in [Änderungen im Quellcode schreiben](/docs/craftforj/source-changes) beschrieben.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Den Quellcode einer Komponente anzeigen {#viewing-the-source-of-a-component}

Sie können jede Komponente bis zum Java-Code zurückverfolgen, der sie erstellt hat. Standardmäßig wird die Quelle in craftforJ als schreibgeschützt geöffnet, positioniert auf der Zeile, die die Komponente erstellt hat. Sie können craftforJ so konfigurieren, dass sie stattdessen in Ihrem Editor an der gleichen Zeile geöffnet wird. Wenn eine Komponente nicht auf eine Zeile zurückverfolgt werden kann, meldet craftforJ dies anstelle eines leeren Viewers zu öffnen.

![Der Quellcode-Viewer, der an der Zeile positioniert ist, die die ausgewählte Komponente erstellt hat](/img/craftforj/inspector/source-viewer.png#rounded-border)
