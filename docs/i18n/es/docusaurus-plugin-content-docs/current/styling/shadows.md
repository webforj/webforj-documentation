---
sidebar_position: 7
title: Shadows
_i18n_hash: 84ad4478632d3020d57752a4827f925a
---
Las propiedades de sombra se utilizan para agregar efectos de sombra alrededor del marco de un elemento. Puedes establecer múltiples efectos separados por comas. En la mayoría de los casos, las sombras se utilizan para indicar elementos que están apilados unos sobre otros en la interfaz de usuario.

<Head>
  <style>{`
  table {
    width: 100%;
    display: table;
  }
  `}</style>
</Head>

### Ejemplo {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

:::tip Color de sombra
Controlas el color de la sombra estableciendo la variable `--dwc-shadow-color`. Por defecto, el color de la sombra es gris con un tinte del color primario.
:::

### Variables {#variables}

| **Variable**       | **Ejemplo**                             |
|--------------------|------------------------------------------|
| `--dwc-shadow-xs`  | <ShadowBox shadow="--dwc-shadow-xs" />  |
| `--dwc-shadow-s`   | <ShadowBox shadow="--dwc-shadow-s" />   |
| `--dwc-shadow-m`   | <ShadowBox shadow="--dwc-shadow-m" />   |
| `--dwc-shadow-l`   | <ShadowBox shadow="--dwc-shadow-l" />   |
| `--dwc-shadow-xl`  | <ShadowBox shadow="--dwc-shadow-xl" />  |
| `--dwc-shadow-2xl` | <ShadowBox shadow="--dwc-shadow-2xl" /> |
| `--dwc-shadow`     | <ShadowBox shadow="--dwc-shadow" />     |
