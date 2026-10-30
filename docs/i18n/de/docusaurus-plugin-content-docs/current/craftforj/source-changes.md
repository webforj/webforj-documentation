---
title: Änderungen an der Quelle schreiben
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: dd96407889e9e9fa1de4af299809dcad
---
Das Ändern einer Eigenschaft in craftforJ verändert die laufende Anwendung und sonst nichts. Um eine Änderung zu speichern, überprüfen Sie sie und schreiben sie in die Java-Datei, aus der sie stammt. Diese Seite beschreibt diesen Schritt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ schreibt in Ihr Projekt
Halten Sie Ihre Arbeit in der Versionskontrolle. Lesen Sie den Unterschied, bevor Sie ihn anwenden, und lesen Sie ihn erneut, bevor Sie ihn festschreiben.
:::

## Ausstehende Änderungen {#pending-changes}

Jede Eigenschaft, die Sie ändern, wird als ausstehende Änderung aufgezeichnet, und craftforJ zeigt an, wie viele darauf warten. Ausstehende Änderungen überstehen ein Neuladen der Seite und einen Routenwechsel, da craftforJ sie wieder anwendet, wenn Ihre Komponenten neu aufgebaut werden.

## Überprüfen und Anwenden {#reviewing-and-applying}

Drücken Sie <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>, um die Überprüfung zu öffnen. Änderungen werden nach der Datei gruppiert, in die sie gelangen werden. Jede zeigt die Eigenschaft mit ihrem alten und neuen Wert und entfaltet sich zum Unterschied der Datei. Wenn eine Änderung einen berechneten Wert durch einen festen ersetzen würde, warnt craftforJ Sie und nennt den Ausdruck, den es ersetzen wird. Nichts wird geschrieben, bis Sie anwenden. Bevor Sie dies tun, können Sie jede Änderung für sich zurücksetzen oder verwerfen.

![Die Überprüfung mit Änderungen, die nach Datei gruppiert sind und eine zu ihrem Unterschied entfaltet](/img/craftforj/source-changes/review.png#rounded-border)

## Wählen, wo eine Änderung geschrieben wird {#choosing-where-a-change-is-written}

Wo eine Änderung geschrieben wird, bestimmt, wie weit sie reicht. Wenn eine Komponente direkt in einer Ansicht erstellt wird, geht die Änderung in diese Ansicht. Wenn sie innerhalb einer wiederverwendbaren Klasse erstellt wird, haben Sie zwei Optionen:

- **Die Verwendung** - der Ort, an dem die Komponente verwendet wird, was nur den Bildschirm vor Ihnen ändert. Dies ist der Standard.
- **Die Definition** - der Ort, an dem die Komponente erstellt wird, was jeden Bildschirm ändert, der sie verwendet.

Jede ausstehende Änderung zeigt an, welche der beiden zutrifft, und lässt Sie zwischen ihnen wechseln. Einige Eigenschaften können nur an der Definition geschrieben werden, da die Komponente sie selbst setzt, anstatt sie vom Aufrufer zu akzeptieren. craftforJ markiert diese, bevor Sie anwenden.

## Nach dem Anwenden {#after-you-apply}

Das Schreiben von Java führt dazu, dass Ihre Anwendung neu erstellt und neu gestartet wird. craftforJ meldet den Neustart, wartet darauf und stellt die Verbindung mit Ihrer Auswahl und Ihren verbleibenden ausstehenden Änderungen wieder her. Angewendete Änderungen verlassen die ausstehende Liste, sobald sie in Ihren Dateien sind.

Dies ist der einzige Punkt, an dem Ihre Neulade-Konfiguration wichtig ist. craftforJ benötigt kein Live-Reload, um zu funktionieren, da alles, was Sie beim Inspektieren ändern, sofort in der laufenden Anwendung wirksam wird, ohne dass ein Neubau erforderlich ist. Das Schreiben in die Quelle ist anders: Es ändert eine Datei, aus der Ihre Anwendung erstellt wurde, weshalb die Anwendung neu erstellt werden muss, bevor die Änderung aus Ihrem Code und nicht aus craftforJ kommt. Mit [live reload](/docs/configuration/deploy-reload/overview) konfiguriert, geschieht dies von selbst. Andernfalls starten Sie die Anwendung selbst neu.

## Deaktivieren {#turning-it-off}

Sie können das Schreiben in Java für eine Anwendung in den craftforJ-Einstellungen deaktivieren oder es vollständig mit der [`source-changes`](/docs/craftforj/configuration#feature-flags) Eigenschaft entfernen. Mit beiden deaktiviert funktioniert die Bearbeitung von Eigenschaften weiterhin, bleibt jedoch live.
