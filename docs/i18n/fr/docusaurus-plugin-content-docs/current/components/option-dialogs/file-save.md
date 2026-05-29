---
title: File Save
sidebar_position: 15
_i18n_hash: 7cad72847c86a30f8ad6000a283a51c2
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` est une boîte de dialogue modale conçue pour permettre aux utilisateurs de sauvegarder un fichier dans un emplacement spécifié sur le système de fichiers du serveur. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fournisse un nom de fichier et confirme l'action ou annule la boîte de dialogue.

<!-- INTRO_END -->

## Usages {#usages}

Le `FileSaveDialog` fournit une méthode rationalisée pour sauvegarder des fichiers dans le système de fichiers, offrant des options configurables par l'utilisateur pour le nommage des fichiers et la gestion des fichiers existants.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Résultat {#result}

Le `FileSaveDialog` renvoie le chemin sélectionné sous forme de chaîne. Si l'utilisateur annule la boîte de dialogue, le résultat sera `null`.

:::important But de la boîte de dialogue
Cette boîte de dialogue ne permet pas réellement de sauvegarder des fichiers, mais renvoie le nom de fichier choisi par l'utilisateur.
:::

:::info
La chaîne résultante est renvoyée par la méthode `show()` ou la méthode équivalente de `OptionDialog`, comme démontré ci-dessous.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Enregistrez votre fichier", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Fichier sauvegardé à : " + path, "Chemin sélectionné");
} else {
  OptionDialog.showMessageDialog("Aucun chemin n'est sélectionné", "Chemin sélectionné",
      MessageDialog.MessageType.ERROR);
}
```

## Action d'existence {#exists-action}

Le `FileSaveDialog` fournit un comportement configurable lorsque un fichier avec le nom spécifié existe déjà :

* **ACCEPT_WITHOUT_ACTION** : La sélection est acceptée sans action supplémentaire de l'utilisateur.
* **ERROR_DIALOGUE** : L'utilisateur se voit présenter une boîte de dialogue d'erreur ; la sélection n'est pas autorisée.
* **CONFIRMATION_DIALOGUE** : L'utilisateur se voit présenter une boîte de dialogue demandant confirmation. C'est le paramètre par défaut.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Enregistrez votre fichier", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Mode de sélection {#selection-mode}

Le `FileSaveDialog` prend en charge différents modes de sélection, vous permettant d'adapter la méthode de sélection à vos besoins spécifiques :

1. **FILES** : Permet la sélection uniquement de fichiers.
2. **DIRECTORIES** : Permet la sélection uniquement de répertoires.
3. **FILES_AND_DIRECTORIES** : Permet la sélection de fichiers et de répertoires.

## Chemin initial {#initial-path}

Spécifiez le répertoire dans lequel la boîte de dialogue s'ouvrira en utilisant le chemin initial. Cela aide les utilisateurs à commencer dans un répertoire logique pour leur opération de sauvegarde.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Enregistrez votre fichier", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Restriction {#restriction}

Vous pouvez restreindre la boîte de dialogue à un répertoire spécifique, empêchant les utilisateurs de naviguer en dehors de celui-ci en utilisant la méthode `setRestricted(boolean restricted)`.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Enregistrez votre fichier", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Nom de fichier {#filename}

Définissez un nom de fichier par défaut pour l'opération de sauvegarde afin de guider les utilisateurs et de minimiser les erreurs.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Enregistrez votre fichier");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, labels et messages au sein du composant sont entièrement personnalisables en utilisant la classe `FileSaveI18n`. Cela garantit que la boîte de dialogue peut être adaptée à diverses exigences de localisation ou de personnalisation.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Enregistrez votre fichier");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Choisir");
i18n.setCancel("Annuler");
dialog.setI18n(i18n);
```

## Filtres {#filters}

Le `FileSaveDialog` vous permet de définir des filtres pour limiter les types de fichiers qui peuvent être sauvegardés en utilisant la méthode `setFilters(List<FileSaveFilter> filters)`.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Filtres personnalisés {#custom-filters}

Vous pouvez activer des filtres personnalisés pour permettre aux utilisateurs de définir leurs propres filtres de fichiers en utilisant la méthode `setCustomFilters(boolean customFilters)`. Les filtres sont sauvegardés dans le stockage local par défaut et restaurés lors des invocations de boîte de dialogue suivantes.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Enregistrez votre fichier", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Bonnes pratiques {#best-practices}

* **Noms de fichiers prédéfinis** : Fournissez un nom de fichier par défaut logique lorsque cela est applicable.
* **Confirmer les écrasements** : Utilisez `CONFIRMATION_DIALOGUE` pour `ExistsAction` afin de prévenir les écrasements accidentels.
* **Chemin initial intuitif** : Définissez un chemin initial qui correspond aux attentes des utilisateurs.
* **Internationalisation** : Personnalisez le texte de la boîte de dialogue pour améliorer l'utilisation pour les audiences internationales.
* **Filtres de type de fichier** : Utilisez des filtres pour restreindre les types de fichiers et guider les utilisateurs vers des extensions de fichiers valides.
