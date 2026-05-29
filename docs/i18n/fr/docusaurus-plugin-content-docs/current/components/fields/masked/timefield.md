---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 97e5bc068e72cfd770c26fed4ceca434
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Le `MaskedTimeField` est un champ de saisie de texte qui permet aux utilisateurs d'entrer des heures sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque précise le format d'heure attendu, guidant à la fois l'entrée et l'affichage. Le composant prend en charge le parsing flexible, la validation, la localisation et la restauration de valeur pour une gestion cohérente du temps.

<!-- INTRO_END -->

## Basics {#basics}

:::tip Vous recherchez une entrée de date ?
Le `MaskedTimeField` est conçu pour une entrée **uniquement horaire**. Si vous recherchez un composant pour gérer des **dates** avec un formatage basé sur un masque similaire, jetez un œil au [`MaskedDateField`](./datefield.md).
:::

Le `MaskedTimeField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un texte d'espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Mask rules {#mask-rules}

Le `MaskedTimeField` utilise des indicateurs de format pour définir comment l'heure est analysée et affichée. Chaque indicateur de format commence par un `%` suivi d'une lettre qui représente un composant temporel.

:::tip Application de masques par programmation
Pour formater ou analyser des heures avec la même syntaxe de masque en dehors d'un champ, utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Time format indicators {#time-format-indicators}

| Format | Description         |
|--------|---------------------|
| `%H`   | Heure (format 24 heures)      |
| `%h`   | Heure (format 12 heures)      |
| `%m`   | Minute              |
| `%s`   | Seconde             |
| `%p`   | AM/PM               |

### Modifiers {#modifiers}

Les modificateurs affinent l'affichage des composants temporels :

| Modificateur | Description               |
|--------------|---------------------------|
| `z`          | Remplissage par des zéros |
| `s`          | Représentation courte      |
| `l`          | Représentation longue      |
| `p`          | Nombre compressé           |
| `d`          | Décimal (format par défaut)|

Ceci permet une mise en forme horaire flexible et adaptée aux cultures locales.

## Time format localization {#time-format-localization}

Le `MaskedTimeField` prend en charge la localisation en définissant la locale appropriée. Cela garantit que l'entrée et la sortie de l'heure correspondent aux conventions régionales.

```java
field.setLocale(Locale.GERMANY);
```

Cela affecte la manière dont les indicateurs AM/PM sont affichés, comment les séparateurs sont gérés et comment les valeurs sont analysées.

## Parsing logic {#parsing-logic}

Le `MaskedTimeField` analyse l'entrée utilisateur en fonction du masque horaire défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une entrée flexible tout en garantissant des heures valides. Le comportement d'analyse dépend de l'ordre des formats défini par le masque (par exemple, `%Hz:%mz` pour l'heure/la minute). Ce format détermine comment les séquences numériques sont interprétées.

### Example parsing scenarios {#example-parsing-scenarios}

| Entrée  | Masque          | Interprété Comme|
|---------|-----------------|------------------|
| `900`   | `%Hz:%mz`       | `09:00`          |
| `1345`  | `%Hz:%mz`       | `13:45`          |
| `0230`  | `%hz:%mz %p`    | `02:30 AM`       |
| `1830`  | `%hz:%mz %p`    | `06:30 PM`       |

## Setting min/max constraints {#setting-minmax-constraints}

Vous pouvez restreindre la plage horaire autorisée dans un `MaskedTimeField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Les entrées en dehors de la plage définie sont considérées comme invalides.

## Restoring the value {#restoring-the-value}

Le `MaskedTimeField` comprend une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini ou original. Cela peut être utile pour annuler des modifications ou revenir à une heure par défaut.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Par programmation**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si remplacée par un écouteur d'événements)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validation patterns {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client en utilisant des expressions régulières avec la méthode `setPattern()` :

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Ce motif garantit que seules les valeurs correspondant au format `HH:mm` (deux chiffres, deux points, deux chiffres) sont considérées comme valides.

:::tip Format d'expression régulière
Le motif doit suivre la syntaxe RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur le traitement des entrées
Le champ tente d'analyser et de formatter les entrées horaires numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours entrer manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou inanalysable (par exemple, `99:99`), elle peut passer les vérifications de motif mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans votre logique d'application, même si un motif d'expression régulière est défini, pour garantir que l'heure est à la fois correctement formatée et significative.
:::

## Time picker {#time-picker}

Le `MaskedTimeField` comprend un sélecteur de temps intégré qui permet aux utilisateurs de sélectionner une heure visuellement, plutôt que de la taper. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des saisies précises sont requises.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Accessing the picker {#accessing-the-picker}

Vous pouvez accéder au sélecteur de temps en utilisant `getPicker()` :

```java
TimePicker picker = field.getPicker();
```

### Show/hide the picker icon {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou masquer l'icône d'horloge à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Auto-open behavior {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour s'ouvrir automatiquement lorsque l'utilisateur interagit avec le champ (par exemple en cliquant, en appuyant sur Entrée ou sur les touches fléchées) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs peuvent uniquement sélectionner une heure à l'aide du sélecteur (et non en tapant manuellement), combinez les deux paramètres suivants :

```java
field.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction utilisateur
field.setAllowCustomValue(false);    // Désactive la saisie de texte manuel
```

Cette configuration garantit que toute saisie horaire provient de l'interface du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse liés à la saisie de texte.
:::

### Manually open the picker {#manually-open-the-picker}

Pour ouvrir le sélecteur de temps par programmation :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // équivalent à open()
```

### Setting the picker step {#setting-the-picker-step}

Vous pouvez définir l'intervalle entre les heures sélectionnables dans le sélecteur en utilisant `setStep()`. Cela vous permet de contrôler la granularité des options horaires, idéal pour des scénarios tels que la planification par intervalles de 15 minutes.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Contraintes de pas
Le pas doit diviser uniformément une heure ou une journée complète. Sinon, une exception sera levée.
:::

Cela garantit que la liste déroulante contient des valeurs prévisibles et espacées régulièrement, comme `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Le `MaskedTimeFieldSpinner` étend le [`MaskedTimeField`](#basics) en ajoutant des commandes de sélection qui permettent aux utilisateurs d'incrémenter ou de décrémenter l'heure à l'aide des touches fléchées ou des boutons de l'interface utilisateur. Il offre un style d'interaction plus guidé, particulièrement utile dans les applications de style bureau.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Key features {#key-features}

- **Pas horaire interactif :**  
  Utilisez les touches fléchées ou les boutons de sélection pour incrémenter ou décrémenter la valeur horaire.

- **Unité de sélection personnalisable :**  
  Choisissez quelle partie de l'heure modifier à l'aide de `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Les options comprennent `HOUR`, `MINUTE`, `SECOND`, et `MILLISECOND`.

- **Bordures Min/Max :**  
  Hérite de la prise en charge des heures minimales et maximales autorisées en utilisant `setMin()` et `setMax()`.

- **Sortie formatée :**  
  Entièrement compatible avec les masques et les paramètres de localisation du `MaskedTimeField`.

### Example: Configure stepping by hour {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
