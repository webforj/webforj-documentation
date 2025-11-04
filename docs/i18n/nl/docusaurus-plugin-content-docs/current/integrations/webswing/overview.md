---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: e8f61966c5b7d0745f65f23172dd114a
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing is een webservertechnologie die het mogelijk maakt om Java desktopapplicaties (Swing, JavaFX, SWT) in een webbrowser uit te voeren zonder wijzigingen aan de oorspronkelijke broncode. Het rendert de desktopapp op de server en streamt de interface naar de browser met behulp van HTML5-canvas, waarbij alle gebruikersinteracties transparant worden afgehandeld.

## Wat Webswing oplost

Veel organisaties hebben aanzienlijke investeringen gedaan in Java desktopapplicaties die cruciale bedrijfslogica bevatten die gedurende jaren of decennia is ontwikkeld. Deze applicaties kunnen vaak niet eenvoudig worden herschreven vanwege:

- Complexe domeinlogica die riskant zou zijn om opnieuw te creëren
- Integratie met desktop-specifieke bibliotheken of hardware
- Tijd- en kostenbeperkingen van een volledige herschrijving
- De noodzaak om de functionele gelijkheid met bestaande functionaliteit te behouden

Webswing stelt deze applicaties in staat om webbemonteerbaar te zijn zonder modificatie, terwijl de oorspronkelijke functionaliteit en uitstraling behouden blijven.

## Integratie met webforJ

De webforJ Webswing-integratie biedt de `WebswingConnector` component, waarmee je Webswing-gehoste applicaties direct binnen je webforJ-app kunt embedden. Dit creëert mogelijkheden voor:

### Progressieve modernisering

In plaats van een alles-of-niets herschrijving, kun je:

1. Beginnen met het embedden van je bestaande Swing-app via `WebswingConnector`
2. Nieuwe functies ontwikkelen in webforJ rond de embedded app
3. Geleidelijk Swing-componenten vervangen door webforJ-equivalenten
4. Uiteindelijk de legacy-app volledig afschaffen

### Hybride applicaties

Combineer moderne web-UI gebouwd met webforJ met gespecialiseerde desktopfunctionaliteit:

- Gebruik webforJ voor gebruikersinterfaces, dashboards en rapporten
- Profiteer van Swing voor complexe visualisaties of gespecialiseerde editors
- Behoud een enkele geïntegreerde app-ervaring

## Hoe het werkt

De integratie werkt door drie lagen:

1. **Webswing Server**: draait je Java desktopapp, vangt de visuele uitvoer en verwerkt gebruikersinvoer
2. **WebswingConnector Component**: een webforJ-component die de Webswing-client embedt, de verbinding en communicatie met de server beheert
3. **Communicatieprotocol**: bidirectionele messaging die het mogelijk maakt dat je webforJ-app opdrachten naar de Swing-app kan sturen en evenementen terug kan ontvangen

Wanneer een gebruiker je webforJ-app benadert, stelt de `WebswingConnector` een verbinding op met de Webswing-server. De server maakt of herverbindt met een app-instantie en begint de visuele staat naar de browser te streamen. Gebruikersinteracties (muis, toetsenbord) worden vastgelegd en naar de server gestuurd, waar ze opnieuw worden afgespeeld op de daadwerkelijke Swing-app.

## Topics {#topics}

<DocCardList className="topics-section" />
