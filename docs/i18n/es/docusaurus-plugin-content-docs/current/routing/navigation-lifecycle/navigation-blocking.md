---
sidebar_position: 3
title: Bloqueo de Navegación
_i18n_hash: a08d56654914719e12d1401d263c7956
---
El bloqueo de navegación añade una o más capas de control a toda la API del enrutador subyacente. Si hay controladores de bloqueo presentes, la navegación se evitará de la siguiente manera:

Si la navegación es desencadenada por algo controlado a nivel de enrutador, puedes realizar cualquier tarea o mostrar un aviso en la interfaz de usuario al usuario para confirmarla acción. Cada componente que implemente el `WillLeaveObserver` en el [árbol de rutas](../route-hierarchy/overview) será llamado. El implementador debe invocar `accept` para continuar con la navegación o `reject` para bloquearla. Si múltiples componentes implementan el `WillLeaveObserver` en el árbol de la ruta, los controladores de veto se ejecutarán secuencialmente en orden inverso.

:::info Ejemplo práctico de manejo de veto
Para ver cómo funciona el veto en la práctica, consulta los [ejemplos de uso de observadores de ciclo de vida](observers#example-handling-unsaved-changes-with-willleaveobserver).
:::

Para los eventos de página que no se pueden controlar directamente, el enrutador no interfiere ni impone un comportamiento específico. Sin embargo, los desarrolladores aún pueden escuchar el evento [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) para hacer un intento final de advertir al usuario sobre datos no guardados si es necesario.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Botón de retroceso del navegador {#browser-back-button}

El botón de retroceso opera fuera del control de las aplicaciones web, lo que dificulta interceptar o prevenir su acción de manera consistente en todos los navegadores. En lugar de intentar bloquear el botón de retroceso, es más efectivo diseñar tu UI/UX de una manera que mitigue el impacto. Considera estrategias como guardar datos no guardados en [almacenamiento de sesión](../../advanced/web-storage#session-storage), para que si un usuario navega y regresa, su progreso se restaure de manera segura. Este enfoque asegura la protección de datos sin depender de un comportamiento de navegador poco confiable.
