---
sidebar_position: 4
title: Composer des Composants
description: >-
  Combine webforJ components into reusable units by extending Composite,
  configuring the bound component, and overriding initBoundComponent.
_i18n_hash: 7ca404aa73a9fd445ce7cd3da09b8155
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Le composant `Composite` combine des composants webforJ existants en composants auto-contenus et réutilisables avec un comportement personnalisé. Utilisez-le pour envelopper des composants internes webforJ en unités logiques réutilisables, réutilisez des modèles de composants dans votre application et combinez plusieurs composants sans exposer les détails d'implémentation.

Un composant `Composite` a une forte association avec un composant lié sous-jacent. Cela vous donne un contrôle sur les méthodes et propriétés auxquelles les utilisateurs peuvent accéder, contrairement à l'héritage traditionnel où tout est exposé.

Si vous devez intégrer des composants web d'une autre source, utilisez des alternatives spécialisées :

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html) : Pour les composants web avec gestion de propriété typée
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html) : Pour les composants web qui acceptent le contenu en slot

<AISkillTip skill="webforj-creating-components" />

## Utilisation {#usage}

Pour définir un composant `Composite`, étendez la classe `Composite` et spécifiez le type de composant qu'elle gère. Cela devient votre composant lié, qui est le conteneur racine qui contient votre structure interne :

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Accédez au composant lié pour le configurer
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Soumettre"));
  }
}
```

La méthode `getBoundComponent()` permet d'accéder à votre composant sous-jacent, vous permettant de configurer ses propriétés, d'ajouter des composants enfants et de gérer son comportement directement.

Le composant lié peut être n'importe quel [composant webforJ](/docs/components/overview) ou [composant d'élément HTML](/docs/components/html-elements). Pour des mises en page flexibles, envisagez d'utiliser [`FlexLayout`](/docs/components/flex-layout) ou [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) comme votre composant lié.

:::note Extension de composant
Ne jamais étendre `Component` ou `DwcComponent` directement. Utilisez toujours des modèles de composition avec `Composite` pour construire des composants personnalisés.
:::

Surchargez `initBoundComponent()` lorsque vous avez besoin d'une plus grande flexibilité dans la création et la gestion du composant lié, comme l'utilisation de constructeurs paramétrés au lieu du constructeur par défaut sans argument. Utilisez ce modèle lorsque le composant lié nécessite que des composants soient passés à son constructeur plutôt que d'être ajoutés par la suite.

```java title="CustomFormLayout.java"
public class CustomFormLayout extends Composite<FlexLayout> {
 private TextField nameField;
 private TextField emailField;
 private Button submitButton;

 @Override
 protected FlexLayout initBoundComponent() {
   nameField = new TextField("Nom");
   emailField = new TextField("Email");
   submitButton = new Button("Soumettre");

   FlexLayout layout = new FlexLayout(nameField, emailField, submitButton);
   layout.setDirection(FlexDirection.COLUMN);
   layout.setSpacing("10px");

   return layout;
 }
}
```

## Cycle de vie du composant {#component-lifecycle}

webforJ gère automatiquement toute la gestion du cycle de vie des composants `Composite`. En utilisant la méthode `getBoundComponent()`, la plupart des comportements personnalisés peuvent être gérés dans le constructeur, y compris l'ajout de composants enfants, la définition de propriétés, la configuration de la mise en page de base et l'enregistrement d'événements.

```java
public class UserDashboard extends Composite<FlexLayout> {
 private final FlexLayout self = getBoundComponent();
 private TextField searchField;
 private Button searchButton;
 private Div resultsContainer;

 public UserDashboard() {
   initializeComponents();
   setupLayout();
   configureEvents();
 }

 private void initializeComponents() {
   searchField = new TextField("Rechercher des utilisateurs...");
   searchButton = new Button("Rechercher");
   resultsContainer = new Div();
 }

 private void setupLayout() {
   FlexLayout searchRow = new FlexLayout(searchField, searchButton);
   searchRow.setAlignment(FlexAlignment.CENTER);
   searchRow.setSpacing("8px");

   getBoundComponent()
     .setDirection(FlexDirection.COLUMN)
     .add(searchRow, resultsContainer);
 }

 private void configureEvents() {
   searchButton.onClick(event -> performSearch());
 }

 private void performSearch() {
   // Logique de recherche ici
 }
}
```

Si vous avez des exigences de configuration ou de nettoyage supplémentaires, vous devrez peut-être utiliser les crochets de cycle de vie optionnels `onDidCreate()` et `onDidDestroy()` :

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialiser les composants qui nécessitent une attache au DOM
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Nettoyer les ressources
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Logique de mise à jour des données
 }
}
```

Si vous devez effectuer des actions après que le composant ait été attaché au DOM, utilisez la méthode `whenAttached()` :

```java title="InteractiveMap.java"
public class InteractiveMap extends Composite<Div> {
  public InteractiveMap() {
    setupMapContainer();

    whenAttached().thenAccept(component -> {
      initializeMapLibrary();
      loadMapData();
    });
  }
}
```

## Exemple de composant `Composite` {#example-composite-component}

L'exemple suivant démontre une liste de tâches où chaque élément est un composant `Composite` contenant un [`RadioButton`](/docs/components/radiobutton), un `Div` avec du texte, et un [`Button`](/docs/components/button).

<ComponentDemo
path='/webforj/composite'
files={[
  'src/main/java/com/webforj/samples/views/composite/CompositeView.java',
  'src/main/frontend/composite/composite.css',
]}
height='500px'
/>

## Exemple : Regroupement de composants {#example-component-grouping}

Parfois, vous pouvez vouloir utiliser un `Composite` pour regrouper des composants connexes en une seule unité, même lorsque la réutilisabilité n'est pas la principale préoccupation :

<ComponentDemo
path='/webforj/analyticscardcomposite'
files={[
  'src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java',
  'src/main/frontend/composite/analyticscomposite.css',
]}
height='550px'
/>
