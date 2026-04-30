---
sidebar_position: 4
title: Composite Components
_i18n_hash: fb15eb19cfe0ca1aebb77a67b10c9ecd
---
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Le composant `Composite` combine des composants webforJ existants en composants réutilisables, autonomes et dotés d'un comportement personnalisé. Utilisez-le pour envelopper des composants internes webforJ en unités de logique métier réutilisables, réutiliser des motifs de composants dans toute votre application et combiner plusieurs composants sans exposer les détails d'implémentation.

Un composant `Composite` a une forte association avec un composant lié sous-jacent. Cela vous permet de contrôler quels méthodes et propriétés les utilisateurs peuvent accéder, contrairement à l'héritage traditionnel où tout est exposé.

Si vous devez intégrer des composants web d'une autre source, utilisez des alternatives spécialisées :

- [ElementComposite](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementComposite.html) : Pour les composants web avec gestion des propriétés type-safe
- [ElementCompositeContainer](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/ElementCompositeContainer.html) : Pour les composants web qui acceptent un contenu de slot

<AISkillTip skill="webforj-creating-components" />

## Utilisation {#usage}

Pour définir un composant `Composite`, étendez la classe `Composite` et spécifiez le type de composant qu'elle gère. Cela devient votre composant lié, qui est le conteneur racine qui accueille votre structure interne :

```java title="BasicComposite.java"
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Accéder au composant lié pour le configurer
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Soumettre"));
  }
}
```

La méthode `getBoundComponent()` fournit un accès à votre composant sous-jacent, ce qui vous permet de configurer ses propriétés, d'ajouter des composants enfants et de gérer son comportement directement.

Le composant lié peut être n'importe quel [composant webforJ](/docs/components/overview) ou [composant d'élément HTML](/docs/components/html-elements). Pour des mises en page flexibles, envisagez d'utiliser [`FlexLayout`](/docs/components/flex-layout) ou [`Div`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/html/elements/Div.html) comme votre composant lié.

:::note Extension de composant
Ne jamais étendre directement `Component` ou `DwcComponent`. Utilisez toujours les motifs de composition avec `Composite` pour créer des composants personnalisés.
:::

Surchargez `initBoundComponent()` lorsque vous avez besoin d'une plus grande flexibilité pour créer et gérer le composant lié, par exemple en utilisant des constructeurs paramétrés au lieu du constructeur par défaut sans argument. Utilisez ce modèle lorsque le composant lié nécessite des composants à passer à son constructeur plutôt qu'à ajouter par la suite.

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

webforJ gère automatiquement toute la gestion de cycle de vie pour les composants `Composite`. En utilisant la méthode `getBoundComponent()`, la plupart des comportements personnalisés peuvent être gérés dans le constructeur, y compris l'ajout de composants enfants, la définition de propriétés, la configuration de la mise en page de base et l'enregistrement d'événements.

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

Si vous avez des exigences spécifiques supplémentaires de configuration ou de nettoyage, vous devrez peut-être utiliser les crochets de cycle de vie optionnels `onDidCreate()` et `onDidDestroy()` :

```java
public class DataVisualizationPanel extends Composite<Div> {
 private Interval refreshInterval;

 @Override
 protected void onDidCreate(Div container) {
   // Initialiser des composants nécessitant une attache DOM
   refreshInterval = new Interval(5.0, event -> updateData());
   refreshInterval.start();
 }

 @Override
 protected void onDidDestroy() {
   // Nettoyage des ressources
   if (refreshInterval != null) {
     refreshInterval.stop();
   }
 }

 private void updateData() {
   // Logique de mise à jour des données
 }
}
```

Si vous devez effectuer des actions après que le composant soit attaché au DOM, utilisez la méthode `whenAttached()` :

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

L'exemple suivant démontre une application Todo où chaque élément est un composant `Composite` constitué d'un [`RadioButton`](../components/radiobutton) stylé en tant qu'interrupteur et d'une Div avec du texte : 

<ComponentDemo 
path='/webforj/composite?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/CompositeView.java'
height='500px'
/>

## Exemple : Groupement de composants {#example-component-grouping}

Parfois, vous souhaiterez peut-être utiliser un `Composite` pour regrouper des composants liés ensemble en une seule unité, même lorsque la réutilisabilité n'est pas la principale préoccupation :

<ComponentDemo
path='/webforj/analyticscardcomposite?'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/composite/analyticscomposite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/composite/AnalyticsCardCompositeView.java'
height='500px'
/>
