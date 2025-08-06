---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: afeb43fb31ce58db2860ceddd8e8527c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Le composant `InfiniteScroll` dans webforJ charge automatiquement plus de contenu lorsque les utilisateurs font défiler vers le bas, éliminant ainsi le besoin de pagination. Cela crée une expérience fluide pour les listes, les flux et les vues riches en données en chargeant le contenu uniquement lorsque cela est nécessaire.

Lorsque les utilisateurs atteignent le bas du contenu défilable, `InfiniteScroll` déclenche un événement pour charger plus de données. Pendant que le nouveau contenu est en cours de chargement, il affiche un [`Spinner`](../components/spinner) avec un texte personnalisable pour indiquer que d'autres éléments arrivent.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Gestion des états {#state-management}

Le composant `InfiniteScroll` émet des événements et maintient un état interne pour aider à gérer comment et quand le contenu est chargé.

Pour récupérer plus de données lorsque l'utilisateur fait défiler, utilisez la méthode `onScroll()` ou `addScrollListener()` pour enregistrer un écouteur. À l'intérieur de l'écouteur, vous chargez généralement un contenu supplémentaire et appelez `update()` pour actualiser l'état de `InfiniteScroll`.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Élément chargé"));
    infiniteScroll.update();
});
```

Une fois que tout le contenu a été chargé, marquez le défilement comme complet pour empêcher d'autres déclenchements. Après avoir défini l'état sur "complet", n'oubliez pas d'appeler `update()` pour appliquer le nouvel état :

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Cela désactive tout comportement de défilement infini.

:::tip Réinitialiser le drapeau de chargement
Vous pouvez réinitialiser ce drapeau en utilisant `setCompleted(false)` si vous permettez ultérieurement à l'utilisateur de charger plus de contenu (par exemple, après un rafraîchissement).
:::

## Personnalisation de l'indicateur de chargement {#loading-indicator-customization}

Par défaut, `InfiniteScroll` montre un indicateur de chargement intégré - un petit [`Spinner`](../components/spinner) animé avec un texte « Chargement des données ». Vous pouvez changer le texte affiché en passant un message personnalisé au constructeur de `InfiniteScroll` ou en utilisant `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Récupération de plus d'enregistrements...");
infiniteScroll.setText("Chargement de plus d'éléments...");
```

De même, vous pouvez personnaliser l'[`Icône`](../components/icon) affichée pendant le chargement en utilisant `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Personnalisation complète {#full-customization}

Si vous souhaitez remplacer complètement à la fois le [`Spinner`](../components/spinner) et le texte par votre propre balisage,
vous pouvez ajouter du contenu directement dans l'emplacement de contenu spécial en utilisant `addToContent()`.

Lorsque vous remplissez l'emplacement de contenu, cela remplace entièrement la mise en page de chargement par défaut.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Style {#styling}

<TableBuilder name="InfiniteScroll" />
