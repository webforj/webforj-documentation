---
title: Getting Started
sidebar_position: 2
_i18n_hash: 5a051bf7c5a9494b21ba5df3629c35b4
---
Este artículo describe los pasos para crear una nueva aplicación webforJ utilizando los [archetypes](../building-ui/archetypes/overview.md) de webforJ. Los archetypes proporcionan estructuras de proyectos preconfiguradas y código de inicio para que puedas comenzar un proyecto rápidamente.
Para crear una nueva aplicación webforJ a partir de un archetype, puedes usar [startforJ](#using-startforj) o la [línea de comandos](#using-the-command-line).

:::tip Prerrequisitos
Antes de comenzar, revisa los [prerrequisitos](./prerequisites) necesarios para configurar y usar webforJ.
:::

## Usando startforJ {#using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto de inicio mínimo basado en un archetype de webforJ elegido. Este proyecto de inicio incluye todas las dependencias requeridas, archivos de configuración y un diseño prehecho, para que puedas comenzar a construir sobre él de inmediato.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Personalizando con startforJ {#customizing-with-startforj}

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la App, ID de Grupo, ID de Artefacto)  
- Versión de webforJ y versión de Java
- Color de Tema e Ícono
- Archetype
- Sabor

Hay dos opciones de sabor para elegir, siendo "Solo webforJ" la predeterminada:
  - **Solo webforJ**: Aplicación estándar de webforJ
  - **webforJ + Spring Boot**: Aplicación webforJ con soporte para Spring Boot

:::tip Archetypes Disponibles
webforJ viene con varios archetypes predefinidos para ayudarte a comenzar rápidamente. Para una lista completa de archetypes disponibles, consulta el [catálogo de archetypes](../building-ui/archetypes/overview).
:::

Con esta información, startforJ creará un proyecto básico a partir de tu archetype elegido con tus personalizaciones seleccionadas.
Puedes optar por descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

Una vez que hayas descargado tu proyecto, abre la carpeta del proyecto en tu IDE.

## Usando la línea de comandos {#using-the-command-line}

Si prefieres usar la línea de comandos, puedes generar un proyecto directamente utilizando el archetype de Maven:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>
