---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: 173c4a1d080dc6e0c01828131af61c08
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

Le composant `DateField` permet aux utilisateurs de saisir ou de sélectionner une date par année, mois et jour. Il gère la validation automatiquement, de sorte que les dates mal formatées soient interceptées avant la soumission du formulaire.

<!-- INTRO_END -->

## Utilisation de `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` étend la classe `Field` partagée, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée des DateFields de départ et de retour qui restent synchronisés, avec des contraintes min et max pour limiter la plage sélectionnable.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Valeur du champ (`LocalDate`) {#field-value-localdate}

Le composant `DateField` stocke sa valeur en interne sous la forme d'un objet `LocalDate`, représentant une date sans information de temps ou de fuseau horaire. Cela permet de gérer de manière précise les entrées basées sur le calendrier à travers différents systèmes.

:::info Valeur affichée VS valeur analysée
Bien que la **valeur affichée** s'adapte à la locale du navigateur de l'utilisateur, garantissant un formatage familier pour la région (par exemple, `MM/DD/YYYY` aux États-Unis ou `DD.MM.YYYY` en Europe), la **valeur analysée** repose toujours sur le format fixe `yyyy-MM-dd`.
:::

### Obtention et définition de la valeur `LocalDate` {#getting-and-setting-the-localdate-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDate value = dateField.getValue();
```

Pour définir la valeur par programme, utilisez la méthode `setValue()` :

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Utilisation de `setText()` {#using-settext}

Vous pouvez également attribuer une valeur en utilisant une chaîne brute, mais elle doit suivre le format exact `yyyy-MM-dd` :

```java
dateField.setText("2024-04-27"); // valide

dateField.setText("04/27/2024"); // invalide
```

:::warning
 Lorsque vous utilisez la méthode `setText()`, une `IllegalArgumentException` sera lancée si le composant ne peut pas analyser l'entrée au format `yyyy-MM-dd`.
:::

## Usages {#usages}

Le `DateField` est idéal pour choisir et afficher des dates dans votre application. Voici quelques exemples de quand utiliser le `DateField` :

1. **Planification d'événements et calendriers** : Les champs de date sont essentiels dans les applications qui impliquent la planification d'événements, la réservation de rendez-vous ou le suivi des dates importantes.

2. **Entrées de formulaire** : Simplifiez le processus de sélection de dates pour un utilisateur remplissant un formulaire qui nécessite une date, comme un anniversaire.

3. **Systèmes de réservation** : Les applications qui impliquent des systèmes de réservation nécessitent souvent que les utilisateurs saisissent des dates spécifiques. Un champ de date rationalise le processus et garantit une sélection de date précise.

4. **Gestion des tâches et délais** : Les champs de date sont précieux dans les applications qui impliquent la gestion des tâches ou la définition de délais. Les utilisateurs peuvent facilement spécifier des dates d'échéance, des dates de début ou d'autres informations sensibles au temps.

## Valeur min et max {#min-and-max-value}

### La valeur min {#the-min-value}
La méthode `setMin()` définit la date la plus ancienne qu'un utilisateur peut saisir dans le composant. Si l'entrée est antérieure à la minimum spécifié, elle échouera à la validation de contrainte. Lorsqu'elle est utilisée avec `setMax()`, la valeur minimale doit être une date égale ou antérieure à la valeur maximale.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimum autorisé : 1er janvier 2023
```

### La valeur max {#the-max-value}
La méthode `setMax()` définit la date la plus récente acceptée par le composant. Si la date saisie est ultérieure à la valeur maximum spécifiée, l'entrée est invalide. Lorsque les deux valeurs sont définies, la valeur maximale doit être une date égale ou postérieure à la valeur minimale.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximum autorisé : 31 décembre 2023
```

## Utilitaires statiques {#static-utilities}

La classe `DateField` fournit également les méthodes utilitaires statiques suivantes :

- `fromDate(String dateAsString)`: Convertit une chaîne de date au format `yyyy-MM-dd` en un objet `LocalDate` qui peut ensuite être utilisé avec ce champ, ou ailleurs.

- `toDate(LocalDate date)`: Convertit un objet `LocalDate` en une chaîne de date au format `yyyy-MM-dd`.

- `isValidDate(String dateAsString)`: Vérifie si la chaîne donnée est une date valide au format `yyyy-MM-dd`.

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `DateField`, considérez les meilleures pratiques suivantes :

- **Accessibilité** : Utilisez des étiquettes appropriées pour garantir que les utilisateurs avec des technologies d'assistance peuvent facilement naviguer et utiliser les champs de date dans votre application.

- **Auto-compléter la date actuelle** : Si cela est approprié pour le cas d'utilisation de votre application, auto-remplissez le champ de date avec la date actuelle.
