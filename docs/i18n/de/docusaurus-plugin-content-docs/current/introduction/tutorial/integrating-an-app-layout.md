---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
sidebar_class_name: new-content
_i18n_hash: 66c364d83c3b6d574acaca5156bbb018
---
In diesem Schritt werden Sie alle Teile Ihrer App in ein kohärentes App-Layout zusammenführen. Am Ende dieses Schrittes wird die Struktur Ihrer App dem [SideMenu-Archetyp](/docs/building-ui/archetypes/sidemenu) sehr ähnlich sein, und Sie werden ein besseres Verständnis dafür haben, wie die folgenden Komponenten und Konzepte funktionieren:

- [`FlexLayout`](/docs/components/flex-layout)
- [Route-Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die Datei `pom.xml` enthält, dies ist `6-integrating-an-app-layout`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Erstellen einer wiederverwendbaren Komponente {#creating-a-reusable-component}

In einem vorherigen Schritt, [Routing und Composites](/docs/introduction/tutorial/routing-and-composites), haben Sie zwei Composite-Komponenten erstellt, die den Inhalt der Kundentabelle und das Kundenformular enthalten. Im Rahmen dieses Schrittes werden Sie eine kleinere, wiederverwendbare Composite-Komponente erstellen, um den Namen der App im Seitenmenü und auf einer "Über"-Seite anzuzeigen. Wenn Sie den Namen der App in Zukunft ändern möchten, müssen Sie ihn nur in dieser Komponente aktualisieren.

In `src/main/java/com/webforj/tutorial/components` erstellen Sie eine Klasse namens `AppTitle`. Die gebundene Komponente für `AppTitle` wird ein `FlexLayout` sein, eine Container-Komponente, die in diesem Schritt verwendet wird, um Ihnen zu zeigen, wie man komplexere Layouts erstellt. Für dieses `FlexLayout` gestalten Sie die Ausrichtung der Elemente und den Abstand zwischen den Elementen. Das geschieht durch die Verwendung der Methoden `setDirection()` und `setSpacing()`.

```java title='AppTitle.java'
// Machen Sie die gebundene Komponente ein FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  
  public AppTitle() {

    // Ordnen Sie die Elemente vertikal an
    self.setDirection(FlexDirection.COLUMN);

    // Setzen Sie den Abstand zwischen den Elementen
    self.setSpacing("0px");
  }
}
```

Verwenden Sie dann Standard-HTML-Elemente, um den Titel und den Untertitel zu erstellen. Das Setzen des unteren Abstands eines Header-Elements auf `0px` bringt die Elemente näher zusammen, und Sie können den Untertitel mithilfe von [DWC-CSS-Variablen](/docs/styling/css-variables) stylen.

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

Obwohl `AppTitle` einfach ist, ermöglicht das Hinzufügen eines booleschen Arguments zur Konstruktormethode, zu steuern, wann bestimmte Teile der Komponente, wie der Untertitel, gerendert werden.

```java title='AppTitle.java'
// Fügen Sie ein boolesches Argument hinzu
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Fügen Sie standardmäßig den Titel hinzu
      .add(title);
  
  // Optional den Untertitel anzeigen
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Abgeschlossene `AppTitle` {#completed-app-title}

Alles zusammen sollte die wiederverwendbare Komponente wie folgt aussehen:

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

## Erstellen einer "Über"-Seite {#creating-an-about-page}

Der erste Ort, an dem Sie die neu erstellte `AppTitle`-Komponente hinzufügen, wird eine "Über"-Seite sein. Diese Seite enthält ein Bild und die `AppTitle`-Komponente, die mittels eines weiteren `FlexLayout` zentriert auf der Seite angeordnet ist.

### Inhalte mit einem `FlexLayout` zentrieren {#centering-content-using-a-flexlayout}

Ziel ist es, den Inhalt der "Über"-Seite mit dem `FlexLayout` zu zentrieren. Die `FlexLayout`-Komponente folgt dem [CSS Flexbox-Layoutmodell](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Methoden für das `FlexLayout`, wie die zuvor verwendeten, um die Elemente in einer Spalte anzuordnen, sind verschiedene Möglichkeiten, die Elemente zu ordnen.

Die Methoden zur Anordnung von Elementen in einem `FlexLayout` verwenden ein relatives Richtungssystem. Anstatt über die horizontalen und vertikalen Achsen nachzudenken, ist es besser, die Achse parallel zu den Elementen als Hauptachse und die Achse senkrecht zu den Elementen als Queraachse zu betrachten.

Das Setzen sowohl der `FlexJustifyContent`- als auch der `FlexAlignment`-Eigenschaften auf `CENTER` wird die Elemente entlang sowohl der Haupt- als auch der Queraachsen im `FlexLayout` zentrieren, und das Erstellen des `FlexLayout`, das den gesamten Raum seines übergeordneten Containers einnimmt, macht es zentriert auf der Seite.

```java
private final FlexLayout layout = new FlexLayout();

// Füllen Sie den gesamten Raum des übergeordneten Elements aus
layout.setSize("100%", "100%");

// Machen Sie die Hauptachse vertikal
layout.setDirection(FlexDirection.COLUMN);

// Zentrieren Sie die Elemente entlang der Queraachse
layout.setAlignment(FlexAlignment.CENTER);

// Zentrieren Sie die Elemente entlang der Hauptachse
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Um zu veranschaulichen, wie die verschiedenen Methoden wirken, werfen Sie einen Blick auf den Blogbeitrag [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Ressourcen hinzufügen {#adding-resources}

Eines der Elemente, das in das zentrierte `FlexLayout` eingefügt wird, ist ein Bild. Für dieses Tutorial können Sie das [Bild der "Über"-Seite](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) auf GitHub ansehen und herunterladen. Nachdem Sie es heruntergeladen haben, fügen Sie es in den statischen Ordner Ihres Projekts in `src/main/resources/static/images` ein und benennen Sie es `Files.svg`.

Das Platzieren dieses Bildes im statischen Ordner ermöglicht es Ihnen, es mithilfe des Webserver-Protokolls zu referenzieren, so wie Sie es getan haben, als Sie die CSS-Datei im ersten Schritt, [Erstellen einer Grundlegenden App](/docs/introduction/tutorial/creating-a-basic-app) referenzieren. Dann können Sie es in Ihrer App als HTML-Element so verwenden:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### Erstellen von `AboutView` {#creating-about-view}

Wie die beiden bestehenden App-Seiten wird die "Über"-Seite eine routbare Ansicht sein. In `src/main/java/com/webforj/tutorial/views` fügen Sie eine Klasse namens `AboutView` hinzu. Verwenden Sie ein `FlexLayout` für die gebundene Komponente, wie Sie es für `AppTitle` getan haben.

Da Sie die Klasse `AboutView` genannt haben, ist es nicht nötig, einen benutzerdefinierten Wert für die URL-Zuordnung anzugeben; diese Seite wird standardmäßig unter `http://localhost:8080/about` gerendert.

So sieht es aus, wenn Sie die Konzepte aus den vorherigen Schritten mit den neu erstellten Komponenten verwenden, um eine neue Ansicht mit zentrierten Inhalten zu erstellen:

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

## Erstellen der `Layout`-Route {#creating-the-layout-route}

Es wird kurz im Schritt [Routing und Composites](/docs/introduction/tutorial/routing-and-composites) erwähnt, aber es gibt zwei [Routentypen](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` und `AboutView` sind alles `View`-Routen, während der Routentyp, den Sie verwenden werden, um das Seitenmenü der App zu erstellen, eine `Layout`-Route ist.

Layout-Routen umhüllen untergeordnete Ansichten und ermöglichen es, dass bestimmte UI-Teile über Ansichten hinweg bestehen bleiben, wie ein Seitenmenü. In `src/main/java/com/webforj/tutorial/layouts` erstellen Sie eine Klasse namens `MainLayout`.

### Route-Outlets {#route-outlets}

Wie die View-Routen benötigt `MainLayout` eine `@Route`-Annotation. Da es jedoch als Suffix `Layout` hat und Layout-Routen nicht zur URL beitragen, benötigt diese Annotation keine Argumente.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

Die App weiß, welche Ansichten innerhalb von `MainLayout` gerendert werden sollen, indem die Layout-Klasse in jeder Ansicht als [Route-Outlet](/docs/routing/route-hierarchy/route-outlets) deklariert wird. Die vorherigen Schritte haben nur eine `value`-Eigenschaft in den `@Route`-Annotationen gesetzt, sodass Sie jetzt explizit angeben müssen, was die `value`- und `outlet`-Eigenschaften für die Ansichtsklassen sind.

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

:::note Schlussanpassungen
Dies ist die letzte Änderung, die für `FormView` und `AboutView` in diesem Schritt erforderlich ist. Vergessen Sie nicht, die `@Route`-Annotation für diese Ansichten vor dem Ausführen Ihrer App zu aktualisieren.
:::

## Verwendung der `AppLayout`-Komponente {#using-the-app-layout-component}

Jetzt, da Ihre App die Ansichten innerhalb von `MainLayout` rendert, können Sie wählen, wo diese Komponenten gerendert werden. Wenn Sie `AppLayout` als gebundene Komponente für `MainLayout` wählen, können Sie die Ansichten standardmäßig in einem Hauptinhaltbereich speichern, während Sie gleichzeitig verschiedene Bereiche haben, um Elemente für die Kopfzeile und das Seitenmenü hinzuzufügen.

### Slots {#slots}

Für viele WebforJ-Container fügt die Verwendung der `add()`-Methoden UI-Komponenten im Hauptinhaltbereich hinzu. Im `AppLayout`-Komponente gibt es mehrere Bereiche zum Hinzufügen von UI-Komponenten, jeweils in einem separaten Slot. Indem Sie `MainLayout` als Layout-Route markieren und seine gebundene Komponente als `AppLayout` festlegen, werden Ansichten automatisch im Hauptinhaltsslot gerendert.

In diesem Schritt werden Sie die `drawer-title`- und `drawer`-Slots verwenden, um ein Seitenmenü zu erstellen, und den `header`-Slot, um anzuzeigen, auf welcher Seite sich der Benutzer befindet und um einen Umschalter für das Seitenmenü bereitzustellen.

### Erstellen eines Seitenmenüs {#making-a-side-menu}

Wenn auf dem Gerät genügend Bildschirmraum vorhanden ist, zeigt die `AppLayout`-Komponente eine Schublade an. Hier fügen Sie die `AppTitle` erneut hinzu sowie Elemente, die es den Benutzern ermöglichen, in der App zu navigieren.

Standardmäßig zeigt `AppLayout` keine Drawer-Header an, aber mit der Methode `setDrawerHeaderVisible()` können Sie Elemente, die im `drawer-title`-Slot enthalten sind, anzeigen, was die `AppTitle` mit ihrem Untertitel ist.

```java
private AppLayout appLayout = new AppLayout();

// Zeigen Sie den Drawer-Header an
appLayout.setDrawerHeaderVisible(true);

// Fügen Sie die AppTitle zum Drawer-Header mit ihrem Untertitel hinzu
appLayout.addToDrawerTitle(new AppTitle(true));
```

Der `drawer`-Slot sollte dann die Komponenten enthalten, die es den Benutzern ermöglichen, in der App zu navigieren. Mit der [`AppNav`](/docs/components/appnav)-Komponente ist es einfach, neue Navigationsoptionen zu erstellen. Für jeden Link müssen Sie lediglich ein `AppNavItem` erstellen. Die `AppNavItem`-Komponenten in diesem Tutorial verwenden drei Parameter:

- Das Label für den Link
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

### Erstellen einer Kopfzeile {#making-a-header}

Der `header`-Slot sollte zwei Elemente umfassen: einen Umschalter, um das Seitenmenü anzuzeigen oder zu verbergen, und eine Möglichkeit, den Rahmentitel anzuzeigen. Beide Elemente werden innerhalb einer [Toolbar](/docs/components/toolbar)-Komponente hinzugefügt, einer weiteren Möglichkeit zur Organisation von Komponenten.

Sie können den Umschalter für die `AppLayout`-Schublade mit der `AppDrawerToggle`-Komponente einfügen. Diese Komponente ist bereits mit einem gängigen Symbol für versteckte Menüoptionen gestylt und zielt auf die Schublade ab, um sie zu öffnen und zu schließen.

```java
// Erstellen Sie die Container-Komponenten
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Fügen Sie die Toolbar zur Kopfzeile des AppLayout hinzu
appLayout.addToHeader(toolbar);

// Fügen Sie den AppDrawerToggle zur Toolbar hinzu
toolbar.addToStart(new AppDrawerToggle());
```

Die Kopfzeile kann auch den Rahmentitel anzeigen, indem das Navigationsevent verwendet wird, um Informationen über die ankommende Komponente abzurufen, während ein Ereignislistener hinzugefügt wird, um die Registrierung zu entfernen und so Gedächtnislecks zu verhindern.

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

// Abrufen des Rahmentitels aus der ankommenden Ansichtsklasse
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Abgeschlossenes `MainLayout`

Hier ist `MainLayout` mit erstelltem Inhalt für die Schublade und die Kopfzeile innerhalb eines `AppLayout`:

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

Für `MainView` werden Sie die gebundene Komponente von einem `Div` in ein `FlexLayout` ändern. Dies ermöglicht es Ihnen, die Tabelle zu zentrieren, während Sie gleichzeitig spezifische Komponenten innerhalb des Layouts verschieben. Mit der Methode `setItemAlignment()` können Sie eine Komponente im Layout auswählen und verschieben, sodass Sie die Tabelle zentriert halten können, während Sie die Schaltfläche "Kunden hinzufügen" in die obere rechte Ecke des Layouts verankern.

```java
// Ändern Sie die gebundene Komponente in ein FlexLayout
private FlexLayout self = getBoundComponent();

// Richten Sie die Schaltfläche an das Ende der Queraachse aus
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Eine weitere Verbesserung, die Sie hier vornehmen können, besteht in der Breite der Tabelle. Anstatt eine feste Breite festzulegen, können Sie sie so einstellen, dass sie mit ihrem übergeordneten Container, dem `FlexLayout`, übereinstimmt. Dann kann dieses `FlexLayout` eine maximale Breite haben, sodass es auf größeren Bildschirmen nicht überdehnt wird.

```java 
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Wenn Sie diese Punkte zusammenfassen und eine weitere Methode erstellen, um das `FlexLayout` wie die vorherigen zu zentrieren, sieht `MainView` mit den hervorgehobenen Änderungen so aus:

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
