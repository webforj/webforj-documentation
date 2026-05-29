---
title: File Save
sidebar_position: 15
_i18n_hash: 7cad72847c86a30f8ad6000a283a51c2
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` es un cuadro de diálogo modal diseñado para permitir a los usuarios guardar un archivo en una ubicación específica en el sistema de archivos del servidor. El diálogo bloquea la ejecución de la aplicación hasta que el usuario proporciona un nombre de archivo y confirma la acción o cancela el diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `FileSaveDialog` proporciona un método simplificado para guardar archivos en el sistema de archivos, ofreciendo opciones configurables por el usuario para el nombrado de archivos y el manejo de archivos existentes.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Resultado {#result}

El `FileSaveDialog` devuelve la ruta seleccionada como una cadena. Si el usuario cancela el diálogo, el resultado será `null`.

:::important Propósito del diálogo
Este diálogo no causa que se guarden archivos, sino que devuelve el nombre de archivo que el usuario ha seleccionado.
:::

:::info
La cadena resultante se devuelve desde el método `show()` o el método equivalente `OptionDialog` como se demuestra a continuación.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Guarde su archivo", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Archivo guardado en: " + path, "Ruta seleccionada");
} else {
  OptionDialog.showMessageDialog("No se ha seleccionado ninguna ruta", "Ruta seleccionada",
      MessageDialog.MessageType.ERROR);
}
```

## Acción existente {#exists-action}

El `FileSaveDialog` proporciona un comportamiento configurable cuando un archivo con el nombre especificado ya existe:

* **ACCEPT_WITHOUT_ACTION**: La selección se acepta sin ninguna acción adicional del usuario.
* **ERROR_DIALOGUE**: Se presenta un diálogo de error al usuario; la selección no está permitida.
* **CONFIRMATION_DIALOGUE**: Se presenta un diálogo al usuario solicitando confirmación. Este es el predeterminado.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Modo de selección {#selection-mode}

El `FileSaveDialog` soporta diferentes modos de selección, permitiéndole adaptar el método de selección a sus necesidades específicas:

1. **FILES**: Permite la selección de archivos solamente.
2. **DIRECTORIES**: Permite la selección de directorios solamente.
3. **FILES_AND_DIRECTORIES**: Permite la selección tanto de archivos como de directorios.

## Ruta inicial {#initial-path}

Especifique el directorio donde se abrirá el diálogo utilizando la ruta inicial. Esto ayuda a los usuarios a comenzar en un directorio lógico para su operación de guardado.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Restricción {#restriction}

Puede restringir el diálogo a un directorio específico, impidiendo que los usuarios naveguen fuera de él utilizando el método `setRestricted(boolean restricted)`.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Nombre de archivo {#filename}

Establezca un nombre de archivo predeterminado para la operación de guardado para guiar a los usuarios y minimizar errores.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileSaveI18n`. Esto asegura que el diálogo pueda adaptarse a diversos requerimientos de localización o personalización.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Filtros {#filters}

El `FileSaveDialog` permite establecer filtros para limitar los tipos de archivos que se pueden guardar utilizando el método `setFilters(List<FileSaveFilter> filters)`.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Filtros personalizados {#custom-filters}

Puede habilitar filtros personalizados para permitir que los usuarios definan sus propios filtros de archivos utilizando el método `setCustomFilters(boolean customFilters)`. Los filtros se guardan en el almacenamiento local de forma predeterminada y se restauran en invocaciones posteriores del diálogo.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Mejores prácticas {#best-practices}

* **Nombres de archivos predefinidos**: Proporcione un nombre de archivo predeterminado lógico cuando sea aplicable.
* **Confirmar sobrescrituras**: Utilice `CONFIRMATION_DIALOGUE` para `ExistsAction` para evitar sobrescrituras accidentales.
* **Ruta inicial intuitiva**: Establezca una ruta inicial que se alinee con las expectativas del usuario.
* **Internacionalización**: Personalice el texto del diálogo para mejorar la usabilidad para audiencias internacionales.
* **Filtros de tipo de archivo**: Aproveche los filtros para restringir los tipos de archivos y guiar a los usuarios hacia extensiones de archivos válidas.
