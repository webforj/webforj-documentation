---
title: File Chooser
sidebar_position: 10
description: >-
  Open a blocking FileChooserDialog to let users pick files or directories from
  the server, with selection modes and initial paths.
_i18n_hash: c86dfab4207241cab3bb28da3e1236ab
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` es un cuadro de diálogo modal diseñado para permitir al usuario seleccionar un archivo o un directorio del sistema de archivos del servidor. El cuadro de diálogo bloquea la ejecución de la aplicación hasta que el usuario realiza una selección o cierra el cuadro de diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `FileChooserDialog` proporciona una forma de seleccionar archivos o directorios del sistema de archivos, permitiendo a los usuarios elegir directorios para guardar datos o realizar operaciones con archivos.

<ComponentDemo
path='/webforj/filechooserdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java']}
height='600px'
/>

## Resultado {#result}

El `FileChooserDialog` devuelve el archivo o directorio seleccionado como una cadena. Si el usuario cierra el cuadro de diálogo sin hacer una selección, el resultado será `null`.

:::info
La cadena resultante se devolverá del método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación.
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
  "Seleccione un archivo", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
  OptionDialog.showMessageDialog("Has seleccionado: " + result, "Selección realizada");
} else {
  OptionDialog.showMessageDialog("No se ha realizado ninguna selección", "Selección cancelada");
}
```

## Modo de selección {#selection-mode}

El `FileChooserDialog` admite diferentes modos de selección, permitiéndote adaptar el método de selección a tus necesidades específicas:

1. **FILES**: Permite la selección de archivos únicamente.
2. **DIRECTORIES**: Permite la selección de directorios únicamente.
3. **FILES_AND_DIRECTORIES**: Permite la selección de archivos y directorios.

## Ruta inicial {#initial-path}

El `FileChooserDialog` te permite especificar una ruta inicial a la que el cuadro de diálogo se abrirá cuando se muestre. Esto puede proporcionar a los usuarios un punto de partida para su selección de archivos.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccione un archivo", "/home/user");
String result = dialog.show();
```

## Restricción {#restriction}

Puedes restringir el cuadro de diálogo a un directorio específico, impidiendo que los usuarios naveguen fuera de él utilizando el método `setRestricted(boolean restricted)`.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccione un archivo", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filtros {#filters}

Cuando el modo de selección es `FILES`, el `FileChooserDialog` te permite establecer filtros para limitar los tipos de archivos que se enumeran. Puedes configurar filtros usando el método `setFilters(List<FileChooserFilter> filters)`.

<ComponentDemo
path='/webforj/filechooserdialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java']}
height='600px'
/>

### Filtros personalizados {#custom-filters}

Puedes permitir que los usuarios agreguen filtros personalizados habilitando la función de filtros personalizados mediante el método `setCustomFilters(boolean customFilters)`. Los filtros personalizados se guardarán en el almacenamiento local del navegador por defecto y se restaurarán cuando se muestre nuevamente el cuadro de diálogo.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccione un archivo", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileChooserI18n`. Esta flexibilidad te permite adaptar la interfaz del cuadro de diálogo para cumplir con los requisitos específicos de localización o preferencias de personalización.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Mejores prácticas {#best-practices}

1. **Indicaciones claras y concisas**: Asegúrate de que el mensaje de aviso explique claramente lo que se le pide al usuario que seleccione.
2. **Modos de selección apropiados**: Elige modos de selección que coincidan con la acción que el usuario debe realizar para garantizar selecciones precisas y relevantes.
3. **Rutas iniciales lógicas**: Establece rutas iniciales que proporcionen a los usuarios un punto de partida útil para su selección.
4. **Restringir la navegación de directorios**: Restringe el cuadro de diálogo a un directorio específico cuando sea necesario para evitar que los usuarios naveguen a áreas no autorizadas.
