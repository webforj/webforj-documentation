---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: bad90a86a29eaf34485d5ee9150aacb3
---
CSS **Partes de Sombra** le dan a los desarrolladores una forma de estilizar elementos dentro del shadow DOM de un componente desde el exterior, mientras se preserva la encapsulación.

## Introducción {#introduction}

Los componentes de webforJ están construidos utilizando [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components), que dependen del [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) para encapsular la estructura interna y los estilos de un componente.

:::tip Web Components
Los Web Components son un conjunto de tecnologías que te permiten crear elementos personalizados reutilizables y encapsulados para su uso en aplicaciones web.
:::

El **Shadow DOM** evita que los estilos y el marcado internos se filtren fuera del componente o sean afectados por estilos externos. Esta encapsulación asegura que los componentes permanezcan autocontenidos, reduciendo el riesgo de conflictos de estilo.

:::tip  Encapsulación de Web Components
La encapsulación es un beneficio clave de los Web Components. Al mantener la estructura, los estilos y el comportamiento de un componente separados del resto de tu aplicación, evitas choques y mantienes un código limpio y fácil de mantener.
:::

Sin embargo, debido a esta encapsulación, **no puedes estilizar directamente** elementos dentro de un shadow DOM utilizando selectores CSS estándar.

Por ejemplo, el componente `dwc-button` renderiza la siguiente estructura:

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Button</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Si intentas estilizar la `label` así:

```css
/* No funciona */
dwc-button .control__label {
  color: pink;
}
```

no tendrá efecto, porque el elemento `.control__label` vive dentro del shadow root.

Aquí es donde entran las **Partes de Sombra de CSS**.

## Estilizando con partes de sombra {#styling-with-shadow-parts}

Las partes de sombra permiten que hojas de estilo externas apunten a elementos específicos dentro de un árbol de sombra, pero **solo si** esos elementos están explícitamente marcados como “expuestos” por el componente.

### Cómo se exponen las partes {#how-parts-are-exposed}

Para exponer un elemento para estilización externa, el autor del componente debe asignarle un atributo `part` dentro del shadow DOM.

Todos los componentes de webforJ exponen automáticamente las partes relevantes para el estilo. Puedes encontrar la lista de partes soportadas en la sección **Styling > Shadow parts** de la documentación de cada componente.

Por ejemplo, el componente `dwc-button` expone partes como `prefix`, `label` y `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Una vez expuestas, estas partes se pueden estilizar desde fuera del componente utilizando el pseudo-elemento [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part).


### El pseudo-elemento `::part()` {#the-part-pseudo-element}

El selector `::part()` te permite aplicar estilos a elementos dentro del shadow DOM que han sido marcados con un atributo `part`.

Por ejemplo, para cambiar el color de la parte `label` en un `dwc-button`:

```css
dwc-button::part(label) {
  color: red;
}
```

Puedes combinar `::part()` con otros selectores, como `:hover`:

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning Limitaciones del Selector ::part()
No puedes seleccionar *dentro* de una parte de sombra. Lo siguiente **no** funcionará:

```css
/* No funciona */
dwc-button::part(label) span {
  /* CSS aquí */
}
```
:::
