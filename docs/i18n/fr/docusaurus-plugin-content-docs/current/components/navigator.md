---
title: Navigator
sidebar_position: 75
description: >-
  Add pagination controls with the Navigator component, binding to a Paginator
  or Repository to drive page size, navigation, and labels.
_i18n_hash: 1223e167b76000411cd73c4bbbbda3d5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Le composant `Navigator` ajoute des contrôles de pagination pour naviguer à travers des ensembles de données. Il peut afficher des boutons pour aller au début, à la fin, suivant et précédent, ainsi que des numéros de page ou un champ de saut rapide, et désactive automatiquement les contrôles lorsque cela n'est pas applicable. Il se lie à une instance de `Paginator` pour gérer la logique de pagination sous-jacente.

<!-- INTRO_END -->

## Liaison aux dépôts {#binding-to-repositories}

Souvent, un composant `Navigator` affiche des informations trouvées dans un `Repository` lié. Cette liaison permet au `Navigator` de paginer automatiquement les données gérées par le dépôt et de rafraîchir d'autres composants liés, tels que des tableaux, en fonction des données naviguées.

Pour ce faire, il suffit de passer l'objet `Repository` souhaité au constructeur d'un objet `Navigator` applicable :

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Cet exemple crée le `Navigator` et le [`Table`](table/overview) avec la même instance de `Repository`. Cela signifie que lorsque vous naviguez vers une nouvelle page avec le `Navigator`, le [`Table`](table/overview) reconnaît ce changement et se redessine.

## Pagination {#pagination}

Le composant `Navigator` est étroitement lié à la classe modèle `Paginator`, calcule les métadonnées de pagination telles que le nombre total de pages, les indices de début/fin des éléments sur la page actuelle, ainsi qu'un tableau de numéros de pages pour la navigation.

Bien que ce ne soit pas absolument nécessaire, utiliser le `Paginator` permet de gérer la logique derrière la navigation. Lorsqu'il est intégré avec un `Paginator`, le `navigator` réagit à tous les changements au sein du `Paginator`. Les objets `Navigator` ont accès à un `Paginator` intégré grâce à l'utilisation de la méthode `getPaginator()`. Il peut également accepter une instance de `Paginator` via la méthode `setPaginator()` ou l'utilisation d'un des constructeurs applicables.

Cette section inclut des extraits de code pratiques pour illustrer comment cette intégration fonctionne en pratique.

### Éléments {#items}

Le terme "éléments" désigne les éléments ou entrées de données individuels paginés. Il peut s'agir d'enregistrements, d'entrées ou de toute unité discrète au sein d'un ensemble de données. Vous pouvez définir le nombre total d'éléments à l'aide de la méthode `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un dépôt associé à l'instance de `Paginator` a le nombre total d'éléments directement géré par le dépôt et ne peut pas être défini directement.
:::

### Pages maximales {#maximum-pages}

La méthode `setMax()` vous permet de définir le nombre maximum de liens de pages à afficher dans la navigation de pagination. Cela est particulièrement utile lorsqu'il y a un grand nombre de pages, car cela contrôle le nombre de liens de pages visibles pour l'utilisateur à tout moment.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Ce programme montre un maximum de cinq pages sur le `Navigator` à la fois en utilisant la méthode `getPaginator()` pour récupérer le `Paginator` associé à l'objet `Navigator`, puis en utilisant la méthode `setMax()` pour spécifier un nombre souhaité de pages maximales affichées.

### Taille de la page {#page-size}

La méthode `setSize()` vous permet de spécifier le nombre d'éléments à afficher sur chaque page de la pagination. Lorsque vous appelez cette méthode et fournissez une nouvelle taille de page, elle ajuste la pagination en conséquence.

```java
navigator.getPaginator().setSize(pageSize);
```

## Personnalisation des boutons, du texte et des infobulles {#customizing-buttons-text-and-tooltips}

Le composant `Navigator` offre de nombreuses options de personnalisation pour les boutons, le texte et les infobulles. Pour changer le texte affiché sur le composant `Navigator`, utilisez la méthode `setText()`. Cette méthode prend du texte, ainsi que la `Part` souhaitée du `Navigator`.

Dans l'exemple suivant, la méthode `setText()` affiche une valeur numérique à l'utilisateur. Cliquer sur les boutons déclenche la méthode `onChange` du `Navigator`, qui vient avec une valeur de `Direction` du bouton cliqué.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Boutons et texte du composant {#buttons-and-component-text}

La méthode `setText()` évalue le paramètre texte comme une expression JavaScript en utilisant les paramètres suivants :

- `page` - le numéro de la page actuelle
- `current` - le numéro de la page actuellement sélectionnée
- `x` - un alias pour la page actuelle
- `startIndex` - L'indice de début de la page actuelle.
- `endIndex` - L'indice de fin de la page actuelle.
- `totalItems` - Le nombre total d'éléments.
- `startPage` - Le numéro de la première page.
- `endPage` - Le numéro de la dernière page.
- `component` - Le composant client Navigator.

<!-- vale off -->
Par exemple, pour définir le texte du bouton de la dernière page dans un `Navigator` avec 10 pages sur "Aller à la page 10", utilisez l'extrait de code suivant :
<!-- vale on -->

```java
navigator.setText("'Aller à la page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texte de l'infobulle {#tooltip-text}

Vous pouvez personnaliser les infobulles pour diverses parties du composant `Navigator` à l'aide de la méthode `setTooltipText()`. Les infobulles fournissent des conseils utiles aux utilisateurs lorsqu'ils survolent des éléments de navigation.

:::info
Le texte de l'infobulle n'évalue pas en JavaScript, contrairement au texte utilisé par la méthode `setText()`
:::

<!-- vale off -->
Par exemple, pour définir le texte de l'infobulle du bouton de la dernière page dans un `Navigator` sur "Aller à la dernière page", utilisez l'extrait de code suivant :
<!-- vale on -->

```java
navigator.setTooltipText("Aller à la dernière page", Navigator.Part.LAST_BUTTON);
```

## Agencements {#layouts}

Diverses options d'agencement existent pour le composant `Navigator` afin de fournir de la flexibilité dans l'affichage des contrôles de pagination. Pour accéder à ces agencements, utilisez les valeurs de l'énumération `Navigator.Layout`. Les options sont les suivantes :

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Agencement sans {#1-none-layout}

L'agencement `NONE` n'affiche aucun texte dans le `Navigator`, affichant uniquement les boutons de navigation sans affichage textuel par défaut. Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Agencement numéroté {#2-numbered-layout}

L'agencement numéroté affiche des puces numérotées correspondant à chaque page dans la zone d'affichage du `Navigator`. Utiliser cet agencement est idéal pour les scénarios où les utilisateurs préfèrent naviguer directement vers des pages spécifiques. Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Agencement d'aperçu {#3-preview-layout}

L'agencement d'aperçu montre le numéro de la page actuelle et le nombre total de pages, et est adapté aux interfaces de pagination compactes avec peu d'espace.

:::info
L'aperçu est l'agencement par défaut du `Navigator`.
:::

Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Agencement de saut rapide {#4-quick-jump-layout}

L'agencement de saut rapide fournit un [NumberField](./fields/number-field.md) permettant aux utilisateurs d'entrer un numéro de page pour une navigation rapide. Cela est utile lorsque les utilisateurs ont besoin de naviguer rapidement vers une page spécifique, en particulier pour de grands ensembles de données. Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Style {#styling}

<TableBuilder name="Navigator" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Navigator`, prenez en compte les meilleures pratiques suivantes :

- **Comprendre l'ensemble de données** : Avant d'intégrer un composant `Navigator` dans votre application, comprenez bien les exigences de navigation des données de vos utilisateurs. Considérez des facteurs tels que la taille de l'ensemble de données, les interactions typiques des utilisateurs et les motifs de navigation préférés.

- **Choisir l'agencement approprié** : Sélectionnez un agencement pour le composant `Navigator` qui s'aligne avec les objectifs d'expérience utilisateur et l'espace d'écran disponible.

- **Personnaliser le texte et les infobulles** : Personnalisez le texte et les infobulles du composant `Navigator` pour correspondre à la langue et à la terminologie utilisées dans votre application. Fournissez des étiquettes descriptives et des conseils utiles pour aider les utilisateurs à naviguer efficacement dans l'ensemble de données.
