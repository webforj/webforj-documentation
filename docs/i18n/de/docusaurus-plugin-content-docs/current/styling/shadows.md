---
sidebar_position: 7
title: Schatten
description: >-
  Staffeln Sie Elemente mit der DWC-Box-Shadow-Skala von xs bis 2xl und
  Schatten, die sich automatisch an den Hell- und Dunkelmodus anpassen.
_i18n_title_hash: dde98fa66ef8081029ffb8f24d643c89
_i18n_description_hash: 7df2812ce68443fa138e9f5942ad4039
_i18n_hash: 423494230ee54caa83fec778e905871b
---
Die Schatteneigenschaften fügen Schatteneffekte um den Rahmen eines Elements hinzu. Schatten signalisieren Elemente, die in der Benutzeroberfläche übereinander gestapelt sind.

Schatten passen sich automatisch sowohl im Licht- als auch im Dunkelmodus an und erscheinen im Dunkelmodus stärker für bessere Sichtbarkeit.

### Beispiel {#example}

```css
.element {
  box-shadow: var(--dwc-shadow-xl);
}
```

### Variablen {#variables}

| **Variable**       | **Beschreibung**                     |
|--------------------|---------------------------------------|
| `--dwc-shadow-xs`  | Sehr kleiner Schatten (1 Schicht)    |
| `--dwc-shadow-s`   | Kleiner Schatten (2 Schichten)       |
| `--dwc-shadow-m`   | Mittlerer Schatten (3 Schichten, Standard) |
| `--dwc-shadow-l`   | Großer Schatten (4 Schichten)        |
| `--dwc-shadow-xl`  | Sehr großer Schatten (5 Schichten)   |
| `--dwc-shadow-2xl` | Doppelter sehr großer Schatten (6 Schichten) |
| `--dwc-shadow`     | `var(--dwc-shadow-m)`                |

<dwc-doc-shadows></dwc-doc-shadows>
