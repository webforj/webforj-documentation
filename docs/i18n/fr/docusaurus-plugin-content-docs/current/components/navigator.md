---
title: Navigator
sidebar_position: 75
_i18n_hash: db351d8f9fdf344a571d374e8d373f22
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Le composant `Navigator` ajoute des contrôles de pagination pour naviguer à travers les ensembles de données. Il peut afficher des boutons pour aller au premier, dernier, suivant et précédent, ainsi que des numéros de page ou un champ de saut rapide, et désactive automatiquement les contrôles lorsqu'ils ne sont pas applicables. Il est lié à une instance de `Paginator` pour gérer la logique de pagination sous-jacente.

<!-- INTRO_END -->

## Liaison aux dépôts {#binding-to-repositories}

Souvent, un composant `Navigator` affiche des informations trouvées dans un `Repository` lié. Cette liaison permet au `Navigator` de paginer automatiquement les données gérées par le dépôt et de rafraîchir d'autres composants liés, tels que des tables, en fonction des données naviguées.

Pour ce faire, il suffit de passer l'objet `Repository` désiré au constructeur d'un objet `Navigator` applicable :

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Cet exemple crée le `Navigator` et le [`Table`](table/overview) avec la même instance de `Repository`. Cela signifie que lorsqu'on navigue vers une nouvelle page avec le `Navigator`, le [`Table`](table/overview) reconnaît ce changement et se redessine.

## Pagination {#pagination}

Le composant `Navigator` est étroitement lié à la classe modèle `Paginator`, calculant des métadonnées de pagination telles que le nombre total de pages, les indices de départ/fin des éléments de la page actuelle, et un tableau de numéros de page pour la navigation.

Bien que cela ne soit pas nécessaire, l'utilisation du `Paginator` permet de gérer la logique derrière la navigation. En s'intégrant à un `Paginator`, le navigateur réagit à tout changement au sein du `Paginator`. Les objets `Navigator` ont accès à un `Paginator` intégré via la méthode `getPaginator()`. Il peut également accepter une instance de `Paginator` via la méthode `setPaginator()`, ou l'utilisation de l'un des constructeurs applicables.

Cette section inclut des exemples pratiques de code pour illustrer comment cette intégration fonctionne en pratique.

### Éléments {#items}

Le terme "éléments" désigne les éléments individuels paginés ou les entrées de données. Cela pourrait être des enregistrements, des entrées, ou toute unité discrète au sein d'un ensemble de données. Vous pouvez définir le nombre total d'éléments en utilisant la méthode `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un dépôt associé à l'instance de `Paginator` possède le nombre total d'éléments directement gérés par le dépôt et ne peut pas être directement défini.
:::

### Pages maximales {#maximum-pages}

La méthode `setMax()` vous permet de définir le nombre maximal de liens de pages à afficher dans la navigation de pagination. Cela est particulièrement utile lors du traitement d'un grand nombre de pages, car cela contrôle le nombre de liens de pages visibles pour l'utilisateur à un moment donné.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Ce programme affiche un maximum de cinq pages sur le `Navigator` à la fois en utilisant la méthode `getPaginator()` pour récupérer le `Paginator` associé à l'objet `Navigator`, puis en utilisant la méthode `setMax()` pour spécifier un nombre désiré de pages maximales affichées.

### Taille de la page {#page-size}

La méthode `setSize()` vous permet de spécifier le nombre d'éléments à afficher sur chaque page de la pagination. Lorsque vous appelez cette méthode et fournissez une nouvelle taille de page, elle ajuste la pagination en conséquence.

```java
navigator.getPaginator().setSize(pageSize);
```

## Personnalisation des boutons, du texte et des infobulles {#customizing-buttons-text-and-tooltips}

Le composant `Navigator` offre de nombreuses options de personnalisation pour les boutons, le texte et les infobulles. Pour changer le texte affiché sur le composant `Navigator`, utilisez la méthode `setText()`. Cette méthode prend du texte, ainsi que la `Part` désirée du `Navigator`.

Dans l'exemple suivant, la méthode `setText()` affiche une valeur numérique à l'utilisateur. Cliquer sur les boutons déclenche la méthode `onChange` du `Navigator`, qui est accompagnée d'une valeur `Direction` du bouton cliqué.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Boutons et texte des composants {#buttons-and-component-text}

La méthode `setText()` évalue le paramètre de texte en tant qu'expression JavaScript utilisant les paramètres suivants :

- `page` - le numéro de la page actuelle
- `current` - le numéro de la page actuellement sélectionnée
- `x` - un alias pour la page actuelle
- `startIndex` - L'indice de départ de la page actuelle.
- `endIndex` - L'indice de fin de la page actuelle.
- `totalItems` - Le nombre total d'éléments.
- `startPage` - Le numéro de la page de départ.
- `endPage` - Le numéro de la page de fin.
- `component` - Le composant client Navigator.

<!-- vale off -->
Par exemple, pour définir le texte du bouton de la dernière page dans un `Navigator` avec 10 pages à "Aller à la page 10", utilisez le code suivant : 
<!-- vale on -->

```java
navigator.setText("'Aller à la page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texte de l'infobulle {#tooltip-text}

Vous pouvez personnaliser les infobulles pour différentes parties du composant `Navigator` en utilisant la méthode `setTooltipText()`. Les infobulles fournissent des conseils utiles aux utilisateurs lorsqu'ils survolent les éléments de navigation.

:::info
Le texte de l'infobulle n'est pas évalué en JavaScript, contrairement au texte utilisé par la méthode `setText()`
:::

<!-- vale off -->
Par exemple, pour définir le texte de l'infobulle du bouton de la dernière page dans un `Navigator` à "Aller à la dernière page", utilisez le code suivant :
<!-- vale on -->

```java
navigator.setTooltipText("Aller à la dernière page", Navigator.Part.LAST_BUTTON);
```

## Dispositions {#layouts}

Diverses options de mise en page existent pour le composant `Navigator` afin de fournir une flexibilité dans l'affichage des contrôles de pagination. Pour accéder à ces mises en page, utilisez les valeurs de l'énumération `Navigator.Layout`. Les options sont les suivantes :

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Mise en page inexistante {#1-none-layout}

La mise en page `NONE` ne rend aucun texte au sein du `Navigator`, affichant uniquement les boutons de navigation sans affichage textuel par défaut. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Mise en page numérotée {#2-numbered-layout}

La mise en page numérotée affiche des puces numérotées correspondant à chaque page dans la zone d'affichage du `Navigator`. Utiliser cette mise en page est idéal pour les scénarios où les utilisateurs préfèrent naviguer directement vers des pages spécifiques. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Mise en page d'aperçu {#3-preview-layout}

La mise en page d'aperçu montre le numéro de la page actuelle et le nombre total de pages, et est adaptée aux interfaces de pagination compactes avec un espace limité.

:::info
L'aperçu est la mise en page par défaut du `Navigator`.
:::

Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Mise en page de saut rapide {#4-quick-jump-layout}

La mise en page de saut rapide fournit un [NumberField](./fields/number-field.md) permettant aux utilisateurs de saisir un numéro de page pour une navigation rapide. Cela est utile lorsque les utilisateurs ont besoin de naviguer rapidement vers une page spécifique, en particulier pour de grands ensembles de données. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Style {#styling}

<TableBuilder name="Navigator" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Navigator`, considérez les meilleures pratiques suivantes :

- **Comprendre l'ensemble de données** : Avant d'intégrer un composant `Navigator` dans votre application, comprenez bien les besoins de navigation des données de vos utilisateurs. Considérez des facteurs tels que la taille de l'ensemble de données, les interactions typiques des utilisateurs, et les schémas de navigation préférés.

- **Choisir une mise en page appropriée** : Sélectionnez une mise en page pour le composant `Navigator` qui soit en adéquation avec les objectifs d'expérience utilisateur et l'espace d'écran disponible.

- **Personnaliser le texte et les infobulles** : Personnalisez le texte et les infobulles du composant `Navigator` pour correspondre à la langue et à la terminologie utilisées dans votre application. Fournissez des étiquettes descriptives et des conseils utiles pour aider les utilisateurs à naviguer efficacement dans l'ensemble de données.
