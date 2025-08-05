---
sidebar_position: 10
title: File Chooser
_i18n_hash: d0efdadb8ec1e44cfab2fb26f95efa0d
---
# Boîte de dialogue de sélection de fichiers

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` est une boîte de dialogue modale conçue pour permettre à l'utilisateur de sélectionner un fichier ou un répertoire à partir du système de fichiers du serveur. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur fasse une sélection ou ferme la boîte de dialogue.

```java
OptionDialog.showFileChooserDialog("Sélectionnez un fichier");
```

## Usages {#usages}

Le `FileChooserDialog` fournit un moyen de sélectionner des fichiers ou des répertoires à partir du système de fichiers, permettant aux utilisateurs de choisir des répertoires pour enregistrer des données, ou d'effectuer des opérations sur des fichiers.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Résultat {#result}

Le `FileChooserDialog` retourne le fichier ou le répertoire sélectionné sous forme de chaîne. Si l'utilisateur ferme la boîte de dialogue sans faire de sélection, le résultat sera `null`.

:::info
La chaîne résultante sera renvoyée par la méthode `show()`, ou la méthode équivalente d'`OptionDialog` comme indiqué ci-dessous. 
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
    "Sélectionnez un fichier", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("Vous avez sélectionné : " + result, "Sélection effectuée");
} else {
    OptionDialog.showMessageDialog("Aucune sélection effectuée", "Sélection annulée");
}
```

## Mode de sélection {#selection-mode}

Le `FileChooserDialog` prend en charge différents modes de sélection, vous permettant d'adapter la méthode de sélection à vos besoins spécifiques :

1. **FILES** : Permet la sélection uniquement de fichiers.
2. **DIRECTORIES** : Permet la sélection uniquement de répertoires.
3. **FILES_AND_DIRECTORIES** : Permet la sélection à la fois de fichiers et de répertoires.

## Chemin initial {#initial-path}

Le `FileChooserDialog` vous permet de spécifier un chemin initial que la boîte de dialogue ouvrira lorsqu'elle sera affichée. Cela peut fournir aux utilisateurs un point de départ pour leur sélection de fichiers.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Sélectionnez un fichier", "/home/user");
String result = dialog.show();
```

## Restriction {#restriction}

Vous pouvez restreindre la boîte de dialogue à un répertoire spécifique, empêchant les utilisateurs de naviguer en dehors de celui-ci en utilisant la méthode `setRestricted(boolean restricted)`.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Sélectionnez un fichier", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filtres {#filters}

Lorsque le mode de sélection est `FILES`, le `FileChooserDialog` vous permet de définir des filtres pour limiter les types de fichiers qui seront listés. Vous pouvez configurer des filtres à l'aide de la méthode `setFilters(List<FileChooserFilter> filters)`.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Filtres personnalisés {#custom-filters}

Vous pouvez permettre aux utilisateurs d'ajouter des filtres personnalisés en activant la fonction de filtres personnalisés avec la méthode `setCustomFilters(boolean customFilters)`.
Les filtres personnalisés seront enregistrés dans le stockage local du navigateur par défaut et restaurés lorsque la boîte de dialogue sera à nouveau affichée.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Sélectionnez un fichier", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages à l'intérieur du composant sont entièrement personnalisables à l'aide de la classe `FileChooserI18n`. Cette flexibilité vous permet d'adapter l'interface de la boîte de dialogue pour répondre à des exigences de localisation spécifiques ou à des préférences de personnalisation.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Sélectionnez un fichier", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Choisir");
i18n.setCancel("Annuler");
dialog.setI18n(i18n);
```

## Meilleures pratiques {#best-practices}

1. **Invitations claires et concises** : Assurez-vous que le message de demande explique clairement ce que l'utilisateur est invité à sélectionner.
2. **Modes de sélection appropriés** : Choisissez des modes de sélection qui correspondent à l'action requise de l'utilisateur afin de garantir des sélections précises et pertinentes.
3. **Chemins initiaux logiques** : Définissez des chemins initiaux qui fournissent aux utilisateurs un point de départ utile pour leur sélection.
4. **Restreindre la navigation dans les répertoires** : Restreignez la boîte de dialogue à un répertoire spécifique lorsque cela est nécessaire pour empêcher les utilisateurs de naviguer vers des zones non autorisées.
