---
title: Comenzando
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: ea669974a5f1b3a02a162f89bb16c6e3
---
Este artículo describe los pasos para crear una nueva aplicación webforJ utilizando los [arquetipos](../building-ui/archetypes/overview.md) de webforJ. Los arquetipos proporcionan estructuras de proyecto preconfiguradas y código inicial para que puedas iniciar un proyecto rápidamente. 
Para crear una nueva aplicación webforJ a partir de un arquetipo, puedes usar [startforJ](#using-startforj) o la [línea de comandos](#using-the-command-line).

:::tip Prerrequisitos
Antes de comenzar, revisa los [prerrequisitos](./prerequisites) necesarios para configurar y usar webforJ.
:::


## Usando startforJ {#using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto inicial mínimo basado en un arquetipo de webforJ seleccionado. Este proyecto inicial incluye todas las dependencias requeridas, archivos de configuración y un diseño predefinido, para que puedas comenzar a construir sobre él de inmediato. 

<!-- <div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div> -->


### Personalizando con startforJ {#customizing-with-startforj}

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la aplicación, ID del grupo, ID del artefacto)
- Versión de webforJ y versión de Java
- Color y icono del tema
- Arquetipo
- Se ejecuta en

La opción **Se ejecuta en** decide dónde se ejecuta la aplicación, siendo **Contenedor de Servlets** la opción predeterminada:
  - **Contenedor de Servlets**: un WAR para Jetty, Tomcat u otro contenedor de servlets
  - **Spring Boot**: un JAR ejecutable con un servidor embebido
  - **BBjServices**: una aplicación que se ejecuta en BBjServices a través de [BBjShip](/docs/configuration/bbjship/overview)

:::tip Arquetipos Disponibles
webforJ incluye varios arquetipos predefinidos para ayudarte a comenzar rápidamente. Para una lista completa de arquetipos disponibles, consulta el [catálogo de arquetipos](../building-ui/archetypes/overview).
:::

Utilizando esta información, startforJ creará un proyecto básico a partir de tu arquetipo elegido con las personalizaciones seleccionadas. 
Puedes optar por descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

Una vez que hayas descargado tu proyecto, abre la carpeta del proyecto en tu IDE.

## Usando la línea de comandos {#using-the-command-line}


Si prefieres utilizar la línea de comandos, puedes generar un proyecto directamente utilizando el arquetipo de Maven:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
