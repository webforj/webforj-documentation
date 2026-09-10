---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 47adf06762f8af67111f20937368723c
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Les notifications push peuvent atteindre les utilisateurs même lorsqu'une application n'est pas ouverte. Le navigateur s'abonne une fois, l'application stocke l'abonnement et le serveur l'utilise pour livrer des notifications lorsqu'un événement se produit. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> gère l'abonnement et la désinscription dans le navigateur. Sur le serveur, <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envoie un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> à un abonnement stocké.

<!-- INTRO_END -->

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/push-notifications/push.mp4" type="video/mp4"/>
  </video>
</div>

## Configuration et prérequis {#setup-and-prerequisites}

Les notifications push sont fournies par un module séparé. Ajoutez-le à votre application :

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-push</artifactId>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-push'
}
```

</TabItem>
</Tabs>

Les notifications push nécessitent :

- Un déploiement de servlet, comme Jetty, Spring Boot ou un fichier WAR.
- Une paire de clés, générée ci-dessous, que le déploiement utilise pour signer les notifications.
- Une origine sécurisée. Les navigateurs rejettent les abonnements servis par tout autre moyen que `https`, sauf depuis `localhost` pendant le développement.

:::info Origines sécurisées
<!-- vale off -->
Pour plus d'informations sur les contextes sécurisés et pourquoi ils sont importants, consultez la [documentation MDN sur les contextes sécurisés](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Génération des clés {#generating-the-keys}

Les services push n'acceptent que les notifications signées par le déploiement auquel le navigateur s'est abonné. Exécutez le [plugin de build](/docs/configuration/build-plugin) une fois pour chaque déploiement afin de générer sa paire de clés :

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn webforj:push-keys
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew webforjPushKeys
```

</TabItem>
</Tabs>

La commande renvoie trois lignes de configuration. Collez-les dans `application.properties` sans les guillemets, ou copiez-les telles qu'imprimées dans `webforj.conf`. Remplacez le sujet par l'adresse de contact du déploiement. Cela doit être une adresse `mailto:` ou `https://` que les services push peuvent utiliser pour contacter l'opérateur.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Propriété | Explication |
|----------|-------------|
| `webforj.push.public-key` | La moitié publique de la paire de clés utilisée par le déploiement pour signer les notifications |
| `webforj.push.private-key` | La moitié privée de la paire de clés. Comme tout autre secret, gardez-la hors du contrôle de version |
| `webforj.push.subject` | L'adresse de contact du déploiement. Cela doit être une adresse `mailto:` ou `https://` par laquelle les services push peuvent atteindre l'opérateur |

L'application lit ces propriétés au démarrage. Si la configuration n'en inclut que certaines, le démarrage échoue et indique quelles propriétés sont manquantes.

:::warning Rotation des clés
Chaque navigateur s'abonne à une paire de clés. Si les clés changent, le service push rejette les abonnements existants. Le prochain appel `subscribe()` dans chaque navigateur remplace son abonnement.
:::

## Comment ça fonctionne {#how-it-works}

Le processus comporte trois étapes :

1. **S'inscrire.** Depuis une vue, `Push.getCurrent().subscribe()` demande la permission de l'utilisateur et renvoie un `PushSubscription` qui identifie l'adresse du navigateur.
2. **Stocker.** L'application enregistre l'abonnement avec ses données et l'associe à l'utilisateur correspondant.
3. **Envoyer.** Plus tard, depuis n'importe quel thread, `PushSender.send(subscription, message)` transmet le message au service push du fournisseur du navigateur. Le service affiche la notification que l'application soit ouverte ou non.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Commande expédiée").setUrl("/orders/42").build());
```

Les sections suivantes expliquent ce que le navigateur affiche et comment gérer les échecs à chaque étape.

## Instance {#instance}

Récupérez l'instance push pour l'environnement actuel :

```java
import com.webforj.push.Push;

Push push = Push.getCurrent();

if (Push.isPresent()) {
  // ...
}

Push.ifPresent(p -> {
  // ...
});
```

## Abonnement du navigateur {#subscribing-the-browser}

Appelez `subscribe()` en réponse à une action de l'utilisateur, comme cliquer sur un bouton "Activer les notifications". Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> retourné se termine avec le <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> du navigateur. Si le navigateur ne peut pas s'abonner, il se termine de façon exceptionnelle avec une <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>.

```java
PendingResult<PushSubscription> request = Push.getCurrent().subscribe();
request.thenAccept(subscription -> {
  subscriptions.save(subscription);
});
request.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable.getCause();
  PushStatus status = error.getStatus();
  String message = error.getMessage();

  return null;
});
```

Si le navigateur est déjà abonné, appeler à nouveau `subscribe()` renvoie l'abonnement existant. Vous pouvez donc l'appeler en toute sécurité à chaque visite.

:::info Permission du navigateur
Le premier appel à `subscribe()` demande la permission à l'utilisateur. Le navigateur affiche cette invite, elle ne fait pas partie de l'interface utilisateur de l'application. Comme les navigateurs montrent cette invite uniquement en réponse à une action de l'utilisateur, appelez `subscribe()` à partir d'un écouteur de clic plutôt que du constructeur de la vue.

Si l'utilisateur bloque l'invite, l'application ne peut pas renouveler la demande pour cette origine.
:::

### Stockage des abonnements {#storing-subscriptions}

Un abonnement représente l'adresse d'un navigateur et appartient au serveur. Stockez-le avec les données de l'application, en utilisant son endpoint comme clé. Incluez toute information dont l'application a besoin pour sélectionner les navigateurs appropriés plus tard, telle que l'utilisateur associé. Chaque abonnement contient trois valeurs textuelles :

| Valeur | Signification |
|-------|---------|
| `getEndpoint()` | L'URL de livraison assignée par le service push du fournisseur du navigateur |
| `getP256dh()` | La clé publique du navigateur |
| `getAuth()` | Le secret d'authentification du navigateur |

Un utilisateur qui s'abonne depuis deux navigateurs a deux abonnements. Supprimez un abonnement lorsque son navigateur se désabonne ou lorsque l'envoi rapporte qu'il a expiré. Voir [Statut d'échec](#failure-status).

### Restauration d'un abonnement {#restoring-a-subscription}

`getSubscription()` renvoie l'abonnement actuel du navigateur, ou un résultat vide s'il n'existe pas. Utilisez-le pour synchroniser la copie du serveur, par exemple après que le stockage de l'application a été réinitialisé :

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

À travers <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>, `getPermission()` signale si l'utilisateur a accordé, refusé ou n'a pas encore répondu à l'invite de notification. Utilisez ce résultat pour cacher le bouton "Activer les notifications" lorsque le fait de cliquer n'aurait aucun effet.

### Désinscription {#unsubscribing}

`unsubscribe()` annule l'abonnement du navigateur. Il se termine avec l'abonnement supprimé afin que l'application puisse supprimer sa copie enregistrée, ou avec un résultat vide si le navigateur n'avait pas d'abonnement.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Envoi de notifications {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envoie un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> à un abonnement stocké. Il signe le message avec les clés du déploiement et le transmet au service push du fournisseur du navigateur. Ce service réveille le navigateur et affiche la notification. Comme l'opération ne bloque jamais le thread appelant, vous pouvez l'invoquer depuis un écouteur de clic, un job programmé ou un gestionnaire de requêtes.

Après que les propriétés sont configurées, l'expéditeur est disponible en tant que bean que vous pouvez injecter dans des vues, des services et des jobs programmés. Pour le remplacer, définissez votre propre bean `PushSender`.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Sans Spring, `new PushSender()` lit les clés de la configuration de l'application. Créez l'expéditeur sur un thread d'application, soit dans une vue, soit dans `App.run()`, puis utilisez-le depuis n'importe quel thread. Tous les expéditeurs partagent un pool de connexions vers les services push, donc il n'y a pas de coût à en créer un là où il est nécessaire.

Pour les notifications qui doivent être envoyées plus tard ou après que l'utilisateur soit parti, utilisez un minuteur sur le serveur comme `TaskScheduler` de Spring. N'utilisez pas un minuteur de page tel qu `Interval`, car il s'arrête lorsque l'onglet se ferme.

### Composition d'un message {#composing-a-message}

Créez un message avec son titre, puis configurez chaque autre option sur le constructeur :

```java
PushMessage message = PushMessage.create("Commande expédiée")
    .setBody("La commande #42 est en route")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Suivre", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Envoyé"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` retourne immédiatement. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> se termine lorsque le service push accepte le message, ou se termine de manière exceptionnelle si le service ne l'accepte pas. Si `send()` est appelé sur un thread d'application, tel qu'à partir d'un écouteur, ses rappels s'exécutent sur ce thread et peuvent mettre à jour les composants. Si la session qui a appelé `send()` se termine avant que la réponse n'arrive, les rappels ne s'exécutent pas, mais la notification est toujours livrée.

Un envoi attend jusqu'à 30 secondes pour que le service push réponde avant d'échouer avec `UNREACHABLE`. Utilisez `setTimeout(Duration)` pour changer le délai d'attente pour chaque expéditeur.

| Option | Effet |
|--------|--------|
| `setBody` | Définit le texte affiché sous le titre |
| `setIcon` | Définit l'image affichée avec la notification. Elle accepte des URLs absolues ainsi que les protocoles `icons://` et `ws://`. Voir [Actifs](/docs/managing-resources/assets-protocols). Elle n'accepte pas le protocole `context://` car les services push limitent un message à 4 Ko |
| `setUrl` | Définit la page qui s'ouvre lorsque l'utilisateur clique sur la notification. Les URLs relatives sont résolues par rapport à la racine de l'application. Si aucune URL n'est définie, la racine de l'application s'ouvre |
| `setActions` | Définit les boutons affichés sur la notification, avec une URL séparée pour chaque bouton. Voir [Support des navigateurs](#browser-support) |
| `setTag` | Définit une balise d'identification. Si une notification affichée a la même balise, la nouvelle notification la remplace |
| `setSilent` | Affiche la notification sans son ni vibration |
| `setTimeToLive` | Définit combien de temps le service push conserve le message pour un appareil hors ligne, jusqu'à quatre semaines |
| `setUrgency` | Utilise <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> pour permettre à l'appareil de retarder les messages de faible urgence et de préserver la batterie |
| `setTopic` | Remplace un message qui attend encore au service push lorsque les deux messages ont le même sujet. Les sujets peuvent contenir au maximum 32 caractères sûrs dans une URL |

Lorsque un onglet affiche déjà la page, cliquer sur la notification focalise l'application. Sinon, la page s'ouvre dans un nouvel onglet. Cliquer sur un bouton de notification ouvre son URL de la même manière.

:::info Une notification par message
Chaque message affiche une notification. Comme les navigateurs ne réveillent pas une page pour un message qui n'affiche rien, les pushes ne peuvent pas être utilisés pour des mises à jour de données silencieuses.
:::

## Statut d'échec {#failure-status}

Lorsque `subscribe()` ou `send()` échoue, son `PendingResult` signale une `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> identifie la raison :

| Statut | Quand | Que faire |
|--------|------|------------|
| `PERMISSION_DENIED` | L'utilisateur a bloqué les notifications pour l'application | Expliquez où l'utilisateur peut autoriser les notifications dans les paramètres du navigateur |
| `UNSUPPORTED` | Les push ne sont pas pris en charge par le navigateur, la page n'est pas dans un contexte sécurisé, ou l'application n'est pas déployée en tant que servlet | Masquez la fonctionnalité |
| `NOT_CONFIGURED` | Au moins une propriété `webforj.push.*` est manquante ou incomplète | Générez les clés et configurez toutes les trois propriétés |
| `SUBSCRIPTION_EXPIRED` | Le service push ne reconnaît plus l'abonnement parce que l'utilisateur s'est désabonné ou qu'il a réinstallé le navigateur | Supprimez l'abonnement stocké |
| `REJECTED` | Le service push a rejeté le message ; `getStatusCode()` contient sa réponse | Vérifiez les clés et la taille du message |
| `UNREACHABLE` | Le service push n'a pas répondu avant le délai d'attente | Réessayez plus tard |
| `UNKNOWN` | Le endpoint stocké n'est pas une URL valide, ou l'abonnement ou le message n'ont pas pu être encodés | Vérifiez l'abonnement stocké |

Supprimez les abonnements expirés lors de chaque envoi :

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip L'expiration arrive avec un message de retard
Les services push désinscrivent les abonnements de manière paresseuse. Ils acceptent toujours le premier message après qu'un utilisateur se soit désabonné, mais ce message n'ira nulle part. Le message suivant rapporte `SUBSCRIPTION_EXPIRED`. Un envoi accepté signifie que le message a atteint le service push, pas que l'utilisateur l'a vu.
:::

## Support des navigateurs {#browser-support}

Tous les principaux navigateurs de bureau et mobiles affichent des notifications push après s'être abonnés. Gardez ces limitations à l'esprit :

- Sur iPhone et iPad, les push ne fonctionnent que pour les applications web ajoutées à l'écran d'accueil sur iOS 16.4 ou ultérieur. Dans un onglet Safari, `subscribe()` rapporte `UNSUPPORTED`. Voir [Applications installables](/docs/configuration/installable-apps) pour le manifeste d'application requis.
- Safari n'affiche pas les boutons de notification. Il affiche les messages avec des actions sans leurs boutons, mais cliquer sur la notification ouvre tout de même l'URL du message.
- Les WebViews Android et iOS n'affichent pas les notifications.

Pour des détails par navigateur, consultez le tableau de compatibilité [showNotification MDN](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Exemple complet {#complete-example}

La vue suivante s'abonne et se désabonne du navigateur, stocke les abonnements en mémoire, et envoie un message à chaque abonnement stocké. Elle peut envoyer immédiatement ou attendre huit secondes en utilisant le `TaskScheduler` de Spring, permettant à l'onglet de se fermer avant que la notification n'arrive. La classe d'application utilise `@EnableScheduling` pour rendre le planificateur disponible.

```java title="PushSubscriptions.java"
package com.example;

import com.webforj.push.PushSubscription;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PushSubscriptions {

  private final Map<String, PushSubscription> byEndpoint = new ConcurrentHashMap<>();

  public void save(PushSubscription subscription) {
    byEndpoint.put(subscription.getEndpoint(), subscription);
  }

  public void delete(PushSubscription subscription) {
    byEndpoint.remove(subscription.getEndpoint());
  }

  public Collection<PushSubscription> findAll() {
    return byEndpoint.values();
  }
}
```

<!-- vale off -->

<ExpandableCode title="PushView.java" language="java" startLine={40} endLine={73}>

```java
package com.example;

import com.webforj.PendingResult;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.push.Push;
import com.webforj.push.PushAction;
import com.webforj.push.PushMessage;
import com.webforj.push.PushSender;
import com.webforj.push.PushStatus;
import com.webforj.push.PushSubscription;
import com.webforj.push.exception.WebforjPushException;
import com.webforj.router.annotation.Route;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.scheduling.TaskScheduler;

@Route("/push")
public class PushView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph status = new Paragraph("Vérification de l'abonnement…");
  private final TextField message = new TextField("Message", "Commande #42 est en route");
  private final Button subscribe =
      new Button("Activer les notifications", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Désactiver les notifications");
  private final Button sendNow = new Button("Envoyer maintenant");
  private final Button sendLater = new Button("Envoyer dans 8 secondes");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Abonné");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Les notifications sont bloquées dans ce navigateur"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Désaboné" : "Il n'y avait pas d'abonnement");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Envoi dans 8 secondes, fermez maintenant l'onglet");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Abonné" : "Pas abonné");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Envoi à " + subscriptions.findAll().size() + " abonnements");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Commandes")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Ouvrir l'accueil", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Livré"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("Un abonnement a expiré et a été supprimé");
        } else {
          report.accept(error.getMessage());
        }

        return null;
      });
    }
  }
}
```

</ExpandableCode>

<!-- vale on -->
