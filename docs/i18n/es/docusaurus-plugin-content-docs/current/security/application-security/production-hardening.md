---
sidebar_position: 3
title: Production Hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: b127e22d65b9a0ee8fc5b58b542aee36
---
El modelo [dirigido por el servidor](/docs/architecture/client-server) de webforJ y las salvaguardas integradas contra [amenazas comunes](/docs/security/application-security/common-threats) cubren mucho, pero un despliegue seguro aún depende de cómo operes la aplicación. Los pasos a continuación completan la imagen.

## Cifra cada conexión {#encrypt-every-connection}

Ejecuta el tráfico de producción solo a través de HTTPS. Termina TLS en el contenedor, proxy o equilibrador de carga frente a la aplicación, y redirige cualquier solicitud en HTTP plano a su equivalente seguro para que las credenciales y los identificadores de sesión nunca viajen sin cifrar.

## No confíes en nada del navegador {#trust-nothing-from-the-browser}

Un cliente manipulado puede enviar cualquier cosa. Revalida cada valor que tu código recibe, incluso aquellos valores que tu interfaz ya restringió, antes de persistir o actuar sobre ellos. El artículo sobre la [Interacción Cliente/Servidor](/docs/architecture/client-server) explica por qué el servidor es el único lugar donde una regla puede sostenerse verdaderamente.

La [vinculación y validación de datos](/docs/data-binding/validation/overview) de webforJ ayuda aquí: porque la vinculación se ejecuta en Java en el servidor, las restricciones que adjuntas a un modelo, incluida la [validación de Jakarta](/docs/data-binding/validation/jakarta-validation), se hacen cumplir en el lado del servidor en lugar de solo en el navegador. Trata eso como tu capa de integridad, no como una defensa contra ataques de inyección o de marcado, que aún necesitan el manejo descrito en el artículo de [Amenazas Comunes](/docs/security/application-security/common-threats).

## Deshabilitado y oculto no son seguridad {#disabled-and-hidden-arent-security}

`setEnabled(false)` y `setVisible(false)` son pistas de interfaz, no controles de acceso. webforJ refleja el estado deshabilitado de un control al cliente, pero no impide que un cliente manipulado vuelva a habilitar ese control y active su acción. Nunca confíes en un control deshabilitado u oculto para evitar que algo ocurra.

Pon la verdadera regla en el manejador del lado del servidor en su lugar: confirma que el usuario tiene permiso y que las precondiciones se cumplen antes de realizar la acción, exactamente como lo harías si el control hubiera estado habilitado todo el tiempo. El estado deshabilitado guía a los usuarios honestos; la regla del lado del servidor detiene a los deshonestos.

## Asegura tus vistas {#lock-down-your-views}

Protege las vistas con [seguridad de rutas](/docs/security/overview) para que cada una demande la autenticación y los roles adecuados. Otorga a las personas el acceso más restringido que les permita trabajar, y prefiere una postura segura por defecto donde una ruta no marcada aún requiera inicio de sesión.

## Mantén secretos externos {#keep-secrets-external}

Las credenciales, claves y tokens no pertenecen al código o a tu repositorio. Obténlos del entorno o de una fuente externa en su lugar, como se muestra en [Gestión de Secretos](/docs/security/application-security/managing-secrets).

## Mantente al día con las dependencias {#stay-current-on-dependencies}

Las bibliotecas que incorporas son una fuente de riesgo mayor que tu propio código. Haz seguimiento de los avisos, actualiza webforJ y tus otras dependencias regularmente, y cuando una versión corregida de una biblioteca transitiva se envíe antes que la biblioteca que la incluye, fija la versión corregida en tu `pom.xml`.

## Falla silenciosamente {#fail-quietly}

No dejes que las trazas de pila, rutas de archivos o identificadores internos lleguen a los usuarios finales. Registra los detalles en tus registros del servidor y presenta un mensaje genérico y simple en la interfaz. Registra un manejador personalizado a través de la [manejadora de errores](/docs/advanced/error-handling) de webforJ para que las excepciones no capturadas muestren una página controlada en lugar de diagnósticos en bruto.

## Divulga de manera responsable {#disclose-responsibly}

¿Encontraste un posible fallo en webforJ mismo? Infórmalo de forma privada a través del [informe de vulnerabilidad privado](https://github.com/webforj/webforj/security/advisories) de GitHub en lugar de abrir un problema público o una solicitud de extracción, para que una solución pueda implementarse antes de que se conozcan los detalles.
