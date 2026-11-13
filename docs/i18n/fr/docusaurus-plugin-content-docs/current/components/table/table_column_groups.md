---
title: Groupes de colonnes
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 4babef21ef20728da6aa7eec5829e4d1
---
<!-- vale off -->
# Groupes de colonnes <DocChip chip='since' label='25.12' />
<!-- vale on -->

Les groupes de colonnes vous permettent d’organiser des colonnes liées sous des en-têtes partagés sur plusieurs lignes. Le libellé d’un groupe s’étend sur ses colonnes enfants, ce qui aide les utilisateurs à parcourir et à comprendre la structure des tableaux complexes. Les groupes peuvent être imbriqués à n’importe quelle profondeur, et `Table` affiche automatiquement le nombre de lignes d’en-tête adéquat.

## Créer des groupes de colonnes {#creating-column-groups}

Créez un groupe avec la méthode de fabrique `ColumnGroup.of()`, puis enchaînez les appels à `add()` pour y ajouter des références de colonnes, d’autres groupes ou une combinaison des deux. Appliquez les groupes à un `Table` avec `setColumnGroups()`.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Lorsque des groupes sont définis, `Table` affiche un en-tête sur plusieurs lignes, où chaque libellé de groupe s’étend sur ses colonnes enfants. La profondeur d’imbrication détermine le nombre de lignes d’en-tête affichées. Un groupe plat ajoute une ligne supplémentaire, tandis qu’une imbrication sur deux niveaux en ajoute deux, et ainsi de suite.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablenestedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Les groupes peuvent être définis ou modifiés à tout moment, avant ou après le rendu de `Table`. Passez `null` ou une liste vide à `setColumnGroups()` pour supprimer tous les groupes et revenir à un en-tête sur une seule ligne.
<!-- vale Google.OxfordComma = YES -->

```java
// Supprimer tous les groupes de colonnes
table.setColumnGroups(null);
```

## Ordre des colonnes {#column-ordering}

Lorsque des groupes sont actifs, l’ordre visuel des colonnes est déterminé par l’arborescence des groupes, et non par l’ordre dans lequel les colonnes ont été ajoutées à `Table`. L’arborescence est parcourue en profondeur, de gauche à droite.

```
Colonnes ajoutées :  [A, B, C, D, E]
Groupes :            Groupe "G1" [C, A], Groupe "G2" [E, D]
Ordre visuel :       C, A, E, D, B
```

Les colonnes non groupées, c’est-à-dire celles qui ne sont référencées dans aucun groupe, ne sont pas masquées. Elles apparaissent à leur position naturelle par rapport aux colonnes groupées, selon l’ordre dans lequel elles ont été ajoutées à l’origine à `Table`.

Dans cet exemple, `Number` apparaît en premier, car il a été ajouté avant `Title`. `Label` apparaît entre `Genre` et `Cost`, car il a été ajouté entre ces deux colonnes dans l’ordre d’origine :

```
Colonnes ajoutées :  [Number, Title, Artist, Genre, Label, Cost]
Groupes :            Groupe "Music" [Title, Artist, Genre], Groupe "Pricing" [Cost]
Ordre visuel :       Number, Title, Artist, Genre, Label, Cost
```

La démo suivante illustre ce comportement. `Number` et `Label` ne sont référencés dans aucun groupe, mais conservent leur position naturelle, selon l’ordre dans lequel ils ont été ajoutés à `Table`.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroupordering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Contrôler le placement des colonnes non groupées
Pour contrôler explicitement le placement des colonnes non groupées, incluez-les comme références de colonnes de premier niveau dans l’arborescence des groupes.
:::

## Déplacement des colonnes dans les groupes {#column-moving-within-groups}

Lorsque des groupes sont actifs, le déplacement des colonnes par glisser-déposer est soumis à des contraintes afin de préserver l’intégrité des groupes :

- **Dans un groupe** : une colonne située dans un groupe ne peut être déplacée qu’au sein de son groupe parent immédiat. Si vous la faites glisser hors du groupe, le déplacement est rejeté et la colonne revient à sa position d’origine.
- **Colonnes non groupées** : une colonne non groupée ne peut être déplacée qu’à une position occupée par une autre colonne non groupée. Elle ne peut pas être déposée au milieu d’un groupe.
- **Réorganisation des groupes** : un groupe entier peut être déplacé par glisser-déposer pour le réorganiser parmi ses groupes frères au même niveau d’imbrication.

```
Groupes : Groupe "G1" [A, B, C], Groupe "G2" [D, E]

Déplacer A à la position 2 -> OK (dans G1, résultat : [B, C, A])
Déplacer A à la position 3 -> Rejeté (la position 3 se trouve dans G2)
Déplacer D à la position 4 -> OK (dans G2, résultat : [E, D])
Déplacer D à la position 1 -> Rejeté (la position 1 se trouve dans G1)
```

## Épingler des groupes {#pinning-groups}

Un groupe peut être épinglé à gauche ou à droite à l’aide de `setPinDirection()`. Toutes les colonnes du groupe héritent du sens d’épinglage du groupe, qui remplace les paramètres d’épinglage individuels des colonnes.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// "number" et "title" sont tous deux épinglés à gauche,
// quels que soient leurs propres paramètres d’épinglage
```

Les colonnes non groupées conservent leur propre sens d’épinglage, défini dans leur définition de colonne.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablepinnedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

## Hauteur de l’en-tête de groupe {#group-header-height}

La hauteur de la ligne d’en-tête de groupe peut être réglée indépendamment de celle des en-têtes de colonnes habituels avec `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Les lignes de groupe font 32 px de haut
table.setHeaderHeight(48);      // La ligne d’en-tête des colonnes reste à 48 px
```

Par défaut, la hauteur de l’en-tête de groupe correspond à celle de l’en-tête.

## Styliser les groupes avec des parties CSS {#styling-groups-with-css-parts}

Les en-têtes de groupe et les colonnes exposent des parties CSS qui permettent de les styliser à l’aide de `::part()`. Les parties suivantes sont disponibles :

| Partie | Description |
| --- | --- |
| `cell-group-{ID}` | Cellule d’en-tête de groupe, ciblée par l’ID du groupe |
| `cell-group-depth-{N}` | Cellule d’en-tête de groupe, ciblée par profondeur (`0` = premier niveau, `1` = deuxième niveau, etc.) |
| `cell-column-{ID}` | Toutes les cellules (d’en-tête et de corps) d’une colonne donnée |
| `cell-content-group-{ID}` | Conteneur de contenu dans un en-tête de groupe |
| `cell-label-group-{ID}` | Libellé dans un en-tête de groupe |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/frontend/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Styliser par ID de groupe {#styling-by-group-id}

Utilisez l’ID du groupe pour appliquer des couleurs ou une typographie différentes à des groupes précis.

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

Les parties basées sur la profondeur sont utiles pour appliquer un style cohérent à tous les groupes d’un même niveau d’imbrication, sans avoir à cibler chaque groupe individuellement.

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

## Colonnes masquées {#hidden-columns}

Les colonnes masquées sont exclues de l’ordre visuel et de la disposition de l’en-tête. Si un groupe contient à la fois des colonnes visibles et masquées, seules les colonnes visibles apparaissent et le `colspan` du groupe est ajusté en conséquence. Si toutes les colonnes d’un groupe sont masquées, l’en-tête de groupe n’est pas rendu.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablehiddencolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->
