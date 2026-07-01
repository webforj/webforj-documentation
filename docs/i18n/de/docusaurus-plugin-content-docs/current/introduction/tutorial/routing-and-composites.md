---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 1ffb9e9bf7ba8d863dad3bc0c42c11d7
---
Bis jetzt war dieses Tutorial nur eine Einzelseitenanwendung. Dieser Schritt ändert das. 
Sie werden die Benutzeroberfläche, die Sie in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data) erstellt haben, auf eine eigene Seite verschieben und eine weitere Seite zum Hinzufügen neuer Kunden erstellen.
Dann verbinden Sie diese Seiten, damit Ihre Anwendung zwischen ihnen navigieren kann, indem Sie diese Konzepte anwenden:

- [Routing](/docs/routing/overview)
- [Komponenten zusammenstellen](/docs/building-ui/composing-components)
- Die [`ColumnsLayout`](/docs/components/columns-layout) Komponente

Abschluss dieses Schrittes erstellt eine Version von [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Hier Video einfügen -->

## Die Anwendung ausführen {#running-the-app}

Während Sie Ihre Anwendung entwickeln, können Sie [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) als Vergleich verwenden. Um die Anwendung in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält; dies ist `3-routing-and-composites`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-Anwendung lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der Anwendung öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Routbare Anwendungen {#routable-apps}

Früher hatte Ihre Anwendung eine einzige Funktion: eine Tabelle mit vorhandenen Kundendaten anzuzeigen. 
In diesem Schritt wird Ihre Anwendung auch in der Lage sein, die Kundendaten zu ändern, indem neue Kunden hinzugefügt werden.
Die Trennung der Benutzeroberflächen für Anzeige und Bearbeitung ist für die langfristige Wartung und Testen von Vorteil, daher fügen Sie diese Funktion als separate Seite hinzu.
Sie werden Ihre Anwendung [routbar](/docs/routing/overview) machen, damit webforJ auf die beiden Benutzeroberflächen einzeln zugreifen und sie laden kann.

Eine routbare Anwendung rendert die Benutzeroberfläche basierend auf der URL. Das Annotieren der Klasse, die die `App`-Klasse erweitert, mit [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) ermöglicht Routing, und das Element `packages` sagt webforJ, welche Pakete UI-Komponenten enthalten.

Wenn Sie die `@Routify`-Annotation zu `Application` hinzufügen, entfernen Sie die `run()`-Methode. Sie werden die Komponenten aus dieser Methode in eine Klasse verschieben, die Sie im `com.webforj.tutorial.views`-Paket erstellen werden. Ihre aktualisierte `Application.java`-Datei sollte so aussehen:

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

// Entfernte überschreibende App.run()-Methode

}
```

:::tip Globale CSS
Die Beibehaltung der `@StyleSheet`-Annotation in `Application` wendet dieses CSS global an.
:::

### Routen erstellen {#creating-routes}

Das Hinzufügen der `@Routify`-Annotation macht Ihre Anwendung routbar. Sobald es routbar ist, sucht Ihre Anwendung im Paket `com.webforj.tutorial.views` nach Routen. 
Sie müssen die Routen für Ihre Benutzeroberflächen erstellen und auch ihre [Routentypen](/docs/routing/route-hierarchy/route-types) angeben. Der Routentyp bestimmt, wie der UI-Inhalt auf die URL abgebildet wird.

Der erste Routentyp ist `View`. Diese Art von Routen mappt direkt auf einen bestimmten URL-Abschnitt in Ihrer Anwendung. Die Benutzeroberflächen für die Tabelle und das neue Kundenformular werden beide `View`-Routen sein.

Der zweite Routentyp ist `Layout`, der UI enthält, die auf mehreren Seiten angezeigt wird, wie z. B. eine Kopfzeile oder eine Seitenleiste. Layout-Routen umschließen auch untergeordnete Ansichten, ohne zur URL beizutragen.

Um den Routentyp einer Klasse anzugeben, fügen Sie den Routentyp als Suffix an das Ende des Klassennamens an.
Beispielsweise ist `MainView` ein `View`-Routentyp.

Um die beiden Funktionen der Anwendung voneinander zu trennen, muss Ihre Anwendung die Benutzeroberflächen auf zwei eindeutige `View`-Routen abbilden: eine für die Tabelle und eine für das Kundenformular. Erstellen Sie im Verzeichnis `/src/main/java/com/webforj/tutorial/views` zwei Klassen mit dem Suffix `View`:

- **`MainView`**: Diese Ansicht wird die `Table` haben, die zuvor in der `Application`-Klasse war.
- **`FormView`**: Diese Ansicht wird ein Formular zum Hinzufügen neuer Kunden haben.

### URLs auf Komponenten abbilden {#mapping-urls-to-components}

Ihre Anwendung ist routbar und weiß, dass sie nach zwei `View`-Routen, `MainView` und `FormView`, suchen muss, hat jedoch keine spezifische URL, um sie zu laden. Mit der `@Route`-Annotation auf einer Ansichts-Klasse können Sie webforJ mitteilen, wo sie basierend auf einem bestimmten URL-Abschnitt geladen werden soll. Zum Beispiel bedeutet die Verwendung von `@Route("about")` in einer Ansicht, dass die Klasse lokal auf `http://localhost:8080/about` abgebildet wird.

Wie der Name schon sagt, ist `MainView` die Klasse, die Sie zunächst laden möchten, wenn die Anwendung ausgeführt wird. Um dies zu erreichen, fügen Sie eine `@Route`-Annotation hinzu, die `MainView` an die Root-URL Ihrer Anwendung abbildet:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Für die `FormView` ordnen Sie die Ansicht so zu, dass sie geladen wird, wenn ein Benutzer zu `http://localhost:8080/customer` geht:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standardverhalten
Wenn Sie der `@Route`-Annotation keinen Wert zuweisen, wird der URL-Abschnitt in den Klassennamen umgewandelt und das `View`-Suffix entfernt.

- `MainView` würde zu `/main` abgebildet
- `FormView` würde zu `/form` abgebildet
:::

## Gemeinsame Merkmale {#shared-characteristics}

Neben der Tatsache, dass beide Ansichten Routen sind, teilen sich `MainView` und `FormView` zusätzliche Merkmale. Einige dieser gemeinsamen Eigenschaften, wie die Verwendung von `Composite`-Komponenten, sind grundlegend für die Verwendung von webforJ-Anwendungen, während andere es einfach einfacher machen, Ihre Anwendung zu verwalten.

### Verwendung von `Composite`-Komponenten {#using-composite-components}

Als die Anwendung eine einzige Seite hatte, haben Sie die Komponenten in einem `Frame` gespeichert. In Zukunft, mit einer Anwendung mit mehreren Ansichten, müssen Sie diese UI-Komponenten in [`Composite`-Komponenten](/docs/building-ui/composing-components) einwickeln.

`Composite`-Komponenten sind Wrapper, die das Erstellen wiederverwendbarer Komponenten erleichtern. 
Um eine `Composite`-Komponente zu erstellen, erweitern Sie die `Composite`-Klasse mit einer bestimmten gebundenen Komponente, die als Grundlage der Klasse dient, z. B. `Composite<FlexLayout>`. 

Dieses Tutorial verwendet `Div`-Elemente als gebundene Komponenten, sie können jedoch jede Komponente sein, wie z. B. [`FlexLayout`](/docs/components/flex-layout) oder [`AppLayout`](/docs/components/app-layout). Mit der `getBoundComponent()`-Methode können Sie auf die gebundene Komponente verweisen und Zugriff auf deren Methoden erhalten. Dadurch können Sie die Größe festlegen, einen CSS-Klassennamen hinzufügen, Komponenten hinzufügen, die Sie in der `Composite`-Komponente anzeigen möchten, und komponentenspezifische Methoden aufrufen.

Für `MainView` und `FormView` erweitern Sie `Composite` mit `Div` als gebundene Komponente. Dann verweisen Sie auf diese gebundene Komponente, damit Sie später die Benutzeroberflächen hinzufügen können. Beide Ansichten sollten eine ähnliche Struktur wie folgt aufweisen:

```java
// Composite mit einer gebundenen Komponente erweitern
public class MainView extends Composite<Div> {

  // Zugriff auf die gebundene Komponente
  private Div self = getBoundComponent();

  // UI-Komponente erstellen
  private Button submit = new Button("Absenden");

  public MainView() {

    // Die UI-Komponente zur gebundenen Komponente hinzufügen
    self.add(submit);
  }
}
```

### Den Rahmen Titel setzen {#setting-the-frame-tile}

Wenn ein Benutzer mehrere Registerkarten in seinem Browser hat, hilft ein eindeutiger Rahmen Titel, schnell zu identifizieren, welchen Teil der Anwendung er geöffnet hat.

Die [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) Annotation definiert, was im Titel des Browsers oder in der Registerkarte der Seite angezeigt wird. Fügen Sie für beide Ansichten einen Rahmen Titel mithilfe der `@FrameTitle`-Annotation hinzu:

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Kunden Tabelle")
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
  @FrameTitle("Kunden Formular")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### Gemeinsame CSS {#shared-css}

Mit einer gebundenen Komponente, auf die Sie in `MainView` und `FormView` verweisen können, können Sie sie mit CSS stylen. 
Sie können die CSS aus dem ersten Schritt, [Erstellen einer Grundanwendung](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), verwenden, um beiden Ansichten identische UI-Containerstile zu geben. 
Fügen Sie den CSS-Klassennamen `card` zur gebundenen Komponente in jeder Ansicht hinzu:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Kunden Tabelle")
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
    @FrameTitle("Kunden Formular")
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

Das letzte gemeinsame Merkmal der Ansichten ist die Verwendung der `CustomerService`-Klasse.
Die `Table` in `MainView` zeigt jeden Kunden an, während `FormView` neue Kunden hinzufügt. Da beide Ansichten mit Kundendaten interagieren, benötigen sie Zugriff auf die Geschäftslogik der Anwendung. 

Die Ansichten erhalten Zugriff über den Spring-Dienst, der in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data#creating-a-service) erstellt wurde, `CustomerService`. Um den Spring-Dienst in jeder Ansicht zu verwenden, machen Sie `CustomerService` zu einem Konstruktionsparameter:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Kunden Tabelle")
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
    @FrameTitle("Kunden Formular")
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

## `MainView` erstellen {#creating-mainview}

Nachdem Sie Ihre Anwendung routbar gemacht und den Ansichten `Composite`-Komponentenwrapper gegeben haben, und den `CustomerService` inbegriffen haben, sind Sie bereit, die Benutzeroberflächen zu erstellen, die für jede Ansicht einzigartig sind. Wie bereits erwähnt, enthält `MainView` die UI-Komponenten, die ursprünglich in `Application` waren. Diese Klasse benötigt auch eine Möglichkeit, zu `FormView` zu navigieren.

### Gruppierung der `Table`-Methoden {#grouping-the-table-methods}

Während Sie die Komponenten von `Application` nach `MainView` verschieben, ist es eine gute Idee, Teile Ihrer Anwendung zu gliedern, damit eine benutzerdefinierte Methode alle Änderungen an der `Table` auf einmal vornehmen kann. Die Gliederung Ihres Codes macht ihn jetzt übersichtlicher, während die Anwendung komplexer wird.

Jetzt sollte Ihr `MainView`-Konstruktor nur eine `buildTable()`-Methode aufrufen, die die Spalten hinzufügt, die Größen festlegt und auf das Repository verweist:

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
  table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
  table.addColumn("company", Customer::getCompany).setLabel("Firma");
  table.addColumn("country", Customer::getCountry).setLabel("Land");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### Navigation zu `FormView` {#navigating-to-formview}

Benutzer benötigen eine Möglichkeit, von `MainView` zu `FormView` zu navigieren, indem sie die Benutzeroberfläche nutzen.

In webforJ können Sie direkt zu einer neuen Ansicht navigieren, indem Sie die Klassenansicht verwenden. Das Routing über eine Klasse anstelle eines URL-Abschnitts garantiert, dass webforJ den richtigen Pfad für das Laden der Ansicht nimmt. 

Um zu einer anderen Ansicht zu navigieren, verwenden Sie die [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) Klasse, um den aktuellen Standort mit `getCurrent()` zu erhalten, und verwenden Sie dann die Methode `navigate()` mit der Klassenansicht als Parameter: 

```java
Router.getCurrent().navigate(FormView.class);
```

Dieser Code wird Benutzer programmgesteuert zum neuen Kundenformular senden, aber die Navigation muss mit einer Benutzeraktion verbunden werden.
Um Benutzern das Hinzufügen eines neuen Kunden zu ermöglichen, können Sie entweder die Info-Schaltfläche von `Application` ändern oder ersetzen. Anstatt einen Nachrichtendialog zu öffnen, kann die Schaltfläche zu der `FormView`-Klasse navigieren:

```java
private Button addCustomer = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Vollständige `MainView` {#completed-mainview}

Mit der Navigation zu `FormView` und der Gruppierung der Tabellenmethoden sollte `MainView` jetzt so aussehen, bevor wir zur Erstellung von `FormView` übergehen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>

```java
@Route("/")
@FrameTitle("Kunden Tabelle")
public class MainView extends Composite<Div> {
  private final CustomerService customerService;
  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
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
    table.addColumn("company", Customer::getCompany).setLabel("Firma");
    table.addColumn("country", Customer::getCountry).setLabel("Land");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());
  }

}
```

</ExpandableCode>
<!-- vale on -->

## `FormView` erstellen {#creating-formview}

`FormView` zeigt ein Formular zum Hinzufügen neuer Kunden an. Für jede Kundenproperty wird `FormView` eine bearbeitbare Komponente haben, mit der die Benutzer interagieren können. Darüber hinaus wird es einen Button geben, mit dem Benutzer die Daten einreichen können, und einen Abbrechen-Button, um sie zu verwerfen.

### Erstellen einer `Customer`-Instanz {#creating-a-customer-instance}

Wenn ein Benutzer Daten für einen neuen Kunden bearbeitet, sollten Änderungen erst dann auf das Repository angewendet werden, wenn er bereit ist, das Formular einzureichen. Die Verwendung einer Instanz des `Customer`-Objekts ist eine bequeme Möglichkeit, die neuen Daten zu bearbeiten und zu speichern, ohne das Repository direkt zu bearbeiten. Erstellen Sie eine neue `Customer`-Instanz in `FormView`, die für das Formular verwendet wird:

```java
private Customer customer = new Customer();
```

Um die `Customer`-Instanz bearbeitbar zu machen, sollte jede Property, außer der `id`, mit einer bearbeitbaren Komponente assoziiert sein. Die Änderungen, die ein Benutzer in der UI vornimmt, sollten in der `Customer`-Instanz widergespiegelt werden.

### Hinzufügen von `TextField`-Komponenten {#adding-textfield-components}

Die ersten drei bearbeitbaren Eigenschaften in `Customer` (`firstName`, `lastName` und `company`) sind alle `String`-Werte und sollten mit einem einzeiligen Texteditor dargestellt werden. [`TextField`](/docs/components/fields/textfield) Komponenten sind eine gute Wahl, um diese Eigenschaften darzustellen.

Mit der `TextField`-Komponente können Sie ein Label und einen Ereignislistener hinzufügen, der jedes Mal aktiviert wird, wenn sich der Feldwert ändert. Jeder Ereignislistener sollte die `Customer`-Instanz für die entsprechende Eigenschaft aktualisieren.

Fügen Sie drei `TextField`-Komponenten hinzu, die die `Customer`-Instanz aktualisieren:

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Firma", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip Gemeinsame Namenskonvention
Die Komponenten den Eigenschaften zu benennen, die sie für die `Customer`-Entität repräsentieren, erleichtert es, Daten in einem zukünftigen Schritt zu binden, [Validierung und Bindung von Daten](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Hinzufügen einer `ChoiceBox`-Komponente {#adding-a-choicebox-component}

Die Verwendung eines `TextField` für die `country`-Eigenschaft wäre nicht ideal, da die Eigenschaft nur einer von fünf Enum-Werten sein kann: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` und `USA`.

Eine bessere Komponente zum Auswählen aus einer vordefinierten Liste von Optionen ist die [`ChoiceBox`](/docs/components/lists/choicebox).

Jede Option für eine `ChoiceBox`-Komponente wird als `ListItem` dargestellt. Jedes `ListItem` hat zwei Werte, einen `Object`-Schlüssel und einen `String`-Text, der in der Benutzeroberfläche angezeigt wird. Zwei Werte für jede Option zu haben, ermöglicht es Ihnen, das `Object` intern zu handhaben, während Sie gleichzeitig eine lesbare Option für Benutzer in der Benutzeroberfläche präsentieren.

Beispielsweise könnte der `Object`-Schlüssel eine Internationale Standardbuchnummer (ISBN) sein, während der `String`-Text der Buchtitel ist, der menschlicher lesbar ist.

```java
new ListItem(isbn, bookTitle);
```

Diese Anwendung befasst sich jedoch mit einer Liste von Ländernamen, nicht mit Büchern. Für jedes `ListItem` möchten Sie, dass der `Object` der `Customer.Country` enum ist, während der Text seine `String`-Darstellung sein kann.

Um alle Länderoptionen in eine `ChoiceBox` einzufügen, können Sie einen Iterator verwenden, um ein `ListItem` für jedes `Customer.Country`-Enum zu erstellen und in eine `ArrayList<ListItem>` einzufügen. Dann können Sie diese `ArrayList<ListItem>` in eine `ChoiceBox`-Komponente einfügen:

```java
//Erstellen der ChoiceBox-Komponente
private ChoiceBox country = new ChoiceBox("Land");

//Erstellen einer ArrayList von ListItem-Objekten
ArrayList<ListItem> listCountries = new ArrayList<>();

//Hinzufügen eines Iterators, der ein ListItem für jede Customer.Country-Option erstellt
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

//Fügen Sie die gefüllte ArrayList in die ChoiceBox ein
country.insert(listCountries);

//Macht das erste `ListItem` zum Standard, wenn das Formular geladen wird
country.selectIndex(0);
```

Wenn der Benutzer eine Option in der `ChoiceBox` auswählt, sollte die `Customer`-Instanz mit dem Schlüssel des ausgewählten Elements aktualisiert werden, der ein `Customer.Country`-Wert ist.

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
  private TextField company = new TextField("Firma", e -> customer.setCompany(e.getValue()));

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

Bei der Verwendung des neuen Kundenformulars sollten die Benutzer in der Lage sein, ihre Änderungen entweder zu speichern oder zu verwerfen.
Erstellen Sie zwei `Button`-Komponenten, um diese Funktion zu implementieren:

```java
private Button submit = new Button("Absenden");
private Button cancel = new Button("Abbrechen");
```

Sowohl die Absendeschaltfläche als auch die Abbrechen-Schaltfläche sollten den Benutzer zu `MainView` zurückbringen.
Dadurch kann der Benutzer sofort die Ergebnisse seiner Aktion sehen, ob er einen neuen Kunden in der Tabelle sieht oder ob er unverändert bleibt. 
Da mehrere Eingaben in `FormView` die Benutzer zu `MainView` führen, sollte die Navigation in eine aufrufbare Methode gestellt werden:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Abbrechen-Schaltfläche**

Das Verwerfen von Änderungen im Formular erfordert keinen zusätzlichen Code für das Ereignis, außer zur Rückkehr zu `MainView`. Da das Abbrechen jedoch keine Hauptaktion ist, hat die Schaltfläche mit dem Thema „Umriss“ mehr Bedeutung als die Absendeschaltfläche. 
Die [Themes](/docs/components/button#themes)-Sektion der `Button`-Komponenten-Seite listet alle verfügbaren Themen auf.

```java
private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Absendeschaltfläche**

Wenn ein Benutzer die Absendeschaltfläche drückt, sollten die Werte in der `Customer`-Instanz verwendet werden, um einen neuen Eintrag im Repository zu erstellen.

Mit dem `CustomerService` können Sie die `Customer`-Instanz verwenden, um die H2-Datenbank zu aktualisieren. Wenn dies geschieht, wird dieser `Customer` eine neue und eindeutige `id` zugewiesen. Nachdem das Repository aktualisiert wurde, können Sie die Benutzer zu `MainView` umleiten, wo sie den neuen Kunden in der Tabelle sehen können.

```java
private Button submit = new Button("Absenden", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Verwendung von `ColumnsLayout` {#using-a-columnslayout}

Nachdem Sie die `TextField`, `ChoiceBox` und `Button`-Komponenten hinzugefügt haben, verfügen Sie jetzt über alle interaktiven Teile des Formulars. Die letzte Verbesserung für `FormView` in diesem Schritt besteht darin, die sechs Komponenten visuell zu organisieren.

Dieses Formular kann ein [`ColumnsLayout`](/docs/components/columns-layout) verwenden, um die Komponenten in zwei Spalten zu trennen, ohne die Breite der interaktiven Komponenten festlegen zu müssen.
Um ein `ColumnsLayout` zu erstellen, geben Sie jede Komponente an, die sich innerhalb des Layouts befinden soll:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Um die Anzahl der Spalten für ein `ColumnsLayout` festzulegen, verwenden Sie eine `List` von `Breakpoint`-Objekten. Jeder `Breakpoint` sagt dem `ColumnsLayout`, welche minimale Breite es haben muss, um eine bestimmte Anzahl von Spalten anzuwenden. Mit dem `ColumnsLayout` können Sie ein Formular mit zwei Spalten erstellen, jedoch nur, wenn der Bildschirm breit genug ist, um zwei Spalten anzuzeigen. Auf kleineren Bildschirmen werden die Komponenten in einer einzelnen Spalte angezeigt.

Die [Breakpoints](/docs/components/columns-layout#breakpoints)-Sektion im `ColumnsLayout`-Artikel erklärt Breakpoints ausführlicher.

Um den Code wartbar zu halten, setzen Sie die Breakpoints in einer separaten Methode. In dieser Methode können Sie auch den horizontalen und vertikalen Abstand zwischen den Komponenten im `ColumnsLayout` mit der `setSpacing()`-Methode steuern.

```java
private void setColumnsLayout() {

  //Zwei Spalten im ColumnsLayout, wenn es breiter als 600px ist
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  //Fügen Sie die Liste der Breakpoints hinzu
  layout.setBreakpoints(breakpoints);

  //Setzen Sie den Abstand zwischen den Komponenten mit einer DWC-CSS-Variable
  layout.setSpacing("var(--dwc-space-l)")
}
```

Abschließend können Sie das neu erstellte `ColumnsLayout` zur gebundenen Komponente von `FormView` hinzufügen, während Sie auch die maximale Breite festlegen und den Klassennamen von früher hinzufügen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Vollständige `FormView` {#completed-formview}

Nachdem Sie eine `Customer`-Instanz, die interaktiven Komponenten und das `ColumnsLayout` hinzugefügt haben, sollte Ihre `FormView` wie folgt aussehen:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer")
@FrameTitle("Kunden Formular")
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Vorname", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Nachname", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Firma", e -> customer.setCompany(e.getValue()));
  private ChoiceBox country = new ChoiceBox("Land",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
  private Button submit = new Button("Absenden", ButtonTheme.PRIMARY, e -> submitCustomer());
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
```

</ExpandableCode>
<!-- vale on -->

## Nächster Schritt {#next-step}

Da Benutzer jetzt Kunden hinzufügen können, sollte Ihre Anwendung in der Lage sein, vorhandene Kunden mit demselben Formular zu bearbeiten. Im nächsten Schritt, [Beobachter und Routenparameter](/docs/introduction/tutorial/observers-and-route-parameters), werden Sie es zulassen, dass die Kunde `id` ein Anfangsparameter für `FormView` ist, damit das Formular mit den Daten dieses Kunden gefüllt werden kann und Benutzer die Eigenschaften ändern können.
