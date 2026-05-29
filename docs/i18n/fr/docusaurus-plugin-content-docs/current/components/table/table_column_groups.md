---
title: Column Groups
sidebar_position: 7
_i18n_hash: f4ab153f6d1e8c4d029e16c3abc41762
---
<DocChip chip='since' label='25.12' />

Les groupes de colonnes vous permettent d'organiser des colonnes liées sous des en-têtes partagés et multi-lignes. Une étiquette de groupe s'étend sur ses colonnes enfants, facilitant la tâche des utilisateurs pour parcourir et comprendre la structure de tables complexes. Les groupes peuvent être imbriqués à n'importe quelle profondeur, et le `Table` rend automatiquement le bon nombre de lignes d'en-tête.

## Création de groupes de colonnes {#creating-column-groups}

Créez un groupe avec la méthode de fabrique `ColumnGroup.of()`, puis enchaînez les appels à `add()` pour le remplir de références de colonnes, d'autres groupes, ou un mélange des deux. Appliquez les groupes à un `Table` avec `setColumnGroups()`.

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

Lorsque les groupes sont définis, le `Table` rend un en-tête multi-lignes où chaque étiquette de groupe s'étend sur ses colonnes enfants. La profondeur de l'imbrication détermine combien de lignes d'en-tête apparaissent. Un groupe plat ajoute une ligne supplémentaire, tandis qu'un imbriqué à deux niveaux en ajoute deux, et ainsi de suite.

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
Les groupes peuvent être définis ou modifiés à tout moment, avant ou après le rendu du `Table`. Passez `null` ou une liste vide à `setColumnGroups()` pour supprimer tout le regroupement et revenir à un en-tête à une seule ligne.
<!-- vale Google.OxfordComma = YES -->

```java
// Supprimer tous les groupes de colonnes
table.setColumnGroups(null);
```

## Ordre des colonnes {#column-ordering}

Lorsque les groupes sont actifs, l'ordre visuel des colonnes est déterminé par l'arbre des groupes plutôt que par l'ordre dans lequel les colonnes ont été ajoutées au `Table`. L'arbre est parcouru en profondeur, de gauche à droite.

```
Colonnes ajoutées :  [A, B, C, D, E]
Groupes :          Groupe "G1" [C, A], Groupe "G2" [E, D]
Ordre visuel :     C, A, E, D, B
```

Les colonnes non groupées, celles qui ne sont pas référencées dans un groupe, ne sont pas cachées. Elles apparaissent à leur position naturelle par rapport aux colonnes groupées, sur la base de l'ordre dans lequel elles ont été ajoutées au `Table`.

Dans cet exemple, `Number` apparaît en premier car il a été ajouté avant `Title`. `Label` apparaît entre `Genre` et `Cost` car il a été ajouté entre eux dans l'ordre de colonne original :

```
Colonnes ajoutées :  [Number, Title, Artist, Genre, Label, Cost]
Groupes :          Groupe "Music" [Title, Artist, Genre], Groupe "Pricing" [Cost]
Ordre visuel :     Number, Title, Artist, Genre, Label, Cost
```

La démo suivante illustre ce comportement. `Number` et `Label` ne sont référencés dans aucun groupe, mais ils conservent leurs positions naturelles en fonction de l'ordre dans lequel ils ont été ajoutés au `Table`.

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

:::tip Contrôle du placement des colonnes non groupées
Pour contrôler le placement des colonnes non groupées explicitement, incluez-les comme références de colonnes de premier niveau dans l'arbre des groupes.
:::

## Déplacement des colonnes au sein des groupes {#column-moving-within-groups}

Lorsque les groupes sont actifs, le mouvement de colonnes par glisser-déposer est contraint pour maintenir l'intégrité du groupe :

- **Au sein d'un groupe** : une colonne à l'intérieur d'un groupe ne peut être déplacée qu'au sein de son groupe parent immédiat. La glisser hors du groupe est rejetée, et la colonne revient à sa position d'origine.
- **Colonnes non groupées** : une colonne non groupée ne peut être déplacée qu'à des positions occupées par d'autres colonnes non groupées. Elle ne peut pas être déposée au milieu d'un groupe.
- **Réorganisation des groupes** : un groupe entier peut être glissé pour le réorganiser parmi ses homologues au même niveau d'imbrication.

```
Groupes :  Groupe "G1" [A, B, C], Groupe "G2" [D, E]

Déplacez A à la position 2 -> OK (dans G1, résultat : [B, C, A])
Déplacez A à la position 3 -> Rejeté (la position 3 est à l'intérieur de G2)
Déplacez D à la position 4 -> OK (dans G2, résultat : [E, D])
Déplacez D à la position 1 -> Rejeté (la position 1 est à l'intérieur de G1)
```

## Fixation des groupes {#pinning-groups}

Un groupe peut être fixé à gauche ou à droite en utilisant `setPinDirection()`. Toutes les colonnes à l'intérieur du groupe héritent de la direction de fixation du groupe, et les paramètres de fixation des colonnes individuelles sont remplacés par ceux du groupe.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "Informations ID")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// À la fois "number" et "title" sont fixés à gauche,
// indépendamment de leurs propres paramètres de fixation
```

Les colonnes non groupées conservent leur propre direction de fixation à partir de leur définition de colonne.

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

## Hauteur d'en-tête du groupe {#group-header-height}

La hauteur de la ligne d'en-tête de groupe peut être contrôlée indépendamment des en-têtes de colonnes réguliers en utilisant `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Les lignes de groupe font 32px de haut
table.setHeaderHeight(48);      // La ligne d'en-tête de colonne reste à 48px
```

La hauteur par défaut de l'en-tête de groupe correspond à la hauteur d'en-tête par défaut.

## Stylisation des groupes avec des parties CSS {#styling-groups-with-css-parts}

Les en-têtes de groupe et les colonnes exposent des parties CSS pour le stylage via `::part()`. Les parties suivantes sont disponibles :

| Partie | Description |
| --- | --- |
| `cell-group-{ID}` | Cellule d'en-tête de groupe, ciblée par l'ID de groupe |
| `cell-group-depth-{N}` | Cellule d'en-tête de groupe, ciblée par la profondeur (`0` = niveau supérieur, `1` = deuxième niveau, etc.) |
| `cell-column-{ID}` | Toutes les cellules (en-tête et corps) pour un ID de colonne donné |
| `cell-content-group-{ID}` | Wrapper de contenu à l'intérieur de l'en-tête de groupe |
| `cell-label-group-{ID}` | Étiquette à l'intérieur de l'en-tête de groupe |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/resources/static/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Stylisation par ID de groupe {#styling-by-group-id}

Utilisez l'ID de groupe pour cibler des groupes spécifiques avec des couleurs ou une typographie uniques.

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

### Stylisation par profondeur {#styling-by-depth}

Les parties basées sur la profondeur sont utiles lorsque vous souhaitez appliquer un style cohérent à tous les groupes à un certain niveau d'imbrication sans cibler chaque groupe individuellement.

```css
/* Style tous les groupes de premier niveau */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Style tous les groupes de deuxième niveau */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Colonnes cachées {#hidden-columns}

Les colonnes cachées sont exclues de l'ordre visuel et de la mise en page de l'en-tête. Si un groupe contient un mélange de colonnes visibles et cachées, seules celles visibles apparaissent et le `colspan` du groupe s'ajuste en conséquence. Si chaque colonne d'un groupe est cachée, l'en-tête du groupe n'est pas du tout rendu.

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
