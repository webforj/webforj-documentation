---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: a384d1b849730a301f5bc1d0a20e9c41
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Jerarquía de rutas

Las rutas están organizadas en una estructura de árbol jerárquica que permite a los desarrolladores definir diseños, gestionar vistas y renderizar componentes de manera dinámica en varias partes de la aplicación.

Los conceptos clave que encontrarás al construir una aplicación enrutada con webforJ incluyen:

- **Jerarquía de Rutas**: Organiza las rutas en estructuras de padre-hijo para el desarrollo modular de UI.
- **Tipos de Rutas**: Las rutas se clasifican como **Rutas de Vista** o **Rutas de Diseño**, cada una con un propósito diferente.
- **Rutas Anidadas**: Las rutas pueden anidarse entre sí, permitiendo que los componentes padre rendericen componentes hijo en salidas designadas.
- **Salidas**: Componentes donde las vistas hijo se inyectan dinámicamente en los diseños padre.
- **Diseños**: Rutas especiales que envuelven componentes hijo sin contribuir a la URL, proporcionando elementos de UI compartidos como encabezados, pies de página o barras laterales.

## Temas {#topics}

<DocCardList className="topics-section" />
