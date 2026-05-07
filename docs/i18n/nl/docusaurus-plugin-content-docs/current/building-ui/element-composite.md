---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: c64ec386d273ab7facb974f5577afecf
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse dient als een veelzijdige basis voor het beheren van samengestelde elementen in webforJ-apps. Het primaire doel is om de interactie met HTML-elementen, vertegenwoordigd door de `Element` klasse, te vergemakkelijken door een gestructureerde aanpak te bieden voor het omgaan met eigenschappen, attributen en gebeurtenislijsten. Het stelt je in staat om elementen in een app te implementeren en hergebruiken. Gebruik de `ElementComposite` klasse wanneer je Web Componenten implementeert voor gebruik in webforJ-apps.

Bij het gebruik van de `ElementComposite` klasse geeft de `getElement()` methode je toegang tot de onderliggende `Element` component. Evenzo geeft de `getNodeName()` methode je de naam van die node in de DOM.

:::tip
Het is mogelijk om alles met de `Element` klasse zelf te doen, zonder de `ElementComposite` klasse te gebruiken. De methoden in `ElementComposite` bieden echter een manier om je werk te hergebruiken.
:::

De voorbeelden op deze pagina demonstreren hoe je de [Shoelace QR code webcomponent](https://shoelace.style/components/qr-code) kunt implementeren met behulp van de `ElementComposite` klasse.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschap- en attribuutbeschrijvingen {#property-and-attribute-descriptors}

Eigenschappen en attributen in webcomponenten vertegenwoordigen de status van de component. Ze worden vaak gebruikt om gegevens of configuratie te beheren. De `ElementComposite` klasse biedt een handige manier om met eigenschappen en attributen te werken.

Eigenschappen en attributen kunnen worden gedeclareerd en geïnitialiseerd als `PropertyDescriptor` leden van de `ElementComposite` klasse die je schrijft, en vervolgens in de code worden gebruikt. Om eigenschappen en attributen te definiëren, gebruik je de `set()` methode om de waarde van een eigenschap in te stellen. Bijvoorbeeld, `set(PropertyDescriptor<V> property, V value)` stelt een eigenschap in op een opgegeven waarde.

:::info
Eigenschappen worden intern binnen de code van de component benaderd en gemanipuleerd en worden niet weergegeven in de DOM. Attributen daarentegen maken deel uit van de externe interface van de component en kunnen worden gebruikt om informatie van buitenaf aan een component door te geven, waardoor een manier wordt geboden voor externe elementen of scripts om de component te configureren.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Voorbeeld attribuut genaamd VALUE in een ElementComposite klasse
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Mijn Titel");
set(VALUE, "Mijn Waarde");
```

Gebruik de `get()` methode in de `ElementComposite` klasse om eigenschappen te benaderen en te lezen. De `get()` methode kan een optionele `boolean` waarde accepteren, die standaard op false is ingesteld, om aan te geven of de methode een verzoek naar de client moet maken om de waarde op te halen. Dit heeft invloed op de prestaties, maar kan nodig zijn als de eigenschap puur aan de client kan worden gewijzigd.

Een `Type` kan ook aan de methode worden doorgegeven, die aangeeft waarnaar de opgehaalde resultaat moet worden gecast.

:::tip
Dit `Type` is niet per se nodig en voegt een extra laag van specificatie toe wanneer de gegevens worden opgehaald.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In de demo hieronder zijn er eigenschappen toegevoegd voor de QR-code op basis van de documentatie voor de webcomponent. Methoden zijn vervolgens geïmplementeerd waarmee gebruikers de verschillende geïmplementeerde eigenschappen kunnen krijgen en instellen.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Gebeurtenisregistratie {#event-registration}

Gebeurtenissen stellen communicatie tussen verschillende delen van je webforJ-app mogelijk. De `ElementComposite` klasse biedt gebeurtenisafhandeling met ondersteuning voor debouncing, throttling, filtering en aangepaste gegevensverzameling van gebeurtenissen.

Registreer gebeurtenislijsten met de `addEventListener()` methode:

```java
// Voorbeeld: Een klik-gebeurtenislistener toevoegen
addEventListener(ElementClickEvent.class, event -> {
  // Verwerk de klikgebeurtenis
});
```

:::info
De `ElementComposite` gebeurtenissen zijn anders dan `Element` gebeurtenissen, omdat ze geen enkele klasse toestaan, maar alleen gespecificeerde `Event` klassen.
:::

### Vooraf gebouwde gebeurtenisklassen {#built-in-event-classes}

webforJ biedt vooraf gebouwde gebeurtenisklassen met getypte gegevens toegang:

- **ElementClickEvent**: Muisklikgebeurtenissen met coördinaten (`getClientX()`, `getClientY()`), knoppeninformatie (`getButton()`), en modifiertoetsen (`isCtrlKey()`, `isShiftKey()`, enz.)
- **ElementDefinedEvent**: Geactiveerd wanneer een aangepast element in de DOM is gedefinieerd en klaar is voor gebruik
- **ElementEvent**: Basis gebeurtenisklasse die toegang biedt tot ruwe gebeurtenisgegevens, gebeurtenistype (`getType()`) en gebeurtenis-ID (`getId()`)

### Gegevens van gebeurtenissen {#event-payloads}

Gebeurtenissen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor ruwe gebeurtenisgegevens of gebruik getypte methoden wanneer beschikbaar op vooraf gebouwde gebeurtenisklassen. Voor meer details over het efficiënt gebruiken van gegevens van gebeurtenissen, zie de [Gids voor gebeurtenissen](../building-ui/events).

## Aangepaste gebeurtenisklassen {#custom-event-classes}

Voor gespecialiseerde gebeurtenishandeling, maak aangepaste gebeurtenisklassen met geconfigureerde gegevenspayloads met behulp van de annotaties `@EventName` en `@EventOptions`.

In het onderstaande voorbeeld is er een klikgebeurtenis gemaakt en vervolgens toegevoegd aan de QR-codecomponent. Deze gebeurtenis, wanneer geactiveerd, toont de "X" coördinaat van de muis op het moment van klikken op de component, die als gegevens aan de Java-gebeurtenis wordt verstrekt. Een methode wordt vervolgens geïmplementeerd om de gebruiker toegang te geven tot deze gegevens, waarmee het in de app wordt weergegeven.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` stelt je in staat om het gedrag van gebeurtenissen aan te passen door te configureren welke gegevens moeten worden verzameld, wanneer gebeurtenissen plaatsvinden en hoe ze worden verwerkt. Hier is een uitgebreide codevoorbeeld waarin alle configuratie-opties worden getoond:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Voer JavaScript uit voordat de gebeurtenis plaatsvindt
  .setCode("component.classList.add('processing');")
  
  // Alleen uitvoeren als aan voorwaarden wordt voldaan
  .setFilter("component.value.length >= 2")
  
  // Uitvoering uitstellen totdat de gebruiker stopt met typen (300 ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Prestatiebeheer {#performance-control}

Beheer wanneer en hoe vaak gebeurtenissen plaatsvinden:

**Debouncing** stelt de uitvoering uit totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300 ms na de laatste gebeurtenis
```

**Throttling** beperkt de uitvoerfrequentie:

```java
options.setThrottle(100); // Voer maximaal één keer per 100 ms uit
```

Beschikbare debounce-fasen:

- `LEADING`: Voer onmiddellijk uit, wacht dan
- `TRAILING`: Wacht op een stille periode, voer dan uit (standaard)
- `BOTH`: Voer onmiddellijk en na een stille periode uit

## Optiemerging {#options-merging}

Combineer gebeurtenisconfiguraties van verschillende bronnen met behulp van `mergeWith()`. Basisopties bieden algemene gegevens voor alle gebeurtenissen, terwijl specifieke opties gespecialiseerde configuratie toevoegen. Latere opties overschrijven conflicterende instellingen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interactie met slots {#interacting-with-slots}

Webcomponenten gebruiken vaak slots om ontwikkelaars in staat te stellen de structuur van een component van buitenaf te definiëren. Een slot is een plaatsaanduiding binnen een webcomponent die kan worden gevuld met inhoud bij het gebruik van de component. In de context van de `ElementComposite` klasse bieden slots een manier om de inhoud binnen een component aan te passen. De volgende methoden worden aangeboden om ontwikkelaars in staat te stellen om met slots te interageren en ze te manipuleren:

1. **`findComponentSlot()`**: Deze methode wordt gebruikt om naar een specifiek component in alle slots in een componentensysteem te zoeken. Het retourneert de naam van het slot waar het component zich bevindt. Als het component in geen enkel slot wordt gevonden, wordt een lege string geretourneerd.

2. **`getComponentsInSlot()`**: Deze methode haalt de lijst op van componenten die zijn toegewezen aan een bepaald slot in een componentensysteem. Optioneel kun je een specifieke klassetype doorgeven om de resultaten van de methode te filteren.

3. **`getFirstComponentInSlot()`**: Deze methode is ontworpen om het eerste component op te halen dat aan het slot is toegewezen. Optioneel kun je een specifieke klassetype doorgeven om de resultaten van deze methode te filteren.

Het is ook mogelijk om de `add()` methode met een `String` parameter te gebruiken om het gewenste slot op te geven waarin het doorgegeven component moet worden toegevoegd.

Deze interacties stellen ontwikkelaars in staat om de kracht van webcomponenten te benutten door een schone en eenvoudige API te bieden voor het manipuleren van slots, eigenschappen en het omgaan met gebeurtenissen binnen de `ElementComposite` klasse.
