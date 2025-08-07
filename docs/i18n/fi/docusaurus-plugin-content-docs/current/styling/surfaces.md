---
sidebar_position: 8
title: Surfaces
_i18n_hash: d26674c84c900aea4d63dad4dca61446
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

Käyttöliittymän hierarkian järjestämiseen käytetään kolmea pinnan tasoa, joita usein yhdistetään [varjoihin](./shadows). Kaikki [väripaletin värit](./colors) on testattu tarjoamaan riittävä kontrasti näiden pintojen kanssa.

### Esimerkki {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Muuttujat {#variables}

| **Muuttuja**      | **Käyttö**                                                              | **Esimerkki**                             |
|-------------------|-------------------------------------------------------------------------|-------------------------------------------|
| `--dwc-surface-1` | Tummain pinta. Käytetään taustana.                                     | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Käytetään komponenteissa (esim. korteissa).                            | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | Vaalein ja korkein pinta. Käytetään valikoissa, pomoversseissa, dialogeissa ... | <SurfaceBox surface="--dwc-surface-3" /> |
