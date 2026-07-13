---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: 433c612e16b0476f720deb896cb73f4a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Le `MaskedDateField` est un champ de saisie de texte qui permet aux utilisateurs d'entrer des dates sous forme de chiffres et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque spécifie le format de date attendu, guidant à la fois l'entrée et l'affichage. Le composant prend en charge le parsing, la validation, la localisation et la restauration des valeurs flexibles pour un traitement des dates cohérent et spécifique à la région.

<!-- INTRO_END -->

## Bases {#basics}

:::tip Vous cherchez une entrée de temps ?
Le `MaskedDateField` se concentre uniquement sur les valeurs de **date**. Si vous avez besoin d'un composant similaire pour saisir et formater l'**heure**, envisagez plutôt le [`MaskedTimeField`](./timefield).
:::

Le `MaskedDateField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Règles de masque {#mask-rules}

Le `MaskedDateField` prend en charge plusieurs formats de date utilisés dans le monde, qui varient par l'ordre du jour, du mois et de l'année. Les motifs courants incluent :

- **Jour/Mois/Année** (utilisé dans la plupart de l'Europe)
- **Mois/Jour/Année** (utilisé aux États-Unis)
- **Année/Mois/Jour** (utilisé en Chine, au Japon et en Corée ; également la norme ISO : `YYYY-MM-DD`)

Dans ces formats, des variations locales incluent le choix de séparateur (par exemple, `-`, `/`, ou `.`), si les années sont en deux ou quatre chiffres, et si les mois ou jours à un chiffre sont complétés par des zéros initiaux.

Pour gérer cette diversité, le `MaskedDateField` utilise des indicateurs de format, chacun commençant par `%`, suivi d'une lettre représentant une partie spécifique de la date. Ces indicateurs définissent comment l'entrée est analysée et comment la date est affichée.

:::tip Application des masques par programmation
Pour formater ou analyser les dates avec la même syntaxe de masque en dehors d'un champ, utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Indicateurs de format de date {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Année      |
| `%M`   | Mois       |
| `%D`   | Jour       |

### Modificateurs {#modifiers}

Les modificateurs permettent un meilleur contrôle sur la façon dont les composants de la date sont formatés :

| Modificateur | Description               |
| ------------ | ------------------------- |
| `z`          | Remplissage par zéro      |
| `s`          | Représentation de texte courte |
| `l`          | Représentation de texte longue  |
| `p`          | Nombre compressé          |
| `d`          | Décimal (format par défaut)   |

Ceci peut être combiné pour construire une grande variété de masques de date.

## Localisation du format de date {#date-format-localization}

Le `MaskedDateField` s'adapte aux formats de date régionaux en définissant la locale appropriée. Cela garantit que les dates sont affichées et analysées d'une manière qui correspond aux attentes de l'utilisateur.

| Région        | Format     | Exemple      |
| ------------- | ---------- | ------------ |
| États-Unis    | MM/DD/YYYY | `07/04/2023` |
| Europe        | DD/MM/YYYY | `04/07/2023` |
| Norme ISO     | YYYY-MM-DD | `2023-07-04` |

Pour appliquer la localisation, utilisez la méthode `setLocale()`. Elle accepte une [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) et ajuste automatiquement à la fois le formatage et l'analyse :

```java
dateField.setLocale(Locale.FRANCE);
```

## Logiciel de parsing {#parsing-logic}

Le `MaskedDateField` analyse l'entrée de l'utilisateur en fonction du masque de date défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des dates valides.
Le comportement d'analyse dépend de l'ordre du format défini par le masque (par exemple, `%Mz/%Dz/%Yz` pour mois/jour/année). Ce format détermine comment les séquences numériques sont interprétées.

Par exemple, en supposant qu'aujourd'hui soit le `15 septembre 2012`, voici comment diverses entrées seraient interprétées :

### Scénarios d'exemple d'analyse {#example-parsing-scenarios}

| Entrée                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Un chiffre unique est toujours interprété comme un nombre de jour dans le mois en cours, donc ce serait le 1er septembre 2012.                                                                            | Pareil que YMD                                                                         | Pareil que YMD                                                                                                                  |
| <div align="center">`12`</div>       | Deux chiffres sont toujours interprétés comme un nombre de jour dans le mois en cours, donc ce serait le 12 septembre 2012.                                                                          | Pareil que YMD                                                                         | Pareil que YMD                                                                                                                  |
| <div align="center">`112`</div>      | Trois chiffres sont interprétés comme un numéro de mois à 1 chiffre suivi d'un numéro de jour à 2 chiffres, donc ce serait le 12 janvier 2012.                                                         | Pareil que YMD                                                                         | Trois chiffres sont interprétés comme un numéro de jour à 1 chiffre suivi d'un numéro de mois à deux chiffres, donc ce serait le 1er décembre 2012. |
| <div align="center">`1004`</div>     | Quatre chiffres sont interprétés comme MMDD, donc ce serait le 4 octobre 2012.                                                                                                                 | Pareil que YMD                                                                         | Quatre chiffres sont interprétés comme DDMM, donc ce serait le 10 avril 2012.                                                         |
| <div align="center">`020304`</div>   | Six chiffres sont interprétés comme YYMMDD, donc ce serait le 4 mars 2002.                                                                                                                     | Six chiffres sont interprétés comme MMDDYY, donc ce serait le 3 février 2004.         | Six chiffres sont interprétés comme DDMMYY, donc ce serait le 2 mars 2004.                                                          |
| <div align="center">`8 digits`</div> | Huit chiffres sont interprétés comme YYYYMMDD. Par exemple, `20040612` est le 12 juin 2004.                                                                                                      | Huit chiffres sont interprétés comme MMDDYYYY. Par exemple, `06122004` est le 12 juin 2004. | Huit chiffres sont interprétés comme DDMMYYYY. Par exemple, `06122004` est le 6 décembre 2004.                                    |
| <div align="center">`12/6`</div>     | Deux nombres séparés par un délimiteur valide sont interprétés comme MM/DD, donc ce serait le 6 décembre 2012. <br />Note : Tous les caractères sauf les lettres et les chiffres sont considérés comme des délimiteurs valides. | Pareil que YMD                                                                         | Deux nombres séparés par un délimiteur sont interprétés comme DD/MM, donc ce serait le 12 juin 2012.                               |
| <div align="center">`3/4/5`</div>    | 5 avril 2012                                                                                                                                                                                      | 4 mars 2005                                                                       | 3 avril 2005                                                                                                                 |


## Analyse de date textuelle <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Par défaut, le `MaskedDateField` n'accepte que les entrées numériques pour les dates. Cependant, vous pouvez activer **l'analyse de dates textuelles** pour permettre aux utilisateurs d'entrer les noms de mois et de jours dans leur entrée. Cette fonctionnalité est particulièrement utile pour créer une saisie de date plus naturelle.

Pour activer le parsing textuel, utilisez la méthode `setTextualDateParsing()` :

```java
dateField.setTextualDateParsing(true);
```

### Substitution des noms de mois {#month-name-substitution}

Lorsque le parsing textuel est activé, vous pouvez utiliser des modificateurs spéciaux dans votre masque pour accepter les noms de mois au lieu des valeurs numériques :

- **`%Ms`** - Accepte les noms de mois courts (Jan, Fév, Mar, etc.)
- **`%Ml`** - Accepte les noms de mois longs (Janvier, Février, Mars, etc.)

Les noms de mois peuvent apparaître à n'importe quelle position dans le masque, et le champ continuera à accepter les entrées numériques comme solution de repli.

#### Exemples {#examples}

| Masque | Entrée | Résultat |
| ------ | ------ | ------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Ml/%Dz/%Yz` | `Septembre/01/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Dz/%Ml/%Yz` | `01/Septembre/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Valide** - La solution numérique de repli fonctionne toujours |

:::info
Tous les 12 mois sont pris en charge sous forme courte (Jan, Fév, Mar, Avr, Mai, Jun, Jul, Aoû, Sep, Oct, Nov, Déc) et sous forme longue (Janvier, Février, etc.).
:::
### Décoration des noms de jours {#day-name-decoration}

Les noms de jours de la semaine peuvent être inclus dans l'entrée pour une meilleure lisibilité, mais ils sont **uniquement décoratifs** et sont supprimés lors de l'analyse. Ils n'affectent pas la valeur de date réelle.

- **`%Ds`** - Accepte les noms de jours courts (Lun, Mar, Mer, etc.)
- **`%Dl`** - Accepte les noms de jours longs (Lundi, Mardi, Mercredi, etc.)

:::warning Les Noms de Jours Nécessitent un Jour Numérique
Lorsque vous utilisez des noms de jours de la semaine (`%Ds` ou `%Dl`), votre masque **doit également inclure** `%Dz` ou `%Dd` pour spécifier le numéro de jour réel. Sans un composant de jour numérique, l'entrée sera invalide.
:::

#### Exemples {#examples-1}

| Masque | Entrée | Résultat |
| ------ | ------ | ------- |
| `%Ds %Mz/%Dz/%Yz` | `Lun 09/01/25` | **Valide** - Le nom du jour est décoratif |
| `%Dl %Mz/%Dz/%Yz` | `Lundi 09/01/25` | **Valide** - Le nom du jour est décoratif |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Mar` | **Valide** - Le nom du jour à la fin |
| `%Dl/%Mz/%Yz` | `Lundi/09/25` | **Invalide** - `%Dz` manquant |
| `%Mz/%Dl/%Yz` | `09/Lundi/25` | **Invalide** - `%Dz` manquant |

Tous les 7 jours de la semaine sont pris en charge sous forme courte (Lun, Mar, Mer, Jeu, Ven, Sam, Dim) et sous forme longue (Lundi, Mardi, etc.).

### Règles supplémentaires d'analyse {#additional-parsing-rules}

L'analyse textuelle des dates comprend plusieurs fonctionnalités utiles :

- **Insensible à la casse :** Les entrées telles que `LUNDI 09/01/25`, `lundi 09/01/25` ou `Lundi 09/01/25` fonctionnent toutes de la même manière.
- **Consciente de la locale :** Les noms de mois et de jours doivent correspondre à la locale configurée du champ. Par exemple, avec une locale française, utilisez `septembre` et non `September`. Les noms anglais ne seront pas reconnus à moins que la locale soit définie sur l'anglais.
  - Locale française : `septembre/01/25` est reconnu comme septembre
  - Locale allemande : `Montag 09/01/25` est reconnu avec lundi comme nom de jour

## Définir des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage de dates autorisées dans un `MaskedDateField` à l'aide des méthodes `setMin()` et `setMax()` :

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Les entrées en dehors de la plage définie seront considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedDateField` inclut une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou original. C'est utile pour annuler la saisie de l'utilisateur ou réinitialiser à une date par défaut.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Programmatique**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins d'être remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`, passant une instance de [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client en utilisant des expressions régulières avec la méthode `setPattern()` :

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Ce modèle garantit que seules les valeurs correspondant au format `MM/DD/YYYY` (deux chiffres, barre oblique, deux chiffres, barre oblique, quatre chiffres) sont considérées comme valides.

:::tip Format de l'expression régulière
Le modèle doit suivre la syntaxe JavaScript RegExp comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notes sur la gestion des entrées
Le champ essaie d'analyser et de formater les entrées numériques des dates en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou non analysable (par exemple `99/99/9999`), elle peut passer les vérifications de modèle mais échouer lors de la validation logique.
Vous devez toujours valider la valeur d'entrée dans la logique de votre application, même si un modèle d'expression régulière est défini, pour garantir que la date est à la fois correctement formatée et significative.
::::

## Sélecteur de date {#date-picker}

Le `MaskedDateField` comprend un sélecteur de calendrier intégré qui permet aux utilisateurs de sélectionner une date visuellement, plutôt que de la taper. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont nécessaires.

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

Vous pouvez configurer le sélecteur pour s'ouvrir automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou sur les touches fléchées) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour vous assurer que les utilisateurs ne peuvent sélectionner une date qu'à l'aide du sélecteur de calendrier (et non en en saisissant manuellement une), combinez les deux paramètres suivants :

```java
dateField.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
dateField.setAllowCustomValue(false);    // Désactive la saisie de texte manuelle
```

Cette configuration garantit que toutes les entrées de date proviennent de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse des entrées saisies.
:::

### Ouvrir manuellement le calendrier {#manually-open-the-calendar}

Pour ouvrir le calendrier programatiquement :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // identique à open()
```

### Afficher les semaines dans le calendrier {#show-weeks-in-the-calendar}

Le sélecteur peut éventuellement afficher les numéros de semaine dans la vue du calendrier :

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Le `MaskedDateFieldSpinner` étend [`MaskedDateField`](#basics) en ajoutant des contrôles de molette qui permettent aux utilisateurs d'augmenter ou de diminuer la date à l'aide de touches fléchées ou de boutons de l'interface utilisateur. Cela fournit un style d'interaction plus guidé, particulièrement utile dans les applications de style bureau.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Caractéristiques clés {#key-features}

- **Incrémentation de date interactive :**
  Utilisez les touches fléchées ou les boutons de molette pour augmenter ou diminuer la valeur de la date.

- **Unité de pas personnalisable :**
  Choisissez quelle partie de la date modifier en utilisant `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Les options incluent `DAY`, `WEEK`, `MONTH`, et `YEAR`.

- **Limites min/max :**
  Hérite du support pour les dates minimales et maximales autorisées via `setMin()` et `setMax()`.

- **Sortie formatée :**
  Entièrement compatible avec les masques et les paramètres de localisation du `MaskedDateField`.

### Exemple : Configurer l'incrémentation hebdomadaire {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Cela fait en sorte que chaque étape de la molette avance ou recule la date d'une semaine.

## Stylisation {#styling}

<TableBuilder name="MaskedDateField" />
