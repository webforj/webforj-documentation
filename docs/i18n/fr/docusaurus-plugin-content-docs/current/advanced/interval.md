---
sidebar_position: 15
title: Interval
_i18n_hash: dc02bb8f8bb43ee67f300071d3ab4ec7
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

La classe <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> représente un minuteur qui déclenche un [événement](../building-ui/events) avec un délai fixe entre chaque déclenchement.

La classe `Interval` fournit un moyen simple de déclencher des événements après un délai spécifié. Il est possible de démarrer, d'arrêter et de redémarrer un `Interval` selon les besoins. De plus, les Intervals peuvent prendre en charge plusieurs auditeurs pour l'événement écoulé.
Optimisé pour le cadre webforJ, il offre de meilleures performances par rapport au minuteur Java standard ou au minuteur Swing.

## Usages {#usages}
La classe `Interval` déclenche un ou plusieurs événements à un délai de temps fixe. En utilisant les Intervals de manière créative, vous pouvez améliorer l'interaction et l'engagement des utilisateurs sur votre site web tout en maintenant l'expérience dynamique et intéressante.

1. **Vérification de l'inactivité** : Affichez un composant [`Dialog`](../components/dialog) si aucune interaction n'a eu lieu sur un formulaire pendant un certain temps.

2. **Contenu en vedette** : Faites défiler des articles, produits ou promotions en vedette sur votre page d'accueil à chaque Interval. Cela rend le contenu dynamique et engageant.

3. **Données en direct** : Rafraîchissez les données de votre application, telles que les prix des actions, les fils d'actualités ou les mises à jour météo, à chaque Interval pour garder les données à jour.

## Gestion des états d'`Interval` : démarrage, arrêt et redémarrage {#managing-interval-states-starting-stopping-and-restart}
Un Interval nécessite une activation manuelle ; utilisez la méthode `start()` pour l'initier. Pour arrêter un Interval, utilisez la méthode `stop()`. La méthode `restart()` peut être utilisée pour redémarrer l'Interval.

## Ajustement du délai de l'`Interval` {#adjusting-the-interval-delay}

Pour modifier le délai d'un Interval, utilisez la méthode `setDelay(float delay)`. La nouvelle valeur de délai est appliquée après que l'Interval est soit arrêté, soit redémarré.

```java
//Changer le Délai
Interval.setDelay(2f);
Interval.restart();
```

:::tip
Le délai peut être en secondes fractionnaires avec une résolution en millisecondes, mais une valeur de délai très petite provoque une inondation d'événements plus rapide que le programme ne peut y répondre.
:::

## Ajout de listeners {#adding-listeners}

Vous pouvez attacher des auditeurs supplémentaires à un Interval en utilisant la méthode `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`. Une fois qu'un auditeur est ajouté, il se déclenche automatiquement lors de l'intervalle suivant si l'Interval est déjà en cours d'exécution.

```java
// Ajout de Listeners
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
