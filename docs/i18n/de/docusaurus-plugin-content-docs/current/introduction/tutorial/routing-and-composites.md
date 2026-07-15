---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: b6d14f241d64208bfcfff527691bf8e9
---
Bis jetzt war dieses Tutorial nur eine Single-Page-App. Dieser Schritt ändert das.
Sie werden die Benutzeroberfläche, die Sie in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data) erstellt haben, auf ihre eigene Seite verschieben und eine weitere Seite zum Hinzufügen neuer Kunden erstellen.
Dann verbinden Sie diese Seiten, sodass Ihre App in der Lage ist, zwischen ihnen zu navigieren, indem Sie diese Konzepte anwenden:

- [Routing](/docs/routing/overview)
- [Komposition von Komponenten](/docs/building-ui/composing-components)
- Die [`ColumnsLayout`](/docs/components/columns-layout) Komponente

Durch das Abschließen dieses Schrittes erstellen Sie eine Version von [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Video hier einfügen -->

## Ausführen der App {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält; dies ist `3-routing-and-composites`, wenn Sie mit der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Durch das Ausführen der App wird automatisch ein neuer Browser unter `http://localhost:8080` geöffnet.

## Routbare Apps {#routable-apps}

Früher hatte Ihre App eine einzige Funktion: das Anzeigen einer Tabelle mit vorhandenen Kundendaten.
In diesem Schritt wird Ihre App auch in der Lage sein, die Kundendaten zu ändern, indem neue Kunden hinzugefügt werden.
Die Trennung der Benutzeroberflächen für Anzeige und Bearbeitung ist vorteilhaft für die langfristige Wartung und Tests, daher fügen Sie diese Funktion als separate Seite hinzu.
Sie werden Ihre App [routable](/docs/routing/overview) machen, damit webforJ auf die beiden Benutzeroberflächen einzeln zugreifen und sie laden kann.

Eine routable App rendert die Benutzeroberfläche basierend auf der URL. Die Annotation der Klasse, die die `App`-Klasse erweitert, mit [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) ermöglicht Routing, und das `packages`-Element sagt webforJ, welche Pakete UI-Komponenten enthalten.

Wenn Sie die `@Routify`-Annotation zu `Application` hinzufügen, entfernen Sie die `run()`-Methode. Sie werden die Komponenten aus dieser Methode in eine Klasse verschieben, die Sie im Paket `com.webforj.tutorial.views` erstellen werden. Ihre aktualisierte `Application.java`-Datei sollte wie folgt aussehen:

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

// Entfernte die überschreibende App.run()-Methode

}
```

:::tip Globale CSS
Die Beibehaltung der `@StyleSheet`-Annotation in `Application` wendet dieses CSS global an.
:::

### Routen erstellen {#creating-routes}

Die Hinzufügung der `@Routify`-Annotation macht Ihre App routable. Sobald sie routable ist, sucht Ihre App im Paket `com.webforj.tutorial.views` nach Routen.
Sie müssen die Routen für Ihre Benutzeroberflächen erstellen und auch deren [Route-Typen](/docs/routing/route-hierarchy/route-types) angeben. Der Routen-Typ bestimmt, wie der UI-Inhalt der URL zugeordnet wird.

Der erste Routentyp ist `View`. Diese Art von Routen wird direkt auf einen bestimmten URL-Segment in Ihrer App abgebildet. Die Benutzeroberflächen für die Tabelle und das Formular für neue Kunden werden beide `View`-Routen sein.

Der zweite Routentyp ist `Layout`, der UI enthält, die auf mehreren Seiten erscheint, wie z. B. einen Header oder eine Seitenleiste. Layout-Routen umschließen auch untergeordnete Ansichten, ohne zur URL beizutragen.

Um den Routentyp einer Klasse anzugeben, fügen Sie den Routentyp als Suffix ans Ende des Klassennamens an.
Zum Beispiel: `MainView` ist ein `View`-Routentyp.

Um die beiden Funktionen der App zu trennen, muss Ihre App die UIs auf zwei einzigartige `View`-Routen abbilden: eine für die Tabelle und eine für das Kundenformular. Erstellen Sie in `/src/main/java/com/webforj/tutorial/views` zwei Klassen mit dem Suffix `View`:

- **`MainView`**: Diese Ansicht hat die `Table`, die sich zuvor in der `Application`-Klasse befand.
- **`FormView`**: Diese Ansicht hat ein Formular zum Hinzufügen neuer Kunden.

### URLs auf Komponenten abbilden {#mapping-urls-to-components}

Ihre App ist routable und weiß, dass sie nach zwei `View`-Routen, `MainView` und `FormView`, suchen muss, hat aber keine spezifische URL, um sie zu laden. Mit der `@Route`-Annotation auf einer Ansichtsklasse können Sie webforJ mitteilen, wo es sie basierend auf einem gegebenen URL-Segment laden soll. Zum Beispiel mappt `@Route("about")` in einer Ansicht lokal die Klasse auf `http://localhost:8080/about`.

Wie der Name andeutet, ist `MainView` die Klasse, die Sie initial laden möchten, wenn die App gestartet wird. Um dies zu erreichen, fügen Sie eine `@Route`-Annotation hinzu, die `MainView` auf die Stamm-URL Ihrer App mappt:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

Für das `FormView` mappen Sie die Ansicht so, dass sie geladen wird, wenn ein Benutzer zu `http://localhost:8080/customer` geht:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Standardverhalten
Wenn Sie keinen Wert für die `@Route`-Annotation ausdrücklich zuweisen, wird das URL-Segment auf den Klassennamen in Kleinbuchstaben konvertiert, wobei das Suffix `View` entfernt wird.

- `MainView` würde auf `/main` abgebildet
- `FormView` würde auf `/form` abgebildet
:::

## Gemeinsame Merkmale {#shared-characteristics}

Neben der Tatsache, dass beide View-Routen sind, teilen sich `MainView` und `FormView` zusätzliche Eigenschaften. Einige dieser gemeinsamen Merkmale, wie die Verwendung von `Composite`-Komponenten, sind grundlegend für die Verwendung von webforJ-Apps, während andere nur die Verwaltung Ihrer App erleichtern.

### Verwendung von `Composite`-Komponenten {#using-composite-components}

Als die App eine Einzelseite war, haben Sie die Komponenten innerhalb eines `Frame` gespeichert. In der Folge, mit einer App mit mehreren Ansichten, müssen Sie diese UI-Komponenten innerhalb von [`Composite`-Komponenten](/docs/building-ui/composing-components) umschließen.

`Composite`-Komponenten sind Wrapper, die es einfach machen, wiederverwendbare Komponenten zu erstellen.
Um eine `Composite`-Komponente zu erstellen, erweitern Sie die Klasse `Composite` mit einer angegebenen gebundenen Komponente, die als Grundlage der Klasse dient, z. B. `Composite<FlexLayout>`.

Dieses Tutorial verwendet `Div`-Elemente als die gebundenen Komponenten, aber sie können jede Komponente sein, wie [`FlexLayout`](/docs/components/flex-layout) oder [`AppLayout`](/docs/components/app-layout). Mit der Methode `getBoundComponent()` können Sie auf die gebundene Komponente verweisen und Zugriff auf ihre Methoden haben. Dies ermöglicht es Ihnen, die Größen festzulegen, einen CSS-Klassennamen hinzuzufügen, Komponenten anzuzeigen, die in der `Composite`-Komponente angezeigt werden sollen, und auf komponentenspezifische Methoden zuzugreifen.

Für `MainView` und `FormView` erweitern Sie `Composite` mit `Div` als gebundene Komponente. Dann verweisen Sie auf die gebundene Komponente, damit Sie später die UIs hinzufügen können. Beide Ansichten sollten ähnliche Strukturen wie folgt haben:

```java
// Erweitern Sie Composite mit einer gebundenen Komponente
public class MainView extends Composite<Div> {

  // Zugriff auf die gebundene Komponente
  private Div self = getBoundComponent();

  // Erstellen Sie eine UI-Komponente
  private Button submit = new Button("Submit");

  public MainView() {

    // Fügen Sie die UI-Komponente der gebundenen Komponente hinzu
    self.add(submit);
  }
}
```

### Festlegen des Fenstertitels {#setting-the-frame-tile}

Wenn ein Benutzer mehrere Registerkarten in seinem Browser hat, hilft ein eindeutiger Fenstertitel dabei, schnell zu erkennen, welcher Teil der App geöffnet ist.

Die [`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) Annotation definiert, was im Titel des Browsers oder im Tab der Seite erscheint. Fügen Sie für beide Ansichten einen Fenstertitel mit der `@FrameTitle`-Annotation hinzu:

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

Mit einer gebundenen Komponente, auf die Sie in `MainView` und `FormView verweisen können, können Sie sie mit CSS stylen.
Sie können das CSS aus dem ersten Schritt, [Erstellen einer Basis-App](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), verwenden, um beiden Ansichten identische UI-Containerstile zu geben.
Fügen Sie den CSS-Klassennamen `card` der gebundenen Komponente in jeder Ansicht hinzu:

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

Das letzte gemeinsame Merkmal für die Ansichten besteht darin, die Klasse `CustomerService` zu verwenden.
Die `Table` in `MainView` zeigt jeden Kunden an, während `FormView` neue Kunden hinzufügt. Da beide Ansichten mit Kundendaten interagieren, benötigen sie Zugriff auf die Geschäftslogik der App.

Die Ansichten erhalten den Zugang über den in [Arbeiten mit Daten](/docs/introduction/tutorial/working-with-data#creating-a-service) erstellten Spring-Dienst `CustomerService`. Um den Spring-Dienst in jeder Ansicht zu verwenden, machen Sie `CustomerService` zu einem Konstruktorparameter:

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

## Erstellen von `MainView` {#creating-mainview}

Nachdem Sie Ihre App routable gemacht und den Ansichten `Composite`-Komponentenwrapper gegeben haben und den `CustomerService` einbezogen haben, sind Sie bereit, die UIs zu erstellen, die für jede Ansicht einzigartig sind. Wie bereits erwähnt, enthält `MainView` die UI-Komponenten, die sich ursprünglich in `Application` befanden. Diese Klasse benötigt auch eine Möglichkeit, zu `FormView` zu navigieren.

### Gruppierung der `Table`-Methoden {#grouping-the-table-methods}

Wenn Sie die Komponenten von `Application` nach `MainView` verschieben, ist es eine gute Idee, zu beginnen, Teile Ihrer App zu sektionieren, sodass eine benutzerdefinierte Methode die Änderungen an der `Table` auf einmal vornehmen kann. Die Pufferung Ihres Codes macht ihn jetzt verwaltbarer, da die App komplexer wird.

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

### Navigation zu `FormView`{#navigating-to-formview}

Benutzer benötigen eine Möglichkeit, von `MainView` zu `FormView` über die Benutzeroberfläche zu navigieren.

In webforJ können Sie direkt zu einer neuen Ansicht navigieren, indem Sie die Klasse der Ansicht verwenden. Die Routen über eine Klasse anstelle eines URL-Segments garantieren, dass webforJ den richtigen Pfad lädt.

Um zu einer anderen Ansicht zu navigieren, verwenden Sie die [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) Klasse, um den aktuellen Standort mit `getCurrent()` zu erhalten, und verwenden Sie dann die Methode `navigate()` mit der Klasse der Ansicht als Parameter:

```java
Router.getCurrent().navigate(FormView.class);
```

Dieser Code sendet Benutzer programmgesteuert zum Formular für neue Kunden, aber die Navigation muss mit einer Benutzeraktion verbunden werden.
Um Benutzern das Hinzufügen eines neuen Kunden zu ermöglichen, können Sie entweder die Informationsschaltfläche von `Application` ändern oder ersetzen. Anstatt ein Meldungsdialogfeld zu öffnen, kann die Schaltfläche zu `FormView` navigieren:

```java
private Button addCustomer = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Abgeschlossene `MainView` {#completed-mainview}

Mit der Navigation zu `FormView` und gruppierten Tabellenelementen sollte `MainView` vor dem Erstellen von `FormView` wie folgt aussehen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
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
`}
</ExpandableCode>
<!-- vale on -->

## Erstellen von `FormView` {#creating-formview}

`FormView` zeigt ein Formular zum Hinzufügen neuer Kunden an. Für jede Kundenproperty wird `FormView` mit einem bearbeitbaren Element ausgestattet, mit dem Benutzer interagieren können. Zusätzlich wird es eine Schaltfläche für Benutzer geben, um die Daten zu übermitteln, sowie eine Schaltfläche „Abbrechen“, um sie zu verwerfen.

### Erstellen einer `Customer`-Instanz {#creating-a-customer-instance}

Wenn ein Benutzer Daten für einen neuen Kunden bearbeitet, sollten die Änderungen erst auf das Repository angewendet werden, wenn er bereit ist, das Formular zu übermitteln. Die Verwendung einer Instanz des `Customer`-Objekts ist eine praktische Möglichkeit, die neuen Daten zu bearbeiten und beizubehalten, ohne das Repository direkt zu bearbeiten. Erstellen Sie eine neue `Customer`-Instanz innerhalb von `FormView`, die Sie für das Formular verwenden können:

```java
private Customer customer = new Customer();
```

Um die Instanz `Customer` bearbeitbar zu machen, sollte jede Property, außer der `id`, mit einem bearbeitbaren Element verknüpft sein. Die Änderungen, die ein Benutzer in der Benutzeroberfläche vornimmt, sollten in der Instanz `Customer` widergespiegelt werden.

### Hinzufügen von `TextField`-Komponenten {#adding-textfield-components}

Die ersten drei bearbeitbaren Eigenschaften in `Customer` (`firstName`, `lastName` und `company`) sind alle `String`-Werte und sollten mit einem einzeiligen Texteditor dargestellt werden. [`TextField`](/docs/components/fields/textfield) Komponenten sind eine ausgezeichnete Wahl, um diese Eigenschaften darzustellen.

Mit der `TextField`-Komponente können Sie ein Label und einen Ereignislistener hinzufügen, der ausgelöst wird, wann immer sich der Feldwert ändert. Jeder Ereignislistener sollte die Instanz `Customer` für die entsprechende Property aktualisieren.

Fügen Sie drei `TextField`-Komponenten hinzu, die die Instanz `Customer` aktualisieren:

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

:::tip Gemeinsame Benennungs-Konvention
Die Benennung der Komponenten genauso wie die Eigenschaften, die sie für die Entität `Customer` darstellen, erleichtert die Datenbindung in einem späteren Schritt, [Validierung und Datenbindung](/docs/introduction/tutorial/validating-and-binding-data).
:::

### Hinzufügen einer `ChoiceBox`-Komponente {#adding-a-choicebox-component}

Die Verwendung eines `TextField` für die `country`-Property wäre nicht ideal, da die Property nur einen von fünf Enum-Werten annehmen kann: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` und `USA`.

Eine bessere Komponente zum Auswählen aus einer vordefinierten Liste von Optionen ist die [`ChoiceBox`](/docs/components/lists/choicebox).

Jede Option für eine `ChoiceBox`-Komponente wird als `ListItem` dargestellt. Jedes `ListItem` hat zwei Werte, einen `Object`-Schlüssel und einen `String`-Text, der in der Benutzeroberfläche angezeigt wird. Die Verwendung von zwei Werten für jede Option ermöglicht es Ihnen, das `Object` intern zu handhaben, während gleichzeitig eine lesbarere Option für die Benutzer in der Benutzeroberfläche präsentiert wird.

```java
new ListItem(isbn, bookTitle);
```

Jedoch befasst sich diese App mit einer Liste von Ländernamen, nicht mit Büchern. Für jedes `ListItem` möchten Sie, dass das `Object` das `Customer.Country`-Enum ist, während der Text seine `String`-Darstellung sein kann.

Um alle `country`-Optionen in eine `ChoiceBox` hinzuzufügen, können Sie einen Iterator verwenden, um für jedes `Customer.Country`-Enum ein `ListItem` zu erstellen und diese in eine `ArrayList<ListItem>` einzufügen. Dann können Sie diese `ArrayList<ListItem>` in eine `ChoiceBox`-Komponente einfügen:

```java
// Erstellen der ChoiceBox-Komponente
private ChoiceBox country = new ChoiceBox("Land");

// Erstellen einer ArrayList von ListItem-Objekten
ArrayList<ListItem> listCountries = new ArrayList<>();

// Fügen Sie einen Iterator hinzu, der für jede Customer.Country-Option ein ListItem erstellt
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Fügen Sie die gefüllte ArrayList in die ChoiceBox ein
country.insert(listCountries);

// Setzt das erste `ListItem` als Standard, wenn das Formular geladen wird
country.selectIndex(0);
```

Dann sollte, wenn der Benutzer eine Option in der `ChoiceBox` auswählt, die Instanz `Customer` mit dem Schlüssel des ausgewählten Elements aktualisiert werden, was ein `Customer.Country`-Wert ist.

```java
private ChoiceBox country = new ChoiceBox("Land",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Um den Code sauber zu halten, sollte der Iterator, der die `ArrayList<ListItem>` erstellt und zur `ChoiceBox` hinzufügt, in eine separate Methode gestellt werden.
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

Wenn Benutzer das neue Kundenformular verwenden, sollten sie in der Lage sein, entweder ihre Änderungen zu speichern oder sie zu verwerfen.
Erstellen Sie zwei `Button`-Komponenten, um diese Funktionalität zu implementieren:

```java
private Button submit = new Button("Abschicken");
private Button cancel = new Button("Abbrechen");
```

Sowohl die Schaltfläche „Abschicken“ als auch die Schaltfläche „Abbrechen“ sollten den Benutzer zu `MainView` zurückbringen.
Dies ermöglicht es dem Benutzer, sofort die Ergebnisse seiner Aktion zu sehen, ob er einen neuen Kunde in der Tabelle sieht oder ob dieser unverändert bleibt.
Da mehrere Eingaben in `FormView` den Benutzer zu `MainView` führen, sollte die Navigation in eine abrufbare Methode gesetzt werden:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Abbrechen-Schaltfläche**

Das Verwerfen der Änderungen im Formular erfordert keinen zusätzlichen Code für die Ereignisse jenseits der Rückkehr zu `MainView`. Da das Abbrechen jedoch keine primäre Aktion ist, gibt das Setzen des Themas der Schaltfläche auf umreißt dem Abschicken-Button mehr Bedeutung.
Die [Themen](/docs/components/button#themes)-Sektion der `Button`-Komponenten-Seite listet alle verfügbaren Themen auf.

```java
private Button cancel = new Button("Abbrechen", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Abschicken-Schaltfläche**

Wenn ein Benutzer die Abschicken-Schaltfläche drückt, sollten die Werte in der Instanz `Customer` verwendet werden, um einen neuen Eintrag im Repository zu erstellen.

Mit dem `CustomerService` können Sie die Instanz `Customer` verwenden, um die H2-Datenbank zu aktualisieren. Wenn dies geschieht, wird der `Customer` eine neue und eindeutige `id` zugewiesen. Nachdem das Repository aktualisiert wurde, können Sie die Benutzer zu `MainView` umleiten, wo sie den neuen Kunden in der Tabelle sehen können.

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

Mit dem Hinzufügen der `TextField`, `ChoiceBox` und `Button`-Komponenten haben Sie jetzt alle interaktiven Teile des Formulars. Die letzte Verbesserung an `FormView` in diesem Schritt besteht darin, die sechs Komponenten visuell zu organisieren.

Dieses Formular kann ein [`ColumnsLayout`](/docs/components/columns-layout) verwenden, um die Komponenten in zwei Spalten zu trennen, ohne die Breite einer interaktiven Komponente festzulegen.
Um ein `ColumnsLayout` zu erstellen, geben Sie jede Komponente an, die sich innerhalb des Layouts befinden sollte:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Um die Anzahl der Spalten für ein `ColumnsLayout` festzulegen, verwenden Sie eine `List` von `Breakpoint`-Objekten. Jeder `Breakpoint` teilt dem `ColumnsLayout` mit, welche Mindestbreite erforderlich ist, um eine bestimmte Anzahl von Spalten anzuwenden. Durch die Verwendung des `ColumnsLayout` können Sie ein Formular mit zwei Spalten erstellen, aber nur, wenn der Bildschirm breit genug ist, um zwei Spalten anzuzeigen. Auf kleineren Bildschirmen werden die Komponenten in einer einzigen Spalte angezeigt.

Der Abschnitt [Breakpoints](/docs/components/columns-layout#breakpoints) im Artikel über `ColumnsLayout` erklärt die Breakpoints im Detail.

Um den Code wartbar zu halten, setzen Sie die Breakpoints in eine separate Methode. In dieser Methode können Sie auch den horizontalen und vertikalen Abstand zwischen den Komponenten im `ColumnsLayout` mit der Methode `setSpacing()` steuern.

```java
private void setColumnsLayout() {

  // Zwei Spalten im ColumnsLayout haben, wenn es breiter als 600px ist
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Fügen Sie die Liste von Breakpoints hinzu
  layout.setBreakpoints(breakpoints);

  // Setzt den Abstand zwischen den Komponenten mithilfe einer DWC-CSS-Variable
  layout.setSpacing("var(--dwc-space-l)")
}
```

Schließlich können Sie das neu erstellte `ColumnsLayout` zur gebundenen Komponente von `FormView` hinzufügen und gleichzeitig die maximale Breite festlegen und den zuvor hinzugefügten Klassennamen hinzufügen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Abgeschlossene `FormView` {#completed-formview}

Nachdem Sie eine `Customer`-Instanz, die interaktiven Komponenten und das `ColumnsLayout` hinzugefügt haben, sollte Ihr `FormView` wie folgt aussehen:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
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

Da Benutzer jetzt Kunden hinzufügen können, sollte Ihre App in der Lage sein, vorhandene Kunden mithilfe desselben Formulars zu bearbeiten. Im nächsten Schritt, [Beobachtungen und Routenparameter](/docs/introduction/tutorial/observers-and-route-parameters), werden Sie es ermöglichen, dass die `id` des Kunden ein anfänglicher Parameter für `FormView` wird, damit das Formular mit den Daten dieses Kunden ausgefüllt wird und Benutzer die Eigenschaften ändern können.
