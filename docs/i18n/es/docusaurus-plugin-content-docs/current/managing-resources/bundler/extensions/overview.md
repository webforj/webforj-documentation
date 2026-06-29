---
title: Extensions
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Understand how a bundler extension contributes a compiler, the three ways an
  extension activates, how to configure one through bun.config.ts, and how to
  write your own.
_i18n_hash: 00c5421ebf8023e2d273f3836176733e
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

El empaquetador no sabe cómo compilar una hoja de estilo SCSS por su cuenta. Ese es el trabajo de una **extensión**: una unidad que contribuye el compilador para un tipo de fuente, declara los paquetes npm que ese compilador necesita y puede generar entradas propias. webforJ envía algunas extensiones, y el modelo es abierto, por lo que puedes agregar las tuyas para cualquier otra fuente. SCSS, Less y Tailwind no son características separadas añadidas al empaquetador. Son extensiones y comparten un mismo modelo.

Una vez que sabes cómo se activa una extensión y qué contribuye, cada extensión se lee de la misma manera, tanto las enviadas como las que escribes.

## Lo que una extensión contribuye {#what-an-extension-contributes}

A una extensión se le da la oportunidad de actuar antes de que se ejecute la compilación. Cuando actúa, puede hacer tres cosas:

- **Declarar paquetes.** Agrega los paquetes npm que su compilador necesita, generalmente como dependencias de construcción, para que se instalen antes de la compilación.
- **Contribuir un compilador.** Registra un complemento de Bun que enseña a la compilación cómo manejar un tipo de fuente.
- **Generar entradas.** Puede escribir una entrada frontal propia, que es cómo Tailwind produce una hoja de estilo sin que tú escribas una.

Cada extensión lleva un **id**, un nombre corto como `webforj-scss` o `webforj-less`. El id es como te refieres a una extensión en la configuración y cómo sus opciones la alcanzan.

## Cómo se activa una extensión {#how-an-extension-activates}

Una extensión solo realiza su trabajo cuando está activa. Hay tres formas en las que puede activarse, y saber qué camino utiliza una integración te dice exactamente cuándo se ejecuta.

### Activada por tipo de archivo {#activated-by-file-type}

Una extensión curada declara las extensiones de archivo que maneja, y se activa cuando hay una fuente de ese tipo presente en `src/main/frontend`. No la habilitas. Escribir el archivo es la señal.

Escribe un archivo `.scss`, y la extensión SCSS se activa. Escribe un archivo `.less`, y la extensión Less se activa. Elimina la última fuente de ese tipo, y la extensión se mantiene apagada, por lo que sus paquetes nunca se instalan y su compilador nunca se ejecuta. Esto mantiene una construcción ligera: un proyecto solo paga por los tipos de fuente que realmente autoriza. Una extensión que escribas sigue la misma regla, activándose en el tipo de archivo que declara.

### Habilitada por id {#enabled-by-id}

Cada extensión puede activarse o desactivarse por su id, que anula el predeterminado de tipo de archivo. Esto importa en dos casos. Una extensión que se envía apagada, como Tailwind, se activa por id. Y una extensión que se activaría por una fuente presente puede ser forzada a apagarse por id.

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <plugins>
      <webforj-tailwind>true</webforj-tailwind>
      <webforj-scss>false</webforj-scss>
    </plugins>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  plugins.put('webforj-tailwind', 'true')
  plugins.put('webforj-scss', 'false')
}
```

</TabItem>
</Tabs>

El bloque anterior activa Tailwind y desactiva la compilación de SCSS, incluso si hay una fuente `.scss` presente.

### Cuando no se necesita una extensión {#when-no-extension-is-needed}

Una extensión existe para contribuir un compilador. Un marco que se renderiza en tiempo de ejecución, en lugar de compilar algo nuevo, no necesita compilador, por lo que no necesita extensión. Bun ya maneja el TypeScript y JSX en los que está escrito dicho marco.

[React](https://react.dev/) es una biblioteca de JavaScript para construir interfaces de usuario, y es el caso más claro. Su entrada es un TypeScript plano que Bun compila sin complemento. Envuelves el componente como un elemento personalizado con una biblioteca de tu elección, como `@r2wc/react-to-web-component`, declaras los paquetes de React con `@BundlePackage`, y la vista consume el elemento resultante. No hay extensión de React que habilitar, porque no hay compilador de React que contribuir, solo paquetes para instalar.

## Configurando una extensión {#configuring-an-extension}

Algunas extensiones aceptan opciones. El compilador SCSS toma una ruta de carga, por ejemplo. Las opciones se encuentran en `bun.config.ts`, un archivo que creas en la raíz de tu fuente frontal, `src/main/frontend/bun.config.ts`, al lado de las entradas que autoras. Van en un objeto `options` indexado por el id de la extensión, y la construcción le entrega a cada extensión el objeto almacenado bajo su propio id:

```ts title="src/main/frontend/bun.config.ts"
export const options = {
  'webforj-scss': { loadPaths: ['styles'] }
};
```

:::tip De dónde vienen las opciones
Una extensión reenvía sus opciones directamente a la herramienta que envuelve, por lo que las opciones que una extensión acepta son las opciones de esa herramienta. Cada página de extensión a continuación nombra la herramienta a la que reenvía y enlaza a la referencia de esa herramienta, donde encontrarás la lista completa.
:::

El mismo archivo puede agregar complementos adicionales de Bun a través de una exportación predeterminada, para un paso de construcción que ninguna extensión curada cubre:

```ts title="src/main/frontend/bun.config.ts"
import myPlugin from './my-plugin';

export default [myPlugin()];
```

## Las extensiones curadas {#the-curated-extensions}

webforJ envía una extensión para SCSS, Less y Tailwind. Cada una sigue el modelo anterior: se activa cuando su tipo de fuente está presente, declara los paquetes que su compilador necesita como dependencias de construcción y contribuye el compilador.

| Extensión | Id | Se activa en | Instala (dependencia de construcción) |
|-----------|----|--------------|---------------------------------------|
| [SCSS y Sass](/docs/managing-resources/bundler/extensions/scss) | `webforj-scss` | una fuente `.scss` o `.sass` | `sass` |
| [Less](/docs/managing-resources/bundler/extensions/less) | `webforj-less` | una fuente `.less` | `less` |
| [Tailwind](/docs/managing-resources/bundler/extensions/tailwind) | `webforj-tailwind` | apagado por defecto, habilitado por id | `tailwindcss`, `bun-plugin-tailwind` |

Para compilar otro tipo de fuente, escribes tu propia extensión bajo el mismo contrato. Consulta [Escribiendo tu propia extensión](/docs/managing-resources/bundler/extensions/writing-your-own), que construye una de principio a fin.
