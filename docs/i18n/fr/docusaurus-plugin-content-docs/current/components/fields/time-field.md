---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 994cad91e2870d59f3c0eec7c2b47141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` est un composant d'interface utilisateur qui permet aux utilisateurs de saisir ou de sélectionner des heures, des minutes et, éventuellement, des secondes. Il offre un moyen intuitif et efficace de gérer les informations liées au temps dans diverses applications.

<!-- INTRO_END -->

## Utilisation du `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` étend la classe partagée `Field`, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `TimeField` de rappel initialisé à l'heure actuelle.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Utilisations {#usages}

Le `TimeField` est idéal pour choisir et afficher des heures dans votre application. Voici quelques exemples de situations où utiliser le `TimeField` :

1. **Planification d'événements** : Les champs de temps sont essentiels dans les applications qui impliquent la définition d'heures pour des événements, des rendez-vous ou des réunions.

2. **Suivi et journalisation du temps** : Les applications qui suivent le temps, comme les feuilles de temps, ont besoin de champs de temps pour des saisies précises.

3. **Rappels et alarmes** : Utiliser un champ de temps simplifie le processus de saisie pour les utilisateurs qui définissent des rappels ou des alarmes dans votre application.

## Valeur minimale et maximale {#min-and-max-value}

Avec les méthodes `setMin()` et `setMax()`, vous pouvez spécifier une plage d'heures acceptables.

- **Pour `setMin()`** : Si la valeur saisie dans le composant est antérieure à l'heure minimale spécifiée, le composant échouera à la validation des contraintes. Lorsque les valeurs minimales et maximales sont toutes deux définies, la valeur minimale doit être une heure égale ou antérieure à la valeur maximale.

- **Pour `setMax()`** : Si la valeur saisie dans le composant est postérieure à l'heure maximale spécifiée, le composant échouera à la validation des contraintes. Lorsque les valeurs minimales et maximales sont toutes deux définies, la valeur maximale doit être une heure égale ou postérieure à la valeur minimale.

## Gestion des valeurs et localisation {#value-handling-and-localization}

En interne, le composant `TimeField` représente sa valeur à l'aide d'un objet `LocalTime` du package `java.time`. Cela permet aux développeurs d'interagir avec des valeurs temporelles précises, quelle que soit leur représentation visuelle.

Alors que **le composant côté client affiche l'heure selon la locale du navigateur de l'utilisateur**, le format analysé et stocké est toujours standardisé en tant que `HH:mm:ss`.

Si vous définissez une valeur de chaîne brute, utilisez la méthode `setText()` avec précaution :

```java
timeField.setText("09:15:00"); // valide
```

:::warning
 Lorsque vous utilisez la méthode `setText()`, une `IllegalArgumentException` sera lancée si le composant ne peut pas analyser l'entrée au format `HH:mm:ss`.
:::


:::info Interface de sélection 
L'apparence de l'interface de sélection d'heures dépend non seulement de la locale sélectionnée mais aussi du navigateur et du système d'exploitation utilisés. Cela garantit une cohérence automatique avec l'interface à laquelle les utilisateurs sont déjà familiers.
:::

## Utilitaires statiques {#static-utilities}

La classe `TimeField` fournit également les méthodes utilitaires statiques suivantes :

- `fromTime(String timeAsString)` : Convertir une chaîne de temps au format HH:mm:ss en un objet LocalTime qui peut ensuite être utilisé avec cette classe ou ailleurs.

- `toTime(LocalTime time)` : Convertir un LocalTime en une chaîne de temps au format HH:mm:ss.

- `isValidTime(String timeAsString)` : Vérifiez si la chaîne donnée est une heure valide au format HH:mm:ss. Cela retournera une valeur booléenne vraie si oui, fausse sinon.

## Meilleures pratiques {#best-practices}

- **Fournir des exemples clairs de format d'heure** : Montrez clairement aux utilisateurs le format d'heure attendu près du `TimeField`. Utilisez des exemples ou des espaces réservés pour les aider à saisir l'heure correctement. Si possible, affichez le format d'heure en fonction de la localisation de l'utilisateur.

- **Accessibilité** : Utilisez le composant `TimeField` en pensant à l'accessibilité, en veillant à ce qu'il respecte les normes d'accessibilité telles que la fourniture d'étiquettes appropriées, un contraste de couleurs suffisant et la compatibilité avec les technologies d'assistance.

- **Option de réinitialisation** : Fournissez un moyen permettant aux utilisateurs de nettoyer facilement le `TimeField` à un état vide ou par défaut.
