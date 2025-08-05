---
sidebar_position: 2
title: Bindings
_i18n_hash: fa6155c6e1eb2724d684d042f561c8a3
---
Een binding in webforJ koppelt een specifieke eigenschap van een Java Bean aan een UI-component. Deze koppeling maakt automatische updates mogelijk tussen de UI en het backend-model. Elke binding kan gegevenssynchronisatie, validatie, transformatie en evenementenbeheer afhandelen.

Je kunt bindings alleen initiëren via de `BindingContext`. Het beheert een verzameling bindinginstanties, elk koppelt een UI-component aan een eigenschap van een bean. Het faciliteert groepsoperaties op bindings, zoals validatie en synchronisatie tussen de UI-componenten en de eigenschappen van de bean. Het fungeert als een aggregator, waardoor collectieve acties op meerdere bindings mogelijk zijn, en daardoor het beheer van datastromen binnen applicaties stroomlijnt.

:::tip Automatische Binding
Dit gedeelte introduceert de basisprincipes van het handmatig configureren van bindings. Daarnaast kun je automatisch bindings creëren op basis van de UI-componenten in je formulier. Zodra je de basisprincipes begrijpt, lees verder in de sectie [Automatische Binding](./automatic-binding).
:::

## Bindings configureren {#configure-bindings}

Begin met het creëren van een nieuwe instantie van `BindingContext` die alle bindings voor een bepaald model beheert. Deze context zorgt ervoor dat alle bindings gezamenlijk gevalideerd en bijgewerkt kunnen worden.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Elke formulier zou slechts één `BindingContext`-instantie moeten hebben, en je zou deze instantie moeten gebruiken voor alle componenten in het formulier.
:::

### De gebonden eigenschap {#the-bound-property}

Een bindingseigenschap is een specifiek veld of attribuut van een Java Bean dat kan worden gekoppeld aan een UI-component in je app. Deze koppeling maakt het mogelijk dat wijzigingen in de UI direct invloed hebben op de overeenkomstige eigenschap van het datamodel, en vice versa, wat een reactieve gebruikerservaring vergemakkelijkt.

Bij het instellen van een binding moet je de naam van de eigenschap als een tekenreeks opgeven. Deze naam moet overeenkomen met de veldnaam in de Java Bean-klasse. Hier is een eenvoudig voorbeeld:

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

De `bind`-methoden retourneren een `BindingBuilder` die het `Binding`-object creëert en waarmee je de binding kunt configureren met verschillende instellingen. De `add`-methode is wat de binding daadwerkelijk aan de context toevoegt.

### De gebonden component {#the-bound-component}

De andere zijde van de binding is de gebonden component, die verwijst naar de UI-component die interactie heeft met de eigenschap van de Java Bean. De gebonden component kan elke UI-component zijn die gebruikersinteractie en weergave ondersteunt, zoals tekstvelden, keuzelijsten, selectievakjes of enige aangepaste component die de `ValueAware`-interface implementeert.

De gebonden component fungeert als het punt van interactie voor de gebruiker met het onderliggende datamodel. Het toont gegevens aan de gebruiker en legt ook gebruikersinvoer vast die vervolgens terug naar het model worden gepromoveerd.

```java
TextField nameTextField = new TextField("Naam");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Gegevens lezen en schrijven {#reading-and-writing-data}

### Gegevens lezen {#reading-data}

Gegevens lezen houdt in dat UI-componenten worden gevuld met waarden uit het datamodel. Dit gebeurt meestal wanneer een formulier aanvankelijk wordt weergegeven, of wanneer je de gegevens moet herladen vanwege wijzigingen in het onderliggende model. De `read`-methode die door `BindingContext` wordt aangeboden, maakt dit proces eenvoudig.

```java
// Veronderstel dat een Hero-object is geïnstantieerd en geïnitialiseerd
Hero hero = new Hero("Clark Kent", "Vliegen");

// BindingContext is al geconfigureerd met bindings
context.read(hero);
```

In dit voorbeeld neemt de `read`-methode een instantie van `Hero` en werkt alle gebonden UI-componenten bij om de eigenschappen van de held weer te geven. Als de naam of kracht van de held verandert, worden de overeenkomstige UI-componenten (zoals een `TextField` voor naam en een `ComboBox` voor krachten) bijgewerkt om deze nieuwe waarden weer te geven.

### Gegevens schrijven {#writing-data}

Gegevens schrijven houdt in dat waarden uit de UI-componenten worden verzameld en het datamodel worden bijgewerkt. Dit gebeurt meestal wanneer een gebruiker een formulier indient. De `write`-methode verwerkt validatie en modelupdate in één stap.

```java
// Dit kan worden getriggerd door een formulierindieningevenement
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Gegevens zijn geldig, en het heldenobject is bijgewerkt
    // repository.save(hero); 
  } else {
    // Behandel validatiefouten
    // results.getMessages();
  }
});
```

In de bovenstaande code, wanneer de gebruiker op de verzendknop klikt, wordt de `write`-methode aangeroepen. Het voert alle geconfigureerde validaties uit en, als de gegevens alle controles doorstaan, werkt het `Hero`-object bij met nieuwe waarden van de gebonden componenten. Als de gegevens geldig zijn, kun je ze mogelijk opslaan in een database of verder verwerken. Als er validatiefouten zijn, moet je dit op een passende manier behandelen, meestal door foutmeldingen aan de gebruiker weer te geven.

:::tip Rapportage van Validatiefouten
Alle kerncomponenten van webforJ hebben standaardconfiguraties om validatiefouten automatisch te rapporteren, hetzij inline of via een popover. Je kunt dit gedrag aanpassen met behulp van [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## Alleen-lezen gegevens {#readonly-data}
<!-- vale on -->

In bepaalde scenario's wil je misschien dat je app gegevens weergeeft zonder de eindgebruiker in staat te stellen deze rechtstreeks via de UI te wijzigen. Dit is waar alleen-lezen databindingen cruciaal worden. webforJ ondersteunt de configuratie van bindings als alleen-lezen, zodat je gegevens kunt weergeven, maar niet kunt bewerken via gebonden UI-componenten.

### Configureren van alleen-lezen bindings {#configuring-readonly-bindings}

Om een alleen-lezen binding in te stellen, kun je de binding configureren om de invoer van de UI-component uit te schakelen of te negeren. Dit zorgt ervoor dat de gegevens vanuit het perspectief van de UI onveranderd blijven, terwijl ze toch programmatisch kunnen worden bijgewerkt indien nodig.

```java
// Een tekstveld configureren om alleen-lezen te zijn in de bindingcontext
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

In deze configuratie zorgt `readOnly` ervoor dat het `nameTextField` geen gebruikersinvoer accepteert, waardoor het tekstveld de gegevens weergeeft zonder wijzigingen toe te laten.

:::info
De binding kan de component alleen als alleen-lezen markeren als de UI-component de `ReadOnlyAware`-interface implementeert.
:::

:::tip Component Alleen-lezen versus Binding Alleen-lezen
Het is belangrijk om te onderscheiden tussen bindings die je als alleen-lezen configureert en UI-componenten die je instelt om als alleen-lezen weer te geven. Wanneer je een binding als alleen-lezen markeert, heeft dit invloed op hoe de binding gegevens beheert tijdens het schrijfproces, niet alleen op het gedrag van de UI.

Wanneer je een binding als alleen-lezen markeert, wordt het systeem overgeslagen voor gegevensupdates. Wijzigingen in de UI-component worden niet teruggestuurd naar het datamodel. Dit zorgt ervoor dat, zelfs als de UI-component op de een of andere manier gebruikersinvoer ontvangt, het het onderliggende datamodel niet bijwerkt. Het handhaven van deze scheiding is cruciaal voor het behouden van de gegevensintegriteit in scenario's waarin gebruikersacties de gegevens niet zouden moeten veranderen.

In tegenstelling tot het instellen van een UI-component als alleen-lezen, zonder de binding zelf als alleen-lezen te configureren, stopt dit eenvoudigweg de gebruiker om wijzigingen aan te brengen in de UI-component, maar voorkomt niet dat de binding het datamodel bijwerkt als er programmatisch of op andere manieren wijzigingen plaatsvinden.
:::

## Bindingsgetters en -setters {#binding-getters-and-setters}

Setters en getters zijn methoden in Java die respectievelijk de waarden van eigenschappen instellen en ophalen. In de context van databinding worden ze gebruikt om te definiëren hoe eigenschappen worden bijgewerkt en opgehaald binnen het bindingframework.

### Aangepaste setters en getters {#customizing-setters-and-getters}

Hoewel webforJ standaard JavaBean-namingconventies automatisch kan gebruiken (bijvoorbeeld `getName()`, `setName()` voor een eigenschap `name`), moet je mogelijk aangepast gedrag definiëren. Dit is nodig wanneer de eigenschap niet de conventionele naamgeving volgt of wanneer de gegevensverwerking extra logica vereist.

### Gebruik van aangepaste getters {#using-custom-getters}

Aangepaste getters worden gebruikt wanneer het ophalen van de waarde meer omvat dan alleen het retourneren van een eigenschap. Bijvoorbeeld, je wilt misschien de tekenreeks formatteren, een waarde berekenen, of bepaalde acties registreren wanneer een eigenschap wordt geopend.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useGetter(hero -> {
        String name = hero.getName();
        return name.toUpperCase(); // Aangepaste logica: zet naam om naar hoofdletters
    });
```

### Gebruik van aangepaste setters {#using-custom-setters}

Aangepaste setters komen in beeld wanneer het instellen van een eigenschap extra bewerkingen vereist, zoals validatie, transformatie of bijeffecten zoals loggen of het informeren van andere delen van je app.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Naam wordt bijgewerkt van " + hero.getName() + " naar " + name);
        hero.setName(name); // Extra bewerking: logging
    });
```
