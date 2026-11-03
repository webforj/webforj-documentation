---
title: Inspección de componentes
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 585425968807283ea392911908fbd030
---
El Inspector muestra el árbol de componentes que creó tu código Java. Un `Composite` aparece como la clase que escribiste y contiene los elementos secundarios que le proporcionaste, en el orden en que webforJ los mantiene. Así, la estructura de craftforJ coincide con la de tu código fuente.

![El árbol de componentes con un componente seleccionado y resaltado en la aplicación en ejecución](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Seleccionar un componente {#selecting-a-component}

Para seleccionar un componente de la página, pulsa <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> y haz clic en él. craftforJ selecciona el nodo correspondiente en el árbol. Al pasar el cursor sobre un nodo del árbol, ocurre lo contrario: se resalta ese componente en la página. Así, puedes moverte entre la pantalla y el árbol en cualquiera de las dos direcciones.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Para buscar en el árbol, pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Si encierras un término entre barras, se tratará como una expresión regular. Al hacer clic con el botón derecho en un nodo, se muestran las acciones disponibles. Puedes abrir su código fuente o enviarlo al [asistente](/docs/craftforj/ai).

## Consultar y cambiar propiedades {#reading-and-changing-properties}

Al seleccionar un componente, la barra lateral muestra sus propiedades agrupadas según lo que afectan. Las propiedades disponibles dependen del componente, y algunas son de solo lectura. Las propiedades cuyo valor no se representa bien como texto sin formato tienen un editor adecuado para ese valor. Los cambios en los valores surten efecto de inmediato en la aplicación en ejecución.

:::info Las ediciones en vivo no modifican tus archivos
Editar una propiedad solo cambia la aplicación que tienes delante. Para incorporar el cambio al código fuente, debes hacerlo en un paso aparte y de forma deliberada, tal como se describe en [Escribir los cambios en el código fuente](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Ver el código fuente de un componente {#viewing-the-source-of-a-component}

Puedes rastrear cualquier componente hasta el código Java que lo creó. De forma predeterminada, el código fuente se abre en craftforJ en modo de solo lectura y se sitúa en la línea que creó el componente. También puedes configurar craftforJ para que lo abra en tu editor, en esa misma línea. Si no es posible rastrear un componente hasta una línea, craftforJ lo indica en lugar de abrir un visor vacío.

![El visor de código fuente situado en la línea que creó el componente seleccionado](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Eliminar un componente <DocChip chip='since' label='26.03' /> {#removing-a-component}

Al hacer clic con el botón derecho en un componente del árbol y elegir **Eliminar componente**, se ofrecen dos formas de quitarlo. Una lo elimina de la aplicación que tienes delante y la otra lo elimina del proyecto.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/delete-components.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

### Eliminar solo del navegador {#removing-from-the-browser-only}

**Eliminar solo del navegador** quita el componente de la aplicación en ejecución y deja intacto el código fuente Java. Úsalo para ver cómo queda un diseño sin un componente en particular antes de tomar una decisión.

La eliminación es temporal. El componente vuelve a aparecer la próxima vez que la aplicación genere esa vista a partir del código fuente, por ejemplo, después de recargar la página.

### Eliminar del código fuente {#removing-from-source}

**Eliminar del código fuente** edita el archivo Java que creó el componente, por lo que este seguirá eliminado después de que se vuelva a generar la aplicación. La eliminación se escribe de inmediato, en lugar de añadirse a los [cambios pendientes](/docs/craftforj/source-changes#pending-changes), así que no hay una etapa de revisión antes de que llegue al archivo.

Escribir código Java hace que la aplicación se vuelva a generar y se reinicie, igual que al [aplicar cambios](/docs/craftforj/source-changes#after-you-apply). Si has configurado la [recarga en vivo](/docs/configuration/deploy-reload/overview), esto ocurre automáticamente. De lo contrario, reinicia la aplicación manualmente.

Por ejemplo, considera un formulario con los botones **Save** y **Cancel**, donde **Save** tiene un tema y un listener de clics:

```java
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}
```

Si seleccionas **Save** y eliges **Eliminar del código fuente**, el resultado es el siguiente. Se eliminan la declaración, el tema, el listener y la posición del botón en la llamada a `add()`, junto con la importación de `ButtonTheme`, que ya no se usa. Después de que se vuelva a generar la aplicación, **Cancel** seguirá ahí:

```java
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}
```

La eliminación abarca el componente y nada más:

- Al eliminar un contenedor, también se eliminan los componentes que contiene.
- Se limpia el código que solo existía para crear, configurar y añadir el componente. Esto incluye los campos y las importaciones que ya no se usan.
- El código que sigue siendo necesario en otros lugares se conserva. Si otra parte de la clase todavía utiliza el componente, por ejemplo, el listener de otro componente, craftforJ solo lo desvincula y deja el código que lo crea y configura.
- Al eliminar una instancia de un componente reutilizable, como un `Composite` que hayas escrito, solo se elimina ese uso. La clase que lo define no se elimina ni se modifica.

craftforJ solo escribe el archivo si el resultado sigue compilando. Nunca se eliminan archivos.

### Cuándo no está disponible la eliminación {#when-removal-isnt-available}

**Eliminar del código fuente** solo se ofrece cuando se permite escribir código Java tanto en la [configuración](/docs/craftforj/configuration#feature-flags) de la aplicación como en el ajuste **Java source changes** de craftforJ. Esta opción no se ofrece para componentes creados en Kotlin ni para componentes que craftforJ no puede rastrear hasta una línea del código fuente.

Algunos componentes no se pueden eliminar del código fuente:

- Las rutas, que el enrutador coloca según su `@Route`
- El marco
- Los componentes que otro componente crea como parte de sí mismo. En su lugar, elimina el componente externo.

craftforJ también puede rechazar una eliminación si no encuentra el código fuente del componente o no puede determinar qué código debe eliminar. Por ejemplo, si una línea crea más de un componente del mismo tipo, la línea por sí sola no indica cuál seleccionaste.

Si se rechaza una eliminación, los archivos permanecen intactos y se indica el motivo:

```
No se pudo eliminar FlexLayout del código fuente: FlexLayout es una parte que ContactCard crea para sí mismo; elimina eso en su lugar
```

### Deshacer una eliminación {#undoing-a-removal}

Para restaurar un componente que eliminaste del código fuente, pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> en craftforJ. Se restaura el archivo tal como estaba antes de la eliminación. Si desde entonces editaste el archivo en tu propio editor, la operación de deshacer se bloquea en lugar de sobrescribir tus cambios. Consulta [Deshacer y rehacer](/docs/craftforj/source-changes#undoing-and-redoing) para saber cómo funciona la operación y qué hacer si se bloquea.
