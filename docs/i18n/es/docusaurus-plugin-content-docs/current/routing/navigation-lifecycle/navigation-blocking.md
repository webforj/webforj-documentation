---
sidebar_position: 3
title: Navigation Blocking
description: >-
  Intercept navigation with WillLeaveObserver veto handlers and the beforeunload
  event to guard unsaved changes.
_i18n_hash: 0deeb3e0583fdd425fe2a604ee1e9164
---
La bloqueación de navegación agrega una o más capas de control a toda la API de enrutador subyacente. Si están presentes manejadores de bloqueo, la navegación será impedida de la siguiente manera:

Si la navegación es desencadenada por algo controlado a nivel de enrutador, puedes realizar cualquier tarea o mostrar un aviso de interfaz de usuario al usuario para confirmar la acción. Cada componente que implemente el `WillLeaveObserver` en el [árbol de rutas](../route-hierarchy/overview) será llamado. El implementador debe invocar `accept` para continuar con la navegación o `reject` para bloquearla. Si múltiples componentes implementan el `WillLeaveObserver` en el árbol de la ruta, los manejadores de veto se ejecutarán de manera secuencial en el orden inverso.

:::info Ejemplo práctico de manejo de veto
Para ver cómo funciona el veto en la práctica, consulta los [Ejemplos de uso de Observadores del Ciclo de Vida](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Para eventos de página que no se pueden controlar directamente, el enrutador no interfiere ni impone un comportamiento específico. Sin embargo, los desarrolladores aún pueden escuchar el evento [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) para hacer un último intento de advertir al usuario sobre datos no guardados si es necesario.

```java
PageEventOptions options = new PageEventOptions();
options.setCode("""
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Botón de retroceso del navegador {#browser-back-button}

El botón de retroceso opera fuera del control de las aplicaciones web, lo que dificulta interceptar o prevenir su acción de manera consistente en todos los navegadores. En lugar de intentar bloquear el botón de retroceso, es más efectivo diseñar tu UI/UX de tal manera que mitigue el impacto. Considera estrategias como guardar datos no guardados en [almacenamiento de sesión](../../advanced/web-storage#session-storage), de modo que si un usuario navega y regresa, su progreso se restaura de manera segura. Este enfoque garantiza la protección de datos sin depender de comportamientos poco fiables del navegador.
