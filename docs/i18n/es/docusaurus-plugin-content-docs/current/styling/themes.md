---
sidebar_position: 2
title: Themes
_i18n_hash: afbc96c2eb0da1c5e0eb2e24a69827c2
---
webforJ incluye tres temas de aplicación integrados y admite la definición de tus propios temas personalizados. Los temas predeterminados son:

- **light**: Un tema brillante con un fondo claro (predeterminado).
- **dark**: Un fondo oscuro teñido con el color primario.
- **dark-pure**: Un tema oscuro completamente neutral basado en tonos de gris.

Para aplicar un tema en tu aplicación, utiliza la anotación `@AppTheme` o el método `App.setTheme()`. El nombre del tema debe ser uno de: `system`, `light`, `dark`, `dark-pure`, o un nombre de tema personalizado.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // código de la app
}

// o programáticamente
App.setTheme("dark-pure");
```

## Sobrescribiendo temas predeterminados {#overriding-default-themes}

Puedes sobrescribir el tema **light** redefiniendo propiedades personalizadas de CSS en el selector `:root`.

:::info `:root` pseudo-clase
La pseudo-clase CSS `:root` apunta al elemento raíz del documento. En HTML, representa el elemento `<html>` y tiene una especificidad mayor que el selector `html` simple.
:::

Ejemplo:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Para sobrescribir los temas **dark** o **dark-pure**, utiliza selectores de atributos en el elemento `<html>`:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 80%;
}
```

## Creando temas personalizados {#creating-custom-themes}

Puedes definir tus propios temas utilizando el selector `html[data-app-theme='THEME_NAME']`. Los temas personalizados pueden coexistir con los predeterminados, y puedes alternar entre ellos dinámicamente en tiempo de ejecución.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Para hacer que un tema personalizado sea oscuro, establece `--dwc-dark-mode: 1` y `color-scheme: dark`:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Luego, en tu aplicación:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // código de la app
}

// o programáticamente
App.setTheme("new-theme");
```

## Temas de componentes {#component-themes}

Además de los temas a nivel de aplicación, los componentes de webforJ admiten un conjunto de **temas de componentes** basados en las paletas de color predeterminadas: `default`, `primary`, `success`, `warning`, `danger`, `info`, y `gray`.

Cada componente documenta sus temas compatibles en la sección **Estilo → Temas**.
