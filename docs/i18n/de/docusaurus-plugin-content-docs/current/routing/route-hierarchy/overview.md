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

# Routenhierarchie

Routen sind in einer hierarchischen Baumstruktur organisiert, die es Entwicklern ermöglicht, Layouts zu definieren, Ansichten zu verwalten und Komponenten dynamisch in verschiedenen Teilen der App zu rendern.

Die wichtigsten Konzepte, auf die Sie beim Erstellen einer routierbaren App für webforJ stoßen werden, umfassen:

- **Routenhierarchie**: Organisiert Routen in Eltern-Kind-Strukturen für eine modulare UI-Entwicklung.
- **Routentypen**: Routen werden in **Ansichts-Routen** oder **Layout-Routen** kategorisiert, die jeweils einen anderen Zweck erfüllen.
- **Verschachtelte Routen**: Routen können innerhalb anderer Routen verschachtelt werden, sodass übergeordnete Komponenten untergeordnete Komponenten in vorgesehenen Ausgaben rendern können.
- **Ausgaben**: Komponenten, in die untergeordnete Ansichten dynamisch in übergeordnete Layouts injiziert werden.
- **Layouts**: Besondere Routen, die untergeordnete Komponenten umschließen, ohne zur URL beizutragen, und gemeinsame UI-Elemente wie Kopfzeilen, Fußzeilen oder Seitenleisten bereitstellen.

## Themen {#topics}

<DocCardList className="topics-section" />
