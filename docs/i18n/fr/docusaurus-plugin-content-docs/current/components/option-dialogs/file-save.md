---
sidebar_position: 15
title: File Save
_i18n_hash: 9f5ecfb61386cfa8c4eb3c31305b1838
---
# Dialogue de Sauvegarde de Fichier

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` est un dialogue modal conçu pour permettre aux utilisateurs de sauvegarder un fichier à un emplacement spécifié sur le système de fichiers du serveur. Le dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse un nom de fichier et confirme l'action ou annule le dialogue.

```java
OptionDialog.showFileSaveDialog("Sauvegardez votre fichier");
```

## Usages {#usages}

Le `FileSaveDialog` fournit une méthode simplifiée pour sauvegarder des fichiers sur le système de fichiers, offrant des options configurables par l'utilisateur pour le nommage des fichiers et la gestion des fichiers existants.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Résultat {#result}

Le `FileSaveDialog` renvoie le chemin sélectionné sous forme de chaîne. Si l'utilisateur annule le dialogue, le résultat sera `null`.

:::important Objectif du Dialogue
Ce dialogue ne sauvegarde en réalité aucun fichier, mais renvoie le nom de fichier que l'utilisateur a sélectionné.
:::

:::info
La chaîne résultante est renvoyée par la méthode `show()` ou la méthode équivalente `OptionDialog` comme montré ci-dessous.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Sauvegardez votre fichier", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Fichier sauvegardé à : " + path, "Chemin Sélectionné");
} else {
  OptionDialog.showMessageDialog("Aucun chemin n'est sélectionné", "Chemin Sélectionné",
      MessageDialog.MessageType.ERROR);
}
```

## Action d'Existence {#exists-action}

Le `FileSaveDialog` fournit un comportement configurable lorsqu'un fichier avec le nom spécifié existe déjà :

* **ACCEPT_WITHOUT_ACTION** : La sélection est acceptée sans action utilisateur supplémentaire.
* **ERROR_DIALOGUE** : L'utilisateur se voit présenter un dialogue d'erreur ; la sélection n'est pas autorisée.
* **CONFIRMATION_DIALOGUE** : L'utilisateur se voit présenter un dialogue demandant confirmation. C'est le paramètre par défaut.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sauvegardez votre fichier", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Mode de Sélection {#selection-mode}

Le `FileSaveDialog` prend en charge différents modes de sélection, vous permettant d'adapter la méthode de sélection à vos besoins spécifiques :

1. **FILES** : Permet la sélection uniquement de fichiers.
2. **DIRECTORIES** : Permet la sélection uniquement de répertoires.
3. **FILES_AND_DIRECTORIES** : Permet la sélection à la fois de fichiers et de répertoires.

## Chemin Initial {#initial-path}

Spécifiez le répertoire où le dialogue s'ouvrira en utilisant le chemin initial. Cela aide les utilisateurs à commencer dans un répertoire logique pour leur opération de sauvegarde.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sauvegardez votre fichier", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Restriction {#restriction}

Vous pouvez restreindre le dialogue à un répertoire spécifique, empêchant les utilisateurs de naviguer en dehors de celui-ci en utilisant la méthode `setRestricted(boolean restricted)`.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Sauvegardez votre fichier", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Nom de Fichier {#filename}

Définissez un nom de fichier par défaut pour l'opération de sauvegarde afin de guider les utilisateurs et de minimiser les erreurs.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sauvegardez votre fichier");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages au sein du composant sont entièrement personnalisables en utilisant la classe `FileSaveI18n`. Cela garantit que le dialogue peut être adapté à diverses exigences de localisation ou de personnalisation.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sauvegardez votre fichier");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Choisir");
i18n.setCancel("Annuler");
dialog.setI18n(i18n);
```

## Filtres {#filters}

Le `FileSaveDialog` vous permet de définir des filtres pour limiter les types de fichiers qui peuvent être sauvegardés en utilisant la méthode `setFilters(List<FileSaveFilter> filters)`.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Filtres Personnalisés {#custom-filters}

Vous pouvez activer des filtres personnalisés pour permettre aux utilisateurs de définir leurs propres filtres de fichiers en utilisant la méthode `setCustomFilters(boolean customFilters)`. Les filtres sont sauvegardés dans le stockage local par défaut et restaurés lors des invocations ultérieures du dialogue.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Sauvegardez votre fichier", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Meilleures Pratiques {#best-practices}

* **Noms de Fichiers Pré-définis** : Fournissez un nom de fichier par défaut logique lorsque cela est applicable.
* **Confirmez les Écrasements** : Utilisez `CONFIRMATION_DIALOGUE` pour `ExistsAction` pour éviter les écrasements accidentels.
* **Chemin Initial Intuitif** : Définissez un chemin initial qui correspond aux attentes de l'utilisateur.
* **Internationalisation** : Personnalisez le texte du dialogue pour améliorer l'utilisabilité pour les publics internationaux.
* **Filtres de Types de Fichiers** : Utilisez des filtres pour restreindre les types de fichiers et guider les utilisateurs vers des extensions de fichiers valides.
