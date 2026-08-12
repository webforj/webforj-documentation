---
title: Writing Changes to Source
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c79e8574cbf260fd784a2cffc00a0ab5
---
Das Ändern einer Eigenschaft in craftforJ ändert die laufende App und nichts anderes. Um eine Änderung zu speichern, überprüfen Sie sie und schreiben Sie sie in die Java-Datei, aus der sie stammt. Diese Seite beschreibt diesen Schritt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4" type="video/mp4" />
  </video>
</div>

:::warning craftforJ schreibt in Ihr Projekt
Halten Sie Ihre Arbeit unter Versionskontrolle. Lesen Sie den Unterschied, bevor Sie ihn anwenden, und lesen Sie ihn erneut, bevor Sie committen.
:::

## Ausstehende Änderungen {#pending-changes}

Jede Eigenschaft, die Sie ändern, wird als ausstehende Änderung aufgezeichnet, und craftforJ zeigt, wie viele auf warten. Ausstehende Änderungen überstehen ein Neuladen der Seite und einen Routenwechsel, da craftforJ sie wieder anwendet, wenn Ihre Komponenten neu erstellt werden.

## Überprüfen und Anwenden {#reviewing-and-applying}

Drücken Sie <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>, um die Überprüfung zu öffnen. Änderungen sind nach der Datei gruppiert, in der sie landen werden. Jede zeigt die Eigenschaft mit ihrem alten und neuen Wert und erweitert sich in den Unterschied der Datei. Wenn eine Änderung einen berechneten Wert durch einen festen ersetzen würde, warnt craftforJ Sie und nennt den Ausdruck, den es ersetzen wird. Nichts wird geschrieben, bis Sie anwenden. Bevor Sie dies tun, können Sie jede Änderung einzeln zurücksetzen oder verwerfen.

![Die Überprüfung mit Änderungen, die nach Datei gruppiert sind, und einer, die auf ihren Unterschied erweitert ist](/img/craftforj/source-changes/review.png#rounded-border)

## Wählen, wo eine Änderung geschrieben wird {#choosing-where-a-change-is-written}

Wo eine Änderung geschrieben wird, bestimmt, wie weit sie reicht. Wenn eine Komponente direkt in einer Ansicht erstellt wird, geht die Änderung in diese Ansicht. Wenn sie innerhalb einer wiederverwendbaren Klasse erstellt wird, haben Sie zwei Optionen:

- **Die Verwendung** - der Ort, an dem die Komponente verwendet wird, was nur den Bildschirm vor Ihnen ändert. Dies ist die Standardeinstellung.
- **Die Definition** - der Ort, an dem die Komponente erstellt wird, was jeden Bildschirm, der sie verwendet, ändert.

Jede ausstehende Änderung zeigt an, welche der beiden Optionen gilt, und ermöglicht es Ihnen, zwischen ihnen zu wechseln. Einige Eigenschaften können nur an der Definition geschrieben werden, da die Komponente sie selbst festlegt und nicht vom Aufrufer akzeptiert. craftforJ markiert diese, bevor Sie anwenden.

## Nachdem Sie angewendet haben {#after-you-apply}

Das Schreiben von Java verursacht, dass Ihre App neu gebaut und neu gestartet wird. craftforJ meldet den Neustart, wartet darauf und stellt die Verbindung mit Ihrer Auswahl und Ihren verbleibenden ausstehenden Änderungen wieder her. Angewendete Änderungen verlassen die ausstehende Liste, sobald sie in Ihren Dateien sind.

Dies ist der einzige Punkt, an dem Ihre Reload-Konfiguration von Bedeutung ist. craftforJ benötigt kein Live-Reload, um zu funktionieren, da alles, was Sie während der Inspektion ändern, sofort in der laufenden App wirksam wird, ohne dass ein Neubau erforderlich ist. Das Schreiben in den Quellcode ist anders: Es ändert eine Datei, aus der Ihre App erstellt wurde, sodass die App neu gebaut werden muss, bevor die Änderung aus Ihrem Code und nicht aus craftforJ kommt. Mit [Live-Reload](/docs/configuration/deploy-reload/overview), das konfiguriert ist, geschieht dies automatisch. Ohne wird die App selbst neu gestartet.

## Deaktivieren {#turning-it-off}

Sie können das Schreiben in Java für eine App in den craftforJ-Einstellungen ausschalten oder es vollständig mit der [`source-changes`](/docs/craftforj/configuration#feature-flags) Eigenschaft entfernen. Wenn eine von beiden deaktiviert ist, funktioniert das Bearbeiten von Eigenschaften weiterhin, bleibt aber live.
