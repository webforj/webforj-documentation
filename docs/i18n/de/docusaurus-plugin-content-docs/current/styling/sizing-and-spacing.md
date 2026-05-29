---
sidebar_position: 5
title: Sizing and Spacing
_i18n_hash: 05261a33707bc38ade5e855f5ae5ce47
---
Abstand- und Größen-Tokens werden verwendet, um konsistenten Abstand und Größen in Ihrer App bereitzustellen. Alle Größen- und Abstands-Eigenschaften sind in `rem` definiert.

:::info REM-Einheit
`rem` ist eine relative Längeneinheit. Sie bezieht sich auf die Schriftgröße des [Wurzelelements](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/html).
:::

## Größen {#sizing}

Verwenden Sie diese Eigenschaften, um die Größe der Komponente (Breite, Höhe) anzupassen. `m` ist die Standardgröße für fast alle Komponenten.

:::tip Auswahl einer Größe
Stellen Sie bei der Auswahl einer Größe immer sicher, dass sie groß genug für die Touch-Ziele ist.
:::

### Beispiel {#example}

```css
.element {
  width: var(--dwc-size-m);
  height: var(--dwc-size-m);
}
```

### Variablen {#variables}

| **Variable**     | **Standardwert**  | **Berechnet (bei 16px Wurzel)** |
| ---------------- | ----------------- | ------------------------------- |
| `--dwc-size-3xs` | 1.125rem          | 18px |
| `--dwc-size-2xs` | 1.375rem          | 22px |
| `--dwc-size-xs`  | 1.625rem          | 26px |
| `--dwc-size-s`   | 1.875rem          | 30px |
| `--dwc-size-m`   | 2.25rem           | 36px |
| `--dwc-size-l`   | 2.75rem           | 44px |
| `--dwc-size-xl`  | 3.25rem           | 52px |
| `--dwc-size-2xl` | 4rem              | 64px |
| `--dwc-size-3xl` | 4.25rem           | 68px |
| `--dwc-size`     | var(--dwc-size-m) | 36px |

<dwc-doc-sizes></dwc-doc-sizes>

## Abstand {#spacing}

Verwenden Sie diese Eigenschaften, um den Abstand zwischen Komponenten (Margin, Padding) anzupassen.

### Beispiel {#example-1}

```css
.element {
  padding: var(--dwc-space-m);
}
```

### Variablen {#variables-1}

| **Variable**      | **Standardwert**   | **Berechnet (bei 16px Wurzel)** |
| ----------------- | ------------------ | ------------------------------- |
| `--dwc-space-3xs` | 0.0625rem          | 1px |
| `--dwc-space-2xs` | 0.125rem           | 2px |
| `--dwc-space-xs`  | 0.25rem            | 4px |
| `--dwc-space-s`   | 0.5rem             | 8px |
| `--dwc-space-m`   | 1rem               | 16px |
| `--dwc-space-l`   | 1.25rem            | 20px |
| `--dwc-space-xl`  | 1.5rem             | 24px |
| `--dwc-space-2xl` | 1.75rem            | 28px |
| `--dwc-space-3xl` | 2rem               | 32px |
| `--dwc-space`     | var(--dwc-space-s) | 8px |

<dwc-doc-spaces></dwc-doc-spaces>
