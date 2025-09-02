---
sidebar_position: 8
title: Surfaces
_i18n_hash: ac1f587cd1039f9bf083c610c29c27b9
---
On kolme tasoa pintoja, joita käytetään UI-hierarkian järjestämiseen, usein yhdistettynä [varjoihin](./shadows). Kaikki [palettivärit](./colors) testataan, jotta ne tarjoavat riittävän kontrastin näitä pintoja vastaan.

### Esimerkki {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Muuttujat {#variables}

| **Muuttuja**      | **Käyttö**                                                              | **Esimerkki**                             |
|-------------------|-------------------------------------------------------------------------|--------------------------------------------|
| `--dwc-surface-1` | Tummenin pinta. Käytetään taustan taustana.                             | <SurfaceBox surface="--dwc-surface-1" /> |
| `--dwc-surface-2` | Käytetään komponenteissa (esim. korteissa).                            | <SurfaceBox surface="--dwc-surface-2" /> |
| `--dwc-surface-3` | Vaalein ja korkeimmalla oleva pinta. Käytetään valikoissa, popovereissa, dialogeissa ... | <SurfaceBox surface="--dwc-surface-3" /> |
