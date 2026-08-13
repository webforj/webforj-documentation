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
_i18n_hash: 6b642a9d173c5943acbb99934542e3a3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** es el entorno de desarrollo visual que viene con webforJ. Se ejecuta dentro de tu aplicación en modo de desarrollo y te ofrece una vista en vivo de los componentes que tu código Java ha creado. Puedes seleccionar un componente, cambiar sus propiedades, ver cómo la aplicación actualiza inmediatamente, y escribir los cambios que deseas conservar de vuelta en el archivo Java que los creó.

<!-- INTRO_END -->

Debido a que craftforJ lee la aplicación a través de webforJ, describe la aplicación en los términos en que la escribiste. El árbol lista tus componentes en lugar del marcado que el navegador renderizó, las propiedades son las que tus componentes declaran, y las rutas son las que tu enrutador registró, junto con las reglas de acceso que les anotaste.

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4" type="video/mp4" />
      </video>
</div>

## Lo que puedes hacer con ello {#what-you-can-do-with-it}

- **[Inspeccionar componentes](/docs/craftforj/inspector)** - navega por el árbol de componentes, selecciona un componente haciendo clic en él en la página, y cambia sus propiedades mientras la aplicación se ejecuta.
- **[Escribir cambios en la fuente](/docs/craftforj/source-changes)** - revisa tus ediciones en vivo como un diff y aplícalas a tus archivos Java.
- **[Trabajar con rutas](/docs/craftforj/routes)** - ve la tabla de enrutamiento, navega a cualquier ruta, y cambia las reglas de acceso declaradas en ella.
- **[Tematizar la aplicación](/docs/craftforj/theme)** - ajusta los tokens de diseño de los que se basa tu aplicación y guarda el resultado en tu hoja de estilos.
- **[Usar el agente de IA](/docs/craftforj/ai)** - un agente de codificación dentro de la aplicación en ejecución que escribe Java libremente, compila lo que escribió, y lo aplica con tu aprobación.

## Cómo se diferencia de un depurador {#how-it-differs-from-a-debugger}

Un depurador pausa tu código y te muestra el estado de tus variables en ese momento. craftforJ deja la aplicación en ejecución y te muestra la interfaz que tu código produjo, así que trabajas con el resultado en lugar de la ejecución. Ambos responden preguntas diferentes y comúnmente se usan juntos.

## Solo en modo de desarrollo {#development-mode-only}

craftforJ requiere que se habiliten dos configuraciones separadas y, por defecto, solo responde al navegador que se ejecuta en la misma máquina que la aplicación. Los proyectos creados con [startforJ](https://docs.webforj.com/startforj) o a partir de un [arquetipo de webforJ](/docs/building-ui/archetypes/overview) lo habilitan para ti, por lo que está disponible la primera vez que los ejecutas. Consulta [Seguridad](/docs/craftforj/security) para saber a qué puede acceder craftforJ y cómo confirmar que está desactivado en producción.

## Temas {#topics}

<DocCardList className="topics-section" />
