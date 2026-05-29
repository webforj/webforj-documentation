---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 6acac582ce905eb255ee09e499fd561f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Le composant `ProgressBar` représente visuellement l'état d'achèvement d'une opération. À mesure que le travail avance, un rectangle se remplit progressivement pour refléter le pourcentage actuel. La barre peut également afficher une représentation textuelle de sa valeur et prend en charge des états déterminés et indéterminés pour les tâches avec une durée connue ou inconnue.

<!-- INTRO_END -->

## Usages {#usages}

Le composant `ProgressBar` est utile pour visualiser l'état d'achèvement des tâches. Il permet :

- Des valeurs minimum et maximum configurables.
- Un mode indéterminé pour les tâches en cours sans fin définie.
- Des options de visibilité du texte, d'animation et de designs rayés pour un meilleur retour visuel.

L'exemple suivant montre une barre de progression rayée et animée avec des contrôles de démarrage, de pause et de réinitialisation :

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Setting values {#setting-values}

Le composant ProgressBar permet de définir et d’obtenir sa valeur actuelle, ainsi que ses limites minimales et maximales.

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

## Indeterminate state {#indeterminate-state}

Le `ProgressBar` prend en charge un état indéterminé pour les tâches dont le temps d'achèvement est inconnu.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Text and text visibility {#text-and-text-visibility}

Par défaut, lors de sa création, la barre de progression affiche le pourcentage d'achèvement au format `XX%`. En utilisant la méthode `setText()`, vous pouvez utiliser l'espace réservé `{{x}}` pour obtenir la valeur actuelle en pourcentage. De plus, vous pouvez utiliser l'espace réservé `{{value}}` pour obtenir la valeur brute actuelle.

```java
ProgressBar bar = new ProgressBar(15, "Téléchargement : {{x}}%");
```

## Styling {#styling}

### Themes {#themes}

Le composant `ProgressBar` est fourni avec des <JavadocLink type="foundation" location="com/webforj/component/Theme"> thèmes </JavadocLink> intégrés pour un style rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent une manière rapide et cohérente de personnaliser l'apparence des ProgressBars dans une application.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best practices {#best-practices}

- **Utiliser des valeurs minimales et maximales appropriées** : Définissez les valeurs minimales et maximales pour refléter avec précision l'étendue de la tâche.
- **Mettre à jour régulièrement la progression** : Mettez continuellement à jour la valeur de progression pour fournir un retour en temps réel aux utilisateurs.
- **Utiliser l'état indéterminé pour les durées inconnues** : Utilisez l'état indéterminé pour les tâches dont les durées sont imprévisibles afin d'indiquer une progression en cours.
- **Afficher du texte pour un meilleur retour utilisateur** : Affichez du texte sur la barre de progression pour offrir un contexte supplémentaire sur l'avancement de la tâche.
