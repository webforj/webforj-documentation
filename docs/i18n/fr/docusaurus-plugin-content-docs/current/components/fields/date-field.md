---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: 9f7f8e2c82305667ea1ace187df17915
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

Le `DateField` est un composant de champ qui permet aux utilisateurs de saisir ou de sélectionner des dates par année, mois et jour. Il fournit une manière intuitive et efficace de gérer les informations liées aux dates dans diverses applications et offre la flexibilité de valider l'entrée d'un utilisateur.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Valeur du champ (`LocalDate`) {#field-value-localdate}

Le composant `DateField` stocke sa valeur en interne sous la forme d'un objet `LocalDate`, représentant une date sans informations de temps ou de fuseau horaire. Cela permet une gestion précise des entrées basées sur le calendrier à travers différents systèmes.

:::info Valeur affichée VS valeur analysée 
Bien que la **valeur affichée** s'adapte à la locale du navigateur de l'utilisateur, garantissant un formatage familier régionalement (par exemple, `MM/DD/YYYY` aux États-Unis ou `DD.MM.YYYY` en Europe), la **valeur analysée** repose toujours sur le format fixe de `yyyy-MM-dd`.
:::

### Récupération et définition de la valeur `LocalDate` {#getting-and-setting-the-localdate-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDate value = dateField.getValue();
```

Pour définir la valeur de manière programmatique, utilisez la méthode `setValue()` :

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

## Utilisations {#usages}

Le `DateField` est idéal pour choisir et afficher des dates dans votre application. Voici quelques exemples de quand utiliser le `DateField` :

1. **Planification d'événements et calendriers** : Les champs de date sont essentiels dans les applications impliquant la planification d'événements, la réservation de rendez-vous, ou le suivi des dates importantes.

2. **Entrées de formulaires** : Simplifiez le processus de sélection de date pour un utilisateur remplissant un formulaire nécessitant une date, comme un anniversaire.

3. **Systèmes de réservation** : Les applications impliquant des systèmes de réservation exigent souvent que les utilisateurs saisissent des dates spécifiques. Un champ de date rationalise le processus et assure une sélection précise des dates.

4. **Gestion des tâches et délais** : Les champs de date sont précieux dans les applications impliquant la gestion des tâches ou la définition de délais. Les utilisateurs peuvent facilement spécifier des dates d'échéance, des dates de début, ou d'autres informations sensibles au temps.

## Valeur minimale et maximale {#min-and-max-value}

### La valeur minimale {#the-min-value}
La méthode `setMin()` définit la date la plus précoce qu'un utilisateur peut entrer dans le composant. Si l'entrée est antérieure à la minimum spécifié, la validation de contrainte échouera. Lorsqu'elle est utilisée avec `setMax()`, la valeur minimale doit être une date égale ou antérieure à la valeur maximale.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimum autorisé : 1er janvier 2023
```

### La valeur maximale {#the-max-value}
La méthode `setMax()` définit la date la plus tardive que le composant accepte. Si la date saisie est postérieure à la maximum spécifiée, l'entrée est invalide. Lorsque les deux valeurs sont définies, la valeur maximale doit être une date égale ou postérieure à la valeur minimale.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximum autorisé : 31 décembre 2023
```

## Utilitaires statiques {#static-utilities}

La classe `DateField` fournit également les méthodes utilitaires statiques suivantes :

- `fromDate(String dateAsString)` : Convertir une chaîne de date au format `yyyy-MM-dd` en un objet `LocalDate` qui peut ensuite être utilisé avec ce champ ou ailleurs.

- `toDate(LocalDate date)` : Convertir un objet `LocalDate` en une chaîne de date au format `yyyy-MM-dd`.

- `isValidDate(String dateAsString)` : Vérifie si la chaîne donnée est une date valide au format `yyyy-MM-dd`.

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `DateField`, considérez les meilleures pratiques suivantes :

- **Accessibilité** : Utilisez des étiquettes appropriées pour garantir que les utilisateurs des technologies d'assistance peuvent facilement naviguer vers et utiliser les champs de date dans votre application.

- **Auto-remplir la date actuelle** : Si cela convient au cas d'utilisation de votre application, remplissez automatiquement le champ de date avec la date actuelle.
