---
title: Getting Started
sidebar_position: 2
_i18n_hash: 32a742a43fe6dd9e983eaf428e04e06d
---
Este artículo describe los pasos para crear una nueva aplicación webforJ utilizando los [archetypes](../building-ui/archetypes/overview.md) de webforJ. Los archetypes proporcionan estructuras de proyecto preconfiguradas y código de inicio para que puedas tener un proyecto funcionando rápidamente.  
Para crear una nueva aplicación webforJ a partir de un archetype, puedes usar [startforJ](#using-startforj) o la [línea de comandos](#using-the-command-line).

:::tip Requisitos previos  
Antes de comenzar, revisa los [requisitos previos](./prerequisites) necesarios para configurar y usar webforJ.  
:::

## Usando startforJ {#using-startforj}

La forma más sencilla de crear una nueva aplicación webforJ es a través de [startforJ](https://docs.webforj.com/startforj), que genera un proyecto de inicio mínimo basado en un archetype de webforJ elegido. Este proyecto de inicio incluye todas las dependencias requeridas, archivos de configuración y un diseño predefinido, por lo que puedes comenzar a construir sobre él de inmediato.

<div class="videos-container">  
  <video controls>  
    <source src="https://cdn.webforj.com/webforj-documentation/video/archetypes/startforj.mp4" type="video/mp4" />  
  </video>  
</div>  

### Personalizando con startforJ {#customizing-with-startforj}

Cuando creas una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (Nombre de la Aplicación, ID de Grupo, ID de Artefacto)  
- versión de webforJ y versión de Java  
- Color y Icono del Tema  
- Archetype  
- Sabor  

Hay dos opciones de sabor para elegir, siendo "webforJ Only" la predeterminada:  
  - **webforJ Only**: Aplicación estándar de webforJ  
  - **webforJ + Spring Boot**: Aplicación de webforJ con soporte para Spring Boot  

:::caution Soporte para Spring Boot  
El sabor de Spring Boot solo está disponible en la versión 25.02 y superior de webforJ. Si seleccionas esta opción, asegúrate de elegir una versión compatible.  
:::

:::tip Archetypes disponibles  
webforJ viene con varios archetypes predefinidos para ayudarte a comenzar rápidamente. Para ver una lista completa de los archetypes disponibles, consulta el [catálogo de archetypes](../building-ui/archetypes/overview).  
:::

Con esta información, startforJ crearás un proyecto básico de tu archetype elegido con tus personalizaciones seleccionadas. Puedes optar por descargar tu proyecto como un archivo ZIP o publicarlo directamente en GitHub.

Una vez que hayas descargado tu proyecto, abre la carpeta del proyecto en tu IDE y continúa con [ejecutando la aplicación](#running-the-app).

## Usando la línea de comandos {#using-the-command-line}

Si prefieres utilizar la línea de comandos, puedes generar un proyecto directamente utilizando el archetype de Maven:

<ComponentArchetype  
project="hello-world"  
flavor="webforj"  
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar tu aplicación, instala los [requisitos previos](./prerequisites.md) si aún no lo has hecho.  
Luego, navega al directorio raíz del proyecto y ejecuta el siguiente comando:

```bash
# para una aplicación estándar de webforj  
mvn jetty:run  

# para webforj + Spring Boot  
mvn spring-boot:run  
```

Una vez que el servidor esté en funcionamiento, abre tu navegador y ve a [http://localhost:8080](http://localhost:8080) para ver la aplicación.

:::info Licencias y marca de agua  
Para información sobre la marca de agua presente en proyectos no licenciados, consulta [Licencias y Marca de Agua](../configuration/licensing-and-watermark).  
:::
