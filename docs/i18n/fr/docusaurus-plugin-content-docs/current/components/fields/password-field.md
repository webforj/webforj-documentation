---
sidebar_position: 30
title: PasswordField
slug: passwordfield
description: A single-line input component for securely entering and masking password data.
sidebar_class_name: updated-content
_i18n_hash: ca055ca343a756152533eb1ab3ec5c8c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/PasswordField" top='true'/>

<ParentLink parent="Field" />

Le composant `PasswordField` permet aux utilisateurs de saisir un mot de passe en toute sécurité. Il est affiché comme un éditeur de texte sur une seule ligne où le texte saisi est obscurci, typiquement remplacé par des symboles comme des astérisques (”*”) ou des points (”•”). Le symbole exact peut varier en fonction du navigateur et du système d'exploitation.

<ComponentDemo 
path='/webforj/passwordfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/passwordfield/PasswordFieldView.java'
/>

## Valeur du champ {#field-value}

Le composant `PasswordField` stocke et récupère sa valeur en tant que simple `String`, similaire à un `TextField`, mais avec un rendu visuel obscurci pour cacher les caractères.

Vous pouvez récupérer la valeur actuelle en utilisant :

```java
passwordField.getValue();
```

:::warning données sensibles
Bien que le champ masque visuellement le contenu, la valeur retournée par `getValue()` est toujours une chaîne simple. Soyez attentif à cela lorsque vous gérez des données sensibles et cryptez ou transformez-les avant de les stocker.
:::

Pour définir ou réinitialiser la valeur par programmation :

```java
passwordField.setValue("MySecret123!");
```

Si aucun valeur n'a été saisie par l'utilisateur et qu'aucune valeur par défaut n'est définie, le champ renverra une chaîne vide (`""`).

Ce comportement imite celui de l'élément HTML natif `<input type="password">`, où la propriété `value` contient l'entrée actuelle.

## Utilisations {#usages}

Le `PasswordField` est mieux utilisé dans des scénarios où la capture ou la gestion d'informations sensibles, telles que des mots de passe ou d'autres données confidentielles, est essentielle pour votre application. Voici quelques exemples de cas d'utilisation du `PasswordField` :

1. **Authentification et inscription des utilisateurs** : Les champs de mot de passe sont cruciaux dans les applications impliquant des processus d'authentification ou d'inscription des utilisateurs, où une saisie sécurisée du mot de passe est requise.

2. **Saisies de formulaires sécurisées** : Lors de la conception de formulaires nécessitant l'entrée d'informations sensibles, telles que des détails de carte de crédit ou des numéros d'identification personnelle (PIN), l'utilisation d'un `PasswordField` sécurise l'entrée de ces données.

3. **Gestion de compte et paramètres de profil** : Les champs de mot de passe sont précieux dans les applications impliquant la gestion de compte ou les paramètres de profil, permettant aux utilisateurs de changer ou de mettre à jour leur mot de passe en toute sécurité.

## Visibilité du mot de passe {#password-visibility}

Les utilisateurs peuvent révéler la valeur du `PasswordField` en cliquant sur l'icône de révélation. Cela permet aux utilisateurs de vérifier ce qu'ils ont saisi ou de copier les informations dans leur presse-papiers. Cependant, pour les environnements à haute sécurité, vous pouvez utiliser `setPasswordReveal()` pour supprimer l'icône de révélation et empêcher les utilisateurs de voir la valeur. Vous pouvez vérifier si un utilisateur peut utiliser l'icône de révélation pour afficher la valeur avec la méthode `isPasswordReveal()`.

## Correspondance des motifs {#pattern-matching}

Il est fortement recommandé d'appliquer un motif d'expression régulière au `PasswordField` en utilisant la méthode `setPattern()`. Cela vous permet d'imposer des règles de caractères et des exigences structurelles, forçant les utilisateurs à créer des identifiants sécurisés et conformes. La correspondance de motifs est particulièrement utile lors de l'application de règles de mots de passe forts, comme l'exigence d'un mélange de lettres majuscules et minuscules, de chiffres et de symboles.

Le motif doit suivre la syntaxe d'une [expression régulière JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), telle qu'interprétée par le navigateur. Le drapeau `u` (Unicode) est utilisé en interne pour garantir la validation sur tous les points de code Unicode. Ne **pas** inclure de barres obliques (`/`) autour du motif.

Dans le snippet suivant, le motif exige au moins une lettre minuscule, une lettre majuscule, un chiffre et une longueur minimale de 8 caractères.

```java
passwordField.setPattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
```

Si le motif est manquant ou invalide, aucune validation ne sera appliquée.

:::tip
Utilisez `setLabel()` pour fournir une étiquette claire décrivant l'objectif du champ de mot de passe. Pour aider les utilisateurs à comprendre les exigences du mot de passe, utilisez `setHelperText()` pour afficher des instructions ou des règles directement sous le champ.
:::

## Longueur minimale et maximale {#minimum-and-maximum-length}

Vous pouvez contrôler la longueur autorisée de l'entrée du mot de passe en utilisant `setMinLength()` et `setMaxLength()` sur le `PasswordField`.

La méthode `setMinLength()` définit le nombre minimum de caractères qu'un utilisateur doit entrer dans le champ pour passer la validation. Cette valeur doit être un entier non négatif et ne doit pas dépasser la longueur maximale si celle-ci est définie.

```java
passwordField.setMinLength(8); // Minimum 8 caractères
```

Si l'utilisateur saisit moins de caractères que le minimum, l'entrée échoue à la validation de contrainte. Cette validation n'est appliquée que lorsque la valeur du champ est modifiée par l'utilisateur.

La méthode `setMaxLength()` définit le nombre maximal de caractères autorisés dans le champ. La valeur doit être égale ou supérieure à 0. Si elle n'est pas définie ou est définie sur une valeur invalide, le champ n'a pas de limite de caractères supérieure.

```java
passwordField.setMaxLength(20); // Maximum 20 caractères
```

Si l'entrée dépasse la limite de caractères maximale, le champ échoue à la validation de contrainte. Comme pour le minimum, cette règle ne s'applique que lorsque l'utilisateur met à jour la valeur du champ.

:::tip
Utilisez à la fois `setMinLength()` et `setMaxLength()` ensemble pour créer des limites d'entrée efficaces. Consultez la [documentation sur les contraintes de longueur HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input#minlength) pour plus de références.
:::

## Meilleures pratiques {#best-practices}

Étant donné que le composant `PasswordField` est souvent associé à des informations sensibles, considérez les meilleures pratiques suivantes lors de l'utilisation du `PasswordField` :

- **Fournir un retour sur la force du mot de passe** : Intégrez des indicateurs de force du mot de passe ou des mécanismes de retour pour aider les utilisateurs à créer des mots de passe forts et sécurisés. Évaluez des facteurs tels que la longueur, la complexité, et un mélange de lettres majuscules et minuscules, de chiffres et de caractères spéciaux.

- **Faire respecter le stockage des mots de passe** : Ne jamais stocker les mots de passe en texte clair. Au lieu de cela, mettez en œuvre des mesures de sécurité appropriées pour gérer et stocker les mots de passe en toute sécurité dans votre application. Utilisez des algorithmes de cryptage conformes aux normes de l'industrie pour les mots de passe et d'autres données sensibles.

- **Confirmation du mot de passe** : Incluez un champ de confirmation supplémentaire lorsqu'un utilisateur change ou crée un mot de passe. Cette mesure aide à minimiser le risque de fautes de frappe et s'assure que les utilisateurs saisissent avec précision le mot de passe souhaité.

- **Permettre la réinitialisation du mot de passe** : Si votre application implique des comptes utilisateurs, fournissez une option permettant aux utilisateurs de réinitialiser leur mot de passe. Cela pourrait prendre la forme d'une fonctionnalité "Mot de passe oublié" qui initie un processus de récupération du mot de passe.

- **Accessibilité** : Configurez le `PasswordField` avec l'accessibilité à l'esprit, afin qu'il respecte les normes d'accessibilité telles que la fourniture d'étiquettes appropriées et la compatibilité avec les technologies d'assistance.
