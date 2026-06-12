---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 1214ec1391242fb6b3ff7f60664a6f79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

Le composant `DateTimeField` permet aux utilisateurs de saisir à la fois une date et une heure dans un seul champ, couvrant l'année, le mois, le jour, les heures et les minutes. Il valide l'entrée pour garantir l'exactitude et peut présenter un sélecteur de date-heure pour faciliter la sélection.

<!-- INTRO_END -->

## Utilisation de `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` prolonge la classe `Field` partagée, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `DateTimeField` étiqueté pour sélectionner une date et une heure de départ.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Usages {#usages}

Le `DateTimeField` est particulièrement utile dans les scénarios où la capture ou l'affichage à la fois de la date **et** de l'heure est essentiel pour votre application. Voici quelques exemples de quand utiliser le `DateTimeField` :

1. **Planification d'événements et calendriers** : Permettre aux utilisateurs de planifier efficacement des événements, de prendre des rendez-vous et de gérer leurs calendriers en leur offrant un seul composant qui leur permet de choisir la date et l'heure.
<!-- vale off -->
2. **Enregistrement et départ** : Faciliter la sélection par l'utilisateur des heures d'enregistrement et de départ lorsqu'une période peut s'étendre sur plusieurs jours.
<!-- vale on -->
3. **Journalisation des données et horodatages** : Utiliser les `DateTimeFields` pour les applications qui impliquent l'enregistrement de la date et de l'heure auxquelles des événements se produisent ou lorsqu'un utilisateur soumet des données.

4. **Gestion des tâches et délais** : Les `DateTimeFields` sont précieux dans les applications qui impliquent la gestion des tâches ou la définition de délais où à la fois la date et l'heure sont pertinentes pour une planification précise.

## Valeur du champ (`LocalDateTime`) {#field-value-localdatetime}

En interne, le composant `DateTimeField` représente sa valeur à l'aide d'un objet `LocalDateTime` du package `java.time`. Cela offre un contrôle précis sur les composants de date et d'heure de l'entrée.

Bien que la valeur **côté client** soit rendue en fonction de la locale du navigateur de l'utilisateur (par exemple, des formats de date et d'heure correspondant aux conventions locales), la valeur **analysée** suit une structure stricte et prévisible : **`yyyy-MM-ddTHH:mm:ss`**.

### Récupération et définition de la valeur {#getting-and-setting-the-value}

Pour récupérer la valeur actuelle, utilisez la méthode `getValue()` :

```java
LocalDateTime value = dateTimeField.getValue();
```

Pour définir programmétiquement la valeur, utilisez la méthode `setValue()` :

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Utilisation de `setText()` {#using-settext}

Si vous préférez définir la valeur via une chaîne brute, elle doit suivre exactement le format `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // valide

dateTimeField.setText("24-04-27T14:30:00"); // invalide
```

:::warning
 Lors de l'utilisation de la méthode `setText()`, une `IllegalArgumentException` sera lancée si le composant ne peut pas analyser l'entrée au format `yyyy-MM-ddTHH:mm:ss`.
:::

## Utilitaires statiques {#static-utilities}

La classe DateTimeField fournit également les méthodes utilitaires statiques suivantes :

- `fromDateTime(String dateTimeAsString)` : Convertit une chaîne de date et d'heure au format `yyyy-MM-ddTHH:mm:ss` en un objet LocalDateTime qui peut ensuite être utilisé avec cette classe ou ailleurs.

- `toDateTime(LocalDateTime dateTime)` : Convertit un objet LocalDateTime en une chaîne de date et d'heure au format `yyyy-MM-ddTHH:mm:ss`.

- `isValidDateTime(String dateTimeAsString)` : Vérifie si la chaîne donnée est une date et une heure valides au format `yyyy-MM-ddTHH:mm:ss`. Cela renverra une valeur booléenne true si c'est le cas, false sinon.

## Valeur min et max {#min-and-max-value}

### La valeur min {#the-min-value}

Si la valeur saisie dans le composant est antérieure à l'horodatage minimum spécifié, le composant échouera la validation des contraintes. Lorsque les valeurs min et max sont toutes les deux définies, la valeur min doit être un horodatage identique ou antérieur à la valeur max.

```java
// Définir l'horodatage minimum autorisé : 1er janvier 2023 à 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### La valeur max {#the-max-value}

Si la valeur saisie dans le composant est postérieure à l'horodatage maximum spécifié, le composant échouera la validation des contraintes. Lorsque les valeurs min et max sont toutes les deux définies, la valeur max doit être un horodatage identique ou postérieur à la valeur min.

```java
// Définir l'horodatage maximum autorisé : 31 décembre 2023 à 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `DateTimeField`, envisagez les meilleures pratiques suivantes :

- **Affichage de date localisé** : La localisation du format de date et l'incorporation des préférences régionales garantissent que les dates sont présentées dans un format familier pour l'utilisateur.

- **Inclure les fuseaux horaires** : Si votre application traite des informations sensibles au temps à travers différents fuseaux horaires, envisagez d'incorporer la sélection du fuseau horaire en complément du champ de date pour garantir une représentation précise de la date-heure.

- **Accessibilité** : Utilisez le `DateTimeField` en tenant compte de l'accessibilité. Assurez-vous qu'il respecte les normes d'accessibilité, comme fournir des étiquettes appropriées et être compatible avec les technologies d'assistance.

- **Auto-remplir la date actuelle** : Envisagez de fournir une option pour pré-remplir la date et l'heure actuelles comme valeur par défaut dans le champ de date-heure, si cela convient au cas d'utilisation de votre application.
