---
title: Getting Started
sidebar_position: 2
_i18n_hash: 24c0a494b270fb4ea83106005e173ae8
---
Este artículo describe los pasos para crear una nueva aplicación webforJ utilizando los [archetypes](../building-ui/archetypes/overview.md) de webforJ. Los archetypes proporcionan estructuras de proyecto preconfiguradas y código inicial para que pueda iniciar un proyecto de manera rápida.

Para crear una nueva aplicación webforJ a partir de un archetype, puede usar [startforJ](#using-startforj) o la [línea de comandos](#using-the-command-line).

:::tip Prerrequisitos
Antes de comenzar, revise los [prerrequisitos](./prerequisites) necesarios para configurar y utilizar webforJ.
:::

## Usando startforJ {#using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es a través de [startforJ](https://docs.webforj.com/startforj), que genera un proyecto mínimo inicial basado en un archetype de webforJ elegido. Este proyecto inicial incluye todas las dependencias requeridas, archivos de configuración y un diseño prehecho, para que pueda comenzar a construir de inmediato.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />
  </video>
</div>

### Personalizando con startforJ {#customizing-with-startforj}

Cuando crea una aplicación con [startforJ](https://docs.webforj.com/startforj), puede personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la aplicación, ID de grupo, ID de artefacto)  
- versión de webforJ y versión de Java
- Color del tema e ícono
- Archetype
- Sabor

Hay dos opciones de sabor para elegir, siendo "solo webforJ" la opción predeterminada:
  - **Solo webforJ**: Aplicación estándar de webforJ
  - **webforJ + Spring Boot**: Aplicación de webforJ con soporte de Spring Boot

:::caution Soporte de Spring Boot
El sabor de Spring Boot solo está disponible en la versión 25.02 de webforJ y superiores. Si selecciona esta opción, asegúrese de elegir una versión compatible.
:::

:::tip Archetypes disponibles
webforJ viene con varios archetypes predefinidos para ayudarle a comenzar rápidamente. Para ver la lista completa de archetypes disponibles, consulte el [catálogo de archetypes](../building-ui/archetypes/overview).
:::

Con esta información, startforJ creará un proyecto básico a partir de su archetype seleccionado con las personalizaciones elegidas. Puede optar por descargar su proyecto como un archivo ZIP o publicarlo directamente en GitHub.

Una vez que haya descargado su proyecto, abra la carpeta del proyecto en su IDE y continúe con [ejecutar la aplicación](#running-the-app).

## Usando la línea de comandos {#using-the-command-line}

Si prefiere usar la línea de comandos, puede generar un proyecto directamente utilizando el archetype de Maven:

<ComponentArchetype
project="hello-world"
flavor="webforj"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar su aplicación, instale los [prerrequisitos](./prerequisites.md) si aún no lo ha hecho. Luego, navegue hasta el directorio raíz del proyecto y ejecute el siguiente comando:

```bash
# para aplicación estándar webforj
mvn jetty:run

# para webforj + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abra su navegador y vaya a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
