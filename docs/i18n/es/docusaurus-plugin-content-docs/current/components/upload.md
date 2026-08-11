---
title: Upload
sidebar_position: 160
sidebar_class_name: new-content
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: 76f8c00c7754fed0a87c27e7963e2877
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

El componente `Upload` es un selector de archivos en línea que permite al usuario seleccionar uno o más archivos de su máquina local y enviarlos al servidor. A diferencia de [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), que presenta el selector en un modal que bloquea la aplicación hasta que el usuario termina, `Upload` se renderiza directamente en el diseño de la página. Se adapta a cualquier lugar donde pertenezca una entrada de archivo: un formulario de perfil, un campo de adjuntos junto a un cuadro de comentarios, o una zona de caída en una página de gestión de medios.

<!-- INTRO_END -->

:::tip Cuándo usar un `Upload`
Utiliza el componente `Upload` cuando la selección de archivo esté acompañada de otras acciones en un flujo de trabajo, como editar un perfil o crear una publicación. Opta por [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) en su lugar cuando las cargas deban ser modales, por ejemplo, cuando se requiere estrictamente un archivo antes de que el usuario pueda continuar.
:::

## Creando una carga {#creating-an-upload}

Por defecto, un componente `Upload` muestra un botón de selección, un área de caída, la lista de archivos actuales y un botón de carga. El botón de cancelar está oculto por defecto. Después de crear un `Upload`, puedes añadir filtros, como tipos de archivos permitidos, y cambiar qué partes son visibles.

```java
Upload upload = new Upload();
upload.addFilter("Imágenes", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

El siguiente ejemplo inserta un `Upload` de currículum en un formulario de contratación, junto a un campo de nombre y un botón de enviar.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Seleccionando archivos {#picking-files}

El comportamiento del selector está controlado por algunas configuraciones independientes: cuántos archivos puede elegir el usuario a la vez, qué se puede seleccionar del sistema de archivos local, y qué tipos son visibles en el cuadro de diálogo de archivos. Juntas, estas configuraciones moldean la experiencia de selección para adaptarse al campo.

Aquí hay un cargador de galería configurado con filtros de imagen y video, selección de múltiples archivos y un límite de 20 archivos:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Modo de selección {#selection-mode}

El modo de selección limita el selector a un solo archivo o varios. `MULTIPLE` es el predeterminado y se adapta a operaciones por lotes como galerías de fotos o adjuntos de facturas. `SINGLE` se adapta a campos que conceptualmente sostienen un solo valor, como una foto de perfil o un contrato firmado.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Origen del selector {#picker-source}

El origen del selector determina qué puede seleccionar el usuario del sistema de archivos local. El valor predeterminado, `FILES`, abre un cuadro de diálogo de archivos estándar. `DIRECTORY` permite al usuario seleccionar una carpeta y subir sus archivos de nivel superior. `DIRECTORY_RECURSIVE` recorre todo el árbol y sube cada archivo dentro.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Las cargas de directorios se adaptan a herramientas que reflejan estructuras de carpetas, como sistemas de implementación, aplicaciones de gestión de activos o utilidades de respaldo. Para la mayoría de los campos de formulario, el selector de archivos predeterminado es la opción adecuada.

### Filtros {#filters}

Los filtros limitan lo que el usuario puede seleccionar del sistema de archivos local. Cada filtro tiene una descripción y uno o más patrones glob separados por punto y coma. El filtro activo aparece en un menú desplegable junto al botón del selector, y el usuario puede alternar entre ellos.

```java
upload.addFilter("Imágenes", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documentos", "*.pdf;*.docx");
upload.setActiveFilter("Imágenes");
```

Algunas configuraciones relacionadas moldean el comportamiento del menú desplegable de filtros: `setFiltersVisible(false)` oculta el menú desplegable mientras mantiene los filtros activos, `setMultiFilterSelection(true)` permite al usuario combinar filtros, y `setAllFilesFilterEnabled(false)` elimina la opción implícita "Todos los archivos".

Un par de estas configuraciones solo se aplican al selector estándar. Cuando se está utilizando la API de Acceso al Sistema de Archivos, el selector nativo de OS maneja la selección de filtros por sí mismo, por lo que `setFiltersVisible(false)` se ignora y `setMultiFilterSelection(true)` no tiene efecto (el selector nativo acepta solo un filtro a la vez). Desactiva la API de Acceso al Sistema de Archivos con `setFileSystemAccess(false)` para hacer que esas configuraciones sean fiables en todos los navegadores.

### Zona de caída {#drop-zone}

Los archivos se pueden arrastrar desde el escritorio y soltar sobre el componente. La etiqueta de caída cambia cuando un archivo flota sobre ella, señalando que la caída será aceptada. La caída está activada por defecto y se puede desactivar cuando el selector solo debe aceptar archivos del cuadro de diálogo de archivos.

```java
upload.setDrop(false);
```

## Validación y límites {#validation-and-limits}

`setMaxFileSize` limita el tamaño en bytes de un solo archivo, y `setMaxFiles` limita el número total de archivos en un lote. Ambos se ejecutan antes de que se transfiera cualquier byte, por lo que un archivo de gran tamaño es rechazado en el cliente sin consumir ancho de banda.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Cuando un archivo seleccionado o soltado excede alguno de los límites, se activa `UploadRejectEvent` con el motivo. La propiedad del lado del servidor `webforj.fileUpload.maxSize` sigue aplicándose y actúa como un límite rígido, independientemente del límite del lado del cliente.

:::warning Validación del lado del servidor
Los filtros, el tamaño máximo y la cantidad máxima de archivos se hacen cumplir en la UI para guiar al usuario, no para proteger al servidor. Cada archivo subido debe ser revisado nuevamente en el servidor antes de ser almacenado, y los archivos temporales deben ser movidos o eliminados poco después de que la carga se complete.
:::

## Comportamiento de carga {#upload-behavior}

Una vez que se seleccionan los archivos, quedan dos decisiones: cuándo comienza la carga y qué sucede con las entradas existentes cuando el usuario selecciona nuevamente. Por defecto, el usuario hace clic en **Cargar** para iniciar la transferencia, y las entradas existentes permanecen en la lista hasta que se eliminan explícitamente.

### Carga automática {#auto-upload}

El modo predeterminado es `NONE`, donde el usuario hace clic en **Cargar** para iniciar la transferencia. `setAutoUpload()` elimina ese clic y comienza la transferencia tan pronto como se seleccionan, sueltan o ambas cosas.

- **`NONE`** deja la carga a cargo del usuario, que hace clic en **Cargar**.
- **`ON_SELECT`** carga tan pronto como se seleccionan archivos a través del cuadro de diálogo de archivos.
- **`ON_DROP`** carga tan pronto como se sueltan archivos sobre el componente.
- **`ALWAYS`** cubre ambos caminos.

:::tip Emparejando con preajustes
La carga automática se combina bien con los preajustes `BUTTON_ONLY` o `INLINE`, donde no hay un botón de Cargar para que el usuario haga clic de todos modos. Para flujos de trabajo en los que el usuario necesita revisar la selección antes de enviar, deja apagada la carga automática.
:::

### Borrado automático {#auto-clear}

Cuando el usuario elige un nuevo lote, el borrado automático decide qué hacer con las entradas ya en la lista. La limpieza ocurre en el momento de la próxima selección, no al completar la carga, por lo que las cargas finalizadas permanecen visibles hasta que el usuario seleccione nuevamente.

- **`COMPLETADO`** limpia las entradas subidas con éxito.
- **`EN_PROGRESO`** cancela y limpia las entradas que aún se están transfiriendo.
- **`TODOS`** limpia todo.
Las entradas en cola que no han comenzado a subir se mantienen independientemente de la configuración.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Borrado automático tiene desencadenantes sutiles
El borrado automático solo entra en efecto una vez que un archivo previamente seleccionado ha comenzado a subir o ha terminado. Sin una carga entre selecciones, ningún archivo coincide con el filtro y la lista sigue creciendo.
:::

Opta por `COMPLETADO` en cargadores que están en pantalla a través de múltiples acciones, como un compositor de chat donde cada mensaje tiene sus propios adjuntos, o un formulario de comentarios que se reutiliza para cada respuesta. Sin él, la lista de éxitos anteriores se acumula a medida que el usuario trabaja.

### Acciones programáticas {#programmatic-actions}

La mayoría de las cargas comienzan con un clic del usuario, pero las mismas acciones están disponibles desde el código del servidor. Ambas operan sobre los archivos que el usuario ya ha seleccionado; no hay forma de seleccionar archivos en nombre del usuario desde el servidor.

```java
// Cargar la selección actual, como si el usuario hiciera clic en Cargar
upload.upload();

// Cancelar cualquier transferencia en progreso
upload.cancel();
```

Llama a `upload()` para activar la transferencia desde un control fuera del componente, como un único botón de enviar compartido por un formulario más grande. Llama a `cancel()` desde un botón "detener" fuera del componente, o desde un guardia de ruta cuando el usuario navega fuera a mitad de la transferencia.

## Captura móvil {#mobile-capture}

En dispositivos móviles, la captura abre la cámara o el micrófono como fuente del selector en lugar del navegador de archivos. `USER` apunta a la cámara frontal o micrófono, `ENVIRONMENT` apunta a la cámara trasera, y `NONE` (el predeterminado) utiliza el selector de archivos estándar.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Foto", "*.jpg;*.png");
```

:::tip Captura y filtros
Restringe la selección a extensiones de imagen para que la cámara se abra en modo estático, o a extensiones de video para que se abra en modo de grabación. Sin un filtro correspondiente, un modo de captura retrocede al selector estándar en la mayoría de las plataformas. Los navegadores de escritorio ignoran completamente la configuración de captura.
:::

Para aplicaciones orientadas a móviles, la captura se complementa bien con [aplicaciones instalables](/docs/configuration/installable-apps), donde la cámara y el micrófono se convierten en una parte natural de la experiencia en la pantalla de inicio.

## Acceso nativo al sistema de archivos {#native-file-system-access}

El componente utiliza la [API de Acceso al Sistema de Archivos](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) del navegador cuando la plataforma lo admite. El selector nativo puede otorgar permiso persistente a la página para una carpeta, de modo que el usuario selecciona una vez y las cargas posteriores desde la misma carpeta saltan el diálogo. En navegadores sin soporte, el componente cae automáticamente de vuelta al selector estándar.

```java
upload.setFileSystemAccess(false); // forzar el selector estándar
```

Desactívalo cuando cada carga deba comenzar desde un nuevo cuadro de diálogo, o cuando un comportamiento consistente en cada navegador sea más importante que la conveniencia del permiso persistente.

## Personalizando el diseño {#customizing-the-layout}

El componente se construye a partir de cinco partes: el botón del selector, la etiqueta de caída, la lista de archivos, el botón de carga y el botón de cancelar. Las primeras cuatro son visibles por defecto; el botón de cancelar está oculto y se puede mostrar con `setVisible(true, Upload.Part.CANCEL_BUTTON)`. El diseño se puede remodelar con preajustes para formas de selector comunes, o con controles de visibilidad por parte para ajustes más finos.

### Preajustes {#presets}

Los preajustes agrupan varios ajustes de visibilidad de partes en formas de selector nombradas. Son una forma más rápida de alcanzar una configuración común que alternar partes individualmente.

- **`FULL`**: Botón del selector, etiqueta de caída, lista de archivos y botón de carga. El predeterminado.
- **`INLINE`**: Botón del selector y etiqueta de caída, con la selección actual renderizada como texto junto al selector. Útil para campos de formulario compactos.
- **`BUTTON_ONLY`**: Solo el botón del selector. Útil cuando la UI circundante ya muestra los archivos seleccionados.
- **`DROPZONE`**: Etiqueta de caída y lista de archivos, sin botón del selector. Útil cuando arrastrar y soltar debe ser la única forma de añadir archivos.
- **`HEADLESS`**: Cada parte oculta, con el borde exterior, radio y relleno colapsados para que el contenido proyectado quede ajustado dentro de los límites del componente.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
  'src/main/frontend/css/upload/uploadPresets.css'
]}
height='650px'
/>

### Visibilidad de partes {#part-visibility}

Cuando un preajuste se acerca, pero no llega exactamente a la forma deseada, se pueden mostrar u ocultar partes individuales. Esto es útil para pequeños ajustes como ocultar el botón de cancelar en un selector de un solo archivo que sube instantáneamente, o ocultar la etiqueta de caída en un campo solo de botón que aún permite caídas. Al usar `setPreset()` y `setVisible()` juntos, llama a `setPreset()` primero.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Slot por defecto {#default-slot}

`Upload` implementa `HasComponents`. Los hijos añadidos a través de `add()` se representan dentro del área de caída, encima del cromado estándar. Combinado con el preajuste `HEADLESS`, el slot permite tomar control de la superficie visual por completo mientras mantienes el selector, caída y comportamiento de carga intactos.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

En el siguiente ejemplo, se utiliza el preajuste `HEADLESS` para proyectar una `Table` dentro de los límites de Upload. Arrastra un CSV y sus filas se renderizan directamente dentro del componente, con columnas construidas a partir de la fila de encabezado del archivo.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Eventos {#events}

`Upload` emite eventos a tres niveles: cosas que el usuario hace al componente completo, el estado de transferencia de un solo archivo y el ciclo de vida del lote en su totalidad. La mayoría de las aplicaciones registran un par de oyentes a través de estos niveles dependiendo de lo que necesiten reaccionar. Un formulario podría necesitar solo `onUpload` para saber cuándo los archivos llegan al servidor; un cargador con una interfaz de usuario de progreso necesita `onListProgress` y `onComplete`; una zona de caída que debe mostrar rechazos necesita `onReject`.

La mayoría de los eventos que transportan archivos exponen tanto `getFile()` (el primer o único archivo en la carga) como `getFiles()` (la lista completa). Usa `getFile()` para eventos de un solo archivo como `onReject`, y `getFiles()` cuando esperas un lote. `UploadCompleteEvent` es la excepción; tiene su propio `getUploadedFiles()` y `getFailedFiles()` accesores ya que el resultado del lote se divide entre éxitos y fallos.

### Acciones del usuario {#user-actions}

Estos se activan en respuesta a algo que hace el usuario en el componente en su totalidad. No dicen nada sobre el progreso de la transferencia, solo que el usuario ha hecho algo a lo que la aplicación podría querer reaccionar.

| Evento | Ocurre |
| --- | --- |
| `UploadChangeEvent` | Cuando la lista de archivos seleccionados cambia |
| `UploadEvent` | Cuando el usuario hace clic en **Cargar** y los archivos llegan al servidor |
| `UploadCancelEvent` | Cuando el usuario hace clic en **Cancelar** |
| `UploadFilterChangeEvent` | Cuando el filtro activo cambia |

```java
upload.onChange(e -> {
    // Se activa cada vez que cambia la lista de archivos seleccionados.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Se activa cuando se desencadena la carga; los archivos han llegado al servidor.
});
```

`UploadEvent` y `UploadCompleteEvent` se ven similares a primera vista, pero responden a preguntas diferentes. `UploadEvent` se activa cuando el usuario desencadena explícitamente la carga (o `setAutoUpload()` la desencadena en su nombre), y es el lugar natural para persistir o transferir los archivos subidos. `UploadCompleteEvent` se activa una vez que se ha terminado la transferencia de cada archivo en cola, y es el gancho correcto para actualizaciones de UI de "el lote está hecho".

### Transferencia por archivo {#per-file-transfer}

Estos se activan una vez por archivo, mientras se está llevando a cabo una transferencia o justo después de que falla. Úsalos cuando la interfaz de usuario necesite reflejar el estado de archivos individuales más que el del lote.

| Evento | Ocurre |
| --- | --- |
| `UploadProgressEvent` | Mientras se transfiere un solo archivo |
| `UploadErrorEvent` | Cuando falla la transferencia de un solo archivo |
| `UploadRejectEvent` | Cuando un archivo seleccionado o soltado no cumple con las restricciones configuradas |

```java
upload.onProgress(e -> {
    // Se activa repetidamente durante la transferencia de un solo archivo.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Se activa cuando un archivo es rechazado por razones de tamaño, cantidad o filtro.
    String reason = e.getMessage();
});
```

Dentro de este grupo, `UploadRejectEvent` es el raro. Se activa antes de que se mueva algún byte, cuando un archivo no cumple con una restricción del lado del cliente como `setMaxFileSize` o `setMaxFiles`. `UploadErrorEvent`, por contraste, se activa después de que comenzó la transferencia y algo salió mal en el camino hacia el servidor.

### Todo el lote {#whole-batch}

Estos se activan en el lote en lugar de en un solo archivo. Úsalos para una UI agregada como una barra de progreso general o un mensaje de "terminado" que resume toda la selección.

| Evento | Ocurre |
| --- | --- |
| `UploadListProgressEvent` | Junto con `UploadProgressEvent`, con el estado de toda la lista |
| `UploadCompleteEvent` | Una vez por lote, cuando cada archivo ha terminado de transferirse |

```java
upload.onComplete(e -> {
    // Se activa una vez cuando todo el lote ha terminado.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` y `onListProgress` cubren la misma transferencia desde dos ángulos. `onProgress` es por archivo, y es el gancho correcto cuando cada archivo tiene su propia interfaz de usuario de progreso. `onListProgress` se activa al mismo tiempo con contadores agregados (`getListTotal`, `getListRemaining`, `getListProgress`) para un único indicador de batch.

En el siguiente ejemplo, `onChange`, `onListProgress` y `onComplete` impulsan una barra de progreso y una línea de estado que se actualizan a medida que cambia la lista de archivos y a medida que se transfieren los archivos.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
  'src/main/frontend/css/upload/uploadEvents.css'
]}
height='450px'
/>

## Internacionalización (i18n) {#internationalization-i18n}

Las etiquetas y los mensajes dentro del componente son personalizables a través del paquete `FileUploadI18n`. El tipo de paquete mantiene el nombre `FileUploadI18n` porque se comparte con el modal [`FileUploadDialog`](/docs/components/option-dialogs/file-upload).

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Enviar");
bundle.setCancel("Descartar");
bundle.setDropFile("Suelta el archivo aquí");
upload.setI18n(bundle);
```

## Temas {#themes}

`UploadTheme` refleja la paleta de temas estándar de DWC e incluye variantes contorneadas para un peso visual más ligero. Los temas se aplican a los botones de selector, carga y cancelar. La lista y el área de caída mantienen un estilo neutral independientemente del tema.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

La demostración a continuación muestra el tema `PRIMARY` combinado con el preajuste `INLINE`.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Estilizando {#styling}

<TableBuilder name="Upload" />
