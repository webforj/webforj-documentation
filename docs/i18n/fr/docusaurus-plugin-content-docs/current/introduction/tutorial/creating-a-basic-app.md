---
title: Creating a Basic App
sidebar_position: 2
_i18n_hash: 9bd91b6d733198a2c16c9377029e8162
---
Cette première étape pose les bases de l'application de gestion des clients en créant une interface simple et interactive. Cela montre comment configurer une application webforJ de base, avec un seul bouton qui ouvre une boîte de dialogue lorsqu'il est cliqué. C'est une mise en œuvre simple qui introduit des composants clés et vous donne un aperçu du fonctionnement de webforJ.

Cette étape tire parti de la classe de base fournie par webforJ pour définir la structure et le comportement de l'application. En poursuivant vers les étapes suivantes, vous passerez à une configuration plus avancée utilisant le routage pour gérer plusieurs écrans, introduit dans [Scaling with Routing and Composites](./scaling-with-routing-and-composites).

À la fin de cette étape, vous disposerez d'une application fonctionnelle qui démontre l'interaction de base avec les composants et la gestion des événements dans webforJ. Pour exécuter l'application :

- Allez dans le répertoire `1-creating-a-basic-app`
- Exécutez la commande `mvn jetty:run`

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/creating-a-basic-app.mp4" type="video/mp4"/>
  </video>
</div>

## Création d'une application webforJ {#creating-a-webforj-app}

Dans webforJ, un `App` représente le centre névralgique pour définir et gérer votre projet. Chaque application webforJ commence par créer une classe qui étend la classe fondamentale `App`, qui sert de cadre principal pour :

- Gérer le cycle de vie de l'application, y compris l'initialisation et la terminaison.
- Gérer le routage et la navigation si activé.
- Définir le thème, la locale et d'autres configurations globales de l'application.
- Fournir des utilitaires essentiels pour interagir avec l'environnement et les composants.

### Extension de la classe `App` {#extending-the-app-class}

Pour cette étape, une classe appelée `DemoApplication.java` est créée et étend la classe `App`.

```java title="DemoApplication.java"
public class DemoApplication extends App {
  @Override
  public void run() {
    // La logique de l'application principale ira ici
  }
}
```

:::tip Propriétés de configuration clés

Dans cette application de démonstration, le fichier `webforj.conf` est configuré avec les deux propriétés essentielles suivantes :

- **`webforj.entry`** : Précise le nom pleinement qualifié de la classe étendant `App` qui agit comme point d'entrée principal pour votre projet. Pour ce tutoriel, définissez-le sur `com.webforj.demos.DemoApplication` pour éviter toute ambiguïté lors de l'initialisation.
  ```hocon
  webforj.entry = com.webforj.demos.DemoApplication
  ```
- **`webforj.debug`** : Active le mode débogage pour des journaux détaillés et une visibilité des erreurs pendant le développement. Assurez-vous que cela est défini sur `true` tout en travaillant sur ce tutoriel :
  ```hocon
  webforj.debug = true
  ```

Pour plus de détails sur d'autres options de configuration, consultez le [Guide de configuration](../../configuration/overview).
:::

### Surcharge de la méthode `run()` {#overriding-the-run-method}

Après avoir assuré la configuration correcte du projet, la méthode `run()` dans votre classe `App` est surchargée.

La méthode `run()` est le cœur de votre application dans webforJ. Elle définit ce qui se passe après que l'application soit initialisée et constitue le point d'entrée principal pour les fonctionnalités de votre application. En surchargeant la méthode `run()`, vous pouvez mettre en œuvre la logique qui crée et gère l'interface utilisateur et le comportement de votre application.

:::tip Utilisation du routage
Lors de la mise en œuvre du routage à l'intérieur d'une application, il n'est pas nécessaire de surcharger la méthode `run()`, car le cadre gère automatiquement l'initialisation des routes et la création du `Frame` initial. La méthode `run()` est invoquée après que la route de base soit résolue, garantissant que le système de navigation de l'application est complètement initialisé avant que toute logique soit exécutée. Ce tutoriel approfondira la mise en œuvre du routage dans [l'étape 3](scaling-with-routing-and-composites). Plus d'informations sont également disponibles dans l'[Article sur le routage](../../routing/overview).
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

Dans webforJ, les composants sont les éléments constitutifs de l'interface utilisateur de votre application. Ces composants représentent des morceaux discrets de l'UI de votre application, tels que des boutons, des champs de texte, des boîtes de dialogue ou des tableaux.

Vous pouvez penser à une interface utilisateur comme à un arbre de composants, avec un `Frame` servant de racine. Chaque composant ajouté au `Frame` devient une branche ou une feuille de cet arbre, contribuant à la structure et au comportement global de votre application.

:::tip Catalogue de composants
Consultez [cette page](../../components/overview) pour une liste des divers composants disponibles dans webforJ.
:::

### `Frame` de l'application {#app-frame}

La classe `Frame` dans webforJ représente une fenêtre de premier niveau non imbriquée dans votre application. Un `Frame` agit généralement comme le conteneur principal pour les composants UI, ce qui en fait un élément essentiel pour la construction de l'interface utilisateur. Chaque application commence avec au moins un `Frame`, et vous pouvez y ajouter des composants tels que des boutons, des boîtes de dialogue ou des formulaires.

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

Chaque composant côté serveur dans webforJ a un composant web côté client correspondant. Les composants côté serveur gèrent la logique et les interactions de backend, tandis que les composants côté client comme `dwc-button` et `dwc-dialog` gèrent le rendu et le style frontend.

:::tip Composants composites

Avec les composants de base fournis par webforJ, vous pouvez concevoir des composants composites personnalisés en regroupant plusieurs éléments en une seule unité réutilisable. Ce concept sera abordé dans cette étape du tutoriel. Plus d'informations sont disponibles dans l'[Article sur les composants composites](../../building-ui/composite-components)
:::

Les composants doivent être ajoutés à une classe conteneur qui implémente l'interface <JavadocLink type="foundation" location="com/webforj/concern/HasComponents" code='true' >HasComponents</JavadocLink>. Le `Frame` est une telle classe - pour cette étape, ajoutez un `Paragraph` et un `Button` au `Frame`, qui seront rendus dans l'interface utilisateur dans le navigateur :

```java title="DemoApplication.java"
public class DemoApplication extends App {
  Paragraph demo = new Paragraph("Application de démonstration !");
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

Exécuter cela devrait vous donner un bouton simple stylisé permettant d'afficher un message disant "Ceci est une démo !"

## Styliser avec CSS {#styling-with-css}

La stylisation dans webforJ vous donne une complète flexibilité pour concevoir l'apparence de votre application. Bien que le cadre prenne en charge un design et un style cohérents dès le départ, il n'impose pas d'approche de style spécifique, vous permettant d'appliquer des styles personnalisés qui correspondent aux exigences de votre application.

Avec webforJ, vous pouvez appliquer dynamiquement des noms de classe aux composants pour un style conditionnel ou interactif, utiliser CSS pour un système de design cohérent et évolutif, et injecter des feuilles de style en ligne ou externes entières.

### Ajout de classes CSS aux composants {#adding-css-classes-to-components}

Vous pouvez ajouter ou supprimer dynamiquement des noms de classes aux composants à l'aide des méthodes `addClassName()` et `removeClassName()`. Ces méthodes vous permettent de contrôler les styles du composant en fonction de la logique de votre application. Ajoutez le nom de classe `mainFrame` au `Frame` créé dans les étapes précédentes en incluant le code suivant dans la méthode `run()` :

```java
mainFrame.addClassName("mainFrame");
```

### Attachement de fichiers CSS {#attaching-css-files}

Pour styliser votre application, vous pouvez inclure des fichiers CSS dans votre projet soit en utilisant des annotations d'actifs, soit en utilisant l'API d'actifs <JavadocLink type="foundation" location="com/webforj/Page" >webforJ</JavadocLink> à l'exécution. [Voir cet article](../../managing-resources/importing-assets) pour plus d'informations.

Par exemple, l'annotation @StyleSheet est utilisée pour inclure des styles provenant du répertoire resources/static. Elle génère automatiquement une URL pour le fichier spécifié et l'injecte dans le DOM, garantissant que les styles sont appliqués à votre application. Notez que les fichiers en dehors du répertoire static ne sont pas accessibles.

```java title="DemoApplication.java"
@StyleSheet("ws://styles/library.css")
public class DemoApplication extends App {
  @Override
  public void run() {
    // Logique de l'application ici
  }
}
```
:::tip URL de serveur Web
Pour garantir que les fichiers statiques sont accessibles, ils doivent être placés dans le dossier resources/static. Pour inclure un fichier statique, vous pouvez construire son URL en utilisant le protocole du serveur web.
:::

### Exemple de code CSS {#sample-css-code}

Un fichier CSS est utilisé dans votre projet à `resources > static > css > demoApplication.css`, et le code CSS suivant est utilisé pour appliquer quelques styles de base à l'application.

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
@AppTitle("Démonstration Étape 1")
public class DemoApplication extends App {
```

Les styles CSS sont appliqués au `Frame` principal et fournissent une structure en disposant les composants avec une [disposition en grille](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_grid_layout), et en ajoutant des styles de marge, de remplissage et de bordure pour rendre l'interface utilisateur visuellement organisée.
