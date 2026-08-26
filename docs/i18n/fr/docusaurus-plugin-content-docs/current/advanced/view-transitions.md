---
sidebar_position: 40
title: Transitions de vue
description: >-
  Animate DOM changes with the browser View Transition API, applying fade,
  slide, zoom, and shared morph effects between component states.
_i18n_hash: df97f8dc10601feff6a211aee0b4e9d7
---
<JavadocLink type="foundation" location="com/webforj/ViewTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Les transitions de vue offrent des transitions animées lorsque le [DOM](/docs/glossary#dom) change, réduisant la discontinuité visuelle et maintenant le contexte spatial lors de la navigation ou des mises à jour de contenu. webforJ s'intègre à l'[API de transition de vue](https://developer.mozilla.org/en-US/docs/Web/API/View_Transition_API) du navigateur pour gérer la complexité de la coordination des animations entre les anciens et les nouveaux états.

<ComponentDemo
path='/webforj/viewtransitionchat'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionChatView.java',
  'src/main/frontend/css/viewtransitions/chat.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='450px'
/>

<ExperimentalWarning />

## Utilisation de base {#basic-usage}

Pour créer une transition de vue, utilisez `Page.getCurrent().startViewTransition()`, qui retourne un constructeur pour configurer la transition :

```java
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    container.remove(oldView);
    container.add(newView);
    done.run();
  })
  .start();
```

Le processus de transition capture un instantané de l'état actuel, applique vos modifications de DOM dans le rappel `onUpdate`, puis anime de l'ancien instantané au nouveau contenu. Vous devez appeler `done.run()` pour indiquer quand vos changements sont terminés.

:::warning Le rappel `onUpdate` est requis
Appeler `start()` sans définir de rappel de mise à jour lance une `IllegalStateException`.
:::

## Application des transitions {#applying-transitions}

webforJ fournit des types de transition prédéfinis que vous pouvez appliquer aux composants entrant ou sortant du DOM :

| Constante | Effet |
|----------|--------|
| `ViewTransition.NONE` | Pas d'animation |
| `ViewTransition.FADE` | Fondu entre l'ancien et le nouveau contenu |
| `ViewTransition.SLIDE_LEFT` | Le contenu coule à gauche (comme une navigation vers l'avant) |
| `ViewTransition.SLIDE_RIGHT` | Le contenu coule à droite (comme une navigation vers l'arrière) |
| `ViewTransition.SLIDE_UP` | Le contenu coule vers le haut |
| `ViewTransition.SLIDE_DOWN` | Le contenu coule vers le bas |
| `ViewTransition.ZOOM` | L'ancien contenu rétrécit, le nouveau contenu grandit |
| `ViewTransition.ZOOM_OUT` | L'ancien contenu grandit, le nouveau contenu rétrécit |

Utilisez `enter()` pour animer un composant ajouté et `exit()` pour animer un composant retiré :

```java
// Animer un composant entrant dans le DOM
Page.getCurrent().startViewTransition()
  .enter(chatPanel, ViewTransition.ZOOM)
  .onUpdate(done -> {
    container.add(chatPanel);
    done.run();
  })
  .start();

// Animer un composant sortant du DOM
Page.getCurrent().startViewTransition()
  .exit(chatPanel, ViewTransition.FADE)
  .onUpdate(done -> {
    container.remove(chatPanel);
    done.run();
  })
  .start();
```

## Transitions de composants partagés {#shared-component-transitions}

Les transitions de composants partagés créent un effet de morphisme où un composant semble se transformer de sa position dans l'ancienne vue à sa position dans la nouvelle vue. Cela se fait en donnant aux composants le même nom de transition à l'aide de la méthode `setViewTransitionName()`, disponible sur tout composant qui implémente l'interface <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>.

```java
// Dans la vue de carte
image.setViewTransitionName("blog-image");

// Dans la vue de détail - le même nom crée le morphisme
image.setViewTransitionName("blog-image");
```

Lors de la transition entre ces vues, le navigateur anime le composant entre les positions, créant une expérience visuelle connectée.

:::tip Utilisez des noms uniques
Lorsqu'il s'agit de listes ou de composants répétés, incluez un identifiant unique dans le nom de transition. Chaque composant nécessite son propre nom distinct pour se modifier correctement dans le composant correspondant de la nouvelle vue. Utiliser le même nom pour plusieurs composants visibles entraîne un comportement indéfini.
:::

<ComponentDemo
path='/webforj/viewtransitionmorph'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionMorphView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogCard.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/BlogDetail.java',
  'src/main/frontend/css/viewtransitions/morph.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='650px'
/>

### Réorganisation de la liste {#list-reordering}

Un cas d'utilisation courant des transitions de composants partagés est d'animer les éléments de liste lorsque leur ordre change. En assignant un `view-transition-name` unique à chaque élément, le navigateur anime automatiquement les composants vers leurs nouvelles positions :

```java
// Chaque carte reçoit un nom de transition unique basé sur son ID
card.setViewTransitionName("card-" + item.id());

// Lors de la permutation, il suffit de mettre à jour le DOM - le navigateur gère l'animation
Page.getCurrent().startViewTransition()
  .onUpdate(done -> {
    renderList();
    done.run();
  })
  .start();
```

<ComponentDemo
path='/webforj/viewtransitionshuffle'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionShuffleView.java',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/ShuffleCard.java',
  'src/main/frontend/css/viewtransitions/shuffle.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='550px'
/>

## Animations CSS personnalisées {#custom-css-animations}

Pour un contrôle total sur les animations, vous pouvez définir des keyframes CSS personnalisées. webforJ ajoute des suffixes `-enter` ou `-exit` à vos noms de transition, que vous utilisez pour cibler les pseudo-éléments de transition de vue :

```css
/* Définir des keyframes pour les composants entrants */
@keyframes flip-enter {
  from {
    opacity: 0;
    transform: perspective(1000px) rotateX(-90deg);
  }
  to {
    opacity: 1;
    transform: perspective(1000px) rotateX(0deg);
  }
}

/* Appliquer au pseudo-élément de transition de vue */
::view-transition-new(flip-in-enter) {
  animation: flip-enter 450ms cubic-bezier(0.34, 1.56, 0.64, 1);
  transform-origin: top center;
}

::view-transition-old(flip-in-enter) {
  display: none;
}
```

Référencez votre animation personnalisée en passant son nom (sans le suffixe) à `enter()` ou `exit()` :

```java
// Utilisez "flip-in" - webforJ ajoute automatiquement le suffixe "-enter"
Page.getCurrent().startViewTransition()
  .enter(notification, "flip-in")
  .onUpdate(done -> {
    stage.add(notification);
    done.run();
  })
  .start();

// Utilisez "blur-out" pour exit - webforJ ajoute automatiquement le suffixe "-exit"
Page.getCurrent().startViewTransition()
  .exit(notification, "blur-out")
  .onUpdate(done -> {
    stage.remove(notification);
    done.run();
  })
  .start();
```

<ComponentDemo
path='/webforj/viewtransitionenterexit'
files={[
  'src/main/java/com/webforj/samples/views/viewtransitions/ViewTransitionEnterExitView.java',
  'src/main/frontend/css/viewtransitions/enterexit.css',
  'src/main/java/com/webforj/samples/views/viewtransitions/components/DemoHeader.java',
  'src/main/frontend/css/viewtransitions/components/demo-header.css',
]}
height='400px'
/>

## Personnalisation CSS {#css-customization}

Chaque type de transition prédéfini expose des propriétés CSS personnalisées pour un ajustement fin :

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Fondu</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Par défaut | Description |
      |----------|---------|-------------|
      | `--vt-fade-duration` | `200ms` | Durée de l'animation |
      | `--vt-fade-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Fonction d'assouplissement |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Glisser à gauche</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Par défaut | Description |
      |----------|---------|-------------|
      | `--vt-slide-left-duration` | `200ms` | Durée de l'animation |
      | `--vt-slide-left-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Fonction d'assouplissement |
      | `--vt-slide-left-distance` | `30%` | Distance de glissement |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Glisser à droite</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Par défaut | Description |
      |----------|---------|-------------|
      | `--vt-slide-right-duration` | `200ms` | Durée de l'animation |
      | `--vt-slide-right-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Fonction d'assouplissement |
      | `--vt-slide-right-distance` | `30%` | Distance de glissement |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Glisser vers le haut</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Par défaut | Description |
      |----------|---------|-------------|
      | `--vt-slide-up-duration` | `200ms` | Durée de l'animation |
      | `--vt-slide-up-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Fonction d'assouplissement |
      | `--vt-slide-up-distance` | `30%` | Distance de glissement |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Glisser vers le bas</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Par défaut | Description |
      |----------|---------|-------------|
      | `--vt-slide-down-duration` | `200ms` | Durée de l'animation |
      | `--vt-slide-down-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Fonction d'assouplissement |
      | `--vt-slide-down-distance` | `30%` | Distance de glissement |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Par défaut | Description |
      |----------|---------|-------------|
      | `--vt-zoom-duration` | `200ms` | Durée de l'animation |
      | `--vt-zoom-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Fonction d'assouplissement |
      | `--vt-zoom-scale` | `0.8` | Facteur d'échelle (ancien rétrécit à cela, nouveau grandit à partir de cela) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Zoom arrière</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      | Variable | Par défaut | Description |
      |----------|---------|-------------|
      | `--vt-zoom-out-duration` | `200ms` | Durée de l'animation |
      | `--vt-zoom-out-easing` | `cubic-bezier(0.4, 0, 0.2, 1)` | Fonction d'assouplissement |
      | `--vt-zoom-out-scale` | `1.2` | Facteur d'échelle (ancien grandit à cela, nouveau rétrécit à partir de cela) |
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <strong>Substitution des variables</strong>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      Pour personnaliser, substituez ces variables dans votre CSS :

      ```css
      :root {
        --vt-fade-duration: 300ms;
        --vt-slide-left-distance: 50%;
      }
      ```

      Pour une personnalisation avancée, ciblez directement les pseudo-éléments de transition de vue :

      ```css
      ::view-transition-old(vt-slide-left-exit) {
        animation-duration: 400ms;
      }

      ::view-transition-new(vt-slide-left-enter) {
        animation-timing-function: ease-out;
      }
      ```
    </div>
  </AccordionDetails>
</Accordion>
<br />
