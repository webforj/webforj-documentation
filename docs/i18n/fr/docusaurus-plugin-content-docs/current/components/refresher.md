---
title: Refresher
sidebar_position: 101
_i18n_hash: 763037d616f2274feb7a7ed24b9c91f0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Le tirage pour rafraîchir est un modèle courant dans les interfaces mobiles et adaptées au toucher, et le composant `Refresher` l'apporte aux conteneurs défilables dans webforJ. Lorsque les utilisateurs glissent vers le bas au-delà d'un seuil configurable, il passe par des états visuels : `pull`, `release`, et `refreshing`, chacun avec une icône personnalisable et un texte localisé. Il se combine bien avec [`InfiniteScroll`](../components/infinitescroll) pour recharger ou réinitialiser le contenu via une entrée gestuelle.

<!-- INTRO_END -->

## Instanciation et internationalisation {#instantiation-and-internationalization}

Ajoutez un `Refresher` en l'instanciant et en enregistrant un écouteur de rafraîchissement. Lorsque les opérations de rafraîchissement sont complétées, appelez `finish()` pour réinitialiser le composant à son état inactif.

:::info Comment activer le `Refresher`
Pour activer le `Refresher`, **cliquez et glissez vers le bas** depuis le haut de la zone défilable. Bien que ce geste soit familier sur mobile, il est moins courant sur desktop—assurez-vous de maintenir et de tirer avec votre souris.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Cette approche est couramment utilisée pour rafraîchir des listes paginées ou redémarrer le chargement d'un défilement infini.

### Internationalisation {#internationalization}

Chaque étiquette d'état peut également être localisée en utilisant l'objet `RefresherI18n`. Les trois états sont :

- Pull : Texte du geste initial (par ex., "Tirez vers le bas pour rafraîchir")
- Release : Seuil de déclenchement atteint (par ex., "Relâchez pour rafraîchir")
- Refresh : État de chargement (par ex., "Rafraîchissement")

Cela permet un support multilingue et des ajustements de marque au besoin.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Personnalisation des icônes {#icon-customization}

Vous pouvez changer les [`Icons`](../components/icon) utilisés pour les étapes `pull`/`release` et `refreshing` en utilisant soit une [`Icon`](../components/icon) prédéfinie, soit une [URL d'icône](../managing-resources/assets-protocols). Celles-ci sont utiles lorsque vous souhaitez appliquer une marque ou une animation personnalisée.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Configuration du comportement de tirage {#pull-behavior-configuration}

### Seuil {#threshold}

Définissez combien l'utilisateur doit tirer vers le bas (en pixels) avant de déclencher le rafraîchissement :

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

Le composant `Refresher` maintient son propre état interne et communique les changements d'état par le biais d'événements. Lorsque l'utilisateur tire vers le bas au-delà du seuil défini, le `Refresher` émet un événement de rafraîchissement auquel vous pouvez répondre en enregistrant un écouteur `onRefresh()`.

À l'intérieur de cet écouteur, vous êtes censé effectuer l'opération requise—comme récupérer de nouvelles données ou réinitialiser une liste—et ensuite appeler explicitement :

```java
refresher.finish();
```
:::warning `finish()` manquant
Si vous oubliez d'appeler `finish()`, le rafraîchisseur restera indéfiniment dans l'état de chargement.
:::

Vous pouvez également désactiver le `Refresher` par programme à tout moment pour empêcher l'utilisateur de déclencher un comportement de rafraîchissement :

```java
refresher.setEnabled(false);
```

Ceci est utile lorsque les rafraîchissements doivent être temporairement interdits—par exemple, lors d'un écran de chargement ou pendant qu'un autre processus critique est en cours d'exécution.

## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Refresher` prend en charge plusieurs thèmes pour distinguer visuellement différents états ou correspondre à l'apparence et à la sensation de votre application. Les thèmes peuvent être appliqués à l'aide de la méthode `setTheme()`.

L'exemple suivant fait défiler tous les thèmes disponibles chaque fois que vous tirez pour rafraîchir, vous donnant un aperçu en direct de l'apparence du `Refresher` à travers différents thèmes :

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
