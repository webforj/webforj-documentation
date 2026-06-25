---
title: MarkdownViewer
sidebar_position: 74
_i18n_hash: e50beb488f343e35da80b6d4f9ceddf5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="vueur-markdown-dwc" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

Le composant `MarkdownViewer` rend du texte markdown en HTML. Il prend en charge la syntaxe markdown standard incluant les en-têtes, les listes, les blocs de code, les liens, les images et le rendu des emojis. Le composant propose également un rendu progressif, qui affiche le contenu caractère par caractère pour un effet de machine à écrire.

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
path='/webforj/markdownviewer'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerView.java']}
height='650px'
/>

## Ajouter du contenu {#appending-content}

La méthode `append()` ajoute du contenu de manière incrémentielle sans remplacer ce qui est déjà présent :

```java
viewer.append("## Nouvelle section\n\n");
viewer.append("Plus de contenu ici...");
```

Par défaut, le contenu ajouté apparaît immédiatement. Lorsque le [rendu progressif](#progressive-rendering) est activé, le contenu ajouté passe dans un tampon et s'affiche caractère par caractère à la place.

## Défilement automatique {#auto-scroll}

Activez le défilement automatique pour garder la vue à la fin à mesure que le contenu s'accroît. Cela fonctionne avec n'importe quelle méthode d'ajout de contenu, que ce soit `setContent()`, `append()`, ou le rendu progressif. Si un utilisateur défile manuellement vers le haut pour examiner du contenu antérieur, le défilement automatique se met en pause et reprend lorsqu'il fait défiler à nouveau vers le bas.

```java
viewer.setAutoScroll(true);
```

## Rendu progressif {#progressive-rendering}

Le rendu progressif affiche du contenu caractère par caractère plutôt que tout à la fois, créant un effet de machine à écrire. Les interfaces de chat AI utilisent souvent cela pour montrer des réponses apparaissant progressivement :

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Lorsqu'il est activé, le contenu ajouté via `setContent()` ou `append()` passe dans un tampon et s'affiche de manière incrémentielle. Lorsqu'il est désactivé, le contenu apparaît immédiatement.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Vitesse de rendu {#render-speed}

La méthode `setRenderSpeed()` contrôle combien de caractères sont rendus par image d'animation. Des valeurs plus élevées signifient un rendu plus rapide. À 60fps, la vitesse par défaut de 4 correspond à environ 240 caractères par seconde :

| Vitesse | Caractères/Seconde |
|-------|-------------------|
| 4 (par défaut) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Correspondre à votre taux de données
Si votre serveur envoie du contenu plus vite que le visualiseur ne le rend, le tampon augmente et le contenu affiché est en retard. Augmentez `renderSpeed` pour garder le rythme, ou appelez `flush()` lorsque tout le contenu a été reçu pour afficher le contenu restant immédiatement.
:::

### État de rendu {#render-state}

Lorsque le rendu progressif est activé, la méthode `isRendering()` retourne `true` tant que le composant est activement en train d'afficher du contenu mis en tampon. Les interfaces de chat utilisent souvent cela pour montrer ou cacher un bouton d'arrêt :

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Cette méthode retourne toujours `false` lorsque le rendu progressif est désactivé.

### Contrôle du rendu {#controlling-rendering}

Deux méthodes contrôlent comment le rendu progressif s'arrête :

- **`stop()`** arrête le rendu et jette tout contenu mis en tampon qui n'a pas encore été affiché. Appelez ceci lorsque l'utilisateur annule.
- **`flush()`** arrête le rendu mais affiche immédiatement tout le contenu mis en tampon restant. Appelez ceci lorsque tout le contenu a été reçu et que vous souhaitez l'afficher sans attendre.

```java
// L'utilisateur a cliqué sur "Arrêter la génération"
viewer.stop();

// Tout le contenu reçu, montrez tout maintenant
viewer.flush();
```

Ces méthodes n'ont aucun effet lorsque le rendu progressif est désactivé.

### Attendre la complétion {#waiting-for-completion}

La méthode `whenRenderComplete()` retourne un `PendingResult` qui se complète lorsque le rendu progressif a terminé d'afficher tout le contenu mis en tampon :

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Si le rendu progressif n'est pas activé ou qu'aucun contenu n'est en train d'être rendu, le `PendingResult` se complète immédiatement.

:::tip Coordination de l'UI
Lors de l'utilisation du rendu progressif, ne réactivez pas les champs de saisie uniquement en fonction du moment où vous avez terminé d'appeler `append()`. Le rendu peut encore afficher le contenu mis en tampon. Attendez `whenRenderComplete()` pour que tout le contenu apparaisse avant que les utilisateurs ne puissent interagir à nouveau.
:::

La démo suivante simule une interface de chat AI utilisant `append()` avec le rendu progressif activé :

<ComponentDemo
path='/webforj/markdownviewerstreaming'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerStreamingView.java']}
height='700px'
/>

## Effacer le contenu {#clearing-content}

Supprimez tout le contenu avec `clear()` :

```java
viewer.clear();
```

Si le rendu progressif est actif, `clear()` arrête également le rendu et complète tous les résultats en attente de `whenRenderComplete()`.

## Surlignement de syntaxe {#syntax-highlighting}

Le `MarkdownViewer` prend en charge le surlignement de syntaxe pour les blocs de code lorsque [Prism.js](https://prismjs.com/) est disponible. Ajoutez Prism.js à votre application en utilisant les annotations `@JavaScript` et `@StyleSheet` :

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

Le plugin autoloader charge les définitions de langue au besoin, donc les blocs de code avec des indices de langue comme ` ```java ` ou ` ```python ` sont surlignés automatiquement.

<TableBuilder name="MarkdownViewer" />
