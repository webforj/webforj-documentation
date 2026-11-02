---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: b8c2cd3c3c48b561e6796ef23aa73fe7
---
webforJ envía un conjunto de componentes que se mapean directamente a elementos HTML estándar, brindándote una API Java tipada para los bloques de construcción más comunes de las páginas web. Para un referencia completa sobre cada elemento HTML subyacente, consulta la [referencia de elementos HTML de MDN](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Componentes disponibles {#available-components}

Los siguientes componentes están disponibles y se corresponden con sus elementos HTML:

| Clase webforJ | Elemento HTML | Descripción | Hijos |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Crea un hipervínculo a una URL, dirección de correo electrónico o ubicación en la página. | ✔️ |
| `Article` | `<article>` | Representa una composición autónoma como una entrada de blog, artículo de noticias o entrada de foro. | ✔️ |
| `Aside` | `<aside>` | Representa contenido que está indirectamente relacionado con el contenido principal, típicamente renderizado como una barra lateral. | ✔️ |
| `Break` | `<hr>` | Representa una ruptura temática entre elementos a nivel de párrafo, renderizada como una regla horizontal. | ❌ |
| `Div` | `<div>` | Un contenedor genérico a nivel de bloque para agrupar y estilizar contenido. | ✔️ |
| `Emphasis` | `<em>` | Marca el texto con énfasis de estrés, cambiando el significado de una oración. | ✔️ |
| `Fieldset` | `<fieldset>` | Agrupa controles de formulario relacionados y sus etiquetas. | ✔️ |
| `Footer` | `<footer>` | Representa el pie de su ancestro de sección más cercano, típicamente conteniendo información de autoría o de navegación. | ✔️ |
| `FormattedText` | `<pre>` | Muestra texto preformateado, preservando espacios en blanco y saltos de línea exactamente como se escribió. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Representan seis niveles de encabezados de sección, siendo `H1` el más importante y `H6` el menos importante. | ✔️ |
| `Header` | `<header>` | Representa contenido introductorio, típicamente conteniendo un encabezado, logo o enlaces de navegación. | ✔️ |
| `Iframe` | `<iframe>` | Incrusta otra página HTML como un contexto de navegación anidado dentro de la página actual. | ❌ |
| `Img` | `<img>` | Incrusta una imagen en el documento. | ❌ |
| `Legend` | `<legend>` | Proporciona un título para el contenido de un `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Representa un ítem individual en una `OrderedList` o `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Representa el contenido dominante del cuerpo del documento, único para la página. | ✔️ |
| `NativeButton` | `<button>` | Un elemento de botón interactivo nativo que puede disparar acciones o enviar formularios. | ✔️ |
| `Nav` | `<nav>` | Representa una sección de la página que contiene enlaces de navegación. | ✔️ |
| `OrderedList` | `<ol>` | Representa una lista numerada y ordenada de elementos. | ✔️ |
| `Paragraph` | `<p>` | Representa un párrafo de texto. | ✔️ |
| `Section` | `<section>` | Representa una sección genérica independiente de un documento, típicamente con un encabezado. | ✔️ |
| `Span` | `<span>` | Un contenedor genérico en línea para texto y otro contenido en línea. | ✔️ |
| `Strong` | `<strong>` | Indica contenido de fuerte importancia, típicamente renderizado en negrita. | ✔️ |
| `UnorderedList` | `<ul>` | Representa una lista no ordenada y con viñetas de elementos. | ✔️ |

## Trabajando con hijos {#working-with-children}

Cada componente HTML extiende `HtmlComponent`. Los componentes marcados con ✔️ en la columna **Hijos** extienden `HtmlComponentContainer`, que agrega los métodos para agregar, eliminar y acceder a componentes hijos. Los componentes sin hijos, como `Break`, extienden `HtmlComponent` directamente.

Para crear elementos HTML arbitrarios más allá de los listados aquí, o para incrustar componentes web personalizados, consulta la documentación de [`Element`](../building-ui/element).
