---
title: Maven Jetty plugin
_i18n_hash: 13b8de676f30b5a21eb7e9c2b49945b6
---
El plugin Jetty de Maven es una herramienta popular que permite a los desarrolladores ejecutar aplicaciones web Java dentro de un servidor Jetty embebido directamente desde sus proyectos Maven.

El Plugin Jetty inicia un servidor Jetty embebido que monitorea los archivos de tu aplicación, incluyendo clases Java y recursos, en busca de cambios. Cuando detecta actualizaciones, automáticamente vuelve a desplegar la aplicación, lo que acelera el desarrollo al eliminar pasos manuales de construcción y despliegue.

## Configuraciones de Jetty {#jetty-configurations}

Aquí hay algunas configuraciones esenciales para ajustar los parámetros de despliegue en caliente y la interacción del servidor del plugin:

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Configura con qué frecuencia el servidor Jetty escanea en busca de cambios de archivos en el **`pom.xml`**. El proyecto base establece esto en `2` segundos. Aumentar este intervalo puede reducir la carga de CPU, pero puede retrasar la reflexión de cambios en la aplicación. | `1`            |

## Configuraciones de webforJ {#webforj-configurations}

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Al utilizar el redeploy en caliente, se intercambia todo el archivo WAR. Si el cliente envía una solicitud mientras el servidor se está reiniciando, se produce un error. Esta configuración permite al cliente intentar recargar la página, asumiendo que el servidor volverá a estar en línea en breve. Solo se aplica a entornos de desarrollo y solo maneja errores específicos de redepliegue en caliente. | `on`           |
| **`webforj.clientHeartbeatRate`** | Establece el intervalo para los pings del cliente para consultar la disponibilidad del servidor. Esto mantiene abierta la comunicación entre cliente y servidor. Para desarrollo, utiliza intervalos más cortos para una detección de errores más rápida. En producción, configúralo a al menos 50 segundos para evitar solicitudes excesivas. | `50s`          |

## Consideraciones de uso {#usage-considerations}

Si bien el Plugin Jetty es altamente efectivo para el desarrollo, tiene algunas limitaciones potenciales:

- **Uso de memoria y CPU**: El escaneo frecuente de archivos, establecido por valores bajos de `scan` en el `pom.xml`, puede aumentar el consumo de recursos, especialmente en proyectos grandes. Aumentar el intervalo puede reducir la carga, pero también ralentiza el redepliegue.

- **Uso limitado en producción**: El Plugin Jetty está diseñado para desarrollo, no para entornos de producción. Carece de las optimizaciones de rendimiento y configuraciones de seguridad requeridas para producción, lo que lo hace más adecuado para pruebas locales.

- **Gestión de sesiones**: Durante el redeploy en caliente, puede que no se conserven las sesiones de usuario, especialmente cuando ocurren grandes cambios estructurales en el código. Esto puede interrumpir pruebas que involucren datos de sesión de usuario, requiriendo una gestión manual de sesiones o configuraciones alternativas para el desarrollo.
