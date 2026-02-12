---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 93973075b9f8f9bcc3eddf18e8b01017
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Le `MaskedDateField` est un contrôle d'entrée de texte conçu pour la saisie de dates structurées. Il permet aux utilisateurs d'entrer des dates sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque est une chaîne qui spécifie le format de date attendu, guidant à la fois l'entrée et l'affichage.

Ce composant prend en charge le parsing flexible, la validation, la localisation et la restauration des valeurs. Il est particulièrement utile dans des formulaires tels que les enregistrements, les réservations et la planification, où des formats de date cohérents et spécifiques à la région sont nécessaires.

:::tip Vous recherchez une entrée de temps ?
Le `MaskedDateField` est uniquement axé sur les valeurs de **date**. Si vous avez besoin d'un composant similaire pour entrer et formater des **heures**, consultez le [`MaskedTimeField`](./timefield) à la place.
:::

## Basics {#basics}

Le `MaskedDateField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Mask rules {#mask-rules}

Le `MaskedDateField` prend en charge plusieurs formats de date utilisés dans le monde, qui varient selon l'ordre du jour, du mois et de l'année. Les motifs communs incluent :

- **Jour/Mois/Année** (utilisé dans la plupart de l'Europe)
- **Mois/Jour/Année** (utilisé aux États-Unis)
- **Année/Mois/Jour** (utilisé en Chine, au Japon et en Corée ; également la norme ISO : `YYYY-MM-DD`)

Dans ces formats, les variations locales incluent le choix du séparateur (par exemple, `-`, `/` ou `.`), que les années soient de deux ou quatre chiffres, et si les mois ou les jours à un chiffre sont complétés par des zéros de tête.

Pour gérer cette diversité, le `MaskedDateField` utilise des indicateurs de format, chacun commençant par `%`, suivi d'une lettre qui représente une partie spécifique de la date. Ces indicateurs définissent comment l'entrée est analysée et comment la date est affichée.

### Date format indicators {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Année      |
| `%M`   | Mois       |
| `%D`   | Jour       |

### Modifiers {#modifiers}

Les modificateurs permettent un meilleur contrôle sur la façon dont les composants de la date sont formatés :

| Modificateur | Description               |
| ------------ | ------------------------- |
| `z`          | Rembourrage à zéro       |
| `s`          | Représentation textuelle courte |
| `l`          | Représentation textuelle longue  |
| `p`          | Nombre compressé              |
| `d`          | Décimal (format par défaut)  |

Ceux-ci peuvent être combinés pour construire une grande variété de masques de date.

## Date format localization {#date-format-localization}

Le `MaskedDateField` s'adapte aux formats de date régionaux en définissant la locale appropriée. Cela garantit que les dates sont affichées et analysées d'une manière qui correspond aux attentes des utilisateurs.

| Région        | Format     | Exemple      |
| ------------- | ---------- | ------------ |
| États-Unis    | MM/DD/YYYY | `07/04/2023` |
| Europe        | DD/MM/YYYY | `04/07/2023` |
| Norme ISO     | YYYY-MM-DD | `2023-07-04` |

Pour appliquer la localisation, utilisez la méthode `setLocale()`. Elle accepte un [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) et ajuste automatiquement à la fois le formatage et l'analyse :

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logic {#parsing-logic}

Le `MaskedDateField` analyse l'entrée de l'utilisateur en fonction du masque de date défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une entrée flexible tout en garantissant des dates valides. 
Le comportement d'analyse dépend de l'ordre du format défini par le masque (par exemple, `%Mz/%Dz/%Yz` pour mois/jour/année). Ce format détermine comment les séquences numériques sont interprétées.

Par exemple, supposons qu'aujourd'hui soit le `15 septembre 2012`, voici comment diverses entrées seraient interprétées :

### Example parsing scenarios {#example-parsing-scenarios}

| Entrée                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | Un seul chiffre est toujours interprété comme un numéro de jour dans le mois en cours, donc cela serait le 1er septembre 2012.                                                                                 | Même que YMD                                                                         | Même que YMD                                                                                                                  |
| <div align="center">`12`</div>        | Deux chiffres sont toujours interprétés comme un numéro de jour dans le mois en cours, donc cela serait le 12 septembre 2012.                                                                                   | Même que YMD                                                                         | Même que YMD                                                                                                                  |
| <div align="center">`112`</div>       | Trois chiffres sont interprétés comme un numéro de mois à 1 chiffre suivi d'un numéro de jour à 2 chiffres, donc cela serait le 12 janvier 2012.                                                                        | Même que YMD                                                                         | Trois chiffres sont interprétés comme un numéro de jour à 1 chiffre suivi d'un numéro de mois à 2 chiffres, donc cela serait le 1er décembre 2012. |
| <div align="center">`1004`</div>      | Quatre chiffres sont interprétés comme MMDD, donc cela serait le 4 octobre 2012.                                                                                                                             | Même que YMD                                                                         | Quatre chiffres sont interprétés comme DDMM, donc cela serait le 10 avril 2012.                                                         |
| <div align="center">`020304`</div>    | Six chiffres sont interprétés comme YYMMDD, donc cela serait le 4 mars 2002.                                                                                                                              | Six chiffres sont interprétés comme MMDDYY, donc cela serait le 3 février 2004.            | Six chiffres sont interprétés comme DDMMYY, donc cela serait le 2 mars 2004.                                                         |
| <div align="center">`8 digits`</div>  | Huit chiffres sont interprétés comme YYYYMMDD. Par exemple, `20040612` est le 12 juin 2004.                                                                                                                | Huit chiffres sont interprétés comme MMDDYYYY. Par exemple, `06122004` est le 12 juin 2004. | Huit chiffres sont interprétés comme DDMMYYYY. Par exemple, `06122004` est le 6 décembre 2004.                                        |
| <div align="center">`12/6`</div>      | Deux nombres séparés par un délimiteur valide sont interprétés comme MM/DD, donc cela serait le 6 décembre 2012. <br />Remarque : Tous les caractères sauf les lettres et les chiffres sont considérés comme des délimiteurs valides. | Même que YMD                                                                         | Deux nombres séparés par un délimiteur sont interprétés comme DD/MM, donc cela serait le 12 juin 2012.                               |
| <div align="center">`3/4/5`</div>     | 5 avril 2012                                                                                                                                                                                      | 4 mars 2005                                                                       | 3 avril 2005                                                                                                                 |

## Textual date parsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Par défaut, le `MaskedDateField` n'accepte que des entrées numériques pour les dates. Cependant, vous pouvez activer le **parsing de date textuelle** pour permettre aux utilisateurs d'entrer des noms de mois et de jours dans leur entrée. Cette fonctionnalité est particulièrement utile pour créer une saisie de date plus naturelle.

Pour activer le parsing textuel, utilisez la méthode `setTextualDateParsing()` :

```java
dateField.setTextualDateParsing(true);
```

### Month name substitution {#month-name-substitution}

Lorsque le parsing textuel est activé, vous pouvez utiliser des modificateurs spéciaux dans votre masque pour accepter les noms de mois plutôt que des valeurs numériques :

- **`%Ms`** - Accepte les noms de mois courts (Jan, Fév, Mar, etc.)
- **`%Ml`** - Accepte les noms de mois longs (Janvier, Février, Mars, etc.)

Les noms de mois peuvent apparaître à n'importe quelle position dans le masque, et le champ acceptera toujours une entrée numérique comme solution de secours.

#### Examples

| Masque | Input | Résultat |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Ml/%Dz/%Yz` | `Septembre/01/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Dz/%Ml/%Yz` | `01/Septembre/25` | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Valide** - La solution numérique de secours fonctionne toujours |

:::info
Tous les 12 mois sont supportés, à la fois en forme courte (Jan, Fév, Mar, Avr, Mai, Jun, Jul, Aoû, Sep, Oct, Nov, Déc) et en forme longue (Janvier, Février, etc.).
:::
### Day name decoration {#day-name-decoration}

Les noms de jour de la semaine peuvent être inclus dans l'entrée pour une meilleure lisibilité, mais ils sont **uniquement décoratifs** et sont retirés lors de l'analyse. Ils n'affectent pas la valeur de date réelle.

- **`%Ds`** - Accepte les noms de jour courts (Lun, Mar, Mer, etc.)
- **`%Dl`** - Accepte les noms de jour longs (Lundi, Mardi, Mercredi, etc.)

:::warning Les noms de jour nécessitent un jour numérique
Lors de l'utilisation des noms de jour de la semaine (`%Ds` ou `%Dl`), votre masque **doit également inclure** `%Dz` ou `%Dd` pour spécifier le numéro de jour réel. Sans un composant de jour numérique, l'entrée sera invalide.
:::

#### Examples

| Masque | Input | Résultat |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Lun 09/01/25` | **Valide** - Le nom du jour est décoratif |
| `%Dl %Mz/%Dz/%Yz` | `Lundi 09/01/25` | **Valide** - Le nom du jour est décoratif |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Mar` | **Valide** - Le nom du jour à la fin |
| `%Dl/%Mz/%Yz` | `Lundi/09/25` | **Invalide** - `%Dz` manquant |
| `%Mz/%Dl/%Yz` | `09/Lundi/25` | **Invalide** - `%Dz` manquant |

Tous les 7 jours de la semaine sont supportés en forme courte (Lun, Mar, Mer, Jeu, Ven, Sam, Dim) et en forme longue (Lundi, Mardi, etc.).

### Additional parsing rules {#additional-parsing-rules}

Le parsing textuel de date comprend plusieurs fonctionnalités utiles :

- **Insensible à la casse :** Des entrées comme `LUNDI 09/01/25`, `lundi 09/01/25` ou `Lundi 09/01/25` fonctionnent toutes de la même manière.
- **Sensible à la locale :** Les noms des mois et des jours doivent correspondre à la locale configurée du champ. Par exemple, avec une locale française, utilisez `septembre` et non `September`. Les noms anglais ne seront pas reconnus à moins que la locale soit définie sur l'anglais.
  - Locale française : `septembre/01/25` est reconnu comme septembre.
  - Locale allemande : `Montag 09/01/25` est reconnu avec lundi comme nom du jour.

## Setting min/max constraints {#setting-minmax-constraints}

Vous pouvez restreindre la plage de dates autorisées dans un `MaskedDateField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Les entrées en dehors de la plage définie seront considérées comme invalides.

## Restoring the value {#restoring-the-value}

Le `MaskedDateField` comprend une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou original. Cela est utile pour revenir à l'entrée de l'utilisateur ou réinitialiser à une date par défaut.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Programmé,** en appelant `restoreValue()`
- **Via le clavier,** en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins d'être remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`, en passant une instance [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validation patterns {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client à l'aide d'expressions régulières avec la méthode `setPattern()` :

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Ce motif garantit que seuls les valeurs correspondant au format `MM/DD/YYYY` (deux chiffres, barre oblique, deux chiffres, barre oblique, quatre chiffres) sont considérées comme valides.

:::tip Format d'expressions régulières
Le motif doit suivre la syntaxe des RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion des entrées
Le champ essaie d'analyser et de formater les entrées numériques de date en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours entrer manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou impossible à analyser (par exemple, `99/99/9999`), elle peut passer les vérifications de motif mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans la logique de votre application, même si un motif d'expression régulière est défini, pour garantir que la date est à la fois correctement formatée et significative.
::::

## Date picker {#date-picker}

Le `MaskedDateField` comprend un sélecteur de calendrier intégré qui permet aux utilisateurs de sélectionner une date visuellement, plutôt que de la taper. Cela améliore l'utilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont nécessaires.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Accessing the picker {#accessing-the-picker}

Vous pouvez accéder au sélecteur de date en utilisant `getPicker()` :

```java
DatePicker picker = dateField.getPicker();
```

### Show/hide the picker icon {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou masquer l'icône de calendrier à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Auto-open behavior {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou sur les touches fléchées) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une date qu'à l'aide du sélecteur de calendrier (et non en la tapant manuellement), combinez les deux paramètres suivants :

```java
dateField.getPicker().setAutoOpen(true); // Ouvre le sélecteur à l'interaction de l'utilisateur
dateField.setAllowCustomValue(false);    // Désactive la saisie manuelle de texte
```

Cette configuration garantit que toutes les entrées de date proviennent de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle de format strict et éliminer les problèmes d'analyse des entrées tapées.
:::

### Manually open the calendar {#manually-open-the-calendar}

Pour ouvrir le calendrier de manière programmatique :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // équivalent à open()
```

### Show weeks in the calendar {#show-weeks-in-the-calendar}

Le sélecteur peut afficher en option les numéros de semaine dans la vue du calendrier :

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Le `MaskedDateFieldSpinner` étend [`MaskedDateField`](#basics) en ajoutant des contrôles de spinner qui permettent aux utilisateurs d'incrémenter ou de décrémenter la date à l'aide des touches fléchées ou de boutons d'interface. Il offre un style d'interaction plus guidé, particulièrement utile dans les applications de style bureau.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Key features {#key-features}

- **Pas à Pas de Date Interactif :**  
  Utilisez les touches fléchées ou les boutons de rotation pour incrémenter ou décrémenter la valeur de la date.

- **Unité de Pas Personnalisable :**  
  Choisissez quelle partie de la date modifier en utilisant `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Les options incluent `DAY`, `WEEK`, `MONTH`, et `YEAR`.

- **Limites Min/Max :**  
  Hérite du support des dates minimales et maximales autorisées à l'aide de `setMin()` et `setMax()`.

- **Sortie Formatée :**  
  Entièrement compatible avec les masques et les paramètres de localisation du `MaskedDateField`.

### Example: Configure weekly stepping {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Cela fait que chaque étape de rotation avance ou recule la date d'une semaine.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
