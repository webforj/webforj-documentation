---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: ca6e544259fc218b59cebd14d34e4530
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` est un composant d'interface utilisateur qui permet aux utilisateurs d'entrer ou de sélectionner des heures, des minutes et, optionnellement, des secondes. Il fournit un moyen intuitif et efficace de gérer des informations liées au temps dans diverses applications.

<!-- INTRO_END -->

## Utilisation du `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` étend la classe `Field` partagée, qui offre des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `TimeField` de rappel initialisé à l'heure actuelle.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Usages {#usages}

Le `TimeField` est idéal pour choisir et afficher des heures dans votre application. Voici quelques exemples de lorsque utiliser le `TimeField` :

1. **Planification d'Événements** : Les champs horaires sont essentiels dans les applications impliquant la définition d'heures pour des événements, des rendez-vous ou des réunions.

2. **Suivi et Journalisation du Temps** : Les applications qui suivent le temps, comme les feuilles de temps, ont besoin de champs horaires pour des entrées précises.

3. **Rappels et Alarmes** : Utiliser un champ horaire simplifie le processus de saisie pour les utilisateurs qui définissent des rappels ou des alarmes dans votre application.

## Valeur min et max {#min-and-max-value}

Avec les méthodes `setMin()` et `setMax()`, vous pouvez spécifier une plage d'heures acceptables.

- **Pour `setMin()`** : Si la valeur saisie dans le composant est antérieure à l'heure minimale spécifiée, le composant échouera à la validation de contrainte. Lorsque les valeurs min et max sont définies, la valeur min doit être une heure identique ou antérieure à la valeur max.

- **Pour `setMax()`** : Si la valeur saisie dans le composant est postérieure à l'heure maximale spécifiée, le composant échouera à la validation de contrainte. Lorsque les valeurs min et max sont définies, la valeur max doit être une heure identique ou postérieure à la valeur min.

## Gestion des valeurs et localisation {#value-handling-and-localization}

En interne, le composant `TimeField` représente sa valeur en utilisant un objet `LocalTime` du paquet `java.time`. Cela permet aux développeurs d'interagir avec des valeurs temporelles précises, peu importe comment elles sont rendues visuellement.

Bien que **le composant côté client affiche l'heure en utilisant la locale du navigateur de l'utilisateur**, le format analysé et stocké est toujours standardisé sous la forme `HH:mm:ss`.

En cas de définition d'une valeur de chaîne brute, utilisez la méthode `setText()` avec prudence :

```java
timeField.setText("09:15:00"); // valide
```

:::warning
Lorsque vous utilisez la méthode `setText()`, une `IllegalArgumentException` sera levée si le composant ne peut pas analyser l'entrée au format `HH:mm:ss`.
:::


:::info Interface Picker 
L'apparence de l'interface de saisie d'heure dépend non seulement de la locale sélectionnée, mais aussi du navigateur et du système d'exploitation utilisés. Cela garantit une cohérence automatique avec l'interface que les utilisateurs connaissent déjà.
:::

## Utilitaires statiques {#static-utilities}

La classe `TimeField` fournit également les méthodes utilitaires statiques suivantes :

- `fromTime(String timeAsString)` : Convertit une chaîne horaire au format HH:mm:ss en un objet LocalTime qui peut ensuite être utilisé avec cette classe, ou ailleurs.

- `toTime(LocalTime time)` : Convertit un LocalTime en une chaîne horaire au format HH:mm:ss.

- `isValidTime(String timeAsString)` : Vérifie si la chaîne donnée est une heure HH:mm:ss valide. Cela retournera une valeur booléenne vraie si c'est le cas, sinon fausse.

## Meilleures pratiques {#best-practices}

- **Fournir des Exemples Clairs de Format d'Heure** : Montrez clairement aux utilisateurs le format d'heure attendu près du `TimeField`. Utilisez des exemples ou des espaces réservés pour les aider à entrer l'heure correctement. Si possible, affichez le format d'heure en fonction de la localisation de l'utilisateur.

- **Accessibilité** : Utilisez le composant `TimeField` en gardant à l'esprit l'accessibilité, en vous assurant qu'il respecte les normes d'accessibilité telles que la fourniture d'étiquettes appropriées, un contraste de couleur suffisant et une compatibilité avec les technologies d'assistance.

- **Option de Réinitialisation** : Fournissez un moyen pour les utilisateurs de vider facilement le `TimeField` à un état vide ou par défaut.
