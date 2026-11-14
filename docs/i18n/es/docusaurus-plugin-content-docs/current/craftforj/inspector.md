---
title: Inspección de componentes
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 91fbaf620dc8967a50c4a5ce6b455841
---
El Inspector muestra el árbol de componentes que ha creado tu código Java. Un `Composite` aparece como la clase que escribiste y contiene los elementos secundarios que le proporcionaste, en el orden en que webforJ los contiene, por lo que la estructura en craftforJ coincide con la estructura de tu código fuente.

![El árbol de componentes con un componente seleccionado y resaltado en la aplicación en ejecución](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Seleccionar un componente {#selecting-a-component}

Para seleccionar un componente de la página, pulsa <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> y haz clic en él. craftforJ selecciona el nodo correspondiente en el árbol. Al pasar el cursor por un nodo del árbol, se resalta el componente correspondiente en la página, de modo que puedes moverte entre la pantalla y el árbol en cualquiera de las dos direcciones.

<VideoPlayer video="craftforj/pick-mode" width={1920} height={992} title="Pick mode" />

Para buscar en el árbol, pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Si escribes un término entre barras, se interpreta como una expresión regular. Al hacer clic con el botón derecho en un nodo, se muestran las acciones disponibles para él. Puedes abrir su código fuente o enviarlo al [asistente](/docs/craftforj/ai).

## Consultar y cambiar propiedades {#reading-and-changing-properties}

Al seleccionar un componente, sus propiedades aparecen en la barra lateral, agrupadas según lo que afectan. Las propiedades disponibles dependen del componente, y algunas son de solo lectura. Las propiedades que no se representan bien como texto sin formato tienen un editor adecuado para su valor. Los cambios se aplican de inmediato en la aplicación en ejecución.

:::info Las ediciones en vivo no modifican tus archivos
Editar una propiedad solo cambia la aplicación que tienes delante. Incorporar ese cambio al código fuente es un paso aparte que debes realizar deliberadamente, tal como se describe en [Escribir cambios en el código fuente](/docs/craftforj/source-changes).
:::

<VideoPlayer video="craftforj/property-edit" width={1920} height={1000} title="Property edit" />

## Ver el código fuente de un componente {#viewing-the-source-of-a-component}

Puedes rastrear cualquier componente hasta el código Java que lo creó. De forma predeterminada, el código fuente se abre en craftforJ como solo lectura, en la línea que creó el componente. Puedes configurar craftforJ para que lo abra en tu editor, en esa misma línea. Si no se puede rastrear un componente hasta una línea, craftforJ lo indica en lugar de abrir un visor vacío.

![El visor de código fuente situado en la línea que creó el componente seleccionado](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Eliminar un componente <DocChip chip='since' label='26.03' /> {#removing-a-component}

Al hacer clic con el botón derecho en un componente del árbol y elegir **Eliminar componente**, se ofrecen dos formas de quitarlo. Una lo elimina de la aplicación que tienes delante y la otra, del proyecto.

<VideoPlayer video="craftforj/delete-components" width={1872} height={1080} title="Delete components" />

### Eliminar solo del navegador {#removing-from-the-browser-only}

**Eliminar solo del navegador** quita el componente de la aplicación en ejecución y deja intacto el código fuente Java. Úsalo para ver cómo queda un diseño sin un componente determinado antes de decidirte.

La eliminación es temporal. El componente vuelve a aparecer la próxima vez que la aplicación genere esa vista a partir del código fuente, por ejemplo, después de recargar la página.

### Eliminar del código fuente {#removing-from-source}

**Eliminar del código fuente** edita el archivo Java que creó el componente, por lo que este seguirá eliminado después de que se vuelva a generar la aplicación. La eliminación se escribe de inmediato en lugar de añadirse a los [cambios pendientes](/docs/craftforj/source-changes#pending-changes), así que no hay ningún paso de revisión antes de que se aplique al archivo.

Al escribir código Java, la aplicación se vuelve a generar y se reinicia, igual que al [aplicar cambios](/docs/craftforj/source-changes#after-you-apply). Si has configurado la [recarga en vivo](/docs/configuration/deploy-reload/overview), esto ocurre automáticamente. Si no, reinicia la aplicación manualmente.

Por ejemplo, considera un formulario con los botones **Guardar** y **Cancelar**, donde **Guardar** tiene un tema y un listener de clic:

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

Al seleccionar **Guardar** y elegir **Eliminar del código fuente**, el resultado es el siguiente. Se eliminan la declaración, el tema, el listener y la posición del botón en la llamada a `add()`, junto con la importación de `ButtonTheme`, que ya no se usa. Después de que se vuelva a generar la aplicación, **Cancelar** seguirá ahí:

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
- Se limpia el código que solo existía para crear, configurar y adjuntar el componente. Esto incluye los campos y las importaciones que no se usen en ningún otro lugar.
- El código que todavía se necesite en otra parte se conserva. Si otra parte de la clase sigue usando el componente, por ejemplo, el listener de otro componente, craftforJ solo lo desvincula y deja el código que lo crea y configura.
- Al eliminar una instancia de un componente reutilizable, como un `Composite` que hayas escrito, se elimina ese uso, pero no se elimina ni se modifica la clase que lo define.

craftforJ solo escribe el archivo si el resultado sigue compilando. Nunca se eliminan archivos.

### Cuándo no está disponible la eliminación {#when-removal-isnt-available}

**Eliminar del código fuente** solo se ofrece si se permite escribir código Java tanto en la [configuración](/docs/craftforj/configuration#feature-flags) de la aplicación como en el ajuste **Cambios en el código fuente Java** de craftforJ. No se ofrece para componentes creados en Kotlin ni para componentes que craftforJ no pueda rastrear hasta una línea de código fuente.

Hay componentes que no se pueden eliminar del código fuente:

- Las rutas, que el enrutador coloca según su `@Route`.
- El marco.
- Los componentes que otro componente crea como parte de sí mismo. En su lugar, elimina el componente externo.

craftforJ también puede rechazar una eliminación si no encuentra el código fuente del componente o no puede determinar qué código eliminar. Por ejemplo, si una línea crea más de un componente del mismo tipo, la línea por sí sola no indica cuál seleccionaste.

Si se rechaza una eliminación, los archivos quedan tal como estaban y se informa el motivo:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Deshacer una eliminación {#undoing-a-removal}

Para restaurar un componente que eliminaste del código fuente, pulsa <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> en craftforJ. Esto restaura el archivo tal como estaba antes de la eliminación. Si has editado el archivo en tu propio editor desde entonces, la acción de deshacer se bloquea en lugar de sobrescribir tus cambios. Consulta [Deshacer y rehacer](/docs/craftforj/source-changes#undoing-and-redoing) para saber cómo funciona la acción de deshacer y qué hacer si se bloquea.
