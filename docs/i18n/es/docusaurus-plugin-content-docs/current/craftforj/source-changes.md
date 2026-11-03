---
title: Aplicar cambios
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: de9ef40848946a35567c308c132dce0d
---
Cambiar una propiedad en craftforJ modifica la aplicación en ejecución y nada más. Para conservar un cambio, revísalo y escríbelo en el archivo Java del que proviene. Esta página describe ese paso.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ escribe en tu proyecto
Mantén tu trabajo bajo control de versiones. Lee el diff antes de aplicarlo y vuelve a leerlo antes de confirmar el cambio.
:::

## Cambios pendientes {#pending-changes}

Cada propiedad que cambias se registra como un cambio pendiente, y craftforJ muestra cuántos están esperando. Los cambios pendientes se conservan al recargar la página y al cambiar de ruta, porque craftforJ los vuelve a aplicar cuando se reconstruyen los componentes.

## Revisar y aplicar {#reviewing-and-applying}

Pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> para abrir la revisión. Los cambios se agrupan por el archivo en el que se escribirán. Cada uno muestra la propiedad con su valor anterior y el nuevo, y se puede expandir para ver el diff del archivo. Si un cambio va a sustituir un valor calculado por uno fijo, craftforJ te advierte y nombra la expresión que está a punto de reemplazar. No se escribe nada hasta que lo apliques. Antes de hacerlo, puedes revertir o descartar cada cambio por separado.

![La revisión con los cambios agrupados por archivo y uno expandido para mostrar su diff](/img/craftforj/source-changes/review.png#rounded-border)

## Elegir dónde se escribe un cambio {#choosing-where-a-change-is-written}

El lugar donde se escribe un cambio determina hasta dónde llega. Cuando un componente se construye directamente en una vista, el cambio se escribe en esa vista. Cuando se construye dentro de una clase reutilizable, tienes dos opciones:

- **El uso**: el lugar donde se utiliza el componente; solo cambia la pantalla que tienes delante. Esta es la opción predeterminada.
- **La definición**: el lugar donde se construye el componente; cambia todas las pantallas que lo utilizan.

Cada cambio pendiente indica cuál de las dos opciones se aplica y te permite alternar entre ellas. Algunas propiedades solo se pueden escribir en la definición, porque el propio componente las establece en lugar de aceptarlas de quien lo llama. craftforJ las marca antes de que las apliques.

## Después de aplicar {#after-you-apply}

Al escribir en Java, la aplicación se vuelve a compilar y se reinicia. craftforJ informa del reinicio, espera a que termine y se vuelve a conectar, conservando tu selección y los cambios pendientes restantes. Los cambios aplicados dejan de aparecer en la lista de pendientes una vez que se escriben en tus archivos.

Este es el único momento en que importa la configuración de recarga. craftforJ no necesita recarga en vivo para funcionar, porque todo lo que cambias mientras inspeccionas tiene efecto de inmediato en la aplicación en ejecución, sin necesidad de recompilar. Escribir en el código fuente es diferente: modifica un archivo a partir del cual se compiló la aplicación, por lo que esta debe recompilarse para que el cambio provenga de tu código y no de craftforJ. Si has configurado la [recarga en vivo](/docs/configuration/deploy-reload/overview), esto ocurre automáticamente. De lo contrario, reinicia la aplicación manualmente.

## Deshacer y rehacer <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> en craftforJ para deshacer el último cambio y <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> para rehacerlo.

La función de deshacer se aplica a los cambios que haces en la aplicación en ejecución, desde las [ediciones de propiedades](/docs/craftforj/inspector#reading-and-changing-properties) hasta los [ajustes del tema](/docs/craftforj/theme#adjusting-a-theme). También funciona con los cambios que craftforJ ha escrito en tu proyecto, como los cambios de propiedades aplicados, un [tema guardado](/docs/craftforj/theme#saving-a-theme) o un [componente eliminado del código fuente](/docs/craftforj/inspector#removing-a-component). Al deshacer un cambio escrito, el archivo vuelve a su estado anterior. Si el archivo es Java, la aplicación debe recompilarse para que se muestre el cambio, igual que [después de aplicar](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Al deshacer varias veces, los cambios se revierten uno por uno, empezando por el más reciente; al rehacer, se vuelven a aplicar en el orden en que los hiciste. Si haces un cambio nuevo después de deshacer, se borran todos los cambios que podrías haber rehecho.

### Cuando no se puede deshacer un cambio {#when-an-undo-is-blocked}

Si editas y guardas un archivo en tu propio editor después de que craftforJ haya escrito en él, deshacer o rehacer ese paso sobrescribiría tu edición. En su lugar, craftforJ lo impide, deja el archivo como está e indica cuál es:

```
Modificado fuera de craftforJ: ContactFormView.java. Deshaz esa edición en tu editor o sáltate este paso.
```

Tienes dos opciones:

- Deshaz la edición en tu editor para que el archivo coincida con lo que craftforJ escribió y vuelve a intentarlo.
- Elige **Omitir**. Esto deja el archivo como está y elimina ese paso del historial. Omitir no revierte nada, así que los pasos anteriores que hayan modificado el mismo archivo podrían seguir bloqueados.

## Desactivarlo {#turning-it-off}

Puedes desactivar la escritura en Java para una aplicación desde la configuración de craftforJ o eliminarla por completo con la propiedad [`source-changes`](/docs/craftforj/configuration#feature-flags). Si se desactiva de cualquiera de estas formas, la edición de propiedades sigue funcionando, pero los cambios solo permanecen activos en la aplicación.
