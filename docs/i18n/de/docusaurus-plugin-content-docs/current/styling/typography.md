---
sidebar_position: 4
title: Typography
_i18n_hash: 5eafa3dea127703b4f573da438cbaf57
---
Typografie-Tokens werden verwendet, um ein konsistentes Set an Schriftstilen in Ihrer App aufrechtzuerhalten.

## Schriftfamilie {#font-family}

Die Eigenschaften der Schriftfamilie werden verwendet, um eine priorisierte Liste von Schriftfamiliennamen anzugeben.

Der Systemschriftstapel wird standardmäßig verwendet:

- `Segoe UI` unter Windows
- `Roboto` unter Android und Chrome OS
- `San Francisco` unter macOS und iOS
- Auf anderen Systemen werden `Helvetica, Arial` als Fallbacks verwendet.

Sie können die Schriftfamilie mit der benutzerdefinierten Eigenschaft `--dwc-font-family` anwenden oder ändern.

### Beispiel {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variablen {#variables}

| **Variable**             | **Standardwert**                                                                                                                               | **Beispiel**                                                           |
| ------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------- |
| `--dwc-font-family-sans` | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family-mono` | Menlo, Monaco, 'Courier New', monospace                                                                                                         | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family`      | `var(--dwc-font-family-sans)`                                                                                                                   | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Sphinx of black quartz, judge my vow.</span>      |

## Schriftgröße {#font-size}

Die Eigenschaften der Schriftgröße definieren eine Auswahl von Schriftgrößen. `s` ist die Standardgröße und wird von den meisten Komponenten standardmäßig verwendet. Alle Schriftgrößen werden in `em` definiert.

:::info EM-Einheit
`em` ist eine relative Längeneinheit. Sie ist relativ zur [Schriftgröße](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) des übergeordneten Elements im Falle typografischer Eigenschaften wie Schriftgröße und zur Schriftgröße des Elements selbst im Falle anderer Eigenschaften wie [Breite](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Beispiel {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variablen {#variables-1}

| **Variable**          | **Standardwert**        | **Beispiel**                                                      |
| --------------------- | ------------------------ | ---------------------------------------------------------------- |
| `--dwc-font-size-2xs` | 0.75rem                  | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`  | 0.813rem                 | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`   | 0.875rem                 | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`   | 1rem                     | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`   | 1.125rem                 | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`  | 1.375rem                 | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl` | 1.75rem                  | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl` | 2.25rem                  | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`     | `var(--dwc-font-size-s)` | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## Schriftgewicht {#font-weight}

Die CSS-Eigenschaft [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) legt das Gewicht (oder die Fettigkeit) der Schrift fest.

### Beispiel {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variable**                 | **Standardwert** | **Beispiel**                                                               |
| ---------------------------- | ----------------- | ------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter`  | 200               | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`    | 300               | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`   | 400               | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold` | 500               | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`     | 700               | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`   | 800               | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Zeilenhöhe {#line-height}

Die CSS-Eigenschaft für die Zeilenhöhe legt die Höhe eines Zeilenblocks fest. Sie wird häufig verwendet, um den Abstand zwischen Textzeilen einzustellen.

### Beispiel {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variablen {#variables-2}

| **Variable**                 | **Standardwert**             | **Beispiel**                                                                                                |
| ---------------------------- | ----------------------------- | ---------------------------------------------------------------------------------------------------------- |
| `--dwc-font-line-height-2xs` | 0.95                          | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height-xs`  | 1.1                           | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-s`   | 1.25                          | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-m`   | 1.375                         | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-l`   | 1.5                           | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-xl`  | 1.75                          | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-2xl` | 2                             | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height`     | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>     |
