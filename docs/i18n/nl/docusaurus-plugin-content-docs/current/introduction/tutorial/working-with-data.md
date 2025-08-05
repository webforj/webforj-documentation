---
title: Working With Data
sidebar_position: 3
_i18n_hash: 3afbf6e4eb4921183cc11d87c8457150
---
Deze stap is gericht op het toevoegen van gegevensbeheer en weergavecapaciteiten aan de demo-app. Hiervoor zullen dummy-gegevens over verschillende `Customer`-objecten worden aangemaakt en de app zal worden bijgewerkt om deze gegevens te verwerken en weer te geven in een [`Table`](../../components/table/overview) die aan de vorige app is toegevoegd.

Het zal de creatie van een `Customer`-modelklasse schetsen en integreren met een `Service`-klasse om toegang te krijgen tot en het beheer van de noodzakelijke gegevens met behulp van de implementatie van een repository. Vervolgens zal het in detail beschrijven hoe de opgehaalde gegevens kunnen worden gebruikt om een `Table`-component in de app te implementeren, die klantinformatie op een interactieve en gestructureerde manier weergeeft.

Aan het einde van deze stap zal de in de [vorige stap](./creating-a-basic-app) gemaakte app een tabel weergeven met de aangemaakte gegevens die later in de volgende stappen verder kunnen worden uitgebreid. Om de app uit te voeren:

- Ga naar de `2-working-with-data`-directory
- Voer `mvn jetty:run` uit

## Een datamodel maken {#creating-a-data-model}

Om een `Table` te maken die gegevens in de hoofd-app weergeeft, moet een Java-beanklasse worden gemaakt die kan worden gebruikt met de `Table` om gegevens weer te geven.

In dit programma doet de `Customer`-klasse in `src/main/java/com/webforj/demos/data/Customer.java` dit. Deze klasse dient als het kern datamodel voor de app en encapsuleert klantgerelateerde attributen zoals `firstName`, `lastName`, `company` en `country`. Dit model zal ook een unieke ID bevatten.

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

:::info Gebruik van `HasEntityKey` voor unieke identificaties

Het implementeren van de `HasEntityKey`-interface is cruciaal voor het beheren van unieke identificaties in modellen die worden gebruikt met een `Table`. Het zorgt ervoor dat elke instantie van het model een unieke sleutel heeft, waardoor de `Table` rijen effectief kan identificeren en beheren.

Voor deze demo retourneert de `getEntityKey()`-methode een UUID voor elke klant, wat zorgt voor unieke identificatie. Hoewel UUID's hier voor de eenvoud worden gebruikt, is een database primaire sleutel vaak een betere keuze voor het genereren van unieke sleutels in echte toepassingen.

Als `HasEntityKey` niet is geïmplementeerd, zal de `Table` standaard de Java-hashcode gebruiken als sleutel. Omdat hashcodes niet gegarandeerd uniek zijn, kan dit conflicten veroorzaken bij het beheren van rijen in de `Table`.
:::

Met het `Customer`-datamodel op zijn plaats, is de volgende stap om deze modellen binnen de app te beheren en te organiseren.

## Een `Service`-klasse maken {#creating-a-service-class}

Als een gecentraliseerde gegevensbeheerder laadt de `Service`-klasse niet alleen `Customer`-gegevens, maar biedt ook een efficiënte interface voor toegang tot en interactie ermee.

De `Service.java`-klasse wordt aangemaakt in `src/main/java/com/webforj/demos/data`. In plaats van handmatig gegevens tussen componenten of klassen door te geven, fungeert de `Service` als een gedeelde hulpbron, zodat geïnteresseerde partijen gemakkelijk gegevens kunnen ophalen en ermee kunnen interageren.

In deze demo leest de `Service`-klasse klantgegevens uit een JSON-bestand dat zich bevindt in `src/main/resources/data/customers.json`. De gegevens worden in `Customer`-objecten gemapt en in een `ArrayList` opgeslagen, wat de basis vormt voor de `Repository` van de tabel.

In webforJ biedt de `Repository`-klasse een gestructureerde manier om collecties van entiteiten te beheren en op te halen. Het fungeert als een interface tussen jouw app en de gegevens, en biedt methoden om te query'en, tellen en vernieuwen terwijl het een schone en consistente structuur behoudt. Het wordt gebruikt door de `Table`-klasse om de gegevens op te slaan.

Hoewel de `Repository` geen methoden bevat voor het bijwerken of verwijderen van entiteiten, fungeert het als een gestructureerde wrapper rond een collectie objecten. Dit maakt het ideaal voor het bieden van georganiseerde, efficiënte gegevens toegang.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  //Overige implementatie
}
```

Om de `Repository` met gegevens te vullen, fungeert de `Service`-klasse als de centrale beheerder, die de laad- en organisatieprocessen van de middelen in de app afhandelt. Klantgegevens worden gelezen uit een JSON-bestand en in de `Customer`-objecten in de `Repository` gemapt.

De `Assets`-utility in webforJ maakt het gemakkelijk om deze gegevens dynamisch te laden met behulp van context-URL's. Om middelen en gegevens in webforJ te laden, gebruikt de `Service`-klasse context-URL's met de `Assets`-utility. Klantgegevens kunnen bijvoorbeeld als volgt uit het JSON-bestand worden geladen:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Gebruik van de `ObjectTable`
De `Service`-klasse gebruikt de `ObjectTable` om instantie dynamisch te beheren, in plaats van te vertrouwen op statische velden. Deze aanpak adresseert een belangrijke beperking bij het gebruik van servlets: statische velden zijn gebonden aan de levenscyclus van de server en kunnen leiden tot problemen in omgevingen met meerdere aanvragen of gelijktijdige sessies. De `ObjectTable` is gebonden aan de gebruikerssessie, en het gebruik ervan zorgt voor een singleton-achtige gedrag zonder deze beperkingen, wat consistente en schaalbare gegevensbeheer mogelijk maakt.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Privé constructeur om gecontroleerde creatie af te dwingen
  private Service() {
    // implementatie
  }

  // Haal de huidige instantie van Service op of maak er een als deze niet bestaat
  public static Service getCurrent() {
    // implementatie
  }

  // Laad klantgegevens uit het JSON-bestand en map deze naar Customer-objecten
  private List<Customer> buildDemoList() {
    // implementatie
  }

  // Getter...
}
```

## Een `Table` maken en gebruiken {#creating-and-using-a-table}

Nu de benodigde gegevens correct zijn aangemaakt via de `Customer`-klasse en als `Repository` via de `Service`-klasse kan worden geretourneerd, is de laatste taak in deze stap om de `Table`-component in de app te integreren om klantgegevens weer te geven.

:::tip Meer over de `Table`
Voor een meer gedetailleerd overzicht van de verschillende functies en gedragingen van de `Table`, zie [dit artikel](../../components/table/overview).
:::

De `Table` biedt een dynamische en flexibele manier om gestructureerde gegevens in jouw app weer te geven. Het is ontworpen om te integreren met de `Repository`-klasse, wat functies mogelijk maakt zoals gegevens query's, paginering en efficiënte updates. Een `Table` is zeer configureerbaar, waardoor je kolommen kunt definiëren, de weergave kunt controleren en het kunt koppelen aan gegevensrepositories met minimale inspanning.

### De `Table` in de app implementeren {#implementing-the-table-in-the-app}

Omdat de gegevens voor de `Table` volledig via de `Service`-klasse worden behandeld, is de belangrijkste taak in `DemoApplication.java` het configureren van de `Table` en deze koppelen aan de `Repository` die door de `Service` wordt verstrekt.

Om de `Table` te configureren:

- Stel de breedte en hoogte in voor lay-outdoeleinden met behulp van de methoden `setHeight()` en `setWidth()`.
- Definieer de kolommen en geef hun namen en de methoden op om de gegevens voor elk op te halen.
- Wijs de `Repository` toe om gegevens dynamisch te verstrekken.

Na het doen hiervan zal de code er ongeveer als volgt uitzien:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Andere componenten van stap één

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

Met de aangebrachte wijzigingen in de app, zullen de volgende stappen plaatsvinden wanneer de app wordt uitgevoerd:

1. De `Service`-klasse haalt `Customer`-gegevens op uit het JSON-bestand en slaat deze op in een `Repository`.
2. De `Table` integreert de `Repository` voor gegevens en vult zijn rijen dynamisch.

Met de `Table` die nu `Customer`-gegevens weergeeft, zal de volgende stap gericht zijn op het maken van een nieuw scherm om klantgegevens te wijzigen en het integreren van routing in de app.

Dit zal helpen bij het effectiever organiseren van de logica van de app door deze uit de hoofdklasse `App` te verplaatsen naar afzonderlijke schermen die via routes toegankelijk zijn.
