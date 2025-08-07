---
sidebar_position: 4
title: Typography
_i18n_hash: 9ecc89dbb5b06c51ad61a22ddb69ce7d
---
Typografia-tunnukset käytetään ylläpitämään johdonmukaista fonttityylien joukkoa koko sovelluksessasi.

## Fonttiperhe {#font-family}

Fonttiperheominaisuuksia käytetään määrittämään priorisoitu lista fonttiperhenimistä.

Oletusarvoisesti käytetään järjestelmän fonttikekoa:

- `Segoe UI` Windowsissa
- `Roboto` Androidissa ja Chrome OS:ssä
- `San Francisco` macOS:ssä ja iOS:ssä
- Muilla järjestelmillä käytetään varafontteina `Helvetica, Arial`.

Voit soveltaa tai muuttaa fonttiperhettä käyttämällä `--dwc-font-family` -erityisominaisuutta.

### Esimerkki {#example}

```css
:root {
  --dwc-font-family: "Roboto", sans-serif;
}
```

### Muuttujat {#variables}

| **Muuttuja**              | **Oletusarvo**                                                                                                                               | **Esimerkki**                                                           |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-font-family-sans`  | -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol' | <span style={{ fontFamily: "var(--dwc-font-family-sans)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family-mono`  | Menlo, Monaco, 'Courier New', monospace                                                                                                     | <span style={{ fontFamily: "var(--dwc-font-family-mono)" }}>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-family`       | `var(--dwc-font-family-sans)`                                                                                                               | <span style={{ fontFamily: "var(--dwc-font-family)" }}>Sphinx of black quartz, judge my vow.</span>      |

## Fonttikoko {#font-size}

Fonttikoko-ominaisuudet määrittävät joukon valittavia fonttikokoja. `s` on vakio koko, ja sitä käytetään useimmissa komponenteissa oletusarvoisesti. Kaikki fonttikoot on määritelty `em`-yksiköissä.

:::info EM-yksikkö
`em` on suhteellinen pituusyksikkö. Se on suhteessa [fonttikokoon](https://developer.mozilla.org/en-US/docs/Web/CSS/font-size) vanhempana, typografisten ominaisuuksien, kuten fonttikoon, tapauksessa, sekä elementin omaan fonttikoon muihin ominaisuuksiin, kuten [leveys](https://developer.mozilla.org/en-US/docs/Web/CSS/width).
:::

### Esimerkki {#example-1}

```css
.title {
  font-size: var(--dwc-font-size-3xl);
}
```

### Muuttujat {#variables-1}

| **Muuttuja**             | **Oletusarvo**         | **Esimerkki**                                                      |
| ------------------------ | ---------------------- | ------------------------------------------------------------------ |
| `--dwc-font-size-2xs`    | 0.75rem                | <span style={{ fontSize: "var(--dwc-font-size-2xs)" }}>Aa</span> |
| `--dwc-font-size-xs`     | 0.813rem               | <span style={{ fontSize: "var(--dwc-font-size-xs)" }}>Aa</span>  |
| `--dwc-font-size-s`      | 0.875rem               | <span style={{ fontSize: "var(--dwc-font-size-s)" }}>Aa</span>   |
| `--dwc-font-size-m`      | 1rem                   | <span style={{ fontSize: "var(--dwc-font-size-m)" }}>Aa</span>   |
| `--dwc-font-size-l`      | 1.125rem               | <span style={{ fontSize: "var(--dwc-font-size-l)" }}>Aa</span>   |
| `--dwc-font-size-xl`     | 1.375rem               | <span style={{ fontSize: "var(--dwc-font-size-xl)" }}>Aa</span>  |
| `--dwc-font-size-2xl`    | 1.75rem                | <span style={{ fontSize: "var(--dwc-font-size-2xl)" }}>Aa</span> |
| `--dwc-font-size-3xl`    | 2.25rem                | <span style={{ fontSize: "var(--dwc-font-size-3xl)" }}>Aa</span> |
| `--dwc-font-size`        | `var(--dwc-font-size-s)` | <span style={{ fontSize: "var(--dwc-font-size)" }}>Aa</span>     |

## Fonttipaino {#font-weight}

[Fonttipaino](https://developer.mozilla.org/en-US/docs/Web/CSS/font-weight) CSS-ominaisuus asettaa fontin painon (tai paksuuden).

### Esimerkki {#example-2}

```css
p {
  font-weight: var(--dwc-font-weight-semibold);
}
```

| **Muuttuja**                   | **Oletusarvo** | **Esimerkki**                                                             |
| ------------------------------- | --------------- | ------------------------------------------------------------------------- |
| `--dwc-font-weight-lighter`     | 200             | <span style={{ fontWeight: "var(--dwc-font-weight-lighter)" }}>Aa</span>  |
| `--dwc-font-weight-light`       | 300             | <span style={{ fontWeight: "var(--dwc-font-weight-light)" }}>Aa</span>    |
| `--dwc-font-weight-normal`      | 400             | <span style={{ fontWeight: "var(--dwc-font-weight-normal)" }}>Aa</span>   |
| `--dwc-font-weight-semibold`    | 500             | <span style={{ fontWeight: "var(--dwc-font-weight-semibold)" }}>Aa</span> |
| `--dwc-font-weight-bold`        | 700             | <span style={{ fontWeight: "var(--dwc-font-weight-bold)" }}>Aa</span>     |
| `--dwc-font-weight-bolder`      | 800             | <span style={{ fontWeight: "var(--dwc-font-weight-bolder)" }}>Aa</span>   |

## Riviväli {#line-height}

Riviväli CSS-ominaisuus asettaa rivin laatikon korkeuden. Sitä käytetään yleisesti tekstirivien välisen etäisyyden määrittämiseen.

### Esimerkki {#example-3}

```css
p {
  line-height: var(--dwc-font-line-height-m);
}
```

### Muuttujat {#variables-2}

| **Muuttuja**                 | **Oletusarvo**             | **Esimerkki**                                                                                                |
| ---------------------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------ |
| `--dwc-font-line-height-2xs` | 0.95                        | <span style={{ lineHeight: "var(--dwc-font-line-height-2xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height-xs`  | 1.1                         | <span style={{ lineHeight: "var(--dwc-font-line-height-xs)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-s`   | 1.25                        | <span style={{ lineHeight: "var(--dwc-font-line-height-s)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-m`   | 1.375                       | <span style={{ lineHeight: "var(--dwc-font-line-height-m)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-l`   | 1.5                         | <span style={{ lineHeight: "var(--dwc-font-line-height-l)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>   |
| `--dwc-font-line-height-xl`  | 1.75                        | <span style={{ lineHeight: "var(--dwc-font-line-height-xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>  |
| `--dwc-font-line-height-2xl` | 2                           | <span style={{ lineHeight: "var(--dwc-font-line-height-2xl)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span> |
| `--dwc-font-line-height`     | var(--dwc-font-line-height-m) | <span style={{ lineHeight: "var(--dwc-font-line-height)", display: "block" }}>Sphinx of black quartz, judge my vow.<br/>Sphinx of black quartz, judge my vow.</span>     |
