---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: de075e855ba84ee82ec08c2bef771ea8
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse dient als een veelzijdige basis voor het beheren van samengestelde elementen in webforJ-apps. Het primaire doel is om de interactie met HTML-elementen, vertegenwoordigd door de `Element` klasse, te vergemakkelijken door een gestructureerde aanpak te bieden voor het afhandelen van eigenschappen, attributen en gebeurtenislister. Het maakt de implementatie en hergebruik van elementen in een app mogelijk. Gebruik de `ElementComposite` klasse bij het implementeren van Web Components voor gebruik in webforJ-apps.

Bij gebruik van de `ElementComposite` klasse geeft de `getElement()` methode je toegang tot de onderliggende `Element` component. Evenzo geeft de `getNodeName()` methode je de naam van die knoop in de DOM.

:::tip
Het is mogelijk om alles met de `Element` klasse zelf te doen, zonder de `ElementComposite` klasse te gebruiken. De methoden in `ElementComposite` bieden echter een manier om je werk te hergebruiken.
:::

De voorbeelden op deze pagina demonstreren hoe je de [Shoelace QR-code webcomponent](https://shoelace.style/components/qr-code) kunt implementeren met behulp van de `ElementComposite` klasse.

<ComponentDemo
path='/webforj/qrdemo'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java']}
height='175px'
/>

## Eigenschap- en attributdescriptoren {#property-and-attribute-descriptors}

Eigenschappen en attributen in webcomponenten vertegenwoordigen de toestand van de component. Ze worden vaak gebruikt om gegevens of configuratie te beheren. De `ElementComposite` klasse biedt een handige manier om met eigenschappen en attributen te werken.

Eigenschappen en attributen kunnen worden gedeclareerd en geïnitialiseerd als `PropertyDescriptor` leden van de te schrijven `ElementComposite` klasse, en vervolgens in de code worden gebruikt. Om eigenschappen en attributen te definiëren, gebruik je de `set()` methode om de waarde van een eigenschap in te stellen. Bijvoorbeeld, `set(PropertyDescriptor<V> property, V value)` stelt een eigenschap in op een opgegeven waarde.

:::info
Eigenschappen worden intern binnen de code van de component benaderd en gemanipuleerd en zijn niet zichtbaar in de DOM. Attributen daarentegen maken deel uit van de externe interface van de component en kunnen worden gebruikt om informatie van buitenaf in een component door te geven, waardoor een manier wordt geboden voor externe elementen of scripts om de component te configureren.
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

Gebruik de `get()` methode in de `ElementComposite` klasse om toegang te krijgen tot en eigenschappen te lezen. De `get()` methode kan een optionele `boolean` waarde accepteren, die standaard false is, om te bepalen of de methode een verzoek naar de client moet doen om de waarde op te halen. Dit heeft invloed op de prestaties, maar kan noodzakelijk zijn als de eigenschap uitsluitend aan de client kan worden aangepast.

Een `Type` kan ook naar de methode worden doorgegeven, wat bepaalt naar welk type het opgehaalde resultaat moet worden geconverteerd.

:::tip
Dit `Type` is niet per se noodzakelijk en voegt een extra laag van specificatie toe terwijl de gegevens worden opgehaald.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In de demo hieronder zijn eigenschappen toegevoegd voor de QR-code op basis van de documentatie voor de webcomponent. Methoden zijn vervolgens geïmplementeerd waarmee gebruikers de verschillende geïmplementeerde eigenschappen kunnen ophalen en instellen.

<ComponentDemo
path='/webforj/qrproperties'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java']}
height='250px'
/>

## Evenementregistratie {#event-registration}

Evenementen stellen communicatie tussen verschillende delen van je webforJ-app mogelijk. De `ElementComposite` klasse biedt gebeurtenisafhandeling met ondersteuning voor debouncing, throttling, filtering en het verzamelen van aangepaste gebeurtenisgegevens.

Registreer gebeurtenislijstners met de methode `addEventListener()`:

```java
// Voorbeeld: Een click-gebeurtenis listener toevoegen
addEventListener(ElementClickEvent.class, event -> {
  // Verwerk de klikgebeurtenis
});
```

:::info
De `ElementComposite` evenementen zijn anders dan de `Element` evenementen, in die zin dat ze geen enkele klasse toestaan, maar alleen specifieke `Event` klassen.
:::

### Ingebouwde gebeurtenisklassen {#built-in-event-classes}

webforJ biedt vooraf gebouwde gebeurtenisklassen met getypte gegevens toegang:

- **ElementClickEvent**: Muisklikgebeurtenissen met coördinaten (`getClientX()`, `getClientY()`), knopinformatie (`getButton()`), en modifier-toetsen (`isCtrlKey()`, `isShiftKey()`, enz.)
- **ElementDefinedEvent**: Wordt geactiveerd wanneer een aangepast element in de DOM is gedefinieerd en klaar voor gebruik
- **ElementEvent**: Basis gebeurtenisklasse die toegang biedt tot ruwe gebeurtenisgegevens, gebeurtenistype (`getType()`) en gebeurtenis-ID (`getId()`)

### Gebeurtenispayloads {#event-payloads}

Evenementen dragen gegevens van de client naar je Java-code. Toegang tot deze gegevens via `getData()` voor ruwe gebeurtenisgegevens of gebruik getypte methoden wanneer beschikbaar op ingebouwde gebeurtenisklassen. Voor meer details over het efficiënt gebruiken van gebeurtenispayloads, zie de [Evenementenhandleiding](../building-ui/events).

## Aangepaste gebeurtenisklassen {#custom-event-classes}

Voor gespecialiseerde gebeurtenisafhandeling, maak aangepaste gebeurtenisklassen met geconfigureerde payloads met behulp van `@EventName` en `@EventOptions` annotaties.

In het onderstaande voorbeeld is een click-gebeurtenis gemaakt en vervolgens toegevoegd aan de QR-code component. Deze gebeurtenis, wanneer geactiveerd, zal de "X" coördinaat van de muis weergeven op het moment van klikken op de component, die als gegevens aan de Java-gebeurtenis wordt verstrekt. Een methode wordt vervolgens geïmplementeerd om de gebruiker toegang te geven tot deze gegevens, wat is hoe het in de app wordt weergegeven.

<ComponentDemo
path='/webforj/qrevent'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java']}
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` stelt je in staat om het gedrag van gebeurtenissen aan te passen door te configureren welke gegevens te verzamelen, wanneer gebeurtenissen worden geactiveerd en hoe ze worden verwerkt. Hier is een uitgebreide codefragment met alle configuratie opties:

```java
ElementEventOptions options = new ElementEventOptions()
  // Verzamel aangepaste gegevens van de client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Voer JavaScript uit voordat de gebeurtenis wordt geactiveerd
  .setCode("component.classList.add('processing');")
  
  // Alleen activeren als aan voorwaarden is voldaan
  .setFilter("component.value.length >= 2")
  
  // Uitvoering uitstellen totdat de gebruiker stopt met typen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Prestatiecontrole {#performance-control}

Beheer wanneer en hoe vaak gebeurtenissen worden geactiveerd:

**Debouncing** stelt de uitvoering uit totdat de activiteit stopt:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Wacht 300ms na de laatste gebeurtenis
```

**Throttling** beperkt de frequentie van uitvoering:

```java
options.setThrottle(100); // Maximaal één keer per 100ms activeren
```

Beschikbare debounceregio's:

- `LEADING`: Direct activeren, dan wachten
- `TRAILING`: Wacht op een rustige periode, dan activeren (standaard)
- `BOTH`: Direct activeren en na een rustige periode

## Opties samenvoegen {#options-merging}

Combineer gebeurtenisconfiguraties uit verschillende bronnen met `mergeWith()`. Basisopties bieden gemeenschappelijke gegevens voor alle gebeurtenissen, terwijl specifieke opties gespecialiseerde configuratie toevoegen. Latere opties overschrijven conflicterende instellingen.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interacting with slots {#interacting-with-slots}

Webcomponenten gebruiken vaak slots om ontwikkelaars in staat te stellen de structuur van een component van buitenaf te definiëren. Een slot is een tijdelijke aanduiding binnen een webcomponent die kan worden ingevuld met inhoud bij het gebruik van de component. In de context van de `ElementComposite` klasse bieden slots een manier om de inhoud binnen een component aan te passen. De volgende methoden zijn beschikbaar om ontwikkelaars in staat te stellen om met slots te interageren en deze te manipuleren:

1. **`findComponentSlot()`**: Deze methode wordt gebruikt om naar een specifiek component in alle slots van een componentensysteem te zoeken. Het retourneert de naam van de slot waar het component zich bevindt. Als het component niet in een slot wordt gevonden, wordt een lege string geretourneerd.

2. **`getComponentsInSlot()`**: Deze methode haalt de lijst op van componenten die aan een bepaalde slot in een componentensysteem zijn toegekend. Optioneel kun je een specifieke klassetype doorgeven om de resultaten van de methode te filteren.

3. **`getFirstComponentInSlot()`**: Deze methode is ontworpen om het eerste component op te halen dat aan de slot is toegewezen. Optioneel kun je een specifieke klassetype doorgeven om de resultaten van deze methode te filteren.

Het is ook mogelijk om de `add()` methode te gebruiken met een `String` parameter om de gewenste slot aan te geven waarin het doorgegeven component moet worden toegevoegd.

Deze interacties stellen ontwikkelaars in staat om de kracht van webcomponenten te benutten door een schone en eenvoudige API te bieden voor het manipuleren van slots, eigenschappen en het afhandelen van gebeurtenissen binnen de `ElementComposite` klasse.
