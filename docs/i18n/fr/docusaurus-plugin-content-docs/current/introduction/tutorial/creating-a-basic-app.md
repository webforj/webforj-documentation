---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: 7c98bf3851e1db10d5e0dd68045ea22d
---
Dans [Project Setup](/docs/introduction/tutorial/project-setup), vous avez généré un projet webforJ. Il est maintenant temps de créer la classe principale pour le projet et d'ajouter une interface interactive en utilisant les composants webforJ. Dans cette étape, vous apprendrez à propos de :

- Le point d'entrée pour les applications utilisant webforJ et Spring Boot
- Les composants webforJ et les éléments HTML
- L'utilisation de CSS pour styliser les composants

Compléter cette étape crée une version de [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insérer la vidéo ici -->

## Exécution de l'application {#running-the-app}

Au fur et à mesure que vous développez votre application, vous pouvez utiliser [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) comme référence. Pour voir l'application en action :

1. Naviguez vers le répertoire de niveau supérieur contenant le fichier `pom.xml`, c'est `1-creating-a-basic-app` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Le point d'entrée {#entry-point}

Chaque application webforJ contient une seule classe qui étend <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. Pour ce tutoriel, et d'autres projets webforJ publiés, elle est communément appelée `Application`. Cette classe se trouve à l'intérieur d'un package nommé d'après le `groupId` que vous avez utilisé dans [Project Setup](/docs/introduction/tutorial/project-setup) :

```
1-creating-a-basic-app
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// highlight-next-line
│   └──com/webforj/tutorial
// highlight-next-line
│       └──Application.java
└───target
```

À l'intérieur de la classe `Application`, la méthode `SpringApplication.run()` utilise les configurations pour lancer l'application. Les diverses annotations concernent les configurations de l'application.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Application Client", shortName = "ClientApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotations {#annotations}

L'[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) est une annotation centrale dans Spring Boot. Vous placez cette annotation sur la classe principale pour marquer le point de départ de votre application.

`@StyleSheet`, `@AppTheme`, et `@AppProfile` ne sont que quelques-unes des nombreuses <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">annotations webforJ</JavadocLink> disponibles lorsque vous souhaitez définir explicitement des configurations.

- **`@StyleSheet`** intègre un fichier CSS dans la page web. Des détails supplémentaires sur la façon d'interagir avec un fichier CSS spécifique peuvent être trouvés plus tard dans [Styling with CSS](#styling-with-css).

- **`@AppTheme`** gère le thème visuel de l'application. S'il est défini sur `system`, l'application adopte automatiquement le thème préféré de l'utilisateur : `light`, `dark`, ou `dark-pure`. Pour des informations sur la création de thèmes personnalisés ou le remplacement des thèmes par défaut, reportez-vous à l'article sur les [Thèmes](/docs/styling/themes).

- **`@AppProfile`** aide à configurer la façon dont l'application se présente à l'utilisateur en tant qu'[application installable](/docs/configuration/installable-apps). Au minimum, cette annotation nécessite un `name` pour le nom complet de l'application et un `shortName` à utiliser lorsque l'espace est limité. Le `shortName` ne doit pas dépasser 12 caractères.

## Création d'une interface utilisateur {#creating-a-ui}

Pour créer votre interface utilisateur, vous devez ajouter des [HTML element components](/docs/components/html-elements) et des [webforJ components](/docs/components/overview). Pour l'instant, vous n'avez qu'une application à page unique, donc vous ajouterez des composants directement dans la classe `Application`.
Pour ce faire, substituez la méthode `App.run()` et créez un `Frame` pour ajouter des composants.

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Créez des composants UI et ajoutez-les au cadre

}
```

### Utilisation des éléments HTML {#using-html-elements}

Vous pouvez ajouter des éléments HTML standard à votre application avec des [HTML element components](/docs/components/html-elements).
Créez une nouvelle instance du composant, puis utilisez la méthode `add()` pour l'ajouter au `Frame` :

```java
// Créer le conteneur pour les éléments UI
Frame mainFrame = new Frame();

// Créer le composant HTML
Paragraph tutorial = new Paragraph("Application Tutoriel!");

// Ajouter le composant au conteneur
mainFrame.add(tutorial);
```

### Utilisation des composants webforJ {#webforj-components-and-html-elements}

Bien que les éléments HTML soient utiles pour la structure, la sémantique, et les besoins UI légers, les [webforJ components](/docs/components/overview) offrent un comportement plus complexe et dynamique.

Le code ci-dessous ajoute un [Button](/docs/components/button), change son apparence avec la méthode `setTheme()`, et ajoute un écouteur d'événements pour créer un composant [Message Dialog](/docs/components/option-dialogs/message) lorsque le bouton est cliqué.
La plupart des méthodes de composants webforJ qui modifient un composant retournent le composant lui-même, vous pouvez donc chaîner plusieurs méthodes pour un code plus compact.

```java
// Créer le conteneur pour les éléments UI
Frame mainFrame = new Frame();

// Créer le composant webforJ
Button btn = new Button("Info");

// Modifier le composant webforJ, et ajouter un écouteur d'événements
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("C'est un tutoriel !", "Info"));

// Ajouter le composant au conteneur
mainFrame.add(btn);
```

## Stylisation avec CSS {#styling-with-css}

La plupart des composants webforJ ont des méthodes intégrées pour apporter des modifications de style courantes, telles que la dimension et le thème.

```java
//Définir la largeur du Frame en utilisant un mot-clé CSS
mainFrame.setWidth("fit-content");

//Définir la largeur maximale du bouton en pixels
btn.setMaxWidth(200);

//Définir le thème du bouton sur PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

En plus de ces méthodes, vous pouvez styliser votre application à l'aide de CSS. La section **Stylisation** de la page de documentation de tout composant contient des détails spécifiques sur les propriétés CSS pertinentes.

webforJ est également livré avec un ensemble de variables CSS conçues appelées tokens DWC. Consultez la documentation sur [Styling](/docs/styling/overview) pour des informations détaillées sur la façon de styliser les composants webforJ et comment utiliser les tokens.

### Référencer un fichier CSS {#referencing-a-css-file}

Il est préférable d'avoir un fichier CSS séparé pour garder tout organisé et maintenable. Créez un fichier nommé `card.css` dans `src/main/resources/static/css`, avec la définition de classe CSS suivante :

```css title="card.css"
.card {
  display: grid;
  gap: var(--dwc-space-l);
  padding: var(--dwc-space-l);
  margin: var(--dwc-space-l) auto;
  border: thin solid var(--dwc-color-default);
  border-radius: 16px;
  background-color: var(--dwc-surface-3);
  box-shadow: var(--dwc-shadow-xs);
}
```

Ensuite, référencez le fichier dans `Application.java` en utilisant l'annotation `@StyleSheet` avec le nom du fichier CSS. Pour cette étape, c'est `@StyleSheet("ws://css/card.css")`.

:::tip Protocoles de serveur web
Ce tutoriel utilise le protocole de serveur web pour référencer le fichier CSS. Pour en savoir plus sur son fonctionnement, consultez [Gestion des Ressources](/docs/managing-resources/overview).
:::

### Ajouter des classes CSS aux composants {#adding-css-classes-to-components}

Vous pouvez ajouter ou supprimer dynamiquement des noms de classes aux composants à l'aide des méthodes `addClassName()` et `removeClassName()`. Pour ce tutoriel, une seule classe CSS est utilisée :

```java
mainFrame.addClassName("card");
```

## Application `Application` complétée {#completed-application}

Votre classe `Application` devrait maintenant ressembler à ceci :

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Application Client", shortName = "ClientApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Application Tutoriel!");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("C'est un tutoriel !", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Pages multiples
Pour une application plus complexe, vous pouvez diviser l'interface utilisateur en plusieurs pages pour une meilleure organisation. Ce concept est traité plus tard dans ce tutoriel dans [Routing and Composites](/docs/introduction/tutorial/routing-and-composites).
:::

## Prochaine étape {#next-step}

Après avoir créé une application fonctionnelle avec une interface utilisateur de base, la prochaine étape consiste à ajouter un modèle de données et à afficher les résultats dans un composant `Table` dans [Working with Data](/docs/introduction/tutorial/working-with-data).
