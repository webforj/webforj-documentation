---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 6ce3da7be312bb71f2ded56a583d7687
---
El plugin Jetty de Maven es una herramienta popular que permite a los desarrolladores ejecutar aplicaciones web en Java dentro de un servidor Jetty embebido directamente desde sus proyectos de Maven.

El Plugin Jetty lanza un servidor Jetty embebido que monitorea los archivos de tu aplicación, incluyendo clases y recursos de Java, en busca de cambios. Cuando detecta actualizaciones, automáticamente vuelve a desplegar la aplicación, lo que acelera el desarrollo al eliminar pasos manuales de construcción y despliegue.

:::tip Cambios en el frontend
Los cambios en `src/main/frontend` son manejados por el [watch del frontend](/docs/configuration/deploy-reload/frontend-watch), que los reconstruye y actualiza el navegador junto con el servidor.
:::

## Configuraciones de Jetty {#jetty-configurations}

Aquí hay algunas configuraciones esenciales para ajustar la implementación en caliente del plugin y la interacción con el servidor:

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**                        | Configura con qué frecuencia el servidor Jetty escanea los cambios de archivos en el **`pom.xml`**. El proyecto base establece esto en `2` segundos. Aumentar este intervalo puede reducir la carga de CPU, pero puede retrasar la reflección de cambios en la aplicación. | `1`            |

## Configuraciones de webforJ {#webforj-configurations}

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Al usar la redeploy en caliente, se intercambia todo el archivo WAR. Si el cliente envía una solicitud mientras el servidor se reinicia, se produce un error. Esta configuración permite al cliente intentar recargar la página, asumiendo que el servidor volverá a estar en línea pronto. Solo se aplica a entornos de desarrollo y solo maneja errores específicos de la redeploy en caliente. | `on`           |
| **`webforj.clientHeartbeatRate`** | Establece el intervalo para los pings del cliente para consultar la disponibilidad del servidor. Esto mantiene abierta la comunicación entre el cliente y el servidor. Para desarrollo, usa intervalos más cortos para una detección de errores más rápida. En producción, establece esto en al menos 50 segundos para evitar solicitudes excesivas. | `50s`          |

## Consideraciones de uso {#usage-considerations}

Si bien el Plugin Jetty es altamente efectivo para el desarrollo, tiene algunas limitaciones potenciales:

- **Uso de memoria y CPU**: Escaneos de archivos frecuentes, establecidos por valores bajos de `scan` en el `pom.xml`, pueden aumentar el consumo de recursos, especialmente en proyectos grandes. Aumentar el intervalo puede reducir la carga pero también ralentiza la redeploy.

- **Uso limitado en producción**: El Plugin Jetty está diseñado para el desarrollo, no para entornos de producción. Carece de la optimización de rendimiento y configuraciones de seguridad requeridas para producción, lo que lo hace más adecuado para pruebas locales.

- **Gestión de sesiones**: Durante la redeploy en caliente, las sesiones de usuario pueden no preservarse, especialmente cuando ocurren cambios estructurales grandes en el código. Esto puede interrumpir pruebas que involucren datos de sesión de usuario, requiriendo gestión manual de sesiones o configuraciones alternativas para el desarrollo.
