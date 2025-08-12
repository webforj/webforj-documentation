---
sidebar_position: 15
title: File Save
_i18n_hash: 9f5ecfb61386cfa8c4eb3c31305b1838
---
# Diálogo de Guardado de Archivos

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` es un cuadro de diálogo modal diseñado para permitir a los usuarios guardar un archivo en una ubicación específica en el sistema de archivos del servidor. El diálogo bloquea la ejecución de la aplicación hasta que el usuario proporciona un nombre de archivo y confirma la acción o cancela el diálogo.

```java
OptionDialog.showFileSaveDialog("Guarde su archivo");
```

## Usos {#usages}

El `FileSaveDialog` proporciona un método simplificado para guardar archivos en el sistema de archivos, ofreciendo opciones configurables por el usuario para el nombrado de archivos y el manejo de archivos existentes.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Resultado {#result}

El `FileSaveDialog` devuelve la ruta seleccionada como una cadena. Si el usuario cancela el diálogo, el resultado será `null`.

:::important Propósito del Diálogo
Este diálogo no causa realmente que se guarden archivos, sino que devuelve el nombre de archivo que el usuario ha seleccionado.
:::

:::info
La cadena resultante se devuelve desde el método `show()` o el equivalente método `OptionDialog` como se demuestra a continuación.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Guarde su archivo", "/home/user/documents", "informe.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Archivo guardado en: " + path, "Ruta Seleccionada");
} else {
  OptionDialog.showMessageDialog("No se ha seleccionado ninguna ruta", "Ruta Seleccionada",
      MessageDialog.MessageType.ERROR);
}
```

## Acción de Existencia {#exists-action}

El `FileSaveDialog` proporciona un comportamiento configurable cuando un archivo con el nombre especificado ya existe:

* **ACCEPT_WITHOUT_ACTION**: La selección se acepta sin acción adicional del usuario.
* **ERROR_DIALOGUE**: Se presenta un cuadro de diálogo de error al usuario; la selección no está permitida.
* **CONFIRMATION_DIALOGUE**: Se presenta un cuadro de diálogo al usuario solicitando confirmación. Esta es la opción predeterminada.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "informe.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Modo de Selección {#selection-mode}

El `FileSaveDialog` admite diferentes modos de selección, lo que le permite adaptar el método de selección a sus necesidades específicas:

1. **ARCHIVOS**: Permite la selección solo de archivos.
2. **DIRECTORIOS**: Permite la selección solo de directorios.
3. **ARCHIVOS_Y_DIRECTORIOS**: Permite la selección de archivos y directorios.

## Ruta Inicial {#initial-path}

Especifique el directorio donde se abrirá el diálogo utilizando la ruta inicial. Esto ayuda a los usuarios a comenzar en un directorio lógico para su operación de guardado.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "informe.xls");
String result = dialog.show();
```

## Restricción {#restriction}

Puede restringir el diálogo a un directorio específico, impidiendo que los usuarios naveguen fuera de él utilizando el método `setRestricted(boolean restricted)`.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Guarde su archivo", "/home/user/documents", "informe.xls");
dialog.setRestricted(true);
dialog.show();
```

## Nombre de Archivo {#filename}

Establezca un nombre de archivo predeterminado para la operación de guardado para guiar a los usuarios y minimizar errores.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo");
dialog.setName("informe.xls");
String result = dialog.show();
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileSaveI18n`. Esto asegura que el diálogo se pueda adaptar a varios requisitos de localización o personalización.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Elegir");
i18n.setCancel("Cancelar");
dialog.setI18n(i18n);
```

## Filtros {#filters}

El `FileSaveDialog` le permite establecer filtros para limitar los tipos de archivos que se pueden guardar utilizando el método `setFilters(List<FileSaveFilter> filters)`.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Filtros Personalizados {#custom-filters}

Puede habilitar filtros personalizados para permitir a los usuarios definir sus propios filtros de archivos utilizando el método `setCustomFilters(boolean customFilters)`. Los filtros se guardan en el almacenamiento local de forma predeterminada y se restauran en invocaciones posteriores del diálogo.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Guarde su archivo", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Mejores Prácticas {#best-practices}

* **Nombres de Archivos Predefinidos**: Proporcione un nombre de archivo predeterminado lógico cuando sea aplicable.
* **Confirmar Sobrescrituras**: Use `CONFIRMATION_DIALOGUE` para `ExistsAction` para prevenir sobrescrituras accidentales.
* **Ruta Inicial Intuitiva**: Establezca una ruta inicial que se alinee con las expectativas del usuario.
* **Internacionalización**: Personalice el texto del diálogo para mejorar la usabilidad para audiencias internacionales.
* **Filtros de Tipo de Archivo**: Aproveche los filtros para restringir los tipos de archivos y guiar a los usuarios hacia extensiones de archivo válidas.
