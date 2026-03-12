---
sidebar_position: 155
title: HTML Element Components
_i18n_hash: b2842b1d092327e8364bbe72fc09ac49
---
webforJ envía un conjunto de componentes que se asignan directamente a los elementos HTML estándar, ofreciéndote una API Java tipada para los bloques de construcción más comunes de las páginas web. Para una referencia completa sobre cada elemento HTML subyacente, consulta la [referencia de elementos HTML de MDN](https://developer.mozilla.org/en-US/docs/Web/HTML/Element).

## Componentes disponibles

Los siguientes componentes están disponibles y se asignan a sus correspondientes elementos HTML:

| Clase de webforJ | Elemento HTML | Descripción | Hijos |
|:--|:--:|:--|:--:|
| `Anchor` | `<a>` | Crea un hiperenlace a una URL, dirección de correo electrónico o ubicación en la página. | ✔️ |
| `Article` | `<article>` | Representa una composición autónoma, como una entrada de blog, artículo de noticias o entrada de foro. | ✔️ |
| `Aside` | `<aside>` | Representa contenido que está indirectamente relacionado con el contenido principal, típicamente renderizado como una barra lateral. | ✔️ |
| `Break` | `<hr>` | Representa una ruptura temática entre elementos de párrafo, renderizado como una regla horizontal. | ❌ |
| `Div` | `<div>` | Un contenedor genérico de nivel de bloque para agrupar y estilizar contenido. | ✔️ |
| `Emphasis` | `<em>` | Marca el texto con énfasis, cambiando el significado de una oración. | ✔️ |
| `Fieldset` | `<fieldset>` | Agrupa controles de formulario relacionados y sus etiquetas. | ✔️ |
| `Footer` | `<footer>` | Representa el pie de página de su ancestro de sección más cercano, que suele contener información sobre la autoría o enlaces de navegación. | ✔️ |
| `FormattedText` | `<pre>` | Muestra texto preformateado, preservando espacios en blanco y saltos de línea exactamente como se escribió. | ✔️ |
| `H1` – `H6` | `<h1>` – `<h6>` | Representan seis niveles de encabezados de sección, siendo `H1` el más importante y `H6` el menos importante. | ✔️ |
| `Header` | `<header>` | Representa contenido introductorio, típicamente conteniendo un encabezado, logo o enlaces de navegación. | ✔️ |
| `Iframe` | `<iframe>` | Inserta otra página HTML como un contexto de navegación anidado dentro de la página actual. | ❌ |
| `Img` | `<img>` | Inserta una imagen en el documento. | ❌ |
| `Legend` | `<legend>` | Proporciona un título para el contenido de un `Fieldset`. | ❌ |
| `ListEntry` | `<li>` | Representa un elemento individual en una `OrderedList` o `UnorderedList`. | ✔️ |
| `Main` | `<main>` | Representa el contenido dominante del cuerpo del documento, único para la página. | ✔️ |
| `NativeButton` | `<button>` | Un elemento de botón interactivo nativo que puede activar acciones o enviar formularios. | ✔️ |
| `Nav` | `<nav>` | Representa una sección de la página que contiene enlaces de navegación. | ✔️ |
| `OrderedList` | `<ol>` | Representa una lista de elementos ordenada por números. | ✔️ |
| `Paragraph` | `<p>` | Representa un párrafo de texto. | ✔️ |
| `Section` | `<section>` | Representa una sección independiente genérica de un documento, típicamente con un encabezado. | ✔️ |
| `Span` | `<span>` | Un contenedor en línea genérico para texto y otro contenido en línea. | ✔️ |
| `Strong` | `<strong>` | Indica contenido de gran importancia, típicamente renderizado en negrita. | ✔️ |
| `UnorderedList` | `<ul>` | Representa una lista desordenada con viñetas de elementos. | ✔️ |

## Trabajando con hijos

Los componentes marcados con ✔️ en la columna **Hijos** admiten la adición, eliminación y acceso a componentes secundarios. Estos métodos se proporcionan a través de la clase [`Element`](../building-ui/element#component-interaction).

Para crear elementos HTML arbitrarios más allá de los aquí listados, o para incrustar componentes web personalizados, consulta la documentación de [`Element`](../building-ui/element).
