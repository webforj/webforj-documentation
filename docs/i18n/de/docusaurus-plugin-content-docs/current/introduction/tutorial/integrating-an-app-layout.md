---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: ddf62eb6d62a711c38f9ddaf9caeabad
---
In diesem Schritt ziehen Sie alle Teile Ihrer Anwendung in ein kohärentes Anwendungs-Layout. Am Ende dieses Schrittes wird die Struktur Ihrer Anwendung dem [SideMenu-Archetyp](/docs/building-ui/archetypes/sidemenu) ähneln, und Sie werden ein besseres Verständnis dafür haben, wie die folgenden Komponenten und Konzepte funktionieren:

- [`FlexLayout`](/docs/components/flex-layout)
- [Route Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Die App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum Verzeichnis auf oberster Ebene, das die `pom.xml`-Datei enthält. Dies ist `6-integrating-an-app-layout`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Eine wiederverwendbare Komponente erstellen {#creating-a-reusable-component}

In einem vorherigen Schritt, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), haben Sie zwei zusammengesetzte Komponenten erstellt, die den Inhalt der Kundentabelle und des Kundenformulars enthielten. Im Rahmen dieses Schrittes erstellen Sie eine kleinere, wiederverwendbare zusammengesetzte Komponente, um den Namen der App im Seitenmenü und auf einer Über-Seite anzuzeigen. Wenn Sie den Namen der App in der Zukunft ändern möchten, müssen Sie ihn nur in dieser Komponente aktualisieren.

Erstellen Sie in `src/main/java/com/webforj/tutorial/components` eine Klasse namens `AppTitle`. Die gebundene Komponente für `AppTitle` wird ein `FlexLayout` sein, eine Containerkomponente, die in diesem Schritt verwendet wird, um Ihnen zu zeigen, wie man komplexere Layouts erstellt. Für dieses `FlexLayout` werden Sie die Richtung der Elemente und den Abstand zwischen den Elementen anordnen. Dies wird durch die Verwendung der Methoden `setDirection()` und `setSpacing()` erreicht.

```java title='AppTitle.java'
// Machen Sie die gebundene Komponente ein FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Arrangieren Sie die Elemente vertikal
    self.setDirection(FlexDirection.COLUMN);

    // Setzen Sie den Abstand zwischen den Elementen
    self.setSpacing("0px");
  }
}
```

Verwenden Sie dann Standard-HTML-Elemente, um den Titel und den Untertitel zu erstellen. Das Setzen des unteren Abstands eines Header-Elements auf `0px` bringt die Elemente näher zusammen, und Sie können den Untertitel mithilfe von [DWC CSS-Variablen](/docs/styling/css-variables) gestalten.

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Kundenmanager");
  private Paragraph subTitle = new Paragraph("Ein einfaches Aufzeichnungssystem");

  public AppTitle() {
    title.setStyle("margin-bottom", "0px");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title, subTitle);
  }
}
```

### Optionales Rendering {#optional-rendering}

Obwohl `AppTitle` einfach ist, ermöglicht das Hinzufügen eines boolesken Arguments zur Konstruktor-Methode, zu steuern, wann bestimmte Teile der Komponente, wie der Untertitel, gerendert werden sollen.

```java title='AppTitle.java'
// Fügen Sie ein boolesches Argument hinzu
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Fügen Sie den Titel standardmäßig hinzu
      .add(title);

  // Optional den Untertitel anzeigen
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Abgeschlossenes `AppTitle` {#completed-app-title}

Insgesamt sollte die wiederverwendbare Komponente wie folgt aussehen:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Kundenmanager");
  private Paragraph subTitle = new Paragraph("Ein einfaches Aufzeichnungssystem");

  public AppTitle(boolean showSubTitle) {
    title.setStyle("margin-bottom", "0");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title);

    if (showSubTitle) {
      self.add(subTitle);
    }
  }
}
```

## Eine Über-Seite erstellen {#creating-an-about-page}

Der erste Ort, an dem die neu erstellte `AppTitle`-Komponente hinzugefügt werden soll, ist eine Über-Seite. Diese Seite enthält ein Bild und die `AppTitle`-Komponente, die mithilfe eines weiteren `FlexLayout` zentriert ist.

### Inhalte mit einem `FlexLayout` zentrieren {#centering-content-using-a-flexlayout}

Das Ziel ist es, den Inhalt der Über-Seite mit dem `FlexLayout` zu zentrieren. Die `FlexLayout`-Komponente folgt dem [CSS Flexbox-Layoutmodell](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Methoden für das `FlexLayout`, wie die zuvor verwendeten, um die Elemente in eine Spalte auszurichten, sind verschiedene Möglichkeiten, die Elemente anzuordnen.

Die Methoden zur Anordnung von Elementen in einem `FlexLayout` verwenden ein relatives Richtungsystem. Anstatt über die horizontalen und vertikalen Achsen nachzudenken, ist es besser, über die Achse nachzudenken, die parallel zu den Elementen verläuft, als die Hauptachse, und über die Achse, die senkrecht zu den Elementen steht, als die Querachse.

Die Eigenschaften `FlexJustifyContent` und `FlexAlignment` beides auf `CENTER` zu setzen, zentriert die Elemente sowohl entlang der Hauptachse als auch der Querachse im `FlexLayout`, und macht das `FlexLayout`, das den gesamten Raum seines übergeordneten Containers einnimmt, zentriert auf der Seite.

```java
private final FlexLayout layout = new FlexLayout();

// Füllen Sie den gesamten Raum des Elternelements
layout.setSize("100%", "100%");

// Machen Sie die Hauptachse vertikal
layout.setDirection(FlexDirection.COLUMN);

// Zentrieren Sie die Elemente entlang der Querachse
layout.setAlignment(FlexAlignment.CENTER);

// Zentrieren Sie die Elemente entlang der Hauptachse
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Um zu helfen, zu visualisieren, wie die verschiedenen Methoden funktionieren, sehen Sie sich den Blog-Beitrag [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container) an.

### Ressourcen hinzufügen {#adding-resources}

Eines der Elemente, das sich im zentrierten `FlexLayout` befinden wird, ist ein Bild. Für dieses Tutorial können Sie das Bild [der Über-Seite](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) auf GitHub anzeigen und herunterladen. Nach dem Herunterladen fügen Sie es in den statischen Ordner Ihres Projekts unter `src/main/resources/static/images` ein und benennen Sie es in `Files.svg` um.

Das Platzieren dieses Bildes im statischen Ordner ermöglicht es Ihnen, es unter Verwendung des [Webserver-Protokolls](/docs/managing-resources/assets-protocols#the-webserver-protocol) zu referenzieren. Dann können Sie es in Ihrer App als HTML-Element verwenden, zum Beispiel so:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### Erstellen von `AboutView` {#creating-about-view}

Wie die zwei bestehenden App-Seiten wird auch die Über-Seite eine routbare Ansicht sein. Fügen Sie in `src/main/java/com/webforj/tutorial/views` eine Klasse namens `AboutView` hinzu. Verwenden Sie ein `FlexLayout` für die gebundene Komponente, wie Sie es für `AppTitle` getan haben.

Da Sie der Klasse `AboutView` den Namen gegeben haben, müssen Sie keinen benutzerdefinierten Wert für das URL-Mapping angeben; diese Seite wird standardmäßig unter `http://localhost:8080/about` gerendert.

Hier sehen Sie, wie Sie die Konzepte aus den vorherigen Schritten mit den neu erstellten Komponenten verwenden, um eine neue Ansicht mit zentriertem Inhalt zu erstellen:

```java title='AboutView.java'
@Route()
@FrameTitle("Über")
public class AboutView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Img fileImg = new Img("ws://images/Files.svg");

  public AboutView() {
    fileImg.setWidth(250);
    self.setSize("100%", "100%")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(fileImg, new AppTitle(false));
  }
}
```

## Die `Layout`-Route erstellen {#creating-the-layout-route}

Es wird kurz im Schritt [Routing and Composites](/docs/introduction/tutorial/routing-and-composites) erwähnt, aber es gibt zwei [Routenarten](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` und `AboutView` sind alles `View`-Routen, während die Routenart, die Sie erstellen werden, um das Seitenmenü der App zu erstellen, eine `Layout`-Route ist.

Layout-Routen umschließen untergeordnete Ansichten und ermöglichen es, dass bestimmte UI-Teile über verschiedene Ansichten hinweg bestehen bleiben, wie ein Seitenmenü. Erstellen Sie in `src/main/java/com/webforj/tutorial/layouts` eine Klasse namens `MainLayout`.

### Routen-Outlets {#route-outlets}

Wie die Sicht-Routen benötigt `MainLayout` eine `@Route`-Annotation. Da es jedoch `Layout` als Suffix hat und Layout-Routen nicht zur URL beitragen, benötigt diese Annotation keine Argumente.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

Die App weiß, welche Ansichten innerhalb von `MainLayout` gerendert werden sollen, indem die Layout-Klasse in jeder Ansicht als [Route Outlet](/docs/routing/route-hierarchy/route-outlets) deklariert wird. In den vorherigen Schritten ist nur die `value`-Eigenschaft in den `@Route`-Annotationen gesetzt, sodass Sie nun explizit angeben müssen, was die `value`- und `outlet`-Eigenschaften für die Ansichtsklassen sind.

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java
  @Route(value = "/", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="AboutView" label="AboutView">
  ```java
  @Route(outlet = MainLayout.class)
  ```
  </TabItem>
</Tabs>

:::note Abschließende Anpassungen
Dies ist die letzte Modifikation, die für `FormView` und `AboutView` in diesem Schritt erforderlich ist. Denken Sie daran, die `@Route`-Annotation für diese Ansichten zu aktualisieren, bevor Sie Ihre App ausführen.
:::

## Verwendung der `AppLayout`-Komponente {#using-the-app-layout-component}

Jetzt, da Ihre App die Ansichten innerhalb von `MainLayout` rendert, können Sie auswählen, wo diese Komponenten gerendert werden. Die Wahl der `AppLayout` als gebundene Komponente für `MainLayout` ermöglicht es Ihnen, die Ansichten standardmäßig in einem Hauptinhaltbereich zu speichern und gleichzeitig verschiedene Bereiche bereitzustellen, um Elemente für die Kopfzeile und das Seitenmenü hinzuzufügen.

### Slots {#slots}

Für viele webforJ-Container fügt die Verwendung der `add()`-Methoden UI-Komponenten zum Hauptinhaltbereich hinzu. In der `AppLayout`-Komponente gibt es mehrere Bereiche zum Hinzufügen von UI-Komponenten, jeweils in einem separaten Slot. Indem Sie `MainLayout` als Layout-Route markiert und seine gebundene Komponente als `AppLayout` festgelegt haben, werden die Ansichten automatisch im Hauptinhaltsbereich gerendert.

In diesem Schritt verwenden Sie die Slots `drawer-title` und `drawer`, um ein Seitenmenü zu erstellen, und den Slot `header`, um anzuzeigen, auf welcher Seite sich der Benutzer befindet und einen Umschalter für das Seitenmenü hinzuzufügen.

### Ein Seitenmenü erstellen {#making-a-side-menu}

Wenn auf dem Gerät genügend Bildschirmplatz vorhanden ist, zeigt die `AppLayout`-Komponente eine Schublade an. Hier fügen Sie die `AppTitle` erneut hinzu und Elemente, die es den Benutzern ermöglichen, durch die App zu navigieren.

Standardmäßig zeigt `AppLayout` keinen Schubladentitel an, aber mit der Methode `setDrawerHeaderVisible()` können Sie Elemente anzeigen, die sich im `drawer-title`-Slot befinden, was die `AppTitle` mit ihrem Untertitel angezeigt wird.

```java
private AppLayout appLayout = new AppLayout();

// Zeigen Sie die Schubladenüberschrift an
appLayout.setDrawerHeaderVisible(true);

// Fügen Sie die AppTitle zur Schubladenüberschrift hinzu, mit ihrem Untertitel
appLayout.addToDrawerTitle(new AppTitle(true));
```

Der `drawer`-Slot sollte dann die Komponenten enthalten, die es den Benutzern ermöglichen, innerhalb der App zu navigieren. Die Verwendung der [`AppNav`](/docs/components/appnav)-Komponente erleichtert es, neue Navigationsoptionen zu erstellen. Für jeden Link müssen Sie nur ein `AppNavItem` erstellen. Die `AppNavItem`-Komponenten in diesem Tutorial verwenden drei Parameter:

- Das Etikett für den Link
- Die Zielansicht
- Eine optionale [`Icon`](/docs/components/icon)-Komponente, die Bilder von [Tabler](https://tabler.io/icons) verwendet

Das Gruppieren aller Drawer-Einstellungen in `MainLayout` sieht wie folgt aus:

```java title="MainLayout"
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setDrawer();
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("Dashboard", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("Über", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
}
```

### Eine Kopfzeile erstellen {#making-a-header}

Der `header`-Slot sollte zwei Elemente enthalten: einen Umschalter zum Anzeigen oder Ausblenden des Seitenmenüs und eine Möglichkeit, den Rahmentitel anzuzeigen. Beide Elemente befinden sich in einer [Toolbar](/docs/components/toolbar)-Komponente, einer weiteren Möglichkeit, Komponenten zu organisieren.

Sie können den Umschalter für die `AppLayout`-Schublade mit der `AppDrawerToggle`-Komponente einfügen. Diese Komponente ist bereits mit einem häufig verwendeten Symbol für versteckte Menüoptionen gestaltet und zielt darauf ab, die Schublade zu öffnen und zu schließen.

```java
// Erstellen Sie die Container-Komponenten
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Fügen Sie die Toolbar zur Kopfzeile von AppLayout hinzu
appLayout.addToHeader(toolbar);

// Fügen Sie den AppDrawerToggle zur Toolbar hinzu
toolbar.addToStart(new AppDrawerToggle());
```

Die Kopfzeile kann auch den Rahmentitel anzeigen, indem das Navigationsevent verwendet wird, um Details über die eingehende Komponente abzurufen, während ein Ereignislistener registriert wird, um die Registrierung zu entfernen, um Gedächtnislecks zu verhindern.

```java
// Erstellen Sie das H1-Element und die Navigationsregistrierung
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Registrieren Sie das Ereignis beim Navigieren
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Entfernen Sie die Listener, bevor MainLayout zerstört wird
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Rufen Sie den Rahmentitel aus der eingehenden Ansichtsklasse ab
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Abgeschlossenes `MainLayout` {#completed-mainlayout}

Hier ist `MainLayout` mit den erstellten Inhalten für den Drawer und die Kopfzeile innerhalb eines `AppLayout`:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route
  public class MainLayout extends Composite<AppLayout> {
    private AppLayout self = getBoundComponent();
    private H1 title = new H1("");
    private ListenerRegistration<NavigateEvent> navigateRegistration;
    private Toolbar toolbar = new Toolbar();
    private AppNav appNav = new AppNav();

    public MainLayout() {
      setHeader();
      setDrawer();
      navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);
    }

    private void setHeader() {
      self.addToHeader(toolbar);

      toolbar.addToStart(new AppDrawerToggle());
      toolbar.addToTitle(title);
    }

    private void setDrawer() {
      self.setDrawerHeaderVisible(true)
          .addToDrawerTitle(new AppTitle(true));

      appNav.addItem(new AppNavItem("Dashboard", MainView.class,
          TablerIcon.create("archive")));
      appNav.addItem(new AppNavItem("Über", AboutView.class,
          TablerIcon.create("info-circle")));
      self.addToDrawer(appNav);
    }

    @Override
    protected void onDidDestroy() {
      if (navigateRegistration != null) {
        navigateRegistration.remove();
      }
    }

    private void onNavigate(NavigateEvent ev) {
      Component component = ev.getContext().getComponent();
      if (component != null) {
        FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
        title.setText(frameTitle != null ? frameTitle.value() : "");
      }
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Aktualisierung von `FormView` {#updating-form-view}

Wie bereits erwähnt, bestand die einzige Änderung an `FormView` in der `@Route`-Annotation.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## Aktualisierung von `MainView` {#updating-main-view}

Für `MainView` ändern Sie die gebundene Komponente von einem `Div` zu einem `FlexLayout`. Dies ermöglicht es Ihnen, die Tabelle zu zentrieren und spezifische Komponenten innerhalb des Layouts zu verschieben. Mit der Methode `setItemAlignment()` können Sie ein Component im Layout auswählen und verschieben, sodass Sie die Tabelle zentriert halten können, während der Button zum Hinzufügen von Kunden in der oberen rechten Ecke des Layouts verankert wird.

```java
// Ändern Sie die gebundene Komponente in ein FlexLayout
private FlexLayout self = getBoundComponent();

// Richten Sie die Schaltfläche am Ende der Querachse aus
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Eine weitere Verbesserung, die Sie hier vornehmen können, ist die Breite der Tabelle. Anstatt eine feste Breite festzulegen, können Sie sie so einstellen, dass sie ihrem übergeordneten Container, dem `FlexLayout`, entspricht. Dann kann dieses `FlexLayout` eine maximale Breite haben, damit es auf größeren Bildschirmen nicht überdehnt wird.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Diese beiden Dinge zusammenzuführen und eine weitere Methode zu erstellen, um das `FlexLayout` wie in den vorherigen Beispielen zu zentrieren, ergibt `MainView` mit den hervorgehobenen Änderungen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Kundentabelle")
  // highlight-next-line
  public class MainView extends Composite<FlexLayout> {
    private final CustomerService customerService;
    // highlight-next-line
    private FlexLayout self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Kunden hinzufügen", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      // highlight-next-line
      setFlexLayout();
      // highlight-next-line
      self.add(addCustomer, table);
      // highlight-next-line
      self.setItemAlignment(FlexAlignment.END, addCustomer);
    }

    private void buildTable() {
      // highlight-next-line
      table.setSize("100%", "294px");
      table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
      table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
      table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
      table.addColumn("country", Customer::getCountry).setLabel("Land");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
      table.setKeyProvider(Customer::getId);
      table.addItemClickListener(this::editCustomer);
    }

    // highlight-next-line
    private void setFlexLayout() {
      // highlight-next-line
      self.setSize("100%", "100%")
          // highlight-next-line
          .setMargin("auto")
          // highlight-next-line
          .setMaxWidth(2000)
          // highlight-next-line
          .setDirection(FlexDirection.COLUMN)
          // highlight-next-line
          .setAlignment(FlexAlignment.CENTER);
          // highlight-next-line
    }

    private void editCustomer(TableItemClickEvent<Customer> e) {
      Router.getCurrent().navigate(FormView.class,
          ParametersBag.of("id=" + e.getItemKey()));
    }
  }
`}
</ExpandableCode>
<!-- vale on -->
