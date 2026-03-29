---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: e90e93f7db172a33b2ce205bfd6a6b3c
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
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Utilisations {#usages}

Le `DateTimeField` est idéal dans les situations où la capture ou l'affichage à la fois de la date **et** de l'heure est essentiel pour votre application. Voici quelques exemples de quand utiliser le `DateTimeField` :

1. **Planification d'événements et calendriers** : Permettez aux utilisateurs de planifier efficacement des événements, de prendre des rendez-vous et de gérer leurs calendriers en leur offrant un seul composant qui leur permet de choisir la date et l'heure.
<!-- vale off -->
2. **Enregistrement et départ** : Facilitez la sélection des heures d'enregistrement et de départ lorsque la période peut s'étendre sur plusieurs jours.
<!-- vale on -->
3. **Journalisation des données et horodatages** : Utilisez des `DateTimeField` pour les applications qui impliquent l'enregistrement de la date et de l'heure des événements ou lorsque l'utilisateur soumet des données.

4. **Gestion des tâches et délais** : Les `DateTimeField` sont précieux dans les applications qui impliquent la gestion des tâches ou la définition de délais où à la fois la date et l'heure sont pertinentes pour un planning précis.

## Valeur du champ (`LocalDateTime`) {#field-value-localdatetime}

En interne, le composant `DateTimeField` représente sa valeur à l'aide d'un objet `LocalDateTime` du package `java.time`. Cela permet un contrôle précis sur les composants de date et d'heure de l'entrée.

Alors que la valeur **côté client** est rendue en fonction de la locale du navigateur de l'utilisateur (par exemple, les formats de date et d'heure qui correspondent aux conventions locales), la valeur **analysée** suit une structure stricte et prévisible : **`yyyy-MM-ddTHH:mm:ss`**.

### Obtention et définition de la valeur {#getting-and-setting-the-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDateTime value = dateTimeField.getValue();
```

Pour définir la valeur de manière programmatique, utilisez la méthode `setValue()` :

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Utilisation de `setText()` {#using-settext}

Si vous préférez définir la valeur via une chaîne brute, elle doit suivre le format exact de `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // valide

dateTimeField.setText("24-04-27T14:30:00"); // invalide
```

:::warning
 Lorsque vous utilisez la méthode `setText()`, une `IllegalArgumentException` sera lancée si le composant ne peut pas analyser l'entrée dans le format `yyyy-MM-ddTHH:mm:ss`.
:::

## Utilitaires statiques {#static-utilities}

La classe DateTimeField fournit également les méthodes utilitaires statiques suivantes :

- `fromDateTime(String dateTimeAsString)` : Convertit une chaîne de date et d'heure au format `yyyy-MM-ddTHH:mm:ss` en un objet LocalDateTime qui peut ensuite être utilisé avec cette classe, ou ailleurs.

- `toDateTime(LocalDateTime dateTime)` : Convertit un objet LocalDateTime en une chaîne de date et d'heure au format `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)` : Vérifie si la chaîne donnée est une date et une heure valide au format `yyyy-MM-ddTHH:mm:ss`. Cette méthode renverra une valeur booléenne vraie si c'est le cas, sinon faux.

## Valeur minimale et maximale {#min-and-max-value}

### La valeur minimale {#the-min-value}

Si la valeur saisie dans le composant est antérieure à l'horodatage minimum spécifié, le composant échouera la validation de contrainte. Lorsque les valeurs minimale et maximale sont définies, la valeur minimale doit être un horodatage égal ou antérieur à la valeur maximale.

```java
// Définir l'horodatage minimum autorisé : 1er janvier 2023 à 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### La valeur maximale {#the-max-value}

Si la valeur saisie dans le composant est postérieure à l'horodatage maximum spécifié, le composant échouera la validation de contrainte. Lorsque les valeurs Minimale et maximale sont définies, la valeur maximale doit être un horodatage égal ou ultérieur à la valeur minimale.

```java
// Définir l'horodatage maximum autorisé : 31 décembre 2023 à 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Bonnes pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `DateTimeField`, prenez en compte les bonnes pratiques suivantes :

- **Affichage de date localisé** : La localisation du format de date et l'incorporation des préférences régionales garantissent que les dates sont présentées dans un format familier à l'utilisateur.

- **Inclure les fuseaux horaires** : Si votre application traite des informations sensibles au temps à travers différents fuseaux horaires, envisagez d'incorporer la sélection de fuseau horaire en même temps que le champ de date pour garantir une représentation précise de la date-heure.

- **Accessibilité** : Utilisez le `DateTimeField` avec accessibilité à l'esprit. Assurez-vous qu'il respecte les normes d'accessibilité, telles que la fourniture d'étiquettes appropriées et la compatibilité avec les technologies d'assistance.

- **Remplissage automatique de la date actuelle** : Envisagez de fournir une option pour remplir automatiquement la date et l'heure actuelles comme valeur par défaut dans le champ de date-heure, si cela est approprié pour le cas d'utilisation de votre application.
