---
sidebar_position: 5
title: Sizing and Spacing
_i18n_hash: 13396e3bc7eb84e83ef282c219954f8a
---
Spacing- und Größen-Tokens werden verwendet, um konsistente Abstände und Größen in Ihrer App bereitzustellen. Alle Größen- und Abstands-Eigenschaften sind in `rem` definiert.

:::info REM-Einheit
`rem` ist eine relative Längeneinheit. Sie ist relativ zur Schriftgröße des [Root-Elements](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html).
:::

## Größe {#sizing}

Verwenden Sie diese Eigenschaften, um die Größe der Komponente (Breite, Höhe) anzupassen. `m` ist die Standardgröße für fast alle Komponenten.

:::tip Größe wählen
Achten Sie bei der Auswahl einer Größe immer darauf, dass sie groß genug für Touch-Ziele ist.
:::

### Beispiel {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Variablen {#variables}

| **Variable**     | **Standardwert**   | **Beispiel**                         |
| ---------------- | ------------------ | ----------------------------------- |
| `--dwc-size-3xs` | 1.125rem           | <SizingBox size="--dwc-size-3xs" /> |
| `--dwc-size-2xs` | 1.375rem           | <SizingBox size="--dwc-size-2xs" /> |
| `--dwc-size-xs`  | 1.625rem           | <SizingBox size="--dwc-size-xs" />  |
| `--dwc-size-s`   | 1.875rem           | <SizingBox size="--dwc-size-s" />   |
| `--dwc-size-m`   | 2.25rem            | <SizingBox size="--dwc-size-m" />   |
| `--dwc-size-l`   | 2.75rem            | <SizingBox size="--dwc-size-l" />   |
| `--dwc-size-xl`  | 3.5rem             | <SizingBox size="--dwc-size-xl" />  |
| `--dwc-size-2xl` | 4rem               | <SizingBox size="--dwc-size-2xl" /> |
| `--dwc-size-3xl` | 4.25rem            | <SizingBox size="--dwc-size-3xl" /> |
| `--dwc-size`     | var(--dwc-size-m)  | <SizingBox size="--dwc-size" />     |

## Abstände {#spacing}

Verwenden Sie diese Eigenschaften, um den Abstand zwischen den Komponenten (Margin, Padding) anzupassen.

### Beispiel {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Variablen {#variables-1}

| **Variable**      | **Standardwert**    | **Beispiel**                            |
| ----------------- | ------------------- | -------------------------------------- |
| `--dwc-space-3xs` | 0.075rem            | <SpacingBox space="--dwc-space-3xs" /> |
| `--dwc-space-2xs` | 0.15rem             | <SpacingBox space="--dwc-space-2xs" /> |
| `--dwc-space-xs`  | 0.25rem             | <SpacingBox space="--dwc-space-xs" />  |
| `--dwc-space-s`   | 0.5rem              | <SpacingBox space="--dwc-space-s" />   |
| `--dwc-space-m`   | 1rem                | <SpacingBox space="--dwc-space-m" />   |
| `--dwc-space-l`   | 1.25rem             | <SpacingBox space="--dwc-space-l" />   |
| `--dwc-space-xl`  | 1.5rem              | <SpacingBox space="--dwc-space-xl" />  |
| `--dwc-space-2xl` | 1.75rem             | <SpacingBox space="--dwc-space-2xl" /> |
| `--dwc-space-3xl` | 2rem                | <SpacingBox space="--dwc-space-3xl" /> |
| `--dwc-space`     | var(--dwc-space-s)  | <SpacingBox space="--dwc-space" />     |
