---
title: Drawer
sidebar_position: 35
_i18n_hash: e3b531e5fb7f1554e035f4d05aad8512
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

El cajón es un contenedor que se desliza dentro del área de visualización para exponer opciones e información adicionales. Se pueden crear múltiples cajones en una aplicación, y estarán apilados uno encima del otro.

El componente Drawer se puede utilizar en muchas situaciones diferentes, como proporcionando un menú de navegación que se puede alternar, un panel que muestra información suplementaria o contextual, o para optimizar el uso en un dispositivo móvil. El siguiente ejemplo mostrará una aplicación móvil que utiliza el componente webforJ AppLayout y muestra un cajón de "Bienvenida" en la parte inferior cuando se carga por primera vez. Además, un componente de cajón de navegación se puede alternar en la aplicación haciendo clic en el menú de hamburguesa.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Usos {#usages}

1. **Menú de Navegación**: Un uso común de un componente de cajón es como menú de navegación. Proporciona una manera eficiente en espacio para mostrar enlaces a varias secciones o páginas de su aplicación, especialmente en diseños móviles o responsivos. Los usuarios pueden abrir y cerrar el cajón para acceder a opciones de navegación sin abarrotar el área de contenido principal.

2. **Filtro y Barra Lateral**: Un cajón se puede utilizar como filtro o barra lateral en aplicaciones que muestran una lista de elementos. Los usuarios pueden expandir el cajón para revelar opciones de filtrado, controles de ordenación o información adicional relacionada con los elementos de la lista. Esto mantiene el contenido principal enfocado en la lista mientras proporciona características avanzadas de manera accesible.

3. **Perfil del Usuario o Configuraciones**: Se puede utilizar un cajón para mostrar información del perfil del usuario o configuraciones de la aplicación. Esto mantiene dicha información fácilmente accesible pero oculta cuando no es necesaria, manteniendo una interfaz limpia y ordenada. Los usuarios pueden abrir el cajón para actualizar sus perfiles o ajustar configuraciones.

4. **Notificaciones**: Para aplicaciones con notificaciones o alertas, un cajón puede deslizarse para mostrar nuevos mensajes o actualizaciones. Los usuarios pueden revisar y eliminar rápidamente las notificaciones sin abandonar su vista actual.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Personalización {#customization}

Existen varias propiedades que permiten la personalización de diversos atributos del componente Drawer. Esta sección describe esas propiedades con ejemplos de su modificación.

## Autofocus {#autofocus}

La propiedad Auto-Focus está diseñada para mejorar la accesibilidad y usabilidad al enfocar automáticamente el primer elemento dentro de un cajón cuando se abre. Esta función elimina la necesidad de que los usuarios naveguen manualmente hasta el elemento deseado, ahorrando tiempo y esfuerzo.

Cuando el cajón se activa para abrir, ya sea a través de un evento, por defecto o cualquier otra interacción, el enfoque del usuario se dirige al primer elemento dentro del cajón. Este primer elemento podría ser un botón, un enlace, una opción de menú o cualquier otro elemento que pueda ser enfocado.

:::tip
Al enfocar automáticamente el primer elemento, el desarrollador asegura que los usuarios puedan interactuar de inmediato con la opción más relevante o utilizada sin tener que tabular o desplazarse por todo el cajón. Este comportamiento agiliza la experiencia del usuario y promueve una navegación eficiente dentro de la interfaz de usuario.
:::

Esta propiedad también puede ser particularmente beneficiosa para las personas que dependen de la navegación mediante teclado o tecnologías asistivas como lectores de pantalla. Proporciona un punto de partida claro dentro del cajón y permite a los usuarios acceder a la funcionalidad deseada sin entrada manual innecesaria.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## Label {#label}

La propiedad Drawer Label es una función diseñada para mejorar la accesibilidad y proporcionar contexto descriptivo para un cajón dentro de una interfaz de usuario. Esta propiedad permite a los desarrolladores asignar una etiqueta a un cajón, principalmente con fines de accesibilidad, asegurando que los lectores de pantalla y otras tecnologías asistivas puedan transmitir con precisión el propósito y contenido del cajón a los usuarios.

Cuando se utiliza la propiedad Drawer Label, la etiqueta asignada se convierte en una parte integral de la infraestructura de accesibilidad del cajón. Permite a los usuarios que dependen de tecnologías asistivas entender la función del cajón y navegar a través de la interfaz de manera más efectiva.

Al proporcionar una etiqueta para el cajón, los desarrolladores aseguran que los lectores de pantalla anuncien el propósito del cajón a los usuarios con discapacidad visual. Esta información empodera a las personas para tomar decisiones informadas sobre cómo interactuar con el cajón, ya que pueden entender su contenido y relevancia dentro de la interfaz de usuario más amplia.

La propiedad Label puede personalizarse para adaptarse al contexto y requisitos de diseño específicos de la aplicación. Los desarrolladores tienen la flexibilidad de proporcionar etiquetas concisas y descriptivas que representen con precisión el contenido o funcionalidad del cajón.

## Ubicación {#placement}

La propiedad de ubicación del componente de UI Drawer permite a los desarrolladores especificar la posición y alineación del cajón dentro del área de visualización. Esta propiedad ofrece una gama de valores enum que brindan flexibilidad para determinar dónde aparece el cajón en relación con el contenido principal.

Los valores enum disponibles para la propiedad de ubicación son los siguientes:

- **TOP**: Este valor coloca el cajón en la parte superior del área de visualización, permitiéndole ocupar la región más alta.

- **TOP_CENTER**: Con este valor, el cajón se posiciona en el centro de la parte superior del área de visualización. Está alineado horizontalmente en el medio, creando un diseño equilibrado.

- **BOTTOM**: Al usar este valor, el cajón se sitúa en la parte inferior del área de visualización, apareciendo debajo del contenido principal.

- **BOTTOM_CENTER**: Este valor centra el cajón horizontalmente en la parte inferior del área de visualización. Proporciona una composición visualmente equilibrada.

- **LEFT**: Al seleccionar este valor, el cajón se posiciona en el lado izquierdo del área de visualización, adyacente al contenido principal.

- **RIGHT**: Al usar este valor, el cajón se coloca en el lado derecho del área de visualización, manteniendo una proximidad cercana al contenido principal.

La propiedad de ubicación permite a los desarrolladores elegir la posición más apropiada para el cajón en función de los requisitos específicos de diseño y experiencia del usuario. Los valores enum ofrecen una variedad de opciones de ubicación para acomodar diferentes diseños de interfaz y jerarquías visuales.

Al aprovechar la propiedad de ubicación, los desarrolladores pueden crear interfaces de usuario intuitivas y eficientes. Por ejemplo, colocar el cajón en el lado izquierdo o derecho permite un acceso rápido a funcionalidades adicionales u opciones de navegación, mientras que las ubicaciones superior o inferior son adecuadas para información contextual o contenido complementario.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Estilo {#styling}

<TableBuilder name="Drawer" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `Drawer`, considere las siguientes mejores prácticas:

1. **Ubicación**: Decida si el cajón debe deslizarse desde la izquierda, derecha, arriba o abajo, según el diseño de su aplicación y las consideraciones de experiencia del usuario. Considere las preferencias del usuario y las convenciones de diseño.

2. **Accesibilidad**: Preste especial atención a la accesibilidad. Asegúrese de que los usuarios puedan abrir y cerrar el cajón utilizando controles de teclado y que los lectores de pantalla puedan anunciar su presencia y estado. Proporcione roles y etiquetas ARIA según sea necesario.

3. **Gestos de Deslizamiento**: En dispositivos habilitados para táctil, soporte gestos de deslizamiento para abrir y cerrar el cajón. Esta es una manera intuitiva para que los usuarios interactúen con él.
