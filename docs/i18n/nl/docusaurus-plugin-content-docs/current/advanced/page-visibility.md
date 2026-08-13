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

De `Page` klasse kan aangeven wanneer de gebruiker van het tabblad hosting je app is wisselt, het venster minimaliseert of weer terugkomt. Gebruik het om het pollingsysteem en animaties te pauzeren wanneer niemand kijkt, meldingen te beperken of verouderde gegevens te vernieuwen wanneer het tabblad weer focus krijgt.

De API heeft twee onderdelen:

- Een getypeerde query, `getVisibilityState()`, die de huidige staat retourneert.
- Een listener, `addVisibilityChangeListener(...)`, die elke keer afgaat wanneer de staat verandert.

## Zichtbaarheidsstatussen {#visibility-states}

`PageVisibilityState` heeft twee waarden:

| Status | Betekenis |
| --- | --- |
| `VISIBLE` | De pagina-inhoud is ten minste gedeeltelijk zichtbaar. Het tabblad is voorgrond van een niet-geminimaliseerd venster. |
| `HIDDEN` | De pagina-inhoud is niet zichtbaar voor de gebruiker. Het tabblad is op de achtergrond, het venster is geminimaliseerd, het scherm is vergrendeld, of het besturingssysteem toont een screensaver. |

## De huidige staat lezen {#reading-the-current-state}

`Page.getVisibilityState()` retourneert een `PendingResult<PageVisibilityState>` dat wordt opgelost met de huidige staat.

```java
Page.getCurrent().getVisibilityState().thenAccept(state -> {
  if (state == PageVisibilityState.VISIBLE) {
    // gebruiker kijkt naar het tabblad
  }
});
```

Roep het aan wanneer je een eenmalig antwoord nodig hebt, bijvoorbeeld wanneer een geplande taak wakker wordt. Voor doorlopende reacties registreer je in plaats daarvan een listener.

## Luisteren naar veranderingen {#listening-for-changes}

`addVisibilityChangeListener(...)` registreert een listener die op de hoogte wordt gesteld elke keer dat de zichtbaarheidstoestand verandert. De bijpassende alias is `onVisibilityChange(...)`.

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

De gebeurtenis draagt de nieuwe staat en enkele handige toegangsmethoden:

| Methode | Retourneert |
| --- | --- |
| `getState()` | De nieuwe `PageVisibilityState`. |
| `isVisible()` | `true` wanneer de nieuwe staat `VISIBLE` is. |
| `isHidden()` | `true` wanneer de nieuwe staat `HIDDEN` is. |
| `getPage()` | De `Page` die de gebeurtenis heeft geproduceerd. |

Verwijder een enkele listener met de geretourneerde `ListenerRegistration`.

## Voorbeeld: alleen notificeren wanneer het tabblad verborgen is {#example-notify-when-hidden}

Een veelvoorkomend gebruiksgeval is het kiezen van het leveringskanaal op basis van of de gebruiker momenteel naar het tabblad kijkt. De onderstaande snippet plant een notificatie vijf seconden in de toekomst. Als het tabblad verborgen is wanneer de timer afgaat, genereert het een desktopnotificatie en trekt het een badge op de favicon. Als het tabblad zichtbaar is, toont het een in-app toast.

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

    H1 title = new H1("Pagina Zichtbaarheid demo");
    Paragraph hint = new Paragraph(
        "Klik op de knop en schakel vervolgens over naar een ander tabblad voordat de timer eindigt.");

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
        activeNotification = new DesktopNotification("Pagina Zichtbaarheid demo",
            "De timer ging af terwijl het tabblad verborgen was.");
        activeNotification.open();
      } else {
        Toast.show("Timer ging af terwijl het tabblad zichtbaar was.", Theme.SUCCESS);
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

De zichtbaarheidlistener wist de favicon-badge en sluit de desktopnotificatie wanneer de gebruiker terugkeert naar het tabblad.

## Wanneer het te gebruiken {#when-to-use-it}

- **Achtergrondwerk pauzeren.** Stop met pollingen, intervallen en animaties wanneer de pagina verborgen is om bandbreedte en CPU te besparen. Herstart ze wanneer deze weer zichtbaar wordt.
- **Meldingen beperken.** Toon een `Toast` wanneer de gebruiker het tabblad kan zien en een `DesktopNotification` wanneer dat niet kan.
- **Vernieuw verouderde gegevens bij terugkeer.** Wanneer de pagina terugkomt van `HIDDEN`, bepaal dan of er voldoende tijd is verstreken om opnieuw op te halen.
- **Volg betrokkenheid.** Markeer een sessie als inactief terwijl het tabblad verborgen is.
