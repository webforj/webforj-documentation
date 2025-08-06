---
sidebar_position: 40
title: Interactivity
slug: Interactivity
_i18n_hash: 64d7e4b5603b5335b498c05566de1784
---
De `Table` component reageert op verschillende toetsenbordinvoeren van gebruikers om fijnere interactie mogelijk te maken. De volgende lijst schetst de verschillende gedragingen wanneer de aangewezen toets wordt ingedrukt:

## Keyboard Interations {#keyboard-interations}

|Toets|Gedrag|
|:-:|-|
|<kbd>Spatie</kbd>|Selecteert de huidige rij.|
|<kbd>Enter</kbd>|Focust de cel, of het focusbare element als de cel een renderer heeft.|
|<kbd>Home</kbd>|Focust de eerste cel van de eerste kolom en rij.|
|<kbd>Eind</kbd>|Focust de laatste cel van de laatste kolom en rij.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Als de cel zich in het lichaam van de tabel bevindt en Shift + Tab wordt ingedrukt, focust het de eerste focusbare cel in de header.|
|<kbd>&#8594;</kbd>|Als de Ctrl-toets wordt ingedrukt, focust het de laatste cel van de huidige rij. Anders focust het de volgende cel horizontaal.|
|<kbd>&#8592;</kbd>|Als de Ctrl-toets wordt ingedrukt, focust het de eerste cel van de huidige rij. Anders focust het de vorige cel horizontaal.|
|<kbd>&#8593;</kbd>|Als de Ctrl-toets wordt ingedrukt, focust het de eerste cel van de huidige kolom. Anders focust het de vorige cel verticaal.|
|<kbd>&#8595;</kbd>|Als de Ctrl-toets wordt ingedrukt, focust het de laatste cel van de huidige kolom. Anders focust het de volgende cel verticaal.|
