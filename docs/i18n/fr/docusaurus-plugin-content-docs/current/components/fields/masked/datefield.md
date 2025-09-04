---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: f76242de3a742ad3a930e1581f688592
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Le `MaskedDateField` est un contrôle de saisie de texte conçu pour l'entrée structurée des dates. Il permet aux utilisateurs de saisir des dates sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque est une chaîne qui spécifie le format de date attendu, guidant à la fois l'entrée et l'affichage.

Ce composant prend en charge l'analyse flexible, la validation, la localisation et la restauration de valeurs. Il est particulièrement utile dans des formulaires tels que les inscriptions, les réservations et la planification, où des formats de dates cohérents et spécifiques à la région sont requis.

:::tip Vous cherchez une saisie de temps ?
Le `MaskedDateField` est axé uniquement sur les valeurs de **date**. Si vous avez besoin d'un composant similaire pour saisir et formater **l'heure**, consultez le [`MaskedTimeField`](./timefield) à la place.
:::

## Basics {#basics}

Le `MaskedDateField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Mask rules {#mask-rules}

Le `MaskedDateField` prend en charge plusieurs formats de date utilisés dans le monde, qui varient par l'ordre du jour, du mois et de l'année. Les modèles courants incluent :

- **Jour/Mois/Année** (utilisé dans la plupart de l'Europe)
- **Mois/Jour/Année** (utilisé aux États-Unis)
- **Année/Mois/Jour** (utilisé en Chine, au Japon et en Corée ; également le standard ISO : `YYYY-MM-DD`)

Au sein de ces formats, des variations locales incluent le choix du séparateur (par exemple, `-`, `/`, ou `.`), si les années sont à deux ou quatre chiffres, et si les mois ou jours à un chiffre sont complétés par des zéros devant.

Pour gérer cette diversité, le `MaskedDateField` utilise des indicateurs de format, chacun commençant par `%`, suivi d'une lettre représentant une partie spécifique de la date. Ces indicateurs définissent comment l'entrée est analysée et comment la date est affichée.

### Date format indicators {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Année       |
| `%M`   | Mois        |
| `%D`   | Jour        |

### Modifiers {#modifiers}

Les modificateurs permettent un meilleur contrôle sur la façon dont les composants de la date sont formatés :

| Modificateur | Description               |
| ------------ | ------------------------- |
| `z`          | Remplissage par zéro      |
| `s`          | Représentation de texte courte |
| `l`          | Représentation de texte longue  |
| `p`          | Nombre empaqueté          |
| `d`          | Décimal (format par défaut)  |

Ces options peuvent être combinées pour créer une grande variété de masques de date.

## Date format localization {#date-format-localization}

Le `MaskedDateField` s'adapte aux formats de date régionaux en définissant la locale appropriée. Cela garantit que les dates sont affichées et analysées d'une manière qui correspond aux attentes des utilisateurs.

| Région        | Format     | Exemple       |
| ------------- | ---------- | ------------- |
| États-Unis    | MM/JJ/AAAA | `07/04/2023`  |
| Europe        | JJ/MM/AAAA | `04/07/2023`  |
| Standard ISO  | AAAA-MM-JJ | `2023-07-04`  |

Pour appliquer la localisation, utilisez la méthode `setLocale()`. Elle accepte une [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) et ajuste automatiquement à la fois le formatage et l'analyse :

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logic {#parsing-logic}

Le `MaskedDateField` analyse l'entrée de l'utilisateur en fonction du masque de date défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des dates valides. 
Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Mz/%Dz/%Yz` pour mois/jour/année). Ce format détermine comment les séquences numériques sont interprétées.

Par exemple, supposons qu'aujourd'hui soit le `15 Septembre 2012`, voici comment diverses entrées seraient interprétées :

### Example parsing scenarios {#example-parsing-scenarios}

| Entrée                               | YMD (ISO)                                                                                                                                                                                              | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Un chiffre unique est toujours interprété comme un numéro de jour dans le mois en cours, donc ce serait le 1er Septembre 2012.                                                                          | Même que YMD                                                                         | Même que YMD                                                                                                              |
| <div align="center">`12`</div>       | Deux chiffres sont toujours interprétés comme un numéro de jour dans le mois en cours, donc ce serait le 12 Septembre 2012.                                                                           | Même que YMD                                                                         | Même que YMD                                                                                                              |
| <div align="center">`112`</div>      | Trois chiffres sont interprétés comme un numéro de mois à un chiffre suivi d'un numéro de jour à deux chiffres, donc ce serait le 12 Janvier 2012.                                                      | Même que YMD                                                                         | Trois chiffres sont interprétés comme un numéro de jour à un chiffre suivi d'un numéro de mois à deux chiffres, donc ce serait le 1er Décembre 2012. |
| <div align="center">`1004`</div>     | Quatre chiffres sont interprétés comme MMJJ, donc ce serait le 4 Octobre 2012.                                                                                                                        | Même que YMD                                                                         | Quatre chiffres sont interprétés comme JJMM, donc ce serait le 10 Avril 2012.                                             |
| <div align="center">`020304`</div>   | Six chiffres sont interprétés comme YYMMJJ, donc ce serait le 4 Mars 2002.                                                                                                                           | Six chiffres sont interprétés comme MMJJYY, donc ce serait le 3 Février 2004.      | Six chiffres sont interprétés comme JJMMYY, donc ce serait le 2 Mars 2004.                                              |
| <div align="center">`8 chiffres`</div> | Huit chiffres sont interprétés comme AAAAMMJJ. Par exemple, `20040612` est le 12 Juin 2004.                                                                                                         | Huit chiffres sont interprétés comme MMJJAAAA. Par exemple, `06122004` est le 12 Juin 2004. | Huit chiffres sont interprétés comme JJMMAAAA. Par exemple, `06122004` est le 6 Décembre 2004.                             |
| <div align="center">`12/6`</div>     | Deux nombres séparés par un délimiteur valide sont interprétés comme MM/JJ, donc ce serait le 6 Décembre 2012. <br />Remarque : Tous les caractères sauf les lettres et les chiffres sont considérés comme des délimiteurs valides. | Même que YMD                                                                         | Deux nombres séparés par n'importe quel délimiteur sont interprétés comme JJ/MM, donc ce serait le 12 Juin 2012.           |
| <div align="center">`3/4/5`</div>    | 5 Avril 2012                                                                                                                                                                                          | 4 Mars 2005                                                                        | 3 Avril 2005                                                                                                                |

## Setting min/max constraints {#setting-minmax-constraints}

Vous pouvez restreindre la plage de dates autorisée dans un `MaskedDateField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Les entrées en dehors de la plage définie seront considérées comme invalides.

## Restoring the value {#restoring-the-value}

Le `MaskedDateField` comprend une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou original. Cela est utile pour revenir à la saisie de l'utilisateur ou réinitialiser à une date par défaut.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Programmiquement**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins d'être remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`, en transmettant une instance de [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validation patterns {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client en utilisant des expressions régulières avec la méthode `setPattern()` :

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Ce modèle garantit que seules les valeurs correspondant au format `MM/JJ/AAAA` (deux chiffres, barre oblique, deux chiffres, barre oblique, quatre chiffres) sont considérées comme valides.

:::tip Format d'expression régulière
Le modèle doit suivre la syntaxe d'expressions régulières de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion de l'entrée
Le champ tente d'analyser et de formatter les entrées de date numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou non analysable (par exemple `99/99/9999`), elle peut passer les vérifications de modèle mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans votre logique d'application, même si un modèle d'expression régulière est défini, pour vous assurer que la date est à la fois correctement formatée et significative.
::::

## Date picker {#date-picker}

Le `MaskedDateField` comprend un sélecteur de calendrier intégré qui permet aux utilisateurs de sélectionner une date visuellement, plutôt que de la taper. Cela améliore l'ergonomie pour les utilisateurs moins techniques ou lorsque des entrées précises sont nécessaires.

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

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou touche les flèches) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une date qu'en utilisant le sélecteur de calendrier (et non en la saisissant manuellement), combinez les deux réglages suivants :

```java
dateField.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
dateField.setAllowCustomValue(false);    // Désactive la saisie de texte manuelle
```

Cette configuration garantit que toutes les entrées de date proviennent de l'interface du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse provenant de la saisie au clavier.
:::

### Manually open the calendar {#manually-open-the-calendar}

Pour ouvrir le calendrier de manière programmatique :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // pareil que open()
```

### Show weeks in the calendar {#show-weeks-in-the-calendar}

Le sélecteur peut afficher en option les numéros de semaine dans la vue du calendrier :

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Le `MaskedDateFieldSpinner` étend le [`MaskedDateField`](#basics) en ajoutant des contrôles de sélection qui permettent aux utilisateurs d'augmenter ou de diminuer la date à l'aide des touches fléchées ou des boutons de l'interface. Il fournit un style d'interaction plus guidé, particulièrement utile dans les applications de type bureau.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Key features {#key-features}

- **Réglage interactif de la date :**  
  Utilisez les touches fléchées ou les boutons de sélection pour augmenter ou diminuer la valeur de la date.

- **Unité de pas personnalisable :**  
  Choisissez quelle partie de la date modifier à l'aide de `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Les options incluent `DAY`, `WEEK`, `MONTH`, et `YEAR`.

- **Limites Min/Max :**  
  Hérite du support des dates minimales et maximales autorisées à l'aide de `setMin()` et `setMax()`.

- **Sortie formatée :**  
  Complètement compatible avec les masques et les paramètres de localisation du `MaskedDateField`.

### Example: Configure weekly stepping {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Cela rend chaque pas de sélection avancé ou reculé d'une semaine. 

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
