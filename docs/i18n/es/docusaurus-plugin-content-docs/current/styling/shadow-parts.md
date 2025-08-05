---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: 8dbd7759364573b73d0b1b00c6d7e219
---
CSS **Shadow Parts** ofrece a los desarrolladores una forma de estilizar elementos dentro del shadow DOM de un componente desde el exterior, mientras se preserva la encapsulación.

## Introducción {#introduction}

Los componentes de webforJ se construyen utilizando [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components), que dependen del [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) para encapsular la estructura interna y los estilos de un componente.

:::tip Web Components
Web Components son un conjunto de tecnologías que te permiten crear elementos personalizados reutilizables y encapsulados para su uso en aplicaciones web.
:::

El **Shadow DOM** previene que los estilos y marcados internos se filtren fuera del componente o sean afectados por estilos externos. Esta encapsulación asegura que los componentes se mantengan autónomos, reduciendo el riesgo de conflictos de estilos.

:::tip  Encapsulación de Web Components
La encapsulación es un beneficio clave de Web Components. Al mantener la estructura, los estilos y el comportamiento de un componente separados del resto de tu aplicación, evitas choques y mantienes un código limpio y mantenible.
:::

Sin embargo, debido a esta encapsulación, **no puedes estilizar directamente** elementos dentro de un shadow DOM utilizando selectores CSS estándar.

Por ejemplo, el componente `dwc-button` genera la siguiente estructura:

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Button</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Si intentas estilizar el `label` de esta manera:

```css
/* NO funciona */
dwc-button .control__label {
  color: pink;
}
```

no tendrá ningún efecto, porque el elemento `.control__label` vive dentro del shadow root.

Aquí es donde entran los **CSS Shadow Parts**.

## Estilizando con shadow parts {#styling-with-shadow-parts}

Los shadow parts permiten que hojas de estilo externas apunten a elementos específicos dentro de un árbol de sombra, pero **solo si** esos elementos están explícitamente marcados como "expuestos" por el componente.

### Cómo se exponen los parts {#how-parts-are-exposed}

Para exponer un elemento para el estilo externo, el autor del componente debe asignarle un atributo `part` dentro del shadow DOM.

Todos los componentes de webforJ exponen automáticamente partes relevantes para el estilo. Puedes encontrar la lista de partes soportadas en la sección **Estilización > Shadow parts** de la documentación de cada componente.

Por ejemplo, el componente `dwc-button` expone partes como `prefix`, `label` y `suffix`:

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Button</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Una vez expuestas, estas partes pueden ser estilizadas desde fuera del componente utilizando el pseudo-elemento [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part).

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
No puedes seleccionar *dentro* de una parte de shadow. Lo siguiente **no** funcionará:

```css
/* NO funciona */
dwc-button::part(label) span {
  /* CSS va aquí */
}
```
:::
