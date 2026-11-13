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
_i18n_hash: 727e01ac5960e204363665aba672d6c7
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

**craftforJ** es el entorno de desarrollo visual que incluye webforJ. Se ejecuta dentro de tu aplicación en modo de desarrollo y te ofrece una vista en vivo de los componentes que creó tu código Java. Puedes seleccionar un componente, cambiar sus propiedades, ver cómo se actualiza la aplicación en ejecución al instante y guardar los cambios que quieras conservar en el archivo Java que lo creó.

<!-- INTRO_END -->

Como craftforJ lee la aplicación a través de webforJ, la describe con los mismos términos que usaste para escribirla. El árbol muestra tus componentes, en lugar del marcado que generó el navegador; las propiedades son las que declaran tus componentes; y las rutas son las que registró tu enrutador, junto con las reglas de acceso que les anotaste.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Qué puedes hacer con craftforJ {#what-you-can-do-with-it}

- **[Inspeccionar componentes](/docs/craftforj/inspector)** - explora el árbol de componentes, selecciona un componente haciendo clic en él en la página y cambia sus propiedades mientras la aplicación se ejecuta.
- **[Escribir cambios en el código fuente](/docs/craftforj/source-changes)** - revisa tus ediciones en vivo como diferencias y aplícalas a tus archivos Java.
- **[Trabajar con rutas](/docs/craftforj/routes)** - consulta la tabla de enrutamiento, navega a cualquier ruta y cambia las reglas de acceso declaradas en ella.
- **[Aplicar un tema a la aplicación](/docs/craftforj/theme)** - ajusta los tokens de diseño con los que se construye tu aplicación y guarda el resultado en tu hoja de estilos.
- **[Usar el agente de IA](/docs/craftforj/ai)** - un agente de programación dentro de la aplicación en ejecución que escribe código Java libremente, lo compila y lo aplica con tu aprobación.

## En qué se diferencia de un depurador {#how-it-differs-from-a-debugger}

Un depurador pausa el código y muestra el estado de las variables en ese momento. craftforJ deja la aplicación en ejecución y muestra la interfaz que produjo tu código, para que trabajes con el resultado y no con la ejecución. Responden a preguntas distintas y se suelen usar juntos.

## Solo en modo de desarrollo {#development-mode-only}

Para habilitar craftforJ, es necesario activar dos configuraciones independientes. De forma predeterminada, solo responde al navegador que se ejecuta en la misma máquina que la aplicación. Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o a partir de un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ lo habilitan por ti, así que está disponible la primera vez que los ejecutas. Consulta [Seguridad](/docs/craftforj/security) para saber a qué puede acceder craftforJ y cómo confirmar que está desactivado en producción.

## Temas {#topics}

<DocCardList className="topics-section" />
