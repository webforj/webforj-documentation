---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: c526ee2878756d5dd13fa2972dfef56e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De `Icon` component toont iconen die op elke grootte kunnen worden geschaald zonder kwaliteitsverlies. Je kunt kiezen uit drie ingebouwde iconenpools of zelf iconen maken. Iconen dienen als visuele aanwijzingen voor navigatie en acties, waardoor de behoefte aan tekstlabels in je interface vermindert.

Elke `Icon` wordt weergegeven als een Scalable Vector Graphics (SVG) afbeelding, on-demand geladen vanaf een content delivery network (CDN) om de latency laag te houden. Om er eentje te maken, kies je een iconenpool en de naam van een icoon. Sommige iconen bieden ook de keuze tussen een omtrek- of een gevulde versie via [variaties](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wist je dat?
Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om de betekenis voor eindgebruikers over te brengen.
:::

## Pools {#pools}

Een iconenpool is een verzameling van vaak gebruikte iconen die eenvoudige toegang en hergebruik mogelijk maakt. Door iconen uit een iconenpool te gebruiken, kun je ervoor zorgen dat de iconen in je app herkenbaar zijn en een consistente stijl delen. Het gebruik van webforJ stelt je in staat om uit drie pools te kiezen, of een aangepaste pool te implementeren. Elke pool heeft een uitgebreide verzameling open source iconen die gratis te gebruiken zijn. Het gebruik van webforJ geeft je de flexibiliteit om uit drie pools te kiezen en ze als unieke klassen te gebruiken, zonder de moeite van het rechtstreeks downloaden van iconen.

| Iconenpool                                         | webforJ Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler iconen.|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het maken van je eigen iconenpool, kijk dan naar [Aangepaste pools maken](#creating-custom-pools).

:::

Zodra je de pool of pools hebt geselecteerd die je in je app wilt opnemen, is de volgende stap het specificeren van de naam van het icoon dat je wilt gebruiken.

## Namen {#names}

Om een icoon in je app op te nemen, heb je alleen de iconenpool en de naam van het icoon nodig. Blader door de iconenpoolwebsite voor het icoon dat je wilt gebruiken en gebruik de naam van het icoon als de parameter van de `create()` methode. Daarnaast kun je de iconen maken via enums voor de `FeatherIcon` en `DwcIcon` klassen, waardoor ze kunnen verschijnen in code-completie.

```java
// Maak een icoon van een String naam
Icon image = TablerIcon.create("image");
// Maak een icoon van een enum
Icon image = FeatherIcon.IMAGE.create();
```

## Variaties {#variations}

Je kunt iconen nog persoonlijker maken door gebruik te maken van variaties. Bepaalde iconen stellen je in staat om te kiezen tussen een omtrek of een gevulde versie, waarmee je een specifiek icoon kunt benadrukken op basis van je voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

### `FontAwesomeIcon` variaties {#fontawesomeicon-variations}

1. `REGULAR`: De omtrekvariatie van iconen. Dit is de standaard.
2. `SOLID`: De gevulde variatie van iconen.
3. `BRAND`: De variatie wanneer je de iconen van merken gebruikt.

### `TablerIcon` variaties {#tablericon-variations}

1. `OUTLINE`: De omtrekvariatie van iconen. Dit is de standaard.
2. `FILLED`: De gevulde variatie van iconen.

```java
// Een gevulde variatie van een icoon van Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

De volgende demo illustreert hoe je iconen uit verschillende pools kunt gebruiken, variaties kunt toepassen en ze naadloos kunt integreren in componenten.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Iconen aan componenten toevoegen {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is nuttig om een `Icon` aan een component toe te voegen om de bedoelde betekenis voor gebruikers verder te verduidelijken. Componenten die de `HasPrefixAndSuffix` interface implementeren, kunnen een `Icon` of andere geldige componenten bevatten. De toegevoegde componenten kunnen worden geplaatst in de `prefix` en `suffix` slots en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met behulp van `prefix` en `suffix` slots kun je bepalen of je het icoon vóór of na de tekst wilt plaatsen met de `setPrefixComponent()` en `setSuffixComponent()` methoden.

Beslissen of je een icoon vóór of na de tekst op een component plaatst, hangt grotendeels af van het doel en de ontwerpcontext.

### Icoonplaatsing: vóór VS na {#icon-placement-before-vs-after}

Iconen die vóór de tekst van de component zijn gepositioneerd, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral voor universeel erkende iconen zoals het opslaan-icoon. Iconen vóór de tekst van een component bieden een logische verwerkingvolgorde en begeleiden gebruikers natuurlijk naar de bedoelde actie, wat voordelig is voor knoppen waarvan de primaire functie een onmiddellijke actie is.

Aan de andere kant is het effectief om iconen na de tekst van de component te plaatsen voor acties die extra context of opties bieden, wat de duidelijkheid en aanwijzingen voor navigatie vergroot. Iconen na de tekst van een component zijn ideaal voor componenten die aanvullende informatie bieden of gebruikers in een richting begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je een stijl kiest, houd deze dan aan op je site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Aangepaste pools maken {#creating-custom-pools}

Naast het gebruik van bestaande iconenverzamelingen heb je de optie om een aangepaste pool te maken die kan worden gebruikt voor aangepaste logo's of avatars. Een aangepaste pool van iconen kan worden opgeslagen in een centrale map of in de map resources (context), wat het iconenbeheer vereenvoudigt. Het hebben van een aangepaste pool maakt de app-ontwikkeling consistenter en vermindert het onderhoud over verschillende componenten en modules.

Aangepaste pools kunnen worden gemaakt vanaf een map met SVG-afbeeldingen door gebruik te maken van de `IconPoolBuilder` klasse. Van daaruit kun je de naam van je aangepaste pool kiezen en deze gebruiken met de SVG-bestandsnamen om aangepaste iconencomponenten te maken.

```java
// Een aangepaste pool maken genaamd "app-pool" die afbeeldingen voor een logo en een avatar bevat.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste poolfabriek {#custom-pool-factory}

Je kunt ook een fabrieksklasse voor een aangepaste pool maken in webforJ, net als `FeatherIcon`. Dit stelt je in staat om iconenbronnen binnen een specifieke pool te maken en te beheren en biedt mogelijkheden voor code-completie. Elk icoon kan worden geïnstantieerd via de `create()` methode, die een `Icon` retourneert. De fabrieksklasse moet pool-specifieke metadata bieden, zoals de naam van de pool en de identifier van het icoon, opgemaakt naar de bestandsnaam van de afbeelding. Dit ontwerp maakt gemakkelijke, gestandaardiseerde toegang tot iconen uit de aangepaste pool mogelijk met behulp van enum-constanten, wat de schaalbaarheid en onderhoudbaarheid van het iconenbeheer ondersteunt.

```java
/// Een aangepaste poolfabriek maken voor app-pool
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
   * @return de naam van het icoon
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

// Maak een Icon met de namen van de aangepaste pool en het afbeeldingsbestand
Icon customLogo = new Icon("logo", "app-pool");

// Maak een Icon met behulp van de aangepaste poolfabriek uit de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icoonknoppen {#icon-buttons}
Een `Icon` component is niet-selecteerbaar, maar voor acties die het beste met alleen een icoon worden weergegeven, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!")
  });
```

## Beste praktijken

- **Toegankelijkheid:** Gebruik een tooltip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van schermlezers.
- **Voorkom verwarring:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of algemeen begrepen is. Als gebruikers moeten raden waar het icoon voor staat, ondermijnt dat het doel.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, gebruik iconen alleen wanneer ze duidelijkheid toevoegen of complexiteit verminderen.

## Styling
Een Icoon erft het thema van zijn directe oudercomponent, maar je kunt dit overschrijven door een thema rechtstreeks op een `Icon` toe te passen.

### Thema's
Iconencomponenten komen met zeven afzonderlijke thema's die zijn ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om het uiterlijk van iconen in een app aan te passen.

Hoewel er veel gebruikssituaties voor elk van de verschillende thema's zijn, zijn hier enkele voorbeelden:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
- `DEFAULT`: Geschikt voor acties in een app die geen speciale aandacht vereisen en algemeen zijn, zoals het toggelen van een instelling.
- `PRIMARY`: Geschikt als een primaire "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan of verder gaan naar een andere pagina.
- `SUCCESS`: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een app, zoals de indiening van een formulier of de voltooiing van een aanmeldingsproces. Het succes-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
- `WARNING`: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel risicovolle actie uit te voeren, zoals navigeren naar een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Danger-thema zouden gebruiken.
- `GRAY`: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina en geen deel uitmaken van de hoofdfunctionaliteit.
- `INFO`: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

<TableBuilder name={['Icon', 'IconButton']} />
