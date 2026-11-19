---
title: 'Webswing met webforJ: een Swing-app stap voor stap moderniseren'
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Gebruik Webswing om een Swing-, JavaFX- of SWT-app in de browser uit te
  voeren, in een webforJ-app te integreren en stap voor stap te vervangen door
  webschermen.
_i18n_title_hash: 698c1a9e98e2ef808c204b0bc4be4669
_i18n_description_hash: 3bd3bdba7583bc1699e8d6153577eb56
_i18n_hash: 53117d208e8a0b0a2e46705f9edd1cdc
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing voert een Swing-, JavaFX- of SWT-app in de browser uit zonder wijzigingen in de broncode. Met webforJ sluit je die app in een webapp in en vervang je deze scherm voor scherm. Om berichten tussen beide uit te wisselen, voeg je de Webswing-API toe aan de Swing-app.

Webswing is een webservertechnologie. De desktopapp wordt op de server weergegeven en de interface wordt met HTML5-canvas naar de browser gestreamd, waarbij alle gebruikersinteracties transparant worden afgehandeld.

Zie [Swing moderniseren](/modernization) voor een overzicht van het volledige traject: van de desktopapp in de browser uitvoeren tot deze uitfaseren.

## Wat Webswing oplost {#what-webswing-solves}

Veel organisaties hebben flink geïnvesteerd in Java-desktopapplicaties met essentiële bedrijfslogica die in de loop van jaren of decennia is ontwikkeld. Deze applicaties zijn vaak niet eenvoudig te herschrijven vanwege:

- Complexe domeinlogica die riskant zou zijn om opnieuw te maken
- Integratie met desktopspecifieke bibliotheken of hardware
- De tijd en kosten van een volledige herschrijving
- De noodzaak om de bestaande app qua functionaliteit te evenaren

Met Webswing zijn deze applicaties zonder aanpassingen toegankelijk via het web. Ze behouden hun oorspronkelijke functies en uiterlijk.

## Integratie met webforJ {#integration-with-webforj}

De Webswing-integratie van webforJ biedt de component `WebswingConnector`, waarmee je applicaties die door Webswing worden gehost rechtstreeks in je webforJ-app kunt insluiten. Dit biedt mogelijkheden voor:

### Stapsgewijze modernisering {#progressive-modernization}

In plaats van alles in één keer te herschrijven, kun je:

1. Beginnen met het insluiten van je bestaande Swing-app via `WebswingConnector`
2. Nieuwe functies in webforJ bouwen rond de ingesloten app
3. Swing-componenten geleidelijk vervangen door equivalenten in webforJ
4. De verouderde app uiteindelijk volledig uitfaseren

### Hybride applicaties {#hybrid-applications}

Combineer een moderne webinterface die met webforJ is gebouwd met gespecialiseerde desktopfuncties:

- Gebruik webforJ voor gebruikersinterfaces, dashboards en rapporten
- Behoud Swing voor complexe visualisaties of gespecialiseerde editors
- Zorg voor één geïntegreerde app-ervaring

:::tip Swing-tegenhangers vervangen
Raadpleeg [Overstappen vanaf Swing](/docs/introduction/coming-from-swing) voor vergelijkingen tussen Swing-componenten en hun equivalenten in webforJ. De vergelijkingen bevatten relevante verwijzingen om je te helpen de functies van elk equivalent in webforJ te gebruiken.
:::

## Hoe het werkt {#how-it-works}

De integratie werkt via drie lagen:

1. **Webswing Server**: voert je Java-desktopapp uit, legt de visuele uitvoer vast en verwerkt gebruikersinvoer
2. **WebswingConnector Component**: een webforJ-component die de Webswing-client insluit en de verbinding en communicatie met de server beheert
3. **Communicatieprotocol**: tweerichtingsberichtenuitwisseling waarmee je webforJ-app opdrachten naar de Swing-app kan sturen en gebeurtenissen kan ontvangen

Wanneer een gebruiker je webforJ-app opent, brengt `WebswingConnector` een verbinding met de Webswing-server tot stand. De server maakt een app-instantie aan of maakt er opnieuw verbinding mee en begint de visuele status naar de browser te streamen. Gebruikersinteracties (muis, toetsenbord) worden vastgelegd en naar de server gestuurd, waar ze opnieuw worden afgespeeld in de daadwerkelijke Swing-app.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
