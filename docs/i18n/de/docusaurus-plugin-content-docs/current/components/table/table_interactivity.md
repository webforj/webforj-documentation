---
sidebar_position: 40
title: Interaktivität
slug: Interactivity
description: >-
  Reference the keyboard shortcuts that move focus, select rows, and navigate
  cells in the Table component.
_i18n_hash: 49377400524e9e1c3a25dd35cfb7750c
---
Die Komponente `Table` reagiert auf verschiedene Tastatureingaben, um eine präzisere Interaktion zu ermöglichen. Die folgende Liste beschreibt das jeweilige Verhalten beim Drücken der angegebenen Taste:

## Tastaturinteraktionen {#keyboard-interactions}

|Taste|Verhalten|
|:-:|-|
|<kbd>Space</kbd>|Wählt die aktuelle Zeile aus.|
|<kbd>Enter</kbd>|Fokussiert die Zelle oder, falls die Zelle einen Renderer hat, das fokussierbare Element.|
|<kbd>Home</kbd>|Fokussiert die erste Zelle der ersten Spalte und Zeile.|
|<kbd>End</kbd>|Fokussiert die letzte Zelle der letzten Spalte und Zeile.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Wenn sich die Zelle im Tabellenkörper befindet und Shift + Tab gedrückt wird, wird die erste fokussierbare Zelle in der Kopfzeile fokussiert.|
|<kbd>&#8594;</kbd>|Wenn die Ctrl-Taste gedrückt ist, wird die letzte Zelle der aktuellen Zeile fokussiert. Andernfalls wird die nächste Zelle horizontal fokussiert.|
|<kbd>&#8592;</kbd>|Wenn die Ctrl-Taste gedrückt ist, wird die erste Zelle der aktuellen Zeile fokussiert. Andernfalls wird die vorherige Zelle horizontal fokussiert.|
|<kbd>&#8593;</kbd>|Wenn die Ctrl-Taste gedrückt ist, wird die erste Zelle der aktuellen Spalte fokussiert. Andernfalls wird die vorherige Zelle vertikal fokussiert.|
|<kbd>&#8595;</kbd>|Wenn die Ctrl-Taste gedrückt ist, wird die letzte Zelle der aktuellen Spalte fokussiert. Andernfalls wird die nächste Zelle vertikal fokussiert.|
