---
sidebar_position: 40
title: Interactivity
slug: Interactivity
_i18n_hash: 64d7e4b5603b5335b498c05566de1784
---
El componente `Table` responderá a varias entradas del teclado por parte de los usuarios para permitir una interacción más precisa. La siguiente lista describe los comportamientos diversos cuando se presiona la tecla designada:


## Interacciones del Teclado {#keyboard-interations}

|Tecla|Comportamiento|
|:-:|-|
|<kbd>Space</kbd>|Selecciona la fila actual.|
|<kbd>Enter</kbd>|Enfoca la celda, o el elemento enfocable si la celda tiene un renderer.|
|<kbd>Home</kbd>|Enfoca la primera celda de la primera columna y fila.|
|<kbd>End</kbd>|Enfoca la última celda de la última columna y fila.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Si la celda está en el cuerpo de la tabla y se presiona Shift + Tab, enfoca la primera celda enfocable en el encabezado.|
|<kbd>&#8594;</kbd>|Si se presiona la tecla Ctrl, enfoca la última celda de la fila actual. De lo contrario, enfoca la siguiente celda horizontalmente.|
|<kbd>&#8592;</kbd>|Si se presiona la tecla Ctrl, enfoca la primera celda de la fila actual. De lo contrario, enfoca la celda anterior horizontalmente.|
|<kbd>&#8593;</kbd>|Si se presiona la tecla Ctrl, enfoca la primera celda de la columna actual. De lo contrario, enfoca la celda anterior verticalmente.|
|<kbd>&#8595;</kbd>|Si se presiona la tecla Ctrl, enfoca la última celda de la columna actual. De lo contrario, enfoca la siguiente celda verticalmente.|
