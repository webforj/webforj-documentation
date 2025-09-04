---
sidebar_position: 8
title: Surfaces
_i18n_hash: ac1f587cd1039f9bf083c610c29c27b9
---
Er zijn drie niveaus van oppervlakken die worden gebruikt om de UI-hiërarchie te organiseren, vaak gecombineerd met [schaduwen](./shadows). Alle [paletkleuren](./colors) zijn getest om voldoende contrast te bieden met deze oppervlakken.

### Voorbeeld {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variabelen {#variables}

| **Variabele**     | **Gebruik**                                                               | **Voorbeeld**                               |
|-------------------|---------------------------------------------------------------------------|---------------------------------------------|
| `--dwc-surface-1` | Het donkerste oppervlak. Gebruikt voor achtergrond van de body.          | <SurfaceBox surface="--dwc-surface-1" />  |
| `--dwc-surface-2` | Gebruikt voor componenten (bijv. kaarten).                              | <SurfaceBox surface="--dwc-surface-2" />  |
| `--dwc-surface-3` | Het lichtste en hoogste oppervlak. Gebruikt voor menu's, pop-overs, dialoogvensters ... | <SurfaceBox surface="--dwc-surface-3" />  |
