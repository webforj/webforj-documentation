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

La classe `Page` peut indiquer quand l'utilisateur s'est éloigné de l'onglet hébergeant votre application, a minimisé la fenêtre ou est revenu. Utilisez-la pour suspendre le polling et les animations lorsque personne ne regarde, restreindre les notifications ou rafraîchir les données obsolètes lorsque l'onglet retrouve le focus.

L'API comporte deux éléments :

- Une requête typée, `getVisibilityState()`, qui retourne l'état actuel.
- Un écouteur, `addVisibilityChangeListener(...)`, qui se déclenche chaque fois que l'état change.

## État de visibilité {#visibility-states}

`PageVisibilityState` a deux valeurs :

| État | Signification |
| --- | --- |
| `VISIBLE` | Le contenu de la page est au moins partiellement visible. L'onglet est au premier plan d'une fenêtre non minimisée. |
| `HIDDEN` | Le contenu de la page n'est pas visible pour l'utilisateur. L'onglet est en arrière-plan, la fenêtre est minimisée, l'écran est verrouillé, ou le système d'exploitation affiche un écran de veille. |

## Lire l'état actuel {#reading-the-current-state}

`Page.getVisibilityState()` retourne un `PendingResult<PageVisibilityState>` qui se résout avec l'état actuel.

```java
Page.getCurrent().getVisibilityState().thenAccept(state -> {
  if (state == PageVisibilityState.VISIBLE) {
    // l'utilisateur regarde l'onglet
  }
});
```

Appelez-le lorsque vous avez besoin d'une réponse unique, par exemple lors du réveil d'une tâche programmée. Pour des réactions continues, enregistrez plutôt un écouteur.

## Écouter les changements {#listening-for-changes}

`addVisibilityChangeListener(...)` enregistre un écouteur qui est notifié chaque fois que l'état de visibilité change. L'alias correspondant est `onVisibilityChange(...)`.

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

L'événement transporte le nouvel état et quelques accesseurs de commodité :

| Méthode | Retourne |
| --- | --- |
| `getState()` | Le nouveau `PageVisibilityState`. |
| `isVisible()` | `true` lorsque le nouvel état est `VISIBLE`. |
| `isHidden()` | `true` lorsque le nouvel état est `HIDDEN`. |
| `getPage()` | La `Page` qui a produit l'événement. |

Supprimez un seul écouteur avec le `ListenerRegistration` retourné.

## Exemple : notifier uniquement lorsque l'onglet est masqué {#example-notify-when-hidden}

Un cas d'utilisation courant consiste à choisir le canal de livraison en fonction de la visibilité de l'utilisateur sur l'onglet. L'extrait ci-dessous planifie une notification cinq secondes dans le futur. Si l'onglet est masqué lorsque le minuteur se déclenche, il envoie une notification de bureau et affiche un badge sur le favicon. Si l'onglet est visible, il montre un toast dans l'application.

```java
@Route("/")
public class NotifyView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Button notifyButton = new Button("Notifier dans 5 secondes");
  private final Debouncer schedule = new Debouncer(5.0f);

  private ListenerRegistration<PageVisibilityChangeEvent> visibilityRegistration;
  private DesktopNotification activeNotification;

  public NotifyView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER);

    H1 title = new H1("Démo de visibilité de page");
    Paragraph hint = new Paragraph(
        "Cliquez sur le bouton, puis changez d'onglet avant la fin du minuteur.");

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
        activeNotification = new DesktopNotification("Démo de visibilité de page",
            "Le minuteur a sonné alors que l'onglet était masqué.");
        activeNotification.open();
      } else {
        Toast.show("Le minuteur a sonné alors que l'onglet était visible.", Theme.SUCCESS);
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

L'écouteur de visibilité efface le badge du favicon et rejette la notification de bureau lorsque l'utilisateur revient à l'onglet.

## Quand l'utiliser {#when-to-use-it}

- **Suspendre le travail en arrière-plan.** Arrêtez le polling, les intervalles et les animations lorsque la page est masquée pour économiser de la bande passante et du processeur. Ils reprennent lorsqu'elle redevient visible.
- **Restreindre les notifications.** Montrez un `Toast` lorsque l'utilisateur peut voir l'onglet et une `DesktopNotification` lorsqu'il ne peut pas.
- **Rafraîchir les données obsolètes à son retour.** Lorsque la page revient de `HIDDEN`, décidez si suffisamment de temps a passé pour le récupérer à nouveau.
- **Suivre l'engagement.** Marquez une session comme inactive tant que l'onglet est masqué.
