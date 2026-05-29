---
sidebar_position: 4
title: Typography
_i18n_hash: 7c5f036abf897a890cad14af0a64c6bd
---
Typografie tokens worden gebruikt om een consistente set van letterstijlletjes door je app heen te handhaven.

## Lettertype familie {#font-family}

De lettertype familie eigenschappen worden gebruikt om een prioriteitenlijst van lettertypen te specificeren.

De systeemlettertype stapel wordt standaard gebruikt via `system-ui`, wat automatisch wordt opgelost naar het native lettertype van het platform:

- `San Francisco` op macOS en iOS
- `Segoe UI` op Windows
- `Roboto` op Android en Chrome OS

Je kunt de lettertype familie toepassen of wijzigen met behulp van de aangepaste eigenschap `--dwc-font-family`.

### Voorbeeld {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Variabelen {#variables}

| **Variabele**              | **Standaardwaarde**                                           |
| -------------------------- | ----------------------------------------------------------- |
| `--dwc-font-family-sans`   | system-ui, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji' |
| `--dwc-font-family-mono`   | ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace |
| `--dwc-font-family`        | `var(--dwc-font-family-sans)`                              |

## Lettergrootte {#font-size}

De lettergrootte eigenschappen definiëren een set lettergroottes om uit te kiezen. `m` is de standaardgrootte en wordt door de meeste componenten standaard gebruikt. Alle lettergroottes zijn gedefinieerd in `rem`.

:::info REM Eenheid
`rem` is een relatieve lengte-eenheid. Het is relatief aan de [lettergrootte](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) van het rootelement (`<html>`), wat in de meeste browsers standaard op 16px staat.
:::

### Voorbeeld {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Variabelen {#variables-1}

| **Variabele**             | **Standaardwaarde**     | **Berekenend (bij 16px root)** |
| ------------------------- | ----------------------- | ------------------------------- |
| `--dwc-font-size-3xs`    | 0.625rem                | 10px                            |
| `--dwc-font-size-2xs`    | 0.6875rem               | 11px                            |
| `--dwc-font-size-xs`     | 0.75rem                 | 12px                            |
| `--dwc-font-size-s`      | 0.8125rem               | 13px                            |
| `--dwc-font-size-m`      | 0.875rem                | 14px                            |
| `--dwc-font-size-l`      | 1rem                    | 16px                            |
| `--dwc-font-size-xl`     | 1.25rem                 | 20px                            |
| `--dwc-font-size-2xl`    | 1.625rem                | 26px                            |
| `--dwc-font-size-3xl`    | 2.125rem                | 34px                            |
| `--dwc-font-size`        | `var(--dwc-font-size-m)`| 14px                            |

<dwc-doc-font-sizes></dwc-doc-font-sizes>

## Lettergewicht {#font-weight}

De [font-weight](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS eigenschap stelt het gewicht (of vetheid) van het lettertype in.

### Voorbeeld {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Variabele**                  | **Standaardwaarde** |
| ------------------------------- | ------------------- |
| `--dwc-font-weight-thin`        | 100                 |
| `--dwc-font-weight-lighter`     | 200                 |
| `--dwc-font-weight-light`       | 300                 |
| `--dwc-font-weight-normal`      | 400                 |
| `--dwc-font-weight-medium`      | 500                 |
| `--dwc-font-weight-semibold`    | 600                 |
| `--dwc-font-weight-bold`        | 700                 |
| `--dwc-font-weight-bolder`      | 800                 |
| `--dwc-font-weight-black`       | 900                 |
| `--dwc-font-weight`             | `var(--dwc-font-weight-normal)` |

<dwc-doc-font-weights></dwc-doc-font-weights>

## Regelhoogte {#line-height}

De lijnhoogte CSS eigenschap stelt de hoogte van een lijnbox in. Het wordt vaak gebruikt om de afstand tussen regels tekst in te stellen.

### Voorbeeld {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Variabelen {#variables-2}

| **Variabele**                   | **Standaardwaarde**       |
| --------------------------------| ------------------------- |
| `--dwc-font-line-height-3xs`    | 1                         |
| `--dwc-font-line-height-2xs`    | 1.1                       |
| `--dwc-font-line-height-xs`     | 1.25                      |
| `--dwc-font-line-height-s`      | 1.375                     |
| `--dwc-font-line-height-m`      | 1.5                       |
| `--dwc-font-line-height-l`      | 1.625                     |
| `--dwc-font-line-height-xl`     | 1.75                      |
| `--dwc-font-line-height-2xl`    | 1.875                     |
| `--dwc-font-line-height-3xl`    | 2                         |
| `--dwc-font-line-height`        | var(--dwc-font-line-height-xs) |

<dwc-doc-line-heights></dwc-doc-line-heights>
