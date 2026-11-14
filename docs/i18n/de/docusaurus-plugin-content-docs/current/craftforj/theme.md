---
title: Thema
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: fe089afe4f44e457a98622707bf995e5
---
Über den Tab „Theme“ kannst du das Aussehen deiner App ändern, während sie läuft. Er nutzt die [DWC-Design-Tokens](/docs/styling/css-variables), die deine App bereits verwendet. Eine einzelne Änderung wirkt sich dadurch auf jede Komponente aus, die dieses Token verwendet, statt nur auf eine einzelne Regel.

<VideoPlayer video="craftforj/theme-knobs" width={1920} height={992} title="Theme knobs" />

## Ein Theme anpassen {#adjusting-a-theme}

Die Steuerelemente sind nach ihren Auswirkungen gruppiert. Sie umfassen die Palette, auf der die App aufbaut, die Oberflächen im Hintergrund, die Form der Kanten und Ecken, die Typografie und die Abstände. Jedes Steuerelement erklärt seine Funktion, denn einige beeinflussen die Lesbarkeit der App und nicht nur ihr Aussehen.

Ein Theme hat eine helle und eine dunkle Variante. Du kannst eine Änderung auf beide oder nur auf eine anwenden und zwischen ihnen wechseln, um die Variante zu sehen, die du gerade bearbeitest. Eine Vorschau zeigt die Palette, die Oberflächen, ein Schriftmuster und die Statusfarben gemeinsam. So erkennst du, wenn eine Kombination auf einem Bildschirm funktioniert, auf einem anderen aber nicht – noch bevor du sie speicherst.

![Die Theme-Steuerelemente neben der Vorschau](/img/craftforj/theme/knob-rail.png#rounded-border)

## Ein Theme speichern {#saving-a-theme}

Ein Theme, an dem du gerade arbeitest, wird auf die App angewendet, gehört aber noch nicht zu deinem Projekt. Beim erneuten Laden der Seite gehen die Änderungen verloren. Beim Speichern wird das Theme in das Stylesheet deiner App geschrieben. Dort bleibt es auch nach einem Neustart erhalten, erscheint in deinem Diff und wird mit deiner App ausgeliefert.

craftforJ schreibt in ein einzelnes Stylesheet, das es entweder selbst erkennt oder das du in den craftforJ-Einstellungen angibst. Wenn diese Datei bereits ein Theme enthält, wird es beim Speichern vollständig ersetzt, anstatt ein zweites Theme darüberzulegen. craftforJ bittet dich vorher um eine Bestätigung. Wenn die Datei geändert wurde, seit craftforJ sie eingelesen hat, werden keine Änderungen geschrieben und craftforJ fordert dich auf, erneut zu speichern.

Du kannst ein Theme auf den zuletzt gespeicherten Stand zurücksetzen oder es vollständig aus dem Stylesheet entfernen, ohne andere Inhalte der Datei zu beeinflussen.

## Vordefinierte Themes {#preset-themes}

Zusätzlich zum standardmäßigen Erscheinungsbild bietet craftforJ mehrere vordefinierte Themes zur Auswahl. Der folgende Vergleich zeigt die Themes App Default und Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![App mit dem Theme App Default](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![App mit dem Theme Portico](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Deaktivieren {#turning-it-off}

In den craftforJ-Einstellungen kannst du das Speichern von Styles für eine App deaktivieren oder es mit der Eigenschaft [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) vollständig entfernen. In beiden Fällen funktioniert der Tab weiterhin und aktualisiert das Aussehen der laufenden App, aber du kannst das Ergebnis nicht speichern.
