---
sidebar_position: 4
title: Typography
_i18n_hash: 7c5f036abf897a890cad14af0a64c6bd
---
Typografietokens werden verwendet, um einen konsistenten Satz von Schriftstilen in Ihrer Anwendung beizubehalten.

## Schriftfamilie {#font-family}

Die Eigenschaften der Schriftfamilie werden verwendet, um eine priorisierte Liste von Schriftfamilien zu spezifizieren.

Der Standardsystemschriftstapel wird standardmäßig über `system-ui` verwendet, der automatisch zur nativen Schriftart der Plattform aufgelöst wird:

- `San Francisco` auf macOS und iOS
- `Segoe UI` auf Windows
- `Roboto` auf Android und Chrome OS

Sie können die Schriftfamilie anwenden oder ändern, indem Sie die benutzerdefinierte Eigenschaft `--dwc-font-family` verwenden.

### Beispiel {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variablen {#variables}

| **Variable**               | **Standardwert** |
| -------------------------- | ----------------- |
| `--dwc-font-family-sans`   | system-ui, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji' |
| `--dwc-font-family-mono`   | ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace |
| `--dwc-font-family`        | `var(--dwc-font-family-sans)` |

## Schriftgröße {#font-size}

Die Eigenschaften der Schriftgröße definieren eine Reihe von Schriftgrößen, aus denen gewählt werden kann. `m` ist die Standardgröße und wird von den meisten Komponenten standardmäßig verwendet. Alle Schriftgrößen sind in `rem` definiert.

:::info REM-Einheit
`rem` ist eine relative Längeneinheit. Sie ist relativ zur [Schriftgröße](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) des root-Elements (`<html>`), die in den meisten Browsern standardmäßig 16px beträgt.
:::

### Beispiel {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variablen {#variables-1}

| **Variable**            | **Standardwert**        | **Berechnet (bei 16px root)** |
| ----------------------- | ----------------------- | ------------------------------ |
| `--dwc-font-size-3xs`   | 0.625rem                | 10px |
| `--dwc-font-size-2xs`   | 0.6875rem               | 11px |
| `--dwc-font-size-xs`    | 0.75rem                 | 12px |
| `--dwc-font-size-s`     | 0.8125rem               | 13px |
| `--dwc-font-size-m`     | 0.875rem                | 14px |
| `--dwc-font-size-l`     | 1rem                    | 16px |
| `--dwc-font-size-xl`    | 1.25rem                 | 20px |
| `--dwc-font-size-2xl`   | 1.625rem                | 26px |
| `--dwc-font-size-3xl`   | 2.125rem                | 34px |
| `--dwc-font-size`       | `var(--dwc-font-size-m)` | 14px |

<dwc-doc-font-sizes></dwc-doc-font-sizes>

## Schriftstärke {#font-weight}

Die CSS-Eigenschaft [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) legt die Stärke (oder Fettheit) der Schrift fest.

### Beispiel {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                 | **Standardwert** |
| ---------------------------- | ----------------- |
| `--dwc-font-weight-thin`     | 100 |
| `--dwc-font-weight-lighter`  | 200 |
| `--dwc-font-weight-light`    | 300 |
| `--dwc-font-weight-normal`   | 400 |
| `--dwc-font-weight-medium`   | 500 |
| `--dwc-font-weight-semibold` | 600 |
| `--dwc-font-weight-bold`     | 700 |
| `--dwc-font-weight-bolder`   | 800 |
| `--dwc-font-weight-black`    | 900 |
| `--dwc-font-weight`          | `var(--dwc-font-weight-normal)` |

<dwc-doc-font-weights></dwc-doc-font-weights>

## Zeilenhöhe {#line-height}

Die CSS-Eigenschaft line-height legt die Höhe eines Zeilenbereichs fest. Sie wird häufig verwendet, um den Abstand zwischen Zeilen von Text festzulegen.

### Beispiel {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variablen {#variables-2}

| **Variable**                    | **Standardwert**            |
| ------------------------------- | ---------------------------- |
| `--dwc-font-line-height-3xs`    | 1 |
| `--dwc-font-line-height-2xs`    | 1.1 |
| `--dwc-font-line-height-xs`     | 1.25 |
| `--dwc-font-line-height-s`      | 1.375 |
| `--dwc-font-line-height-m`      | 1.5 |
| `--dwc-font-line-height-l`      | 1.625 |
| `--dwc-font-line-height-xl`     | 1.75 |
| `--dwc-font-line-height-2xl`    | 1.875 |
| `--dwc-font-line-height-3xl`    | 2 |
| `--dwc-font-line-height`        | var(--dwc-font-line-height-xs) |

<dwc-doc-line-heights></dwc-doc-line-heights>
