---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 6bc148e8bcb06161115d0509601b516b
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

En webforj 25.00 y versiones posteriores, el componente **DesktopNotification** proporciona una interfaz sencilla para crear, mostrar y gestionar notificaciones de escritorio. Con un enfoque en una configuración mínima y manejo de eventos incorporado, el componente se puede utilizar cuando se necesita notificar a los usuarios sobre eventos en tiempo real (como nuevos mensajes, alertas o eventos del sistema) mientras están navegando por tu aplicación.

:::warning característica experimental
El componente `DesktopNotification` aún está en evolución y su API puede experimentar cambios a medida que madura. Para comenzar a utilizar esta función, asegúrate de incluir la siguiente dependencia en tu pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Requisitos previos
Antes de integrar el componente `DesktopNotification`, asegúrate de que:

- Tu aplicación se ejecute en un **contexto seguro** (HTTPS).
- El navegador no esté en modo incógnito o de navegación privada.
- El usuario haya interactuado con la aplicación (p. ej., hizo clic en un botón o presionó una tecla), ya que las notificaciones requieren un gesto del usuario para mostrarse.
- El usuario haya otorgado permisos para las notificaciones (esto se solicitará automáticamente si es necesario).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Uso básico {#basic-usage}

Hay múltiples formas de crear y mostrar una notificación. En la mayoría de los escenarios, el enfoque más simple es llamar a uno de los métodos estáticos `show` que encapsulan el ciclo de vida completo de la notificación.

### Ejemplo: Mostrando una notificación básica {#example-displaying-a-basic-notification}

```java
// Notificación básica con título y mensaje
DesktopNotification.show("Actualización disponible", "¡Tu descarga está completa!");
```

Esta línea crea una notificación con un título y un cuerpo, luego intenta mostrarla.

## Personalizando la notificación {#customizing-the-notification}

Existen varias opciones para personalizar el aspecto y la sensación de la notificación mostrada, dependiendo de las necesidades de la aplicación y el propósito de la notificación.

### Estableciendo un `Icono` personalizado {#setting-a-custom-icon}

Por defecto, la notificación utiliza el icono de la aplicación definido a través del [protocolo de iconos](../managing-resources/assets-protocols#the-icons-protocol). Puedes establecer un icono personalizado utilizando el método `setIcon`. El componente admite diferentes esquemas de URL:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Resuelto como una URL de contexto que apunta a la carpeta de recursos de la aplicación; la imagen está codificada en base64.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Resuelto como una URL de servidor web, dando una URL completamente calificada.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Resuelto como una URL de iconos.

**Ejemplo:**

```java
// Creando una notificación con una URL de icono personalizada
DesktopNotification notification = new DesktopNotification(
  "Recordatorio", "La reunión empieza en 10 minutos."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Eventos de notificación {#notification-events}

El `DesktopNotification` admite varios eventos del ciclo de vida, y se pueden adjuntar oyentes para manejar eventos, como cuando se muestra, cierra, hace clic en la notificación o se produce un error.

| Evento                  | Descripción                                           | Cuándo usar                                               |
|-------------------------|------------------------------------------------------|----------------------------------------------------------|
| **Abrir**               | Se activa cuando se muestra la notificación.        | Registrar la visualización de la notificación, actualizar UI, rastrear el compromiso.    |
| **Cerrar**              | Se activa cuando se cierra la notificación.         | Limpiar recursos, registrar despidos, ejecutar acciones posteriores.|
| **Error**               | Se activa cuando ocurre un error con la notificación o el usuario no otorgó permiso.| Manejar errores de manera adecuada, notificar al usuario, aplicar soluciones alternativas.  |
| **Clic**                | Se activa cuando el usuario hace clic en la notificación. | Navegar a una sección específica, registrar interacciones, volver a enfocar la aplicación. |

```java
DesktopNotification notification = new DesktopNotification("Alerta", "¡Tienes un nuevo mensaje!")

// Adjuntar un oyente de eventos para el evento de apertura
notification.onOpen(event -> {
  System.out.println("La notificación fue abierta por el usuario.");
});

// De manera similar, escuchar el evento de clic
notification.onClick(event -> {
  System.out.println("Notificación clicada.");
});
```

:::warning Comportamiento del clic
Las políticas de seguridad del navegador evitan que el evento de clic de la notificación traiga automáticamente la ventana o pestaña de tu aplicación al enfoque. Este comportamiento es impuesto por el navegador y no se puede anular programáticamente. Si tu aplicación requiere que la ventana esté enfocada, deberás instruir a los usuarios que hagan clic dentro de la aplicación después de interactuar con la notificación.
:::

## Consideraciones de seguridad y compatibilidad {#security-and-compatibility-considerations}

Al utilizar el componente **DesktopNotification**, ten en cuenta los siguientes puntos:

- **Contexto de seguridad:** Tu aplicación debe ser servida a través de HTTPS para asegurarse de que la mayoría de los navegadores modernos permitan notificaciones.
- **Requerimiento de gesto del usuario:** Las notificaciones solo se muestran después de una acción desencadenada por el usuario. Simplemente cargar una página no activará una notificación.
- **Limitaciones del navegador:** No todos los navegadores manejan los iconos personalizados o el comportamiento de enfoque de la misma manera. Por ejemplo, los iconos personalizados pueden no funcionar en Safari, mientras que el comportamiento de eventos puede variar en otros navegadores.
- **Permisos:** Siempre verifica que tu aplicación compruebe y solicite permisos de notificación al usuario de manera adecuada.

## Mejores prácticas de uso {#usage-best-practices}

Ten en cuenta las siguientes mejores prácticas mientras utilizas el componente `DesktopNotification` en tu aplicación:

- **Informa a tus usuarios:** Haz saber a los usuarios por qué se necesitan notificaciones y cómo pueden beneficiarse de ellas.
- **Proporciona soluciones alternativas:** Dado que algunos navegadores pueden restringir las notificaciones, considera formas alternativas de alertar a los usuarios (p. ej., mensajes dentro de la aplicación).
- **Manejo de errores:** Siempre registra un oyente de errores para manejar de manera adecuada los escenarios en los que las notificaciones no se pueden mostrar.
