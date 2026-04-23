---
title: Tree
sidebar_position: 150
_i18n_hash: 6d2decdf16e3054012a22aca28980ccf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Le composant `Tree` organise les données sous forme de hiérarchie de nœuds. Chaque nœud contient une clé unique et une étiquette. Les nœuds se connectent pour former des relations parent-enfant. Vous pouvez développer ou réduire les nœuds pour afficher ou cacher leurs enfants. Des icônes clarifient quel type de nœud vous regardez et s’il est sélectionné. La sélection permet de choisir un nœud ou plusieurs à la fois.

<!-- INTRO_END -->

## Modèle de nœud et structure de l'arborescence {#node-model-and-tree-structure}

### Le rôle de `TreeNode` {#the-role-of-treenode}

Chaque élément de données dans l'arborescence est encapsulé dans un `TreeNode`. Cet objet contient la clé, l'étiquette de texte et des liens vers ses nœuds parents et enfants. Le nœud racine est spécial : il existe dans chaque arbre mais n'est pas visible. Il sert de conteneur pour tous les nœuds de premier niveau, ce qui rend la structure de l'arborescence plus facile à gérer en interne.

Comme les nœuds gardent des références à leurs parents et enfants, traverser l’arborescence est simple. Que vous souhaitiez monter, descendre ou trouver un nœud spécifique par clé, les connexions sont toujours accessibles.

### Création et gestion des nœuds {#node-creation-and-management}

Les nœuds sont créés à l'aide de méthodes de fabrique simples, que ce soit en fournissant une clé et un texte ou simplement un texte (qui fait double emploi comme clé). Cela garantit que chaque nœud est valide et identifiable de manière unique.

Ajouter des nœuds à l'arborescence implique d'appeler `add()` ou `insert()` sur un nœud parent. Ces méthodes gèrent l'attribution de la référence parent et notifient l'arborescence de mettre à jour son interface utilisateur.

Exemple :

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Un seul parent
Essayer d'assigner le même nœud à plus d'un parent entraînera une exception. Cette protection garantit que l'arborescence maintient une hiérarchie appropriée en empêchant les nœuds d'avoir plusieurs parents, ce qui pourrait compromettre l'intégrité de la structure et provoquer un comportement inattendu.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Modification des nœuds {#modifying-nodes}

Vous mettez à jour l’étiquette d’un nœud en appelant `setText(String text)`. Cette méthode change le texte affiché pour le nœud dans l'arborescence.

Pour supprimer un nœud enfant spécifique, utilisez `remove(TreeNode child)`. Cela détache l'enfant de son parent et le supprime de la structure de l'arborescence. Cela efface également la référence parent.

Si vous souhaitez effacer tous les enfants d'un nœud, appelez `removeAll()`. Cela supprime chaque nœud enfant, efface leurs références parent et vide la liste des enfants.

Chaque nœud prend en charge le stockage d'informations supplémentaires côté serveur en utilisant `setUserData(Object key, Object data)`. Cela vous permet d'associer des métadonnées ou des références arbitraires avec le nœud, sans exposer ces données au client ou à l'interface utilisateur.

:::tip Utiliser la démo pour éditer le texte du nœud
Dans la démo, double-cliquez sur un nœud pour ouvrir un éditeur pour son texte. Entrez le nouveau texte et enregistrez-le pour mettre à jour l'étiquette du nœud dans l'arborescence.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
cssURL='/css/tree/tree-modify-view.css'
height='320px'
/>

## Icônes {#icons}

Les icônes fournissent des repères visuels sur ce que représentent les nœuds et leur état. Elles améliorent la lisibilité en distinguant les types de nœuds et l'état de sélection en un coup d'œil. Le composant `Tree` prend en charge la définition d'icônes par défaut au niveau global, la personnalisation des icônes par nœud et le basculement de la visibilité des icônes.

### Icônes globales {#global-icons}

L'arborescence vous permet de définir des icônes par défaut pour les groupes réduits, les groupes développés, les nœuds de feuille et les feuilles sélectionnées.

Exemple :

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Sources d’icônes
Une icône peut être n'importe quelle définition d' [icône](./icon) valide dans webforJ ou un fichier de ressource chargé via un des [protocoles d'actifs pris en charge](../managing-resources/assets-protocols).
:::

### Icônes par nœud {#per-node-icons}

Vous pouvez remplacer les valeurs par défaut globales en assignant des icônes à des nœuds individuels. Cela est utile lorsque certains nœuds représentent des concepts différents, comme des dossiers de “projet” ou des fichiers spéciaux.

Exemple :

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Visibilité des icônes {#icon-visibility}

Parfois, vous voudrez peut-être cacher des icônes pour des groupes ou des feuilles afin de réduire l'encombrement. Le composant vous permet de basculer la visibilité au niveau global pour ces catégories, vous permettant ainsi de simplifier l'apparence de l'arborescence sans perdre sa structure.

Exemple :

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## Expansion et réduction des nœuds {#node-expansion-and-collapse}

Les nœuds peuvent être développés ou réduits pour contrôler quelles parties de l'arborescence sont visibles. Cela permet de se concentrer sur des sections pertinentes et prend en charge des scénarios comme le chargement à la demande ou les mises à jour de données dynamiques.

### Opérations d'expansion et de réduction {#expand-and-collapse-operations}

L'arborescence prend en charge l'expansion et la réduction de nœuds individuels par leur clé ou leur référence directe. Vous pouvez également développer ou réduire tous les descendants d'un nœud à la fois.

Ces opérations vous permettent de contrôler combien de l'arborescence est visible et prennent en charge le chargement à la demande de données ou la concentration sur des zones d'intérêt.

Exemple :

```java
tree.expand(node);
tree.collapse(key);

// réduire les sous-arbres
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Réduction de la racine
Le nœud racine ancre l'arborescence, mais reste caché. Réduire la racine cacherait normalement tout, faisant apparaître l'arborescence comme vide. Pour éviter cela, la réduction de la racine réduit en réalité tous ses enfants mais garde la racine développée en interne, garantissant que l'arborescence montre toujours son contenu correctement.
:::

### Chargement paresseux des nœuds {#lazy-loading-nodes}

L'arborescence prend en charge le chargement paresseux des enfants des nœuds en réagissant aux événements d'expansion. Lorsque l'utilisateur développe un nœud, votre application peut charger ou générer dynamiquement les enfants de ce nœud. Cela améliore les performances en ne chargeant que les parties visibles de l'arborescence à la demande.

Utilisez l'événement `onExpand` pour détecter quand un nœud est développé. À l'intérieur du gestionnaire, vérifiez si les enfants du nœud sont des espaces réservés (par exemple, un indicateur de chargement ou un nœud vide) et remplacez-les par des données réelles une fois chargées.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Sélection {#selection}

La sélection détermine quels nœuds sont choisis par l'utilisateur. Le composant `Tree` prend en charge des modes flexibles et des API pour sélectionner, désélectionner et interroger des nœuds.

### Modes de sélection {#selection-modes}

Vous pouvez choisir si l'arborescence permet la sélection d'un seul nœud à la fois ou plusieurs nœuds simultanément. Passer d'une sélection multiple à une sélection unique désélectionne automatiquement tous sauf le premier nœud sélectionné.

Exemple :

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaction de sélection multiple
Lorsque l'arborescence est définie sur le mode de sélection multiple, les utilisateurs peuvent sélectionner plus d'un nœud à la fois. Le fonctionnement dépend de l'appareil :

* **Bureau (souris et clavier) :** Les utilisateurs maintiennent la touche **Ctrl** (ou la touche **Cmd** sur macOS) et cliquent sur les nœuds pour les ajouter ou les retirer de la sélection actuelle. Cela permet de sélectionner plusieurs nœuds individuels sans désélectionner les autres.
* **Appareils mobiles et tactiles :** Étant donné que les touches modificateurs ne sont pas disponibles, les utilisateurs tapent simplement sur les nœuds pour les sélectionner ou les désélectionner. Chaque tap augmente ou diminue l'état de sélection de ce nœud, permettant une sélection multiple facile par de simples tapotements.
:::

### Sélection et désélection {#selecting-and-deselecting}

Les nœuds peuvent être sélectionnés ou désélectionnés par référence, clé, individuellement ou en lots. Vous pouvez également sélectionner ou désélectionner tous les enfants d'un nœud en un seul appel.

Exemple :

```java
// sélectionner le nœud par référence ou clé
tree.select(node);
tree.selectKey(key);

// désélectionner le nœud par référence ou clé
tree.deselect(node);
tree.deselectAll();

// sélection ou désélection des enfants des nœuds
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
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Stylisation {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
