---
title: Icon
sidebar_position: 55
_i18n_hash: 2da7d4e8288df67fc46f2a3ba84e12ee
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De webforJ `Icon` component stelt je in staat om moeiteloos iconen in je gebruikersinterface op te nemen. 
Iconen zijn een fundamenteel onderdeel van het verbeteren van het ontwerp van de gebruikersinterface, waardoor het voor gebruikers sneller is om het scherm te scannen op acties die ze kunnen ondernemen. 
Het gebruik van iconen in je app creëert visuele aanwijzingen voor navigatie en acties, wat de hoeveelheid benodigde tekst kan verminderen en de gebruikersinterface kan vereenvoudigen. Je kunt kiezen uit drie bestaande iconencollecties en webforJ biedt je ook de optie om nieuwe iconen vanaf nul te maken.

:::tip Wist je dat?

Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om betekenis over te brengen aan eindgebruikers.

:::

## Basisprincipes {#basics}

Elke `Icon` is ontworpen als een Scalable Vector Graphics (SVG) afbeelding, wat betekent dat deze eenvoudig kan worden geschaald naar elke grootte zonder verlies van helderheid of kwaliteit. 
Daarnaast worden `Icon` componenten op aanvraag geladen vanuit een content delivery network (CDN), wat helpt om de latentie te verminderen en de algehele prestaties te verbeteren.

Bij het maken van een `Icon` moet je een specifieke collectie identificeren en de naam van het icon zelf. 
Sommige iconen bieden ook de keuze tussen een omrand of een gevulde versie via [variaties](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Collecties {#pools}

Een iconencollectie is een verzameling veelgebruikte iconen die eenvoudig toegankelijk en herbruikbaar zijn. Door iconen uit een iconencollectie te gebruiken, kun je ervoor zorgen dat de iconen in je app herkenbaar zijn en een consistente stijl hebben. 
Het gebruik van webforJ stelt je in staat om uit drie collecties te kiezen, of een aangepaste collectie te implementeren. 
Elke collectie heeft een uitgebreide verzameling open-source iconen die gratis te gebruiken zijn. 
Het gebruik van webforJ biedt je de flexibiliteit om uit drie collecties te kiezen en deze als unieke klassen te gebruiken, zonder dat je iconen rechtstreeks hoeft te downloaden.

| Iconencollectie                                | webforJ Klasse |
| ----------------------------------------------- | -------------- |
| [Tabler](https://tabler-icons.io/)             | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler iconen.|    
| [Feather](https://feathericons.com/)           | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search) | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het creëren van je eigen iconencollectie, zie [Creating custom pools](#creating-custom-pools).

:::

Zodra je de collectie of collecties hebt geselecteerd die je in je app wilt opnemen, is de volgende stap om de naam van het icon te specificeren dat je wilt gebruiken.

### Namen {#names}

Om een icon in je app op te nemen, heb je alleen de iconencollectie en de iconnaam nodig. Blader door de website van de iconencollectie voor het icon dat je wilt gebruiken en gebruik de iconnaam als parameter van de `create()` methode. 
Daarnaast kun je de iconen ook maken via enums voor de `FeatherIcon` en `DwcIcon` klassen, waardoor ze in de code-completion kunnen verschijnen.

```java
// Maak een icon aan vanuit een String naam
Icon image = TablerIcon.create("image");
// Maak een icon aan vanuit een enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaties {#variations}

Je kunt iconen nog verder personaliseren door gebruik te maken van variaties. 
Bepaalde iconen stellen je in staat om te kiezen tussen een omrand of een gevulde versie, waardoor je een specifiek icon kunt benadrukken op basis van je voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

#### `FontAwesomeIcon` variaties {#fontawesomeicon-variations}

1. `REGULAR`: De omrand variant van iconen. Dit is de standaard.
2. `SOLID`: De gevulde variant van iconen.
3. `BRAND`: De variant voor wanneer je de iconen van merken gebruikt.

#### `TablerIcon` variaties {#tablericon-variations}

1. `OUTLINE`: De omrand variant van iconen. Dit is de standaard.
2. `FILLED`: De gevulde variant van iconen.

```java
// Een gevulde variant van een icon van Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

De volgende demo illustreert hoe je iconen uit verschillende collecties kunt gebruiken, variaties kunt toepassen en ze naadloos in componenten kunt integreren.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Iconen aan componenten toevoegen {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is voordelig om een `Icon` aan een component toe te voegen om de bedoelde betekenis voor gebruikers verder te verduidelijken. 
Componenten die de `HasPrefixAndSuffix` interface implementeren, kunnen een `Icon` of andere geldige componenten bevatten. De toegevoegde componenten kunnen in de `prefix` en `suffix` slots worden geplaatst en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met behulp van de `prefix` en `suffix` slots kun je bepalen of je het icon vóór of na de tekst wilt hebben met behulp van de `setPrefixComponent()` en `setSuffixComponent()` methoden.

Bepalen of je een icon vóór of na de tekst op een component plaatst hangt grotendeels af van het doel en de ontwerpcontext.

### Iconplaatsing: voor VS na {#icon-placement-before-vs-after}

Iconen die vóór de componenttekst zijn geplaatst, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral bij universeel erkende iconen zoals het opslaan-icoon. 
Iconen vóór de tekst van een component bieden een logische verwerkingsvolgorde, waardoor gebruikers op natuurlijke wijze door de bedoelde actie worden geleid, wat voordelig is voor knoppen waarvan de primaire functie een directe actie is.

Aan de andere kant is het effectief om iconen na de componenttekst te plaatsen voor acties die aanvullende context of opties bieden, waardoor duidelijkheid en aanwijzingen voor navigatie worden verbeterd. 
Iconen na de tekst van een component zijn ideaal voor componenten die aanvullende informatie bieden of gebruikers in een richtingstroom begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je een stijl hebt gekozen, houd deze dan aan in je site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Aangepaste collecties maken {#creating-custom-pools}

Naast het gebruik van bestaande iconencollecties, heb je de optie om een aangepaste collectie te creëren die kan worden gebruikt voor aangepaste logo's of avatars. 
Een aangepaste collectie iconen kan worden opgeslagen in een gecentraliseerde map of in de bronnenmap (context), wat het proces van iconbeheer vereenvoudigt. 
Het hebben van een aangepaste collectie maakt de app-creatie consistenter en vermindert het onderhoud over verschillende componenten en modules.

Aangepaste collecties kunnen worden gemaakt vanuit een map met SVG-afbeeldingen en door gebruik te maken van de `IconPoolBuilder` klasse. Van daaruit kun je de naam van je aangepaste collectie kiezen en deze gebruiken met de namen van de SVG-bestanden om aangepaste iconcomponenten te maken.

```java
// Een aangepaste collectie genaamd "app-pool" creëren met afbeeldingen voor een logo en een avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste poolfabriek {#custom-pool-factory}

Je kunt ook een fabrieksklasse voor een aangepaste collectie in webforJ maken, net zoals `FeatherIcon`. Dit stelt je in staat om iconresources binnen een opgegeven collectie te creëren en te beheren en ondersteunt code-completion. 
Elk icon kan worden geïnstantieerd via de `create()` methode, die een `Icon` retourneert. De fabrieksklasse moet pool-specifieke metadata bieden, zoals de naam van de collectie en de identificator van het icon, geformatteerd volgens de bestandsnaam van de afbeelding. 
Dit ontwerp maakt gemakkelijke, gestandaardiseerde toegang tot icon-assets vanuit de aangepaste collectie met behulp van enum-constanten, wat schaalbaarheid en onderhoudbaarheid in iconbeheer ondersteunt.

```java
// Een aangepaste poolfabriek maken voor app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return de naam van de collectie voor de iconen
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return de naam van het icon
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

De volgende snippet toont de twee verschillende manieren om een aangepaste collectie te gebruiken.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Maak een Icon met de namen van de aangepaste collectie en het afbeeldingsbestand
Icon customLogo = new Icon("logo", "app-pool");

// Maak een Icon met de aangepaste poolfabriek uit de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Iconknoppen {#icon-buttons}
Een `Icon` component is niet selecteerbaar, maar voor acties die het beste worden weergegeven met alleen een icon, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!");
});
```

## Beste praktijken

- **Toegankelijkheid:** Gebruik een tooltip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van schermlezers.
- **Vermijd ambiguïteit:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of breed begrepen is. Als gebruikers moeten raden wat het icon vertegenwoordigt, heeft het geen zin.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, gebruik daarom alleen iconen wanneer ze duidelijkheid toevoegen of de complexiteit verminderen.

## Stijlen
Een Icon erft het thema van zijn directe bovenliggende component, maar je kunt dit overschrijven door een thema rechtstreeks op een `Icon` toe te passen.

### Thema's
Iconcomponenten worden geleverd met zeven discrete thema's die zijn ingebouwd voor snelle stijling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van iconen door de hele app te personaliseren.

Hoewel er veel toepassingen zijn voor elk van de verschillende thema's, zijn er enkele voorbeelden van gebruik:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
- `DEFAULT`: Geschikt voor acties in een app die geen speciale aandacht vereisen en algemeen zijn, zoals het in- of uitschakelen van een instelling.
- `PRIMARY`: Geschikt als een belangrijke "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan of naar een andere pagina gaan.
- `SUCCESS`: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een app, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het success-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
- `WARNING`: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel riskante actie uit te voeren, zoals het navigeren van een pagina met ongebruikte wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Danger-thema zouden gebruiken.
- `GRAY`: Goed voor subtiele acties, zoals kleinere instellingen of acties die meer aanvullend zijn voor een pagina en geen deel uitmaken van de belangrijkste functionaliteit.
- `INFO`: Goed voor het bieden van aanvullende verduidelijkende informatie aan een gebruiker.

<TableBuilder name="Icon" />
