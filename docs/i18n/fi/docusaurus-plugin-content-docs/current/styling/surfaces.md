---
sidebar_position: 8
title: Surfaces
_i18n_hash: 05e79ea41c1483cb30396be9bc096c4f
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

On kolme pinta-aluetta, joita käytetään käyttöliittymän hierarkian järjestämiseen, usein yhdistettynä [varjoihin](./shadows). Kaikki [palettivärit](./colors) on testattu tarjoamaan riittävää kontrastia näitä pintoja vastaan.

### Esimerkki {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Muuttujat {#variables}

| **Muuttuja**      | **Käyttö**                                                             | **Esimerkki**                             |
|-------------------|------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | Tummain pinta. Käytetään rungon taustana.                             | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Käytetään komponenteissa (esim. korteissa).                           | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | Vaalein ja korkein pinta. Käytetään valikoissa, ponnahdusikkunoissa, dialogeissa ... | <SurfaceBox surface="--dwc-surface-3" /> |
