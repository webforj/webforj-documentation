---
title: Drawer
sidebar_position: 35
_i18n_hash: 73da264dca1e3f8cfd58b697e3e9d0dc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

El cajón es un contenedor que se desliza hacia dentro del área de visualización para exponer opciones e información adicional. Se pueden crear múltiples cajones en una aplicación, y se apilarán uno encima del otro.

El componente Drawer se puede utilizar en muchas situaciones diferentes, como al proporcionar un menú de navegación que se puede alternar, un panel que muestra información suplementaria o contextual, o para optimizar el uso en un dispositivo móvil. El siguiente ejemplo mostrará una aplicación móvil que utiliza el componente webforJ AppLayout y muestra un cajón "Bienvenida Popup" en la parte inferior cuando se carga por primera vez. Además, un componente de cajón navegacional se puede alternar en la aplicación al hacer clic en el menú hamburguesa.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Usos {#usages}

1. **Menú de Navegación**: Un uso común de un componente de cajón es como menú de navegación. Proporciona una forma eficiente de espacio para mostrar enlaces a varias secciones o páginas de tu aplicación, especialmente en diseños móviles o responsivos. Los usuarios pueden abrir y cerrar el cajón para acceder a las opciones de navegación sin desordenar el área de contenido principal.

2. **Filtro y Barra Lateral**: Un cajón se puede utilizar como filtro o barra lateral en aplicaciones que muestran una lista de elementos. Los usuarios pueden expandir el cajón para revelar opciones de filtro, controles de ordenación, o información adicional relacionada con los elementos de la lista. Esto mantiene el contenido principal enfocado en la lista mientras proporciona funciones avanzadas de una manera accesible.

3. **Perfil de Usuario o Configuración**: Puedes usar un cajón para mostrar información del perfil del usuario o configuraciones de la aplicación. Esto mantiene dicha información fácilmente accesible pero oculta cuando no es necesaria, manteniendo una interfaz limpia y ordenada. Los usuarios pueden abrir el cajón para actualizar sus perfiles o ajustar configuraciones.

4. **Notificaciones**: Para aplicaciones con notificaciones o alertas, un cajón puede deslizarse para mostrar nuevos mensajes o actualizaciones. Los usuarios pueden revisar y descartar rápidamente las notificaciones sin salir de su vista actual.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Personalización {#customization}

Existen varias propiedades que permiten la personalización de varios atributos del componente Drawer. Esta sección describe esas propiedades con ejemplos de su modificación.

## Autofocus {#autofocus}

La propiedad Auto-Focus está diseñada para mejorar la accesibilidad y usabilidad al enfocar automáticamente el primer elemento dentro de un cajón cuando se abre. Esta característica elimina la necesidad de que los usuarios naveguen manualmente al elemento deseado, ahorrando tiempo y esfuerzo.

Cuando se activa el cajón para abrirse, ya sea a través de un evento, por defecto o cualquier otra interacción, el enfoque del usuario se dirige al primer elemento dentro del cajón. Este primer elemento podría ser un botón, un enlace, una opción de menú, o cualquier otro elemento que pueda recibir el enfoque.

:::tip
Al enfocar automáticamente el primer elemento, el desarrollador asegura que los usuarios puedan interactuar de inmediato con la opción más relevante o utilizada sin tener que tabular o desplazarse por todo el cajón. Este comportamiento optimiza la experiencia del usuario y promueve una navegación eficiente dentro de la interfaz de usuario.
:::

Esta propiedad también puede ser particularmente beneficiosa para personas que dependen de la navegación por teclado o tecnologías asistivas como lectores de pantalla. Proporciona un punto de partida claro dentro del cajón y permite a los usuarios acceder a la funcionalidad deseada sin entradas manuales innecesarias.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## Etiqueta {#label}

La propiedad Drawer Label es una característica diseñada para mejorar la accesibilidad y proporcionar un contexto descriptivo para un cajón dentro de una interfaz de usuario. Esta propiedad permite a los desarrolladores asignar una etiqueta a un cajón, principalmente para fines de accesibilidad, asegurando que los lectores de pantalla y otras tecnologías asistivas puedan transmitir con precisión el propósito y contenido del cajón a los usuarios.

Cuando se utiliza la propiedad Drawer Label, la etiqueta asignada se convierte en una parte integral de la infraestructura de accesibilidad del cajón. Permite a los usuarios que dependen de tecnologías asistivas comprender la función del cajón y navegar por la interfaz de manera más efectiva.

Al proporcionar una etiqueta para el cajón, los desarrolladores aseguran que los lectores de pantalla anuncian el propósito del cajón a los usuarios con discapacidades visuales. Esta información empodera a las personas para que tomen decisiones informadas sobre la interacción con el cajón, ya que pueden entender su contenido y relevancia dentro de la interfaz de usuario más amplia.

La propiedad Label se puede personalizar para adaptarse al contexto específico y los requisitos de diseño de la aplicación. Los desarrolladores tienen la flexibilidad de proporcionar etiquetas concisas y descriptivas que representen con precisión el contenido o la funcionalidad del cajón.

## Ubicación {#placement}

La propiedad de ubicación del componente de interfaz de usuario Drawer permite a los desarrolladores especificar la posición y alineación del cajón dentro del área de visualización. Esta propiedad ofrece una variedad de valores de enumeración que brindan flexibilidad para determinar dónde aparece el cajón en relación con el contenido principal.

Los valores de enumeración disponibles para la propiedad de ubicación son los siguientes:

- **TOP**: Este valor coloca el cajón en la parte superior del área de visualización, permitiendo que ocupe la región más alta.

- **TOP_CENTER**: Con este valor, el cajón se posiciona en el centro de la parte superior del área de visualización. Está alineado horizontalmente en el medio, creando un diseño equilibrado.

- **BOTTOM**: Al usar este valor, el cajón se sitúa en la parte inferior del área de visualización, apareciendo debajo del contenido principal.

- **BOTTOM_CENTER**: Este valor centra el cajón horizontalmente en la parte inferior del área de visualización. Proporciona una composición visualmente equilibrada.

- **LEFT**: Seleccionar este valor hace que el cajón se posicione en el lado izquierdo del área de visualización, adyacente al contenido principal.

- **RIGHT**: Al utilizar este valor, el cajón se coloca en el lado derecho del área de visualización, manteniendo una proximidad cercana al contenido principal.

La propiedad de ubicación permite a los desarrolladores elegir la posición más apropiada para el cajón en función de los requisitos específicos de diseño y experiencia del usuario. Los valores de enumeración ofrecen una variedad de opciones de ubicación para acomodar diferentes diseños de interfaz y jerarquías visuales.

Al aprovechar la propiedad de ubicación, los desarrolladores pueden crear interfaces de usuario intuitivas y eficientes. Por ejemplo, colocar el cajón en el lado izquierdo o derecho permite un acceso rápido a funcionalidades o opciones de navegación adicionales, mientras que las ubicaciones superior o inferior son adecuadas para información contextual o contenido suplementario.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Estilo {#styling}

<TableBuilder name="Drawer" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia de usuario óptima al utilizar el componente `Drawer`, considera las siguientes mejores prácticas:

1. **Ubicación**: Decide si el cajón debe deslizarse desde la izquierda, derecha, arriba o abajo, según el diseño de tu aplicación y consideraciones de experiencia del usuario. Considera las preferencias del usuario y las convenciones de diseño.

2. **Accesibilidad**: Presta especial atención a la accesibilidad. Asegúrate de que los usuarios puedan abrir y cerrar el cajón usando controles de teclado y que los lectores de pantalla puedan anunciar su presencia y estado. Proporciona roles y etiquetas ARIA según sea necesario.

3. **Gestos de deslizamiento**: En dispositivos habilitados para táctil, apoya los gestos de deslizamiento para abrir y cerrar el cajón. Esta es una forma intuitiva para que los usuarios interactúen con él.
