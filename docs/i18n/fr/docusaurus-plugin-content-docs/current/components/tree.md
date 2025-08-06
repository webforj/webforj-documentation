---
title: Tree
sidebar_position: 150
_i18n_hash: b161d0d5855f65cb593cf23bc2695d5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Le composant `Tree` organise les données sous forme de hiérarchie de nœuds. Chaque nœud contient une clé unique et un label. Les nœuds se connectent pour former des relations parent-enfant. Vous pouvez développer ou réduire les nœuds pour afficher ou masquer leurs enfants. Les icônes clarifient quel type de nœud vous regardez et s'il est sélectionné. La sélection prend en charge le choix d'un ou plusieurs nœuds à la fois.

## Modèle de nœud et structure d'arbre {#node-model-and-tree-structure}

### Le rôle de `TreeNode` {#the-role-of-treenode}

Chaque morceau de données dans l'arbre est enveloppé dans un `TreeNode`. Cet objet contient la clé, le label en texte, et des liens vers ses nœuds parent et enfants. Le nœud racine est spécial : il existe dans chaque arbre mais n'est pas visible. Il sert de conteneur pour tous les nœuds de premier niveau, rendant la structure de l'arbre plus facile à gérer en interne.

Comme les nœuds conservent des références à leurs parents et enfants, la traversée de l'arbre est simple. Que vous souhaitiez monter, descendre ou trouver un nœud spécifique par clé, les connexions sont toujours accessibles.

### Création et gestion des nœuds {#node-creation-and-management}

Les nœuds sont créés à l'aide de méthodes de fabrique simples, en fournissant soit une clé et du texte, soit juste du texte (qui double en tant que clé). Cela garantit que chaque nœud est valide et identifiable de manière unique.

Ajouter des nœuds à l'arbre implique d'appeler `add()` ou `insert()` sur un nœud parent. Ces méthodes gèrent l'attribution de la référence parent et notifient l'arbre de mettre à jour son interface utilisateur.

Exemple :

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Un seul parent
Essayer d'assigner le même nœud à plus d'un parent entraînera une exception. Cette protection garantit que l'arbre maintient une hiérarchie correcte en empêchant les nœuds d'avoir plusieurs parents, ce qui briserait l'intégrité de la structure et provoquerait un comportement imprévisible.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Modification des nœuds {#modifying-nodes}

Vous mettez à jour le label d'un nœud en appelant `setText(String text)`. Cette méthode change le texte affiché pour le nœud dans l'arbre.

Pour supprimer un nœud enfant spécifique, utilisez `remove(TreeNode child)`. Cela détache l'enfant de son parent et le supprime de la structure de l'arbre. Cela vide également la référence parent.

Si vous souhaitez vider tous les enfants d'un nœud, appelez `removeAll()`. Cela supprime chaque nœud enfant, vide leurs références parent et vide la liste des enfants.

Chaque nœud prend en charge le stockage d'informations supplémentaires côté serveur en utilisant `setUserData(Object key, Object data)`. Cela vous permet d'associer des métadonnées ou des références arbitraires avec le nœud, sans exposer ces données au client ou à l'UI.

:::tip Utiliser la démo pour modifier le texte des nœuds
Dans la démo, double-cliquez sur un nœud pour ouvrir un éditeur pour son texte. Entrez le nouveau texte et enregistrez-le pour mettre à jour le label du nœud dans l'arbre.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Icônes {#icons}

Les icônes fournissent des indications visuelles sur ce que représentent les nœuds et leur état. Elles améliorent la lisibilité en distinguant les types de nœuds et l'état de sélection d'un coup d'œil. Le composant `Tree` prend en charge la définition d'icônes par défaut globalement, la personnalisation des icônes par nœud et le basculement de la visibilité des icônes.

### Icônes globales {#global-icons}

L'arbre vous permet de définir des icônes par défaut pour les groupes réduits, les groupes développés, les nœuds feuilles, et les feuilles sélectionnées.

Exemple :

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Sources d'icônes
Une icône peut être n'importe quelle définition d'icône webforJ [icon](./icon) valide ou un fichier de ressource chargé via un [protocoles pris en charge par les actifs](../managing-resources/assets-protocols) webforJ.
:::

### Icônes par nœud {#per-node-icons}

Vous pouvez remplacer les défauts globaux en assignant des icônes à des nœuds individuels. Cela est utile lorsque certains nœuds représentent des concepts différents, comme des dossiers de « projet » ou des fichiers spéciaux.

Exemple :

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Visibilité des icônes {#icon-visibility}

Parfois, vous voudrez peut-être cacher les icônes pour des groupes ou des feuilles afin de réduire le désordre. Le composant vous permet de basculer la visibilité globalement pour ces catégories, vous permettant de simplifier l'apparence de l'arbre sans perdre la structure.

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

## Expansion et rétraction des nœuds {#node-expansion-and-collapse}

Les nœuds peuvent être développés ou réduits pour contrôler quelles parties de l'arbre sont visibles. Cela permet de se concentrer sur des sections pertinentes et prend en charge des scénarios tels que le chargement paresseux ou les mises à jour de données dynamiques.

### Opérations d'expansion et de rétraction {#expand-and-collapse-operations}

L'arbre prend en charge l'expansion et la rétraction de nœuds individuels soit par leur clé, soit par référence directe. Vous pouvez également développer ou réduire tous les descendants d'un nœud à la fois.

Ces opérations vous permettent de contrôler combien de l'arbre est visible et prennent en charge le chargement paresseux de données ou le focus sur des zones d'intérêt.

Exemple :

```java
tree.expand(node);
tree.collapse(key);

// réduire les sous-arbres
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Rétracter la racine
Le nœud racine ancre l'arbre mais reste caché. Réduire la racine cacherait normalement tout, faisant apparaître l'arbre vide. Pour éviter cela, réduire la racine réduit en fait tous ses enfants mais garde la racine développée en interne, garantissant que l'arbre montre toujours son contenu correctement.
:::

### Chargement paresseux de nœuds {#lazy-loading-nodes}

L'arbre prend en charge le chargement paresseux des enfants des nœuds en réagissant aux événements d'expansion. Lorsque l'utilisateur développe un nœud, votre application peut charger ou générer dynamiquement les enfants de ce nœud. Cela améliore les performances en ne chargeant que les parties visibles de l'arbre à la demande.

Utilisez l'événement `onExpand` pour détecter quand un nœud est développé. À l'intérieur du gestionnaire, vérifiez si les enfants du nœud sont des espaces réservés (par exemple, un spinner ou un nœud vide) et remplacez-les par des données réelles une fois chargées.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Sélection {#selection}

La sélection contrôle quels nœuds sont choisis par l'utilisateur. Le composant `Tree` prend en charge des modes flexibles et des APIs pour sélectionner, désélectionner, et interroger les nœuds.

### Modes de sélection {#selection-modes}

Vous pouvez choisir si l'arbre permet de sélectionner un seul nœud à la fois ou plusieurs nœuds simultanément. Passer de la sélection multiple à la sélection unique désélectionne automatiquement tous les nœuds sauf le premier nœud sélectionné.

Exemple :

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaction de sélection multiple
Lorsque l'arbre est réglé en mode de sélection multiple, les utilisateurs peuvent sélectionner plus d'un nœud à la fois. La façon dont cela fonctionne dépend de l'appareil :

* **Bureau (souris et clavier) :** Les utilisateurs maintiennent la touche **Ctrl** (ou la touche **Cmd** sur macOS) et cliquent sur les nœuds pour les ajouter ou les supprimer de la sélection actuelle. Cela permet de sélectionner plusieurs nœuds individuels sans désélectionner les autres.
* **Appareils mobiles et tactiles :** Comme les touches de modification ne sont pas disponibles, les utilisateurs tapent simplement sur les nœuds pour les sélectionner ou les désélectionner. Chaque tap est basculé l'état de sélection de ce nœud, permettant une sélection multiple facile par des taps simples.
:::

### Sélection et désélection {#selecting-and-deselecting}

Les nœuds peuvent être sélectionnés ou désélectionnés par référence, clé, individuellement, ou par lots. Vous pouvez également sélectionner ou désélectionner tous les enfants d'un nœud en un seul appel.

Exemple :

```java
// sélectionner le nœud par référence ou clé
tree.select(node);
tree.selectKey(key);

// désélectionner le nœud par référence ou clé
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
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Style {#styling}

<TableBuilder name="Tree" />
