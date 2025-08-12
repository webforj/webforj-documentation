---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 9b2f9ec23124d60ab5f8fca18e561acb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Le ProgressBar est un composant qui affiche visuellement l'avancement d'une tâche. À mesure que la tâche progresse vers son achèvement, la barre de progression affiche le pourcentage d'achèvement de la tâche. Ce pourcentage est représenté visuellement par un rectangle qui commence vide et se remplit progressivement à mesure que la tâche avance. De plus, la barre de progression peut afficher une représentation textuelle de ce pourcentage.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Usages {#usages}

Le composant `ProgressBar` est utile pour visualiser l'état d'achèvement des tâches. Il prend en charge :

- Des valeurs minimales et maximales configurables.
- Un mode indéterminé pour les tâches en cours sans fin définie.
- Des options pour la visibilité du texte, l'animation et des designs rayés pour un meilleur retour visuel.

## Setting values {#setting-values}

Le composant ProgressBar permet de définir et d'obtenir sa valeur actuelle, ses limites minimales et maximales.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientation {#orientation}

La `ProgressBar` peut être orientée horizontalement ou verticalement.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Indeterminate state {#indeterminate-state}

La `ProgressBar` prend en charge un état indéterminé pour les tâches dont le temps de complétion est inconnu.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Text and text visibility {#text-and-text-visibility}

Par défaut, lorsque créé, la barre de progression affiche le pourcentage d'achèvement au format `XX%`. En utilisant la méthode `setText()`, vous pouvez utiliser le placeholder `{{x}}` pour obtenir la valeur actuelle en pourcentage. De plus, vous pouvez utiliser le placeholder `{{value}}` pour obtenir la valeur actuelle brute.

```java
ProgressBar bar = new ProgressBar(15, "Téléchargement : {{x}}%");
```

## Styling {#styling}

### Themes {#themes}

Le composant `ProgressBar` est livré avec des <JavadocLink type="foundation" location="com/webforj/component/Theme"> thèmes </JavadocLink> intégrés pour un stylisme rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour modifier leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des ProgressBars dans toute une application.

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best practices {#best-practices}

- **Utiliser des valeurs minimales et maximales appropriées** : Définissez les valeurs minimales et maximales pour refléter avec précision l'intervalle de la tâche.
- **Mettre à jour régulièrement le progrès** : Mettez continuellement à jour la valeur de progression pour fournir un retour d'information en temps réel aux utilisateurs.
- **Utiliser l'état indéterminé pour des durées inconnues** : Utilisez l'état indéterminé pour les tâches dont les durées sont imprévisibles afin d'indiquer un progrès en cours.
- **Afficher du texte pour un meilleur retour d'utilisateur** : Affichez du texte sur la barre de progression pour offrir un contexte supplémentaire sur l'avancement de la tâche.
