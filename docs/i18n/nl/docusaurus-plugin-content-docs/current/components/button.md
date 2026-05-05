---
title: Button
sidebar_position: 15
_i18n_hash: 5e0b4998a50b6c7d935c53c9c11009d6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Een `Button` is een klikbaar element dat een actie activeert wanneer het ingedrukt wordt. Het kan tekst, pictogrammen of een combinatie van beide weergeven. Knoppen ondersteunen meerdere visuele thema's en maten, en kunnen uitgeschakeld worden om interactie te voorkomen tijdens langdurige operaties of wanneer aan bepaalde voorwaarden niet is voldaan.

<!-- INTRO_END -->

## Gebruik {#usages}

De `Button` klasse is een veelzijdig component dat vaak wordt gebruikt in verschillende situaties waar gebruikersinteracties en -acties moeten worden geactiveerd. Hier zijn enkele typische scenario's waarin je een knop in jouw applicatie zou kunnen gebruiken:

1. **Formulierindiening**: Knoppen worden vaak gebruikt om formuliervelden in te dienen. In een applicatie kun je bijvoorbeeld:

  > - Een "Verzend" knop gebruiken om gegevens naar de server te sturen
  > - Een "Wis" knop gebruiken om informatie die al in het formulier is ingevuld te verwijderen

2. **Gebruikersacties**: Knoppen worden gebruikt om gebruikers in staat te stellen specifieke acties binnen de applicatie uit te voeren. Je kunt bijvoorbeeld een knop hebben met het label:

  > - "Verwijderen" om de verwijdering van een geselecteerd item te initiëren
  > - "Opslaan" om wijzigingen die in een document of pagina zijn aangebracht op te slaan.

3. **Bevestigingsdialoogvensters**: Knoppen worden vaak opgenomen in [`Dialog`](../components/dialog) componenten die voor verschillende doeleinden zijn gebouwd om gebruikers opties te bieden om een actie te bevestigen of te annuleren, of andere functionaliteit die is ingebouwd in de [`Dialog`](../components/dialog) die je gebruikt.

4. **Interactie Triggers**: Knoppen kunnen dienen als triggers voor interacties of gebeurtenissen binnen de applicatie. Door op een knop te klikken, kunnen gebruikers complexe acties initiëren of animaties activeren, inhoud vernieuwen of de weergave bijwerken.

5. **Navigatie**: Knoppen kunnen worden gebruikt voor navigatiedoeleinden, zoals het navigeren tussen verschillende secties of pagina's binnen een applicatie. Navigatieknoppen kunnen omvatten:

  > - "Volgende" - Brengt de gebruiker naar de volgende pagina of sectie van de huidige applicatie of pagina.
  > - "Vorige" - Brengt de gebruiker terug naar de vorige pagina van de applicatie of sectie waarin ze zich bevinden.
  > - "Terug" - Brengt de gebruiker terug naar het eerste deel van de applicatie of pagina waarin ze zich bevinden.
  
Het volgende voorbeeld laat knoppen zien die worden gebruikt voor formulierindiening en het wissen van invoer:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Iconen aan knoppen toevoegen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Het incorporeren van een pictogram in een knop kan het ontwerp van je applicatie aanzienlijk verbeteren, waardoor gebruikers snel actie-items op het scherm kunnen identificeren. De [`Icon`](./icon.md) component biedt een brede selectie van iconen om uit te kiezen.

Door gebruik te maken van de methoden `setPrefixComponent()` en `setSuffixComponent()`, heb je de flexibiliteit om te bepalen of een `Icon` voor of na de tekst op een knop moet verschijnen. Alternatief kan de `setIcon()` methode worden gebruikt om een `Icon` toe te voegen na de tekst, maar vóór de `suffix` slot van de knop.

<!-- Voeg dit weer toe zodra Icon is samengevoegd -->
<!-- Zie de [Icon component](../components/icon) pagina voor meer informatie over het configureren en aanpassen van iconen. -->

:::tip
Standaard erft een `Icon` het thema en de omvang van de knop.
:::

Hieronder staan voorbeelden van knoppen met tekst aan de linker- en rechterkant, alsook een knop met alleen een pictogram:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

De `Button` component maakt gebruik van naamgeving, die gebruikt wordt voor toegankelijkheid. Wanneer een naam niet expliciet is ingesteld, wordt het label van de `Button` in plaats daarvan gebruikt. Sommige iconen hebben echter geen labels en tonen alleen niet-tekstuele elementen, zoals pictogrammen. In dit geval is het raadzaam om de `setName()` methode te gebruiken om ervoor te zorgen dat de gemaakte `Button` component voldoet aan de toegankelijkheidsnormen.

## Een knop uitschakelen {#disabling-a-button}

Knopcomponenten, net als vele andere, kunnen worden uitgeschakeld om aan een gebruiker duidelijk te maken dat een bepaalde actie nog niet of niet langer beschikbaar is. Een uitgeschakelde knop vermindert de opaciteit van de knop en is beschikbaar voor alle knopthema's en -afmetingen.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Een knop uitschakelen kan op elk moment in de code worden gedaan met de <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> functie. Voor extra gemak kan een knop ook worden uitgeschakeld wanneer deze wordt ingedrukt met de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> functie.

In sommige gevallen, activeert klikken op een knop een langdurige actie. Het uitschakelen van de knop totdat je applicatie de actie verwerkt, voorkomt dat de gebruiker de knop meerdere keren klikt, vooral in omgevingen met hoge latentie.

:::tip
Uitschakelen bij klikken helpt niet alleen de verwerking van acties te optimaliseren, maar voorkomt ook dat de ontwikkelaar deze functionaliteit zelf moet implementeren, aangezien deze methode is geoptimaliseerd om het aantal communicaties te verminderen.
:::

## Stijlen {#styling}

### Thema's {#themes}

`Button` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 discrete thema's</JavadocLink> die ingebouwd zijn voor snelle styling zonder gebruik te maken van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die kunnen worden toegepast op knoppen om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van knoppen in een applicatie aan te passen.

Hoewel er veel gebruiksscenario's zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

  - **Gevaren**: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/data.
  - **Standaard**: Geschikt voor acties in een applicatie die geen speciale aandacht vereisen en generiek zijn, zoals het in- of uitschakelen van een instelling.
  - **Primair**: Geschikt als belangrijkste "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan, of verdergaan naar een andere pagina.
  - **Succes**: Uitstekend voor het visualiseren van een succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel riskante actie uit te voeren, zoals het navigeren weg van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Gevaren-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina en niet onderdeel zijn van de hoofdfunctionaliteit.
  - **Informatie**: Goed voor het bieden van aanvullende verduidelijkende informatie aan een gebruiker.

Hieronder zie je voorbeeldknoppen met elk van de ondersteunde Thema's toegepast: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
height='175px'
/>

### Afmetingen {#expanses}
De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Afmetingen waarden </JavadocLink> staan snelle styling toe zonder gebruik te maken van CSS. Dit maakt het mogelijk om de afmetingen van de Knop te manipuleren zonder het expliciet in te stellen met styling. Naast het vereenvoudigen van de styling, helpt het ook om uniformiteit in je applicatie te creëren en te behouden. De standaard `Button` afmeting is `Expanse.MEDIUM`.

Verschillende maten zijn vaak geschikt voor verschillende toepassingen:
  - **Grotere** afmetingwaarden zijn geschikt voor knoppen die aandacht moeten trekken, functionaliteit moeten benadrukken of essentieel zijn voor de kernfunctionaliteit van een applicatie of pagina.
  - **Middelgrote** afmeting knoppen, de standaardgrootte, zouden de standaardgrootte van knoppen moeten zijn. Deze functies zouden noch belangrijker, noch minder belangrijk moeten zijn dan soortgelijke componenten.
  - **Kleinere** afmetingwaarden moeten worden gebruikt voor knoppen die geen integrale gedragingen in de applicatie hebben en een meer aanvullende of utilitaire rol vervullen, in plaats van een belangrijke rol in gebruikersinteractie te spelen. Dit omvat `Button` componenten die alleen met pictogrammen worden gebruikt voor utilitaire doeleinden.

Hieronder staan de verschillende afmetingen die worden ondersteund voor de `Button` component: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Button` component, overweeg de volgende best practices:

1. **Juiste Tekst**: Gebruik duidelijke en beknopte tekst voor de tekst binnen je `Button` component om een duidelijke indicatie van het doel te geven.

2. **Geschikte Visuele Styling**: Overweeg de visuele styling en het thema van de `Button` om consistentie met het ontwerp van je applicatie te waarborgen. Bijvoorbeeld:
  > - Een "Annuleer" `Button` component zou moeten worden gestyled met het juiste thema of CSS-styling om ervoor te zorgen dat gebruikers zeker weten dat ze een actie willen annuleren.
  > - Een "Bevestig" `Button` zou een andere styling hebben dan een "Annuleer" knop, maar zou ook opvallen om ervoor te zorgen dat gebruikers weten dat dit een speciale actie is.

3. **Efficiënte Evenementafhandeling**: Behandel `Button` evenementen efficiënt en bied gebruikers de juiste feedback. Raadpleeg [Evenementen](../building-ui/events) om efficiënte gedragingen voor het toevoegen van evenementen te bekijken.

4. **Testen en Toegankelijkheid**: Test het gedrag van de Knop in verschillende scenario's, zoals wanneer deze is uitgeschakeld of focus ontvangt, om een soepele gebruikerservaring te waarborgen. Volg toegankelijkheidsrichtlijnen om de `Button` bruikbaar te maken voor alle gebruikers, inclusief degenen die afhankelijk zijn van ondersteunende technologieën.
