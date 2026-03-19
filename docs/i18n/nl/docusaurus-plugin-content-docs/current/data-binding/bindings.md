---
sidebar_position: 2
title: Bindings
_i18n_hash: c567705312942e83f5e83a77f1d510a4
---
Een binding in webforJ koppelt een specifieke eigenschap van een Java Bean aan een UI-component. Deze koppeling maakt automatische updates tussen de UI en het backendmodel mogelijk. Elke binding kan gegevenssynchronisatie, validatie, transformatie en gebeurtenisbeheer afhandelen.

Je kunt bindings alleen initiëren via de `BindingContext`. Het beheert een verzameling bindinginstanties, waarbij elke binding een UI-component koppelt aan een eigenschap van een bean. Het vergemakkelijkt groepsbewerkingen op bindings, zoals validatie en synchronisatie tussen de UI-componenten en de eigenschappen van de bean. Het fungeert als een aggregator, waarmee collectieve acties op meerdere bindings mogelijk zijn, waardoor het beheer van gegevensstroom binnen applicaties wordt gestroomlijnd.

:::tip Automatische Binding
In deze sectie worden de basisprincipes van handmatig configureren van bindings geïntroduceerd. Bovendien kun je automatisch bindings creëren op basis van de UI-componenten in je formulier. Zodra je de basis onder de knie hebt, leer je meer door de sectie [Automatische Binding](./automatic-binding) te lezen.
:::

## Configureer bindings {#configure-bindings}

Begin met het maken van een nieuwe instantie van `BindingContext` die alle bindings voor een bepaald model beheert. Deze context zorgt ervoor dat alle bindings gezamenlijk kunnen worden gevalideerd en bijgewerkt.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Elk formulier moet slechts één `BindingContext`-instantie hebben, en je moet deze instantie gebruiken voor alle componenten in het formulier.
:::

### De gebonden eigenschap {#the-bound-property}

Een bindingseigenschap is een specifiek veld of attribuut van een Java Bean dat kan worden gekoppeld aan een UI-component in je app. Deze koppeling zorgt ervoor dat wijzigingen in de UI directe invloed hebben op de bijbehorende eigenschap van het datamodel, en vice versa, wat een reactieve gebruikerservaring vergemakkelijkt.

Bij het instellen van een binding moet je de eigenschapsnaam als een tekenreeks opgeven. Deze naam moet overeenkomen met de veldnaam in de Java Bean-klasse. Hier is een eenvoudig voorbeeld:

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

De `bind` methode retourneert een `BindingBuilder` die het `Binding` object creëert en waarmee je verschillende instellingen voor de binding kunt configureren, de `add` methode, die de binding daadwerkelijk aan de context toevoegt.

### De gebonden component {#the-bound-component}

De andere kant van de binding is de gebonden component, die verwijst naar de UI-component die interactie heeft met de eigenschap van de Java Bean. De gebonden component kan elke UI-component zijn die gebruikersinteractie en weergave ondersteunt, zoals tekstvelden, keuzelijsten, selectievakjes of elke aangepaste component die de `ValueAware` interface implementeert.

De gebonden component dient als het contactpunt voor de gebruiker met het onderliggende datamodel. Het toont gegevens aan de gebruiker en verzamelt ook gebruikersinvoer die vervolgens terug naar het model wordt doorgegeven.

```java
TextField nameTextField = new TextField("Naam");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Gegevens lezen en schrijven {#reading-and-writing-data}

### Gegevens lezen {#reading-data}

Gegevens lezen houdt in dat UI-componenten worden gevuld met waarden uit het datamodel. Dit gebeurt meestal wanneer een formulier aanvankelijk wordt weergegeven of wanneer je de gegevens opnieuw moet laden vanwege wijzigingen in het onderliggende model. De `read` methode die door `BindingContext` wordt aangeboden, maakt dit proces eenvoudig.

```java
// Veronderstel dat het Hero-object is geïnstantieerd en geïnitialiseerd
Hero hero = new Hero("Clark Kent", "Vliegen");

// BindingContext is al geconfigureerd met bindings
context.read(hero);
```

In dit voorbeeld neemt de `read` methode een instantie van `Hero` en werkt alle gebonden UI-componenten bij om de eigenschappen van de held weer te geven. Als de naam of kracht van de held verandert, geven de bijbehorende UI-componenten (zoals een `TextField` voor de naam en een `ComboBox` voor de krachten) deze nieuwe waarden weer.

### Gegevens schrijven {#writing-data}

Gegevens schrijven houdt in dat waarden van de UI-componenten worden verzameld en het datamodel wordt bijgewerkt. Dit gebeurt meestal wanneer een gebruiker een formulier indient. De `write` methode behandelt validatie en model-update in één stap.

```java
// Dit kan worden geactiveerd door een formulierindienevenement
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Gegevens zijn geldig en het heldenobject is bijgewerkt
    // repository.save(hero); 
  } else {
    // Behandel validatiefouten
    // results.getMessages();
  }
});
```

In de bovenstaande code, wanneer de gebruiker op de verzendknop klikt, wordt de `write` methode aangeroepen. Het voert alle geconfigureerde validaties uit en, als de gegevens alle controles doorstaan, werkt het `Hero`-object bij met nieuwe waarden van de gebonden componenten. Als de gegevens geldig zijn, kun je deze mogelijk opslaan in een database of verder verwerken. Als er validatiefouten zijn, moet je dat op de juiste manier afhandelen, meestal door foutmeldingen aan de gebruiker weer te geven.

:::tip Rapportage van validatiefouten
Alle belangrijke componenten van webforJ hebben standaardconfiguraties om automatisch validatiefouten te rapporteren, hetzij inline, hetzij via een popover. Je kunt dit gedrag aanpassen met behulp van [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## Alleen-lezen gegevens {#readonly-data}
<!-- vale on -->

In bepaalde scenario's wil je misschien dat je app gegevens weergeeft zonder dat de eindgebruiker deze rechtstreeks via de UI kan aanpassen. Dit is waar alleen-lezen databindingen cruciaal worden. webforJ ondersteunt de configuratie van bindings als alleen-lezen, zodat je gegevens kunt weergeven, maar niet kunt bewerken via gebonden UI-componenten.

### Configureren van alleen-lezen bindings {#configuring-readonly-bindings}

Om een alleen-lezen binding in te stellen, kun je de binding configureren om gebruikersinvoer op de UI-component uit te schakelen of te negeren. Dit zorgt ervoor dat de gegevens vanuit het UI-perspectief onveranderd blijven, terwijl ze programmaatmatig kunnen worden bijgewerkt indien nodig.

```java
// Een tekstveld configureren als alleen-lezen in de binding context
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

In deze configuratie zorgt `readOnly` ervoor dat het `nameTextField` geen gebruikersinvoer accepteert, waardoor het tekstveld de gegevens weergeeft zonder wijzigingen toe te staan.

:::info
De binding kan de component alleen als alleen-lezen markeren als de UI-component de `ReadOnlyAware` interface implementeert.
:::

:::tip Component Alleen-Lezen vs Binding Alleen-Lezen
Het is belangrijk om te onderscheiden tussen bindings die je configureert als alleen-lezen en UI-componenten die je instelt om als alleen-lezen te worden weergegeven. Wanneer je een binding als alleen-lezen markeert, heeft dat invloed op hoe de binding gegevens beheert tijdens het schrijfproces, niet alleen op het gedrag van de UI.

Wanneer je een binding als alleen-lezen markeert, slaat het systeem gegevensupdates over. Wijzigingen in de UI-component worden niet teruggestuurd naar het datamodel. Dit zorgt ervoor dat zelfs als de UI-component op de een of andere manier gebruikersinvoer ontvangt, het het onderliggende datamodel niet bijwerkt. Het behouden van deze scheiding is cruciaal voor het waarborgen van de gegevensintegriteit in scenario's waarin gebruikersacties de gegevens niet mogen wijzigen.

Daarentegen stopt het instellen van een UI-component als alleen-lezen, zonder de binding zelf als alleen-lezen te configureren, simpelweg de gebruiker om wijzigingen aan te brengen in de UI-component, maar stopt het de binding niet om het datamodel bij te werken als er programmawijzigingen optreden of op andere manieren.
:::

## Binding getters en setters {#binding-getters-and-setters}

Setters en getters zijn methoden in Java die respectievelijk de waarden van eigenschappen instellen en ophalen. In de context van databinding worden ze gebruikt om te definiëren hoe eigenschappen binnen het bindingframework worden bijgewerkt en opgehaald.

### Aangepaste setters en getters {#customizing-setters-and-getters}

Hoewel webforJ automatisch de standaard JavaBean-naamgevingsconventies kan gebruiken (bijvoorbeeld `getName()`, `setName()` voor een eigenschap `name`), moet je mogelijk aangepast gedrag definiëren. Dit is nodig wanneer de eigenschap niet de conventionele naamgeving volgt of wanneer de gegevensafhandeling extra logica vereist.

### Aangepaste getters gebruiken {#using-custom-getters}

Aangepaste getters worden gebruikt wanneer het proces van waardeophaling meer inhoudt dan alleen het retourneren van een eigenschap. Bijvoorbeeld, je wilt misschien de string formatteren, een waarde berekenen of bepaalde acties loggen wanneer een eigenschap wordt benaderd.

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

Aangepaste setters komen in beeld wanneer het instellen van een eigenschap extra bewerkingen met zich meebrengt, zoals validatie, transformatie of bijeffecten zoals loggen of andere delen van je app notificeren.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Naam bijwerken van " + hero.getName() + " naar " + name);
    hero.setName(name); // Extra operatie: logging
  });
```
