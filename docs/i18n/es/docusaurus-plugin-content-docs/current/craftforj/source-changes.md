---
title: Aplicar cambios
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c421d594499c29fdae8dd88ca6e35e3d
---
Cambiar una propiedad en craftforJ modifica la aplicación en ejecución y nada más. Para conservar el cambio, revísalo y escríbelo en el archivo Java del que procede. Esta página describe ese paso.

<VideoPlayer video="craftforj/apply-changes" width={1920} height={992} title="Apply changes" />

:::warning craftforJ escribe en tu proyecto
Mantén tu trabajo bajo control de versiones. Lee el diff antes de aplicarlo y vuelve a leerlo antes de confirmar los cambios.
:::

## Cambios pendientes {#pending-changes}

Cada propiedad que cambias se registra como un cambio pendiente, y craftforJ muestra cuántos quedan por aplicar. Los cambios pendientes se conservan al recargar la página o cambiar de ruta, porque craftforJ los vuelve a aplicar cuando se reconstruyen los componentes.

## Revisar y aplicar {#reviewing-and-applying}

Pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> para abrir la revisión. Los cambios se agrupan por el archivo en el que se escribirán. Cada uno muestra la propiedad con su valor anterior y el nuevo, y se puede expandir para ver el diff del archivo. Si un cambio reemplaza un valor calculado por uno fijo, craftforJ te avisa e indica la expresión que va a reemplazar. No se escribe nada hasta que lo aplicas. Antes de hacerlo, puedes revertir o descartar cada cambio por separado.

![La revisión con los cambios agrupados por archivo y uno expandido para mostrar su diff](/img/craftforj/source-changes/review.png#rounded-border)

## Elegir dónde se escribe un cambio {#choosing-where-a-change-is-written}

El lugar donde se escribe un cambio determina hasta dónde llega. Cuando un componente se crea directamente en una vista, el cambio se escribe en esa vista. Cuando se crea dentro de una clase reutilizable, tienes dos opciones:

- **El uso**: el lugar donde se usa el componente; solo cambia la pantalla que tienes delante. Esta es la opción predeterminada.
- **La definición**: el lugar donde se crea el componente; cambia todas las pantallas que lo usan.

Cada cambio pendiente indica cuál de las dos opciones se aplica y te permite cambiarla. Algunas propiedades solo se pueden escribir en la definición, porque el propio componente las establece en lugar de recibirlas de quien lo invoca. craftforJ las marca antes de que las apliques.

## Después de aplicar {#after-you-apply}

Escribir en Java hace que la aplicación se reconstruya y reinicie. craftforJ informa del reinicio, espera a que termine y vuelve a conectarse conservando tu selección y los cambios pendientes restantes. Los cambios aplicados dejan de aparecer en la lista de pendientes una vez que están en tus archivos.

Este es el único momento en que importa la configuración de recarga. craftforJ no necesita recarga en vivo para funcionar, porque todo lo que cambias mientras inspeccionas se aplica inmediatamente en la aplicación en ejecución, sin necesidad de reconstruirla. Escribir en el código fuente es distinto: modifica un archivo a partir del cual se creó la aplicación, así que esta debe reconstruirse para que el cambio provenga de tu código y no de craftforJ. Si tienes configurada la [recarga en vivo](/docs/configuration/deploy-reload/overview), esto ocurre automáticamente. Si no, reinicia la aplicación manualmente.

## Deshacer y rehacer <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> en craftforJ para deshacer el último cambio, y <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> para rehacerlo.

Deshacer funciona con los cambios que haces en la aplicación en ejecución, desde las [ediciones de propiedades](/docs/craftforj/inspector#reading-and-changing-properties) hasta los [ajustes del tema](/docs/craftforj/theme#adjusting-a-theme). También funciona con los cambios que craftforJ ha escrito en tu proyecto, como los cambios de propiedades aplicados, un [tema guardado](/docs/craftforj/theme#saving-a-theme) o un [componente eliminado del código fuente](/docs/craftforj/inspector#removing-a-component). Deshacer un cambio escrito restaura el archivo a su estado anterior. Si el archivo es Java, la aplicación debe reconstruirse para que se vea el cambio, igual que [después de aplicar](#after-you-apply).

<!-- TODO: añadir el registro de deshacer/rehacer -->

Si pulsas deshacer varias veces, los cambios se revierten uno a uno, empezando por el más reciente; rehacer los vuelve a aplicar en el orden en que los hiciste. Si haces un cambio nuevo después de deshacer, se borra todo lo que podrías haber rehecho.

### Cuando no se puede deshacer un cambio {#when-an-undo-is-blocked}

Si editas y guardas un archivo en tu propio editor después de que craftforJ haya escrito en él, deshacer o rehacer ese paso sobrescribiría tu edición. En su lugar, craftforJ se niega a hacerlo, deja el archivo tal como está e indica su nombre:

```
Modificado fuera de craftforJ: ContactFormView.java. Deshaz esa edición en tu editor o sáltate este paso.
```

Tienes dos opciones:

- Deshaz tu edición en el editor para que el archivo coincida con lo que escribió craftforJ y vuelve a intentarlo.
- Elige **Omitir**. Esto deja el archivo tal como está y elimina ese paso del historial. Omitir no revierte nada, así que los pasos anteriores que afectaran al mismo archivo aún pueden quedar bloqueados.

## Desactivarlo {#turning-it-off}

Puedes desactivar la escritura en Java para una aplicación en los ajustes de craftforJ, o eliminarla por completo con la propiedad [`source-changes`](/docs/craftforj/configuration#feature-flags). Si desactivas cualquiera de estas opciones, la edición de propiedades sigue funcionando, pero los cambios solo se aplican en vivo.
