---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: ee9981c57d9964a3f759b116dbd75af2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

Le `DateField` est un composant de champ qui permet aux utilisateurs d'entrer ou de sélectionner des dates par année, mois et jour. Il fournit un moyen intuitif et efficace de gérer les informations liées aux dates dans diverses applications et offre la flexibilité de valider l'entrée d'un utilisateur.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Valeur du champ (`LocalDate`) {#field-value-localdate}

Le composant `DateField` stocke sa valeur en interne en tant qu'objet `LocalDate`, représentant une date sans information d'heure ou de fuseau horaire. Cela permet de gérer avec précision les entrées basées sur le calendrier dans différents systèmes.

:::info Valeur affichée VS valeur analysée 
Alors que la **valeur affichée** s'adapte à la locale du navigateur de l'utilisateur, garantissant un format familièrement régional (par exemple, `MM/JJ/YYYY` aux États-Unis ou `JJ.MM.YYYY` en Europe), la **valeur analysée** repose toujours sur le format fixe de `yyyy-MM-dd`.
:::

### Récupérer et définir la valeur `LocalDate` {#getting-and-setting-the-localdate-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDate value = dateField.getValue();
```

Pour définir la valeur de manière programmatique, utilisez la méthode `setValue()` :

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Utilisation de `setText()` {#using-settext}

Vous pouvez également assigner une valeur en utilisant une chaîne brute, mais elle doit suivre le format exact `yyyy-MM-dd` :

```java
dateField.setText("2024-04-27"); // valide

dateField.setText("04/27/2024"); // invalide
```

:::warning
 Lors de l'utilisation de la méthode `setText()`, une `IllegalArgumentException` sera levée si le composant ne peut pas analyser l'entrée au format `yyyy-MM-dd`.
:::

## Usages {#usages}

Le `DateField` est idéal pour choisir et afficher des dates dans votre application. Voici quelques exemples de quand utiliser le `DateField` :

1. **Planification d'événements et calendriers** : Les champs de date sont essentiels dans les applications qui impliquent la planification d'événements, la réservation de rendez-vous ou le suivi de dates importantes.

2. **Entrées de formulaire** : Simplifiez le processus de sélection de date pour un utilisateur remplissant un formulaire qui nécessite une date, comme un anniversaire.

3. **Systèmes de réservation et de réservation** : Les applications qui impliquent des systèmes de réservation nécessitent souvent que les utilisateurs saisissent des dates spécifiques. Un champ de date accélère le processus et garantit une sélection précise des dates.

4. **Gestion de tâches et délais** : Les champs de date sont précieux dans les applications impliquant la gestion des tâches ou l'établissement de délais. Les utilisateurs peuvent facilement spécifier des dates d'échéance, des dates de début ou d'autres informations sensibles au temps.

## Valeur minimale et maximale {#min-and-max-value}

### La valeur minimale {#the-min-value}
La méthode `setMin()` définit la date la plus ancienne qu'un utilisateur peut entrer dans le composant. Si l'entrée est antérieure à la minimum spécifié, cela échouera à la validation des contraintes. Lorsqu'elle est utilisée avec `setMax()`, la minimum doit être une date qui est la même ou antérieure à la maximum.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimum autorisé : 1er janvier 2023
```

### La valeur maximale {#the-max-value}
La méthode `setMax()` définit la dernière date que le composant accepte. Si la date saisie est postérieure à la maximum spécifié, l'entrée est invalide. Lorsque les deux valeurs sont définies, la maximum doit être une date qui est la même ou postérieure à la minimum.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximum autorisé : 31 décembre 2023
```

## Utilitaires statiques {#static-utilities}

La classe `DateField` fournit également les méthodes utilitaires statiques suivantes :

- `fromDate(String dateAsString)` : Convertit une chaîne de date au format `yyyy-MM-dd` en un objet `LocalDate` qui peut ensuite être utilisé avec ce champ, ou ailleurs.

- `toDate(LocalDate date)` : Convertit un objet `LocalDate` en une chaîne de date au format `yyyy-MM-dd`.

- `isValidDate(String dateAsString)` : Vérifie si la chaîne donnée est une date valide au format `yyyy-MM-dd`.

## Meilleures pratiques {#best-practices}

Pour assurer une expérience utilisateur optimale lors de l'utilisation du composant `DateField`, considérez les meilleures pratiques suivantes :

- **Accessibilité** : Utilisez des étiquettes appropriées pour garantir que les utilisateurs avec des technologies d'assistance peuvent facilement naviguer vers et utiliser les champs de date dans votre application.

- **Auto-compléter la date actuelle** : Si cela convient au cas d'utilisation de votre application, auto-complétez le champ de date avec la date actuelle.
