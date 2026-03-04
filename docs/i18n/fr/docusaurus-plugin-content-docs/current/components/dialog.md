---
title: Dialog
sidebar_position: 30
_i18n_hash: 4896ea2a90b7c5155fe9ef291e69b2ad
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Le composant `Dialog` affiche une fenêtre contextuelle qui superpose la vue actuelle, attirant l'attention sur un contenu focalisé comme des formulaires, des confirmations ou des messages informatifs.

<!-- INTRO_END -->

## Structure du `Dialog` {#dialog-structure}

Le `Dialog` est organisé en trois sections : un en-tête, une zone de contenu et un pied de page. Des composants peuvent être ajoutés à chaque section en utilisant `addToHeader()`, `addToContent()`, et `addToFooter()`.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Usages {#usages}

1. **Retour d'information et confirmation** : Les composants `Dialog` sont souvent utilisés pour fournir des retours d'information ou demander une confirmation à l'utilisateur. Ils peuvent afficher divers messages importants à l'utilisateur, tels que :

  >- Messages de succès 
  >- Alertes d'erreur
  >- Soumissions de confirmation

2. **Saisie et édition de formulaires** : Vous pouvez utiliser des dialogues pour recueillir des saisies utilisateurs ou leur permettre de modifier des informations de manière contrôlée et focalisée. Par exemple, un dialogue peut s'ouvrir pour modifier les détails du profil utilisateur ou compléter un formulaire en plusieurs étapes.

3. **Informations contextuelles** : Afficher des informations contextuelles supplémentaires ou des infobulles dans un dialogue peut aider les utilisateurs à comprendre des fonctionnalités ou des données complexes. Les dialogues peuvent fournir des explications détaillées, des graphiques ou de la documentation d'aide.

4. **Aperçus d'images et de médias** : Lorsque les utilisateurs ont besoin de visualiser des pièces de médias, un `Dialog` peut être utilisé pour afficher de plus grands aperçus ou galeries, par exemple lors de l'interaction avec :
  >- Images
  >- Vidéos
  >- Autres médias

## Arrière-plan et flou {#backdrop-and-blur}

En activant l'attribut d'arrière-plan du composant webforJ `Dialog`, un arrière-plan sera affiché derrière le `Dialog`. De plus, lorsqu'il est activé, l'attribut flou du Dialog floutera l'arrière-plan du `Dialog`. Modifier ces paramètres peut aider les utilisateurs en fournissant des profondeurs, une hiérarchie visuelle et un contexte, menant à une orientation plus claire pour l'utilisateur.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Ouverture et fermeture du `Dialog` {#opening-and-closing-the-dialog}

Après avoir créé un nouvel objet `Dialog`, utilisez la méthode `open()` pour afficher le dialogue. Ensuite, le composant `Dialog` peut se fermer par l'une de ces actions :
- En utilisant la méthode `close()`
- En appuyant sur la touche <kbd>ESC</kbd>
- En cliquant en dehors du `Dialog`

Les développeurs peuvent choisir quelles interactions ferment le `Dialog` avec `setCancelOnEscKey()` et `setCancelOnOutsideClick()`. De plus, la méthode `setClosable()` peut empêcher ou autoriser à la fois l'appui sur la touche <kbd>ESC</kbd> et le clic en dehors du `Dialog` pour fermer le composant.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Mise au point automatique {#auto-focus}

Lorsqu'il est activé, la mise au point automatique donnera automatiquement le focus au premier élément à l'intérieur du dialogue qui peut être mis au point. Cela est utile pour aider à diriger l'attention des utilisateurs, et est personnalisable via la méthode `setAutoFocus()`.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Déplaçable {#draggable}

Le `Dialog` a une fonctionnalité intégrée pour être déplaçable, permettant à l'utilisateur de relocaliser la fenêtre du `Dialog` en cliquant et en faisant glisser. La position du `Dialog` peut être manipulée depuis n'importe lequel des champs à l'intérieur : l'en-tête, le contenu ou le pied de page.

### Accrocher à l'écran {#snap-to-edge}
Il est également possible de calibrer ce comportement pour s'accrocher au bord de l'écran, ce qui signifie que le `Dialog` s'alignera automatiquement avec le bord de l'affichage lorsqu'il est relâché depuis sa date de glisser-déposer. L'accrochage peut être modifié via la méthode `setSnapToEdge()`. La méthode `setSnapThreshold()` prend un nombre de pixels, qui définira à quelle distance le `Dialog` doit être des bords de l'écran avant de s'accrocher automatiquement.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionnement {#positioning}

La position du dialogue peut être manipulée à l'aide des méthodes intégrées `setPosx()` et `setPosy()`. Ces méthodes prennent un argument de chaîne qui peut représenter toute unité de longueur CSS applicable, telle que les pixels ou la hauteur/largeur de la vue. Une liste de ces mesures [peut être trouvée à ce lien](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Alignement vertical {#vertical-alignment}

En plus de l'assignation manuelle de la position X et Y d'un dialogue, il est possible d'utiliser la classe énumérée intégrée du dialogue pour aligner le `Dialog`. Il y a trois valeurs possibles, `TOP`, `CENTER` et `BOTTOM`, chacune d'elles pouvant être utilisée avec la méthode `setAlignment()`.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Plein écran et points de rupture {#full-screen-and-breakpoints}

Le `Dialog` peut être réglé pour passer en mode plein écran. Lorsque le plein écran est activé, le `Dialog` ne peut pas être déplacé ou positionné. Ce mode peut être manipulé avec l'attribut de point de rupture du `Dialog`. Le point de rupture est une requête média qui détermine quand le `Dialog` passera automatiquement en mode plein écran. Lorsque la requête correspond, le `Dialog` change pour être en plein écran - sinon, il reste positionné.

## Style {#styling}

### Thèmes {#themes}

Les composants `Dialog` sont livrés avec <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 thèmes discrets</JavadocLink> intégrés pour un style rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des boutons dans une application.

Bien qu'il y ait de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples d'utilisations :

  - **Danger** : Les actions ayant des conséquences graves, telles que la suppression d'informations saisies ou la suppression permanente d'un compte/données, représentent un bon cas d'utilisation pour des dialogues avec le thème Danger.
  - **Default** : Le thème par défaut est approprié pour des actions dans une application qui ne nécessitent pas d'attention particulière et qui sont génériques, comme basculer un paramètre.
  - **Primary** : Ce thème est approprié comme un "appel à l'action" principal sur une page, comme s'inscrire, enregistrer des modifications ou continuer vers une autre page.
  - **Success** : Les dialogues thématiques de succès sont excellents pour visualiser l'achèvement réussi d'un élément dans une application, tel que la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué par programmation une fois qu'une action réussie a été accomplie.
  - **Warning** : Les dialogues d'avertissement sont utiles pour indiquer aux utilisateurs qu'ils sont sur le point d'effectuer une action potentiellement risquée, par exemple lors de la navigation hors d'une page avec des modifications non enregistrées. Ces actions ont souvent moins d'impact que celles utilisant le thème Danger.
  - **Gray** : Bon pour des actions subtiles, telles que des paramètres mineurs ou des actions qui sont plus complémentaires à une page et ne font pas partie de la fonctionnalité principale.
  - **Info** : Le thème Info est un bon choix pour fournir des informations supplémentaires et clarificatrices à un utilisateur lorsqu'il est demandé.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
