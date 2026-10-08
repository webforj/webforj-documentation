---
title: Upload
sidebar_position: 160
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: df26b1e4b51f3ba6ece7602ca1a1f33f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

Le composant `Upload` est un sélecteur de fichiers en ligne qui permet à l'utilisateur de sélectionner un ou plusieurs fichiers depuis sa machine locale et de les envoyer au serveur. Contrairement au [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), qui présente le sélecteur dans une modal qui bloque l'application jusqu'à ce que l'utilisateur ait terminé, `Upload` s'affiche directement dans la mise en page de la page. Il s'intègre n'importe où un champ de fichier est nécessaire : un formulaire de profil, un champ de pièce jointe à côté d'une zone de commentaire, ou une zone de dépôt sur une page de gestion de médias.

<!-- INTRO_END -->

:::tip Quand utiliser un `Upload`
Utilisez le composant `Upload` lorsque la sélection de fichiers est accompagnée d'autres actions dans un flux de travail, comme l'édition d'un profil ou la création d'un post. Privilégiez [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) lorsque les téléchargements doivent être modaux, par exemple lorsque un fichier est strictement requis avant que l'utilisateur puisse continuer.
:::

## Création d'un upload {#creating-an-upload}

Par défaut, un composant `Upload` affiche un bouton de sélection, une zone de dépôt, la liste des fichiers en cours et un bouton de téléchargement. Le bouton d'annulation est caché par défaut. Après avoir créé un `Upload`, vous pouvez ajouter des filtres, comme les types de fichiers autorisés, et changer les parties visibles.

```java
Upload upload = new Upload();
upload.addFilter("Images", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

L'exemple suivant incruste un `Upload` de CV dans un formulaire de recrutement, aux côtés d'un champ de nom et d'un bouton de soumission.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Sélection de fichiers {#picking-files}

Le comportement du sélecteur est contrôlé par quelques paramètres indépendants : combien de fichiers l'utilisateur peut sélectionner à la fois, ce qui est sélectionnable depuis le système de fichiers local, et quels types sont visibles dans la boîte de dialogue de fichiers. Ensemble, ils façonnent l'expérience de sélection pour s'adapter au champ.

Voici un téléchargeur de galerie configuré avec des filtres pour les images et les vidéos, la sélection de plusieurs fichiers, et une limite de 20 fichiers :

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Mode de sélection {#selection-mode}

Le mode de sélection limite le sélecteur à un fichier ou plusieurs. `MULTIPLE` est le paramètre par défaut et convient aux opérations par lots comme les galeries photo ou les pièces jointes de factures. `SINGLE` convient aux champs qui, conceptuellement, contiennent une seule valeur, comme une photo de profil ou un contrat signé.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Source du sélecteur {#picker-source}

La source du sélecteur détermine ce que l'utilisateur peut sélectionner depuis le système de fichiers local. Le paramètre par défaut, `FILES`, ouvre une boîte de dialogue de fichiers standard. `DIRECTORY` permet à l'utilisateur de choisir un dossier et télécharge ses fichiers de premier niveau. `DIRECTORY_RECURSIVE` explore l'ensemble de l'arborescence et télécharge tous les fichiers à l'intérieur.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Les téléchargements de répertoires conviennent aux outils qui reflètent les structures de dossiers, comme les systèmes de déploiement, les applications de gestion d'actifs ou les utilitaires de sauvegarde. Pour la plupart des champs de formulaire, le sélecteur de fichiers par défaut est le bon choix.

### Filtres {#filters}

Les filtres limitent ce que l'utilisateur peut choisir depuis le système de fichiers local. Chaque filtre a une description et un ou plusieurs motifs globaux séparés par des points-virgules. Le filtre actif apparaît dans un menu déroulant à côté du bouton du sélecteur, et l'utilisateur peut basculer entre eux.

```java
upload.addFilter("Images", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documents", "*.pdf;*.docx");
upload.setActiveFilter("Images");
```

Quelques paramètres associés façonnent le comportement du menu déroulant des filtres : `setFiltersVisible(false)` cache le menu tout en gardant les filtres actifs, `setMultiFilterSelection(true)` permet à l'utilisateur de combiner des filtres, et `setAllFilesFilterEnabled(false)` supprime l'option implicite "Tous les fichiers".

Quelques-uns de ces paramètres ne s'appliquent qu'au sélecteur standard. Lorsque l'API d'accès au système de fichiers est utilisée, le sélecteur natif gère lui-même la sélection du filtre, donc `setFiltersVisible(false)` est ignoré et `setMultiFilterSelection(true)` n'a pas d'effet (le sélecteur natif n'accepte qu'un seul filtre à la fois). Désactivez l'API d'accès au système de fichiers avec `setFileSystemAccess(false)` pour rendre ces paramètres fiables sur tous les navigateurs.

### Zone de dépôt {#drop-zone}

Des fichiers peuvent être glissés depuis le bureau et déposés sur le composant. L'étiquette de dépôt change lorsqu'un fichier le survole, signalant que le dépôt sera accepté. Le dépôt est activé par défaut et peut être désactivé lorsque le sélecteur ne doit accepter que des fichiers provenant de la boîte de dialogue de fichiers.

```java
upload.setDrop(false);
```

## Validation et limites {#validation-and-limits}

`setMaxFileSize` limite la taille en octets d'un seul fichier, et `setMaxFiles` limite le nombre total de fichiers dans un lot. Les deux sont exécutés avant que des octets ne soient transférés, donc un fichier surdimensionné est rejeté côté client sans consommer de bande passante.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 Mo
upload.setMaxFiles(10);
```

Lorsque un fichier sélectionné ou déposé dépasse l'une ou l'autre limite, `UploadRejectEvent` est déclenché avec la raison. La propriété côté serveur `webforj.fileUpload.maxSize` s'applique toujours et agit comme un plafond, quelle que soit la limite du côté client.

:::warning Validation côté serveur
Les filtres, la taille maximum et le nombre maximum de fichiers sont appliqués dans l'interface utilisateur pour guider l'utilisateur, pas pour protéger le serveur. Chaque fichier téléchargé doit être vérifié à nouveau sur le serveur avant d'être stocké, et les fichiers temporaires doivent être déplacés ou supprimés peu après l'achèvement du téléchargement.
:::

## Comportement de téléchargement {#upload-behavior}

Une fois les fichiers sélectionnés, deux décisions restent à prendre : quand le téléchargement commence, et ce qu'il advient des entrées existantes lorsque l'utilisateur sélectionne à nouveau. Par défaut, l'utilisateur clique sur **Télécharger** pour lancer le transfert, et les entrées existantes restent dans la liste jusqu'à ce qu'elles soient explicitement supprimées.

### Téléchargement automatique {#auto-upload}

Le mode par défaut est `NONE`, où l'utilisateur clique sur **Télécharger** pour commencer le transfert. `setAutoUpload()` supprime ce clic et commence le transfert dès que des fichiers sont sélectionnés, déposés ou les deux.

- **`NONE`** laisse le téléchargement à l'utilisateur, qui clique sur **Télécharger**.
- **`ON_SELECT`** télécharge dès que des fichiers sont choisis via la boîte de dialogue de fichiers.
- **`ON_DROP`** télécharge dès que des fichiers sont déposés sur le composant.
- **`ALWAYS`** couvre les deux chemins.

:::tip Association avec des presets
Le téléchargement automatique se marie bien avec les presets `BUTTON_ONLY` ou `INLINE`, où il n'y a pas de bouton de téléchargement pour que l'utilisateur clique de toute façon. Pour les flux de travail où l'utilisateur doit revoir la sélection avant d'envoyer, laissez le téléchargement automatique désactivé.
:::

### Effacement automatique {#auto-clear}

Lorsque l'utilisateur sélectionne un nouveau lot, l'effacement automatique décide quoi faire des entrées déjà dans la liste. L'effacement se produit au moment de la prochaine sélection, pas à l'achèvement du téléchargement, donc les téléchargements terminés restent visibles jusqu'à ce que l'utilisateur sélectionne à nouveau.

- **`COMPLETED`** efface les entrées téléchargées avec succès.
- **`IN_PROGRESS`** annule et efface les entrées encore en cours de transfert.
- **`ALL`** efface tout.
Les entrées en attente qui n'ont pas commencé à se télécharger sont conservées peu importe le paramètre.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning L'effacement automatique a des déclencheurs subtils
L'effacement automatique ne prend effet qu'une fois qu'un fichier précédemment sélectionné a effectivement commencé à se télécharger ou a terminé. Sans un téléchargement entre les sélections, aucun fichier ne correspond au filtre et la liste continue de croître.
:::

Privilégiez `COMPLETED` dans les téléchargeurs qui vivent à l'écran à travers plusieurs actions, comme un compositeur de chat où chaque message a ses propres pièces jointes, ou un formulaire de commentaire qui est réutilisé pour chaque réponse. Sans cela, la liste des succès précédents s'accumule au fur et à mesure que l'utilisateur travaille.

### Actions programmatiques {#programmatic-actions}

La plupart des téléchargements commencent par un clic de l'utilisateur, mais les mêmes actions sont disponibles depuis le code serveur. Les deux opèrent sur les fichiers que l'utilisateur a déjà sélectionnés ; il n'est pas possible de sélectionner des fichiers au nom de l'utilisateur depuis le serveur.

```java
// Télécharger la sélection actuelle, comme si l'utilisateur avait cliqué sur Télécharger
upload.upload();

// Annuler tous les transferts en cours
upload.cancel();
```

Appelez `upload()` pour déclencher le transfert depuis un contrôle extérieur au composant, comme un bouton de soumission unique partagé par un formulaire plus grand. Appelez `cancel()` depuis un bouton "stop" extérieur au composant, ou depuis une garde de route lorsque l'utilisateur navigue en dehors pendant le transfert.

## Capture mobile {#mobile-capture}

Sur les appareils mobiles, la capture ouvre la caméra ou le microphone comme source du sélecteur au lieu du navigateur de fichiers. `USER` cible la caméra avant ou le microphone, `ENVIRONMENT` cible la caméra arrière, et `NONE` (le paramètre par défaut) utilise le sélecteur de fichiers standard.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Photo", "*.jpg;*.png");
```

:::tip Capture et filtres
Restreignez la sélection aux extensions d'image afin que la caméra s'ouvre en mode photo, ou aux extensions vidéo afin qu'elle s'ouvre en mode enregistrement. Sans un filtre correspondant, un mode de capture retombe dans le sélecteur standard sur la plupart des plateformes. Les navigateurs de bureau ignorent totalement le paramètre de capture.
:::

Pour les applications axées sur le mobile, la capture s'associe bien avec les [applications installables](/docs/configuration/installable-apps), où la caméra et le microphone deviennent une partie naturelle de l'expérience d'accueil.

## Accès natif au système de fichiers {#native-file-system-access}

Le composant utilise l'[API d'accès au système de fichiers](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) du navigateur lorsque la plateforme le supporte. Le sélecteur natif peut accorder à la page une autorisation persistante à un dossier, de sorte que l'utilisateur choisisse une fois et que les téléchargements suivants du même dossier passent la boîte de dialogue. Sur les navigateurs sans prise en charge, le composant revient automatiquement au sélecteur standard.

```java
upload.setFileSystemAccess(false); // forcer le sélecteur standard
```

Désactivez-le lorsque chaque téléchargement doit commencer à partir d'une boîte de dialogue vierge, ou lorsque le comportement cohérent sur chaque navigateur compte plus que la commodité de l'autorisation persistante.

## Personnalisation de la mise en page {#customizing-the-layout}

Le composant est constitué de cinq parties : le bouton de sélection, l'étiquette de dépôt, la liste des fichiers, le bouton de téléchargement et le bouton d'annulation. Les quatre premières sont visibles par défaut ; le bouton d'annulation est caché et peut être affiché avec `setVisible(true, Upload.Part.CANCEL_BUTTON)`. La mise en page peut être remodelée avec des presets pour des formes de sélecteur courantes, ou avec des contrôles de visibilité par partie pour des ajustements plus fins.

### Presets {#presets}

Les presets regroupent plusieurs paramètres de visibilité des parties en formes de sélecteur nommées. Ils constituent un moyen plus rapide d'atteindre une configuration commune que de basculer les parties individuellement.

- **`FULL`** : Bouton de sélection, étiquette de dépôt, liste de fichiers, et bouton de téléchargement. Le paramètre par défaut.
- **`INLINE`** : Bouton de sélection et étiquette de dépôt, avec la sélection actuelle affichée comme texte à côté du sélecteur. Utile pour les champs de formulaire compacts.
- **`BUTTON_ONLY`** : Le bouton de sélection seul. Utile lorsque l'interface utilisateur environnante montre déjà les fichiers sélectionnés.
- **`DROPZONE`** : Étiquette de dépôt et liste de fichiers, sans bouton de sélection. Utile lorsque le glisser-déposer doit être le seul moyen d'ajouter des fichiers.
- **`HEADLESS`** : Chaque partie cachée, avec la bordure externe, le rayon, et le rembourrage réduits pour que le contenu projeté soit bien à l'intérieur des limites du composant.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
]}
height='650px'
/>

### Visibilité des parties {#part-visibility}

Lorsque qu'un preset s'approche du résultat souhaité mais n'est pas tout à fait le bon, des parties individuelles peuvent être affichées ou cachées. Cela est utile pour de petits ajustements, comme cacher le bouton d'annulation sur un sélecteur de fichier unique qui télécharge instantanément, ou cacher l'étiquette de dépôt sur un champ à bouton uniquement qui permet toujours les dépôts. Lors de l'utilisation de `setPreset()` et `setVisible()` ensemble, appelez d'abord `setPreset()`.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Slot par défaut {#default-slot}

`Upload` implémente `HasComponents`. Les enfants ajoutés via `add()` se rendent à l'intérieur de la zone de dépôt, au-dessus de l'apparence standard. Combiné avec le preset `HEADLESS`, le slot vous permet de prendre entièrement le contrôle de la surface visuelle tout en conservant le sélecteur, le dépôt et le comportement de téléchargement intacts.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

Dans l'exemple suivant, le preset `HEADLESS` est utilisé pour projeter une `Table` dans les limites de l'Upload. Déposez un CSV et ses lignes s'affichent directement à l'intérieur du composant, avec des colonnes construites à partir de la ligne d'en-tête du fichier.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Événements {#events}

`Upload` émet des événements à trois niveaux : les actions que l'utilisateur réalise sur l'ensemble du composant, l'état de transfert d'un seul fichier, et le cycle de vie du lot dans son ensemble. La plupart des applications enregistrent quelques écouteurs au travers de ces niveaux selon ce à quoi elles doivent réagir. Un formulaire pourrait ne nécessiter que `onUpload` pour savoir quand les fichiers atteignent le serveur ; un téléchargeur avec une interface utilisateur de progression a besoin de `onListProgress` et `onComplete` ; une zone de dépôt qui doit faire remonter les rejets nécessite `onReject`.

La plupart des événements qui portent des fichiers exposent à la fois `getFile()` (le premier ou le seul fichier dans la charge utile) et `getFiles()` (la liste complète). Utilisez `getFile()` pour des événements à fichier unique comme `onReject`, et `getFiles()` lorsque vous vous attendez à un lot. `UploadCompleteEvent` est l'exception ; il a ses propres accesseurs `getUploadedFiles()` et `getFailedFiles()` puisque le résultat du lot se divise entre succès et échecs.

### Actions utilisateurs {#user-actions}

Celles-ci se déclenchent en réponse à ce que l'utilisateur fait sur l'ensemble du composant. Elles ne fournissent aucune information sur la progression du transfert, juste que l'utilisateur a fait quelque chose que l'application pourrait vouloir traiter.

| Événement | Se déclenche |
| --- | --- |
| `UploadChangeEvent` | Lorsque la liste des fichiers sélectionnés change |
| `UploadEvent` | Lorsque l'utilisateur clique sur **Télécharger** et que les fichiers atteignent le serveur |
| `UploadCancelEvent` | Lorsque l'utilisateur clique sur **Annuler** |
| `UploadFilterChangeEvent` | Lorsque le filtre actif change |

```java
upload.onChange(e -> {
    // Se déclenche chaque fois que la liste de fichiers sélectionnés change.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Se déclenche lorsque le téléchargement est déclenché ; les fichiers ont atteint le serveur.
});
```

`UploadEvent` et `UploadCompleteEvent` semblent similaires à première vue, mais ils répondent à des questions différentes. `UploadEvent` se déclenche lorsque l'utilisateur déclenche explicitement le téléchargement (ou `setAutoUpload()` le déclenche en son nom), et constitue l'endroit naturel pour persister ou transmettre les fichiers téléchargés. `UploadCompleteEvent` se déclenche une fois le transfert de chaque fichier en attente terminé, et est le bon crochet pour les mises à jour UI "le lot est terminé".

### Transfert par fichier {#per-file-transfer}

Celles-ci se déclenchent une fois par fichier, pendant qu'un transfert a lieu ou juste après son échec. Utilisez-les lorsque l'interface utilisateur a besoin de refléter l'état des fichiers individuels plutôt que du lot.

| Événement | Se déclenche |
| --- | --- |
| `UploadProgressEvent` | Pendant le transfert d'un seul fichier |
| `UploadErrorEvent` | Lorsqu'un transfert de fichier unique échoue |
| `UploadRejectEvent` | Lorsqu'un fichier sélectionné ou déposé ne respecte pas les contraintes configurées |

```java
upload.onProgress(e -> {
    // Se déclenche de manière répétée pendant le transfert d'un fichier unique.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Se déclenche lorsque qu'un fichier est refusé pour des raisons de taille, de nombre, ou de filtre.
    String reason = e.getMessage();
});
```

Au sein de ce groupe, `UploadRejectEvent` est l'exception. Il se déclenche avant même que des octets ne bougent, lorsque qu'un fichier échoue à une contrainte côté client comme `setMaxFileSize` ou `setMaxFiles`. `UploadErrorEvent`, en revanche, se déclenche après que le transfert a commencé et que quelque chose s'est mal passé en route vers le serveur.

### Lot entier {#whole-batch}

Celles-ci se déclenchent sur le lot plutôt que sur un fichier unique. Utilisez-les pour une interface utilisateur agrégée comme une barre de progression globale ou un message "terminé" qui résume l'ensemble de la sélection.

| Événement | Se déclenche |
| --- | --- |
| `UploadListProgressEvent` | En même temps que `UploadProgressEvent`, avec l'état de l'ensemble de la liste |
| `UploadCompleteEvent` | Une fois par lot, lorsque chaque fichier a terminé son transfert |

```java
upload.onComplete(e -> {
    // Se déclenche une fois lorsque tout le lot est terminé.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` et `onListProgress` couvrent le même transfert sous deux angles. `onProgress` est par fichier, et est le bon crochet lorsque chaque fichier a sa propre interface utilisateur de progression. `onListProgress` se déclenche en même temps avec des compteurs agrégés (`getListTotal`, `getListRemaining`, `getListProgress`) pour un indicateur unique basé sur l'ensemble du lot.

Dans l'exemple suivant, `onChange`, `onListProgress` et `onComplete` animent une barre de progression et une ligne de statut qui se mettent à jour à mesure que la liste de fichiers change et que les fichiers sont transférés.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
]}
height='450px'
/>

## Internationalisation (i18n) {#internationalization-i18n}

Les étiquettes et messages à l'intérieur du composant sont personnalisables via le paquet `FileUploadI18n`. Le type de paquet garde le nom `FileUploadI18n` car il est partagé avec le modal [`FileUploadDialog`](/docs/components/option-dialogs/file-upload).

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Envoyer");
bundle.setCancel("Ignorer");
bundle.setDropFile("Déposez le fichier ici");
upload.setI18n(bundle);
```

## Thèmes {#themes}

`UploadTheme` reflète la palette de thèmes standard de DWC et inclut des variantes contournées pour un poids visuel plus léger. Les thèmes s'appliquent au sélecteur, au téléchargement et aux boutons d'annulation. La liste et la zone de dépôt conservent un style neutre indépendamment du thème.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

La démo ci-dessous montre le thème `PRIMARY` combiné avec le preset `INLINE`.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Style {#styling}

<TableBuilder name="Upload" />
