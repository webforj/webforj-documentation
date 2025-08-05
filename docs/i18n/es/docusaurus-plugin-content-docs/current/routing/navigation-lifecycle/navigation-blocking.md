---
sidebar_position: 3
title: Navigation Blocking
_i18n_hash: c0d79c6ce266eb4b9f9fd28915dcc380
---
El bloqueo de navegación añade una o más capas de control a toda la API subyacente del enrutador. Si hay algún controlador de bloqueo presente, la navegación será bloqueada de la siguiente manera:

Si la navegación es provocada por algo controlado a nivel de enrutador, puedes realizar cualquier tarea o mostrar un aviso de interfaz de usuario al usuario para confirmar la acción. Cada componente que implemente el `WillLeaveObserver` en el [árbol de rutas](../route-hierarchy/overview) será llamado. El implementador debe invocar `accept` para continuar con la navegación o `reject` para bloquearla. Si múltiples componentes implementan el `WillLeaveObserver` en el árbol de la ruta, los controladores de veto se ejecutarán secuencialmente en orden inverso.

:::info Ejemplo Práctico de Manejo de Veto
Para ver cómo funciona el veto en la práctica, consulta los [ejemplos de Uso de Lifecycle Observers](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Para los eventos de la página que no pueden ser controlados directamente, el enrutador no interviene ni aplica un comportamiento específico. Sin embargo, los desarrolladores aún pueden escuchar el evento [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) para hacer un último intento de advertir al usuario sobre datos no guardados si es necesario.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Botón de retroceso del navegador {#browser-back-button}

El botón de retroceso opera fuera del control de las aplicaciones web, lo que dificulta interceptar o prevenir su acción de manera consistente en todos los navegadores. En lugar de intentar bloquear el botón de retroceso, es más efectivo diseñar tu UI/UX de tal manera que mitigue el impacto. Considera estrategias como guardar datos no guardados en [almacenamiento de sesión](../../advanced/web-storage#session-storage), para que si un usuario navega lejos y regresa, su progreso se restaure de forma segura. Este enfoque asegura la protección de los datos sin depender de un comportamiento poco confiable del navegador.
