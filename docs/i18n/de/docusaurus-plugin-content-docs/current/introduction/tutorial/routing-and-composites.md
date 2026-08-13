---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 6016bff3255689b6be8a69997542a372
---
Bis jetzt war dieses Tutorial nur eine Einzelseitenanwendung. Dieser Schritt ändert das.
Sie werden die Benutzeroberfläche, die Sie in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data) erstellt haben, auf eine eigene Seite verschieben und eine weitere Seite zum Hinzufügen neuer Kunden erstellen.
Anschließend verbinden Sie diese Seiten, damit Ihre Anwendung zwischen ihnen navigieren kann, indem Sie diese Konzepte anwenden:

- [Routing](/docs/routing/overview)
- [Komponieren von Komponenten](/docs/building-ui/composing-components)
- Die [`ColumnsLayout`](/docs/components/columns-layout) Komponente

Das Abschließen dieses Schrittes erstellt eine Version von [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Video hier einfügen -->

## Anwendung ausführen {#running-the-app}

Während Sie Ihre Anwendung entwickeln, können Sie [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) als Vergleich verwenden. Um die Anwendung in Aktion zu sehen:

1. Navigieren Sie zum Hauptverzeichnis, das die `pom.xml`-Datei enthält; dies ist `3-routing-and-composites`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-Anwendung lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der Anwendung öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Routbare Apps {#routable-apps}

Früher hatte Ihre Anwendung eine einzige Funktion: die Anzeige einer Tabelle mit bestehenden Kundendaten.
In diesem Schritt wird Ihre Anwendung auch in der Lage sein, die Kundendaten zu ändern, indem sie neue Kunden hinzufügt.
Die Trennung der Benutzeroberflächen für Anzeige und Modifikation ist vorteilhaft für die langfristige Wartung und das Testen. Daher fügen Sie dieses Feature als separate Seite hinzu.
Sie werden Ihre Anwendung [routbar](/docs/routing/overview) machen, damit webforJ die beiden Benutzeroberflächen einzeln abrufen und laden kann.

Eine routbare Anwendung rendert die Benutzeroberfläche basierend auf der URL. Das Annotieren der Klasse, die die `App`-Klasse erweitert, mit [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) aktiviert das Routing, und das Element `packages` sagt webforJ, welche Pakete UI-Komponenten enthalten.

Wenn Sie die `@Routify`-Annotation zu `Application` hinzufügen, entfernen Sie die `run()`-Methode. Sie werden die Komponenten aus dieser Methode in eine Klasse verschieben, die Sie im Paket `com.webforj.tutorial.views` erstellen werden. Ihre aktualisierte `Application.java`-Datei sollte folgendermaßen aussehen:

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

// Entfernte überschreibende App.run()-Methode

}
```

:::tip Globale CSS
Das Beibehalten der `@BundleEntry`-Annotation in `Application` fügt die CSS-Datei zum frontend-bundles auf Anwendungsebene hinzu, sodass die Stile in allen gerouteten Ansichten verfügbar bleiben.
:::

### Routen erstellen {#creating-routes}

Das Hinzufügen der `@Routify`-Annotation macht Ihre Anwendung routbar. Sobald es routbar ist, sucht Ihre Anwendung im Paket `com.webforj.tutorial.views` nach Routen.
Sie müssen die Routen für Ihre Benutzeroberflächen erstellen und auch deren [Routentypen](/docs/routing/route-hierarchy/route-types) angeben. Der Routentyp bestimmt, wie der UI-Inhalt zur URL zugeordnet wird.

Der erste Routentyp ist `View`. Diese Art von Routen verweist direkt auf ein bestimmtes URL-Segment in Ihrer Anwendung. Die Benutzeroberflächen für die Tabelle und das neue Kundenformular werden beide `View`-Routen sein.

Der zweite Routentyp ist `Layout`, der UI enthält, die auf mehreren Seiten angezeigt wird, z. B. eine Kopfzeile oder eine Seitenleiste. Layout-Routen umschließen auch untergeordnete Ansichten, ohne zur URL beizutragen.

Um den Routentyp einer Klasse anzugeben, fügen Sie den Routentyp am Ende des Klassennamens als Suffix hinzu.
Zum Beispiel ist `MainView` ein `View` Routentyp.

Um die beiden Funktionen der Anwendung zu trennen, muss Ihre Anwendung die Benutzeroberflächen auf zwei einzigartige `View`-Routen abbilden: eine für die Tabelle und eine für das Kundenformular. Erstellen Sie in `/src/main/java/com/webforj/tutorial/views` zwei Klassen mit einem `View`-Suffix:

- **`MainView`**: Diese Ansicht wird die `Table` enthalten, die zuvor in der `Application`-Klasse war.
- **`FormView`**: Diese Ansicht wird ein Formular zum Hinzufügen neuer Kunden haben.

### URLs auf Komponenten abbilden {#mapping-urls-to-components}

Ihre Anwendung ist routbar und weiß, dass sie nach zwei `View`-Routen, `MainView` und `FormView`, suchen muss, hat jedoch keine spezifische URL, um sie zu laden. Mit der Annotation `@Route` auf einer Ansichts-Klasse können Sie webforJ mitteilen, wo sie basierend auf einem gegebenen URL-Segment laden soll. Wenn Sie beispielsweise `@Route("about")` in einer Ansicht verwenden, wird die Klasse lokal mit `http://localhost:8080/about` verknüpft.

Wie der Name schon sagt, ist `MainView` die Klasse, die Sie beim Starten der Anwendung zunächst laden möchten. Um dies zu erreichen, fügen Sie eine `@Route`-Annotation hinzu, die `MainView` der Stamm-URL Ihrer Anwendung zuordnet:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Für `FormView` verknüpfen Sie die Ansicht so, dass sie geladen wird, wenn ein Benutzer zu `http://localhost:8080/customer` navigiert:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standardverhalten
Wenn Sie keinen expliziten Wert für die `@Route`-Annotation zuweisen, wird das URL-Segment in den Klassennamen umgewandelt, in Kleinbuchstaben, wobei das `View`-Suffix entfernt wird.

- `MainView` würde zu `/main` abgebildet werden
- `FormView` würde zu `/form` abgebildet werden
:::

## Gemeinsame Merkmale {#shared-characteristics}

Neben der Tatsache, dass beide Ansichtsrouten sind, teilen sich `MainView` und `FormView` zusätzliche Merkmale. Einige dieser gemeinsamen Merkmale, wie die Verwendung von `Composite`-Komponenten, sind grundlegend für die Verwendung von webforJ-Anwendungen, während andere nur die Verwaltung Ihrer Anwendung vereinfachen.

### Verwendung von `Composite`-Komponenten {#using-composite-components}

Als die Anwendung eine Einzelansicht war, haben Sie die Komponenten in einem `Frame` gespeichert. Zukünftig, bei einer Anwendung mit mehreren Ansichten, müssen Sie diese UI-Komponenten in [`Composite`-Komponenten](/docs/building-ui/composing-components) einfügen.

`Composite`-Komponenten sind Wrapper, die es einfach machen, wiederverwendbare Komponenten zu erstellen.
Um eine `Composite`-Komponente zu erstellen, erweitern Sie die `Composite`-Klasse mit einer angegebenen gebundenen Komponente, die als Grundlage für die Klasse dient, z.B. `Composite<FlexLayout>`.

Dieses Tutorial verwendet `Div`-Elemente als gebundene Komponenten, sie können jedoch auch jede andere Komponente sein, z.B. [`FlexLayout`](/docs/components/flex-layout) oder [`AppLayout`](/docs/components/app-layout). Mit der Methode `getBoundComponent()` können Sie auf die gebundene Komponente zugreifen und deren Methoden verwenden. So können Sie die Größen einstellen, einen CSS-Klassennamen hinzufügen, Komponenten hinzufügen, die Sie in der `Composite`-Komponente anzeigen möchten, und auf komponentenspezifische Methoden zugreifen.

Für `MainView` und `FormView` erweitern Sie `Composite` mit `Div` als gebundene Komponente. Dann verweisen Sie auf diese gebundene Komponente, damit Sie später die Benutzeroberflächen hinzufügen können. Beide Ansichten sollten einer ähnlichen Struktur wie die folgende entsprechen:

```java
// Erweitern Sie Composite mit einer gebundenen Komponente
public class MainView extends Composite<Div> {

  // Greifen Sie auf die gebundene Komponente zu
  private Div self = getBoundComponent();

  // Erstellen Sie eine Komponenten-UI
  private Button submit = new Button("Submit");

  public MainView() {

    // Fügen Sie die UI-Komponente der gebundenen Komponente hinzu
    self.add(submit);
  }
}
```

### Den Titel des Frames festlegen {#setting-the-frame-tile}

Wenn ein Benutzer mehrere Registerkarten in seinem Browser hat, hilft ein einzigartiger Titel des Frames, schnell zu erkennen, welchen Teil der Anwendung er geöffnet hat.

Die [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) Annotation definiert, was im Titel des Browsers oder im Tab der Seite angezeigt wird. Fügen Sie für beide Ansichten einen Titel des Frames hinzu, indem Sie die `@FrameTitle`-Annotation verwenden:

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

Mit einer gebundenen Komponente, auf die Sie in `MainView` und `FormView` zugreifen können, können Sie diese mit CSS gestalten.
Sie können das CSS aus dem ersten Schritt, [Eine grundlegende Anwendung erstellen](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), verwenden, um beiden Ansichten identische UI-Container-Stile zu geben.
Fügen Sie den CSS-Klassennamen `card` zur gebundenen Komponente in jeder Ansicht hinzu:

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

Das letzte gemeinsame Merkmal für die Ansichten ist die Verwendung der Klasse `CustomerService`.
Die `Table` in `MainView` zeigt jeden Kunden an, während `FormView` neue Kunden hinzufügt. Da beide Ansichten mit Kundendaten interagieren, benötigen sie Zugriff auf die Geschäftslogik der Anwendung.

Die Ansichten erhalten den Zugriff über den in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data#creating-a-service) erstellten Spring-Service, `CustomerService`. Um den Spring-Service in jeder Ansicht zu verwenden, machen Sie `CustomerService` zu einem Konstruktorparameter:

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

## `MainView` erstellen {#creating-mainview}

Nachdem Sie Ihre Anwendung routbar gemacht haben, den Ansichten `Composite`-Komponenten-Wrapper gegeben haben und `CustomerService` eingebunden haben, sind Sie bereit, die für jede Ansicht eindeutigen Benutzeroberflächen zu erstellen. Wie bereits erwähnt, enthält `MainView` die Benutzeroberflächenkomponenten, die ursprünglich in `Application` waren. Diese Klasse benötigt auch eine Möglichkeit, zu `FormView` zu navigieren.

### Gruppierung der `Table`-Methoden {#grouping-the-table-methods}

Während Sie die Komponenten von `Application` in `MainView` verschieben, ist es eine gute Idee, damit zu beginnen, Teile Ihrer Anwendung zu sektionieren, sodass eine benutzerdefinierte Methode die Änderungen an der `Table` auf einmal vornehmen kann. Das Sektionieren Ihres Codes jetzt macht ihn besser verwaltbar, wenn die Anwendung komplexer wird.

Jetzt sollte Ihr Konstruktor von `MainView` nur eine Methode `buildTable()` aufrufen, die die Spalten hinzufügt, die Größen festlegt und auf das Repository verweist:

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

Benutzer benötigen eine Möglichkeit, von `MainView` zu `FormView` über die Benutzeroberfläche zu navigieren.

In webforJ können Sie direkt zu einer neuen Ansicht navigieren, indem Sie die Klassenansicht verwenden. Die Navigation über eine Klasse anstelle eines URL-Segments garantiert, dass webforJ den richtigen Pfad nimmt, um die Ansicht zu laden.

Um zu einer anderen Ansicht zu navigieren, verwenden Sie die [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) Klasse, um den aktuellen Standort mit `getCurrent()` abzurufen, und verwenden Sie dann die Methode `navigate()` mit der Klassenansicht als Parameter:

```java
Router.getCurrent().navigate(FormView.class);
```

Dieser Code wird die Benutzer programmgesteuert zum neuen Kundenformular senden, aber die Navigation muss mit einer Benutzeraktion verbunden sein.
Um den Benutzern das Hinzufügen eines neuen Kunden zu ermöglichen, können Sie insbesondere die Info-Schaltfläche von `Application` ändern oder ersetzen. Anstatt ein Meldungsdialogfeld zu öffnen, kann die Schaltfläche zu der Klasse `FormView` navigieren:

```java
private Button addCustomer = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Abgeschlossenes `MainView` {#completed-mainview}

Mit der Navigation zu `FormView` und gruppierten Tabellenmethoden sollte folgendermaßen aussehen, bevor Sie mit der Erstellung von `FormView` fortfahren:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
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
      table.addColumn("company", Customer::getCompany).setLabel("Firma");
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

## `FormView` erstellen {#creating-formview}

`FormView` wird ein Formular anzeigen, um neue Kunden hinzuzufügen. Für jede Kunden-Eigenschaft wird `FormView` eine bearbeitbare Komponente haben, mit der Benutzer interagieren können. Darüber hinaus wird es eine Schaltfläche haben, mit der Benutzer die Daten übermitteln können, und eine Abbrechen-Schaltfläche, um sie zu verwerfen.

### Erstellen einer `Customer`-Instanz {#creating-a-customer-instance}

Wenn ein Benutzer Daten für einen neuen Kunden bearbeitet, sollten Änderungen erst auf das Repository angewendet werden, wenn er bereit ist, das Formular abzusenden. Die Verwendung einer Instanz des `Customer`-Objekts ist eine praktische Möglichkeit, die neuen Daten zu bearbeiten und zu verwalten, ohne das Repository direkt zu bearbeiten. Erstellen Sie eine neue `Customer`-Instanz innerhalb von `FormView`, um sie für das Formular zu verwenden:

```java
private Customer customer = new Customer();
```

Um die `Customer`-Instanz bearbeitbar zu machen, sollte jede Eigenschaft, mit Ausnahme der `id`, mit einer bearbeitbaren Komponente verbunden sein. Die Änderungen, die ein Benutzer in der Benutzeroberfläche vornimmt, sollten in der `Customer`-Instanz widergespiegelt werden.

### Hinzufügen von `TextField`-Komponenten {#adding-textfield-components}

Die ersten drei bearbeitbaren Eigenschaften in `Customer` (`firstName`, `lastName` und `company`) sind alle `String`-Werte und sollten mit einem einzeiligen Texteditor dargestellt werden. [`TextField`](/docs/components/fields/textfield) Komponenten sind eine gute Wahl, um diese Eigenschaften darzustellen.

Mit der `TextField`-Komponente können Sie ein Label und einen Ereignislistener hinzufügen, der immer dann ausgelöst wird, wenn sich der Wert der Felder ändert. Jeder Ereignislistener sollte die `Customer`-Instanz für die entsprechende Eigenschaft aktualisieren.

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
Die Benennung der Komponenten genauso wie die Eigenschaften, die sie für die `Customer`-Entität darstellen, erleichtert das Binden der Daten in einem zukünftigen Schritt, [Validieren und Binden von Daten](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Hinzufügen einer `ChoiceBox`-Komponente {#adding-a-choicebox-component}

Die Verwendung eines `TextField` für die `country`-Eigenschaft wäre nicht ideal, da diese Eigenschaft nur einer von fünf Enum-Werten sein kann: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` und `USA`.

Eine bessere Komponente zur Auswahl aus einer vordefinierten Liste von Optionen ist die [`ChoiceBox`](/docs/components/lists/choicebox).

Jede Option für eine `ChoiceBox`-Komponente wird als `ListItem` dargestellt. Jeder `ListItem` hat zwei Werte, einen `Object`-Schlüssel und einen `String`-Text, der in der Benutzeroberfläche angezeigt wird. Wenn zwei Werte für jede Option vorhanden sind, können Sie den `Object` intern behandeln und gleichzeitig eine verständlichere Option für Benutzer in der Benutzeroberfläche präsentieren.

Zum Beispiel könnte der `Object`-Schlüssel eine Internationale Standardbuchnummer (ISBN) sein, während der `String`-Text der Buchtitel ist, der ein human-readable ist.

```java
new ListItem(isbn, bookTitle);
```

In dieser Anwendung handelt es sich jedoch um eine Liste von Ländernamen. Für jeden `ListItem` möchten Sie, dass der `Object` der `Customer.Country`-Enum ist, während der Text seine `String`-Darstellung sein kann.

Um alle `country`-Optionen in eine `ChoiceBox` einzufügen, können Sie einen Iterator verwenden, um für jede `Customer.Country`-Enum einen `ListItem` zu erstellen, und diese in eine `ArrayList<ListItem>` einfügen. Anschließend können Sie diese `ArrayList<ListItem>` in eine `ChoiceBox`-Komponente einfügen:

```java
//Erstellen Sie die ChoiceBox-Komponente
private ChoiceBox country = new ChoiceBox("Land");

//Erstellen Sie eine ArrayList von ListItem-Objekten
ArrayList<ListItem> listCountries = new ArrayList<>();

//Fügen Sie einen Iterator hinzu, der einen ListItem für jede Customer.Country-Option erstellt
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

//Fügen Sie die gefüllte ArrayList in die ChoiceBox ein
country.insert(listCountries);

//Macht das erste `ListItem` zur Standardauswahl, wenn das Formular geladen wird
country.selectIndex(0);
```

Wenn der Benutzer eine Option in der `ChoiceBox` auswählt, sollte die `Customer`-Instanz mit dem Schlüssel des ausgewählten Elements aktualisiert werden, der ein `Customer.Country`-Wert ist.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Um den Code sauber zu halten, sollte der Iterator, der die `ArrayList<ListItem>` erstellt und sie zur `ChoiceBox` hinzufügt, in einer separaten Methode stehen.
Nachdem Sie eine `ChoiceBox` hinzugefügt haben, die dem Benutzer ermöglicht, die `country`-Eigenschaft auszuwählen, sollte `FormView` so aussehen:

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
Erstellen Sie zwei `Button`-Komponenten, um dieses Feature zu implementieren:

```java
private Button submit = new Button("Abschicken");
private Button cancel = new Button("Abbrechen");
```

Sowohl die Absenden- als auch die Abbrechen-Schaltflächen sollten den Benutzer zu `MainView` zurückführen.
Dies ermöglicht es dem Benutzer, die Ergebnisse seiner Aktion sofort zu sehen, unabhängig davon, ob er einen neuen Kunden in der Tabelle sieht oder ob dieser unverändert bleibt.
Da mehrere Eingaben in `FormView` den Benutzer zu `MainView` führen, sollte die Navigation in eine aufrufbare Methode eingefügt werden:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Abbrechen-Schaltfläche**

Das Verwerfen der Änderungen im Formular erfordert keinen zusätzlichen Code für das Ereignis, außer zurück zu `MainView` zu gehen. Da das Abbrechen jedoch keine Hauptaktion ist, gibt das Setzen des Themas der Schaltfläche auf eine Umrandung der Absenden-Schaltfläche mehr Bedeutung.

Die [Themes](/docs/components/button#themes) Abschnitt der `Button`-Komponenten-Seite listet alle verfügbaren Themen auf.

```java
private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Absenden-Schaltfläche**

Wenn ein Benutzer die Absenden-Schaltfläche drückt, sollten die Werte in der `Customer`-Instanz verwendet werden, um einen neuen Eintrag im Repository zu erstellen.

Mit dem `CustomerService` können Sie die `Customer`-Instanz verwenden, um die H2-Datenbank zu aktualisieren. Wenn dies geschieht, wird der `Customer` eine neue und eindeutige `id` zugewiesen. Nach der Aktualisierung des Repositorys können Sie die Benutzer zu `MainView` umleiten, wo sie den neuen Kunden in der Tabelle sehen können.

```java
private Button submit = new Button("Abschicken", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### Verwendung eines `ColumnsLayout` {#using-a-columnslayout}

Durch das Hinzufügen der `TextField`, `ChoiceBox` und `Button`-Komponenten haben Sie jetzt alle interaktiven Teile des Formulars. Die letzte Verbesserung an `FormView` in diesem Schritt besteht darin, die sechs Komponenten visuell zu organisieren.

Dieses Formular kann ein [`ColumnsLayout`](/docs/components/columns-layout) verwenden, um die Komponenten in zwei Spalten zu unterteilen, ohne die Breite einer aktiven Komponente festzulegen.
Um ein `ColumnsLayout` zu erstellen, spezifizieren Sie jede Komponente, die sich innerhalb des Layouts befinden soll:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Um die Anzahl der Spalten für ein `ColumnsLayout` festzulegen, verwenden Sie eine `List` von `Breakpoint`-Objekten. Jeder `Breakpoint` sagt dem `ColumnsLayout`, wie viel Platz es mindestens haben muss, um eine bestimmte Anzahl von Spalten anzuwenden. Mit dem `ColumnsLayout` können Sie ein Formular mit zwei Spalten erstellen, jedoch nur, wenn der Bildschirm breit genug ist, um zwei Spalten anzuzeigen. Bei kleineren Bildschirmen werden die Komponenten in einer einzelnen Spalte angezeigt.

Der [Breakpoints](/docs/components/columns-layout#breakpoints) Abschnitt im Artikel über `ColumnsLayout` erklärt die Breakpoints ausführlicher.

Um den Code wartbar zu halten, setzen Sie die Breakpoints in einer separaten Methode. In dieser Methode können Sie auch den horizontalen und vertikalen Abstand zwischen den Komponenten innerhalb des `ColumnsLayout` mit der Methode `setSpacing()` steuern.

```java
private void setColumnsLayout() {

  //Zwei Spalten im ColumnsLayout haben, wenn es breiter als 600px ist
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  //Fügen Sie die Liste der Breakpoints hinzu
  layout.setBreakpoints(breakpoints);

  //Setzen Sie den Abstand zwischen den Komponenten mit einer DWC-CSS-Variable
  layout.setSpacing("var(--dwc-space-l)")
}
```

Schließlich können Sie das neu erstellte `ColumnsLayout` zur gebundenen Komponente von `FormView` hinzufügen und auch die maximale Breite festlegen und den Klassennamen von zuvor hinzufügen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Abgeschlossenes `FormView` {#completed-formview}

Nachdem Sie eine `Customer`-Instanz, die interaktiven Komponenten und das `ColumnsLayout` hinzugefügt haben, sollte Ihr `FormView` wie folgt aussehen:

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
    private TextField company = new TextField("Firma", e -> customer.setCompany(e.getValue()));
    private ChoiceBox country = new ChoiceBox("Land",
        e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
    private Button submit = new Button("Abschicken", ButtonTheme.PRIMARY, e -> submitCustomer());
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

Da Benutzer jetzt Kunden hinzufügen können, sollte Ihre Anwendung auch in der Lage sein, bestehende Kunden mit demselben Formular zu bearbeiten. Im nächsten Schritt, [Beobachter und Routenparameter](/docs/introduction/tutorial/observers-and-route-parameters), werden Sie es ermöglichen, dass die Kunden-ID einen anfänglichen Parameter für `FormView` darstellt, damit das Formular mit den Daten dieses Kunden ausgefüllt werden kann und die Benutzer die Eigenschaften ändern können.
