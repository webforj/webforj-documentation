---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: c7dd349ebf7570b2e34953fce990173d
---
Het tabblad Thema laat je de uitstraling van je app aanpassen terwijl deze draait. Het werkt met de [DWC-ontwerptokens](/docs/styling/css-variables) die je app al gebruikt, zodat één enkele wijziging elke component bereikt die dat token leest in plaats van één regel tegelijk.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Een thema aanpassen {#adjusting-a-theme}

De bedieningselementen zijn gegroepeerd op basis van wat ze beïnvloeden, en bestrijken het palet waaruit de app is opgebouwd, de oppervlakken daarachter, de vorm van de randen en hoeken, de typografie en de spatiëring. Elk bedieningselement legt uit wat het doet, aangezien sommige van hen de leesbaarheid van de app veranderen in plaats van alleen de uitstraling.

Een thema heeft een lichte en een donkere kant. Je kunt een wijziging toepassen op beide of op één, en de app tussen hen omwisselen om de kant te zien waar je aan werkt. Een voorbeeld toont het palet, de oppervlakken, een typevoorbeeld en de statuskleuren samen, zodat je een combinatie kunt zien die op het ene scherm werkt maar op het andere niet, voordat je het opslaat.

![De themabedieningselementen naast de voorbeeldweergave](/img/craftforj/theme/knob-rail.png#rounded-border)

## Een thema opslaan {#saving-a-theme}

Een thema waarmee je werkt, wordt toegepast op de app, maar maakt nog geen deel uit van je project en het herladen van de pagina verworpen het. Opslaan schrijft het in de stylesheet van je app, waar het herstarts overleeft, verschijnt in je diff en met je app meegeleverd wordt.

craftforJ schrijft naar een enkele stylesheet, die het zelf detecteert of die je benoemt in de instellingen van craftforJ. Als dat bestand al een thema bevat, vervangt opslaan het als geheel in plaats van er een tweede bovenop te leggen, en craftforJ vraagt eerst om bevestiging. Als het bestand is gewijzigd nadat craftforJ het heeft gelezen, wordt er niets geschreven en vraagt craftforJ je om opnieuw op te slaan.

Je kunt een thema terugzetten naar de laatst opgeslagen staat, of het volledig uit de stylesheet verwijderen zonder iets anders in het bestand te beïnvloeden.

## Vooraf ingestelde thema's {#preset-themes}

Naast de standaard uitstraling en gevoel heeft craftforJ verschillende thema-voorkeuren om uit te kiezen. Hieronder staat een vergelijking tussen de thema's App Standaard en Portico.

<Tabs>
  <TabItem value="app-default" label="App Standaard" default>
    ![App met het App Standaard-thema](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![App met het Portico-thema](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Uitzetten {#turning-it-off}

Je kunt het opslaan van stijlen voor een app uitschakelen in de instellingen van craftforJ, of het volledig verwijderen met de [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) eigenschap. Met een van beide uitgeschakeld, werkt het tabblad nog steeds en schildert het de draaiende app opnieuw, maar je kunt het resultaat niet opslaan.
