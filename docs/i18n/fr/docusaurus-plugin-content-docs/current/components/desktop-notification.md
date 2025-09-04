---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 16e95136d6067cafa258ef513f9e757f
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

Dans webforj 25.00 et supérieur, le composant **DesktopNotification** fournit une interface simple pour créer, afficher et gérer des notifications de bureau. Avec un accent sur la configuration minimale et la gestion des événements intégrée, le composant peut être utilisé lorsque vous devez notifier les utilisateurs d'événements en temps réel (tels que de nouveaux messages, alertes ou événements système) pendant qu'ils naviguent dans votre application.

:::warning fonctionnalité expérimentale
Le composant `DesktopNotification` est encore en évolution et son API peut subir des changements au fur et à mesure qu'il mûrit. Pour commencer à utiliser cette fonctionnalité, assurez-vous d'inclure la dépendance suivante dans votre pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Prérequis
Avant d'intégrer le composant `DesktopNotification`, assurez-vous que :

- Votre application fonctionne dans un **contexte sécurisé** (HTTPS).
- Le navigateur n'est pas en mode incognito ou de navigation privée.
- L'utilisateur a interagi avec l'application (par exemple, cliqué sur un bouton ou pressé une touche), car les notifications nécessitent un geste de l'utilisateur pour être affichées.
- L'utilisateur a accordé des autorisations pour les notifications (cela sera demandé automatiquement si nécessaire).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Utilisation de base {#basic-usage}

Il existe plusieurs façons de créer et d'afficher une notification. Dans la plupart des scénarios, l'approche la plus simple consiste à appeler l'une des méthodes statiques `show` qui encapsulent tout le cycle de vie de la notification.

### Exemple : Affichage d'une notification de base {#example-displaying-a-basic-notification}

```java
// Notification de base avec titre et message
DesktopNotification.show("Mise à jour disponible", "Votre téléchargement est terminé !");
```

Cette ligne crée une notification avec un titre et un corps, puis tente de l'afficher.

## Personnalisation de la notification {#customizing-the-notification}

Il existe diverses options pour personnaliser l'apparence et la présentation de la notification affichée, en fonction des besoins de l'application et du but de la notification. 

### Définir une `Icône` personnalisée {#setting-a-custom-icon}

Par défaut, la notification utilise l'icône de votre application définie via le [protocole des icônes](../managing-resources/assets-protocols#the-icons-protocol). Vous pouvez définir une icône personnalisée en utilisant la méthode `setIcon`. Le composant prend en charge différents schémas d'URL :

- [`context://`](../managing-resources/assets-protocols#the-context-protocol) : Résolu comme une URL de contexte pointant vers le dossier de ressources de l'application ; l'image est encodée en base64.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol) : Résolu comme une URL de serveur Web, donnant une URL complètement qualifiée.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol) : Résolu comme une URL d'icônes.

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

Le composant `DesktopNotification` prend en charge plusieurs événements de cycle de vie, et des écouteurs peuvent être attachés pour gérer les événements, comme lorsque qu'une notification est affichée, fermée, cliquée ou rencontre une erreur.

| Événement                  | Description                                           | Quand l'utiliser                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Ouvrir** | Déclenché lorsque la notification est affichée.       | Journaliser l'affichage de la notification, mettre à jour l'UI, suivre l'engagement.    |
| **Fermer**| Déclenché lorsque la notification est fermée.         | Nettoyer les ressources, enregistrer les refus, exécuter des actions de suivi.|
| **Erreur**| Déclenché lorsqu'une erreur se produit avec la notification ou que l'utilisateur n'a pas accordé l'autorisation.| Gérer les erreurs avec élégance, informer l'utilisateur, appliquer des solutions de repli.  |
| **Cliquer**| Déclenché lorsque l'utilisateur clique sur la notification. | Naviguer vers une section spécifique, enregistrer les interactions, recentrer l'application. |


```java
DesktopNotification notification = new DesktopNotification("Alerte", "Vous avez un nouveau message !")

// Attacher un écouteur d'événements pour l'événement d'ouverture
notification.onOpen(event -> {
  System.out.println("La notification a été ouverte par l'utilisateur.");
});

// De la même manière, écouter l'événement de clic
notification.onClick(event -> {
  System.out.println("Notification cliquée.");
});
```

:::warning Comportement de clic
Les politiques de sécurité du navigateur empêchent l'événement de clic de notification d'amener automatiquement la fenêtre ou l'onglet de votre application au premier plan. Ce comportement est imposé par le navigateur et ne peut pas être contourné par programme. Si votre application nécessite que la fenêtre soit mise au premier plan, vous devrez instruire les utilisateurs de cliquer dans l'application après avoir interagi avec la notification.
:::

## Considérations de sécurité et de compatibilité {#security-and-compatibility-considerations}

Lorsque vous utilisez le composant **DesktopNotification**, gardez les points suivants à l'esprit :

- **Contexte de sécurité :** Votre application doit être servie via HTTPS pour garantir que les notifications sont autorisées par la plupart des navigateurs modernes.
- **Exigence de geste de l'utilisateur :** Les notifications ne sont affichées qu'après une action déclenchée par l'utilisateur. Le simple chargement d'une page ne déclenchera pas une notification.
- **Limitations du navigateur :** Tous les navigateurs ne gèrent pas les icônes personnalisées ou le comportement de mise au premier plan de la même manière. Par exemple, les icônes personnalisées peuvent ne pas fonctionner dans Safari, tandis que le comportement des événements peut varier dans d'autres navigateurs.
- **Autorisations :** Vérifiez toujours que votre application vérifie et demande les autorisations de notification à l'utilisateur de manière élégante.

## Meilleures pratiques d'utilisation {#usage-best-practices}

Gardez à l'esprit les meilleures pratiques suivantes lors de l'utilisation du composant `DesktopNotification` dans votre application :

- **Informez vos utilisateurs :** Faites savoir aux utilisateurs pourquoi les notifications sont nécessaires et comment elles peuvent en bénéficier.
- **Fournir des solutions de repli :** Étant donné que certains navigateurs peuvent restreindre les notifications, envisagez des moyens alternatifs d'alerter les utilisateurs (par exemple, des messages au sein de l'application).
- **Gestion des erreurs :** Enregistrez toujours un écouteur d'erreurs pour gérer avec élégance les scénarios où les notifications échouent à s'afficher.
