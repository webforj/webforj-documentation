---
title: App Basics
sidebar_position: 3
_i18n_hash: eb456b4bb94bf617f33f8aa8425ad97f
---
Une fois que webforJ et ses dépendances sont configurés dans votre projet, vous êtes prêt à créer la structure de l'application. Cet article vous guidera à travers les éléments clés d'une application webforJ de base, en se concentrant spécifiquement sur les classes `Application` et `HomeView`, qui sont les classes fondamentales dans le projet de démarrage `webforj-archetype-hello-world`.

## Classe principale de l'application : `Application.java` {#main-app-class-applicationjava}

La classe `Application` sert de point d'entrée pour votre application webforJ, configurant les configurations et les routes essentielles. Pour commencer, remarquez la déclaration et les annotations de la classe.

Cette classe étend la classe de base `App` de webforJ, la rendant reconnaissable comme une application webforJ. Diverses annotations configurent le thème, le titre et le routage de l'application.

```java
@Routify(packages = "com.samples.views")
@AppTitle("webforJ Hello World")
@StyleSheet("ws://app.css")
public class Application extends App {
}
```

- `@Routify` : Spécifie que webforJ doit scanner le package `com.samples.views` pour les composants de route.
- `@AppTitle` : Définit le titre affiché sur l'onglet du navigateur de l'application.
- `@StyleSheet` : Lien vers un fichier CSS externe, `app.css`, permettant un stylage personnalisé pour l'application.

La classe `Application` ne contient pas de méthodes supplémentaires car les configurations sont définies par des annotations, et webforJ gère l'initialisation de l'application.

Avec `Application.java` configuré, l'application est maintenant dotée d'un titre et de routes pointant vers le package des vues. Ensuite, un aperçu de la classe `HomeView` donne un aperçu de ce qui est affiché lorsque l'application est exécutée.

### Découverte d'une `App` {#discovering-an-app}

Une seule <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> est imposée dans webforJ, ce qui transfère toutes les responsabilités de gestion des erreurs au côté Java et donne aux développeurs un contrôle total sur la gestion des erreurs.

Au cours du processus de démarrage de webforJ, toutes les classes qui étendent <JavadocLink type="foundation" location="com/webforj/App" code='true'>com.webforj.App</JavadocLink> sont scannées. Si plusieurs applications sont trouvées, le système recherche l'annotation <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true'>com.webforj.annotation.AppEntry</JavadocLink>. Si l'une des classes découvertes est annotée avec <JavadocLink type="foundation" location="com/webforj/annotation/AppEntry" code='true' >@AppEntry</JavadocLink>, la première rencontrée est considérée comme le point d'entrée.

- Si une classe est annotée avec `@AppEntry`, cette classe est sélectionnée comme point d'entrée.
- Si plusieurs classes sont annotées avec `@AppEntry`, une exception est levée, listant toutes les classes découvertes.
- Si aucune classe n'est annotée et qu'une seule sous-classe de `App` est trouvée, cette classe est sélectionnée comme point d'entrée.
- Si aucune classe n'est annotée et que plusieurs sous-classes de `App` sont trouvées, une exception est levée, détaillant chaque sous-classe.

:::tip Gestion des erreurs
Pour plus d'informations sur la façon dont les erreurs sont gérées dans webforJ, consultez [cet article](../advanced/error-handling).
:::

## Classe principale de la vue : `HomeView.java` {#main-view-class-homeviewjava}

La classe `HomeView` définit un composant de vue simple qui sert de page d'accueil pour l'application. Elle affiche un champ et un bouton pour saluer le nom saisi par l'utilisateur.

### Déclaration de la classe et annotations {#class-declaration-and-annotations}

`HomeView` étend `Composite<FlexLayout>`, ce qui lui permet d'agir comme un composant réutilisable composé d'un composant [`FlexLayout`](../components/flex-layout). L'annotation [`@Route("/")`](../routing/overview) fait de cela la route racine de l'application.

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("Quel est votre nom ?");
  private Button btn = new Button("Dire Bonjour");

  public HelloWorldView(){
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> 
          Toast.show("Bienvenue dans le Starter webforJ " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}
```

### Initialisation des composants {#component-initialization}

Dans la classe, plusieurs éléments de l'interface utilisateur sont initialisés et déclarés :

```java
private FlexLayout self = getBoundComponent();
private TextField hello = new TextField("Quel est votre nom ?");
private Button btn = new Button("Dire Bonjour");
```

- `self` : Le composant principal de mise en page utilisant [`FlexLayout`](../components/flex-layout), configuré comme un conteneur pour les éléments. Cet élément utilise la méthode `getBoundComponent()` pour stocker le principal `FlexLayout` que la classe contient.
- `hello` : Un [`TextField`](../components/fields/textfield) étiqueté `Quel est votre nom ?` pour que les utilisateurs saisissent leur nom.
- `btn` : Un [`Button`](../components/button) stylé principal étiqueté `Dire Bonjour`.

### Configuration de la mise en page {#layout-configuration}

La mise en page `(self)` est configurée avec quelques propriétés de style clés :

- `FlexDirection.COLUMN` empile les éléments verticalement.
- `setMaxWidth(300)` limite la largeur à 300 pixels pour une mise en page compacte.
- `setStyle("margin", "1em auto")` centre la mise en page avec une marge autour.

### Ajout de composants à la mise en page {#adding-components-to-the-layout}
Enfin, le champ de texte hello et le bouton btn sont ajoutés au conteneur [`FlexLayout`](../components/flex-layout) en appelant `self.add(hello, btn)`. Cet agencement définit la structure de la vue, rendant le formulaire à la fois interactif et visuellement centré.

## Styliser l'application {#styling-the-app}

Le fichier `styles.css` fournit un stylage personnalisé pour votre application webforJ. Ce fichier CSS est référencé dans la classe Application à l'aide de l'annotation [`@StyleSheet`](../managing-resources/importing-assets#importing-css-files), ce qui permet à l'application d'appliquer des styles aux composants au sein de l'application.

Ce fichier est situé dans le répertoire `resources/static` du projet, et peut être référencé en utilisant l'URL du serveur web `ws://app.css`.
