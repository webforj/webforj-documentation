---
title: DesktopNotification
sidebar_position: 29
description: >-
  Send native OS notifications outside the browser window with the
  DesktopNotification component for real-time messages, alerts, and status
  changes.
_i18n_hash: 529ae2fce596f744b423574be0a95dc0
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

Le composant `DesktopNotification` affiche des notifications de bureau natives en dehors de la fenêtre du navigateur. Il peut être utilisé pour alerter les utilisateurs sur des événements en temps réel, tels que de nouveaux messages, des alertes système ou des changements de statut pendant qu'ils utilisent votre application.

<!-- INTRO_END -->

## Configuration et prérequis {#setup-and-prerequisites}

<ExperimentalWarning />

Pour commencer à utiliser cette fonctionnalité, incluez la dépendance suivante dans votre pom.xml :

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Avant d’intégrer le composant `DesktopNotification`, assurez-vous que :

- Votre application fonctionne dans un **contexte sécurisé** (HTTPS).
- Le navigateur n'est pas en mode incognito ou navigation privée.
- L'utilisateur a interagi avec l'application (par exemple, a cliqué sur un bouton ou a appuyé sur une touche), car les notifications nécessitent un geste de l'utilisateur pour s'afficher.
- L'utilisateur a accordé les permissions pour les notifications (cela sera demandé automatiquement si nécessaire).

## Utilisation de base {#basic-usage}

Il existe plusieurs façons de créer et d'afficher une notification. Dans la plupart des scénarios, la méthode la plus simple consiste à appeler l'une des méthodes statiques `show` qui encapsulent l'ensemble du cycle de vie de la notification.

### Exemple : Afficher une notification de base {#example-displaying-a-basic-notification}

```java
// Notification de base avec titre et message
DesktopNotification.show("Mise à jour disponible", "Votre téléchargement est complet !");
```

Cette ligne crée une notification avec un titre et un corps, puis tente de l'afficher.

## Personnaliser la notification {#customizing-the-notification}

Il existe diverses options pour personnaliser l'apparence de la notification affichée, en fonction des besoins de l'application et de l'objectif de la notification.

### Définir une `Icon` personnalisée {#setting-a-custom-icon}

Par défaut, la notification utilise l'icône de votre application définie via le [protocole des icônes](../managing-resources/assets-protocols#the-icons-protocol). Vous pouvez définir une icône personnalisée en utilisant la méthode `setIcon`. Le composant prend en charge différents schémas d'URL :

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Résolu en tant qu'URL de contexte pointant vers le dossier de ressources de l'application ; l'image est encodée en base64.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Résolu en tant qu'URL de serveur web, fournissant une URL entièrement qualifiée.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Résolu en tant qu'URL d'icônes.

**Exemple :**

```java
// Création d'une notification avec une URL d'icône personnalisée
DesktopNotification notification = new DesktopNotification(
  "Rappel", "La réunion commence dans 10 minutes."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Événements de notification {#notification-events}

Le `DesktopNotification` prend en charge plusieurs événements de cycle de vie, et des écouteurs peuvent être attachés pour gérer les événements, comme lorsque la notification est affichée, fermée, cliquée ou rencontre une erreur.

| Événement               | Description                                           | Quand l'utiliser                                           |
|-------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Ouvert**              | Déclenché lorsque la notification est affichée.      | Journaliser l'affichage de la notification, mettre à jour l'interface utilisateur, suivre l'engagement. |
| **Fermé**              | Déclenché lorsque la notification est fermée.         | Nettoyer les ressources, enregistrer les dismissals, exécuter des actions de suivi. |
| **Erreur**              | Déclenché lorsqu'une erreur se produit avec la notification ou que l'utilisateur n'a pas accordé la permission. | Gérer les erreurs gracieusement, notifier l'utilisateur, appliquer des solutions alternatives. |
| **Clic**                | Déclenché lorsque l'utilisateur clique sur la notification. | Naviguer vers une section spécifique, enregistrer les interactions, recentrer l'application. |

```java
DesktopNotification notification = new DesktopNotification("Alerte", "Vous avez un nouveau message!")

// Attacher un écouteur d'événements pour l'événement ouvert
notification.onOpen(event -> {
  System.out.println("La notification a été ouverte par l'utilisateur.");
});

// De même, écoutez l'événement clic
notification.onClick(event -> {
  System.out.println("Notification cliquée.");
});
```

:::warning Comportement de clic
Les politiques de sécurité des navigateurs empêchent l'événement de clic de la notification d'amener automatiquement la fenêtre ou l'onglet de votre application au premier plan. Ce comportement est imposé par le navigateur et ne peut pas être contourné par programmation. Si votre application nécessite que la fenêtre soit mise au premier plan, vous devrez demander aux utilisateurs de cliquer dans l'application après avoir interagi avec la notification.
:::

## Considérations de sécurité et de compatibilité {#security-and-compatibility-considerations}

Lors de l'utilisation du composant **DesktopNotification**, gardez à l'esprit les points suivants :

- **Contexte de sécurité :** Votre application doit être servie sur HTTPS pour garantir que les notifications sont autorisées par la plupart des navigateurs modernes.
- **Exigence de geste de l'utilisateur :** Les notifications ne sont affichées qu'après une action déclenchée par l'utilisateur. Seule le chargement d'une page ne déclenchera pas une notification.
- **Limitations des navigateurs :** Tous les navigateurs ne gèrent pas les icônes personnalisées ou le comportement de mise au premier plan de la même manière. Par exemple, les icônes personnalisées peuvent ne pas fonctionner dans Safari, tandis que le comportement des événements peut varier dans d'autres navigateurs.
- **Permissions :** Vérifiez toujours que votre application vérifie et demande gracieusement les permissions de notification à l'utilisateur.

## Meilleures pratiques d'utilisation {#usage-best-practices}

Gardez à l'esprit les meilleures pratiques suivantes lors de l'utilisation du composant `DesktopNotification` dans votre application :

- **Informez vos utilisateurs :** Faites savoir aux utilisateurs pourquoi les notifications sont nécessaires et comment elles peuvent en bénéficier.
- **Fournissez des solutions de repli :** Comme certains navigateurs peuvent restreindre les notifications, envisagez des moyens alternatifs d'alerter les utilisateurs (par exemple, des messages in-app).
- **Gestion des erreurs :** Enregistrez toujours un écouteur d'erreurs pour gérer gracieusement les scénarios où les notifications échouent à s'afficher.
