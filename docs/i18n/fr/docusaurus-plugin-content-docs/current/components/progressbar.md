---
title: ProgressBar
sidebar_position: 90
description: >-
  Visualize task completion with the ProgressBar component, supporting
  determinate and indeterminate modes, orientation, and themes.
_i18n_hash: 47c51276d2b1da6c6bef337f76403515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Le composant `ProgressBar` représente visuellement l'état d'achèvement d'une opération. À mesure que le travail avance, un rectangle se remplit progressivement pour refléter le pourcentage actuel. La barre peut également afficher une représentation textuelle de sa valeur et prend en charge à la fois des états déterminés et indéterminés pour des tâches avec une durée connue ou inconnue.

<!-- INTRO_END -->

## Usages {#usages}

Le composant `ProgressBar` est utile pour visualiser l'état d'achèvement des tâches. Il prend en charge :

- Des valeurs minimales et maximales configurables.
- Un mode indéterminé pour les tâches en cours sans fin définie.
- Des options pour la visibilité du texte, l'animation et des designs rayés pour un meilleur retour visuel.

L'exemple suivant montre une barre de progression rayée et animée avec des contrôles de démarrage, de pause et de réinitialisation :

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Définir les valeurs {#setting-values}

Le composant ProgressBar permet de définir et d'obtenir sa valeur actuelle, ses limites minimales et maximales.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientation {#orientation}

Le `ProgressBar` peut être orienté horizontalement ou verticalement.

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## État indéterminé {#indeterminate-state}

Le `ProgressBar` prend en charge un état indéterminé pour les tâches dont le temps d'achèvement est inconnu.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Texte et visibilité du texte {#text-and-text-visibility}

Par défaut, lorsqu'il est créé, la barre de progression affiche le pourcentage d'achèvement au format `XX%`. En utilisant la méthode `setText()`, vous pouvez utiliser le placeholder `{{x}}` pour obtenir la valeur actuelle sous forme de pourcentage. De plus, vous pouvez utiliser le placeholder `{{value}}` pour obtenir la valeur actuelle brute.

```java
ProgressBar bar = new ProgressBar(15, "Téléchargement : {{x}}%");
```

## Style {#styling}

### Thèmes {#themes}

Le composant `ProgressBar` est livré avec des <JavadocLink type="foundation" location="com/webforj/component/Theme"> thèmes </JavadocLink> intégrés pour un style rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des ProgressBars dans toute une application.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Meilleures pratiques {#best-practices}

- **Utilisez des valeurs minimales et maximales appropriées** : Définissez les valeurs minimales et maximales pour refléter avec précision l'étendue de la tâche.
- **Mettez à jour régulièrement les progrès** : Mettez continuellement à jour la valeur d'avancement pour fournir des retours d'information en temps réel aux utilisateurs.
- **Utilisez l'état indéterminé pour des durées inconnues** : Utilisez l'état indéterminé pour des tâches avec des durées imprévisibles afin d'indiquer un avancement en cours.
- **Affichez du texte pour un meilleur retour d'information aux utilisateurs** : Affichez du texte sur la barre de progression pour offrir un contexte supplémentaire sur l'avancement de la tâche.
