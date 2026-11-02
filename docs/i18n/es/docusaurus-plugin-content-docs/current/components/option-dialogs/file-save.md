---
title: File Save
sidebar_position: 15
description: >-
  Prompt users for a save location with the FileSaveDialog, configuring initial
  path, default filename, and overwrite behavior.
_i18n_hash: 861257322ca233cfa9e0eed5e4d3aa95
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` es un diálogo modal diseñado para permitir a los usuarios guardar un archivo en una ubicación especificada en el sistema de archivos del servidor. El diálogo bloquea la ejecución de la aplicación hasta que el usuario proporciona un nombre de archivo y confirma la acción o cancela el diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `FileSaveDialog` proporciona un método simplificado para guardar archivos en el sistema de archivos, ofreciendo opciones configurables por el usuario para el nombramiento de archivos y el manejo de archivos existentes.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Resultado {#result}

El `FileSaveDialog` devuelve la ruta seleccionada como una cadena. Si el usuario cancela el diálogo, el resultado será `null`.

:::important Propósito del Diálogo
Este diálogo no causa realmente que se guarden archivos, sino que devuelve el nombre de archivo que el usuario ha seleccionado.
:::

:::info
La cadena resultante se devuelve desde el método `show()` o el método equivalente de `OptionDialog` como se demuestra a continuación.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Guarde su archivo", "/home/user/documents", "reporte.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Ruta seleccionada: " + result, "Ruta Seleccionada");
} else {
  OptionDialog.showMessageDialog("No se ha seleccionado ninguna ruta", "Ruta Seleccionada",
      MessageDialog.MessageType.ERROR);
}
```

## Acción de existencia {#exists-action}

El `FileSaveDialog` proporciona un comportamiento configurable cuando un archivo con el nombre especificado ya existe:

* **ACCEPT_WITHOUT_ACTION**: La selección es aceptada sin acción adicional del usuario.
* **ERROR_DIALOGUE**: Se presenta al usuario un diálogo de error; la selección no está permitida.
* **CONFIRMATION_DIALOGUE**: Se presenta al usuario un diálogo solicitando confirmación. Este es el predeterminado.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "reporte.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Modo de selección {#selection-mode}

El `FileSaveDialog` admite diferentes modos de selección, permitiéndole personalizar el método de selección según sus necesidades específicas:

1. **FILES**: Permite la selección de archivos solamente.
2. **DIRECTORIES**: Permite la selección de directorios solamente.
3. **FILES_AND_DIRECTORIES**: Permite la selección de archivos y directorios.

## Ruta inicial {#initial-path}

Especifique el directorio donde se abrirá el diálogo utilizando la ruta inicial. Esto ayuda a los usuarios a comenzar en un directorio lógico para su operación de guardado.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "reporte.xls");
String result = dialog.show();
```

## Restricción {#restriction}

Puede restringir el diálogo a un directorio específico, evitando que los usuarios naveguen fuera de él utilizando el método `setRestricted(boolean restricted)`.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "reporte.xls");
dialog.setRestricted(true);
dialog.show();
```

## Nombre de archivo {#filename}

Establezca un nombre de archivo predeterminado para la operación de guardado para guiar a los usuarios y minimizar errores.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo");
dialog.setName("reporte.xls");
String result = dialog.show();
```

## Internacionalización (i18n) {#internationalization-i18n}

Utilice `FileChooserI18n` para personalizar los títulos, descripciones, etiquetas y mensajes del diálogo para diferentes locales o requisitos de la aplicación.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Elegir");
i18n.setCancel("Cancelar");
dialog.setI18n(i18n);
```

## Filtros {#filters}

El `FileSaveDialog` le permite establecer filtros para limitar los tipos de archivos que se pueden guardar utilizando el método `setFilters(List<FileChooserFilter> filters)`.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Filtros personalizados {#custom-filters}

Puede habilitar filtros personalizados para permitir a los usuarios definir sus propios filtros de archivos utilizando el método `setCustomFilters(boolean customFilters)`. Los filtros se guardan en el almacenamiento local por defecto y se restauran en invocaciones posteriores del diálogo.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Mejores prácticas {#best-practices}

* **Nombres de Archivo Predefinidos**: Proporcione un nombre de archivo predeterminado lógico cuando sea aplicable.
* **Confirmar Sobreescrituras**: Use `CONFIRMATION_DIALOGUE` para `ExistsAction` para evitar sobreescrituras accidentales.
* **Ruta Inicial Intuitiva**: Establezca una ruta inicial que se alinee con las expectativas del usuario.
* **Internacionalización**: Personalice el texto del diálogo para mejorar la usabilidad para audiencias internacionales.
* **Filtros de Tipo de Archivo**: Aproveche los filtros para restringir tipos de archivos y guiar a los usuarios hacia extensiones de archivos válidas.
