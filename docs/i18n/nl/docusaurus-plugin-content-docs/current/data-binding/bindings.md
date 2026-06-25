---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 047676a64833283bcc160d7a8d226559
---
Een binding in webforJ koppelt een specifieke eigenschap van een Java Bean aan een UI-component. Deze koppeling stelt automatische updates mogelijk tussen de UI en het backend-model. Elke binding kan omgaan met gegevenssynchronisatie, validatie, transformatie en evenementenbeheer.

Je kunt bindings alleen initiëren via de `BindingContext`. Het beheert een collectie van bindinginstanties, waarbij elke instantie een UI-component koppelt aan een eigenschap van een bean. Het faciliteert groepsbewerkingen op bindings, zoals validatie en synchronisatie tussen de UI-componenten en de eigenschappen van de bean. Het fungeert als een aggregator, waardoor gezamenlijke acties op meerdere bindings mogelijk zijn, waarmee het beheer van datastromen binnen applicaties soepel verloopt.

:::tip Automatische Binding
Dit gedeelte introduceert de basisprincipes van het handmatig configureren van bindings. Daarnaast kun je automatisch bindings creëren op basis van de UI-componenten in je formulier. Zodra je de basis begrijpt, leer je meer door het lezen van de [Automatische Binding](/docs/data-binding/automatic-binding) sectie.
:::

## Configureren van bindings {#configure-bindings}

Begin met het creëren van een nieuwe instantie van `BindingContext` die alle bindings voor een bepaald model beheert. Deze context valideert en update alle bindings gezamenlijk.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Elk formulier zou slechts één `BindingContext` instantie moeten hebben, en je zou deze instantie moeten gebruiken voor alle componenten in het formulier.
:::

### De gebonden eigenschap {#the-bound-property}

Een bindingseigenschap is een specifiek veld of attribuut van een Java Bean dat kan worden gekoppeld aan een UI-component in je app. Deze koppeling stelt wijzigingen in de UI in staat om direct de bijbehorende eigenschap van het datamodel te beïnvloeden, en vice versa, zodat de UI en het datamodel op één lijn blijven.

Bij het opzetten van een binding zou je de eigenschapsnaam als een string moeten opgeven. Deze naam moet overeenkomen met de veldnaam in de Java Bean klasse. Hier is een eenvoudig voorbeeld:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add()
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters en getters
}
```

de `bind`-methoden retourneren een `BindingBuilder` die het `Binding`-object creëert en je kunt gebruiken om de binding te configureren van verschillende instellingen, de `add`-methode die is wat de binding daadwerkelijk aan de context toevoegt.

### De gebonden component {#the-bound-component}

De andere kant van de binding is de gebonden component, die verwijst naar de UI-component die interactie heeft met de eigenschap van de Java Bean. De gebonden component kan elke UI-component zijn die gebruikersinteractie en weergave ondersteunt, zoals tekstvelden, keuzelijsten, selectievakjes of elke aangepaste component die de `ValueAware` interface implementeert.

De gebonden component dient als het punt van interactie voor de gebruiker met het onderliggende datamodel. Het toont gegevens aan de gebruiker en vangt ook gebruikersinvoer, die vervolgens terug naar het model wordt doorgegeven.

```java
TextField nameTextField = new TextField("Naam");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Gegevens lezen en schrijven {#reading-and-writing-data}

### Gegevens lezen {#reading-data}

Gegevens lezen houdt in dat UI-componenten worden gevuld met waarden uit het datamodel. Dit gebeurt meestal wanneer een formulier in eerste instantie wordt weergegeven, of wanneer je de gegevens opnieuw moet laden vanwege wijzigingen in het onderliggende model. De `read`-methode die door `BindingContext` wordt aangeboden maakt dit proces eenvoudig.

```java
// Veronderstel dat het Hero-object is geïnstantieerd en geïnitialiseerd
Hero hero = new Hero("Clark Kent", "Vliegen");

// BindingContext is al geconfigureerd met bindings
context.read(hero);
```

In dit voorbeeld neemt de `read`-methode een instantie van `Hero` en update alle gebonden UI-componenten om de eigenschappen van de held weer te geven. Als de naam of kracht van de held verandert, worden de bijbehorende UI-componenten (zoals een `TextField` voor de naam en een `ComboBox` voor krachten) bijgewerkt met deze nieuwe waarden.

### Gegevens schrijven {#writing-data}

Gegevens schrijven houdt in dat waarden van de UI-componenten worden verzameld en het datamodel wordt bijgewerkt. Dit gebeurt meestal wanneer een gebruiker een formulier indient. De `write`-methode behandelt validatie en modelupdates in één stap.

```java
// Dit kan worden geactiveerd door een formulierindieningsevenement
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Gegevens zijn geldig en het hero-object is bijgewerkt
    // repository.save(hero); 
  } else {
    // Afhandelen van validatiefouten
    // results.getMessages();
  }
});
```

In de bovenstaande code, wanneer de gebruiker op de verzendknop klikt, wordt de `write`-methode aangeroepen. 
Deze voert alle geconfigureerde validaties uit en, als de gegevens alle controles doorstaan, bijgewerkt het `Hero`-object 
met nieuwe waarden van de gebonden componenten. 
Als de gegevens geldig zijn, kun je deze mogelijk opslaan in een database of verder verwerken. Als er validatiefouten zijn, 
moet je deze op een passende manier afhandelen, meestal door foutmeldingen aan de gebruiker weer te geven.

:::tip Rapportage van Validatiefouten
Alle kerncomponenten van webforJ hebben standaardconfiguraties om validatiefouten automatisch te rapporteren, hetzij inline of via een popover. Je kunt dit gedrag aanpassen met behulp van [Reporters](./validation/reporters.md).
:::

## Geneste bean-eigenschappen <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Een bindingseigenschap kan een gedoteerd pad zijn dat naar een eigenschap binnen een geneste bean wijst. Elke segment in het pad volgt de standaard JavaBean getter- en setterconventies, dus `address.street` leest via `getAddress().getStreet()` en schrijft via `getAddress().setStreet()`.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getters en setters
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getters en setters
}
```

Bij het lezen wordt een pad veilig opgelost, zelfs als een tussenliggende bean `null` is. Als een `Hero` geen `Address` heeft, worden de componenten die zijn gebonden aan `address.street` en `address.city` als leeg weergegeven in plaats van dat er een uitzondering wordt gegooid, zodat het formulier nog steeds wordt gevuld.

Bij het schrijven creëert de context elke ontbrekende tussenliggende bean via de constructor zonder argumenten, zodat het schrijven van het formulier in een `Hero` zonder `Address` een nieuwe, populierte `Address` produceert. Een bestaande `Address` wordt hergebruikt.

[Jakarta validatie](/docs/data-binding/validation/jakarta-validation) annotaties op een geneste eigenschap worden op dezelfde manier gedetecteerd als op een bovenliggende eigenschap. Een annotatie zoals `@NotNull` op `Address.street` markeert de `address.street` binding als [vereist](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Paden worden vooraf gevalideerd
Het volledige pad wordt gevalideerd wanneer je `bind` aanroept. Een typefout in een segment, op het hoogste niveau of dieper in het pad, gooit een `IllegalArgumentException`, zodat bindingfouten onmiddellijk zichtbaar zijn in plaats van tijdens lees- of schrijftijd.
:::

<!-- vale off -->
## Alleen-lezen gegevens {#readonly-data}
<!-- vale on -->

In bepaalde scenario's wil je misschien dat je app gegevens toont zonder dat de eindgebruiker deze rechtstreeks via de UI kan wijzigen. Alleen-lezen data bindings adressen dit. webforJ ondersteunt het configureren van bindings als alleen-lezen, zodat je gegevens kunt weergeven, maar deze niet kunt bewerken via gebonden UI-componenten.

### Configureren van alleen-lezen bindings {#configuring-readonly-bindings}

Om een alleen-lezen binding op te zetten, kun je de binding configureren om invoer van UI-componenten uit te schakelen of te negeren. De gegevens blijven vervolgens onveranderd vanuit het perspectief van de UI, terwijl ze nog steeds programmatisch worden bijgewerkt wanneer dat nodig is.

```java
// Een tekstveld configureren om alleen-lezen te zijn in de binding context
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

In deze configuratie stopt `readOnly` het `nameTextField` van het accepteren van gebruikersinvoer, zodat het tekstveld de 
gegevens weergeeft zonder wijzigingen toe te staan.

:::info
De binding kan de component alleen als alleen-lezen markeren als de UI-component de `ReadOnlyAware` interface implementeert.
:::

:::tip Component Alleen-lezen vs Binding Alleen-lezen
Het is belangrijk om een onderscheid te maken tussen bindings die je configureert als alleen-lezen en UI-componenten die je als alleen-lezen instelt om weer te geven. 
Wanneer je een binding als alleen-lezen markeert, heeft dit invloed op hoe de binding gegevens beheert tijdens het schrijfproces, niet alleen op het gedrag van de UI.

Als je een binding als alleen-lezen markeert, wordt het systeem overgeslagen bij gegevensupdates. Wijzigingen in de UI-component worden niet teruggezonden naar het datamodel. 
Als gevolg hiervan, zelfs als de UI-component op de een of andere manier gebruikersinvoer ontvangt, wordt het onderliggende datamodel niet bijgewerkt. 
Het behouden van deze scheiding beschermt de gegevensintegriteit in scenario's waarin gebruikersacties de gegevens niet zouden moeten wijzigen.

In tegenstelling tot het instellen van een UI-component als alleen-lezen, zonder de binding zelf als alleen-lezen te configureren, stopt simpelweg het maken van veranderingen aan de UI-component, maar stopt de binding niet met het bijwerken van het datamodel als wijzigingen programmatisch optreden of op andere manieren.
:::

## Binding getters en setters {#binding-getters-and-setters}

Setters en getters zijn methoden in Java die respectievelijk de waarden van eigenschappen instellen en ophalen. 
In de context van databinding worden ze gebruikt om te definiëren hoe eigenschappen worden bijgewerkt en opgehaald binnen het bindingframework.

### Aangepaste setters en getters {#customizing-setters-and-getters}

Hoewel webforJ automatisch de standaard JavaBean-naamgevingsconventies kan gebruiken 
(bijvoorbeeld, `getName()`, `setName()` voor een eigenschap `name`), moet je mogelijk aangepaste gedrag definiëren. 
Dit is nodig wanneer de eigenschap niet de conventionele naamgeving volgt of wanneer de gegevensverwerking extra logica vereist.

### Aangepaste getters gebruiken {#using-custom-getters}

Aangepaste getters worden gebruikt wanneer het ophalen van een waarde meer inhoudt dan alleen het retourneren van een eigenschap. 
Bijvoorbeeld, je wilt misschien de string formatteren, een waarde berekenen of bepaalde acties loggen wanneer een eigenschap wordt benaderd.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Aangepaste logica: naam omzetten naar hoofdletters
  });
```

### Aangepaste setters gebruiken {#using-custom-setters}

Aangepaste setters komen in beeld wanneer het instellen van een eigenschap extra bewerkingen met zich meebrengt, zoals validatie, transformatie of bijwerkingen 
zoals logging of het notificeren van andere delen van je app.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Bijwerken naam van " + hero.getName() + " naar " + name);
    hero.setName(name); // Extra bewerking: logging
  });
```
