---
title: Getting Started
description: >-
  Create a new webforJ project from an archetype using either the startforJ web
  wizard or a Maven command-line generator.
sidebar_position: 2
_i18n_hash: c1867c61e2072cb6657bad9492f22083
---
Este artículo describe los pasos para crear una nueva aplicación webforJ utilizando los [arquetipos](../building-ui/archetypes/overview.md) de webforJ. Los arquetipos proporcionan estructuras de proyecto preconfiguradas y código base para que puedas iniciar un proyecto rápidamente.  
Para crear una nueva aplicación webforJ a partir de un arquetipo, puedes usar [startforJ](#using-startforj) o la [línea de comando](#using-the-command-line).

:::tip Requisitos Previos  
Antes de comenzar, revisa los [requisitos previos](./prerequisites) necesarios para configurar y usar webforJ.  
:::


## Usando startforJ {#using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto inicial mínimo basado en un arquetipo de webforJ elegido. Este proyecto inicial incluye todas las dependencias necesarias, archivos de configuración y un diseño prehecho, para que puedas comenzar a construir de inmediato.

### Personalizando con startforJ {#customizing-with-startforj}

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la App, ID de Grupo, ID del Artefacto)
- Versión de webforJ y versión de Java
- Color y Icono del Tema
- Arquetipo
- Sabor

Hay dos opciones de sabor para elegir, siendo "Solo webforJ" la opción por defecto:
  - **Solo webforJ**: Aplicación estándar de webforJ
  - **webforJ + Spring Boot**: Aplicación de webforJ con soporte para Spring Boot

:::tip Arquetipos Disponibles  
webforJ viene con varios arquetipos predefinidos para ayudarte a comenzar rápidamente. Para una lista completa de los arquetipos disponibles, consulta el [catálogo de arquetipos](../building-ui/archetypes/overview).  
:::

Con esta información, startforJ creará un proyecto básico a partir de tu arquetipo elegido con las personalizaciones que has elegido.  
Puedes optar por descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

Una vez que hayas descargado tu proyecto, abre la carpeta del proyecto en tu IDE.

## Usando la línea de comando {#using-the-command-line}

Si prefieres usar la línea de comando, puedes generar un proyecto directamente utilizando el arquetipo de Maven:

<ComponentArchetype  
project="hello-world"  
flavor="webforj"  
/>
