---
sidebar_position: 3
title: Route Outlets
_i18n_hash: bab7ef02dabbb653741f7c8176913213
---
Ein **Outlet** ist eine festgelegte Komponente, entweder ein [Routenlayout](./route-types#layout-routes) oder eine [Routenansicht](./route-types#view-routes), in der untergeordnete Routen dynamisch gerendert werden. Es definiert, wo der Inhalt der untergeordneten Route innerhalb der übergeordneten Route angezeigt wird. Outlets sind grundlegend für die Erstellung modularer, geschachtelter UIs und flexibler Navigationsstrukturen.

## Definieren eines Outlets {#defining-an-outlet}

Outlets werden typischerweise durch Containerkomponenten implementiert, die untergeordneten Inhalt halten und verwalten können. In webforJ kann jede Komponente, die das Interface `HasComponents` implementiert oder aus einer Vielzahl solcher Komponenten besteht, als Outlet fungieren. Zum Beispiel implementiert [`FlexLayout`](../../components/flex-layout) das `HasComponents`-Interface, was es zu einem gültigen Outlet für untergeordnete Routen macht.

Wenn für eine Route kein Outlet explizit definiert ist, wird das erste `Frame` der App als Standard-Outlet verwendet. Dieses Verhalten stellt sicher, dass jede untergeordnete Route einen Platz zum Rendern hat.

:::tip Rahmenverwaltung
In Anwendungen mit mehreren Rahmen können Sie angeben, welcher Rahmen als Outlet für untergeordnete Routen verwendet werden soll, indem Sie das Attribut `frame` in der `@Route`-Annotation setzen. Das `frame`-Attribut akzeptiert den Namen des Rahmens, der für das Rendering verwendet werden soll.
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
    getBoundComponent().add(new H1("Dashboard-Inhalt"));
  }
}
```

In diesem Beispiel:

- `MainLayout` fungiert als Layoutcontainer, aber da kein spezifisches Outlet definiert ist, wird das Standard-`Frame` der App verwendet.
- Die `DashboardView` wird innerhalb von `MainLayout` über das Standard-Outlet (Inhaltsbereich) des `AppLayout` gerendert.

Somit werden untergeordnete Routen von `MainLayout` automatisch im Inhaltsplatz des `AppLayout` gerendert, es sei denn, ein anderes Outlet oder ein anderer Rahmen ist angegeben.

## Lebenszyklus des Outlets {#outlet-lifecycle}

Outlets sind eng mit dem Lebenszyklus von Routen verbunden. Wenn sich die aktive Route ändert, aktualisiert das Outlet seinen Inhalt dynamisch, indem es die entsprechende untergeordnete Komponente einfügt und alle nicht mehr benötigten Komponenten entfernt. Dies gewährleistet, dass nur die relevanten Ansichten zu einem bestimmten Zeitpunkt gerendert werden.

- **Erstellung**: Outlets werden initialisiert, bevor untergeordnete Komponenten erstellt werden.
- **Inhaltseinfügung**: Wenn eine untergeordnete Route übereinstimmt, wird ihre Komponente in das Outlet eingefügt.
- **Aktualisierung**: Bei der Navigation zwischen Routen aktualisiert das Outlet seinen Inhalt, indem es die neue untergeordnete Komponente einfügt und abgelaufene Komponenten entfernt.

## Benutzerdefinierte Outlets {#custom-outlets}

Das `RouteOutlet`-Interface ist verantwortlich für die Verwaltung des Lebenszyklus von Routenkomponenten und bestimmt, wie Komponenten gerendert und entfernt werden. Jede Komponente, die dieses Interface implementiert, kann als Outlet für andere Komponenten fungieren.

### Schlüsselmethoden im `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Verantwortlich für das Rendern der bereitgestellten Komponente im Outlet. Dies wird aufgerufen, wenn der Router eine Route abgleicht und die untergeordnete Komponente angezeigt werden muss.
- **`removeRouteContent(Component component)`**: Behandelt das Entfernen der Komponente aus dem Outlet, typischerweise aufgerufen, wenn von der aktuellen Route navigiert wird.

Durch die Implementierung von `RouteOutlet` können Entwickler steuern, wie Routen in spezifische Bereiche der App injiziert werden. Zum Beispiel:

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

In diesem Beispiel implementiert die Klasse `MainLayout` das `RouteOutlet`-Interface und ermöglicht es, Komponenten dynamisch zum Drawer des `AppLayout` hinzuzufügen oder zu entfernen, basierend auf der Routen-Navigation, anstatt auf den Standard-Inhaltsbereich, der im `AppLayout` definiert ist.

## Caching von Outlet-Komponenten {#caching-outlet-components}

Standardmäßig fügen Outlets dynamisch Komponenten hinzu und entfernen sie, wenn sie zu und von Routen navigieren. In bestimmten Fällen - insbesondere für Ansichten mit komplexen Komponenten - kann es jedoch vorteilhaft sein, die Sichtbarkeit von Komponenten umzuschalten, anstatt sie vollständig aus dem DOM zu entfernen. Hier kommt das `PersistentRouteOutlet` ins Spiel, das es ermöglicht, Komponenten im Speicher zu behalten und sie einfach auszublenden oder anzuzeigen, anstatt sie zu zerstören und neu zu erstellen.

Das `PersistentRouteOutlet` speichert gerenderte Komponenten im Cache und behält sie im Speicher, wenn der Benutzer zu einer anderen Route navigiert. Dies verbessert die Leistung, indem unnötige Zerstörung und Neuerstellung von Komponenten vermieden wird, was besonders vorteilhaft für Anwendungen ist, in denen Benutzer häufig zwischen Ansichten wechseln.

### Wie `PersistentRouteOutlet` funktioniert: {#how-persistentrouteoutlet-works}

- **Komponenten-Caching**: Es hält einen In-Memory-Cache aller Komponenten, die im Outlet gerendert wurden.
- **Sichtbarkeitsumschaltung**: Anstatt Komponenten aus dem DOM zu entfernen, wird deren Sichtbarkeit beim Navigieren von einer Route verborgen.
- **Wiederherstellung der Komponenten**: Wenn der Benutzer zu einer zuvor zwischengespeicherten Route zurücknavigiert, wird die Komponente einfach wieder angezeigt, ohne dass eine Neuerstellung erforderlich ist.

Dieses Verhalten ist besonders nützlich für komplexe UIs, bei denen ständiges Neurendern der Komponenten die Leistung beeinträchtigen kann. Um jedoch diese Sichtbarkeitsumschaltung zu ermöglichen, müssen die verwalteten Komponenten das `HasVisibility`-Interface implementieren, das dem `PersistentRouteOutlet` ermöglicht, ihre Sichtbarkeit zu steuern.

:::tip Wann `PersistentRouteOutlet` verwenden
Verwenden Sie `PersistentRouteOutlet`, wenn häufige Erstellung und Zerstörung von Komponenten in Ihrer App zu Leistungsengpässen führen. Es wird allgemein empfohlen, das Standardverhalten zuzulassen, bei dem Komponenten während der Routenübergänge erstellt und zerstört werden, da dies hilft, potenzielle Fehler und Probleme im Zusammenhang mit der Aufrechterhaltung eines konsistenten Status zu vermeiden. In Szenarien, in denen die Leistung kritisch ist und Komponenten komplex oder teuer zu reproduzieren sind, kann `PersistentRouteOutlet` jedoch erhebliche Verbesserungen bieten, indem es Komponenten speichert und ihre Sichtbarkeit verwaltet.
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

In diesem Beispiel verwendet `MainLayout` `PersistentRouteOutlet`, um seine untergeordneten Routen zu verwalten. Bei der Navigation zwischen Routen werden Komponenten nicht aus dem DOM entfernt, sondern nur ausgeblendet, wodurch sichergestellt wird, dass sie für ein schnelles Neurendern verfügbar bleiben, wenn der Benutzer zurück navigiert. Dieser Ansatz verbessert die Leistung erheblich, insbesondere für Ansichten mit komplexen Inhalten oder hohem Ressourcenverbrauch.
