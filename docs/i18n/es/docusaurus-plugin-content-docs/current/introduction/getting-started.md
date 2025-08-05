---
title: Getting Started
sidebar_position: 2
_i18n_hash: 37b65c983d6210a89474156b10af1e93
---
Este artículo describe los pasos para crear una nueva aplicación webforJ utilizando [arquetipos](../building-ui/archetypes/overview.md). Los arquetipos proporcionan estructuras de proyecto preconfiguradas y código de inicio para que puedas poner un proyecto en marcha rápidamente. 
Para crear una nueva aplicación webforJ a partir de un arquetipo, puedes usar [startforJ](#using-startforj) o la [línea de comandos](#using-the-command-line).

:::tip Prerrequisitos
Antes de comenzar, revisa los [prerrequisitos](./prerequisites) necesarios para configurar y utilizar webforJ.
:::

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

## Usando startforJ {#using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es [startforJ](https://docs.webforj.com/startforj), que genera un proyecto de inicio mínimo basado en un arquetipo webforJ elegido. Este proyecto de inicio incluye todas las dependencias requeridas, archivos de configuración y un diseño prehecho, para que puedas comenzar a construir sobre él de inmediato.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Personalizando con startforJ {#customizing-with-startforj}

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la aplicación, ID del grupo, ID del artefacto)  
- Versión de webforJ y versión de Java
- Color y ícono del tema
- Arquetipo
- Sabor

Hay dos opciones de sabor para elegir, siendo "Solo webforJ" la predeterminada:
  - **Solo webforJ**: Aplicación estándar de webforJ
  - **webforJ + Spring Boot**: Aplicación webforJ con soporte para Spring Boot

:::caution Soporte de Spring Boot
El sabor de Spring Boot solo está disponible en la versión 25.02 de webforJ y superiores. Si seleccionas esta opción, asegúrate de elegir una versión compatible.
:::

:::tip Arquetipos disponibles
webforJ viene con varios arquetipos predefinidos para ayudarte a comenzar rápidamente. Para una lista completa de arquetipos disponibles, consulta el [catálogo de arquetipos](../building-ui/archetypes/overview).
:::

Utilizando esta información, startforJ creará un proyecto básico a partir de tu arquetipo elegido con tus personalizaciones seleccionadas. 
Puedes elegir descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

Una vez que hayas descargado tu proyecto, abre la carpeta del proyecto en tu IDE y continúa con [ejecutando la aplicación](#running-the-app).

## Usando la línea de comandos {#using-the-command-line}

Si prefieres usar la línea de comandos, puedes generar un proyecto directamente utilizando el arquetipo de Maven:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar tu aplicación, instala los [prerrequisitos](./prerequisites.md) si aún no lo has hecho. 
Luego, navega a la carpeta raíz del proyecto y ejecuta el siguiente comando:

```bash
# para aplicación estándar webforj
mvn jetty:run

# para webforj + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abre tu navegador y ve a [http://localhost:8080](http://localhost:8080) para ver la aplicación.

:::info Licencia y marca de agua
Para información sobre la marca de agua presente en proyectos no licenciados, consulta [Licencia y Marca de Agua](../configuration/licensing-and-watermark).
:::
