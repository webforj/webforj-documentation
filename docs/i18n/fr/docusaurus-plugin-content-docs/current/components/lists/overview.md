---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: cf4917e0ffb74122b24c210ec7502cbf
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Cette section décrit les fonctionnalités communes à tous les composants de liste et n'est pas une classe qui peut être instanciée ou utilisée directement.
:::

Il existe trois types de listes disponibles pour une utilisation dans vos applications : [`ListBox`](listbox), [`ChoiceBox`](choicebox) et [`ComboBox`](combobox). Ces composants affichent tous une liste d'éléments clé-valeur et fournissent des méthodes pour ajouter, supprimer, sélectionner et gérer les éléments dans la liste.

Cette page présente les fonctionnalités et le comportement partagés de tous les composants de liste, tandis que les détails spécifiques pour chacun sont abordés dans leurs pages respectives.

## Utilisation de `ListItem` {#using-listitem}

Les composants de liste sont composés d'objets <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>, qui représentent des éléments individuels dans une liste. Chaque <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> est associé à une clé unique et un texte d'affichage. Les caractéristiques importantes de la classe <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> incluent :

- Un <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> encapsule une clé unique `Object` et un texte `String` à afficher au sein du composant de liste.
- Vous pouvez construire un <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> en fournissant une clé et un texte, ou en spécifiant seulement le texte pour qu'une clé aléatoire soit générée.

## Gestion des objets `ListItem` avec l'API {#managing-listitem-objects-with-the-api}

Les différents composants de liste offrent plusieurs méthodes pour gérer la liste des éléments et maintenir un état cohérent entre la liste et le client. En utilisant ces méthodes, vous pouvez gérer efficacement les éléments au sein de la liste. L'API vous permet d'interagir avec et de manipuler la liste pour répondre aux exigences de votre application.

### Ajout d’éléments {#adding-items}

- **Ajout d’un élément :**

   - Pour ajouter un `ListItem` à la liste, vous pouvez utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink>.
   - Vous pouvez également ajouter un nouveau `ListItem` en spécifiant la clé et le texte en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> ou <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink>.

- **Insertion d'un élément à un index spécifique :**

   - Pour insérer un élément à un index spécifique, utilisez la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink>.
   - Vous pouvez insérer un élément avec une clé et un texte en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> ou <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink>.

- **Insertion de plusieurs éléments :** 

   - Vous pouvez insérer plusieurs éléments à un index spécifié en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink>.

:::tip
Pour optimiser les performances, au lieu de déclencher un message serveur-client chaque fois que vous utilisez la méthode `add()`, il est plus efficace de créer d'abord une liste d'objets <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>. Une fois que vous avez cette liste, vous pouvez les ajouter tous en une fois en utilisant la méthode `insert(int index, List<ListItem> items)`. Cette approche réduit la communication serveur-client, améliorant l'efficacité globale. Pour des directives détaillées sur cela et d'autres meilleures pratiques dans l'architecture webforJ, consultez [Client/Server Interaction](/docs/architecture/client-server).
:::

### Suppression d’éléments {#removing-items}

- **Suppression d’un élément :**

   - Pour supprimer un élément de la liste, utilisez la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> ou <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink>.

- **Suppression de tous les éléments :**
   - Vous pouvez supprimer tous les éléments de la liste en utilisant <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Sélection d’éléments {#selecting-items}

Tous les types de liste implémentent l'interface `SelectableList`. Cette interface permet plusieurs manières différentes de sélectionner le `ListItem` actuel.

#### Avec un `ListItem` donné {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> prend un `ListItem` comme paramètre pour sélectionner.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Élément de démonstration");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Avec une clé donnée d'un `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> prend une clé d'un `ListItem` comme paramètre pour sélectionner.

```java {3}
List demoList = new List();
demoList.add("demo","Élément de démonstration");
demoList.selectKey("demo");
```

#### Avec un index donné d'un `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> prend un index d'un `ListItem` comme paramètre pour sélectionner.

```java {3}
List demoList = new List();
demoList.add("demo","Élément de démonstration");
demoList.selectKey(0);
```

### Autres opérations sur les listes {#other-list-operations}

- **Accéder et mettre à jour des éléments :**

   - Pour accéder aux éléments par clé ou index, utilisez <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> ou <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Vous pouvez mettre à jour le texte d'un élément en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> au sein de la classe <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>.

- **Récupération d'informations sur la liste :**
   - Vous pouvez obtenir la taille de la liste en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink>.
   - Pour vérifier si la liste est vide, utilisez la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink>.

### Itération sur les listes {#iterating-over-lists}

Tous les composants de liste implémentent l'interface Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html), offrant un moyen efficace et intuitif d'itérer à travers le contenu d'une liste. Avec cette interface, vous pouvez facilement parcourir chaque `ListItem`, ce qui rend simple l'accès, la modification ou l'exécution d'actions sur chaque élément avec un minimum d'effort. L'interface `Iterable` est un modèle standard du langage Java, garantissant que votre code est familier et maintenable pour tout développeur Java.

Le code ci-dessous montre deux façons faciles d'itérer à travers une liste :

```java
list.forEach(item -> {
   item.setText("Modifié : " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modifié2 : " + item.getText());
}
```

## Propriétés partagées des listes {#shared-list-properties}

### Étiquette {#label}

Tous les composants de liste peuvent se voir attribuer une étiquette, qui est un texte descriptif ou un titre associé au composant. Les étiquettes fournissent une brève explication ou un indice pour aider les utilisateurs à comprendre le but ou la sélection attendue pour cette liste particulière. En plus de leur importance pour l’utilisabilité, les étiquettes de liste jouent également un rôle crucial dans l'accessibilité, permettant aux lecteurs d'écran et aux technologies d'assistance de fournir des informations précises et de faciliter la navigation au clavier.

### Texte d'aide {#helper-text}

Chaque composant de liste peut afficher un texte d'aide sous la liste en utilisant la méthode `setHelperText()`. Ce texte d'aide offre un contexte ou des explications supplémentaires concernant les options disponibles, garantissant que les utilisateurs disposent des informations nécessaires pour faire des sélections éclairées.

### Alignement horizontal {#horizontal-alignment}

Tous les composants de liste implémentent l'interface <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>, vous donnant le contrôle sur l'alignement du texte et du contenu à l'intérieur du composant.

Utilisez la méthode `setHorizontalAlignment()` pour définir l'alignement :

- `HorizontalAlignment.LEFT` (par défaut)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Pour obtenir l'alignement actuel :
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Expanses {#expanses}

Tous les composants de liste dans webforJ implémentent également l'interface <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink>, vous permettant d'ajuster la taille globale et le poids visuel du composant. Cela est utile pour adapter le composant à divers contextes UI, tels que des formulaires, des dialogues, des barres latérales, etc.

Utilisez la méthode `setExpanse()` pour définir le niveau d'expansion. Les options incluent :

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` (par défaut)
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setExpanse(Expanse.LARGE);
```

Vous pouvez récupérer le réglage actuel en utilisant :
```java
Expanse current = listBox.getExpanse();
```

## Sujets {#topics}

<DocCardList className="topics-section" />
