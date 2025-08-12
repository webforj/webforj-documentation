---
sidebar_position: 2
title: Bindings
_i18n_hash: 0afea0971d509f25324b46172b5e020e
---
Een binding in webforJ koppelt een specifieke eigenschap van een Java Bean aan een UI-component. Deze koppeling maakt automatische updates tussen de UI en het backend-model mogelijk. Elke binding kan gegevenssynchronisatie, validatie, transformatie en evenementenbeheer afhandelen.

Je kunt bindings alleen initiëren via de `BindingContext`. Deze beheert een collectie van bindinginstellingen, waarbij elke binding een UI-component koppelt aan een eigenschap van een bean. Het faciliteert groepsoperaties op bindings, zoals validatie en synchronisatie tussen de UI-componenten en de eigenschappen van de bean. Het fungeert als een aggregator, waarmee collectieve acties op meerdere bindings mogelijk zijn, en zo het beheer van gegevensstroom binnen applicaties vereenvoudigt.

:::tip Automatische Binding
Deze sectie introduceert de basisprincipes van het handmatig configureren van bindings. Daarnaast kun je automatisch bindings maken op basis van de UI-componenten in je formulier. Zodra je de basisprincipes begrijpt, leer je meer door de sectie [Automatische Binding](./automatic-binding) te lezen.
:::

## Bindings configureren {#configure-bindings}

Begin met het creëren van een nieuwe instantie van `BindingContext` die alle bindings voor een bepaald model beheert. Deze context zorgt ervoor dat alle bindings gezamenlijk kunnen worden gevalideerd en bijgewerkt.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Elke formulier zou slechts één `BindingContext`-instantie moeten hebben, en je zou deze instantie moeten gebruiken voor alle componenten in het formulier.
:::

### De gebonden eigenschap {#the-bound-property}

Een bindingseigenschap is een specifiek veld of attribuut van een Java Bean dat kan worden gekoppeld aan een UI-component in je app. Deze koppeling zorgt ervoor dat wijzigingen in de UI direct invloed hebben op de corresponderende eigenschap van het gegevensmodel, en vice versa, waardoor een reactieve gebruikerservaring ontstaat.

Bij het opzetten van een binding moet je de naam van de eigenschap als een string opgeven. Deze naam moet overeenkomen met de veldnaam in de Java Bean-klasse. Hier is een eenvoudig voorbeeld:

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

de `bind` methode retourneert een `BindingBuilder` die het `Binding`-object creëert en dat je kunt gebruiken om verschillende instellingen voor de binding te configureren, de `add`-methode waarmee de binding daadwerkelijk aan de context wordt toegevoegd.

### De gebonden component {#the-bound-component}

De andere kant van de binding is de gebonden component, die verwijst naar de UI-component die interactie heeft met de eigenschap van de Java Bean. De gebonden component kan elke UI-component zijn die gebruikersinteractie en weergave ondersteunt, zoals tekstvelden, comboboxen, selectievakjes, of elke aangepaste component die de `ValueAware`-interface implementeert.

De gebonden component fungeert als het punt van interactie voor de gebruiker met het onderliggende gegevensmodel. Het toont gegevens aan de gebruiker en legt ook gebruikersinvoer vast die vervolgens terug naar het model wordt gepropageerd.

```java
TextField nameTextField = new TextField("Naam");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Gegevens lezen en schrijven {#reading-and-writing-data}

### Gegevens lezen {#reading-data}

Gegevens lezen houdt in dat UI-componenten worden gevuld met waarden uit het gegevensmodel. Dit gebeurt meestal wanneer een formulier aanvankelijk wordt weergegeven of wanneer je de gegevens opnieuw moet laden vanwege wijzigingen in het onderliggende model. De `read`-methode van `BindingContext` maakt dit proces eenvoudig.

```java
// Neem aan dat het Hero-object is geïnstantieerd en geïnitialiseerd
Hero hero = new Hero("Clark Kent", "Vliegen");

// BindingContext is al geconfigureerd met bindings
context.read(hero);
```

In dit voorbeeld neemt de `read`-methode een instantie van `Hero` en werkt alle gebonden UI-componenten bij om de eigenschappen van de held weer te geven. Als de naam of kracht van de held verandert, tonen de corresponderende UI-componenten (zoals een `TextField` voor de naam en een `ComboBox` voor krachten) deze nieuwe waarden.

### Gegevens schrijven {#writing-data}

Gegevens schrijven houdt in dat waarden worden verzameld uit de UI-componenten en het gegevensmodel wordt bijgewerkt. Dit gebeurt meestal wanneer een gebruiker een formulier indient. De `write`-methode verzamelt validatie en modelbijwerking in één stap.

```java
// Dit kan worden geactiveerd door een formulier-inzendgebeurtenis
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Gegevens zijn geldig en het held-object is bijgewerkt
    // repository.save(hero); 
  } else {
    // Behandel validatiefouten
    // results.getMessages();
  }
});
```

In de bovenstaande code, wanneer de gebruiker op de verzendknop klikt, wordt de `write`-methode aangeroepen. Het voert alle geconfigureerde validaties uit en, als de gegevens alle controles doorstaan, werkt het `Hero`-object bij met nieuwe waarden van de gebonden componenten. Als de gegevens geldig zijn, kun je deze opslaan in een database of verder verwerken. Als er validatiefouten zijn, moet je dit op een passende manier behandelen, meestal door foutmeldingen aan de gebruiker weer te geven.

:::tip Rapportage van validatiefouten
Alle kerncomponenten van webforJ hebben standaardconfiguraties om validatiefouten automatisch te rapporteren, hetzij inline of via een popover. Je kunt dit gedrag aanpassen met behulp van [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## Alleen-lezen gegevens {#readonly-data}
<!-- vale on -->

In bepaalde scenario's wil je misschien dat je app gegevens weergeeft zonder dat de eindgebruiker deze direct via de UI kan wijzigen. Dit is waar alleen-lezen gegevensbindings cruciaal worden. webforJ ondersteunt de configuratie van bindings als alleen-lezen, waardoor je gegevens kunt weergeven, maar deze niet kunt bewerken via gebonden UI-componenten.

### Configureren van alleen-lezen bindings {#configuring-readonly-bindings}

Om een alleen-lezen binding op te zetten, kun je de binding configureren om UI-componentinvoer uit te schakelen of te negeren. Dit zorgt ervoor dat de gegevens vanuit de UI-perspectief onveranderd blijven, terwijl ze indien nodig nog steeds programmatisch worden bijgewerkt.

```java
// Een tekstveld configureren als alleen-lezen in de bindingcontext
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

In deze configuratie zorgt `readOnly` ervoor dat het `nameTextField` geen gebruikersinvoer accepteert, waardoor het tekstveld effectief de gegevens weergeeft zonder wijzigingen toe te staan.

:::info
De binding kan de component alleen als alleen-lezen markeren als de UI-component de `ReadOnlyAware`-interface implementeert.
:::

:::tip Component Alleen-lezen vs Binding Alleen-lezen
Het is belangrijk om onderscheid te maken tussen bindings die je als alleen-lezen configureert en UI-componenten die je als alleen-lezen wilt weergeven. Wanneer je een binding als alleen-lezen markeert, heeft dit invloed op hoe de binding gegevens beheert tijdens het schrijfproces, en niet alleen op het gedrag van de UI.

Wanneer je een binding als alleen-lezen markeert, slaat het systeem gegevensupdates over. Wijzigingen in de UI-component worden niet teruggevoerd naar het gegevensmodel. Dit zorgt ervoor dat zelfs als de UI-component op de een of andere manier gebruikersinvoer ontvangt, dit het onderliggende gegevensmodel niet bijwerkt. Het handhaven van deze scheiding is essentieel voor het behoud van de gegevensintegriteit in scenario's waarin gebruikersacties de gegevens niet mogen wijzigen.

In tegenstelling tot het instellen van een UI-component als alleen-lezen, zonder de binding zelf als alleen-lezen te configureren, voorkomt dat simpelweg dat de gebruiker wijzigingen aanbrengt in de UI-component, maar stopt niet de binding van het bijwerken van het gegevensmodel als er programmatic of op andere manieren wijzigingen optreden.
:::

## Binding getters en setters {#binding-getters-and-setters}

Setters en getters zijn methoden in Java die respectievelijk de waarden van eigenschappen instellen en opvragen. In de context van gegevensbinding worden ze gebruikt om te definiëren hoe eigenschappen worden bijgewerkt en opgehaald binnen het bindingframework.

### Het aanpassen van setters en getters {#customizing-setters-and-getters}

Hoewel webforJ automatisch de standaard JavaBean-naamgevingsconventies kan gebruiken (bijvoorbeeld `getName()`, `setName()` voor een eigenschap `name`), moet je mogelijk aangepast gedrag definiëren. Dit is nodig wanneer de eigenschap niet de conventionele naamgeving volgt of wanneer de gegevensverwerking extra logica vereist.

### Het gebruik van aangepaste getters {#using-custom-getters}

Aangepaste getters worden gebruikt wanneer het ophalen van de waarde meer inhoudt dan alleen het retourneren van een eigenschap. Bijvoorbeeld, je wilt misschien de string formatteren, een waarde berekenen of bepaalde acties loggen wanneer een eigenschap wordt geraadpleegd.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // Aangepaste logica: omzetting van naam naar hoofdletters
    });
```

### Het gebruik van aangepaste setters {#using-custom-setters}

Aangepaste setters komen in beeld wanneer het instellen van een eigenschap aanvulling van operaties omvat, zoals validatie, transformatie of bijwerkingen zoals logging of het notificeren van andere delen van je app.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Bijwerken van naam van " + hero.getName() + " naar " + name);
        hero.setName(name); // Aanvullende operatie: logging
    });
```
