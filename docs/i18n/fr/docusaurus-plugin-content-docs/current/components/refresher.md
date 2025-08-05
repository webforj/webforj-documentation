---
title: Refresher
sidebar_position: 101
_i18n_hash: de00fad980f74bdd18544409408de0b8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Le composant `Refresher` dans webforJ permet une interaction de tirage pour actualiser dans des conteneurs défilables, idéal pour le chargement de données dynamiques dans des interfaces mobiles ou faciles à tactiles. À mesure que les utilisateurs glissent vers le bas au-delà d'un seuil configurable, le refresher passe par des états visuels : `pull`, `release`, et `refreshing`. Chaque état présente une icône personnalisable et un texte localisé pour communiquer clairement les retours.

Vous pouvez utiliser `Refresher` en tandem avec des composants comme [`InfiniteScroll`](../components/infinitescroll) pour recharger le contenu ou réinitialiser l'état via une entrée gestuelle simple. Le composant est entièrement configurable en termes de comportement d'interaction, d'apparence, de localisation, et d'intégration avec le reste de votre interface utilisateur.

## Instantiation et internationalisation {#instantiation-and-internationalization}

Ajoutez un `Refresher` en l'instanciant et en enregistrant un écouteur de rafraîchissement. Lorsque les opérations de rafraîchissement sont terminées, appelez `finish()` pour réinitialiser le composant à son état de repos.

:::info Comment activer le `Refresher`
Pour activer le `Refresher`, **cliquez et faites glisser vers le bas** depuis le haut de la zone défilable. Alors que ce geste est familier sur mobile, il est moins courant sur desktop—assurez-vous de maintenir et de tirer avec votre souris.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Cette approche est couramment utilisée pour rafraîchir des listes paginées ou redémarrer le chargement de défilement infini.

### Internationalisation {#internationalization}

Chaque étiquette d'état peut également être localisée à l'aide de l'objet `RefresherI18n`. Les trois états sont :

- Pull : Texte du geste initial (par exemple, "Tirez vers le bas pour rafraîchir")
- Release : Seuil de déclenchement atteint (par exemple, "Relâchez pour rafraîchir")
- Refresh : État de chargement (par exemple, "Rafraîchissement")

Cela permet un support multilingue et des ajustements de marque si nécessaire.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Personnalisation des icônes {#icon-customization}

Vous pouvez changer les [`Icons`](../components/icon) utilisés pour les étapes `pull`/`release` et `refreshing` en utilisant soit une [`Icon`](../components/icon) prédéfinie ou une [Icon URL](../managing-resources/assets-protocols). Ceux-ci sont utiles lorsque vous souhaitez appliquer une marque ou une animation personnalisée.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Configuration du comportement de tirage {#pull-behavior-configuration}

### Seuil {#threshold}

Définissez jusqu'où l'utilisateur doit tirer vers le bas (en pixels) avant de déclencher le rafraîchissement :

```java
refresher.setThreshold(80); // par défaut : 80px
```

### Maximum du seuil {#threshold-maximum}

Pour définir la distance de tirage maximale autorisée, utilisez la méthode `setThresholdMax()` :

```java
refresher.setThresholdMax(160);
```

Ces seuils contrôlent la sensibilité du geste et la courbe de résistance.

## Gestion des états {#state-management}

Le composant `Refresher` maintient son propre état interne et communique les changements d'état par le biais d'événements. Lorsqu'un utilisateur tire vers le bas au-delà du seuil défini, le `Refresher` émet un événement de rafraîchissement auquel vous pouvez répondre en enregistrant un écouteur `onRefresh()`.

À l'intérieur de cet écouteur, vous êtes censé effectuer l'opération requise—comme le chargement de nouvelles données ou la réinitialisation d'une liste—et ensuite appeler explicitement :

```java
refresher.finish();
```
:::warning Manque de `finish()`
Si vous oubliez d'appeler `finish()`, le refresher restera dans l'état de chargement indéfiniment.
:::

Vous pouvez également désactiver le `Refresher` par programme à tout moment pour empêcher l'utilisateur de déclencher le comportement de rafraîchissement :

```java
refresher.setEnabled(false);
```

Ceci est utile lorsque les rafraîchissements doivent être temporairement interdits—par exemple, pendant un écran de chargement ou lorsqu'un autre processus critique est en cours d'exécution.

## Stylistique {#styling}

### Thèmes {#themes}

Le composant `Refresher` prend en charge plusieurs thèmes pour distinguer visuellement les différents états ou pour correspondre à l'apparence de votre application. Les thèmes peuvent être appliqués en utilisant la méthode `setTheme()`.

L'exemple suivant fait défiler tous les thèmes disponibles chaque fois que vous tirez pour rafraîchir, vous offrant un aperçu en direct de l'apparence du `Refresher` à travers différents thèmes :

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
