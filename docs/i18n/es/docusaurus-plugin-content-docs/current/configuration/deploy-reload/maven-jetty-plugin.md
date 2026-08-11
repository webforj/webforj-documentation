---
title: Jetty
sidebar_position: 40
description: >-
  Run a webforJ app on the embedded Jetty server with the Maven Jetty plugin,
  with live reload and hotswap during development.
_i18n_hash: 73514e3b51a43e4a876aefd5cf933577
---
El plugin Maven Jetty ejecuta la aplicación en un servidor Jetty embebido directamente desde el proyecto. Un proyecto arquetipo establece `compile webforj:watch jetty:run` como su objetivo Maven predeterminado, por lo que `mvn` sin argumentos compila la aplicación, inicia el [frontend watch](/docs/configuration/deploy-reload/frontend-watch) y sirve la aplicación en Jetty.

## Requisitos {#requirements}

Un proyecto Jetty declara las herramientas de desarrollo por sí mismo, en el perfil utilizado para las ejecuciones de desarrollo:

```xml title="pom.xml"
<profiles>
  <profile>
    <id>dev</id>
    <activation>
      <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
      <dependency>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-devtools</artifactId>
      </dependency>
    </dependencies>
  </profile>
</profiles>
```

La versión proviene del Bill of Materials (BOM) de webforJ. El perfil mantiene la dependencia fuera del war empaquetado. Un proyecto creado a partir de un [arquetipo](/docs/introduction/getting-started) tiene este perfil.

## Activar recarga en vivo {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Las claves son las mismas que una aplicación Spring Boot establece en `application.properties`, listadas en los [ajustes](/docs/configuration/deploy-reload/overview#settings).

## Cambios en clases {#class-changes}

Con una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) configurada, la herramienta aplica los cambios en clases y Jetty no vuelve a desplegar nada. Dos propiedades de Jetty soportan esto, y un proyecto arquetipo establece ambas:

- `scan` es `0`, lo que desactiva el escaneo de archivos de Jetty.
- `deployMode` permanece sin establecer. Hotswap requiere el modo forkeado, y el plugin lo selecciona. Un build que establece `deployMode` en otro valor comienza sin la herramienta y lo registra.

Sin una herramienta de hotswap, se debe establecer `scan` en un intervalo en segundos y Jetty vuelve a desplegar la aplicación cuando las clases compiladas o los recursos cambian:

| Propiedad | Descripción | Predeterminado |
|-----------|-------------|----------------|
| `scan`    | Intervalo en segundos entre escaneos de la salida compilada, establecido como la propiedad `jetty.scan`. `0` desactiva el escaneo. Intervalos más largos reducen la carga y retrasan el redepliegue. | `1` |

## Consideraciones de uso {#usage-considerations}

- **Memoria y CPU**: valores bajos de `scan` aumentan el consumo de recursos en proyectos grandes. Intervalos más largos lo reducen y retrasan el redepliegue.
- **Solo desarrollo**: el plugin Jetty no es para despliegues en producción.
- **Sesiones**: un redepliegue puede eliminar sesiones de usuario. Una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) aplica cambios sin un redepliegue, y la sesión sobrevive.
