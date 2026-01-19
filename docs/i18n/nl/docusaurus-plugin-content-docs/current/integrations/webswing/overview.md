---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 853a4bb057c1a3499c26d4714120170f
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing is een webservertechnologie waarmee Java-desktopapplicaties (Swing, JavaFX, SWT) in een webbrowser kunnen draaien zonder wijzigingen aan de oorspronkelijke broncode. Het rendert de desktopapplicatie op de server en streamt de interface naar de browser met behulp van HTML5-canvas, waarbij alle gebruikersinteracties transparant worden afgehandeld.

## Wat Webswing oplost {#what-webswing-solves}

Veel organisaties hebben aanzienlijke investeringen in Java-desktopapplicaties die cruciale bedrijfslogica bevatten, ontwikkeld over jaren of decennia. Deze applicaties kunnen vaak niet eenvoudig worden herschreven vanwege:

- Complexe domeinlogica die riskant zou zijn om opnieuw te creëren
- Integratie met desktop-specifieke bibliotheken of hardware
- Tijds- en kostenbeperkingen van een volledige herschrijving
- De noodzaak om functionaliteit met bestaande functionaliteiten in overeenstemming te houden

Webswing stelt deze applicaties in staat om toegankelijk te zijn via het web zonder modificatie, waarbij hun oorspronkelijke functionaliteit en uiterlijk behouden blijven.

## Integratie met webforJ {#integration-with-webforj}

De webforJ Webswing-integratie biedt de `WebswingConnector`-component, waarmee je Webswing-gehoste applicaties direct binnen je webforJ-app kunt opnemen. Dit creëert kansen voor:

### Progressieve modernisering {#progressive-modernization}

In plaats van een al-of-niets herschrijving, kun je:

1. Beginnen met het opnemen van je bestaande Swing-app via `WebswingConnector`
2. Nieuwe functies in webforJ bouwen rondom de geïncorporeerde app
3. Gradueel Swing-componenten vervangen door webforJ-equivalenten
4. Uiteindelijke de verouderde app geheel afbouwen

### Hybride applicaties {#hybrid-applications}

Combineer moderne web UI, opgebouwd met webforJ, met gespecialiseerde desktopfunctionaliteit:

- Gebruik webforJ voor gebruikersinterfaces, dashboards en rapporten
- Maak gebruik van Swing voor complexe visualisaties of gespecialiseerde editors
- Behoud een enkele geïntegreerde app-ervaring

## Hoe het werkt {#how-it-works}

De integratie opereert via drie lagen:

1. **Webswing Server**: draait je Java-desktopapplicatie, vangt de visuele output op en verwerkt gebruikersinvoer
2. **WebswingConnector Component**: een webforJ-component die de Webswing-client embed, en het beheer van de verbinding en communicatie met de server verzorgt
3. **Communicatieprotocol**: bidirectionele berichtgeving die het mogelijk maakt voor je webforJ-app om commando's naar de Swing-app te sturen en evenementen terug te ontvangen

Wanneer een gebruiker toegang krijgt tot je webforJ-app, stelt de `WebswingConnector` een verbinding op met de Webswing-server. De server creëert of herverbinding met een app-instantie en begint de visuele staat naar de browser te streamen. Gebruikersinteracties (muisklikken, toetsenbord) worden vastgelegd en naar de server gestuurd, waar ze op de feitelijke Swing-app worden herhaald.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
