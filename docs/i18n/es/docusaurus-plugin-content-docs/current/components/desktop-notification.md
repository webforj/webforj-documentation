---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 79d5ddb69e13f8536439346d8d4ee85d
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

El componente `DesktopNotification` muestra notificaciones nativas de escritorio fuera de la ventana del navegador. Se puede usar para alertar a los usuarios sobre eventos en tiempo real como nuevos mensajes, alertas del sistema o cambios de estado mientras utilizan su aplicación.

<!-- INTRO_END -->

## Configuración y requisitos previos {#setup-and-prerequisites}

:::warning característica experimental
El componente `DesktopNotification` todavía está evolucionando, y su API puede experimentar cambios a medida que madura. Para comenzar a usar esta función, asegúrese de incluir la siguiente dependencia en su pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

Antes de integrar el componente `DesktopNotification`, asegúrese de que:

- Su aplicación se ejecute en un **contexto seguro** (HTTPS).
- El navegador no esté en modo incógnito o de navegación privada.
- El usuario haya interactuado con la aplicación (por ejemplo, haciendo clic en un botón o presionando una tecla), ya que las notificaciones requieren un gesto del usuario para mostrarse.
- El usuario haya otorgado permisos para las notificaciones (esto se solicitará automáticamente si es necesario).

## Uso básico {#basic-usage}

Hay múltiples formas de crear y mostrar una notificación. En la mayoría de los escenarios, el enfoque más sencillo es llamar a uno de los métodos estáticos `show` que encapsulan todo el ciclo de vida de la notificación.

### Ejemplo: Mostrando una notificación básica {#example-displaying-a-basic-notification}

```java
// Notificación básica con título y mensaje
DesktopNotification.show("Actualización disponible", "¡Su descarga está completa!");
```

Esta línea crea una notificación con un título y cuerpo, y luego intenta mostrarla.

## Personalizando la notificación {#customizing-the-notification}

Hay diversas opciones para personalizar el aspecto de la notificación mostrada, dependiendo de las necesidades de la aplicación y del propósito de la notificación.

### Estableciendo un `Icono` personalizado {#setting-a-custom-icon}

Por defecto, la notificación utiliza el icono de su aplicación definido a través del [protocolo de iconos](../managing-resources/assets-protocols#the-icons-protocol). Puede establecer un icono personalizado utilizando el método `setIcon`. El componente admite diferentes esquemas de URL:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Resuelto como una URL de contexto apuntando a la carpeta de recursos de la aplicación; la imagen está codificada en base64.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Resuelto como una URL de servidor web, proporcionando una URL completamente calificada.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Resuelto como una URL de iconos.

**Ejemplo:**

```java
// Creando una notificación con una URL de icono personalizado
DesktopNotification notification = new DesktopNotification(
  "Recordatorio", "La reunión comienza en 10 minutos."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Eventos de notificación {#notification-events}

El `DesktopNotification` soporta varios eventos de ciclo de vida, y se pueden adjuntar listeners para manejar eventos, como cuando se muestra, cierra, hace clic o encuentra un error en la notificación.

| Evento                  | Descripción                                           | Cuándo usar                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Abrir** | Activado cuando la notificación se muestra.       | Registrar la visualización de la notificación, actualizar la interfaz, rastrear el compromiso.    |
| **Cerrar**| Activado cuando la notificación se cierra.         | Limpiar recursos, registrar desestimaciones, ejecutar acciones de seguimiento.|
| **Error**| Activado cuando ocurre un error con la notificación o el usuario no otorgó permiso.| Manejar errores de manera adecuada, notificar al usuario, aplicar alternativas.  |
| **Clic**| Activado cuando el usuario hace clic en la notificación. | Navegar a una sección específica, registrar interacciones, reenfocar la aplicación. |

```java
DesktopNotification notification = new DesktopNotification("Alerta", "¡Tienes un nuevo mensaje!")

// Adjuntar un listener de eventos para el evento de apertura
notification.onOpen(event -> {
  System.out.println("La notificación fue abierta por el usuario.");
});

// De manera similar, escuchar el evento de clic
notification.onClick(event -> {
  System.out.println("Notificación clickeada.");
});
```

:::warning Comportamiento de clic
Las políticas de seguridad del navegador impiden que el evento de clic de la notificación traiga automáticamente la ventana o pestaña de su aplicación al foco. Este comportamiento es impuesto por el navegador y no puede ser anulado programáticamente. Si su aplicación requiere que la ventana esté enfocada, deberá instruir a los usuarios que hagan clic dentro de la aplicación después de interactuar con la notificación.
:::

## Consideraciones de seguridad y compatibilidad {#security-and-compatibility-considerations}

Al utilizar el componente **DesktopNotification**, tenga en cuenta los siguientes puntos:

- **Contexto de Seguridad:** Su aplicación debe servirse a través de HTTPS para garantizar que las notificaciones estén permitidas por la mayoría de los navegadores modernos.
- **Requisito de Gesto del Usuario:** Las notificaciones solo se muestran después de una acción desencadenada por el usuario. Simplemente cargar una página no activará una notificación.
- **Limitaciones del Navegador:** No todos los navegadores manejan los iconos personalizados o el comportamiento de enfoque de la misma manera. Por ejemplo, los iconos personalizados pueden no funcionar en Safari, mientras que el comportamiento de eventos puede variar en otros navegadores.
- **Permisos:** Verifique siempre que su aplicación revise y solicite permisos de notificación al usuario de manera adecuada.

## Mejores prácticas de uso {#usage-best-practices}

Tenga en cuenta las siguientes mejores prácticas mientras utiliza el componente `DesktopNotification` en su aplicación:

- **Informe a sus usuarios:** Hágales saber por qué se necesitan notificaciones y cómo pueden beneficiarse de ellas.
- **Proporcione alternativas:** Dado que algunos navegadores pueden restringir las notificaciones, considere maneras alternativas de alertar a los usuarios (por ejemplo, mensajes en la aplicación).
- **Manejo de errores:** Registre siempre un listener de errores para gestionar de manera adecuada los escenarios donde las notificaciones no se muestren.
