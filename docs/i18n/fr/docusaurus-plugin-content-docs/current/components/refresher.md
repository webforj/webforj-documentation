---
title: Refresher
sidebar_position: 101
_i18n_hash: 99793e9f95d4c5a052014f677aa8a6cb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Le pull-to-refresh est un motif courant dans les interfaces mobiles et adaptées aux tactiles, et le composant `Refresher` l'apporte aux conteneurs défilables dans webforJ. Lorsque les utilisateurs glissent vers le bas au-delà d'un seuil configurable, il passe par des états visuels : `pull`, `release` et `refreshing`, chacun avec une icône personnalisable et un texte localisé. Il s'associe bien avec [`InfiniteScroll`](../components/infinitescroll) pour recharger ou réinitialiser le contenu par le biais d'entrées Gestuelles.

<!-- INTRO_END -->

## Instantiation et internationalisation {#instantiation-and-internationalization}

Ajoutez un `Refresher` en l'instanciant et en enregistrant un écouteur de rafraîchissement. Lorsque les opérations de rafraîchissement sont terminées, appelez `finish()` pour réinitialiser le composant à son état inactif.

:::info Comment activer le `Refresher`
Pour activer le `Refresher`, **cliquez et faites glisser vers le bas** depuis le haut de la zone défilable. Bien que ce geste soit familier sur mobile, il est moins courant sur bureau—assurez-vous de maintenir et tirer avec votre souris.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

Cette approche est couramment utilisée pour rafraîchir des listes paginées ou redémarrer le chargement infini.

### Internationalisation {#internationalization}

Chaque étiquette d'état peut également être localisée à l'aide de l'objet `RefresherI18n`. Les trois états sont :

- Pull : Texte de geste initial (par exemple, "Tirez vers le bas pour rafraîchir")
- Release : Seuil déclencheur atteint (par exemple, "Relâchez pour rafraîchir")
- Refresh : État de chargement (par exemple, "Rafraîchissement")

Cela permet un support multilingue et des ajustements de marque au besoin.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Personnalisation des icônes {#icon-customization}

Vous pouvez changer les [`Icônes`](../components/icon) utilisées pour les étapes `pull`/`release` et `refreshing` en utilisant soit une [`Icône`](../components/icon) prédéfinie, soit une [URL d'icône](../managing-resources/assets-protocols). Celles-ci sont utiles lorsque vous souhaitez appliquer une marque ou une animation personnalisée.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Configuration du comportement de tirage {#pull-behavior-configuration}

### Seuil {#threshold}

Définissez jusqu'où l'utilisateur doit tirer vers le bas (en pixels) avant de déclencher le rafraîchissement :

```java
refresher.setThreshold(80); // par défaut : 80px
```

### Maximum de seuil {#threshold-maximum}

Pour définir la distance de tirage maximale autorisée, utilisez la méthode `setThresholdMax()` :

```java
refresher.setThresholdMax(160);
```

Ces seuils contrôlent la sensibilité du geste et la courbe de résistance.

## Gestion de l'état {#state-management}

Le composant `Refresher` maintient son propre état interne et communique les changements d'état par le biais d'événements. Lorsqu'un utilisateur tire vers le bas au-delà du seuil défini, le `Refresher` émet un événement de rafraîchissement auquel vous pouvez répondre en enregistrant un écouteur `onRefresh()`.

À l'intérieur de cet écouteur, vous êtes censé effectuer l'opération requise—comme extraire de nouvelles données ou réinitialiser une liste—et ensuite appeler explicitement :

```java
refresher.finish();
```
:::warning Manquant `finish()`
Si vous oubliez d'appeler `finish()`, le réfresh restera indéfiniment dans l'état de chargement.
:::

Vous pouvez également désactiver le `Refresher` par programme à tout moment pour empêcher l'utilisateur de déclencher le comportement de rafraîchissement :

```java
refresher.setEnabled(false);
```

Cela est utile lorsque les rafraîchissements doivent être temporairement interdits—par exemple, pendant un écran de chargement ou pendant qu'un autre processus critique est en cours d'exécution.

## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Refresher` prend en charge plusieurs thèmes pour distinguer visuellement les différents états ou pour s'adapter à l'apparence et à la sensation de votre application. Les thèmes peuvent être appliqués à l'aide de la méthode `setTheme()`.

L'échantillon suivant fait défiler à travers tous les thèmes disponibles chaque fois que vous tirez pour rafraîchir, vous donnant un aperçu en direct de l'apparence du `Refresher` à travers différents thèmes :

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
