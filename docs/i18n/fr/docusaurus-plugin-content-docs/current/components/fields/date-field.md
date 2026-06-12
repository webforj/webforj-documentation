---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: a996ccdd786de35de1dece0a5fc8f27a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

Le composant `DateField` permet aux utilisateurs d'entrer ou de sélectionner une date par année, mois et jour. Il gère automatiquement la validation, de sorte que les dates mal formatées sont détectées avant la soumission du formulaire.

<!-- INTRO_END -->

## Utilisation de `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` étend la classe partagée `Field`, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée des DateFields de départ et de retour qui restent synchronisés, avec des contraintes min et max pour limiter la plage sélectionnable.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Valeur de champ (`LocalDate`) {#field-value-localdate}

Le composant `DateField` stocke sa valeur en interne sous la forme d'un objet `LocalDate`, représentant une date sans information de temps ou de fuseau horaire. Cela permet une gestion précise des entrées basées sur le calendrier à travers différents systèmes.

:::info Valeur affichée VS valeur analysée 
Bien que la **valeur affichée** s'adapte à la langue du navigateur de l'utilisateur, garantissant un formatage familier régional (par exemple, `MM/JJ/AAAA` aux États-Unis ou `JJ.MM.AAAA` en Europe), la **valeur analysée** repose toujours sur le format fixe `aaaa-MM-jj`.
:::

### Obtenir et définir la valeur `LocalDate` {#getting-and-setting-the-localdate-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDate value = dateField.getValue();
```

Pour définir la valeur par programmation, utilisez la méthode `setValue()` :

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Utilisation de `setText()` {#using-settext}

Vous pouvez également attribuer une valeur à l'aide d'une chaîne brute, mais elle doit suivre le format exact `aaaa-MM-jj` :

```java
dateField.setText("2024-04-27"); // valide

dateField.setText("04/27/2024"); // invalide
```

:::warning
 Lors de l'utilisation de la méthode `setText()`, une `IllegalArgumentException` sera levée si le composant ne peut pas analyser l'entrée au format `aaaa-MM-jj`.
:::

## Usages {#usages}

Le `DateField` est idéal pour choisir et afficher des dates dans votre application. Voici quelques exemples de quand utiliser le `DateField` :

1. **Planification d'événements et Calendriers** : Les champs de date sont essentiels dans les applications qui impliquent la planification d'événements, la réservation de rendez-vous ou le suivi de dates importantes.

2. **Entrées de formulaire** : Simplifiez le processus de sélection de date pour un utilisateur remplissant un formulaire qui exige une date, comme un anniversaire.

3. **Systèmes de réservation** : Les applications qui impliquent des systèmes de réservation nécessitent souvent que les utilisateurs saisissent des dates spécifiques. Un champ de date rationalise le processus et garantit une sélection précise des dates.

4. **Gestion des tâches et Dates limites** : Les champs de date sont précieux dans les applications impliquant la gestion des tâches ou la définition de délais. Les utilisateurs peuvent facilement spécifier des dates d'échéance, des dates de début ou d'autres informations sensibles au temps.

## Valeur minimale et maximale {#min-and-max-value}

### La valeur minimale {#the-min-value}
La méthode `setMin()` définit la date la plus ancienne qu'un utilisateur peut entrer dans le composant. Si l'entrée est antérieure à la minimale spécifiée, elle échouera à la validation des contraintes. Lorsqu'elle est utilisée en même temps que `setMax()`, la minimale doit être une date qui est la même que ou antérieure à la maximale.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimum autorisé : 1er janvier 2023
```

### La valeur maximale {#the-max-value}
La méthode `setMax()` définit la date la plus récente que le composant accepte. Si la date saisie est postérieure à la maximale spécifiée, l'entrée est invalide. Lorsque les deux valeurs sont définies, la maximale doit être une date qui est la même que ou postérieure à la minimale.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximum autorisé : 31 décembre 2023
```

## Utilitaires statiques {#static-utilities}

La classe `DateField` fournit également les méthodes utilitaires statiques suivantes :

- `fromDate(String dateAsString)`: Convertit une chaîne de date au format `aaaa-MM-jj` en un objet `LocalDate` qui peut ensuite être utilisé avec ce champ, ou ailleurs.

- `toDate(LocalDate date)`: Convertit un objet `LocalDate` en une chaîne de date au format `aaaa-MM-jj`.

- `isValidDate(String dateAsString)`: Vérifie si la chaîne donnée est une date valide au format `aaaa-MM-jj`.

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `DateField`, considérez les meilleures pratiques suivantes :

- **Accessibilité** : Utilisez des étiquettes appropriées pour garantir que les utilisateurs avec des technologies d'assistance peuvent facilement naviguer et utiliser les champs de date dans votre application.

- **Auto-remplir la date actuelle** : Si cela est approprié pour le cas d'utilisation de votre application, auto-remplissez le champ de date avec la date actuelle.
