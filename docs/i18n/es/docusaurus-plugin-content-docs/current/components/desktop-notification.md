---
title: DesktopNotification
sidebar_position: 29
description: >-
  Send native OS notifications outside the browser window with the
  DesktopNotification component for real-time messages, alerts, and status
  changes.
_i18n_hash: 529ae2fce596f744b423574be0a95dc0
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

El componente `DesktopNotification` muestra notificaciones de escritorio nativas fuera de la ventana del navegador. Se puede utilizar para alertar a los usuarios sobre eventos en tiempo real, como nuevos mensajes, alertas del sistema o cambios de estado mientras utilizan tu aplicación.

<!-- INTRO_END -->

## Configuración y requisitos previos {#setup-and-prerequisites}

<ExperimentalWarning />

Para comenzar a utilizar esta función, incluye la siguiente dependencia en tu pom.xml:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Antes de integrar el componente `DesktopNotification`, asegúrate de que:

- Tu aplicación se ejecute en un **contexto seguro** (HTTPS).
- El navegador no esté en modo incógnito o navegación privada.
- El usuario ha interactuado con la aplicación (por ejemplo, ha hecho clic en un botón o ha presionado una tecla), ya que las notificaciones requieren un gesto del usuario para ser mostradas.
- El usuario ha concedido permisos para las notificaciones (esto se solicitará automáticamente si es necesario).

## Uso básico {#basic-usage}

Existen múltiples formas de crear y mostrar una notificación. En la mayoría de los escenarios, el enfoque más simple es llamar a uno de los métodos estáticos `show`, que encapsulan todo el ciclo de vida de la notificación.

### Ejemplo: Mostrando una notificación básica {#example-displaying-a-basic-notification}

```java
// Notificación básica con título y mensaje
DesktopNotification.show("Actualización disponible", "¡Tu descarga ha finalizado!");
```

Esta línea crea una notificación con un título y un cuerpo, y luego intenta mostrarla.

## Personalizando la notificación {#customizing-the-notification}

Hay varias opciones para personalizar el aspecto y la sensación de la notificación mostrada, dependiendo de las necesidades de la aplicación y el propósito de la notificación.

### Estableciendo un `Icono` personalizado {#setting-a-custom-icon}

Por defecto, la notificación utiliza el ícono definido de tu aplicación a través del [protocolo de íconos](../managing-resources/assets-protocols#the-icons-protocol). Puedes establecer un ícono personalizado usando el método `setIcon`. El componente soporta diferentes esquemas de URL:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Resuelto como una URL de contexto que apunta a la carpeta de recursos de la aplicación; la imagen está codificada en base64.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Resuelto como una URL de servidor web, proporcionando una URL completamente cualificada.
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

El `DesktopNotification` soporta varios eventos de ciclo de vida, y se pueden adjuntar oyentes para manejar eventos, como cuando se muestra, cierra, hace clic en la notificación o se produce un error.

| Evento                  | Descripción                                           | Cuándo usar                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Abrir** | Se activa cuando se muestra la notificación.       | Registrar la visualización de notificación, actualizar la IU, rastrear la participación.    |
| **Cerrar**| Se activa cuando se cierra la notificación.         | Limpiar recursos, registrar despidos, ejecutar acciones de seguimiento.|
| **Error**| Se activa cuando ocurre un error con la notificación o el usuario no concedió permiso.| Manejar errores de manera adecuada, notificar al usuario, aplicar alternativas.  |
| **Clic**| Se activa cuando el usuario hace clic en la notificación. | Navegar a una sección específica, registrar interacciones, reenfocar la aplicación. |


```java
DesktopNotification notification = new DesktopNotification("Alerta", "¡Tienes un nuevo mensaje!")

// Adjuntar un oyente de eventos para el evento abrir
notification.onOpen(event -> {
  System.out.println("La notificación fue abierta por el usuario.");
});

// Del mismo modo, escuchar el evento clic
notification.onClick(event -> {
  System.out.println("Notificación clicada.");
});
```

:::warning Comportamiento del clic
Las políticas de seguridad del navegador impiden que el evento de clic de la notificación traiga automáticamente la ventana o pestaña de tu aplicación al foco. Este comportamiento es impuesto por el navegador y no se puede sobrepasar programáticamente. Si tu aplicación requiere que la ventana esté en foco, necesitarás instruir a los usuarios para que hagan clic dentro de la aplicación después de interactuar con la notificación.
:::

## Consideraciones de seguridad y compatibilidad {#security-and-compatibility-considerations}

Al utilizar el componente **DesktopNotification**, ten en cuenta los siguientes puntos:

- **Contexto de seguridad:** Tu aplicación debe ser servida a través de HTTPS para asegurar que las notificaciones sean permitidas por la mayoría de los navegadores modernos.
- **Requisito de gesto del usuario:** Las notificaciones solo se muestran tras una acción desencadenada por el usuario. Simplemente cargar una página no desencadenará una notificación.
- **Limitaciones del navegador:** No todos los navegadores manejan íconos personalizados o el comportamiento de enfoque de la misma manera. Por ejemplo, los íconos personalizados pueden no funcionar en Safari, mientras que el comportamiento de los eventos puede variar en otros navegadores.
- **Permisos:** Siempre verifica que tu aplicación compruebe y solicite permisos de notificación al usuario de manera adecuada.

## Mejores prácticas de uso {#usage-best-practices}

Ten en cuenta las siguientes mejores prácticas al utilizar el componente `DesktopNotification` en tu aplicación:

- **Informa a tus usuarios:** Haz saber a los usuarios por qué se necesitan las notificaciones y cómo pueden beneficiarse de ellas.
- **Proporciona alternativas:** Dado que algunos navegadores pueden restringir las notificaciones, considera formas alternativas de alertar a los usuarios (por ejemplo, mensajes dentro de la aplicación).
- **Manejo de errores:** Siempre registra un oyente de errores para gestionar de manera adecuada los escenarios donde las notificaciones no se muestran.
