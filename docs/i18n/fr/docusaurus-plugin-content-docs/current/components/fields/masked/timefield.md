---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 2631f01d383c134ba92d8ad03f5a57d3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Le `MaskedTimeField` est un champ de texte qui permet aux utilisateurs d'entrer des heures sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque spécifie le format de temps attendu, guidant à la fois l'entrée et l'affichage. Le composant prend en charge l'analyse flexible, la validation, la localisation et la restauration de valeur pour un traitement temporel cohérent.

<!-- INTRO_END -->

## Bases {#basics}

:::tip Vous cherchez une entrée de date ?
Le `MaskedTimeField` est conçu pour une entrée **uniquement temporelle**. Si vous cherchez un composant pour gérer **des dates** avec un formatage basé sur des masques similaire, jetez un œil au [`MaskedDateField`](./datefield.md).
:::

Le `MaskedTimeField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Règles de masque {#mask-rules}

Le `MaskedTimeField` utilise des indicateurs de format pour définir comment le temps est analysé et affiché. Chaque indicateur de format commence par un `%` suivi d'une lettre représentant un composant temporel.

:::tip Application de masques par programmation
Pour formater ou analyser des heures avec la même syntaxe de masque en dehors d'un champ, utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Indicateurs de format temporel {#time-format-indicators}

| Format | Description         |
|--------|---------------------|
| `%H`   | Heure (24 heures)   |
| `%h`   | Heure (12 heures)   |
| `%m`   | Minute              |
| `%s`   | Seconde             |
| `%p`   | AM/PM               |

### Modificateurs {#modifiers}

Les modificateurs affinent l'affichage des composants temporels :

| Modificateur | Description               |
|--------------|---------------------------|
| `z`          | Remplissage par zéro      |
| `s`          | Représentation de texte courte |
| `l`          | Représentation de texte longue  |
| `p`          | Nombre compact            |
| `d`          | Décimal (format par défaut)  |

Cela permet un formatage du temps flexible et adapté aux régions.

## Localisation du formatage temporel {#time-format-localization}

Le `MaskedTimeField` prend en charge la localisation en définissant la locale appropriée. Cela garantit que l'entrée et la sortie temporelles correspondent aux conventions régionales.

```java
field.setLocale(Locale.GERMANY);
```

Cela affecte la façon dont les indicateurs AM/PM sont affichés, comment les séparateurs sont gérés et comment les valeurs sont analysées.

## Logique d'analyse {#parsing-logic}

Le `MaskedTimeField` analyse l'entrée utilisateur en fonction du masque temporel défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des heures valides. Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Hz:%mz` pour heure/minute). Ce format détermine comment les séquences numériques sont interprétées.

### Scénarios d'analyse d'exemple {#example-parsing-scenarios}

| Entrée  | Masque        | Interprété Comme |
|---------|---------------|-------------------|
| `900`   | `%Hz:%mz`     | `09:00`           |
| `1345`  | `%Hz:%mz`     | `13:45`           |
| `0230`  | `%hz:%mz %p`  | `02:30 AM`        |
| `1830`  | `%hz:%mz %p`  | `06:30 PM`        |

## Définir des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage horaire autorisée dans un `MaskedTimeField` à l'aide des méthodes `setMin()` et `setMax()` :

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Les entrées en dehors de la plage définie sont considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedTimeField` inclut une fonctionnalité de restauration qui réinitialise la valeur du champ à un état préalablement défini ou original. Cela peut être utile pour annuler des changements ou revenir à une heure par défaut.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Méthodes pour restaurer la valeur {#ways-to-restore-the-value}

- **Par programmation**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins qu'elle ne soit remplacée par un écouteur d'événements)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client à l'aide d'expressions régulières avec la méthode `setPattern()` :

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Ce motif garantit que seules les valeurs correspondant au format `HH:mm` (deux chiffres, deux-points, deux chiffres) sont considérées comme valables.

:::tip Format d'expression régulière
Le motif doit suivre la syntaxe RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion des entrées
Le champ tente d'analyser et de formater les entrées de temps numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours entrer manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou inanalysable (par exemple `99:99`), elle peut passer les vérifications de motif mais échouer la validation logique.
Vous devez toujours valider la valeur d'entrée dans votre logique d'application, même si un motif d'expression régulière est défini, pour garantir que l'heure est à la fois correctement formatée et significative.
:::

## Sélecteur horaire {#time-picker}

Le `MaskedTimeField` inclut un sélecteur d'heure intégré qui permet aux utilisateurs de sélectionner une heure visuellement, au lieu de la saisir. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont requises.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Accéder au sélecteur {#accessing-the-picker}

Vous pouvez accéder au sélecteur horaire en utilisant `getPicker()` :

```java
TimePicker picker = field.getPicker();
```

### Afficher/masquer l'icône du sélecteur {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour montrer ou masquer l'icône d'horloge à côté du champ :

```java
picker.setIconVisible(true); // montre l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour s'ouvrir automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou utilise les flèches) :

```java
picker.setAutoOpen(true);
```

:::tip Forcer la sélection via le sélecteur
Pour vous assurer que les utilisateurs ne peuvent sélectionner une heure qu'à l'aide du sélecteur (et ne pas en saisir une manuellement), combinez les deux paramètres suivants :

```java
field.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
field.setAllowCustomValue(false);    // Désactive l'entrée de texte manuelle
```

Cette configuration garantit que toutes les saisies d'heures passent par l'interface du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse des saisies manuelles.
:::

### Ouvrir manuellement le sélecteur {#manually-open-the-picker}

Pour ouvrir le sélecteur d'heure par programmation :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // identique à open()
```

### Définir l'étape du sélecteur {#setting-the-picker-step}

Vous pouvez définir l'intervalle entre les heures sélectionnables dans le sélecteur en utilisant `setStep()`. Cela vous permet de contrôler la granularité des options temporelles—idéal pour des scénarios comme la planification par blocs de 15 minutes.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Contrainte d'étape
L'étape doit diviser uniformément une heure ou une journée entière. Sinon, une exception sera levée.
:::

Cela garantit que la liste déroulante contient des valeurs prévisibles et régulièrement espacées comme `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Le `MaskedTimeFieldSpinner` étend le [`MaskedTimeField`](#basics) en ajoutant des contrôles de type spinner qui permettent aux utilisateurs d'incrémenter ou de décrémenter l'heure à l'aide des flèches ou des boutons de l'interface utilisateur. Il fournit un style d'interaction plus guidé, particulièrement utile dans les applications de style bureau.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Caractéristiques clés {#key-features}

- **Pas de temps interactif :**
  Utilisez les flèches ou les boutons de rotation pour incrémenter ou décrémenter la valeur temporelle.

- **Unité de rotation personnalisable :**
  Choisissez quelle partie du temps modifier en utilisant `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Les options incluent `HOUR`, `MINUTE`, `SECOND`, et `MILLISECOND`.

- **Limites Min/Max :**
  Hérite du soutien pour les heures minimum et maximum autorisées en utilisant `setMin()` et `setMax()`.

- **Sortie formatée :**
  Entièrement compatible avec les masques et les paramètres de localisation du `MaskedTimeField`.

### Exemple : Configurer l'étape par heure {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Style {#styling}

<TableBuilder name="MaskedTimeField" />
