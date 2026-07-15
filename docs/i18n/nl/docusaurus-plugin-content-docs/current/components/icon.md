---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: 0e51ecab262c62fb63cd767ba8167084
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De `Icon` component toont iconen die naar elke grootte kunnen schalen zonder kwaliteitsverlies. Je kunt kiezen uit drie ingebouwde iconenpools of je eigen iconen maken. Iconen dienen als visuele aanduidingen voor navigatie en acties, waardoor de behoefte aan tekstlabels in je interface vermindert.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Elke `Icon` is ontworpen als een Scalable Vector Graphics (SVG) afbeelding, wat betekent dat deze gemakkelijk kan worden geschaald naar elke grootte zonder verlies van helderheid of kwaliteit. Bovendien worden `Icon` componenten op aanvraag geladen van een content delivery network (CDN), wat helpt om de latentie te verminderen en de algehele prestaties te verbeteren.

Bij het maken van een `Icon` moet je een specifieke pool en de naam van het icon zelf identificeren. Sommige iconen bieden ook de keuze tussen een omrand of een vullen versie via [variaties](#variations).

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wist je dat?
Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om betekenis over te brengen aan eindgebruikers.
:::

### Pools {#pools}

Een iconenpool is een verzameling veelgebruikte iconen diegemakkelijk toegang en hergebruik mogelijk maakt. Door iconen uit een iconenpool te gebruiken, zorg je ervoor dat de iconen in je app herkenbaar zijn en een consistente stijl delen. Het gebruik van webforJ stelt je in staat om uit drie pools te kiezen of een aangepaste pool te implementeren. Elke pool heeft een uitgebreide verzameling open-source iconen die gratis te gebruiken zijn. Het gebruik van webforJ geeft je de flexibiliteit om uit drie pools te kiezen en deze als unieke klassen te gebruiken, zonder de moeite om een van de iconen direct te downloaden.

| Icon Pool                                         | webforJ Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler-iconen.|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het maken van je eigen iconenpool, zie [Een aangepaste pool maken](#creating-custom-pools).

:::

Zodra je de pool of pools hebt geselecteerd die je in je app wilt opnemen, is de volgende stap om de naam van het icon dat je wilt gebruiken op te geven.

### Namen {#names}

Om een icon in je app op te nemen, heb je alleen de iconenpool en de iconnaam nodig. Blader door de iconenpool-website voor het icon dat je wilt gebruiken en gebruik de iconnaam als parameter van de `create()` methode. Daarnaast kun je de iconen creëren via enums voor de `FeatherIcon` en `DwcIcon` klassen, zodat ze verschijnen in de code-aanvulling.

```java
// Maak een icon aan van een String naam
Icon image = TablerIcon.create("image");
// Maak een icon aan van een enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaties {#variations}

Je kunt iconen zelfs verder personaliseren door variaties te gebruiken. Bepaalde iconen stellen je in staat om te kiezen tussen een omrand of een gevuld versie, zodat je een specifiek icon kunt benadrukken op basis van je voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

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

De volgende demo illustreert hoe je iconen uit verschillende pools kunt gebruiken, variaties kunt toepassen en ze naadloos in componenten kunt integreren.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Iconen toevoegen aan componenten {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is voordelig om een `Icon` toe te voegen aan een component om de bedoelde betekenis verder te verduidelijken aan gebruikers. Componenten die de interface `HasPrefixAndSuffix` implementeren kunnen een `Icon` of andere geldige componenten bevatten. De toegevoegde componenten kunnen worden geplaatst in de `prefix` en `suffix` slots en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met behulp van `prefix` en `suffix` slots kun je bepalen of je het icon voor of na de tekst wilt hebben met behulp van de `setPrefixComponent()` en `setSuffixComponent()` methoden.

Beslissen of je een icon voor of na de tekst op een component plaatst, hangt grotendeels af van het doel en de ontwerpcontext.

### Icon plaatsing: voor VS na {#icon-placement-before-vs-after}

Iconen die vóór de componenttekst zijn gepositioneerd, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral voor universeel erkende iconen zoals het opslaan icon. Iconen vóór de tekst van een component bieden een logische verwerkingsvolgorde, wat gebruikers op een natuurlijke manier door de bedoelde actie leidt, wat voordelig is voor knoppen waarvan de primaire functie een onmiddellijke actie is.

Aan de andere kant is het effectief om iconen na de componenttekst te plaatsen voor acties die extra context of opties bieden, wat de duidelijkheid en aanwijzingen voor navigatie verbetert. Iconen na de tekst van een component zijn ideaal voor componenten die ofwel aanvullende informatie bieden of gebruikers in een richtingsstroom begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je voor een stijl kiest, houd deze dan aan op je site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Aangepaste pools maken {#creating-custom-pools}

Naast het gebruik van bestaande iconenverzamelingen heb je de optie om een aangepaste pool te creëren die kan worden gebruikt voor aangepaste logo's of avatars. Een aangepaste pool van iconen kan worden opgeslagen in een gecentraliseerde directory of in de resources map (context), waardoor het beheersen van iconen eenvoudiger wordt. Een aangepaste pool zorgt voor consistentie bij het maken van apps en vermindert het onderhoud over verschillende componenten en modules.

Aangepaste pools kunnen worden gemaakt van een map met SVG-afbeeldingen en door de `IconPoolBuilder` klasse te gebruiken. Van daaruit kun je de naam van je aangepaste pool kiezen en deze gebruiken met de SVG-bestandsnamen om aangepaste icon componenten te creëren.

```java
// Een aangepaste pool maken genaamd "app-pool" die afbeeldingen voor een logo en een avatar bevat.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste pool factory {#custom-pool-factory}

Je kunt ook een factory-klasse voor een aangepaste pool in webforJ maken, net als `FeatherIcon`. Dit stelt je in staat om iconenbronnen binnen een specifieke pool te creëren en te beheren en maakt code-aanvulling mogelijk. Elk icon kan worden geïnstantieerd via de `create()` methode, die een `Icon` retourneert. De factory-klasse moet pool-specifieke metadata bieden, zoals de naam van de pool en de identificatie van het icon, geformatteerd naar de bestandsnaam van de afbeelding. Dit ontwerp zorgt voor een gemakkelijke, gestandaardiseerde toegang tot iconen uit de aangepaste pool met behulp van enum-constanten, wat schaalbaarheid en onderhoudbaarheid in het iconbeheer ondersteunt.

```java
/// Een custom pool factory maken voor app-pool
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
   * @return de naam van het icon
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

// Maak een icon aan met de namen van de aangepaste pool en afbeeldingsbestand
Icon customLogo = new Icon("logo", "app-pool");

// Maak een icon aan met de custom pool factory uit de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icon knoppen {#icon-buttons}
Een `Icon` component is niet-selecteerbaar, maar voor acties die het beste worden weergegeven met alleen een icon, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!")
  });
```

## Beste praktijken

- **Toegankelijkheid:** Gebruik een tooltip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van schermlezers.
- **Vermijd ambiguïteit:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of breed begrepen is. Als gebruikers moeten raden wat het icon voorstelt, dan heeft het geen nut.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, gebruik iconen dus alleen wanneer ze duidelijkheid toevoegen of complexiteit verminderen.

## Stijlen
Een Icon erfde het thema van zijn directe bovenliggende component, maar je kunt dit overschrijven door een thema rechtstreeks op een `Icon` toe te passen.

### Thema's
Icon componenten komen met zeven discrete thema's ingebouwd voor snelle stijlen zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van iconen in een app aan te passen.

Hoewel er veel gebruiksmogelijkheden zijn voor elk van de verschillende thema's, zijn hier enkele voorbeeldtoepassingen:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/data.
- `DEFAULT`: Geschikt voor acties in een app die geen speciale aandacht vereisen en generiek zijn, zoals het toggelen van een instelling.
- `PRIMARY`: Geschikt als een belangrijke "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan, of naar een andere pagina gaan.
- `SUCCESS`: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een app, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes-thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
- `WARNING`: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel riskante actie uit te voeren, zoals het navigeren van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Danger-thema zouden gebruiken.
- `GRAY`: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina, en niet deel uitmaken van de belangrijkste functionaliteit.
- `INFO`: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

<TableBuilder name={['Icon', 'IconButton']} />
