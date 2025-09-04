---
sidebar_position: 2
title: Themes
_i18n_hash: afb80b03bfe243ffa93d6f72a05809e2
---
webforJ incluye tres temas de aplicación integrados y admite la definición de sus propios temas personalizados. Los temas predeterminados son:

- **light**: Un tema brillante con un fondo claro (predeterminado).
- **dark**: Un fondo oscuro con un tono del color primario.
- **dark-pure**: Un tema oscuro completamente neutral basado en tonos grises.

Para aplicar un tema en su aplicación, use la anotación `@AppTheme` o el método `App.setTheme()`. El nombre del tema debe ser uno de: `system`, `light`, `dark`, `dark-pure` o un nombre de tema personalizado.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // código de la aplicación
}

// o programáticamente
App.setTheme("dark-pure");
```

## Sobrescribiendo temas predeterminados {#overriding-default-themes}

Puede sobrescribir el tema **light** redefiniendo propiedades CSS personalizadas en el selector `:root`.

:::info `:root` pseudo-clase
La pseudo-clase CSS `:root` apunta al elemento raíz del documento. En HTML, representa el elemento `<html>` y tiene una especificidad mayor que el selector `html` simple.
:::

Ejemplo:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 50;
  --dwc-font-size: var(--dwc-font-size-m);
}
```

Para sobrescribir los temas **dark** o **dark-pure**, use selectores de atributos en el elemento `<html>`:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Creando temas personalizados {#creating-custom-themes}

Puede definir sus propios temas utilizando el selector `html[data-app-theme='THEME_NAME']`. Los temas personalizados pueden coexistir con los predeterminados, y puede alternar entre ellos dinámicamente en tiempo de ejecución.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

Luego, en su aplicación:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // código de la aplicación
}

// o programáticamente
App.setTheme("new-theme");
```

## Temas de componentes {#component-themes}

Además de los temas a nivel de aplicación, los componentes de webforJ admiten un conjunto de **temas de componentes** basados en las paletas de colores predeterminadas: `default`, `primary`, `success`, `warning`, `danger`, `info` y `gray`.

Cada componente documenta sus temas admitidos en la sección **Estilo → Temas**.
