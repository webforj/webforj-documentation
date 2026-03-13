---
sidebar_position: 20
title: Interval
_i18n_hash: a220fb1607867630d6bfc03a1ce5d3e9
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

La classe <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> représente un minuteur qui déclenche un [événement](../building-ui/events) avec un délai fixe entre chaque déclenchement.

La classe `Interval` fournit un moyen simple de déclencher des événements après un délai spécifié. Il est possible de démarrer, arrêter et redémarrer un `Interval` au besoin.
Dans webforJ, un `Interval` offre de meilleures performances par rapport à un minuteur Java standard ou à un minuteur Swing.
Il prend également en charge plusieurs auditeurs pour l'événement écoulé.

## Usages {#usages}
La classe `Interval` déclenche des événements à un délai fixe. En utilisant les Intervals de manière créative, vous pouvez créer des expériences dynamiques et intéressantes dans votre application :

1. **Vérification de l'inactivité** : Affichez un composant [`Dialog`](../components/dialog) s'il n'y a eu aucune interaction sur un formulaire dans un délai donné.

2. **Contenu en vedette** : Faites défiler des articles, des produits ou des promotions en vedette sur votre page d'accueil à chaque Intervalle. Cela rend le contenu dynamique et engageant.

3. **Données en direct** : Rafraîchissez les données sur votre application, telles que les prix des actions, les flux d'actualités ou les mises à jour météorologiques, à chaque Intervalle pour maintenir les données à jour.

## Gestion des états de `Interval` : démarrer, arrêter et redémarrer {#managing-interval-states-starting-stopping-and-restart}
Un Interval nécessite une activation manuelle ; utilisez la méthode `start()` pour l'initier. Pour arrêter un Interval, utilisez la méthode `stop()`. La méthode `restart()` peut être utilisée pour redémarrer l'Interval.

## Ajustement du délai de l'`Interval` {#adjusting-the-interval-delay}

Pour modifier le délai d'un Interval, utilisez la méthode `setDelay(float delay)`. La nouvelle valeur de délai est appliquée après que l'Interval ait été arrêté ou redémarré.

```java
//Changer le délai
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Le délai peut être des secondes fractionnelles à la milliseconde, mais une valeur de délai très petite entraîne un afflux d'événements plus rapidement que le programme ne peut y répondre.
:::

## Ajout d'auditeurs {#adding-listeners}

Vous pouvez attacher des auditeurs supplémentaires à un Interval en utilisant la méthode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`. Une fois qu'un auditeur est ajouté, il se déclenche automatiquement au prochain intervalle si l'Interval est déjà en cours d'exécution.

```java
// Ajout d'auditeurs
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
