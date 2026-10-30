---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: f1572fcb6d1396a20fac32bfa282c091
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De `Icon` component toont iconen die naar elke grootte schalen zonder kwaliteitsverlies. Je kunt kiezen uit drie ingebouwde iconenpools of je eigen iconen maken. Iconen dienen als visuele aanwijzingen voor navigatie en acties, waardoor de behoefte aan tekstlabels in je interface vermindert.

Elke `Icon` wordt weergegeven als een Scalable Vector Graphics (SVG) afbeelding, die op verzoek wordt geladen vanaf een content delivery network (CDN) om de latentie laag te houden. Om er een te maken, kies je een iconenpool en de naam van het icoon. Sommige iconen bieden ook de keuze tussen een omlijnde of een gevulde versie via [variaties](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wist je dat?
Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om gebruikers van betekenis te voorzien.
:::

## Pools {#pools}

Een iconenpool is een verzameling van vaak gebruikte iconen die gemakkelijk toegankelijk en herbruikbaar zijn. Door iconen uit een iconenpool te gebruiken, kun je ervoor zorgen dat de iconen in je app herkenbaar zijn en een consistente stijl delen. 
Met webforJ kun je kiezen uit drie pools, of een aangepaste pool implementeren.
Elke pool heeft een uitgebreide verzameling open source iconen die gratis te gebruiken zijn.
Het gebruik van webforJ biedt je de flexibiliteit om uit drie pools te kiezen en deze als unieke klassen te gebruiken, zonder de rompslomp van het rechtstreeks downloaden van iconen.

| Icon Pool                                         | webforJ Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler-iconen.|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het creëren van je eigen iconenpool, zie dan [Aangepaste pools maken](#creating-custom-pools).

:::

Zodra je de pool of pools hebt geselecteerd die je in je app wilt opnemen, is de volgende stap om de naam van het icoon dat je wilt gebruiken te specificeren.

## Namen {#names}

Om een icoon in je app op te nemen, heb je alleen de iconenpool en de iconennaam nodig. Blader door de iconenpoolwebsite voor het icoon dat je wilt gebruiken en gebruik de iconennaam als parameter van de `create()`-methode.
Daarnaast kun je de iconen maken via enums voor de `FeatherIcon` en `DwcIcon` klassen, waardoor ze kunnen verschijnen in de codeaanvulling.

```java
// Maak een icoon van een String naam
Icon image = TablerIcon.create("image");
// Maak een icoon van een enum
Icon image = FeatherIcon.IMAGE.create();
```

## Variaties {#variations}

Je kunt iconen nog meer personaliseren door variaties te gebruiken.
Bepaalde iconen stellen je in staat om te kiezen tussen een omlijnde of een gevulde versie, zodat je een specifiek icoon kunt benadrukken op basis van je voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

### `FontAwesomeIcon` variaties {#fontawesomeicon-variations}

1. `REGULAR`: De omlijnde variant van iconen. Dit is de standaard.
2. `SOLID`: De gevulde variant van iconen.
3. `BRAND`: De variant voor wanneer je de iconen van merken gebruikt.

### `TablerIcon` variaties {#tablericon-variations}

1. `OUTLINE`: De omlijnde variant van iconen. Dit is de standaard.
2. `FILLED`: De gevulde variant van iconen.

```java
// Een gevulde variant van een icoon van Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

De volgende demo laat zien hoe je iconen uit verschillende pools kunt gebruiken, variaties kunt toepassen, en ze naadloos in componenten kunt integreren.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Iconen aan componenten toevoegen {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is voordelig om een `Icon` aan een component toe te voegen om de bedoelde betekenis verder te verduidelijken voor gebruikers.
Componenten die de `HasPrefixAndSuffix` interface implementeren, kunnen een `Icon` of andere geldige componenten opnemen. De toegevoegde componenten kunnen worden geplaatst in de `prefix` en `suffix` slots en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met de `prefix` en `suffix` slots kun je bepalen of je het icoon vóór of na de tekst wilt plaatsen met de methoden `setPrefixComponent()` en `setSuffixComponent()`.

Beslissen of je een icoon vóór of na de tekst op een component plaatst, hangt grotendeels af van het doel en de ontwerpscontext.

### Icoonplaatsing: vóór VS na {#icon-placement-before-vs-after}

Iconen die vóór de componenttekst zijn geplaatst, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral voor universeel erkende iconen zoals het opsla-icon.
Iconen vóór de tekst van een component bieden een logische verwerkingsvolgorde, waardoor gebruikers natuurlijk worden geleid door de bedoelde actie, wat nuttig is voor knoppen waarvan de primaire functie een onmiddellijke actie is.

Aan de andere kant is het effectief om iconen na de componenttekst te plaatsen voor acties die extra context of opties bieden, waardoor duidelijkheid en aanwijzingen voor navigatie worden verbeterd.
Iconen na de tekst van een component zijn ideaal voor componenten die ofwel aanvullende informatie bieden of gebruikers in een richtingsstroom begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je een stijl kiest, houd die dan aan op je site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Aangepaste pools maken {#creating-custom-pools}

Naast het gebruik van bestaande iconenverzamelingen, heb je de optie om een aangepaste pool te creëren die kan worden gebruikt voor aangepaste logo's of avatars.
Een aangepaste pool van iconen kan worden opgeslagen in een gecentraliseerde directory of in de bronnenmap (context), wat het beheerproces van iconen vereenvoudigt.
Het hebben van een aangepaste pool maakt de app-creatie consistenter en vermindert onderhoud over verschillende componenten en modules.

Aangepaste pools kunnen worden gemaakt vanuit een map die SVG-afbeeldingen bevat en door gebruik te maken van de `IconPoolBuilder` klasse. Van daaruit kun je de naam van je aangepaste pool kiezen en deze gebruiken met de SVG-bestandsnamen om aangepaste iconencomponenten te maken.

```java
// Een aangepaste pool maken met de naam "app-pool" die afbeeldingen voor een logo en een avatar bevat.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste poolfabriek {#custom-pool-factory}

Je kunt ook een fabrieksklasse voor een aangepaste pool in webforJ maken, net als `FeatherIcon`. Dit stelt je in staat om iconenresources binnen een gespecificeerde pool te creëren en beheren en codeaanvulling mogelijk te maken.
Elk icoon kan worden aangemaakt via de `create()`-methode, die een `Icon` retourneert. De fabrieksklasse moet pool-specifieke metadata bieden, zoals de poolnaam en de identifier van het icoon, opgemaakt op basis van de bestandsnaam van de afbeelding.
Dit ontwerp maakt gemakkelijke, gestandaardiseerde toegang tot iconen van de aangepaste pool mogelijk met behulp van enum-constanten, ondersteunend aan schaalbaarheid en onderhoudbaarheid bij het beheren van iconen.

```java
/// Een fabrieksklasse voor een aangepaste pool voor app-pool aanmaken
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return de poolnaam voor de iconen
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return de iconennaam
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

// Maak een Icon aan met de namen van de aangepaste pool en de afbeeldingsbestand
Icon customLogo = new Icon("logo", "app-pool");

// Maak een Icon aan met behulp van de aangepaste poolfabriek uit de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icoonknoppen <DocChip chip="name" label="dwc-icon-button" /> {#icon-buttons}
Een `Icon` component is niet-selecteerbaar, maar voor acties die het beste alleen met een icoon kunnen worden weergegeven, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!")
  });
```

## Beste praktijken

- **Toegankelijkheid:** Gebruik een tooltip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van schermlezers.
- **Vermijd ambiguïteit:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of algemeen begrepen is. Als gebruikers moeten raden wat het icoon vertegenwoordigt, heeft het geen nut.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, gebruik dus alleen iconen wanneer ze duidelijkheid of vermindering van complexiteit toevoegen.

## Styling
Een icoon neemt het thema van zijn directe bovenliggende component over, maar je kunt dit overschrijven door een thema rechtstreeks op een `Icon` toe te passen.

### Thema's
Icooncomponenten zijn uitgerust met zeven afzonderlijke thema's die zijn ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van iconen in een app aan te passen.

Hoewel er veel gebruiksscenario's zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden van gebruik:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
- `DEFAULT`: Geschikt voor acties in een app die geen speciale aandacht vereisen en generiek zijn, zoals het toggelen van een instelling.
- `PRIMARY`: Geschikt als een belangrijke "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan of naar een andere pagina gaan.
- `SUCCESS`: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een app, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programmatically worden toegepast zodra een succesvolle actie is voltooid.
- `WARNING`: Nuttig om aan te geven dat een gebruiker een potentieel risicovolle actie onderneemt, zoals het navigeren weg van een pagina met onopgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die waarvoor het Danger-thema zou worden gebruikt.
- `GRAY`: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina, en geen deel uitmaken van de hoofdfunctionaliteit.
- `INFO`: Goed voor het geven van aanvullende verduidelijkende informatie aan een gebruiker.

<TableBuilder name={['Icon', 'IconButton']} />
