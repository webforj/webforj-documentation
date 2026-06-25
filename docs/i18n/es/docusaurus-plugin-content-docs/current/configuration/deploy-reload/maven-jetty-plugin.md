---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 7b8efc852651f2bea9db4ca110fe11ed
---
El plugin Jetty de Maven es una herramienta popular que permite a los desarrolladores ejecutar aplicaciones web Java dentro de un servidor Jetty embebido directamente desde sus proyectos Maven.

El Plugin Jetty lanza un servidor Jetty embebido que monitorea los archivos de tu aplicación, incluyendo clases Java y recursos, en busca de cambios. Cuando detecta actualizaciones, vuelve a desplegar automáticamente la aplicación, lo que acelera el desarrollo al eliminar pasos manuales de construcción y despliegue.

:::tip Cambios en el frontend
Los cambios bajo `src/main/frontend` son manejados por el [frontend watch](/docs/configuration/deploy-reload/frontend-watch), que los recompila y actualiza el navegador junto con el servidor.
:::

## Configuraciones de Jetty {#jetty-configurations}

Aquí hay algunas configuraciones esenciales para afinar los ajustes de despliegue en caliente del plugin y la interacción con el servidor:

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**                        | Configura con qué frecuencia el servidor Jetty escanea los cambios en archivos en el **`pom.xml`**. El proyecto esqueleto establece esto en `2` segundos. Aumentar este intervalo puede reducir la carga de CPU, pero puede demorar que los cambios se reflejen en la aplicación. | `1`            |

## Configuraciones de webforJ {#webforj-configurations}

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Al usar el redeploy en caliente, se intercambia todo el archivo WAR. Si el cliente envía una solicitud mientras el servidor se reinicia, ocurre un error. Esta configuración permite que el cliente intente recargar la página, asumiendo que el servidor volverá a estar en línea pronto. Solo se aplica a entornos de desarrollo y maneja errores específicos del redeploy en caliente. | `on`           |
| **`webforj.clientHeartbeatRate`** | Establece el intervalo para los pings del cliente para consultar la disponibilidad del servidor. Esto mantiene la comunicación cliente-servidor abierta. Para desarrollo, se recomiendan intervalos más cortos para una detección de errores más rápida. En producción, establece esto en al menos 50 segundos para evitar solicitudes excesivas. | `50s`          |

## Consideraciones de uso {#usage-considerations}

Si bien el Plugin Jetty es altamente efectivo para el desarrollo, tiene algunas limitaciones potenciales:

- **Uso de memoria y CPU**: El escaneo frecuente de archivos, establecido por valores bajos de `scan` en el `pom.xml`, puede aumentar el consumo de recursos, especialmente en proyectos grandes. Aumentar el intervalo puede reducir la carga pero también ralentiza el redeployment.

- **Uso limitado en producción**: El Plugin Jetty está diseñado para el desarrollo, no para entornos de producción. Carece de la optimización de rendimiento y las configuraciones de seguridad requeridas para producción, lo que lo hace más adecuado para pruebas locales.

- **Gestión de sesiones**: Durante el redeploy en caliente, las sesiones de usuario pueden no ser preservadas, especialmente cuando ocurren cambios estructurales grandes en el código. Esto puede interrumpir las pruebas que implican datos de sesiones de usuario, requiriendo gestión manual de sesiones o configuraciones alternativas para el desarrollo.
