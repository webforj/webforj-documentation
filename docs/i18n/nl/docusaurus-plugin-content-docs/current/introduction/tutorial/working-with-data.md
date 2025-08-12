---
title: Working With Data
sidebar_position: 3
_i18n_hash: 42dff7cecf07f976ccbe007e04e78a22
---
Deze stap richt zich op het toevoegen van gegevensbeheer en weergavecapaciteiten aan de demo-app. Om dit te doen, zullen dummygegevens over verschillende `Customer`-objecten worden aangemaakt en zal de app worden bijgewerkt om deze gegevens te verwerken en weer te geven in een [`Table`](../../components/table/overview) die aan de vorige app is toegevoegd.

Het zal het creëren van een `Customer`-modelclass beschrijven en deze integreren met een `Service`-klasse om toegang te krijgen tot en het beheren van de benodigde gegevens met behulp van de implementatie van een repository. Vervolgens wordt uitgelegd hoe de opgehaalde gegevens kunnen worden gebruikt om een `Table`-component in de app te implementeren, waarmee klantinformatie op een interactieve en gestructureerde manier wordt weergegeven.

Aan het einde van deze stap zal de app die is gemaakt in de [vorige stap](./creating-a-basic-app) een tabel weergeven met de aangemaakte gegevens die vervolgens in de volgende stappen verder kunnen worden uitgebreid. Om de app uit te voeren:

- Ga naar de `2-working-with-data` directory
- Voer `mvn jetty:run` uit

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

## Een datamodel maken {#creating-a-data-model}

Om een `Table` te maken die gegevens in de hoofdapp weergeeft, moet er een Java-bean klasse worden gemaakt die kan worden gebruikt met de `Table` om gegevens weer te geven.

In dit programma doet de `Customer`-klasse in `src/main/java/com/webforj/demos/data/Customer.java` dit. Deze klasse fungeert als het kern datamodel voor de app, waarin klantgerelateerde attributen zoals `firstName`, `lastName`, `company` en `country` zijn encapsuleerd. Dit model bevat ook een unieke ID.

```java title="Customer.java"
public class Customer implements HasEntityKey {
  private String firstName = "";
  private String lastName = "";
  private String company = "";
  private Country country = Country.UNKNOWN;
  private UUID uuid = UUID.randomUUID();

  public enum Country {

    @SerializedName("Germany")
    GERMANY,

    // Overige landen
  }

    // Getters en Setters

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info Gebruik `HasEntityKey` voor unieke identificatoren

Het implementeren van de `HasEntityKey`-interface is cruciaal voor het beheren van unieke identificatoren in modellen die met een `Table` worden gebruikt. Het zorgt ervoor dat elke instantie van het model een unieke sleutel heeft, waardoor de `Table` rijen effectief kan identificeren en beheren.

Voor deze demo retourneert de `getEntityKey()`-methode een UUID voor elke klant, wat zorgt voor een unieke identificatie. Hoewel hier UUID's worden gebruikt voor de eenvoud, is in real-world toepassingen een database primaire sleutel vaak een beter keuze voor het genereren van unieke sleutels.

Als `HasEntityKey` niet is geïmplementeerd, zal de `Table` standaard de Java-hashcode als sleutel gebruiken. Aangezien hashcodes niet gegarandeerd uniek zijn, kan dit conflicten veroorzaken bij het beheren van rijen in de `Table`.
:::

Met het `Customer`-datamodel op zijn plaats, is de volgende stap het beheren en organiseren van deze modellen binnen de app.

## Een `Service`-klasse maken {#creating-a-service-class}

Als een gecentraliseerde databeheerder laadt de `Service`-klasse niet alleen `Customer`-gegevens, maar biedt ook een efficiënte interface voor toegang tot en interactie ermee.

De klasse `Service.java` wordt aangemaakt in `src/main/java/com/webforj/demos/data`. In plaats van gegevens handmatig tussen componenten of klassen door te geven, fungeert de `Service` als een gedeelde bron, waardoor geïnteresseerden eenvoudig gegevens kunnen ophalen en ermee kunnen interageren.

In deze demo leest de `Service`-klasse klantgegevens uit een JSON-bestand dat zich bevindt op `src/main/resources/data/customers.json`. De gegevens worden toegewezen aan `Customer`-objecten en opgeslagen in een `ArrayList`, die de basis vormt voor de `Repository` van de tabel.

In webforJ biedt de `Repository`-klasse een gestructureerde manier om verzamelingen van entiteiten te beheren en op te halen. Het fungeert als een interface tussen uw app en zijn gegevens, en biedt methoden om gegevens op te vragen, tellen en vernieuwen, terwijl het een schone en consistente structuur behoudt. Het wordt gebruikt door de `Table`-klasse om de gegevens weer te geven die daarin zijn opgeslagen.

Hoewel de `Repository` geen methoden voor het bijwerken of verwijderen van entiteiten bevat, fungeert het als een gestructureerde wrapper rondom een verzameling objecten. Dit maakt het ideaal voor het bieden van georganiseerde en efficiënte gegevens toegang.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // Overige implementatie
}
```

Om de `Repository` met gegevens te vullen, fungeert de `Service`-klasse als de centrale beheerder die de laadt en organiseert van activa in de app. Klantgegevens worden gelezen uit een JSON-bestand en toegewezen aan de `Customer`-objecten in de `Repository`.

De `Assets`-utility in webforJ maakt het eenvoudig om deze gegevens dynamisch te laden met behulp van context-URL's. Om activa en gegevens in webforJ te laden, gebruikt de `Service`-klasse context-URL's met de `Assets`-utility. Klantgegevens kunnen bijvoorbeeld als volgt worden geladen vanuit het JSON-bestand:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Het gebruik van de `ObjectTable`
De `Service`-klasse gebruikt de `ObjectTable` om instanties dynamisch te beheren, in plaats van te vertrouwen op statische velden. Deze benadering adresseert een belangrijke beperking bij het gebruik van servlets: statische velden zijn gekoppeld aan de levenscyclus van de server en kunnen problemen veroorzaken in omgevingen met meerdere verzoeken of gelijktijdige sessies. De `ObjectTable` is beperkt tot de gebruikerssessie, en het gebruik ervan zorgt voor een singleton-achtige werking zonder deze beperkingen, wat zorgt voor consistente en schaalbare gegevensbeheer.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Privé constructor om gecontroleerde instantiatie af te dwingen
  private Service() {
    // implementatie
  }

  // Verkrijgt de huidige instantie van Service of creëert er een als deze niet bestaat
  public static Service getCurrent() {
    // implementatie
  }

  // Laad klantgegevens uit het JSON-bestand en wijs het toe aan Customer-objecten
  private List<Customer> buildDemoList() {
    // implementatie
  }

  // Getter...
}
```

## Een `Table` maken en gebruiken {#creating-and-using-a-table}

Nu de benodigde gegevens correct zijn aangemaakt via de `Customer`-klasse en als een `Repository` via de `Service`-klasse worden teruggegeven, is de laatste taak in deze stap het integreren van de `Table`-component in de app om klantgegevens weer te geven.

:::tip Meer over de `Table`
Voor een meer gedetailleerd overzicht van de verschillende functies en gedragingen van de `Table`, zie [dit artikel](../../components/table/overview).
:::

De `Table` biedt een dynamische en flexibele manier om gestructureerde gegevens in uw app weer te geven. Het is ontworpen om te integreren met de `Repository`-klasse, waardoor functies zoals gegevensopvraging, paginering en efficiënte updates mogelijk worden. Een `Table` is zeer configureerbaar, waardoor u kolommen kunt definiëren, het uiterlijk kunt controleren en het met minimale inspanning aan gegevensrepositories kunt binden.

### De `Table` implementeren in de app {#implementing-the-table-in-the-app}

Aangezien de gegevens voor de `Table` volledig worden afgehandeld via de `Service`-klasse, is de belangrijkste taak in `DemoApplication.java` het configureren van de `Table` en deze te koppelen aan de `Repository` die door de `Service` wordt geleverd.

Om de `Table` te configureren:

- Stel de breedte en hoogte in voor lay-outdoeleinden met behulp van de `setHeight()` en `setWidth()`-methoden.
- Definieer de kolommen, waarbij u hun namen opgeeft en de methoden voor het ophalen van de gegevens voor elke kolom specificeert.
- Wijs de `Repository` toe om gegevens dynamisch te verstrekken.

Na het doen hiervan zal de code er ongeveer als volgt uitzien:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Andere componenten uit stap één

  // De Table-component voor het weergeven van klantgegevens
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // Vorige implementatie van stap één
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Stel de hoogte van de tabel in op 300 pixels
    table.setHeight("300px");
    // Stel de breedte van de tabel in op 1000 pixels
    table.setWidth(1000);

    // Voeg de verschillende kolomtitels toe en wijs de juiste getters toe
    table.addColumn("Voornaam", Customer::getFirstName);
    table.addColumn("Achternaam", Customer::getLastName);
    table.addColumn("Bedrijf", Customer::getCompany);
    table.addColumn("Land", Customer::getCountry);

    // Koppel de Table aan een Repository met klantgegevens
    // De Repository wordt opgehaald via de Service-klasse
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Met de voltooide wijzigingen in de app geïmplementeerd, zullen de volgende stappen plaatsvinden wanneer de app wordt uitgevoerd:

1. De `Service`-klasse haalt `Customer`-gegevens uit het JSON-bestand en slaat deze op in een `Repository`.
2. De `Table` integreert de `Repository` voor gegevens en vult zijn rijen dynamisch.

Met de `Table` die nu `Customer`-gegevens weergeeft, zal de volgende stap zich richten op het creëren van een nieuw scherm om klantgegevens te wijzigen en het integreren van routing in de app.

Dit zal de organisatie van de logica van de app effectiever maken door deze uit de hoofdingang `App`-klasse te halen en naar samenstellende schermen te verplaatsen die via routes toegankelijk zijn.
