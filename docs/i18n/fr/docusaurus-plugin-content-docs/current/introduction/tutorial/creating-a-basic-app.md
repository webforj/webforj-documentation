---
title: Créer une application de base
sidebar_position: 2
_i18n_hash: c59ff0def84230ed79877cba3d5e5aa4
---
Cette première étape pose les bases de l'application de gestion des clients en créant une interface simple et interactive. Cela démontre comment configurer une application webforJ de base, avec un seul bouton qui ouvre une boîte de dialogue lorsqu'il est cliqué. C'est une implémentation directe qui introduit des composants clés et vous donne une idée de la façon dont fonctionne webforJ.

Cette étape utilise la classe d'application de base fournie par webforJ pour définir la structure et le comportement de l'application. En poursuivant vers les étapes suivantes, vous passerez à une configuration plus avancée utilisant le routage pour gérer plusieurs écrans, introduite dans [Scaling with Routing and Composites](./scaling-with-routing-and-composites).

À la fin de cette étape, vous disposerez d'une application fonctionnelle qui démontre une interaction basique avec les composants et la gestion des événements dans webforJ. Pour exécuter l'application :

- Allez dans le répertoire `1-creating-a-basic-app`
- Exécutez la commande `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Création d'une application webforJ {#creating-a-webforj-app}

Dans webforJ, une `App` représente le point central pour définir et gérer votre projet. Chaque application webforJ commence par la création d'une classe qui étend la classe fondamentale `App`, qui sert de framework central pour :

- Gérer le cycle de vie de l'application, y compris l'initialisation et la terminaison.
- Gérer le routage et la navigation si activés.
- Définir le thème, la locale et d'autres configurations générales de l'application.
- Fournir des utilitaires essentiels pour interagir avec l'environnement et les composants.

### Élargir la classe `App` {#extending-the-app-class}

Pour cette étape, une classe appelée `DemoApplication.java` est créée et étend la classe `App`.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // La logique principale de l'application sera ici
  }
}
```

:::tip Propriétés de configuration clés

Dans cette application de démonstration, le fichier `webforj.conf` est configuré avec les deux propriétés essentielles suivantes :

- **`webforj.entry`** : Spécifie le nom complètement qualifié de la classe étendant `App` qui agit comme le point d'entrée principal pour votre projet. Pour ce tutoriel, il doit être défini sur `com.webforj.demos.DemoApplication` pour éviter toute ambiguïté lors de l'initialisation.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`** : Active le mode débogage pour des journaux détaillés et une visibilité des erreurs lors du développement. Assurez-vous que cela est réglé sur `true` pendant que vous travaillez sur ce guide :
  ```hocon
  webforj.debug = true
  ```

Pour plus de détails sur les options de configuration supplémentaires, consultez le [Guide de configuration](../../configuration/overview).
:::

### Surcharger la méthode `run()` {#overriding-the-run-method}

Après avoir vérifié la configuration correcte du projet, la méthode `run()` dans votre classe `App` est surchargée.

La méthode `run()` est le cœur de votre application dans webforJ. Elle définit ce qui se passe après que l'application a été initialisée et constitue le point d'entrée principal pour les fonctionnalités de votre application. En surchargeant la méthode `run()`, vous pouvez implémenter la logique qui crée et gère l'interface utilisateur et le comportement de votre application.

:::tip Utilisation du routage
Lors de l'implémentation du routage dans une application, il n'est pas nécessaire de surcharger la méthode `run()`, car le framework gère automatiquement l'initialisation des routes et la création du `Frame` initial. La méthode `run()` est invoquée après que la route de base a été résolue, garantissant que le système de navigation de l'application est complètement initialisé avant que toute logique ne soit exécutée. Ce tutoriel approfondira l'implémentation du routage dans [l'étape 3](scaling-with-routing-and-composites). Plus d'informations sont également disponibles dans l'[Article sur le routage](../../routing/overview).
:::

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    // Logique de l'application
  }
}
```

## Ajout de composants {#adding-components}

Dans webforJ, les composants sont les éléments de base de l'interface utilisateur de votre application. Ces composants représentent des parties discrètes de l'interface de votre application, telles que des boutons, des champs de texte, des boîtes de dialogue ou des tableaux.

Vous pouvez considérer une interface utilisateur comme un arbre de composants, avec un `Frame` servant de racine. Chaque composant ajouté au `Frame` devient une branche ou une feuille dans cet arbre, contribuant à la structure et au comportement global de votre application.

:::tip Catalogue de composants
Consultez [cette page](../../components/overview) pour une liste des différents composants disponibles dans webforJ.
:::

### `Frame` de l'application {#app-frame}

La classe `Frame` dans webforJ représente une fenêtre principale non imbriquée dans votre application. Un `Frame` agit généralement comme le conteneur principal pour les composants d'interface utilisateur, ce qui en fait un élément essentiel pour construire l'interface utilisateur. Chaque application commence avec au moins un `Frame`, et vous pouvez y ajouter des composants tels que des boutons, des boîtes de dialogue ou des formulaires.

Un `Frame` est créé dans la méthode `run()` à cette étape - par la suite, des composants seront ajoutés ici.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
  }
}
```

### Composants côté serveur et côté client {#server-and-client-side-components}

Chaque composant côté serveur dans webforJ a un composant web correspondant côté client. Les composants côté serveur gèrent la logique et les interactions en arrière-plan, tandis que les composants côté client comme `dwc-button` et `dwc-dialog` gèrent le rendu et le style des éléments en avant-plan.

:::tip Composants composites

En plus des composants de base fournis par webforJ, vous pouvez concevoir des composants composites personnalisés en regroupant plusieurs éléments en une seule unité réutilisable. Ce concept sera couvert dans cette étape du tutoriel. Plus d'informations sont disponibles dans l'[Article Composite](../../building-ui/composite-components)
:::

Les composants doivent être ajoutés à une classe conteneur qui implémente l'interface <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink>. Le `Frame` est une de ces classes - pour cette étape, ajoutez un `Paragraph` et un `Button` au `Frame`, qui seront rendus dans l'interface utilisateur dans le navigateur :

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Application de démo !");
  Button btn = new Button("Info");

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    btn.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> showMessageDialog("Ceci est une démo !", "Info"));
    mainFrame.add(demo, btn);
  }
}
```

En exécutant ceci, vous devriez obtenir un bouton simple stylisé permettant d'afficher un message disant "Ceci est une démo !"

## Stylisation avec CSS {#styling-with-css}

La stylisation dans webforJ vous donne une flexibilité totale pour concevoir l'apparence de votre application. Bien que le framework prenne en charge un design et un style cohérents par défaut, il n'impose pas d'approche spécifique de stylisation, vous permettant d'appliquer des styles personnalisés qui correspondent aux exigences de votre application.

Avec webforJ, vous pouvez appliquer dynamiquement des noms de classe aux composants pour un stylage conditionnel ou interactif, utiliser CSS pour un système de design cohérent et évolutif, et injecter des feuilles de style internes ou externes complètes.

### Ajout de classes CSS aux composants {#adding-css-classes-to-components}

Vous pouvez ajouter ou supprimer dynamiquement des noms de classe aux composants à l'aide des méthodes `addClassName()` et `removeClassName()`. Ces méthodes vous permettent de contrôler les styles du composant en fonction de la logique de votre application. Ajoutez le nom de classe `mainFrame` au `Frame` créé dans les étapes précédentes en incluant le code suivant dans la méthode `run()` :

```java
mainFrame.addClassName("mainFrame");
```

### Attachement de fichiers CSS {#attaching-css-files}

Pour styliser votre application, vous pouvez inclure des fichiers CSS dans votre projet soit en utilisant des annotations d'actif, soit en utilisant l'API <JavadocLink type="foundation" location="com/webforj/Page" >asset</JavadocLink> de webforJ à l'exécution. [Voir cet article](../../managing-resources/importing-assets) pour plus d'informations. 

Par exemple, l'annotation @StyleSheet est utilisée pour inclure des styles à partir du répertoire resources/static. Elle génère automatiquement une URL pour le fichier spécifié et l'injecte dans le DOM, assurant ainsi que les styles sont appliqués à votre application. Notez que les fichiers en dehors du répertoire statique ne sont pas accessibles.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // Logique de l'application ici
  }
}
```
:::tip URL de serveur web
Pour garantir que les fichiers statiques sont accessibles, ils doivent être placés dans le dossier resources/static. Pour inclure un fichier statique, vous pouvez construire son URL en utilisant le protocole du serveur web.
:::

### Exemple de code CSS {#sample-css-code}

Un fichier CSS est utilisé dans votre projet à `resources > static > css > demoApplication.css`, et le code CSS suivant est utilisé pour appliquer un style de base à l'application.

```css
.mainFrame {
  display: inline-grid;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border: 1px dashed;
  border-radius: 10px;
}
```

Une fois cela fait, l'annotation suivante doit être ajoutée à votre classe `App` :

```java title="DemoApplication.java"
@StyleSheet("ws://css/demoApplication.css")
@AppTitle("Étape de Démo 1")
public class DemoApplication extends App {
```

Les styles CSS sont appliqués au `Frame` principal et fournissent une structure en organisant les composants avec un [layout en grille](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout), et en ajoutant des marges, des remplissages et des styles de bordure pour rendre l'interface utilisateur visuellement organisée.
