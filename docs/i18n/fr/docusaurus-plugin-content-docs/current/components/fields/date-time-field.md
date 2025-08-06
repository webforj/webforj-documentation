---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: 70f471320621b40dc1bb4170e4cbf752
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

Le composant `DateTimeField` est conçu pour permettre aux utilisateurs d'entrer à la fois une date et une heure. Cela comprend la spécification de l'année, du mois et du jour, ainsi que l'heure en heures et minutes. Il offre aux utilisateurs la possibilité de valider leur saisie pour en garantir l'exactitude ou d'utiliser une interface de sélection de date-heure dédiée pour simplifier le processus de sélection.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usages {#usages}

Le `DateTimeField` est mieux utilisé dans des scénarios où la capture ou l'affichage à la fois de la date **et** de l'heure est essentiel pour votre application. Voici quelques exemples de quand utiliser le `DateTimeField` :

1. **Planification d'événements et calendriers** : Permettez aux utilisateurs de planifier efficacement des événements, de réserver des rendez-vous et de gérer leurs calendriers en leur fournissant un seul composant qui leur permet de choisir la date et l'heure.
<!-- vale off -->
2. **Enregistrement et départ** : Facilitez la sélection par l'utilisateur des heures d'enregistrement et de départ lorsque la période peut s'étendre sur plusieurs jours.
<!-- vale on -->
3. **Journalisation de données et horodatages** : Utilisez les `DateTimeFields` pour des applications impliquant l'enregistrement de la date et de l'heure des événements ou des moments où un utilisateur soumet des données.

4. **Gestion des tâches et délais** : Les `DateTimeFields` sont précieux dans les applications qui impliquent la gestion des tâches ou la définition de délais où à la fois la date et l'heure sont pertinentes pour un planificateur précis.

## Valeur du champ (`LocalDateTime`) {#field-value-localdatetime}

En interne, le composant `DateTimeField` représente sa valeur à l'aide d'un objet `LocalDateTime` du package `java.time`. Cela permet un contrôle précis sur les composants de la date et de l'heure de l'entrée.

Bien que la valeur **côté client** soit rendue en fonction de la locale du navigateur de l'utilisateur (par exemple, formats de date et d'heure correspondant aux conventions locales), la valeur **analysée** suit une structure stricte et prévisible : **`yyyy-MM-ddTHH:mm:ss`**.

### Obtenir et définir la valeur {#getting-and-setting-the-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDateTime value = dateTimeField.getValue();
```

Pour définir programmatique la valeur, utilisez la méthode `setValue()` :

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Utiliser `setText()` {#using-settext}

Si vous préférez définir la valeur via une chaîne brute, elle doit suivre le format exact de `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // valide

dateTimeField.setText("24-04-27T14:30:00"); // invalide
```

:::warning
 Lors de l'utilisation de la méthode `setText()`, une `IllegalArgumentException` sera levée si le composant ne peut pas analyser l'entrée au format `yyyy-MM-ddTHH:mm:ss`.
:::

## Utilitaires statiques {#static-utilities}

La classe DateTimeField fournit également les méthodes utilitaires statiques suivantes :

- `fromDateTime(String dateTimeAsString)` : Convertir une chaîne de date et d'heure au format `yyyy-MM-ddTHH:mm:ss` en un objet LocalDateTime qui peut ensuite être utilisé avec cette classe, ou ailleurs.

- `toDateTime(LocalDateTime dateTime)` : Convertir un objet LocalDateTime en une chaîne de date et d'heure au format `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)` : Vérifie si la chaîne donnée est une date et heure valide au format `yyyy-MM-ddTHH:mm:ss`. Cela renverra une valeur booléenne true si oui, false sinon.

## Valeur min et max {#min-and-max-value}

### La valeur min {#the-min-value}

Si la valeur saisie dans le composant est antérieure à l'horodatage minimum spécifié, le composant échouera à la validation des contraintes. Lorsque les valeurs min et max sont définies, la valeur min doit être un horodatage qui est le même ou antérieur à la valeur max.

```java
// Définir l'horodatage minimum autorisé : 1er janvier 2023 à 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### La valeur max {#the-max-value}

Si la valeur saisie dans le composant est postérieure à l'horodatage maximum spécifié, le composant échouera à la validation des contraintes. Lorsque les valeurs min et max sont définies, la valeur max doit être un horodatage qui est le même ou postérieur à la valeur min.

```java
// Définir l'horodatage maximum autorisé : 31 décembre 2023 à 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Meilleures pratiques {#best-practices}

Pour assurer une expérience utilisateur optimale lors de l'utilisation du composant `DateTimeField`, considérez les meilleures pratiques suivantes :

- **Affichage des dates localisées** : La localisation du format de date et l'incorporation des préférences régionales garantissent que les dates sont présentées dans un format familier à l'utilisateur.

- **Inclure les fuseaux horaires** : Si votre application traite des informations sensibles au temps à travers différents fuseaux horaires, envisagez d'incorporer la sélection du fuseau horaire en plus du champ de date pour garantir une représentation précise de la date-heure.

- **Accessibilité** : Utilisez le `DateTimeField` en gardant à l'esprit l'accessibilité. Assurez-vous qu'il respecte les normes d'accessibilité, telles que la fourniture d'étiquettes appropriées et la compatibilité avec les technologies d'assistance.

- **Auto-remplir la date actuelle** : Envisagez de fournir une option pour auto-remplir la date et l'heure actuelles comme valeur par défaut dans le champ de date-heure, si approprié pour le cas d'utilisation de votre application.
