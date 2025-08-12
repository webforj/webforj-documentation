---
title: Refresher
sidebar_position: 101
_i18n_hash: 77c3e72a5a59a55d61a7dba79efb7324
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Le composant `Refresher` dans webforJ permet une interaction de tirage pour rafraîchir au sein de conteneurs défilables—idéal pour le chargement dynamique de données dans des interfaces mobiles ou adaptées au toucher. Lorsqu'un utilisateur fait glisser vers le bas au-delà d'un seuil configurable, le rafraîchisseur passe par des états visuels : `pull`, `release`, et `refreshing`. Chaque état présente une icône personnalisable et un texte localisé pour communiquer clairement un retour d'information.

Vous pouvez utiliser `Refresher` avec des composants comme [`InfiniteScroll`](../components/infinitescroll) pour recharger du contenu ou réinitialiser l'état via une simple entrée gestuelle. Le composant est entièrement configurable en ce qui concerne le comportement d'interaction, l'apparence, la localisation, et l'intégration avec le reste de votre interface utilisateur.

## Instantiation et internationalisation {#instantiation-and-internationalization}

Ajoutez un `Refresher` en l'instanciant et en enregistrant un écouteur de rafraîchissement. Lorsque les opérations de rafraîchissement sont terminées, appelez `finish()` pour réinitialiser le composant à son état inactif.

:::info Comment activer le `Refresher`
Pour activer le `Refresher`, **cliquez et faites glisser vers le bas** depuis le haut de la zone défilable. Bien que ce geste soit familier sur mobile, il est moins courant sur bureau—veillez à maintenir et tirer avec votre souris.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Cette approche est couramment utilisée pour rafraîchir des listes paginées ou redémarrer le chargement infini.

### Internationalisation {#internationalization}

Chaque étiquette d'état peut également être localisée en utilisant l'objet `RefresherI18n`. Les trois états sont :

- Pull : Texte du geste initial (par exemple, "Tirez vers le bas pour rafraîchir")
- Release : Seuil de déclenchement atteint (par exemple, "Relâchez pour rafraîchir")
- Refresh : État de chargement (par exemple, "Rafraîchissement en cours")

Cela permet un support multilingue et des ajustements de marque si nécessaire.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Personnalisation des icônes {#icon-customization}

Vous pouvez changer les [`Icônes`](../components/icon) utilisées pour les étapes `pull`/`release` et `refreshing` en utilisant soit une [`Icône`](../components/icon) prédéfinie, soit une [URL d'icône](../managing-resources/assets-protocols). Celles-ci sont utiles lorsque vous souhaitez appliquer un branding ou une animation personnalisée.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Configuration du comportement de tirage {#pull-behavior-configuration}

### Seuil {#threshold}

Définissez la distance que l'utilisateur doit tirer vers le bas (en pixels) avant de déclencher le rafraîchissement :

```java
refresher.setThreshold(80); // par défaut : 80px
```

### Seuil maximum {#threshold-maximum}

Pour définir la distance de tirage maximale autorisée, utilisez la méthode `setThresholdMax()` :

```java
refresher.setThresholdMax(160);
```

Ces seuils contrôlent la sensibilité du geste et la courbe de résistance.

## Gestion de l'état {#state-management}

Le composant `Refresher` maintient son propre état interne et communique les changements d'état par le biais d'événements. Lorsqu'un utilisateur tire vers le bas au-delà du seuil défini, le `Refresher` émet un événement de rafraîchissement auquel vous pouvez répondre en enregistrant un écouteur `onRefresh()`.

À l'intérieur de cet écouteur, vous êtes censé effectuer les opérations requises—comme récupérer de nouvelles données ou réinitialiser une liste—et ensuite appeler explicitement :

```java
refresher.finish();
```
:::warning Manque `finish()`
Si vous oubliez d'appeler `finish()`, le rafraîchisseur restera dans l'état de chargement indéfiniment.
:::

Vous pouvez également désactiver le `Refresher` par programme à tout moment pour empêcher l'utilisateur de déclencher un comportement de rafraîchissement :

```java
refresher.setEnabled(false);
```

Cela est utile lorsque les rafraîchissements doivent être temporairement interdits—par exemple, pendant un écran de chargement ou pendant qu'un autre processus critique est en cours d'exécution.

## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Refresher` prend en charge plusieurs thèmes pour distinguer visuellement différents états ou pour s'adapter à l'apparence de votre application. Les thèmes peuvent être appliqués à l'aide de la méthode `setTheme()`.

L'exemple suivant passe en revue tous les thèmes disponibles chaque fois que vous tirez pour rafraîchir, vous donnant un aperçu en direct de l'apparence du `Refresher` à travers différents thèmes :

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
