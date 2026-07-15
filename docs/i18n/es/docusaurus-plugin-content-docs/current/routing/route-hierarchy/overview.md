---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
description: >-
  Organize webforJ routes into parent-child trees with view routes, layout
  routes, outlets, and nested components.
_i18n_hash: 4bfc9c9d46d57c866c67a2baaf2e3c3a
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Jerarquía de rutas

Las rutas están organizadas en una estructura jerárquica de árbol que permite a los desarrolladores definir diseños, gestionar vistas y renderizar componentes de manera dinámica en varias partes de la aplicación.

Los conceptos clave que encontrarás al construir una aplicación webforJ enrutada incluyen:

- **Jerarquía de Rutas**: Organiza las rutas en estructuras de padre-hijo para un desarrollo de UI modular.
- **Tipos de Rutas**: Las rutas se clasifican como **Rutas de Vista** o **Rutas de Diseño**, cada una con un propósito diferente.
- **Rutas Anidadas**: Las rutas pueden estar anidadas entre sí, permitiendo que los componentes padres rendericen componentes hijos en salidas designadas.
- **Salidas**: Componentes donde las vistas hijas se inyectan dinámicamente en los diseños de los padres.
- **Diseños**: Rutas especiales que envuelven componentes hijos sin contribuir a la URL, proporcionando elementos de UI compartidos como encabezados, pies de página o barras laterales.

## Temas {#topics}

<DocCardList className="topics-section" />
