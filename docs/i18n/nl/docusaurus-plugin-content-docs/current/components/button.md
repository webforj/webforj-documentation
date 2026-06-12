---
title: Button
sidebar_position: 15
_i18n_hash: 4f84dd4c618dafe32cbeb47c7dcbbe36
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Een `Button` is een klikbare element dat een actie activeert wanneer het wordt ingedrukt. Het kan tekst, iconen of een combinatie van beide weergeven. Knoppen ondersteunen meerdere visuele thema's en maten, en kunnen worden uitgeschakeld om interactie te voorkomen tijdens langdurige bewerkingen of wanneer aan bepaalde voorwaarden niet is voldaan.

<!-- INTRO_END -->

## Gebruik {#usages}

De `Button`-klasse is een veelzijdig component dat vaak wordt gebruikt in verschillende situaties waar gebruikersinteracties en acties moeten worden geactiveerd. Hier zijn enkele typische scenario's waarin je misschien een knop in je toepassing nodig hebt:

1. **Formulierindiening**: Knoppen worden vaak gebruikt om formuliergegevens in te dienen. Bijvoorbeeld, in een applicatie kun je gebruiken:

  > - Een "Verzend" knop om gegevens naar de server te sturen
  > - Een "Wis" knop om alle informatie die al in het formulier staat te verwijderen


2. **Gebruikersacties**: Knoppen worden gebruikt om gebruikers in staat te stellen specifieke acties binnen de applicatie uit te voeren. Bijvoorbeeld, je kunt een knop met het label hebben:

  > - "Verwijder" om de verwijdering van een geselecteerd item te initiëren
  > - "Bewaar" om wijzigingen die aan een document of pagina zijn aangebracht op te slaan.

3. **Bevestigingsdialoogvensters**: Knoppen worden vaak opgenomen in [`Dialog`](../components/dialog) componenten die voor verschillende doeleinden zijn gebouwd om gebruikers opties te bieden om een actie te bevestigen of te annuleren, of enige andere functionaliteit die is ingebouwd in de [`Dialog`](../components/dialog) die je gebruikt.

4. **Interactietriggers**: Knoppen kunnen dienen als triggers voor interacties of gebeurtenissen binnen de applicatie. Door op een knop te klikken, kunnen gebruikers complexe acties initiëren of animaties, vernieuwen van inhoud, of het bijwerken van de weergave activeren.

5. **Navigatie**: Knoppen kunnen worden gebruikt voor navigatiedoeleinden, zoals bewegen tussen verschillende secties of pagina's binnen een applicatie. Navigatieknoppen kunnen omvatten:

  > - "Volgende" - Brengt de gebruiker naar de volgende pagina of sectie van de huidige applicatie of pagina.
  > - "Vorige" - Brengt de gebruiker terug naar de vorige pagina van de applicatie of sectie waarin ze zich bevinden.
  > - "Terug" - Brengt de gebruiker terug naar het eerste deel van de applicatie of pagina waarin ze zich bevinden.
  
Het volgende voorbeeld toont knoppen die worden gebruikt voor formulierindiening en het wissen van invoer:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Iconen aan knoppen toevoegen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Het opnemen van een pictogram in een knop kan het ontwerp van je applicatie aanzienlijk verbeteren, waardoor gebruikers actie-items op het scherm snel kunnen identificeren. De [`Icon`](./icon.md) component biedt een breed scala aan iconen om uit te kiezen.

Door de methoden `setPrefixComponent()` en `setSuffixComponent()` te gebruiken, heb je de flexibiliteit om te bepalen of een `Icon` voor of na de tekst op een knop moet verschijnen. Alternatief kan de `setIcon()` methode worden gebruikt om een `Icon` na de tekst toe te voegen, maar vóór de `suffix` plaats van de knop.

<!-- Voeg dit terug in zodra Icon is samengevoegd -->
<!-- Raadpleeg de [Icon-component](../components/icon) pagina voor meer informatie over het configureren en aanpassen van iconen. -->

:::tip
Standaard erft een `Icon` het thema en de grootte van de knop.
:::

Hieronder staan voorbeelden van knoppen met tekst links en rechts, evenals een knop met alleen een pictogram:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Namen {#names}

De `Button`-component maakt gebruik van naamgeving, die wordt gebruikt voor toegankelijkheid. Wanneer een naam niet expliciet is ingesteld, wordt het label van de `Button` in plaats daarvan gebruikt. Sommige pictogrammen hebben echter geen labels en tonen slechts niet-tekstuele elementen, zoals pictogrammen. In dit geval is het handig om de `setName()`-methode te gebruiken om ervoor te zorgen dat de gemaakte `Button`-component voldoet aan de toegankelijkheidsnormen.

## Een knop uitschakelen {#disabling-a-button}

Knopcomponenten, zoals vele anderen, kunnen worden uitgeschakeld om een gebruiker te laten zien dat een bepaalde actie nog niet of niet meer beschikbaar is. Een uitgeschakelde knop verkleint de opaciteit van de knop en is beschikbaar voor alle knoopthema's en -groottes.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Een knop uitschakelen kan op elk moment in de code worden gedaan met de <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> functie. Voor extra gemak kan een knop ook worden uitgeschakeld wanneer erop wordt geklikt met de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> functie.

In sommige gevallen activeert het klikken op een knop een langdurige actie. Het uitschakelen van de knop totdat je app de actie verwerkt, voorkomt dat de gebruiker de knop meerdere keren klikt, vooral in omgevingen met hoge latentie.

:::tip
Uitschakelen bij klik helpt niet alleen de verwerking van acties te optimaliseren, maar voorkomt ook dat de ontwikkelaar deze functionaliteit zelf hoeft te implementeren, aangezien deze methode is geoptimaliseerd om ronde communicaties te verminderen.
:::

## Stijlen {#styling}

### Thema's {#themes}

`Button`-componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 discrete thema's</JavadocLink> die zijn ingebouwd voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie aan te passen.

Hoewel er veel gebruikssituaties zijn voor elk van de verschillende thema's, zijn enkele voorbeelden:

  - **Gevaren**: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/data.
  - **Standaard**: Geschikt voor acties binnen een applicatie die geen speciale aandacht vereisen en algemeen van aard zijn, zoals het toggelen van een instelling.
  - **Primair**: Geschikt als een belangrijke "call-to-action" op een pagina, zoals inschrijven, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programmatic worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel risicovolle actie uit te voeren, zoals het navigeren van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder ingrijpend dan die welke het Gevaren-thema gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer suppletief zijn voor een pagina, en niet onderdeel zijn van de hoofdfunctionaliteit.
  - **Informatie**: Goed om extra verduidelijkende informatie aan een gebruiker te geven.

Hieronder staan voorbeeldknoppen met elk van de ondersteunde thema's toegepast: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Groottes {#expanses}
De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse">grootte waarden</JavadocLink> stellen snelle styling zonder het gebruik van CSS mogelijk. Dit maakt manipulatie van de afmetingen van de knop mogelijk zonder deze expliciet in te stellen met enige styling. Naast het vereenvoudigen van styling helpt het ook de uniformiteit in je applicatie te creëren en te behouden. De standaard `Button`-grootte is `Expanse.MEDIUM`.

Verschillende maten zijn vaak geschikt voor verschillende toepassingen:
  - **Grotere** grootte waarden zijn geschikt voor knoppen die de aandacht moeten trekken, functionaliteit benadrukken of essentieel zijn voor de kernfunctionaliteit van een applicatie of pagina.
  - **Middelgrote** knoppen, de standaardgrootte, moeten de standaardgrootte van knoppen zijn. De functies van deze knoppen moeten noch kritischer noch minder kritisch zijn dan vergelijkbare componenten.
  - **Kleinere** grootte waarden moeten worden gebruikt voor knoppen die geen integrale gedragingen in de applicatie hebben, en die een meer suppletieve of utilitaire rol vervullen, in plaats van een belangrijke rol in gebruikersinteractie te spelen. Dit omvat `Button`-componenten die alleen met iconen worden gebruikt voor utilitaire doeleinden.

Hieronder staan de verschillende groottes die worden ondersteund voor de `Button`-component: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Button`-component, overweeg de volgende beste praktijken:

1. **Juiste tekst**: Gebruik duidelijke en beknopte tekst voor de tekst binnen je `Button`-component om een duidelijke indicatie van het doel ervan te geven.

2. **Geschikte visuele styling**: Overweeg de visuele styling en het thema van de `Button` om consistentie met het ontwerp van je applicatie te waarborgen. Bijvoorbeeld:
  > - Een "Annuleer" `Button`-component moet gestyled zijn met het juiste thema of CSS-styling om ervoor te zorgen dat gebruikers zeker weten dat ze een actie willen annuleren.
  > - Een "Bevestig" `Button` zou een andere styling hebben dan een "Annuleer" knop, maar zou vergelijkbaar opvallen om ervoor te zorgen dat gebruikers weten dat dit een speciale actie is.

3. **Efficiënt evenementenbeheer**: Behandel `Button`-evenementen efficiënt en bied geschikte feedback aan gebruikers. Raadpleeg [Evenementen](../building-ui/events) om efficiënte gedragingen bij het toevoegen van evenementen te bekijken.

4. **Testen en toegankelijkheid**: Test het gedrag van de knop in verschillende scenario's, zoals wanneer deze is uitgeschakeld of focus krijgt, om een soepele gebruikerservaring te waarborgen. Volg toegankelijkheidsrichtlijnen om de `Button` bruikbaar te maken voor alle gebruikers, inclusief degenen die afhankelijk zijn van ondersteunende technologieën.
