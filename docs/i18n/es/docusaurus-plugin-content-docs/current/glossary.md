---
sidebar_position: 1000
title: Glossary
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definitions for terms used across webforJ documentation, including DOM, Shadow
  DOM, and other web platform and framework concepts.
_i18n_hash: 5567c28b0575afa1ce7a9fcafcbe429c
---
## DOM {#dom}

El DOM (Modelo de Objetos del Documento) es una interfaz de programación para documentos web. Representa la estructura de la página como un árbol de objetos, donde cada nodo corresponde a un elemento HTML. JavaScript y los frameworks web utilizan el DOM para acceder y manipular dinámicamente el contenido, la estructura y el estilo de las páginas web.

## Shadow DOM {#shadow-dom}

Shadow DOM es un estándar web que permite la encapsulación del DOM (Modelo de Objetos del Documento) y CSS dentro de un elemento específico, conocido como el árbol de sombra. Este DOM y CSS aislados son separados del DOM del documento principal, creando efectivamente un límite de alcance para el componente. Shadow DOM ayuda a crear componentes web reutilizables y autónomos que se pueden agregar a una página web sin preocuparse por conflictos con otros estilos y scripts en la página.

También introduce características como partes de sombra y slots que permiten a los desarrolladores exponer ciertas partes del árbol de sombra para su personalización por la página principal. Esto proporciona una forma flexible de pasar contenido al componente y personalizar su apariencia mientras se mantiene la encapsulación.
