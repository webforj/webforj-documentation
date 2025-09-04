---
sidebar_position: 20
title: File Upload
_i18n_hash: 1218c7729c6cb025d2d6b4312bd95658
---
# Boîte de dialogue de téléchargement de fichier

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

Une `FileUploadDialog` est une boîte de dialogue modale conçue pour permettre à l'utilisateur de télécharger des fichiers depuis son système de fichiers local. La boîte de dialogue bloque l'exécution de l'application jusqu'à ce que l'utilisateur sélectionne des fichiers à télécharger ou ferme la boîte de dialogue.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Télécharger un fichier");
```

## Utilisations {#usages}

La `FileUploadDialog` fournit un moyen de sélectionner et de télécharger des fichiers, permettant aux utilisateurs de soumettre des documents, des images ou d'autres types de fichiers requis par l'application.

## Résultat {#result}

La `FileUploadDialog` retourne un objet `UploadedFile` qui contient des informations sur le fichier téléchargé, telles que son nom, sa taille et son contenu. Si l'utilisateur ferme la boîte de dialogue sans sélectionner un fichier, le résultat sera `null`.

:::important
La chaîne résultante sera renvoyée par la méthode `show()`, ou la méthode équivalente d'`OptionDialog` comme montré ci-dessous. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Déplacement des fichiers téléchargés {#moving-uploaded-files}

Par défaut, webforJ stocke les fichiers téléchargés dans un dossier temporaire qui est régulièrement nettoyé. Si vous ne déplacez pas le fichier ailleurs, il sera supprimé. Pour déplacer le fichier, utilisez la méthode `move` et spécifiez le chemin de destination.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Sélectionner un fichier à télécharger");
try {
    File file = uploadedFile.move("mon/chemin/complet/" + uploadedFile.getSanitizedClientName());
    // ... faire quelque chose avec le fichier
} catch (IOException e) {
    // gérer l'exception
}
```
:::tip Nom de client assainit
Utilisez la méthode `getSanitizedClientName` pour obtenir une version assainie du nom du fichier téléchargé. Cette méthode contribue à prévenir les risques de sécurité tels que les attaques par traversée de répertoire ou les caractères invalides dans les noms de fichiers, garantissant l'intégrité et la sécurité de votre système de stockage de fichiers.
:::

## Filtres {#filters}

La `FileUploadDialog` vous permet de définir des filtres pour limiter les types de fichiers qui peuvent être sélectionnés pour le téléchargement. Vous pouvez configurer des filtres en utilisant la méthode `setFilters(List<FileChooserFilter> filters)`.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Télécharger un fichier", 
    Arrays.asList(new FileChooserFilter("Fichiers texte", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Validation des filtres
Le serveur ne validera pas le fichier téléchargé par rapport aux filtres. Les filtres ne sont appliqués qu'au niveau de l'interface utilisateur pour guider la sélection de l'utilisateur. Vous devez mettre en œuvre une validation côté serveur pour vous assurer que les fichiers téléchargés répondent aux exigences de votre application.
:::

## Taille maximale {#max-size}

Il est possible de définir la taille maximale des fichiers pour les téléchargements afin de garantir que les utilisateurs ne téléchargent pas de fichiers trop volumineux pour votre application. Cela peut être configuré en utilisant la méthode `setMaxFileSize(long maxSize)`, où maxSize est spécifié en octets.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Définir la taille maximale à 2 Mo
```

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages à l'intérieur du composant sont entièrement personnalisables en utilisant la classe `FileUploadI18n`. Cette flexibilité vous permet d'adapter l'interface de la boîte de dialogue pour répondre à des exigences de localisation spécifiques ou à des préférences de personnalisation.

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
3. **Chemins initiaux logiques** : Définissez des chemins initiaux qui fournissent aux utilisateurs un point de départ utile pour leur sélection de fichiers.
4. **Restreindre la navigation dans les répertoires** : Restreignez la boîte de dialogue à un répertoire spécifique lorsque cela est nécessaire pour empêcher les utilisateurs de naviguer vers des zones non autorisées.
5. **Thématique cohérente** : Alignez les thèmes de la boîte de dialogue et du champ de téléchargement avec le design de votre application pour une expérience utilisateur cohérente.
6. **Minimiser l'usage excessif** : Utilisez les boîtes de dialogue de téléchargement de fichiers avec parcimonie pour éviter de frustrer les utilisateurs. Réservez-les pour des actions nécessitant des téléchargements de fichiers spécifiques par les utilisateurs.
