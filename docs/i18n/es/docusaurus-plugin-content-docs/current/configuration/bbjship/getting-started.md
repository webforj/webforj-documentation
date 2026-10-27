---
title: Comenzando
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 06ef0b29df76b835ecd12d7268e4eca6
---
Esta guía crea una aplicación webforJ que se ejecuta con BBjServices, la ejecuta en tu BBj local mientras desarrollas y la despliega con BBjShip.

:::tip Prerrequisitos
Necesitas un proyecto Maven y una instalación de BBj de la misma versión que webforJ, por ejemplo, BBj 26.03 para webforJ 26.03, con BBjServices en funcionamiento. BBjShip no soporta Gradle. Revisa los [prerrequisitos](/docs/introduction/prerequisites) para el resto de las herramientas.
:::

## Crear una aplicación BBj {#create-a-bbj-app}

Tienes dos opciones para crear una nueva aplicación webforJ que se ejecute con BBjServices: utilizando la herramienta gráfica startforJ o la línea de comandos de Maven.

<!-- vale off -->
### Opción 1: usando startforJ {#option-1-using-startforj}
<!-- vale on -->

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la Aplicación, ID del Grupo, ID del Artefacto)
- Versión de webforJ y versión de Java
- Color del Tema e Ícono
- Arquetipo
- **Se ejecuta en** - Selecciona **BBjServices** para crear un proyecto que se ejecute con BBjServices

### Opción 2: usando la línea de comandos {#option-2-using-the-command-line}

Si prefieres usar la línea de comandos, genera el proyecto con los arquetipos oficiales de webforJ:

```bash {8}
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=org.example \
  -DartifactId=my-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-bbj
```

Con el parámetro `flavor`, el arquetipo genera un proyecto que se ejecuta con BBjServices.

Esto crea un proyecto con:
- La dependencia `webforj` sin el motor webforJ, que proporciona BBjServices
- El plugin Maven de webforJ, configurado para desplegar a BBjShip en tu BBj local bajo el ID del artefacto como el nombre de la aplicación
- HotswapAgent para ejecuciones de desarrollo
- Vistas de ejemplo
- Archivos de configuración de webforJ para desarrollo y producción

## Ejecutar la aplicación {#run-the-app}

Un proyecto arquetipo establece su objetivo predeterminado de Maven, por lo que `mvn` sin argumentos compila la aplicación y comienza una [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) en tu BBjServices local:

```bash
mvn
```

La primera ejecución prepara tu BBj local. Instala BBjShip y agrega HotswapAgent a BBjServices, lo que reinicia BBjServices una vez. Luego, la construcción imprime la dirección de tu aplicación:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press Ctrl+C to stop the app and the watch
```

Las clases que compilas llegan a la aplicación en ejecución sin necesidad de reiniciar. Detén la construcción con `Ctrl+C` para detener la aplicación.

## Desplegar la aplicación {#deploy-the-app}

Para desplegar la aplicación empaquetada, ejecuta:

```bash
mvn install
```

La aplicación se publica en `http://localhost:8888/webapp/my-app` hasta que la despliegues nuevamente o la elimines. Consulta [Desplegando](/docs/configuration/bbjship/deploying) para desplegar en otro servidor BBj, por ejemplo, desde una pipeline de CI.
