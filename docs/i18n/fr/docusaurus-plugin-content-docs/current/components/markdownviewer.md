---
title: MarkdownViewer
sidebar_position: 74
description: >-
  Render markdown as HTML with the MarkdownViewer component, supporting append,
  auto-scroll, and progressive typewriter rendering.
_i18n_hash: fbd31d2317bf5de95c282a1319f35cf6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-markdown-viewer" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="markdown-viewer" location="com/webforj/component/markdown/MarkdownViewer" top='true'/>

Le composant `MarkdownViewer` rend le texte markdown en HTML. Il prend en charge la syntaxe markdown standard, y compris les en-têtes, les listes, les blocs de code, les liens, les images et le rendu d'emoji. Le composant propose également un rendu progressif, qui affiche le contenu caractère par caractère pour un effet de machine à écrire.

## Configuration du contenu {#setting-content}

Créez un `MarkdownViewer` avec ou sans contenu initial, puis mettez à jour son contenu en utilisant `setContent()` :

```java
MarkdownViewer viewer = new MarkdownViewer("# Hello World");

// Remplacez complètement le contenu
viewer.setContent("""
    ## Nouveau contenu

    - Éléments 1
    - Éléments 2
    """);

// Obtenez le contenu actuel
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

La méthode `append()` ajoute du contenu progressivement sans remplacer ce qui est déjà là :

```java
viewer.append("## Nouvelle section\n\n");
viewer.append("Plus de contenu ici...");
```

Par défaut, le contenu ajouté apparaît immédiatement. Lorsque le [rendu progressif](#progressive-rendering) est activé, le contenu ajouté va dans un tampon et s'affiche caractère par caractère à la place.

## Défilement automatique {#auto-scroll}

Activez le défilement automatique pour garder le champ de vision en bas à mesure que le contenu augmente. Cela fonctionne avec n'importe quelle méthode d'ajout de contenu, que ce soit `setContent()`, `append()`, ou le rendu progressif. Si un utilisateur fait défiler manuellement vers le haut pour examiner un contenu précédent, le défilement automatique se met en pause et reprend lorsqu'il fait défiler à nouveau vers le bas.

```java
viewer.setAutoScroll(true);
```

## Rendu progressif {#progressive-rendering}

Le rendu progressif affiche le contenu caractère par caractère plutôt que tout d'un coup, créant un effet de machine à écrire. Les interfaces de chat AI utilisent souvent cela pour montrer des réponses apparaissant progressivement :

```java
MarkdownViewer viewer = new MarkdownViewer();
viewer.setProgressiveRender(true);
```

Lorsqu'il est activé, le contenu ajouté via `setContent()` ou `append()` va dans un tampon et s'affiche progressivement. Lorsqu'il est désactivé, le contenu apparaît immédiatement.

<ComponentDemo
path='/webforj/markdownviewerprogressive'
files={['src/main/java/com/webforj/samples/views/markdownviewer/MarkdownViewerProgressiveView.java']}
height='650px'
/>

### Vitesse de rendu {#render-speed}

La méthode `setRenderSpeed()` contrôle combien de caractères se rendent par image d'animation. Des valeurs plus élevées signifient un rendu plus rapide. À 60 images par seconde, la vitesse par défaut de 4 correspond à environ 240 caractères par seconde :

| Vitesse | Caractères/Seconde |
|---------|---------------------|
| 4 (par défaut) | ~240 |
| 6 | ~360 |
| 10 | ~600 |

```java
viewer.setRenderSpeed(6);
```

:::tip Correspondre à votre taux de données
Si votre serveur envoie du contenu plus vite que le visualiseur ne le rend, le tampon grandit et le contenu affiché prend du retard. Augmentez `renderSpeed` pour garder le rythme, ou appelez `flush()` lorsque tout le contenu a été reçu pour afficher immédiatement le reste du contenu.
:::

### État de rendu {#render-state}

Lorsque le rendu progressif est activé, la méthode `isRendering()` retourne `true` pendant que le composant affiche activement le contenu mis en tampon. Les interfaces de chat utilisent souvent cela pour afficher ou masquer un bouton d'arrêt :

```java
if (viewer.isRendering()) {
  stopButton.setVisible(true);
}
```

Cette méthode retourne toujours `false` lorsque le rendu progressif est désactivé.

### Contrôle du rendu {#controlling-rendering}

Deux méthodes contrôlent la façon dont le rendu progressif s'arrête :

- **`stop()`** arrête le rendu et jette tout contenu mis en tampon qui n'a pas encore été affiché. Appelez cela lorsque l'utilisateur annule.
- **`flush()`** arrête le rendu mais affiche immédiatement tout le contenu mis en tampon restant. Appelez cela lorsque tout le contenu a été reçu et que vous souhaitez le montrer sans attendre.

```java
// L'utilisateur a cliqué sur "Arrêter la génération"
viewer.stop();

// Tout le contenu reçu, montrez tout maintenant
viewer.flush();
```

Ces méthodes n'ont aucun effet lorsque le rendu progressif est désactivé.

### Attendre la fin {#waiting-for-completion}

La méthode `whenRenderComplete()` retourne un `PendingResult` qui se termine lorsque le rendu progressif a fini d'afficher tout le contenu mis en tampon :

```java
viewer.whenRenderComplete().thenAccept(v -> {
  inputField.setEnabled(true);
  inputField.focus();
});
```

Si le rendu progressif n'est pas activé ou qu'aucun contenu n'est en cours de rendu, le `PendingResult` se termine immédiatement.

:::tip Coordination de l'UI
Lors de l'utilisation du rendu progressif, ne réactivez pas les champs de saisie uniquement en fonction du moment où vous avez terminé d'appeler `append()`. Le moteur pourrait encore afficher du contenu mis en tampon. Attendez `whenRenderComplete()` afin que tout le contenu apparaisse avant que les utilisateurs puissent interagir à nouveau.
:::

La démo suivante simule une interface de chat AI en utilisant `append()` avec le rendu progressif activé :

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

Si le rendu progressif est actif, `clear()` arrête également le rendu et complète tout résultat `whenRenderComplete()` en attente.

## Mise en surbrillance de la syntaxe {#syntax-highlighting}

Le `MarkdownViewer` prend en charge la mise en surbrillance de la syntaxe pour les blocs de code lorsque [Prism.js](https://prismjs.com/) est disponible. Intégrez Prism dans votre application avec le [regroupement frontal](/docs/managing-resources/bundler/overview) : déclarez le package dans votre classe `App` et rédigez une entrée qui importe Prism, le plugin autoloader et un thème.

```java title="Application.java"
@BundlePackage(value = "prismjs", version = "^1.29.0")
@BundleEntry("prism/entry.ts")
public class Application extends App {
  // ...
}
```

```ts title="src/main/frontend/prism/entry.ts"
import "prismjs";
import "prismjs/plugins/autoloader/prism-autoloader";
import "prismjs/themes/prism-tomorrow.min.css";
```

Le plugin autoloader charge les définitions de langue au besoin, donc les blocs de code avec des indices de langue comme ` ```java ` ou ` ```python ` sont automatiquement mis en surbrillance.

<TableBuilder name="MarkdownViewer" />
