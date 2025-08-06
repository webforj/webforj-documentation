---
sidebar_position: 20
title: File Upload
_i18n_hash: e25933325d4f0d5a7044a5e0776e3741
---
# Diálogo de Carga de Archivos

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Un `FileUploadDialog` es un cuadro de diálogo modal diseñado para permitir al usuario cargar archivos desde su sistema de archivos local. El diálogo bloquea la ejecución de la aplicación hasta que el usuario selecciona archivos para cargar o cierra el diálogo.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Subir un archivo");
```

## Usos {#usages}

El `FileUploadDialog` proporciona una forma de seleccionar y cargar archivos, permitiendo a los usuarios enviar documentos, imágenes u otros tipos de archivos requeridos por la aplicación.

## Resultado {#result}

El `FileUploadDialog` devuelve un objeto `UploadedFile` que contiene información sobre el archivo cargado, como su nombre, tamaño y contenido. Si el usuario cierra el diálogo sin seleccionar un archivo, el resultado será `null`.

:::important
La cadena resultante será devuelta del método `show()`, o el método equivalente de `OptionDialog` como se muestra a continuación.
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Mover archivos cargados {#moving-uploaded-files}

Por defecto, webforJ almacena los archivos cargados en una carpeta temporal que se limpia regularmente. Si no mueves el archivo a otro lugar, será eliminado. Para mover el archivo, utiliza el método `move` y especifica la ruta de destino.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Selecciona un archivo para cargar");
try {
    File file = uploadedFile.move("mi/ruta/completa/" + uploadedFile.getSanitizedClientName());
    // ... hacer algo con el archivo
} catch (IOException e) {
    // manejar la excepción
}
```
:::tip Nombre del Cliente Saneado
Utiliza el método `getSanitizedClientName` para obtener una versión saneada del nombre del archivo cargado. Este método ayuda a prevenir riesgos de seguridad como ataques de recorrido de directorios o caracteres invalidos en nombres de archivos, asegurando la integridad y seguridad de tu sistema de almacenamiento de archivos.
:::

## Filtros {#filters}

El `FileUploadDialog` te permite establecer filtros para limitar los tipos de archivos que se pueden seleccionar para carga. Puedes configurar filtros utilizando el método `setFilters(List<FileChooserFilter> filters)`.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Subir un archivo", 
    Arrays.asList(new FileChooserFilter("Archivos de Texto", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validación de Filtros
El servidor no validará el archivo cargado contra los filtros. Los filtros solo se aplican en la interfaz de usuario para guiar la selección del usuario. Debes implementar la validación del lado del servidor para asegurarte de que los archivos cargados cumplan con los requisitos de tu aplicación.
:::

## Tamaño Máximo {#max-size}

Es posible establecer el tamaño máximo de archivo para cargas para asegurar que los usuarios no carguen archivos que sean demasiado grandes para que tu aplicación los maneje. Esto se puede configurar utilizando el método `setMaxFileSize(long maxSize)`, donde maxSize se especifica en bytes.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Establecer el tamaño máximo en 2 MB
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

## Mejores Prácticas {#best-practices}

1. **Solicitudes Claras y Concisas**: Asegúrate de que el mensaje de solicitud explique claramente qué se le está pidiendo al usuario que cargue.
2. **Filtros Apropiados**: Establece filtros de archivos que coincidan con los tipos de archivos requeridos para asegurar que los usuarios carguen archivos relevantes.
3. **Rutas Iniciales Lógicas**: Establece rutas iniciales que proporcionen a los usuarios un punto de partida útil para su selección de archivos.
4. **Restringir la Navegación del Directorio**: Restringe el diálogo a un directorio específico cuando sea necesario para evitar que los usuarios naveguen a áreas no autorizadas.
5. **Tematización Consistente**: Alinea los temas del diálogo y del campo de carga con el diseño de tu aplicación para una experiencia de usuario cohesiva.
6. **Minimizar el Uso Excesivo**: Utiliza los diálogos de carga de archivos de manera moderada para evitar la frustración del usuario. Resérvalos para acciones que requieran cargas de archivos específicas por parte del usuario.
