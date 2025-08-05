---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4d70b1e894fa3ca05afb5a4bc6ed982d
---
Este tutorial está diseñado para guiarte paso a paso a través del proceso de creación de la aplicación. Esta aplicación, diseñada para gestionar la información del cliente, demuestra cómo usar webforJ para construir una interfaz funcional y amigable con características para ver, agregar y editar datos de clientes. Cada sección se basará en la anterior, pero siéntete libre de saltar adelante según sea necesario.

Cada paso en el tutorial resultará en un programa que se compila en un archivo WAR, el cual puede ser desplegado en cualquier servidor de aplicaciones web Java. Para este tutorial, se utilizará el plugin Maven Jetty para desplegar la aplicación localmente. Esta configuración ligera asegura que la aplicación pueda ejecutarse rápidamente, y que los cambios se vean en tiempo real durante el desarrollo.

## Características de la aplicación del tutorial {#tutorial-app-features}

 - Trabajando con datos en una tabla.
 - Usando el [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) y gestión de activos.
 - [Enrutamiento](../../routing/overview) y [navegación](../../routing/route-navigation)
 - [Vínculos de datos](../../data-binding/overview) y [validación](../../data-binding/validation/overview)

## Requisitos previos {#prerequisites}

Para aprovechar al máximo este tutorial, se asume que tienes un entendimiento básico de programación en Java y estás familiarizado con herramientas como Maven. Si eres nuevo en webforJ, no te preocupes: los fundamentos del marco de trabajo se cubrirán a lo largo del camino.

Las siguientes herramientas/recursos deben estar presentes en tu máquina de desarrollo:

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

El tutorial se divide en las siguientes secciones. Procede secuencialmente para un recorrido completo, o salta adelante para información específica.

:::tip Configuración del proyecto
Para aquellos que buscan adelantar a temas específicos, se recomienda leer primero la sección de Configuración del Proyecto antes de continuar. 
:::

<DocCardList className="topics-section" />
