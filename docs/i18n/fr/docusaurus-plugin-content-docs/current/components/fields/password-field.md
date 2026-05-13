---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
_i18n_hash: b0641475acf187af7c45d6786506010d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

Le composant `PasswordField` permet aux utilisateurs de saisir un mot de passe en toute sécurité. Il est affiché comme un éditeur de texte à une seule ligne où le texte saisie est obscurci, généralement remplacé par des symboles comme des astérisques ("*") ou des points ("•"). Le symbole exact peut varier en fonction du navigateur et du système d'exploitation.

<!-- INTRO_END -->

## Utilisation de `PasswordField` {#using-passwordfield}

<ParentLink parent="Field" />

`PasswordField` étend la classe `Field` partagée, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant crée un `PasswordField` avec un label et un texte d'espace réservé.

<ComponentDemo
path='/webforj/passwordfield'
files={['src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java']}
/>

## Valeur du champ {#field-value}

Le composant `PasswordField` stocke et récupère sa valeur en tant que chaîne de caractères simple (`String`), similaire à un `TextField`, mais avec un rendu visuel obscurci pour cacher les caractères.

Vous pouvez récupérer la valeur actuelle en utilisant :

```java
passwordField.getValue();
```

:::warning données sensibles
Bien que le champ masque visuellement le contenu, la valeur retournée par `getValue()` est toujours une chaîne simple. Soyez prudent lorsque vous traitez des données sensibles et cryptez ou transformez-les avant de les stocker.
:::

Pour définir ou réinitialiser la valeur de manière programmatique :

```java
passwordField.setValue("MonSecret123!");
```

Si aucune valeur n'a été saisie par l'utilisateur et qu'aucune valeur par défaut n'est définie, le champ renverra une chaîne vide (`""`).

Ce comportement imite celui de l'élément HTML natif `<input type="password">`, où la propriété `value` contient l'entrée actuelle.


## Utilisations {#usages}

Le `PasswordField` est préférable dans les scénarios où la capture ou le traitement d'informations sensibles, telles que des mots de passe ou d'autres données confidentielles, est essentiel pour votre application. Voici quelques exemples de quand utiliser le `PasswordField` :

1. **Authentification et enregistrement des utilisateurs** : Les champs de mot de passe sont cruciaux dans les applications impliquant des processus d'authentification ou d'enregistrement d'utilisateur, où une saisie sécurisée du mot de passe est requise.

2. **Saisies de formulaires sécurisés** : Lors de la conception de formulaires nécessitant la saisie d'informations sensibles, telles que des informations de carte de crédit ou des numéros d'identification personnelle (PIN), l'utilisation d'un `PasswordField` sécurise la saisie de ces données.

3. **Gestion des comptes et paramètres de profil** : Les champs de mot de passe sont précieux dans les applications impliquant la gestion de comptes ou les paramètres de profil, permettant aux utilisateurs de modifier ou de mettre à jour leurs mots de passe en toute sécurité.

## Visibilité du mot de passe {#password-visibility}

Les utilisateurs peuvent révéler la valeur du `PasswordField` en cliquant sur l'icône de révélation. Cela permet aux utilisateurs de vérifier ce qu'ils ont entré ou de copier l'information dans leur presse-papiers. Cependant, pour des environnements à haute sécurité, vous pouvez utiliser `setPasswordReveal()` pour supprimer l'icône de révélation et empêcher les utilisateurs de voir la valeur. Vous pouvez vérifier si un utilisateur peut utiliser l'icône de révélation pour afficher la valeur avec la méthode `isPasswordReveal()`.

## Correspondance de modèle {#pattern-matching}

Il est fortement recommandé d'appliquer un modèle d'expression régulière au `PasswordField` en utilisant la méthode `setPattern()`. Cela vous permet d'imposer des règles de caractères et des exigences structurelles, obligeant les utilisateurs à créer des identifiants sécurisés et conformes. La correspondance de modèle est particulièrement utile lors de l'application de règles de mot de passe strictes, comme l'exigence d'un mélange de lettres majuscules et minuscules, de chiffres et de symboles.

Le modèle doit suivre la syntaxe d'une [expression régulière JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), telle qu'interprétée par le navigateur. Le drapeau `u` (Unicode) est utilisé en interne pour garantir la validation à travers tous les points de code Unicode. Ne **pas** inclure de barres obliques (`/`) autour du modèle.

Dans l'extrait suivant, le modèle exige au moins une lettre minuscule, une lettre majuscule, un chiffre et une longueur minimale de 8 caractères.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Si le modèle est manquant ou invalide, aucune validation ne sera appliquée.

:::tip
Utilisez `setLabel()` pour fournir un label clair décrivant l'objectif du champ de mot de passe. Pour aider les utilisateurs à comprendre les exigences relatives aux mots de passe, utilisez `setHelperText()` pour afficher des conseils ou des règles directement sous le champ.
:::


## Longueur minimale et maximale {#minimum-and-maximum-length}

Vous pouvez contrôler la longueur autorisée de la saisie du mot de passe en utilisant `setMinLength()` et `setMaxLength()` sur le `PasswordField`.

La méthode `setMinLength()` définit le nombre minimum de caractères qu'un utilisateur doit entrer dans le champ pour réussir la validation. Cette valeur doit être un entier non négatif et ne doit pas dépasser la longueur maximale si celle-ci est fixée.

```java
passwordField.setMinLength(8); // Minimum 8 caractères
```

Si l'utilisateur saisit moins de caractères que le minimum, l'entrée échoue la validation de contrainte. Cette validation ne s'applique que lorsque la valeur du champ est modifiée par l'utilisateur.

La méthode `setMaxLength()` définit le nombre maximal de caractères autorisés dans le champ. La valeur doit être 0 ou plus. Si elle n'est pas définie ou est définie sur une valeur invalide, le champ n'a pas de limite supérieure de caractères.

```java
passwordField.setMaxLength(20); // Maximum 20 caractères
```

Si l'entrée dépasse la limite maximale de caractères, le champ échoue la validation de contrainte. Comme pour le minimum, cette règle ne s'applique que lorsque l'utilisateur met à jour la valeur du champ.

:::tip
Utilisez à la fois `setMinLength()` et `setMaxLength()` ensemble pour créer des limites d'entrée efficaces. Consultez la [documentation sur les contraintes de longueur HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) pour plus de références.
:::


## Meilleures pratiques {#best-practices}

Étant donné que le composant `PasswordField` est souvent associé à des informations sensibles, considérez les meilleures pratiques suivantes lors de l'utilisation du `PasswordField` :

- **Fournir des retours sur la force du mot de passe** : Incorporez des indicateurs ou des mécanismes de retour sur la force du mot de passe pour aider les utilisateurs à créer des mots de passe forts et sécurisés. Évaluez des facteurs tels que la longueur, la complexité, et un mélange de lettres majuscules et minuscules, de chiffres et de caractères spéciaux.

- **Appliquer le stockage sécurisé des mots de passe** : Ne jamais stocker des mots de passe en texte clair. Au lieu de cela, mettez en œuvre des mesures de sécurité appropriées pour manipuler et stocker les mots de passe en toute sécurité dans votre application. Utilisez des algorithmes de cryptage standard de l'industrie pour les mots de passe et d'autres données sensibles.

- **Confirmation du mot de passe** : Incluez un champ de confirmation supplémentaire lorsque l'utilisateur modifie ou crée un mot de passe. Cela aide à minimiser la probabilité d'erreurs de frappe et garantit que les utilisateurs saisissent correctement leur mot de passe souhaité.

- **Permettre la réinitialisation du mot de passe** : Si votre application implique des comptes utilisateurs, fournissez une option aux utilisateurs pour réinitialiser leur mot de passe. Cela peut être sous la forme d'une fonctionnalité "Mot de passe oublié" qui initie un processus de récupération de mot de passe.

- **Accessibilité** : Configurez le `PasswordField` en tenant compte de l'accessibilité, afin qu'il respecte les normes d'accessibilité telles que la fourniture de labels appropriés et la compatibilité avec les technologies d'assistance.
