---
sidebar_position: 52
title: HTML Components
description: >-
  Compose pages with typed Java wrappers for standard HTML elements like Div,
  Anchor, Paragraph, Img, headings, and semantic containers.
_i18n_hash: 40b5b7346cf57ebc6795c87e25fe3a74
---
webforJ envía un conjunto de componentes que se mapean directamente a elementos HTML estándar, brindándote una API Java tipada para los bloques de construcción más comunes de las páginas web. Para una referencia completa de cada elemento HTML subyacente, consulta la [referencia de elementos HTML de MDN](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Componentes disponibles {#available-components}

Los siguientes componentes están disponibles y se mapean a sus correspondientes elementos HTML:

| Clase de webforJ | Elemento HTML | Descripción | Hijos |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Crea un hipervínculo a una URL, dirección de correo electrónico, o ubicación en la página. | ✔️ |
| `Article` | `<article>` | Representa una composición autosuficiente, como una entrada de blog, artículo de noticias, o entrada en un foro. | ✔️ |
| `Aside` | `<aside>` | Representa contenido que está indirectamente relacionado con el contenido principal, típicamente renderizado como una barra lateral. | ✔️ |
| `Break` | `<hr>` | Representa un salto temático entre elementos de nivel de párrafo, renderizado como una regla horizontal. | ❌ |
| `Div` | `<div>` | Un contenedor genérico de nivel bloque para agrupar y estilizar contenido. | ✔️ |
| `Emphasis` | `<em>` | Marca el texto con énfasis de estrés, cambiando el significado de una oración. | ✔️ |
| `Fieldset` | `<fieldset>` | Agrupa controles de formulario relacionados y sus etiquetas. | ✔️ |
| `Footer` | `<footer>` | Representa el pie de página de su ancestro de sección más cercano, típicamente conteniendo información de autoría o navegación. | ✔️ |
| `FormattedText` | `<pre>` | Muestra texto preformateado, preservando espacios en blanco y saltos de línea exactamente como se escribió. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Representan seis niveles de encabezados de sección, siendo `H1` el más importante y `H6` el menos importante. | ✔️ |
| `Header` | `<header>` | Representa contenido introductorio, típicamente conteniendo un encabezado, logo o enlaces de navegación. | ✔️ |
| `Iframe` | `<iframe>` | Inserta otra página HTML como un contexto de navegación anidado dentro de la página actual. | ❌ |
| `Img` | `<img>` | Inserta una imagen en el documento. | ❌ |
| `Legend` | `<legend>` | Proporciona un título para el contenido de un `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Representa un elemento individual en una `OrderedList` o `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Representa el contenido dominante del cuerpo del documento, único para la página. | ✔️ |
| `NativeButton` | `<button>` | Un elemento de botón interactivo nativo que puede desencadenar acciones o enviar formularios. | ✔️ |
| `Nav` | `<nav>` | Representa una sección de la página que contiene enlaces de navegación. | ✔️ |
| `OrderedList` | `<ol>` | Representa una lista de elementos numerada y ordenada. | ✔️ |
| `Paragraph` | `<p>` | Representa un párrafo de texto. | ✔️ |
| `Section` | `<section>` | Representa una sección genérica independiente de un documento, típicamente con un encabezado. | ✔️ |
| `Span` | `<span>` | Un contenedor en línea genérico para texto y otro contenido en línea. | ✔️ |
| `Strong` | `<strong>` | Indica contenido de fuerte importancia, típicamente renderizado en negrita. | ✔️ |
| `UnorderedList` | `<ul>` | Representa una lista de elementos con viñetas y desordenada. | ✔️ |

## Trabajando con hijos {#working-with-children}

Los componentes marcados con ✔️ en la columna **Hijos** soportan agregar, eliminar y acceder a componentes hijos. Estos métodos se proporcionan a través de la clase [`Element`](../building-ui/element#component-interaction).

Para crear elementos HTML arbitrarios más allá de los listados aquí, o para incrustar componentes web personalizados, consulta la documentación de [`Element`](../building-ui/element).
