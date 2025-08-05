---
title: Navigator
sidebar_position: 75
_i18n_hash: 7c09a72456eb84a8227da3ff263b6c69
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Le composant `Navigator` est un composant de pagination personnalisable conçu pour naviguer à travers des ensembles de données, prenant en charge plusieurs mises en page. Vous pouvez le configurer pour afficher divers contrôles de navigation tels que les boutons premier, dernier, suivant et précédent, ainsi que les numéros de page ou un champ de saut rapide en fonction des paramètres de mise en page.

Il prend en charge la désactivation automatique des boutons de navigation en fonction de la page actuelle et du nombre total d'éléments, et offre des options de personnalisation pour le texte et les infobulles pour différentes parties du navigateur. De plus, vous pouvez le lier à une instance de `Paginator` pour gérer la logique de pagination de l'ensemble de données et refléter les changements dans les contrôles de navigation.

## Liaison aux dépôts {#binding-to-repositories}

Souvent, un composant `Navigator` affiche des informations trouvées dans un `Repository` lié. Cette liaison permet au `Navigator` de paginer automatiquement les données gérées par le dépôt et de rafraîchir d'autres composants pouvant être liés, tels que des tables, en fonction des données naviguées.

Pour ce faire, il suffit de passer l'objet `Repository` souhaité au constructeur d'un objet `Navigator` applicable :

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Cet exemple crée le `Navigator` et [`Table`](table/overview) avec la même instance de `Repository`. Cela signifie que lorsqu'il navigue vers une nouvelle page avec le `Navigator`, la [`Table`](table/overview) reconnaît ce changement et se rend.

## Pagination {#pagination}

Le composant `Navigator` est étroitement lié à la classe modèle `Paginator`, calcule les métadonnées de pagination telles que le nombre total de pages, les indices de début/fin des éléments sur la page actuelle et un tableau de numéros de page pour la navigation.

Bien que non nécessairement, l'utilisation de `Paginator` permet de gérer la logique derrière la navigation. Lors de l'intégration avec un `Paginator`, le navigateur répond à tout changement au sein du `Paginator`. Les objets `Navigator` ont accès à un `Paginator` intégré grâce à l'utilisation de la méthode `getPaginator()`. Il peut également accepter une instance de `Paginator` via la méthode `setPaginator()`, ou l'utilisation de l'un des constructeurs applicables.

Cette section inclut des extraits de code pratiques pour illustrer comment cette intégration fonctionne en pratique.

### Éléments {#items}

Le terme "éléments" désigne les éléments ou entrées de données paginés individuels. Il peut s'agir d'enregistrements, d'entrées ou de toute unité discrète au sein d'un ensemble de données. Vous pouvez définir le nombre total d'éléments à l'aide de la méthode `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un dépôt associé à l'instance de `Paginator` a le nombre total d'éléments directement gérés par le dépôt et ne peut pas être directement fixé.
:::

### Pages maximales {#maximum-pages}

La méthode `setMax()` vous permet de définir le nombre maximum de liens de pages à afficher dans la navigation de pagination. Cela est particulièrement utile lorsqu'il y a un grand nombre de pages, car cela contrôle le nombre de liens de pages visibles pour l'utilisateur à tout moment.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Ce programme montre un maximum de cinq pages sur le `Navigator` à un moment donné en utilisant la méthode `getPaginator()` pour récupérer le `Paginator` associé à l'objet `Navigator`, puis en utilisant la méthode `setMax()` pour spécifier un nombre maximum de pages affichées.

### Taille de page {#page-size}

La méthode `setSize()` vous permet de spécifier le nombre d'éléments à afficher sur chaque page de la pagination. Lorsque vous appelez cette méthode et fournissez une nouvelle taille de page, cela ajuste la pagination en conséquence.

```java
navigator.getPaginator().setSize(pageSize);
```

## Personnalisation des boutons, du texte et des infobulles {#customizing-buttons-text-and-tooltips}

Le composant `Navigator` offre d'amples options de personnalisation pour les boutons, le texte et les infobulles. Pour modifier le texte affiché sur le composant `Navigator`, utilisez la méthode `setText()`. Cette méthode prend du texte, ainsi que la `Part` souhaitée du `Navigator`.

Dans l'exemple suivant, la méthode `setText()` affiche une valeur numérique pour l'utilisateur. En cliquant sur les boutons, la méthode `onChange` du `Navigator` est déclenchée, qui possède une valeur `Direction` pour le bouton cliqué.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Boutons et texte de composant {#buttons-and-component-text}

La méthode `setText()` évalue le paramètre de texte comme une expression JavaScript en utilisant les paramètres suivants :

- `page` - le numéro de la page actuelle
- `current` - le numéro de la page actuellement sélectionnée
- `x` - un alias pour la page actuelle
- `startIndex` - L'indice de début de la page actuelle.
- `endIndex` - L'indice de fin de la page actuelle.
- `totalItems` - le nombre total d'éléments.
- `startPage` - le numéro de la page de départ.
- `endPage` - le numéro de la page de fin.
- `component` - le composant client Navigator.

<!-- vale off -->
Par exemple, pour définir le texte du bouton de dernière page dans un `Navigator` avec 10 pages sur "Aller à la page 10", utilisez l'extrait de code suivant : 
<!-- vale on -->

```java
navigator.setText("'Aller à la page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texte de l'infobulle {#tooltip-text}

Vous pouvez personnaliser les infobulles pour différentes parties du composant `Navigator` à l'aide de la méthode `setTooltipText()`. Les infobulles fournissent des indices utiles aux utilisateurs lorsqu'ils survolent des éléments de navigation.

:::info
Le texte de l'infobulle n'est pas évalué en JavaScript, contrairement au texte utilisé par la méthode `setText()`.
:::

<!-- vale off -->
Par exemple, pour définir le texte de l'infobulle du bouton de dernière page dans un `Navigator` sur "Aller à la dernière page", utilisez l'extrait de code suivant :
<!-- vale on -->

```java
navigator.setTooltipText("Aller à la dernière page", Navigator.Part.LAST_BUTTON);
```

## Mises en page {#layouts}

Diverses options de mise en page existent pour le composant `Navigator` pour fournir une flexibilité dans l'affichage des contrôles de pagination. Pour accéder à ces mises en page, utilisez les valeurs de l'énumération `Navigator.Layout`. Les options sont les suivantes :

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Aucun agencement {#1-none-layout}

L'agencement `NONE` ne rend aucun texte à l'intérieur du `Navigator`, affichant uniquement les boutons de navigation sans affichage textuel par défaut. Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Mise en page numérotée {#2-numbered-layout}

La mise en page numérotée affiche des puces numérotées correspondant à chaque page dans la zone d'affichage du `Navigator`. Utiliser cet agencement est idéal pour les scénarios où les utilisateurs préfèrent naviguer directement vers des pages spécifiques. Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Mise en page prévisualisation {#3-preview-layout}

La mise en page de prévisualisation montre le numéro de la page actuelle et le nombre total de pages, et est adaptée aux interfaces de pagination compactes avec un espace limité.

:::info
La prévisualisation est la mise en page par défaut du `Navigator`.
:::

Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Mise en page de saut rapide {#4-quick-jump-layout}

La mise en page de saut rapide fournit un [NumberField](./fields/number-field.md) permettant aux utilisateurs d'entrer un numéro de page pour une navigation rapide. Cela est utile lorsque les utilisateurs ont besoin de naviguer rapidement vers une page spécifique, en particulier pour de grands ensembles de données. Pour activer cet agencement, utilisez :

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Stylisation {#styling}

<TableBuilder name="Navigator" />

## Meilleures pratiques {#best-practices}

Pour assurer une expérience utilisateur optimale lors de l'utilisation du composant `Navigator`, considérez les meilleures pratiques suivantes :

- **Comprendre l'ensemble de données** : Avant d'intégrer un composant `Navigator` dans votre application, comprenez bien les besoins de navigation des utilisateurs en matière de données. Tenez compte de facteurs tels que la taille de l'ensemble de données, les interactions typiques des utilisateurs et les modèles de navigation préférés.

- **Choisissez une mise en page appropriée** : Sélectionnez un agencement pour le composant `Navigator` qui s'aligne avec les objectifs d'expérience utilisateur et l'espace d'écran disponible.

- **Personnalisez le texte et les infobulles** : Personnalisez le texte et les infobulles du composant `Navigator` pour correspondre à la langue et à la terminologie utilisées dans votre application. Fournissez des étiquettes descriptives et des conseils utiles pour aider les utilisateurs à naviguer efficacement dans l'ensemble de données.
