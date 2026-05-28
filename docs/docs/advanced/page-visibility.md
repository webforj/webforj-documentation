---
title: Page Visibility
sidebar_position: 32
sidebar_class_name: new-content
description: Detect when the tab hosting your app moves between the foreground and the background, and react in Java.
---

<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/Page" top='true'/>

The `Page` class can tell when the user has switched away from the tab hosting your app, minimized the window, or returned. Use it to pause polling and animations when nobody is looking, gate notifications, or refresh stale data when the tab regains focus.

The API has two pieces:

- A typed query, `getVisibilityState()`, that returns the current state.
- A listener, `addVisibilityChangeListener(...)`, that fires every time the state changes.

## Visibility states {#visibility-states}

`PageVisibilityState` has two values:

| State | Meaning |
| --- | --- |
| `VISIBLE` | The page content is at least partially visible. The tab is in the foreground of a non minimized window. |
| `HIDDEN` | The page content is not visible to the user. The tab is in the background, the window is minimized, the screen is locked, or the operating system is showing a screensaver. |

## Reading the current state {#reading-the-current-state}

`Page.getVisibilityState()` returns a `PendingResult<PageVisibilityState>` that resolves with the current state.

```java
Page.getCurrent().getVisibilityState().thenAccept(state -> {
  if (state == PageVisibilityState.VISIBLE) {
    // user is looking at the tab
  }
});
```

Call it when you need a one shot answer, for example when a scheduled task wakes up. For ongoing reactions, register a listener instead.

## Listening for changes {#listening-for-changes}

`addVisibilityChangeListener(...)` registers a listener that is notified every time the visibility state changes. The matching alias is `onVisibilityChange(...)`.

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

The event carries the new state and a few convenience accessors:

| Method | Returns |
| --- | --- |
| `getState()` | The new `PageVisibilityState`. |
| `isVisible()` | `true` when the new state is `VISIBLE`. |
| `isHidden()` | `true` when the new state is `HIDDEN`. |
| `getPage()` | The `Page` that produced the event. |

Remove a single listener with the returned `ListenerRegistration`.

## Example: notify only when the tab is hidden {#example-notify-when-hidden}

A common use case is choosing the delivery channel based on whether the user is currently looking at the tab. The snippet below schedules a notification five seconds in the future. If the tab is hidden when the timer fires, it raises a desktop notification and draws a badge on the favicon. If the tab is visible, it shows an in app toast.

```java
@Route("/")
public class NotifyView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Button notifyButton = new Button("Notify in 5 seconds");
  private final Debouncer schedule = new Debouncer(5.0f);

  private ListenerRegistration<PageVisibilityChangeEvent> visibilityRegistration;
  private DesktopNotification activeNotification;

  public NotifyView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER);

    H1 title = new H1("Page Visibility demo");
    Paragraph hint = new Paragraph(
        "Click the button, then switch to another tab before the timer ends.");

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
        activeNotification = new DesktopNotification("Page Visibility demo",
            "The timer fired while the tab was hidden.");
        activeNotification.open();
      } else {
        Toast.show("Timer fired while the tab was visible.", Theme.SUCCESS);
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

The visibility listener clears the favicon badge and dismisses the desktop notification when the user returns to the tab.

## When to use it {#when-to-use-it}

- **Pause background work.** Stop polling, intervals, and animations when the page is hidden to save bandwidth and CPU. Resume them when it becomes visible again.
- **Gate notifications.** Show a `Toast` when the user can see the tab and a `DesktopNotification` when they cannot.
- **Refresh stale data on return.** When the page comes back from `HIDDEN`, decide whether enough time has passed to re fetch.
- **Track engagement.** Mark a session as inactive while the tab is hidden.
