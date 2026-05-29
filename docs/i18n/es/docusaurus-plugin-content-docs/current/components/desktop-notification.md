---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: b7e4651594dee824d6bcdf1ac32e1998
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

El componente `DesktopNotification` muestra notificaciones nativas de escritorio fuera de la ventana del navegador. Se puede usar para alertar a los usuarios sobre eventos en tiempo real como nuevos mensajes, alertas del sistema o cambios de estado mientras están usando tu aplicación.

<!-- INTRO_END -->

## Configuración y requisitos previos {#setup-and-prerequisites}

<ExperimentalWarning />

Para comenzar a usar esta función, incluye la siguiente dependencia en tu pom.xml:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Antes de integrar el componente `DesktopNotification`, asegúrate de que:

- Tu aplicación se ejecute en un **contexto seguro** (HTTPS).
- El navegador no esté en modo incógnito o de navegación privada.
- El usuario haya interactuado con la aplicación (por ejemplo, haciendo clic en un botón o presionando una tecla), ya que las notificaciones requieren un gesto del usuario para mostrarse.
- El usuario haya concedido permisos para las notificaciones (esto se solicitará automáticamente si es necesario).

## Uso básico {#basic-usage}

Hay múltiples formas de crear y mostrar una notificación. En la mayoría de los escenarios, el enfoque más simple es llamar a uno de los métodos estáticos `show` que encapsulan todo el ciclo de vida de la notificación.

### Ejemplo: Mostrando una notificación básica {#example-displaying-a-basic-notification}

```java
// Notificación básica con título y mensaje
DesktopNotification.show("Actualización disponible", "¡Tu descarga está completa!");
```

Esta línea crea una notificación con un título y un cuerpo, y luego intenta mostrarla.

## Personalizando la notificación {#customizing-the-notification}

Hay varias opciones para personalizar la apariencia y la sensación de la notificación mostrada, dependiendo de las necesidades de la aplicación y el propósito de la notificación.

### Estableciendo un `Icono` personalizado {#setting-a-custom-icon}

Por defecto, la notificación usa el ícono de la aplicación definido a través del [protocolo de iconos](../managing-resources/assets-protocols#the-icons-protocol). Puedes establecer un ícono personalizado utilizando el método `setIcon`. El componente admite diferentes esquemas de URL:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Resuelto como una URL de contexto que apunta a la carpeta de recursos de la aplicación; la imagen está codificada en base64.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Resuelto como una URL de servidor web, proporcionando una URL completamente calificada.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Resuelto como una URL de íconos.

**Ejemplo:**

```java
// Creando una notificación con una URL de ícono personalizado
DesktopNotification notification = new DesktopNotification(
  "Recordatorio", "La reunión comienza en 10 minutos."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Eventos de notificación {#notification-events}

El `DesktopNotification` admite varios eventos de ciclo de vida, y se pueden adjuntar oyentes para manejar eventos, como cuando se muestra, cierra, hace clic en la notificación o se encuentra un error.

| Evento                  | Descripción                                           | Cuándo usar                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Abrir** | Se activa cuando se muestra la notificación.       | Registrar la visualización de la notificación, actualizar la interfaz, rastrear la participación.    |
| **Cerrar**| Se activa cuando se cierra la notificación.         | Limpiar recursos, registrar descartes, ejecutar acciones de seguimiento.|
| **Error**| Se activa cuando ocurre un error con la notificación o el usuario no concedió permiso.| Manejar errores con gracia, notificar al usuario, aplicar alternativas.  |
| **Clic**| Se activa cuando el usuario hace clic en la notificación. | Navegar a una sección específica, registrar interacciones, reenfocar la aplicación. |


```java
DesktopNotification notification = new DesktopNotification("Alerta", "¡Tienes un nuevo mensaje!")

// Adjuntar un oyente de eventos para el evento de apertura
notification.onOpen(event -> {
  System.out.println("La notificación fue abierta por el usuario.");
});

// De manera similar, escuchar el evento de clic
notification.onClick(event -> {
  System.out.println("Notificación clickeada.");
});
```

:::warning Comportamiento de clic
Las políticas de seguridad del navegador impiden que el evento de clic en la notificación traiga automáticamente la ventana o pestaña de tu aplicación a foco. Este comportamiento es impuesto por el navegador y no se puede anular programáticamente. Si tu aplicación requiere que la ventana esté enfocada, necesitarás instruir a los usuarios para que hagan clic dentro de la aplicación después de interactuar con la notificación.
:::

## Consideraciones de seguridad y compatibilidad {#security-and-compatibility-considerations}

Al usar el componente **DesktopNotification**, ten en cuenta los siguientes puntos:

- **Contexto de Seguridad:** Tu aplicación debe ser servida a través de HTTPS para asegurarte de que las notificaciones sean permitidas por la mayoría de los navegadores modernos.
- **Requisito de Gesto del Usuario:** Las notificaciones solo se muestran después de una acción desencadenada por el usuario. Simplemente cargar una página no desencadenará una notificación.
- **Limitaciones del Navegador:** No todos los navegadores manejan los íconos personalizados o el comportamiento de enfoque de la misma manera. Por ejemplo, los íconos personalizados pueden no funcionar en Safari, mientras que el comportamiento de eventos puede variar en otros navegadores.
- **Permisos:** Siempre verifica que tu aplicación verifique y solicite permisos de notificación del usuario de manera adecuada.

## Mejores prácticas de uso {#usage-best-practices}

Ten en cuenta las siguientes mejores prácticas al usar el componente `DesktopNotification` en tu aplicación:

- **Informa a tus Usuarios:** Haz saber a los usuarios por qué se necesitan las notificaciones y cómo pueden beneficiarse de ellas.
- **Proporciona Alternativas:** Dado que algunos navegadores pueden restringir las notificaciones, considera formas alternativas de alertar a los usuarios (por ejemplo, mensajes dentro de la aplicación).
- **Manejo de Errores:** Siempre registra un oyente de errores para gestionar con gracia los escenarios en los que las notificaciones no se muestran.
