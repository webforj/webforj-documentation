---
title: Upgrade to 27.00
description: Upgrade from 26.00 to 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_hash: 85416371e550d0aedae0a0771aff67be
---
Cette documentation sert de guide pour mettre à niveau les applications webforJ de 26.00 à 27.00. Voici les modifications nécessaires pour que les applications existantes continuent de fonctionner sans problème. Comme toujours, consultez le [aperçu des versions GitHub](https://github.com/webforj/webforj/releases) pour une liste plus complète des changements entre les versions.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Texte et contenu HTML {#text-and-html-content}

Les versions antérieures traitaient une valeur enveloppée dans `<html>` et passée à `setText()` comme du HTML. Ce comportement est obsolète et est supprimé dans webforJ 27.00. Le paramètre `webforj.legacyHtmlInText` le contrôle :

- `true` (par défaut) : une valeur enveloppée dans `<html>` rend son contenu en tant que HTML.
- `false` : La même valeur est affichée littéralement.

Le wrapper `<html>` n'est jamais affiché dans les deux cas.

La première fois qu'une valeur enveloppée dans `<html>` atteint `setText()`, un avertissement est enregistré, mentionnant le composant et le site d'appel, afin que l'appel puisse être déplacé vers `setHtml()`.
