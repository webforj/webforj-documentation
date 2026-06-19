---
title: Page Visibility
sidebar_position: 32
sidebar_class_name: new-content
description: >-
  Detect when the tab hosting your app moves between the foreground and the
  background, and react in Java.
_i18n_hash: 8382d0314f6143663c03e11409de08d5
---
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/Page" top='true'/>

Die Klasse `Page` kann erkennen, wenn der Benutzer den Tab, der Ihre App hostet, gewechselt, das Fenster minimiert oder zurückgekehrt ist. Verwenden Sie sie, um Polling und Animationen anzuhalten, wenn niemand zusieht, Benachrichtigungen zu steuern oder veraltete Daten zu aktualisieren, wenn der Tab wieder in den Fokus gelangt.

Die API hat zwei Komponenten:

- Eine typisierte Abfrage, `getVisibilityState()`, die den aktuellen Zustand zurückgibt.
- Einen Listener, `addVisibilityChangeListener(...)`, der jedes Mal ausgelöst wird, wenn sich der Zustand ändert.

## Sichtbarkeitszustände {#visibility-states}

`PageVisibilityState` hat zwei Werte:

| Zustand | Bedeutung |
| --- | --- |
| `VISIBLE` | Der Seiteninhalt ist mindestens teilweise sichtbar. Der Tab befindet sich im Vordergrund eines nicht minimierten Fensters. |
| `HIDDEN` | Der Seiteninhalt ist für den Benutzer nicht sichtbar. Der Tab befindet sich im Hintergrund, das Fenster ist minimiert, der Bildschirm ist gesperrt oder das Betriebssystem zeigt einen Bildschirmschoner an. |

## Den aktuellen Zustand lesen {#reading-the-current-state}

`Page.getVisibilityState()` gibt ein `PendingResult<PageVisibilityState>` zurück, das mit dem aktuellen Zustand aufgelöst wird.

```java
Page.getCurrent().getVisibilityState().thenAccept(state -> {
  if (state == PageVisibilityState.VISIBLE) {
    // Benutzer sieht den Tab
  }
});
```

Rufen Sie es auf, wenn Sie eine einmalige Antwort benötigen, z. B. wenn ein geplanter Task aufwacht. Für fortlaufende Reaktionen registrieren Sie stattdessen einen Listener.

## Auf Änderungen hören {#listening-for-changes}

`addVisibilityChangeListener(...)` registriert einen Listener, der benachrichtigt wird, jedes Mal, wenn sich der Sichtbarkeitszustand ändert. Das passende Alias ist `onVisibilityChange(...)`.

```java
ListenerRegistration<PageVisibilityChangeEvent> registration =
    Page.getCurrent().onVisibilityChange(event -> {
      if (event.isHidden()) {
        pauseRendering();
      } else {
        resumeRendering();
      }
    });
```

Das Ereignis enthält den neuen Zustand und einige Komfort-Accessor:

| Methode | Gibt zurück |
| --- | --- |
| `getState()` | Der neue `PageVisibilityState`. |
| `isVisible()` | `true`, wenn der neue Zustand `VISIBLE` ist. |
| `isHidden()` | `true`, wenn der neue Zustand `HIDDEN` ist. |
| `getPage()` | Die `Page`, die das Ereignis erzeugt hat. |

Entfernen Sie einen einzelnen Listener mit der zurückgegebenen `ListenerRegistration`.

## Beispiel: Nur benachrichtigen, wenn der Tab versteckt ist {#example-notify-when-hidden}

Ein gängiger Anwendungsfall ist die Auswahl des Zust channel basierend darauf, ob der Benutzer derzeit den Tab ansieht. Der folgende Code plant eine Benachrichtigung in fünf Sekunden. Wenn der Tab versteckt ist, wenn der Timer auslöst, wird eine Desktop-Benachrichtigung angezeigt und ein Badge auf dem Favicon angezeigt. Wenn der Tab sichtbar ist, wird ein in-App-Toast angezeigt.

```java
@Route("/")
public class NotifyView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Button notifyButton = new Button("Benachrichtigung in 5 Sekunden");
  private final Debouncer schedule = new Debouncer(5.0f);

  private ListenerRegistration<PageVisibilityChangeEvent> visibilityRegistration;
  private DesktopNotification activeNotification;

  public NotifyView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER);

    H1 title = new H1("Seiten-Sichtbarkeit-Demo");
    Paragraph hint = new Paragraph(
        "Klicken Sie auf die Schaltfläche und wechseln Sie dann zu einem anderen Tab, bevor der Timer abläuft.");

    notifyButton.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .onClick(e -> schedule.run(this::deliver));

    self.add(title, hint, notifyButton);

    visibilityRegistration = Page.getCurrent().onVisibilityChange(this::onVisibility);
  }

  private void deliver() {
    Page page = Page.getCurrent();
    page.getVisibilityState().thenAccept(state -> {
      if (state == PageVisibilityState.HIDDEN) {
        page.setIconBadge(1);
        activeNotification = new DesktopNotification("Seiten-Sichtbarkeit-Demo",
            "Der Timer hat ausgelöst, während der Tab versteckt war.");
        activeNotification.open();
      } else {
        Toast.show("Timer hat ausgelöst, während der Tab sichtbar war.", Theme.SUCCESS);
      }
    });
  }

  private void onVisibility(PageVisibilityChangeEvent event) {
    if (event.isVisible() && activeNotification != null) {
      Page.getCurrent().setIconBadge(0);
      activeNotification.close();
      activeNotification = null;
    }
  }

  @Override
  protected void onDidDestroy() {
    schedule.cancel();
    if (visibilityRegistration != null) {
      visibilityRegistration.remove();
    }
  }
}
```

Der Sichtbarkeit-Listener entfernt das Favicon-Badge und schließt die Desktop-Benachrichtigung, wenn der Benutzer zum Tab zurückkehrt.

## Wann man es verwenden sollte {#when-to-use-it}

- **Hintergrundarbeit pausieren.** Stoppen Sie Polling, Intervalle und Animationen, wenn die Seite versteckt ist, um Bandbreite und CPU zu sparen. Setzen Sie sie fort, wenn sie wieder sichtbar wird.
- **Benachrichtigungen steuern.** Zeigen Sie einen `Toast` an, wenn der Benutzer den Tab sehen kann, und eine `DesktopNotification`, wenn er es nicht kann.
- **Veraltete Daten bei Rückkehr aktualisieren.** Wenn die Seite von `HIDDEN` zurückkommt, entscheiden Sie, ob genug Zeit vergangen ist, um die Daten erneut abzurufen.
- **Engagement verfolgen.** Markieren Sie eine Sitzung als inaktiv, während der Tab versteckt ist.
