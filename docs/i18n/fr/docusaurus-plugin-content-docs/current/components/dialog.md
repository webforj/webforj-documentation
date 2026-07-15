---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 3dcdd5a9a66f2b00229064500da2bb79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Le composant `Dialog` affiche une fenêtre pop-up qui superpose la vue actuelle, attirant l'attention sur le contenu ciblé tel que des formulaires, des confirmations ou des messages d'information.

<!-- INTRO_END -->

## Structure du `Dialog` {#dialog-structure}

Le `Dialog` est organisé en trois sections : un en-tête, une zone de contenu et un pied de page. Des composants peuvent être ajoutés à chaque section en utilisant `addToHeader()`, `addToContent()`, et `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Usages {#usages}

1. **Retour d'information et confirmation de l'utilisateur** : Les composants `Dialog` sont souvent utilisés pour fournir des retours d'information ou demander une confirmation de l'utilisateur. Ils peuvent afficher divers éléments de retour d'information importants, tels que :

  >- Messages de succès
  >- Alertes d'erreur
  >- Soumissions de confirmation

2. **Saisie de formulaire et édition** : Vous pouvez utiliser des dialogues pour collecter des saisies utilisateur ou leur permettre de modifier des informations de manière contrôlée et ciblée. Par exemple, un dialogue peut s'afficher pour modifier les détails du profil utilisateur ou compléter un formulaire multi-étapes.

3. **Informations contextuelles** : Afficher des informations contextuelles supplémentaires ou des infobulles dans un dialogue peut aider les utilisateurs à comprendre des fonctionnalités ou des données complexes. Les dialogues peuvent fournir des explications détaillées, des graphiques ou de la documentation d'aide.

4. **Aperçus d'images et de médias** : Lorsque les utilisateurs ont besoin de consulter des éléments multimédias, un `Dialog` peut être utilisé pour montrer des aperçus ou des galeries plus larges, par exemple lors de l'interaction avec :
  >- Images
  >- Vidéos
  >- Autres médias

## Fond et flou {#backdrop-and-blur}

En activant l'attribut de fond du composant `Dialog` de webforJ, un fond sera affiché derrière le `Dialog`. De plus, lorsqu'il est activé, l'attribut flou du `Dialog` rendra flou le fond du `Dialog`. Modifier ces paramètres peut aider les utilisateurs en fournissant de la profondeur, une hiérarchie visuelle et un contexte, ce qui conduit à une orientation plus claire pour l'utilisateur.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## Ouverture et fermeture du `Dialog` {#opening-and-closing-the-dialog}

Après avoir créé un nouvel objet `Dialog`, utilisez la méthode `open()` pour afficher le dialogue. Ensuite, le composant `Dialog` peut se fermer grâce à l'une de ces actions :
- Utilisation de la méthode `close()`
- En appuyant sur la touche <kbd>ESC</kbd>
- En cliquant en dehors du `Dialog`

Les développeurs peuvent choisir quelles interactions ferment le `Dialog` avec `setCancelOnEscKey()` et `setCancelOnOutsideClick()`. De plus, la méthode `setClosable()` peut empêcher ou autoriser à la fois l'appui sur la touche <kbd>ESC</kbd> et le clic à l'extérieur du `Dialog` pour fermer le composant.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Mise au point automatique {#auto-focus}

Lorsqu'elle est activée, la mise au point automatique donnera automatiquement le focus au premier élément dans le dialogue qui peut être focalisé. Cela est utile pour aider à diriger l'attention des utilisateurs et est personnalisable via la méthode `setAutoFocus()`.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Déplaçable {#draggable}

Le `Dialog` dispose d'une fonctionnalité intégrée pour être déplaçable, permettant à l'utilisateur de relocaliser la fenêtre `Dialog` en cliquant et en faisant glisser. La position du `Dialog` peut être manipulée depuis n'importe quel champ à l'intérieur : l'en-tête, le contenu ou le pied.

### Accrocher au bord {#snap-to-edge}
Il est également possible de calibrer ce comportement pour accrocher au bord de l'écran, ce qui signifie que le `Dialog` s'alignera automatiquement avec le bord de l'affichage lorsque relâché depuis sa date de glisser-déposer. L'accrochage peut être modifié via la méthode `setSnapToEdge()`. La méthode `setSnapThreshold()` prend un nombre de pixels, qui définira à quelle distance le `Dialog` doit être des côtés de l'écran avant de s'accrocher automatiquement aux bords.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Positionnement {#positioning}

La position du dialogue peut être manipulée à l'aide des méthodes intégrées `setPosx()` et `setPosy()`. Ces méthodes prennent un argument de chaîne qui peut représenter toute unité de longueur CSS applicable, comme des pixels ou la hauteur/largeur de la vue. Une liste de ces mesures [peut être trouvée à ce lien](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Alignement vertical {#vertical-alignment}

En plus de l'attribution manuelle de la position X et Y d'un dialogue, il est possible d'utiliser l'énumération intégrée du dialogue pour aligner le `Dialog`. Il y a trois valeurs possibles, `TOP`, `CENTER` et `BOTTOM`, chacune pouvant être utilisée avec la méthode `setAlignment()`.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Plein écran et points de rupture {#full-screen-and-breakpoints}

Le `Dialog` peut être configuré pour entrer en mode plein écran. Lorsque le plein écran est activé, le `Dialog` ne peut pas être déplacé ou positionné. Ce mode peut être manipulé avec l'attribut de point de rupture du `Dialog`. Le point de rupture est une requête média qui détermine quand le `Dialog` basculera automatiquement en mode plein écran. Lorsque la requête est satisfaite, le `Dialog` passe en plein écran - sinon, il est positionné.

### Largeur automatique <DocChip chip='since' label='26.00' /> {#auto-width}

Par défaut, le `Dialog` s'étire pour remplir l'espace horizontal disponible. Lorsque la largeur automatique est activée via `setAutoWidth(true)`, le `Dialog` ajuste sa taille en fonction de la largeur de son contenu.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Style {#styling}

### Thèmes {#themes}

Les composants `Dialog` sont fournis avec <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 thèmes discrets</JavadocLink> intégrés pour un style rapide sans l'utilisation de CSS. Ces thèmes sont des styles pré-défini qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des boutons à travers une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des divers thèmes, quelques exemples d'utilisation sont :

  - **Danger** : Les actions avec des conséquences graves, telles que supprimer des informations remplies ou supprimer définitivement un compte/données, représentent un bon cas d'utilisation pour les dialogues avec le thème Danger.
  - **Default** : Le thème par défaut est approprié pour des actions dans une application qui ne nécessitent pas d'attention particulière et qui sont génériques, comme basculer un paramètre.
  - **Primary** : Ce thème est approprié comme principal "appel à l'action" sur une page, comme s'inscrire, enregistrer des modifications ou continuer vers une autre page.
  - **Success** : Les dialogues au thème de succès sont excellents pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué par programmation une fois qu'une action réussie a été réalisée.
  - **Warning** : Les dialogues d'avertissement sont utiles pour indiquer aux utilisateurs qu'ils sont sur le point d'effectuer une action potentiellement risquée, comme lorsqu'ils naviguent loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, comme de petits réglages ou des actions qui sont plus complémentaires à une page et non pas partie de la fonctionnalité principale.
  - **Info** : Le thème Info est un bon choix pour fournir des informations supplémentaires clarificatrices à un utilisateur lorsqu'elles sont poussées.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
