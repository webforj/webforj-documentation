---
sidebar_position: 7
title: Shadows
_i18n_hash: 423494230ee54caa83fec778e905871b
---
De schaduweigenschappen voegen schaduweffecten toe rond het frame van een element. Schaduwen duiden op items die in de gebruikersinterface bovenop elkaar zijn gestapeld.

Schaduwen passen zich automatisch aan zowel lichte als donkere modus aan, en verschijnen sterker in de donkere modus voor betere zichtbaarheid.

### Voorbeeld {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

### Variabelen {#variables}

| **Variabele**      | **Beschrijving**                      |
|--------------------|---------------------------------------|
| `--dwc-shadow-xs`  | Extra kleine schaduw (1 laag)        |
| `--dwc-shadow-s`   | Kleine schaduw (2 lagen)             |
| `--dwc-shadow-m`   | Middelgrote schaduw (3 lagen, standaard) |
| `--dwc-shadow-l`   | Grote schaduw (4 lagen)              |
| `--dwc-shadow-xl`  | Extra grote schaduw (5 lagen)        |
| `--dwc-shadow-2xl` | Dubbel extra grote schaduw (6 lagen) |
| `--dwc-shadow`     | `var(--dwc-shadow-m)`                |

<dwc-doc-shadows></dwc-doc-shadows>
