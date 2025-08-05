---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 135ed95be60a01a6a5ccb297c6bcce8f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Arquetipo en blanco

El arquetipo `blank` es un proyecto base fundamental para aplicaciones webforJ. Esta plantilla ofrece un lienzo limpio para que construyas tu aplicación desde cero. Es ideal para desarrolladores que desean tener control completo sobre la estructura y los componentes de su aplicación sin ninguna restricción predefinida.

:::tip Usando startforJ
Para tener más control sobre la personalización y la configuración, puedes utilizar [startforJ](https://docs.webforj.com/startforj/) para crear tu proyecto: solo selecciona el arquetipo `Blank` al elegir las opciones de configuración.
:::

## Uso del arquetipo `blank` {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar tu aplicación, instala los [prerrequisitos](../../introduction/prerequisites) si aún no lo has hecho.
Luego, navega al directorio raíz del proyecto y ejecuta el siguiente comando:

```bash
# para la aplicación webforJ estándar
mvn jetty:run

# para webforJ + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abre tu navegador y ve a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
