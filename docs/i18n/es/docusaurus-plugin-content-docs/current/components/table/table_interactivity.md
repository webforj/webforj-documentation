---
sidebar_position: 40
title: Interactividad
slug: Interactivity
description: >-
  Consulta los atajos de teclado para mover el foco, seleccionar filas y navegar
  por las celdas del componente Table.
_i18n_title_hash: fcbe6546766fcb51d1197a3d75a7a7f4
_i18n_description_hash: 84575804ecb073661d379d81f9143b66
_i18n_hash: 49377400524e9e1c3a25dd35cfb7750c
---
El componente `Table` responderá a varias entradas del teclado para permitir una interacción más precisa. La siguiente lista describe los distintos comportamientos que se producen al pulsar la tecla indicada:

## Interacciones del teclado {#keyboard-interactions}

|Tecla|Comportamiento|
|:-:|-|
|<kbd>Space</kbd>|Selecciona la fila actual.|
|<kbd>Enter</kbd>|Enfoca la celda o el elemento enfocable si la celda tiene un renderizador.|
|<kbd>Home</kbd>|Enfoca la primera celda de la primera columna y fila.|
|<kbd>End</kbd>|Enfoca la última celda de la última columna y fila.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Si la celda está en el cuerpo de la tabla y se pulsa Shift + Tab, enfoca la primera celda enfocable del encabezado.|
|<kbd>&#8594;</kbd>|Si se pulsa la tecla Ctrl, enfoca la última celda de la fila actual. De lo contrario, enfoca la siguiente celda horizontalmente.|
|<kbd>&#8592;</kbd>|Si se pulsa la tecla Ctrl, enfoca la primera celda de la fila actual. De lo contrario, enfoca la celda anterior horizontalmente.|
|<kbd>&#8593;</kbd>|Si se pulsa la tecla Ctrl, enfoca la primera celda de la columna actual. De lo contrario, enfoca la celda anterior verticalmente.|
|<kbd>&#8595;</kbd>|Si se pulsa la tecla Ctrl, enfoca la última celda de la columna actual. De lo contrario, enfoca la siguiente celda verticalmente.|
