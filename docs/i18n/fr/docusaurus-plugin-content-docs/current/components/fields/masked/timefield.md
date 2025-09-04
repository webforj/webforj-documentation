---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: e50a52f19876f98eec1bd825ca82cd6a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Le `MaskedTimeField` est un contrôle de saisie de texte conçu pour une saisie précise et structurée du temps. Il permet aux utilisateurs d'entrer des heures sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque est une chaîne qui spécifie le format d'heure attendu, guidant à la fois l'entrée et l'affichage.

Ce composant prend en charge l'analyse flexible, la validation, la localisation et la restauration des valeurs. Il est particulièrement utile dans des formulaires sensibles au temps, comme des calendriers, des feuilles de temps et des réservations.

:::tip Vous cherchez une saisie de date ?
Le `MaskedTimeField` est conçu pour une saisie **uniquement de temps**. Si vous cherchez un composant pour gérer des **dates** avec un formatage basé sur des masques similaire, jetez un œil au [`MaskedDateField`](./datefield.md).
:::

## Basics {#basics}

Le `MaskedTimeField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Mask rules {#mask-rules}

Le `MaskedTimeField` utilise des indicateurs de format pour définir comment le temps est analysé et affiché. Chaque indicateur de format commence par un `%` suivi d'une lettre qui représente un composant de temps.

### Time format indicators {#time-format-indicators}

| Format | Description         |
|--------|---------------------|
| `%H`   | Heure (24 heures)      |
| `%h`   | Heure (12 heures)      |
| `%m`   | Minute              |
| `%s`   | Seconde              |
| `%p`   | AM/PM               |

### Modifiers {#modifiers}

Les modificateurs affinent l'affichage des composants de temps :

| Modificateur | Description               |
|--------------|---------------------------|
| `z`          | Remplissage avec des zéros |
| `s`          | Représentation textuelle courte |
| `l`          | Représentation textuelle longue  |
| `p`          | Nombre compact             |
| `d`          | Décimal (format par défaut)  |

Ceux-ci permettent un formatage du temps flexible et adapté à la localisation.

## Time format localization {#time-format-localization}

Le `MaskedTimeField` prend en charge la localisation en définissant la locale appropriée. Cela garantit que l'entrée et la sortie du temps correspondent aux conventions régionales.

```java
field.setLocale(Locale.GERMANY);
```

Cela affecte la façon dont les indicateurs AM/PM sont affichés, comment les séparateurs sont gérés et comment les valeurs sont analysées.

## Parsing logic {#parsing-logic}

Le `MaskedTimeField` analyse l'entrée utilisateur sur la base du masque de temps défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des heures valides. 
Le comportement d'analyse dépend de l'ordre du format défini par le masque (par exemple, `%Hz:%mz` pour heure/minute). Ce format détermine comment les séquences numériques sont interprétées.

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

Le `MaskedTimeField` inclut une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini ou d'origine. Cela peut être utile pour annuler des modifications ou revenir à une heure par défaut.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **De manière programmatique**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si remplacée par un écouteur d'événements)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validation patterns {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client en utilisant des expressions régulières avec la méthode `setPattern()` :

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Ce motif garantit que seules les valeurs correspondant au format `HH:mm` (deux chiffres, deux-points, deux chiffres) sont considérées comme valides.

:::tip Format d'Expression Régulière
Le motif doit suivre la syntaxe des expressions régulières JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur le traitement des entrées
Le champ tente de parser et de formater les entrées temporelles numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours entrer manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou incompréhensible (par exemple `99:99`), elle peut passer les vérifications de motif mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans la logique de votre application, même si un motif d'expression régulière est défini, pour garantir que le temps est à la fois correctement formaté et significatif.
:::

## Time picker {#time-picker}

Le `MaskedTimeField` comprend un sélecteur de temps intégré qui permet aux utilisateurs de sélectionner une heure visuellement, plutôt que de taper. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont nécessaires.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Accessing the picker {#accessing-the-picker}

Vous pouvez accéder au sélecteur de temps en utilisant `getPicker()` :

```java
TimePicker picker = field.getPicker();
```

### Show/hide the picker icon {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou masquer l'icône de l'horloge à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Auto-open behavior {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou sur les flèches) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une heure qu'à l'aide du sélecteur (et non en tapant manuellement), combinez les deux paramètres suivants :

```java
field.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
field.setAllowCustomValue(false);    // Désactive la saisie de texte manuelle
```

Cette configuration garantit que toutes les saisies d'heure proviennent de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes de parsing des entrées tapées.
:::

### Manually open the picker {#manually-open-the-picker}

Pour ouvrir le sélecteur de temps de manière programmatique :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // même effet que open()
```

### Setting the picker step {#setting-the-picker-step}

Vous pouvez définir l'intervalle entre les heures sélectionnables dans le sélecteur en utilisant `setStep()`. Cela vous permet de contrôler la granularité des options horaires—idéal pour des scénarios tels que la planification par tranches de 15 minutes.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Contrainte de pas
Le pas doit diviser également une heure ou une journée entière. Sinon, une exception sera levée.
:::

Cela garantit que la liste déroulante contient des valeurs prévisibles et uniformément espacées telles que `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Le `MaskedTimeFieldSpinner` étend [`MaskedTimeField`](#basics) en ajoutant des contrôles de molette qui permettent aux utilisateurs d'augmenter ou de diminuer le temps en utilisant les flèches ou des boutons de l'interface utilisateur. Il fournit un style d'interaction plus guidé, particulièrement utile dans les applications de bureau.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Key features {#key-features}

- **Pas de Temps Interactif :**  
  Utilisez les flèches ou les boutons de rotation pour augmenter ou diminuer la valeur temporelle.

- **Unité de Rotation Personnalisable :**  
  Choisissez quelle partie du temps modifier en utilisant `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Les options incluent `HOUR`, `MINUTE`, `SECOND` et `MILLISECOND`.

- **Limites Min/Max :**  
  Hérite de la prise en charge des heures minimales et maximales autorisées à l'aide de `setMin()` et `setMax()`.

- **Sortie Formatée :**  
  Complètement compatible avec les masques et les paramètres de localisation de `MaskedTimeField`.

### Example: Configure stepping by hour {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
