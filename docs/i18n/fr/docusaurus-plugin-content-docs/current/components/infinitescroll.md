---
title: InfiniteScroll
sidebar_position: 60
description: >-
  Load more content as users scroll with the InfiniteScroll component, emitting
  scroll events and showing a customizable loading spinner.
_i18n_hash: f021168e8d6187e38da9107bd2f3ad65
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Le composant `InfiniteScroll` dans webforJ charge automatiquement plus de contenu à mesure que les utilisateurs font défiler vers le bas, éliminant ainsi le besoin de pagination. Cela crée une expérience fluide pour les listes, les fils d'actualité et les vues riches en données en ne chargeant le contenu que lorsque cela est nécessaire.

Lorsque les utilisateurs atteignent le bas du contenu défilable, `InfiniteScroll` déclenche un événement pour charger plus de données. Pendant que le nouveau contenu charge, il affiche un [`Spinner`](../components/spinner) avec un texte personnalisable pour indiquer que d'autres éléments sont en route.

<!-- INTRO_END -->

## Gestion des états {#state-management}

Le composant `InfiniteScroll` émet des événements et maintient un état interne pour aider à gérer comment et quand le contenu est chargé.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

Pour récupérer plus de données lorsque l'utilisateur fait défiler, utilisez la méthode `onScroll()` ou `addScrollListener()` pour enregistrer un écouteur. À l'intérieur de l'écouteur, vous chargez généralement du contenu supplémentaire et appelez `update()` pour rafraîchir l'état de `InfiniteScroll`.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Élément chargé"));
  infiniteScroll.update();
});
```

Une fois que tout le contenu a été chargé, marquez le défilement comme complet pour empêcher d'autres déclenchements. Après avoir défini comme complet, n'oubliez pas d'appeler `update()` pour appliquer le nouvel état :

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Cela désactive le comportement de défilement infini.

:::tip Réinitialiser le drapeau de chargement
Vous pouvez réinitialiser ce drapeau en utilisant `setCompleted(false)` si vous permettez plus tard à l'utilisateur de charger plus de contenu (par exemple, après un rafraîchissement).
:::


## Personnalisation de l'indicateur de chargement {#loading-indicator-customization}

Par défaut, `InfiniteScroll` affiche un indicateur de chargement intégré - un petit [`Spinner`](../components/spinner) animé accompagné d'un texte "Chargement des données". Vous pouvez changer le texte affiché en passant un message personnalisé au constructeur `InfiniteScroll` ou en utilisant `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Récupération de plus d'enregistrements...");
infiniteScroll.setText("Chargement de plus d'éléments...");
```

De même, vous pouvez personnaliser l[`Icon`](../components/icon) affichée pendant le chargement en utilisant `setIcon()`.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

### Personnalisation totale {#full-customization}

Si vous souhaitez remplacer complètement à la fois le [`Spinner`](../components/spinner) et le texte par votre propre balisage,
vous pouvez ajouter du contenu directement dans le slot de contenu spécial en utilisant `addToContent()`.

Lorsque vous remplissez le slot de contenu, il remplace entièrement la mise en page de chargement par défaut.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Style {#styling}

<TableBuilder name="InfiniteScroll" />
