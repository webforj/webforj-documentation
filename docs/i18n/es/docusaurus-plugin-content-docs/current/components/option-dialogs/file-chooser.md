---
title: File Chooser
sidebar_position: 10
_i18n_hash: c8d1ebc420bc1e1749c5c98a9fd3284c
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` es un diálogo modal diseñado para permitir al usuario seleccionar un archivo o un directorio del sistema de archivos del servidor. El diálogo bloquea la ejecución de la aplicación hasta que el usuario haga una selección o cierre el diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `FileChooserDialog` proporciona una forma de seleccionar archivos o directorios del sistema de archivos, permitiendo a los usuarios elegir directorios para guardar datos o realizar operaciones con archivos.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Resultado {#result}

El `FileChooserDialog` devuelve el archivo o directorio seleccionado como una cadena. Si el usuario cierra el diálogo sin hacer una selección, el resultado será `null`.

:::info
La cadena resultante será devuelta desde el método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación. 
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
  "Seleccionar un archivo", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
  OptionDialog.showMessageDialog("Usted seleccionó: " + result, "Selección Realizada");
} else {
  OptionDialog.showMessageDialog("No se hizo ninguna selección", "Selección Cancelada");
}
```

## Modo de selección {#selection-mode}

El `FileChooserDialog` admite diferentes modos de selección, permitiéndole personalizar el método de selección según sus necesidades específicas:

1. **FILES**: Permite la selección de archivos solamente.
2. **DIRECTORIES**: Permite la selección de directorios solamente.
3. **FILES_AND_DIRECTORIES**: Permite la selección tanto de archivos como de directorios.

## Ruta inicial {#initial-path}

El `FileChooserDialog` le permite especificar una ruta inicial a la que se abrirá el diálogo al mostrarse. Esto puede proporcionar a los usuarios un punto de partida para su selección de archivos.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccionar un archivo", "/home/user");
String result = dialog.show();
```

## Restricción {#restriction}

Puede restringir el diálogo a un directorio específico, impidiendo que los usuarios naveguen fuera de él utilizando el método `setRestricted(boolean restricted)`.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccionar un archivo", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filtros {#filters}

Cuando el modo de selección es `FILES`, el `FileChooserDialog` le permite establecer filtros para limitar los tipos de archivos que se listan. Puede configurar filtros utilizando el método `setFilters(List<FileChooserFilter> filters)`.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Filtros personalizados {#custom-filters}

Puede permitir a los usuarios agregar filtros personalizados habilitando la función de filtros personalizados mediante el método `setCustomFilters(boolean customFilters)`. 
Los filtros personalizados se guardarán en el almacenamiento local del navegador por defecto y se restaurarán cuando el diálogo se muestre nuevamente.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccionar un archivo", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileChooserI18n`. Esta flexibilidad le permite adaptar la interfaz del diálogo para cumplir con requisitos de localización específicos o preferencias de personalización.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Mejores prácticas {#best-practices}

1. **Indicaciones claras y concisas**: Asegúrese de que el mensaje de indicación explique claramente qué se le pide al usuario que seleccione.
2. **Modos de selección apropiados**: Elija modos de selección que coincidan con la acción requerida por el usuario para garantizar selecciones precisas y relevantes.
3. **Rutas iniciales lógicas**: Establezca rutas iniciales que ofrezcan a los usuarios un punto de partida útil para su selección.
4. **Restringir la navegación en directorios**: Restringa el diálogo a un directorio específico cuando sea necesario para evitar que los usuarios naveguen a áreas no autorizadas.
