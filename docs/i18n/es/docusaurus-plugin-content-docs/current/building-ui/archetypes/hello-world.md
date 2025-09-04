---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: 145d1e89a5f688fa0c912b87056a35d1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# Arquetipo HelloWorld
<!-- vale on -->

Este arquetipo crea una aplicación simple hello world para demostrar los conceptos básicos de construir una interfaz de usuario con webforJ. Este template es ideal para principiantes que desean comenzar rápidamente. Proporciona un ejemplo sencillo de cómo configurar y ejecutar una aplicación básica de webforJ, convirtiéndolo en un excelente punto de partida para nuevos desarrolladores.

:::tip Comenzando desde cero
Este arquetipo crea una aplicación minimalista con algunos componentes y un poco de estilo. Para los desarrolladores que deseen crear un proyecto con una estructura mínima, consulte el [`arquetipo en blanco`](./blank).
:::

:::tip Usando startforJ
Para tener más control sobre la personalización y configuración, puede utilizar [startforJ](https://docs.webforj.com/startforj/) para crear su proyecto; simplemente seleccione el arquetipo `HelloWorld` al elegir las opciones de configuración.
:::

## Usando el arquetipo `hello-world` {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar su aplicación, instale los [requisitos previos](../../introduction/prerequisites) si aún no lo ha hecho. 
Luego, navegue hasta el directorio raíz del proyecto y ejecute el siguiente comando:

```bash
# para la aplicación estándar de webforJ
mvn jetty:run

# para webforJ + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abra su navegador y dirígete a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
