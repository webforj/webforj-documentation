---
sidebar_position: 3
title: Colors
_i18n_hash: cc233e97e4b7333262eb47b14bfe572a
---
webforJ proporciona un sistema de color basado en propiedades personalizadas de CSS. Estas variables de color mantienen un estilo visual coherente a lo largo de tu aplicación mientras te dan control total para personalizar paletas según las necesidades de tu diseño.

Puedes hacer referencia a cualquier color usando la sintaxis `--dwc-color-{palette}-{step}`, donde `{palette}` es el nombre del grupo de colores (por ejemplo, `primary`, `danger`, ...) y `{step}` es un número del `5` al `95` en incrementos de `5`, representando la claridad del color.

```css
.element {
  background-color: var(--dwc-color-primary-50);
  color: var(--dwc-color-on-primary-text-50);
}
```

:::tip Escala de Pasos de Sombra
Los valores de paso varían de `5` (más oscuro) a `95` (más claro), aumentando en pasos de `5`. El paso 5 es siempre el más oscuro y el paso 95 es siempre el más claro, independientemente del modo claro u oscuro.
:::

## Paletas de colores {#color-palettes}

DWC configura siete paletas más la paleta `black/white`, donde cada paleta es un conjunto de variaciones (sombras y matices) de un color semántico.

### Paletas disponibles {#available-palettes}

- **default**: Una paleta neutral teñida con el matiz primario, utilizada para la mayoría de los fondos de componentes, bordes y elementos de interfaz de usuario neutros.
- **primary**: Representa típicamente el color primario de tu marca.
- **success**, **warning**, **danger**: Paletas semánticas utilizadas para indicadores de estado apropiados.
- **info**: Una paleta complementaria para énfasis secundario.
- **gray**: Una paleta de escala de grises pura, sin teñir.
- **black/white**: Colores dinámicos conscientes del modo que se adaptan al tema actual. El casi negro en modo claro se convierte en casi blanco en modo oscuro, y viceversa.

<dwc-doc-palettes></dwc-doc-palettes>

### Semillas de paleta {#palette-seeds}

Cada paleta se genera a partir de dos variables de semilla: `hue` y `saturation`. Establecer estos dos valores genera automáticamente los 19 pasos.

| Variable de semilla | Descripción |
|---|---|
| `--dwc-color-{name}-h` | El ángulo de tono del color semilla (0-360). |
| `--dwc-color-{name}-s` | El porcentaje de saturación. `100%` es completamente saturado, `0%` es completamente desaturado (gris). |

Puedes ajustar una paleta redefiniendo estas variables en tus estilos raíz. Por ejemplo, para modificar la paleta primaria:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
}
```

<Tabs>

<TabItem value="Primary">

| Variable | Valor predeterminado |
|---|---|
| `--dwc-color-primary-h` | 223 |
| `--dwc-color-primary-s` | 91% |

</TabItem>

<TabItem value="Success">

| Variable | Valor predeterminado |
|---|---|
| `--dwc-color-success-h` | 153 |
| `--dwc-color-success-s` | 60% |

</TabItem>

<TabItem value="Warning">

| Variable | Valor predeterminado |
|---|---|
| `--dwc-color-warning-h` | 35 |
| `--dwc-color-warning-s` | 90% |

</TabItem>

<TabItem value="Danger">

| Variable | Valor predeterminado |
|---|---|
| `--dwc-color-danger-h` | 4 |
| `--dwc-color-danger-s` | 90% |

</TabItem>

<TabItem value="Info">

| Variable | Valor predeterminado |
|---|---|
| `--dwc-color-info-h` | 262 |
| `--dwc-color-info-s` | 65% |

</TabItem>

<TabItem value="Gray">

| Variable | Valor predeterminado |
|---|---|
| `--dwc-color-gray-h` | 0 |
| `--dwc-color-gray-s` | 0% |

</TabItem>

<TabItem value="Default / Tone">

| Variable | Valor predeterminado |
|---|---|
| `--dwc-color-default-h` | var(--dwc-color-primary-h) |
| `--dwc-color-default-s` | 3% |

</TabItem>

</Tabs>

### Anulación directa de semilla {#direct-seed-override}

Cada paleta también expone una variable `--dwc-color-{name}-seed`. Por defecto, esta se construye a partir de los valores de tono y saturación, pero puedes anularla directamente con cualquier color CSS válido para eludir completamente el sistema de tono/saturación.

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

### Rotación de tono {#hue-rotation}

El generador de paletas aplica una sutil rotación de tono a través de los pasos para crear paletas de apariencia más natural. Las sombras más oscuras se desplazan ligeramente hacia el calor mientras que las sombras más claras se desplazan ligeramente hacia el frío. Esto imita cómo se comportan realmente los pigmentos y evita que las paletas se vean planas o sintéticas.

| Variable | Valor predeterminado | Descripción |
|---|---|---|
| `--dwc-color-hue-rotate` | 3 | Grados de desplazamiento de tono a través de la paleta. Establecer en 0 para desactivarlo. |

<dwc-doc-hue-rotate name="primary"></dwc-doc-hue-rotate>

### Variables generadas por paso {#generated-variables-per-step}

Cada paleta genera 19 pasos (del 5 al 95). Para cada paso, se producen las siguientes variables:

| Patrón de Variable | Descripción |
|---|---|
| `--dwc-color-{name}-{step}` | La sombra de la paleta en ese paso. |
| `--dwc-color-{name}-text-{step}` | Un color de texto seguro en la superficie derivado de ese paso (cumple con WCAG AA). |
| `--dwc-color-on-{name}-text-{step}` | Color de texto para usar SOBRE la sombra como fondo (cambia automáticamente entre claro/oscuridad). |

:::tip Accesibilidad
Todos los colores de texto generados cumplen automáticamente con los requisitos de contraste WCAG AA. No necesitas calcular las proporciones de contraste tú mismo.
:::

La fila superior muestra la sombra con su color de `on-text` (para el texto colocado directamente sobre esa sombra). La fila inferior muestra el color `text` sobre un fondo de superficie:

<dwc-doc-step-vars name="primary"></dwc-doc-step-vars>

### Variables generadas adicionales {#additional-generated-variables}

| Patrón de Variable | Descripción |
|---|---|
| `--dwc-color-{name}-tint` | El color semilla a 12% de opacidad, para fondos de resaltado sutiles. |
| `--dwc-border-color-{name}` | Color de borde consciente del modo teñido con el matiz de la paleta. |
| `--dwc-border-color-{name}-emphasis` | Color de borde consciente del modo más fuerte para estados de hover, foco y activos. |
| `--dwc-focus-ring-{name}` | Sombra del anillo de enfoque para la paleta. |

## Colores globales {#global-colors}

Estos son colores conscientes del modo que se adaptan automáticamente a los temas claros y oscuros.

| Variable | Descripción |
|---|---|
| `--dwc-color-black` | Casi negro en modo claro, casi blanco en modo oscuro. |
| `--dwc-color-white` | Casi blanco en modo claro, casi negro en modo oscuro. |
| `--dwc-color-body-text` | Color de texto predeterminado del cuerpo (usa `--dwc-color-black`). |

## Temas de componentes {#theming-components-with-abstract-variables}

DWC abstrae el uso de las paletas disponibles con un conjunto de variables de variación semántica de nivel superior. Los componentes utilizan estas variaciones en lugar de números de pasos en bruto, porque las variaciones se adaptan automáticamente a los modos claros y oscuros.

Las variaciones se dividen en tres grupos: `normal`, `dark`, y `light`.

1. Las variables `normal` son el color base, utilizadas para fondos y elementos de interfaz de usuario primarios.
2. Las variables `dark` se utilizan principalmente para estados `activos/pulsados`.
3. Las variables `light` se utilizan principalmente para estados `hover/foco`.

<Tabs>

<TabItem value="Primary">

<dwc-doc-variations name="primary"></dwc-doc-variations>

```css
--dwc-color-primary-dark: var(--dwc-color-primary-45)
--dwc-color-primary: var(--dwc-color-primary-50)
--dwc-color-primary-light: var(--dwc-color-primary-55)
--dwc-color-primary-alt: var(--dwc-color-primary-tint)

--dwc-color-primary-text-dark: var(--dwc-color-primary-text-40)
--dwc-color-primary-text: var(--dwc-color-primary-text-45)
--dwc-color-primary-text-light: var(--dwc-color-primary-text-50)

--dwc-color-on-primary-text-dark: var(--dwc-color-on-primary-text-45)
--dwc-color-on-primary-text: var(--dwc-color-on-primary-text-50)
--dwc-color-on-primary-text-light: var(--dwc-color-on-primary-text-55)
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text)
```

</TabItem>

<TabItem value="Success">

<dwc-doc-variations name="success"></dwc-doc-variations>

```css
--dwc-color-success-dark: var(--dwc-color-success-45)
--dwc-color-success: var(--dwc-color-success-50)
--dwc-color-success-light: var(--dwc-color-success-55)
--dwc-color-success-alt: var(--dwc-color-success-tint)

--dwc-color-success-text-dark: var(--dwc-color-success-text-40)
--dwc-color-success-text: var(--dwc-color-success-text-45)
--dwc-color-success-text-light: var(--dwc-color-success-text-50)

--dwc-color-on-success-text-dark: var(--dwc-color-on-success-text-45)
--dwc-color-on-success-text: var(--dwc-color-on-success-text-50)
--dwc-color-on-success-text-light: var(--dwc-color-on-success-text-55)
--dwc-color-on-success-text-alt: var(--dwc-color-success-text)
```

</TabItem>

<TabItem value="Warning">

<dwc-doc-variations name="warning"></dwc-doc-variations>

```css
--dwc-color-warning-dark: var(--dwc-color-warning-45)
--dwc-color-warning: var(--dwc-color-warning-50)
--dwc-color-warning-light: var(--dwc-color-warning-55)
--dwc-color-warning-alt: var(--dwc-color-warning-tint)

--dwc-color-warning-text-dark: var(--dwc-color-warning-text-40)
--dwc-color-warning-text: var(--dwc-color-warning-text-45)
--dwc-color-warning-text-light: var(--dwc-color-warning-text-50)

--dwc-color-on-warning-text-dark: var(--dwc-color-on-warning-text-45)
--dwc-color-on-warning-text: var(--dwc-color-on-warning-text-50)
--dwc-color-on-warning-text-light: var(--dwc-color-on-warning-text-55)
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text)
```

</TabItem>

<TabItem value="Danger">

<dwc-doc-variations name="danger"></dwc-doc-variations>

```css
--dwc-color-danger-dark: var(--dwc-color-danger-45)
--dwc-color-danger: var(--dwc-color-danger-50)
--dwc-color-danger-light: var(--dwc-color-danger-55)
--dwc-color-danger-alt: var(--dwc-color-danger-tint)

--dwc-color-danger-text-dark: var(--dwc-color-danger-text-40)
--dwc-color-danger-text: var(--dwc-color-danger-text-45)
--dwc-color-danger-text-light: var(--dwc-color-danger-text-50)

--dwc-color-on-danger-text-dark: var(--dwc-color-on-danger-text-45)
--dwc-color-on-danger-text: var(--dwc-color-on-danger-text-50)
--dwc-color-on-danger-text-light: var(--dwc-color-on-danger-text-55)
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text)
```

</TabItem>

<TabItem value="Info">

<dwc-doc-variations name="info"></dwc-doc-variations>

```css
--dwc-color-info-dark: var(--dwc-color-info-45)
--dwc-color-info: var(--dwc-color-info-50)
--dwc-color-info-light: var(--dwc-color-info-55)
--dwc-color-info-alt: var(--dwc-color-info-tint)

--dwc-color-info-text-dark: var(--dwc-color-info-text-40)
--dwc-color-info-text: var(--dwc-color-info-text-45)
--dwc-color-info-text-light: var(--dwc-color-info-text-50)

--dwc-color-on-info-text-dark: var(--dwc-color-on-info-text-45)
--dwc-color-on-info-text: var(--dwc-color-on-info-text-50)
--dwc-color-on-info-text-light: var(--dwc-color-on-info-text-55)
--dwc-color-on-info-text-alt: var(--dwc-color-info-text)
```

</TabItem>

<TabItem value="Default / Tone">

<dwc-doc-variations name="default"></dwc-doc-variations>

La variación por defecto se utiliza para elementos de interfaz de usuario neutrales como fondos de componentes y bordes. Hereda su matiz de la paleta primaria con saturación muy baja. A diferencia de paletas cromáticas, la predeterminada utiliza sus propios cálculos de claridad OKLCH en lugar de pasos de paleta.

```css
--dwc-color-default-dark
--dwc-color-default
--dwc-color-default-light
--dwc-color-default-alt: var(--dwc-color-primary-alt)

--dwc-color-default-text-dark: var(--dwc-color-default-text-40)
--dwc-color-default-text: var(--dwc-color-default-text-45)
--dwc-color-default-text-light: var(--dwc-color-default-text-50)

--dwc-color-on-default-text-dark
--dwc-color-on-default-text
--dwc-color-on-default-text-light
--dwc-color-on-default-text-alt: var(--dwc-color-primary-text)

--dwc-focus-ring-default: var(--dwc-focus-ring-primary)
```

</TabItem>

<TabItem value="Gray">

<dwc-doc-variations name="gray"></dwc-doc-variations>

La variación gris utiliza sombras de gris puro y es consciente del modo, eligiendo de pasos oscuros en modo claro y pasos claros en modo oscuro.

```css
--dwc-color-gray-dark
--dwc-color-gray
--dwc-color-gray-light
--dwc-color-gray-alt: var(--dwc-color-gray-tint)

--dwc-color-gray-text-dark: var(--dwc-color-gray-text-40)
--dwc-color-gray-text: var(--dwc-color-gray-text-45)
--dwc-color-gray-text-light: var(--dwc-color-gray-text-50)

--dwc-color-on-gray-text-dark
--dwc-color-on-gray-text
--dwc-color-on-gray-text-light
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text)
```

</TabItem>

</Tabs>

### Referencia de variaciones {#variation-reference}

| Variable | Descripción |
|---|---|
| `--dwc-color-{name}` | El color base. Utilizado para fondos, rellenos y bordes. |
| `--dwc-color-{name}-dark` | Una versión más oscura. Utilizada para estados activos/pulsados. |
| `--dwc-color-{name}-light` | Una versión más clara. Utilizada para estados de hover/foco. |
| `--dwc-color-{name}-alt` | La semilla a 12% de opacidad. Utilizada para estados de resaltado sutil. |
| `--dwc-color-{name}-text` | Color de texto seguro en superficies de aplicación (WCAG AA). |
| `--dwc-color-{name}-text-dark` | Variación de texto más oscura. |
| `--dwc-color-{name}-text-light` | Variación de texto más clara. |
| `--dwc-color-on-{name}-text` | Color de texto para usar SOBRE `--dwc-color-{name}` como fondo. |
| `--dwc-color-on-{name}-text-dark` | Color de texto para usar SOBRE `--dwc-color-{name}-dark`. |
| `--dwc-color-on-{name}-text-light` | Color de texto para usar SOBRE `--dwc-color-{name}-light`. |
| `--dwc-color-on-{name}-text-alt` | Color de texto para usar SOBRE `--dwc-color-{name}-alt`. |
| `--dwc-border-color-{name}` | Color de borde consciente del modo. |
| `--dwc-border-color-{name}-emphasis` | Color de borde más fuerte consciente del modo. |
| `--dwc-focus-ring-{name}` | Sombra del anillo de enfoque. |
