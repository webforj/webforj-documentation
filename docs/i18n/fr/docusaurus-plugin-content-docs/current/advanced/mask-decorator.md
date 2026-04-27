---
sidebar_position: 16
title: MaskDecorator
sidebar_class_name: new-content
_i18n_hash: 30ecd8eeaa79a3e5f963e319373d1378
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/MaskDecorator" top='true'/>

`MaskDecorator` est une classe utilitaire statique permettant d'appliquer des masques sur des chaînes, des nombres, des dates et des heures en dehors d'un champ de saisie. Elle utilise la même syntaxe de masque que les [composants de champ masqués de webforJ](/docs/components/fields/masked/overview), ce qui facilite le formatage et l'analyse des valeurs de manière cohérente—que ce soit dans une étiquette d'affichage, un renderer de [`Table`](/docs/components/table/overview), ou tout autre emplacement dans votre application.

Utilisez `MaskDecorator` lorsque vous devez formater des valeurs de manière programmatique pour l'affichage plutôt que pour une saisie interactive, comme dans des renderers de cellules de tableau, des étiquettes en lecture seule, des rapports exportés, ou tout contexte où un champ de formulaire n'est pas approprié. Pour un formatage interactif au fur et à mesure que l'utilisateur tape, utilisez un composant de champ masqué à la place.

## Masquage des chaînes {#masking-strings}

Utilisez `forString()` pour appliquer un masque de caractères à une valeur de chaîne ordinaire :

```java
String result = MaskDecorator.forString("abc123", "AAA-000");
// → "ABC-123"
```

Le masque contrôle quels caractères sont acceptés à chaque position.

### Caractères de masque {#string-mask-characters}

| Caractère | Description |
|-----------|-------------|
| `X`       | N'importe quel caractère imprimable |
| `a`       | N'importe quel caractère alphabétique |
| `A`       | N'importe quel caractère alphabétique ; les lettres minuscules sont converties en majuscules |
| `0`       | N'importe quel chiffre (0-9) |
| `z`       | N'importe quel chiffre ou lettre |
| `Z`       | N'importe quel chiffre ou lettre ; les lettres minuscules sont converties en majuscules |

Tout autre caractère dans le masque est traité comme un littéral et est inséré tel quel dans la sortie. Les caractères invalides dans l'entrée sont silencieusement ignorés, les entrées courtes sont complétées par des espaces, et les entrées longues sont tronquées pour s'adapter au masque.

### Exemples {#string-examples}

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forString("a1b2c3",     "A0A 0A0");         // → "A1B 2C3"
MaskDecorator.forString("1234",       "ZZZZ-0000");        // → "1234-    " (complété)
```

## Masquage des nombres {#masking-numbers}

Utilisez `forNumber()` pour formater une valeur numérique en utilisant un masque de nombre :

```java
String result = MaskDecorator.forNumber(1234567.89, "#,###,##0.00");
// → "1,234,567.89"
```

### Caractères de masque {#number-mask-characters}

| Caractère | Description |
|-----------|-------------|
| `0`       | Remplacé toujours par un chiffre (0-9) |
| `#`       | Supprime les zéros à gauche. Remplacé par le caractère de remplissage à gauche du point décimal. Pour les chiffres à droite, remplacé par un espace ou un zéro. Sinon, remplacé par un chiffre |
| `,`       | Utilisé comme séparateur de groupe. Remplacé par le caractère de remplissage si aucun chiffre n'a encore été placé ; sinon, affiché comme une virgule |
| `-`       | Affiche `-` si le nombre est négatif ; remplacé par le caractère de remplissage si positif |
| `+`       | Affiche `+` si positif, ou `-` si négatif |
| `$`       | Résulte toujours en un signe dollar |
| `(`       | Insère `(` si le nombre est négatif ; remplacé par le caractère de remplissage si positif |
| `)`       | Insère `)` si le nombre est négatif ; remplacé par le caractère de remplissage si positif |
| `CR`      | Insère `CR` pour les nombres négatifs ; deux espaces pour les nombres positifs |
| `DR`      | Insère `CR` pour les nombres négatifs ; `DR` pour les nombres positifs |
| `*`       | Insère toujours un astérisque |
| `.`       | Marque le point décimal. Remplacé par le caractère de remplissage si aucun chiffre n'apparaît dans la sortie. Après le décimal, le caractère de remplissage devient un espace |
| `B`       | Devenant toujours un espace ; tout autre caractère littéral est copié tel quel |

Les caractères `-`, `+`, `$`, et `(` peuvent flotter : la première occurrence est déplacée à la dernière position où un `#` ou `,` a été remplacé par le caractère de remplissage.

:::info Comportement d'arrondi
`forNumber()` arrondit les valeurs pour correspondre à la précision décimale dans le masque. Par exemple, `MaskDecorator.forNumber(12.34567, "###0.00")` produit `"  12.35"`.
:::

### Exemples {#number-examples}

```java
MaskDecorator.forNumber(1234.5,    "###,##0.00");  // → "  1,234.50"
MaskDecorator.forNumber(-9876.0,   "###,##0.00-"); // → "  9,876.00-"
MaskDecorator.forNumber(42.0,      "$###,##0.00"); // → "     $42.00"
MaskDecorator.forNumber(0.5,       "#0.000");      // → " 0.500"
```

## Masquage des dates {#masking-dates}

Utilisez `forDate()` pour formater une valeur `LocalDate` avec un masque de date :

```java
LocalDate date = LocalDate.of(2025, 7, 4);
String result = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
// → "07/04/2025"
```

Utilisez `parseDate()` pour analyser une chaîne de date masquée en un `LocalDate` :

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl");
// → LocalDate.of(2025, 7, 4)
```

Un sursaut aware de la locale est disponible lors de l'analyse des chaînes contenant des références du numéro de semaine :

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl", Locale.US);
```

### Indicateurs de format de date {#date-format-indicators}

| Format | Description |
|--------|-------------|
| `%Y`   | Année       |
| `%M`   | Mois        |
| `%D`   | Jour        |

### Modificateurs {#date-modifiers}

Un modificateur optionnel suit immédiatement l'indicateur de format :

| Modificateur | Description               |
|--------------|---------------------------|
| `z`          | Rembourré à zéro         |
| `s`          | Représentation texte courte|
| `l`          | Représentation texte longue|
| `p`          | Nombre compressé          |
| `d`          | Décimal (format par défaut) |

### Exemples {#date-examples}

```java
LocalDate d = LocalDate.of(2025, 3, 5);

MaskDecorator.forDate(d, "%Mz/%Dz/%Yl");  // → "03/05/2025"
MaskDecorator.forDate(d, "%Dz.%Mz.%Yz");  // → "05.03.25"
MaskDecorator.forDate(d, "%Dl, %Ml %Dz");  // → "Wednesday, March 05"
MaskDecorator.forDate(d, "%Yl-%Mz-%Dz");  // → "2025-03-05"
```

## Masquage des heures {#masking-times}

Utilisez `forTime()` pour formater une valeur `LocalTime` avec un masque de temps :

```java
LocalTime time = LocalTime.of(14, 30, 0);
String result = MaskDecorator.forTime(time, "%Hz:%mz");
// → "14:30"
```

Utilisez `parseTime()` pour analyser une chaîne de temps masquée en un `LocalTime` :

```java
LocalTime time = MaskDecorator.parseTime("14:30", "%Hz:%mz");
// → LocalTime.of(14, 30)
```

Un sursaut aware de la locale est disponible lors de l'analyse des chaînes contenant des valeurs AM/PM localisées :

```java
LocalTime time = MaskDecorator.parseTime("02:30 pm", "%hz:%mz %p", Locale.US);
```

### Indicateurs de format de temps {#time-format-indicators}

| Format | Description          |
|--------|----------------------|
| `%H`   | Heure (horloge 24 heures) |
| `%h`   | Heure (horloge 12 heures) |
| `%m`   | Minute               |
| `%s`   | Seconde              |
| `%p`   | am/pm                |

### Modificateurs {#time-modifiers}

Les masques de temps utilisent les mêmes modificateurs que les masques de date. Voir [Modificateurs de date](#date-modifiers).

### Exemples {#time-examples}

```java
LocalTime t = LocalTime.of(9, 5, 30);

MaskDecorator.forTime(t, "%Hz:%mz:%sz");  // → "09:05:30"
MaskDecorator.forTime(t, "%hz:%mz %p");   // → "09:05 am"
MaskDecorator.forTime(t, "%Hz%mz");       // → "0905"
```

## Masquage des dates et heures {#masking-datetime}

Utilisez `forDateTime()` pour formater une valeur `LocalDateTime` en utilisant un masque combiné date et heure :

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);
String result = MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");
// → "07/04/2025 14:30"
```

### Indicateurs de format {#datetime-format-indicators}

`forDateTime()` prend en charge tous les indicateurs de format de date et d'heure dans n'importe quelle combinaison. Voir [Indicateurs de format de date](#date-format-indicators) et [Indicateurs de format de temps](#time-format-indicators) pour la liste complète.

### Modificateurs {#datetime-modifiers}

Tous les modificateurs décrits dans [Modificateurs de date](#date-modifiers) s'appliquent à la fois aux parties date et heure d'un masque combiné.

### Exemples {#datetime-examples}

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);

MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");      // → "07/04/2025 14:30"
MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz:%sz");  // → "07/04/2025 14:30:00"
MaskDecorator.forDateTime(dt, "%Dz.%Mz.%Yz %hz:%mz %p");  // → "04.07.25 02:30 pm"
```

## Gestion des résultats nuls {#handling-null-results}

:::warning
Tous les méthodes `for*()` et `parse*()` retournent `null` si l'entrée est invalide ou ne peut pas être analysée. Vérifiez toujours que le résultat n'est pas nul avant de l'utiliser dans la logique de votre application.
:::

```java
String formatted = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
if (formatted != null) {
  label.setText(formatted);
}
```
