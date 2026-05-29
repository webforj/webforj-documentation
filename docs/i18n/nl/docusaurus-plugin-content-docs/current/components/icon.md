---
title: Icon
sidebar_position: 55
_i18n_hash: ae46080226d89087113b901c748f0942
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De `Icon` component toont iconen die zonder kwaliteitsverlies naar elke grootte kunnen worden geschaald. Je kunt kiezen uit drie ingebouwde iconenpools of je eigen iconen maken. Iconen dienen als visuele aanwijzingen voor navigatie en acties, waardoor de behoefte aan tekstlabels in je interface vermindert.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Elke `Icon` is ontworpen als een Scalable Vector Graphics (SVG) afbeelding, wat betekent dat het gemakkelijk naar elke grootte kan worden geschaald zonder verlies van helderheid of kwaliteit.
Bovendien worden `Icon` componenten op aanvraag geladen van een content delivery network (CDN), wat helpt de latentie te verminderen en de algehele prestaties te verbeteren.

Wanneer je een `Icon` maakt, moet je een specifieke pool en de naam van het icon zelf identificeren.
Sommige iconen bieden ook de keuze tussen een omrande of een gevulde versie via [variaties](#variations).

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wist je dat?
Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om betekenis aan eindgebruikers over te brengen.
:::

### Pools {#pools}

Een iconenpool is een verzameling veelgebruikte iconen die gemakkelijke toegang en hergebruik mogelijk maakt. Door iconen uit een iconenpool te gebruiken, kun je ervoor zorgen dat de iconen in je app herkenbaar zijn en een consistente stijl delen.
Met webforJ kun je kiezen uit drie pools of een aangepaste pool implementeren.
Elke pool heeft een uitgebreide collectie open source iconen die gratis te gebruiken zijn.
Het gebruik van webforJ geeft je de flexibiliteit om uit drie pools te kiezen en ze als unieke klassen te gebruiken, zonder dat je de iconen rechtstreeks hoeft te downloaden.

| Icon Pool                                         | webforJ Class |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler iconen.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het maken van je eigen iconenpool, zie [Aangepaste pools maken](#creating-custom-pools).

:::

Zodra je de pool of pools hebt geselecteerd om in je app op te nemen, is de volgende stap om de naam van het gewenste icoon op te geven.

### Namen {#names}

Om een icon in je app op te nemen, heb je alleen de iconenpool en de naam van het icoon nodig. Blader door de website van de iconenpool naar het icoon dat je wilt gebruiken en gebruik de naam van het icoon als parameter van de `create()` methode.
Daarnaast kun je de iconen maken via enums voor de `FeatherIcon` en `DwcIcon` klassen, zodat ze in code-completion kunnen verschijnen.

```java
// Maak een icoon van een String naam
Icon image = TablerIcon.create("image");
// Maak een icoon van een enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaties {#variations}

Je kunt iconen nog meer personaliseren door variaties te gebruiken.
Bepaalde iconen bieden je de keuze tussen een omrande of een gevulde versie, zodat je een specifiek icoon kunt benadrukken op basis van jouw voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

#### `FontAwesomeIcon` variaties {#fontawesomeicon-variations}

1. `REGULAR`: De omrande variant van iconen. Dit is de standaardversie.
2. `SOLID`: De gevulde variant van iconen.
3. `BRAND`: De variant voor het gebruik van merksiconen.

#### `TablerIcon` variaties {#tablericon-variations}

1. `OUTLINE`: De omrande variant van iconen. Dit is de standaardversie.
2. `FILLED`: De gevulde variant van iconen.

```java
// Een gevulde variant van een icoon van Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

De volgende demo illustreert hoe je iconen uit verschillende pools kunt gebruiken, variaties kunt toepassen en ze naadloos in componenten kunt integreren.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Iconen toevoegen aan componenten {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is voordelig om een `Icon` aan een component toe te voegen om de bedoelde betekenis verder te verduidelijken voor gebruikers.
Componenten die de interface `HasPrefixAndSuffix` implementeren, kunnen een `Icon` of andere geldige componenten bevatten. De toegevoegde componenten kunnen in de `prefix` en `suffix` slots worden geplaatst en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met behulp van `prefix` en `suffix` slots kun je bepalen of je het icoon vóór of na de tekst wilt met de `setPrefixComponent()` en `setSuffixComponent()` methoden.

Beslissen of je een icoon vóór of na de tekst op een component plaatst, hangt grotendeels af van het doel en de ontwerpcontext.

### Icoonplaatsing: voor VS na {#icon-placement-before-vs-after}

Iconen die vóór de tekst van de component zijn geplaatst, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral voor universeel erkende iconen zoals het opslaan-icoon.
Iconen vóór de tekst van een component bieden een logische verwerkingsvolgorde, waardoor gebruikers natuurlijk door de bedoelde actie worden geleid, wat voordelig is voor knoppen waarvan de primaire functie een onmiddellijke actie is.

Aan de andere kant is het plaatsen van iconen na de tekst van de component effectief voor acties die extra context of opties bieden, waardoor duidelijkheid en aanwijzingen voor navigatie worden versterkt.
Iconen na de tekst van een component zijn ideaal voor componenten die ofwel aanvullende informatie bieden of gebruikers in een richting begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je een stijl kiest, houd deze dan aan op je hele site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Aangepaste pools maken {#creating-custom-pools}

Naast het gebruik van bestaande iconenverzamelingen, heb je de mogelijkheid om een aangepaste pool te maken die kan worden gebruikt voor aangepaste logo's of avatar's.
Een aangepaste pool van iconen kan worden opgeslagen in een gecentraliseerde map of in de resources map (context), wat het beheer van iconen vereenvoudigt.
Het hebben van een aangepaste pool maakt de app-creatie consistenter en vermindert het onderhoud over verschillende componenten en modules.

Aangepaste pools kunnen worden gemaakt vanuit een map met SVG-afbeeldingen en door gebruik te maken van de `IconPoolBuilder` klasse. Van daaruit kun je de naam van je aangepaste pool kiezen en dat gebruiken met de SVG-bestandnamen om aangepaste iconcomponenten te maken.

```java
// Een aangepaste pool aanmaken genaamd "app-pool" die afbeeldingen voor een logo en een avatar bevat.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste poolfabriek {#custom-pool-factory}

Je kunt ook een fabrieksklasse voor een aangepaste pool maken in webforJ, net als `FeatherIcon`. Dit stelt je in staat om iconenbronnen binnen een specifieke pool te creëren en te beheren en code-completion mogelijk te maken.
Elke icoon kan worden geïnstantieerd via de `create()` methode, die een `Icon` retourneert. De fabrieksklasse moet pool-specifieke metadata bieden, zoals de poolnaam en de identifier van het icoon, geformatteerd volgens de afbeeldingsbestandsnaam.
Dit ontwerp maakt gemakkelijke, gestandaardiseerde toegang tot iconen vanuit de aangepaste pool mogelijk met behulp van enum-constanten, ter ondersteuning van schaalbaarheid en onderhoudbaarheid in iconenbeheer.

```java
/// Een fabrieksklasse voor een aangepaste pool aanmaken voor app-pool
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

// Maak een Icon met behulp van de aangepaste poolfabriek uit de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icoonknoppen {#icon-buttons}
Een `Icon` component is niet-selecteerbaar, maar voor acties die het beste worden weergegeven met slechts een icoon, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!")
  });
```

## Beste praktijken

- **Toegankelijkheid:** Gebruik een tooltip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van schermlezers.
- **Vermijd ambiguïteit:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of algemeen begrepen is. Als gebruikers moeten raden wat het icoon voorstelt, verliest het zijn doel.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, dus gebruik iconen alleen wanneer ze duidelijkheid bieden of de complexiteit verminderen.

## Stijling
Een Icoon erft het thema van zijn directe bovenliggende component, maar je kunt dit overschrijven door een thema rechtstreeks op een `Icon` toe te passen.

### Thema's
Iconcomponenten worden geleverd met zeven discrete thema's die zijn ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van iconen in een app aan te passen.

Hoewel er veel gebruiksscenario's zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden van gebruik:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/data.
- `DEFAULT`: Geschikt voor acties door de app die geen speciale aandacht vereisen en algemeen zijn, zoals het in-/uitschakelen van een instelling.
- `PRIMARY`: Geschikt als een belangrijke "call-to-action" op een pagina, zoals inschrijven, wijzigingen opslaan of verdergaan naar een andere pagina.
- `SUCCESS`: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een app, zoals het indienen van een formulier of het voltooien van een aanmeldingsproces. Het succesvolle thema kan programmatisch worden toegepast zodra een succesvolle actie is voltooid.
- `WARNING`: Handig om aan te geven dat een gebruiker op het punt staat een potentieel risicovolle actie uit te voeren, zoals het navigeren weg van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan die welke het Danger-thema zouden gebruiken.
- `GRAY`: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina, en niet deel uitmaken van de belangrijkste functionaliteit.
- `INFO`: Goed voor het verstrekken van aanvullende verduidelijkende informatie aan een gebruiker.

<TableBuilder name={['Icon', 'IconButton']} />
