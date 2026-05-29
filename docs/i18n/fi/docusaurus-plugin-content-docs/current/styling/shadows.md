---
sidebar_position: 7
title: Shadows
_i18n_hash: 423494230ee54caa83fec778e905871b
---
Varjostusominaisuudet lisäävät varjotehosteita elementin kehän ympärille. Varjostukset merkitsevät käyttöliittymässä päällekkäin olevia kohteita.

Varjostukset sopeutuvat automaattisesti sekä vaaleisiin että tummiin teemoihin, ja ne näyttävät voimakkaammilta tummassa tilassa paremman näkyvyyden saavuttamiseksi.

### Esimerkki {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

### Muuttujat {#variables}

| **Muuttuja**       | **Kuvaus**                           |
|--------------------|---------------------------------------|
| `--dwc-shadow-xs`  | Erittäin pieni varjo (1 kerros)      |
| `--dwc-shadow-s`   | Pieni varjo (2 kerrosta)             |
| `--dwc-shadow-m`   | Keskikokoinen varjo (3 kerrosta, oletus) |
| `--dwc-shadow-l`   | Suuri varjo (4 kerrosta)             |
| `--dwc-shadow-xl`  | Erittäin suuri varjo (5 kerrosta)   |
| `--dwc-shadow-2xl` | Kaksois-erittäin suuri varjo (6 kerrosta) |
| `--dwc-shadow`     | `var(--dwc-shadow-m)`                |

<dwc-doc-shadows></dwc-doc-shadows>
