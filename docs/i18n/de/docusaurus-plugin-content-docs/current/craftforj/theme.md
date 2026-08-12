---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: 98545075c2ac2777380812af08d71345
---
Der Registerkarte "Theme" können Sie das Aussehen Ihrer Anwendung während der Ausführung ändern. Sie funktioniert mit den [DWC-Design-Token](/docs/styling/css-variables), die Ihre Anwendung bereits verwendet, sodass eine einzelne Änderung jede Komponente erreicht, die dieses Token liest, anstatt eine Regel nach der anderen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4" type="video/mp4" />
  </video>
</div>

## Anpassen eines Themas {#adjusting-a-theme}

Die Steuerelemente sind nach ihrem Einfluss gruppiert und decken die Farbpalette ab, aus der die Anwendung erstellt ist, die Oberflächen dahinter, die Form ihrer Kanten und Ecken, die Typografie und den Abstand. Jedes Steuerelement erklärt, was es tut, da einige von ihnen die Lesbarkeit der Anwendung ändern, nicht nur ihr Aussehen.

Ein Thema hat eine helle und eine dunkle Seite. Sie können eine Änderung auf beiden oder auf einer Seite anwenden und die Anwendung zwischen ihnen umschalten, um die Seite zu sehen, an der Sie arbeiten. Eine Vorschau zeigt die Farbpalette, die Oberflächen, ein Typbeispiel und die Statusfarben zusammen, sodass Sie eine Kombination erkennen können, die auf einem Bildschirm funktioniert, aber auf einem anderen nicht, bevor Sie sie speichern.

![Die Steuerelemente des Themas neben der Vorschau](/img/craftforj/theme/knob-rail.png#rounded-border)

## Speichern eines Themas {#saving-a-theme}

Ein Thema, an dem Sie arbeiten, wird auf die Anwendung angewendet, gehört jedoch noch nicht zu Ihrem Projekt, und das Neuladen der Seite verwirft es. Das Speichern schreibt es in das Stylesheet Ihrer Anwendung, wo es Neustarts übersteht, in Ihrem Diff erscheint und mit Ihrer Anwendung ausgeliefert wird.

craftforJ schreibt in ein einzelnes Stylesheet, das es selbst erkennt oder das Sie in den craftforJ-Einstellungen benennen. Wenn diese Datei bereits ein Thema enthält, ersetzt das Speichern das gesamte Thema, anstatt ein zweites oben draufzulegen, und craftforJ bittet Sie zuerst um Bestätigung. Wenn sich die Datei geändert hat, nachdem craftforJ sie gelesen hat, wird nichts geschrieben und craftforJ bittet Sie, erneut zu speichern.

Sie können ein Thema auf den zuletzt gespeicherten Zustand zurücksetzen oder es vollständig aus dem Stylesheet entfernen, ohne etwas anderes in der Datei zu beeinflussen.

## Vorgestellte Themen {#preset-themes}

Über das standardmäßige Aussehen und das Gefühl hinaus bietet craftforJ mehrere Themenvorgaben zur Auswahl. Im Folgenden wird ein Vergleich zwischen den Themen "App Default" und "Portico" gezeigt.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![App mit dem Thema "App Default"](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![App mit dem Thema "Portico"](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Deaktivieren {#turning-it-off}

Sie können das Speichern von Stilen für eine Anwendung in den craftforJ-Einstellungen deaktivieren oder es vollständig mit der [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) Eigenschaft entfernen. Wenn eines von beiden deaktiviert ist, funktioniert die Registerkarte weiterhin und malt die laufende Anwendung neu, aber Sie können das Ergebnis nicht speichern.
