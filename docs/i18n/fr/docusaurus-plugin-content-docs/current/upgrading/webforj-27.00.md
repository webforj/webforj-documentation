---
title: Mise à niveau vers 27.00
description: Passez de la version 26.00 à la version 27.00.
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_title_hash: f521441c4a5da00c1bc05ae7307c3644
_i18n_description_hash: 2ca050c7f4bc5f02fb7c8680550799a3
_i18n_hash: 2757e1b06263f629ef0c1e5c15785122
---
Cette documentation explique comment mettre à niveau les applications webforJ de la version 26.00 à la version 27.00.  
Voici les modifications nécessaires pour que les applications existantes continuent de fonctionner correctement.  
Comme toujours, consultez la [présentation des versions sur GitHub](https://github.com/webforj/webforj/releases) pour obtenir une liste plus complète des changements apportés entre les versions.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Contenu texte et HTML {#text-and-html-content}

Dans les versions précédentes, une valeur encadrée par `<html>` et transmise à `setText()` était traitée comme du HTML. Ce comportement est obsolète et a été supprimé dans webforJ 27.00. Le paramètre `webforj.legacyHtmlInText` permet de le contrôler :

- `true` (par défaut) : une valeur encadrée par `<html>` affiche son contenu en HTML.
- `false` : la même valeur est affichée littéralement.

Dans les deux cas, l’encadrement `<html>` n’est jamais affiché.

La première fois qu’une valeur encadrée par `<html>` est transmise à `setText()`, un avertissement est consigné. Il indique le nom du composant et l’emplacement de l’appel, afin de permettre de remplacer cet appel par `setHtml()`.

## Liaison obligatoire avec `@Size` {#required-binding-using-size-annotation}

Dans les versions antérieures à 27.00, l’utilisation de l’[annotation Jakarta](/docs/data-binding/validation/jakarta-validation) `@Size` rendait automatiquement le composant lié obligatoire.
Désormais, `@Size` ne rend un composant lié obligatoire que si une valeur `min` est définie.

```java
// no longer marks the field as required
@Size(max = 80, message = "Street must be shorter than 80 characters")
private String street;
```

```java
// marks the field as required
@Size(min = 2, max = 80, message = "Street must be between 2 and 80 characters")
private String street;
```
