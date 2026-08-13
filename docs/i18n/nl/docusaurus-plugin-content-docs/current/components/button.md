---
title: Button
sidebar_position: 15
description: >-
  Trigger click actions in webforJ with the Button component, including themes,
  expanses, prefix and suffix icons, and disabled state.
_i18n_hash: 31fa93b60126cba6b26198da5a5c15b5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Een `Button` is een klikbaar element dat een actie activeert wanneer het wordt ingedrukt. Het kan tekst, iconen of een combinatie van beide weergeven. Knoppen ondersteunen meerdere visuele thema's en maten, en kunnen worden uitgeschakeld om interactie te voorkomen tijdens langdurige bewerkingen of wanneer aan bepaalde voorwaarden niet wordt voldaan.

<!-- INTRO_END -->

## Gebruik {#usages}

De `Button`-klasse is een veelzijdige component die vaak wordt gebruikt in verschillende situaties waar gebruikersinteracties en acties moeten worden geactiveerd. Hier zijn enkele typische scenario's waarin u een knop in uw applicatie nodig zou kunnen hebben:

1. **Formuliersubmissie**: Knoppen worden vaak gebruikt om formuliergegevens in te dienen. Bijvoorbeeld, in een applicatie kunt u gebruiken:

  > - Een "Indienen" knop om gegevens naar de server te verzenden
  > - Een "Wissen" knop om alle informatie die al in het formulier aanwezig is te verwijderen

2. **Gebruikersacties**: Knoppen worden gebruikt om gebruikers in staat te stellen specifieke acties binnen de applicatie uit te voeren. U kunt bijvoorbeeld een knop hebben met het label:

  > - "Verwijderen" om de verwijdering van een geselecteerd item te starten
  > - "Opslaan" om wijzigingen aan een document of pagina op te slaan.

3. **Bevestigingsdialogen**: Knoppen worden vaak opgenomen in [`Dialog`](../components/dialog) componenten die voor verschillende doeleinden zijn gebouwd, om opties te bieden voor gebruikers om een actie te bevestigen of te annuleren, of andere functionaliteiten die zijn ingebouwd in de [`Dialog`](../components/dialog) die u gebruikt.

4. **Interactie-triggers**: Knoppen kunnen dienen als triggers voor interacties of gebeurtenissen binnen de applicatie. Door op een knop te klikken, kunnen gebruikers complexe acties initiëren of animaties, contentvernieuwingen of weergave-updates activeren.

5. **Navigatie**: Knoppen kunnen worden gebruikt voor navigatiedoeleinden, zoals het verplaatsen tussen verschillende secties of pagina's binnen een applicatie. Navigatieknoppen kunnen omvatten:

  > - "Volgende" - Brengt de gebruiker naar de volgende pagina of sectie van de huidige applicatie of pagina.
  > - "Vorige" - Brengt de gebruiker terug naar de vorige pagina van de applicatie of sectie waarin ze zich bevinden.
  > - "Terug" - Brengt de gebruiker terug naar het eerste deel van de applicatie of pagina waarin ze zich bevinden.

Het volgende voorbeeld toont knoppen voor formuliersubmissie en het wissen van invoer:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Iconen toevoegen aan knoppen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Het invoegen van een pictogram in een knop kan het ontwerp van uw app aanzienlijk verbeteren, zodat gebruikers snel actie-items op het scherm kunnen identificeren. De [`Icon`](./icon.md) component biedt een brede selectie van iconen om uit te kiezen.

Door gebruik te maken van de methoden `setPrefixComponent()` en `setSuffixComponent()`, heeft u de flexibiliteit om te bepalen of een `Icon` vóór of na de tekst op een knop moet verschijnen. Alternatief kan de methode `setIcon()` worden gebruikt om een `Icon` na de tekst toe te voegen, maar voor de `suffix`-slot van de knop.

<!-- Voeg dit terug in zodra Icon is samengevoegd -->
<!-- Zie de [Icon component](../components/icon) pagina voor meer informatie over het configureren en aanpassen van iconen. -->

:::tip
Standaard erft een `Icon` het thema en de expansie van de knop.
:::

Hieronder staan voorbeelden van knoppen met tekst aan de linkerkant en rechterkant, evenals een knop met alleen een pictogram:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Namen {#names}

De `Button` component maakt gebruik van benoemingen, die worden gebruikt voor toegankelijkheid. Wanneer een naam niet expliciet is ingesteld, zal het label van de `Button` worden gebruikt. Sommige iconen hebben echter geen labels en tonen alleen niet-tekst elementen, zoals iconen. In dit geval is het raadzaam om de methode `setName()` te gebruiken om ervoor te zorgen dat de gemaakte `Button` component voldoet aan de toegankelijkheidseisen.

## Een knop uitschakelen {#disabling-a-button}

Knopcomponenten kunnen, zoals veel andere, worden uitgeschakeld om aan een gebruiker tecommuniceren dat een bepaalde actie nog niet of niet meer beschikbaar is. Een uitgeschakelde knop verlaagt de opaciteit van de knop, en het is beschikbaar voor alle knoopthema's en -expansies.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Het uitschakelen van een knop kan op elk moment in de code worden gedaan door de <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> functie te gebruiken. Voor extra gemak kan een knop ook worden uitgeschakeld wanneer erop wordt geklikt met behulp van de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> functie.

In sommige gevallen activeert het klikken op een knop een langdurige actie. Het uitschakelen van de knop totdat uw app de actie verwerkt, voorkomt dat de gebruiker de knop meerdere keren klikt, vooral in omgevingen met hoge latentie.

:::tip
Uitschakelen bij klikken helpt niet alleen bij het optimaliseren van de verwerking van acties, maar voorkomt ook dat de ontwikkelaar deze functionaliteit zelf hoeft te implementeren, aangezien deze methode is geoptimaliseerd om rondreiscommunicatie te verminderen.
:::

## Stijlen {#styling}

### Thema's {#themes}

`Button` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 discrete thema's</JavadocLink> ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie aan te passen.

Hoewel er veel gebruiksscenario's zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden van gebruik:

  - **Gevarieerd**: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
  - **Standaard**: Geschikt voor acties in een applicatie die geen speciale aandacht vereisen en generiek zijn, zoals het toggelen van een instelling.
  - **Primair**: Geschikt als een belangrijke "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
  - **Succes**: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een applicatie, zoals de indiening van een formulier of voltooiing van een aanmeldproces. Het succes-thema kan programmatically worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Handig om aan te geven dat een gebruiker op het punt staat een potentieel risicovolle actie uit te voeren, zoals het navigeren van een pagina met onopgeslagen wijzigingen. Deze acties zijn vaak minder ingrijpend dan die welke het Gevarieerd-thema zouden gebruiken.
  - **Grijs**: Goede keuze voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend aan een pagina zijn, en geen deel uitmaken van de belangrijkste functionaliteit.
  - **Informatie**: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

Hieronder staan voorbeeldknoppen met elk van de ondersteunde thema's toegepast: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Expanses {#expanses}
De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses waarden </JavadocLink> maken snelle styling mogelijk zonder CSS te gebruiken. Dit maakt manipulatie van de afmetingen van de Knop mogelijk zonder het expliciet instellen met enige styling. Naast het vereenvoudigen van styling helpt het ook bij het creëren en onderhouden van een uniformiteit in uw applicatie. De standaard `Button` expanse is `Expanse.MEDIUM`.

Verschillende maten zijn vaak geschikt voor verschillende doeleinden:
  - **Grotere** expanse waarden zijn geschikt voor knoppen die de aandacht moeten trekken, functionaliteit benadrukken of essentieel zijn voor de kernfunctionaliteit van een applicatie of pagina.
  - **Middelgrote** expansiekoppen, de standaardgrootte, zouden de standaardgrootte van knoppen moeten zijn. De functies van deze knoppen zouden noch meer, noch minder cruciaal moeten zijn dan soortgelijke componenten.
  - **Kleinere** expansiewaarden moeten worden gebruikt voor knoppen die geen essentiële gedragingen in de applicatie hebben, en dienen meer een aanvullende of utilitaire rol, eerder dan een belangrijke rol in de gebruikersinteractie. Dit omvat `Button` componenten die alleen met iconen worden gebruikt voor utilitaire doeleinden.

Hieronder staan de verschillende expansies die worden ondersteund voor de `Button` component: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Button` component, overweeg de volgende beste praktijken:

1. **Juiste tekst**: Gebruik duidelijke en beknopte tekst voor de tekst binnen uw `Button` component om een duidelijke indicatie van het doel te geven.

2. **Juiste visuele styling**: Overweeg de visuele styling en het thema van de `Button` om consistentie met het ontwerp van uw applicatie te waarborgen. Bijvoorbeeld:
  > - Een "Annuleren" `Button` component moet worden gestyled met het juiste thema of CSS-styling om ervoor te zorgen dat gebruikers zeker weten dat ze een actie willen annuleren
  > - Een "Bevestigen" `Button` zou een andere styling hebben dan een "Annuleren" knop, maar zou ook opvallen om ervoor te zorgen dat gebruikers weten dat dit een speciale actie is.

3. **Efficiënte gebeurtenisverwerking**: Behandel `Button` gebeurtenissen efficiënt en geef gebruikers de juiste feedback. Zie [Evenementen](../building-ui/events) om het efficiënte gedrag bij het toevoegen van gebeurtenissen te bekijken.

4. **Testen en toegankelijkheid**: Test het gedrag van de knop in verschillende scenario's, zoals wanneer deze is uitgeschakeld of focus krijgt, om een soepele gebruikerservaring te waarborgen.
Volg richtlijnen voor toegankelijkheid om de `Button` bruikbaar te maken voor alle gebruikers, inclusief degenen die afhankelijk zijn van hulptechnologieën.
