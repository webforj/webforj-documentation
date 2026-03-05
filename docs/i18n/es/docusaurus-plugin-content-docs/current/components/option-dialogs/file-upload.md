---
title: File Upload
sidebar_position: 20
_i18n_hash: 70bfb275a09a977cf365a14535aaf02e
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Un `FileUploadDialog` es un diálogo modal diseñado para permitir al usuario subir archivos desde su sistema de archivos local. El diálogo bloquea la ejecución de la aplicación hasta que el usuario seleccione archivos para subir o cierre el diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `FileUploadDialog` proporciona una manera de seleccionar y subir archivos, permitiendo a los usuarios enviar documentos, imágenes u otros tipos de archivos requeridos por la aplicación. Usa `showFileUploadDialog()` para mostrar el diálogo y capturar el archivo subido.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Subir un archivo");
```

## Resultado {#result}

El `FileUploadDialog` devuelve un objeto `UploadedFile` que contiene información sobre el archivo subido, como su nombre, tamaño y contenido. Si el usuario cierra el diálogo sin seleccionar un archivo, el resultado será `null`.

:::important
La cadena resultante será devuelta desde el método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Mover archivos subidos {#moving-uploaded-files}

Por defecto, webforJ almacena archivos subidos en una carpeta temporal que se limpia regularmente. Si no mueves el archivo a otra ubicación, será eliminado. Para mover el archivo, usa el método `move` y especifica la ruta de destino.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecciona un archivo para subir");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... haz algo con el archivo
} catch (IOException e) {
    // maneja la excepción
}
```
:::tip Nombre de Cliente Sanitizado
Usa el método `getSanitizedClientName` para obtener una versión sanitizada del nombre del archivo subido. Este método ayuda a prevenir riesgos de seguridad como ataques de traversal de directorios o caracteres inválidos en nombres de archivos, asegurando la integridad y seguridad de tu sistema de almacenamiento de archivos.
:::

## Filtros {#filters}

El `FileUploadDialog` permite establecer filtros para limitar los tipos de archivos que pueden seleccionarse para subir. Puedes configurar filtros usando el método `setFilters(List<FileChooserFilter> filters)`.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Subir un archivo", 
    Arrays.asList(new FileChooserFilter("Archivos de Texto", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validación de Filtros
El servidor no validará el archivo subido contra los filtros. Los filtros solo se aplican en la interfaz de usuario para guiar la selección del usuario. Debes implementar la validación del lado del servidor para asegurarte de que los archivos subidos cumplan con los requisitos de tu aplicación.
:::

## Tamaño máximo {#max-size}

Es posible establecer el tamaño máximo del archivo para las subidas para asegurar que los usuarios no suban archivos que sean demasiado grandes para que tu aplicación los maneje. Esto se puede configurar usando el método `setMaxFileSize(long maxSize)`, donde maxSize está especificado en bytes.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Establecer tamaño máximo a 2 MB
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileUploadI18n`. Esta flexibilidad te permite adaptar la interfaz del diálogo para satisfacer requisitos específicos de localización o preferencias de personalización.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Subir archivo");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Subir");
i18n.setCancel("Cancelar");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Mejores prácticas {#best-practices}

1. **Prompts claros y concisos**: Asegúrate de que el mensaje de aviso explique claramente lo que se le pide al usuario que suba.
2. **Filtros apropiados**: Establece filtros de archivos que coincidan con los tipos de archivos requeridos para garantizar que los usuarios suban archivos relevantes.
3. **Rutas iniciales lógicas**: Establece rutas iniciales que proporcionen a los usuarios un punto de partida útil para la selección de archivos.
4. **Restringir la navegación por directorios**: Restringe el diálogo a un directorio específico cuando sea necesario para evitar que los usuarios naveguen a áreas no autorizadas.
5. **Temas consistentes**: Alinea los temas del diálogo y del campo de carga con el diseño de tu aplicación para una experiencia de usuario cohesiva.
6. **Minimizar el uso excesivo**: Usa diálogos de carga de archivos moderadamente para evitar la frustración del usuario. Resérvalos para acciones que requieran cargas específicas de archivos por parte del usuario.
