---
title: HelloWorld
sidebar_position: 4
hide_table_of_contents: true
_i18n_hash: e1da494f783aca68616cd374b92e700c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# Archetipo HelloWorld
<!-- vale on -->

Este arquetipo crea una aplicación simple de hola mundo para demostrar lo básico de construir una interfaz de usuario con webforJ. Esta plantilla es ideal para principiantes que desean comenzar rápidamente. Proporciona un ejemplo claro de cómo configurar y ejecutar una aplicación webforJ básica, lo que la convierte en un excelente punto de partida para nuevos desarrolladores.

:::tip Comenzando desde cero
Este arquetipo crea una aplicación minimalista con algunos componentes y algo de estilo. Para los desarrolladores que deseen crear un proyecto con una estructura mínima, consulte el [arquetipo `blank`](./blank).
:::

:::tip Usando startforJ
Para más control sobre la personalización y configuración, puede usar [startforJ](https://docs.webforj.com/startforj/) para crear su proyecto: solo seleccione el arquetipo `HelloWorld` al elegir las opciones de configuración.
:::

## Usando el arquetipo `hello-world` {#using-the-hello-world-archetype}

<ComponentArchetype
project="hello-world"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar su aplicación, instale los [prerrequisitos](../../introduction/prerequisites) si aún no lo ha hecho. Luego, navegue a la raíz del proyecto y ejecute el siguiente comando:

```bash
# para aplicación webforJ estándar
mvn jetty:run

# para webforJ + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abra su navegador y vaya a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
