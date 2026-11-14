---
title: Thema
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: fe089afe4f44e457a98622707bf995e5
---
Op het tabblad Theme kun je tijdens het uitvoeren aanpassen hoe je app eruitziet. Het werkt met de [DWC-ontwerptokens](/docs/styling/css-variables) die je app al gebruikt, zodat één wijziging van toepassing is op elk component dat die token gebruikt, in plaats van op één regel tegelijk.

<VideoPlayer video="craftforj/theme-knobs" width={1920} height={992} title="Theme knobs" />

## Een thema aanpassen {#adjusting-a-theme}

De bedieningselementen zijn gegroepeerd op basis van hun effect. Ze omvatten het palet waarop de app is gebaseerd, de oppervlakken erachter, de vorm van de randen en hoeken, de typografie en de tussenruimte. Elk bedieningselement legt uit wat het doet, omdat sommige ervan de leesbaarheid van de app beïnvloeden en niet alleen het uiterlijk.

Een thema heeft een lichte en een donkere variant. Je kunt een wijziging op beide varianten toepassen of op één ervan, en de app tussen beide varianten schakelen om te zien aan welke variant je werkt. In een voorbeeldweergave zie je het palet, de oppervlakken, een typografievoorbeeld en de statuskleuren bij elkaar. Zo kun je zien of een combinatie op het ene scherm werkt, maar niet op het andere, voordat je het thema opslaat.

![De themabedieningselementen naast de voorbeeldweergave](/img/craftforj/theme/knob-rail.png#rounded-border)

## Een thema opslaan {#saving-a-theme}

Een thema waaraan je werkt, is toegepast op de app, maar maakt nog geen deel uit van je project. Als je de pagina opnieuw laadt, gaat het verloren. Als je het opslaat, wordt het in de stylesheet van je app geschreven. Daar blijft het behouden na herstarts, verschijnt het in je diff en wordt het samen met je app meegeleverd.

craftforJ schrijft naar één stylesheet. Het detecteert deze zelf, of je kunt de stylesheet opgeven in de instellingen van craftforJ. Als dat bestand al een thema bevat, wordt het bij het opslaan volledig vervangen in plaats van dat er een tweede thema bovenop wordt geplaatst. craftforJ vraagt je eerst om bevestiging. Als het bestand is gewijzigd nadat craftforJ het heeft gelezen, wordt er niets geschreven en vraagt craftforJ je om het opnieuw op te slaan.

Je kunt een thema terugzetten naar de laatst opgeslagen staat, of het volledig uit de stylesheet verwijderen zonder iets anders in het bestand te beïnvloeden.

## Vooraf ingestelde thema's {#preset-themes}

Naast de standaarduitstraling heeft craftforJ verschillende vooraf ingestelde thema's waaruit je kunt kiezen. Hieronder zie je een vergelijking tussen de thema's App Default en Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![App met het thema App Default](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![App met het thema Portico](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Uitschakelen {#turning-it-off}

Je kunt het opslaan van stijlen voor een app uitschakelen in de instellingen van craftforJ, of de functie volledig verwijderen met de eigenschap [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags). Als een van beide is uitgeschakeld, blijft het tabblad werken en wordt de actieve app nog steeds opnieuw weergegeven, maar kun je het resultaat niet opslaan.
