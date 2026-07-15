---
sidebar_position: 2
title: Assets Protocols
description: >-
  Reference app resources with the webforJ ws, context, and icons protocols to
  load static files, classpath content, and dynamic icons.
_i18n_hash: 3928ba255cb9887c80c20f904baf62b8
---
webforJ ondersteunt aangepaste activa-protocollen die gestructureerde en efficiënte toegang tot bronnen mogelijk maken. Deze protocollen abstraheren de complexiteit van statische en dynamische brontoegang, zodat activa naadloos binnen de app kunnen worden opgehaald en geïntegreerd.

## Het webserverprotocol {#the-webserver-protocol}

Het **`ws://`** protocol stelt je in staat om activa op te halen die zijn gehost in de statische map van een webforJ-app. Alle bestanden die zich bevinden binnen het app-classpath `src/main/resources/static` zijn rechtstreeks toegankelijk vanuit de browser. Als je bijvoorbeeld een bestand met de naam **css/app.css** hebt in **resources/static**, kan het worden benaderd op: **`/static/css/app.css`**

Het **ws://** protocol verwijdert de noodzaak om het `static`-prefx hardcoded in je resource-URLs op te nemen, waardoor je beschermd bent tegen veranderende prefixes afhankelijk van de implementatiecontext. Als de webapp onder een context anders dan de root is gedeployed, zoals **/mycontext**, zou de URL voor **css/app.css** zijn: **`/mycontext/static/css/app.css`**

:::tip Configureren van het statische prefix
Je kunt het `static`-prefix beheersen met de [webforj-configuratie](../configuration/properties#configuration-options) optie `webforj.assetsDir`. Deze instelling specificeert de route naam die wordt gebruikt om statische bestanden te serveren, terwijl **de fysieke map `static` genoemd blijft**. Het is vooral nuttig als de standaard statische route in conflict komt met een route in je app, omdat dit je in staat stelt de routenaam te wijzigen zonder de map te hernoemen.
:::

Je kunt de <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> hulpprogramma klasse gebruiken om een gegeven webserver-URL op te lossen. Dit kan handig zijn als je een aangepast component hebt dat dat protocol moet ondersteunen.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Het contextprotocol {#the-context-protocol}

Het contextprotocol mappt naar bronnen binnen het classpath van de app op `src/main/resources`. Sommige resource API-methoden en annotaties ondersteunen dit protocol om de inhoud van een bestand in de resources-map te lezen en deze inhoud naar de browser te sturen. Bijvoorbeeld, de `InlineJavaScript` annotatie stelt je in staat om de inhoud van een JavaScript-bestand vanuit de resources-map te lezen en deze inline aan de clientzijde te plaatsen.

Bijvoorbeeld:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Het iconenprotocol {#the-icons-protocol}

Het **`icons://`** protocol biedt een dynamische benadering van iconenbeheer, waardoor het efficiënt en flexibel is voor het genereren en serveren van iconen in [installeerbare apps](../configuration/installable-apps). Dit protocol stelt je in staat om iconen on-the-fly te genereren op basis van de aangevraagde bestandsnaam en parameters, waardoor de noodzaak voor vooraf geassembleerde iconen in veel gevallen wordt geëlimineerd.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Basisicoon {#base-icon}

Wanneer een icoon wordt aangevraagd met het `icons://` protocol, wordt een basisafbeelding dynamisch afgeleid van de aangevraagde icoonbestandsnaam. Dit zorgt voor consistentie in het iconontwerp en maakt automatisch gebruik van een standaardafbeelding als er geen basisicoon wordt geleverd.

- **Voorbeeld 1:** Aanvraag: `/icons/icon-192x192.png` → Basisicoon: `resources/icons/icon.png`
- **Voorbeeld 2:** Aanvraag: `/icons/icon-different-192x192.png` → Basisicoon: `resources/icons/icon-different.png`

Als er geen basisafbeelding bestaat voor het aangevraagde icoon, wordt het standaard webforJ-logo gebruikt als fallback.

:::tip `webforj.iconsDir`
Standaard serveert webforJ iconen vanuit de `resources/icons/` directory. Je kunt de endpointnaam wijzigen door de eigenschap `webforj.iconsDir` in het webforJ-configuratiebestand in te stellen. Dit verandert alleen de URL-endpoint die wordt gebruikt om toegang te krijgen tot de iconen, niet de feitelijke mapnaam. De standaard endpoint is `icons/`.
:::

### Iconen overschrijven {#overriding-icons}

Je kunt specifieke iconformaten overschrijven door vooraf geassembleerde afbeeldingen in de `resources/icons/` directory te plaatsen. Dit biedt meer controle over het uiterlijk van iconen wanneer bepaalde formaten of stijlen moeten worden aangepast.

- **Voorbeeld:** Als `resources/icons/icon-192x192.png` bestaat, zal deze rechtstreeks worden bediend in plaats van dynamisch te worden gegenereerd.

### Het uiterlijk van iconen aanpassen {#customizing-icon-appearance}

Het `icons://` protocol ondersteunt aanvullende parameters waarmee je het uiterlijk van dynamisch gegenereerde iconen kunt aanpassen. Dit omvat opties voor opvulling en achtergrondkleur.

- **Opvulling**: De parameter `padding` kan worden gebruikt om de opvulling rond het icoon te beheersen. Geaccepteerde waarden variëren van `0`, wat geen opvulling betekent, tot `1`, wat maximale opvulling betekent.
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
