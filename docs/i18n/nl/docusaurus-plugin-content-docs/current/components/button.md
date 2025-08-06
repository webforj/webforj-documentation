---
title: Button
sidebar_position: 15
_i18n_hash: 186724659f1f66cdb6f85e7a1628def8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Een `Button` component is een fundamenteel gebruikersinterface-element dat wordt gebruikt in de ontwikkeling van applicaties om interactieve elementen te creëren die actie of gebeurtenissen oproepen wanneer erop wordt geklikt of geactiveerd. Het dient als een klikbaar element waarmee gebruikers kunnen interageren om verschillende acties binnen een applicatie of website uit te voeren.

Het belangrijkste doel van de `Button` component is om een duidelijke en intuïtieve oproep tot actie te bieden voor gebruikers, waarmee ze worden geleid om specifieke taken uit te voeren, zoals het indienen van een formulier, navigeren naar een andere pagina, een functie oproepen of een proces starten. Knoppen zijn essentieel voor het verbeteren van gebruikersinteracties, het verbeteren van de toegankelijkheid en het creëren van een meer boeiende gebruikerservaring.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Toepassingen {#usages}

De `Button` klasse is een veelzijdig component dat vaak wordt gebruikt in verschillende situaties waar gebruikersinteracties en acties moeten worden geactiveerd. Hier zijn enkele typische scenario's waarin je een knop in je applicatie nodig hebt:

1. **Formulierindiening**: Knoppen worden vaak gebruikt om formulierdata in te dienen. Bijvoorbeeld, in een applicatie kun je gebruiken:

  > - Een "Verstuur" knop om gegevens naar de server te verzenden
  > - Een "Wis" knop om alle informatie die al in het formulier staat te verwijderen

2. **Gebruikersacties**: Knoppen worden gebruikt om gebruikers in staat te stellen specifieke acties binnen de applicatie uit te voeren. Bijvoorbeeld, je kunt een knop hebben met het label:

  > - "Verwijder" om de verwijdering van een geselecteerd item te initiëren
  > - "Opslaan" om aangebrachte wijzigingen op een document of pagina op te slaan.

3. **Bevestigingsdialoogvensters**: Knoppen zijn vaak opgenomen in [`Dialog`](../components/dialog) componenten die voor verschillende doeleinden zijn gebouwd om opties voor gebruikers te bieden om een actie te bevestigen of te annuleren, of enige andere functionaliteit die is ingebouwd in de [`Dialog`](../components/dialog) die je gebruikt.

4. **Interactietriggers**: Knoppen kunnen dienen als triggers voor interacties of gebeurtenissen binnen de applicatie. Door op een knop te klikken, kunnen gebruikers complexe acties starten of animaties oproepen, inhoud vernieuwen of de weergave bijwerken.

5. **Navigatie**: Knoppen kunnen worden gebruikt voor navigatiedoeleinden, zoals bewegen tussen verschillende secties of pagina's binnen een applicatie. Navigatieknoppen kunnen zijn:

  > - "Volgende" - Brengt de gebruiker naar de volgende pagina of sectie van de huidige applicatie of pagina.
  > - "Vorige" - Brengt de gebruiker terug naar de vorige pagina van de applicatie of sectie waarin ze zich bevinden.
  > - "Terug" - Brengt de gebruiker terug naar het eerste deel van de applicatie of pagina waarin ze zich bevinden.

## Icons aan knoppen toevoegen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Het incorporeren van een icoon in een knop kan het ontwerp van je app enorm verbeteren, waardoor gebruikers actiegerichte items op het scherm snel kunnen identificeren. De [`Icon`](./icon.md) component biedt een brede selectie van iconen om uit te kiezen.

Door gebruik te maken van de methoden `setPrefixComponent()` en `setSuffixComponent()`, heb je de flexibiliteit om te bepalen of een `Icon` voor of na de tekst op een knop moet verschijnen. Alternatief kan de methode `setIcon()` worden gebruikt om een `Icon` toe te voegen na de tekst, maar vóór de `suffix` slot van de knop.

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
Standaard erft een `Icon` het thema en de expanse van de knop.
:::

Hieronder staan voorbeelden van knoppen met tekst aan de linker- en rechterkant, evenals een knop met alleen een icoon:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

De `Button` component maakt gebruik van namen, die worden gebruikt voor toegankelijkheid. Wanneer een naam niet expliciet is ingesteld, wordt het label van de `Button` in plaats daarvan gebruikt. Sommige iconen hebben echter geen labels en tonen alleen niet-tekstuele elementen, zoals iconen. In dit geval is het handig om de methode `setName()` te gebruiken om ervoor te zorgen dat de gemaakte `Button` component voldoet aan de toegankelijkheidseisen.

## Een knop uitschakelen {#disabling-a-button}

Knopcomponenten, zoals veel andere, kunnen worden uitgeschakeld om aan een gebruiker te verduidelijken dat een bepaalde actie nog niet of niet meer beschikbaar is. Een uitgeschakelde knop zal de opaciteit van de knop verlagen, en is beschikbaar voor alle knopthema's en expansies.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Een knop uitschakelen kan op elk moment in de code worden gedaan met de <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> functie. Voor extra gemak kan een knop ook worden uitgeschakeld wanneer deze wordt aangeklikt met de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> functie.

In sommige applicaties triggert het klikken op een knop een langdurige actie. In de meeste gevallen wil de applicatie ervoor zorgen dat slechts één klik wordt verwerkt. Dit kan een probleem zijn in omgevingen met hoge latentie wanneer de gebruiker meerdere keren op de knop klikt voordat de applicatie de kans heeft gehad om de resulterende actie te verwerken.

:::tip
Uitschakelen bij klikken helpt niet alleen bij het optimaliseren van de verwerking van acties, maar voorkomt ook dat de ontwikkelaar deze functionaliteit zelf hoeft te implementeren, aangezien deze methode is geoptimaliseerd om rondsendcommunicatie te verminderen.
:::

## Stylen {#styling}

### Thema's {#themes}

`Button` componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 afzonderlijke thema's</JavadocLink> ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen in een applicatie te personaliseren.

Hoewel er vele gebruikscases zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden van gebruik:

  - **Gevaren**: Het beste voor acties met ernstige gevolgen, zoals het leegmaken van ingevulde informatie of het permanent verwijderen van een account/gegevens.
  - **Standaard**: Geschikt voor acties in een applicatie die geen speciale aandacht vereisen en algemeen zijn, zoals het toggelen van een instelling.
  - **Primaire**: Geschikt als een belangrijke "oproep tot actie" op een pagina, zoals aanmelden, wijzigingen opslaan, of doorgaan naar een andere pagina.
  - **Succes**: Uitstekend voor het visualiseren van succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of de voltooiing van een aanmeldproces. Het succes thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Handig om aan te geven dat een gebruiker op het punt staat een potentieel riskante actie uit te voeren, zoals navigeren van een pagina met onopgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Gevaren-thema zouden gebruiken.
  - **Grijs**: Goed voor subtiele acties, zoals kleinere instellingen of acties die meer aanvullend zijn aan een pagina, en geen deel uitmaken van de belangrijkste functionaliteit.
  - **Informatie**: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

Hieronder zijn voorbeeldknoppen met elk van de ondersteunde thema's toegepast: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expansies {#expanses}
De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expansies waarden</JavadocLink> staan snelle styling toe zonder het gebruik van CSS. Dit staat manipulatie van de afmetingen van de knop toe zonder het expliciet in te stellen met styling. Naast het vereenvoudigen van styling helpt het ook om uniformiteit in je applicatie te creëren en te behouden. De standaard `Button` expanse is `Expanse.MEDIUM`.

Verschillende maten zijn vaak geschikt voor verschillende toepassingen:
  - **Grotere** expanse waarden zijn geschikt voor knoppen die de aandacht moeten trekken, functionaliteit moeten benadrukken of essentieel zijn voor de kernfunctionaliteit van een applicatie of pagina.
  - **Middelgrote** expanse knoppen, de standaardgrootte, moeten worden gebruikt als een "standaardgrootte", wanneer het gedrag van een knop niet belangrijker of minder belangrijk is dan andere vergelijkbare componenten.
  - **Kleinere** expanse waarden moeten worden gebruikt voor knoppen die geen essentiële gedragingen in de applicatie hebben en een meer aanvullende of utilitaire rol vervullen, in plaats van een belangrijke rol in gebruikersinteracties te spelen. Dit omvat `Button` componenten die alleen met iconen voor utilitaire doeleinden worden gebruikt.

Hieronder staan de verschillende expansies die worden ondersteund voor de `Button` component: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Button` component, overweeg je de volgende best practices:

1. **Duidelijke tekst**: Gebruik duidelijke en beknopte tekst voor de tekst binnen je `Button` component om een duidelijke indicatie van het doel ervan te geven.

2. **Passende visuele styling**: Overweeg de visuele stijl en het thema van de `Button` om consistentie met het ontwerp van je applicatie te waarborgen. Bijvoorbeeld:
  > - Een "Annuleren" `Button` component moet worden gestyled met het juiste thema of CSS-styling om ervoor te zorgen dat gebruikers zeker weten dat ze een actie willen annuleren.
  > - Een "Bevestigen" `Button` zou anders zijn gestyled dan een "Annuleren" knop, maar zou op dezelfde manier opvallen om ervoor te zorgen dat gebruikers weten dat dit een speciale actie is.

3. **Efficiënte gebeurtenisafhandeling**: Behandel `Button` evenementen efficiënt en biedt passende feedback aan gebruikers. Verwijs naar [Evenementen](../building-ui/events) om efficiënte gebeurtenis toevoegde gedragingen te bekijken.

4. **Testen en toegankelijkheid**: Test het gedrag van de knop in verschillende scenario's, zoals wanneer deze is uitgeschakeld of focus ontvangt, om een soepele gebruikerservaring te waarborgen. Volg de richtlijnen voor toegankelijkheid om de `Button` bruikbaar te maken voor alle gebruikers, inclusief degenen die afhankelijk zijn van hulptechnologieën.
