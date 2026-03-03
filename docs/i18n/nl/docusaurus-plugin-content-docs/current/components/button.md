---
title: Button
sidebar_position: 15
_i18n_hash: 6c3425f6d7138e710c5222d2baf84644
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Een `Button` is een klikbare element dat een actie activeert wanneer erop gedrukt wordt. Het kan tekst, iconen of een combinatie van beiden weergeven. Knoppen ondersteunen meerdere visuele thema's en formaten, en kunnen worden uitgeschakeld om interactie te voorkomen tijdens langdurige operaties of wanneer aan bepaalde voorwaarden niet is voldaan.

<!-- INTRO_END -->

## Usages {#usages}

De `Button` klasse is een veelzijdig component dat vaak wordt gebruikt in verschillende situaties waarin gebruikersinteracties en -acties moeten worden geactiveerd. Hier zijn enkele typische scenario's waarin je een knop in je applicatie nodig zou kunnen hebben:

1. **Formulierindiening**: Knoppen worden vaak gebruikt om formulier gegevens in te dienen. Bijvoorbeeld, in een applicatie kun je gebruiken:

  > - Een "Verzenden" knop om gegevens naar de server te sturen
  > - Een "Wis" knop om informatie die al in het formulier staat te verwijderen


2. **Gebruikersacties**: Knoppen worden gebruikt om gebruikers in staat te stellen specifieke acties binnen de applicatie uit te voeren. Bijvoorbeeld, je kunt een knop hebben gelabeld:

  > - "Verwijderen" om de verwijdering van een geselecteerd item te initiëren
  > - "Opslaan" om wijzigingen aan een document of pagina op te slaan.

3. **Bevestigingsdialogen**: Knoppen worden vaak opgenomen in [`Dialog`](../components/dialog) componenten die voor verschillende doeleinden zijn gebouwd om opties voor gebruikers te bieden om een actie te bevestigen of te annuleren, of andere functionaliteiten die zijn ingebouwd in de [`Dialog`](../components/dialog) die je gebruikmaakt.

4. **Interactie Triggers**: Knoppen kunnen dienen als triggers voor interacties of evenementen binnen de applicatie. Door op een knop te klikken, kunnen gebruikers complexe acties initiëren of animaties activeren, inhoud vernieuwen of de weergave bijwerken.

5. **Navigatie**: Knoppen kunnen worden gebruikt voor navigatiedoeleinden, zoals het verplaatsen tussen verschillende secties of pagina's binnen een applicatie. Knoppen voor navigatie kunnen omvatten:

  > - "Volgende" - Brengt de gebruiker naar de volgende pagina of sectie van de huidige applicatie of pagina.
  > - "Vorige" - Brengt de gebruiker terug naar de vorige pagina van de applicatie of sectie waarin ze zich bevinden.
  > - "Terug" - Brengt de gebruiker terug naar het eerste deel van de applicatie of pagina waarin ze zich bevinden.
  
Het volgende voorbeeld demonstreert knoppen die worden gebruikt voor formulierindiening en het wissen van invoer:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Iconen toevoegen aan knoppen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Het opnemen van een pictogram in een knop kan het ontwerp van je app aanzienlijk verbeteren, waardoor gebruikers actie-items op het scherm snel kunnen identificeren. De [`Icon`](./icon.md) component biedt een brede selectie van iconen om uit te kiezen.

Door gebruik te maken van de methoden `setPrefixComponent()` en `setSuffixComponent()`, heb je de flexibiliteit om te bepalen of een `Icon` vóór of na de tekst op een knop moet verschijnen. Alternatief kan de `setIcon()` methode worden gebruikt om een `Icon` toe te voegen na de tekst, maar vóór de `suffix` slot van de knop.

<!-- Voeg dit terug in zodra Icon is samengevoegd -->
<!-- Raadpleeg de [Icon component](../components/icon) pagina voor meer informatie over het configureren en aanpassen van iconen. -->

:::tip
Standaard erft een `Icon` het thema en de grootte van de knop.
:::

Hieronder staan voorbeelden van knoppen met tekst aan de linker- en rechterkant, evenals een knop met alleen een pictogram:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

De `Button` component maakt gebruik van namen, die worden gebruikt voor toegankelijkheid. Wanneer een naam niet expliciet is ingesteld, zal het label van de `Button` in plaats daarvan worden gebruikt. Sommige iconen hebben echter geen labels en tonen alleen niet-tekstuele elementen, zoals iconen. In dit geval is het handig om de `setName()` methode te gebruiken om ervoor te zorgen dat de `Button` component die wordt gemaakt voldoet aan de toegankelijkheidsnormen.

## Een knop uitschakelen {#disabling-a-button}

Knopcomponenten, zoals vele andere, kunnen worden uitgeschakeld om aan een gebruiker aan te geven dat een bepaalde actie nog niet of niet meer beschikbaar is. Een uitgeschakelde knop zal de opaciteit van de knop verlagen en is beschikbaar voor alle knopthema's en -formaten.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Een knop kan op elk moment in de code worden uitgeschakeld met behulp van de <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> functie. Voor extra gemak kan een knop ook worden uitgeschakeld bij klikken met de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> functie.

In sommige applicaties activeert het klikken op een knop een langdurige actie. In de meeste gevallen wil de applicatie ervoor zorgen dat slechts één klik wordt verwerkt. Dit kan een probleem zijn in omgevingen met hoge latentie wanneer de gebruiker de knop meerdere keren klikt voordat de applicatie de kans heeft gehad om te beginnen met het verwerken van de resulterende actie. 

:::tip
Uitschakelen bij klikken helpt niet alleen bij het optimaliseren van de verwerking van acties, maar voorkomt ook dat de ontwikkelaar deze functionaliteit zelf moet implementeren, aangezien deze methode is geoptimaliseerd om rondreizen te verminderen.
:::

## Stijlen {#styling}

### Thema's {#themes}

`Button` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 discrete thema's</JavadocLink> die zijn ingebouwd voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen door de hele applicatie aan te passen. 

Hoewel er veel gebruiksmogelijkheden zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

  - **Danger**: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegegevens.
  - **Default**: Geschikt voor acties door de gehele applicatie die geen speciale aandacht vereisen en generiek zijn, zoals het wisselen van een instelling.
  - **Primary**: Geschikt als een hoofd "call-to-action" op een pagina, zoals zich aanmelden, wijzigingen opslaan of naar een andere pagina gaan.
  - **Success**: Uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het success thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Warning**: Handig om aan te geven dat een gebruiker op het punt staat om een potentieel riskante actie uit te voeren, zoals navigeren van een pagina met niet-opgeslagen wijzigingen. Deze acties hebben vaak minder impact dan die welke het Danger thema zouden gebruiken.
  - **Gray**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina, en niet deel uitmaken van de belangrijkste functionaliteit.
  - **Info**: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

Hieronder staan voorbeeldknoppen met elk van de ondersteunde thema's toegepast: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Uitbreidingen {#expanses}
De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse">Uitbreidingen waarden</JavadocLink> maken snelle styling mogelijk zonder gebruik van CSS. Dit maakt het mogelijk om de afmetingen van de knop te manipuleren zonder deze expliciet in te stellen met enige styling. Naast het vereenvoudigen van styling helpt het ook bij het creëren en onderhouden van uniformiteit in je applicatie. De standaard `Button` uitbreiding is `Expanse.MEDIUM`.

Verschillende formaten zijn vaak geschikt voor verschillende toepassingen:
  - **Grotere** uitbreidingswaarden zijn geschikt voor knoppen die de aandacht moeten trekken, functionaliteit benadrukken of integraal zijn voor de kernfunctionaliteit van een applicatie of pagina.
  - **Medium** uitbreiding knoppen, de standaardgrootte, moeten worden gebruikt als een "standaardgrootte", wanneer het gedrag van een knop niet meer of minder belangrijk is dan andere soortgelijke componenten.
  - **Kleinere** uitbreidingswaarden moeten worden gebruikt voor knoppen die geen integraal gedrag in de applicatie hebben, en een meer aanvullende of utilitaire rol vervullen, in plaats van een belangrijke rol in gebruikersinteractie te spelen. Dit omvat `Button` componenten die alleen met iconen voor utilitaire doeleinden worden gebruikt.

Hieronder staan de verschillende uitbreidingen die voor de `Button` component worden ondersteund: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Button` component, overweeg de volgende best practices:

1. **Duidelijke Tekst**: Gebruik duidelijke en beknopte tekst binnen je `Button` component om een duidelijke indicatie van zijn doelstellingen te geven.

2. **Geschikte Visuele Styling**: Overweeg de visuele styling en het thema van de `Button` om consistentie met het ontwerp van je applicatie te waarborgen. Bijvoorbeeld:
  > - Een "Annuleer" `Button` component moet worden gestileerd met het juiste thema of CSS-styling om ervoor te zorgen dat gebruikers zeker zijn dat ze een actie willen annuleren.
  > - Een "Bevestigen" `Button` zou een andere styling hebben dan een "Annuleer" knop, maar zou op dezelfde manier opvallen om ervoor te zorgen dat gebruikers weten dat dit een speciale actie is.

3. **Efficiënte Evenementafhandeling**: Behandel `Button` evenementen efficiënt en bied geschikte feedback aan gebruikers. Raadpleeg [Evenementen](../building-ui/events) voor een overzicht van efficiënte evenement toegevoegde gedragingen.

4. **Testen en Toegankelijkheid**: Test het gedrag van de knop in verschillende scenario's, zoals wanneer deze is uitgeschakeld of focus ontvangt, om een soepele gebruikerservaring te waarborgen.
Volg toegankelijkheidsrichtlijnen om de `Button` bruikbaar te maken voor alle gebruikers, inclusief degenen die afhankelijk zijn van hulpprogramma's.
