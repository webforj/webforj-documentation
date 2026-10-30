---
title: Inspeccionando Componentes
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 93bf0d91de1f942373082ccebd0bab6d
---
El Inspector muestra el árbol de componentes que tu código Java construyó. Un `Composite` aparece como la clase que escribiste, conteniendo los hijos que le diste en el orden en que webforJ los mantiene, por lo que la estructura en craftforJ coincide con la estructura en tu código fuente.

![El árbol de componentes con un componente seleccionado y resaltado en la aplicación en ejecución](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Seleccionando un componente {#selecting-a-component}

Para seleccionar un componente de la página, presiona <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> y haz clic en él. craftforJ selecciona el nodo correspondiente en el árbol. Pasar el cursor sobre un nodo en el árbol hace lo contrario y resalta ese componente en la página, por lo que puedes moverte entre la pantalla y el árbol en ambas direcciones.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Para buscar en el árbol, presiona <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Envolver un término entre barras lo trata como una expresión regular. Hacer clic derecho en un nodo abre las acciones disponibles para él. Puedes abrir su fuente o entregárselo al [asistente](/docs/craftforj/ai).

## Leyendo y cambiando propiedades {#reading-and-changing-properties}

Seleccionar un componente llena la barra lateral con sus propiedades, agrupadas por lo que afectan. Las propiedades que un componente ofrece dependen del componente, y algunas de ellas son de solo lectura. Las propiedades que no se leen bien como texto plano obtienen un editor adecuado a su valor. Cambiar un valor tiene efecto en la aplicación en ejecución de inmediato.

:::info Las ediciones en vivo no modifican tus archivos
Una edición de propiedad cambia la aplicación frente a ti y nada más. Hacer que entre en tu código fuente es un paso separado que realizas deliberadamente, descrito en [Escribiendo cambios en la fuente](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Visto la fuente de un componente {#viewing-the-source-of-a-component}

Puedes rastrear cualquier componente hasta el Java que lo construyó. Por defecto, la fuente se abre en craftforJ como de solo lectura, posicionada en la línea que creó el componente. Puedes configurar craftforJ para abrirlo en tu editor en su lugar, en la misma línea. Cuando un componente no puede ser rastreado hasta una línea, craftforJ lo informa en lugar de abrir un visor vacío.

![El visor de fuente posicionado en la línea que creó el componente seleccionado](/img/craftforj/inspector/source-viewer.png#rounded-border)
