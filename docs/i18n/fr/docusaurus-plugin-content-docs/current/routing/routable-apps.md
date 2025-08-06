---
sidebar_position: 2
title: Routable Apps
_i18n_hash: 889bb5d90fac8315d6b7b1cf766fadea
---
Le routage dans webforJ est un outil optionnel. Les développeurs peuvent choisir entre la solution de routage webforJ ou un modèle traditionnel avec manipulation de `Frame` et sans liaison profonde. Pour activer le routage, l'annotation **`@Routify`** doit être appliquée au niveau de la classe implémentant `App`. Cela accorde à webforJ l'autorité de gérer l'historique du navigateur, de répondre aux événements de navigation et de rendre les composants de l'application en fonction de l'URL.

:::info
Pour en savoir plus sur la construction d'interfaces utilisateur utilisant des frames, des composants intégrés et personnalisés, visitez la section [Building UIs](../building-ui/basics).
:::

## Objectif de l'annotation `@Routify` {#purpose-of-the-routify-annotation}

**`@Routify`** permet au cadre d'enregistrer automatiquement des routes, de gérer la visibilité des frames et de définir des comportements de routage tels que le débogage et l'initialisation des frames, permettant un routage dynamique et flexible dans l'application.

## Utilisation de `@Routify` {#usage-of-routify}

L'annotation **`@Routify`** est appliquée au niveau de la classe principale de l'application. Elle spécifie l'ensemble des packages à scanner pour les routes et gère d'autres paramètres liés au routage tels que l'initialisation des frames et la gestion de la visibilité.

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
    // Logique de l'application ici
  }
}
```

:::tip Configurations par défaut de Routify
L'annotation **`@Routify`** vient avec des configurations par défaut raisonnables. Elle suppose que le package actuel où l'application est définie, ainsi que tous ses sous-packages, doivent être scannés pour les routes. De plus, elle suppose que l'application gère uniquement une frame par défaut. Si votre application suit cette structure, il n'est pas nécessaire de fournir des configurations personnalisées à l'annotation.
:::

## Éléments clés de `@Routify` {#key-elements-of-routify}

### 1. **`packages`** {#1-packages}

L'élément `packages` définit quels packages doivent être scannés pour les définitions de route. Il permet la découverte automatique des routes sans enregistrement manuel, simplifiant le processus d'expansion du système de routage de l'application.

```java
@Routify(packages = {"com.myapp.views"})
```

Si aucun package n'est spécifié, le package par défaut de l'application est utilisé.

### 2. **`defaultFrameName`** {#2-defaultframename}

Cet élément spécifie le nom de la frame par défaut que l'application initialise. Les frames représentent des conteneurs UI de haut niveau, et ce paramètre contrôle la manière dont la première frame est nommée et gérée.

```java
@Routify(defaultFrameName = "MainFrame")
```

Par défaut, si elle n'est pas fournie explicitement, la valeur est définie sur `Routify.DEFAULT_FRAME_NAME`.

### 3. **`initializeFrame`** {#3-initializeframe}

Le drapeau `initializeFrame` détermine si le cadre doit automatiquement initialiser la première frame lorsque l'application démarre. Le définir sur `true` simplifie la configuration de la première frame.

```java
@Routify(initializeFrame = true)
```

### 4. **`manageFramesVisibility`** {#4-manageframesvisibility}

Cet élément contrôle si le cadre doit automatiquement basculer la visibilité des frames pendant la navigation. Lorsqu'il est activé, la route correspondante montre automatiquement la frame correspondante tout en cachant les autres, garantissant une interface utilisateur propre et ciblée. Ce paramètre est uniquement pertinent lorsque votre application gère plusieurs frames.

```java
@Routify(manageFramesVisibility = true)
```

### 5. **`debug`** {#5-debug}

Le drapeau `debug` permet d'activer ou de désactiver le mode de débogage du routage. Lorsqu'il est activé, les informations et actions de routage sont enregistrées dans la console pour faciliter le débogage pendant le développement. 

```java
@Routify(debug = true)
```

:::info Mode de débogage du routeur et mode de débogage webforJ  
Si le mode de débogage du routeur est défini sur `true` mais que le mode de débogage webforJ est défini sur `false`, aucune information de débogage ne sera affichée dans la console.  
:::
