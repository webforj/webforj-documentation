---
sidebar_position: 1000
title: Glosario
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definiciones de términos utilizados en toda la documentación de webforJ,
  incluidos DOM, Shadow DOM y otros conceptos de la plataforma web y los
  frameworks.
_i18n_title_hash: 31d109d162629f8d0cfc8ce8316c0567
_i18n_description_hash: a6875fc0c7c07fa420f5f6c4dd606cf9
_i18n_hash: b8c6955dd483c1d5904668a163666015
---
## DOM {#dom}

El DOM (Document Object Model) es una interfaz de programación para documentos web. Representa la estructura de la página como un árbol de objetos, donde cada nodo corresponde a un elemento HTML. JavaScript y los frameworks web usan el DOM para acceder y manipular dinámicamente el contenido, la estructura y los estilos de las páginas web.

## DOM de sombra {#shadow-dom}

El DOM de sombra es un estándar web que permite encapsular el DOM (Document Object Model) y el CSS dentro de un elemento específico, conocido como árbol de sombra. Este DOM y CSS aislados están separados del DOM del documento principal, lo que crea un límite de ámbito para el componente. El DOM de sombra ayuda a crear componentes web autocontenidos y reutilizables que se pueden agregar a una página web sin preocuparse por conflictos con otros estilos y scripts de la página.

También introduce funciones como las partes de sombra y las ranuras, que permiten a los desarrolladores exponer determinadas partes del árbol de sombra para que la página principal las personalice. Esto ofrece una forma flexible de pasar contenido al componente y personalizar su apariencia, a la vez que se mantiene el encapsulamiento.

## Componentes con ámbito {#scoped-components}

Componentes cuyos estilos están aislados para evitar conflictos con los estilos externos al componente. Este aislamiento se logra aplicando atributos de datos únicos a los estilos del componente, en lugar de depender del DOM de sombra nativo.
