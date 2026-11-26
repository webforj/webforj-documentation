---
title: Actualización a 27.00
description: Actualiza de 26.00 a 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_title_hash: f521441c4a5da00c1bc05ae7307c3644
_i18n_description_hash: 2ca050c7f4bc5f02fb7c8680550799a3
_i18n_hash: 2757e1b06263f629ef0c1e5c15785122
---
Esta documentación sirve como guía para actualizar las aplicaciones webforJ de la versión 26.00 a la 27.00.
A continuación se indican los cambios necesarios para que las aplicaciones existentes sigan funcionando sin problemas.
Como siempre, consulta el [resumen de la versión en GitHub](https://github.com/webforj/webforj/releases) para ver una lista más completa de los cambios entre versiones.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Contenido de texto y HTML {#text-and-html-content}

En versiones anteriores, un valor envuelto en `<html>` y pasado a `setText()` se trataba como HTML. Este comportamiento está obsoleto y se elimina en webforJ 27.00. La configuración `webforj.legacyHtmlInText` lo controla:

- `true` (valor predeterminado): el contenido de un valor envuelto en `<html>` se representa como HTML.
- `false`: el mismo valor se muestra literalmente.

El contenedor `<html>` nunca se muestra en ninguno de los dos casos.

La primera vez que un valor envuelto en `<html>` llega a `setText()`, se registra una advertencia que indica el componente y el lugar de la llamada, para que la llamada pueda cambiarse a `setHtml()`.

## Enlace obligatorio con `@Size` {#required-binding-using-size-annotation}

En versiones anteriores a la 27.00, el uso de la anotación de [Jakarta](/docs/data-binding/validation/jakarta-validation) `@Size` marcaba automáticamente el componente enlazado como obligatorio.
A partir de ahora, `@Size` solo marca un componente enlazado como obligatorio si tiene definido un valor `min`.

```java
// ya no marca el campo como obligatorio
@Size(max = 80, message = "La calle debe tener menos de 80 caracteres")
private String street;
```

```java
// marca el campo como obligatorio
@Size(min = 2, max = 80, message = "La calle debe tener entre 2 y 80 caracteres")
private String street;
```
