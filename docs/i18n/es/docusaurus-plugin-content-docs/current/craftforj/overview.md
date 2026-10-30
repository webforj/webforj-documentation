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
_i18n_hash: e5fd219fd4360d24535c26696d1c9d1d
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** es el entorno de desarrollo visual que se incluye con webforJ. Se ejecuta dentro de tu aplicación en modo de desarrollo y te brinda una vista en vivo de los componentes que tu código Java creó. Puedes seleccionar un componente, cambiar sus propiedades, ver cómo la aplicación en ejecución se actualiza inmediatamente y escribir los cambios que deseas conservar de vuelta en el archivo Java que los creó.

<!-- INTRO_END -->

Debido a que craftforJ lee la aplicación a través de webforJ, describe la aplicación en los términos en los que la escribiste. El árbol lista tus componentes en lugar del marcado que el navegador renderizó, las propiedades son las que tus componentes declararon y las rutas son las que tu enrutador registró, junto con las reglas de acceso que les anotaste.

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## Lo que puedes hacer con esto {#what-you-can-do-with-it}

- **[Inspeccionar componentes](/docs/craftforj/inspector)** - navega por el árbol de componentes, selecciona un componente haciendo clic en él en la página y cambia sus propiedades mientras la aplicación se ejecuta.
- **[Escribir cambios en el código fuente](/docs/craftforj/source-changes)** - revisa tus ediciones en vivo como un diff y aplícalas a tus archivos Java.
- **[Trabajar con rutas](/docs/craftforj/routes)** - ve la tabla de enrutamiento, navega a cualquier ruta y cambia las reglas de acceso declaradas sobre ella.
- **[Tematizar la aplicación](/docs/craftforj/theme)** - ajusta los tokens de diseño de los que se construyó tu aplicación y guarda el resultado en tu hoja de estilos.
- **[Usar el agente de IA](/docs/craftforj/ai)** - un agente de codificación dentro de la aplicación en ejecución que escribe Java libremente, compila lo que escribió y lo aplica con tu aprobación.

## Cómo se diferencia de un depurador {#how-it-differs-from-a-debugger}

Un depurador pausa tu código y te muestra el estado de tus variables en ese momento. craftforJ deja la aplicación en ejecución y te muestra la interfaz que tu código produjo, por lo que trabajas con el resultado en lugar de con la ejecución. Ambos responden a diferentes preguntas y se utilizan comúnmente juntos.

## Solo en modo de desarrollo {#development-mode-only}

craftforJ requiere que se habiliten dos configuraciones separadas, y por defecto, solo responde al navegador que se ejecuta en la misma máquina que la aplicación. Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o desde un [arquetipo](/docs/building-ui/archetypes/overview) de webforJ lo habilitan para ti, así que estará disponible la primera vez que los ejecutes. Consulta [Seguridad](/docs/craftforj/security) para ver qué puede alcanzar craftforJ y cómo confirmar que está desactivado en producción.

## Temas {#topics}

<DocCardList className="topics-section" />
