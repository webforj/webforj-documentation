---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: c463da47b9db7f6619c8723b6105f9bd
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Componentes de IU | Componentes para la Construcción de Aplicaciones de Interfaz de Usuario</title>
</Head>

En webforJ, las aplicaciones se crean utilizando unidades modulares conocidas como Componentes, que facilitan un desarrollo de IU rápido y eficiente. El marco ofrece una gama de componentes esenciales, como botones, elementos de entrada y contenedores de diseño. Después de dominar los fundamentos, puedes consultar la [JavaDocs](https://javadoc.io/doc/com.webforj) para obtener un resumen detallado de todos los componentes y sus funcionalidades.

## Diseños {#layouts}

Los componentes de diseño proporcionan la base para estructurar interfaces de usuario, permitiendo a los desarrolladores organizar el contenido de manera eficiente. Estos componentes ofrecen diversas formas de controlar la disposición de los componentes secundarios, ya sea para diseños simples o complejos.

Los siguientes componentes de diseño están diseñados para manejar una amplia gama de casos de uso, desde diseño responsivo hasta gestión avanzada de contenido.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Un componente contenedor que proporciona un diseño estructurado para la navegación y organización del contenido de la aplicación de nivel superior.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Un componente contenedor horizontal que alberga un conjunto de botones de acción, íconos u otros controles, utilizado normalmente para realizar tareas relacionadas con el contexto actual.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Un componente de diseño que organiza a sus hijos utilizando reglas de caja flexible (flexbox) para un diseño y alineación responsivos.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Un componente de diseño que organiza a sus hijos en múltiples columnas verticales, útil para crear formularios y estructuras en forma de cuadrícula.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Un componente de diseño que divide el espacio disponible entre dos componentes secundarios, permitiendo a los usuarios redimensionarlos arrastrando la barra divisoria.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Un componente de panel deslizante normalmente utilizado para navegación lateral o para albergar contenido adicional que puede mostrarse u ocultarse.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Un componente de ventana modal que superpone contenido para mostrar información importante o promover la interacción del usuario, a menudo requiriendo acción del usuario para cerrar.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Un componente que proporciona una IU preconstruida para la autenticación del usuario, que normalmente incluye campos para nombre de usuario y contraseña junto con un botón de enviar.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Un componente contenedor que organiza contenido en múltiples pestañas, permitiendo a los usuarios alternar entre diferentes vistas o secciones.</p>
  </GalleryCard>
</GalleryGrid>

## Entrada de datos {#data-entry}

Los componentes de entrada de datos proporcionan herramientas esenciales para capturar la entrada del usuario y gestionar interacciones dentro de tu aplicación. Estos componentes son versátiles, facilitando la construcción de formularios interactivos y la recolección de varios tipos de datos.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Un componente de entrada de una sola línea para ingresar y editar datos de texto.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Un componente de entrada de texto que restringe la entrada del usuario a un formato o patrón específico, utilizado normalmente para campos como números de teléfono, fechas o números de tarjetas de crédito.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Un componente que proporciona un campo de entrada basado en el navegador para ingresar valores numéricos, con controles integrados para incrementar o decrementar el valor.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Un componente de entrada numérica que restringe la entrada del usuario a un formato o patrón numérico específico, asegurando la entrada de números válidos, como para moneda, porcentajes o otros números formateados.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Un componente de entrada de una sola línea para ingresar y enmascarar datos de contraseña de forma segura.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Un componente que proporciona un selector de fecha basado en el navegador para seleccionar una fecha a través de un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Un componente de entrada de fecha que impone un formato o patrón de fecha específico, asegurando que el usuario ingrese una fecha válida de acuerdo con la máscara definida.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Un componente que proporciona un selector de hora basado en el navegador para seleccionar un valor de tiempo a través de un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Un componente de entrada de tiempo que impone un formato o patrón de tiempo específico, asegurando que el usuario ingrese un tiempo válido de acuerdo con la máscara definida.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Un componente que proporciona un selector de fecha y hora basado en el navegador para seleccionar tanto la fecha como la hora a través de un solo campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Un componente que proporciona un selector de color basado en el navegador, permitiendo a los usuarios seleccionar un color de un campo de entrada.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Un componente de entrada de texto de múltiples líneas que permite a los usuarios ingresar o editar bloques de texto más grandes.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Un componente que representa una opción binaria, permitiendo a los usuarios alternar entre un estado marcado (verdadero) o desmarcado (falso).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Un componente que permite a los usuarios seleccionar una única opción de un grupo de elecciones mutuamente excluyentes.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Un componente de alternancia que permite a los usuarios cambiar entre dos estados, como encendido/apagado o verdadero/falso, con una acción de deslizar.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Un componente que proporciona una lista desplegable de opciones predeterminadas, permitiendo a los usuarios seleccionar una opción de la lista.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Un componente que combina una lista desplegable con una entrada de texto editable, permitiendo a los usuarios seleccionar una opción de la lista o ingresar un valor personalizado.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Un componente que muestra una lista desplazable de opciones, permitiendo a los usuarios seleccionar uno o más elementos de la lista.</p>
  </GalleryCard>
</GalleryGrid>

## Diálogos de opciones {#option-dialogs}

Los diálogos de opciones proporcionan una forma de presentar a los usuarios elecciones o solicitarles confirmación antes de proceder con una acción. Estos componentes son esenciales para crear flujos de trabajo interactivos y basados en decisiones, permitiendo a los usuarios confirmar, cancelar o elegir entre varias opciones de manera clara y estructurada.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Un componente de diálogo utilizado para mostrar mensajes informativos o alertas al usuario, normalmente con un único botón `OK` para reconocer el mensaje.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Un componente de diálogo que pide al usuario que confirme o cancele una acción, proporcionando normalmente botones `Sí` y `No` o `OK` y `Cancelar`.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Un componente de diálogo que solicita al usuario que ingrese texto o datos, típicamente proporcionando un campo de entrada junto con botones de acción como `OK` y `Cancelar`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Un componente de diálogo que permite a los usuarios navegar y seleccionar archivos del sistema de archivos del servidor.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Un componente de diálogo que permite a los usuarios cargar archivos desde su sistema de archivos local a la aplicación.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Un componente de diálogo que permite a los usuarios guardar un archivo en una ubicación específica en el sistema de archivos del servidor.</p>
  </GalleryCard>
</GalleryGrid>

## Interacción y visualización {#interaction-and-display}

Esta categoría incluye componentes que facilitan interacciones del usuario y visualizan datos o estados de la aplicación. Estos componentes ayudan a los usuarios a navegar por la aplicación, desencadenar acciones y entender el progreso o los resultados a través de elementos visuales dinámicos.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p> Un componente utilizado para mostrar datos en un formato tabular estructurado con filas y columnas, soportando características como ordenamiento y paginación.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Un componente que se integra con Google Charts para mostrar varios tipos de gráficos y representaciones visuales de datos en la aplicación.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Un componente clickeable que desencadena una acción o evento cuando se presiona.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Un componente de notificación ligero y no bloqueante que muestra brevemente un mensaje al usuario antes de desaparecer automáticamente.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Un componente que muestra mensajes importantes o advertencias en un formato notable para captar la atención del usuario.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Un componente que aprovecha la API de Notificación nativa del navegador para alertar a los usuarios con notificaciones de escritorio personalizadas.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Un componente de paginación personalizable para navegar a través de conjuntos de datos, soportando diseños con botones de inicio, fin, siguiente, anterior y campos de salto rápido.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Un componente que representa visualmente el progreso de una tarea o proceso, típicamente mostrado como una barra horizontal que se llena a medida que se avanza.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Un componente que permite a los usuarios seleccionar un valor de un rango definido arrastrando un control a lo largo de una pista.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p> Un indicador visual a nivel de toda la aplicación, típicamente un spinner, que señala que un proceso global está en curso.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Un indicador de carga específico que se muestra dentro de un componente padre, indicando que el contenido o los datos se están cargando en esa sección.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Un componente que muestra una animación rotativa, típicamente utilizado para indicar que un proceso o acción está en progreso.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Un componente que proporciona un menú de navegación para la aplicación, utilizado normalmente para listar enlaces o elementos de navegación para alternar entre diferentes secciones o vistas.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Un componente que muestra un símbolo gráfico o imagen, a menudo utilizado para representar una acción, estado o categoría en la interfaz de usuario.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Un componente que simula una interfaz de línea de comandos (CLI) dentro de la aplicación, permitiendo a los usuarios ingresar y ejecutar comandos basados en texto.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Un componente que carga más elementos al desplazarse, muestra un cargador y rastrea cuándo se han recuperado todo el contenido.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Un componente que permite una interacción de arrastrar para refrescar dentro de contenedores desplazables, ideal para cargar datos dinámicos.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Un componente para mostrar datos jerárquicos, permitiendo a los usuarios expandir, colapsar e interactuar con elementos anidados.</p>
  </GalleryCard>
</GalleryGrid>
