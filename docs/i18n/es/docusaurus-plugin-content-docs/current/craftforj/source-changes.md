---
title: Escribiendo Cambios en el Origen
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: dd96407889e9e9fa1de4af299809dcad
---
Cambiar una propiedad en craftforJ altera la aplicación en ejecución y nada más. Para mantener un cambio, lo revisas y lo escribes en el archivo Java de donde provino. Esta página describe ese paso.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ escribe en tu proyecto
Mantén tu trabajo bajo control de versiones. Lee la diferencia antes de aplicarla, y revísala nuevamente antes de hacer el commit.
:::

## Cambios pendientes {#pending-changes}

Cada propiedad que cambias se registra como un cambio pendiente, y craftforJ muestra cuántos están esperando. Los cambios pendientes sobreviven a una recarga de página y a un cambio de ruta, porque craftforJ los reaplica cuando se reconstruyen tus componentes.

## Revisión y aplicación {#reviewing-and-applying}

Presiona <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> para abrir la revisión. Los cambios se agrupan por el archivo en el que se introducirán. Cada uno muestra la propiedad con su valor antiguo y nuevo, y se expande en la diferencia del archivo. Si un cambio reemplaza un valor calculado por uno fijo, craftforJ te advierte y nombra la expresión que está a punto de reemplazar. Nada se escribe hasta que apliques. Antes de hacerlo, puedes revertir o descartar cada cambio por separado.

![La revisión con cambios agrupados por archivo y uno expandido a su diferencia](/img/craftforj/source-changes/review.png#rounded-border)

## Elegir dónde se escribe un cambio {#choosing-where-a-change-is-written}

Dónde se escribe un cambio determina hasta dónde llega. Cuando un componente se construye directamente en una vista, el cambio va a esa vista. Cuando se construye dentro de una clase reutilizable, tienes dos opciones:

- **El uso** - el lugar donde se utiliza el componente, que solo cambia la pantalla frente a ti. Este es el valor predeterminado.
- **La definición** - el lugar donde se construye el componente, que cambia cada pantalla que lo utiliza.

Cada cambio pendiente muestra cuál de los dos se aplica y te permite alternar entre ellos. Algunas propiedades solo se pueden escribir en la definición, porque el componente las establece por sí mismo en lugar de aceptarlas del llamador. craftforJ marca esas antes de que apliques.

## Después de aplicar {#after-you-apply}

Escribir en Java causa que tu aplicación se reconstruya y reinicie. craftforJ informa sobre el reinicio, espera a que ocurra y se reconecta con tu selección y tus cambios pendientes restantes intactos. Los cambios aplicados salen de la lista de pendientes una vez que están en tus archivos.

Este es el único punto donde tu configuración de recarga importa. craftforJ no necesita recarga en vivo para funcionar, porque todo lo que cambias mientras inspeccionas tiene efecto en la aplicación en ejecución de inmediato, sin que se requiera reconstrucción. Escribir en la fuente es diferente: cambia un archivo del cual se construyó tu aplicación, por lo que la aplicación debe reconstruirse antes de que el cambio provenga de tu código en lugar de craftforJ. Con [recarga en vivo](/docs/configuration/deploy-reload/overview) configurada, eso ocurre por sí solo. Sin ella, reinicia la aplicación tú mismo.

## Desactivarlo {#turning-it-off}

Puedes desactivar la escritura en Java para una aplicación en la configuración de craftforJ, o eliminarla por completo con la propiedad [`source-changes`](/docs/craftforj/configuration#feature-flags). Con cualquiera de las dos desactivadas, la edición de propiedades sigue funcionando pero permanece en vivo.
