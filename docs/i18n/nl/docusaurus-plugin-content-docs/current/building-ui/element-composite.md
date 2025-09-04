---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 78629dd08e77cbd5f111aabb094f8db8
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

De `ElementComposite` klasse dient als een veelzijdige basis voor het beheren van samengestelde elementen in webforJ-toepassingen. Het primaire doel is om de interactie met HTML-elementen, vertegenwoordigd door de `Element` klasse, te vergemakkelijken door een gestructureerde aanpak te bieden voor het omgaan met eigenschappen, attributen en gebeurtenislislisteners. Het maakt de implementatie en hergebruik van elementen in een toepassing mogelijk. Gebruik de `ElementComposite` klasse bij het implementeren van Web Components voor gebruik in webforJ-toepassingen.

Bij het gebruik van de `ElementComposite` klasse geeft het gebruik van de `getElement()` methode toegang tot de onderliggende `Element` component. Evenzo geeft de `getNodeName()` methode de naam van die node in de DOM.

:::tip
Het is mogelijk om alles te doen met de `Element` klasse zelf, zonder de `ElementComposite` klasse te gebruiken. De aangeboden methoden in de `ElementComposite` bieden echter een manier voor gebruikers om het werk dat wordt gedaan te hergebruiken. 
:::

Gedurende deze gids zullen we de [Shoelace QR-code webcomponent](https://shoelace.style/components/qr-code) implementeren met behulp van de `ElementComposite` klasse.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Eigenschappen en attribuutbeschrijvingen {#property-and-attribute-descriptors}

Eigenschappen en attributen in webcomponenten vertegenwoordigen de staat van de component. Ze worden vaak gebruikt om gegevens of configuratie te beheren. De `ElementComposite` klasse biedt een handige manier om met eigenschappen en attributen te werken.

Eigenschappen en attributen kunnen worden verklaard en geïnitialiseerd als `PropertyDescriptor` leden van de `ElementComposite` klasse die wordt geschreven, en vervolgens in de code worden gebruikt. Om eigenschappen en attributen te definiëren, gebruik de `set()` methode om de waarde van een eigenschap in te stellen. Bijvoorbeeld, `set(PropertyDescriptor<V> property, V value)` stelt een eigenschap in op een gespecificeerde waarde.

:::info
Eigenschappen worden intern binnen de code van de component toegankelijk gemaakt en gemanipuleerd en weerspiegelen zich niet in de DOM. Attributen daarentegen maken deel uit van de externe interface van de component en kunnen worden gebruikt om informatie van buitenaf aan een component door te geven, wat een manier biedt voor externe elementen of scripts om de component te configureren.
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

Naast het instellen van een eigenschap, kunt u de `get()` methode in de `ElementComposite` klasse gebruiken om eigenschappen te benaderen en te lezen. De `get()` methode kan een optionele `boolean` waarde worden doorgegeven, die standaard false is, om aan te geven of de methode een reis naar de client moet maken om de waarde op te halen. Dit heeft invloed op de prestaties, maar kan nodig zijn als de eigenschap puur in de client kan worden gewijzigd.

Een `Type` kan ook aan de methode worden doorgegeven, wat aangeeft naar wat de opgehaalde resultaat moet worden gecast.

:::tip
Deze `Type` is niet per se noodzakelijk en voegt een extra laag specificatie toe als de gegevens worden opgehaald.
:::

```java
// Voorbeeld eigenschap genaamd TITLE in een ElementComposite klasse
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

In de demo hieronder zijn eigenschappen toegevoegd voor de QR-code, gebaseerd op de documentatie voor de webcomponent. Methoden zijn vervolgens geïmplementeerd waarmee gebruikers de verschillende eigenschappen kunnen opvragen en instellen die zijn geïmplementeerd.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Evenementregistratie {#event-registration}

Evenementen zijn een cruciaal onderdeel van webcomponenten, waardoor communicatie tussen verschillende delen van een toepassing mogelijk is. De `ElementComposite` klasse vereenvoudigt de registratie en afhandeling van evenementen. Om een gebeurtenisluisteraar te registreren, gebruikt u de `addEventListener()` methode om gebeurtenisluisteraars voor specifieke gebeurtenistypes te registreren. Geef de gebeurtenisklasse, de luisteraar en optionele gebeurtenisopties op.

```java
// Voorbeeld: Een klikgebeurtenisluisteraar toevoegen
addEventListener(ClickEvent.class, event -> {
    // Behandel de klikgebeurtenis
});
```

:::info
De evenementen van de `ElementComposite` zijn anders dan die van `Element`, omdat dit geen enkele klasse toestaat, maar alleen opgegeven `Event` klassen.
:::

In de demonstratie hieronder is een klikgebeurtenis gemaakt en vervolgens toegevoegd aan de QR-codecomponent. Deze gebeurtenis, wanneer deze wordt geactiveerd, toont de "X" coördinaat van de muis op het moment van klikken op de component, die als gegevens aan de Java-gebeurtenis wordt aangeboden. Een methode is vervolgens geïmplementeerd om de gebruiker toegang te geven tot deze gegevens, wat is hoe het in de toepassing wordt weergegeven.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interactie met Slots {#interacting-with-slots}

Webcomponenten gebruiken vaak slots om ontwikkelaars in staat te stellen de structuur van een component van buitenaf te definiëren. Een slot is een tijdelijke aanduiding binnen een webcomponent die kan worden ingevuld met inhoud bij het gebruik van de component. In de context van de `ElementComposite` klasse bieden slots een manier om de inhoud binnen een component aan te passen. De volgende methoden worden aangeboden om ontwikkelaars in staat te stellen met slots te interageren en deze te manipuleren:

1. **`findComponentSlot()`**: Deze methode wordt gebruikt om naar een specifiek component in alle slots in een componentensysteem te zoeken. Het retourneert de naam van het slot waar het component zich bevindt. Als het component in geen enkele slot wordt gevonden, wordt een lege string geretourneerd.

2. **`getComponentsInSlot()`**: Deze methode haalt de lijst op van componenten die aan een bepaalde slot in een componentensysteem zijn toegewezen. Geef optioneel een specifieke klassensoort door om de resultaten van de methode te filteren.

3. **`getFirstComponentInSlot()`**: Deze methode is ontworpen om de eerste component op te halen die aan de slot is toegewezen. Geef optioneel een specifieke klassensoort door om de resultaten van deze methode te filteren.

Het is ook mogelijk om de `add()` methode te gebruiken met een `String` parameter om het gewenste slot te specificeren waarin de doorgegeven component moet worden toegevoegd.

Deze interacties stellen ontwikkelaars in staat om de kracht van webcomponenten te benutten door een schone en eenvoudige API te bieden voor het manipuleren van slots, eigenschappen en het afhandelen van evenementen binnen de `ElementComposite` klasse.
