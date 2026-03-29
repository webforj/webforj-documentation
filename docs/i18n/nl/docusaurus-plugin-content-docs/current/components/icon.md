---
title: Icon
sidebar_position: 55
_i18n_hash: 8350df59fb9ce335776bc0556861cda5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

De `Icon` component toont iconen die in elke grootte kunnen worden geschaald zonder kwaliteitsverlies. Je kunt kiezen uit drie ingebouwde iconensets of aangepaste iconen maken. Iconen dienen als visuele aanwijzingen voor navigatie en acties, waardoor de behoefte aan tekstlabels in je interface vermindert.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Elke `Icon` is ontworpen als een schaalbare vectorafbeelding (SVG), wat betekent dat deze gemakkelijk kan worden geschaald naar elke omvang zonder helderheid of kwaliteit te verliezen. Bovendien worden `Icon` componenten op aanvraag geladen vanuit een content delivery network (CDN), wat helpt om de latentie te verminderen en de algehele prestaties te verbeteren.

Wanneer je een `Icon` aanmaakt, moet je een specifieke set en de naam van het icoon zelf identificeren. Sommige iconen bieden ook de keuze tussen een omrand of een gevuld exemplaar via [variaties](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Wist je dat?
Sommige componenten, zoals `PasswordField` en `TimeField`, hebben ingebouwde iconen om betekenis over te brengen aan eindgebruikers.
:::

### Pools {#pools}

Een iconenpool is een verzameling van veelgebruikte iconen die gemakkelijke toegang en herbruikbaarheid mogelijk maakt. Door iconen uit een iconenpool te gebruiken, kun je ervoor zorgen dat de iconen in je app herkenbaar zijn en een consistente stijl delen. Door gebruik te maken van webforJ heb je de keuze uit drie pools of kun je een aangepaste pool implementeren. Elke pool heeft een uitgebreide verzameling open source iconen die gratis te gebruiken zijn. Met webforJ heb je de flexibiliteit om uit drie pools te kiezen en ze als unieke klassen te gebruiken, zonder dat je iconen rechtstreeks hoeft te downloaden.

| Iconenpool                                        | webforJ Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` en `DwcIcon`.<br/>`DwcIcon` is een subset van de Tabler-iconen.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Als je geïnteresseerd bent in het maken van je eigen iconenpool, zie [Eigen pools maken](#creating-custom-pools).

:::

Zodra je de pool of pools hebt geselecteerd die je in je app wilt opnemen, is de volgende stap om de naam van het icoon op te geven dat je wilt gebruiken.

### Namen {#names}

Om een icoon in je app op te nemen, heb je alleen de iconenpool en de naam van het icoon nodig. Blader op de website van de iconenpool voor het icoon dat je wilt gebruiken en gebruik de naam van het icoon als parameter van de `create()` methode. Daarnaast kun je de iconen maken via enums voor de `FeatherIcon` en `DwcIcon` klassen, waardoor ze verschijnen in de code-completie.

```java
// Creëer een icoon van een String naam
Icon image = TablerIcon.create("image");
// Creëer een icoon van een enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variaties {#variations}

Je kunt iconen nog persoonlijker maken door gebruik te maken van variaties. Bepaalde iconen stellen je in staat om te kiezen tussen een omrand of een gevuld exemplaar, zodat je een specifiek icoon kunt benadrukken op basis van jouw voorkeur. `FontAwesomeIcon` en `Tabler` iconen bieden variaties.

#### `FontAwesomeIcon` variaties {#fontawesomeicon-variations}

1. `REGULAR`: De omrand variatie van iconen. Dit is de standaard.
2. `SOLID`: De gevulde variatie van iconen.
3. `BRAND`: De variatie voor wanneer je de iconen van merken gebruikt.

#### `TablerIcon` variaties {#tablericon-variations}

1. `OUTLINE`: De omrand variatie van iconen. Dit is de standaard.
2. `FILLED`: De gevulde variatie van iconen.

```java
// Een gevulde variatie van een icoon van Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

De volgende demo toont hoe je iconen uit verschillende pools kunt gebruiken, variaties kunt toepassen en ze naadloos in componenten kunt integreren.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Iconen toevoegen aan componenten {#adding-icons-to-components}

Integreer iconen in je componenten met behulp van slots. Slots bieden flexibele opties om componenten nuttiger te maken. Het is voordelig om een `Icon` aan een component toe te voegen om de beoogde betekenis verder te verduidelijken voor gebruikers. Componenten die de interface `HasPrefixAndSuffix` implementeren, kunnen een `Icon` of andere geldige componenten bevatten. De toegevoegde componenten kunnen worden geplaatst in de `prefix` en `suffix` slots en kunnen zowel het algehele ontwerp als de gebruikerservaring verbeteren.

Met behulp van de `prefix` en `suffix` slots kun je bepalen of je het icoon voor of na de tekst wilt plaatsen met de methoden `setPrefixComponent()` en `setSuffixComponent()`.

De beslissing om een icoon voor of na de tekst op een component te plaatsen, hangt grotendeels af van het doel en de ontwerpscontext.

### Icoonplaatsing: voor VS na {#icon-placement-before-vs-after}

Iconen die voor de componenttekst zijn gepositioneerd, helpen gebruikers snel de primaire actie of het doel van de component te begrijpen, vooral voor universeel erkende iconen zoals het opsla-icoon. Iconen vóór de tekst van een component bieden een logische verwerkingsvolgorde, waardoor gebruikers op een natuurlijke manier door de beoogde actie worden geleid, wat gunstig is voor knoppen waarvan de belangrijkste functie een directe actie is.

Aan de andere kant is het effectief om iconen na de tekst van de component te plaatsen voor acties die extra context of opties bieden, wat de helderheid en aanwijzingen voor navigatie verbetert. Iconen na de tekst van een component zijn ideaal voor componenten die aanvullende informatie bieden of gebruikers in een richtingstroom begeleiden.

Uiteindelijk is consistentie de sleutel. Zodra je een stijl hebt gekozen, onderhoud deze dan door je hele site voor een samenhangend en gebruiksvriendelijk ontwerp.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Eigen pools maken {#creating-custom-pools}

Naast het gebruik van bestaande iconenverzamelingen heb je de optie om een aangepaste pool te creëren die kan worden gebruikt voor aangepaste logo's of avatarafbeeldingen. Een aangepaste pool van iconen kan worden opgeslagen in een gecentraliseerde directory of in de resources map (context), waardoor het beheer van iconen wordt vereenvoudigd. Een aangepaste pool maakt de app-creatie consistenter en vermindert onderhoud over verschillende componenten en modules.

Aangepaste pools kunnen worden gemaakt vanuit een map met SVG-afbeeldingen en door gebruik te maken van de `IconPoolBuilder` klasse. Van daaruit kun je de naam van je aangepaste pool kiezen en deze gebruiken met de bestandsnamen van de SVG-bestanden om aangepaste iconencomponenten te creëren.

```java
// Een aangepaste pool aanmaken genaamd "app-pool" met afbeeldingen voor een logo en een avatar.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Zorg ervoor dat je iconen ontwerpt met gelijke breedte en hoogte, aangezien `Icon` componenten zijn ontworpen om een vierkante ruimte in te nemen.
:::

### Aangepaste poolfabriek {#custom-pool-factory}

Je kunt ook een fabrieksklasse voor een aangepaste pool in webforJ maken, net als `FeatherIcon`. Dit maakt het mogelijk om iconenbronnen binnen een bepaalde pool te creëren en te beheren en maakt code-completie mogelijk. Elk icoon kan worden geïnstantieerd via de `create()` methode, die een `Icon` retourneert. De fabrieksklasse moet poolspecifieke metadata bieden, zoals de naam van de pool en de identificatie van het icoon, opgemaakt naar de bestandsnaam van de afbeelding. Dit ontwerp maakt een gemakkelijke, gestandaardiseerde toegang tot iconenassets vanuit de aangepaste pool mogelijk met behulp van enum-constanten, wat de schaalbaarheid en onderhoudbaarheid in het iconenbeheer ondersteunt.

```java
/// Een aangepaste poolfabriek voor app-pool maken
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

// Maak een icoon aan met de namen van de aangepaste pool en de afbeeldingsbestand
Icon customLogo = new Icon("logo", "app-pool");

// Maak een icoon aan met de aangepaste poolfabriek van de vorige snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icoonknoppen {#icon-buttons}
Een `Icon` component is niet selecteerbaar, maar voor acties die het beste worden weergegeven met alleen een icoon, zoals meldingen of waarschuwingen, kun je de `IconButton` gebruiken.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Je hebt een nieuw bericht!", "Ding Dong!")
  });
```

## Beste praktijken

- **Toegankelijkheid:** Gebruik een tooltip of een label op iconen om je app toegankelijk te maken voor visueel gehandicapte gebruikers die afhankelijk zijn van screenreaders.
- **Vermijd ambiguïteit:** Vermijd het gebruik van iconen als de betekenis niet duidelijk of algemeen begrepen is. Als gebruikers moeten raden wat het icoon vertegenwoordigt, verliest het zijn doel.
- **Gebruik iconen spaarzaam:** Te veel iconen kunnen gebruikers overweldigen, gebruik iconen alleen wanneer ze helderheid toevoegen of complexiteit verminderen.

## Stijlen
Een icoon erfde het thema van zijn directe bovenliggende component, maar je kunt dit overschrijven door een thema rechtstreeks op een `Icon` toe te passen.

### Thema's
Icon componenten worden geleverd met zeven afzonderlijke thema's die zijn ingebouwd voor snelle styling zonder het gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die op iconen kunnen worden toegepast om hun uiterlijk en visuele presentatie te veranderen. Ze bieden een snelle en consistente manier om de look van iconen door een app heen aan te passen.

Hoewel er veel gebruiksmogelijkheden zijn voor elk van de verschillende thema's, zijn hier enkele voorbeelden van gebruik:

- `DANGER`: Het beste voor acties met ernstige gevolgen, zoals het wissen van ingevulde informatie of het permanent verwijderen van een account/gegevens.
- `DEFAULT`: Geschikt voor acties door een app die geen speciale aandacht vereisen en algemeen zijn, zoals het toggelen van een instelling.
- `PRIMARY`: Geschikt als een hoofd "call-to-action" op een pagina, zoals aanmelden, wijzigingen opslaan of doorgaan naar een andere pagina.
- `SUCCESS`: Uitstekend voor het visualiseren van de succesvolle voltooiing van een element in een app, zoals het indienen van een formulier of het voltooien van een aanmeldproces. Het succes thema kan programmatisch worden toegepast zodra een succesvolle actie heeft plaatsgevonden.
- `WARNING`: Nuttig om aan te geven dat een gebruiker op het punt staat een potentieel riskante actie uit te voeren, zoals het navigeren van een pagina met niet-opgeslagen wijzigingen. Deze acties zijn vaak minder impactvol dan diegene die het Danger-thema zouden gebruiken.
- `GRAY`: Goed voor subtiele acties, zoals kleine instellingen of acties die meer aanvullend zijn voor een pagina, en geen deel uitmaken van de hoofdfunctionaliteit.
- `INFO`: Goed voor het bieden van aanvullende verduidelijkende informatie aan een gebruiker. 

<TableBuilder name="Icon" />
