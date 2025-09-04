---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 0a4e46f57c88d52966be27b35070a027
---
In webforJ werden alle Routen innerhalb eines Frames gerendert, der als oberster Container dient und dafür verantwortlich ist, den Inhalt der aktuellen Route anzuzeigen. Während die Benutzer zwischen verschiedenen Routen navigieren, wird der Frame-Titel dynamisch aktualisiert, um die aktive Ansicht widerzuspiegeln, was dabei hilft, den Benutzern einen klaren Kontext über ihren aktuellen Standort innerhalb der App zu bieten.

Der Titel eines Frames kann entweder statisch mithilfe von Annotations oder dynamisch zur Laufzeit per Code festgelegt werden. Dieser flexible Ansatz ermöglicht es Entwicklern, Titel zu definieren, die mit dem Zweck jeder Ansicht übereinstimmen, während sie sich auch an spezifische Szenarien oder Parameter anpassen lassen.

## Frame-Titel mit Annotations {#frame-title-with-annotations}

Der einfachste Weg, den Titel eines Frames in der Ansicht festzulegen, ist die Verwendung der `@FrameTitle`-Annotation. Diese Annotation ermöglicht es, einen statischen Titel für jede Routenkomponente zu definieren, der dann auf den Frame angewendet wird, wenn die Komponente gerendert wird.

### Verwendung der `@FrameTitle`-Annotation {#using-the-frametitle-annotation}

Die `@FrameTitle`-Annotation wird auf Klassenebene angewendet und ermöglicht es, einen Stringwert festzulegen, der den Titel der Seite darstellt. Wenn der Router zu einer Komponente mit dieser Annotation navigiert, wird der angegebene Titel automatisch für das Browserfenster festgelegt.

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
- Die `@FrameTitle("Dashboard")`-Annotation legt den Frame-Titel auf "Dashboard" fest.
- Wenn der Benutzer zu `/dashboard` navigiert, wird der Titel des Frames automatisch auf den angegebenen Wert aktualisiert.

Diese Methode ist nützlich für Routen, die einen statischen Titel haben und keine häufigen Aktualisierungen basierend auf dem Kontext der Route erfordern.

:::tip `@AppTitle` und `@FrameTitle`  
Wenn der App-Titel festgelegt ist, wird der Frame-Titel ihn einbeziehen. Wenn beispielsweise der App-Titel mit `@AppTitle("webforJ")` definiert ist und der Frame-Titel mit `@FrameTitle("Dashboard")` festgelegt ist, lautet der endgültige Seitentitel `Dashboard - webforJ`. Sie können das Format des endgültigen Titels in der `@AppTitle`-Annotation bei Bedarf mit dem `format`-Attribut anpassen.  
:::

## Dynamische Frame-Titel {#dynamic-frame-titles}

In Fällen, in denen sich der Frame-Titel dynamisch basierend auf dem Status der App oder den Routenparametern ändern muss, bietet webforJ eine Schnittstelle namens `HasFrameTitle`. Diese Schnittstelle ermöglicht es Komponenten, einen Frame-Titel basierend auf dem aktuellen Navigationskontext und den Routenparametern bereitzustellen.

### Implementierung der `HasFrameTitle`-Schnittstelle {#implementing-the-hasframetitle-interface}

Die `HasFrameTitle`-Schnittstelle enthält eine einzelne Methode `getFrameTitle()`, die aufgerufen wird, bevor der Titel des Frames aktualisiert wird. Diese Methode bietet die Flexibilität, einen Titel dynamisch basierend auf dem Navigationskontext oder anderen dynamischen Faktoren zu generieren.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {

  public ProfileView() {
    getBoundComponent().add(new H1("Profilseite"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Dynamisch den Frame-Titel anhand der Routenparameter festlegen
    String userId = parameters.get("id").orElse("Unbekannt");
    return "Profil - Benutzer " + userId;
  }
}
```

In diesem Beispiel:
- Die Komponente `ProfileView` implementiert die Schnittstelle `HasFrameTitle`.
- Die Methode `getFrameTitle()` generiert dynamisch einen Titel mithilfe des `id`-Parameters aus der URL.
- Wenn die Route `/profile/123` ist, wird der Titel auf "Profil - Benutzer 123" aktualisiert.

:::tip Kombination von Annotations und dynamischen Titeln
Sie können sowohl statische als auch dynamische Methoden kombinieren. Wenn eine Routenkomponente sowohl eine `@FrameTitle`-Annotation hat als auch die Schnittstelle `HasFrameTitle` implementiert, hat der dynamisch bereitgestellte Titel aus `getFrameTitle()` Vorrang vor dem statischen Wert aus der Annotation.
:::
