---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: 66716282278634ab574f3620a2a660ce
---
```markdown
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Jerarquía de rutas

Las rutas están organizadas en una estructura de árbol jerárquica que permite a los desarrolladores definir diseños, gestionar vistas y renderizar dinámicamente componentes en varias partes de la aplicación.

Los conceptos clave que encontrarás al construir una aplicación enrutable webforJ incluyen:

- **Jerarquía de rutas**: Organiza las rutas en estructuras de padre-hijo para el desarrollo modular de la interfaz de usuario.
- **Tipos de rutas**: Las rutas se clasifican como **Rutas de vista** o **Rutas de diseño**, cada una con un propósito diferente.
- **Rutas anidadas**: Las rutas pueden estar anidadas unas dentro de otras, permitiendo que los componentes padre rendericen componentes hijo en salidas designadas.
- **Salidas**: Componentes donde las vistas hijas se inyectan dinámicamente en los diseños de los padres.
- **Diseños**: Rutas especiales que envuelven componentes hijos sin contribuir a la URL, proporcionando elementos de interfaz de usuario compartidos como encabezados, pies de página o barras laterales.

## Temas {#topics}

<DocCardList className="topics-section" />
```
