---
sidebar_position: 5
title: Route Patterns
_i18n_hash: 2f1668e34197bb2f4bb6c5b3ec6e87e5
---
**Les motifs de route** sont utilisés pour définir comment les URL sont associées à des vues spécifiques, y compris des segments dynamiques et optionnels, des expressions régulières et des caractères génériques. Les motifs de route permettent au framework de faire correspondre les URL, d'extraire des paramètres et de générer des URL dynamiquement. Ils jouent un rôle essentiel dans la structuration de la navigation d'une application et le rendu des composants en fonction de l'emplacement du navigateur.

## Syntaxe des motifs de route {#route-pattern-syntax}

Les motifs de route dans webforJ sont très flexibles, prenant en charge les fonctionnalités suivantes :

- **Paramètres nommés :** Désignés par `:paramName`, ils sont obligatoires, sauf s'ils sont marqués comme optionnels.
- **Paramètres optionnels :** Désignés par `:paramName?`, ils peuvent être omis de l'URL.
- **Segments de caractères génériques :** Représentés par `*`, ils capturent tous les segments restants de l'URL.
- **Contraintes d'expressions régulières :** Les contraintes ne peuvent être ajoutées qu'aux paramètres nommés (par exemple, `:id<[0-9]+>`).

### Exemple de définitions de motifs de route {#example-of-route-pattern-definitions}

```java
@Route("customer/:id<[0-9]+>/named/:name/*")
public class CustomerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String name = parameters.getAlpha("name").orElse("Inconnu");
    String extra = parameters.getAlpha("*").orElse("");

    String result =
        "ID du client : " + id + "-" +
        "Nom : " + name + "-" +
        "* : " + extra;

    console().log(result);
  }
}
```

Dans cet exemple :

- `:id<[0-9]+>` capture un ID client numérique.
- `:name` capture un nom.
- `*` capture tous les segments de chemin supplémentaires au-delà de `named/:name`.

## Paramètres nommés {#named-parameters}

Les paramètres nommés sont définis en préfixant deux-points `:` au nom du paramètre dans le motif. Ils sont obligatoires, sauf s'ils sont marqués comme optionnels. Les paramètres nommés peuvent également avoir des [contraintes](#regular-expression-constraints) d'expressions régulières pour valider les valeurs.

### Exemple : {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Logique du composant ici
}
```

Ce motif correspond à des URL comme `/product/123` où `id` est `123`.

## Paramètres optionnels {#optional-parameters}

Les paramètres optionnels sont indiqués en ajoutant un `?` après le nom du paramètre. Ces segments ne sont pas obligatoires et peuvent être omis de l'URL.

### Exemple : {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("ID de commande : " + id),
      () -> console().log("Aucun ID de commande n'a été fourni")
    );
  }
}
```

Ce motif correspond à la fois à `/order/123` pour inclure une valeur numérique, et à `/order`, permettant l'omission d'une valeur numérique lorsque `/order` est entré.

## Contraintes d'expressions régulières {#regular-expression-constraints}

Vous pouvez appliquer des contraintes d'expressions régulières aux paramètres en les ajoutant entre chevrons `<>`. Cela vous permet de spécifier des règles de correspondance plus strictes pour les paramètres.

### Exemple : {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("Code produit : " + code),
      () -> console().error("Code produit introuvable"));
  }
}
```

Ce motif ne correspond qu'aux codes produit au format `ABC-1234`. Par exemple, `/product/XYZ-5678` correspondra, mais `/product/abc-5678` ne correspondra pas.

## Segments de caractères génériques {#wildcard-segments}

Les caractères génériques peuvent être utilisés pour capturer les chemins entiers suivant un segment de route spécifique, mais ils ne peuvent apparaître qu'en tant que dernier segment dans le motif, résolvant toutes les valeurs suivantes dans l'URL. Pour une meilleure lisibilité, les segments de caractères génériques peuvent être nommés. Cependant, contrairement aux paramètres nommés, les segments de caractères génériques ne peuvent avoir aucune contrainte.

### Exemple : {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView : " + pathname),
      () -> console().log("FileManagerView : Aucun paramètre de chemin")
    );
  }
}
```

Ce motif correspond à toute URL commençant par `/files` et capture le reste du chemin en tant que caractère générique.

## Priorité des routes {#route-priority}

Lorsque plusieurs routes correspondent à une URL donnée, l'attribut de priorité d'une route détermine quelle route est sélectionnée en premier. Cela est particulièrement utile lorsque deux ou plusieurs routes se chevauchent dans leurs motifs de chemin, et que vous devez contrôler laquelle d'entre elles est prioritaire. L'attribut de priorité est disponible dans les annotations `@Route` et `@RouteAlias`.

### Comment fonctionne le système de priorité {#how-the-priority-system-works}

L'attribut de priorité permet au routeur de déterminer l'ordre dans lequel les routes sont évaluées lorsqu'il pourrait y avoir plusieurs routes correspondant à une URL donnée. Les routes sont triées en fonction de leurs valeurs de priorité, avec une priorité plus élevée (valeurs numériques inférieures) étant correspondue en premier. Cela garantit que les routes plus spécifiques prennent le pas sur les plus générales.

Si deux routes partagent la même priorité, le routeur résout le conflit en sélectionnant la route qui a été enregistrée en premier. Ce mécanisme garantit que la bonne route est choisie, même lorsque plusieurs routes se chevauchent dans leurs motifs d'URL.

:::info Priorité par défaut  
Par défaut, toutes les routes se voient attribuer une priorité de `10`.  
:::

### Exemple : Routes en conflit {#example-conflicting-routes}

Considérons un scénario où deux routes correspondent à des motifs d'URL similaires :

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("inconnu");
    console().log("Consultation de la catégorie : " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("inconnu");
    console().log("Consultation du produit : " + productId);
  }
}
```

Voici comment le système de priorité aide à résoudre les conflits :

- **`ProductCategoryView`** correspond à des URL comme `/products/electronics`.
- **`ProductView`** correspond à des URL plus spécifiques comme `/products/electronics/123`, où `123` est l'ID produit.

Dans ce cas, les deux routes pourraient correspondre à l'URL `/products/electronics`. Cependant, parce que `ProductCategoryView` a une priorité plus élevée (priorité = 9), elle sera correspondue en premier lorsqu'il n'y a pas de `productId` dans l'URL. Pour des URL comme `/products/electronics/123`, `ProductView` sera correspondue en raison de la présence du paramètre `productId`.
