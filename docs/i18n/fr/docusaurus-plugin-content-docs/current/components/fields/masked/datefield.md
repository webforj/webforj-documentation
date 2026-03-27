---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 981d5cd2686c83144433a0135b1222dc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Le `MaskedDateField` est un champ de texte qui permet aux utilisateurs de saisir des dates sous forme de chiffres et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque spécifie le format de date attendu, guidant à la fois la saisie et l'affichage. Le composant prend en charge un parsing flexible, la validation, la localisation et la restauration de valeurs pour un traitement des dates cohérent et spécifique à la région.

<!-- INTRO_END -->

## Bases {#basics}

:::tip Vous cherchez une saisie de temps ?
Le `MaskedDateField` est uniquement axé sur les valeurs **date**. Si vous avez besoin d'un composant similaire pour saisir et formater du **temps**, consultez le [`MaskedTimeField`](./timefield) à la place.
:::

Le `MaskedDateField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Règles de masque {#mask-rules}

Le `MaskedDateField` prend en charge plusieurs formats de date utilisés dans le monde, qui varient en fonction de l'ordre jour, mois et année. Les modèles courants incluent :

- **Jour/Mois/Année** (utilisé dans la plupart des pays européens)
- **Mois/Jour/Année** (utilisé aux États-Unis)
- **Année/Mois/Jour** (utilisé en Chine, au Japon et en Corée ; également norme ISO : `YYYY-MM-DD`)

Dans ces formats, les variations locales incluent le choix de séparateur (par exemple, `-`, `/` ou `.`), si les années sont de deux ou quatre chiffres, et si les mois ou jours à un chiffre sont complétés par des zéros non significatifs.

Pour gérer cette diversité, le `MaskedDateField` utilise des indicateurs de format, chacun commençant par `%`, suivi d'une lettre représentant une partie spécifique de la date. Ces indicateurs définissent comment l'entrée est analysée et comment la date est affichée.

### Indicateurs de format de date {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Année       |
| `%M`   | Mois        |
| `%D`   | Jour        |

### Modificateurs {#modifiers}

Les modificateurs permettent un meilleur contrôle sur la manière dont les composants de la date sont formatés :

| Modificateur | Description               |
| ------------ | ------------------------- |
| `z`          | Remplissage à zéro       |
| `s`          | Représentation textuelle courte |
| `l`          | Représentation textuelle longue  |
| `p`          | Nombre groupé            |
| `d`          | Décimal (format par défaut)  |

Ceux-ci peuvent être combinés pour construire une grande variété de masques de date.

## Localisation du format de date {#date-format-localization}

Le `MaskedDateField` s'adapte aux formats de date régionaux en définissant la locale appropriée. Cela garantit que les dates sont affichées et analysées de manière à correspondre aux attentes des utilisateurs.

| Région        | Format     | Exemple      |
| ------------- | ---------- | ------------ |
| États-Unis    | MM/JJ/AAAA | `07/04/2023` |
| Europe        | JJ/MM/AAAA | `04/07/2023` |
| Norme ISO     | AAAA-MM-JJ | `2023-07-04` |

Pour appliquer la localisation, utilisez la méthode `setLocale()`. Elle accepte une [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) et ajuste automatiquement à la fois le formatage et l'analyse :

```java
dateField.setLocale(Locale.FRANCE);
```

## Logique de parsing {#parsing-logic}

Le `MaskedDateField` analyse les entrées utilisateurs en fonction du masque de date défini. Il accepte les entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des dates valides.
Le comportement d'analyse dépend de l'ordre des formats défini par le masque (par exemple, `%Mz/%Dz/%Yz` pour mois/jour/année). Ce format détermine comment les séquences numériques sont interprétées.

Par exemple, en supposant qu'aujourd'hui soit le `15 septembre 2012`, voici comment différentes entrées seraient interprétées :

### Scénarios d'analyse d'exemple {#example-parsing-scenarios}

| Entrée                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | Un seul chiffre est toujours interprété comme un numéro de jour dans le mois courant, donc ce serait le 1er septembre 2012.                                                                          | Idem à YMD                                                                         | Idem à YMD                                                                                                                  |
| <div align="center">`12`</div>        | Deux chiffres sont toujours interprétés comme un numéro de jour dans le mois courant, donc ce serait le 12 septembre 2012.                                                                         | Idem à YMD                                                                         | Idem à YMD                                                                                                                  |
| <div align="center">`112`</div>       | Trois chiffres sont interprétés comme un numéro de mois à un chiffre suivi d'un numéro de jour à deux chiffres, donc ce serait le 12 janvier 2012.                                                 | Idem à YMD                                                                         | Trois chiffres sont interprétés comme un numéro de jour à un chiffre suivi d'un numéro de mois à deux chiffres, donc ce serait le 1er décembre 2012. |
| <div align="center">`1004`</div>      | Quatre chiffres sont interprétés comme MMJJ, donc ce serait le 4 octobre 2012.                                                                                                                     | Idem à YMD                                                                         | Quatre chiffres sont interprétés comme JJMM, donc ce serait le 10 avril 2012.                                                         |
| <div align="center">`020304`</div>    | Six chiffres sont interprétés comme YYMMJJ, donc ce serait le 4 mars 2002.                                                                                                                        | Six chiffres sont interprétés comme MMJJYY, donc ce serait le 3 février 2004.            | Six chiffres sont interprétés comme JJMMYY, donc ce serait le 2 mars 2004.                                                         |
| <div align="center">`8 digits`</div>  | Huit chiffres sont interprétés comme AAAAMMJJ. Par exemple, `20040612` est le 12 juin 2004.                                                                                                         | Huit chiffres sont interprétés comme MMJJAAAA. Par exemple, `06122004` est le 12 juin 2004. | Huit chiffres sont interprétés comme JJMMAAAA. Par exemple, `06122004` est le 6 décembre 2004.                                        |
| <div align="center">`12/6`</div>      | Deux nombres séparés par tout délimiteur valide sont interprétés comme MM/JJ, donc ce serait le 6 décembre 2012. <br />Remarque : Tous les caractères sauf les lettres et les chiffres sont considérés comme des délimiteurs valides. | Idem à YMD                                                                         | Deux nombres séparés par tout délimiteur sont interprétés comme JJ/MM, donc ce serait le 12 juin 2012.                               |
| <div align="center">`3/4/5`</div>     | 5 avril 2012                                                                                                                                                                                      | 4 mars 2005                                                                       | 3 avril 2005                                                                                                                 |


## Analyse des dates textuelles <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Par défaut, le `MaskedDateField` n'accepte que des entrées numériques pour les dates. Cependant, vous pouvez activer l'**analyse de dates textuelles** pour permettre aux utilisateurs de saisir des noms de mois et de jours dans leur entrée. Cette fonctionnalité est particulièrement utile pour créer une saisie de date plus naturelle.

Pour activer le parsing textuel, utilisez la méthode `setTextualDateParsing()` :

```java
dateField.setTextualDateParsing(true);
```

### Substitution des noms de mois {#month-name-substitution}

Lorsque le parsing textuel est activé, vous pouvez utiliser des modificateurs spéciaux dans votre masque pour accepter des noms de mois au lieu de valeurs numériques :

- **`%Ms`** - Accepte les noms de mois courts (Jan, Feb, Mar, etc.)
- **`%Ml`** - Accepte les noms de mois longs (janvier, février, mars, etc.)

Les noms de mois peuvent apparaître à n'importe quelle position dans le masque, et le champ acceptera toujours les entrées numériques comme secours.

#### Exemples

| Masque           | Entrée            | Résultat |
| ---------------- | ------------------ | -------- |
| `%Ms/%Dz/%Yz`    | `Sep/01/25`        | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Ml/%Dz/%Yz`    | `September/01/25`  | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Dz/%Ml/%Yz`    | `01/September/25`  | **Valide** - Analyse comme le 1er septembre 2025 |
| `%Mz/%Dz/%Yz`    | `09/01/25`        | **Valide** - Le secours numérique fonctionne toujours |

:::info
Tous les 12 mois sont pris en charge sous forme courte (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec) et longue (janvier, février, etc.).
:::
### Décoration des noms de jour {#day-name-decoration}

Les noms des jours de la semaine peuvent être inclus dans l'entrée pour une meilleure lisibilité, mais ils sont **uniques pour la décoration** et sont supprimés lors de l'analyse. Ils n'affectent pas la valeur réelle de la date.

- **`%Ds`** - Accepte les noms de jours courts (Mon, Tue, Wed, etc.)
- **`%Dl`** - Accepte les noms de jours longs (lundi, mardi, mercredi, etc.)

:::warning Les noms de jour exigent un jour numérique
Lorsque vous utilisez des noms de jours de la semaine (`%Ds` ou `%Dl`), votre masque **doit également inclure** `%Dz` ou `%Dd` pour spécifier le numéro de jour réel. Sans un composant de jour numérique, l'entrée sera invalide.
:::

#### Exemples

| Masque           | Entrée            | Résultat |
| ---------------- | ---------------   | ------   |
| `%Ds %Mz/%Dz/%Yz`| `Mon 09/01/25`    | **Valide** - Le nom du jour est décoratif |
| `%Dl %Mz/%Dz/%Yz`| `Monday 09/01/25` | **Valide** - Le nom du jour est décoratif |
| `%Mz/%Dz/%Yz %Ds`| `09/01/25 Tue`    | **Valide** - Le nom du jour à la fin |
| `%Dl/%Mz/%Yz`    | `Monday/09/25`    | **Invalide** - `%Dz` manquant |
| `%Mz/%Dl/%Yz`    | `09/Monday/25`    | **Invalide** - `%Dz` manquant |

Les 7 jours de la semaine sont pris en charge à la fois sous forme courte (Mon, Tue, Wed, Thu, Fri, Sat, Sun) et longue (lundi, mardi, etc.).

### Règles de parsing supplémentaires {#additional-parsing-rules}

L'analyse de dates textuelles inclut plusieurs fonctionnalités utiles :

- **Insensible à la casse :** Des entrées comme `LUNDI 09/01/25`, `lundi 09/01/25` ou `Lundi 09/01/25` fonctionnent toutes de la même manière.
- **Consciente de la locale :** Les noms de mois et de jours doivent correspondre à la locale configurée du champ. Par exemple, avec une locale française, utilisez `septembre` et non `September`. Les noms anglais ne seront pas reconnus à moins que la locale soit définie sur l'anglais.
  - Locale française : `septembre/01/25` est reconnu comme septembre.
  - Locale allemande : `Montag 09/01/25` est reconnu avec lundi comme nom de jour.

## Définir des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage de dates autorisées dans un `MaskedDateField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Les entrées en dehors de la plage définie seront considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedDateField` inclut une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou d'origine. Cela est utile pour revenir à une saisie utilisateur ou réinitialiser à une date par défaut.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Méthodes pour restaurer la valeur {#ways-to-restore-the-value}

- **Par programmation**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins qu'elle ne soit remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`, en passant une instance de [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html).

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client à l'aide d'expressions régulières avec la méthode `setPattern()` :

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Ce modèle garantit que seules les valeurs correspondant au format `MM/JJ/AAAA` (deux chiffres, barre oblique, deux chiffres, barre oblique, quatre chiffres) sont considérées comme valides.

:::tip Format d'expression régulière
Le modèle doit suivre la syntaxe des RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion des entrées
Le champ tente d'analyser et de formater les entrées numériques de date en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou non analysable (par exemple `99/99/9999`), elle peut passer les vérifications de motif mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans votre logique applicative, même si un modèle d'expression régulière est défini, pour garantir que la date est à la fois correctement formatée et significative.
::::

## Sélecteur de date {#date-picker}

Le `MaskedDateField` inclut un sélecteur de calendrier intégré qui permet aux utilisateurs de sélectionner une date visuellement, plutôt que de la taper. Cela améliore l'ergonomie pour les utilisateurs moins techniques ou lorsque des saisies précises sont requises.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Accéder au sélecteur {#accessing-the-picker}

Vous pouvez accéder au sélecteur de dates en utilisant `getPicker()` :

```java
DatePicker picker = dateField.getPicker();
```

### Afficher/masquer l'icône de sélecteur {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou masquer l'icône de calendrier à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, en cliquant, en appuyant sur Entrée ou sur des touches fléchées) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une date qu'en utilisant le sélecteur de calendrier (et non en saisissant manuellement), combinez les deux réglages suivants :

```java
dateField.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
dateField.setAllowCustomValue(false);    // Désactive la saisie de texte manuelle
```

Cette configuration garantit que toutes les saisies de date proviennent de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez contrôler rigoureusement le format et éliminer les problèmes d'analyse liés à une saisie manuelle.
:::

### Ouvrir manuellement le calendrier {#manually-open-the-calendar}

Pour ouvrir le calendrier par programme :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // identique à ouvrir()
```

### Afficher les semaines dans le calendrier {#show-weeks-in-the-calendar}

Le sélecteur peut afficher éventuellement les numéros de semaine dans la vue du calendrier :

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

Le `MaskedDateFieldSpinner` étend le [`MaskedDateField`](#basics) en ajoutant des contrôles de type spinner qui permettent aux utilisateurs d'incrémenter ou de décrémenter la date à l'aide des touches fléchées ou des boutons de l'interface utilisateur. Cela fournit un style d'interaction plus guidé, particulièrement utile dans les applications de type bureau.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Principales fonctionnalités {#key-features}

- **Avancement interactif de la date :**  
  Utilisez les touches fléchées ou les boutons pour incrémenter ou décrémenter la valeur de la date.

- **Unité d'étape personnalisable :**  
  Choisissez quelle partie de la date modifier à l'aide de `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Les options incluent `DAY`, `WEEK`, `MONTH` et `YEAR`.

- **Limites Min/Max :**  
  Hérite du support pour les dates minimales et maximales autorisées à l'aide de `setMin()` et `setMax()`.

- **Sortie formatée :**  
  Entièrement compatible avec les masques et les paramètres de localisation de `MaskedDateField`.

### Exemple : Configuration de l'avancement hebdomadaire {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Cela fait que chaque étape d'avancement déplace la date d'une semaine.


## Stylisation {#styling}

<TableBuilder name="MaskedDateField" />
