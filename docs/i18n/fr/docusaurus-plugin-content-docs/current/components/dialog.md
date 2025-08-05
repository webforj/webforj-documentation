---
title: Dialog
sidebar_position: 30
_i18n_hash: d0087974ac244db9b082133be7966a3e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

Le composant de dialogue webforJ est conçu pour permettre à un développeur d'afficher rapidement et facilement un dialogue sur son application, par exemple pour un menu de connexion ou une boîte d'information.

Le composant est conçu avec trois sections, chacune étant un composant `Panel` : l'**en-tête**, le **contenu**, et le **pied de page**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Usages {#usages}

1. **Retour d'information et confirmation** : Les composants `Dialog` sont souvent utilisés pour fournir des retours d'information ou demander une confirmation à l'utilisateur. Ils peuvent afficher divers éléments importants pour un utilisateur, tels que :

  >- Messages de succès 
  >- Alertes d'erreur
  >- Soumissions de confirmation

2. **Saisie de formulaire et édition** : Vous pouvez utiliser des dialogues pour collecter des informations de l'utilisateur ou leur permettre d'éditer des informations de manière contrôlée et ciblée. Par exemple, un dialogue peut apparaître pour modifier les détails du profil utilisateur ou compléter un formulaire en plusieurs étapes.

3. **Informations contextuelles** : Afficher des informations contextuelles supplémentaires ou des infobulles dans un dialogue peut aider les utilisateurs à comprendre des fonctionnalités ou des données complexes. Les dialogues peuvent fournir des explications détaillées, des graphiques ou de la documentation d'aide.

4. **Aperçus d'images et de médias** : Lorsque les utilisateurs ont besoin de visualiser des éléments de médias, un `Dialog` peut être utilisé pour afficher de grands aperçus ou des galeries, par exemple lors de l'interaction avec :
  >- Images
  >- Vidéos
  >- Autres médias

## Arrière-plan et flou {#backdrop-and-blur}

En activant l'attribut d'arrière-plan du composant `Dialog` webforJ, un arrière-plan sera affiché derrière le `Dialog`. De plus, lorsqu'il est activé, l'attribut flou du `Dialog` floutera l'arrière-plan du `Dialog`. La modification de ces paramètres peut aider les utilisateurs en fournissant de la profondeur, une hiérarchie visuelle et du contexte, menant à des indications plus claires pour un utilisateur.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Ouverture et fermeture du `Dialog` {#opening-and-closing-the-dialog}

Après avoir créé un nouvel objet `Dialog`, utilisez la méthode `open()` pour afficher le dialogue. Ensuite, le composant `Dialog` peut se fermer grâce à l'une de ces actions :
- Utiliser la méthode `close()`
- Appuyer sur la touche <kbd>ESC</kbd>
- Cliquer en dehors du `Dialog`

Les développeurs peuvent choisir quelles interactions ferment le `Dialog` avec `setCancelOnEscKey()` et `setCancelOnOutsideClick()`. De plus, la méthode `setClosable()` peut empêcher ou permettre à la fois d'appuyer sur la touche <kbd>ESC</kbd> et de cliquer en dehors du `Dialog` pour fermer le composant.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Mise au point automatique {#auto-focus}

Lorsqu'il est activé, la mise au point automatique donnera automatiquement le focus au premier élément dans le dialogue qui peut être mis au point. Cela est utile pour aider à diriger l'attention des utilisateurs et est personnalisable via la méthode `setAutoFocus()`.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Déplaçable {#draggable}

Le `Dialog` a une fonctionnalité intégrée pour être déplaçable, permettant à l'utilisateur de déplacer la fenêtre `Dialog` en cliquant et en faisant glisser. La position du `Dialog` peut être manipulée à partir de n'importe lequel des champs qu'il contient : l'en-tête, le contenu ou le pied de page.

### Accrochage au bord {#snap-to-edge}
Il est également possible de calibrer ce comportement pour s'accrocher au bord de l'écran, ce qui signifie que le `Dialog` s'alignera automatiquement avec le bord de l'affichage lorsqu'il sera libéré à partir de sa date de glisser-déposer. L'accrochage peut être modifié via la méthode `setSnapToEdge()`. La méthode `setSnapThreshold()` prend un nombre de pixels, qui définira combien de pixels le `Dialog` doit être éloigné des côtés de l'écran avant de s'accrocher automatiquement aux bords.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Positionnement {#positioning}

La position du dialogue peut être manipulée à l'aide des méthodes intégrées `setPosx()` et `setPosy()`. Ces méthodes prennent un argument de chaîne qui peut représenter toute unité de longueur CSS applicable, telle que des pixels ou une hauteur/largeur de vue. Une liste de ces mesures [peut être trouvée à ce lien](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Alignement vertical {#vertical-alignment}

En plus de l'attribution manuelle de la position X et Y d'un dialogue, il est possible d'utiliser la classe énumérée intégrée du dialogue pour aligner le `Dialog`. Il existe trois valeurs possibles, `TOP`, `CENTER` et `BOTTOM`, chacune pouvant être utilisée avec la méthode `setAlignment()`.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Plein écran et points de rupture {#full-screen-and-breakpoints}

Le `Dialog` peut être configuré pour entrer en mode plein écran. Lorsque le plein écran est activé, le `Dialog` ne peut pas être déplacé ou positionné. Ce mode peut être manipulé avec l'attribut de point de rupture du `Dialog`. Le point de rupture est une requête média qui détermine quand le `Dialog` passera automatiquement en mode plein écran. Lorsque la requête correspond, le `Dialog` change en plein écran - sinon il est positionné.

## Stylisation {#styling}

### Thèmes {#themes}

Les composants `Dialog` sont livrés avec <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 thèmes discrets </JavadocLink> intégrés pour un style rapide sans avoir besoin de CSS. Ces thèmes sont des styles pré-définis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des boutons dans toute une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, quelques exemples d'utilisation sont :

  - **Danger** : Des actions avec des conséquences graves, telles que la suppression d'informations saisies ou la suppression définitive d'un compte/données, représentent un bon cas d'utilisation pour les dialogues avec le thème Danger.
  - **Default** : Le thème par défaut est approprié pour les actions à travers une application qui ne nécessitent pas d'attention particulière et qui sont génériques, telles que le basculement d'un paramètre.
  - **Primary** : Ce thème est approprié en tant qu'appel à l'action principal sur une page, tel que s'inscrire, enregistrer des modifications ou continuer vers une autre page.
  - **Success** : Les dialogues au thème Success sont excellents pour visualiser l'achèvement réussi d'un élément dans une application, tel que la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de réussite peut être appliqué par programme une fois qu'une action réussie a été réalisée.
  - **Warning** : Les dialogues d'avertissement sont utiles pour indiquer aux utilisateurs qu'ils s'apprêtent à effectuer une action potentiellement risquée, comme lorsqu'ils naviguent loin d'une page avec des modifications non enregistrées. Ces actions ont souvent moins d'impact que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, telles que des paramètres mineurs ou des actions qui sont plus complémentaires à une page et ne font pas partie de la fonctionnalité principale.
  - **Info** : Le thème Info est un bon choix pour fournir des informations supplémentaires et clarificatrices à un utilisateur lorsqu'il est poussé.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
