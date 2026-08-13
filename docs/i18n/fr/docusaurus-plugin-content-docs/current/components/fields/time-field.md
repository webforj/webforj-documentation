---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9688647e85d453578ccd59934e52e26b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` est un composant d'interface utilisateur qui permet aux utilisateurs d'entrer ou de sélectionner des heures en heures, minutes et, éventuellement, secondes. Il fournit un moyen intuitif et efficace de gérer les informations liées au temps dans diverses applications.

<!-- INTRO_END -->

## Utilisation du `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` étend la classe `Field` partagée, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `TimeField` de rappel initialisé à l'heure actuelle.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Utilisations {#usages}

Le `TimeField` est idéal pour choisir et afficher des heures dans votre application. Voici quelques exemples d'utilisation du `TimeField` :

1. **Planification d'événements** : Les champs horaires sont essentiels dans les applications qui impliquent la programmation d'heures pour des événements, des rendez-vous ou des réunions.

2. **Suivi et journalisation du temps** : Les applications qui suivent le temps, comme les feuilles de temps, nécessitent des champs horaires pour des entrées précises.

3. **Rappels et alarmes** : Utiliser un champ horaire simplifie le processus de saisie pour les utilisateurs définissant des rappels ou des alarmes dans votre application.

## Valeur minimale et maximale {#min-and-max-value}

Avec les méthodes `setMin()` et `setMax()`, vous pouvez spécifier une plage d'heures acceptables.

- **Pour `setMin()`** : Si la valeur saisie dans le composant est antérieure à l'heure minimale spécifiée, le composant échouera à la validation des contraintes. Lorsque les valeurs min et max sont toutes deux définies, la valeur minimale doit être une heure identique ou antérieure à la valeur maximale.

- **Pour `setMax()`** : Si la valeur saisie dans le composant est postérieure à l'heure maximale spécifiée, le composant échouera à la validation des contraintes. Lorsque les valeurs min et max sont toutes deux définies, la valeur maximale doit être une heure identique ou postérieure à la valeur minimale.

## Gestion et localisation des valeurs {#value-handling-and-localization}

Le composant `TimeField` représente en interne sa valeur à l'aide d'un objet `LocalTime` du paquet `java.time`. Cela permet aux développeurs d'interagir avec des valeurs horaires précises, quelle que soit leur représentation visuelle.

Bien que le **composant côté client affiche le temps selon la locale du navigateur de l'utilisateur**, le format analysé et stocké est toujours standardisé sous `HH:mm:ss`.

Si vous définissez une valeur de chaîne brute, utilisez la méthode `setText()` avec précaution :

```java
timeField.setText("09:15:00"); // valide
```

:::warning
 Lors de l'utilisation de la méthode `setText()`, une `IllegalArgumentException` sera lancée si le composant ne peut pas analyser l'entrée dans le format `HH:mm:ss`.
:::


:::info Interface Picker
L'apparence de l'interface utilisateur du sélecteur horaire dépend non seulement de la locale sélectionnée mais aussi du navigateur et du système d'exploitation utilisés. Cela garantit une cohérence automatique avec l'interface que les utilisateurs connaissant déjà.
:::

## Utilitaires statiques {#static-utilities}

La classe `TimeField` fournit également les méthodes utilitaires statiques suivantes :

- `fromTime(String timeAsString)`: Convertit une chaîne horaire au format HH:mm:ss en un objet LocalTime qui peut ensuite être utilisé avec cette classe ou ailleurs.

- `toTime(LocalTime time)`: Convertit un LocalTime en une chaîne horaire au format HH:mm:ss.

- `isValidTime(String timeAsString)`: Vérifie si la chaîne donnée est une heure valide au format HH:mm:ss. Cela renverra une valeur booléenne vraie si c'est le cas, fausse sinon.

## Meilleures pratiques {#best-practices}

- **Fournir des exemples clairs de format d'heure** : Montrez clairement aux utilisateurs le format horaire attendu près du `TimeField`. Utilisez des exemples ou des espaces réservés pour les aider à entrer l'heure correctement. Si possible, affichez le format d'heure en fonction de l'emplacement de l'utilisateur.

- **Accessibilité** : Utilisez le composant `TimeField` en tenant compte de l'accessibilité, en vous assurant qu'il respecte les normes d'accessibilité telles que la fourniture d'étiquettes appropriées, un contraste de couleurs suffisant et la compatibilité avec les technologies d'assistance.

- **Option de réinitialisation** : Offrez un moyen aux utilisateurs de vider facilement le `TimeField` pour le ramener à un état vide ou par défaut.
