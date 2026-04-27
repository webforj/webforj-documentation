---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: dcbc11ba7581a82ae6857abfe11a62c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

Le composant `MarkdownViewer` rend du texte markdown en HTML. Il prend en charge la syntaxe markdown standard, y compris les en-têtes, listes, blocs de code, liens, images et rendu d'emoji. Le composant offre également un rendu progressif, qui affiche le contenu caractère par caractère pour un effet machine à écrire.

## Configuration du contenu {#setting-content}

Créez un `MarkdownViewer` avec ou sans contenu initial, puis mettez-le à jour en utilisant `setContent()` :

```java
MarkdownViewer viewer = new MarkdownViewer("# Bonjour le monde");

// Remplacer le contenu entièrement
viewer.setContent("""
    ## Nouveau contenu

    - Élément 1
    - Élément 2
    """);

// Obtenir le contenu actuel
String content = viewer.getContent();
```
:::tip
Le composant implémente `HasText`, donc `setText()` et `getText()` fonctionnent comme des alias pour les méthodes de contenu.
:::
<ComponentDemo 
path='/webforj/markdownviewer?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java'
height='650px'
/>

## Ajout de contenu {#appending-content}

La méthode `append()` ajoute du contenu par incréments sans remplacer ce qui est déjà là :

```java
viewer.append("## Nouvelle section\n\n");
viewer.append("Encore du contenu ici...");
```

Par défaut, le contenu ajouté apparaît immédiatement. Lorsque le [rendu progressif](#progressive-rendering) est activé, le contenu ajouté va dans un tampon et s'affiche caractère par caractère à la place.

## Défilement automatique {#auto-scroll}

Activez le défilement automatique pour maintenir la vue à la fin au fur et à mesure que le contenu grandit. Cela fonctionne avec n'importe quelle méthode d'ajout de contenu, que ce soit `setContent()`, `append()`, ou le rendu progressif. Si un utilisateur fait défiler manuellement vers le haut pour consulter un contenu antérieur, le défilement automatique se met en pause et reprend lorsqu'il fait défiler à nouveau vers le bas.

```java
viewer.setAutoScroll(true);
```

## Rendu progressif {#progressive-rendering}

Le rendu progressif affiche le contenu caractère par caractère plutôt que tout à la fois, créant un effet de machine à écrire. Les interfaces de chat AI utilisent souvent ceci pour afficher des réponses apparaissant progressivement :

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Une fois activé, le contenu ajouté via `setContent()` ou `append()` va dans un tampon et s'affiche progressivement. Lorsqu'il est désactivé, le contenu apparaît immédiatement.

<ComponentDemo 
path='/webforj/markdownviewerprogressive?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java'
height='650px'
/>

### Vitesse de rendu {#render-speed}

La méthode `setRenderSpeed()` contrôle combien de caractères se rendent par image d'animation. Des valeurs plus élevées signifient un rendu plus rapide. À 60fps, la vitesse par défaut de 4 se traduit par environ 240 caractères par seconde :

| Vitesse | Caractères/seconde |
|---------|---------------------|
| 4 (par défaut) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Correspondre à votre taux de données
Si votre serveur envoie du contenu plus rapidement que le viewer ne le rend, le tampon grandit et le contenu affiché prend du retard. Augmentez `renderSpeed` pour suivre le rythme, ou appelez `flush()` lorsque tout le contenu a été reçu pour afficher le contenu restant immédiatement.
:::

### État de rendu {#render-state}

Lorsque le rendu progressif est activé, la méthode `isRendering()` renvoie `true` tant que le composant affiche activement le contenu mis en tampon. Les interfaces de discussion utilisent souvent cela pour afficher ou masquer un bouton d'arrêt :

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Cette méthode renvoie toujours `false` lorsque le rendu progressif est désactivé.

### Contrôle du rendu {#controlling-rendering}

Deux méthodes contrôlent comment le rendu progressif s'arrête :

- **`stop()`** interrompt le rendu et supprime tout contenu mis en tampon qui n'a pas encore été affiché. Appelez ceci lorsque l'utilisateur annule.
- **`flush()`** interrompt le rendu mais affiche immédiatement tout le contenu tampon restant. Appelez ceci lorsque tout le contenu a été reçu et que vous souhaitez l'afficher sans attendre.

```java
// L'utilisateur a cliqué sur "Arrêter la génération"
viewer.stop();

// Tout le contenu reçu, montrez tout maintenant
viewer.flush();
```

Ces méthodes n'ont aucun effet lorsque le rendu progressif est désactivé.

### Attente de la complétion {#waiting-for-completion}

La méthode `whenRenderComplete()` renvoie un `PendingResult` qui se termine lorsque le rendu progressif a fini d'afficher tout le contenu mis en tampon :

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Si le rendu progressif n'est pas activé ou qu'aucun contenu n'est en cours de rendu, le `PendingResult` se termine immédiatement.

:::tip Coordination UI
Lors de l'utilisation du rendu progressif, ne réactivez pas les champs de saisie uniquement en fonction du moment où vous terminez d'appeler `append()`. Le rendu peut encore afficher du contenu tampon. Attendez `whenRenderComplete()` afin que tout le contenu apparaisse avant que les utilisateurs ne puissent interagir à nouveau.
:::

La démonstration suivante simule une interface de chat AI utilisant `append()` avec le rendu progressif activé :

<ComponentDemo 
path='/webforj/markdownviewerstreaming?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java'
height='700px'
/>

## Effacer le contenu {#clearing-content}

Supprimez tout le contenu avec `clear()` :

```java
viewer.clear();
```

Si le rendu progressif est actif, `clear()` arrête également le rendu et complète tout résultat `whenRenderComplete()` en attente.

## Mise en surbrillance de la syntaxe {#syntax-highlighting}

Le `MarkdownViewer` prend en charge la mise en surbrillance de la syntaxe pour les blocs de code lorsque [Prism.js](https://prismjs.com/) est disponible. Ajoutez Prism.js à votre application en utilisant les annotations `@JavaScript` et `@StyleSheet` :

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/prismjs@1/themes/prism-tomorrow.min.css")
@JavaScript(
  value = "https://cdn.jsdelivr.net/combine/npm/prismjs@1/prism.min.js,npm/prismjs@1/plugins/autoloader/prism-autoloader.min.js",
  top = true
)
public class Application extends App {
  // ...
}
```

Le plugin autoloader charge les définitions de langue au besoin, de sorte que les blocs de code avec des indices de langue comme ` ```java ` ou ` ```python ` soient mis en surbrillance automatiquement.

<TableBuilder name="MarkdownViewer" />
