---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: cbd0aa0a56b47ee6270000fc326a7967
---
In webforJ werden alle Routen innerhalb eines Frames gerendert, der als oberste Containerstruktur dient und dafür verantwortlich ist, den Inhalt der aktuellen Route anzuzeigen. Wenn Benutzer zwischen verschiedenen Routen navigieren, wird der Frame-Titel dynamisch aktualisiert, um die aktive Ansicht widerzuspiegeln und so einen klaren Kontext über den aktuellen Standort des Benutzers innerhalb der App zu bieten.

Der Titel eines Frames kann entweder statisch mit Anmerkungen oder dynamisch zur Laufzeit über Code festgelegt werden. Dieser flexible Ansatz ermöglicht es Entwicklern, Titel zu definieren, die mit dem Zweck jeder Ansicht übereinstimmen, während sie sich auch an spezifische Szenarien oder Parameter anpassen können.

## Frame-Titel mit Anmerkungen {#frame-title-with-annotations}

Der einfachste Weg, den Titel eines Frames in einer Ansicht festzulegen, besteht darin, die Annotation `@FrameTitle` zu verwenden. Diese Annotation ermöglicht es, einen statischen Titel für jede Routenkomponente zu definieren, der dann angewendet wird, wenn die Komponente gerendert wird.

### Verwendung der Annotation `@FrameTitle` {#using-the-frametitle-annotation}

Die Annotation `@FrameTitle` wird auf Klassenebene angewendet und ermöglicht es Ihnen, einen string-Wert anzugeben, der den Titel der Seite darstellt. Wenn der Router zu einer Komponente mit dieser Annotation navigiert, wird der angegebene Titel automatisch für das Browserfenster festgelegt.

Hier ist ein Beispiel:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // Sichtlogik
  }
}
```

In diesem Beispiel:
- Die Klasse `DashboardView` ist mit `@Route` annotiert, um die Route zu definieren.
- Die Annotation `@FrameTitle("Dashboard")` setzt den Frame-Titel auf "Dashboard".
- Wenn der Benutzer zu `/dashboard` navigiert, wird der Titel des Frames automatisch auf den angegebenen Wert aktualisiert.

Diese Methode ist nützlich für Routen, die einen statischen Titel haben und keine häufigen Updates basierend auf dem Kontext der Route erfordern.

:::tip `@AppTitle` und `@FrameTitle`  
Wenn der App-Titel festgelegt ist, wird der Frame-Titel diesen einbeziehen. Wenn die App den Titel als `@AppTitle("webforJ")` definiert und der Frame-Titel als `@FrameTitle("Dashboard")` festgelegt ist, wird der endgültige Seitentitel `Dashboard - webforJ` sein. Sie können das Format des endgültigen Titels in der Annotation `@AppTitle` bei Bedarf über das Attribut `format` anpassen.  
:::

## Dynamische Frame-Titel {#dynamic-frame-titles}

In Fällen, in denen der Frame-Titel dynamisch basierend auf dem Zustand der App oder den Routenparametern geändert werden muss, bietet webforJ eine Schnittstelle namens `HasFrameTitle`. Diese Schnittstelle ermöglicht es Komponenten, einen Frame-Titel basierend auf dem aktuellen Navigationskontext und den Routenparametern bereitzustellen.

### Implementierung der Schnittstelle `HasFrameTitle` {#implementing-the-hasframetitle-interface}

Die Schnittstelle `HasFrameTitle` enthält eine einzige Methode `getFrameTitle()`, die aufgerufen wird, bevor der Titel des Frames aktualisiert wird. Diese Methode bietet die Flexibilität, einen Titel dynamisch basierend auf dem Navigationskontext oder anderen dynamischen Faktoren zu generieren.

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
- Die Methode `getFrameTitle()` generiert dynamisch einen Titel mit dem `id`-Parameter aus der URL.
- Wenn die Route `/profile/123` ist, wird der Titel auf "Profil - Benutzer 123" aktualisiert.

:::tip Kombination von Anmerkungen und dynamischen Titeln
Sie können sowohl statische als auch dynamische Methoden kombinieren. Wenn eine Routenkomponente sowohl eine `@FrameTitle`-Annotation hat als auch die Schnittstelle `HasFrameTitle` implementiert, hat der dynamisch bereitgestellte Titel aus `getFrameTitle()` Vorrang vor dem statischen Wert aus der Annotation.
:::
