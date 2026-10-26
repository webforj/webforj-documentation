---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 07256952a84572a67b1fe2b66dd5b5a5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Le `MaskedTimeField` est un champ de texte qui permet aux utilisateurs d'entrer des heures sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque précise le format horaire attendu, guidant ainsi l'entrée et l'affichage. Le composant prend en charge l'analyse flexible, la validation, la localisation et la restauration des valeurs pour une gestion cohérente du temps.

<!-- INTRO_END -->

:::tip Vous cherchez un champ de date ?
Le `MaskedTimeField` est conçu pour une saisie **d'heure uniquement**. Si vous cherchez un composant pour gérer des **dates** avec un formatage masqué similaire, jetez un œil au [`MaskedDateField`](/docs/components/fields/masked/datefield).
:::

Le `MaskedTimeField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, un label, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Règles de masque {#mask-rules}

Le `MaskedTimeField` utilise des indicateurs de format pour définir comment le temps est analysé et affiché. Chaque indicateur de format commence par un `%` suivi d'une lettre qui représente un composant horaire.

:::tip Application de masques par programme
Pour formater ou analyser des heures avec la même syntaxe de masque en dehors d'un champ, utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Indicateurs de format horaire {#time-format-indicators}

| Format | Description         |
|--------|---------------------|
| `%H`   | Heure (24 heures)   |
| `%h`   | Heure (12 heures)   |
| `%m`   | Minute              |
| `%s`   | Seconde             |
| `%p`   | AM/PM               |

### Modificateurs {#modifiers}

Les modificateurs précisent l'affichage des composants horaires :

| Modificateur | Description               |
|--------------|---------------------------|
| `z`          | Remplissage par zéro      |
| `s`          | Représentation courte      |
| `l`          | Représentation longue      |
| `p`          | Nombre compressé           |
| `d`          | Décimal (format par défaut)|

Cela permet un formatage horaire flexible et adapté aux régions.

## Localisation du format horaire {#time-format-localization}

Le `MaskedTimeField` prend en charge la localisation en définissant la locale appropriée. Cela garantit que l'entrée et la sortie d'heure correspondent aux conventions régionales.

```java
field.setLocale(Locale.GERMANY);
```

Cela affecte l'affichage des indicateurs AM/PM, la gestion des séparateurs et la façon dont les valeurs sont analysées.

## Logique d'analyse {#parsing-logic}

Le `MaskedTimeField` analyse l'entrée de l'utilisateur en fonction du masque horaire défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant la validité des heures. Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Hz:%mz` pour heure/minute). Ce format détermine comment les séquences numériques sont interprétées.

### Scénarios d'analyse d'exemple {#example-parsing-scenarios}

| Entrée  | Masque        | Interprété Comme|
|---------|---------------|------------------|
| `900`   | `%Hz:%mz`     | `09:00`          |
| `1345`  | `%Hz:%mz`     | `13:45`          |
| `0230`  | `%hz:%mz %p`  | `02:30 AM`       |
| `1830`  | `%hz:%mz %p`  | `06:30 PM`       |

## Définition des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage horaire autorisée dans un `MaskedTimeField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Les entrées en dehors de la plage définie sont considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedTimeField` comprend une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini ou d'origine. Cela peut être utile pour annuler des changements ou revenir à une heure par défaut.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Méthodes pour restaurer la valeur {#ways-to-restore-the-value}

- **Par programme**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si remplacée par un écouteur d'événements)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client en utilisant des expressions régulières avec la méthode `setPattern()` :

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Ce motif garantit que seules les valeurs correspondant au format `HH:mm` (deux chiffres, deux points, deux chiffres) sont considérées comme valides.

:::tip Format des expressions régulières
Le motif doit suivre la syntaxe RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion des entrées
Le champ tente d'analyser et de formater les entrées numériques horaires en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou impossible à analyser (par exemple, `99:99`), elle peut passer les vérifications de motif mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans votre logique applicative, même si un motif d'expression régulière est défini, pour garantir que l'heure est à la fois correctement formatée et significative.
:::

## Sélecteur d'heure {#time-picker}

Le `MaskedTimeField` comprend un sélecteur d'heure intégré qui permet aux utilisateurs de sélectionner une heure visuellement, plutôt que de la taper. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont requises.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Accéder au sélecteur {#accessing-the-picker}

Vous pouvez accéder au sélecteur d'heure en utilisant `getPicker()` :

```java
TimePicker picker = field.getPicker();
```

### Afficher/cacher l'icône du sélecteur {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou cacher l'icône de l'horloge à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, en cliquant, en appuyant sur Entrée ou sur les flèches) :

```java
picker.setAutoOpen(true);
```

:::tip Renforcer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une heure qu'en utilisant le sélecteur (et ne pas la taper manuellement), combinez les deux paramètres suivants :

```java
field.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
field.setAllowCustomValue(false);     // Désactive l'entrée de texte manuelle
```

Cette configuration garantit que toute saisie horaire provient de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse liés aux entrées tapées.
:::

### Ouvrir manuellement le sélecteur {#manually-open-the-picker}

Pour ouvrir le sélecteur d'heure par programme :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // équivalent à open()
```

### Définir l'étape du sélecteur {#setting-the-picker-step}

Vous pouvez définir l'intervalle entre les heures sélectionnables dans le sélecteur en utilisant `setStep()`. Cela vous permet de contrôler la granularité des options horaires—idéal pour des scénarios comme la planification par blocs de 15 minutes.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Contrainte d'étape
L'étape doit diviser uniformément une heure ou une journée complète. Sinon, une exception sera levée.
:::

Cela garantit que la liste déroulante contient des valeurs prévisibles et espacées uniformément telles que `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Le `MaskedTimeFieldSpinner` étend le `MaskedTimeField` en ajoutant des contrôles de sélecteur qui permettent aux utilisateurs d'incrémenter ou de décrémenter l'heure en utilisant les flèches ou des boutons de l'interface utilisateur. Cela fournit un style d'interaction plus guidé, particulièrement utile dans les applications de style bureau.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Caractéristiques clés {#key-features}

- **Pas de temps interactif :**
  Utilisez les flèches ou les boutons de rotation pour incrémenter ou décrémenter la valeur horaire.

- **Unité de rotation personnalisable :**
  Choisissez quelle partie de l'heure modifier en utilisant `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Les options comprennent `HOUR`, `MINUTE`, `SECOND`, et `MILLISECOND`.

- **Limites min/max :**
  Hérite du support pour les heures minimales et maximales autorisées en utilisant `setMin()` et `setMax()`.

- **Sortie formatée :**
  Complètement compatible avec les masques et les réglages de localisation du `MaskedTimeField`.

### Exemple : Configurer le pas par heure {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Style {#styling}

<TableBuilder name="MaskedTimeField" />
