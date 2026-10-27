---
title: BBjServices
sidebar_position: 45
sidebar_class_name: new-content
description: >-
  Set up live reload and hotswap for a webforJ app that runs in the BBjServices
  of your own machine through BBjShip.
_i18n_hash: 46a42789a4b05b258a057e9acae1ef12
---
En BBjServices, la construcción no inicia su propio servidor. `mvn compile webforj:watch` ejecuta la aplicación en los BBjServices de tu propia máquina a través de [BBjShip](/docs/configuration/bbjship/overview), directamente desde el proyecto. Un proyecto arquetipo con el sabor de BBj establece esto como su objetivo predeterminado de Maven, por lo que `mvn` sin argumentos compila la aplicación, inicia el [frontend watch](/docs/configuration/deploy-reload/frontend-watch) y ejecuta la aplicación.

## Requisitos {#requirements}

Un proyecto Maven configurado para BBjShip, consulta [Introducción](/docs/configuration/bbjship/getting-started) o [Proyectos existentes](/docs/configuration/bbjship/existing-projects), con BBj instalado en la misma máquina que el proyecto. BBjServices carga las clases desde las carpetas en las que la construcción compila, por lo que un servidor BBj en otra máquina solo recibe [despliegues](/docs/configuration/bbjship/deploying), sin recarga en vivo ni hotswap.

## Activar la recarga en vivo {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Las claves restantes están enumeradas en los [ajustes](/docs/configuration/deploy-reload/overview#settings).

## Cambios de clase {#class-changes}

En BBjServices, un cambio de clase llega a la aplicación en ejecución solo a través de una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap). Sin una, un cambio compilado no se aplica y la página no se recarga hasta que el watch comienza nuevamente. Un proyecto arquetipo configura HotswapAgent:

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

La herramienta se adjunta a BBjServices, no a la construcción. La primera ejecución la añade a los argumentos de Java de BBjServices y reinicia BBjServices una vez. Las ejecuciones posteriores comienzan sin un reinicio. En macOS y Linux, y en Windows cuando BBjServices no está registrado como un servicio de Windows, el BBjServices reiniciado se ejecuta fuera del administrador de servicios del sistema operativo hasta que la máquina se reinicia. [Solución de problemas](/docs/configuration/bbjship/troubleshooting#development-runs) enumera los mensajes de una ejecución de desarrollo y qué hacer con cada uno.

:::warning Reinicializa BBjServices
La primera ejecución cambia la configuración de Java de BBjServices en `<BBj home>/cfg/BBj.properties` y reinicia BBjServices. El reinicio termina todas las sesiones de BBj en ejecución en esa máquina, no solo las de webforJ, así que ejecútalo cuando nadie más esté usando ese BBj. Antes del primer cambio, se guarda una copia del archivo como `BBj.properties.before-webforj` en la carpeta `<BBj home>/plugins/.webforj-bbjship-deployments`. Haz una copia de seguridad del archivo tú mismo primero si lo cambiaste a mano. `mvn webforj:bbj-setup` realiza el mismo cambio.
:::

Un cambio de dependencia necesita un nuevo inicio del watch.

### Java de BBjServices {#java-of-bbjservices}

Con HotswapAgent, los [cambios en la estructura de clases](/docs/configuration/deploy-reload/hotswap#class-structure-changes) dependen de la versión de Java con la que se ejecuta BBjServices, no de la versión de Java de la construcción. El watch nunca lo cambia. Para ejecutar BBjServices en un [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases):

1. En Enterprise Manager, ve a **BBjServices**, **Configuraciones de Java**.
2. Selecciona **BBjServices** bajo **Aplicación** y establece la ruta del hogar de Java.
3. Reinicia BBjServices. **Detalles de Java** en Enterprise Manager mostrará entonces el JDK y la versión con la que se ejecuta.

Consulta [Requisitos previos](/docs/introduction/prerequisites#java-development-kit-jdk-21) para instalar un JetBrains Runtime.

## Construcciones de producción {#production-builds}

`mvn install` despliega la aplicación empaquetada sin herramientas de desarrollo, consulta [Despliegue](/docs/configuration/bbjship/deploying). La propiedad `webforj.devtools.livereload.enabled` no tiene efecto en una aplicación empaquetada.
