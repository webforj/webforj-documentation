---
sidebar_position: 15
title: Interval
_i18n_hash: 07054545ea64670e83423a6b11a5cce3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

La classe <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> représente un minuteur qui déclenche un [événement](../building-ui/events) avec un délai de temps fixe entre chaque déclenchement.

La classe `Interval` fournit un moyen simple de déclencher des événements après un délai spécifié. Il est possible de démarrer, d'arrêter et de redémarrer un `Interval` selon les besoins. De plus, les Intervals peuvent prendre en charge plusieurs auditeurs pour l'événement écoulé. Optimisé pour le framework webforJ, il offre de meilleures performances par rapport au minuteur Java standard ou au minuteur Swing.

## Usages {#usages}
La classe `Interval` déclenche des événements avec un délai de temps fixe. En tirant parti des Intervals de manière créative, vous pouvez améliorer l'interaction et l'engagement des utilisateurs sur votre site Web tout en gardant l'expérience dynamique et intéressante :

1. **Vérifier l'Inactivité** : Afficher un composant [`Dialog`](../components/dialog) s'il n'y a eu aucune interaction sur un formulaire dans un délai donné.

2. **Contenu en Vedette** : Faire défiler des articles, des produits ou des promotions en vedette sur votre page d'accueil à chaque Intervalle. Cela garde le contenu dynamique et engageant.

3. **Données en Direct** : Actualiser les données de votre application, telles que les prix des actions, les flux d'actualités ou les mises à jour météorologiques, à chaque Intervalle pour garder les données à jour.

## Gestion des états de l'`Interval` : démarrer, arrêter et redémarrer {#managing-interval-states-starting-stopping-and-restart}
Un Interval nécessite une activation manuelle ; utilisez la méthode `start()` pour l'initier. Pour arrêter un Interval, utilisez la méthode `stop()`. La méthode `restart()` peut être utilisée pour redémarrer l'Interval.

## Ajustement du délai de l'`Interval` {#adjusting-the-interval-delay}

Pour modifier le délai d'un Interval, utilisez la méthode `setDelay(float delay)`. La nouvelle valeur de délai est appliquée après que l'Intervalle soit soit arrêté, soit redémarré.

```java
//Changement du Délai
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Le délai peut être des secondes fractionnaires à une résolution milliseconde, mais une valeur d'attente très petite entraîne un flux d'événements plus rapide que le programme ne peut y répondre.
:::

## Ajout d'auditeurs {#adding-listeners}

Vous pouvez attacher des auditeurs supplémentaires à un Interval en utilisant la méthode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`. Une fois qu'un auditeur est ajouté, il se déclenche automatiquement lors du prochain intervalle si l'Interval est déjà en cours d'exécution.

```java
// Ajout d'Auditeurs
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Code exécutable
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Code exécutable
});

interval.addElapsedListener(secondListener);
```
