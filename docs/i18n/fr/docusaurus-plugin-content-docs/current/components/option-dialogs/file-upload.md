---
title: File Upload
sidebar_position: 20
description: >-
  Capture client uploads with the FileUploadDialog, returning an UploadedFile
  that you can filter, move, and process server-side.
_i18n_hash: 411ef7de8459154002fa2a620f073b0d
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Un `FileUploadDialog` est une boîte de dialogue modale conçue pour permettre à l'utilisateur de télécharger des fichiers depuis son système de fichiers local. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur sélectionne des fichiers à télécharger ou ferme la boîte de dialogue.

<!-- INTRO_END -->

:::tip Composant en ligne
Si vous souhaitez un sélecteur de fichiers qui s'affiche directement dans la mise en page de la page au lieu d'une boîte de dialogue, envisagez d'utiliser le composant [`Upload`](/docs/components/upload).
:::

## Usages {#usages}

Le `FileUploadDialog` offre un moyen de sélectionner et de télécharger des fichiers, permettant aux utilisateurs de soumettre des documents, des images ou d'autres types de fichiers requis par l'application. Utilisez `showFileUploadDialog()` pour afficher la boîte de dialogue et capturer le fichier téléchargé.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Télécharger un fichier");
```

## Résultat {#result}

Le `FileUploadDialog` renvoie un objet `UploadedFile` qui contient des informations sur le fichier téléchargé, telles que son nom, sa taille et son contenu. Si l'utilisateur ferme la boîte de dialogue sans sélectionner de fichier, le résultat sera `null`.

:::important
L'objet `UploadedFile` résultant est renvoyé par la méthode `show()`, ou la méthode équivalente `OptionDialog` comme indiqué ci-dessous.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Déplacement des fichiers téléchargés {#moving-uploaded-files}

Par défaut, webforJ stocke les fichiers téléchargés dans un dossier temporaire qui est régulièrement nettoyé. Si vous ne déplacez pas le fichier ailleurs, il sera supprimé. Pour déplacer le fichier, utilisez la méthode `move` et spécifiez le chemin de destination.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Sélectionnez un fichier à télécharger");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... faites quelque chose avec le fichier
} catch (IOException e) {
  // gérer l'exception
}
```
:::tip Nom de client sanitisé
Utilisez la méthode `getSanitizedClientName` pour obtenir une version sanitée du nom du fichier téléchargé. Cette méthode aide à prévenir les risques de sécurité tels que les attaques de traversée de répertoire ou les caractères invalides dans les noms de fichiers, garantissant l'intégrité et la sécurité de votre système de stockage de fichiers.
:::

## Filtres {#filters}

Le `FileUploadDialog` permet de définir des filtres afin de limiter les types de fichiers pouvant être sélectionnés pour le téléchargement. Vous pouvez configurer des filtres en utilisant la méthode `setFilters(List<FileChooserFilter> filters)`.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Télécharger un fichier",
  Arrays.asList(new FileChooserFilter("Fichiers texte", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validation des filtres
Le serveur ne validera pas le fichier téléchargé par rapport aux filtres. Les filtres ne s'appliquent que dans l'interface utilisateur pour guider la sélection de l'utilisateur. Vous devez mettre en œuvre une validation côté serveur pour vous assurer que les fichiers téléchargés répondent aux exigences de votre application.
:::

## Taille maximale {#max-size}

Utilisez `setMaxFileSize(Number maxSize)` pour définir la taille maximale de téléchargement en octets.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Définir la taille maximale à 2 Mo
```

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages à l'intérieur du composant sont entièrement personnalisables à l'aide de la classe `FileUploadI18n`. Cette flexibilité vous permet d'adapter l'interface de la boîte de dialogue pour répondre à des exigences de localisation spécifiques ou à des préférences de personnalisation.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Télécharger un fichier");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Télécharger");
i18n.setCancel("Annuler");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Meilleures pratiques {#best-practices}

1. **Invitations claires et concises** : Assurez-vous que le message d'invitation explique clairement ce que l'on demande à l'utilisateur de télécharger.
2. **Filtres appropriés** : Définissez des filtres de fichiers qui correspondent aux types de fichiers requis pour garantir que les utilisateurs téléchargent des fichiers pertinents.
3. **Thématisation cohérente** : Correspondre les thèmes de la boîte de dialogue et du champ de téléchargement au design de votre application.
4. **Minimiser l'utilisation excessive** : Utilisez les boîtes de dialogue de téléchargement de fichiers avec parcimonie pour éviter la frustration des utilisateurs. Réservez-les pour des actions nécessitant des téléchargements de fichiers spécifiques par l'utilisateur.
