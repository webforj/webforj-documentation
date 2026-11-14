---
sidebar_position: 1
title: craftforJ
slug: /craftforj
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Inspect the component tree of a running webforJ app, change components live,
  and write the changes you keep back into your Java source.
sidebar_class_name: new-content
_i18n_hash: 771ef18b5a680d5b9260f06a2704f877
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# craftforJ <DocChip chip='since' label='26.02' />
<!-- vale on -->

**craftforJ** es el entorno de desarrollo visual que incluye webforJ. Se ejecuta dentro de tu app en modo de desarrollo y te ofrece una vista en tiempo real de los componentes que creó tu código Java. Puedes seleccionar un componente, cambiar sus propiedades, ver cómo se actualiza la app al instante y escribir los cambios que quieras conservar en el archivo Java que lo creó.

<!-- INTRO_END -->

Como craftforJ lee la app a través de webforJ, la describe en los términos en que la escribiste. El árbol enumera tus componentes, no el marcado que generó el navegador; las propiedades son las que declaran tus componentes; y las rutas son las que registró tu enrutador, junto con las reglas de acceso que les asignaste mediante anotaciones.

<VideoPlayer video="craftforj/intro" width={1920} height={1080} title="Intro" />

## Qué puedes hacer con él {#what-you-can-do-with-it}

- **[Inspeccionar componentes](/docs/craftforj/inspector)**: explora el árbol de componentes, selecciona un componente haciendo clic en él en la página y cambia sus propiedades mientras la app está en ejecución.
- **[Escribir los cambios en el código fuente](/docs/craftforj/source-changes)**: revisa tus ediciones en tiempo real como un diff y aplícalas a tus archivos Java.
- **[Trabajar con rutas](/docs/craftforj/routes)**: consulta la tabla de enrutamiento, navega a cualquier ruta y cambia las reglas de acceso declaradas para ella.
- **[Aplicar un tema a la app](/docs/craftforj/theme)**: ajusta los tokens de diseño con los que se construye tu app y guarda el resultado en tu hoja de estilos.
- **[Usar el agente de IA](/docs/craftforj/ai)**: un agente de programación dentro de la app en ejecución que escribe código Java libremente, compila lo que escribió y lo aplica con tu aprobación.

## En qué se diferencia de un depurador {#how-it-differs-from-a-debugger}

Un depurador pausa tu código y te muestra el estado de las variables en ese momento. craftforJ mantiene la app en ejecución y te muestra la interfaz que produjo tu código, para que trabajes con el resultado en lugar de con la ejecución. Cada uno responde preguntas distintas y suelen usarse juntos.

## Solo en modo de desarrollo {#development-mode-only}

Para usar craftforJ, deben habilitarse dos opciones independientes y, de forma predeterminada, solo responde al navegador que se ejecuta en la misma máquina que la app. Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o a partir de un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ lo habilitan automáticamente, así que está disponible desde la primera vez que los ejecutas. Consulta [Seguridad](/docs/craftforj/security) para saber a qué puede acceder craftforJ y cómo confirmar que está desactivado en producción.

## Temas {#topics}

<DocCardList className="topics-section" />
