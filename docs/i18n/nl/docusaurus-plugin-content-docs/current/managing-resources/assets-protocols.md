---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: a7482285684e797c3cfc30d025a95482
---
webforJ ondersteunt aangepaste assetprotocollen die gestructureerde en efficiënte toegang tot middelen mogelijk maken. Deze protocollen abstraheren de complexiteit van de statische en dynamische middelenver retrieval, zodat assets naadloos binnen de app worden opgehaald en geïntegreerd.

## Het webserverprotocol {#the-webserver-protocol}

Het **`ws://`** protocol stelt je in staat om assets op te halen die gehost zijn in de statische map van een webforJ-app. Alle bestanden die zich bevinden binnen de app-classpath `src/main/resources/static` zijn rechtstreeks toegankelijk vanuit de browser. Als je bijvoorbeeld een bestand hebt met de naam **css/app.css** in **resources/static**, kan dit worden benaderd op: **`/static/css/app.css`**  

Het **ws://** protocol verwijdert de noodzaak om het `static` voorvoegsel in je resource-URL's hardcoded in te voeren, waardoor het veilig is tegen veranderde voorvoegsels afhankelijk van de implementatiecontext. Als de web-app onder een context anders dan de root is geïmplementeerd, zoals **/mycontext**, zou de URL voor **css/app.css** zijn: **`/mycontext/static/css/app.css`**  

:::tip Configureren van het statische voorvoegsel
Je kunt het `static` voorvoegsel beheersen met de [webforj-configuratie](../configuration/properties#configuration-options) optie `webforj.assetsDir`. Deze instelling specificeert de route naam die gebruikt wordt om statische bestanden te serveren, terwijl **de fysieke map de naam `static` behoudt**. Dit is bijzonder nuttig als de standaard statische route in conflict komt met een route in jouw app, omdat het je in staat stelt de routenaam te wijzigen zonder de mapnaam te hoeven wijzigen.
:::

Je kunt de <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> hulpprogramma-class gebruiken om een gegeven webserver-URL op te lossen. Dit kan nuttig zijn als je een aangepast component hebt dat dat protocol moet ondersteunen.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Het contextprotocol {#the-context-protocol}

Het contextprotocol verwijst naar middelen binnen de classpath van de app op `src/main/resources`. Sommige resource API-methoden en annotaties ondersteunen dit protocol om de inhoud van een bestand in de resources-map te lezen en de inhoud naar de browser te verzenden. De `InlineJavaScript` annotatie, bijvoorbeeld, maakt het mogelijk om de inhoud van een JavaScript-bestand vanuit de resources-map te lezen en inline weer te geven aan de clientzijde.

Bijvoorbeeld:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Het iconenprotocol {#the-icons-protocol}

Het **`icons://`** protocol biedt een dynamische benadering van iconenbeheer, waardoor het efficiënt en flexibel is voor het genereren en serveren van iconen in [installeerbare apps](../configuration/installable-apps). Dit protocol stelt je in staat om iconen on-the-fly te genereren op basis van de gevraagde bestandsnaam en parameters, waardoor in veel gevallen de behoefte aan vooraf gegenereerde iconen wordt geëlimineerd.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Basisicoon {#base-icon}

Wanneer een icoon wordt aangevraagd met het `icons://` protocol, wordt een basisafbeelding dynamisch afgeleid van de gevraagde iconenbestandsnaam. Dit zorgt voor consistentie in het iconontwerp en maakt automatische terugval naar een standaardafbeelding mogelijk als er geen basisicoon wordt opgegeven.

- **Voorbeeld 1:** Aanvraag: `/icons/icon-192x192.png` → Basisicoon: `resources/icons/icon.png`
- **Voorbeeld 2:** Aanvraag: `/icons/icon-different-192x192.png` → Basisicoon: `resources/icons/icon-different.png`

Als er geen basisafbeelding bestaat voor het aangevraagde icoon, wordt het standaard webforJ-logo gebruikt als terugval.

:::tip `webforj.iconsDir`
Standaard serveert webforJ iconen vanuit de `resources/icons/` directory. Je kunt de eindpuntnaam wijzigen door de `webforj.iconsDir` eigenschap in het webforJ-configuratiebestand in te stellen. Dit verandert alleen de URL-eindpunt die wordt gebruikt om toegang tot de iconen te krijgen, niet de daadwerkelijke mapnaam. Het standaard eindpunt is `icons/`. 
:::

### Iconen Overschrijven {#overriding-icons}

Je kunt specifieke iconenformaten overschrijven door vooraf gegenereerde afbeeldingen in de `resources/icons/` directory te plaatsen. Dit biedt meer controle over het uiterlijk van iconen wanneer bepaalde formaten of stijlen moeten worden aangepast.

- **Voorbeeld:** Als `resources/icons/icon-192x192.png` bestaat, zal dit rechtstreeks worden geleverd in plaats van dynamisch te worden gegenereerd.

### Het uiterlijk van iconen aanpassen {#customizing-icon-appearance}

Het `icons://` protocol ondersteunt aanvullende parameters die je in staat stellen het uiterlijk van dynamisch gegenereerde iconen aan te passen. Dit omvat opties voor padding en achtergrondkleur.

- **Padding**: De parameter `padding` kan worden gebruikt om de padding rond het icoon te beheersen. Geaccepteerde waarden variëren van `0`, wat geen padding betekent, tot `1`, wat maximale padding betekent.
  - **Voorbeeld:** `/icons/icon-192x192.png?padding=0.3`
  
- **Achtergrondkleur**: De parameter `background` stelt je in staat om de achtergrondkleur van het icoon in te stellen. Het ondersteunt de volgende waarden:
  - **`transparent`**: Geen achtergrondkleur.
  <!-- vale off -->
  - **Hexadecimale kleurcodes**: Aangepaste achtergrondkleuren (bijv. `FFFFFF` voor wit).
  <!-- vale on -->
  - **`auto`**: Probeert automatisch de achtergrondkleur van het icoon te detecteren.

  Bijvoorbeeld: 
  
  - **Voorbeeld 1:** `/icons/icon-192x192.png?background=000000`
  - **Voorbeeld 2:** `/icons/icon-192x192.png?background=auto`
