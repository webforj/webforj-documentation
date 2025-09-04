---
title: Maven Jetty plugin
_i18n_hash: 7311fe4d0b6c5382244d898f099b9435
---
El plugin Maven Jetty es una herramienta popular que permite a los desarrolladores ejecutar aplicaciones web Java dentro de un servidor Jetty incrustado directamente desde sus proyectos Maven.

El Plugin Jetty lanza un servidor Jetty incrustado que monitorea los archivos de tu aplicación, incluidos las clases Java y los recursos, en busca de cambios. Cuando detecta actualizaciones, vuelve a desplegar automáticamente la aplicación, lo que acelera el desarrollo al eliminar pasos manuales de compilación y despliegue.

## Configuraciones de Jetty {#jetty-configurations}

Aquí hay algunas configuraciones esenciales para ajustar los ajustes de despliegue en caliente y la interacción del servidor del plugin:

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Configura con qué frecuencia el servidor Jetty escanea en busca de cambios en los archivos en el **`pom.xml`**. El proyecto base establece esto en `2` segundos. Aumentar este intervalo puede reducir la carga de la CPU, pero puede retrasar la reflección de los cambios en la aplicación. | `1`            |

## Configuraciones de webforJ {#webforj-configurations}

| Propiedad                          | Descripción                                                                                                                                                                           | Predeterminado |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Al usar el redeploy en caliente, todo el archivo WAR es intercambiado. Si el cliente envía una solicitud mientras el servidor se está reiniciando, ocurre un error. Esta configuración permite al cliente intentar una recarga de página, asumiendo que el servidor volverá a estar en línea pronto. Solo se aplica a entornos de desarrollo y solo maneja errores específicos de redeployment en caliente. | `on`           |
| **`webforj.clientHeartbeatRate`** | Establece el intervalo para los pings del cliente para consultar la disponibilidad del servidor. Esto mantiene abierta la comunicación cliente-servidor. Para desarrollo, utiliza intervalos más cortos para una detección de errores más rápida. En producción, establece esto en al menos 50 segundos para evitar solicitudes excesivas. | `50s`          |

## Consideraciones de uso {#usage-considerations}

Si bien el Plugin Jetty es muy efectivo para el desarrollo, tiene algunas limitaciones potenciales:

- **Uso de memoria y CPU**: La escaneos frecuentes de archivos, establecidos por valores bajos de `scan` en el `pom.xml`, pueden aumentar el consumo de recursos, especialmente en proyectos grandes. Aumentar el intervalo puede reducir la carga pero también ralentiza el redeployment.

- **Uso limitado en producción**: El Plugin Jetty está diseñado para el desarrollo, no para entornos de producción. Carece de la optimización de rendimiento y configuraciones de seguridad requeridas para producción, lo que lo hace más adecuado para pruebas locales.

- **Gestión de sesiones**: Durante el redeployment en caliente, las sesiones de usuario pueden no ser preservadas, especialmente cuando ocurren grandes cambios estructurales en el código. Esto puede interrumpir pruebas que involucren datos de sesión de usuario, requiriendo una gestión manual de sesiones o configuraciones alternativas para el desarrollo.
