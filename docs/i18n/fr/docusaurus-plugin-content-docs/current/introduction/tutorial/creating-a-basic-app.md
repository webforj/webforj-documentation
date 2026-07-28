---
title: Creating a Basic App
sidebar_position: 2
description: Step 1 - Add components to an app.
_i18n_hash: d7385c22706cf76508b7e1971186f88d
---
Dans [Configuration du projet](/docs/introduction/tutorial/project-setup), vous avez généré un projet webforJ. Maintenant, il est temps de créer la classe principale pour le projet et d'ajouter une interface interactive en utilisant les composants webforJ. À cette étape, vous apprendrez à propos de :

- Le point d'entrée pour les applications utilisant webforJ et Spring Boot
- Les composants webforJ et éléments HTML
- Utiliser le CSS pour styliser les composants

Compléter cette étape crée une version de [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app).

<!-- Insérez la vidéo ici -->

## Exécuter l'application {#running-the-app}

Au fur et à mesure que vous développez votre application, vous pouvez utiliser [1-creating-a-basic-app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app) comme point de comparaison. Pour voir l'application en action :

1. Naviguez jusqu'au répertoire de premier niveau contenant le fichier `pom.xml`, qui est `1-creating-a-basic-app` si vous suivez la version sur GitHub.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.

## Le point d'entrée {#entry-point}

Chaque application webforJ contient une seule classe qui s'étend <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>. Pour ce tutoriel, et d'autres projets webforJ publiés, elle est couramment appelée `Application`. Cette classe est à l'intérieur d'un package qui porte le nom de `groupId` que vous avez utilisé dans [Configuration du projet](/docs/introduction/tutorial/project-setup) :

```
1-creating-a-basic-app
│   .editorconfig
│   .gitignore
│   pom.xml
│   README.md
│
├───.vscode
├───src/main/java
// surligner la ligne suivante
│   └──com/webforj/tutorial
// surligner la ligne suivante
│       └──Application.java
└───target
```

À l'intérieur de la classe `Application`, la méthode `SpringApplication.run()` utilise les configurations pour lancer l'application. Les différentes annotations sont pour les configurations de l'application.

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Application Client", shortName = "CustomerApp")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

### Annotations {#annotations}

L'[`@SpringBootApplication`](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/SpringBootApplication.html) est une annotation principale dans Spring Boot. Vous mettez cette annotation sur la classe principale pour la marquer comme le point de départ de votre application.

`@BundleEntry`, `@AppTheme`, et `@AppProfile` ne sont que quelques-unes des <JavadocLink type="foundation" location="com/webforj/annotation/package-summary">annotations webforJ</JavadocLink> disponibles lorsque vous voulez définir explicitement des configurations.

- **`@BundleEntry`** ajoute un fichier de `src/main/frontend` au bundle frontend de l'application. À cette étape, il charge le fichier CSS que vous créerez plus tard dans [Styliser avec CSS](#styling-with-css).

- **`@AppTheme`** gère le thème visuel de l'application. S'il est défini sur `system`, l'application adopte automatiquement le thème préféré de l'utilisateur : `light`, `dark`, ou `dark-pure`. Pour des informations sur la création de thèmes personnalisés ou la substitution des thèmes par défaut, veuillez consulter l'article [Thèmes](/docs/styling/themes).

- **`@AppProfile`** aide à configurer comment l'application se présente à l'utilisateur en tant qu'[application installable](/docs/configuration/installable-apps). Au minimum, cette annotation nécessite un `name` pour le nom complet de l'application et un `shortName` à utiliser lorsque l'espace est limité. Le `shortName` ne doit pas dépasser 12 caractères.

## Créer une interface utilisateur {#creating-a-ui}

Pour créer votre UI, vous devrez ajouter des [éléments HTML ](/docs/components/html-elements) et des [composants webforJ](/docs/components/overview). Pour l’instant, vous n'avez qu'une application monopage, donc vous ajouterez directement des composants dans la classe `Application`. Pour ce faire, vous devez remplacer la méthode `App.run()` et créer un `Frame` pour ajouter des composants.

```java
@Override
public void run() throws WebforjException {
  Frame mainFrame = new Frame();

  // Créer des composants UI et les ajouter au cadre

}
```

### Utiliser des éléments HTML {#using-html-elements}

Vous pouvez ajouter des éléments HTML standards à votre application avec [éléments HTML](/docs/components/html-elements). Créez une nouvelle instance du composant, puis utilisez la méthode `add()` pour l'ajouter au `Frame` :

```java
// Créer le conteneur pour les éléments UI
Frame mainFrame = new Frame();

// Créer le composant HTML
Paragraph tutorial = new Paragraph("Application Tutoriel !");

// Ajouter le composant au conteneur
mainFrame.add(tutorial);
```

### Utiliser des composants webforJ {#webforj-components-and-html-elements}

Bien que les éléments HTML soient utiles pour la structure, la sémantique et les besoins UI légers, les [composants webforJ](/docs/components/overview) offrent un comportement plus complexe et dynamique.

Le code ci-dessous ajoute un [Button](/docs/components/button) composant, change son apparence avec la méthode `setTheme()`, et ajoute un écouteur d'événements pour créer un [Message Dialog](/docs/components/option-dialogs/message) composant lorsque le bouton est cliqué. La plupart des méthodes de composant webforJ qui modifient un composant retournent le composant lui-même, donc vous pouvez chaîner plusieurs méthodes pour un code plus compact.

```java
// Créer le conteneur pour les éléments UI
Frame mainFrame = new Frame();

// Créer le composant webforJ
Button btn = new Button("Info");

// Modifier le composant webforJ et ajouter un écouteur d'événements
btn.setTheme(ButtonTheme.PRIMARY)
  .addClickListener(e -> OptionDialog.showMessageDialog("C'est un tutoriel !", "Info"));

// Ajouter le composant au conteneur
mainFrame.add(btn);
```

## Styliser avec CSS {#styling-with-css}

La plupart des composants webforJ ont des méthodes intégrées pour effectuer des changements de style communs, comme la taille et le thème.

```java
//Définir la largeur du cadre en utilisant un mot clé CSS
mainFrame.setWidth("fit-content");

//Définir la largeur maximale du bouton en utilisant des pixels
btn.setMaxWidth(200);

//Définir le thème du bouton sur PRIMARY
btn.setTheme(ButtonTheme.PRIMARY);
```

En plus de ces méthodes, vous pouvez styliser votre application en utilisant CSS. La section **Stylisation** de la page de documentation de n'importe quel composant contient des détails spécifiques sur les propriétés CSS pertinentes.

webforJ est également livré avec un ensemble de variables CSS conçues appelées tokens DWC. Consultez la documentation [Stylisation](/docs/styling/overview) pour des informations détaillées sur la façon de styliser les composants webforJ, et comment utiliser les tokens.

### Ajouter CSS au bundle frontend {#referencing-a-css-file}

Il est préférable d'avoir un fichier CSS séparé pour garder tout organisé et maintenable. Créez un fichier nommé `card.css` à l'intérieur de `src/main/frontend/css`, avec la définition de classe CSS suivante :

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

Ensuite, ajoutez le fichier au bundle frontend depuis `Application.java` en utilisant `@BundleEntry("css/card.css")`. Le chemin est relatif à `src/main/frontend`.

:::tip Bundler frontend
La configuration Maven du projet tutoriel exécute le surveillant frontend webforJ lorsque vous démarrez l'application avec `mvn`, de sorte que les changements sous `src/main/frontend` sont reconstruits pendant le développement. Pour en savoir plus, consultez [Bundler frontend](/docs/managing-resources/bundler/overview).
:::

### Ajouter des classes CSS aux composants {#adding-css-classes-to-components}

Vous pouvez ajouter ou supprimer dynamiquement des noms de classe aux composants à l'aide des méthodes `addClassName()` et `removeClassName()`. Pour ce tutoriel, une seule classe CSS est utilisée :

```java
mainFrame.addClassName("card");
```

## Application `Application` terminée {#completed-application}

Votre classe `Application` devrait désormais ressembler à ceci :

```java title="Application.java"
@SpringBootApplication
@BundleEntry("css/card.css")
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

    btn
      .setTheme(ButtonTheme.PRIMARY)
      .setMaxWidth(200)
      .addClickListener(e ->
        OptionDialog.showMessageDialog("C'est un tutoriel !", "Info")
      );

    mainFrame.setWidth("fit-content").addClassName("card").add(tutorial, btn);
  }
}
```

:::tip Pages multiples
Pour une application plus complexe, vous pouvez diviser l'UI en plusieurs pages pour une meilleure organisation. Ce concept est couvert plus tard dans ce tutoriel dans [Routage et Composites](/docs/introduction/tutorial/routing-and-composites).
:::

## Étape suivante {#next-step}

Après avoir créé une application fonctionnelle avec une interface utilisateur de base, l'étape suivante consiste à ajouter un modèle de données et afficher les résultats dans un composant `Table` dans [Travailler avec des données](/docs/introduction/tutorial/working-with-data).
