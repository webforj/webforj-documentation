---
title: Navigator
sidebar_position: 75
_i18n_hash: be5e20c3abb0d7b64b7a0eff03f7aded
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Le composant `Navigator` ajoute des contrôles de pagination pour naviguer à travers des ensembles de données. Il peut afficher les boutons de premier, dernier, suivant, et précédent, ainsi que des numéros de page ou un champ de saut rapide, et désactive automatiquement les contrôles lorsqu'ils ne sont pas applicables. Il se lie à une instance de `Paginator` pour gérer la logique de pagination sous-jacente.

<!-- INTRO_END -->

## Liaison aux dépôts {#binding-to-repositories}

Souvent, un composant `Navigator` affiche des informations trouvées dans un `Repository` lié. Cette liaison permet au `Navigator` de paginer automatiquement les données gérées par le dépôt et de rafraîchir d'autres composants liés, tels que des tables, en fonction des données naviguées.

Pour ce faire, il suffit de passer l'objet `Repository` souhaité au constructeur d'un objet `Navigator` applicable :

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Cet exemple crée le `Navigator` et le [`Table`](table/overview) avec la même instance de `Repository`. Cela signifie que lorsque vous naviguez vers une nouvelle page avec le `Navigator`, le [`Table`](table/overview) reconnaît ce changement et se réaffiche.

## Pagination {#pagination}

Le composant `Navigator` est étroitement lié à la classe modèle `Paginator`, calcule les métadonnées de pagination telles que le nombre total de pages, les indices de début/fin des éléments sur la page actuelle, et un tableau de numéros de pages pour la navigation. 

Bien que cela ne soit pas strictement nécessaire, l'utilisation du `Paginator` permet de gérer la logique derrière la navigation. Lors de l'intégration avec un `Paginator`, le navigateur réagit à tout changement au sein du `Paginator`. Les objets `Navigator` ont accès à un `Paginator` intégré via la méthode `getPaginator()`. Il peut également accepter une instance de `Paginator` via la méthode `setPaginator()`, ou l'utilisation de l'un des constructeurs applicables.

Cette section inclut des extraits de code pratiques pour illustrer comment cette intégration fonctionne en pratique.

### Éléments {#items}

Le terme "éléments" désigne les éléments ou entrées de données paginés individuels. Ceux-ci peuvent être des enregistrements, des entrées, ou toute unité discrète au sein d'un ensemble de données. Vous pouvez définir le nombre total d'éléments en utilisant la méthode `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un dépôt associé à l'instance de `Paginator` a le nombre total d'éléments directement géré par le dépôt et ne peut pas être défini directement.
:::

### Pages maximales {#maximum-pages}

La méthode `setMax()` vous permet de définir le nombre maximum de liens de pages à afficher dans la navigation de pagination. Cela est particulièrement utile lorsqu'il s'agit d'un grand nombre de pages, car cela contrôle le nombre de liens de pages visibles par l'utilisateur à tout moment.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Ce programme montre un maximum de cinq pages sur le `Navigator` à la fois en utilisant la méthode `getPaginator()` pour récupérer le `Paginator` associé à l'objet `Navigator`, puis en utilisant la méthode `setMax()` pour spécifier un nombre maximal de pages affichées souhaité.

### Taille de la page {#page-size}

La méthode `setSize()` vous permet de spécifier le nombre d'éléments à afficher sur chaque page de la pagination. Lorsque vous appelez cette méthode et fournissez une nouvelle taille de page, elle ajuste la pagination en conséquence. 

```java
navigator.getPaginator().setSize(pageSize);
```

## Personnalisation des boutons, du texte et des infobulles {#customizing-buttons-text-and-tooltips}

Le composant `Navigator` fournit d'excellentes options de personnalisation pour les boutons, le texte et les infobulles. Pour changer le texte affiché sur le composant `Navigator`, utilisez la méthode `setText()`. Cette méthode prend du texte, ainsi que la `Part` souhaitée du `Navigator`. 

Dans l'exemple suivant, la méthode `setText()` affiche une valeur numérique à l'utilisateur. En cliquant sur les boutons, cela déclenche la méthode `onChange` du `Navigator`, qui est accompagnée d'une valeur `Direction` pour le bouton cliqué.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Boutons et texte du composant {#buttons-and-component-text}

La méthode `setText()` évalue le paramètre de texte comme une expression JavaScript utilisant les paramètres suivants :

- `page` - le numéro de la page actuelle
- `current` - le numéro de la page actuellement sélectionnée
- `x` - un alias pour la page actuelle
- `startIndex` - L'index de départ de la page actuelle.
- `endIndex` - L'index de fin de la page actuelle.
- `totalItems` - Le nombre total d'éléments.
- `startPage` - Le numéro de la première page.
- `endPage` - Le numéro de la dernière page.
- `component` - Le composant client Navigator.

<!-- vale off -->
Par exemple, pour définir le texte du bouton de dernière page dans un `Navigator` avec 10 pages sur "Aller à la page 10", utilisez l'extrait de code suivant : 
<!-- vale on -->

```java
navigator.setText("'Aller à la page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texte d'infobulle {#tooltip-text}

Vous pouvez personnaliser les infobulles pour diverses parties du composant `Navigator` en utilisant la méthode `setTooltipText()`. Les infobulles fournissent des conseils utiles aux utilisateurs lorsqu'ils survolent les éléments de navigation.

:::info
Le texte des infobulles ne s'évalue pas en JavaScript, contrairement au texte utilisé par la méthode `setText()`
:::

<!-- vale off -->
Par exemple, pour définir le texte de l'infobulle du bouton de dernière page dans un `Navigator` sur "Aller à la dernière page", utilisez l'extrait de code suivant :
<!-- vale on -->

```java
navigator.setTooltipText("Aller à la dernière page", Navigator.Part.LAST_BUTTON);
```

## Agencements {#layouts}

Diverses options de mise en page existent pour le composant `Navigator` afin de fournir de la flexibilité dans l'affichage des contrôles de pagination. Pour accéder à ces mises en page, utilisez les valeurs de l'énumération `Navigator.Layout`. Les options sont les suivantes :

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Mise en page None {#1-none-layout}

La mise en page `NONE` ne rend aucun texte dans le `Navigator`, affichant uniquement les boutons de navigation sans affichage textuel par défaut. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Mise en page Numérotée {#2-numbered-layout}

La mise en page numérotée affiche des puces numérotées correspondant à chaque page dans la zone d'affichage du `Navigator`. Utiliser cette mise en page est idéal pour les scénarios où les utilisateurs préfèrent naviguer directement vers des pages spécifiques. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Mise en page Aperçu {#3-preview-layout}

La mise en page d'aperçu montre le numéro de la page actuelle et le nombre total de pages, et convient aux interfaces de pagination compactes avec un espace limité.

:::info
L'aperçu est la mise en page par défaut du `Navigator`.
:::

Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Mise en page Saut rapide {#4-quick-jump-layout}

La mise en page de saut rapide fournit un [NumberField](./fields/number-field.md) permettant aux utilisateurs de saisir un numéro de page pour une navigation rapide. Cela est utile lorsque les utilisateurs ont besoin de naviguer rapidement vers une page spécifique, surtout pour de grands ensembles de données. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Style {#styling}

<TableBuilder name="Navigator" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Navigator`, considérez les meilleures pratiques suivantes : 

- **Comprendre l'ensemble de données** : Avant d'intégrer un composant `Navigator` dans votre application, comprenez bien les exigences de navigation des données de vos utilisateurs. Envisagez des facteurs tels que la taille de l'ensemble de données, les interactions typiques des utilisateurs et les modèles de navigation préférés.

- **Choisir la mise en page appropriée** : Sélectionnez une mise en page pour le composant `Navigator` qui correspond aux objectifs d'expérience utilisateur et à l'espace disponible à l'écran.

- **Personnaliser le texte et les infobulles** : Personnalisez le texte et les infobulles du composant `Navigator` pour correspondre à la langue et à la terminologie utilisées dans votre application. Fournissez des étiquettes descriptives et des conseils utiles pour aider les utilisateurs à naviguer efficacement dans l'ensemble de données.
