---
title: File Save
sidebar_position: 15
description: >-
  Prompt users for a save location with the FileSaveDialog, configuring initial
  path, default filename, and overwrite behavior.
_i18n_hash: 861257322ca233cfa9e0eed5e4d3aa95
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` est une boîte de dialogue modale conçue pour permettre aux utilisateurs d'enregistrer un fichier à un emplacement spécifié sur le système de fichiers du serveur. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse un nom de fichier et confirme l'action ou annule la boîte de dialogue.

<!-- INTRO_END -->

## Usages {#usages}

Le `FileSaveDialog` fournit une méthode simplifiée pour enregistrer des fichiers sur le système de fichiers, offrant des options configurables par l'utilisateur pour le nommage des fichiers et la gestion des fichiers existants.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Résultat {#result}

Le `FileSaveDialog` renvoie le chemin sélectionné sous forme de chaîne. Si l'utilisateur annule la boîte de dialogue, le résultat sera `null`.

:::important Objectif de la boîte de dialogue
Cette boîte de dialogue ne permet pas réellement d'enregistrer des fichiers, mais renvoie le nom de fichier sélectionné par l'utilisateur.
:::

:::info
La chaîne résultante est renvoyée par la méthode `show()` ou la méthode équivalente `OptionDialog` comme démontré ci-dessous.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Enregistrez votre fichier", "/home/user/documents", "rapport.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Chemin sélectionné : " + result, "Chemin Sélectionné");
} else {
  OptionDialog.showMessageDialog("Aucun chemin sélectionné", "Chemin Sélectionné",
      MessageDialog.MessageType.ERROR);
}
```

## Action d'existence {#exists-action}

Le `FileSaveDialog` fournit un comportement configurable lorsqu'un fichier avec le nom spécifié existe déjà :

* **ACCEPT_WITHOUT_ACTION** : La sélection est acceptée sans action supplémentaire de l'utilisateur.
* **ERROR_DIALOGUE** : L'utilisateur se voit présenter une boîte de dialogue d'erreur ; la sélection n'est pas autorisée.
* **CONFIRMATION_DIALOGUE** : L'utilisateur se voit présenter une boîte de dialogue demandant confirmation. C'est le comportement par défaut.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Enregistrez votre fichier", "/home/user/documents", "rapport.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Mode de sélection {#selection-mode}

Le `FileSaveDialog` prend en charge différents modes de sélection, vous permettant d'adapter la méthode de sélection à vos besoins spécifiques :

1. **FILES** : Permet la sélection de fichiers uniquement.
2. **DIRECTORIES** : Permet la sélection de répertoires uniquement.
3. **FILES_AND_DIRECTORIES** : Permet la sélection à la fois de fichiers et de répertoires.

## Chemin initial {#initial-path}

Spécifiez le répertoire où la boîte de dialogue s'ouvrira en utilisant le chemin initial. Cela aide les utilisateurs à commencer dans un répertoire logique pour leur opération d'enregistrement.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Enregistrez votre fichier", "/home/user/documents", "rapport.xls");
String result = dialog.show();
```

## Restriction {#restriction}

Vous pouvez restreindre la boîte de dialogue à un répertoire spécifique, empêchant les utilisateurs de naviguer en dehors de celui-ci en utilisant la méthode `setRestricted(boolean restricted)`.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Enregistrez votre fichier", "/home/user/documents", "rapport.xls");
dialog.setRestricted(true);
dialog.show();
```

## Nom de fichier {#filename}

Définissez un nom de fichier par défaut pour l'opération d'enregistrement afin de guider les utilisateurs et de minimiser les erreurs.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Enregistrez votre fichier");
dialog.setName("rapport.xls");
String result = dialog.show();
```

## Internationalisation (i18n) {#internationalization-i18n}

Utilisez `FileChooserI18n` pour personnaliser les titres, descriptions, étiquettes et messages de la boîte de dialogue pour différents locales ou exigences d'application.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Enregistrer votre fichier");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Choisir");
i18n.setCancel("Annuler");
dialog.setI18n(i18n);
```

## Filtres {#filters}

Le `FileSaveDialog` vous permet de définir des filtres pour limiter les types de fichiers qui peuvent être enregistrés à l'aide de la méthode `setFilters(List<FileChooserFilter> filters)`.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Filtres personnalisés {#custom-filters}

Vous pouvez activer des filtres personnalisés pour permettre aux utilisateurs de définir leurs propres filtres de fichiers à l'aide de la méthode `setCustomFilters(boolean customFilters)`. Les filtres sont enregistrés dans le stockage local par défaut et restaurés lors des appels suivants à la boîte de dialogue.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Enregistrez votre fichier", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Meilleures pratiques {#best-practices}

* **Noms de fichiers prédéfinis** : Fournissez un nom de fichier par défaut logique lorsque cela est applicable.
* **Confirmer les écrasements** : Utilisez `CONFIRMATION_DIALOGUE` pour `ExistsAction` afin d'éviter les écrasements accidentels.
* **Chemin initial intuitif** : Définissez un chemin initial qui correspond aux attentes des utilisateurs.
* **Internationalisation** : Personnalisez le texte de la boîte de dialogue pour améliorer l'utilisabilité pour les audiences internationales.
* **Filtres de type de fichier** : Utilisez des filtres pour restreindre les types de fichiers et guider les utilisateurs vers des extensions de fichier valides.
