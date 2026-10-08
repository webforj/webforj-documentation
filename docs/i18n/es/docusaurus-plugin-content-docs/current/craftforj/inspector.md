---
title: Inspeccionando componentes
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 5dd1df77df56d81dd4e54c1998289e71
---
El Inspector muestra el árbol de componentes que tu código Java construyó. Un `Composite` aparece como la clase que escribiste, conteniendo los hijos que le diste en el orden en que webforJ los sostiene, así que la estructura en craftforJ coincide con la estructura en tu fuente.

![El árbol de componentes con un componente seleccionado y destacado en la aplicación en ejecución](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Seleccionando un componente {#selecting-a-component}

Para seleccionar un componente de la página, presiona <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> y haz clic en él. craftforJ seleccionará el nodo correspondiente en el árbol. Pasar el ratón sobre un nodo en el árbol hace lo contrario y resalta ese componente en la página, así que puedes moverte entre la pantalla y el árbol en ambas direcciones.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

Para buscar en el árbol, presiona <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Encerrar un término entre barras lo trata como una expresión regular. Hacer clic derecho en un nodo abre las acciones disponibles para él. Puedes abrir su fuente o entregárselo al [asistente](/docs/craftforj/ai).

## Leyendo y cambiando propiedades {#reading-and-changing-properties}

Seleccionar un componente llena la barra lateral con sus propiedades, agrupadas por lo que afectan. Las propiedades que ofrece un componente dependen del componente, y algunas de ellas son de solo lectura. Las propiedades que no se leen bien como texto plano obtienen un editor adecuado a su valor en su lugar. Cambiar un valor toma efecto en la aplicación en ejecución inmediatamente.

:::info Las ediciones en vivo no modifican tus archivos
Una edición de propiedad cambia la aplicación frente a ti y nada más. Llevarlo a tu fuente es un paso separado que realizas deliberadamente, descrito en [Escribiendo cambios en la fuente](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## Visualizando la fuente de un componente {#viewing-the-source-of-a-component}

Puedes rastrear cualquier componente hasta el Java que lo construyó. De manera predeterminada, la fuente se abre en craftforJ como de solo lectura, posicionada en la línea que creó el componente. Puedes configurar craftforJ para abrirlo en tu editor en su lugar, en la misma línea. Cuando un componente no se puede rastrear hasta una línea, craftforJ informa eso en lugar de abrir un visor vacío.

![El visor de fuente posicionado en la línea que creó el componente seleccionado](/img/craftforj/inspector/source-viewer.png#rounded-border)
