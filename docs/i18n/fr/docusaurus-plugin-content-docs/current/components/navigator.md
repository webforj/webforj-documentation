---
title: Navigator
sidebar_position: 75
_i18n_hash: 920c1d604673e69a32f58161e3fd4e14
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Le composant `Navigator` est un composant de pagination personnalisable conçu pour naviguer à travers des ensembles de données, prenant en charge plusieurs mises en page. Vous pouvez le configurer pour afficher divers contrôles de navigation tels que les boutons premier, dernier, suivant et précédent, ainsi que des numéros de page ou un champ de saut rapide selon les paramètres de mise en page.

Il prend en charge la désactivation automatique des boutons de navigation en fonction de la page actuelle et du nombre total d'éléments, et propose des options de personnalisation pour le texte et les infobulles de différentes parties du navigateur. De plus, vous pouvez le lier à une instance de `Paginator` pour gérer la logique de pagination de l'ensemble de données et refléter les changements dans les contrôles de navigation.

## Liaison aux dépôts {#binding-to-repositories}

Souvent, un composant `Navigator` affiche des informations trouvées dans un `Repository` lié. Cette liaison permet au `Navigator` de paginer automatiquement les données gérées par le dépôt et de rafraîchir d'autres composants liés, tels que des tableaux, en fonction des données naviguées.

Pour ce faire, il suffit de passer l'objet `Repository` souhaité au constructeur d'un objet `Navigator` applicable :

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Cet exemple crée le `Navigator` et la [`Table`](table/overview) avec la même instance de `Repository`. Cela signifie que lorsque vous naviguez vers une nouvelle page avec le `Navigator`, la [`Table`](table/overview) reconnaît ce changement et se re-render.

## Pagination {#pagination}

Le composant `Navigator` est étroitement lié à la classe modèle `Paginator`, qui calcule les métadonnées de pagination telles que le nombre total de pages, les indices de début/fin des éléments de la page actuelle et un tableau de numéros de page pour la navigation.

Bien que cela ne soit pas absolument nécessaire, l'utilisation du `Paginator` permet de gérer la logique derrière la navigation. Lorsqu'il est intégré à un `Paginator`, le navigateur réagit à tout changement au sein du `Paginator`. Les objets `Navigator` ont accès à un `Paginator` intégré grâce à la méthode `getPaginator()`. Il peut également accepter une instance de `Paginator` via la méthode `setPaginator()`, ou l'utilisation de l'un des constructeurs applicables.

Cette section comprend des extraits de code pratiques pour illustrer comment cette intégration fonctionne en pratique.

### Éléments {#items}

Le terme "éléments" désigne les éléments individuels paginés ou les entrées de données. Ce peuvent être des enregistrements, des entrées ou toute unité discrète au sein d'un ensemble de données. Vous pouvez définir le nombre total d'éléments en utilisant la méthode `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un dépôt associé à l'instance de `Paginator` a le nombre total d'éléments gérés directement par le dépôt et ne peut pas être défini directement.
:::

### Pages maximales {#maximum-pages}

La méthode `setMax()` vous permet de définir le nombre maximum de liens de page à afficher dans la navigation de pagination. Cela est particulièrement utile lorsque vous traitez un grand nombre de pages, car cela contrôle le nombre de liens de page visibles pour l'utilisateur à tout moment.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Ce programme montre un maximum de cinq pages sur le `Navigator` à un moment donné en utilisant la méthode `getPaginator()` pour récupérer le `Paginator` associé à l'objet `Navigator`, puis en utilisant la méthode `setMax()` pour spécifier un nombre de pages maximales affichées désiré.

### Taille de page {#page-size}

La méthode `setSize()` vous permet de spécifier le nombre d'éléments à afficher sur chaque page de la pagination. Lorsque vous appelez cette méthode et fournissez une nouvelle taille de page, elle ajuste la pagination en conséquence.

```java
navigator.getPaginator().setSize(pageSize);
```

## Personnalisation des boutons, du texte et des infobulles {#customizing-buttons-text-and-tooltips}

Le composant `Navigator` propose d'importantes options de personnalisation pour les boutons, le texte et les infobulles. Pour changer le texte affiché sur le composant `Navigator`, utilisez la méthode `setText()`. Cette méthode prend du texte, ainsi que la `Part` désirée du `Navigator`.

Dans l'exemple suivant, la méthode `setText()` affiche une valeur numérique à l'utilisateur. Cliquer sur les boutons déclenche la méthode `onChange` du `Navigator`, qui s'accompagne d'une valeur `Direction` liée au bouton cliqué.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Boutons et texte du composant {#buttons-and-component-text}

La méthode `setText()` évalue le paramètre de texte comme une expression JavaScript en utilisant les paramètres suivants :

- `page` - le numéro de la page actuelle
- `current` - le numéro de la page actuellement sélectionnée
- `x` - un alias pour la page actuelle
- `startIndex` - L'indice de départ de la page actuelle.
- `endIndex` - L'indice de fin de la page actuelle.
- `totalItems` - Le nombre total d'éléments.
- `startPage` - Le numéro de la page de début.
- `endPage` - Le numéro de la page de fin.
- `component` - Le composant client du Navigator.

<!-- vale off -->
Par exemple, pour définir le texte du bouton de la dernière page dans un `Navigator` avec 10 pages à "Aller à la page 10", utilisez l'extrait de code suivant : 
<!-- vale on -->

```java
navigator.setText("'Aller à la page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texte des infobulles {#tooltip-text}

Vous pouvez personnaliser les infobulles pour différentes parties du composant `Navigator` à l'aide de la méthode `setTooltipText()`. Les infobulles fournissent des conseils utiles aux utilisateurs lorsqu'ils survolent des éléments de navigation.

:::info
Le texte des infobulles n'est pas évalué en JavaScript, contrairement au texte utilisé par la méthode `setText()`.
:::

<!-- vale off -->
Par exemple, pour définir le texte de l'infobulle du bouton de la dernière page dans un `Navigator` à "Aller à la dernière page", utilisez l'extrait de code suivant :
<!-- vale on -->

```java
navigator.setTooltipText("Aller à la dernière page", Navigator.Part.LAST_BUTTON);
```

## Mises en page {#layouts}

Différentes options de mise en page existent pour le composant `Navigator` afin de fournir flexibilité dans l'affichage des contrôles de pagination. Pour accéder à ces mises en page, utilisez les valeurs de l'énumération `Navigator.Layout`. Les options sont les suivantes :

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Mise en page Aucune {#1-none-layout}

La mise en page `NONE` ne rend aucun texte au sein du `Navigator`, affichant uniquement les boutons de navigation sans affichage textuel par défaut. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Mise en page numérotée {#2-numbered-layout}

La mise en page numérotée affiche des puces numérotées correspondant à chaque page au sein de la zone d'affichage du `Navigator`. Utiliser cette mise en page est idéal dans les scénarios où les utilisateurs préfèrent une navigation directe vers des pages spécifiques. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Mise en page Aperçu {#3-preview-layout}

La mise en page Aperçu affiche le numéro de la page actuelle et le nombre total de pages, et est adaptée aux interfaces de pagination compactes avec un espace limité.

:::info
Aperçu est la mise en page par défaut du `Navigator`.
:::

Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Mise en page Saut rapide {#4-quick-jump-layout}

La mise en page de saut rapide fournit un [NumberField](./fields/number-field.md) pour que les utilisateurs saisissent un numéro de page pour une navigation rapide. Cela est utile lorsque les utilisateurs doivent naviguer rapidement vers une page spécifique, en particulier pour de grands ensembles de données. Pour activer cette mise en page, utilisez :

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Style {#styling}

<TableBuilder name="Navigator" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Navigator`, considérez les meilleures pratiques suivantes : 

- **Comprendre l'ensemble de données** : Avant d'intégrer un composant `Navigator` dans votre application, comprenez bien les exigences de navigation des données de vos utilisateurs. Considérez des facteurs tels que la taille de l'ensemble de données, les interactions typiques des utilisateurs et les schémas de navigation préférés.

- **Choisir une mise en page appropriée** : Sélectionnez une mise en page pour le composant `Navigator` qui s'aligne avec les objectifs de l'expérience utilisateur et l'espace disponible à l'écran.

- **Personnaliser le texte et les infobulles** : Personnalisez le texte et les infobulles du composant `Navigator` pour correspondre à la langue et à la terminologie utilisées dans votre application. Fournissez des étiquettes descriptives et des conseils utiles pour aider les utilisateurs à naviguer efficacement dans l'ensemble de données.
