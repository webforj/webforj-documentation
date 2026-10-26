---
title: Upload
sidebar_position: 160
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: df26b1e4b51f3ba6ece7602ca1a1f33f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

El componente `Upload` es un selector de archivos en línea que permite al usuario seleccionar uno o más archivos de su máquina local y enviarlos al servidor. A diferencia de [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), que presenta el selector en un modal que bloquea la aplicación hasta que el usuario finaliza, `Upload` se renderiza directamente en el diseño de la página. Se adapta a cualquier lugar donde un campo de entrada de archivo pertenezca: un formulario de perfil, un campo de adjunto junto a un cuadro de comentarios, o una zona de arrastre en una página de gestión de medios.

<!-- INTRO_END -->

:::tip Cuándo usar un `Upload`
Utiliza el componente `Upload` cuando la selección de archivos vaya acompañada de otras acciones en un flujo de trabajo, como editar un perfil o crear una publicación. Opta por [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) en su lugar cuando las cargas deban ser modales, por ejemplo, cuando un archivo es estrictamente requerido antes de que el usuario pueda continuar.
:::

## Creando una carga {#creating-an-upload}

Por defecto, un componente `Upload` muestra un botón de selección, un área de arrastre, la lista de archivos actuales y un botón de carga. El botón de cancelar está oculto por defecto. Después de crear un `Upload`, puedes añadir filtros, como tipos de archivos permitidos, y cambiar qué partes son visibles.

```java
Upload upload = new Upload();
upload.addFilter("Imágenes", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

El siguiente ejemplo inserta un `Upload` para un currículum en un formulario de contratación, junto a un campo de nombre y un botón de enviar.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Seleccionando archivos {#picking-files}

El comportamiento del selector está controlado por algunas configuraciones independientes: cuántos archivos puede seleccionar el usuario a la vez, qué se puede seleccionar del sistema de archivos local, y qué tipos son visibles en el cuadro de diálogo de archivos. En conjunto, moldean la experiencia de selección para adaptarse al campo.

Aquí hay un cargador de galería configurado con filtros de imagen y video, selección de múltiples archivos, y un límite de 20 archivos:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Modo de selección {#selection-mode}

El modo de selección limita el selector a un archivo o muchos. `MULTIPLE` es el modo por defecto y se adapta a operaciones por lotes como galerías de fotos o adjuntos de facturas. `SINGLE` se adapta a campos que conceptualmente contienen un solo valor, como una foto de perfil o un contrato firmado.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Fuente del selector {#picker-source}

La fuente del selector determina de qué puede seleccionar el usuario del sistema de archivos local. El valor por defecto, `FILES`, abre un cuadro de diálogo de archivos estándar. `DIRECTORY` permite al usuario seleccionar una carpeta y subir sus archivos de nivel superior. `DIRECTORY_RECURSIVE` recorre todo el árbol y sube cada archivo dentro.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Las cargas de directorios se adaptan a herramientas que reflejan estructuras de carpetas, como sistemas de implementación, aplicaciones de gestión de activos o utilidades de respaldo. Para la mayoría de los campos de formulario, el selector de archivos por defecto es la opción correcta.

### Filtros {#filters}

Los filtros limitan lo que el usuario puede seleccionar del sistema de archivos local. Cada filtro tiene una descripción y uno o más patrones glob separados por punto y coma. El filtro activo aparece en un menú desplegable junto al botón del selector, y el usuario puede alternar entre ellos.

```java
upload.addFilter("Imágenes", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documentos", "*.pdf;*.docx");
upload.setActiveFilter("Imágenes");
```

Algunas configuraciones relacionadas dan forma a cómo se comporta el menú desplegable de filtros: `setFiltersVisible(false)` oculta el menú desplegable mientras mantiene los filtros activos, `setMultiFilterSelection(true)` permite al usuario combinar filtros, y `setAllFilesFilterEnabled(false)` elimina la opción implícita de "Todos los Archivos".

Un par de estas configuraciones solo se aplican al selector estándar. Cuando se utiliza la API de Acceso al Sistema de Archivos, el selector nativo gestiona la selección de filtros por sí mismo, por lo que `setFiltersVisible(false)` se ignora y `setMultiFilterSelection(true)` no tiene efecto (el selector nativo acepta solo un filtro a la vez). Desactiva la API de Acceso al Sistema de Archivos con `setFileSystemAccess(false)` para que esas configuraciones sean fiables en todos los navegadores.

### Zona de soltar {#drop-zone}

Los archivos se pueden arrastrar desde el escritorio y soltarlos sobre el componente. La etiqueta de soltar cambia cuando un archivo está sobre ella, señalando que la caída será aceptada. La opción de soltar está habilitada por defecto, y se puede desactivar cuando el selector solo debe aceptar archivos del cuadro de diálogo de archivos.

```java
upload.setDrop(false);
```

## Validación y límites {#validation-and-limits}

`setMaxFileSize` limita el tamaño en bytes de un solo archivo, y `setMaxFiles` limita el número total de archivos en un lote. Ambos se ejecutan antes de que se transfieran bytes, así que un archivo que excede el tamaño se rechaza en el cliente sin consumir ancho de banda.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Cuando un archivo seleccionado o deslizado excede cualquiera de los límites, se dispara `UploadRejectEvent` con el motivo. La propiedad del lado del servidor `webforj.fileUpload.maxSize` aún se aplica y actúa como un techo estricto, independientemente del límite del lado del cliente.

:::warning Validación del lado del servidor
Los filtros, el tamaño máximo y el conteo máximo de archivos se aplican en la interfaz de usuario para guiar al usuario, no para proteger al servidor. Cada archivo cargado debe ser revisado nuevamente en el servidor antes de ser almacenado, y los archivos temporales deben ser movidos o eliminados poco después de que la carga se complete.
:::

## Comportamiento de la carga {#upload-behavior}

Una vez que se seleccionan los archivos, quedan dos decisiones: cuándo comienza la carga y qué sucede con las entradas existentes cuando el usuario selecciona nuevamente. Por defecto, el usuario hace clic en **Cargar** para iniciar la transferencia, y las entradas existentes permanecen en la lista hasta que se borran explícitamente.

### Carga automática {#auto-upload}

El modo por defecto es `NONE`, donde el usuario hace clic en **Cargar** para iniciar la transferencia. `setAutoUpload()` elimina ese clic y comienza la transferencia tan pronto como se seleccionan, sueltan o ambos los archivos.

- **`NONE`** deja la carga en manos del usuario, que hace clic en **Cargar**.
- **`ON_SELECT`** carga tan pronto como se seleccionan archivos a través del cuadro de diálogo de archivos.
- **`ON_DROP`** carga tan pronto como se sueltan archivos sobre el componente.
- **`ALWAYS`** cubre ambos caminos.

:::tip Combinando con configuraciones predefinidas
La carga automática se combina bien con los presets `BUTTON_ONLY` o `INLINE`, donde no hay un botón de carga para que el usuario haga clic. Para flujos de trabajo donde el usuario necesita revisar la selección antes de enviar, deja la carga automática desactivada.
:::

### Borrado automático {#auto-clear}

Cuando el usuario selecciona un nuevo lote, el borrado automático decide qué hacer con las entradas que ya están en la lista. El borrado ocurre en el momento de la siguiente selección, no al completar la carga, así que las cargas completadas permanecen visibles hasta que el usuario selecciona nuevamente.

- **`COMPLETED`** borra entradas que se han cargado con éxito.
- **`IN_PROGRESS`** cancela y borra entradas que aún se están transfiriendo.
- **`ALL`** borra todo.
Las entradas en cola que no han comenzado a cargar se mantienen independientemente de la configuración.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning El borrado automático tiene desencadenadores sutiles
El borrado automático solo toma efecto una vez que un archivo previamente seleccionado ha comenzado realmente a cargarse o ha terminado. Sin una carga entre selecciones, ningún archivo coincide con el filtro y la lista continúa creciendo.
:::

Opta por `COMPLETED` en cargadores que viven en pantalla a través de múltiples acciones, como un compositor de chat donde cada mensaje tiene sus propios adjuntos, o un formulario de comentarios que se reutiliza para cada respuesta. Sin ello, la lista de éxitos previos se acumula a medida que el usuario trabaja.

### Acciones programáticas {#programmatic-actions}

La mayoría de las cargas comienzan con un clic del usuario, pero las mismas acciones están disponibles desde el código del servidor. Ambas operan sobre los archivos que el usuario ya ha seleccionado; no hay forma de seleccionar archivos en nombre del usuario desde el servidor.

```java
// Cargar la selección actual, como si el usuario hubiera hecho clic en Cargar
upload.upload();

// Cancelar cualquier transferencia en progreso
upload.cancel();
```

Llama a `upload()` para activar la transferencia desde un control fuera del componente, como un solo botón de envío compartido por un formulario más grande. Llama a `cancel()` desde un botón "detener" fuera del componente, o desde un guardia de ruta cuando el usuario navega lejos durante la transferencia.

## Captura móvil {#mobile-capture}

En dispositivos móviles, la captura abre la cámara o el micrófono como la fuente del selector en lugar del navegador de archivos. `USER` apunta a la cámara frontal o al micrófono, `ENVIRONMENT` apunta a la cámara trasera, y `NONE` (el valor predeterminado) utiliza el selector de archivos estándar.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Foto", "*.jpg;*.png");
```

:::tip Captura y filtros
Restringe la selección a extensiones de imagen para que la cámara se abra en modo estático, o a extensiones de video para que se abra en modo de grabación. Sin un filtro correspondiente, un modo de captura vuelve al selector estándar en la mayoría de las plataformas. Los navegadores de escritorio ignoran completamente la configuración de captura.
:::

Para aplicaciones móviles, la captura se combina bien con [aplicaciones instalables](/docs/configuration/installable-apps), donde la cámara y el micrófono se convierten en una parte natural de la experiencia de la pantalla de inicio.

## Acceso nativo al sistema de archivos {#native-file-system-access}

El componente utiliza la [API de Acceso al Sistema de Archivos](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) del navegador cuando la plataforma lo admite. El selector nativo puede otorgar permiso persistente a una carpeta, de modo que el usuario selecciona una vez y las cargas subsiguientes desde la misma carpeta evitan el cuadro de diálogo. En los navegadores sin soporte, el componente automáticamente vuelve al selector estándar.

```java
upload.setFileSystemAccess(false); // forzar el selector estándar
```

Desactívalo cuando cada carga deba comenzar desde un nuevo cuadro de diálogo, o cuando el comportamiento consistente en todos los navegadores sea más importante que la conveniencia del permiso persistente.

## Personalizando el diseño {#customizing-the-layout}

El componente está construido a partir de cinco partes: el botón de selección, la etiqueta de soltar, la lista de archivos, el botón de carga y el botón de cancelar. Las primeras cuatro son visibles por defecto; el botón de cancelar está oculto y se puede mostrar con `setVisible(true, Upload.Part.CANCEL_BUTTON)`. El diseño puede ser remodelado con presets para formas comunes de selectores, o con controles de visibilidad por parte para ajustes más finos.

### Presets {#presets}

Los presets agrupan varias configuraciones de visibilidad de partes en formas de selectores nombradas. Son una forma más rápida de alcanzar una configuración común que alternar partes de forma individual.

- **`FULL`**: Botón de selector, etiqueta de soltar, lista de archivos, y botón de carga. El valor por defecto.
- **`INLINE`**: Botón de selector y etiqueta de soltar, con la selección actual representada como texto junto al selector. Útil para campos de formulario compactos.
- **`BUTTON_ONLY`**: El botón de selector por sí solo. Útil cuando la interfaz de usuario circundante ya muestra los archivos seleccionados.
- **`DROPZONE`**: Etiqueta de soltar y lista de archivos, sin botón de selector. Útil cuando el arrastre y la caída deben ser la única forma de añadir archivos.
- **`HEADLESS`**: Todas las partes ocultas, con el borde exterior, el radio y el relleno colapsados para que el contenido proyectado quede alineado dentro de los límites del componente.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
]}
height='650px'
/>

### Visibilidad de partes {#part-visibility}

Cuando un preset se acerca pero no alcanza la forma deseada, las partes individuales se pueden mostrar u ocultar. Esto es útil para pequeños ajustes como ocultar el botón de cancelar en un selector de un solo archivo que carga instantáneamente, o para ocultar la etiqueta de soltar en un campo solo de botones que todavía permite arrastres. Al usar `setPreset()` y `setVisible()` juntos, llama primero a `setPreset()`.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Slot por defecto {#default-slot}

`Upload` implementa `HasComponents`. Los hijos añadidos a través de `add()` se renderizan dentro del área de soltar, sobre el cromado estándar. Combinado con el preset `HEADLESS`, el slot te permite tomar el control de la superficie visual completamente mientras mantienes intacto el comportamiento de selección, arrastre y carga.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

En el siguiente ejemplo, se utiliza el preset `HEADLESS` para proyectar una `Table` dentro de los límites de `Upload`. Sube un CSV y sus filas se renderizan directamente dentro del componente, con columnas construidas a partir de la fila de encabezados del archivo.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Eventos {#events}

`Upload` emite eventos en tres niveles: cosas que el usuario hace al componente en su totalidad, el estado de transferencia de un solo archivo, y el ciclo de vida del lote en su conjunto. La mayoría de las aplicaciones registran un par de oyentes a través de estos niveles dependiendo de lo que necesiten reaccionar. Un formulario podría necesitar solo `onUpload` para saber cuándo los archivos llegan al servidor; un cargador con una interfaz de progreso necesita `onListProgress` y `onComplete`; una zona de arrastre que debe mostrar rechazos necesita `onReject`.

La mayoría de los eventos que transportan archivos exponen tanto `getFile()` (el primer o único archivo en la carga) como `getFiles()` (la lista completa). Utiliza `getFile()` para eventos de un solo archivo como `onReject`, y `getFiles()` cuando esperas un lote. `UploadCompleteEvent` es la excepción; tiene sus propios accesores `getUploadedFiles()` y `getFailedFiles()` ya que el resultado del lote se divide entre éxitos y fracasos.

### Acciones del usuario {#user-actions}

Estos se disparan en respuesta a algo que el usuario hace en el componente en su totalidad. No dicen nada sobre el progreso de transferencia, solo que el usuario ha hecho algo a lo que la aplicación podría querer reaccionar.

| Evento | Se dispara |
| --- | --- |
| `UploadChangeEvent` | Cuando la lista de archivos seleccionados cambia |
| `UploadEvent` | Cuando el usuario hace clic en **Cargar** y los archivos llegan al servidor |
| `UploadCancelEvent` | Cuando el usuario hace clic en **Cancelar** |
| `UploadFilterChangeEvent` | Cuando cambia el filtro activo |

```java
upload.onChange(e -> {
    // Se dispara cada vez que cambia la lista de archivos seleccionados.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Se dispara cuando se activa la carga; los archivos han llegado al servidor.
});
```

`UploadEvent` y `UploadCompleteEvent` se ven similares a primera vista, pero responden a preguntas diferentes. `UploadEvent` se dispara cuando el usuario activa explícitamente la carga (o `setAutoUpload()` la activa en su nombre), y es el lugar natural para persistir o entregar los archivos cargados. `UploadCompleteEvent` se dispara una vez que se ha completado la transferencia de cada archivo en cola, y es el gancho adecuado para actualizaciones de interfaz de usuario "el lote está terminado".

### Transferencia por archivo {#per-file-transfer}

Estos se disparan una vez por archivo, mientras se está llevando a cabo una transferencia o justo después de que falla. Úsalos cuando la interfaz de usuario necesita reflejar el estado de archivos individuales en lugar del lote.

| Evento | Se dispara |
| --- | --- |
| `UploadProgressEvent` | Mientras se transfiere un solo archivo |
| `UploadErrorEvent` | Cuando falla la transferencia de un solo archivo |
| `UploadRejectEvent` | Cuando un archivo seleccionado o deslizado no cumple con las restricciones configuradas |

```java
upload.onProgress(e -> {
    // Se dispara repetidamente durante la transferencia de un solo archivo.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Se dispara cuando un archivo es rechazado por razones de tamaño, conteo o filtro.
    String reason = e.getMessage();
});
```

Dentro de este grupo, `UploadRejectEvent` es el que se destaca. Se dispara antes de que se transfieran bytes, cuando un archivo no cumple con una restricción del lado del cliente como `setMaxFileSize` o `setMaxFiles`. `UploadErrorEvent`, en contraste, se dispara después de que haya comenzado la transferencia y algo haya salido mal en el camino hacia el servidor.

### Todo el lote {#whole-batch}

Estos se disparan en el lote en lugar de en un solo archivo. Úsalos para UI agregadas como una barra de progreso general o un mensaje "hecho" que resume toda la selección.

| Evento | Se dispara |
| --- | --- |
| `UploadListProgressEvent` | Junto con `UploadProgressEvent`, con el estado de toda la lista |
| `UploadCompleteEvent` | Una vez por lote, cuando cada archivo ha terminado de transferirse |

```java
upload.onComplete(e -> {
    // Se dispara una vez cuando todo el lote está terminado.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` y `onListProgress` cubren la misma transferencia desde dos ángulos. `onProgress` es por archivo y es el gancho adecuado cuando cada archivo tiene su propia interfaz de progreso. `onListProgress` se dispara junto con él con contadores agregados (`getListTotal`, `getListRemaining`, `getListProgress`) para un único indicador de lote.

En el siguiente ejemplo, `onChange`, `onListProgress`, y `onComplete` impulsan una barra de progreso y una línea de estado que se actualizan a medida que la lista de archivos cambia y a medida que los archivos se transfieren.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
]}
height='450px'
/>

## Internacionalización (i18n) {#internationalization-i18n}

Las etiquetas y mensajes dentro del componente son personalizables a través del paquete `FileUploadI18n`. El tipo de paquete mantiene el nombre `FileUploadI18n` porque se comparte con el modal [`FileUploadDialog`](/docs/components/option-dialogs/file-upload).

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Enviar");
bundle.setCancel("Descartar");
bundle.setDropFile("Suelta el archivo aquí");
upload.setI18n(bundle);
```

## Temas {#themes}

`UploadTheme` refleja la paleta de temas estándar de DWC e incluye variantes delineadas para un peso visual más ligero. Los temas se aplican a los botones de selección, carga y cancelar. La lista y el área de soltar mantienen un estilo neutral independientemente del tema.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

La demostración a continuación muestra el tema `PRIMARY` combinado con el preset `INLINE`.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Estilizando {#styling}

<TableBuilder name="Upload" />
