---
title: Button
sidebar_position: 15
_i18n_hash: 7df385d72b74249e5689c31575568ae8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Een `Button` is een klikbaar element dat een actie in gang zet wanneer het wordt ingedrukt. Het kan tekst, pictogrammen of een combinatie van beide weergeven. Knoppen ondersteunen meerdere visuele thema's en maten, en kunnen worden uitgeschakeld om te voorkomen dat interactie plaatsvindt tijdens langdurige operaties of wanneer aan bepaalde voorwaarden niet is voldaan.

<!-- INTRO_END -->

## Usages {#usages}

De `Button`-klasse is een veelzijdig component dat vaak wordt gebruikt in verschillende situaties waarin gebruikersinteracties en acties moeten worden geactiveerd. Hier zijn enkele typische scenario's waarin je een knop in je applicatie nodig zou kunnen hebben:

1. **Formulierindiening**: Knoppen worden vaak gebruikt om formuliergegevens in te dienen. In een applicatie kun je bijvoorbeeld:

  > - Een "Indienen" knop gebruiken om gegevens naar de server te verzenden
  > - Een "Wissen" knop om alle informatie die al in het formulier staat te verwijderen

2. **Gebruikersacties**: Knoppen worden gebruikt om gebruikers specifieke acties binnen de applicatie te laten uitvoeren. Je kunt bijvoorbeeld een knop hebben met het label:

  > - "Verwijderen" om de verwijdering van een geselecteerd item te starten
  > - "Opslaan" om wijzigingen aan een document of pagina op te slaan.

3. **Bevestigingsdialogen**: Knoppen zijn vaak inbegrepen in [`Dialog`](../components/dialog) componenten die voor verschillende doeleinden zijn gebouwd om opties voor gebruikers te bieden om een actie te bevestigen of te annuleren, of enige andere functionaliteit die is ingebouwd in de [`Dialog`](../components/dialog) die je gebruikt.

4. **Interactietriggers**: Knoppen kunnen dienen als triggers voor interacties of evenementen binnen de applicatie. Door op een knop te klikken, kunnen gebruikers complexe acties starten of animaties activeren, inhoud vernieuwen of de weergave bijwerken.

5. **Navigatie**: Knoppen kunnen worden gebruikt voor navigatiedoeleinden, zoals verplaatsen tussen verschillende secties of pagina's binnen een applicatie. Navigatieknoppen kunnen onder andere omvatten:

  > - "Volgende" - Brengt de gebruiker naar de volgende pagina of sectie van de huidige applicatie of pagina.
  > - "Vorige" - Brengt de gebruiker terug naar de vorige pagina van de applicatie of sectie waarin ze zich bevinden.
  > - "Terug" - Brengt de gebruiker terug naar het eerste deel van de applicatie of pagina waarin ze zich bevinden.
  
Het volgende voorbeeld toont knoppen die worden gebruikt voor formulierindiening en het wissen van invoer:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Iconen aan knoppen toevoegen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Het opnemen van een pictogram in een knop kan het ontwerp van je app aanzienlijk verbeteren, waardoor gebruikers snel actiegerichte items op het scherm kunnen identificeren. De [`Icon`](./icon.md) component biedt een brede selectie van pictogrammen om uit te kiezen.

Door gebruik te maken van de methoden `setPrefixComponent()` en `setSuffixComponent()` heb je de flexibiliteit om te bepalen of een `Icon` vóór of na de tekst op een knop moet verschijnen. Alternatief kan de `setIcon()`-methode worden gebruikt om een `Icon` na de tekst toe te voegen, maar vóór de `suffix`-slot van de knop.

<!-- Voeg dit later weer toe als Icon is samengevoegd -->
<!-- Zie de [Icon component](../components/icon) pagina voor meer informatie over het configureren en aanpassen van pictogrammen. -->

:::tip
Standaard erft een `Icon` het thema en de uitgestrektheid van de knop.
:::

Hieronder worden voorbeelden gegeven van knoppen met tekst aan de linker- en rechterkant, evenals een knop met alleen een pictogram:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

De `Button`-component maakt gebruik van naamgeving, die wordt gebruikt voor toegankelijkheid. Wanneer een naam niet expliciet is ingesteld, wordt het label van de `Button` in plaats daarvan gebruikt. Sommige pictogrammen hebben echter geen labels en tonen alleen niet-tekstuele elementen, zoals pictogrammen. In dit geval is het handig om de `setName()`-methode te gebruiken om ervoor te zorgen dat de gemaakte `Button`-component voldoet aan de toegankelijkheidstandaarden.

## Een knop uitschakelen {#disabling-a-button}

Knopcomponenten, zoals veel andere, kunnen worden uitgeschakeld om aan een gebruiker duidelijk te maken dat een bepaalde actie nog niet of niet meer beschikbaar is. Een uitgeschakelde knop zal de opaciteit van de knop verlagen en is beschikbaar voor alle knoopthema's en -uitgestrektheden.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Een knop uitschakelen kan op elk moment in de code worden gedaan door de functie <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> te gebruiken. Voor extra gemak kan een knop ook worden uitgeschakeld wanneer erop wordt geklikt met de ingebouwde <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> functie.

In sommige gevallen activeert het klikken op een knop een langdurige actie. Het uitschakelen van de knop totdat je app de actie verwerkt, voorkomt dat de gebruiker meerdere keren op de knop klikt, vooral in omgevingen met hoge latentie.

:::tip
Uitschakelen bij klikken helpt niet alleen om de verwerking van acties te optimaliseren, maar voorkomt ook dat de ontwikkelaar deze gedrag zelf hoeft te implementeren, omdat deze methode is geoptimaliseerd om rondreiscommunicaties te verminderen.
:::

## Styling {#styling}

### Thema's {#themes}

`Button`-componenten worden geleverd met <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 discrete thema's</JavadocLink> die ingebouwd zijn voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op knoppen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van knoppen door een applicatie heen aan te passen. 

Er zijn veel gebruikscasussen voor elk van de verschillende thema's, enkele voorbeelden zijn:

  - **Gevaren**: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
  - **Standaard**: Geschikt voor acties binnen een toepassing die geen speciale aandacht vereisen en generiek zijn, zoals het in- of uitschakelen van een instelling.
  - **Primaire**: Geschikt als de belangrijkste "oproep tot actie" op een pagina, zoals aanmelden, wijzigingen opslaan of naar een andere pagina gaan.
  - **Succes**: Uitstekend voor het visualiseren van een succesvolle voltooiing van een element in een applicatie, zoals het indienen van een formulier of het voltooien van een aanmeldingsproces. Het succesthema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
  - **Waarschuwing**: Handig om aan te geven dat een gebruiker op het punt staat een potentieel risicovolle actie uit te voeren, zoals het navigeren weg van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die met het Gevaren-thema.
  - **Grijs**: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn aan een pagina en niet deel uitmaken van de hoofdfunctionaliteit.
  - **Informatie**: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

Hieronder staan voorbeeldknoppen met elk van de ondersteunde thema's toegepast: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Uitgestrektheden {#expanses}
De volgende <JavadocLink type="foundation" location="com/webforj/component/Expanse"> uitgestrektheidswaarden </JavadocLink> staan snelle styling toe zonder gebruik van CSS. Dit maakt manipulatie van de afmetingen van de knop mogelijk zonder deze expliciet met styling in te stellen. Naast het vereenvoudigen van styling helpt het ook om een uniformiteit in je applicatie te creëren en te behouden. De standaard `Button` uitgestrektheid is `Expanse.MEDIUM`.

Verschillende maten zijn vaak geschikt voor verschillende toepassingen:
  - **Grotere** uitgestrektheidswaarden zijn geschikt voor knoppen die de aandacht moeten trekken, functionaliteit moeten benadrukken of essentieel zijn voor de kernfunctionaliteit van een applicatie of pagina.
  - **Middelgrote** uitgestrektheidsknoppen, de standaardmaat, moet de standaardgrootte van knoppen zijn. De functies van deze knoppen moeten even belangrijk zijn als vergelijkbare componenten.
  - **Kleinere** uitgestrektheidswaarden moeten worden gebruikt voor knoppen die geen essentiële functies in de applicatie hebben, en dienen een meer aanvullende of utilitaire rol, in plaats van een belangrijke rol in gebruikersinteractie te spelen. Dit omvat `Button`-componenten die alleen met pictogrammen voor utilitaire doeleinden worden gebruikt.

Hieronder worden de verschillende uitgestrektheden die worden ondersteund voor de `Button`-component weergegeven: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `Button`-component, overweeg de volgende best practices:

1. **Juiste Tekst**: Gebruik duidelijke en beknopte tekst voor de tekst binnen je `Button`-component om een duidelijke indicatie van zijn doel te geven.

2. **Geschikte Visuele Styling**: Overweeg de visuele styling en het thema van de `Button` om consistentie met het ontwerp van je applicatie te waarborgen. Bijvoorbeeld:
  > - Een "Annuleren" `Button`-component moet gestyled worden met het juiste thema of CSS-styling om ervoor te zorgen dat gebruikers zeker weten dat ze een actie willen annuleren.
  > - Een "Bevestigen" `Button` zou een andere opmaak hebben dan een "Annuleer" knop, maar zou op dezelfde manier opvallen om ervoor te zorgen dat gebruikers weten dat dit een speciale actie is.

3. **Efficiënte Evenementafhandeling**: Behandel `Button`-evenementen efficiënt en geef passende feedback aan gebruikers. Raadpleeg [Evenementen](../building-ui/events) om de efficiënte gedragingen voor het toevoegen van evenementen te bekijken.

4. **Tests en Toegankelijkheid**: Test het gedrag van de knop in verschillende scenario's, zoals wanneer deze is uitgeschakeld of focus ontvangt, om een soepele gebruikerservaring te waarborgen.
Volg toegankelijkheid richtlijnen om de `Button` bruikbaar te maken voor alle gebruikers, inclusief degenen die afhankelijk zijn van ondersteunende technologieën.
