---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: ac74bc5c04bce477a7407c9ff94323a4
---
Dans [Configuration du projet](/docs/introduction/tutorial/project-setup), vous avez généré un projet webforJ. Il est maintenant temps de créer la classe principale pour le projet et d'ajouter une interface interactive en utilisant les composants webforJ. Dans cette étape, vous apprendrez sur :

- Le point d'entrée pour les applications utilisant webforJ et Spring Boot
- Les composants de webforJ et d'éléments HTML
- Utiliser CSS pour styliser les composants

Finaliser cette étape crée une version de [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insérez la vidéo ici -->

## Exécution de l'application {#running-the-app}

Alors que vous développez votre application, vous pouvez utiliser [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) comme comparaison. Pour voir l'application en action :

1. Accédez au répertoire principal contenant le fichier `pom.xml`, c'est `1-creating-a-basic-app` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Le point d'entrée {#entry-point}

Chaque application webforJ contient une seule classe qui étend <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. Pour ce tutoriel, et d'autres projets webforJ publiés, elle est couramment appelée `Application`. Cette classe se trouve dans un paquet qui porte le nom du `groupId` que vous avez utilisé dans [Configuration du projet](/docs/introduction/tutorial/project-setup) :

```
1-creating-a-basic-app 
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// surligne-la-prochaine-ligne
│   └──com/webforj/tutorial
// surligne-la-prochaine-ligne
│       └──Application.java
└───target
```

À l'intérieur de la classe `Application`, la méthode `SpringApplication.run()` utilise les configurations pour lancer l'application. Les différentes annotations sont pour les configurations de l'application.

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Application Client", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotations {#annotations}

L'[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) est une annotation essentielle dans Spring Boot. Vous placez cette annotation sur la classe principale pour la marquer comme le point de départ de votre application.

`@StyleSheet`, `@AppTheme` et `@AppProfile` ne sont que quelques-unes des nombreuses <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">annotations webforJ</JavadocLink> disponibles lorsque vous souhaitez définir explicitement des configurations.

- **`@StyleSheet`** intègre un fichier CSS dans la page Web. Des informations complémentaires sur la façon d'interagir avec un fichier CSS spécifique seront abordées plus tard dans [Styliser avec CSS](#styling-with-css).

- **`@AppTheme`** gère le thème visuel de l'application. S'il est défini sur `system`, l'application adopte automatiquement le thème préféré de l'utilisateur : `clair`, `sombre` ou `sombre-pur`. Pour des informations sur la création de thèmes personnalisés ou le remplacement des thèmes par défaut, reportez-vous à l'article [Thèmes](/docs/styling/themes).

- **`@AppProfile`** aide à configurer comment l'application se présente à l'utilisateur en tant qu'[application installable](/docs/configuration/installable-apps). Au minimum, cette annotation nécessite un `name` pour le nom complet de l'application et un `shortName` à utiliser lorsque l'espace est limité. Le `shortName` ne doit pas dépasser 12 caractères.  

## Création d'une interface utilisateur {#creating-a-ui}

Pour créer votre UI, vous devrez ajouter des [composants d'éléments HTML](/docs/components/html-elements) et des [composants webforJ](/docs/components/overview). Pour l'instant, vous n'avez qu'une application à page unique, donc vous ajouterez directement des composants à la classe `Application`. 
Pour ce faire, vous devez remplacer la méthode `App.run()` et créer un `Frame` pour y ajouter des composants. 

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Créez les composants UI et ajoutez-les au cadre

}
```

### Utilisation des éléments HTML {#using-html-elements}

Vous pouvez ajouter des éléments HTML standard à votre application avec des [composants d'éléments HTML](/docs/components/html-elements).
Créez une nouvelle instance du composant, puis utilisez la méthode `add()` pour l'ajouter au `Frame` :

```java
// Créez le conteneur pour les éléments UI
Frame mainFrame = new Frame();

// Créez le composant HTML
Paragraph tutorial = new Paragraph("Application Tutoriel !");

// Ajoutez le composant au conteneur
mainFrame.add(tutorial);
```

### Utilisation des composants webforJ {#webforj-components-and-html-elements}

Bien que les éléments HTML soient utiles pour la structure, la sémantique et des besoins UI légers, les [composants webforJ](/docs/components/overview) offrent un comportement plus complexe et dynamique.

Le code ci-dessous ajoute un [Bouton](/docs/components/button) composant, change son apparence avec la méthode `setTheme()`, et ajoute un écouteur d'événements pour créer un composant [Dialogue de message](/docs/components/option-dialogs/message) lorsque le bouton est cliqué.
La plupart des méthodes des composants webforJ qui modifient un composant retournent le composant lui-même, vous pouvez donc chaîner plusieurs méthodes pour avoir un code plus compact.

```java
// Créez le conteneur pour les éléments UI
Frame mainFrame = new Frame();

// Créez le composant webforJ
Button btn = new Button("Info");

// Modifiez le composant webforJ et ajoutez un écouteur d'événements
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("Ceci est un tutoriel !", "Info"));

// Ajoutez le composant au conteneur
mainFrame.add(btn);
```

## Styliser avec CSS {#styling-with-css}

La plupart des composants webforJ ont des méthodes intégrées pour effectuer des changements de style courants, comme la taille et le thème.

```java
// Définir la largeur du Frame en utilisant un mot clé CSS
mainFrame.setWidth("fit-content");

// Définir la largeur maximale du bouton en pixels
btn.setMaxWidth(200);

// Définir le thème du bouton sur PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

En plus de ces méthodes, vous pouvez styliser votre application en utilisant CSS. La section **Styliser** de la page de documentation de tout composant contient des détails spécifiques sur les propriétés CSS pertinentes.

webforJ est également accompagné d'un ensemble de variables CSS conçues appelées tokens DWC. Consultez la documentation [Styliser](/docs/styling/overview) pour des informations détaillées sur la façon de styliser les composants webforJ et d'utiliser les tokens.

### Référence à un fichier CSS {#referencing-a-css-file} 

Il est préférable d'avoir un fichier CSS séparé pour garder tout organisé et facile à maintenir. Créez un fichier nommé `card.css` à l'intérieur de `src/main/resources/static/css`, avec la définition de classe CSS suivante :

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

:::tip Protocole Webserver
Ce tutoriel utilise le protocole Webserver pour référencer le fichier CSS. Pour en savoir plus sur son fonctionnement, consultez [Gestion des ressources](/docs/managing-resources/overview).
:::

### Ajout de classes CSS aux composants {#adding-css-classes-to-components}

Vous pouvez ajouter ou supprimer dynamiquement des noms de classe aux composants en utilisant les méthodes `addClassName()` et `removeClassName()`. Pour ce tutoriel, il n'y a qu'une seule classe CSS utilisée :

```java
mainFrame.addClassName("card");
```

## Application `Application` complétée {#completed-application}

Votre classe `Application` devrait maintenant ressembler à ceci :

```java title="Application.java"
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Application Client", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Application Tutoriel !");
    Button btn = new Button("Info");

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Ceci est un tutoriel !", "Info"));

    mainFrame.setWidth("fit-content")
        .addClassName("card")
        .add(tutorial, btn);
  }

}
```

:::tip Plusieurs pages
Pour une application plus complexe, vous pouvez diviser l'interface utilisateur en plusieurs pages pour une meilleure organisation. Ce concept est abordé plus tard dans ce tutoriel dans [Routage et composites](/docs/introduction/tutorial/routing-and-composites).
:::

## Étape suivante {#next-step}

Après avoir créé une application fonctionnelle avec une interface utilisateur de base, la prochaine étape consiste à ajouter un modèle de données et à afficher les résultats dans un composant `Table` dans [Travailler avec des données](/docs/introduction/tutorial/working-with-data).
