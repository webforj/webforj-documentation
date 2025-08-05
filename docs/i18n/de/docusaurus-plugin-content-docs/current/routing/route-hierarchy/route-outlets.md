---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 1871c92c77115c99444f1d7a0c20aed9
---
Ein **Outlet** ist eine bestimmte Komponente, entweder ein [Routenlayout](./route-types#layout-routes) oder eine [Routenansicht](./route-types#view-routes), in der untergeordnete Routen dynamisch gerendert werden. Es definiert, wo der Inhalt der untergeordneten Route innerhalb der Elter Route angezeigt wird. Outlets sind grundlegend für die Erstellung modularer, verschachtelter Benutzeroberflächen und flexibler Navigationsstrukturen.

## Definition eines Outlets {#defining-an-outlet}

Outlets werden typischerweise mithilfe von Containerkomponenten implementiert, die untergeordneten Inhalt halten und verwalten können. In webforJ kann jede Komponente, die die Schnittstelle `HasComponents` implementiert oder eine Kombination solcher Komponenten ist, als Outlet fungieren. Zum Beispiel implementiert [`FlexLayout`](../../components/flex-layout) die Schnittstelle `HasComponents`, was es zu einem gültigen Outlet für untergeordnete Routen macht.

Wenn kein Outlet ausdrücklich für eine Route definiert ist, wird der erste `Frame` der Anwendung als Standard-Outlet verwendet. Dieses Verhalten stellt sicher, dass jede untergeordnete Route einen Platz hat, um gerendert zu werden.

:::tip Frame-Verwaltung
In Anwendungen mit mehreren Frames können Sie angeben, welcher Frame als Outlet für untergeordnete Routen verwendet werden soll, indem Sie das Attribut `frame` in der `@Route`-Annotation festlegen. Das Attribut `frame` akzeptiert den Namen des Frames, der zum Rendern verwendet werden soll.
:::

### Beispiel: {#example}

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    getBoundComponent().add(new H1("Dashboard Inhalt"));
  }
}
```

In diesem Beispiel:

- `MainLayout` fungiert als Layout-Container, aber da kein spezifisches Outlet definiert ist, wird der Standard-`Frame` der App verwendet.
- Die `DashboardView` wird innerhalb von `MainLayout` mit dem Standard-Outlet (Inhaltsbereich) des `AppLayout` gerendert.

Somit werden untergeordnete Routen von `MainLayout` automatisch im Inhaltsslot von `AppLayout` gerendert, es sei denn, es wird ein anderes Outlet oder Frame angegeben.

## Lebenszyklus des Outlets {#outlet-lifecycle}

Outlets sind eng mit dem Lebenszyklus der Routen verbunden. Wenn sich die aktive Route ändert, aktualisiert das Outlet seinen Inhalt dynamisch, indem es die entsprechende untergeordnete Komponente injiziert und alle Komponenten entfernt, die nicht mehr benötigt werden. Dies stellt sicher, dass zu jedem Zeitpunkt nur die relevanten Ansichten gerendert werden.

- **Erstellung**: Outlets werden initialisiert, bevor untergeordnete Komponenten erstellt werden.
- **Inhaltseinfügung**: Wenn eine untergeordnete Route übereinstimmt, wird ihre Komponente in das Outlet eingefügt.
- **Aktualisierung**: Bei der Navigation zwischen Routen aktualisiert das Outlet seinen Inhalt, injiziert die neue untergeordnete Komponente und entfernt veraltete Komponenten.

## Benutzerdefinierte Outlets {#custom-outlets}

Die `RouteOutlet`-Schnittstelle ist verantwortlich für die Verwaltung des Lebenszyklus von Routenkomponenten, wodurch bestimmt wird, wie Komponenten gerendert und entfernt werden. Jede Komponente, die diese Schnittstelle implementiert, kann als Outlet für andere Komponenten fungieren.

### Wichtige Methoden in `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Verantwortlich für das Rendern der angegebenen Komponente im Outlet. Dies wird aufgerufen, wenn der Router eine Route übereinstimmt und die untergeordnete Komponente angezeigt werden muss.
- **`removeRouteContent(Component component)`**: Verantwortlich für das Entfernen der Komponente aus dem Outlet, typischerweise aufgerufen, wenn von der aktuellen Route navigiert wird.

Durch die Implementierung von `RouteOutlet` können Entwickler steuern, wie Routen in bestimmten Bereichen der App injiziert werden. Zum Beispiel

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {

  @Override
  public void showRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.remove(component);
  }
}
```

In diesem Beispiel implementiert die Klasse `MainLayout` die Schnittstelle `RouteOutlet`, die es ermöglicht, Komponenten dynamisch aus dem Drawer des AppLayouts basierend auf der Routenavigation hinzuzufügen oder zu entfernen, anstatt den standardmäßigen Inhaltsbereich des `AppLayout`-Komponenten zu verwenden.

## Caching von Outlet-Komponenten {#caching-outlet-components}

Standardmäßig fügen Outlets dynamisch Komponenten hinzu und entfernen sie, wenn sie zu und von Routen navigieren. In bestimmten Fällen – insbesondere bei Ansichten mit komplexen Komponenten – kann es jedoch vorzuziehen sein, die Sichtbarkeit von Komponenten ein- oder auszuschalten, anstatt sie vollständig aus dem DOM zu entfernen. Hier kommt der `PersistentRouteOutlet` ins Spiel, der es ermöglicht, dass Komponenten im Speicher bleiben und einfach ausgeblendet oder angezeigt werden, anstatt zerstört und neu erstellt zu werden.

Der `PersistentRouteOutlet` cached gerenderte Komponenten und hält sie im Speicher, wenn der Benutzer sich von einer Route entfernt. Dies verbessert die Leistung, indem unnötige Zerstörung und Neuschöpfung von Komponenten vermieden werden, was besonders vorteilhaft für Anwendungen ist, in denen Benutzer häufig zwischen Ansichten wechseln.

### Wie `PersistentRouteOutlet` funktioniert: {#how-persistentrouteoutlet-works}

- **Komponenten-Caching**: Es hält einen im Speicher befindlichen Cache aller Komponenten, die innerhalb des Outlets gerendert wurden.
- **Sichtbarkeitsumschaltung**: Anstatt Komponenten aus dem DOM zu entfernen, blendet es sie aus, wenn von einer Route navigiert wird.
- **Komponenten-Wiederherstellung**: Wenn der Benutzer zu einer zuvor zwischengespeicherten Route navigiert, wird die Komponente einfach erneut angezeigt, ohne sie neu erstellen zu müssen.

Dieses Verhalten ist besonders nützlich für komplexe Benutzeroberflächen, bei denen ständiges Neurendern von Komponenten die Leistung beeinträchtigen kann. Damit dieser Sichtbarkeitsumschaltvorgang funktioniert, müssen die verwalteten Komponenten die Schnittstelle `HasVisibility` implementieren, die es dem `PersistentRouteOutlet` ermöglicht, ihre Sichtbarkeit zu steuern.

:::tip Wann `PersistentRouteOutlet` verwenden
Verwenden Sie `PersistentRouteOutlet`, wenn das häufige Erstellen und Zerstören von Komponenten zu Leistungsengpässen in Ihrer App führt. Es wird im Allgemeinen empfohlen, das Standardverhalten des Erstellens und Zerstörens von Komponenten während Routenübergängen zuzulassen, um potenzielle Fehler und Probleme im Zusammenhang mit dem Erhalt des konsistenten Zustands zu vermeiden. In Szenarien, in denen die Leistung entscheidend ist und Komponenten komplex oder teuer zu reproduzieren sind, kann `PersistentRouteOutlet` jedoch erhebliche Verbesserungen bieten, indem es Komponenten speichert und ihre Sichtbarkeit verwaltet.
:::

### Beispiel für die Implementierung von `PersistentRouteOutlet`: {#example-of-persistentrouteoutlet-implementation}

```java
@Route
public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  PersistentRouteOutlet outlet = new PersistentRouteOutlet(this);

  public MainLayout() {
    setHeader();
    setDrawer();
  }

  @Override
  public void removeRouteContent(Component component) {
    outlet.removeRouteContent(component);
  }

  @Override
  public void showRouteContent(Component component) {
    outlet.showRouteContent(component);
  }
}
```

In diesem Beispiel verwendet `MainLayout` den `PersistentRouteOutlet`, um seine untergeordneten Routen zu verwalten. Bei der Navigation zwischen Routen werden Komponenten nicht aus dem DOM entfernt, sondern stattdessen ausgeblendet, sodass sie beim Zurücknavigieren schnell wieder gerendert werden können. Dieser Ansatz verbessert die Leistung erheblich, insbesondere für Ansichten mit komplexen Inhalten oder hohem Ressourcenverbrauch.
