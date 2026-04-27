---
title: Icon
sidebar_position: 55
_i18n_hash: 5c32d2def53818005b15e22290fb3d52
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De `Icon` component toont iconen die naar elke grootte kunnen worden geschaald zonder kwaliteitsverlies. Je kunt kiezen uit drie ingebouwde iconenpools of zelf iconen creëren. Iconen dienen als visuele aanwijzingen voor navigatie en acties, waardoor de behoefte aan tekstlabels in je interface vermindert.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Elke `Icon` is ontworpen als een Scalable Vector Graphics (SVG) afbeelding, wat betekent dat deze eenvoudig naar elke grootte kan worden geschaald zonder helderheid of kwaliteit te verliezen. Bovendien worden `Icon` componenten op aanvraag geladen vanuit een content delivery network (CDN), wat helpt om latentie te verminderen en de algehele prestaties te verbeteren.

Bij het creëren van een `Icon` moet je een specifieke pool en de naam van het icon zelf identificeren. Sommige iconen bieden ook de keuze tussen een omrande of een gevulde versie via [variaties](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Wist je dat?
Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om betekenis over te brengen aan eindgebruikers.
:::

### Pools {#pools}

Een iconenpool is een verzameling veelgebruikte iconen die gemakkelijke toegang en hergebruik mogelijk maakt. Door iconen uit een iconenpool te gebruiken, kun je ervoor zorgen dat de iconen in je app herkenbaar zijn en een consistente stijl delen. Het gebruik van webforJ stelt je in staat om te kiezen uit drie pools of een aangepaste pool te implementeren. Elke pool heeft een uitgebreide collectie open source iconen die gratis te gebruiken zijn. Het gebruik van webforJ geeft je de flexibiliteit om uit drie pools te kiezen en ze als unieke klassen te gebruiken, zonder dat je de iconen direct hoeft te downloaden.

| Icon Pool                                         | webforJ Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler iconen.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het creëren van je eigen iconenpool, zie [Creëren van aangepaste pools](#creating-custom-pools).

:::

Zodra je de pool of pools hebt geselecteerd die je in je app wilt opnemen, is de volgende stap om de naam van het icon dat je wilt gebruiken specifiek te maken.

### Namen {#names}

Om een icon in je app op te nemen, heb je alleen de iconenpool en de naam van het icon nodig. Blader door de website van de iconenpool voor het icon dat je wilt gebruiken, en gebruik de naam van het icon als parameter van de `create()` methode. Bovendien kun je de iconen via enums voor de `FeatherIcon` en `DwcIcon` klassen creëren, zodat ze in de codevoltooiing verschijnen.

```java
// Creëer een icon vanuit een String naam
Icon image = TablerIcon.create("image");
// Creëer een icon vanuit een enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaties {#variations}

Je kunt iconen nog verder personaliseren door variaties te gebruiken. Bepaalde iconen stellen je in staat om te kiezen tussen een omrande of een gevulde versie, waardoor je een specifiek icon kunt benadrukken op basis van je voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

#### `FontAwesomeIcon` variaties {#fontawesomeicon-variations}

1. `REGULAR`: De omrande variatie van iconen. Dit is de standaard.
2. `SOLID`: De gevulde variatie van iconen.
3. `BRAND`: De variatie voor wanneer je de iconen van merken gebruikt.

#### `TablerIcon` variaties {#tablericon-variations}

1. `OUTLINE`: De omrande variatie van iconen. Dit is de standaard.
2. `FILLED`: De gevulde variatie van iconen.

```java
// Een gevulde variatie van een icon van Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

De volgende demo illustreert hoe je iconen van verschillende pools kunt gebruiken, variaties kunt toepassen en ze naadloos in componenten kunt integreren.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Iconen aan componenten toevoegen {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is voordelig om een `Icon` aan een component toe te voegen om de bedoelde betekenis voor gebruikers verder te verduidelijken. Componenten die de `HasPrefixAndSuffix` interface implementeren, kunnen een `Icon` of andere geldige componenten bevatten. De toegevoegde componenten kunnen in de `prefix` en `suffix` slots worden geplaatst en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met behulp van `prefix` en `suffix` slots kun je bepalen of je het icon voor of na de tekst wilt plaatsen, met de methoden `setPrefixComponent()` en `setSuffixComponent()`.

De beslissing om een icon voor of na de tekst op een component te plaatsen, hangt grotendeels af van het doel en de ontwerpscontext.

### Plaatsing van iconen: voor VS na {#icon-placement-before-vs-after}

Iconen die vóór de componenttekst zijn geplaatst, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral voor universeel erkende iconen zoals het opslaan-icoon. Iconen vóór de tekst van een component bieden een logische verwerkingsvolgorde, waardoor gebruikers op een natuurlijke manier door de bedoelde actie worden geleid, wat voordelig is voor knoppen waarvan de primaire functie een onmiddellijke actie is.

Aan de andere kant is het effectief om iconen na de componenttekst te plaatsen voor acties die extra context of opties bieden, waardoor duidelijkheid en aanwijzingen voor navigatie worden verbeterd. Iconen na de tekst van een component zijn ideaal voor componenten die aanvullende informatie bieden of gebruikers in een richtingsstroom begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je een stijl kiest, behoud deze dan op je hele site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Aangepaste pools creëren {#creating-custom-pools}

Naast het gebruiken van bestaande iconenverzamelingen, heb je de optie om een aangepaste pool te creëren die kan worden gebruikt voor aangepaste logo's of avatars. Een aangepaste pool van iconen kan in een gecentraliseerde directory of in de bronnenmap (context) worden opgeslagen, wat het iconbeheerproces vereenvoudigt. Het hebben van een aangepaste pool maakt de app-creatie consistenter en vermindert het onderhoud over verschillende componenten en modules.

Aangepaste pools kunnen worden gemaakt vanuit een map met SVG-afbeeldingen en door gebruik te maken van de `IconPoolBuilder` klasse. Van daaruit kun je de naam van je aangepaste pool kiezen en deze gebruiken met de SVG-bestandsnamen om aangepaste iconcomponenten te creëren.

```java
// Een aangepaste pool genaamd "app-pool" creëren die afbeeldingen heeft voor een logo en een avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste pool-fabriek {#custom-pool-factory}

Je kunt ook een fabrieksklasse voor een aangepaste pool in webforJ creëren, net zoals `FeatherIcon`. Dit stelt je in staat om iconenbronnen binnen een specifieke pool te maken en te beheren en maakt codevoltooiing mogelijk. Elk icon kan worden geïnstantieerd via de `create()` methode, die een `Icon` retourneert. De fabrieksklasse moet poolspecifieke metadata bieden, zoals de naam van de pool en de identifier van het icon, geformatteerd naar de bestandsnaam van de afbeelding. Dit ontwerp zorgt voor gemakkelijke, gestandaardiseerde toegang tot iconenactiva vanuit de aangepaste pool met behulp van enum-constanten, wat de schaalbaarheid en onderhoudbaarheid in iconbeheer ondersteunt.

```java
/// Een aangepaste poolfabriek creëren voor app-pool
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

// Creëer een Icon met de namen van de aangepaste pool en het afbeeldingsbestand
Icon customLogo = new Icon("logo", "app-pool");

// Creëer een Icon met behulp van de aangepaste poolfabriek uit de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Iconknoppen {#icon-buttons}
Een `Icon` component is niet selecteerbaar, maar voor acties die het beste met alleen een icon kunnen worden weergegeven, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!")
  });
```

## Best practices

- **Toegankelijkheid:** Gebruik een tool tip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van schermlezers.
- **Vermijd ambiguïteit:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of algemeen begrepen is. Als gebruikers moeten raden wat het icon betekent, dan doet het afbreuk aan het doel.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, dus gebruik alleen iconen wanneer ze duidelijkheid toevoegen of complexiteit verminderen.

## Styling
Een Icon erft het thema van zijn directe bovenliggende component, maar je kunt dit overschrijven door een thema direct op een `Icon` toe te passen.

### Thema's
Iconcomponenten komen met zeven discrete thema's ingebouwd voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de uitstraling van iconen door een app heen te customiseren.

Hoewel er veel gebruikstoepassingen zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
- `DEFAULT`: Geschikt voor acties door een app heen die geen speciale aandacht vereisen en generiek zijn, zoals het toggelen van een instelling.
- `PRIMARY`: Geschikt als een hoofdpunt "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
- `SUCCESS`: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een app, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes thema kan programmaticaal worden toegepast zodra een succesvolle actie is voltooid.
- `WARNING`: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel risicovolle actie uit te voeren, zoals navigeren vanuit een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder ingrijpend dan die welke het Danger-thema zouden gebruiken.
- `GRAY`: Goed voor subtiele acties, zoals kleinere instellingen of acties die meer aanvullend zijn aan een pagina, en geen deel uitmaken van de hoofdfunctionaliteit.
- `INFO`: Goed voor het geven van aanvullende verduidelijkende informatie aan een gebruiker.

<TableBuilder name={['Icon', 'IconButton']} />
