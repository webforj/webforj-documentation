---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: aa5cbd6fb54c91be419380eeaf26e65b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` est un composant d'interface utilisateur qui permet aux utilisateurs d'entrer ou de sélectionner des heures, des minutes et, éventuellement, des secondes. Il fournit une méthode intuitive et efficace pour gérer les informations liées au temps dans diverses applications.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages {#usages}

Le `TimeField` est idéal pour choisir et afficher des heures dans votre application. Voici quelques exemples d'utilisation du `TimeField` :

1. **Planification d'événements** : Les champs de temps sont essentiels dans les applications qui impliquent la définition d'heures pour des événements, des rendez-vous ou des réunions.

2. **Suivi et journalisation du temps** : Les applications qui suivent le temps, telles que les feuilles de temps, nécessitent des champs de temps pour des entrées précises.

3. **Rappels et alarmes** : L'utilisation d'un champ de temps simplifie le processus d'entrée pour les utilisateurs définissant des rappels ou des alarmes dans votre application.

## Valeur minimale et maximale {#min-and-max-value}

Avec les méthodes `setMin()` et `setMax()`, vous pouvez spécifier une plage d'heures acceptables.

- **Pour `setMin()`** : Si la valeur entrée dans le composant est antérieure à l'heure minimale spécifiée, le composant échouera la validation de contrainte. Lorsque les valeurs min et max sont définies, la valeur min doit être une heure identique ou antérieure à la valeur max.

- **Pour `setMax()`** : Si la valeur entrée dans le composant est postérieure à l'heure maximale spécifiée, le composant échouera la validation de contrainte. Lorsque les valeurs min et max sont définies, la valeur max doit être une heure identique ou postérieure à la valeur min.

## Traitement des valeurs et localisation {#value-handling-and-localization}

En interne, le composant `TimeField` représente sa valeur à l'aide d'un objet `LocalTime` du package `java.time`. Cela permet aux développeurs d'interagir avec des valeurs temporelles précises, peu importe comment elles sont rendues visuellement.

Bien que le **composant côté client affiche l'heure en utilisant la locale du navigateur de l'utilisateur**, le format analysé et stocké est toujours standardisé en tant que `HH:mm:ss`.

Lors de la définition d'une valeur de chaîne brute, utilisez la méthode `setText()` avec précaution :

```java
timeField.setText("09:15:00"); // valide
```

:::warning
 Lors de l'utilisation de la méthode `setText()`, une `IllegalArgumentException` sera lancée si le composant ne peut pas analyser l'entrée au format `HH:mm:ss`.
:::

:::info Interface utilisateur du sélecteur
L'apparence de l'interface d'entrée du sélecteur de temps dépend non seulement de la locale sélectionnée, mais également du navigateur et du système d'exploitation utilisés. Cela assure une cohérence automatique avec l'interface que les utilisateurs connaissent déjà.
:::

## Utilitaires statiques {#static-utilities}

La classe `TimeField` fournit également les méthodes utilitaires statiques suivantes :

- `fromTime(String timeAsString)` : Convertit une chaîne horaire au format HH:mm:ss en un objet LocalTime qui peut ensuite être utilisé avec cette classe ou ailleurs.

- `toTime(LocalTime time)` : Convertit un LocalTime en une chaîne horaire au format HH:mm:ss.

- `isValidTime(String timeAsString)` : Vérifie si la chaîne donnée est une heure valide au format HH:mm:ss. Cela retournera une valeur booléenne vraie si c'est le cas, fausse sinon.

## Meilleures pratiques {#best-practices}

- **Fournir des exemples clairs de format d'heure** : Montrez clairement aux utilisateurs le format d'heure attendu près du `TimeField`. Utilisez des exemples ou des espaces réservés pour les aider à entrer l'heure correctement. Si possible, affichez le format d'heure en fonction de l'emplacement de l'utilisateur.

- **Accessibilité** : Utilisez le composant `TimeField` en tenant compte de l'accessibilité, en veillant à ce qu'il respecte les normes d'accessibilité telles que la fourniture de bonnes étiquettes, un contraste de couleur suffisant et une compatibilité avec les technologies d'assistance.

- **Option de réinitialisation** : Fournissez un moyen pour les utilisateurs de facilement effacer le `TimeField` pour le ramener à un état vide ou par défaut.
