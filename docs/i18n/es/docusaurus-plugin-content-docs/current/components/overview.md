---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 2af867ffb7bb39ed4624efa14b81d452
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Componentes UI | Componentes para la construcción de aplicaciones de interfaz de usuario</title>
</Head>

En webforJ, las aplicaciones se crean utilizando unidades modulares conocidas como Componentes, que facilitan el desarrollo rápido y eficiente de UI. El marco ofrece una variedad de componentes esenciales como botones, elementos de entrada y contenedores de diseño. Después de dominar los fundamentos, puedes consultar la [JavaDocs](https://javadoc.io/doc/com.webforj) para obtener un resumen detallado de todos los componentes y sus funcionalidades.

## Diseños {#layouts}

Los componentes de diseño proporcionan la base para estructurar interfaces de usuario, permitiendo a los desarrolladores organizar el contenido de manera eficiente. Estos componentes ofrecen diversas formas de controlar la disposición de los componentes secundarios, ya sea para diseños simples o complejos.

Los siguientes componentes de diseño están diseñados para manejar una amplia variedad de casos de uso, desde diseño responsivo hasta gestión avanzada de contenido.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Un componente contenedor que proporciona un diseño estructurado para la navegación de la aplicación de nivel superior y la organización del contenido.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Un componente contenedor horizontal que sostiene un conjunto de botones de acción, íconos u otros controles, utilizado típicamente para realizar tareas relacionadas con el contexto actual.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Un componente de diseño que organiza a sus hijos utilizando reglas de caja flexible (flexbox) para un diseño y alineación responsivos.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Un componente de diseño que organiza a sus hijos en múltiples columnas verticales, útil para crear formularios y estructuras tipo cuadrícula.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Un componente de diseño que divide el espacio disponible entre dos componentes secundarios, permitiendo a los usuarios redimensionarlos arrastrando la barra divisoria.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Un panel deslizante que se utiliza típicamente para la navegación lateral o para albergar contenido adicional que puede mostrarse u ocultarse.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Un componente de ventana modal que superpone contenido para mostrar información importante o solicitar interacción del usuario, a menudo requiriendo acción del usuario para cerrar.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Un componente que proporciona una UI preconstruida para la autenticación de usuarios, típicamente incluyendo campos para nombre de usuario y contraseña junto con un botón de envío.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Un conjunto de paneles colapsables apilados verticalmente, cada uno con un encabezado clickeable que alterna la visibilidad de su contenido.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Un componente contenedor que organiza contenido en múltiples pestañas, permitiendo a los usuarios alternar entre diferentes vistas o secciones.</p>
  </GalleryCard>
</GalleryGrid>

## Entrada de datos {#data-entry}

Los componentes de entrada de datos proporcionan herramientas esenciales para capturar la entrada del usuario y gestionar interacciones dentro de tu aplicación. Estos componentes son versátiles, facilitando la construcción de formularios interactivos y la recopilación de diversos tipos de datos.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Un componente de entrada de una sola línea para ingresar y editar datos de texto.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Un componente de entrada de texto que restringe la entrada del usuario a un formato o patrón específico, utilizado típicamente para campos como números de teléfono, fechas o números de tarjetas de crédito.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Un componente que proporciona un campo de entrada basado en el navegador para ingresar valores numéricos, con controles integrados para incrementar o decrementar el valor.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Un componente de entrada numérica que restringe la entrada del usuario a un formato o patrón numérico específico, asegurando una entrada válida de números como para divisas, porcentajes u otros números formateados.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Un componente de entrada de una sola línea para ingresar y enmascarar datos de contraseña de manera segura.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Un componente que proporciona un selector de fecha basado en el navegador para seleccionar una fecha a través de un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Un componente de entrada de fecha que impone un formato o patrón de fecha específico, asegurando que el usuario ingrese una fecha válida de acuerdo con la máscara definida.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Un componente que proporciona un selector de hora basado en el navegador para seleccionar un valor de tiempo a través de un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Un componente de entrada de tiempo que impone un formato o patrón de tiempo específico, asegurando que el usuario ingrese un tiempo válido de acuerdo con la máscara definida.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Un componente que proporciona un selector de fecha y hora basado en el navegador para seleccionar tanto la fecha como la hora a través de un único campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Un componente que proporciona un selector de color basado en el navegador, permitiendo a los usuarios seleccionar un color a través de un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Un componente de entrada de texto multilinea que permite a los usuarios ingresar o editar bloques más grandes de texto.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Un componente que representa una opción binaria, permitiendo a los usuarios alternar entre un estado marcado (verdadero) o no marcado (falso).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Un componente que permite a los usuarios seleccionar una única opción de un grupo de elecciones mutuamente exclusivas.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Un componente de alternancia que permite a los usuarios cambiar entre dos estados, como encendido/apagado o verdadero/falso, con una acción deslizante.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Un componente que proporciona una lista desplegable de opciones predefinidas, permitiendo a los usuarios seleccionar una opción de la lista.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Un componente que combina una lista desplegable con una entrada de texto editable, permitiendo a los usuarios seleccionar una opción de la lista o ingresar un valor personalizado.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Un componente que muestra una lista desplazable de opciones, permitiendo a los usuarios seleccionar uno o más elementos de la lista.</p>
  </GalleryCard>
</GalleryGrid>

## Diálogos de opciones {#option-dialogs}

Los diálogos de opciones proporcionan una forma de presentar a los usuarios elecciones o solicitarles confirmación antes de proceder con una acción. Estos componentes son esenciales para crear flujos de trabajo interactivos y basados en decisiones, permitiendo a los usuarios confirmar, cancelar o elegir entre diversas opciones de manera clara y estructurada.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Un componente de diálogo utilizado para mostrar mensajes informativos o alertas al usuario, típicamente con un solo botón `OK` para reconocer el mensaje.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Un componente de diálogo que pregunta al usuario si desea confirmar o cancelar una acción, proporcionando típicamente botones de `Sí` y `No` o `OK` y `Cancelar`.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Un componente de diálogo que solicita al usuario que ingrese texto o datos, típicamente proporcionando un campo de entrada junto con botones de acción como `OK` y `Cancelar`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Un componente de diálogo que permite a los usuarios explorar y seleccionar archivos del sistema de archivos del servidor.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Un componente de diálogo que permite a los usuarios subir archivos desde su sistema de archivos local a la aplicación.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Un componente de diálogo que permite a los usuarios guardar un archivo en una ubicación especificada en el sistema de archivos del servidor.</p>
  </GalleryCard>
</GalleryGrid>

## Interacción y visualización {#interaction-and-display}

Esta categoría incluye componentes que facilitan las interacciones del usuario y muestran visualmente datos o estados de la aplicación. Estos componentes ayudan a los usuarios a navegar por la aplicación, activar acciones y comprender el progreso o resultados a través de elementos visuales dinámicos.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Un componente utilizado para mostrar datos en un formato tabular estructurado con filas y columnas, soportando características como clasificación y paginación.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Un componente que se integra con Google Charts para mostrar varios tipos de gráficos y representaciones visuales de datos en la aplicación.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Un componente clickeable que activa una acción o evento al ser presionado.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Un componente de notificación liviano y no bloqueante que muestra brevemente un mensaje al usuario antes de desaparecer automáticamente.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Un componente que muestra mensajes importantes o advertencias en un formato notable para captar la atención del usuario.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Un pequeño componente de etiqueta para mostrar conteos, estados o metadatos breves, con soporte para temas, tamaños e íconos.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Un componente que utiliza la API de Notificación nativa del navegador para alertar a los usuarios con notificaciones personalizadas de escritorio.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Un componente de paginación personalizable para navegar a través de conjuntos de datos, soportando diseños con botones de primero, último, siguiente, anterior y campos de salto rápido.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Un componente que representa visualmente el progreso de una tarea o proceso, típicamente mostrado como una barra horizontal que se llena a medida que se avanza.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Un componente que permite a los usuarios seleccionar un valor de un rango definido arrastrando un control a lo largo de una pista.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Un indicador visual a nivel de aplicación, típicamente un spinner, que señala que un proceso global está en curso.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Un indicador de carga localizado que se muestra dentro de un componente padre específico, indicando que el contenido o los datos se están cargando en esa sección.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Un componente que muestra una animación de rotación, utilizado típicamente para indicar que un proceso o acción está en progreso.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Un componente que proporciona un menú de navegación para la aplicación, típicamente utilizado para listar enlaces o elementos de navegación para cambiar entre diferentes secciones o vistas.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Un componente que muestra un símbolo o imagen gráfica, a menudo utilizado para representar una acción, estado o categoría en la interfaz de usuario.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Un componente que simula una interfaz de línea de comandos (CLI) dentro de la aplicación, permitiendo a los usuarios ingresar y ejecutar comandos basados en texto.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Un componente que carga más elementos al desplazarse, muestra un cargador y rastrea cuándo todo el contenido se ha recuperado.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Un componente que permite una interacción de arrastrar para refrescar dentro de contenedores desplazables—ideal para cargar datos dinámicos.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Un componente para mostrar datos jerárquicos, permitiendo a los usuarios expandir, colapsar e interactuar con elementos anidados.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Un componente para mostrar imágenes de perfil de usuario o iniciales, con soporte para diferentes tamaños, formas y temas.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Un componente para mostrar contenido en markdown con renderizado progresivo carácter por carácter, ideal para interfaces de chat de IA y texto en streaming.</p>
  </GalleryCard>
  
</GalleryGrid>
