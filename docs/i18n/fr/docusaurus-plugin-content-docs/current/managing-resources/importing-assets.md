---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 356ee4ac3242f607ead40f76311ead79
---
Les annotations des actifs fournissent une approche déclarative pour intégrer des ressources externes et inline telles que JavaScript et CSS dans une application de manière statique. Ces annotations simplifient la gestion des ressources en garantissant que les dépendances sont chargées au phase d'exécution appropriée, réduisant la configuration manuelle et améliorant la maintenabilité.

## Importation de fichiers JavaScript {#importing-javascript-files}

L'inclusion déclarative de JavaScript est supportée par l'annotation `@JavaScript`, permettant le chargement automatique des dépendances. L'annotation peut être appliquée tant au niveau des composants qu'au niveau de l'application.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

L'annotation accepte un chemin relatif ou complet à charger dans l'application. Cela sera inséré dans le DOM comme une balise [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script). De plus, l'annotation prend en charge les propriétés suivantes :

| Propriété     | Type    | Description                                                                                                                                       | Par défaut |
| ------------- | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | ---------- |
| `top`         | Boolean | Spécifie si le script doit être injecté dans la fenêtre de premier niveau                                                                         | `false`    |
| `attributes`  | Object  | Un ensemble de <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributs</JavadocLink> à appliquer au script. | `{}`       |

#### Exemple : {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Les fichiers sont chargés uniquement lorsque le composant déclarant l'annotation est attaché à un conteneur. Si plusieurs composants chargent le même fichier, le fichier n'est injecté qu'une seule fois.
:::

## Injection de JavaScript {#injecting-javascript}

Dans certains cas, vous pouvez vouloir injecter du code JavaScript directement dans le DOM au lieu de fournir un chemin JavaScript. L'annotation `InlineJavaScript` vous permet d'injecter du contenu JavaScript.

```java
@InlineJavaScript("alert('Je suis un script inline !');")
@JavaScript("context://js/app.js")
```

| Propriété     | Type    | Description                                                               | Par défaut |
| ------------- | ------- | ------------------------------------------------------------------------- | ---------- |
| `top`         | `Boolean` | Spécifie si le script doit être injecté dans la fenêtre de premier niveau | `false`    |
| `attributes`  | `Object`  | Attributs à appliquer au script                                         | `{}`       |
| `id`          | `String`  | Un identifiant de ressource unique pour assurer une seule injection     | `""`       |

:::warning
Les scripts peuvent être injectés plusieurs fois en utilisant `InlineJavaScript` à moins qu'un ID spécifique ne soit assigné en utilisant la propriété `id`.
:::

## Importation de fichiers CSS {#importing-css-files}

L'inclusion déclarative de CSS est supportée par l'annotation `@StyleSheet`, permettant le chargement automatique des dépendances. L'annotation peut être appliquée tant au niveau des composants qu'au niveau de l'application.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Propriété     | Type    | Description                                                                   | Par défaut |
| ------------- | ------- | ----------------------------------------------------------------------------- | ---------- |
| `top`         | Boolean | Spécifie si la feuille de style doit être injectée dans la fenêtre de premier niveau | `false`    |
| `attributes`  | Object  | Attributs à appliquer à la feuille de style                                   | `{}`       |

#### Exemple : {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Les fichiers sont chargés uniquement lorsque le composant déclarant l'annotation est attaché à un conteneur. Chaque fichier est chargé une seule fois.
:::

## Injection de CSS {#injecting-css}

L'annotation `InlineStyleSheet` vous permet d'injecter du contenu CSS directement dans une page web tant au niveau des composants qu'au niveau de l'application.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Propriété     | Type    | Description                                                                                                               | Par défaut |
| ------------- | ------- | ------------------------------------------------------------------------------------------------------------------------- | ---------- |
| `top`         | Boolean | Spécifie si la feuille de style doit être injectée dans la fenêtre de premier niveau de la page.                         | `false`    |
| `attributes`  | Object  | Un ensemble de [attributs](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) à appliquer à l'élément de style. | `{}`       |
| `id`          | String  | Un identifiant de ressource unique. Si plusieurs ressources ont le même ID, elles seront regroupées ensemble dans un seul élément de style. | `""`       |
| `once`        | Boolean | Détermine si la feuille de style doit être injectée dans la page une seule fois, quelle que soit le nombre d'instances du composant. | `true`     |

:::tip 
Pour une meilleure coloration syntaxique lors de l'écriture de CSS inline pour vos composants, vous pouvez utiliser l'extension webforJ pour VS Code : [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Actifs dynamiques au runtime {#dynamic-assets-at-runtime}

La gestion dynamique des ressources est possible grâce à l'injection programmatique de JavaScript et CSS au runtime. Vous pouvez charger ou injecter des ressources en fonction du contexte runtime.

### Chargement et injection de JavaScript {#loading-and-injecting-javascript}

Chargez ou injectez dynamiquement du JavaScript au runtime en utilisant l'<JavadocLink type="foundation" location="com/webforj/Page" code='true'>API Page</JavadocLink>. Cela vous permet de charger des scripts depuis des URL ou d'injecter des scripts inline directement dans le DOM.

```java
Page page = Page.getCurrent();

// Chargement de fichiers JavaScript
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injection de JavaScript inline
page.addInlineJavaScript("console.log('Injection au runtime');");
page.addInlineJavaScript("alert('Ce script s'exécute inline');");
```

| Paramètre     | Description                                                                                                             |
| --------------| ----------------------------------------------------------------------------------------------------------------------- |
| `script`      | L'URL ou le contenu du script inline à injecter. Les URL commençant par `context://` se résolvent dans le dossier des ressources racine de l'application. |
| `top`         | Détermine si le script doit être injecté en haut de la page.                                                          |
| `attributes`  | Une carte des attributs à définir pour le script.                                                                      |

### Chargement et Injection de CSS {#loading-and-injecting-css}

Chargez ou injectez dynamiquement du CSS au runtime en utilisant l'<JavadocLink type="foundation" location="com/webforj/Page" code='true'>API Page</JavadocLink>. Cela vous permet de charger des feuilles de style depuis des URL ou d'injecter des styles inline directement dans le DOM.

```java
Page page = Page.getCurrent();

// Chargement de fichiers CSS
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Injection de CSS inline
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Paramètre     | Description                                                                                                                 |
| --------------| --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet`  | L'URL ou le contenu de la feuille de style inline à injecter. Les URL commençant par `context://` se résolvent dans le dossier des ressources racine de l'application. |
| `top`         | Détermine si la feuille de style doit être injectée en haut de la page.                                                  |
| `attributes`  | Une carte des attributs à définir pour la feuille de style.                                                                |
