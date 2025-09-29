---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 8dcd8fdee2734f6b4b243b0ea82fa1c2
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing is een webservertechnologie die het mogelijk maakt om Java-desktopapplicaties (Swing, JavaFX, SWT) in een webbrowser uit te voeren zonder wijzigingen aan de oorspronkelijke broncode. Het rendert de desktopapp op de server en streamt de interface naar de browser met behulp van HTML5-canvas, waarbij alle gebruikersinteracties transparant worden afgehandeld.

## Wat Webswing oplost

Veel organisaties hebben aanzienlijke investeringen in Java-desktopapplicaties die cruciale bedrijfslogica bevatten die gedurende jaren of decennia is ontwikkeld. Deze applicaties kunnen vaak niet eenvoudig worden herschreven vanwege:

- Complexe domeinlogica die riskant zou zijn om opnieuw te creëren
- Integratie met desktop-specifieke bibliotheken of hardware
- Tijd- en kostenbeperkingen van een volledige herschrijving
- Behoefte om functionaliteit gelijk te houden met bestaande mogelijkheden

Webswing stelt deze applicaties in staat om web-toegankelijk te zijn zonder wijzigingen, waardoor hun oorspronkelijke functionaliteit en uiterlijk behouden blijven.

## Integratie met webforJ

De webforJ Webswing-integratie biedt de `WebswingConnector`-component, waarmee je Webswing-gehoste applicaties rechtstreeks in je webforJ-app kunt insluiten. Dit creëert kansen voor:

### Progressieve modernisering

In plaats van een alles-of-niets herschrijving, kun je:

1. Beginnen met het insluiten van je bestaande Swing-app via `WebswingConnector`
2. Nieuwe functies in webforJ bouwen rondom de ingesloten app
3. Geleidelijk Swing-componenten vervangen door webforJ-equivalenten
4. Uiteindelijk de legacy-app volledig afbouwen

### Hybride applicaties

Combineer moderne web-UI gebouwd met webforJ met gespecialiseerde desktopfunctionaliteit:

- Gebruik webforJ voor gebruikersinterface, dashboards en rapporten
- Maak gebruik van Swing voor complexe visualisaties of gespecialiseerde editors
- Behoud een enkele geïntegreerde app-ervaring

## Hoe het werkt

De integratie werkt via drie lagen:

1. **Webswing Server**: draait je Java-desktopapp, legt de visuele output vast en verwerkt gebruikersinvoer
2. **WebswingConnector Component**: een webforJ-component die de Webswing-client insluit, en de verbinding en communicatie met de server beheert
3. **Communicatieprotocol**: bidirectionele messaging die je webforJ-app in staat stelt om commando's naar de Swing-app te sturen en evenementen terug te ontvangen

Wanneer een gebruiker toegang heeft tot je webforJ-app, stelt de `WebswingConnector` een verbinding met de Webswing-server in. De server maakt of maakt opnieuw verbinding met een app-instantie en begint de visuele staat naar de browser te streamen. Gebruikersinteracties (muis, toetsenbord) worden vastgelegd en naar de server gestuurd, waar ze opnieuw worden afgespeeld op de daadwerkelijke Swing-app.

## Topics {#topics}

<DocCardList className="topics-section" />
