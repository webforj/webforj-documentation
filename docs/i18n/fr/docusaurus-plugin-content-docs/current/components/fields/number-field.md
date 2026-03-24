---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: e1cde7099182ddabd898e0c5391fe8b7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

Le composant `NumberField` accepte des entrées numériques et rejette automatiquement les valeurs invalides. Il prend en charge les limites min et max, les intervalles de pas et le texte de placeholder.

<!-- INTRO_END -->

## Utilisation de `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

Le `NumberField` étend la classe partagée `Field`, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `NumberField` avec un label et un texte de placeholder.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Valeur du champ {#field-value}

Le composant `NumberField` stocke sa valeur en tant que `Double`, permettant un traitement précis des entiers et des nombres décimaux.

### Obtention de la valeur actuelle {#getting-the-current-value}

Vous pouvez récupérer la valeur numérique saisie par l'utilisateur en utilisant :

```java
Double currentValue = numberField.getValue();
```

### Définir une nouvelle valeur {#setting-a-new-value}

Pour définir le champ par programmation :

```java
numberField.setValue(42.5);
```

Si aucune valeur n'a été saisie et qu'aucune valeur par défaut n'est définie, `getValue()` renverra `null`.

:::tip
Bien que le champ soit conçu pour accepter uniquement des entrées numériques valides, gardez à l'esprit que la valeur sous-jacente peut être nulle. Testez toujours pour null avant d'utiliser le résultat.
:::

## Usages {#usages}

Le `NumberField` est idéal dans les scénarios où la capture, l'affichage ou la manipulation de données numériques est essentielle pour votre application. Voici quelques exemples de quand utiliser le `NumberField` :

1. **Formulaires de saisie numérique** : Lors de la conception de formulaires nécessitant des saisies numériques, l'utilisation d'un `NumberField` simplifie le processus de saisie pour les utilisateurs. Cela est particulièrement utile pour les applications qui collectent des données utilisateur ou nécessitent des valeurs numériques.

2. **Analyse de données et calculs** : Un `NumberField` est particulièrement précieux dans les applications impliquant l'analyse de données, des calculs ou des opérations mathématiques. Ils permettent aux utilisateurs d'entrer ou de manipuler des valeurs numériques avec précision.

3. **Applications financières et de budgétisation** : Les applications impliquant des calculs financiers, la budgétisation ou le suivi des dépenses nécessitent souvent des saisies numériques précises. Un `NumberField` garantit l'entrée correcte des chiffres financiers.

4. **Mesure et conversion d'unités** : Dans les applications qui traitent des mesures ou des conversions d'unités, le `NumberField` est idéal pour saisir des valeurs numériques avec des unités telles que longueur, poids ou volume.

## Valeur min et max {#min-and-max-value}

Avec la méthode `setMin()`, vous pouvez spécifier la valeur minimale acceptable dans le champ numérique. Si un utilisateur saisit une valeur inférieure à ce seuil, le composant échouera à la validation de contrainte et fournira un retour d'information approprié.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimum autorisé : 0.0
```

Séparément, la méthode `setMax()` vous permet de définir la valeur maximale acceptable. Si un utilisateur saisit une valeur supérieure à cette limite, l'entrée sera rejetée. Lorsque les valeurs minimales et maximales sont définies, la valeur maximale doit être supérieure ou égale à la valeur minimale.

```java
numberField.setMax(100.0); // Maximum autorisé : 100.0
```

Dans cette configuration, saisir une valeur comme -5 ou 150 serait invalide, tandis que les valeurs comprises entre 0 et 100 sont acceptées.

## Granularité {#granularity}

Vous pouvez utiliser la méthode `setStep()` pour spécifier la granularité à laquelle la valeur doit adhérer lors de l'utilisation des flèches pour modifier la valeur. Cela incrémentera ou décrémentera la valeur du composant d'un certain pas à chaque fois. Cela ne s'applique pas lorsque l'utilisateur saisit directement une valeur, mais uniquement lorsqu'il interagit avec le `NumberField` en utilisant les flèches.

## Texte de placeholder {#placeholder-text}

Vous pouvez définir un texte de placeholder pour le `NumberField` en utilisant la méthode `setPlaceholder()`. Le texte de placeholder est affiché lorsque le champ est vide, aidant à inciter l'utilisateur à entrer une saisie appropriée dans le `NumberField`.

:::tip Donnez un contexte clair pour l'exactitude
Si l'entrée numérique se rapporte à une unité de mesure spécifique ou a un contexte particulier, fournissez un étiquetage clair ou des informations supplémentaires pour guider les utilisateurs et garantir une saisie précise.
:::

## Meilleures pratiques {#best-practices}

Pour garantir une intégration fluide et une expérience utilisateur optimale, considérez les meilleures pratiques suivantes lors de l'utilisation du `NumberField` :

- **Accessibilité** : Utilisez le composant `NumberField` en tenant compte de l'accessibilité, en respectant les normes d'accessibilité telles que l'étiquetage approprié, le support de la navigation au clavier et la compatibilité avec les technologies d'assistance. Assurez-vous que les utilisateurs handicapés peuvent interagir efficacement avec le `NumberField`.

- **Utilisez les boutons d'incrément/décrément** : Si cela est approprié pour votre application, envisagez d'utiliser des boutons d'incrément et de décrément avec le `NumberField`. Cela permet aux utilisateurs d'ajuster la valeur numérique par un incrément ou décrément spécifique par un simple clic.
