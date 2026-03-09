---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 9d594a84516af29dde3f66726bc22825
---
In webforJ wird jede Route innerhalb eines Frames gerendert, der als Container auf oberster Ebene fungiert und für die Anzeige des Inhalts der aktuellen Route verantwortlich ist. Während die Nutzer zwischen verschiedenen Routen navigieren, wird der Frame-Titel dynamisch aktualisiert, um die aktive Ansicht widerzuspiegeln, was hilft, den aktuellen Standort des Nutzers innerhalb der App klar zu vermitteln.

Der Titel eines Frames kann entweder statisch mit Annotations oder dynamisch zur Laufzeit durch Code festgelegt werden. Dieser flexible Ansatz ermöglicht es Entwicklern, Titel zu definieren, die mit dem Zweck jeder Ansicht übereinstimmen, während sie sich auch an spezifische Szenarien oder Parameter anpassen lassen.

## Frame-Titel mit Annotations {#frame-title-with-annotations}

Die einfachste Möglichkeit, den Titel eines Frames in der Ansicht festzulegen, besteht darin, die Annotation `@FrameTitle` zu verwenden. Diese Annotation ermöglicht es, einen statischen Titel für jede Routenkomponente zu definieren, der dann auf den Frame angewendet wird, wenn die Komponente gerendert wird.

### Verwendung der Annotation `@FrameTitle` {#using-the-frametitle-annotation}

Die Annotation `@FrameTitle` wird auf Klassenebene angewendet und ermöglicht es, einen String-Wert anzugeben, der den Titel der Seite darstellt. Wenn der Router zu einer Komponente mit dieser Annotation navigiert, wird der angegebene Titel automatisch für das Browserfenster festgelegt.

Hier ist ein Beispiel:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // Ansichtslogik
  }
}
```

In diesem Beispiel:
- Die Klasse `DashboardView` ist mit `@Route` annotiert, um die Route zu definieren.
- Die Annotation `@FrameTitle("Dashboard")` setzt den Titel des Frames auf "Dashboard".
- Wenn der Nutzer zu `/dashboard` navigiert, wird der Titel des Frames automatisch auf den angegebenen Wert aktualisiert.

Diese Methode ist nützlich für Routen, die einen statischen Titel haben und keine häufigen Aktualisierungen in Abhängigkeit vom Kontext der Route erfordern.

:::tip `@AppTitle` und `@FrameTitle`  
Wenn der App-Titel festgelegt ist, wird der Frame-Titel ihn einbeziehen. Zum Beispiel, wenn die App den Titel mit `@AppTitle("webforJ")` definiert und der Frame-Titel mit `@FrameTitle("Dashboard")` festgelegt ist, wird der endgültige Seitentitel `Dashboard - webforJ` sein. Sie können das Format des endgültigen Titels in der Annotation `@AppTitle` bei Bedarf mit dem Attribut `format` anpassen.  
:::

## Dynamische Frame-Titel {#dynamic-frame-titles}

In Fällen, in denen der Frame-Titel dynamisch geändert werden muss, basierend auf dem Zustand der App oder den Routenparametern, bietet webforJ ein Interface namens `HasFrameTitle`. Dieses Interface ermöglicht es Komponenten, einen Frame-Titel basierend auf dem aktuellen Navigationskontext und den Routenparametern bereitzustellen.

### Implementierung des Interfaces `HasFrameTitle` {#implementing-the-hasframetitle-interface}

Das Interface `HasFrameTitle` enthält eine einzelne Methode `getFrameTitle()`, die aufgerufen wird, bevor der Titel des Frames aktualisiert wird. Diese Methode bietet die Flexibilität, einen Titel dynamisch zu generieren, basierend auf dem Navigationskontext oder anderen dynamischen Faktoren.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profilseite"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Dynamisch den Frame-Titel mit den Routenparametern festlegen
    String userId = parameters.get("id").orElse("Unbekannt");
    return "Profil - Benutzer " + userId;
  }
}
```

In diesem Beispiel:
- Die Komponente `ProfileView` implementiert das Interface `HasFrameTitle`.
- Die Methode `getFrameTitle()` generiert dynamisch einen Titel unter Verwendung des `id`-Parameters aus der URL.
- Wenn die Route `/profile/123` ist, wird der Titel auf "Profil - Benutzer 123" aktualisiert.

:::tip Kombination von Annotations und dynamischen Titeln
Sie können sowohl statische als auch dynamische Methoden kombinieren. Wenn eine Routenkomponente sowohl eine `@FrameTitle`-Annotation hat als auch das Interface `HasFrameTitle` implementiert, hat der dynamisch bereitgestellte Titel aus `getFrameTitle()` Vorrang vor dem statischen Wert aus der Annotation.
:::
