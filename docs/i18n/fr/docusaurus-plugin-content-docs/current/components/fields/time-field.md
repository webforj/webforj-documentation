---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9b4c187f1c86207e13b38812f0eb7e6c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` est un composant d'interface utilisateur qui permet aux utilisateurs d'entrer ou de sélectionner des heures avec une précision d'heure et de minute. Si des secondes sont fournies, le composant les ignore.

<!-- INTRO_END -->

## Utilisation du `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` étend la classe `Field` partagée, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `TimeField` de rappel initialisé à l'heure actuelle.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Usages {#usages}

Le `TimeField` est idéal pour choisir et afficher des heures dans votre application. Voici quelques exemples de quand utiliser le `TimeField` :

1. **Planification d'événements** : Les champs de temps sont essentiels dans les applications qui impliquent la définition des heures pour des événements, des rendez-vous ou des réunions.

2. **Suivi et enregistrement du temps** : Les applications qui suivent le temps, comme les feuilles de temps, ont besoin de champs de temps pour des entrées précises.

3. **Rappels et alarmes** : L'utilisation d'un champ de temps simplifie le processus d'entrée pour les utilisateurs qui définissent des rappels ou des alarmes dans votre application.

## Valeur minimale et maximale {#min-and-max-value}

Avec les méthodes `setMin()` et `setMax()`, vous pouvez spécifier une plage d'heures acceptables.

- **Pour `setMin()`** : Si la valeur saisie dans le composant est antérieure à l'heure minimale spécifiée, le composant échouera à la validation des contraintes. Lorsque les valeurs minimale et maximale sont définies, la valeur minimale doit être une heure identique ou antérieure à la valeur maximale.

- **Pour `setMax()`** : Si la valeur saisie dans le composant est postérieure à l'heure maximale spécifiée, le composant échouera à la validation des contraintes. Lorsque les valeurs minimale et maximale sont définies, la valeur maximale doit être une heure identique ou postérieure à la valeur minimale.

## Gestion des valeurs et localisation {#value-handling-and-localization}

En interne, le composant `TimeField` représente sa valeur à l'aide d'un objet `LocalTime` du package `java.time`. Cela permet aux développeurs d'interagir avec des valeurs horaires précises, quel que soit leur rendu visuel.

Le navigateur détermine comment le sélecteur affiche l'heure pour la locale de l'utilisateur. La valeur textuelle du champ utilise le format 24 heures `HH:mm`, et sa valeur `LocalTime` est tronquée aux minutes.

Si vous définissez une valeur de chaîne brute, utilisez la méthode `setText()` avec précaution :

```java
timeField.setText("09:15");    // valide
timeField.setText("09:15:30"); // également valide ; les secondes sont ignorées, laissant 09:15
```

:::warning
Lorsque vous utilisez `setText()`, une `IllegalArgumentException` est levée si l'entrée ne peut pas être analysée comme une heure valide. Les entrées `HH:mm` et `HH:mm:ss` sont acceptées, mais les secondes sont ignorées.
:::


:::info Interface utilisateur du sélecteur
L'apparence de l'interface utilisateur du sélecteur de temps dépend de la locale sélectionnée, du navigateur et du système d'exploitation. Cela crée une cohérence automatique avec l'interface dont les utilisateurs sont déjà familiers.
:::

## Utilitaires statiques {#static-utilities}

La classe `TimeField` fournit également les méthodes utilitaires statiques suivantes :

- `fromTime(String timeAsString)` : Analyse une chaîne de temps, avec ou sans secondes, en une `LocalTime` tronquée aux minutes.

- `toTime(LocalTime time)` : Convertit un `LocalTime` en une chaîne au format `HH:mm`, en ignorant les secondes.

- `isValidTime(String timeAsString)` : Vérifie si une chaîne horaire est valide, y compris les entrées `HH:mm` et `HH:mm:ss`. Renvoie `true` si valide et `false` sinon.

## Meilleures pratiques {#best-practices}

- **Fournir des exemples clairs de format horaire** : Montrez clairement aux utilisateurs le format horaire attendu près du `TimeField`. Utilisez des exemples ou des espaces réservés pour les aider à entrer l'heure correctement. Si possible, affichez le format horaire en fonction de la localisation de l'utilisateur.

- **Accessibilité** : Utilisez le composant `TimeField` en tenant compte de l'accessibilité, en respectant les normes d'accessibilité telles que des étiquettes appropriées, un contraste de couleur suffisant et une compatibilité avec les technologies d'assistance.

- **Option de réinitialisation** : Offrez aux utilisateurs un moyen facile de réinitialiser le `TimeField` à un état vide ou par défaut.
