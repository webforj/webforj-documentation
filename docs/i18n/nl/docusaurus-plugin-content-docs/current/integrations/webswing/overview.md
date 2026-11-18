---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Integreer bestaande Swing-, JavaFX- of SWT-desktopapps via Webswing in een
  webforJ-interface voor geleidelijke modernisering en hybride ervaringen.
_i18n_title_hash: 2e5d4e6133d5cb0ee82f8b6e37cd0767
_i18n_description_hash: 349a14bb13871c58d909824c9d874837
_i18n_hash: 300c45aa8cc9f51831219f75d74d31b6
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing is een webservertechnologie waarmee Java-desktopapplicaties (Swing, JavaFX, SWT) zonder aanpassingen aan de oorspronkelijke broncode in een webbrowser kunnen worden uitgevoerd. De desktopapplicatie wordt op de server weergegeven en de interface wordt met HTML5-canvas naar de browser gestreamd, terwijl alle gebruikersinteracties transparant worden afgehandeld.

## Wat Webswing oplost {#what-webswing-solves}

Veel organisaties hebben aanzienlijk geïnvesteerd in Java-desktopapplicaties met essentiële bedrijfslogica die in de loop van jaren of decennia is ontwikkeld. Deze applicaties zijn vaak niet eenvoudig te herschrijven vanwege:

- Complexe domeinlogica die riskant zou zijn om opnieuw te implementeren
- Integratie met desktop-specifieke bibliotheken of hardware
- Beperkingen in tijd en kosten voor een volledige herschrijving
- De noodzaak om dezelfde functionaliteit te behouden

Met Webswing kunnen deze applicaties zonder aanpassingen via het web toegankelijk worden gemaakt, met behoud van hun oorspronkelijke functionaliteit en uiterlijk.

## Integratie met webforJ {#integration-with-webforj}

De Webswing-integratie van webforJ biedt het component `WebswingConnector`, waarmee je applicaties die op Webswing worden gehost rechtstreeks in je webforJ-app kunt insluiten. Dit biedt mogelijkheden voor:

### Geleidelijke modernisering {#progressive-modernization}

In plaats van alles in één keer te herschrijven, kun je:

1. Beginnen met het insluiten van je bestaande Swing-app via `WebswingConnector`
2. Nieuwe functies in webforJ bouwen rond de ingesloten app
3. Swing-componenten geleidelijk vervangen door webforJ-equivalenten
4. De verouderde app uiteindelijk volledig uitfaseren

### Hybride applicaties {#hybrid-applications}

Combineer moderne webinterfaces gebouwd met webforJ met gespecialiseerde desktopfunctionaliteit:

- Gebruik webforJ voor gebruikersinterfaces, dashboards en rapporten
- Benut Swing voor complexe visualisaties of gespecialiseerde editors
- Behoud één geïntegreerde app-ervaring

:::tip Swing-equivalenten vervangen
Raadpleeg [Overstappen van Swing](/docs/introduction/coming-from-swing) voor vergelijkingen tussen Swing-componenten en hun webforJ-equivalenten. De vergelijkingen bevatten relevante verwijzingen om je te helpen elke equivalente functie in webforJ te gebruiken.
:::

## Hoe het werkt {#how-it-works}

De integratie werkt via drie lagen:

1. **Webswing Server**: voert je Java-desktopapp uit, legt de visuele uitvoer vast en verwerkt gebruikersinvoer
2. **WebswingConnector-component**: een webforJ-component dat de Webswing-client insluit en de verbinding en communicatie met de server beheert
3. **Communicatieprotocol**: bidirectionele berichtenuitwisseling waarmee je webforJ-app opdrachten naar de Swing-app kan sturen en gebeurtenissen kan ontvangen

Wanneer een gebruiker je webforJ-app opent, maakt `WebswingConnector` verbinding met de Webswing-server. De server maakt een app-instantie aan of maakt opnieuw verbinding met een bestaande instantie, en begint de visuele status naar de browser te streamen. Gebruikersinteracties (muis, toetsenbord) worden vastgelegd en naar de server gestuurd, waar ze opnieuw worden uitgevoerd in de daadwerkelijke Swing-app.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
