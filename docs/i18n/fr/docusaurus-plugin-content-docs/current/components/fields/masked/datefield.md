---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: e2073fda6d7853bbacc6431c615e8cff
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Le `MaskedDateField` est un contrôle de saisie de texte conçu pour l'entrée structurée de dates. Il permet aux utilisateurs d'entrer des dates sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque est une chaîne qui spécifie le format de date attendu, guidant à la fois l'entrée et l'affichage.

Ce composant prend en charge le parsing flexible, la validation, la localisation et la restauration des valeurs. Il est particulièrement utile dans des formulaires comme les inscriptions, les réservations et la planification, où des formats de date cohérents et spécifiques à la région sont requis.

:::tip Vous cherchez une saisie de temps ?
Le `MaskedDateField` se concentre uniquement sur les valeurs de **date**. Si vous avez besoin d'un composant similaire pour saisir et formater le **temps**, examinez le [`MaskedTimeField`](./timefield) à la place.
:::

## Bases {#basics}

Le `MaskedDateField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Règles de masque {#mask-rules}

Le `MaskedDateField` prend en charge plusieurs formats de date utilisés dans le monde, qui varient selon l'ordre du jour, du mois et de l'année. Les modèles courants incluent :

- **Jour/Mois/Année** (utilisé dans la plupart des pays européens)
- **Mois/Jour/Année** (utilisé aux États-Unis)
- **Année/Mois/Jour** (utilisé en Chine, au Japon et en Corée ; également norme ISO : `YYYY-MM-DD`)

Dans ces formats, les variations locales incluent le choix du séparateur (par exemple, `-`, `/` ou `.`), que les années soient à deux ou quatre chiffres et si les mois ou jours à un chiffre sont complétés par des zéros devant.

Pour gérer cette diversité, le `MaskedDateField` utilise des indicateurs de format, chacun commençant par `%`, suivi d'une lettre qui représente une partie spécifique de la date. Ces indicateurs définissent comment l'entrée est analysée et comment la date est affichée.

### Indicateurs de format de date {#date-format-indicators}

| Format | Description |
| ------ | ----------- |
| `%Y`   | Année      |
| `%M`   | Mois       |
| `%D`   | Jour       |

### Modificateurs {#modifiers}

Les modificateurs permettent un meilleur contrôle sur la manière dont les composants de la date sont formatés :

| Modificateur | Description               |
| ------------ | ------------------------- |
| `z`          | Remplissage avec zéro     |
| `s`          | Représentation textuelle courte |
| `l`          | Représentation textuelle longue  |
| `p`          | Nombre compressé          |
| `d`          | Décimal (format par défaut)  |

Ceux-ci peuvent être combinés pour créer une grande variété de masques de date.

## Localisation du format de date {#date-format-localization}

Le `MaskedDateField` s'adapte aux formats de date régionaux en définissant la locale appropriée. Cela garantit que les dates sont affichées et analysées de manière à correspondre aux attentes de l'utilisateur.

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

Le `MaskedDateField` analyse l'entrée de l'utilisateur en fonction du masque de date défini. Il accepte les entrées numériques complètes et abrégées avec ou sans délimiteurs, permettant une entrée flexible tout en garantissant des dates valides. Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Mz/%Dz/%Yz` pour mois/jour/année). Ce format détermine comment les séquences numériques sont interprétées.

Par exemple, en supposant qu'aujourd'hui soit le `15 septembre 2012`, voici comment diverses entrées seraient interprétées :

### Exemples de scénarios d'analyse {#example-parsing-scenarios}

| Entrée                               | YMD (ISO)                                                                                                                                                                                           | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | Un chiffre unique est toujours interprété comme un numéro de jour dans le mois courant, donc cela serait le 1er septembre 2012.                                                                     | Même que YMD                                                                         | Même que YMD                                                                                                                  |
| <div align="center">`12`</div>        | Deux chiffres sont toujours interprétés comme un numéro de jour dans le mois courant, donc cela serait le 12 septembre 2012.                                                                       | Même que YMD                                                                         | Même que YMD                                                                                                                  |
| <div align="center">`112`</div>       | Trois chiffres sont interprétés comme un numéro de mois à un chiffre suivi d'un numéro de jour à deux chiffres, donc cela serait le 12 janvier 2012.                                                | Même que YMD                                                                         | Trois chiffres sont interprétés comme un numéro de jour à un chiffre suivi d'un numéro de mois à deux chiffres, donc cela serait le 1er décembre 2012. |
| <div align="center">`1004`</div>      | Quatre chiffres sont interprétés comme MMDD, donc cela serait le 4 octobre 2012.                                                                                                                | Même que YMD                                                                         | Quatre chiffres sont interprétés comme DDMM, donc cela serait le 10 avril 2012.                                                         |
| <div align="center">`020304`</div>    | Six chiffres sont interprétés comme YYMMDD, donc cela serait le 4 mars 2002.                                                                                                                    | Six chiffres sont interprétés comme MMDDYY, donc cela serait le 3 février 2004.            | Six chiffres sont interprétés comme DDMMYY, donc cela serait le 2 mars 2004.                                                         |
| <div align="center">`8 chiffres`</div> | Huit chiffres sont interprétés comme YYYYMMDD. Par exemple, `20040612` est le 12 juin 2004.                                                                                                        | Huit chiffres sont interprétés comme MMDDYYYY. Par exemple, `06122004` est le 12 juin 2004. | Huit chiffres sont interprétés comme DDMMYYYY. Par exemple, `06122004` est le 6 décembre 2004.                                        |
| <div align="center">`12/6`</div>      | Deux nombres séparés par un délimiteur valide sont interprétés comme MM/DD, donc cela serait le 6 décembre 2012. <br />Remarque : Tous les caractères sauf les lettres et les chiffres sont considérés comme des délimiteurs valides. | Même que YMD                                                                         | Deux nombres séparés par n'importe quel délimiteur sont interprétés comme DD/MM, donc cela serait le 12 juin 2012.                               |
| <div align="center">`3/4/5`</div>     | 5 avril 2012                                                                                                                                                                                      | 4 mars 2005                                                                       | 3 avril 2005                                                                                                                 |

## Définir des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage de dates autorisées dans un `MaskedDateField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Les entrées en dehors de la plage définie seront considérées comme invalides.

## Restaurer la valeur {#restoring-the-value}

Le `MaskedDateField` comprend une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini ou original. Cela est utile pour annuler une saisie utilisateur ou réinitialiser à une date par défaut.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Programmatiquement**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins d'être remplacée par un écouteur d'événements)

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

Ce modèle garantit que seules les valeurs correspondant au format `MM/DD/YYYY` (deux chiffres, slash, deux chiffres, slash, quatre chiffres) sont considérées comme valides.

:::tip Format d'Expression Régulière
Le motif doit suivre la syntaxe RegExp de JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Remarques sur la gestion des entrées
Le champ tente d'analyser et de formater les entrées de date numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours saisir manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou impossible à analyser (par exemple, `99/99/9999`), elle peut passer les vérifications de motif mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans votre logique d'application, même si un motif d'expression régulière est défini, pour garantir que la date est à la fois correctement formatée et significative.
::::

## Sélecteur de date {#date-picker}

Le `MaskedDateField` comprend un sélecteur de calendrier intégré qui permet aux utilisateurs de sélectionner une date visuellement, plutôt que de la saisir. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont requises.

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

Utilisez `setIconVisible()` pour afficher ou cacher l'icône de calendrier à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, clique, appuie sur Entrée ou sur les touches fléchées) :

```java
picker.setAutoOpen(true);
```

:::tip Forcer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une date qu'en utilisant le sélecteur de calendrier (et ne pas en taper manuellement une), combinez les deux paramètres suivants :

```java
dateField.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction utilisateur
dateField.setAllowCustomValue(false);    // Désactive l'entrée de texte manuelle
```

Cette configuration garantit que toutes les saisies de date viennent par l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse provenant des entrées saisies.
:::

### Ouvrir manuellement le calendrier {#manually-open-the-calendar}

Pour ouvrir le calendrier par programme :

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

Le `MaskedDateFieldSpinner` étend [`MaskedDateField`](#basics) en ajoutant des contrôles de sélection qui permettent aux utilisateurs d'incrémenter ou de décrémenter la date à l'aide des flèches ou des boutons de l'interface utilisateur. Il propose un style d'interaction plus guidé, particulièrement utile dans les applications de style bureau.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Caractéristiques clés {#key-features}

- **Parcours de date interactif :**  
  Utilisez les flèches ou les boutons de défilement pour incrémenter ou décrémenter la valeur de la date.

- **Unité de pas personnalisable :**  
  Choisissez quelle partie de la date modifier à l'aide de `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Les options incluent `DAY`, `WEEK`, `MONTH` et `YEAR`.

- **Limites min/max :**  
  Hérite du support pour les dates minimales et maximales autorisées via `setMin()` et `setMax()`.

- **Sortie formatée :**  
  Entièrement compatible avec les masques et les paramètres de localisation de `MaskedDateField`.

### Exemple : Configurer l'incrémentation hebdomadaire {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Cela fait en sorte que chaque pas de défilement avance ou recule la date d'une semaine.

## Style {#styling}

<TableBuilder name="MaskedDateField" />
