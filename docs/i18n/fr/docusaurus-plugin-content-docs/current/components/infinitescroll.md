---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 8c7fc66f78d6508466b5fb9b5dfc3a68
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Le composant `InfiniteScroll` dans webforJ charge automatiquement plus de contenu à mesure que les utilisateurs font défiler vers le bas, éliminant ainsi le besoin de pagination. Cela crée une expérience fluide pour les listes, les flux et les vues riches en données en chargeant le contenu uniquement en cas de besoin.

Lorsque les utilisateurs atteignent le bas du contenu défilable, `InfiniteScroll` déclenche un événement pour charger plus de données. Pendant que le nouveau contenu se charge, il affiche un [`Spinner`](../components/spinner) avec un texte personnalisable pour indiquer qu'il y a plus d'éléments en cours de chargement.

<!-- INTRO_END -->

## Gestion de l'état {#state-management}

Le composant `InfiniteScroll` émet des événements et maintient un état interne pour aider à gérer comment et quand le contenu est chargé.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

Pour récupérer plus de données lorsque l'utilisateur fait défiler, utilisez la méthode `onScroll()` ou `addScrollListener()` pour enregistrer un écouteur. Dans l'écouteur, vous chargez généralement du contenu supplémentaire et appelez `update()` pour actualiser l'état d'`InfiniteScroll`.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Article chargé"));
  infiniteScroll.update();
});
```

Une fois tout le contenu chargé, marquez le défilement comme terminé pour empêcher d'autres déclenchements. Après avoir défini comme terminé, n'oubliez pas d'appeler `update()` pour appliquer le nouvel état :

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Cela désactive le comportement de défilement infini.

:::tip Réinitialiser le Drapeau de Chargement
Vous pouvez réinitialiser ce drapeau en utilisant `setCompleted(false)` si vous permettez ensuite à l'utilisateur de charger plus de contenu (par exemple, après un rafraîchissement).
:::


## Personnalisation de l'indicateur de chargement {#loading-indicator-customization}

Par défaut, `InfiniteScroll` affiche un indicateur de chargement intégré - un petit [`Spinner`](../components/spinner) animé accompagné d'un texte « Chargement des données ». Vous pouvez changer le texte affiché en passant un message personnalisé au constructeur `InfiniteScroll` ou en utilisant `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Récupération de plus d'enregistrements...");
infiniteScroll.setText("Chargement de plus d'éléments...");
```

De même, vous pouvez personnaliser l'[`Icône`](../components/icon) affichée pendant le chargement en utilisant `setIcon()`.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

### Personnalisation complète {#full-customization}

Si vous souhaitez complètement remplacer à la fois le [`Spinner`](../components/spinner) et le texte par votre propre balisage, vous pouvez ajouter du contenu directement dans l'emplacement de contenu spécial en utilisant `addToContent()`.

Lorsque vous remplissez l'emplacement de contenu, il remplace entièrement la mise en page de chargement par défaut.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Style {#styling}

<TableBuilder name="InfiniteScroll" />
