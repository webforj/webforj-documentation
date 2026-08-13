---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 4343173cf0cbef440f24c05cf9ee3fbd
---
Les annotations des ressources fournissent une approche déclarative pour l'intégration de ressources externes et internes telles que JavaScript et CSS dans une application de manière statique. Ces annotations simplifient la gestion des ressources en s'assurant que les dépendances sont chargées au bon moment d'exécution, réduisant ainsi la configuration manuelle et améliorant la maintenabilité.

:::tip Le bundler est celui par défaut pour npm et les frameworks
Les annotations de ressources attachent un script ou une feuille de style que vous avez déjà, sans étape de construction. Pour intégrer des packages npm, un framework de composant tel que React, ou un langage de feuille de style tel que SCSS, utilisez le [bundler frontend](/docs/managing-resources/bundler/overview). C'est le chemin par défaut pour ce travail, et il fait tout ce que font les annotations.
:::

## Importation de fichiers JavaScript {#importing-javascript-files}

L'inclusion déclarative de JavaScript est prise en charge via l'annotation `@JavaScript`, permettant un chargement automatique des dépendances. L'annotation peut être appliquée au niveau du composant ou au niveau de l'application.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

L'annotation accepte un chemin relatif ou complet à charger dans l'application. Cela sera inséré dans le DOM sous la forme d'une balise [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script). De plus, l'annotation prend en charge les propriétés suivantes :

| Propriété    | Type    | Description                                                                                                                                       | Par défaut |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ----------- |
| `top`        | Boolean | Spécifie si le script doit être injecté dans la fenêtre de niveau supérieur                                                                       | `false`     |
| `attributes` | Objet   | Un ensemble d'<JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributs</JavadocLink> à appliquer au script. | `{}`        |

#### Exemple : {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Les fichiers ne sont chargés que lorsque le composant déclarant l'annotation est attaché à un conteneur. Si plusieurs composants chargent le même fichier, le fichier est injecté une seule fois.
:::

## Injection de JavaScript {#injecting-javascript}

Dans certains cas, vous pouvez vouloir injecter du code JavaScript directement dans le DOM au lieu de fournir un chemin JavaScript. L'annotation `InlineJavaScript` vous permet d'injecter du contenu JavaScript.

```java
@InlineJavaScript("alert('Je suis un script en ligne !');")
@JavaScript("context://js/app.js")
```

| Propriété    | Type    | Description                                                               | Par défaut |
| ------------ | ------- | ------------------------------------------------------------------------- | ----------- |
| `top`        | `Boolean` | Spécifie si le script doit être injecté dans la fenêtre de niveau supérieur | `false`     |
| `attributes` | `Objet`  | Attributs à appliquer au script                                         | `{}`        |
| `id`         | `String`  | Un identifiant de ressource unique pour garantir une seule injection    | `""`        |

:::warning
Les scripts peuvent être injectés plusieurs fois à l'aide de `InlineJavaScript`, sauf si un ID spécifique est attribué à l'aide de la propriété `id`.
:::

## Importation de fichiers CSS {#importing-css-files}

L'inclusion déclarative de CSS est prise en charge via l'annotation `@StyleSheet`, permettant un chargement automatique des dépendances. L'annotation peut être appliquée au niveau du composant ou au niveau de l'application.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Propriété    | Type    | Description                                                                   | Par défaut |
| ------------ | ------- | ----------------------------------------------------------------------------- | ----------- |
| `top`        | Boolean | Spécifie si la feuille de style doit être injectée dans la fenêtre de niveau supérieur | `false`     |
| `attributes` | Objet   | Attributs à appliquer à la feuille de style                                   | `{}`        |

#### Exemple : {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Les fichiers ne sont chargés que lorsque le composant déclarant l'annotation est attaché à un conteneur. Chaque fichier est chargé une seule fois.
:::

## Injection de CSS {#injecting-css}

L'annotation `InlineStyleSheet` vous permet d'injecter du contenu CSS directement dans une page web, tant au niveau du composant qu'au niveau de l'application.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Propriété    | Type    | Description                                                                                                               | Par défaut |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------- | ----------- |
| `top`        | Boolean | Spécifie si la feuille de style doit être injectée dans la fenêtre de niveau supérieur de la page.                       | `false`     |
| `attributes` | Objet   | Un ensemble d'[attributs](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) à appliquer à l'élément de style.     | `{}`        |
| `id`         | String  | Un identifiant de ressource unique. Si plusieurs ressources ont le même ID, elles seront regroupées dans un seul élément de style. | `""`        |
| `once`       | Boolean | Détermine si la feuille de style doit être injectée dans la page une seule fois, indépendamment de plusieurs instances de composant. | `true`      |

:::tip
Pour une meilleure coloration syntaxique lors de l'écriture de CSS en ligne pour vos composants, vous pouvez utiliser l'extension webforJ VS Code : [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Ressources dynamiques à l'exécution {#dynamic-assets-at-runtime}

La gestion dynamique des ressources est possible grâce à l'injection programmatique de JavaScript et de CSS à l'exécution. Vous pouvez charger ou injecter des ressources en fonction du contexte d'exécution.

### Chargement et injection de JavaScript {#loading-and-injecting-javascript}

Chargez ou injectez dynamiquement JavaScript à l'exécution en utilisant l'<JavadocLink type="foundation" location="com/webforj/Page" code='true'>API Page</JavadocLink>. Cela vous permet de charger des scripts à partir d'URL ou d'injecter des scripts en ligne directement dans le DOM.

```java
Page page = Page.getCurrent();

// Chargement de fichiers JavaScript
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injection de JavaScript en ligne
page.addInlineJavaScript("console.log('Injection à l'exécution');");
page.addInlineJavaScript("alert('Ce script s'exécute en ligne');");
```

| Paramètre    | Description                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `script`     | L'URL ou le contenu du script en ligne à injecter. Les URL commençant par `context://` se résolvent vers le dossier des ressources racines de l'application. |
| `top`        | Détermine si le script doit être injecté en haut de la page.                                                            |
| `attributes` | Une carte d'attributs à définir pour le script.                                                                          |

### Chargement et injection de CSS {#loading-and-injecting-css}

Chargez ou injectez dynamiquement CSS à l'exécution en utilisant l'<JavadocLink type="foundation" location="com/webforj/Page" code='true'>API Page</JavadocLink>. Cela vous permet de charger des feuilles de style à partir d'URL ou d'injecter des styles en ligne directement dans le DOM.

```java
Page page = Page.getCurrent();

// Chargement de fichiers CSS
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Injection de CSS en ligne
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Paramètre    | Description                                                                                                                 |
| ------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | L'URL ou le contenu de la feuille de style en ligne à injecter. Les URL commençant par `context://` se résolvent vers le dossier des ressources racines de l'application. |
| `top`        | Détermine si la feuille de style doit être injectée en haut de la page.                                                    |
| `attributes` | Une carte d'attributs à définir pour la feuille de style.                                                                    |
