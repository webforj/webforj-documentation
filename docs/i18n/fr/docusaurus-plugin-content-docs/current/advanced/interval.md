---
sidebar_position: 20
title: Interval
_i18n_hash: 1fd4c3fc2bf38df65a68d909a6ff77a3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

La classe <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> représente un minuteur qui déclenche un [événement](../building-ui/events) avec un délai fixe entre chaque déclenchement.

Vous pouvez [démarrer, arrêter et redémarrer](#managing-interval-states-starting-stopping-and-restart) un `Interval` selon vos besoins et ajouter plusieurs [écouteurs](#adding-listeners) pour l'événement écoulé.
Dans webforJ, un `Interval` présente des [performances supérieures](#performance) par rapport à un minuteur Java standard ou un minuteur Swing.

## Usages {#usages}
La classe `Interval` déclenche des événements à un délai fixe. En utilisant les Intervals de manière créative, vous pouvez créer des expériences dynamiques et intéressantes dans votre application :

1. **Vérifier l'inactivité** : Afficher un composant [`Dialog`](../components/dialog) s'il n'y a eu aucune interaction sur un formulaire pendant un certain temps.

2. **Contenu en vedette** : Faire défiler des articles, des produits ou des promotions en vedette sur votre page d'accueil à chaque Interval. Cela garde le contenu dynamique et engageant.

3. **Données en direct** : Rafraîchir les données de votre application, telles que les prix des actions, les flux d'actualités ou les mises à jour météo, à chaque Interval pour garder les données actuelles.

## Gestion des états de `Interval` : démarrer, arrêter et redémarrer {#managing-interval-states-starting-stopping-and-restart}
Un Interval nécessite une activation manuelle ; utilisez la méthode `start()` pour l'initier. Pour arrêter un Interval, utilisez la méthode `stop()`. La méthode `restart()` peut être utilisée pour redémarrer l'Interval.

## Ajustement du délai de `Interval` {#adjusting-the-interval-delay}

Pour modifier le délai d'un Interval, utilisez la méthode `setDelay(float delay)`. La nouvelle valeur de délai est appliquée après que l'Interval est soit arrêté, soit redémarré.

```java
//Changement du Délai
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Le délai peut être une fraction de seconde à la milliseconde en résolution, mais une valeur de délai très faible provoque un afflux d'événements plus rapide que le programme ne peut y répondre.
:::

## Ajout d'écouteurs {#adding-listeners}

Vous pouvez attacher des écouteurs supplémentaires à un Interval en utilisant la méthode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`. Une fois qu'un écouteur est ajouté, il se déclenche automatiquement au prochain intervalle si l'Interval est déjà en cours d'exécution.

```java
// Ajout d'Écouteurs
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

## Performances {#performance}

La classe `Interval` est spécifiquement conçue pour offrir de meilleures performances et fiabilité pour les charges importantes rencontrées par les applications web.
Dans Java Swing, le même comportement peut être géré de manière suffisamment efficace par un `Timer` ou un nouveau thread, mais cette approche ne s'adapte pas bien aux applications web.
Les applications web sont susceptibles d'avoir de nombreux utilisateurs concurrents, et si chaque utilisateur crée un nouveau Timer ou thread, le système peut rapidement s'effondrer lorsqu'il manque de threads.

Il existe plusieurs options viables qui fonctionnent à cette échelle : [**threads virtuels**](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html), [**Spring TaskExecutor et TaskScheduler**](https://docs.spring.io/spring-framework/reference/integration/scheduling.html), et **`Interval`**.
En fonction de votre application et de votre cas d'utilisation, l'une de ces options peut être la meilleure pour vous.
Par défaut, `Interval` est un choix fiable spécifiquement conçu pour fonctionner avec webforJ, et ne nécessite aucune configuration supplémentaire.
