---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: 6421e3007af8e795adefa317a13363f0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` est un composant d'interface utilisateur qui permet aux utilisateurs de saisir ou de sélectionner des heures, des minutes et, de manière optionnelle, des secondes. Il fournit un moyen intuitif et efficace de gérer des informations liées au temps dans diverses applications.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages {#usages}

Le `TimeField` est idéal pour choisir et afficher des heures dans votre application. Voici quelques exemples de situations où utiliser le `TimeField` :

1. **Planification d'événements** : Les champs horaires sont essentiels dans les applications qui impliquent de définir des heures pour des événements, des rendez-vous ou des réunions.

2. **Suivi et journalisation du temps** : Les applications qui suivent le temps, comme les feuilles de temps, ont besoin de champs horaires pour des saisies précises.

3. **Rappels et alarmes** : L'utilisation d'un champ horaire simplifie le processus de saisie pour les utilisateurs qui définissent des rappels ou des alarmes dans votre application.

## Valeur minimale et maximale {#min-and-max-value}

Avec les méthodes `setMin()` et `setMax()`, vous pouvez spécifier une plage de temps acceptables.

- **Pour `setMin()`** : Si la valeur saisie dans le composant est antérieure à l'heure minimale spécifiée, le composant échouera la validation de contrainte. Lorsque les valeurs minimales et maximales sont définies, la valeur minimale doit être une heure qui est la même ou antérieure à la valeur maximale.

- **Pour `setMax()`** : Si la valeur saisie dans le composant est postérieure à l'heure maximale spécifiée, le composant échouera la validation de contrainte. Lorsque les valeurs minimales et maximales sont définies, la valeur maximale doit être une heure qui est la même ou postérieure à la valeur minimale. 

## Gestion des valeurs et localisation {#value-handling-and-localization}

En interne, le composant `TimeField` représente sa valeur à l'aide d'un objet `LocalTime` du package `java.time`. Cela permet aux développeurs d'interagir avec des valeurs temporelles précises, peu importe comment elles sont affichées.

Bien que le **composant côté client affiche l'heure en fonction de la langue du navigateur de l'utilisateur**, le format analysé et enregistré est toujours standardisé sous `HH:mm:ss`.

Si vous définissez une valeur de chaîne brute, utilisez la méthode `setText()` avec précaution :

```java
timeField.setText("09:15:00"); // valide
```

:::warning
 Lorsque vous utilisez la méthode `setText()`, une `IllegalArgumentException` sera levée si le composant ne peut pas analyser l'entrée au format `HH:mm:ss`.
:::


:::info Interface utilisateur du sélecteur 
L'apparence de l'interface utilisateur du sélecteur horaire dépend non seulement de la langue sélectionnée mais aussi du navigateur et du système d'exploitation utilisés. Cela garantit une cohérence automatique avec l'interface que les utilisateurs connaissent déjà.
:::

## Utilitaires statiques {#static-utilities}

La classe `TimeField` fournit également les méthodes utilitaires statiques suivantes :

- `fromTime(String timeAsString)` : Convertir une chaîne horaire au format HH:mm:ss en un objet LocalTime qui peut ensuite être utilisé avec cette classe ou ailleurs.

- `toTime(LocalTime time)` : Convertir un LocalTime en une chaîne horaire au format HH:mm:ss.

- `isValidTime(String timeAsString)` : Vérifiez si la chaîne donnée est une heure valide au format HH:mm:ss. Cela renverra une valeur booléenne vraie si c'est le cas, fausse sinon.

## Meilleures pratiques {#best-practices}

- **Fournir des exemples clairs de format d'heure** : Montrez clairement aux utilisateurs le format d'heure attendu près du `TimeField`. Utilisez des exemples ou des espaces réservés pour les aider à saisir correctement l'heure. Si possible, affichez le format d'heure en fonction de la position de l'utilisateur.

- **Accessibilité** : Utilisez le composant `TimeField` en gardant à l'esprit l'accessibilité, en veillant à ce qu'il respecte les normes d'accessibilité telles que fournir des étiquettes appropriées, un contraste de couleur suffisant, et une compatibilité avec les technologies d'assistance.

- **Option de réinitialisation** : Fournissez un moyen pour les utilisateurs de vider facilement le `TimeField` pour un état vide ou par défaut.
