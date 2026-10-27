---
sidebar_position: 40
title: Interactiviteit
slug: Interactivity
_i18n_hash: 8ec1ec3ef58f8bfcde31ee8dc9891579
---
De `Table` component reageert op verschillende toetsenbordinvoer van gebruikers om meer gedetailleerde interactie mogelijk te maken. De volgende lijst beschrijft de verschillende gedragingen wanneer de aangegeven toets wordt ingedrukt:

## Toetsenbordinteracties {#keyboard-interations}

|Toets|Gedrag|
|:-:|-|
|<kbd>Spatie</kbd>|Selecteert de huidige rij.|
|<kbd>Enter</kbd>|Focust de cel, of het focusbare element als de cel een renderer heeft.|
|<kbd>Home</kbd>|Focust de eerste cel van de eerste kolom en rij.|
|<kbd>Einde</kbd>|Focust de laatste cel van de laatste kolom en rij.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Als de cel in het lichaam van de tabel staat en Shift + Tab wordt ingedrukt, focust het de eerste focusbare cel in de kop.|
|<kbd>&#8594;</kbd>|Als de Ctrl-toets is ingedrukt, focust het de laatste cel van de huidige rij. Anders focust het de volgende cel horizontaal.|
|<kbd>&#8592;</kbd>|Als de Ctrl-toets is ingedrukt, focust het de eerste cel van de huidige rij. Anders focust het de vorige cel horizontaal.|
|<kbd>&#8593;</kbd>|Als de Ctrl-toets is ingedrukt, focust het de eerste cel van de huidige kolom. Anders focust het de vorige cel verticaal.|
|<kbd>&#8595;</kbd>|Als de Ctrl-toets is ingedrukt, focust het de laatste cel van de huidige kolom. Anders focust het de volgende cel verticaal.|
