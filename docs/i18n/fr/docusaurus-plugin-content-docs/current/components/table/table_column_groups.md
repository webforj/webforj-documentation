---
title: Column Groups
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: fccec5a60bfd614d344758d3624bc602
---
<DocChip chip='since' label='25.12' />

Les groupes de colonnes vous permettent d'organiser des colonnes liées sous des en-têtes partagés à plusieurs lignes. Une étiquette de groupe s'étend sur ses colonnes enfants, ce qui facilite la tâche des utilisateurs pour parcourir et comprendre la structure des tableaux complexes. Les groupes peuvent être imbriqués à n'importe quelle profondeur, et le `Table` rend automatiquement le bon nombre de lignes d'en-tête.

## Création de groupes de colonnes {#creating-column-groups}

Créez un groupe avec la méthode de fabrique `ColumnGroup.of()`, puis enchaînez les appels `add()` pour le peupler avec des références de colonnes, d'autres groupes ou un mélange des deux. Appliquez les groupes à un `Table` avec `setColumnGroups()`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Lorsque les groupes sont définis, le `Table` affiche un en-tête à plusieurs lignes où chaque étiquette de groupe s'étend sur ses colonnes enfants. La profondeur d'imbrication détermine combien de lignes d'en-tête apparaissent. Un groupe plat ajoute une ligne supplémentaire, tandis qu'un imbrication à deux niveaux en ajoute deux, et ainsi de suite.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablenestedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Les groupes peuvent être définis ou changés à tout moment, avant ou après que le `Table` ait été rendu. Passez `null` ou une liste vide à `setColumnGroups()` pour supprimer tout groupement et revenir à un en-tête à une seule ligne.
<!-- vale Google.OxfordComma = YES -->

```java
// Supprimer tous les groupes de colonnes
table.setColumnGroups(null);
```

## Ordre des colonnes {#column-ordering}

Lorsque les groupes sont actifs, l'ordre des colonnes visuel est déterminé par l'arbre des groupes plutôt que par l'ordre dans lequel les colonnes ont été ajoutées au `Table`. L'arbre est parcouru en profondeur, de gauche à droite.

```
Colonnes ajoutées :  [A, B, C, D, E]
Groupes :          Groupe "G1" [C, A], Groupe "G2" [E, D]
Ordre visuel :    C, A, E, D, B
```

Les colonnes non groupées, celles qui ne sont référencées dans aucun groupe, ne sont pas masquées. Elles apparaissent à leur position naturelle par rapport aux colonnes groupées, en fonction de l'ordre dans lequel elles ont été ajoutées au `Table`.

Dans cet exemple, `Number` apparaît en premier parce qu'il a été ajouté avant `Title`. `Label` apparaît entre `Genre` et `Cost` parce qu'il a été ajouté entre eux dans l'ordre des colonnes d'origine :

```
Colonnes ajoutées :  [Number, Title, Artist, Genre, Label, Cost]
Groupes :          Groupe "Music" [Title, Artist, Genre], Groupe "Pricing" [Cost]
Ordre visuel :    Number, Title, Artist, Genre, Label, Cost
```

La démo suivante illustre ce comportement. `Number` et `Label` ne sont référencés dans aucun groupe, mais ils conservent leurs positions naturelles en fonction de l'ordre dans lequel ils ont été ajoutés au `Table`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroupordering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::tip Contrôle du placement des colonnes non groupées
Pour contrôler explicitement le placement des colonnes non groupées, incluez-les en tant que références de colonnes de niveau supérieur dans l'arbre des groupes.
:::

## Déplacement de colonnes dans les groupes {#column-moving-within-groups}

Lorsque les groupes sont actifs, le déplacement de colonnes par glisser-déposer est contraint pour maintenir l'intégrité du groupe :

- **À l'intérieur d'un groupe** : une colonne à l'intérieur d'un groupe ne peut être déplacée qu'à l'intérieur de son groupe parent immédiat. La tentative de la glisser en dehors du groupe est rejetée, et la colonne se remet à sa position d'origine.
- **Colonnes non groupées** : une colonne non groupée ne peut se déplacer qu'à des positions occupées par d'autres colonnes non groupées. Elle ne peut pas être déposée au milieu d'un groupe.
- **Réordonner les groupes** : un groupe entier peut être glissé pour le réorganiser parmi ses frères au même niveau d'imbrication.

```
Groupes :  Groupe "G1" [A, B, C], Groupe "G2" [D, E]

Déplacer A à la position 2 -> OK (dans G1, résultat : [B, C, A])
Déplacer A à la position 3 -> Rejeté (la position 3 est à l'intérieur de G2)
Déplacer D à la position 4 -> OK (dans G2, résultat : [E, D])
Déplacer D à la position 1 -> Rejeté (la position 1 est à l'intérieur de G1)
```

## Fixation des groupes {#pinning-groups}

Un groupe peut être fixé à gauche ou à droite en utilisant `setPinDirection()`. Toutes les colonnes à l'intérieur du groupe héritent de la direction de fixation du groupe, et les paramètres de fixation individuels des colonnes sont remplacés par ceux du groupe.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Both "number" and "title" are pinned left,
// regardless of their own pin settings
```

Les colonnes non groupées conservent leur propre direction de fixation selon leur définition de colonne.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablepinnedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

## Hauteur de l'en-tête de groupe {#group-header-height}

La hauteur de la ligne d'en-tête de groupe peut être contrôlée indépendamment des en-têtes de colonnes réguliers à l'aide de `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Les lignes de groupe mesurent 32px de haut
table.setHeaderHeight(48);      // La ligne d'en-tête de colonne reste à 48px
```

La hauteur d'en-tête de groupe par défaut correspond à la hauteur d'en-tête par défaut.

## Styliser les groupes avec des parties CSS {#styling-groups-with-css-parts}

Les en-têtes de groupe et les colonnes exposent des parties CSS pour le style via `::part()`. Les parties suivantes sont disponibles :

| Partie | Description |
| --- | --- |
| `cell-group-{ID}` | Cellule d'en-tête de groupe, ciblée par l'ID du groupe |
| `cell-group-depth-{N}` | Cellule d'en-tête de groupe, ciblée par profondeur (`0` = de premier niveau, `1` = de second niveau, etc.) |
| `cell-column-{ID}` | Toutes les cellules (en-tête et corps) pour un ID de colonne donné |
| `cell-content-group-{ID}` | Wrapper de contenu dans un en-tête de groupe |
| `cell-label-group-{ID}` | Étiquette dans un en-tête de groupe |

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablestyledcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java'
cssURL='/css/table/tablestyledcolumngroups.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

### Styliser par ID de groupe {#styling-by-group-id}

Utilisez l'ID de groupe pour cibler des groupes spécifiques avec des couleurs ou des typographies uniques.

```css
dwc-table::part(cell-group-catalog) {
  background: var(--dwc-color-primary-30);
  color: var(--dwc-color-primary-text-30);
  font-weight: 600;
}

dwc-table::part(cell-group-bio) {
  background: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}

dwc-table::part(cell-column-title) {
  font-weight: 600;
}
```

### Styliser par profondeur {#styling-by-depth}

Les parties basées sur la profondeur sont utiles lorsque vous souhaitez appliquer un style cohérent à tous les groupes à un certain niveau d'imbrication sans cibler chaque groupe individuellement.

```css
/* Styliser tous les groupes de premier niveau */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Styliser tous les groupes de deuxième niveau */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Colonnes cachées {#hidden-columns}

Les colonnes cachées sont exclues de l'ordre visuel et de la mise en page de l'en-tête. Si un groupe contient un mélange de colonnes visibles et cachées, seule les colonnes visibles apparaissent et le `colspan` du groupe s'ajuste en conséquence. Si toutes les colonnes d'un groupe sont cachées, l'en-tête du groupe n'est pas rendu du tout.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablehiddencolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->
