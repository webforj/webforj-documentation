---
title: Getting Started
sidebar_position: 2
_i18n_hash: 5c658711bfa3dc70787cccbf2dfb6d2d
---
Este artículo describe los pasos para crear una nueva aplicación webforJ utilizando los [arquetipos](../building-ui/archetypes/overview.md) de webforJ. Los arquetipos proporcionan estructuras de proyecto preconfiguradas y código inicial para que puedas poner un proyecto en marcha rápidamente. 
Para crear una nueva aplicación webforJ a partir de un arquetipo, puedes usar [startforJ](#using-startforj) o la [línea de comandos](#using-the-command-line).

:::tip Requisitos previos
Antes de comenzar, revisa los [requisitos previos](./prerequisites) necesarios para configurar y utilizar webforJ.
:::

## Usando startforJ {#using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto base mínimo basado en un arquetipo webforJ elegido. Este proyecto inicial incluye todas las dependencias requeridas, archivos de configuración y un diseño prehecho, para que puedas comenzar a construir sobre él de inmediato.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Personalizando con startforJ {#customizing-with-startforj}

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la app, ID del grupo, ID del artefacto)  
- Versión de webforJ y versión de Java
- Color y icono del tema
- Arquetipo
- Sabor

Hay dos opciones de sabor para elegir, siendo "Solo webforJ" la opción predeterminada:
  - **Solo webforJ**: Aplicación estándar de webforJ
  - **webforJ + Spring Boot**: Aplicación de webforJ con soporte de Spring Boot

:::tip Arquetipos disponibles
webforJ viene con varios arquetipos predefinidos para ayudarte a comenzar rápidamente. Para obtener una lista completa de los arquetipos disponibles, consulta el [catálogo de arquetipos](../building-ui/archetypes/overview).
:::

Usando esta información, startforJ creará un proyecto básico a partir del arquetipo que elijas con tus personalizaciones seleccionadas. 
Puedes optar por descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

Una vez que hayas descargado tu proyecto, abre la carpeta del proyecto en tu IDE y pasa a [ejecutar la aplicación](#running-the-app).

## Usando la línea de comandos {#using-the-command-line}

Si prefieres usar la línea de comandos, puedes generar un proyecto directamente usando el arquetipo de Maven:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
