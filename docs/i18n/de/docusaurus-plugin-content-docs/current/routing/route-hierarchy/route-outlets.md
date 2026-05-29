---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 8a64cd917fe9f1de3f37ee01254e80e7
---
Ein **Outlet** ist eine benannte Komponente, entweder ein [Routenlayout](./route-types#layout-routes) oder eine [Routenansicht](./route-types#view-routes), in der Kindrouten dynamisch gerendert werden. Es definiert, wo der Inhalt der Kindroute innerhalb der Elternroute angezeigt wird. Outlets sind grundlegend für die Erstellung modularer, geschachtelter UIs und flexibler Navigationsstrukturen.

## Definieren eines Outlets {#defining-an-outlet}

Outlets werden typischerweise mithilfe von Containerkomponenten implementiert, die Kindinhalte halten und verwalten können. In webforJ kann jede Komponente, die das `HasComponents`-Interface implementiert oder eine Komposition solcher Komponenten ist, als Outlet dienen. Zum Beispiel implementiert [`FlexLayout`](../../components/flex-layout) das `HasComponents`-Interface und ist somit ein gültiges Outlet für Kindrouten.

Wenn kein Outlet explizit für eine Route definiert ist, wird der erste `Frame` der Anwendung als Standard-Outlet verwendet. Dieses Verhalten stellt sicher, dass jede Kindroute einen Platz zum Rendern hat.

:::tip Rahmenverwaltung
In Anwendungen mit mehreren Rahmen können Sie angeben, welchen Rahmen Sie als Outlet für Kindrouten verwenden möchten, indem Sie das `frame`-Attribut in der `@Route`-Annotation festlegen. Das `frame`-Attribut akzeptiert den Namen des Rahmens, der zum Rendern verwendet werden soll.
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
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Dashboard-Inhalt"));
  }
}
```

In diesem Beispiel:

- `MainLayout` fungiert als Layout-Container, aber da kein spezifisches Outlet definiert ist, wird der Standard-`Frame` der App verwendet.
- Die `DashboardView` wird innerhalb von `MainLayout` unter Verwendung des Standard-Outlets (Inhaltsbereich) des `AppLayout` gerendert.

Somit werden Kindrouten von `MainLayout` automatisch im Inhaltsbereich des `AppLayout` gerendert, es sei denn, ein anderes Outlet oder ein anderer Rahmen wird angegeben.

## Lebenszyklus des Outlets {#outlet-lifecycle}

Outlets sind eng mit dem Lebenszyklus von Routen verbunden. Wenn sich die aktive Route ändert, aktualisiert das Outlet seinen Inhalt dynamisch, indem die entsprechende Kindkomponente eingefügt und nicht mehr benötigte Komponenten entfernt werden. Dies stellt sicher, dass nur die relevanten Ansichten zu einem bestimmten Zeitpunkt gerendert werden.

- **Erstellung**: Outlets werden initialisiert, bevor Kindkomponenten erstellt werden.
- **Inhaltsinjektion**: Wenn eine Kindroute übereinstimmt, wird ihre Komponente in das Outlet injiziert.
- **Aktualisierung**: Bei der Navigation zwischen Routen aktualisiert das Outlet seinen Inhalt, indem die neue Kindkomponente injiziert und veraltete Komponenten entfernt werden.

## Benutzerdefinierte Outlets {#custom-outlets}

Das `RouteOutlet`-Interface ist verantwortlich für die Verwaltung des Lebenszyklus von Routenkomponenten und bestimmt, wie Komponenten gerendert und entfernt werden. Jede Komponente, die dieses Interface implementiert, kann als Outlet für andere Komponenten fungieren.

### Wichtige Methoden im `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Verantwortlich für das Rendern der bereitgestellten Komponente im Outlet. Dies wird aufgerufen, wenn der Router eine Route übereinstimmt und die Kindkomponente angezeigt werden muss.
- **`removeRouteContent(Component component)`**: Behandelt das Entfernen der Komponente aus dem Outlet, typischerweise aufgerufen, wenn von der aktuellen Route navigiert wird.

Durch die Implementierung von `RouteOutlet` können Entwickler steuern, wie Routen in spezifische Bereiche der App injiziert werden. Zum Beispiel:

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  private final AppLayout self = getBoundComponent();

  @Override
  public void showRouteContent(Component component) {
    self.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    self.remove(component);
  }
}
```

In diesem Beispiel implementiert die Klasse `MainLayout` das `RouteOutlet`-Interface, sodass Komponenten dynamisch basierend auf der Routenavigation zum Drawer des `AppLayout` hinzugefügt oder entfernt werden, anstatt zum Standardinhaltsbereich, der in der `AppLayout`-Komponente definiert ist.

## Caching von Outlet-Komponenten {#caching-outlet-components}

Standardmäßig fügen Outlets bei der Navigation zu und von Routen dynamisch Komponenten hinzu und entfernen sie. In bestimmten Fällen — insbesondere bei Ansichten mit komplexen Komponenten — kann es jedoch besser sein, die Sichtbarkeit von Komponenten ein- und auszublenden, anstatt sie vollständig aus dem DOM zu entfernen. Hier kommt das `PersistentRouteOutlet` ins Spiel, das es ermöglicht, dass Komponenten im Speicher verbleiben und einfach ausgeblendet oder angezeigt werden, anstatt zerstört und neu erstellt zu werden.

Das `PersistentRouteOutlet` cached gerenderte Komponenten und hält sie im Speicher, wenn der Benutzer weg navigiert. Dies verbessert die Leistung, indem unnötige Zerstörung und Neuschöpfung von Komponenten vermieden wird, was besonders vorteilhaft für Anwendungen ist, in denen Benutzer häufig zwischen Ansichten wechseln.

### Wie `PersistentRouteOutlet` funktioniert: {#how-persistentrouteoutlet-works}

- **Komponenten-Caching**: Es hält einen im Speicher befindlichen Cache aller Komponenten, die innerhalb des Outlets gerendert wurden.
- **Sichtbarkeitsumschaltung**: Anstatt Komponenten aus dem DOM zu entfernen, werden sie beim Navigieren von einer Route ausgeblendet.
- **Wiederherstellung von Komponenten**: Wenn der Benutzer zu einer zuvor zwischengespeicherten Route zurückkehrt, wird die Komponente einfach wieder angezeigt, ohne neu erstellt zu werden.

Dieses Verhalten ist besonders nützlich für komplexe UIs, bei denen ständiges Neurendern von Komponenten die Leistung beeinträchtigen kann. Um diese Sichtbarkeitsumschaltung zu ermöglichen, müssen die verwalteten Komponenten das `HasVisibility`-Interface implementieren, das dem `PersistentRouteOutlet` die Kontrolle über ihre Sichtbarkeit erlaubt.

:::tip Wann man `PersistentRouteOutlet` verwenden sollte
Verwenden Sie `PersistentRouteOutlet`, wenn das häufige Erstellen und Zerstören von Komponenten in Ihrer App zu Leistungsengpässen führt. Es wird allgemein empfohlen, das Standardverhalten bei der Erstellung und Zerstörung von Komponenten während Routenübergängen zuzulassen, da dies potenzielle Fehler und Probleme im Zusammenhang mit der Aufrechterhaltung eines konsistenten Zustands hilft zu vermeiden. In Szenarien, in denen die Leistung entscheidend ist und die Komponenten komplex oder teuer zu recreatieren sind, kann `PersistentRouteOutlet` erhebliche Verbesserungen bieten, indem es Komponenten cached und deren Sichtbarkeit verwaltet.
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

In diesem Beispiel verwendet `MainLayout` `PersistentRouteOutlet`, um seine Kindrouten zu verwalten. Bei der Navigation zwischen Routen werden Komponenten nicht aus dem DOM entfernt, sondern versteckt, wodurch sichergestellt wird, dass sie für ein schnelles Neurendern zur Verfügung stehen, wenn der Benutzer zurück navigiert. Dieser Ansatz verbessert die Leistung erheblich, insbesondere bei Ansichten mit komplexen Inhalten oder hohem Ressourcenverbrauch.
