---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 9ccb17716ffd5fa2d32781fc603d5042
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

Le composant `DateTimeField` permet aux utilisateurs de saisir à la fois une date et une heure dans un seul champ, couvrant l'année, le mois, le jour, les heures et les minutes. Il valide les entrées pour leur exactitude et peut présenter un sélecteur de date-heure pour faciliter la sélection.

<!-- INTRO_END -->

## Utilisation de `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` étend la classe partagée `Field`, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `DateTimeField` étiqueté pour sélectionner une date et une heure de départ.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Usages {#usages}

Le `DateTimeField` est particulièrement utile dans les scénarios où la capture ou l'affichage à la fois d'une date **et** d'une heure est essentiel pour votre application. Voici quelques exemples de quand utiliser le `DateTimeField` :

1. **Planification d'événements et calendriers** : Permettez aux utilisateurs de planifier des événements, de réserver des rendez-vous et de gérer leurs calendriers de manière efficace en leur offrant un seul composant leur permettant de choisir la date et l'heure.
<!-- vale off -->
2. **Enregistrement et départ** : Facilitez la sélection par les utilisateurs des heures d'enregistrement et de départ lorsque la période peut s'étendre sur plusieurs jours.
<!-- vale on -->
3. **Journalisation des données et horodatages** : Utilisez les `DateTimeFields` pour les applications qui impliquent l'enregistrement de la date et de l'heure d'occurrence d'événements ou de soumission de données par un utilisateur.

4. **Gestion des tâches et délais** : Les `DateTimeFields` sont précieux dans les applications qui impliquent la gestion des tâches ou la définition de délais où la date et l'heure sont pertinentes pour une planification précise.

## Valeur du champ (`LocalDateTime`) {#field-value-localdatetime}

En interne, le composant `DateTimeField` représente sa valeur à l'aide d'un objet `LocalDateTime` du package `java.time`. Les valeurs sont tronquées aux minutes, donc les secondes et les fractions de seconde sont supprimées.

Le navigateur affiche la date et l'heure en fonction de la locale de l'utilisateur, tandis que la valeur textuelle du champ utilise le format `yyyy-MM-ddTHH:mm`. `LocalDateTime` n'inclut pas de fuseau horaire, et les entrées avec un suffixe de fuseau horaire comme `Z` ou `+02:00` ne sont pas acceptées.

### Obtention et définition de la valeur {#getting-and-setting-the-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDateTime value = dateTimeField.getValue();
```

Pour définir la valeur de manière programmatique, utilisez la méthode `setValue()` :

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30));
```

### Utilisation de `setText()` {#using-settext}

Pour définir la valeur à partir d'une chaîne brute, utilisez `setText()` avec une date-heure locale, comme `yyyy-MM-ddTHH:mm`. Les entrées peuvent également inclure des secondes ou des fractions de secondes, qui sont supprimées.

```java
dateTimeField.setText("2024-04-27T14:30");       // valide
dateTimeField.setText("2024-04-27T14:30:45");    // valide ; les secondes sont supprimées

dateTimeField.setText("24-04-27T14:30");        // invalide ; utilisez une année à quatre chiffres
dateTimeField.setText("2024-04-27T14:30Z");      // invalide ; les suffixes de fuseau horaire ne sont pas acceptés
```

:::warning
Lors de l'utilisation de `setText()`, une `IllegalArgumentException` est lancée si l'entrée ne peut pas être analysée comme une date-heure locale valide. Les entrées avec précision à la minute et les entrées avec secondes sont acceptées, mais les suffixes de fuseau horaire ne le sont pas.
:::

## Utilitaires statiques {#static-utilities}

La classe DateTimeField fournit également les méthodes utilitaires statiques suivantes :

- `fromDateTime(String dateTimeAsString)` : analyse une chaîne de date-heure locale, avec ou sans secondes, en un `LocalDateTime` tronqué aux minutes.

- `toDateTime(LocalDateTime dateTime)` : convertit un `LocalDateTime` en une chaîne au format `yyyy-MM-ddTHH:mm`. Les secondes et les fractions de seconde sont supprimées.

- `isValidDateTime(String dateTimeAsString)` : vérifie si une chaîne est une date-heure locale valide. Les entrées avec secondes ou fractions de secondes sont acceptées. Renvoie `false` pour les entrées invalides ou les suffixes de fuseau horaire.

## Valeur minimale et maximale {#min-and-max-value}

### La valeur minimale {#the-min-value}

Si la valeur saisie dans le composant est antérieure à l'horodatage minimal spécifié, le composant échouera à la validation de contrainte. Lorsque les valeurs minimale et maximale sont définies, la valeur minimale doit être un horodatage identique ou antérieur à la valeur maximale.

```java
// Définir l'horodatage minimal autorisé : 1er janvier 2023 à 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### La valeur maximale {#the-max-value}

Si la valeur saisie dans le composant est postérieure à l'horodatage maximal spécifié, le composant échouera à la validation de contrainte. Lorsque les valeurs minimale et maximale sont définies, la valeur maximale doit être un horodatage identique ou postérieur à la valeur minimale.

```java
// Définir l'horodatage maximal autorisé : 31 décembre 2023 à 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `DateTimeField`, considérez les meilleures pratiques suivantes :

- **Affichage de date localisé** : Localiser le format de date et intégrer les préférences régionales permet de présenter les dates dans un format familier à l'utilisateur.

- **Gérer les fuseaux horaires séparément** : si votre application fonctionne à travers différents fuseaux horaires, fournissez un sélecteur de fuseau horaire séparé et gérez les conversions dans votre application. Le `DateTimeField` ne stocke que la date et l'heure locales.

- **Accessibilité** : Utilisez le `DateTimeField` en tenant compte de l'accessibilité. Assurez-vous qu'il respecte les normes d'accessibilité, telles que la fourniture d'étiquettes appropriées et sa compatibilité avec les technologies d'assistance.

- **Préremplir la date actuelle automatiquement** : envisagez de fournir une option pour préremplir la date et l'heure actuelles comme valeur par défaut dans le champ de date-heure, si cela est approprié pour le cas d'utilisation de votre application.
