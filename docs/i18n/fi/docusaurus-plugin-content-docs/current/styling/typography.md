---
sidebar_position: 4
title: Typography
_i18n_hash: 5eafa3dea127703b4f573da438cbaf57
---
Typografia-tunnukset käytetään yhtenäisten fonttityylien ylläpitämiseen sovelluksessasi.

## Fonttiperhe {#font-family}

Fonttiperheominaisuuksia käytetään määrittämään priorisoitu luettelo fonttiperhenimistä.

Järjestelmän fonttipinoa käytetään oletuksena:

- `Segoe UI` Windowsissa
- `Roboto` Androidissa ja Chrome OS:ssä
- `San Francisco` macOS:ssa ja iOS:ssa
- Muilla järjestelmillä käytetään varafontteina `Helvetica, Arial`.

Voit soveltaa tai muuttaa fonttiperhettä käyttämällä `--dwc-font-family` -muokattavaa ominaisuutta.

### Esimerkki {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Muuttujat {#variables}

| **Muuttuja**               | **Oletusarvo**                                                                                                                                 | **Esimerkki**                                                         |
| -------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------- |
| `--dwc-font-family-sans`   | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Mustan kvartsin sfinksi, tuomitse vanni.</span> |
| `--dwc-font-family-mono`   | Menlo, Monaco, 'Courier New', monospace                                                                                                       | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Mustan kvartsin sfinksi, tuomitse vanni.</span> |
| `--dwc-font-family`        | `var(--dwc-font-family-sans)`                                                                                                                 | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Mustan kvartsin sfinksi, tuomitse vanni.</span>    |

## Fonttikoko {#font-size}

Fonttikoko-ominaisuudet määrittävät joukon fonttikokoja valittavaksi. `s` on normaali koko ja sitä käytetään useimmissa komponenteissa oletuksena. Kaikki fonttikoot on määritetty `em`-yksiköissä.

:::info EM-yksikkö
`em` on suhteellinen pituusyksikkö. Se on suhteellinen vanhemman [fonttikoon](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) osalta typografisissa ominaisuuksissa, kuten font-size, ja elementin oman fonttikoon osalta muissa ominaisuuksissa, kuten [leveys](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Esimerkki {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Muuttujat {#variables-1}

| **Muuttuja**                | **Oletusarvo**        | **Esimerkki**                                                      |
| --------------------------- | --------------------- | ------------------------------------------------------------------ |
| `--dwc-font-size-2xs`      | 0.75rem               | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`       | 0.813rem              | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`        | 0.875rem              | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`        | 1rem                  | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`        | 1.125rem              | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`       | 1.375rem              | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl`      | 1.75rem               | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl`      | 2.25rem               | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`          | `var(--dwc-font-size-s)` | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## Fonttipaino {#font-weight}

[Fonttipaino](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS-ominaisuus asettaa fontin painon (tai paksuuden).

### Esimerkki {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Muuttuja**                 | **Oletusarvo** | **Esimerkki**                                                               |
| ---------------------------- | --------------- | ------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter`  | 200             | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`    | 300             | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`   | 400             | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold` | 500             | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`     | 700             | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`   | 800             | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Rivikorkeus {#line-height}

Rivikorkeus CSS-ominaisuus asettaa riviboksin korkeuden. Sitä käytetään yleisesti tekstirivien välin asettamiseen.

### Esimerkki {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Muuttujat {#variables-2}

| **Muuttuja**                 | **Oletusarvo**             | **Esimerkki**                                                                                                |
| ---------------------------- | --------------------------- | ---------------------------------------------------------------------------------------------------------- |
| `--dwc-font-line-height-2xs` | 0.95                        | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span> |
| `--dwc-font-line-height-xs`  | 1.1                         | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span>  |
| `--dwc-font-line-height-s`   | 1.25                        | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span>   |
| `--dwc-font-line-height-m`   | 1.375                       | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span>   |
| `--dwc-font-line-height-l`   | 1.5                         | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span>   |
| `--dwc-font-line-height-xl`  | 1.75                        | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span>  |
| `--dwc-font-line-height-2xl` | 2                           | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span> |
| `--dwc-font-line-height`     | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Mustan kvartsin sfinksi, tuomitse vanni.<br/>Mustan kvartsin sfinksi, tuomitse vanni.</span>     |
