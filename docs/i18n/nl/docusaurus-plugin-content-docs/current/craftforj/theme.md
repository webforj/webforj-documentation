---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: 98545075c2ac2777380812af08d71345
---
Het tabblad Thema laat je veranderen hoe je app eruit ziet terwijl deze draait. Het werkt met de [DWC-ontwerptokens](/docs/styling/css-variables) die je app al gebruikt, zodat een enkele wijziging elke component bereikt die dat token leest in plaats van één regel tegelijk.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4" type="video/mp4" />
  </video>
</div>

## Een thema aanpassen {#adjusting-a-theme}

De bedieningselementen zijn gegroepeerd op basis van wat ze beïnvloeden, waaronder het palet waaruit de app is opgebouwd, de oppervlakken erachter, de vorm van zijn randen en hoeken, de typografie en de ruimte. Elk bedieningselement legt uit wat het doet, aangezien sommige ervan de leesbaarheid van de app veranderen in plaats van alleen hoe het eruit ziet.

Een thema heeft een lichte en een donkere kant. Je kunt een wijziging op beide of op één toepassen en de app ertussen schakelen om de kant te zien waaraan je werkt. Een voorbeeld toont het palet, de oppervlakken, een typemonster en de statuskleuren samen, zodat je een combinatie kunt spotten die op het ene scherm werkt maar niet op het andere voordat je het opslaat.

![De thema-controles naast de preview](/img/craftforj/theme/knob-rail.png#rounded-border)

## Een thema opslaan {#saving-a-theme}

Een thema waaraan je werkt, is toegepast op de app maar maakt nog geen deel uit van je project, en het opnieuw laden van de pagina vervalt deze. Opslaan schrijft het in de stylesheet van je app, waar het restarts overleeft, verschijnt in je diff en meegeleverd wordt met je app.

craftforJ schrijft naar een enkele stylesheet, die het zelf detecteert of die je benoemt in de instellingen van craftforJ. Als dat bestand al een thema bevat, vervangt opslaan het als geheel in plaats van een tweede bovenop te leggen, en vraagt craftforJ je eerst om bevestiging. Als het bestand is gewijzigd nadat craftforJ het heeft gelezen, wordt er niets geschreven en vraagt craftforJ je om opnieuw op te slaan.

Je kunt een thema terugzetten naar de laatst opgeslagen staat, of het volledig uit de stylesheet verwijderen zonder iets anders in het bestand te beïnvloeden.

## Vooraf ingestelde thema's {#preset-themes}

Naast de standaard uitstraling heeft craftforJ verschillende standaardthema's om uit te kiezen. Het volgende toont een vergelijking tussen de thema's App Standaard en de Portico.

<Tabs>
  <TabItem value="app-default" label="App Standaard" default>
    ![App met het App Standaard-thema](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![App met het Portico-thema](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Uitzetten {#turning-it-off}

Je kunt het opslaan van stijlen voor een app in de instellingen van craftforJ uitzetten, of het volledig verwijderen met de [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) eigenschap. Als een van beide is uitgeschakeld, werkt het tabblad nog steeds en schildert het de draaiende app opnieuw, maar je kunt het resultaat niet opslaan.
