---
sidebar_position: 6
title: Query Parameters
_i18n_hash: c3b57611c46f7cd4fa9946ff704213cc
---
Les paramètres de requête vous permettent de passer des données supplémentaires via les URL, en utilisant le format `?key1=value1&key2=value2`. Alors que les paramètres de route sont utilisés pour passer des données requises dans le chemin de l'URL, les paramètres de requête fournissent un mécanisme flexible pour passer des données optionnelles ou supplémentaires. Ils sont particulièrement utiles lors du filtrage de contenu, du tri ou du traitement de plusieurs valeurs pour la même clé.

## Aperçu des paramètres de requête {#query-parameters-overview}

Les paramètres de requête dans webforJ suivent la convention URL typique : paires clé-valeur séparées par `=` et concaténées avec `&`. Ils sont ajoutés à l'URL après un `?` et offrent un moyen flexible de passer des données optionnelles, telles que des préférences de filtrage ou de tri.

Par exemple :

```
/products?category=electronics&sort=price
```

## Récupération des paramètres de requête {#retrieving-query-parameters}

Les paramètres de requête sont accessibles via l'objet `ParametersBag`. Pour récupérer les paramètres de requête, utilisez la méthode `getQueryParameters()` de l'objet `Location`.

Voici comment vous pouvez récupérer les paramètres de requête à partir d'une URL dans une vue :

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    String category = queryParameters.get("category").orElse("all");
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Catégorie: " + category);
    console().log("Tri: " + sort);
  }
}
```

Dans cet exemple :
- La méthode `onDidEnter` récupère les paramètres de requête de l'objet `Location` fourni par l'événement `DidEnterEvent`.
- Le `ParametersBag` vous permet de récupérer des paramètres de requête spécifiques à l'aide de `get()`, qui renvoie un `Optional<String>`. Vous pouvez spécifier une valeur par défaut en utilisant `orElse()` si le paramètre n'est pas présent.

:::tip Méthodes d'accès du `ParametersBag`
Le `ParametersBag` fournit plusieurs variations de getters pour aider à convertir la valeur des paramètres de requête en types spécifiques et les filtrer. Voici la liste complète des getters disponibles :

- **`get(String key)`** : Récupère la valeur du paramètre en tant que `String`.
- **`getAlpha(String key)`** : Retourne uniquement les caractères alphabétiques à partir de la valeur du paramètre.
- **`getAlnum(String key)`** : Retourne uniquement les caractères alphanumériques à partir de la valeur du paramètre.
- **`getDigits(String key)`** : Retourne uniquement les chiffres numériques à partir de la valeur du paramètre.
- **`getInt(String key)`** : Analyse et retourne la valeur du paramètre en tant que `Integer`.
- **`getFloat(String key)`** : Analyse et retourne la valeur du paramètre en tant que `Float`.
- **`getDouble(String key)`** : Analyse et retourne la valeur du paramètre en tant que `Double`.
- **`getBoolean(String key)`** : Analyse et retourne la valeur du paramètre en tant que `Boolean`.

Ces méthodes vous aident à garantir que les valeurs sont formatées et castées correctement, évitant le besoin de parsing ou de validation manuelle.
:::

## Gestion de plusieurs valeurs pour un paramètre de requête {#handling-multiple-values-for-a-query-parameter}

Parfois, un paramètre de requête peut avoir plusieurs valeurs pour la même clé, comme dans l'exemple suivant :

```
/products?category=electronics,appliances&sort=price
```

Le `ParametersBag` fournit une méthode pour gérer cela en récupérant les valeurs sous forme de liste :

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    List<String> categories = queryParameters.getList("category").orElse(List.of("all"));
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Catégories: " + categories);
    console().log("Tri: " + sort);
  }
}
```

Dans cet exemple :
- `getList("category")` récupère toutes les valeurs associées à la clé `category`, les retournant sous forme de liste.

:::tip Délimiteur de plusieurs valeurs
Par défaut, la méthode `getList()` utilise une virgule (`,`) comme délimiteur. Vous pouvez personnaliser le délimiteur en passant un caractère différent ou une expression régulière comme deuxième paramètre à la méthode `getList(String key, String regex)`.
:::

## Cas d'utilisation des paramètres de requête {#use-cases-for-query-parameters}

- **Filtrage de contenu** : Les paramètres de requête sont souvent utilisés pour appliquer des filtres, comme des catégories ou des mots-clés de recherche.
- **Tri des données** : Vous pouvez passer des préférences de tri via des paramètres de requête, comme le tri par prix, note ou date.
- **Gestion des paramètres optionnels** : Lorsque vous devez passer des données qui ne font pas partie de la structure de route requise, les paramètres de requête offrent de la flexibilité.
- **Passer plusieurs valeurs** : Les paramètres de requête vous permettent d'envoyer plusieurs valeurs pour une seule clé, ce qui est utile lorsque les utilisateurs sélectionnent plusieurs options, comme des catégories de produits ou des filtres.
