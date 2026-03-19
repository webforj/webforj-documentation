---
title: File Upload
sidebar_position: 20
_i18n_hash: 0c52346e43f2f615464dde85f39d7cd0
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Un `FileUploadDialog` es un diálogo modal diseñado para permitir al usuario subir archivos desde su sistema de archivos local. El diálogo bloquea la ejecución de la aplicación hasta que el usuario seleccione archivos para subir o cierre el diálogo.

<!-- INTRO_END -->

## Usos {#usages}

El `FileUploadDialog` proporciona una forma de seleccionar y subir archivos, lo que permite a los usuarios enviar documentos, imágenes u otros tipos de archivos requeridos por la aplicación. Usa `showFileUploadDialog()` para mostrar el diálogo y capturar el archivo subido.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Sube un archivo");
```

## Resultado {#result}

El `FileUploadDialog` devuelve un objeto `UploadedFile` que contiene información sobre el archivo subido, como su nombre, tamaño y contenido. Si el usuario cierra el diálogo sin seleccionar un archivo, el resultado será `null`.

:::important
La cadena resultante será devuelta del método `show()`, o del método equivalente `OptionDialog` como se muestra a continuación. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Mover archivos subidos {#moving-uploaded-files}

Por defecto, webforJ almacena los archivos subidos en una carpeta temporal que se limpia regularmente. Si no mueves el archivo a otro lugar, será eliminado. Para mover el archivo, utiliza el método `move` y especifica la ruta de destino.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecciona un archivo para subir");
try {
  File file = uploadedFile.move("mi/ruta/completa/" + uploadedFile.getSanitizedClientName());
  // ... haz algo con el archivo
} catch (IOException e) {
  // maneja la excepción
}
```
:::tip Nombre de Cliente Sanitizado
Usa el método `getSanitizedClientName` para obtener una versión sanitizada del nombre del archivo subido. Este método ayuda a prevenir riesgos de seguridad como ataques de traversía de directorios o caracteres inválidos en nombres de archivos, garantizando la integridad y seguridad de tu sistema de almacenamiento de archivos.
:::

## Filtros {#filters}

El `FileUploadDialog` te permite establecer filtros para limitar los tipos de archivos que pueden ser seleccionados para su subida. Puedes configurar filtros usando el método `setFilters(List<FileChooserFilter> filters)`.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Sube un archivo", 
  Arrays.asList(new FileChooserFilter("Archivos de Texto", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validación de Filtros
El servidor no validará el archivo subido contra los filtros. Los filtros solo se aplican en la interfaz de usuario para guiar la selección del usuario. Debes implementar validación del lado del servidor para asegurarte de que los archivos subidos cumplan con los requisitos de tu aplicación.
:::

## Tamaño máximo {#max-size}

Es posible establecer el tamaño máximo de archivo para las subidas para asegurarte de que los usuarios no suban archivos que sean demasiado grandes para que tu aplicación los maneje. Esto se puede configurar utilizando el método `setMaxFileSize(long maxSize)`, donde maxSize se especifica en bytes.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Establecer el tamaño máximo a 2 MB
```

## Internacionalización (i18n) {#internationalization-i18n}

Los títulos, descripciones, etiquetas y mensajes dentro del componente son completamente personalizables utilizando la clase `FileUploadI18n`. Esta flexibilidad te permite adaptar la interfaz del diálogo para cumplir con requisitos de localización específicos o preferencias de personalización.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Sube un archivo");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Subir");
i18n.setCancel("Cancelar");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Mejores prácticas {#best-practices}

1. **Sugerencias Claras y Concisas**: Asegúrate de que el mensaje de solicitud explique claramente lo que se le pide al usuario que suba.
2. **Filtros Apropiados**: Establece filtros de archivos que coincidan con los tipos de archivos requeridos para asegurar que los usuarios suban archivos relevantes.
3. **Rutas Iniciales Lógicas**: Establece rutas iniciales que proporcionen a los usuarios un punto de partida útil para su selección de archivos.
4. **Restringir la Navegación por Directorios**: Restringe el diálogo a un directorio específico cuando sea necesario para evitar que los usuarios naveguen a áreas no autorizadas.
5. **Temas Consistentes**: Alinea los temas del diálogo y del campo de subida con el diseño de tu aplicación para una experiencia de usuario cohesiva.
6. **Minimizar el Uso Excesivo**: Utiliza diálogos de subida de archivos de manera moderada para evitar la frustración del usuario. Resérvalos para acciones que requieran subidas específicas de archivos por parte del usuario.
