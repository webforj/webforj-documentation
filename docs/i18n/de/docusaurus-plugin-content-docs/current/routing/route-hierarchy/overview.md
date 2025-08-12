---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: 66716282278634ab574f3620a2a660ce
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Routenhierarchie

Routen sind in einer hierarchischen Baumstruktur organisiert, die es Entwicklern ermöglicht, Layouts zu definieren, Ansichten zu verwalten und Komponenten dynamisch in verschiedenen Teilen der App zu rendern.

Die wichtigsten Konzepte, auf die Sie beim Erstellen einer routbaren App mit webforJ stoßen werden, sind:

- **Routenhierarchie**: Organisiert Routen in Eltern-Kind-Strukturen für die modulare UI-Entwicklung.
- **Routentypen**: Routen werden in **Ansichts-Routen** oder **Layout-Routen** kategorisiert, wobei jede einen anderen Zweck erfüllt.
- **Verschachtelte Routen**: Routen können innerhalb anderer Routen verschachtelt werden, sodass Elternkomponenten Kindkomponenten in festgelegten Ausgaben rendern können.
- **Ausgänge**: Komponenten, in die Kindansichten dynamisch in Eltern-Layouts injiziert werden.
- **Layouts**: Besondere Routen, die Kindkomponenten umschließen, ohne zur URL beizutragen, und gemeinsame UI-Elemente wie Header, Fußzeilen oder Seitenleisten bereitstellen.

## Themen {#topics}

<DocCardList className="topics-section" />
