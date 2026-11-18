---
sidebar_position: 8
title: Oppervlakken
description: >-
  Breng een UI-hiërarchie aan met drie modusafhankelijke DWC-oppervlaktokens
  voor pagina-achtergronden, kaarten en popovers of dialogen.
_i18n_title_hash: d40d1008e1abc156a84c98148422b0b9
_i18n_description_hash: 35f5960e176694545a805c578abc82b8
_i18n_hash: cac300e6e9c10cd9d1da6b266e536c74
---
DWC definieert drie niveaus van oppervlakken die worden gebruikt om de UI-hiërarchie te organiseren, gecombineerd met [schaduwen](./shadows). Alle [paletkleuren](./colors) zijn getest om voldoende contraste te hebben met deze oppervlakken.

Oppervlakken krijgen een subtiele tint van de primaire kleur en passen zich automatisch aan licht- en donkerstanden aan.

### Voorbeeld {#example}

```css
.element {
  background: var(--dwc-surface-2);
}
```

### Variabelen {#variables}

| **Variabele**      | **Gebruik**                          |
|-------------------|------------------------------------|
| `--dwc-surface-1` | Achtergrond van de pagina en het lichaam.         |
| `--dwc-surface-2` | Toolbar, menubalken, kaarten.        |
| `--dwc-surface-3` | Vensters, menu's, popovers, dialogen.|

<dwc-doc-surfaces></dwc-doc-surfaces>
