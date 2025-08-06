---
sidebar_position: 10
title: File Chooser
_i18n_hash: d0efdadb8ec1e44cfab2fb26f95efa0d
---
# Diálogo de Selección de Archivos

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` es un diálogo modal diseñado para permitir al usuario seleccionar un archivo o un directorio del sistema de archivos del servidor. El diálogo bloquea la ejecución de la aplicación hasta que el usuario haga una selección o cierre el diálogo.

```java
OptionDialog.showFileChooserDialog("Seleccione un archivo");
```

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
    "Seleccione un archivo", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("Usted seleccionó: " + result, "Selección Realizada");
} else {
    OptionDialog.showMessageDialog("No se realizó ninguna selección", "Selección Cancelada");
}
```

## Modo de selección {#selection-mode}

El `FileChooserDialog` admite diferentes modos de selección, lo que permite adaptar el método de selección a sus necesidades específicas:

1. **FILES**: Permite la selección solo de archivos.
2. **DIRECTORIES**: Permite la selección solo de directorios.
3. **FILES_AND_DIRECTORIES**: Permite la selección de archivos y directorios.

## Ruta inicial {#initial-path}

El `FileChooserDialog` permite especificar una ruta inicial a la que se abrirá el diálogo cuando se muestre. Esto puede proporcionar a los usuarios un punto de partida para su selección de archivos.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccione un archivo", "/home/user");
String result = dialog.show();
```

## Restricción {#restriction}

Puede restringir el diálogo a un directorio específico, evitando que los usuarios naveguen fuera de él utilizando el método `setRestricted(boolean restricted)`.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccione un archivo", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filtros {#filters}

Cuando el modo de selección es `FILES`, el `FileChooserDialog` le permite establecer filtros para limitar los tipos de archivos que se enumeran. Puede configurar filtros utilizando el método `setFilters(List<FileChooserFilter> filters)`.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Filtros personalizados {#custom-filters}

Puede permitir que los usuarios agreguen filtros personalizados habilitando la función de filtros personalizados mediante el método `setCustomFilters(boolean customFilters)`. 
Los filtros personalizados se guardarán en el almacenamiento local del navegador por defecto y se restaurarán cuando se muestre nuevamente el diálogo.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccione un archivo", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileChooserI18n`. Esta flexibilidad le permite adaptar la interfaz del diálogo para cumplir con requisitos específicos de localización o preferencias de personalización.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Seleccione un archivo", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Seleccionar");
i18n.setCancel("Cancelar");
dialog.setI18n(i18n);
```

## Mejores prácticas {#best-practices}

1. **Prompts Claros y Concisos**: Asegúrese de que el mensaje de aviso explique claramente qué se le pide al usuario que seleccione.
2. **Modos de Selección Apropiados**: Elija modos de selección que coincidan con la acción requerida del usuario para garantizar selecciones precisas y relevantes.
3. **Rutas Iniciales Lógicas**: Establezca rutas iniciales que proporcionen a los usuarios un punto de partida útil para su selección.
4. **Restringir la Navegación de Directorios**: Restringa el diálogo a un directorio específico cuando sea necesario para evitar que los usuarios naveguen a áreas no autorizadas.
