---
title: Overview
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 781bf0258ed2366e2125e99587cda439
---
Este tutorial paso a paso te guía a través del proceso de construcción de una aplicación de gestión de clientes utilizando webforJ y Spring Boot. Te enseña cómo crear una interfaz moderna y fácil de usar para ver, agregar y editar datos de clientes.

Cada paso introduce nuevos conceptos y resulta en una aplicación ejecutable de Spring Boot (JAR). Puedes lanzar tu aplicación localmente utilizando Maven e interactuar con ella en un navegador web. Con esta configuración, obtienes un ciclo de desarrollo rápido y un modelo de despliegue listo para producción, utilizando el servidor embebido de Spring Boot.

No se necesita experiencia previa en Spring Boot o webforJ, pero deberías tener un conocimiento básico de Java y Maven para aprovechar al máximo este tutorial. Este tutorial cubrirá conceptos de Spring a medida que surjan, pero aquellos interesados en una comprensión profunda de Spring pueden consultar [la documentación principal de Spring](https://spring.io/learn) y la documentación de Spring sobre [Spring Boot](https://docs.spring.io/spring-boot/index.html).

## Conceptos del tutorial {#tutorial-concepts}

La primera parte del tutorial está dedicada a [la configuración del proyecto](/docs/introduction/tutorial/project-setup) para preparar tu ambiente de Spring Boot + webforJ. Luego, los pasos siguientes introducen nuevas características y avanzan tu proyecto. Al seguirlo, obtendrás una comprensión clara de cómo evoluciona una aplicación a medida que implementas funcionalidades.

Cada paso tiene una aplicación correspondiente ejecutable disponible en GitHub:

| Paso | Documentación | GitHub |
| ----- | ----- | ----- |
| 1 | [Creando una aplicación básica](/docs/introduction/tutorial/creating-a-basic-app)                               | [Aplicación del Paso 1](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Trabajando con datos](/docs/introduction/tutorial/working-with-data)                                     | [Aplicación del Paso 2](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Rutas y compuestos](/docs/introduction/tutorial/routing-and-composites)                           | [Aplicación del Paso 3](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Observadores y parámetros de ruta](/docs/introduction/tutorial/observers-and-route-parameters)           | [Aplicación del Paso 4](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Validando y vinculando datos](/docs/introduction/tutorial/validating-and-binding-data)                 | [Aplicación del Paso 5](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)
| 6 | [Integrando un diseño de aplicación](/docs/introduction/tutorial/integrating-an-app-layout)                     | [Aplicación del Paso 6](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)

## Prerrequisitos {#prerequisites}

Deberías tener las siguientes herramientas/recursos en tu máquina de desarrollo:

- Java 21 o 25
- Maven
- Un IDE de Java
- Git (recomendado pero no obligatorio)

:::info Prerrequisitos de webforJ
Consulta el [artículo de prerrequisitos](/docs/introduction/prerequisites) para una descripción más detallada de las herramientas requeridas para tu entorno de desarrollo.
:::

<DocCardList className="topics-section" />
