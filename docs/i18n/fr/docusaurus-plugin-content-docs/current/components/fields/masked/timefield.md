---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 3d719856c08ce148bcd2999878d8c161
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Le `MaskedTimeField` est un contrôle d'entrée texte conçu pour une saisie de temps précise et structurée. Il permet aux utilisateurs de saisir des heures sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque est une chaîne qui spécifie le format de temps attendu, guidant à la fois la saisie et l'affichage.

Ce composant prend en charge l'analyse flexible, la validation, la localisation et la restauration des valeurs. Il est particulièrement utile dans des formulaires sensibles au temps tels que les emplois du temps, les feuilles de temps et les réservations.

:::tip Vous cherchez une entrée de date ? 
Le `MaskedTimeField` est conçu pour une saisie **uniquement de temps**. Si vous recherchez un composant pour gérer des **dates** avec un formatage basé sur un masque similaire, jetez un œil au [`MaskedDateField`](./datefield.md).
:::

## Bases {#basics}

Le `MaskedTimeField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Règles du masque {#mask-rules}

Le `MaskedTimeField` utilise des indicateurs de format pour définir comment le temps est analysé et affiché. Chaque indicateur de format commence par un `%` suivi d'une lettre représentant un composant temporel.

### Indicateurs de format de temps {#time-format-indicators}

| Format | Description         |
|--------|---------------------|
| `%H`   | Heure (24 heures)   |
| `%h`   | Heure (12 heures)   |
| `%m`   | Minute              |
| `%s`   | Seconde             |
| `%p`   | AM/PM               |

### Modificateurs {#modifiers}

Les modificateurs raffinent l'affichage des composants de temps :

| Modificateur | Description                 |
|--------------|------------------------------|
| `z`          | Remplissage par zéro        |
| `s`          | Représentation de texte courte|
| `l`          | Représentation de texte longue |
| `p`          | Nombre compact               |
| `d`          | Decimal (format par défaut)  |

Cela permet un formatage temporel flexible et adapté aux locales.

## Localisation du format de temps {#time-format-localization}

Le `MaskedTimeField` prend en charge la localisation en définissant la locale appropriée. Cela garantit que l'entrée et la sortie du temps correspondent aux conventions régionales.

```java
field.setLocale(Locale.GERMANY);
```

Cela affecte la façon dont les indicateurs AM/PM sont affichés, comment les séparateurs sont gérés et comment les valeurs sont analysées.

## Logique d'analyse {#parsing-logic}

Le `MaskedTimeField` analyse l'entrée utilisateur en fonction du masque de temps défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des heures valides.
Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Hz:%mz` pour heure/minute). Ce format détermine la manière dont les séquences numériques sont interprétées.

### Scénarios d'exemple d'analyse {#example-parsing-scenarios}

| Entrée  | Masque        | Interprété Comme  |
|---------|---------------|--------------------|
| `900`   | `%Hz:%mz`     | `09:00`            |
| `1345`  | `%Hz:%mz`     | `13:45`            |
| `0230`  | `%hz:%mz %p`  | `02:30 AM`         |
| `1830`  | `%hz:%mz %p`  | `06:30 PM`         |

## Définir les contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage horaire autorisée dans un `MaskedTimeField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Les entrées en dehors de la plage définie sont considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedTimeField` comprend une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini ou d'origine. Cela peut être utile pour annuler des changements ou revenir à un temps par défaut.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Programmatiquement**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut sauf si remplacée par un écouteur d'événements)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client en utilisant des expressions régulières avec la méthode `setPattern()` :

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Ce modèle garantit que seules les valeurs correspondant au format `HH:mm` (deux chiffres, deux-points, deux chiffres) sont considérées comme valides.

:::tip Format d'Expression Régulière
Le modèle doit suivre la syntaxe RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion des entrées
Le champ tente d'analyser et de formater les entrées numériques de temps en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou non analysable (par exemple, `99:99`), elle peut passer les vérifications de modèle mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans votre logique d'application, même si un modèle d'expression régulière est défini, pour garantir que le temps est à la fois correctement formaté et significatif.
:::

## Sélecteur de temps {#time-picker}

Le `MaskedTimeField` inclut un sélecteur de temps intégré qui permet aux utilisateurs de sélectionner un temps visuellement, plutôt que de le taper. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des saisies précises sont nécessaires.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Accéder au sélecteur {#accessing-the-picker}

Vous pouvez accéder au sélecteur de temps en utilisant `getPicker()` :

```java
TimePicker picker = field.getPicker();
```

### Afficher/masquer l'icône du sélecteur {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou masquer l'icône d'horloge à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour s'ouvrir automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou sur les touches fléchées) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner un temps qu'en utilisant le sélecteur (et non en le saisissant manuellement), combinez les deux paramètres suivants :

```java
field.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
field.setAllowCustomValue(false);    // Désactive la saisie manuelle
```

Cette configuration garantit que toutes les saisies de temps proviennent de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle de format strict et éliminer les problèmes d'analyse des saisies de textes.
:::

### Ouvrir manuellement le sélecteur {#manually-open-the-picker}

Pour ouvrir le sélecteur de temps par programme :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // équivalent à open()
```

### Définir l'étape du sélecteur {#setting-the-picker-step}

Vous pouvez définir l'intervalle entre les temps sélectionnables dans le sélecteur en utilisant `setStep()`. Cela vous permet de contrôler le niveau de granularité des options de temps—idéal pour des scénarios de planification par blocs de 15 minutes.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Contrainte d'étape
L'étape doit diviser uniformément une heure ou une journée complète. Sinon, une exception sera lancée.
:::

Cela garantit que la liste déroulante contient des valeurs prévisibles et espacées de manière régulière comme `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Le `MaskedTimeFieldSpinner` étend [`MaskedTimeField`](#basics) en ajoutant des contrôles de type spinner qui permettent aux utilisateurs d'incrémenter ou de décrémenter l'heure en utilisant les touches fléchées ou des boutons d'interface. Cela fournit un style d'interaction plus guidé, particulièrement utile dans les applications de type bureau.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Caractéristiques clés {#key-features}

- **Incrémentation de temps interactive :**  
  Utilisez les touches fléchées ou les boutons de rotation pour incrémenter ou décrémenter la valeur horaire.

- **Unité de rotation personnalisable :**  
  Choisissez quelle partie du temps modifier en utilisant `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Les options incluent `HOUR`, `MINUTE`, `SECOND`, et `MILLISECOND`.

- **Limites Min/Max :**  
  Hérite du support pour les heures minimales et maximales autorisées en utilisant `setMin()` et `setMax()`.

- **Sortie formatée :**  
  Entièrement compatible avec les masques et les paramètres de localisation de `MaskedTimeField`.

### Exemple : Configurer l'incrémentation par heure {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Style {#styling}

<TableBuilder name="MaskedTimeField" />
