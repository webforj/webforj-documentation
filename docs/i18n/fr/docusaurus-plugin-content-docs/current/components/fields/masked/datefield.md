---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: d63b5c4325ef201b54da5b78b4e66f1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Le `MaskedDateField` est un champ de saisie de texte qui permet aux utilisateurs d'entrer des dates sous forme de chiffres et formate automatiquement la saisie en fonction d'un masque défini lorsque le champ perd le focus. Le masque spécifie le format de date attendu, guidant à la fois la saisie et l'affichage. Le composant prend en charge l'analyse flexible, la validation, la localisation et la restauration de valeur pour un traitement des dates cohérent et spécifique à la région.

<!-- INTRO_END -->

:::tip Vous cherchez une entrée horaire ?
Le `MaskedDateField` est uniquement axé sur les valeurs de **date**. Si vous avez besoin d'un composant similaire pour entrer et formater des **heures**, consultez le [`MaskedTimeField`](/docs/components/fields/masked/timefield) à la place.
:::

Le `MaskedDateField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, un label, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Règles de masque {#mask-rules}

Le `MaskedDateField` prend en charge plusieurs formats de date utilisés dans le monde, qui varient en fonction de l'ordre du jour, du mois et de l'année. Les modèles courants incluent :

- **Jour/Mois/Année** (utilisé dans la plupart des pays européens)
- **Mois/Jour/Année** (utilisé aux États-Unis)
- **Année/Mois/Jour** (utilisé en Chine, au Japon et en Corée ; également la norme ISO : `YYYY-MM-DD`)

Dans ces formats, les variations locales incluent le choix du séparateur (par exemple, `-`, `/`, ou `.`), que les années soient à deux ou quatre chiffres, et si les mois ou jours à un chiffre sont complétés par des zéros devant.

Pour gérer cette diversité, le `MaskedDateField` utilise des indicateurs de format, chacun commençant par `%`, suivi d'une lettre qui représente une partie spécifique de la date. Ces indicateurs définissent comment l'entrée est analysée et comment la date est affichée.

:::tip Application des masques par programmation
Pour formater ou analyser des dates avec la même syntaxe de masque en dehors d'un champ, utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Indicateurs de format de date {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Année       |
| `%M`   | Mois        |
| `%D`   | Jour        |

### Modificateurs {#modifiers}

Les modificateurs permettent un meilleur contrôle sur la façon dont les composants de la date sont formatés :

| Modificateur | Description                   |
| ------------ | ----------------------------- |
| `z`          | Remplissage par zéro          |
| `s`          | Représentation textuelle courte |
| `l`          | Représentation textuelle longue  |
| `p`          | Nombre compact                |
| `d`          | Décimal (format par défaut)   |

Ces modificateurs peuvent être combinés pour construire une large variété de masques de date.

## Localisation du format de date {#date-format-localization}

Le `MaskedDateField` s'adapte aux formats de date régionaux en définissant la locale appropriée. Cela garantit que les dates sont affichées et analysées de manière à correspondre aux attentes des utilisateurs.

| Région        | Format     | Exemple      |
| ------------- | ---------- | ------------ |
| États-Unis    | MM/DD/YYYY | `07/04/2023` |
| Europe        | DD/MM/YYYY | `04/07/2023` |
| Norme ISO     | YYYY-MM-DD | `2023-07-04` |

Pour appliquer la localisation, utilisez la méthode `setLocale()`. Elle accepte une [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) et ajuste automatiquement le formatage et l'analyse :

```java
dateField.setLocale(Locale.FRANCE);
```

## Logique d'analyse {#parsing-logic}

Le `MaskedDateField` analyse la saisie de l'utilisateur en fonction du masque de date défini. Il accepte des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des dates valides. Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Mz/%Dz/%Yz` pour mois/jour/année). Ce format détermine comment les séquences numériques sont interprétées.

Par exemple, en supposant qu'aujourd'hui soit le `15 septembre 2012`, voici comment diverses saisies seraient interprétées :

### Scénarios d'analyse d'exemple {#example-parsing-scenarios}

| Entrée                               | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Un seul chiffre est toujours interprété comme un numéro de jour dans le mois en cours, donc ce serait le 1er septembre 2012.                                                                        | Identique à YMD                                                                     | Identique à YMD                                                                                                            |
| <div align="center">`12`</div>       | Deux chiffres sont toujours interprétés comme un numéro de jour dans le mois en cours, donc ce serait le 12 septembre 2012.                                                                       | Identique à YMD                                                                     | Identique à YMD                                                                                                            |
| <div align="center">`112`</div>      | Trois chiffres sont interprétés comme un numéro de mois à 1 chiffre suivi d'un numéro de jour à 2 chiffres, donc ce serait le 12 janvier 2012.                                                     | Identique à YMD                                                                     | Trois chiffres sont interprétés comme un numéro de jour à 1 chiffre suivi d'un numéro de mois à deux chiffres, donc ce serait le 1er décembre 2012. |
| <div align="center">`1004`</div>     | Quatre chiffres sont interprétés comme MMDD, donc ce serait le 4 octobre 2012.                                                                                                                 | Identique à YMD                                                                     | Quatre chiffres sont interprétés comme DDMM, donc ce serait le 10 avril 2012.                                             |
| <div align="center">`020304`</div>   | Six chiffres sont interprétés comme YYMMDD, donc ce serait le 4 mars 2002.                                                                                                                    | Six chiffres sont interprétés comme MMDDYY, donc ce serait le 3 février 2004.      | Six chiffres sont interprétés comme DDMMYY, donc ce serait le 2 mars 2004.                                               |
| <div align="center">`8 chiffres`</div> | Huit chiffres sont interprétés comme YYYYMMDD. Par exemple, `20040612` est le 12 juin 2004.                                                                                                    | Huit chiffres sont interprétés comme MMDDYYYY. Par exemple, `06122004` est le 12 juin 2004. | Huit chiffres sont interprétés comme DDMMYYYY. Par exemple, `06122004` est le 6 décembre 2004.                            |
| <div align="center">`12/6`</div>     | Deux nombres séparés par n'importe quel délimiteur valide sont interprétés comme MM/DD, donc ce serait le 6 décembre 2012. <br />Note : Tous les caractères sauf les lettres et les chiffres sont considérés comme des délimiteurs valides. | Identique à YMD                                                                     | Deux nombres séparés par n'importe quel délimiteur sont interprétés comme DD/MM, donc ce serait le 12 juin 2012.         |
| <div align="center">`3/4/5`</div>    | 5 avril 2012                                                                                                                                                                                      | 4 mars 2005                                                                        | 3 avril 2005                                                                                                             |

## Analyse des dates textuelles <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Par défaut, le `MaskedDateField` n'accepte que des entrées numériques pour les dates. Cependant, vous pouvez activer l'**analyse des dates textuelles** pour permettre aux utilisateurs d'entrer des noms de mois et de jours dans leur saisie. Cette fonctionnalité est particulièrement utile pour rendre la saisie de dates plus naturelle.

Pour activer l'analyse textuelle, utilisez la méthode `setTextualDateParsing()` :

```java
dateField.setTextualDateParsing(true);
```

### Substitution des noms de mois {#month-name-substitution}

Lorsque l'analyse textuelle est activée, vous pouvez utiliser des modificateurs spéciaux dans votre masque pour accepter des noms de mois au lieu de valeurs numériques :

- **`%Ms`** - Accepte les noms de mois courts (Jan, Fév, Mar, etc.)
- **`%Ml`** - Accepte les noms de mois longs (Janvier, Février, Mars, etc.)

Les noms de mois peuvent apparaître à n'importe quelle position dans le masque, et le champ acceptera toujours une saisie numérique en secours.

#### Exemples {#examples}

| Masque | Entrée          | Résultat                                 |
| ------ | --------------- | ---------------------------------------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25`    | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Ml/%Dz/%Yz` | `Septembre/01/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Dz/%Ml/%Yz` | `01/Septembre/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25`     | **Valide** - Le secours numérique fonctionne toujours |

:::info
Les 12 mois sont pris en charge sous forme courte (Jan, Fév, Mar, Avr, Mai, Juin, Juil, Août, Sep, Oct, Nov, Déc) et longue (Janvier, Février, etc.).
:::
### Décoration des noms de jours {#day-name-decoration}

Les noms des jours de la semaine peuvent être inclus dans la saisie pour une meilleure lisibilité, mais ils sont **uniquement décoratifs** et sont supprimés lors de l'analyse. Ils n'affectent pas la valeur de la date réelle.

- **`%Ds`** - Accepte les noms de jours courts (Lun, Mar, Mer, etc.)
- **`%Dl`** - Accepte les noms de jours longs (Lundi, Mardi, Mercredi, etc.)

:::warning Les noms de jours nécessitent un jour numérique
Lorsque vous utilisez des noms de jours de la semaine (`%Ds` ou `%Dl`), votre masque **doit également inclure** `%Dz` ou `%Dd` pour spécifier le numéro de jour réel. Sans un composant de jour numérique, l'entrée sera invalide.
:::

#### Exemples {#examples-1}

| Masque | Entrée          | Résultat                             |
| ------ | --------------- | ------------------------------------ |
| `%Ds %Mz/%Dz/%Yz` | `Lun 09/01/25`  | **Valide** - Le nom du jour est décoratif |
| `%Dl %Mz/%Dz/%Yz` | `Lundi 09/01/25`| **Valide** - Le nom du jour est décoratif |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Mar`  | **Valide** - Le nom du jour à la fin |
| `%Dl/%Mz/%Yz` | `Lundi/09/25` | **Invalide** - Manque `%Dz`       |
| `%Mz/%Dl/%Yz` | `09/Lundi/25`  | **Invalide** - Manque `%Dz`       |

Tous les 7 jours de la semaine sont pris en charge à la fois sous forme courte (Lun, Mar, Mer, Jeu, Ven, Sam, Dim) et longue (Lundi, Mardi, etc.).

### Règles d'analyse supplémentaires {#additional-parsing-rules}

L'analyse des dates textuelles inclut plusieurs fonctionnalités utiles :

- **Insensible à la casse :** Des entrées comme `LUNDI 09/01/25`, `lundi 09/01/25` ou `Lundi 09/01/25` fonctionnent de la même manière.
- **Consciente de la locale :** Les noms de mois et de jours doivent correspondre à la locale configurée du champ. Par exemple, avec une locale française, utilisez `septembre` et non `September`. Les noms en anglais ne seront pas reconnus à moins que la locale soit réglée sur l'anglais.
  - Locale française : `septembre/01/25` est reconnu comme septembre
  - Locale allemande : `Montag 09/01/25` est reconnu avec le nom du jour lundi

## Définition des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage de dates autorisées dans un `MaskedDateField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Ces deux méthodes acceptent des valeurs de type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Les saisies en dehors de la plage définie seront considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedDateField` comprend une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou d'origine. Cela est utile pour annuler la saisie de l'utilisateur ou réinitialiser à une date par défaut.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manières de restaurer la valeur {#ways-to-restore-the-value}

- **Par programmation**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins qu'elle ne soit remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`, en passant une instance de [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client à l'aide d'expressions régulières avec la méthode `setPattern()` :

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Ce modèle garantit que seuls les valeurs correspondant au format `MM/DD/YYYY` (deux chiffres, barre oblique, deux chiffres, barre oblique, quatre chiffres) sont considérées comme valides.

:::tip Format d'expression régulière
Le modèle doit suivre la syntaxe RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notes sur la gestion de l'entrée
Le champ tente d'analyser et de formater les entrées de date numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou non analysable (par exemple `99/99/9999`), elle peut passer les vérifications de modèle mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans la logique de votre application, même si un modèle d'expression régulière est défini, pour garantir que la date est à la fois correctement formatée et significative.
::::

## Sélecteur de date {#date-picker}

Le `MaskedDateField` inclut un sélecteur de calendrier intégré qui permet aux utilisateurs de sélectionner une date visuellement, plutôt que de la taper. Cela améliore la convivialité pour les utilisateurs moins techniques ou lorsque une saisie précise est requise.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Accéder au sélecteur {#accessing-the-picker}

Vous pouvez accéder au sélecteur de date en utilisant `getPicker()` :

```java
DatePicker picker = dateField.getPicker();
```

### Afficher/masquer l'icône du sélecteur {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou masquer l'icône de calendrier à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou utilise les flèches) :

```java
picker.setAutoOpen(true);
```

:::tip Forcer la sélection par le biais du sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une date qu'en utilisant le sélecteur de calendrier (et non en la saisissant manuellement), combinez les deux réglages suivants :

```java
dateField.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
dateField.setAllowCustomValue(false);    // Désactive la saisie de texte manuelle
```

Cette configuration garantit que toutes les saisies de date proviennent du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict sur le format et éliminer les problèmes d'analyse liés à la saisie manuelle.
:::

### Ouvrir manuellement le calendrier {#manually-open-the-calendar}

Pour ouvrir le calendrier par programmation :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // identique à open()
```

### Afficher les semaines dans le calendrier {#show-weeks-in-the-calendar}

Le sélecteur peut afficher facultativement les numéros de semaine dans la vue de calendrier :

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Le `MaskedDateFieldSpinner` étend le `MaskedDateField` en ajoutant des contrôles de molette qui permettent aux utilisateurs d'incrémenter ou de diminuer la date à l'aide des flèches ou des boutons de l'interface utilisateur. Il fournit un style d'interaction plus guidé, particulièrement utile dans les applications de style bureau.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Caractéristiques clés {#key-features}

- **Saisie de date interactive :**
  Utilisez les flèches ou les boutons de la molette pour incrémenter ou diminuer la valeur de la date.

- **Unité de pas personnalisable :**
  Choisissez quelle partie de la date modifier avec `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Les options comprennent `DAY`, `WEEK`, `MONTH`, et `YEAR`.

- **Limites min/max :**
  Hérite du support pour les dates minimales et maximales autorisées en utilisant `setMin()` et `setMax()`.

- **Sortie formatée :**
  Entièrement compatible avec les masques et les paramètres de localisation du `MaskedDateField`.

### Exemple : Configurer un pas hebdomadaire {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Cela fera en sorte que chaque pas de molette avance ou recule la date d'une semaine.

## Style {#styling}

<TableBuilder name="MaskedDateField" />
