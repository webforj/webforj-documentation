---
title: Änderungen anwenden
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: de9ef40848946a35567c308c132dce0d
---
Eine Eigenschaft in craftforJ zu ändern, wirkt sich auf die laufende App aus und sonst auf nichts. Damit eine Änderung erhalten bleibt, überprüfst du sie und schreibst sie in die Java-Datei zurück, aus der sie stammt. Auf dieser Seite wird dieser Schritt beschrieben.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ schreibt in dein Projekt
Bewahre deine Arbeit in der Versionskontrolle auf. Lies den Diff, bevor du ihn anwendest, und lies ihn vor dem Commit noch einmal.
:::

## Ausstehende Änderungen {#pending-changes}

Jede Eigenschaft, die du änderst, wird als ausstehende Änderung erfasst, und craftforJ zeigt an, wie viele Änderungen noch ausstehen. Ausstehende Änderungen bleiben auch nach dem Neuladen einer Seite oder einem Routenwechsel erhalten, da craftforJ sie erneut anwendet, wenn deine Komponenten neu erstellt werden.

## Überprüfen und anwenden {#reviewing-and-applying}

Drücke <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>, um die Überprüfung zu öffnen. Die Änderungen sind nach der Datei gruppiert, in die sie geschrieben werden. Für jede Änderung werden die Eigenschaft sowie ihr alter und neuer Wert angezeigt. Außerdem kannst du die Änderung aufklappen, um den Diff der Datei anzuzeigen. Wenn eine Änderung einen berechneten Wert durch einen festen Wert ersetzen würde, warnt craftforJ dich und nennt den Ausdruck, der ersetzt werden soll. Es wird nichts geschrieben, bevor du die Änderungen anwendest. Zuvor kannst du jede Änderung einzeln zurücksetzen oder verwerfen.

![Die Überprüfung mit nach Datei gruppierten Änderungen und einer aufgeklappten Änderung mit ihrem Diff](/img/craftforj/source-changes/review.png#rounded-border)

## Festlegen, wo eine Änderung geschrieben wird {#choosing-where-a-change-is-written}

Wo eine Änderung geschrieben wird, bestimmt, wie weit sie reicht. Wird eine Komponente direkt in einer Ansicht erstellt, wird die Änderung in diese Ansicht geschrieben. Wird sie innerhalb einer wiederverwendbaren Klasse erstellt, hast du zwei Möglichkeiten:

- **Die Verwendung** – die Stelle, an der die Komponente verwendet wird. Dadurch ändert sich nur der Bildschirm, den du gerade siehst. Dies ist die Standardeinstellung.
- **Die Definition** – die Stelle, an der die Komponente erstellt wird. Dadurch ändern sich alle Bildschirme, auf denen sie verwendet wird.

Für jede ausstehende Änderung wird angezeigt, welche der beiden Möglichkeiten zutrifft. Du kannst zwischen ihnen wechseln. Einige Eigenschaften können nur in der Definition geschrieben werden, weil die Komponente sie selbst festlegt, anstatt sie vom Aufrufer zu übernehmen. craftforJ kennzeichnet diese Eigenschaften, bevor du die Änderungen anwendest.

## Nach dem Anwenden {#after-you-apply}

Beim Schreiben von Java wird deine App neu erstellt und gestartet. craftforJ meldet den Neustart, wartet darauf und stellt die Verbindung mit deiner Auswahl und den übrigen ausstehenden Änderungen wieder her. Angewendete Änderungen werden aus der Liste der ausstehenden Änderungen entfernt, sobald sie in deinen Dateien stehen.

Nur an dieser Stelle ist deine Reload-Konfiguration relevant. craftforJ benötigt kein Live-Reload, da alle Änderungen, die du beim Untersuchen vornimmst, sofort in der laufenden App wirksam werden, ohne dass ein Neuaufbau erforderlich ist. Das Schreiben in den Quellcode ist etwas anderes: Es ändert eine Datei, aus der deine App erstellt wurde. Daher muss die App neu erstellt werden, damit die Änderung aus deinem Code statt von craftforJ stammt. Wenn [Live-Reload](/docs/configuration/deploy-reload/overview) konfiguriert ist, geschieht das automatisch. Andernfalls musst du die App selbst neu starten.

## Rückgängig machen und wiederherstellen <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Drücke <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> in craftforJ, um deine letzte Änderung rückgängig zu machen, oder <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd>, um sie wiederherzustellen.

Das Rückgängigmachen gilt für Änderungen, die du in der laufenden App vornimmst – von [Eigenschaftsänderungen](/docs/craftforj/inspector#reading-and-changing-properties) bis hin zu [Theme-Anpassungen](/docs/craftforj/theme#adjusting-a-theme). Es gilt auch für Änderungen, die craftforJ in dein Projekt geschrieben hat, etwa angewendete Eigenschaftsänderungen, ein [gespeichertes Theme](/docs/craftforj/theme#saving-a-theme) oder eine [aus dem Quellcode entfernte Komponente](/docs/craftforj/inspector#removing-a-component). Wenn du eine geschriebene Änderung rückgängig machst, wird die Datei in ihren vorherigen Zustand zurückversetzt. Handelt es sich dabei um eine Java-Datei, muss deine App neu erstellt werden, bevor die Änderung sichtbar wird – genau wie [nach dem Anwenden](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Wenn du mehrmals auf „Rückgängig“ drückst, werden deine Änderungen einzeln und beginnend mit der neuesten zurückgenommen. Beim Wiederherstellen werden sie in der Reihenfolge ausgeführt, in der du sie vorgenommen hast. Wenn du nach dem Rückgängigmachen eine neue Änderung vornimmst, werden alle Änderungen gelöscht, die du hättest wiederherstellen können.

### Wenn eine Änderung nicht rückgängig gemacht werden kann {#when-an-undo-is-blocked}

Wenn du eine Datei in deinem eigenen Editor bearbeitest und speicherst, nachdem craftforJ hineingeschrieben hat, würde das Rückgängigmachen oder Wiederherstellen dieses Schritts deine Änderung überschreiben. craftforJ verweigert den Vorgang, lässt die Datei unverändert und nennt sie:

```
Changed outside craftforJ: ContactFormView.java. Undo that edit in your editor, or skip this step.
```

Du hast zwei Möglichkeiten:

- Mache deine Änderung in deinem Editor rückgängig, damit die Datei wieder dem entspricht, was craftforJ geschrieben hat, und versuche es dann erneut.
- Wähle **Überspringen**. Dadurch bleibt die Datei unverändert und der Schritt wird aus dem Verlauf entfernt. Beim Überspringen wird nichts zurückgesetzt. Daher können frühere Schritte, die dieselbe Datei betreffen, weiterhin blockiert sein.

## Deaktivieren {#turning-it-off}

Du kannst das Schreiben in Java für eine App in den craftforJ-Einstellungen deaktivieren oder die Funktion vollständig über die Eigenschaft [`source-changes`](/docs/craftforj/configuration#feature-flags) entfernen. In beiden Fällen funktioniert die Bearbeitung von Eigenschaften weiterhin, bleibt aber nur in der laufenden App wirksam.
