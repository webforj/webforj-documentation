---
title: Working With Data
sidebar_position: 3
_i18n_hash: 42dff7cecf07f976ccbe007e04e78a22
---
Dieser Schritt konzentriert sich darauf, Datenmanagement- und Anzeige-Funktionen zur Demo-App hinzuzufügen. Dazu werden Dummy-Daten über verschiedene `Customer`-Objekte erstellt, und die App wird aktualisiert, um diese Daten zu verarbeiten und in einer [`Table`](../../components/table/overview), die zur vorherigen App hinzugefügt wurde, anzuzeigen.

Es wird erläutert, wie eine `Customer`-Modellklasse erstellt wird und wie sie mit einer `Service`-Klasse integriert wird, um auf die erforderlichen Daten über die Implementierung eines Repositories zuzugreifen und diese zu verwalten. Anschließend wird detailliert beschrieben, wie die abgerufenen Daten verwendet werden, um eine `Table`-Komponente in der App zu implementieren, die Kundeninformationen in einem interaktiven und strukturierten Format anzeigt.

Am Ende dieses Schrittes wird die in dem [vorherigen Schritt](./creating-a-basic-app) erstellte App eine Tabelle mit den erstellten Daten anzeigen, die dann in den folgenden Schritten erweitert werden kann. Um die App auszuführen:

- Gehe zum Verzeichnis `2-working-with-data`
- Führe `mvn jetty:run` aus

## Erstellen eines Datenmodells {#creating-a-data-model}

Um eine `Table` zu erstellen, die Daten in der Haupt-App anzeigt, muss eine Java-Bean-Klasse erstellt werden, die mit der `Table` verwendet werden kann, um Daten anzuzeigen.

In diesem Programm übernimmt die `Customer`-Klasse in `src/main/java/com/webforj/demos/data/Customer.java` diese Aufgabe. Diese Klasse dient als das zentrale Datenmodell für die App und kapselt kundenbezogene Attribute wie `firstName`, `lastName`, `company` und `country`. Dieses Modell wird auch eine eindeutige ID enthalten.

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

    // Weitere Länder
  }

    // Getter und Setter

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info Verwendung von `HasEntityKey` für eindeutige Identifikatoren

Die Implementierung des `HasEntityKey`-Interfaces ist entscheidend für die Verwaltung eindeutiger Identifikatoren in Modellen, die mit einer `Table` verwendet werden. Es stellt sicher, dass jede Instanz des Modells über einen eindeutigen Schlüssel verfügt, wodurch die `Table` in der Lage ist, Zeilen effektiv zu identifizieren und zu verwalten.

Für dieses Demo gibt die Methode `getEntityKey()` für jeden Kunden eine UUID zurück, die eine eindeutige Identifizierung gewährleistet. Während hier UUIDs der Einfachheit halber verwendet werden, ist in realen Anwendungen oft ein Primärschlüssel einer Datenbank die bessere Wahl für die Generierung eindeutiger Schlüssel.

Wenn `HasEntityKey` nicht implementiert ist, wird die `Table` standardmäßig den Java-Hashcode als Schlüssel verwenden. Da Hashcodes nicht garantiert eindeutig sind, kann dies zu Konflikten bei der Verwaltung von Zeilen in der `Table` führen.
:::

Mit dem `Customer`-Datenmodell an Ort und Stelle besteht der nächste Schritt darin, diese Modelle innerhalb der App zu verwalten und zu organisieren.

## Erstellen einer `Service`-Klasse {#creating-a-service-class}

Die `Service`-Klasse fungiert als zentraler Datenmanager, der nicht nur `Customer`-Daten lädt, sondern auch eine effiziente Schnittstelle für den Zugriff auf und die Interaktion mit diesen bietet.

Die Klasse `Service.java` wird in `src/main/java/com/webforj/demos/data` erstellt. Anstatt Daten manuell zwischen Komponenten oder Klassen zu übergeben, fungiert der `Service` als gemeinsame Ressource, die es interessierten Parteien ermöglicht, Daten einfach abzurufen und damit zu interagieren.

In dieser Demo liest die `Service`-Klasse Kundendaten aus einer JSON-Datei, die sich unter `src/main/resources/data/customers.json` befindet. Die Daten werden auf `Customer`-Objekte abgebildet und in einer `ArrayList` gespeichert, die die Grundlage für das `Repository` der Tabelle bildet.

In webforJ bietet die `Repository`-Klasse eine strukturierte Möglichkeit, Sammlungen von Entitäten zu verwalten und abzurufen. Sie fungiert als Schnittstelle zwischen deiner App und ihren Daten und bietet Methoden zum Abfragen, Zählen und Aktualisieren von Daten, während sie eine saubere und konsistente Struktur aufrechterhält. Sie wird von der `Table`-Klasse verwendet, um die darin gespeicherten Daten anzuzeigen.

Obwohl das `Repository` keine Methoden zum Aktualisieren oder Löschen von Entitäten enthält, dient es als strukturierter Wrapper um eine Sammlung von Objekten. Dies macht es ideal für den organisierten, effizienten Datenzugriff.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // Weitere Implementierung
}
```

Um das `Repository` mit Daten zu befüllen, fungiert die `Service`-Klasse als zentraler Manager, der das Laden und Organisieren von Ressourcen in der App verwaltet. Kundendaten werden aus einer JSON-Datei gelesen und den `Customer`-Objekten im `Repository` zugeordnet. 

Das `Assets`-Hilfsprogramm in webforJ erleichtert das dynamische Laden dieser Daten mithilfe von Kontext-URLs. Um Ressourcen und Daten in webforJ zu laden, verwendet die `Service`-Klasse Kontext-URLs mit dem `Assets`-Hilfsprogramm. Beispielsweise können Kundendaten aus der JSON-Datei wie folgt geladen werden:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Verwendung der `ObjectTable`
Die `Service`-Klasse verwendet die `ObjectTable`, um Instanzen dynamisch zu verwalten, anstatt sich auf statische Felder zu verlassen. Dieser Ansatz behebt eine wesentliche Einschränkung bei der Verwendung von Servlets: Statische Felder sind an den Lebenszyklus des Servers gebunden und können in Umgebungen mit mehreren Anfragen oder gleichzeitigen Sitzungen zu Problemen führen. Die `ObjectTable` ist auf die Benutzersitzung beschränkt, und ihre Verwendung garantiert ein Singleton-ähnliches Verhalten ohne diese Einschränkungen, was eine konsistente und skalierbare Datenverwaltung ermöglicht.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Privater Konstruktor zur Durchsetzung kontrollierter Instanziierung
  private Service() {
    // Implementierung
  }

  // Gibt die aktuelle Instanz von Service zurück oder erstellt eine, wenn sie nicht existiert
  public static Service getCurrent() {
    // Implementierung
  }

  // Lädt Kundendaten aus der JSON-Datei und mappt sie auf Customer-Objekte
  private List<Customer> buildDemoList() {
    // Implementierung
  }

  // Getter...
}
```

## Erstellen und Verwenden einer `Table` {#creating-and-using-a-table}

Jetzt, da die benötigten Daten über die `Customer`-Klasse richtig erstellt wurden und über die `Service`-Klasse als ein `Repository` zurückgegeben werden können, besteht die letzte Aufgabe in diesem Schritt darin, die `Table`-Komponente in die App zu integrieren, um Kundendaten anzuzeigen.

:::tip Mehr über die `Table`
Für einen detaillierteren Überblick über die verschiedenen Funktionen und Verhaltensweisen der `Table`, siehe [diesen Artikel](../../components/table/overview).
:::

Die `Table` bietet eine dynamische und flexible Möglichkeit, strukturierte Daten in deiner App anzuzeigen. Sie wurde entwickelt, um sich mit der `Repository`-Klasse zu integrieren, um Funktionen wie Datenabfrage, Paginierung und effiziente Aktualisierungen zu ermöglichen. Eine `Table` ist hochgradig konfigurierbar und ermöglicht es dir, Spalten zu definieren, ihr Erscheinungsbild zu steuern und sie mit Daten-Repositories mit minimalem Aufwand zu verbinden.

### Implementierung der `Table` in der App {#implementing-the-table-in-the-app}

Da die Daten für die `Table` vollständig über die `Service`-Klasse verwaltet werden, besteht die Hauptaufgabe in `DemoApplication.java` darin, die `Table` zu konfigurieren und sie mit dem vom `Service` bereitgestellten `Repository` zu verknüpfen.

Um die `Table` zu konfigurieren:

- Setze ihre Breite und Höhe für Layoutzwecke mithilfe der Methoden `setHeight()` und `setWidth()`.
- Definiere die Spalten, indem du ihre Namen und die Methoden angibst, um die Daten für jede zu holen.
- Weise das `Repository` zu, um Daten dynamisch bereitzustellen.

Nachdem dies erledigt ist, sieht der Code ungefähr so aus:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Weitere Komponenten aus Schritt eins

  // Die Table-Komponente zum Anzeigen von Kundendaten
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // Vorherige Implementierung aus Schritt eins
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Setze die Höhe der Tabelle auf 300 Pixel
    table.setHeight("300px");
    // Setze die Breite der Tabelle auf 1000 Pixel
    table.setWidth(1000);

    // Füge die verschiedenen Spaltennamen hinzu und weise die entsprechenden Getter zu
    table.addColumn("Vorname", Customer::getFirstName);
    table.addColumn("Nachname", Customer::getLastName);
    table.addColumn("Unternehmen", Customer::getCompany);
    table.addColumn("Land", Customer::getCountry);

    // Binde die Table an ein Repository, das Kundendaten enthält
    // Das Repository wird über die Service-Klasse abgerufen
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Mit den abgeschlossenen Änderungen, die in die App implementiert wurden, werden folgende Schritte ausgeführt, wenn die App ausgeführt wird:

1. Die `Service`-Klasse ruft `Customer`-Daten aus der JSON-Datei ab und speichert sie in einem `Repository`.
2. Die `Table` integriert das `Repository` für Daten und füllt ihre Zeilen dynamisch.

Mit der nun die `Customer`-Daten anzeigenden `Table` wird der nächste Schritt darin bestehen, einen neuen Bildschirm zu erstellen, um Kundendetails zu bearbeiten und das Routing in die App zu integrieren.

Dies wird es ermöglichen, die Logik der App effektiver zu organisieren, indem sie aus der Hauptklasse `App` heraus und in konstituierende Bildschirme verlagert wird, die über Routen zugänglich sind.
