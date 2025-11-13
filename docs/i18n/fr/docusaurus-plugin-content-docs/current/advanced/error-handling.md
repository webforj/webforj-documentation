---
title: Gestion des erreurs
sidebar_position: 25
_i18n_hash: 15106dd9fa7ccf0d4f722ca675f0d362
---
La gestion des erreurs est un aspect crucial du développement d'applications web robustes. Dans webforJ, la gestion des erreurs est conçue pour être flexible et personnalisable, permettant aux développeurs de gérer les exceptions de la manière qui correspond le mieux aux besoins de leur application.

## Aperçu {#overview}

Dans webforJ, la gestion des erreurs repose sur l'interface `ErrorHandler`. Cette interface permet aux développeurs de définir comment leur application doit réagir lorsque des exceptions se produisent pendant l'exécution. Par défaut, webforJ fournit un `GlobalErrorHandler` qui gère toutes les exceptions de manière générique. Cependant, les développeurs peuvent créer des gestionnaires d'erreurs personnalisés pour des exceptions spécifiques afin de fournir des réponses plus adaptées.

## Découverte et utilisation des gestionnaires d'erreurs {#discovering-and-using-error-handlers}

webforJ utilise l'interface de fournisseur de services (SPI) de Java pour découvrir et charger les gestionnaires d'erreurs.

### Processus de découverte {#discovery-process}

1. **Enregistrement du service** : Les gestionnaires d'erreurs sont enregistrés via le mécanisme `META-INF/services`.
2. **Chargement du service** : Au démarrage de l'application, webforJ charge toutes les classes mentionnées dans `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Gestion des erreurs** : Lorsqu'une exception se produit, webforJ vérifie si un gestionnaire d'erreurs existe pour cette exception spécifique.

### Sélection du gestionnaire {#handler-selection}

- Si un gestionnaire spécifique pour l'exception existe, il est utilisé.
- Si aucun gestionnaire spécifique n'est trouvé, mais qu'un gestionnaire d'erreurs global personnalisé `WebforjGlobalErrorHandler` est défini, il est utilisé.
- Si aucun des deux n'est trouvé, le `GlobalErrorHandler` par défaut est utilisé.

## L'interface `ErrorHandler` {#the-errorhandler-interface}

L'interface `ErrorHandler` est conçue pour gérer les erreurs qui se produisent pendant l'exécution d'une application webforJ. Les applications qui souhaitent gérer des exceptions spécifiques doivent implémenter cette interface.

### Méthodes {#methods}

- **`onError(Throwable throwable, boolean debug)`** : Appelée lorsqu'une erreur se produit. Cette méthode doit contenir la logique pour gérer l'exception.
- **`showErrorPage(String title, String content)`** : Une méthode par défaut qui affiche la page d'erreur avec le titre et le contenu fournis.

### Convention de nommage {#naming-convention}

La classe implémentante doit être nommée d'après l'exception qu'elle gère, avec le suffixe `ErrorHandler`. Par exemple, pour gérer `NullPointerException`, la classe doit être nommée `NullPointerExceptionErrorHandler`.

### Enregistrement {#registration}

Le gestionnaire d'erreurs personnalisé doit être enregistré dans le fichier `META-INF/services/com.webforj.error.ErrorHandler` afin que webforJ puisse le découvrir et l'utiliser.

## Implémentation d'un gestionnaire d'erreurs personnalisé {#implementing-a-custom-error-handler}

Les étapes suivantes détaillent l'implémentation d'un gestionnaire d'erreurs personnalisé pour une exception spécifique :

### Étape 1 : Créer la classe du gestionnaire d'erreurs {#step-1-create-the-error-handler-class}

Créez une nouvelle classe qui implémente l'interface `ErrorHandler` et est nommée d'après l'exception qu'elle gère.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Logique de gestion personnalisée pour NullPointerException
    String title = "Exception de pointeur nul";
    String content = "Une valeur nulle a été rencontrée là où un objet est requis.";

    showErrorPage(title, content);
  }
}
```

:::info Méthode `showErrorPage()`
La méthode `showErrorPage` est une méthode utilitaire qui utilise l'API webforJ pour envoyer le contenu HTML et le titre de la page fournis au navigateur, affichant une page d'erreur. Lorsque se produit une exception et que l'application n'est pas en mesure de récupérer, il devient impossible d'utiliser les composants webforJ pour construire une page d'erreur personnalisée. Cependant, l'API `Page` reste accessible, permettant au développeur de rediriger ou d'afficher une page d'erreur comme ultime tentative.
:::

### Étape 2 : Enregistrer le gestionnaire d'erreurs {#step-2-register-the-error-handler}

Créez un fichier nommé `com.webforj.error.ErrorHandler` dans le répertoire `META-INF/services` de votre application. Ajoutez le nom complètement qualifié de votre classe de gestionnaire d'erreurs à ce fichier.

**Fichier** : `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Maintenant, chaque fois qu'une `NullPointerException` est lancée, webforJ sélectionne votre gestionnaire enregistré et exécute sa logique pour gérer l'erreur.

## Utilisation de `AutoService` pour simplifier l'enregistrement {#using-autoservice-to-simplify-registration}

Il est facile pour les développeurs d'oublier de mettre à jour ou de spécifier correctement les descripteurs de service. En utilisant `AutoService` de Google, vous pouvez automatiser la génération du fichier `META-INF/services/com.webforj.error.ErrorHandler`. Il vous suffit d'annoter le gestionnaire d'erreurs avec l'annotation `AutoService`. Vous pouvez en savoir plus sur [AutoService ici](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Logique de gestion personnalisée pour NullPointerException
    String title = "Exception de pointeur nul";
    String content = "Une valeur nulle a été rencontrée là où un objet est requis.";

    showErrorPage(title, content);
  }
}
```

## La classe `GlobalErrorHandler` {#the-globalerrorhandler-class}

Le `GlobalErrorHandler` est le gestionnaire d'erreurs par défaut fourni par webforJ. Il implémente l'interface `ErrorHandler` et fournit une gestion des erreurs générique.

### Comportement {#behavior}

- **Journalisation** : Les erreurs sont enregistrées à la fois dans les consoles serveur et navigateur.
- **Affichage de la page d'erreur** : En fonction du mode de débogage, la page d'erreur affiche la trace de la pile ou un message d'erreur générique.

### Définir un gestionnaire d'erreurs global personnalisé {#defining-a-custom-global-error-handler}

Pour définir un gestionnaire d'erreurs global, vous devez créer un nouveau gestionnaire d'erreurs nommé `WebforjGlobalErrorHandler`. Ensuite, suivez [les étapes pour enregistrer les gestionnaires d'erreurs](#step-2-register-the-error-handler) comme expliqué précédemment. Dans ce cas, webforJ cherche d'abord les gestionnaires d'erreurs personnalisés pour gérer les exceptions. Si aucun n'est trouvé, webforJ revient au gestionnaire d'erreurs global personnalisé.

:::info
Si plusieurs `WebforjGlobalErrorHandler` sont enregistrés, alors webforJ sélectionne le premier.
:::
