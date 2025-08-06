---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: b37158687b15dc2b94a7543624eaa09f
---
webforJ ondersteunt aangepaste assetprotocolen die gestructureerde en efficiënte toegang tot bronnen mogelijk maken. Deze protocollen abstraheren de complexiteit van statische en dynamische resource-ophalingen, waardoor ervoor gezorgd wordt dat assets naadloos binnen de app worden opgehaald en geïntegreerd.

## Het webserverprotocol {#the-webserver-protocol}

Het **`ws://`** protocol stelt je in staat om assets op te halen die gehost zijn in de statische map van een webforJ-app. Alle bestanden die zich binnen het app-classpath `src/main/resources/static` bevinden, zijn rechtstreeks toegankelijk vanuit de browser. Als je bijvoorbeeld een bestand met de naam **css/app.css** hebt in **resources/static**, kan het worden geopend op: **`/static/css/app.css`**  

Het **ws://** protocol elimineert de noodzaak om het `static` voorvoegsel in je resource-URL's hardcoded te hebben, en beschermt tegen gewijzigde voorvoegsels afhankelijk van de implementatiecontext. Als de webapp onder een andere context dan de root is geïmplementeerd, zoals **/mycontext**, zou de URL voor **css/app.css** zijn: **`/mycontext/static/css/app.css`**  

:::tip Het statische voorvoegsel configureren
Je kunt het `static` voorvoegsel beheersen met de [webforj-configuratie](../configuration/properties#configuration-options) optie `webforj.assetsDir`. Deze instelling specificeert de routenaam die wordt gebruikt om statische bestanden te serveren, terwijl **de fysieke map genaamd `static` blijft**. Dit is bijzonder nuttig als de standaard statische route in conflict komt met een route in je app, omdat het je in staat stelt om de routenaam te wijzigen zonder de map te hernoemen.
:::

Je kunt de <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> hulpprogrammaclasse gebruiken om een gegeven webserver-URL op te lossen. Dit kan nuttig zijn als je een aangepast component hebt dat dat protocol moet ondersteunen.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Het contextprotocol {#the-context-protocol}

Het contextprotocol is gekoppeld aan bronnen binnen het classpath van de app op `src/main/resources`. Sommige resource-API-methoden en annotaties ondersteunen dit protocol om de inhoud van een bestand in de resources-map te lezen en deze inhoud naar de browser te verzenden. De `InlineJavaScript` annotatie bijvoorbeeld, maakt het mogelijk om de inhoud van een JavaScript-bestand vanuit de resources-map te lezen en deze aan de clientzijde in te voegen.

Bijvoorbeeld:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Het iconenprotocol {#the-icons-protocol}

Het **`icons://`** protocol biedt een dynamische benadering van iconenbeheer, waardoor het efficiënt en flexibel is voor het genereren en serveren van iconen in [installeerbare apps](../configuration/installable-apps). Dit protocol stelt je in staat om iconen on-the-fly te genereren op basis van de aangevraagde bestandsnaam en parameters, waardoor de noodzaak voor vooraf gegenereerde iconen in veel gevallen vervalt.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Basisicoon {#base-icon}

Wanneer een icoon wordt aangevraagd met behulp van het `icons://` protocol, wordt een basisafbeelding dynamisch afgeleid van de aangevraagde ikonbestandsnaam. Dit zorgt voor consistentie in het ontwerp van iconen en maakt automatische terugval naar een standaardafbeelding mogelijk als er geen basisicoon wordt opgegeven.

- **Voorbeeld 1:** Aanvraag: `/icons/icon-192x192.png` → Basisicoon: `resources/icons/icon.png`
- **Voorbeeld 2:** Aanvraag: `/icons/icon-different-192x192.png` → Basisicoon: `resources/icons/icon-different.png`

Als er geen basisafbeelding bestaat voor het aangevraagde icoon, wordt het standaard webforJ-logo als back-up gebruikt.

:::tip `webforj.iconsDir`
Standaard serveren webforJ iconen vanuit de `resources/icons/` directory. Je kunt de endpointnaam wijzigen door de `webforj.iconsDir`-eigenschap in het webforJ-configuratiebestand in te stellen. Dit verandert alleen de URL-endpoint die wordt gebruikt om toegang te krijgen tot de iconen, niet de werkelijke mapnaam. De standaardendpoint is `icons/`. 
:::

### Iconen Overschrijven {#overriding-icons}

Je kunt specifieke iconformaten overschrijven door vooraf gegenereerde afbeeldingen in de `resources/icons/` map te plaatsen. Dit biedt meer controle over het uiterlijk van iconen wanneer bepaalde formaten of stijlen moeten worden aangepast.

- **Voorbeeld:** Als `resources/icons/icon-192x192.png` bestaat, wordt deze rechtstreeks geserveerd in plaats van dynamisch te worden gegenereerd.

### Het uiterlijk van iconen aanpassen {#customizing-icon-appearance}

Het `icons://` protocol ondersteunt extra parameters waarmee je het uiterlijk van dynamisch gegenereerde iconen kunt aanpassen. Dit omvat opties voor padding en achtergrondkleur.

- **Padding**: De `padding` parameter kan worden gebruikt om de padding rond het icoon te beheersen. Geaccepteerde waarden variëren van `0`, wat betekent dat er geen padding is, tot `1`, wat betekent dat er maximale padding is.
  - **Voorbeeld:** `/icons/icon-192x192.png?padding=0.3`
  
- **Achtergrondkleur**: De `background` parameter stelt je in staat om de achtergrondkleur van het icoon in te stellen. Het ondersteunt de volgende waarden:
  - **`transparent`**: Geen achtergrondkleur.
  <!-- vale off -->
  - **Hexadecimale kleurcodes**: Aangepaste achtergrondkleuren (bijv. `FFFFFF` voor wit).
  <!-- vale on -->
  - **`auto`**: Probeert automatisch de achtergrondkleur van het icoon te detecteren.

  Bijvoorbeeld: 
  
  - **Voorbeeld 1:** `/icons/icon-192x192.png?background=000000`
  - **Voorbeeld 2:** `/icons/icon-192x192.png?background=auto`
