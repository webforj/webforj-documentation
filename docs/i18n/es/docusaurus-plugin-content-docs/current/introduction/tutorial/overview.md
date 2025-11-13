---
title: Resumen
hide_giscus_comments: true
_i18n_hash: 4174ea766ba47277c5bcb607c4111e29
---
Este tutorial está diseñado para guiarte paso a paso a través del proceso de creación de la aplicación. Esta aplicación, diseñada para gestionar la información del cliente, demuestra cómo usar webforJ para construir una interfaz funcional y fácil de usar con características para ver, agregar y editar datos de clientes. Cada sección se basará en la anterior, pero siéntete libre de avanzar según sea necesario.

Cada paso en el tutorial resultará en un programa que se compila en un archivo WAR, que puede ser desplegado en cualquier servidor de aplicaciones web Java. Para este tutorial, se utilizará el plugin Maven Jetty para desplegar la aplicación localmente. Esta configuración ligera garantiza que la aplicación pueda ejecutarse rápidamente y que los cambios se vean en tiempo real durante el desarrollo.

## Características de la aplicación del tutorial {#tutorial-app-features}

 - Trabajando con datos en una tabla.
 - Usando el [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) y gestión de activos.
 - [Enrutamiento](../../routing/overview) y [navegación](../../routing/route-navigation)
 - [Vinculaciones de datos](../../data-binding/overview) y [validación](../../data-binding/validation/overview)

## Requisitos previos {#prerequisites}

Para aprovechar al máximo este tutorial, se asume que tienes un conocimiento básico de programación en Java y estás familiarizado con herramientas como Maven. Si eres nuevo en webforJ, no te preocupes: los fundamentos del framework se cubrirán a lo largo del camino.

Las siguientes herramientas/recursos deberían estar presentes en tu máquina de desarrollo

<!-- vale off -->
- Java 17 o superior
- Maven
- Un IDE de Java
- Un navegador web
- Git (recomendado pero no obligatorio)
<!-- vale on -->

:::tip Requisitos previos de webforJ
Consulta [este artículo](../prerequisites) para una visión más detallada de las herramientas requeridas.
:::

## Secciones {#sections}

El tutorial se divide en las siguientes secciones. Procede secuencialmente para una guía completa, o salta adelante para información específica.

:::tip Configuración del proyecto
Para aquellos que buscan saltar a temas específicos, se recomienda primero leer la sección de Configuración del Proyecto antes de avanzar.
:::

<DocCardList className="topics-section" />
