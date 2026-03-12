---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 673861b579764a7f9b81512fc0b1e576
---
Bis jetzt war dieses Tutorial nur eine Ein-Seiten-App. Dieser Schritt ändert das. 
Sie werden die Benutzeroberfläche, die Sie in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data) erstellt haben, auf ihre eigene Seite verschieben und eine weitere Seite zum Hinzufügen neuer Kunden erstellen. 
Dann verbinden Sie diese Seiten, sodass Ihre App in der Lage ist, zwischen ihnen zu navigieren, indem Sie diese Konzepte anwenden:

- [Routing](/docs/routing/overview)
- [Komposite Komponenten](/docs/building-ui/composite-components)
- Die [`ColumnsLayout`](/docs/components/columns-layout) Komponente

Wenn Sie diesen Schritt abschließen, erstellen Sie eine Version von [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Video hier einfügen -->

## Ausführen der App {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält; das ist `3-routing-and-composites`, wenn Sie mit der Version auf GitHub fortfahren.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Beim Ausführen der App wird automatisch ein neuer Browser unter `http://localhost:8080` geöffnet.

## Routbare Apps {#routable-apps}

Zuvor hatte Ihre App eine einzige Funktion: die Anzeige einer Tabelle mit vorhandenen Kundendaten. 
In diesem Schritt kann Ihre App auch die Kundendaten ändern, indem sie neue Kunden hinzufügt. 
Die Trennung der Benutzeroberflächen für Anzeige und Modifikation ist langfristig vorteilhaft für die Wartung und das Testen, daher fügen Sie dieses Feature als separate Seite hinzu. 
Sie werden Ihre App [routable](/docs/routing/overview) machen, sodass webforJ die beiden Benutzeroberflächen einzeln zugreifen und laden kann.

Eine routable App rendert die Benutzeroberfläche basierend auf der URL. Durch die Annotation der Klasse, die die `App`-Klasse erweitert, mit [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html), wird das Routing aktiviert, und das `packages`-Element zeigt webforJ, welche Pakete UI-Komponenten enthalten.

Wenn Sie die `@Routify`-Annotation zu `Application` hinzufügen, entfernen Sie die `run()`-Methode. Sie werden die Komponenten aus dieser Methode in eine Klasse verschieben, die Sie im Paket `com.webforj.tutorial.views` erstellen. Ihre aktualisierte `Application.java`-Datei sollte folgendermaßen aussehen:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

// Hinzugefügte @Routify-Annotation
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Entfernte überschriebenen App.run() Methode

}
```

:::tip Globale CSS
Das Beibehalten der `@StyleSheet`-Annotation in `Application` wendet dieses CSS global an.
:::

### Erstellen von Routen {#creating-routes}

Das Hinzufügen der `@Routify`-Annotation macht Ihre App routable. Sobald sie routable ist, sucht Ihre App im Paket `com.webforj.tutorial.views` nach Routen. 
Sie müssen die Routen für Ihre Benutzeroberflächen erstellen und auch deren [Routenarten](/docs/routing/route-hierarchy/route-types) festlegen. Der Typ der Route bestimmt, wie der UI-Inhalt der URL zugeordnet wird.

Der erste Routentyp ist `View`. Diese Art von Routen wird direkt einem bestimmten URL-Segment in Ihrer App zugeordnet. Die Benutzeroberflächen für die Tabelle und das Formular für neue Kunden werden beide `View`-Routen sein.

Der zweite Routentyp ist `Layout`, der UI enthält, die auf mehreren Seiten angezeigt wird, wie z. B. eine Kopfzeile oder eine Seitenleiste. Layout-Routen umhüllen auch untergeordnete Ansichten, ohne zur URL beizutragen.

Um den Routentyp einer Klasse anzugeben, fügen Sie den Routentyp als Suffix am Ende des Klassennamens hinzu. 
Zum Beispiel ist `MainView` ein Routentyp `View`.

Um die beiden Funktionen der App voneinander zu trennen, muss Ihre App die Benutzeroberflächen auf zwei einzigartige `View`-Routen zuordnen: eine für die Tabelle und eine für das Kundenformular. Erstellen Sie in `/src/main/java/com/webforj/tutorial/views` zwei Klassen mit dem Suffix `View`:

- **`MainView`**: Diese Ansicht wird die `Table` enthalten, die sich zuvor in der `Application`-Klasse befand.
- **`FormView`**: Diese Ansicht wird ein Formular zum Hinzufügen neuer Kunden enthalten.

### Zuordnen von URLs zu Komponenten {#mapping-urls-to-components}

Ihre App ist routable und weiß, dass sie nach zwei `View`-Routen, `MainView` und `FormView`, suchen muss, hat aber keine spezifische URL, um sie zu laden. Mit der Annotation `@Route` an einer Ansichtsklasse können Sie webforJ mitteilen, wo sie basierend auf einem gegebenen URL-Segment geladen werden soll. Zum Beispiel, wenn Sie `@Route("about")` in einer Ansicht verwenden, wird die Klasse lokal zur URL `http://localhost:8080/about` zugeordnet.

Wie der Name schon sagt, ist `MainView` die Klasse, die Sie beim Starten der App zuerst laden möchten. Um dies zu erreichen, fügen Sie eine `@Route`-Annotation hinzu, die `MainView` zur Stamm-URL Ihrer App zuordnet:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Für `FormView` ordnen Sie die Ansicht so zu, dass sie geladen wird, wenn ein Benutzer zu `http://localhost:8080/customer` geht:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standardverhalten
Wenn Sie der `@Route`-Annotation keinen Wert zuweisen, ist das URL-Segment der Klassenname, der in Kleinbuchstaben umgewandelt wird, mit dem entfernten Suffix `View`.

- `MainView` würde zu `/main` zugeordnet werden
- `FormView` würde zu `/form` zugeordnet werden
:::

## Gemeinsame Merkmale {#shared-characteristics}

Neben der Tatsache, dass es sich bei beiden um Ansichts-Routen handelt, teilen `MainView` und `FormView` zusätzliche Merkmale. Einige dieser gemeinsamen Merkmale, wie die Verwendung von `Composite`-Komponenten, sind grundlegend für die Verwendung von webforJ-Apps, während andere es einfacher machen, Ihre App zu verwalten.

### Verwendung von `Composite` Komponenten {#using-composite-components}

Als die App eine Ein-Seiten-Anwendung war, hatten Sie die Komponenten innerhalb eines `Frame` gespeichert. Im Folgenden, mit einer App mit mehreren Ansichten, müssen Sie diese UI-Komponenten innerhalb von [`Composite`-Komponenten](/docs/building-ui/composite-components) umschließen.

`Composite`-Komponenten sind Wrapper, die es einfach machen, wiederverwendbare Komponenten zu erstellen. 
Um eine `Composite`-Komponente zu erstellen, erweitern Sie die `Composite`-Klasse mit einer bestimmten gebundenen Komponente, die als Grundlage der Klasse dient, z. B. `Composite<FlexLayout>`. 

Dieses Tutorial verwendet `Div`-Elemente als gebundene Komponenten, aber sie können jede Komponente sein, wie [`FlexLayout`](/docs/components/flex-layout) oder [`AppLayout`](/docs/components/app-layout). Mit der Methode `getBoundComponent()` können Sie auf die gebundene Komponente zugreifen und deren Methoden verwenden. Dadurch können Sie die Größe festlegen, einen CSS-Klassennamen hinzufügen, Komponenten hinzufügen, die Sie in der `Composite`-Komponente anzeigen möchten, und auf komponentenspezifische Methoden zugreifen.

Für `MainView` und `FormView` erweitern Sie `Composite` mit `Div` als gebundene Komponente. Dann referenzieren Sie diese gebundene Komponente, damit Sie später die UIs hinzufügen können. Beide Ansichten sollten folgendermaßen aussehen:

```java
// Erweitern Sie Composite mit einer gebundenen Komponente
public class MainView extends Composite<Div> {

  // Zugriff auf die gebundene Komponente
  private Div self = getBoundComponent();

  // Erstellen Sie eine UI-Komponente
  private Button submit = new Button("Submit");

  public MainView() {

    // Fügen Sie die UI-Komponente zur gebundenen Komponente hinzu
    self.add(submit);
  }
}
```

### Festlegen des Rahmentitels {#setting-the-frame-tile}

Wenn ein Benutzer mehrere Registerkarten in seinem Browser hat, hilft ein eindeutiger Rahmentitel dabei, schnell zu identifizieren, welcher Teil der App geöffnet ist.

Die Annotation [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) definiert, was im Titel des Browsers oder der Registerkarte der Seite erscheint. Für beide Ansichten fügen Sie einen Rahmentitel mit der `@FrameTitle`-Annotation hinzu:

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Kundentabelle")
  public class MainView extends Composite<Div> {

    private Div self = getBoundComponent();

    public MainView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java title="FormView.java" {2}
  @Route("customer")
  @FrameTitle("Kundenformular")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### Gemeinsame CSS {#shared-css}

Mit einer gebundenen Komponente, auf die Sie in `MainView` und `FormView zugreifen können, können Sie sie mit CSS gestalten. 
Sie können die CSS aus dem ersten Schritt, [Erstellen einer grundlegenden App](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), verwenden, um beiden Ansichten identische UI-Container-Stile zu geben. 
Fügen Sie der gebundenen Komponente in jeder Ansicht den CSS-Klassennamen `card` hinzu:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Kundentabelle")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {9} title="FormView.java"
    @Route("customer")
    @FrameTitle("Kundenformular")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### Verwendung von `CustomerService` {#using-customerservice}

Der letzte gemeinsame Trait für die Ansichten ist die Verwendung der `CustomerService`-Klasse. 
Die `Table` in `MainView` zeigt jeden Kunden an, während `FormView` neue Kunden hinzufügt. Da beide Ansichten mit Kundendaten interagieren, benötigen sie Zugriff auf die Geschäftslogik der App. 

Die Ansichten erhalten Zugriff über den in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data#creating-a-service) erstellten Spring-Service, `CustomerService`. Um den Spring-Service in jeder Ansicht zu verwenden, machen Sie `CustomerService` zu einem Konstruktorparameter:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Kundentabelle")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {7-8} title="FormView.java"
    @Route("customer")
    @FrameTitle("Kundenformular")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

## Erstellen von `MainView` {#creating-mainview}

Nachdem Sie Ihre App routable gemacht haben, die Ansichten mit `Composite`-Komponenten-Wrappern versehen und `CustomerService` einbezogen haben, sind Sie bereit, die UIs zu erstellen, die für jede Ansicht einzigartig sind. Wie bereits erwähnt, enthält `MainView` die UI-Komponenten, die sich anfangs in `Application` befanden. Diese Klasse benötigt außerdem eine Möglichkeit, zu `FormView` zu navigieren.

### Gruppierung der `Table`-Methoden {#grouping-the-table-methods}

Während Sie die Komponenten von `Application` nach `MainView` verschieben, ist es eine gute Idee, damit zu beginnen, Teile Ihrer App zu sectionieren, sodass eine benutzerdefinierte Methode die Änderungen an der `Table` auf einmal vornehmen kann. Das Sectionieren Ihres Codes erleichtert die Verwaltung, wenn die App komplexer wird.

Jetzt sollte der Konstruktor Ihrer `MainView`-Klasse nur eine `buildTable()`-Methode aufrufen, die die Spalten hinzufügt, die Größen festlegt und auf das Repository verweist:

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
  table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
  table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
  table.addColumn("country", Customer::getCountry).setLabel("Land");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### Navigieren zu `FormView` {#navigating-to-formview}

Benutzer benötigen eine Möglichkeit, von `MainView` zu `FormView` zu navigieren, indem sie die UI benutzen.

In webforJ können Sie direkt zu einer neuen Ansicht navigieren, indem Sie die Klassennamen der Ansicht verwenden. Das Routing über eine Klasse anstelle eines URL-Segments gewährleistet, dass webforJ den richtigen Pfad zur Ladung der Ansicht einschlägt. 

Um zu einer anderen Ansicht zu navigieren, verwenden Sie die [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html)-Klasse, um den aktuellen Standort mit `getCurrent()` zu erhalten, und verwenden Sie dann die Methode `navigate()` mit der Klassenbezeichnung der Ansicht als Parameter: 

```java
Router.getCurrent().navigate(FormView.class);
```

Dieser Code wird die Benutzer programmatisch zum Formular für neue Kunden senden, aber die Navigation muss mit einer Benutzeraktion verbunden sein. 
Um Benutzern das Hinzufügen eines neuen Kunden zu ermöglichen, können Sie entweder die Info-Schaltfläche von `Application` ändern oder ersetzen. Anstelle eines Meldungsdialogs kann die Schaltfläche zu der `FormView`-Klasse navigieren:

```java
private Button addCustomer = new Button("Neuen Kunden hinzufügen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Vollständige `MainView` {#completed-mainview}

Mit der Navigation zu `FormView` und den gruppierten Tabellenmethoden sollte `MainView` wie folgt aussehen, bevor Sie mit der Erstellung von `FormView` fortfahren:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
  @FrameTitle("Kundentabelle")
  public class MainView extends Composite<Div> {
    private final CustomerService customerService;
    private Div self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Neuen Kunden hinzufügen", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      self.setWidth("fit-content")
          .addClassName("card")
          .add(table, addCustomer);
    }

    private void buildTable() {
      table.setSize("1000px", "294px");
      table.setMaxWidth("90vw");
      table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
      table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
      table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
      table.addColumn("country", Customer::getCountry).setLabel("Land");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Erstellen von `FormView` {#creating-formview}

`FormView` wird ein Formular zur Eingabe neuer Kunden anzeigen. Für jedes Kundenattribut hat `FormView` eine Editierkomponente, mit der Benutzer interagieren können. Darüber hinaus wird es einen Button für Benutzer geben, um die Daten zu übermitteln, und einen Abbrechen-Button, um sie zu verwerfen.

### Erstellen einer `Customer`-Instanz {#creating-a-customer-instance}

Wenn ein Benutzer Daten für einen neuen Kunden bearbeitet, sollten Änderungen nur auf das Repository angewendet werden, wenn er bereit ist, das Formular zu übermitteln. Die Verwendung einer Instanz des `Customer`-Objekts ist eine bequeme Möglichkeit, die neuen Daten zu bearbeiten und zu verwalten, ohne das Repository direkt zu bearbeiten. Erstellen Sie eine neue `Customer`-Instanz innerhalb von `FormView`, um sie für das Formular zu verwenden:

```java
private Customer customer = new Customer();
```

Um die `Customer`-Instanz editierbar zu machen, sollte jedes Attribut, mit Ausnahme von `id`, mit einer editierbaren Komponente verknüpft werden. Die Änderungen, die ein Benutzer in der Benutzeroberfläche vornimmt, sollten in der `Customer`-Instanz widergespiegelt werden.

### Hinzufügen von `TextField`-Komponenten {#adding-textfield-components}

Die ersten drei editierbaren Eigenschaften in `Customer` (`firstName`, `lastName` und `company`) sind alle `String`-Werte und sollten mit einem einzeiligen Texteditor dargestellt werden. [`TextField`](/docs/components/fields/textfield)-Komponenten sind eine großartige Wahl, um diese Eigenschaften darzustellen.

Mit der `TextField`-Komponente können Sie ein Label hinzufügen und einen Ereignis-Listener anfügen, der immer dann ausgelöst wird, wenn sich der Wert des Feldes ändert. Jeder Ereignis-Listener sollte die `Customer`-Instanz für die entsprechende Eigenschaft aktualisieren.

Fügen Sie drei `TextField`-Komponenten hinzu, die die `Customer`-Instanz aktualisieren:

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Unternehmen", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip Gemeinsame Namenskonvention
Das Benennen der Komponenten, die die Eigenschaften der `Customer`-Entität darstellen, macht es einfacher, Daten in einem zukünftigen Schritt zu binden, [Validieren und Binden von Daten](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Hinzufügen einer `ChoiceBox`-Komponente {#adding-a-choicebox-component}

Die Verwendung eines `TextField` für die `country`-Eigenschaft wäre nicht ideal, da die Eigenschaft nur einer von fünf Enum-Werten sein kann: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` und `USA`.

Eine bessere Komponente zur Auswahl aus einer vordefinierten Liste von Optionen ist die [`ChoiceBox`](/docs/components/lists/choicebox).

Jede Option für eine `ChoiceBox`-Komponente wird als `ListItem` dargestellt. Jedes `ListItem` hat zwei Werte, ein `Object`-Schlüssel und einen `String`-Text, der in der Benutzeroberfläche angezeigt wird. Zwei Werte für jede Option zu haben, ermöglicht es Ihnen, das `Object` intern zu behandeln, während gleichzeitig eine lesbarere Option für Benutzer in der Benutzeroberfläche präsentiert wird.

Zum Beispiel könnte der `Object`-Schlüssel eine Internationale Standardbuchnummer (ISBN) sein, während der `String`-Text der Buchtitel ist, was lesbarer ist.

```java
new ListItem(isbn, bookTitle);
```

Dieses App handelt jedoch von einer Liste von Ländernamen und nicht von Büchern. Für jedes `ListItem` möchten Sie, dass der `Object` der `Customer.Country`-Enum ist, während der Text seine `String`-Darstellung sein kann.

Um alle Länderoptionen in eine `ChoiceBox` einzufügen, können Sie einen Iterator verwenden, um ein `ListItem` für jedes `Customer.Country`-Enum zu erstellen und diese in einer `ArrayList<ListItem>` zu speichern. Dann können Sie diese `ArrayList<ListItem>` in eine `ChoiceBox`-Komponente einfügen:

```java
// Erstellen Sie die ChoiceBox-Komponente
private ChoiceBox country = new ChoiceBox("Land");

// Erstellen Sie eine ArrayList von ListItem-Objekten
ArrayList<ListItem> listCountries = new ArrayList<>();

// Fügen Sie einen Iterator hinzu, der für jede Customer.Country-Option ein ListItem erstellt
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Fügen Sie die gefüllte ArrayList in die ChoiceBox ein
country.insert(listCountries);

// Macht das erste `ListItem` zum Standard, wenn das Formular geladen wird
country.selectIndex(0);
```

Dann, wenn der Benutzer eine Option in der `ChoiceBox` auswählt, sollte die `Customer`-Instanz mit dem Schlüssel des ausgewählten Elements, das ein `Customer.Country`-Wert ist, aktualisiert werden.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Um den Code sauber zu halten, sollte der Iterator, der die `ArrayList<ListItem>` erstellt und sie zur `ChoiceBox` hinzufügt, in einer separaten Methode sein. 
Nachdem Sie eine `ChoiceBox` hinzugefügt haben, die es dem Benutzer ermöglicht, die `country`-Eigenschaft auszuwählen, sollte `FormView` wie folgt aussehen:

```java title="FormView.java" {9-10,15,18-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Unternehmen", e -> customer.setCompany(e.getValue()));

  private ChoiceBox country = new ChoiceBox("Land",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
    fillCountries();
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

}
```

### Hinzufügen von `Button`-Komponenten {#adding-button-components}

Beim Verwenden des neuen Kundenformulars sollten die Benutzer in der Lage sein, ihre Änderungen entweder zu speichern oder zu verwerfen. 
Erstellen Sie zwei `Button`-Komponenten, um diese Funktion zu implementieren:

```java
private Button submit = new Button("Einreichen");
private Button cancel = new Button("Abbrechen");
```

Sowohl der Submit- als auch der Abbrechen-Button sollten den Benutzer zurück zu `MainView` führen. Dies ermöglicht es dem Benutzer, sofort die Ergebnisse seiner Aktion zu sehen, ob sie einen neuen Kunden in der Tabelle sehen oder ob dieser unverändert bleibt. 
Da mehrere Eingaben in `FormView` den Benutzer zu `MainView` führen, sollte die Navigation in eine wiederverwendbare Methode gesteckt werden:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Abbrechen-Button**

Das Verwerfen der Änderungen im Formular erfordert keinen zusätzlichen Code für das Ereignis, außer dass es zu `MainView` zurückkehrt. Da das Abbrechen jedoch keine primäre Aktion ist, verleiht das Festlegen des Themas der Schaltfläche auf umreißt dem Submit-Button mehr Bedeutung. 
Der Abschnitt [Themen](/docs/components/button#themes) auf der `Button`-Komponenten-Seite listet alle verfügbaren Themen auf.

```java
private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Submit-Button**

Wenn ein Benutzer auf die Submit-Schaltfläche drückt, sollten die Werte in der `Customer`-Instanz verwendet werden, um einen neuen Eintrag im Repository zu erstellen.

Mit `CustomerService` können Sie die `Customer`-Instanz verwenden, um die H2-Datenbank zu aktualisieren. Wenn dies geschieht, wird der `Customer` eine neue und eindeutige `id` zugewiesen. Nach dem Aktualisieren des Repositorys können Sie die Benutzer zu `MainView` umleiten, wo sie den neuen Kunden in der Tabelle sehen können.

```java
private Button submit = new Button("Einreichen", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Verwendung eines `ColumnsLayout` {#using-a-columnslayout}

Durch das Hinzufügen der `TextField`, `ChoiceBox` und `Button`-Komponenten haben Sie nun alle interaktiven Teile des Formulars. Die letzte Verbesserung für `FormView` in diesem Schritt besteht darin, die sechs Komponenten visuell zu organisieren.

Dieses Formular kann ein [`ColumnsLayout`](/docs/components/columns-layout) verwenden, um die Komponenten in zwei Spalten zu trennen, ohne die Breite der interaktiven Komponenten festlegen zu müssen. 
Um ein `ColumnsLayout` zu erstellen, geben Sie jede Komponente an, die sich innerhalb des Layouts befinden soll:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Um die Anzahl der Spalten für ein `ColumnsLayout` festzulegen, verwenden Sie eine Liste von `Breakpoint`-Objekten. Jeder `Breakpoint` sagt dem `ColumnsLayout`, welche Mindestbreite es haben muss, um eine bestimmte Anzahl von Spalten anzuwenden. Mit dem `ColumnsLayout` können Sie ein Formular mit zwei Spalten erstellen, aber nur, wenn der Bildschirm breit genug ist, um zwei Spalten anzuzeigen. Auf kleineren Bildschirmen werden die Komponenten in einer einzigen Spalte angezeigt.

Der Abschnitt [Breakpoints](/docs/components/columns-layout#breakpoints) im Artikel über `ColumnsLayout` erklärt Breakpoints näher.

Um den Code wartbar zu halten, sollten die Breakpoints in einer separaten Methode festgelegt werden. In dieser Methode können Sie auch den horizontalen und vertikalen Abstand zwischen den Komponenten innerhalb des `ColumnsLayout` mit der Methode `setSpacing()` steuern.

```java
private void setColumnsLayout() {

  // Haben Sie zwei Spalten im ColumnsLayout, wenn es breiter als 600px ist
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Fügen Sie die Liste der Breakpoints hinzu
  layout.setBreakpoints(breakpoints);

  // Stellen Sie den Abstand zwischen den Komponenten mit einer DWC-CSS-Variable ein
  layout.setSpacing("var(--dwc-space-l)")
}
```

Schließlich können Sie das neu erstellte `ColumnsLayout` der gebundenen Komponente von `FormView` hinzufügen, während Sie auch die maximale Breite festlegen und den zuvor erwähnten Klassennamen hinzufügen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Vollständige `FormView` {#completed-formview}

Nachdem Sie eine `Customer`-Instanz, die interaktiven Komponenten und das `ColumnsLayout` hinzugefügt haben, sollte Ihre `FormView` wie folgt aussehen:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
  @FrameTitle("Kundenformular")
  public class FormView extends Composite<Div> {
    private final CustomerService customerService;
    private Customer customer = new Customer();
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
    private TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
    private TextField company = new TextField("Unternehmen", e -> customer.setCompany(e.getValue()));
    private ChoiceBox country = new ChoiceBox("Land",
        e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
    private Button submit = new Button("Einreichen", ButtonTheme.PRIMARY, e -> submitCustomer());
    private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
    private ColumnsLayout layout = new ColumnsLayout(
        firstName, lastName,
        company, country,
        submit, cancel);

    public FormView(CustomerService customerService) {
      this.customerService = customerService;
      fillCountries();
      setColumnsLayout();
      self.setMaxWidth(600)
          .addClassName("card")
          .add(layout);
      submit.setStyle("margin-top", "var(--dwc-space-l)");
      cancel.setStyle("margin-top", "var(--dwc-space-l)");
    }

    private void setColumnsLayout() {
      List<Breakpoint> breakpoints = List.of(
          new Breakpoint(600, 2));
      layout.setSpacing("var(--dwc-space-l)")
          .setBreakpoints(breakpoints);
    }

    private void fillCountries() {
      ArrayList<ListItem> listCountries = new ArrayList<>();
      for (Country countryItem : Customer.Country.values()) {
        listCountries.add(new ListItem(countryItem, countryItem.toString()));
      }
      country.insert(listCountries);
      country.selectIndex(0);
    }

    private void submitCustomer() {
      customerService.createCustomer(customer);
      navigateToMain();
    }

    private void navigateToMain() {
      Router.getCurrent().navigate(MainView.class);
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Nächster Schritt {#next-step}

Da Benutzer jetzt Kunden hinzufügen können, sollte Ihre App in der Lage sein, vorhandene Kunden mit demselben Formular zu bearbeiten. Im nächsten Schritt [Beobachter und Routenparameter](/docs/introduction/tutorial/observers-and-route-parameters) werden Sie zulassen, dass die Kunden-ID ein Anfangsparameter für `FormView` ist, damit das Formular mit den Daten dieses Kunden ausgefüllt werden kann und Benutzer die Eigenschaften ändern können.
