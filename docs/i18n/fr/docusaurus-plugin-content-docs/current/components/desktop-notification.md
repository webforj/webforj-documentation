---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 79d5ddb69e13f8536439346d8d4ee85d
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

Le composant `DesktopNotification` affiche des notifications de bureau natives en dehors de la fenêtre du navigateur. Il peut être utilisé pour alerter les utilisateurs concernant des événements en temps réel comme de nouveaux messages, des alertes système ou des changements de statut pendant qu'ils utilisent votre application.

<!-- INTRO_END -->

## Configuration et prérequis {#setup-and-prerequisites}

:::warning fonctionnalité expérimentale
Le composant `DesktopNotification` est encore en évolution, et son API peut subir des changements au fur et à mesure de sa maturation. Pour commencer à utiliser cette fonctionnalité, assurez-vous d'inclure la dépendance suivante dans votre pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

Avant d'intégrer le composant `DesktopNotification`, assurez-vous que :

- Votre application s'exécute dans un **contexte sécurisé** (HTTPS).
- Le navigateur n'est pas en mode navigation privée ou incognito.
- L'utilisateur a interagi avec l'application (par exemple, en cliquant sur un bouton ou en appuyant sur une touche), car les notifications nécessitent un geste de l'utilisateur pour s'afficher.
- L'utilisateur a accordé des permissions pour les notifications (cette demande sera faite automatiquement si nécessaire).

## Utilisation de base {#basic-usage}

Il existe plusieurs façons de créer et d'afficher une notification. Dans la plupart des scénarios, l'approche la plus simple consiste à appeler l'une des méthodes statiques `show` qui encapsulent l'ensemble du cycle de vie de la notification.

### Exemple : Affichage d'une notification de base {#example-displaying-a-basic-notification}

```java
// Notification de base avec titre et message
DesktopNotification.show("Mise à jour disponible", "Votre téléchargement est terminé !");
```

Cette ligne crée une notification avec un titre et un corps, puis tente de l'afficher.

## Personnalisation de la notification {#customizing-the-notification}

Il existe plusieurs options pour personnaliser l'apparence de la notification affichée, en fonction des besoins de l'application et de l'objectif de la notification.

### Définir une `Icône` personnalisée {#setting-a-custom-icon}

Par défaut, la notification utilise l'icône de votre application définie via le [protocole des icônes](../managing-resources/assets-protocols#the-icons-protocol). Vous pouvez définir une icône personnalisée en utilisant la méthode `setIcon`. Le composant prend en charge différents schémas d'URL :

- [`context://`](../managing-resources/assets-protocols#the-context-protocol) : Résolu en tant qu'URL de contexte pointant vers le dossier de ressources de l'application ; l'image est encodée en base64.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol) : Résolu en tant qu'URL de serveur web, donnant une URL pleinement qualifiée.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol) : Résolu en tant qu'URL d'icônes.

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

Le `DesktopNotification` prend en charge plusieurs événements de cycle de vie, et des auditeurs peuvent être attachés pour gérer des événements, comme lorsque la notification est affichée, fermée, cliquée ou rencontre une erreur.

| Événement             | Description                                           | Quand l'utiliser                                           |
|-----------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Ouverture**         | Déclenché lorsque la notification est affichée.       | Journaliser l'affichage de la notification, mettre à jour l'interface utilisateur, suivre l'engagement.    |
| **Fermeture**         | Déclenché lorsque la notification est fermée.         | Nettoyer les ressources, journaliser les dismissals, exécuter des actions de suivi.|
| **Erreur**            | Déclenché lorsqu'une erreur se produit avec la notification ou si l'utilisateur n'a pas accordé la permission. | Gérer les erreurs de manière élégante, notifier l'utilisateur, appliquer des solutions alternatives.  |
| **Clic**              | Déclenché lorsque l'utilisateur clique sur la notification. | Naviguer vers une section spécifique, journaliser les interactions, recentrer l'application. |

```java
DesktopNotification notification = new DesktopNotification("Alerte", "Vous avez un nouveau message !")

// Attacher un écouteur d'événements pour l'événement d'ouverture
notification.onOpen(event -> {
  System.out.println("La notification a été ouverte par l'utilisateur.");
});

// De même, écouter l'événement de clic
notification.onClick(event -> {
  System.out.println("Notification cliquée.");
});
```

:::warning Comportement de clic
Les politiques de sécurité des navigateurs empêchent l'événement de clic de la notification de ramener automatiquement la fenêtre ou l'onglet de votre application au premier plan. Ce comportement est imposé par le navigateur et ne peut pas être contourné par programmation. Si votre application nécessite que la fenêtre soit mise au premier plan, vous devrez instruire les utilisateurs de cliquer dans l'application après avoir interagi avec la notification.
:::

## Considérations de sécurité et de compatibilité {#security-and-compatibility-considerations}

Lorsque vous utilisez le composant **DesktopNotification**, gardez les points suivants à l'esprit :

- **Contexte de sécurité :** Votre application doit être servie via HTTPS pour garantir que les notifications sont autorisées par la plupart des navigateurs modernes.
- **Exigence de geste utilisateur :** Les notifications ne s'affichent qu'après une action déclenchée par l'utilisateur. Simplement charger une page ne déclenchera pas une notification.
- **Limitations des navigateurs :** Tous les navigateurs ne gèrent pas les icônes personnalisées ou le comportement de mise au premier plan de la même manière. Par exemple, les icônes personnalisées peuvent ne pas fonctionner dans Safari, tandis que le comportement des événements peut varier dans d'autres navigateurs.
- **Permissions :** Vérifiez toujours que votre application vérifie et demande les permissions de notification à l'utilisateur de manière élégante.

## Bonnes pratiques d'utilisation {#usage-best-practices}

Gardez à l'esprit les meilleures pratiques suivantes lors de l'utilisation du composant `DesktopNotification` dans votre application :

- **Informez vos utilisateurs :** Faites savoir aux utilisateurs pourquoi les notifications sont nécessaires et comment elles peuvent en bénéficier.
- **Fournir des solutions alternatives :** Étant donné que certains navigateurs peuvent restreindre les notifications, envisagez des moyens alternatifs d'alerter les utilisateurs (par exemple, des messages dans l'application).
- **Gestion des erreurs :** Enregistrez toujours un écouteur d'erreurs pour gérer élégamment les scénarios où les notifications échouent à s'afficher.
