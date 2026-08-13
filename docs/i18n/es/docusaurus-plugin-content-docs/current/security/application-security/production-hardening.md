---
sidebar_position: 3
title: Production Hardening
description: >-
  Practical steps for running a webforJ app safely in production, from transport
  encryption and dependency upkeep to server-side checks and disclosure.
_i18n_hash: 62e3e574855705f8b97295f4ebe5169b
---
El modelo [impulsado por el servidor](/docs/architecture/client-server) de webforJ y las salvaguardas integradas contra [amenazas comunes](/docs/security/application-security/common-threats) cubren mucho, pero un despliegue seguro aún depende de cómo operas la aplicación. Los pasos a continuación completan la imagen.

## Encripta cada conexión {#encrypt-every-connection}

Ejecuta el tráfico de producción solo sobre HTTPS. Termina TLS en el contenedor, proxy o equilibrador de carga frente a la aplicación, y redirige cualquier solicitud HTTP sin cifrar a su equivalente seguro para que las credenciales e identificadores de sesión nunca viajen sin cifrar.

## No confíes en nada del navegador {#trust-nothing-from-the-browser}

Un cliente manipulado puede enviar cualquier cosa. Revalida cada valor que tu código recibe, incluso aquellos que tu interfaz ya restringió, antes de que los persistas o actúes sobre ellos. El artículo sobre [Interacción Cliente/Servidor](/docs/architecture/client-server) explica por qué el servidor es el único lugar donde una regla puede sostenerse verdaderamente.

La [vinculación y validación de datos](/docs/data-binding/validation/overview) de webforJ ayuda aquí: dado que la vinculación se ejecuta en Java en el servidor, las restricciones que adjuntas a un modelo, incluyendo [validación de Jakarta](/docs/data-binding/validation/jakarta-validation), se aplican del lado del servidor en lugar de solo en el navegador. Trata eso como tu capa de integridad, no como una defensa contra ataques de inyección o de marcado, que aún necesitan el manejo descrito en el artículo sobre [Amenazas Comunes](/docs/security/application-security/common-threats).

## Deshabilitado y oculto no son seguridad {#disabled-and-hidden-arent-security}

`setEnabled(false)` y `setVisible(false)` son señales de interfaz, no controles de acceso. webforJ refleja el estado deshabilitado de un control al cliente, pero no impide que un cliente manipulado vuelva a habilitar ese control y desencadene su acción. Nunca te apoyes en un control deshabilitado u oculto para evitar que algo suceda.

Coloca la verdadera regla en el controlador del lado del servidor en su lugar: confirma que el usuario está autorizado y que se cumplen las condiciones previas antes de realizar la acción, exactamente como lo harías si el control hubiera estado habilitado todo el tiempo. El estado deshabilitado guía a los usuarios honestos; la regla del lado del servidor detiene a los deshonestos.

## Asegura tus vistas {#lock-down-your-views}

Protege las vistas con [seguridad de ruta](/docs/security/overview) para que cada una exija la autenticación y roles correctos. Da a las personas el acceso más restringido que les permita trabajar, y prefiere una postura de seguridad predeterminada donde una ruta no marcada aún requiera inicio de sesión.

## Mantén secretos externos {#keep-secrets-external}

Credenciales, claves y tokens no pertenecen al código ni a tu repositorio. Sácarlos del entorno o de una fuente externa en su lugar, como se muestra en [Gestión de Secretos](/docs/security/application-security/managing-secrets).

## Apaga las herramientas de desarrollo {#leave-development-tooling-off}

[craftforJ](/docs/craftforj) es el entorno de desarrollo que inspecciona una aplicación en ejecución y escribe cambios de vuelta en su fuente de Java. Requiere tanto `webforj.debug` como `webforj.devtools.craftforj.enabled`, y por defecto responde solo a la máquina que ejecuta la aplicación. Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o desde un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ tienen ambas configuraciones habilitadas para desarrollo, así que confírmalo en lugar de asumirlo.

Asegúrate de que ambas propiedades no estén establecidas o sean `false` en la configuración que realmente despliegas, incluyendo cualquier variable de entorno o perfil que aplique solo en producción. Luego, carga la aplicación desplegada y confirma que no aparezca ningún desencadenante de craftforJ en la página. Consulta [seguridad de craftforJ](/docs/craftforj/security) para obtener la imagen completa.

## Mantente al día con las dependencias {#stay-current-on-dependencies}

Las bibliotecas que introduces son una fuente de riesgo mayor que tu propio código. Sigue los avisos, actualiza webforJ y tus otras dependencias regularmente, y cuando una versión corregida de una biblioteca transitiva se envíe antes de la biblioteca que la incluye, fija la versión corregida en tu `pom.xml`.

## Fallar en silencio {#fail-quietly}

No dejes que las trazas de pila, rutas de archivos o identificadores internos lleguen a los usuarios finales. Registra los detalles en tus registros del servidor y presenta un mensaje genérico y sencillo en la interfaz. Registra un controlador personalizado a través de la [gestión de errores](/docs/advanced/error-handling) de webforJ para que las excepciones no capturadas muestren una página controlada en lugar de diagnósticos en bruto.

## Divulgar responsablemente {#disclose-responsibly}

¿Encontraste un posible fallo en webforJ? Reporta de forma privada a través del [informe de vulnerabilidades privadas de GitHub](https://github.com/webforj/webforj/security/advisories) en lugar de abrir un problema o solicitud de extracción pública, para que una solución pueda llegar antes de que se conozcan los detalles.
