---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 6c75156564c20c2d451ebe7046213c37
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Le `MaskedDateField` est un champ de saisie de texte qui permet aux utilisateurs d'entrer des dates sous forme de chiffres et formate automatiquement l'entrée basée sur un masque défini lorsque le champ perd le focus. Le masque spécifie le format de date attendu, guidant à la fois l'entrée et l'affichage. Le composant prend en charge l'analyse flexible, la validation, la localisation et la restauration de valeurs pour un traitement des dates cohérent et spécifique à la région.

<!-- INTRO_END -->

## Bases {#basics}

:::tip Vous cherchez une saisie de temps ?
Le `MaskedDateField` est uniquement axé sur les valeurs **dates**. Si vous avez besoin d'un composant similaire pour entrer et formater l'**heure**, consultez le [`MaskedTimeField`](./timefield) à la place.
:::

Le `MaskedDateField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un auditeur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Règles de masque {#mask-rules}

Le `MaskedDateField` prend en charge plusieurs formats de date utilisés dans le monde, qui varient selon l'ordre du jour, du mois et de l'année. Les modèles courants incluent :

- **Jour/Mois/Année** (utilisé dans la plupart de l'Europe)
- **Mois/Jour/Année** (utilisé aux États-Unis)
- **Année/Mois/Jour** (utilisé en Chine, au Japon et en Corée ; également la norme ISO : `YYYY-MM-DD`)

Dans ces formats, les variations locales incluent le choix du séparateur (par exemple, `-`, `/`, ou `.`), si les années sont de deux ou quatre chiffres, et si les mois ou jours à un chiffre sont complétés par des zéros devant.

Pour gérer cette diversité, le `MaskedDateField` utilise des indicateurs de format, chacun commençant par `%`, suivi d'une lettre représentant une partie spécifique de la date. Ces indicateurs définissent comment l'entrée est analysée et comment la date est affichée.

:::tip Application de masques par programmation
Pour formater ou analyser des dates avec la même syntaxe de masque en dehors d'un champ, utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Indicateurs de format date {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Année       |
| `%M`   | Mois        |
| `%D`   | Jour        |

### Modificateurs {#modifiers}

Les modificateurs permettent un meilleur contrôle sur la façon dont les composants de la date sont formatés :

| Modificateur | Description                   |
| ------------ | ----------------------------- |
| `z`          | Remplissage zéro              |
| `s`          | Représentation en texte court  |
| `l`          | Représentation en texte long   |
| `p`          | Nombre compact                 |
| `d`          | Décimal (format par défaut)   |

Ces modificateurs peuvent être combinés pour construire une large variété de masques de date.

## Localisation du format de date {#date-format-localization}

Le `MaskedDateField` s'adapte aux formats de date régionaux en définissant la locale appropriée. Cela garantit que les dates sont affichées et analysées de manière à correspondre aux attentes de l'utilisateur.

| Région        | Format     | Exemple      |
| ------------- | ---------- | ------------ |
| États-Unis    | MM/DD/YYYY | `07/04/2023` |
| Europe        | DD/MM/YYYY | `04/07/2023` |
| Norme ISO     | YYYY-MM-DD | `2023-07-04` |

Pour appliquer la localisation, utilisez la méthode `setLocale()`. Elle accepte un [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) et ajuste automatiquement à la fois le formatage et l'analyse :

```java
dateField.setLocale(Locale.FRANCE);
```

## Logique d'analyse {#parsing-logic}

Le `MaskedDateField` analyse l'entrée de l'utilisateur en fonction du masque de date défini. Il accepte à la fois des entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des dates valides.
Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Mz/%Dz/%Yz` pour mois/jour/année). Ce format détermine comment les séquences numériques sont interprétées.

Par exemple, en supposant qu'aujourd'hui soit le `15 septembre 2012`, voici comment diverses entrées seraient interprétées :

### Scénarios d'analyse d'exemple {#example-parsing-scenarios}

| Entrée                                 | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
|---------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| <div align="center">`1`</div>         | Un chiffre unique est toujours interprété comme un numéro de jour dans le mois en cours, donc ce serait le 1er septembre 2012.                                                                    | Identique à YMD                                                                     | Identique à YMD                                                                                                          |
| <div align="center">`12`</div>        | Deux chiffres sont toujours interprétés comme un numéro de jour dans le mois en cours, donc ce serait le 12 septembre 2012.                                                                     | Identique à YMD                                                                     | Identique à YMD                                                                                                          |
| <div align="center">`112`</div>       | Trois chiffres sont interprétés comme un numéro de mois à 1 chiffre suivi d'un numéro de jour à 2 chiffres, donc ce serait le 12 janvier 2012.                                                    | Identique à YMD                                                                     | Trois chiffres sont interprétés comme un numéro de jour à 1 chiffre suivi d'un numéro de mois à 2 chiffres, donc ce serait le 1er décembre 2012. |
| <div align="center">`1004`</div>      | Quatre chiffres sont interprétés comme MMDD, donc ce serait le 4 octobre 2012.                                                                                                                 | Identique à YMD                                                                     | Quatre chiffres sont interprétés comme DDMM, donc ce serait le 10 avril 2012.                                          |
| <div align="center">`020304`</div>    | Six chiffres sont interprétés comme YYMMDD, donc ce serait le 4 mars 2002.                                                                                                                    | Six chiffres sont interprétés comme MMDDYY, donc ce serait le 3 février 2004.     | Six chiffres sont interprétés comme DDMMYY, donc ce serait le 2 mars 2004.                                             |
| <div align="center">`8 digits`</div>  | Huit chiffres sont interprétés comme YYYYMMDD. Par exemple, `20040612` correspond au 12 juin 2004.                                                                                            | Huit chiffres sont interprétés comme MMDDYYYY. Par exemple, `06122004` correspond au 12 juin 2004. | Huit chiffres sont interprétés comme DDMMYYYY. Par exemple, `06122004` correspond au 6 décembre 2004.                  |
| <div align="center">`12/6`</div>      | Deux nombres séparés par un délimiteur valide sont interprétés comme MM/DD, donc ce serait le 6 décembre 2012. <br />Remarque : Tous les caractères sauf les lettres et les chiffres sont considérés comme des délimiteurs valides. | Identique à YMD                                                                     | Deux nombres séparés par un délimiteur sont interprétés comme DD/MM, donc ce serait le 12 juin 2012.                     |
| <div align="center">`3/4/5`</div>     | 5 avril 2012                                                                                                                                                                                      | 4 mars 2005                                                                         | 3 avril 2005                                                                                                            |


## Analyse de date textuelle <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Par défaut, le `MaskedDateField` n'accepte que des entrées numériques pour les dates. Cependant, vous pouvez activer l'**analyse de dates textuelles** pour permettre aux utilisateurs d'entrer des noms de mois et de jours dans leur saisie. Cette fonctionnalité est particulièrement utile pour créer une saisie de date plus naturelle.

Pour activer l'analyse textuelle, utilisez la méthode `setTextualDateParsing()` :

```java
dateField.setTextualDateParsing(true);
```

### Substitution de nom de mois {#month-name-substitution}

Lorsque l'analyse textuelle est activée, vous pouvez utiliser des modificateurs spéciaux dans votre masque pour accepter les noms de mois au lieu de valeurs numériques :

- **`%Ms`** - Accepte les noms de mois courts (Jan, Feb, Mar, etc.)
- **`%Ml`** - Accepte les noms de mois longs (janvier, février, mars, etc.)

Les noms de mois peuvent apparaître à n'importe quelle position dans le masque, et le champ acceptera toujours les entrées numériques comme solution de secours.

#### Exemples

| Masque | Entrée            | Résultat                           |
| ------ | ----------------- | ---------------------------------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25`       | **Valide** - Analyse comme 1er septembre 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Valide** - Analyse comme 1er septembre 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Valide** - Analyse comme 1er septembre 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25`       | **Valide** - La solution de secours numérique fonctionne toujours |

:::info
Tous les 12 mois sont pris en charge sous les formes courtes (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec) et longues (janvier, février, etc.).
:::
### Décoration du nom de jour {#day-name-decoration}

Les noms des jours de la semaine peuvent être inclus dans l'entrée pour une meilleure lisibilité, mais ils sont **décoratifs uniquement** et sont supprimés lors de l'analyse. Ils n'affectent pas la valeur réelle de la date.

- **`%Ds`** - Accepte les noms de jours courts (Mon, Tue, Wed, etc.)
- **`%Dl`** - Accepte les noms de jours longs (lundi, mardi, mercredi, etc.)

:::warning Les noms des jours nécessitent un jour numérique
Lors de l'utilisation des noms des jours de la semaine (`%Ds` ou `%Dl`), votre masque **doit également inclure** `%Dz` ou `%Dd` pour spécifier le numéro réel du jour. Sans un composant de jour numérique, l'entrée sera invalide.
:::

#### Exemples

| Masque             | Entrée            | Résultat                           |
| ------------------ | ----------------- | ---------------------------------- |
| `%Ds %Mz/%Dz/%Yz`  | `Mon 09/01/25`    | **Valide** - Le nom du jour est décoratif |
| `%Dl %Mz/%Dz/%Yz`  | `Monday 09/01/25` | **Valide** - Le nom du jour est décoratif |
| `%Mz/%Dz/%Yz %Ds`  | `09/01/25 Tue`    | **Valide** - Le nom du jour à la fin |
| `%Dl/%Mz/%Yz`      | `Monday/09/25`    | **Invalide** - `%Dz` manquant     |
| `%Mz/%Dl/%Yz`      | `09/Monday/25`    | **Invalide** - `%Dz` manquant     |

Tous les 7 jours de la semaine sont pris en charge sous les formes courtes (Mon, Tue, Wed, Thu, Fri, Sat, Sun) et longues (lundi, mardi, etc.).

### Règles d'analyse supplémentaires {#additional-parsing-rules}

L'analyse de date textuelle comprend plusieurs fonctionnalités utiles :

- **Insensible à la casse :** L'entrée comme `MONDAY 09/01/25`, `monday 09/01/25` ou `Monday 09/01/25` fonctionnent toutes de la même manière.
- **Sensible à la locale :** Les noms de mois et de jours doivent correspondre à la locale configurée du champ. Par exemple, avec une locale française, utilisez `septembre` et non `September`. Les noms en anglais ne seront pas reconnus à moins que la locale ne soit définie sur l'anglais.
  - Locale française : `septembre/01/25` est reconnu comme septembre
  - Locale allemande : `Montag 09/01/25` est reconnu avec lundi comme nom de jour

## Définir des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage de dates autorisées dans un `MaskedDateField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Les entrées en dehors de la plage définie seront considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedDateField` comprend une fonctionnalité de restauration qui remet la valeur du champ à un état prédéfini ou d'origine. Cela est utile pour revenir à une saisie utilisateur ou réinitialiser à une date par défaut.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Par programmation**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins qu'elle ne soit remplacée par un auditeur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`, en passant une instance de [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client en utilisant des expressions régulières avec la méthode `setPattern()` :

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Ce modèle garantit que seules les valeurs correspondant au format `MM/DD/YYYY` (deux chiffres, barre oblique, deux chiffres, barre oblique, quatre chiffres) sont considérées comme valides.

:::tip Format d'expression régulière
Le modèle doit suivre la syntaxe RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion des entrées
Le champ tente d'analyser et de formater les entrées de date numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou impossible à analyser (par exemple, `99/99/9999`), elle peut passer les vérifications de modèle mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans la logique de votre application, même si un modèle d'expression régulière est défini, pour garantir que la date est à la fois correctement formatée et significative.
::::

## Sélecteur de date {#date-picker}

Le `MaskedDateField` comprend un sélecteur de calendrier intégré qui permet aux utilisateurs de sélectionner une date visuellement, plutôt que de la taper. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont nécessaires.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Accéder au sélecteur {#accessing-the-picker}

Vous pouvez accéder au sélecteur de date en utilisant `getPicker()` :

```java
DatePicker picker = dateField.getPicker();
```

### Afficher/cacher l'icône du sélecteur {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou masquer l'icône de calendrier à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou utilise les touches fléchées) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une date qu'à l'aide du sélecteur de calendrier (et ne puisse pas en saisir une manuellement), combinez les deux réglages suivants :

```java
dateField.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
dateField.setAllowCustomValue(false);    // Désactive la saisie manuelle de texte
```

Cette configuration garantit que toutes les entrées de date proviennent de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse provenant d'entrées saisies.
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

Le sélecteur peut éventuellement afficher les numéros de semaine dans la vue du calendrier :

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Le `MaskedDateFieldSpinner` étend [`MaskedDateField`](#basics) en ajoutant des contrôles de molette qui permettent aux utilisateurs d'incrémenter ou de décrémenter la date à l'aide des touches fléchées ou des boutons UI. Il fournit un style d'interaction plus guidé, particulièrement utile dans les applications de type bureau.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Fonctionnalités clés {#key-features}

- **Saut de date interactif :**  
  Utilisez les touches fléchées ou les boutons de rotation pour incrémenter ou décrémenter la valeur de la date.

- **Unité de pas personnalisable :**  
  Choisissez quelle partie de la date modifier avec `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Les options incluent `DAY`, `WEEK`, `MONTH` et `YEAR`.

- **Limites minimales/maximales :**  
  Hérite du support pour les dates minimales et maximales autorisées en utilisant `setMin()` et `setMax()`.

- **Sortie formatée :**  
  Entièrement compatible avec les masques et les paramètres de localisation du `MaskedDateField`.

### Exemple : Configurer le saut hebdomadaire {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Cela fait avancer ou reculer chaque étape de rotation la date d'une semaine.

## Stimulation {#styling}

<TableBuilder name="MaskedDateField" />
