---
title: Icon
sidebar_position: 55
_i18n_hash: ab67367c75477c4366e5e86862dac630
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De webforJ `Icon` component stelt je in staat om iconen moeiteloos in je gebruikersinterface op te nemen.
Iconen zijn een fundamenteel onderdeel van het verbeteren van het ontwerp van de gebruikersinterface, waardoor het sneller is voor gebruikers om het scherm af te scannen naar actie-items.
Het gebruik van iconen in je app creëert visuele aanwijzingen voor navigatie en acties, wat de hoeveelheid benodigde tekst kan verminderen en de gebruikersinterface kan vereenvoudigen. Je kunt kiezen uit drie bestaande iconenpools en webforJ geeft je ook de optie om nieuwe iconen vanaf nul te maken.

:::tip Wist je dat?

Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om betekenis over te brengen aan eindgebruikers.

:::

## Basisprincipes {#basics}

Elke `Icon` is ontworpen als een Scalable Vector Graphics (SVG) afbeelding, wat betekent dat deze gemakkelijk naar elke grootte kan worden geschaald zonder verlies van helderheid of kwaliteit.
Bovendien worden `Icon` componenten op aanvraag geladen vanuit een content delivery network (CDN), wat helpt om de latentie te verminderen en de algehele prestaties te verbeteren.

Wanneer je een `Icon` maakt, moet je een specifieke pool en de naam van het icon zelf identificeren.
Sommige iconen bieden ook de keuze tussen een omrande of een gevulde versie via [variaties](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Pools {#pools}

Een iconenpool is een verzameling veelgebruikte iconen die gemakkelijke toegang en hergebruik mogelijk maakt. Door iconen uit een iconenpool te gebruiken, kun je ervoor zorgen dat de iconen in je app herkenbaar zijn en een consistente stijl delen.
Het gebruik van webforJ stelt je in staat om te kiezen uit drie pools of een aangepaste pool te implementeren.
Elke pool heeft een uitgebreide verzameling open-source iconen die gratis te gebruiken zijn.
Door webforJ te gebruiken, krijg je de flexibiliteit om uit drie pools te kiezen en deze als unieke klassen te gebruiken, zonder gedoe met het direct downloaden van iconen.

| Icon Pool                                         | webforJ Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler-iconen.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het maken van je eigen iconenpool, zie [Aangepaste pools maken](#creating-custom-pools).

:::

Zodra je de pool of pools hebt geselecteerd die je in je app wilt opnemen, is de volgende stap het specificeren van de naam van het icon dat je wilt gebruiken.

### Namen {#names}

Om een icon in je app op te nemen, heb je alleen de iconenpool en de iconnaam nodig. Blader door de website van de iconenpool naar het icon dat je wilt gebruiken en gebruik de iconnaam als parameter van de `create()` methode.
Daarnaast kun je iconen creëren via enums voor de `FeatherIcon` en `DwcIcon` klassen, waardoor ze kunnen verschijnen in de code-completie.

```java
// Maak een icon van een String naam
Icon image = TablerIcon.create("image");
// Maak een icon van een enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaties {#variations}

Je kunt iconen nog verder personaliseren door variaties te gebruiken.
Bepaalde iconen stellen je in staat om te kiezen tussen een omrande of een gevulde versie, zodat je een specifiek icon kunt benadrukken op basis van je voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

#### `FontAwesomeIcon` variaties {#fontawesomeicon-variations}

1. `REGULAR`: De omrande variant van iconen. Dit is de standaard.
2. `SOLID`: De gevulde variant van iconen.
3. `BRAND`: De variant voor wanneer je de iconen van merken gebruikt.

#### `TablerIcon` variaties {#tablericon-variations}

1. `OUTLINE`: De omrande variant van iconen. Dit is de standaard.
2. `FILLED`: De gevulde variant van iconen.

```java
// Een gevulde variant van een icon van Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

De volgende demo illustreert hoe je iconen uit verschillende pools kunt gebruiken, variaties kunt toepassen en ze naadloos in componenten integreert.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Iconen aan componenten toevoegen {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is voordelig om een `Icon` aan een component toe te voegen om de bedoelde betekenis verder te verduidelijken voor gebruikers.
Componenten die de `HasPrefixAndSuffix` interface implementeren, kunnen een `Icon` of andere geldige componenten bevatten. De toegevoegde componenten kunnen in de `prefix` en `suffix` slots worden geplaatst en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met `prefix` en `suffix` slots kun je bepalen of je het icon voor of na de tekst wilt hebben met de `setPrefixComponent()` en `setSuffixComponent()` methoden.

Bepalen of je een icon voor of na de tekst op een component plaatst, hangt grotendeels af van het doel en de ontwerpcontext.

### Icon plaatsing: voor VS na {#icon-placement-before-vs-after}

Iconen die voor de componenttekst zijn geplaatst, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral voor universeel erkende iconen zoals het opslaan-icoon.
Iconen voor de tekst van een component bieden een logische verwerkingsvolgorde, waardoor gebruikers natuurlijk door de bedoelde actie worden geleid, wat nuttig is voor knoppen waarvan de primaire functie een onmiddellijke actie is.

Aan de andere kant is het effectief om iconen na de tekst van de component te plaatsen voor acties die aanvullende context of opties bieden, waarbij duidelijkheid en aanwijzingen voor navigatie worden verbeterd.
Iconen na de tekst van een component zijn ideaal voor componenten die aanvullende informatie bieden of gebruikers in een richting begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je een stijl kiest, houd deze dan consistent op je site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Aangepaste pools maken {#creating-custom-pools}

Naast het gebruiken van bestaande iconenverzamelingen, heb je de optie om een aangepaste pool te creëren die kan worden gebruikt voor aangepaste logo's of avatars.
Een aangepaste pool van iconen kan worden opgeslagen in een gecentraliseerde map of in de resources map (context), waardoor het beheer van iconen wordt vereenvoudigd.
Het hebben van een aangepaste pool maakt het creëren van apps consistenter en vermindert het onderhoud over verschillende componenten en modules.

Aangepaste pools kunnen worden gemaakt uit een map met SVG-afbeeldingen en door gebruik te maken van de `IconPoolBuilder` klasse. Vanaf daar kun je de naam van je aangepaste pool kiezen en deze gebruiken met de namen van de SVG-bestanden om aangepaste iconcomponenten te maken.

```java
// Een aangepaste pool maken genaamd "app-pool" die afbeeldingen heeft voor een logo en een avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met een gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste poolfabriek {#custom-pool-factory}

Je kunt ook een fabrieksklasse voor een aangepaste pool creëren in webforJ, net als `FeatherIcon`. Dit stelt je in staat om iconenresources binnen een specifieke pool te creëren en te beheren en maakt code-completie mogelijk.
Elke icon kan worden geïnstantieerd via de `create()` methode, die een `Icon` retourneert. De fabrieksklasse moet poolspecifieke metadata bieden, zoals de naam van de pool en de identifier van het icon, opgemaakt naar de bestandsnaam van de afbeelding.
Dit ontwerp zorgt voor gemakkelijke, gestandaardiseerde toegang tot iconen uit de aangepaste pool met behulp van enum-constanten, wat schaalbaarheid en onderhoudbaarheid in het beheer van iconen ondersteunt.

```java
/// Een fabrieksklasse voor een aangepaste pool creëren voor app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return de naam van de pool voor de iconen
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return de iconnaam
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

De volgende snippet toont de twee verschillende manieren om een aangepaste pool te gebruiken.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Maak een Icon met de namen van de aangepaste pool en afbeeldingsbestand
Icon customLogo = new Icon("logo", "app-pool");

// Maak een Icon met de aangepaste poolfabriek uit de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Iconenknoppen {#icon-buttons}
Een `Icon` component is niet selecteerbaar, maar voor acties die het beste worden weergegeven met alleen een icon, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!")
});
```

## Beste praktijken

- **Toegankelijkheid:** Gebruik een tooltip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van schermlezers.
- **Vermijd ambiguïteit:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of algemeen begrepen is. Als gebruikers moeten gissen wat het icon vertegenwoordigt, ondermijnt dat het doel.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, dus gebruik iconen alleen als ze duidelijkheid toevoegen of de complexiteit verminderen.

## Stijl
Een Icon erft het thema van zijn directe bovenliggende component, maar je kunt dit overriden door een thema rechtstreeks op een `Icon` toe te passen.

### Themas
Icon componenten komen met zeven discrete thema's ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van iconen door een app heen te personaliseren.

Hoewel er veel gebruiksscenario's zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/data.
- `DEFAULT`: Geschikt voor acties door een app heen die geen speciale aandacht vereisen en generiek zijn, zoals het in- of uitschakelen van een instelling.
- `PRIMARY`: Geschikt als een hoofddoel "oproep tot actie" op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
- `SUCCESS`: Uitstekend voor het visualiseren van het succesvolle overlijden van een element in een app, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het sucsesthema kan programatisch worden toegepast zodra een succesvolle actie is voltooid.
- `WARNING`: Handig om aan te geven dat een gebruiker op het punt staat een potentieel risicovolle actie te ondernemen, zoals navigeren weg van een pagina zonder opgeslagen wijzigingen. Deze acties zijn vaak minder ingrijpend dan die welke het gevaren thema zouden gebruiken.
- `GRAY`: Goed voor subtiele acties, zoals kleinere instellingen of acties die meer aanvullend zijn voor een pagina en geen deel uitmaken van de hoofdfunctionaliteit.
- `INFO`: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

<TableBuilder name="Icon" />
