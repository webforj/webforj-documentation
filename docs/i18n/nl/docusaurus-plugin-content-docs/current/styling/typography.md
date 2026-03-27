---
sidebar_position: 4
title: Typography
_i18n_hash: 86ea072dd194053c5a0195a6e7b7200e
---
Typografie-tokens worden gebruikt om een consistente set van letterstijl door je app te handhaven.

## Lettertypefamilie {#font-family}

De lettertypefamilie-eigenschappen worden gebruikt om een geprioriteerde lijst van lettertype-namen op te geven.

Standaard wordt de systeemlettertype-stapel gebruikt:

- `Segoe UI` op Windows
- `Roboto` op Android en Chrome OS
- `San Francisco` op macOS en iOS
- Op andere systemen worden `Helvetica, Arial` als fallback gebruikt.

Je kunt de lettertypefamilie toepassen of wijzigen met de aangepaste eigenschap `--dwc-font-family`.

### Voorbeeld {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variabelen {#variables}

<!-- vale Google.FirstPerson = NO -->
| **Variabele**             | **Standaardwaarde**                                                                                                                                          | **Voorbeeld**                                                           |
| ------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-font-family-sans`  | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol'          | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span> |
| `--dwc-font-family-mono`  | Menlo, Monaco, 'Courier New', monospace                                                                                                                   | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span> |
| `--dwc-font-family`       | `var(--dwc-font-family-sans)`                                                                                                                              | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span>      |
<!-- vale Google.FirstPerson = YES -->

## Lettergrootte {#font-size}

De lettergrootte-eigenschappen definieëren een set van lettergroottes om uit te kiezen. `s` is de standaardgrootte en wordt standaard door de meeste componenten gebruikt. Alle lettergroottes zijn gedefinieerd in `em`.

:::info EM Eenheid
`em` is een relatieve lengte-eenheid. Het is relatief aan de [lettergrootte](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) van de ouder, in het geval van typografische eigenschappen zoals lettergrootte, en de lettergrootte van het element zelf en in het geval van andere eigenschappen zoals [breedte](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Voorbeeld {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variabelen {#variables-1}

| **Variabele**           | **Standaardwaarde** | **Voorbeeld**                                                      |
| ----------------------- | --------------------| ------------------------------------------------------------------ |
| `--dwc-font-size-2xs`  | 0.75rem             | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`   | 0.813rem            | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`    | 0.875rem            | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`    | 1rem                | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`    | 1.125rem            | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`   | 1.375rem            | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl`  | 1.75rem             | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl`  | 2.25rem             | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`      | `var(--dwc-font-size-s)` | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## Lettergewicht {#font-weight}

De [lettergewicht](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS-eigenschap stelt het gewicht (of de vetheid) van het lettertype in.

### Voorbeeld {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variabele**               | **Standaardwaarde** | **Voorbeeld**                                                               |
| --------------------------- | --------------------| --------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter` | 200                  | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`   | 300                  | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`  | 400                  | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold` | 500                  | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`    | 700                  | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`  | 800                  | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Regelhoogte {#line-height}

De regelhoogte CSS-eigenschap stelt de hoogte van een regelbox in. Het wordt vaak gebruikt om de afstand tussen tekstregels in te stellen.

### Voorbeeld {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variabelen {#variables-2}

<!-- vale Google.FirstPerson = NO -->
| **Variabele**                  | **Standaardwaarde**         | **Voorbeeld**                                                                                                |
| ------------------------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------ |
| `--dwc-font-line-height-2xs`   | 0.95                        | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span> |
| `--dwc-font-line-height-xs`    | 1.1                         | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span>  |
| `--dwc-font-line-height-s`     | 1.25                        | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span>   |
| `--dwc-font-line-height-m`     | 1.375                       | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span>   |
| `--dwc-font-line-height-l`     | 1.5                         | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span>   |
| `--dwc-font-line-height-xl`    | 1.75                        | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span>  |
| `--dwc-font-line-height-2xl`   | 2                           | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span> |
| `--dwc-font-line-height`        | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Sphinx van zwarte kwarts, oordeel over mijn belofte.<br/>Sphinx van zwarte kwarts, oordeel over mijn belofte.</span>     |
<!-- vale Google.FirstPerson = YES -->
