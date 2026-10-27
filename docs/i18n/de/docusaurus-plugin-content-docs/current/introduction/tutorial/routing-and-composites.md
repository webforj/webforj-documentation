---
title: Routing und Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: f32a8552d85a9c85b565fe6f026c93bb
---
Bis jetzt war dieses Tutorial nur eine Einzelseitenanwendung. Dieser Schritt verändert das.
Sie werden die Benutzeroberfläche, die Sie in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data) erstellt haben, auf eine eigene Seite verschieben und eine weitere Seite zum Hinzufügen neuer Kunden erstellen.
Dann verbinden Sie diese Seiten, damit Ihre App in der Lage ist, zwischen ihnen zu navigieren, indem Sie diese Konzepte anwenden:

- [Routing](/docs/routing/overview)
- [Komponenten zusammensetzen](/docs/building-ui/composing-components)
- Die [`ColumnsLayout`](/docs/components/columns-layout) Komponente

Der Abschluss dieses Schrittes erstellt eine Version von [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Video hier einfügen -->

## Ausführung der App {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die Datei `pom.xml` enthält; das ist `3-routing-and-composites`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Routbare Apps {#routable-apps}

Früher hatte Ihre App eine einzige Funktion: das Anzeigen einer Tabelle mit vorhandenen Kundendaten.
In diesem Schritt wird Ihre App auch in der Lage sein, die Kundendaten zu ändern, indem neue Kunden hinzugefügt werden.
Die Trennung der Benutzeroberflächen für Anzeige und Änderung ist für die langfristige Wartung und das Testen vorteilhaft, daher fügen Sie diese Funktion als separate Seite hinzu.
Sie machen Ihre App [routable](/docs/routing/overview), sodass webforJ auf die beiden Benutzeroberflächen einzeln zugreifen und sie laden kann.

Eine routable App rendert die Benutzeroberfläche basierend auf der URL. Die Annotierung der Klasse, die die `App` Klasse erweitert, mit [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) ermöglicht das Routing, und das Element `packages` sagt webforJ, welche Pakete UI-Komponenten enthalten.

Wenn Sie die `@Routify` Annotation zu `Application` hinzufügen, entfernen Sie die `run()` Methode. Sie werden die Komponenten aus dieser Methode in eine Klasse verschieben, die Sie im Paket `com.webforj.tutorial.views` erstellen. Ihre aktualisierte `Application.java` Datei sollte so aussehen:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@BundleEntry("css/card.css")
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
Das Beibehalten der `@BundleEntry`-Annotation in `Application` fügt die CSS-Datei zum app-weiten Frontend-Bundle hinzu, sodass die Stile in den gerouteten Ansichten verfügbar bleiben.
:::

### Erstellen von Routen {#creating-routes}

Das Hinzufügen der `@Routify`-Annotation macht Ihre App routable. Sobald sie routable ist, wird Ihre App im Paket `com.webforj.tutorial.views` nach Routen suchen.
Sie müssen die Routen für Ihre Benutzeroberflächen erstellen und auch deren [Routentypen](/docs/routing/route-hierarchy/route-types) angeben. Der Routentyp bestimmt, wie der UI-Inhalt der URL zugeordnet wird.

Der erste Routentyp ist `View`. Diese Arten von Routen mappen direkt auf ein spezifisches URL-Segment in Ihrer App. Die Benutzeroberflächen für die Tabelle und das neue Kundenformular werden beide `View` Routen sein.

Der zweite Routentyp ist `Layout`, das UI enthält, das auf mehreren Seiten erscheint, wie z.B. eine Kopfzeile oder Seitenleiste. Layout-Routen umhüllen auch untergeordnete Ansichten, ohne zur URL beizutragen.

Um den Routentyp einer Klasse anzugeben, fügen Sie den Routentyp als Suffix an das Ende des Klassennamens an.
Zum Beispiel ist `MainView` ein `View` Routentyp.

Um die beiden Funktionen der App zu trennen, muss Ihre App die Benutzeroberflächen zwei einzigartigen `View` Routen zuordnen: eine für die Tabelle und eine für das Kundenformular. Erstellen Sie im Verzeichnis `/src/main/java/com/webforj/tutorial/views` zwei Klassen mit dem Suffix `View`:

- **`MainView`**: Diese Ansicht wird die `Table` enthalten, die zuvor in der `Application` Klasse war.
- **`FormView`**: Diese Ansicht wird ein Formular zum Hinzufügen neuer Kunden enthalten.

### Zuordnen von URLs zu Komponenten {#mapping-urls-to-components}

Ihre App ist routable und weiß, dass sie nach zwei `View` Routen, `MainView` und `FormView`, suchen muss, hat jedoch keine spezifische URL, um sie zu laden. Mithilfe der `@Route`-Annotation an einer Ansichtsklasse können Sie webforJ mitteilen, wo sie basierend auf einem gegebenen URL-Segment geladen werden soll. Zum Beispiel mappt `@Route("about")` lokal die Klasse auf `http://localhost:8080/about`.

Wie der Name schon sagt, ist `MainView` die Klasse, die Sie initial laden möchten, wenn die App ausgeführt wird. Um dies zu erreichen, fügen Sie eine `@Route`-Annotation hinzu, die `MainView` auf die Root-URL Ihrer App abbildet:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Für die `FormView` mappen Sie die Ansicht so, dass sie geladen wird, wenn ein Benutzer zu `http://localhost:8080/customer` geht:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standardverhalten
Wenn Sie der `@Route`-Annotation keinen Wert explizit zuweisen, wird das URL-Segment der Klassenname in Kleinbuchstaben ohne das Suffix `View` sein.

- `MainView` würde auf `/main` abgebildet
- `FormView` würde auf `/form` abgebildet
:::

## Gemeinsame Merkmale {#shared-characteristics}

Neben der Tatsache, dass beide Ansichtsrouten sind, teilen `MainView` und `FormView` zusätzliche Merkmale. Einige dieser gemeinsamen Eigenschaften, wie die Verwendung von `Composite` Komponenten, sind grundlegend für die Verwendung von webforJ Apps, während andere nur das Management Ihrer App erleichtern.

### Verwendung von `Composite` Komponenten {#using-composite-components}

Als die App eine Einzelseite war, haben Sie die Komponenten innerhalb eines `Frame` gespeichert. In Zukunft, mit einer App mit mehreren Ansichten, müssen Sie diese UI-Komponenten innerhalb von [`Composite` Komponenten](/docs/building-ui/composing-components) umhüllen.

`Composite` Komponenten sind Wrapper, die es einfach machen, wiederverwendbare Komponenten zu erstellen.
Um eine `Composite` Komponente zu erstellen, erweitern Sie die `Composite` Klasse mit einer bestimmten gebundenen Komponente, die als Fundament der Klasse dient, z.B. `Composite<FlexLayout>`.

Dieses Tutorial verwendet `Div`-Elemente als die gebundenen Komponenten, aber sie können jede Komponente sein, wie z.B. [`FlexLayout`](/docs/components/flex-layout) oder [`AppLayout`](/docs/components/app-layout). Mithilfe der Methode `getBoundComponent()` können Sie auf die gebundene Komponente zugreifen und auf deren Methoden zugreifen. Dies ermöglicht es Ihnen, die Größe festzulegen, einen CSS-Klassennamen hinzuzufügen, Komponenten hinzuzufügen, die im `Composite`-Komponenten angezeigt werden sollen, und auf komponentenspezifische Methoden zuzugreifen.

Für `MainView` und `FormView` erweitern Sie `Composite` mit `Div` als der gebundenen Komponente. Dann verweisen Sie auf diese gebundene Komponente, damit Sie später die UIs hinzufügen können. Beide Ansichten sollten ähnlich zu dieser Struktur aussehen:

```java
// Erweitern Sie Composite mit einer gebundenen Komponente
public class MainView extends Composite<Div> {

  // Zugriff auf die gebundene Komponente
  private Div self = getBoundComponent();

  // Erstellen einer UI-Komponente
  private Button submit = new Button("Submit");

  public MainView() {

    // Fügen Sie die UI-Komponente der gebundenen Komponente hinzu
    self.add(submit);
  }
}
```

### Festlegen des Rahmentitels {#setting-the-frame-tile}

Wenn ein Benutzer mehrere Tabs in seinem Browser hat, hilft ein einzigartiger Rahmentitel ihm, schnell zu erkennen, welcher Teil der App geöffnet ist.

Die [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) Annotation definiert, was im Titel des Browsers oder im Tab der Seite erscheint. Für beide Ansichten fügen Sie einen Rahmentitel mit der `@FrameTitle`-Annotation hinzu:

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

Mit einer gebundenen Komponente, auf die Sie in `MainView` und `FormView` verweisen können, können Sie sie mit CSS stylen.
Sie können das CSS aus dem ersten Schritt, [Erstellen einer Basis-App](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), verwenden, um beiden Ansichten identische UI-Containerstile zu geben.
Fügen Sie den CSS-Klassennamen `card` der gebundenen Komponente in jeder Ansicht hinzu:

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

Das letzte gemeinsame Merkmal der Ansichten ist die Verwendung der `CustomerService` Klasse.
Die `Table` in `MainView` zeigt jeden Kunden an, während `FormView` neue Kunden hinzufügt. Da beide Ansichten mit Kundendaten interagieren, benötigen sie Zugang zur Geschäftslogik der App.

Die Ansichten erhalten den Zugang über den Spring-Dienst, der in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data#creating-a-service) erstellt wurde, `CustomerService`. Um den Spring-Dienst in jeder Ansicht zu verwenden, machen Sie `CustomerService` zu einem Konstruktorparameter:

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

## Erstellung von `MainView` {#creating-mainview}

Nachdem Sie Ihre App routable gemacht haben, die Ansichten mit `Composite`-Komponenten-Wrappern versehen und den `CustomerService` hinzugefügt haben, sind Sie bereit, die für jede Ansicht einzigartigen Benutzeroberflächen zu erstellen. Wie bereits erwähnt, enthält `MainView` die UI-Komponenten, die ursprünglich in `Application` waren. Diese Klasse benötigt auch einen Weg, um zu `FormView` zu navigieren.

### Gruppieren der `Table` Methoden {#grouping-the-table-methods}

Während Sie die Komponenten von `Application` zu `MainView` verschieben, ist es eine gute Idee, Teile Ihrer App zu sectionieren, sodass eine benutzerdefinierte Methode die Änderungen an der `Table` auf einmal vornehmen kann. Das Sectionieren Ihres Codes macht ihn jetzt besser verwaltbar, während die App komplexer wird.

Jetzt sollte der Konstruktor von `MainView` nur eine `buildTable()` Methode aufrufen, die die Spalten hinzufügt, die Größe festlegt und das Repository referenziert:

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

### Navigation zu `FormView`{#navigating-to-formview}

Benutzer benötigen einen Weg, um von `MainView` zu `FormView` über die Benutzeroberfläche zu navigieren.

In webforJ können Sie direkt zu einer neuen Ansicht navigieren, indem Sie die Klassenansicht verwenden. Die Navigation über eine Klasse anstelle eines URL-Segments garantiert, dass webforJ den richtigen Weg wählen wird, um die Ansicht zu laden.

Um zu einer anderen Ansicht zu navigieren, verwenden Sie die [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) Klasse, um den aktuellen Standort mit `getCurrent()` zu erhalten, und verwenden Sie dann die Methode `navigate()` mit der Klassenansicht als Parameter:

```java
Router.getCurrent().navigate(FormView.class);
```

Dieser Code wird die Benutzer programmgesteuert zum neuen Kundenformular senden, aber die Navigation muss mit einer Benutzeraktion verbunden sein.
Um es den Benutzern zu ermöglichen, einen neuen Kunden hinzuzufügen, können Sie entweder den Info-Button aus `Application` modifizieren oder ersetzen. Anstatt ein Nachrichten-Dialogfeld zu öffnen, kann der Button zur `FormView` Klasse navigieren:

```java
private Button addCustomer = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Abgeschlossenes `MainView` {#completed-mainview}

Mit der Navigation zu `FormView` und gruppierten Tabellenmethoden sollte `MainView` vor dem Erstellen von `FormView` so aussehen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>

```java
@Route("/")
@FrameTitle("Kundentabelle")
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
    table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
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

## Erstellung von `FormView` {#creating-formview}

`FormView` wird ein Formular zur Anzeige hinzufügen, um neue Kunden hinzuzufügen. Für jede Kunden-Eigenschaft wird `FormView` eine bearbeitbare Komponente haben, mit der die Benutzer interagieren können. Darüber hinaus wird es einen Button für die Benutzer geben, um die Daten einzureichen, und einen Abbrechen-Button, um sie zu verwerfen.

### Erstellen einer `Customer` Instanz {#creating-a-customer-instance}

Wenn ein Benutzer Daten für einen neuen Kunden bearbeitet, sollten die Änderungen erst auf das Repository angewendet werden, wenn er bereit ist, das Formular einzureichen. Die Verwendung einer Instanz des `Customer` Objekts ist eine praktische Möglichkeit, die neuen Daten zu bearbeiten und zu verwalten, ohne das Repository direkt zu bearbeiten. Erstellen Sie eine neue `Customer` Instanz innerhalb von `FormView`, die für das Formular verwendet wird:

```java
private Customer customer = new Customer();
```

Um die `Customer` Instanz bearbeitbar zu machen, sollte jede Eigenschaft, mit Ausnahme der `id`, mit einer bearbeitbaren Komponente verbunden werden. Die Änderungen, die ein Benutzer in der UI vornimmt, sollten in der `Customer` Instanz reflektiert werden.

### Hinzufügen von `TextField` Komponenten {#adding-textfield-components}

Die ersten drei bearbeitbaren Eigenschaften in `Customer` (`firstName`, `lastName` und `company`) sind alle `String`-Werte und sollten mit einem einzeiligen Texteditor dargestellt werden. [`TextField`](/docs/components/fields/textfield) Komponenten sind eine großartige Wahl, um diese Eigenschaften darzustellen.

Mit der `TextField` Komponente können Sie ein Label hinzufügen und einen Ereignis-Listener hinzufügen, der jedes Mal ausgelöst wird, wenn sich der Wert des Feldes ändert. Jeder Ereignis-Listener sollte die `Customer` Instanz für die entsprechende Eigenschaft aktualisieren.

Fügen Sie drei `TextField` Komponenten hinzu, die die `Customer` Instanz aktualisieren:

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
Die Benennung der Komponenten gleich wie die Eigenschaften, die sie für die `Customer` Entität darstellen, erleichtert das Binden von Daten in einem zukünftigen Schritt, [Validieren und Binden von Daten](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Hinzufügen einer `ChoiceBox` Komponente {#adding-a-choicebox-component}

Die Verwendung eines `TextField` für die Eigenschaft `country` wäre nicht ideal, da die Eigenschaft nur einen von fünf Enum-Werten sein kann: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` und `USA`.

Eine bessere Komponente zum Auswählen aus einer vordefinierten Liste von Optionen ist die [`ChoiceBox`](/docs/components/lists/choicebox).

Jede Option für eine `ChoiceBox`-Komponente wird als `ListItem` dargestellt. Jedes `ListItem` hat zwei Werte, einen `Object` Schlüssel und einen `String` Text, der in der UI angezeigt wird. Zwei Werte für jede Option zu haben, ermöglicht es Ihnen, den `Object` intern zu behandeln und gleichzeitig eine brauchbarere Option für Benutzer in der UI zu präsentieren.

Zum Beispiel könnte der `Object` Schlüssel eine Internationale Standardbuchnummer (ISBN) sein, während der `String` Text der Buchtitel ist, der lesbarer ist.

```java
new ListItem(isbn, bookTitle);
```

Allerdings handelt es sich bei dieser App um eine Liste von Ländernamen, nicht um Bücher. Für jedes `ListItem` wollen Sie, dass der `Object` der `Customer.Country` Enum ist, während der Text seine `String`-Darstellung sein kann.

Um alle `country`-Optionen in eine `ChoiceBox` einzufügen, können Sie einen Iterator verwenden, um für jedes `Customer.Country` Enum ein `ListItem` zu erstellen und sie in eine `ArrayList<ListItem>` zu setzen. Dann können Sie diese `ArrayList<ListItem>` in eine `ChoiceBox`-Komponente einfügen:

```java
// Erstellen Sie die ChoiceBox-Komponente
private ChoiceBox country = new ChoiceBox("Land");

// Erstellen Sie eine ArrayList der ListItem-Objekte
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

Dann, wenn der Benutzer eine Option in der `ChoiceBox` auswählt, sollte die `Customer` Instanz mit dem Schlüssel des ausgewählten Elements aktualisiert werden, der ein `Customer.Country` Wert ist.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Um den Code sauber zu halten, sollte der Iterator, der die `ArrayList<ListItem>` erstellt und sie zur `ChoiceBox` hinzufügt, in einer separaten Methode sein.
Nachdem Sie eine `ChoiceBox` hinzugefügt haben, die es dem Benutzer ermöglicht, die `country`-Eigenschaft auszuwählen, sollte `FormView` so aussehen:

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

### Hinzufügen von `Button` Komponenten {#adding-button-components}

Wenn Benutzer das neue Kundenformular verwenden, sollten sie in der Lage sein, ihre Änderungen zu speichern oder abzubrechen.
Erstellen Sie zwei `Button`-Komponenten, um dieses Feature zu implementieren:

```java
private Button submit = new Button("Einreichen");
private Button cancel = new Button("Abbrechen");
```

Sowohl der Einreichen- als auch der Abbrechen-Button sollten den Benutzer zu `MainView` zurückführen.
Dies ermöglicht es dem Benutzer, sofort die Ergebnisse seiner Aktion zu sehen, ob er einen neuen Kunden in der Tabelle sieht oder ob es unverändert bleibt.
Da multiple Eingaben in `FormView` die Benutzer zu `MainView` zurückführen, sollte die Navigation in eine wiederholbare Methode gesetzt werden:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Abbrechen-Button**

Das Verwerfen der Änderungen im Formular erfordert keinen weiteren Code für das Ereignis über das Zurückkehren zu `MainView`. Da das Abbrechen jedoch keine primäre Aktion ist, verleiht das Setzen des Themes des Buttons auf ein Umriss dem Einreichen-Button mehr Prominenz.
Die [Themes](/docs/components/button#themes) Sektion der `Button`-Komponenten-Seite listet alle verfügbaren Themen auf.

```java
private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Einreichen-Button**

Wenn ein Benutzer den Einreichen-Button drückt, sollten die Werte in der `Customer`-Instanz verwendet werden, um einen neuen Eintrag im Repository zu erstellen.

Mithilfe des `CustomerService` können Sie die `Customer`-Instanz verwenden, um die H2-Datenbank zu aktualisieren. Wenn dies geschieht, wird der `Customer` eine neue und einzigartige `id` zugewiesen. Nachdem das Repository aktualisiert wurde, können Sie die Benutzer zu `MainView` umleiten, wo sie den neuen Kunden in der Tabelle sehen können.

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

Durch das Hinzufügen der `TextField`, `ChoiceBox` und `Button` Komponenten haben Sie nun alle interaktiven Teile des Formulars.
Die letzte Verbesserung an `FormView` in diesem Schritt besteht darin, die sechs Komponenten visuell zu organisieren.

Dieses Formular kann ein [`ColumnsLayout`](/docs/components/columns-layout) verwenden, um die Komponenten in zwei Spalten zu trennen, ohne die Breite von interaktiven Komponenten festlegen zu müssen.
Um ein `ColumnsLayout` zu erstellen, geben Sie jede Komponente an, die innerhalb des Layouts enthalten sein soll:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Um die Anzahl der Spalten für ein `ColumnsLayout` festzulegen, verwenden Sie eine `List` von `Breakpoint` Objekten. Jeder `Breakpoint` sagt dem `ColumnsLayout`, wie breit es mindestens sein muss, um eine bestimmte Anzahl von Spalten anzuwenden. Durch die Verwendung des `ColumnsLayout` können Sie ein Formular mit zwei Spalten erstellen, aber nur, wenn der Bildschirm breit genug ist, um zwei Spalten anzuzeigen. Auf kleineren Bildschirmen werden die Komponenten in einer einzigen Spalte angezeigt.

Die [Breakpoints](/docs/components/columns-layout#breakpoints) Sektion im `ColumnsLayout` Artikel erklärt die Breakpoints im Detail.

Um den Code wartbar zu halten, setzen Sie die Breakpoints in eine separate Methode. In dieser Methode können Sie auch den horizontalen und vertikalen Abstand zwischen den Komponenten innerhalb des `ColumnsLayout` mit der Methode `setSpacing()` steuern.

```java
private void setColumnsLayout() {

  // Zwei Spalten im ColumnsLayout, wenn es breiter als 600px ist
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Fügen Sie die Liste der Breakpoints hinzu
  layout.setBreakpoints(breakpoints);

  // Setzen Sie den Abstand zwischen den Komponenten mit einer DWC CSS-Variable
  layout.setSpacing("var(--dwc-space-l)")
}
```

Schließlich können Sie das neu erstellte `ColumnsLayout` zur gebundenen Komponente von `FormView` hinzufügen, während Sie auch die maximale Breite festlegen und den Klassennamen von früher hinzufügen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Abgeschlossenes `FormView` {#completed-formview}

Nachdem Sie eine `Customer` Instanz, die interaktiven Komponenten und das `ColumnsLayout` hinzugefügt haben, sollte Ihre `FormView` wie folgt aussehen:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer")
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
```

</ExpandableCode>
<!-- vale on -->

## Nächster Schritt {#next-step}

Da Benutzer jetzt Kunden hinzufügen können, sollte Ihre App in der Lage sein, bestehende Kunden mit demselben Formular zu bearbeiten. Im nächsten Schritt, [Beobachter und Routenparameter](/docs/introduction/tutorial/observers-and-route-parameters), werden Sie es ermöglichen, dass die `id` des Kunden ein Anfangsparameter für `FormView` ist, sodass das Formular mit den Daten dieses Kunden gefüllt wird und die Benutzer die Eigenschaften ändern können.
