---
sidebar_position: 3
title: Colors
_i18n_hash: c0e3dc5c992621c0c9cb3da24ef3964f
---
import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';

webforJ proporciona un sistema de color basado en propiedades personalizadas de CSS. Estas variables de color mantienen un estilo visual consistente en toda tu aplicación mientras te brindan control total para personalizar paletas según tus necesidades de diseño.

Puedes hacer referencia a cualquier color utilizando la sintaxis `--dwc-color-{palette}-{shade}`, donde `{palette}` es el nombre del grupo de colores (por ejemplo, `primary`, `danger`, ..) y `{shade}` es un número del `5` al `95` en incrementos de `5`, representando la claridad del color.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Escala de Valores de Sombra
Los valores de sombra van desde `5` (más oscuro) hasta `95` (más claro), aumentando en pasos de `5`.
:::

## Paletas de colores {#color-palettes}

Hay varias paletas de colores integradas, cada una diseñada para casos de uso semántico como branding, mensajes de éxito, advertencias y más. Cada paleta está compuesta de matices y tonos generados dinámicamente basados en tres propiedades clave: `hue`, `saturation`, y `contrast-threshold`.

### Paletas disponibles {#available-palettes}

- **default**: Una paleta neutra basada en gris, matizada con el color primario, utilizada para la mayoría de los componentes.
- **primary**: Representa típicamente el color primario de tu marca.
- **success**, **warning**, **danger**: Paletas semánticas utilizadas para indicadores de estado apropiados.
- **info**: Una paleta complementaria opcional para un énfasis secundario.
- **gray**: Una paleta de escala de grises verdadera, sin matizar.
- **black/white**: Valores de color estáticos.

<ColorPalette></ColorPalette>

<br/>

:::tip DWC HueCraft
Para simplificar el proceso de generación de paletas compatibles con WCAG para tus aplicaciones webforJ, puedes utilizar la herramienta [DWC HueCraft](https://webforj.github.io/huecraft/). Soporta la creación de paletas basadas en colores de marca o logotipos y permite una exportación rápida de CSS.
:::

### Comportamiento en modo oscuro {#dark-mode-behavior}

webforJ admite una estrategia de color invertido en modo oscuro. En vez de utilizar paletas de colores completamente separadas, invierte la forma en que se interpretan los valores de claridad.

Esto significa que en **temas oscuros**, los valores de sombra más bajos (por ejemplo, `--dwc-color-primary-5`) se convierten en claros y los valores más altos (por ejemplo, `--dwc-color-primary-95`) se convierten en oscuros. La lógica se invierte para asegurar un contraste y una legibilidad óptimos en todos los fondos.

El código de tu componente permanece igual, independientemente del tema. Por ejemplo:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

En modo claro, esto da un fondo de tono medio. En modo oscuro, todavía da un tono medio, pero visualmente invertido para funcionar sobre superficies oscuras. Este enfoque evita la duplicación, mantiene tus estilos consistentes y suaviza las transiciones visuales al cambiar entre temas claros y oscuros.

### Variables de configuración de paleta {#palette-configuration-variables}

Cada paleta se genera en base a las siguientes variables:

| Variable               | Descripción |
|------------------------|-------------|
| `hue`                  | El ángulo (en grados) en la rueda de color. Los valores sin unidad se interpretan como grados. |
| `saturation`           | Un porcentaje que indica la intensidad del color. `100%` es completamente saturado; `0%` es escala de grises. |
| `contrast-threshold`   | Un valor entre `0` y `100` que determina si el texto debe ser claro u oscuro según la claridad del fondo. |

Puedes ajustar una paleta redefiniendo estas variables en tus estilos raíz. Por ejemplo, para modificar la paleta primaria:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Tematización de componentes con variables abstractas {#theming-components-with-abstract-variables}

Para simplificar el estilo y la consistencia en todos los componentes, webforJ introduce una capa de abstracción sobre las paletas de colores base. Esta capa se basa en **variables de tema abstractas** - propiedades personalizadas de CSS que se refieren a tonos específicos dentro de una paleta de colores.

Estas variables facilitan la aplicación de temas en todos los componentes sin referirse directamente a valores de color en crudo o muestras. Puedes pensar en ellas como *atajos de estilo semántico* que reflejan la intención de tu aplicación en lugar de sus detalles de implementación.

### Grupos de variables {#variable-groups}

Las variables de tema abstractas se organizan en cuatro grupos:

1. [Normal](#normal-state) Usado para la apariencia predeterminada, como fondos y texto en componentes inactivos.
2. [Oscuro](#darker-variant) Usado para estados activos o seleccionados.
3. [Claro](#lighter-variant) Usado para estados de hover y enfoque.
4. [Alt](#alt-variant) Usado para realces secundarios, como enfoque de teclado o acentos sutiles de UI.

Cada grupo define:

- Un color de fondo
- Un color de primer plano (texto)
- Un color de borde (para estados enfocados/hover/activos)
- Un anillo de enfoque (utilizado cuando el componente recibe un enfoque visible)

Cada pestaña a continuación muestra las variables abstractas definidas para una paleta específica (`primary`, `success`, `danger`, etc.). Estas variables extraen valores de la paleta subyacente (por ejemplo, `--dwc-color-primary-40`) y los hacen reutilizables en toda tu aplicación.

Por ejemplo, en lugar de usar directamente `--dwc-color-primary-40`, puedes aplicar `--dwc-color-primary`, que abstrae el papel de ese color como el *fondo predeterminado* para un componente estilizado como primario.

Cambiar los valores de la paleta en un lugar actualizará el aspecto de todos los componentes que dependen de estas variables abstractas.

### Estado normal {#normal-state}

Usado para la apariencia base, neutral de un componente—cuando está inactivo y no se está interactuando con él.

| Variable                           | Descripción                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | El color de fondo predeterminado. También se utiliza para los bordes en muchos componentes. |
| `--dwc-color-on-${name}-text`      | El color del texto mostrado sobre el fondo predeterminado.             |
| `--dwc-color-${name}-text`         | El color del texto cuando el componente se coloca sobre el fondo de superficie. |
| `--dwc-border-color-${name}`       | Color del borde, utilizado principalmente para estados hover, enfoque y activos. |
| `--dwc-focus-ring-${name}`         | Sombra del anillo de enfoque cuando el componente recibe el estilo de enfoque visible. |

---

### Variante más oscura {#darker-variant}

Usado para estados seleccionados o activos—generalmente un tono más profundo para un contraste y énfasis más fuertes.

| Variable                                | Descripción                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Una versión más oscura del color base. A menudo utilizado para estados presionados o seleccionados. |
| `--dwc-color-on-${name}-text-dark`      | Color del texto cuando se utiliza sobre un fondo oscuro.                 |
| `--dwc-color-${name}-text-dark`         | Una alternativa de texto ligeramente más oscura cuando se muestra sobre la superficie. |

---

### Variante más clara {#lighter-variant}

Usado para hover, enfoque y pistas visuales menos dominantes. Estos son tonos suaves diseñados para una retroalimentación sutil de interacción.

| Variable                                | Descripción                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Una versión más clara del color base. Típicamente utilizado para fondos de hover/enfoque. |
| `--dwc-color-on-${name}-text-light`     | Color del texto cuando se muestra sobre un fondo claro.                  |
| `--dwc-color-${name}-text-light`        | Un tono de texto más claro para utilizar en estados menos prominentes.   |

---

### Variante alterna {#alt-variant}

Usado para énfasis secundario o realces de UI—como contornos de enfoque de navegación por teclado o indicadores auxiliares.

| Variable                                | Descripción                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Una versión muy clara del color, utilizada principalmente para realces o brillos de fondo. |
| `--dwc-color-on-${name}-text-alt`       | Color del texto cuando el fondo es el color alternativo (`alt`).         |

<Tabs>

<TabItem value="Default / Tone">

```css
--dwc-color-default-dark: var(--dwc-color-default-85);
--dwc-color-on-default-text-dark: var(--dwc-color-default-text-85);
--dwc-color-default-text-dark: var(--dwc-color-default-35);

--dwc-color-default: var(--dwc-color-default-90);
--dwc-color-on-default-text: var(--dwc-color-default-text-90);
--dwc-color-default-text: var(--dwc-color-default-40);

--dwc-color-default-light: var(--dwc-color-default-95);
--dwc-color-on-default-text-light: var(--dwc-color-default-text-95);
--dwc-color-default-text-light: var(--dwc-color-default-45);

--dwc-color-default-alt: var(--dwc-color-primary-alt);
--dwc-color-on-default-text-alt: var(--dwc-color-on-primary-text-alt);

--dwc-border-color-default: var(--dwc-border-color-primary);
--dwc-focus-ring-default: var(--dwc-focus-ring-primary);
```

</TabItem>

<TabItem value="Primary">

```css
--dwc-color-primary-dark: var(--dwc-color-primary-35);
--dwc-color-on-primary-text-dark: var(--dwc-color-primary-text-35);
--dwc-color-primary-text-dark: var(--dwc-color-primary-30);

--dwc-color-primary: var(--dwc-color-primary-40);
--dwc-color-on-primary-text: var(--dwc-color-primary-text-40);
--dwc-color-primary-text: var(--dwc-color-primary-35);

--dwc-color-primary-light: var(--dwc-color-primary-45);
--dwc-color-on-primary-text-light: var(--dwc-color-primary-text-45);
--dwc-color-primary-text-light: var(--dwc-color-primary-40);

--dwc-color-primary-alt: var(--dwc-color-primary-95);
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text-95);

--dwc-border-color-primary: var(--dwc-color-primary);
--dwc-focus-ring-primary: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-primary-h),
    var(--dwc-color-primary-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Success">

```css
--dwc-color-success-dark: var(--dwc-color-success-20);
--dwc-color-on-success-text-dark: var(--dwc-color-success-text-20);
--dwc-color-success-text-dark: var(--dwc-color-success-15);

--dwc-color-success: var(--dwc-color-success-25);
--dwc-color-on-success-text: var(--dwc-color-success-text-25);
--dwc-color-success-text: var(--dwc-color-success-20);

--dwc-color-success-light: var(--dwc-color-success-30);
--dwc-color-on-success-text-light: var(--dwc-color-success-text-30);
--dwc-color-success-text-light: var(--dwc-color-success-25);

--dwc-color-success-alt: var(--dwc-color-success-95);
--dwc-color-on-success-text-alt: var(--dwc-color-success-text-95);

--dwc-border-color-success: var(--dwc-color-success);
--dwc-focus-ring-success: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-success-h),
    var(--dwc-color-success-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Warning">

```css
--dwc-color-warning-dark: var(--dwc-color-warning-35);
--dwc-color-on-warning-text-dark: var(--dwc-color-warning-text-35);
--dwc-color-warning-text-dark: var(--dwc-color-warning-15);

--dwc-color-warning: var(--dwc-color-warning-40);
--dwc-color-on-warning-text: var(--dwc-color-warning-text-40);
--dwc-color-warning-text: var(--dwc-color-warning-20);

--dwc-color-warning-light: var(--dwc-color-warning-45);
--dwc-color-on-warning-text-light: var(--dwc-color-warning-text-45);
--dwc-color-warning-text-light: var(--dwc-color-warning-25);

--dwc-color-warning-alt: var(--dwc-color-warning-95);
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text-95);

--dwc-border-color-warning: var(--dwc-color-warning);
--dwc-focus-ring-warning: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-warning-h),
    var(--dwc-color-warning-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-warning-a)
  );
```

</TabItem>

<TabItem value="Danger">

```css
--dwc-color-danger-dark: var(--dwc-color-danger-35);
--dwc-color-on-danger-text-dark: var(--dwc-color-danger-text-35);
--dwc-color-danger-text-dark: var(--dwc-color-danger-30);

--dwc-color-danger: var(--dwc-color-danger-40);
--dwc-color-on-danger-text: var(--dwc-color-danger-text-40);
--dwc-color-danger-text: var(--dwc-color-danger-35);

--dwc-color-danger-light: var(--dwc-color-danger-45);
--dwc-color-on-danger-text-light: var(--dwc-color-danger-text-45);
--dwc-color-danger-text-light: var(--dwc-color-danger-40);

--dwc-color-danger-alt: var(--dwc-color-danger-95);
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text-95);

--dwc-border-color-danger: var(--dwc-color-danger);
--dwc-focus-ring-danger: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-danger-h),
    var(--dwc-color-danger-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Info">

```css
--dwc-color-info-dark: var(--dwc-color-info-35);
--dwc-color-on-info-text-dark: var(--dwc-color-info-text-35);
--dwc-color-info-text-dark: var(--dwc-color-info-35);

--dwc-color-info: var(--dwc-color-info-40);
--dwc-color-on-info-text: var(--dwc-color-info-text-40);
--dwc-color-info-text: var(--dwc-color-info-40);

--dwc-color-info-light: var(--dwc-color-info-45);
--dwc-color-on-info-text-light: var(--dwc-color-info-text-45);
--dwc-color-info-text-light: var(--dwc-color-info-45);

--dwc-color-info-alt: var(--dwc-color-info-95);
--dwc-color-on-info-text-alt: var(--dwc-color-info-text-95);

--dwc-border-color-info: var(--dwc-color-info);
--dwc-focus-ring-info: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-info-h),
    var(--dwc-color-info-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-info-a)
  );
```

</TabItem>

<TabItem value="Gray">

```css
--dwc-color-gray-dark: var(--dwc-color-gray-10);
--dwc-color-on-gray-text-dark: var(--dwc-color-gray-text-10);
--dwc-color-gray-text-dark: var(--dwc-color-gray-15);

--dwc-color-gray: var(--dwc-color-gray-15);
--dwc-color-on-gray-text: var(--dwc-color-gray-text-15);
--dwc-color-gray-text: var(--dwc-color-gray-20);

--dwc-color-gray-light: var(--dwc-color-gray-20);
--dwc-color-on-gray-text-light: var(--dwc-color-gray-text-20);
--dwc-color-gray-text-light: var(--dwc-color-gray-25);

--dwc-color-gray-alt: var(--dwc-color-gray-95);
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text-95);

--dwc-border-color-gray: var(--dwc-color-gray);
--dwc-focus-ring-gray: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-gray-h),
    var(--dwc-color-gray-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-gray-a)
  );
```
</TabItem>

</Tabs>
