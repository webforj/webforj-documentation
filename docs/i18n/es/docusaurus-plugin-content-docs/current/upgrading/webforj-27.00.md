---
title: Upgrade to 27.00
description: Upgrade from 26.00 to 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_hash: 85416371e550d0aedae0a0771aff67be
---
Esta documentación sirve como guía para actualizar aplicaciones webforJ de 26.00 a 27.00.  
Aquí están los cambios necesarios para que las aplicaciones existentes sigan funcionando sin problemas.  
Como siempre, consulta el [resumen de lanzamientos de GitHub](https://github.com/webforj/webforj/releases) para obtener una lista más completa de cambios entre versiones.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Texto y contenido HTML {#text-and-html-content}

Las versiones anteriores trataban un valor envuelto en `<html>` y pasado a `setText()` como HTML. Este comportamiento está obsoleto y se ha eliminado en webforJ 27.00. La configuración `webforj.legacyHtmlInText` lo controla:

- `true` (predeterminado): un valor envuelto en `<html>` renderiza su contenido como HTML.
- `false`: el mismo valor se muestra literalmente.

El envoltorio `<html>` nunca se muestra en ningún caso.

La primera vez que un valor envuelto en `<html>` llega a `setText()`, se registra una advertencia que nombra el componente y el sitio de llamada, para que la llamada se pueda mover a `setHtml()`.
