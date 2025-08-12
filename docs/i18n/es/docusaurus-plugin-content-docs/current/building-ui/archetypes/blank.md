---
title: Blank
sidebar_position: 1
hide_table_of_contents: true
_i18n_hash: 5e7b116f0fea5cee2aa0d880d6fee05a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Plantilla en blanco

La plantilla `blank` es un proyecto inicial fundamental para aplicaciones webforJ. Este template proporciona un lienzo limpio para que construyas tu aplicación desde cero. Es ideal para desarrolladores que desean tener control total sobre la estructura y los componentes de su aplicación sin ninguna restricción predefinida.

:::tip Usando startforJ
Para más control sobre la personalización y configuración, puedes usar [startforJ](https://docs.webforj.com/startforj/) para crear tu proyecto; solo selecciona la plantilla `Blank` al elegir las opciones de configuración.
:::

## Usando la plantilla `blank` {#using-the-blank-archetype}

<ComponentArchetype
project="blank"
/>

## Ejecutando la aplicación {#running-the-app}

Antes de ejecutar tu aplicación, instala los [prerequisitos](../../introduction/prerequisites) si aún no lo has hecho. Luego, navega al directorio raíz del proyecto y ejecuta el siguiente comando:

```bash
# para aplicación webforJ estándar
mvn jetty:run

# para webforJ + Spring Boot
mvn spring-boot:run
```

Una vez que el servidor esté en funcionamiento, abre tu navegador y dirígete a [http://localhost:8080](http://localhost:8080) para ver la aplicación.
