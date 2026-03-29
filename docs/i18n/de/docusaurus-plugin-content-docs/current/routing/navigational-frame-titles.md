---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 7a3b1c6780e7032040802a936bfb83fb
---
In webforJ werden alle Routen innerhalb eines Frames gerendert, der als oberster Container fungiert und dafür verantwortlich ist, den Inhalt der aktuellen Route anzuzeigen. Wenn Benutzer zwischen verschiedenen Routen navigieren, wird der Titel des Frames dynamisch aktualisiert, um die aktive Ansicht widerzuspiegeln, was hilft, den aktuellen Standort des Benutzers innerhalb der App klar zu kennzeichnen.

Der Titel eines Frames kann entweder statisch über Annotations oder dynamisch zur Laufzeit über Code festgelegt werden. Dieser flexible Ansatz ermöglicht es Entwicklern, Titel zu definieren, die mit dem Zweck jeder Ansicht übereinstimmen, während sie sich auch an spezifische Szenarien oder Parameter anpassen können.

## Frame-Titel mit Annotations {#frame-title-with-annotations}

Die einfachste Möglichkeit, den Titel eines Frames in der Ansicht festzulegen, besteht darin, die `@FrameTitle` Annotation zu verwenden. Mit dieser Annotation können Sie einen statischen Titel für jede Routenkomponente definieren, der dann auf den Frame angewendet wird, wenn die Komponente gerendert wird.

### Verwendung der `@FrameTitle` Annotation {#using-the-frametitle-annotation}

Die `@FrameTitle` Annotation wird auf Klassenebene angewendet und ermöglicht es Ihnen, einen String-Wert anzugeben, der den Titel der Seite darstellt. Wenn der Router zu einer Komponente mit dieser Annotation navigiert, wird der angegebene Titel automatisch für das Browserfenster festgelegt.

Hier ist ein Beispiel:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // view logic
  }
}
```

In diesem Beispiel:
- Die `DashboardView` Klasse ist mit `@Route` annotiert, um die Route zu definieren.
- Die `@FrameTitle("Dashboard")` Annotation setzt den Titel des Frames auf "Dashboard".
- Wenn der Benutzer zu `/dashboard` navigiert, wird der Titel des Frames automatisch auf den angegebenen Wert aktualisiert.

Diese Methode ist nützlich für Routen, die einen statischen Titel haben und keine häufigen Updates basierend auf dem Kontext der Route erfordern.

:::tip `@AppTitle` und `@FrameTitle`  
Wenn der App-Titel festgelegt ist, wird der Frame-Titel ihn einbeziehen. Wenn die App beispielsweise den Titel als `@AppTitle("webforJ")` definiert hat und der Frame-Titel als `@FrameTitle("Dashboard")` festgelegt ist, wird der endgültige Seitentitel `Dashboard - webforJ` sein. Sie können das Format des endgültigen Titels in der `@AppTitle` Annotation bei Bedarf mit dem `format` Attribut anpassen.  
:::

## Dynamische Frame-Titel {#dynamic-frame-titles}

In Fällen, in denen sich der Titel des Frames dynamisch ändern muss, basierend auf dem Zustand der App oder Routenparametern, bietet webforJ eine Schnittstelle namens `HasFrameTitle`. Diese Schnittstelle ermöglicht es Komponenten, einen Frame-Titel basierend auf dem aktuellen Navigationskontext und den Routenparametern bereitzustellen.

### Implementierung der `HasFrameTitle` Schnittstelle {#implementing-the-hasframetitle-interface}

Die `HasFrameTitle` Schnittstelle enthält eine einzelne Methode `getFrameTitle()`, die aufgerufen wird, bevor der Titel des Frames aktualisiert wird. Diese Methode bietet die Flexibilität, einen Titel dynamisch basierend auf dem Navigationskontext oder anderen dynamischen Faktoren zu generieren.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Profilseite"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Dynamisch den Frame-Titel unter Verwendung von Routenparametern festlegen
    String userId = parameters.get("id").orElse("Unbekannt");
    return "Profil - Benutzer " + userId;
  }
}
```

In diesem Beispiel:
- Die `ProfileView` Komponente implementiert die `HasFrameTitle` Schnittstelle.
- Die `getFrameTitle()` Methode generiert dynamisch einen Titel unter Verwendung des `id` Parameters aus der URL.
- Wenn die Route `/profile/123` ist, wird der Titel auf "Profil - Benutzer 123" aktualisiert.

:::tip Kombination von Annotations und dynamischen Titeln
Sie können sowohl statische als auch dynamische Methoden kombinieren. Wenn eine Routenkomponente sowohl eine `@FrameTitle` Annotation hat als auch die `HasFrameTitle` Schnittstelle implementiert, hat der dynamisch bereitgestellte Titel aus `getFrameTitle()` Vorrang vor dem statischen Wert aus der Annotation.
:::
