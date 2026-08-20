---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: 3a2148bdfb680284a597a17c263609da
---
In diesem Schritt wirst du alle Teile deiner App in ein einheitliches App-Layout zusammenführen. Am Ende dieses Schrittes wird die Struktur deiner App der [SideMenu-Archetyp]( /docs/building-ui/archetypes/sidemenu) sehr ähnlich sehen, und du wirst ein besseres Verständnis dafür haben, wie die folgenden Komponenten und Konzepte funktionieren:

- [`FlexLayout`](/docs/components/flex-layout)
- [Route Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Die App ausführen {#running-the-app}

Während du deine App entwickelst, kannst du [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigiere zum obersten Verzeichnis, das die `pom.xml`-Datei enthält, das ist `6-integrating-an-app-layout`, wenn du der Version auf GitHub folgst.

2. Verwende den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Eine wiederverwendbare Komponente erstellen {#creating-a-reusable-component}

In einem vorherigen Schritt, [Routing und Kompositen](/docs/introduction/tutorial/routing-and-composites), hast du zwei composite Komponenten erstellt, die den Inhalt der Kundentabelle und das Kundenformular enthalten.
Im Rahmen dieses Schrittes wirst du eine kleinere, wiederverwendbare Composite-Komponente erstellen, um den Namen der App im Seitenmenü und auf einer "Über"-Seite anzuzeigen. Wenn du dich entscheidest, den Namen der App in Zukunft zu ändern, musst du ihn nur in dieser Komponente aktualisieren.

Erstelle in `src/main/java/com/webforj/tutorial/components` eine Klasse namens `AppTitle`. Die gebundene Komponente für `AppTitle` wird ein `FlexLayout` sein, eine Container-Komponente, die in diesem Schritt verwendet wird, um dir zu zeigen, wie man komplexere Layouts erstellt.
Für dieses `FlexLayout` wirst du die Richtung der Elemente und den Abstand zwischen den Elementen anordnen. Das geschieht durch die Verwendung von `setDirection()` und `setSpacing()` Methoden.

```java title='AppTitle.java'
// Mache die gebundene Komponente ein FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Ordne die Elemente vertikal an
    self.setDirection(FlexDirection.COLUMN);

    // Setze den Abstand zwischen den Elementen
    self.setSpacing("0px");
  }
}
```

Verwende dann standardmäßige HTML-Elemente, um die Überschrift und Unterüberschrift zu erstellen. Das Setzen des unteren Rands eines Header-Elements auf `0px` bringt die Elemente näher zusammen, und du kannst die Unterüberschrift mit Hilfe von [DWC CSS-Variablen](/docs/styling/css-variables) formatieren.

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

### Optionale Darstellung {#optional-rendering}

Auch wenn `AppTitle` einfach ist, ermöglicht das Hinzufügen eines booleschen Arguments zur Konstruktor-Methode, dass du kontrollieren kannst, wann bestimmte Teile der Komponente gerendert werden sollen, wie die Unterüberschrift.

```java title='AppTitle.java'
// Füge ein boolesches Argument hinzu
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Füge standardmäßig die Überschrift hinzu
      .add(title);

  // Optional die Unterüberschrift anzeigen
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Vollständige `AppTitle` {#completed-app-title}

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

## Eine "Über"-Seite erstellen {#creating-an-about-page}

Der erste Ort, an dem die neu erstellte `AppTitle`-Komponente hinzugefügt wird, wird eine "Über"-Seite sein. Diese Seite enthält ein Bild und die `AppTitle`-Komponente, zentriert auf der Seite mit einem weiteren `FlexLayout`.

### Inhalte zentrieren mit einem `FlexLayout` {#centering-content-using-a-flexlayout}

Das Ziel ist es, den Inhalt der "Über"-Seite mit dem `FlexLayout` zu zentrieren. Die `FlexLayout`-Komponente folgt dem [CSS Flexbox-Layoutmodell](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Methoden für das `FlexLayout`, wie die vorherigen, um die Elemente in einer Spalte anzuordnen, sind verschiedene Möglichkeiten, die Elemente anzuordnen.

Die Methoden zur Anordnung der Elemente in einem `FlexLayout` nutzen ein relatives Richtungssystem. Statt über die horizontalen und vertikalen Achsen nachzudenken, ist es besser, an die Achse parallel zu den Elementen als Hauptachse und die Achse senkrecht zu den Elementen als Querschnittsachse zu denken.

Das Setzen sowohl der `FlexJustifyContent`- als auch der `FlexAlignment`-Eigenschaften auf `CENTER` wird die Elemente entlang sowohl der Haupt- als auch der Querschnittsachsen im `FlexLayout` zentrieren, und dass das `FlexLayout` den gesamten Raum seines Elterncontainers einnimmt, macht es auf der Seite zentriert.

```java
private final FlexLayout layout = new FlexLayout();

// Fülle den gesamten Platz des Elternelements
layout.setSize("100%", "100%");

// Mache die Hauptachse vertikal
layout.setDirection(FlexDirection.COLUMN);

// Zentriere die Elemente entlang der Querschnittsachse
layout.setAlignment(FlexAlignment.CENTER);

// Zentriere die Elemente entlang der Hauptachse
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Um zu veranschaulichen, wie die verschiedenen Methoden funktionieren, sieh dir den Blogbeitrag [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container) an.

### Ressourcen hinzufügen {#adding-resources}

Eines der Elemente, das in das zentrierte `FlexLayout` gelangt, ist ein Bild. Für dieses Tutorial kannst du das [Bild der "Über"-Seite](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) auf GitHub anzeigen und herunterladen.
Sobald du es heruntergeladen hast, füge es in den statischen Ordner deines Projekts in `src/main/resources/static/images` ein und benenne es `Files.svg`.

Das Platzieren dieses Bildes im statischen Ordner ermöglicht es dir, es mithilfe des [Webserver-Protokolls](/docs/managing-resources/assets-protocols#the-webserver-protocol) zu referenzieren. Dann kannst du es innerhalb deiner App als HTML-Element verwenden, wie folgt:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### Erstellen von `AboutView` {#creating-about-view}

Wie die beiden bestehenden App-Seiten wird die "Über"-Seite eine routierbare Ansicht sein. Füge in `src/main/java/com/webforj/tutorial/views` eine Klasse namens `AboutView` hinzu. Verwende ein `FlexLayout` für die gebundene Komponente, wie du es für `AppTitle` getan hast.

Da du die Klasse `AboutView` genannt hast, ist es nicht notwendig, einen benutzerdefinierten Wert für die URL-Zuordnung zu vergeben; diese Seite wird standardmäßig unter `http://localhost:8080/about` gerendert.

Hier ist, wie es aussieht, wenn du die Konzepte aus den vorherigen Schritten mit den neu erstellten Komponenten kombiniert hast, um eine neue Ansicht mit zentrierten Inhalten zu erstellen:

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

Es wird kurz im Schritt [Routing und Kompositen](/docs/introduction/tutorial/routing-and-composites) erwähnt, aber es gibt zwei [Routentypen](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` und `AboutView` sind alles `View`-Routen, während der Routentyp, den du verwenden wirst, um das Seitenmenü der App zu erstellen, eine `Layout`-Route ist.

Layout-Routen umschließen untergeordnete Ansichten und lassen bestimmte UI-Teile über Ansichten hinweg bestehen, wie ein Seitenmenü. Erstelle in `src/main/java/com/webforj/tutorial/layouts` eine Klasse namens `MainLayout`.

### Route-Outlets {#route-outlets}

Wie die Sicht-Routen benötigt `MainLayout` eine `@Route`-Annotation. Da es jedoch `Layout` als Suffix hat und Layout-Routen nicht zur URL beitragen, benötigt diese Annotation keine Argumente.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

Die App weiß, welche Ansichten innerhalb von `MainLayout` gerendert werden sollen, indem die Layout-Klasse in jeder Ansicht als [Route-Outlets]( /docs/routing/route-hierarchy/route-outlets) deklariert wird. In den vorherigen Schritten hatten die `@Route`-Annotationen nur eine `value`-Eigenschaft festgelegt, also musst du nun explizit angeben, was die `value`- und `outlet`-Eigenschaften für die Klassen sind.

<!-- vale Google.Quotes = NO -->
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
<!-- vale Google.Quotes = YES -->

:::note Final touches
Dies ist die letzte Änderung, die für `FormView` und `AboutView` in diesem Schritt erforderlich ist, also denke daran, die `@Route`-Annotation für diese Ansichten zu aktualisieren, bevor du deine App ausführst.
:::

## Die Verwendung der `AppLayout`-Komponente {#using-the-app-layout-component}

Jetzt, da deine App die Ansichten innerhalb von `MainLayout` rendert, kannst du wählen, wo diese Komponenten gerendert werden. Wenn du `AppLayout` als die gebundene Komponente für `MainLayout` wählst, kannst du die Ansichten standardmäßig in einem Hauptinhaltsbereich speichern und hast zugleich verschiedene Bereiche, um Elemente für den Header und das Seitenmenü hinzuzufügen.

### Slots {#slots}

Für viele webforJ-Container fügen die `add()`-Methoden UI-Komponenten zum Hauptinhaltsbereich hinzu. In der `AppLayout`-Komponente gibt es mehrere Bereiche zum Hinzufügen von UI-Komponenten, jeweils in einem separaten Slot.
Indem du `MainLayout` als Layout-Route markierst und seine gebundene Komponente als `AppLayout` festlegst, werden die Ansichten automatisch im Hauptinhaltsslot gerendert.

In diesem Schritt wirst du die Slots `drawer-title` und `drawer` verwenden, um ein Seitenmenü zu erstellen, und den `header`-Slot verwenden, um anzuzeigen, auf welcher Seite sich der Benutzer befindet und einen Umschalter für das Seitenmenü.

### Ein Seitenmenü erstellen {#making-a-side-menu}

Wenn auf dem Gerät genügend Platz vorhanden ist, zeigt die `AppLayout`-Komponente eine Schublade an. Hier wirst du die `AppTitle` erneut hinzufügen und Elemente, die es den Benutzern ermöglichen, in der App zu navigieren.

Standardmäßig zeigt `AppLayout` keinen Schubladentitel an, aber durch die Verwendung der `setDrawerHeaderVisible()`-Methode kannst du Elemente anzeigen, die sich im `drawer-title`-Slot befinden, was die `AppTitle` mit angezeigter Unterüberschrift sein wird.

```java
private AppLayout appLayout = new AppLayout();

// Zeige den Schubladentitel an
appLayout.setDrawerHeaderVisible(true);

// Füge die AppTitle zum Schubladentitel mit ihrer Unterüberschrift hinzu
appLayout.addToDrawerTitle(new AppTitle(true));
```

Der `drawer`-Slot sollte dann die Komponenten enthalten, die es den Benutzern ermöglichen, in der App zu navigieren. Die Verwendung der [`AppNav`](/docs/components/appnav) Komponente erleichtert das Erstellen neuer Navigationsoptionen. Für jeden Link musst du nur ein `AppNavItem` erstellen.
Die `AppNavItem`-Komponenten in diesem Tutorial verwenden drei Parameter:

- Das Label für den Link
- Die Zielansicht
- Eine optionale [`Icon`](/docs/components/icon) Komponente, die Bilder von [Tabler](https://tabler.io/icons) verwendet

Die Gruppierung aller Schubladeneinstellungen in `MainLayout` sieht wie folgt aus:

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

### Einen Header erstellen {#making-a-header}

Der `header`-Slot sollte zwei Elemente enthalten: einen Schalter, um das Seitenmenü anzuzeigen oder auszublenden, und eine Möglichkeit, den Rahmentitel anzuzeigen. Beide Elemente werden innerhalb einer [Toolbar](/docs/components/toolbar) Komponente gruppiert, eine weitere Möglichkeit, Komponenten zu organisieren.

Du kannst den Schalter für die `AppLayout`-Schublade mit der `AppDrawerToggle`-Komponente einfügen. Diese Komponente ist bereits mit einem häufig verwendeten Symbol für ausgeblendete Menüoptionen gestaltet und zielt darauf ab, die Schublade zu öffnen und zu schließen.

```java
// Erstelle die Container-Komponenten
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Füge die Toolbar zum Header von AppLayout hinzu
appLayout.addToHeader(toolbar);

// Füge den AppDrawerToggle zur Toolbar hinzu
toolbar.addToStart(new AppDrawerToggle());
```

Der Header kann auch den Rahmentitel anzeigen, indem er das Navigationsevent verwendet, um Details über die eingehende Komponente abzurufen, während er einen Ereignis-Listener hat, um die Registrierung zu entfernen, um Speicherlecks zu vermeiden.

```java
// Erstelle das H1-Element und die Navigationsregistrierung
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Registriere das Ereignis beim Navigieren
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Entferne Listener, bevor MainLayout zerstört wird
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Hole den Rahmentitel von der eingehenden Ansichts-Klasse
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Vollständiges `MainLayout` {#completed-mainlayout}

Hier ist `MainLayout` mit erstelltem Inhalt für die Schublade und den Header innerhalb eines `AppLayout`:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">

```java
@Route
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
```

</ExpandableCode>
<!-- vale on -->

## `FormView` aktualisieren {#updating-form-view}

Wie bereits zuvor erwähnt, bestand die einzige Änderung an `FormView` darin, die `@Route`-Annotation zu aktualisieren.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## `MainView` aktualisieren {#updating-main-view}

Für `MainView` wirst du die gebundene Komponente von einem `Div` zu einem `FlexLayout` ändern. Dies ermöglicht es dir, die Tabelle zu zentrieren und spezifische Komponenten im Layout zu verschieben. Mit der Methode `setItemAlignment()` kannst du eine Komponente im Layout auswählen und verschieben, sodass du die Tabelle zentrieren kannst, während du den "Kunden hinzufügen"-Button in die obere rechte Ecke des Layouts verankerst.

```java
// Ändere die gebundene Komponente in ein FlexLayout
private FlexLayout self = getBoundComponent();

// Richte den Button am Ende der Querschnittsachse aus
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Eine weitere Verbesserung, die du hier vornehmen kannst, betrifft die Breite der Tabelle. Anstatt eine feste Breite zu verwenden, kannst du sie so einstellen, dass sie ihrem übergeordneten Container, dem `FlexLayout`, entspricht. Dann kann dieses `FlexLayout` eine maximale Breite haben, damit es auf größeren Bildschirmen nicht überdehnt wird.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Wenn du diese beiden zusammenfügst und eine weitere Methode erstellst, um das `FlexLayout` wie die vorherigen zentriert zu erhalten, sieht `MainView` mit den hervorgehobenen Änderungen so aus:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">

```java
@Route(value = "/", outlet = MainLayout.class)
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
```

</ExpandableCode>
<!-- vale on -->
