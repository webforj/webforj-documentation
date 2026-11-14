---
sidebar_position: 39
title: Notifications push
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: be5e6f5947bf38824dde0289d6415971
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Les notifications push peuvent atteindre les utilisateurs même lorsqu’une application n’est pas ouverte. Le navigateur s’abonne une fois, l’application enregistre l’abonnement, puis le serveur l’utilise pour envoyer des notifications lorsqu’un événement se produit. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> gère l’abonnement et le désabonnement dans le navigateur. Côté serveur, <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envoie un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> à un abonnement enregistré.

<!-- INTRO_END -->

<VideoPlayer video="push-notifications/push" width={1920} height={1072} title="Push notifications" />

## Configuration et prérequis {#setup-and-prerequisites}

Les notifications push sont fournies par un module distinct. Ajoutez-le à votre application :

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

- Un déploiement de servlet, tel que Jetty, Spring Boot ou un fichier WAR.
- Une paire de clés, générée ci-dessous, que le déploiement utilise pour signer les notifications.
- Une origine sécurisée. Les navigateurs refusent les abonnements servis autrement qu’en `https`, sauf depuis `localhost` pendant le développement.

:::info Origines sécurisées
<!-- vale off -->
Pour en savoir plus sur les contextes sécurisés et leur importance, consultez la [documentation MDN sur les contextes sécurisés](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Génération des clés {#generating-the-keys}

Les services push n’acceptent que les notifications signées par le déploiement auquel le navigateur s’est abonné. Exécutez une fois le [plug-in de build](/docs/configuration/build-plugin) pour chaque déploiement afin de générer sa paire de clés :

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

La commande affiche trois lignes de configuration. Collez-les dans `application.properties` sans les guillemets, ou copiez-les telles quelles dans `webforj.conf`. Remplacez le sujet par l’adresse de contact du déploiement. Il doit s’agir d’une adresse `mailto:` ou `https://` que les services push peuvent utiliser pour contacter l’opérateur.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Propriété | Explication |
|----------|-------------|
| `webforj.push.public-key` | La partie publique de la paire de clés utilisée par le déploiement pour signer les notifications |
| `webforj.push.private-key` | La partie privée de la paire de clés. Comme tout autre secret, elle doit être tenue à l’écart du contrôle de version |
| `webforj.push.subject` | L’adresse de contact du déploiement. Il doit s’agir d’une adresse `mailto:` ou `https://` permettant aux services push de joindre l’opérateur |

L’application lit ces propriétés au démarrage. Si la configuration n’en inclut que certaines, le démarrage échoue et indique les propriétés manquantes.

:::warning Rotation des clés
Chaque navigateur s’abonne à une seule paire de clés. Si les clés changent, le service push rejette les abonnements existants. Le prochain appel à `subscribe()` dans chaque navigateur remplace son abonnement.
:::

## Fonctionnement {#how-it-works}

Le processus comporte trois étapes :

1. **S’abonner.** Depuis une vue, `Push.getCurrent().subscribe()` demande l’autorisation de l’utilisateur et renvoie un `PushSubscription` qui identifie l’adresse du navigateur.
2. **Enregistrer.** L’application enregistre l’abonnement avec ses données et l’associe à l’utilisateur correspondant.
3. **Envoyer.** Plus tard, depuis n’importe quel thread, `PushSender.send(subscription, message)` transmet le message au service push du fournisseur du navigateur. Le service affiche la notification, que l’application soit ouverte ou non.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Order shipped").setUrl("/orders/42").build());
```

Les sections suivantes expliquent ce que le navigateur affiche et comment gérer les échecs à chaque étape.

## Instance {#instance}

Récupérez l’instance push pour l’environnement actuel :

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

Appelez `subscribe()` en réponse à une action de l’utilisateur, par exemple un clic sur un bouton « Activer les notifications ». Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> renvoyé se termine avec le <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> du navigateur. Si le navigateur ne peut pas s’abonner, le résultat se termine de façon exceptionnelle avec une <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>.

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

Si le navigateur est déjà abonné, un nouvel appel à `subscribe()` renvoie l’abonnement existant. Vous pouvez donc l’appeler sans risque à chaque visite.

:::info Autorisation du navigateur
Le premier appel à `subscribe()` demande l’autorisation de l’utilisateur. Cette invite est affichée par le navigateur et ne fait pas partie de l’interface de l’application. Comme les navigateurs n’affichent cette invite qu’en réponse à une action de l’utilisateur, appelez `subscribe()` depuis un écouteur de clic plutôt que depuis le constructeur de la vue.

Si l’utilisateur bloque l’invite, l’application ne peut plus la réafficher pour cette origine.
:::

### Enregistrement des abonnements {#storing-subscriptions}

Un abonnement représente l’adresse d’un navigateur et doit être conservé sur le serveur. Enregistrez-le avec les données de l’application, en utilisant son endpoint comme clé. Incluez toutes les informations nécessaires à l’application pour sélectionner les navigateurs appropriés ultérieurement, par exemple l’utilisateur associé. Chaque abonnement contient trois valeurs textuelles :

| Valeur | Signification |
|-------|---------|
| `getEndpoint()` | L’URL de livraison attribuée par le service push du fournisseur du navigateur |
| `getP256dh()` | La clé publique du navigateur |
| `getAuth()` | Le secret d’authentification du navigateur |

Un utilisateur qui s’abonne depuis deux navigateurs a deux abonnements. Supprimez un abonnement lorsque son navigateur se désabonne ou lorsqu’un envoi indique qu’il a expiré. Consultez la section [Statut d’échec](#failure-status).

### Restauration d’un abonnement {#restoring-a-subscription}

`getSubscription()` renvoie l’abonnement actuel du navigateur, ou un résultat vide s’il n’en existe aucun. Utilisez-le pour synchroniser la copie stockée sur le serveur, par exemple après la réinitialisation du stockage de l’application :

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

`getPermission()` indique, via <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>, si l’utilisateur a autorisé ou refusé les notifications, ou s’il n’a pas encore répondu à l’invite. Utilisez ce résultat pour masquer le bouton « Activer les notifications » lorsque son clic n’aurait aucun effet.

### Désabonnement {#unsubscribing}

`unsubscribe()` annule l’abonnement du navigateur. Le résultat se termine avec l’abonnement supprimé afin que l’application puisse effacer sa copie enregistrée, ou avec un résultat vide si le navigateur n’avait aucun abonnement.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Envoi de notifications {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> envoie un <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> à un abonnement enregistré. Il signe le message avec les clés du déploiement et le transmet au service push du fournisseur du navigateur. Ce service réveille le navigateur et affiche la notification. Comme l’opération ne bloque jamais le thread appelant, vous pouvez l’invoquer depuis un écouteur de clic, une tâche planifiée ou un gestionnaire de requêtes.

Une fois les propriétés configurées, l’expéditeur est disponible en tant que bean que vous pouvez injecter dans des vues, des services et des tâches planifiées. Pour le remplacer, définissez votre propre bean `PushSender`.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Sans Spring, `new PushSender()` lit les clés dans la configuration de l’application. Créez l’expéditeur sur un thread de l’application, soit dans une vue, soit dans `App.run()`, puis utilisez-le depuis n’importe quel thread. Tous les expéditeurs partagent un pool de connexions vers les services push : il n’y a donc aucun coût à en créer un là où il est nécessaire.

Pour envoyer des notifications ultérieurement ou après le départ de l’utilisateur, utilisez un minuteur côté serveur, tel que le `TaskScheduler` de Spring. N’utilisez pas de minuteur de page tel que `Interval`, car il s’arrête à la fermeture de l’onglet.

### Composition d’un message {#composing-a-message}

Créez un message avec son titre, puis configurez toutes les autres options avec le builder :

```java
PushMessage message = PushMessage.create("Order shipped")
    .setBody("Order #42 is on its way")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Track", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Sent"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` renvoie immédiatement. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> se termine lorsque le service push accepte le message, ou de façon exceptionnelle si le service ne l’accepte pas. Si `send()` est appelé sur un thread de l’application, par exemple depuis un écouteur, ses rappels s’exécutent sur ce thread et peuvent mettre à jour les composants. Si la session qui a appelé `send()` se termine avant l’arrivée de la réponse, les rappels ne s’exécutent pas, mais la notification est tout de même envoyée.

Un envoi attend jusqu’à 30 secondes la réponse du service push avant d’échouer avec `UNREACHABLE`. Utilisez `setTimeout(Duration)` pour modifier le délai d’attente de chaque expéditeur.

| Option | Effet |
|--------|--------|
| `setBody` | Définit le texte affiché sous le titre |
| `setIcon` | Définit l’image affichée avec la notification. Accepte les URL absolues ainsi que les protocoles `icons://` et `ws://`. Consultez [Ressources](/docs/managing-resources/assets-protocols). Le protocole `context://` n’est pas accepté, car les services push limitent un message à 4 Ko |
| `setUrl` | Définit la page qui s’ouvre lorsque l’utilisateur clique sur la notification. Les URL relatives sont résolues par rapport à la racine de l’application. Si aucune URL n’est définie, la racine de l’application s’ouvre |
| `setActions` | Définit les boutons affichés sur la notification, avec une URL distincte pour chaque bouton. Consultez [Compatibilité des navigateurs](#browser-support) |
| `setTag` | Définit une balise d’identification. Si une notification affichée porte la même balise, la nouvelle notification la remplace |
| `setSilent` | Affiche la notification sans son ni vibration |
| `setTimeToLive` | Définit la durée pendant laquelle le service push conserve le message pour un appareil hors ligne, jusqu’à quatre semaines |
| `setUrgency` | Utilise <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> pour permettre à l’appareil de différer les messages de faible urgence et d’économiser la batterie |
| `setTopic` | Remplace un message encore en attente auprès du service push lorsque les deux messages ont le même sujet. Les sujets peuvent contenir au maximum 32 caractères utilisables dans une URL |

Si un onglet affiche déjà la page, cliquer sur la notification met l’application au premier plan. Sinon, la page s’ouvre dans un nouvel onglet. Cliquer sur un bouton de notification ouvre son URL de la même manière.

:::info Une notification par message
Chaque message affiche une notification. Comme les navigateurs ne réveillent pas une page pour un message qui n’affiche rien, les notifications push ne peuvent pas servir à effectuer des mises à jour de données silencieuses.
:::

## Statut d’échec {#failure-status}

En cas d’échec de `subscribe()` ou de `send()`, le `PendingResult` correspondant signale une `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> en indique la raison :

| Statut | Quand | Que faire |
|--------|------|------------|
| `PERMISSION_DENIED` | L’utilisateur a bloqué les notifications pour l’application | Indiquez à l’utilisateur où autoriser les notifications dans les paramètres du navigateur |
| `UNSUPPORTED` | Le navigateur ne prend pas en charge les notifications push, la page n’est pas dans un contexte sécurisé ou l’application n’est pas déployée en tant que servlet | Masquez cette fonctionnalité |
| `NOT_CONFIGURED` | Au moins une propriété `webforj.push.*` est manquante ou incomplète | Générez les clés et configurez les trois propriétés |
| `SUBSCRIPTION_EXPIRED` | Le service push ne reconnaît plus l’abonnement, car l’utilisateur s’est désabonné ou a réinstallé le navigateur | Supprimez l’abonnement enregistré |
| `REJECTED` | Le service push a rejeté le message ; `getStatusCode()` contient sa réponse | Vérifiez les clés et la taille du message |
| `UNREACHABLE` | Le service push n’a pas répondu avant l’expiration du délai | Réessayez plus tard |
| `UNKNOWN` | L’endpoint enregistré n’est pas une URL valide, ou l’abonnement ou le message n’a pas pu être encodé | Vérifiez l’abonnement enregistré |

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

:::tip L’expiration n’est signalée qu’un message plus tard
Les services push suppriment les abonnements de manière différée. Ils acceptent encore le premier message après le désabonnement d’un utilisateur, mais celui-ci n’arrive nulle part. Le message suivant signale `SUBSCRIPTION_EXPIRED`. Un envoi accepté signifie que le message a atteint le service push, et non que l’utilisateur l’a vu.
:::

## Compatibilité des navigateurs {#browser-support}

Tous les principaux navigateurs de bureau et mobiles affichent les notifications push une fois l’abonnement effectué. Gardez à l’esprit les limitations suivantes :

- Sur iPhone et iPad, les notifications push ne fonctionnent que pour les applications web ajoutées à l’écran d’accueil sous iOS 16.4 ou version ultérieure. Dans un onglet Safari, `subscribe()` signale `UNSUPPORTED`. Consultez [Applications installables](/docs/configuration/installable-apps) pour connaître le manifeste d’application requis.
- Safari n’affiche pas les boutons de notification. Il affiche les messages avec actions sans leurs boutons, mais cliquer sur la notification ouvre toujours l’URL du message.
- Les WebViews Android et iOS n’affichent pas les notifications.

Pour obtenir les détails par navigateur, consultez le [tableau de compatibilité de showNotification](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility) de MDN.

## Exemple complet {#complete-example}

La vue suivante permet d’abonner et de désabonner le navigateur, stocke les abonnements en mémoire et envoie un message à chaque abonnement enregistré. L’envoi peut être immédiat ou différé de huit secondes avec le `TaskScheduler` de Spring, ce qui permet de fermer l’onglet avant l’arrivée de la notification. La classe de l’application utilise `@EnableScheduling` pour rendre le planificateur disponible.

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
  private final Paragraph status = new Paragraph("Checking subscription…");
  private final TextField message = new TextField("Message", "Order #42 is on its way");
  private final Button subscribe =
      new Button("Enable notifications", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Disable notifications");
  private final Button sendNow = new Button("Send now");
  private final Button sendLater = new Button("Send in 8 seconds");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Subscribed");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Notifications are blocked in this browser"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Unsubscribed" : "There was no subscription");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Sending in 8 seconds, close the tab now");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Subscribed" : "Not subscribed");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Sending to " + subscriptions.findAll().size() + " subscriptions");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Orders")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Open home", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Delivered"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("A subscription expired and was removed");
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
