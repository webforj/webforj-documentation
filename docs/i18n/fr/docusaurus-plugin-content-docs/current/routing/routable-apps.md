---
sidebar_position: 2
title: Routable Apps
_i18n_hash: edec1086b0723febd831816f8d1fa76a
---
Le routage dans webforJ est un outil optionnel. Les développeurs peuvent choisir entre la solution de routage de webforJ ou un modèle traditionnel avec manipulation de `Frame` et sans liaison profonde. Pour activer le routage, l'annotation **`@Routify`** doit être appliquée au niveau d'une classe implémentant `App`. Cela permet à webforJ de gérer l'historique du navigateur, de répondre aux événements de navigation et de rendre les composants de l'application en fonction de l'URL.

:::info
Pour en savoir plus sur la construction d'interfaces utilisateur à l'aide de cadres, de composants intégrés et personnalisés, visitez [Construction d'interfaces utilisateur](../building-ui/overview).
:::

## Objectif de l'annotation `@Routify` {#purpose-of-the-routify-annotation}

**`@Routify`** permet au framework d'enregistrer automatiquement les routes, de gérer la visibilité des cadres et de définir des comportements de routage tels que le débogage et l'initialisation des cadres, permettant ainsi un routage dynamique et flexible dans l'application.

## Utilisation de `@Routify` {#usage-of-routify}

L'annotation **`@Routify`** est appliquée au niveau de la classe principale de l'application. Elle spécifie l'ensemble des packages à analyser pour les routes et gère d'autres paramètres liés au routage tels que l'initialisation des cadres et la gestion de la visibilité.

Voici un exemple de base :

```java
@Routify(
  packages = {"com.myapp.views"},
  defaultFrameName = "MainFrame",
  initializeFrame = true,
  manageFramesVisibility = false,
  debug = true
)
public class MyApp extends App {

  @Override
  public void run() {
    // La logique de l'application va ici
  }
}
```

:::tip Configurations par défaut de Routify
L'annotation **`@Routify`** est fournie avec des configurations par défaut raisonnables. Elle suppose que le package actuel où l'application est définie, ainsi que tous ses sous-packages, doivent être analysés pour les routes. De plus, elle suppose que l'application gère par défaut un seul cadre. Si votre application suit cette structure, il n'est pas nécessaire de fournir des configurations personnalisées à l'annotation.
:::

## Éléments clés de `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

L'élément `packages` définit quels packages doivent être analysés pour les définitions de routes. Cela permet la découverte automatique des routes sans enregistrement manuel, simplifiant le processus d'expansion du système de routage de l'application.

```java
@Routify(packages = {"com.myapp.views"})
```

Si aucun package n'est spécifié, le package par défaut de l'application est utilisé.

### 2. **`defaultFrameName`** {#2-defaultframename}

Cet élément spécifie le nom du cadre par défaut que l'application initialise. Les cadres représentent des conteneurs UI de niveau supérieur, et ce paramètre contrôle comment le premier cadre est nommé et géré.

```java
@Routify(defaultFrameName = "MainFrame")
```

Par défaut, si aucun nom n'est fourni, la valeur est définie sur `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

Le drapeau `initializeFrame` détermine si le framework doit automatiquement initialiser le premier cadre lorsque l'application démarre. Le définir sur `true` simplifie la configuration initiale du cadre.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Cet élément contrôle si le framework doit automatiquement basculer la visibilité des cadres pendant la navigation. Lorsqu'il est activé, la route correspondante affiche automatiquement le cadre correspondant tout en masquant les autres, garantissant une interface utilisateur propre et ciblée. Ce paramètre est pertinent uniquement si votre application gère plusieurs cadres.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

Le drapeau `debug` active ou désactive le mode de débogage du routage. Lorsqu'il est activé, les informations et actions de routage sont enregistrées dans la console pour faciliter le débogage pendant le développement.

```java
@Routify(debug = true)
```

:::info Mode de débogage du routeur et mode de débogage de webforJ  
Si le mode de débogage du routeur est défini sur `true` mais que le mode de débogage de webforJ est défini sur `false`, aucune information de débogage ne sera affichée dans la console.  
:::
