---
sidebar_position: 12
title: Route Transitions
_i18n_hash: 98050ac6a061f4dc3728af3888aa44b0
---
<JavadocLink type="foundation" location="com/webforj/router/annotation/RouteTransition" top='true'/>

<DocChip chip='since' label='25.11' />
<DocChip chip='experimental' />

Les transitions de route fournissent des transitions animées déclaratives lors de la navigation entre les routes. Basé sur l'API [View Transitions](/docs/advanced/view-transitions), l'ajout de l'annotation `@RouteTransition` à vos composants de route permet au routeur de gérer automatiquement le cycle de vie de l'animation pendant la navigation.

<ExperimentalWarning />

:::info Contrôle programmatique
Pour des scénarios de transition plus complexes ou un contrôle programmatique, utilisez directement l'API [View Transitions](/docs/advanced/view-transitions).
:::

## L'annotation `@RouteTransition` {#the-routetransition-annotation}

L'annotation `@RouteTransition` définit comment un composant de route s'anime lorsqu'il entre ou sort de la vue :

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.FADE)
public class DashboardView extends Composite<Div> {
  // implémentation de la vue
}
```

L'annotation accepte les propriétés suivantes :

| Propriété | Description |
|-----------|-------------|
| `enter`   | Animation appliquée lorsque cette vue apparaît |
| `exit`    | Animation appliquée lorsque cette vue quitte |

Les deux propriétés acceptent n'importe quel type de transition prédéfini ou une valeur de chaîne personnalisée :

| Constante                       | Effet                                                       |
|---------------------------------|-------------------------------------------------------------|
| `ViewTransition.NONE`           | Pas d'animation                                            |
| `ViewTransition.FADE`           | Fondu entre l'ancien et le nouveau contenu                |
| `ViewTransition.SLIDE_LEFT`     | Le contenu se déplace vers la gauche (comme une navigation vers l'avant) |
| `ViewTransition.SLIDE_RIGHT`    | Le contenu se déplace vers la droite (comme une navigation vers l'arrière) |
| `ViewTransition.SLIDE_UP`       | Le contenu se déplace vers le haut                         |
| `ViewTransition.SLIDE_DOWN`     | Le contenu se déplace vers le bas                          |
| `ViewTransition.ZOOM`           | L'ancien contenu se rétrécit, le nouveau contenu grandit   |
| `ViewTransition.ZOOM_OUT`       | L'ancien contenu grandit, le nouveau contenu se rétrécit   |

## Utilisation de base {#basic-usage}

Ajoutez l'annotation à tout composant de route pour activer les transitions :

```java title="InboxView.java"
@Route(value = "inbox", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.ZOOM, exit = ViewTransition.SLIDE_RIGHT)
@FrameTitle("Inbox")
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.add(new H1("Inbox"));
    // ...
  }
}
```

Dans cet exemple :
- Lors de la navigation vers `InboxView`, le composant entre avec une animation de zoom
- Lors de la navigation hors de `InboxView`, le composant sort avec le contenu se déplaçant vers la droite

## Flux de navigation {#navigation-flow}

Lors de la navigation entre deux routes, le routeur coordonne la séquence de transition :

1. L'animation `exit` du composant sortant commence
2. Les changements [DOM](/docs/glossary#dom) se produisent (ancienne vue supprimée, nouvelle vue ajoutée)
3. L'animation `enter` du composant entrant se joue

Si la navigation se fait vers la même vue déjà affichée, la transition est ignorée pour éviter des animations inutiles.

:::tip Animations de sortie cohérentes
L'utilisation de la même animation de sortie sur toutes les vues crée une cohérence directionnelle. Par exemple, configurer toutes les vues pour sortir avec `SLIDE_RIGHT` établit un motif de mouvement uniforme vers l'arrière, rendant le comportement de navigation prévisible, peu importe la vue d'origine.
:::

## Héritage de transition {#transition-inheritance}

Les routes héritent des transitions de leurs routes parentes. Lorsqu'une route n'a pas `@RouteTransition`, le routeur monte dans la hiérarchie pour en trouver une.

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {
  // Mise en page parente avec transition
}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Hérite de ZOOM de MainLayout
}

@Route(value = "/sub", outlet = InboxView.class)
public class SubView extends Composite<FlexLayout> {
  // Hérite de ZOOM de MainLayout (via InboxView)
}
```

Toutes les routes enfants héritent du même style d'animation sans répéter l'annotation.

### Surcharge des transitions héritées {#overriding-inherited-transitions}

Les routes enfants peuvent remplacer la transition héritée en définissant leur propre `@RouteTransition` :

```java
@Route
@RouteTransition(enter = ViewTransition.ZOOM)
public class MainLayout extends Composite<AppLayout> {}

@Route(value = "/inbox", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  // Hérite de ZOOM
}

@Route(value = "/settings", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.SLIDE_UP, exit = ViewTransition.SLIDE_DOWN)
public class SettingsView extends Composite<FlexLayout> {
  // Remplace avec SLIDE_UP/SLIDE_DOWN
}
```

## Transitions de composants partagés {#shared-component-transitions}

Vous pouvez combiner des transitions de route avec des animations de composants partagés pour créer des expériences connectées. Les composants avec des valeurs `view-transition-name` correspondantes se transforment entre les vues. Utilisez la méthode `setViewTransitionName()`, disponible sur tout composant qui implémente l'interface <JavadocLink type="foundation" location="com/webforj/concern/HasStyle" code='true'>HasStyle</JavadocLink>.

```java title="ProductListView.java"
@Route(value = "products", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductListView extends Composite<FlexLayout> {

  private void buildProductCard(Product product) {
      Img thumbnail = new Img(product.getImageUrl());
      thumbnail.setViewTransitionName("product-image-" + product.getId());
      // ...
  }
}
```

```java title="ProductDetailView.java"
@Route(value = "products/:id", outlet = MainLayout.class)
@RouteTransition(enter = ViewTransition.FADE)
public class ProductDetailView extends Composite<FlexLayout> implements DidEnterObserver {

  private Img heroImage = new Img();

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
      String id = parameters.get("id").orElse("");
      heroImage.setViewTransitionName("product-image-" + id);
      // ...
  }
}
```

Lors de la navigation de la liste à la vue détaillée, la miniature du produit se transforme en position d'image héroïque tandis que le reste du contenu se transitionne avec l'animation de fondu.
