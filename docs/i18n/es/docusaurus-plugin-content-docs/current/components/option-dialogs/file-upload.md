---
title: File Upload
sidebar_position: 20
description: >-
  Capture client uploads with the FileUploadDialog, returning an UploadedFile
  that you can filter, move, and process server-side.
_i18n_hash: 411ef7de8459154002fa2a620f073b0d
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Un `FileUploadDialog` es un cuadro de diálogo modal diseñado para permitir al usuario subir archivos desde su sistema de archivos local. El diálogo bloquea la ejecución de la aplicación hasta que el usuario selecciona los archivos para subir o cierra el diálogo.

<!-- INTRO_END -->

:::tip Componente en línea
Si quieres un selector de archivos que se renderice directamente en el diseño de la página en lugar de un diálogo, considera usar el componente [`Upload`](/docs/components/upload).
:::

## Usos {#usages}

El `FileUploadDialog` proporciona una forma de seleccionar y subir archivos, permitiendo a los usuarios enviar documentos, imágenes u otros tipos de archivos requeridos por la aplicación. Usa `showFileUploadDialog()` para mostrar el diálogo y capturar el archivo subido.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Sube un archivo");
```

## Resultado {#result}

El `FileUploadDialog` devuelve un objeto `UploadedFile` que contiene información sobre el archivo subido, como su nombre, tamaño y contenido. Si el usuario cierra el diálogo sin seleccionar un archivo, el resultado será `null`.

:::important
El objeto `UploadedFile` resultante se devuelve del método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Mover archivos subidos {#moving-uploaded-files}

Por defecto, webforJ almacena los archivos subidos en una carpeta temporal que se limpia regularmente. Si no mueves el archivo a otro lugar, será eliminado. Para mover el archivo, usa el método `move` y especifica la ruta de destino.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecciona un archivo para subir");
try {
  File file = uploadedFile.move("mi/ruta/completa/" + uploadedFile.getSanitizedClientName());
  // ... haz algo con el archivo
} catch (IOException e) {
  // maneja la excepción
}
```
:::tip Nombre del Cliente Sanitizado
Usa el método `getSanitizedClientName` para obtener una versión sanitizada del nombre del archivo subido. Este método ayuda a prevenir riesgos de seguridad como ataques de recorrido de directorios o caracteres no válidos en los nombres de archivos, asegurando la integridad y seguridad de tu sistema de almacenamiento de archivos.
:::

## Filtros {#filters}

El `FileUploadDialog` te permite establecer filtros para limitar los tipos de archivos que se pueden seleccionar para subir. Puedes configurar filtros usando el método `setFilters(List<FileChooserFilter> filters)`.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Sube un archivo",
  Arrays.asList(new FileChooserFilter("Archivos de Texto", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validación de Filtros
El servidor no validará el archivo subido contra los filtros. Los filtros se aplican solo en la interfaz de usuario para guiar la selección del usuario. Debes implementar la validación del lado del servidor para asegurar que los archivos subidos cumplan con los requisitos de tu aplicación.
:::

## Tamaño máximo {#max-size}

Usa `setMaxFileSize(Number maxSize)` para establecer el tamaño máximo de subida en bytes.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Establecer tamaño máximo a 2 MB
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileUploadI18n`. Esta flexibilidad te permite adaptar la interfaz del diálogo para satisfacer requisitos de localización específicos o preferencias de personalización.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Subir archivo");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Subir");
i18n.setCancel("Cancelar");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Mejores prácticas {#best-practices}

1. **Mensajes claros y concisos**: Asegúrate de que el mensaje de aviso explique claramente lo que se le pide al usuario que suba.
2. **Filtros apropiados**: Establece filtros de archivos que coincidan con los tipos de archivos requeridos para garantizar que los usuarios suban archivos relevantes.
3. **Temática consistente**: Ajusta los temas del diálogo y del campo de subida al diseño de tu aplicación.
4. **Minimiza el uso excesivo**: Usa cuadros de diálogo de subida de archivos con moderación para evitar la frustración del usuario. Resérvalos para acciones que requieren especificaciones de carga de archivos por parte del usuario.
