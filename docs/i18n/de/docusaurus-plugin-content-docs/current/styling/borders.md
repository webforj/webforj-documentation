---
sidebar_position: 6
title: Border
_i18n_hash: fe0a0386da63ff7ea085db8daa12d0fe
---
Die Rahmen-Eigenschaften werden verwendet, um den Rahmenstil und die -breite der Komponente zu steuern. Siehe [verfügbare Rahmenstile](https://developer.mozilla.org/en-US/docs/Web/CSS/border-style).

### Beispiel {#example}

```css
.element {
  border: var(--dwc-border-width) var(--dwc-border-style) red;
}
```

### Variablen {#variables}

| **Variable**         | **Standardwert** |
|----------------------|-------------------|
| `--dwc-border-width` | 1px               |
| `--dwc-border-style` | solid             |

## Rahmeneckenradius {#border-radius}

Die Variablen für den Rahmeneckenradius definieren, wie abgerundet die Ecken einer Komponente sind. Alle Werte sind in `em` definiert, sodass sie mit der Schriftgröße skalieren.

:::info EM-Einheit
`em` ist eine relative Einheit, die mit der [Schriftgröße](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) des übergeordneten Elements skaliert.
:::

### Beispiel {#example-1}

```css
.element {
  border-radius: var(--dwc-border-radius-m);
}
```

### Variablen {#variables-1}

| **Variable**                | **Standardwert**          | **Beispiel**                         |
|-----------------------------|-----------------------------|--------------------------------------|
| `--dwc-border-radius-2xs`   | 0.071em                    | <RadiusBox radius="--dwc-border-radius-2xs" /> |
| `--dwc-border-radius-xs`    | 0.125em                    | <RadiusBox radius="--dwc-border-radius-xs" /> |
| `--dwc-border-radius-s`     | 0.25em                     | <RadiusBox radius="--dwc-border-radius-s" />  |
| `--dwc-border-radius-m`     | 0.375em                    | <RadiusBox radius="--dwc-border-radius-m" />  |
| `--dwc-border-radius-l`     | 0.5em                      | <RadiusBox radius="--dwc-border-radius-l" />  |
| `--dwc-border-radius-xl`    | 0.75em                     | <RadiusBox radius="--dwc-border-radius-xl" /> |
| `--dwc-border-radius-2xl`   | 1em                        | <RadiusBox radius="--dwc-border-radius-2xl" /> |
| `--dwc-border-radius-round` | 50%                        | <RadiusBox radius="--dwc-border-radius-round" /> |
| `--dwc-border-radius-pill`  | 9999px                     | <RadiusBox radius="--dwc-border-radius-pill" /> |
| `--dwc-border-radius`       | var(--dwc-border-radius-s) | <RadiusBox radius="--dwc-border-radius" />     |
