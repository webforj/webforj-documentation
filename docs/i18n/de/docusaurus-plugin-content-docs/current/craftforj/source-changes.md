---
title: Änderungen anwenden
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c421d594499c29fdae8dd88ca6e35e3d
---
Eine Eigenschaft in craftforJ zu ändern, wirkt sich auf die laufende App aus und sonst auf nichts. Um eine Änderung beizubehalten, überprüfst du sie und schreibst sie in die Java-Datei, aus der sie stammt. Auf dieser Seite wird dieser Schritt beschrieben.

<VideoPlayer video="craftforj/apply-changes" width={1920} height={992} title="Apply changes" />

:::warning craftforJ schreibt in dein Projekt
Verwalte deine Arbeit in der Versionskontrolle. Lies den Diff, bevor du die Änderungen anwendest, und noch einmal, bevor du sie commitest.
:::

## Ausstehende Änderungen {#pending-changes}

Jede Eigenschaft, die du änderst, wird als ausstehende Änderung erfasst, und craftforJ zeigt an, wie viele Änderungen noch offen sind. Ausstehende Änderungen bleiben auch nach dem Neuladen einer Seite oder einem Routenwechsel erhalten, weil craftforJ sie erneut anwendet, wenn deine Komponenten neu erstellt werden.

## Überprüfen und Anwenden {#reviewing-and-applying}

Drücke <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>, um die Überprüfung zu öffnen. Die Änderungen sind nach der Datei gruppiert, in die sie geschrieben werden. Für jede Änderung werden die Eigenschaft sowie ihr alter und neuer Wert angezeigt. Außerdem kannst du die Änderung aufklappen, um den Diff der Datei zu sehen. Wenn eine Änderung einen berechneten Wert durch einen festen Wert ersetzen würde, warnt craftforJ dich und nennt den Ausdruck, der ersetzt werden soll. Bis zum Anwenden wird nichts geschrieben. Zuvor kannst du jede Änderung einzeln rückgängig machen oder verwerfen.

![Die Überprüfung mit nach Datei gruppierten Änderungen und einer aufgeklappten Änderung samt Diff](/img/craftforj/source-changes/review.png#rounded-border)

## Festlegen, wohin eine Änderung geschrieben wird {#choosing-where-a-change-is-written}

Wohin eine Änderung geschrieben wird, bestimmt, wie weit sie sich auswirkt. Wird eine Komponente direkt in einer View erstellt, landet die Änderung in dieser View. Wird sie in einer wiederverwendbaren Klasse erstellt, hast du zwei Möglichkeiten:

- **Die Verwendung** – die Stelle, an der die Komponente verwendet wird. Dadurch wird nur der Bildschirm geändert, den du gerade siehst. Das ist die Standardeinstellung.
- **Die Definition** – die Stelle, an der die Komponente erstellt wird. Dadurch werden alle Bildschirme geändert, auf denen sie verwendet wird.

Bei jeder ausstehenden Änderung wird angezeigt, welche der beiden Möglichkeiten zutrifft. Du kannst zwischen ihnen wechseln. Einige Eigenschaften können nur in der Definition geschrieben werden, weil die Komponente sie selbst festlegt, anstatt sie vom Aufrufer zu übernehmen. craftforJ kennzeichnet solche Eigenschaften, bevor du die Änderungen anwendest.

## Nach dem Anwenden {#after-you-apply}

Wenn Java-Code geschrieben wird, wird deine App neu erstellt und neu gestartet. craftforJ meldet den Neustart, wartet darauf und stellt die Verbindung wieder her. Deine Auswahl und die übrigen ausstehenden Änderungen bleiben dabei erhalten. Angewendete Änderungen werden aus der Liste der ausstehenden Änderungen entfernt, sobald sie in deinen Dateien stehen.

Nur an diesem Punkt ist deine Reload-Konfiguration relevant. craftforJ benötigt kein Live-Reload, um zu funktionieren, denn alles, was du während der Inspektion änderst, wird sofort in der laufenden App wirksam – ganz ohne Neuerstellung. Das Schreiben in den Quellcode ist etwas anderes: Dabei wird eine Datei geändert, aus der deine App erstellt wurde. Deshalb muss die App neu erstellt werden, damit die Änderung aus deinem Code statt von craftforJ stammt. Wenn [Live-Reload](/docs/configuration/deploy-reload/overview) konfiguriert ist, geschieht das automatisch. Andernfalls musst du die App selbst neu starten.

## Rückgängig machen und wiederholen <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Drücke in craftforJ <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>, um deine letzte Änderung rückgängig zu machen, und <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd>, um sie wiederherzustellen.

Das Rückgängigmachen funktioniert für Änderungen, die du in der laufenden App vornimmst – von [Eigenschaftsänderungen](/docs/craftforj/inspector#reading-and-changing-properties) bis zu [Theme-Anpassungen](/docs/craftforj/theme#adjusting-a-theme). Es funktioniert auch für Änderungen, die craftforJ in dein Projekt geschrieben hat, etwa angewendete Eigenschaftsänderungen, ein [gespeichertes Theme](/docs/craftforj/theme#saving-a-theme) oder eine [aus dem Quellcode entfernte Komponente](/docs/craftforj/inspector#removing-a-component). Wenn du eine geschriebene Änderung rückgängig machst, wird die Datei auf ihren vorherigen Stand zurückgesetzt. Bei einer Java-Datei muss deine App neu erstellt werden, bevor die Änderung sichtbar wird – wie [nach dem Anwenden](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Wenn du mehrmals auf „Rückgängig“ drückst, werden deine Änderungen einzeln und beginnend mit der neuesten zurückgenommen. Mit „Wiederholen“ werden sie in der Reihenfolge wiederhergestellt, in der du sie vorgenommen hast. Wenn du nach dem Rückgängigmachen eine neue Änderung vornimmst, werden alle Änderungen gelöscht, die du hättest wiederholen können.

### Wenn eine Änderung nicht rückgängig gemacht werden kann {#when-an-undo-is-blocked}

Wenn du eine Datei in deinem eigenen Editor bearbeitest und speicherst, nachdem craftforJ hineingeschrieben hat, würde das Rückgängigmachen oder Wiederholen dieses Schritts deine Änderung überschreiben. craftforJ bricht den Vorgang stattdessen ab, lässt die Datei unverändert und nennt sie:

```
Außerhalb von craftforJ geändert: ContactFormView.java. Mache die Änderung in deinem Editor rückgängig oder überspringe diesen Schritt.
```

Du hast zwei Möglichkeiten:

- Mache deine Änderung im Editor rückgängig, sodass die Datei wieder dem entspricht, was craftforJ geschrieben hat, und versuche es dann erneut.
- Wähle **Überspringen**. Die Datei bleibt unverändert, und der Schritt wird aus dem Verlauf entfernt. Beim Überspringen wird nichts zurückgesetzt. Daher können frühere Schritte, die dieselbe Datei geändert haben, weiterhin blockiert sein.

## Deaktivieren {#turning-it-off}

In den craftforJ-Einstellungen kannst du für eine App das Schreiben in Java deaktivieren. Alternativ kannst du es über die Eigenschaft [`source-changes`](/docs/craftforj/configuration#feature-flags) vollständig entfernen. In beiden Fällen funktioniert das Bearbeiten von Eigenschaften weiterhin, die Änderungen bleiben jedoch nur live.
