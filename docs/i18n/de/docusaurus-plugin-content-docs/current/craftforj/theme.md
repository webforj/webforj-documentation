---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: c7dd349ebf7570b2e34953fce990173d
---
Die Theme-Registerkarte ermöglicht es Ihnen, das Aussehen Ihrer App während des Betriebs zu ändern. Sie arbeitet mit den [DWC-Design-Token](/docs/styling/css-variables), die Ihre App bereits verwendet, sodass eine einzige Änderung jede Komponente erreicht, die dieses Token liest, anstatt eine Regel nach der anderen zu ändern.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Anpassung eines Themas {#adjusting-a-theme}

Die Steuerelemente sind nach ihren Einflussbereichen gruppiert und decken die Palette ab, aus der die App aufgebaut ist, die Oberflächen dahinter, die Form ihrer Kanten und Ecken, ihre Typografie sowie ihren Abstand. Jedes Steuerelement erklärt, was es tut, da einige von ihnen die Lesbarkeit der App ändern, nicht nur ihr Aussehen.

Ein Thema hat eine helle und eine dunkle Seite. Sie können eine Bearbeitung auf beide Seiten oder nur auf eine anwenden und die App zwischen ihnen umschalten, um die Seite zu sehen, an der Sie arbeiten. Eine Vorschau zeigt die Palette, die Oberflächen, ein Typbeispiel und die Statusfarben zusammen an, sodass Sie eine Kombination erkennen können, die auf einem Bildschirm funktioniert, aber auf einem anderen nicht, bevor Sie sie speichern.

![Die Themensteuerelemente neben der Vorschau](/img/craftforj/theme/knob-rail.png#rounded-border)

## Speichern eines Themas {#saving-a-theme}

Ein Thema, an dem Sie arbeiten, wird auf die App angewendet, gehört jedoch noch nicht zu Ihrem Projekt, und ein Neuladen der Seite verwirft es. Das Speichern schreibt es in das Stylesheet Ihrer App, wo es Überarbeitungen übersteht, in Ihrem Diff angezeigt wird und mit Ihrer App ausgeliefert wird.

craftforJ schreibt in ein einzelnes Stylesheet, das es selbst erkennt oder das Sie in den Einstellungen von craftforJ benennen. Wenn diese Datei bereits ein Thema enthält, ersetzt das Speichern sie als Ganzes, anstatt ein zweites darüber zu legen, und craftforJ bittet Sie, dies zuerst zu bestätigen. Wenn die Datei sich geändert hat, nachdem craftforJ sie gelesen hat, wird nichts geschrieben, und craftforJ fordert Sie auf, erneut zu speichern.

Sie können ein Thema auf den zuletzt gespeicherten Zustand zurücksetzen oder es vollständig aus dem Stylesheet entfernen, ohne andere Inhalte in der Datei zu beeinträchtigen.

## Vorgefertigte Themen {#preset-themes}

Über das standardmäßige Aussehen und Gefühl hinaus bietet craftforJ mehrere Themenvorgaben zur Auswahl. Das folgende zeigt einen Vergleich zwischen den Themen App Standard und Portico.

<Tabs>
  <TabItem value="app-default" label="App Standard" default>
    ![App mit dem App Standard-Thema](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![App mit dem Portico-Thema](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Deaktivieren {#turning-it-off}

Sie können das Speichern von Stilen für eine App in den Einstellungen von craftforJ deaktivieren oder es vollständig mit der [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) Eigenschaft entfernen. Wenn eines von beidem deaktiviert ist, funktioniert die Registerkarte weiterhin und malt die laufende App neu, aber Sie können das Ergebnis nicht speichern.
