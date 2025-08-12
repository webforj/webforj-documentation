---
title: Button
sidebar_position: 15
_i18n_hash: 0282098a1b80b4d494409d4f416caa5d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Een `Button` component is een fundamenteel gebruikersinterface-element dat wordt gebruikt in de ontwikkeling van applicaties om interactieve elementen te creëren die acties of gebeurtenissen triggeren wanneer erop geklikt of geactiveerd. Het fungeert als een klikbaar element waarmee gebruikers verschillende acties binnen een applicatie of website kunnen uitvoeren.

Het primaire doel van de `Button` component is om een duidelijke en intuïtieve oproep tot actie voor gebruikers te bieden, waarmee ze worden geleid om specifieke taken uit te voeren, zoals het indienen van een formulier, navigeren naar een andere pagina, het activeren van een functie of het starten van een proces. Knoppen zijn essentieel voor het verbeteren van gebruikersinteracties, het verbeteren van de toegankelijkheid, en het creëren van een meer betrokken gebruikerservaring.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Usages {#usages}

De `Button` klasse is een veelzijdig component dat vaak wordt gebruikt in verschillende situaties waarin gebruikersinteracties en acties moeten worden getriggerd. Hier zijn enkele typische scenario's waarbij je een knop in je applicatie nodig zou kunnen hebben:

1. **Formulierindiening**: Knoppen worden vaak gebruikt voor het indienen van formuliergegevens. Bijvoorbeeld, in een applicatie kun je gebruiken:

  > - Een "Indienen" knop om gegevens naar de server te sturen
  > - Een "Wissen" knop om alle reeds bestaande informatie in het formulier te verwijderen

2. **Gebruikersacties**: Knoppen worden gebruikt om gebruikers in staat te stellen specifieke acties binnen de applicatie uit te voeren. Bijvoorbeeld, je kunt een knop hebben met een label:

  > - "Verwijderen" om de verwijdering van een geselecteerd item te initiëren
  > - "Opslaan" om aangebrachte wijzigingen in een document of pagina te bewaren.

3. **Bevestigingsdialoogvensters**: Knoppen worden vaak opgenomen in [`Dialog`](../components/dialog) componenten die zijn gebouwd voor verschillende doeleinden om opties voor gebruikers te bieden om een actie te bevestigen of te annuleren, of enige andere functionaliteit die is ingebouwd in de [`Dialog`](../components/dialog) die je gebruikt.

4. **Interactie-triggers**: Knoppen kunnen dienen als triggers voor interacties of evenementen binnen de applicatie. Door op een knop te klikken, kunnen gebruikers complexe acties initiëren of animaties starten, inhoud vernieuwen of de weergave bijwerken.

5. **Navigatie**: Knoppen kunnen worden gebruikt voor navigatiedoeleinden, zoals het bewegen tussen verschillende secties of pagina's binnen een applicatie. Knoppen voor navigatie kunnen omvatten:

  > - "Volgende" - Neem de gebruiker mee naar de volgende pagina of sectie van de huidige applicatie of pagina.
  > - "Vorige" - Breng de gebruiker terug naar de vorige pagina van de applicatie of sectie waarin ze zich bevinden.
  > - "Terug" - Breng de gebruiker terug naar het eerste deel van de applicatie of pagina waarin ze zich bevinden.

## Iconen aan knoppen toevoegen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Het opnemen van een pictogram in een knop kan het ontwerp van je app aanzienlijk verbeteren, waardoor gebruikers snel herkenbare actie-items op het scherm kunnen identificeren. De [`Icon`](./icon.md) component biedt een brede selectie van pictogrammen om uit te kiezen.

Door gebruik te maken van de methoden `setPrefixComponent()` en `setSuffixComponent()`, heb je de flexibiliteit om te bepalen of een `Icon` vóór of na de tekst op een knop moet verschijnen. Alternatief kan de `setIcon()` methode worden gebruikt om een `Icon` na de tekst, maar vóór de `suffix` slot van de knop toe te voegen.

<!-- Voeg dit terug in zodra Icon is samengevoegd -->
<!-- Raadpleeg de [Icon component](../components/icon) pagina voor meer informatie over het configureren en aanpassen van pictogrammen. -->

:::tip
Standaard erft een `Icon` het thema en de uitgestrektheid van de knop.
:::

Hieronder staan voorbeelden van knoppen met tekst links en rechts, evenals een knop met alleen een pictogram:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

De `Button` component maakt gebruik van naming, die wordt gebruikt voor toegankelijkheid. Wanneer een naam niet expliciet is ingesteld, wordt het label van de `Button` in plaats daarvan gebruikt. Echter, sommige pictogrammen hebben geen labels en tonen alleen niet-tekst elementen, zoals pictogrammen. In dit geval is het nuttig om de `setName()` methode te gebruiken om ervoor te zorgen dat de gemaakte `Button` component voldoet aan toegankelijkheidseisen.

## Een knop uitschakelen {#disabling-a-button}

Knopcomponenten, net als vele anderen, kunnen worden uitgeschakeld om aan de gebruiker te communiceren dat een bepaalde actie nog niet of niet meer beschikbaar is. Een uitgeschakelde knop verlaagt de opaciteit van de knop en is beschikbaar voor alle knoopthema's en uitgestrektheden.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Een knop uitschakelen kan op elk moment in de code worden gedaan door gebruik te maken van de <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> functie. Voor extra gemak kan een knop ook uitgeschakeld worden tijdens het klikken met de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> functie.

In sommige applicaties triggeren klikken op een knop een langlopende actie. In de meeste gevallen wil de applicatie ervoor zorgen dat slechts één klik wordt verwerkt. Dit kan een probleem zijn in omgevingen met hoge latency wanneer de gebruiker de knop meerdere keren klikt voordat de applicatie de kans heeft gehad om te beginnen met het verwerken van de resulterende actie.

:::tip
Uitschakelen bij klikken helpt niet alleen de verwerking van acties te optimaliseren, maar voorkomt ook dat de ontwikkelaar deze functie zelf moet implementeren, aangezien deze methode is geoptimaliseerd om het aantal roundtrip-communicaties te verminderen.
:::

## Styling {#styling}

### Thema's {#themes}

`Button` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 discrete thema's</JavadocLink> ingebouwd voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van knoppen in een applicatie aan te passen.

Hoewel er veel gebruiksgevallen zijn voor elk van de verschillende thema's, zijn enkele voorbeelden van gebruik:

  - **Gevaren**: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
  - **Standaard**: Geschikt voor acties doorheen een applicatie die geen speciale aandacht vereisen en algemeen zijn, zoals het schakelen van een instelling.
  - **Primair**: Geschikt als een belangrijkste "oproep tot actie" op een pagina, zoals inschrijven, wijzigingen opslaan of door gaan naar een andere pagina.
  - **Succes**: Uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programma-technisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel riskante actie te ondernemen, zoals het navigeren van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Gevaren-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina en geen onderdeel zijn van de hoofdfunctionaliteit.
  - **Informatie**: Goed voor het geven van aanvullende verduidelijkende informatie aan een gebruiker.

Hieronder staan voorbeeldknoppen met elk van de ondersteunde thema's toegepast: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Uitgestrektheden {#expanses}
De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse">uitgestrektheidswaarden</JavadocLink> staan snelle styling toe zonder gebruik van CSS. Dit stelt je in staat om de afmetingen van de knop te manipuleren zonder deze expliciet in te stellen met enige styling. Naast het vereenvoudigen van styling helpt het ook om een uniformiteit in je applicatie te creëren en te onderhouden. De standaard `Button` uitgestrektheid is `Expanse.MEDIUM`.

Verschillende maten zijn vaak geschikt voor verschillende gebruiksdoeleinden:
  - **Grotere** uitgestrektheidswaarden zijn geschikt voor knoppen die aandacht moeten trekken, functionaliteit benadrukken of essentieel zijn voor de kernfunctionaliteit van een applicatie of pagina.
  - **Gemiddelde** uitgestrektheidsknoppen, de standaardgrootte, moeten worden gebruikt als een "standaardgrootte", wanneer het gedrag van een knop niet meer of minder belangrijk is dan andere soortgelijke componenten.
  - **Kleinere** uitgestrektheidswaarden moeten worden gebruikt voor knoppen die geen integraal gedrag in de applicatie hebben en een meer aanvullende of utilitaire rol dienen, in plaats van een belangrijke rol in gebruikersinteractie te spelen. Dit omvat `Button` componenten die alleen met pictogrammen voor utilitaire doeleinden worden gebruikt.

Hieronder staan de verschillende uitgestrektheden die worden ondersteund voor de `Button` component: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Button` component, overweeg de volgende beste praktijken:

1. **Juiste tekst**: Gebruik duidelijke en beknopte tekst voor tekst binnen je `Button` component om een duidelijke indicatie van het doel ervan te geven.

2. **Geschikte visuele styling**: Overweeg de visuele styling en het thema van de `Button` om consistentie met het ontwerp van je applicatie te waarborgen. Bijvoorbeeld:
  > - Een "Annuleer" `Button` component moet worden gestyled met het juiste thema of CSS-styling om ervoor te zorgen dat gebruikers zeker weten dat ze een actie willen annuleren.
  > - Een "Bevestigen" `Button` zou een andere styling hebben dan een "Annuleer" knop, maar zou op dezelfde manier opvallen om ervoor te zorgen dat gebruikers weten dat dit een speciale actie is.

3. **Efficiënte gebeurtenishandling**: Handhaal `Button` gebeurtenissen efficiënt en bied passende feedback aan gebruikers. Raadpleeg [Gebeurtenissen](../building-ui/events) om efficiënte gedragingen voor het toevoegen van gebeurtenissen te bekijken.

4. **Testen en toegankelijkheid**: Test het gedrag van de knop in verschillende scenario's, zoals wanneer deze is uitgeschakeld of focus ontvangt, om een soepele gebruikerservaring te waarborgen. Volg de toegankelijkheid richtlijnen om ervoor te zorgen dat de `Button` bruikbaar is voor alle gebruikers, inclusief degenen die afhankelijk zijn van assistieve technologieën.
