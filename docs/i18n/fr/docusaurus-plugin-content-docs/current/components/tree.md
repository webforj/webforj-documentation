---
title: Tree
sidebar_position: 150
description: >-
  Display hierarchical data with the Tree component, using TreeNode parent-child
  links, expand or collapse, icons, and selection.
_i18n_hash: cacef36e8323b6349fa209a7c057c88b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Le composant `Tree` organise les données sous forme d'une hiérarchie de nœuds. Chaque nœud possède une clé unique et une étiquette. Les nœuds se connectent pour former des relations parent-enfant. Vous pouvez développer ou réduire les nœuds pour montrer ou cacher leurs enfants. Des icônes clarifient quel type de nœud vous visualisez et s'il est sélectionné. La sélection permet de choisir un ou plusieurs nœuds à la fois.

<!-- INTRO_END -->

## Modèle de nœud et structure d'arbre {#node-model-and-tree-structure}

### Le rôle de `TreeNode` <DocChip chip="name" label="dwc-tree-node" /> {#the-role-of-treenode}

Chaque donnée dans l'arbre est enveloppée dans un `TreeNode`. Cet objet contient la clé, l'étiquette textuelle, et des liens vers ses nœuds parents et enfants. Le nœud racine est spécial : il existe dans chaque arbre mais n'est pas visible. Il sert de conteneur pour tous les nœuds de niveau supérieur, facilitant ainsi la gestion de la structure de l'arbre en interne.

Étant donné que les nœuds gardent des références à leurs parents et enfants, le parcours de l'arbre est simple. Que vous souhaitiez monter, descendre ou trouver un nœud spécifique par sa clé, les connexions sont toujours accessibles.

### Création et gestion des nœuds {#node-creation-and-management}

Les nœuds sont créés à l'aide de méthodes de fabrique simples, soit en fournissant une clé et un texte, soit juste un texte (qui fait également office de clé). Cela garantit que chaque nœud est valide et identifiable de manière unique.

Ajouter des nœuds à l'arbre implique d'appeler `add()` ou `insert()` sur un nœud parent. Ces méthodes gèrent l'attribution de la référence parent et notifient l'arbre pour mettre à jour son interface utilisateur.

Exemple :

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Un seul parent
Tenter d'assigner le même nœud à plusieurs parents entraînera le lancement d'une exception. Cette protection garantit que l'arbre maintienne une hiérarchie correcte en empêchant les nœuds d'avoir plusieurs parents, ce qui compromettrait l'intégrité de la structure et provoquerait un comportement inattendu.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Modification des nœuds {#modifying-nodes}

Vous mettez à jour l'étiquette d'un nœud en appelant `setText(String text)`. Cette méthode change le texte affiché pour le nœud dans l'arbre.

Pour supprimer un nœud enfant spécifique, utilisez `remove(TreeNode child)`. Cela détache l'enfant de son parent et le retire de la structure de l'arbre. Cela supprime également la référence parent.

Si vous souhaitez vider tous les enfants d'un nœud, appelez `removeAll()`. Cela retire chaque nœud enfant, efface leurs références parent et vide la liste des enfants.

Chaque nœud prend en charge le stockage d'informations supplémentaires côté serveur à l'aide de `setUserData(Object key, Object data)`. Cela vous permet d'associer des métadonnées ou des références arbitraires au nœud, sans exposer ces données au client ou à l'interface utilisateur.

:::tip Utiliser la démo pour modifier le texte d'un nœud
Dans la démo, double-cliquez sur un nœud pour ouvrir un éditeur pour son texte. Saisissez le nouveau texte et enregistrez-le pour mettre à jour l'étiquette du nœud dans l'arbre.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/frontend/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Icônes {#icons}

Les icônes fournissent des indices visuels sur ce que représentent les nœuds et leur état. Elles améliorent la lisibilité en distinguant les types de nœuds et le statut de sélection d'un coup d'œil. Le composant `Tree` prend en charge la définition d'icônes par défaut globalement, la personnalisation d'icônes par nœud et la commutation de la visibilité des icônes.

### Icônes globales {#global-icons}

L'arbre vous permet de définir des icônes par défaut pour les groupes réduits, les groupes développés, les nœuds feuilles et les feuilles sélectionnées.

Exemple :

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Sources d'icônes
Une icône peut être toute définition d'[icône](./icon) valide pour webforJ ou un fichier de ressource chargé via des [protocoles de ressources pris en charge par webforJ](../managing-resources/assets-protocols).
:::

### Icônes par nœud {#per-node-icons}

Vous pouvez remplacer les valeurs par défaut globales en assignant des icônes à des nœuds individuels. Cela est utile lorsque certains nœuds représentent des concepts différents, comme des dossiers de projet ou des fichiers spéciaux.

Exemple :

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Visibilité des icônes {#icon-visibility}

Parfois, vous pourriez vouloir cacher les icônes pour les groupes ou les feuilles afin de réduire l'encombrement. Le composant vous permet de basculer la visibilité globalement pour ces catégories, vous laissant simplifier l'apparence de l'arbre sans perdre la structure.

Exemple :

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo
path='/webforj/treeicons'
files={['src/main/java/com/webforj/samples/views/tree/TreeIconsView.java']}
height='320px'
/>

## Expansion et effondrement des nœuds {#node-expansion-and-collapse}

Les nœuds peuvent être développés ou réduits pour contrôler quelles parties de l'arbre sont visibles. Cela permet de se concentrer sur des sections pertinentes et prend en charge des scénarios comme le chargement paresseux ou les mises à jour de données dynamiques.

### Opérations d'expansion et d'effondrement {#expand-and-collapse-operations}

L'arbre prend en charge l'expansion et l'effondrement des nœuds individuels par leur clé ou leur référence directe. Vous pouvez également développer ou réduire tous les descendants d'un nœud à la fois.

Ces opérations vous permettent de contrôler combien de l'arbre est visible et prennent en charge le chargement paresseux des données ou la focalisation sur des zones d'intérêt.

Exemple :

```java
tree.expand(node);
tree.collapse(key);

// effondrer des sous-arbres
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Effondrement de la racine
Le nœud racine ancre l'arbre mais reste caché. Réduire la racine cacherait normalement tout, faisant apparaître l'arbre comme vide. Pour éviter cela, réduire la racine réduit en fait tous ses enfants, mais garde la racine développée en interne, garantissant que l'arbre affiche toujours correctement son contenu.
:::

### Chargement paresseux des nœuds {#lazy-loading-nodes}

L'arbre prend en charge le chargement paresseux des enfants des nœuds en réagissant aux événements d'expansion. Lorsque l'utilisateur développe un nœud, votre application peut charger ou générer dynamiquement les enfants de ce nœud. Cela améliore les performances en chargeant uniquement les parties visibles de l'arbre à la demande.

Utilisez l'événement `onExpand` pour détecter quand un nœud est développé. À l'intérieur du gestionnaire, vérifiez si les enfants du nœud sont des espaces réservés (par exemple, un indicateur de chargement ou nœud vide) et remplacez-les par des données réelles une fois chargées.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Sélection {#selection}

La sélection contrôle quels nœuds sont choisis par l'utilisateur. Le composant `Tree` prend en charge des modes flexibles et des API pour sélectionner, désélectionner et interroger des nœuds.

### Modes de sélection {#selection-modes}

Vous pouvez choisir si l'arbre permet la sélection d'un seul nœud à la fois ou de plusieurs nœuds simultanément. Le passage de la sélection multiple à la sélection unique désélectionne automatiquement tous sauf le premier nœud sélectionné.

Exemple :

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaction de sélection multiple
Lorsque l'arbre est réglé sur le mode de sélection multiple, les utilisateurs peuvent sélectionner plus d'un nœud à la fois. Cela dépend de l'appareil :

* **Bureau (souris et clavier) :** Les utilisateurs maintiennent la touche **Ctrl** (ou la touche **Cmd** sur macOS) et cliquent sur les nœuds pour les ajouter ou les retirer de la sélection actuelle. Cela permet de sélectionner plusieurs nœuds individuels sans désélectionner d'autres.
* **Appareils mobiles et tactiles :** Étant donné que les touches modificateurs ne sont pas disponibles, les utilisateurs tapent simplement sur les nœuds pour les sélectionner ou les désélectionner. Chaque tapotement bascule l'état de sélection de ce nœud, permettant une sélection multiple facile par de simples tapotements.
:::

### Sélection et désélection {#selecting-and-deselecting}

Les nœuds peuvent être sélectionnés ou désélectionnés par référence, clé, individuellement, ou en lots. Vous pouvez également sélectionner ou désélectionner tous les enfants d'un nœud en un seul appel.

Exemple :

```java
// sélectionner un nœud par référence ou clé
tree.select(node);
tree.selectKey(key);

// désélectionner un nœud par référence ou clé
tree.deselect(node);
tree.deselectAll();

// sélectionner ou désélectionner les enfants des nœuds
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Récupération de l'état de sélection {#selection-state-retrieval}

Vous pouvez obtenir la sélection actuelle en utilisant le code affiché ci-dessous :

```java
// obtenir la référence du nœud sélectionné
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// obtenir la clé du nœud sélectionné
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## Style {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
