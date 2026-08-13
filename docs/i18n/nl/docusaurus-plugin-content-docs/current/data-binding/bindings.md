---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 9a4b6da2f5a3bd524a0b3cf6a1eb86e1
---
Een binding in webforJ verbindt een specifieke eigenschap van een Java Bean met een UI-component. Deze koppeling maakt automatische updates mogelijk tussen de UI en het backendmodel. Elke binding kan omgaan met datasynchronisatie, validatie, transformatie en het beheer van gebeurtenissen.

Je kunt bindingen alleen initiëren via de `BindingContext`. Het beheert een verzameling bindinginstanties, waarbij elke binding een UI-component koppelt aan een eigenschap van een bean. Het vergemakkelijkt groepsbewerkingen op bindingen, zoals validatie en synchronisatie tussen de UI-componenten en de eigenschappen van de bean. Het fungeert als een aggregator, waardoor collectieve acties op meerdere bindingen mogelijk zijn en het beheer van de datastroom binnen applicaties wordt gestroomlijnd.

:::tip Automatische Binding
Dit gedeelte introduceert de basisprincipes van handmatige configuratie van bindingen. Daarnaast kun je automatisch bindingen creëren op basis van de UI-componenten in je formulier. Zodra je de fundamenten begrijpt, leer je meer door de sectie [Automatische Binding](/docs/data-binding/automatic-binding) te lezen.
:::

## Configureren van bindingen {#configure-bindings}

Begin met het maken van een nieuwe instantie van `BindingContext` die alle bindingen voor een bepaald model beheert. Deze context valideert en werkt alle bindingen collectief bij.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Elk formulier moet slechts één instantie van `BindingContext` hebben, en je moet deze instantie gebruiken voor alle componenten in het formulier.
:::

### De gebonden eigenschap {#the-bound-property}

Een bindingseigenschap is een specifiek veld of attribuut van een Java Bean dat kan worden gekoppeld aan een UI-component in je app. Deze koppeling zorgt ervoor dat wijzigingen in de UI rechtstreeks invloed hebben op de overeenkomstige eigenschap van het datamodel, en vice versa, zodat de UI en het datamodel in sync blijven.

Bij het opzetten van een binding moet je de eigenschapsnaam als een tekenreeks opgeven. Deze naam moet overeenkomen met de veldnaam in de Java Bean-klasse. Hier is een eenvoudig voorbeeld:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add();
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters en getters
}
```

De `bind`-methode retourneert een `BindingBuilder` die het `Binding`-object creëert en je kunt gebruiken om de binding met verschillende instellingen te configureren, de `add`-methode die de binding daadwerkelijk aan de context toevoegt.

### De gebonden component {#the-bound-component}

De andere kant van de binding is de gebonden component, die verwijst naar de UI-component die interactie heeft met de eigenschap van de Java Bean. De gebonden component kan elke UI-component zijn die gebruikersinteractie en weergave ondersteunt, zoals tekstvelden, comboboxen, selectievakjes of elke aangepaste component die de `ValueAware`-interface implementeert.

De gebonden component fungeert als het punt van interactie voor de gebruiker met het onderliggende datamodel. Het toont gegevens aan de gebruiker en vangt ook gebruikersinvoer die vervolgens terug wordt gepromoveerd naar het model.

```java
TextField nameTextField = new TextField("Naam");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Gegevens lezen en schrijven {#reading-and-writing-data}

### Gegevens lezen {#reading-data}

Gegevens lezen houdt in dat UI-componenten worden gevuld met waarden uit het datamodel. Dit gebeurt meestal wanneer een formulier aanvankelijk wordt weergegeven, of wanneer je de gegevens opnieuw moet laden vanwege wijzigingen in het onderliggende model. De `read`-methode die door `BindingContext` wordt aangeboden, maakt dit proces eenvoudig.

```java
// Stel dat het Hero-object is geïnstantieerd en geïnitialiseerd
Hero hero = new Hero("Clark Kent", "Vliegen");

// BindingContext is al geconfigureerd met bindingen
context.read(hero);
```

In dit voorbeeld neemt de `read`-methode een instantie van `Hero` en werkt alle gebonden UI-componenten bij om de eigenschappen van de held weer te geven. Als de naam of kracht van de held verandert, tonen de overeenkomstige UI-componenten (zoals een `TextField` voor de naam en een `ComboBox` voor krachten) deze nieuwe waarden.

### Gegevens schrijven {#writing-data}

Gegevens schrijven houdt in dat waarden van de UI-componenten worden verzameld en het datamodel worden bijgewerkt. Dit gebeurt meestal wanneer een gebruiker een formulier indient. De `write`-methode behandelt validatie en modelupdates in één stap.

```java
// Dit kan worden geactiveerd door een formulierindieningsgebeurtenis
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Gegevens zijn geldig, en het heldobject is bijgewerkt
    // repository.save(hero);
  } else {
    // Verwerking van validatiefouten
    // results.getMessages();
  }
});
```

In de bovenstaande code, wanneer de gebruiker op de verzendknop klikt, wordt de `write`-methode aangeroepen. Het voert alle geconfigureerde validaties uit en als de gegevens alle controles doorstaan, werkt het `Hero`-object bij met nieuwe waarden van de gebonden componenten. Als de gegevens geldig zijn, kun je deze naar een database opslaan of verder verwerken. Als er validatiefouten zijn, moet je deze op de juiste manier afhandelen, meestal door foutmeldingen aan de gebruiker weer te geven.

:::tip Rapportage van validatiefouten
Alle kerncomponenten van webforJ hebben standaardconfiguraties om validatiefouten automatisch te rapporteren, hetzij inline of via een popover. Je kunt dit gedrag aanpassen met behulp van [Reporters](./validation/reporters.md).
:::

## Geneste bean-eigenschappen <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Een bindingseigenschap kan een gedoteerd pad zijn dat naar een eigenschap binnen een geneste bean wijst. Elk segment in het pad volgt de standaard JavaBean getter- en setterconventies, zodat `address.street` door `getAddress().getStreet()` leest en door `getAddress().setStreet()` schrijft.

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

Bij het lezen wordt een pad veilig opgelost, zelfs als een tussenliggende bean `null` is. Als een `Hero` geen `Address` heeft, worden de componenten die zijn gekoppeld aan `address.street` en `address.city` als leeg gelezen in plaats van een fout te veroorzaken, zodat het formulier nog steeds wordt gevuld.

Bij het schrijven creëert de context elke ontbrekende tussenliggende bean via de no-argument constructor, zodat het schrijven van het formulier naar een `Hero` zonder `Address` een nieuwe, gevulde `Address` oplevert. Een bestaande `Address` wordt hergebruikt.

[Jakarta validatie](/docs/data-binding/validation/jakarta-validation) annotaties op een geneste eigenschap worden op dezelfde manier gedetecteerd als op een eigenschap op het hoogste niveau. Een annotatie zoals `@NotNull` op `Address.street` markeert de `address.street` binding als [verplicht](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Paden worden vooraf gevalideerd
Het volledige pad wordt gevalideerd wanneer je `bind` aanroept. Een typfout in een segment, op het hoogste niveau of dieper in het pad, werpt een `IllegalArgumentException`, zodat bindingfouten onmiddellijk aan het licht komen in plaats van bij lees- of schrijftijd.
:::

<!-- vale off -->
## Alleen-lezen gegevens {#readonly-data}
<!-- vale on -->

In bepaalde scenario's wil je misschien dat je app gegevens weergeeft zonder dat de eindgebruiker deze rechtstreeks via de UI kan wijzigen. Alleen-lezen data bindingen adresseren dit. webforJ ondersteunt het configureren van bindingen als alleen-lezen, zodat je gegevens kunt weergeven, maar deze niet kunt bewerken via gebonden UI-componenten.

### Configureren van alleen-lezen bindingen {#configuring-readonly-bindings}

Om een alleen-lezen binding in te stellen, kun je de binding configureren om gebruikersinvoer uit of te negeren. De gegevens blijven dan onveranderd vanuit het perspectief van de UI, terwijl ze nog steeds programmatisch worden bijgewerkt wanneer dat nodig is.

```java
// Een tekstveld configureren als alleen-lezen in de binding context
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

In deze configuratie stopt `readOnly` het `nameTextField` van het accepteren van gebruikersinvoer, zodat het tekstveld de gegevens weergeeft zonder modificaties toe te staan.

:::info
De binding kan de component alleen als alleen-lezen markeren als de UI-component de `ReadOnlyAware`-interface implementeert.
:::

:::tip Component Alleen-lezen versus Binding Alleen-lezen
Het is belangrijk om te onderscheiden tussen bindingen die je configureert als alleen-lezen en UI-componenten die je instelt om als alleen-lezen te worden weergegeven. Wanneer je een binding als alleen-lezen markeert, heeft dit invloed op hoe de binding gegevens beheert tijdens het schrijfproces, niet alleen op het gedrag van de UI.

Wanneer je een binding als alleen-lezen markeert, slaat het systeem gegevensupdates over. Elke wijziging in de UI-component wordt niet teruggestuurd naar het datamodel. Hierdoor, zelfs als de UI-component op de een of andere manier gebruikersinvoer ontvangt, wordt het onderliggende datamodel niet bijgewerkt. Dit behoudt de integriteit van de gegevens in scenario's waarin gebruikersacties de gegevens niet mogen wijzigen.

In tegenstelling tot het instellen van een UI-component als alleen-lezen, zonder de binding zelf ook als alleen-lezen te configureren, stopt simpelweg de gebruiker van het aanbrengen van wijzigingen in de UI-component, maar stopt niet dat de binding het datamodel kan bijwerken als wijzigingen programmatisch of via andere middelen optreden.
:::

## Binding getters en setters {#binding-getters-and-setters}

Setters en getters zijn methoden in Java die respectievelijk de waarden van eigenschappen instellen en ophalen. In de context van databinding worden ze gebruikt om te definiëren hoe eigenschappen worden bijgewerkt en opgehaald binnen het bindingframework.

### Aangepaste setters en getters {#customizing-setters-and-getters}

Hoewel webforJ standaard gebruik kan maken van conventionele JavaBean-naamgevingsconventies (bijvoorbeeld `getName()`, `setName()` voor een eigenschap `name`), moet je mogelijk aangepaste gedrag definiëren. Dit is nodig wanneer de eigenschap niet de conventionele naamgeving volgt of wanneer de gegevensverwerking aanvullende logica vereist.

### Aangepaste getters gebruiken {#using-custom-getters}

Aangepaste getters worden gebruikt wanneer het ophalen van de waarde meer inhoudt dan alleen het retourneren van een eigenschap. Bijvoorbeeld, je wilt misschien de tekenreeks formatteren, een waarde berekenen of bepaalde acties loggen wanneer een eigenschap wordt geopend.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Aangepaste logica: conversie van naam naar hoofdletters
  });
```

### Aangepaste setters gebruiken {#using-custom-setters}

Aangepaste setters komen in beeld wanneer het instellen van een eigenschap aanvullende bewerkingen met zich meebrengt, zoals validatie, transformatie of bijwerkingen zoals loggen of andere delen van je app notificeren.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Naam bijwerken van " + hero.getName() + " naar " + name);
    hero.setName(name); // Aanvullende operatie: loggen
  });
```
