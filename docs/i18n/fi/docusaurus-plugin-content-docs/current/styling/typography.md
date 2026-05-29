---
sidebar_position: 4
title: Typography
_i18n_hash: 7c5f036abf897a890cad14af0a64c6bd
---
Typografia-tokeneita käytetään säilyttämään johdonmukainen fontti-estetiikka sovelluksessasi.

## Fonttiperhe {#font-family}

Fonttiperheominaisuudet määrittävät priorisoidun luettelon fonttiperhenimistä.

Järjestelmän fonttipinoa käytetään oletuksena `system-ui`, joka automaattisesti ratkaisee alustan natiivin fontin:

- `San Francisco` macOS:llä ja iOS:llä
- `Segoe UI` Windowsissa
- `Roboto` Androidissa ja Chrome OS:ssä

Voit soveltaa tai muuttaa fonttiperhettä käyttämällä `--dwc-font-family` mukautettua ominaisuutta.

### Esimerkki {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Muuttujat {#variables}

| **Muuttuja**                  | **Oletusarvo** |
| ----------------------------- | ----------------- |
| `--dwc-font-family-sans`     | system-ui, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji' |
| `--dwc-font-family-mono`     | ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace |
| `--dwc-font-family`          | `var(--dwc-font-family-sans)` |

## Fonttikoko {#font-size}

Fonttikoko-ominaisuudet määrittävät valittavissa olevan fonttikokojen joukon. `m` on vakio koko ja sitä käytetään useimmissa komponenteissa oletuksena. Kaikki fonttikoot on määritelty `rem`-yksikköinä.

:::info REM-yksikkö
`rem` on suhteellinen pituusyksikkö. Se on suhteessa [fonttikokoon](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) juurielementissä (`<html>`), joka oletusarvoisesti on 16px useimmissa selaimissa.
:::

### Esimerkki {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Muuttujat {#variables-1}

| **Muuttuja**               | **Oletusarvo**                 | **Laskettu (16px juurella)** |
| -------------------------- | ------------------------------- | ----------------------------- |
| `--dwc-font-size-3xs`     | 0.625rem                        | 10px                          |
| `--dwc-font-size-2xs`     | 0.6875rem                       | 11px                          |
| `--dwc-font-size-xs`      | 0.75rem                         | 12px                          |
| `--dwc-font-size-s`       | 0.8125rem                       | 13px                          |
| `--dwc-font-size-m`       | 0.875rem                        | 14px                          |
| `--dwc-font-size-l`       | 1rem                            | 16px                          |
| `--dwc-font-size-xl`      | 1.25rem                         | 20px                          |
| `--dwc-font-size-2xl`     | 1.625rem                        | 26px                          |
| `--dwc-font-size-3xl`     | 2.125rem                        | 34px                          |
| `--dwc-font-size`         | `var(--dwc-font-size-m)`       | 14px                          |

<dwc-doc-font-sizes></dwc-doc-font-sizes>

## Fonttipaino {#font-weight}

[Fonttipaino](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS-ominaisuus asettaa fontin painon (tai paksuuden).

### Esimerkki {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Muuttuja**                 | **Oletusarvo** |
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

## Rivikorkeus {#line-height}

Rivikorkeus CSS-ominaisuus asettaa riviboksin korkeuden. Sitä käytetään yleisesti tekstirivien välimatkan asettamiseen.

### Esimerkki {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Muuttujat {#variables-2}

| **Muuttuja**                   | **Oletusarvo**                |
| ------------------------------ | ------------------------------ |
| `--dwc-font-line-height-3xs`   | 1                              |
| `--dwc-font-line-height-2xs`   | 1.1                            |
| `--dwc-font-line-height-xs`    | 1.25                           |
| `--dwc-font-line-height-s`     | 1.375                          |
| `--dwc-font-line-height-m`     | 1.5                            |
| `--dwc-font-line-height-l`     | 1.625                          |
| `--dwc-font-line-height-xl`    | 1.75                           |
| `--dwc-font-line-height-2xl`   | 1.875                          |
| `--dwc-font-line-height-3xl`   | 2                              |
| `--dwc-font-line-height`       | var(--dwc-font-line-height-xs) |

<dwc-doc-line-heights></dwc-doc-line-heights>
