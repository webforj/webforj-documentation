---
sidebar_position: 8
title: Surfaces
_i18n_hash: d26674c84c900aea4d63dad4dca61446
---
import SurfaceBox from '@site/src/components/DWCTheme/SurfaceBox/SurfaceBox';

Er zijn drie niveaus van oppervlakken die worden gebruikt om de UI-hiërarchie te organiseren, vaak gecombineerd met [schaduwen](./shadows). Alle [paletkleuren](./colors) zijn getest om voldoende contrast te bieden tegen deze oppervlakken.

### Voorbeeld {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variabelen {#variables}

| **Variabele**     | **Gebruik**                                                               | **Voorbeeld**                               |
|-------------------|---------------------------------------------------------------------------|---------------------------------------------|
| `--dwc-surface-1` | Het donkerste oppervlak. Gebruikt voor de achtergrond van de body.       | <SurfaceBox surface="--dwc-surface-1" />  |
| `--dwc-surface-2` | Gebruikt voor componenten (bijv. kaarten).                               | <SurfaceBox surface="--dwc-surface-2" />  |
| `--dwc-surface-3` | Het lichtste en hoogste oppervlak. Gebruikt voor menu's, popovers, dialogen ... | <SurfaceBox surface="--dwc-surface-3" />  |
