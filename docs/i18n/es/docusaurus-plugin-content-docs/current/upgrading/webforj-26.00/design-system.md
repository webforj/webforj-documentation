---
title: Upgrading to the v26 Design System
description: >-
  Reference for the design-system updates in DWC 26 - color engine, dark mode,
  surfaces, shadows, typography, radius, focus ring, and interaction feedback.
sidebar_position: 2
_i18n_hash: 8a36bc047ecfc90874412da4d39643fb
---
DWC 26 introduce un sistema de diseño renovado. La actualización es incremental en lugar de una reescritura completa: la mayoría de las variables CSS de la v25 siguen disponibles, la API pública del motor de temas se mantiene, y las personalizaciones existentes continúan funcionando sin cambios.

Esta guía documenta lo que cambió, dónde difiere el resultado visual y los pasos de actualización requeridos cuando una aplicación depende de un comportamiento específico de la v25.

## Veredicto rápido {#quick-verdict}

| Escenario | Qué esperar |
| --- | --- |
| Usa el estilo predeterminado | Renovación visual. Los tonos de la paleta predeterminada fueron ajustados (por ejemplo, el primario pasó de `h: 211 / s: 100%` a `h: 223 / s: 91%`), las sombras se ven más estratificadas y los componentes se sienten más redondeados. No se necesita cambio de código, pero los colores predeterminados de la marca cambian. |
| Sobrescribe `--dwc-color-{nombre}-h` y `-s` | Sigue funcionando. La ruta de la semilla HSL se mantiene. |
| Sobrescribe pasos individuales de la paleta (por ejemplo, `--dwc-color-primary-40`) | Los números pueden resolverse en diferentes colores. Consulta [Paleta de colores](#color-palette-step-5-is-always-darkest). |
| Depende de `--dwc-color-{nombre}-c` | Eliminar. El cambio de texto claro/oscuras ahora se calcula automáticamente por tono. |
| Hace referencia a tokens de tamaño de fuente nombrados (`--dwc-font-size-m`, `-l`, etc.) | La escala se desplazó hacia abajo un nivel. `m` ahora es 14px en lugar de 16px. Consulta [Tipografía](#typography). |
| Usa `--dwc-font-weight-semibold` para obtener un peso de 500 | `semibold` ahora es 600. Cambia al nuevo `--dwc-font-weight-medium` para 500. |
| Reserva espacio alrededor de elementos enfocados con `--dwc-focus-ring-width` | El anillo ahora tiene un espacio. Añade `--dwc-focus-ring-gap` a ese relleno, o el anillo se desbordará. Consulta [Anillo de enfoque](#focus-ring). |
| Personalizó efectos de hover/ripple de botón | Las ondas han desaparecido. La retroalimentación al presionar ahora es una pequeña reducción de escala. |

Si ninguno de esos aplica, puedes dejar de leer aquí. Tu actualización ha terminado.

## Novedades a simple vista {#whats-new-at-a-glance}

- **Motor de color moderno.** Las paletas se generan en OKLCH en lugar de HSL. Los pasos de claridad son perceptivamente uniformes (por lo que los pasos adyacentes se ven como pasos adyacentes), y el modo oscuro ya no invierte la paleta.
- **Modo oscuro a través de una variable.** `--dwc-dark-mode: 1` invierte toda la interfaz. La adaptación del modo ocurre en la capa de variación, en lugar de remapear cada paso.
- **Colores de texto `on-text` automáticos.** Cada paso de paleta recibe un compañero `--dwc-color-on-{nombre}-text-{paso}` ajustado para el contraste WCAG AA en ese tono. No tienes que calcular el contraste manualmente.
- **Anulación de semilla directa.** Pasa cualquier color CSS (hex, `rgb()`, `oklch()`, `lab()`, etc.) a `--dwc-color-{nombre}-seed` y toda la paleta se regenera a partir de eso.
- **Sombras reajustadas.** Los mismos seis niveles (`xs` a `2xl`), ahora con caída de capa realista y un refuerzo de fuerza en modo oscuro automático a través de `--dwc-shadow-strength`.
- **Superficies y `default` utilizan su propia curva de claridad.** Ambos ahora se adaptan a claro/oscuro a través de `--dwc-dark-mode` y un ligero tinte primario, en lugar de redefinir superficies en el tema oscuro y asignar `default` a pasos de paleta.
- **Retroalimentación de presión en escala.** Las ondas son reemplazadas por una pequeña reducción de escala al presionar. Tokens: `--dwc-scale-press`, `--dwc-scale-press-deep`.
- **Anillo de enfoque con espacio.** El anillo ahora tiene un pequeño espacio del color de la superficie (`--dwc-focus-ring-gap`) antes de la sombra de color, así que permanece visible en botones sólidos y diseños estrechos.
- **El radio de borde es sembrado.** Cambia `--dwc-border-radius-seed` y los pasos de `s` a `4xl` se reajustan proporcionalmente (`2xs` y `xs` se mantienen en valores de píxeles fijos). Nuevos pasos `3xl` y `4xl`.

## El sistema de color {#the-color-system}

Este es el cambio más significativo bajo el capó. El comportamiento que ves debería ser familiar, los internos son diferentes.

### Dos formas de definir un color {#two-ways-to-define-a-color}

Puedes seguir usando el tono + saturación como antes, o sobrescribir la semilla directamente con cualquier color CSS.

```css
/* Tono + saturación (sigue siendo la ruta predeterminada) */
:root {
  --dwc-color-primary-h: 223;
  --dwc-color-primary-s: 91%;
}

/* Semilla directa - cualquier formato de color CSS */
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

Si ya estabas usando `-h` y `-s`, no necesitas hacer nada. La anulación de semilla es el nuevo camino para colores de marca directos.

### Paleta de colores: el paso 5 es siempre el más oscuro {#color-palette-step-5-is-always-darkest}

En la v25, la paleta se invertía en modo oscuro (el paso 5 más oscuro en claro, el más claro en oscuro). En la v26, el paso 5 es siempre el tono más oscuro y el paso 95 es siempre el más claro, independientemente del modo. La adaptación del modo ahora ocurre un nivel más arriba, en los tokens de variación:

```css
/* v26 - las variaciones apuntan a pasos fijos */
--dwc-color-primary-dark:  var(--dwc-color-primary-45);
--dwc-color-primary:       var(--dwc-color-primary-50);
--dwc-color-primary-light: var(--dwc-color-primary-55);
```

| Escenario | Qué cambia |
| --- | --- |
| Usa `--dwc-color-primary` (o `-dark`, `-light`, `-text`) | Nada. Las variaciones todavía se comportan igual en los modos. |
| Codificaste un paso como `--dwc-color-primary-40` | Ese paso ahora resuelve la misma claridad OKLCH en ambos modos. El color que viste en modo oscuro provenía de un paso diferente. Cambia a la token de variación si deseas un comportamiento consciente del modo. |
| Escribiste `hsl(var(--dwc-color-primary-h), ...)` directamente | Sigue funcionando. La semilla HSL todavía se construye a partir de h + s. |

### Los colores son derivados, no prometidos {#colors-are-derived-not-promised}

:::info Atención
El tono que estableces es una **semilla**, no un objetivo. El color que pasas a través de `--dwc-color-{nombre}-h` / `-s` (o `-seed`) no necesariamente aparecerá en el paso 50.
:::

Debido a que la paleta usa claridad OKLCH absoluta por paso, dónde aterriza tu semilla depende de su claridad natural. Los tonos brillantes (cian, amarillo) tienen alta claridad OKLCH y terminan cerca del paso 80-85. Los tonos más oscuros (azul) se sitúan cerca del paso 50 por coincidencia.

Si necesitas un color exacto en un paso exacto, establece el paso explícitamente:

```css
:root {
  --dwc-color-primary-50: #1d4ed8;
}
```

### `--dwc-color-{nombre}-c` ha desaparecido {#dwc-color-name-c-is-gone}

En la v25, `-c` era el umbral de contraste: la claridad de fondo en la que el color de texto compañero cambiaba de blanco a negro. Un valor de `50` significaba que el texto era blanco en fondos más oscuros que el 50% y negro en fondos más claros que el 50%.

En la v26 no seleccionas un punto de inversión. Cada paso recibe un color de texto `on-text` teñido computado automáticamente a partir de la sombra misma. El resultado es siempre seguro para WCAG AA y mantiene un guiño al tono de la paleta en lugar de caer en negro o blanco puro.

Si tienes alguna anulación `--dwc-color-{nombre}-c`, puedes eliminarla, no tienen efecto.

### Textos y colores `on-text` {#text-and-on-text-colors}

La v25 tenía un token de texto por paso, `--dwc-color-{nombre}-text-{paso}`, que era un color puro negro o blanco calculado a partir del umbral `-c` y destinado para texto **en** ese paso como fondo.

La v26 mantiene el mismo nombre de token pero cambia su significado, y añade un segundo token por paso:

| Token | Significado v25 | Significado v26 |
| --- | --- | --- |
| `--dwc-color-{nombre}-text-{paso}` | Puro negro/blanco, destinado para texto en la sombra como fondo | Texto teñido, **seguro para superficie**, legible en fondos neutrales de página |
| `--dwc-color-on-{nombre}-text-{paso}` | (no existía como token por paso) | Texto teñido para uso **en** ese paso como fondo |

Ambos tokens v26 están limitados para el contraste WCAG AA en su fondo destinado. Si usaste `--dwc-color-{nombre}-text-{paso}` como el primer plano en un fondo coloreado, cambia a `--dwc-color-on-{nombre}-text-{paso}` (el nuevo token `on-text`) para preservar esa semántica.

### Tinturas y bordes {#tints-and-borders}

El generador ahora emite tres tokens por paleta, uno verdaderamente nuevo, una nueva variante, y uno cuyo origen se movió:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{nombre}-tint` | (no existía) | Semilla con 12% de opacidad, para fondos alternativos |
| `--dwc-border-color-{nombre}-emphasis` | (no existía) | Borde más fuerte, consciente del modo, para hover/enfoque/activo |
| `--dwc-border-color-{nombre}` | Establecido por variación como `var(--dwc-color-{nombre})` (la sombra saturada) | Computado en el generador: tono aclarado consciente del modo de la semilla |

Si tu CSS leía `--dwc-border-color-primary` esperando el color primario saturado, el visual ahora es un sutil tono separador. Si quieres específicamente la apariencia saturada, cambia directamente a `--dwc-color-primary`.

## Modo oscuro {#dark-mode}

El modo oscuro se controla mediante una sola variable, `--dwc-dark-mode`. Establece en `1` para oscuro, `0` para claro:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Participa en expresiones `calc()` en todo el sistema, que es cómo la adaptación del modo se propaga a superficies, sombras, bordes y colores de texto.

En la v25, los temas incorporados `dark` y `dark-pure` tenían que redefinir superficies, sombras y muchas variaciones de paleta manualmente. En la v26, todo eso se deriva de `--dwc-dark-mode` y los colores semilla. Un tema oscuro típico que solía ser un bloque de anulación de 20 líneas se convierte en:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  color-scheme: dark;
}
```

Si tienes un tema oscuro personalizado copiado de la estructura de la v25, generalmente puedes eliminar la mayor parte del bloque interno y mantener solo la semilla y la bandera de modo oscuro.

## Superficies y `default` {#surfaces-and-default}

En la v25, las superficies se definían dos veces, una en `:root` para el modo claro (`hsl(default-h, default-s, 96%)`, etc.) y nuevamente en el tema oscuro (`hsl(default-h, default-s, 8%)`, etc.). La variación `default` apuntaba a pasos de paleta y, de manera similar, necesitaba sobreescrituras para el tema oscuro.

En la v26, ambos se computan una vez con un término `--dwc-dark-mode` incorporado en el cálculo de claridad, lo que garantiza:

- Las superficies siempre están ligeramente por debajo de `default`, para que las tarjetas visualmente floten por encima de la página.
- Se aplica un pequeño tinte primario a través de la cromaticidad de la semilla en ambos modos.
- El tema `dark-pure` establece `--dwc-color-default-s: 0%`, lo que reduce automáticamente el tinte a cero.

Si tu aplicación sobrescribe `--dwc-surface-1` (o cualquier otra superficie) con un color fijo, sigue funcionando; simplemente optas por no participar en la adaptación automática del modo.

La fuente del token `--dwc-color-{nombre}-alt` también cambió:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{nombre}-alt` | Paso de paleta 95 (fondo cercano al blanco) | Semilla con 12% de opacidad (tinte translúcido) |

Si usaste `-alt` como fondo sólido cercano al blanco, ahora se leerá como una superposición translúcida teñida. Elige un paso específico (`--dwc-color-{nombre}-95`) o diseña alrededor de la semántica translúcida.

## Sombras {#shadows}

La escala de seis niveles (`xs`, `s`, `m`, `l`, `xl`, `2xl`) permanece sin cambios en nombre y recuento, pero los desplazamientos de capa fueron reconstruidos para una caída realista y las sombras en modo oscuro ahora son automáticamente 5 veces más fuertes a través de `--dwc-shadow-strength` porque las superficies oscuras necesitan más contraste para transmitir profundidad.

Si solo usas `var(--dwc-shadow)` obtienes la sombra media reajustada y nada más cambia. La variable `--dwc-shadow-color` todavía se emite, pero su formato de valor cambió:

| | v25 | v26 |
| --- | --- | --- |
| `--dwc-shadow-color` | Tripleta HSL (`h, s%, l%`) | Color completo OKLCH |

Si tu CSS utiliza la forma de tripleta heredada como `hsla(var(--dwc-shadow-color), 0.07)`, cambia a un token de sombra completo (`var(--dwc-shadow-m)`) o reescribe con `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

## Tipografía {#typography}

Los tokens de tamaño de los componentes (`--dwc-size-*`) permanecen sin cambios. La escala de fuentes fue reajustada para anclar el nivel `m` al mismo tamaño de texto ligero utilizado por otros tokens de DWC, por lo que los nombres de los cubos se desplazaron hacia abajo un nivel:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-size-3xs` | 10px | 10px |
| `--dwc-font-size-2xs` | 12px | 11px |
| `--dwc-font-size-xs` | 13px | 12px |
| `--dwc-font-size-s` | 14px | 13px |
| `--dwc-font-size-m` | 16px | 14px |
| `--dwc-font-size-l` | 18px | 16px |
| `--dwc-font-size-xl` | 22px | 20px |
| `--dwc-font-size-2xl` | 28px | 26px |
| `--dwc-font-size-3xl` | 36px | 34px |

El `--dwc-font-size` predeterminado sigue resolviendo a **14px**, simplemente llega allí a través de `--dwc-font-size-m` (v26) en lugar de `--dwc-font-size-s` (v25).

Si tu CSS hace referencia a tokens de tamaño de fuente por nombre (por ejemplo, `font-size: var(--dwc-font-size-l)`), el resultado visible será más pequeño en la v26. Aumenta un nivel para preservar el tamaño de la v25.

Los pesos de fuente ganaron tres tokens (`thin`, `medium`, `black`) y un token existente se desplazó:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-weight-semibold` | 500 | 600 |
| `--dwc-font-weight-medium` | (no existía) | 500 |

Si utilizaste `--dwc-font-weight-semibold` para obtener texto de peso 500, cambia al nuevo `--dwc-font-weight-medium`.

Los cubos de altura de línea se desplazaron en la misma dirección que los tamaños de fuente; el `--dwc-font-line-height` predeterminado sigue resolviendo a 1.25.

`--dwc-font-family-sans` y `--dwc-font-family-mono` se modernizaron para usar pilas de `system-ui` y `ui-monospace`. Si apuntaste a una fuente nombrada específica de la antigua pila (`Dank Mono`, `Operator Mono`, `Roboto`, etc.) y la deseas de vuelta, establece `--dwc-font-family` a una pila que controles.

## Espaciado {#spacing}

La escala de espacio no ha cambiado desde `xs` en adelante. Solo los dos tokens más pequeños fueron redondeados a valores de píxeles enteros:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-space-3xs` | 1.2px | 1px |
| `--dwc-space-2xs` | 2.4px | 2px |

No se necesita ninguna acción en casi ninguna aplicación.

## Radio de borde {#border-radius}

El radio de borde ahora es sembrado. Cambia `--dwc-border-radius-seed` y cada paso (`s`, `m`, `l`, `xl`, `2xl`, `3xl`, `4xl`) se reajusta proporcionalmente. Los pasos `2xs` y `xs` siguen fijados a valores de píxeles fijos (demasiado pequeños para derivar significativamente).

Tres cosas cambiaron:

| | v25 | v26 |
| --- | --- | --- |
| Unidad | `em` (escala con el tamaño de fuente del padre) | `rem` (escala con el tamaño de fuente raíz) |
| `--dwc-border-radius` predeterminado | `--dwc-border-radius-s` (4px) | `--dwc-border-radius-seed` (8px) |
| Pasos disponibles | hasta `2xl` | añade `3xl`, `4xl` |

Los componentes se sienten más redondeados de serie. Si un componente anidado dentro de un texto más grande solía heredar un radio más grande a través de `em`, ese escalado ya no ocurre, los radios ahora están anclados a la raíz. Si deseas el tamaño predeterminado de la v25 de vuelta, reduce la semilla a la mitad:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, reduce a la mitad toda la escala */
}
```

## Easing {#easings}

El catálogo de easing es mayormente el mismo, con nuevos tokens de atajo para los casos comunes: `--dwc-ease`, `--dwc-ease-in`, `--dwc-ease-out`, `--dwc-ease-outGlide`. Consulta la página de [Transiciones y Easing](/docs/styling/transitions-easing) para el listado completo.

## Transiciones {#transitions}

Las duraciones de transición fueron reequilibradas para una sensación más ágil:

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-transition-slow` | 500&nbsp;ms | 300&nbsp;ms |
| `--dwc-transition-medium` | 250&nbsp;ms | 250&nbsp;ms |
| `--dwc-transition-fast` | 150&nbsp;ms | 150&nbsp;ms |
| `--dwc-transition-x-fast` | 50&nbsp;ms | 100&nbsp;ms |

Si dependes de una duración específica, anúlala en `:root`.

## Anillo de enfoque {#focus-ring}

El anillo de enfoque ahora utiliza un patrón de doble anillo: un pequeño espacio del color de la superficie, luego el anillo de color. Esto mantiene el anillo legible sobre botones sólidos y diseños densos.

| Variable | v25 | v26 |
| --- | --- | --- |
| `--dwc-focus-ring-width` | 3px | 2px |
| `--dwc-focus-ring-a` | 0.4 | 0.75 |
| `--dwc-focus-ring-gap` | (ninguno) | 2px |
| `--dwc-focus-ring-l` | 45% | (eliminado, la claridad se calcula por modo) |

Si reservas espacio alrededor de elementos enfocados con `padding: var(--dwc-focus-ring-width)`, añade el espacio a ese relleno para que el nuevo anillo tenga espacio para renderizar:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

## Retroalimentación de interacción {#interaction-feedback}

Los efectos de ondas estilo material ya no son utilizados por ningún componente DWC. La nueva retroalimentación para cualquier elemento clickeable es una pequeña reducción de escala:

```css
--dwc-scale-press: 0.97;      /* Reducción estándar del 3% */
--dwc-scale-press-deep: 0.93; /* Reducción más profunda del 7% para botones */
```

El mixin SCSS `ripple` y la variable CSS `--dwc-ripple-color` todavía existen en la compilación, pero nada las importa por defecto. Si tus propios componentes optaron por el mixin, cambia a los tokens de escala de presión para coincidir con la nueva sensación.

## Soporte de navegador {#browser-support}

El nuevo sistema utiliza dos características CSS cuyas tablas de compatibilidad de navegador puedes ver en MDN:

- [Espacio de color OKLCH](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/oklch#browser_compatibility), incluye sintaxis de color relativa (`oklch(from ...)`)
- [`color-mix()`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/color-mix#browser_compatibility)

Ambos se han lanzado en Chrome, Edge, Firefox y Safari en su versión más reciente.

## Una lista de verificación de actualización pragmática {#a-pragmatic-upgrade-checklist}

1. Busca `--dwc-color-*-c` y elimina esas declaraciones.
2. Busca `hsla(var(--dwc-shadow-color)` y reemplázalo con un token de sombra (`var(--dwc-shadow-m)`) o reescribe como `oklch(from ...)`.
3. Busca referencias directas de pasos de paleta (`--dwc-color-{nombre}-{número}`). Si alguna alimenta estilos específicos del modo oscuro, cambia a tokens de variación (`--dwc-color-{nombre}`, `-dark`, `-light`).
4. Busca referencias de tamaño de fuente nombradas (`--dwc-font-size-m`, `-l`, etc.). Si deseas el tamaño de la v25, sube un nivel.
5. Busca `--dwc-font-weight-semibold`. Si deseabas 500, cambia a `--dwc-font-weight-medium`.
6. Si reservas espacio alrededor de elementos enfocados con `--dwc-focus-ring-width`, añade `--dwc-focus-ring-gap` al relleno.
7. Abre la aplicación, haz clic por ahí. La mayoría de las aplicaciones no necesitan nada más.
