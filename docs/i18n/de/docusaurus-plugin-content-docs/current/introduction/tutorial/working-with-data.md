---
title: Working With Data
sidebar_position: 3
_i18n_hash: 3afbf6e4eb4921183cc11d87c8457150
---
Dieser Schritt konzentriert sich darauf, Datenmanagement- und Anzeige-Funktionen zur Demoversion der App hinzuzufügen. Dazu werden Dummy-Daten zu verschiedenen `Customer`-Objekten erstellt, und die App wird aktualisiert, um diese Daten zu verarbeiten und in einer [`Table`](../../components/table/overview) anzuzeigen, die zur vorherigen App hinzugefügt wird.

Es wird beschrieben, wie eine `Customer`-Modellklasse erstellt und mit einer `Service`-Klasse integriert wird, um auf die benötigten Daten zuzugreifen und diese zu verwalten, indem die Implementierung eines Repositories verwendet wird. Anschließend wird detailliert, wie die abgerufenen Daten genutzt werden, um eine `Table`-Komponente in der App zu implementieren, die Kundeninformationen in einem interaktiven und strukturierten Format anzeigt.

Am Ende dieses Schrittes wird die in dem [vorherigen Schritt](./creating-a-basic-app) erstellte App eine Tabelle mit den erstellten Daten anzeigen, die in den folgenden Schritten weiter ausgebaut werden kann. Um die App auszuführen:

- Gehe ins Verzeichnis `2-working-with-data`
- Führe `mvn jetty:run` aus

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

## Erstellen eines Datenmodells {#creating-a-data-model}

Um eine `Table` zu erstellen, die Daten in der Hauptanwendung anzeigt, muss eine Java-Bean-Klasse erstellt werden, die zusammen mit der `Table` zur Anzeige von Daten verwendet werden kann.

In diesem Programm erledigt die `Customer`-Klasse in `src/main/java/com/webforj/demos/data/Customer.java` dies. Diese Klasse dient als das zentrale Datenmodell für die App und kapselt customer-bezogene Attribute wie `firstName`, `lastName`, `company` und `country`. Dieses Modell wird auch eine eindeutige ID enthalten.

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

Die Implementierung des `HasEntityKey`-Interfaces ist entscheidend für die Verwaltung eindeutiger Identifikatoren in Modellen, die mit einer `Table` verwendet werden. Es stellt sicher, dass jede Instanz des Modells einen eindeutigen Schlüssel hat, was es der `Table` ermöglicht, Zeilen effektiv zu identifizieren und zu verwalten.

Für diese Demo gibt die Methode `getEntityKey()` für jeden Kunden eine UUID zurück, die eine eindeutige Identifizierung gewährleistet. Während UUIDs hier der Einfachheit halber verwendet werden, ist in der realen Anwendung ein Primärschlüssel einer Datenbank oft die bessere Wahl zur Erstellung eindeutiger Schlüssel.

Wenn `HasEntityKey` nicht implementiert ist, wird die `Table` standardmäßig den Java-Hashcode als Schlüssel verwenden. Da Hashcodes nicht garantiert eindeutig sind, kann dies zu Konflikten bei der Verwaltung von Zeilen in der `Table` führen.
:::

Mit dem `Customer`-Datenmodell an seinem Platz ist der nächste Schritt, diese Modelle innerhalb der App zu verwalten und zu organisieren.

## Erstellen einer `Service`-Klasse {#creating-a-service-class}

Als zentraler Datenmanager lädt die `Service`-Klasse nicht nur `Customer`-Daten, sondern bietet auch eine effiziente Schnittstelle für den Zugriff auf und die Interaktion mit diesen Daten.

Die Klasse `Service.java` wird in `src/main/java/com/webforj/demos/data` erstellt. Anstatt Daten manuell zwischen Komponenten oder Klassen zu übergeben, fungiert die `Service`-Klasse als gemeinsame Ressource, die es interessierten Parteien ermöglicht, Daten einfach abzurufen und mit ihnen zu interagieren.

In dieser Demo liest die `Service`-Klasse Kundendaten aus einer JSON-Datei, die sich in `src/main/resources/data/customers.json` befindet. Die Daten werden auf `Customer`-Objekte gemappt und in einer `ArrayList` gespeichert, die die Grundlage für das `Repository` der Tabelle bildet.

In webforJ bietet die `Repository`-Klasse eine strukturierte Möglichkeit, Sammlungen von Entitäten zu verwalten und abzurufen. Sie fungiert als Schnittstelle zwischen Ihrer App und ihren Daten und bietet Methoden zum Abfragen, Zählen und Aktualisieren von Daten, während sie eine saubere und konsistente Struktur aufrechterhält. Sie wird von der `Table`-Klasse verwendet, um die darin gespeicherten Daten anzuzeigen.

Obwohl die `Repository`-Klasse keine Methoden zum Aktualisieren oder Löschen von Entitäten enthält, dient sie als strukturierter Wrapper um eine Sammlung von Objekten. Das macht sie ideal für einen organisierten und effizienten Datenzugriff.

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // verbleibende Implementierung
}
```

Um das `Repository` mit Daten zu füllen, fungiert die `Service`-Klasse als zentraler Manager, der das Laden und Organisieren von Assets in der App übernimmt. Kundendaten werden aus einer JSON-Datei gelesen und in den `Customer`-Objekten im `Repository` gemappt.

Das `Assets`-Utility in webforJ erleichtert das dynamische Laden dieser Daten mithilfe von Kontext-URLs. Um Assets und Daten in webforJ zu laden, verwendet die `Service`-Klasse Kontext-URLs mit dem `Assets`-Utility. Beispielweise können Kundendaten aus der JSON-Datei wie folgt geladen werden:

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip Verwendung der `ObjectTable`
Die `Service`-Klasse verwendet die `ObjectTable`, um Instanzen dynamisch zu verwalten, anstatt sich auf statische Felder zu verlassen. Dieser Ansatz behebt eine wichtige Einschränkung bei der Verwendung von Servlets: Statische Felder sind an den Lebenszyklus des Servers gebunden und können in Umgebungen mit mehreren Anfragen oder gleichzeitigen Sitzungen zu Problemen führen. Die `ObjectTable` ist auf die Benutzersitzung beschränkt, und ihre Verwendung gewährleistet ein Singleton-ähnliches Verhalten ohne diese Einschränkungen, was eine konsistente und skalierbare Datenverwaltung ermöglicht.
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // Privater Konstruktor, um kontrollierte Instanziierung durchzusetzen
  private Service() {
    // Implementierung
  }

  // Ruft die aktuelle Instanz von Service ab oder erstellt eine, wenn sie nicht existiert
  public static Service getCurrent() {
    // Implementierung
  }

  // Lädt Kundendaten aus der JSON-Datei und mapped sie auf Customer-Objekte
  private List<Customer> buildDemoList() {
    // Implementierung
  }

  // Getter...
}
```

## Erstellen und Verwenden einer `Table` {#creating-and-using-a-table}

Jetzt, da die benötigten Daten ordnungsgemäß über die `Customer`-Klasse erstellt wurden und als `Repository` über die `Service`-Klasse zurückgegeben werden können, besteht die letzte Aufgabe in diesem Schritt darin, die `Table`-Komponente in die App zu integrieren, um die Kundendaten anzuzeigen.

:::tip Weitere Informationen zur `Table`
Für eine detailliertere Übersicht über die verschiedenen Funktionen und Verhaltensweisen der `Table`, siehe [diesen Artikel](../../components/table/overview).
:::

Die `Table` bietet eine dynamische und flexible Möglichkeit, strukturierte Daten in Ihrer App anzuzeigen. Sie wurde entwickelt, um mit der `Repository`-Klasse zu integrieren, wodurch Funktionen wie Datenabfragen, Paginierung und effiziente Aktualisierungen ermöglicht werden. Eine `Table` ist hochgradig konfigurierbar, sodass Sie Spalten definieren, das Erscheinungsbild steuern und sie mit minimalem Aufwand an Datenrepositorys binden können.

### Implementierung der `Table` in der App {#implementing-the-table-in-the-app}

Da die Daten für die `Table` vollständig über die `Service`-Klasse verwaltet werden, besteht die Hauptaufgabe in `DemoApplication.java` darin, die `Table` zu konfigurieren und sie mit dem von der `Service`-Klasse bereitgestellten `Repository` zu verknüpfen.

Um die `Table` zu konfigurieren:

- Setzen Sie ihre Höhe und Breite für Layout-Zwecke mit den Methoden `setHeight()` und `setWidth()`.
- Definieren Sie die Spalten, wobei Sie deren Namen angeben und die Methoden zur Abfrage der Daten für jede Spalte festlegen.
- Weisen Sie das `Repository` zu, um die Daten dynamisch bereitzustellen.

Nachdem dies geschehen ist, wird der Code ähnlich wie im folgenden Snippet aussehen:

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // Weitere Komponenten aus Schritt eins

  // Die Table-Komponente zur Anzeige von Kundendaten
  Table<Customer> table = new Table<>(); 

  @Override
  public void run() throws WebforjException {
    // Vorherige Implementierung von Schritt eins
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // Die Höhe der Tabelle auf 300 Pixel setzen
    table.setHeight("300px");
    // Die Breite der Tabelle auf 1000 Pixel setzen
    table.setWidth(1000);

    // Fügen Sie die verschiedenen Spaltentitel hinzu und weisen Sie die entsprechenden Getter zu
    table.addColumn("Vorname", Customer::getFirstName);
    table.addColumn("Nachname", Customer::getLastName);
    table.addColumn("Unternehmen", Customer::getCompany);
    table.addColumn("Land", Customer::getCountry);

    // Binden Sie die Tabelle an ein Repository, das Kundendaten enthält
    // Das Repository wird über die Service-Klasse abgerufen
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

Nachdem die Änderungen an der App implementiert wurden, geschehen beim Ausführen der App die folgenden Schritte:

1. Die `Service`-Klasse ruft die `Customer`-Daten aus der JSON-Datei ab und speichert sie in einem `Repository`.
2. Die `Table` integriert das `Repository` für Daten und füllt ihre Zeilen dynamisch.

Da die `Table` nun die `Customer`-Daten anzeigt, wird der nächste Schritt darin bestehen, einen neuen Bildschirm zu erstellen, um die Kundendetails zu ändern und das Routing in die App zu integrieren.

Dies ermöglicht eine effektivere Organisation der Logik der App, indem sie aus der Hauptklasse `App` herausbewegt und in separate Bildschirme verschoben wird, die über Routen aufgerufen werden können.
