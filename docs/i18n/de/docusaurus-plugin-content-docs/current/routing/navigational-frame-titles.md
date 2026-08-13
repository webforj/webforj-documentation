---
sidebar_position: 10
title: Navigational Frame Titles
description: >-
  Set browser frame titles per route with the @FrameTitle annotation or generate
  them dynamically using HasFrameTitle.
_i18n_hash: 7b190f89d8eeb58df6d8a25ce863cc5e
---
In webforJ werden alle Routen innerhalb eines Frames gerendert, der als oberste Containeranordnung dient und für die Anzeige des Inhalts der aktuellen Route verantwortlich ist. Wenn Benutzer zwischen verschiedenen Routen navigieren, wird der Titel des Frames dynamisch aktualisiert, um die aktive Ansicht widerzuspiegeln, was hilft, den Benutzer darüber zu informieren, wo er sich derzeit innerhalb der App befindet.

Der Titel eines Frames kann entweder statisch mithilfe von Anmerkungen oder dynamisch durch Code zur Laufzeit festgelegt werden. Dieser flexible Ansatz ermöglicht es Entwicklern, Titel zu definieren, die mit dem Zweck jeder Ansicht übereinstimmen, und sich gleichzeitig an spezifische Szenarien oder Parameter anzupassen, wie es erforderlich ist.

## Frame-Titel mit Anmerkungen {#frame-title-with-annotations}

Der einfachste Weg, den Titel eines Frames in der Ansicht festzulegen, besteht darin, die Anmerkung `@FrameTitle` zu verwenden. Diese Anmerkung ermöglicht es Ihnen, einen statischen Titel für jede Routenkomponente zu definieren, der dann auf den Frame angewendet wird, wenn die Komponente gerendert wird.

### Verwendung der `@FrameTitle`-Anmerkung {#using-the-frametitle-annotation}

Die `@FrameTitle`-Anmerkung wird auf Klassenebene angewendet und ermöglicht es Ihnen, einen Stringwert anzugeben, der den Titel der Seite darstellt. Wenn der Router zu einer Komponente mit dieser Anmerkung navigiert, wird der angegebene Titel automatisch für das Browserfenster festgelegt.

Hier ist ein Beispiel:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // Ansicht Logik
  }
}
```

In diesem Beispiel:
- Die Klasse `DashboardView` ist mit `@Route` annotiert, um die Route zu definieren.
- Die Anmerkung `@FrameTitle("Dashboard")` setzt den Frame-Titel auf "Dashboard".
- Wenn der Benutzer zu `/dashboard` navigiert, wird der Titel des Frames automatisch auf den angegebenen Wert aktualisiert.

Diese Methode ist nützlich für Routen, die einen statischen Titel haben und keine häufigen Aktualisierungen basierend auf dem Kontext der Route erfordern.

:::tip `@AppTitle` und `@FrameTitle`
Wenn der App-Titel festgelegt ist, wird der Frame-Titel diesen mit einbeziehen. Beispielsweise, wenn die App den Titel als `@AppTitle("webforJ")` definiert und der Frame-Titel als `@FrameTitle("Dashboard")` gesetzt ist, wird der endgültige Seitentitel `Dashboard - webforJ` sein. Sie können das Format des endgültigen Titels in der `@AppTitle`-Anmerkung mithilfe des `format`-Attributs anpassen, falls erforderlich.
:::

## Dynamische Frame-Titel {#dynamic-frame-titles}

In Fällen, in denen der Frame-Titel dynamisch geändert werden muss, basierend auf dem Status der App oder Routenparametern, bietet webforJ ein Interface namens `HasFrameTitle`. Dieses Interface ermöglicht es Komponenten, einen Frame-Titel basierend auf dem aktuellen Navigationskontext und den Routenparametern bereitzustellen.

### Implementierung des `HasFrameTitle`-Interfaces {#implementing-the-hasframetitle-interface}

Das `HasFrameTitle`-Interface enthält eine einzige Methode `getFrameTitle()`, die aufgerufen wird, bevor der Titel des Frames aktualisiert wird. Diese Methode bietet die Flexibilität, einen Titel dynamisch basierend auf dem Navigationskontext oder anderen dynamischen Faktoren zu generieren.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Profilseite"));
  }

  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Dynamisch den Frame-Titel mit Routenparametern festlegen
    String userId = parameters.get("id").orElse("Unbekannt");
    return "Profil - Benutzer " + userId;
  }
}
```

In diesem Beispiel:
- Die Komponente `ProfileView` implementiert das `HasFrameTitle`-Interface.
- Die Methode `getFrameTitle()` generiert dynamisch einen Titel unter Verwendung des `id`-Parameters aus der URL.
- Wenn die Route `/profile/123` ist, wird der Titel auf "Profil - Benutzer 123" aktualisiert.

:::tip Kombination von Anmerkungen und dynamischen Titeln
Sie können statische und dynamische Methoden kombinieren. Wenn eine Routenkomponente sowohl eine `@FrameTitle`-Anmerkung als auch das `HasFrameTitle`-Interface implementiert, hat der dynamisch bereitgestellte Titel aus `getFrameTitle()` Vorrang vor dem statischen Wert aus der Anmerkung.
:::
