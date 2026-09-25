---
title: Komponenten untersuchen
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 5dd1df77df56d81dd4e54c1998289e71
---
Der Inspector zeigt den Komponentenbaum, den Ihr Java-Code erstellt hat. Ein `Composite` erscheint als die Klasse, die Sie geschrieben haben, und enthält die Kinder, die Sie ihm in der Reihenfolge gegeben haben, in der webforJ sie hält, sodass die Struktur in craftforJ der Struktur in Ihrem Quellcode entspricht.

![Der Komponentenbaum mit einer ausgewählten und hervorgehobenen Komponente in der laufenden App](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Auswahl einer Komponente {#selecting-a-component}

Um eine Komponente von der Seite auszuwählen, drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> und klicken Sie darauf. craftforJ wählt den entsprechenden Knoten im Baum aus. Das Überfahren eines Knotens im Baum funktioniert umgekehrt und hebt diese Komponente auf der Seite hervor, sodass Sie in beide Richtungen zwischen dem Bildschirm und dem Baum wechseln können.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

Um im Baum zu suchen, drücken Sie <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Ein Begriff, der in Schrägstriche gesetzt ist, wird als regulärer Ausdruck behandelt. Das Rechtsklicken auf einen Knoten öffnet die verfügbaren Aktionen dafür. Sie können seine Quelle öffnen oder es an den [assistant](/docs/craftforj/ai) übergeben.

## Eigenschaften lesen und ändern {#reading-and-changing-properties}

Das Auswählen einer Komponente füllt die Seitenleiste mit ihren Eigenschaften, gruppiert nach dem, was sie beeinflussen. Welche Eigenschaften eine Komponente bietet, hängt von der Komponente ab, und einige von ihnen sind schreibgeschützt. Eigenschaften, die sich nicht gut als einfachen Text lesen lassen, erhalten stattdessen einen Editor, der für ihren Wert geeignet ist. Eine Änderung eines Wertes tritt sofort in der laufenden App in Kraft.

:::info Live-Änderungen ändern nicht Ihre Dateien
Eine Bearbeitung einer Eigenschaft ändert die App vor Ihnen und sonst nichts. Um es in Ihre Quelle zu bekommen, ist ein separater Schritt erforderlich, den Sie absichtlich durchführen, wie in [Änderungen an der Quelle schreiben](/docs/craftforj/source-changes) beschrieben.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## Die Quelle einer Komponente anzeigen {#viewing-the-source-of-a-component}

Sie können jede Komponente bis zum Java zurückverfolgen, das sie erstellt hat. Standardmäßig wird die Quelle in craftforJ als schreibgeschützt geöffnet, positioniert an der Zeile, die die Komponente erstellt hat. Sie können craftforJ so konfigurieren, dass sie stattdessen in Ihrem Editor an der gleichen Zeile geöffnet wird. Wenn eine Komponente nicht auf eine Zeile zurückverfolgt werden kann, berichtet craftforJ darüber, anstatt einen leeren Viewer zu öffnen.

![Der Quell-Viewer positioniert an der Zeile, die die ausgewählte Komponente erstellt hat](/img/craftforj/inspector/source-viewer.png#rounded-border)
