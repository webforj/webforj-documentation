---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: bfaa13bee2b1c6dd1c88c8af989a6532
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Le `MaskedTimeField` est un champ de texte qui permet aux utilisateurs d'entrer des heures sous forme de **nombres** et formate automatiquement l'entrée en fonction d'un masque défini lorsque le champ perd le focus. Le masque spécifie le format horaire attendu, guidant à la fois l'entrée et l'affichage. Le composant prend en charge le parsing flexible, la validation, la localisation et la restauration des valeurs pour une gestion cohérente du temps.

<!-- INTRO_END -->

## Bases {#basics}

:::tip Vous cherchez un champ de saisie de date ?
Le `MaskedTimeField` est conçu pour une saisie **uniquement horaire**. Si vous cherchez un composant pour gérer des **dates** avec un formatage similaire basé sur un masque, jetez un œil au [`MaskedDateField`](./datefield.md).
:::

Le `MaskedTimeField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour les changements de valeur.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Règles de masque {#mask-rules}

Le `MaskedTimeField` utilise des indicateurs de format pour définir comment le temps est analysé et affiché. Chaque indicateur de format commence par un `%` suivi d'une lettre représentant une composante horaire.

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

Les modificateurs affinent l'affichage des composants horaires :

| Modificateur | Description                 |
|--------------|-----------------------------|
| `z`          | Remplissage avec des zéros  |
| `s`          | Représentation textuelle courte |
| `l`          | Représentation textuelle longue |
| `p`          | Nombre compressé             |
| `d`          | Décimal (format par défaut)  |

Cela permet un formatage horaire flexible et adapté aux différentes localisations.

## Localisation du format horaire {#time-format-localization}

Le `MaskedTimeField` prend en charge la localisation en définissant la locale appropriée. Cela garantit que l'entrée et la sortie de temps correspondent aux conventions régionales.

```java
field.setLocale(Locale.GERMANY);
```

Cela affecte l'affichage des indicateurs AM/PM, la gestion des séparateurs et l'analyse des valeurs.

## Logique d'analyse {#parsing-logic}

Le `MaskedTimeField` analyse l'entrée utilisateur en fonction du masque horaire défini. Il accepte à la fois des entrées numériques complètes et abrégées, avec ou sans délimiteurs, permettant une saisie flexible tout en garantissant des heures valides. Le comportement d'analyse dépend de l'ordre de format défini par le masque (par exemple, `%Hz:%mz` pour heure/minute). Ce format détermine comment les séquences numériques sont interprétées.

### Scénarios d'analyse exemple {#example-parsing-scenarios}

| Saisie | Masque        | Interprété Comme |
|--------|---------------|------------------|
| `900`  | `%Hz:%mz`     | `09:00`          |
| `1345` | `%Hz:%mz`     | `13:45`          |
| `0230` | `%hz:%mz %p`  | `02:30 AM`       |
| `1830` | `%hz:%mz %p`  | `06:30 PM`       |

## Réglage des contraintes min/max {#setting-minmax-constraints}

Vous pouvez restreindre la plage horaire autorisée dans un `MaskedTimeField` en utilisant les méthodes `setMin()` et `setMax()` :

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Les deux méthodes acceptent des valeurs de type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Les entrées en dehors de la plage définie sont considérées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedTimeField` inclut une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini ou original. Cela peut être utile pour annuler des modifications ou revenir à une heure par défaut.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Par programme**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins d'être remplacée par un écouteur d'événements)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Modèles de validation {#validation-patterns}

Vous pouvez appliquer des règles de validation côté client à l'aide d'expressions régulières avec la méthode `setPattern()` :

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Ce modèle garantit que seules les valeurs correspondant au format `HH:mm` (deux chiffres, deux points, deux chiffres) sont considérées comme valides.

:::tip Format d'Expression Régulière
Le modèle doit suivre la syntaxe des expressions régulières JavaScript comme documenté [ici](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notes sur le traitement des entrées
Le champ tente d'analyser et de formater les entrées horaires numériques en fonction du masque actuel. Cependant, les utilisateurs peuvent toujours entrer manuellement des valeurs qui ne correspondent pas au format attendu. Si l'entrée est syntaxiquement valide mais sémantiquement incorrecte ou intraitable (par exemple `99:99`), elle peut passer les vérifications de modèle, mais échouer à la validation logique.
Vous devez toujours valider la valeur d'entrée dans la logique de votre application, même si un modèle d'expression régulière est déclaré, pour garantir que l'heure est à la fois correctement formatée et significative.
:::

## Sélecteur de temps {#time-picker}

Le `MaskedTimeField` inclut un sélecteur de temps intégré qui permet aux utilisateurs de sélectionner une heure visuellement, plutôt que de la taper. Cela améliore l'utilisabilité pour les utilisateurs moins techniques ou lorsque des entrées précises sont requises.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Accéder au sélecteur {#accessing-the-picker}

Vous pouvez accéder au sélecteur de temps en utilisant `getPicker()` :

```java
TimePicker picker = field.getPicker();
```

### Afficher/cacher l'icône de sélection {#showhide-the-picker-icon}

Utilisez `setIconVisible()` pour afficher ou cacher l'icône de l'horloge à côté du champ :

```java
picker.setIconVisible(true); // affiche l'icône
```

### Comportement d'ouverture automatique {#auto-open-behavior}

Vous pouvez configurer le sélecteur pour qu'il s'ouvre automatiquement lorsque l'utilisateur interagit avec le champ (par exemple, en cliquant, en appuyant sur Entrée ou sur les flèches) :

```java
picker.setAutoOpen(true);
```

:::tip Imposer la sélection via le sélecteur
Pour garantir que les utilisateurs ne peuvent sélectionner une heure qu'en utilisant le sélecteur (et non en la saisissant manuellement), combinez les deux paramètres suivants :

```java
field.getPicker().setAutoOpen(true); // Ouvre le sélecteur lors de l'interaction de l'utilisateur
field.setAllowCustomValue(false);    // Désactive la saisie manuelle de texte
```

Cette configuration garantit que toutes les saisies horaires proviennent de l'interface utilisateur du sélecteur, ce qui est utile lorsque vous souhaitez un contrôle strict du format et éliminer les problèmes d'analyse provenant de saisies manuelles.
:::

### Ouvrir manuellement le sélecteur {#manually-open-the-picker}

Pour ouvrir le sélecteur de temps par programme :

```java
picker.open();
```

Ou utilisez l'alias :

```java
picker.show(); // identique à open()
```

### Réglage de l'intervalle du sélecteur {#setting-the-picker-step}

Vous pouvez définir l'intervalle entre les heures sélectionnables dans le sélecteur en utilisant `setStep()`. Cela vous permet de contrôler la granularité des options horaires — idéal pour des scénarios comme la planification par blocs de 15 minutes.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Contrainte d'intervalle
L'intervalle doit diviser uniformément une heure ou une journée complète. Sinon, une exception sera lancée.
:::

Cela garantit que la liste déroulante contient des valeurs prévisibles et uniformément espacées comme `09:00`, `09:15`, `09:30`, etc.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

Le `MaskedTimeFieldSpinner` étend le [`MaskedTimeField`](#basics) en ajoutant des contrôles de sélection qui permettent aux utilisateurs d'incrémenter ou de décrémenter le temps en utilisant les flèches ou les boutons de l'interface utilisateur. Il offre un style d'interaction plus guidé, particulièrement utile dans les applications de type bureau.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Caractéristiques clés {#key-features}

- **Incrémentation interactive du temps :**  
  Utilisez les flèches ou les boutons de rotation pour incrémenter ou décrémenter la valeur horaire.

- **Unité de rotation personnalisable :**  
  Choisissez quelle partie de l'heure modifier en utilisant `setSpinField()` :

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Les options incluent `HOUR`, `MINUTE`, `SECOND` et `MILLISECOND`.

- **Limites min/max :**  
  Hérite le support pour les heures minimales et maximales autorisées en utilisant `setMin()` et `setMax()`.

- **Sortie formatée :**  
  Entièrement compatible avec les masques et les paramètres de localisation du `MaskedTimeField`.

### Exemple : Configurer l'incrémentation par heure {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Stylisation {#styling}

<TableBuilder name="MaskedTimeField" />
