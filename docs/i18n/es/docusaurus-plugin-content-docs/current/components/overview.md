---
title: Componentes de interfaz
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Explora el catálogo de componentes de interfaz de usuario de webforJ, que
  abarca diseño, entrada de datos, navegación, retroalimentación y
  visualización.
_i18n_title_hash: 45ac037def4dc03161f6bedffcc3b307
_i18n_description_hash: 97182b12d746c9ed301eeeced3480321
_i18n_hash: 9bc06f09a5141c15a7f4f21d22457571
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Java UI Components | webforJ</title>
</Head>

En webforJ, las aplicaciones se crean mediante unidades modulares conocidas como componentes, que facilitan el desarrollo rápido y eficiente de interfaces de usuario. El framework ofrece una variedad de componentes esenciales, como botones, elementos de entrada y contenedores de diseño. Una vez que domines los fundamentos, puedes consultar los [JavaDocs](https://javadoc.io/doc/com.webforj) para obtener una descripción detallada de todos los componentes y sus funcionalidades.

## Diseños {#layouts}

Los componentes de diseño proporcionan la base para estructurar las interfaces de usuario y permiten a los desarrolladores organizar el contenido de forma eficiente. Estos componentes ofrecen diversas maneras de controlar la disposición de los componentes secundarios, tanto en diseños sencillos como complejos.

Los siguientes componentes de diseño están pensados para cubrir una amplia variedad de casos de uso, desde el diseño adaptable hasta la gestión avanzada de contenido.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Un componente contenedor que proporciona un diseño estructurado para la navegación principal de la aplicación y la organización del contenido.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Un componente contenedor horizontal que contiene un conjunto de botones de acción, iconos u otros controles, y que suele utilizarse para realizar tareas relacionadas con el contexto actual.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Un componente de diseño que organiza sus elementos secundarios mediante las reglas de flexbox para ofrecer un diseño adaptable y una alineación adecuada.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Un componente de diseño que organiza sus elementos secundarios en varias columnas verticales, útil para crear formularios y estructuras similares a cuadrículas.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Un componente de diseño que divide el espacio disponible entre dos componentes secundarios y permite a los usuarios cambiar su tamaño arrastrando la barra divisoria.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Un componente de panel deslizante que suele utilizarse para la navegación lateral o para alojar contenido adicional que se puede mostrar u ocultar.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Un componente de ventana modal que se superpone al contenido para mostrar información importante o solicitar la interacción del usuario, y que a menudo requiere una acción del usuario para cerrarse.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Un componente que ofrece una interfaz de usuario prediseñada para la autenticación de usuarios, normalmente con campos para el nombre de usuario y la contraseña, además de un botón de envío.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Un conjunto de paneles plegables apilados verticalmente, cada uno con un encabezado en el que se puede hacer clic para alternar la visibilidad del contenido del panel.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Un componente contenedor que organiza el contenido en varias pestañas y permite a los usuarios cambiar entre distintas vistas o secciones.</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Una superficie que agrupa contenido y acciones relacionados, con secciones para elementos multimedia, encabezados, contenido principal y pies de página.</p>
  </GalleryCard>
</GalleryGrid>

## Entrada de datos {#data-entry}

Los componentes de entrada de datos proporcionan herramientas esenciales para capturar la información introducida por el usuario y gestionar las interacciones dentro de la aplicación. Estos componentes son versátiles y facilitan la creación de formularios interactivos y la recopilación de distintos tipos de datos.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Un componente de entrada de una sola línea para introducir y editar datos de texto.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Un componente de entrada de texto que restringe lo que el usuario puede introducir a un formato o patrón específico. Suele utilizarse en campos como los de números de teléfono, fechas o números de tarjetas de crédito.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Un componente que proporciona un campo de entrada predeterminado del navegador para introducir valores numéricos, con controles integrados para aumentar o disminuir el valor.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Un componente de entrada numérica que restringe lo que el usuario puede introducir a un formato o patrón numérico específico, lo que garantiza la validez de valores como monedas, porcentajes u otros números con formato.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Un componente de entrada de una sola línea para introducir y enmascarar contraseñas de forma segura.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Un componente que proporciona un selector de fecha predeterminado del navegador para elegir una fecha mediante un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Un componente de entrada de fecha que aplica un formato o patrón específico para garantizar que el usuario introduzca una fecha válida según la máscara definida.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Un componente que proporciona un selector de hora predeterminado del navegador para elegir un valor de hora mediante un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Un componente de entrada de hora que aplica un formato o patrón específico para garantizar que el usuario introduzca una hora válida según la máscara definida.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Un componente que proporciona un selector de fecha y hora predeterminado del navegador para elegir ambos valores mediante un único campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Un componente que proporciona un selector de color predeterminado del navegador para que los usuarios elijan un color desde un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Un componente de entrada de texto multilínea que permite a los usuarios introducir o editar bloques de texto más extensos.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Un componente que representa una opción binaria y permite a los usuarios alternar entre los estados marcado (true) y desmarcado (false).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Un componente que permite a los usuarios seleccionar una única opción de un grupo de opciones mutuamente excluyentes.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Un componente de alternancia que permite a los usuarios cambiar entre dos estados, como activado/desactivado o true/false, mediante un movimiento deslizante.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Un componente que proporciona una lista desplegable de opciones predefinidas para que los usuarios seleccionen una de ellas.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Un componente que combina una lista desplegable con un campo de texto editable y permite a los usuarios seleccionar una opción de la lista o introducir un valor personalizado.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Un componente que muestra una lista de opciones desplazable y permite a los usuarios seleccionar uno o varios elementos.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Un selector de archivos integrado que permite a los usuarios elegir uno o varios archivos de su equipo local y subirlos al servidor, con funciones de arrastrar y soltar, filtros y seguimiento de eventos para cada archivo.</p>
  </GalleryCard>
</GalleryGrid>

## Cuadros de diálogo de opciones {#option-dialogs}

Los cuadros de diálogo de opciones permiten presentar alternativas a los usuarios o solicitarles confirmación antes de continuar con una acción. Estos componentes son esenciales para crear flujos de trabajo interactivos basados en decisiones, ya que permiten a los usuarios confirmar, cancelar o elegir entre varias opciones de forma clara y estructurada.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Un componente de diálogo que muestra mensajes informativos o alertas al usuario, normalmente con un único botón `OK` para confirmar que ha visto el mensaje.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Un componente de diálogo que solicita al usuario confirmar o cancelar una acción, normalmente con botones `Yes` y `No` o `OK` y `Cancel`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Un componente de diálogo que solicita al usuario introducir texto o datos, normalmente mediante un campo de entrada y botones de acción como `OK` y `Cancel`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Un componente de diálogo que permite a los usuarios buscar y seleccionar archivos del sistema de archivos del servidor.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Un componente de diálogo que permite a los usuarios subir archivos desde su sistema de archivos local a la aplicación.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Un componente de diálogo que permite a los usuarios guardar un archivo en una ubicación especificada del sistema de archivos del servidor.</p>
  </GalleryCard>
</GalleryGrid>

## Interacción y visualización {#interaction-and-display}

Esta categoría incluye componentes que facilitan las interacciones del usuario y muestran visualmente datos o estados de la aplicación. Estos componentes ayudan a los usuarios a navegar por la aplicación, ejecutar acciones y comprender el progreso o los resultados mediante elementos visuales dinámicos.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Un componente que muestra datos en un formato tabular estructurado, con filas y columnas, y que admite funciones como la ordenación y la paginación.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Un componente que se integra con Google Charts para mostrar diversos tipos de gráficos y representaciones visuales de datos en la aplicación.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Un componente en el que se puede hacer clic para activar una acción o un evento al pulsarlo.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Un componente de notificación ligero y no bloqueante que muestra brevemente un mensaje al usuario y luego desaparece automáticamente.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Un componente que muestra mensajes importantes o advertencias en un formato llamativo para captar la atención del usuario.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Un componente de etiqueta pequeña para mostrar recuentos, estados o metadatos breves, con compatibilidad para temas, tamaños e iconos.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Un componente que utiliza la API de notificaciones nativa del navegador para avisar a los usuarios mediante notificaciones de escritorio personalizadas.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Un componente de paginación personalizable para navegar por conjuntos de datos, con diseños que admiten botones para ir al primero, al último, al siguiente y al anterior, además de campos para saltar rápidamente a una página.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Un componente que representa visualmente el progreso de una tarea o proceso, normalmente mediante una barra horizontal que se va llenando a medida que avanza.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Un componente que permite a los usuarios seleccionar un valor de un intervalo definido arrastrando un control a lo largo de una pista.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Un indicador visual para toda la aplicación, normalmente un círculo giratorio, que señala que hay un proceso global en curso.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Un indicador de carga delimitado que se muestra dentro de un componente principal específico e indica que se está cargando contenido o datos en esa sección.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Un componente que muestra una animación giratoria y que suele utilizarse para indicar que hay un proceso o una acción en curso.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Un componente que proporciona un menú de navegación para la aplicación, normalmente utilizado para mostrar enlaces o elementos de navegación que permiten cambiar entre distintas secciones o vistas.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Un componente que muestra un símbolo gráfico o una imagen, a menudo utilizado para representar una acción, un estado o una categoría en la interfaz de usuario.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Un componente que simula una interfaz de línea de comandos (CLI) dentro de la aplicación y permite a los usuarios introducir y ejecutar comandos de texto.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Un componente que carga más elementos al desplazarse, muestra un indicador de carga y registra cuándo se ha obtenido todo el contenido.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Un componente que permite actualizar el contenido tirando hacia abajo dentro de contenedores desplazables, ideal para cargar datos dinámicamente.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Un componente para mostrar datos jerárquicos que permite a los usuarios expandir, contraer e interactuar con elementos anidados.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Un componente para mostrar fotos de perfil o iniciales de usuarios, con compatibilidad para distintos tamaños, formas y temas.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Un componente para mostrar contenido Markdown con renderizado progresivo, carácter por carácter, ideal para interfaces de chat con IA y texto transmitido en tiempo real.</p>
  </GalleryCard>

</GalleryGrid>
