---
title: Upload
sidebar_position: 160
sidebar_class_name: new-content
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: 76f8c00c7754fed0a87c27e7963e2877
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

Le composant `Upload` est un sélecteur de fichiers en ligne qui permet à l'utilisateur de sélectionner un ou plusieurs fichiers de son ordinateur local et de les envoyer au serveur. Contrairement à [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), qui présente le sélecteur dans une fenêtre modale qui bloque l'application jusqu'à ce que l'utilisateur ait fini, `Upload` s'affiche directement dans la mise en page de la page. Il s'adapte à tout endroit où un champ de fichier est approprié : un formulaire de profil, un champ de pièce jointe à côté d'une zone de commentaire, ou une zone de dépôt sur une page de gestion des médias.

<!-- INTRO_END -->

:::tip Quand utiliser un `Upload`
Utilisez le composant `Upload` lorsque la sélection de fichiers est accompagnée d'autres actions dans un flux de travail, comme la modification d'un profil ou la création d'un post. Privilégiez [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) lorsque les téléchargements doivent être modaux, par exemple lorsque un fichier est strictement requis avant que l'utilisateur puisse continuer.
:::

## Création d'un upload {#creating-an-upload}

Par défaut, un composant `Upload` montre un bouton de sélection, une zone de dépôt, la liste des fichiers actuels et un bouton de téléchargement. Le bouton d'annulation est caché par défaut. Après avoir créé un `Upload`, vous pouvez ajouter des filtres, comme les types de fichiers autorisés, et changer les parties visibles.

```java
Upload upload = new Upload();
upload.addFilter("Images", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

L'exemple suivant intègre un `Upload` de CV dans un formulaire de recrutement, accompagné d'un champ de nom et d'un bouton de soumission.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Sélection de fichiers {#picking-files}

Le comportement du sélecteur est contrôlé par quelques paramètres indépendants : combien de fichiers l'utilisateur peut sélectionner à la fois, ce qui est sélectionnable depuis le système de fichiers local et quels types sont visibles dans la boîte de dialogue des fichiers. Ensemble, ils façonnent l'expérience de sélection pour s'adapter au champ.

Voici un chargeur de galerie configuré avec des filtres d'image et de vidéo, une sélection de fichiers multiples et une limite de 20 fichiers :

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Mode de sélection {#selection-mode}

Le mode de sélection limite le sélecteur à un fichier ou plusieurs. `MULTIPLE` est le mode par défaut et convient à des opérations par lots telles que des galeries photo ou des pièces jointes de facture. `SINGLE` convient aux champs qui contiennent conceptuellement une seule valeur, comme une photo de profil ou un contrat signé.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Source du sélecteur {#picker-source}

La source du sélecteur détermine ce que l'utilisateur peut sélectionner depuis le système de fichiers local. Par défaut, `FILES` ouvre une boîte de dialogue de fichiers standard. `DIRECTORY` permet à l'utilisateur de sélectionner un dossier et charge ses fichiers de premier niveau. `DIRECTORY_RECURSIVE` parcourt l'arborescence entière et télécharge chaque fichier à l'intérieur.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Les téléchargements de dossiers conviennent aux outils qui reflètent des structures de dossiers, comme les systèmes de déploiement, les applications de gestion des actifs ou les utilitaires de sauvegarde. Pour la plupart des champs de formulaire, le sélecteur de fichiers par défaut est le bon choix.

### Filtres {#filters}

Les filtres limitent ce que l'utilisateur peut sélectionner depuis le système de fichiers local. Chaque filtre a une description et un ou plusieurs motifs glob qui sont séparés par des points-virgules. Le filtre actif apparaît dans un menu déroulant à côté du bouton de sélection, et l'utilisateur peut basculer entre eux.

```java
upload.addFilter("Images", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documents", "*.pdf;*.docx");
upload.setActiveFilter("Images");
```

Quelques paramètres connexes modifient le comportement du menu déroulant des filtres : `setFiltersVisible(false)` cache le menu tout en maintenant les filtres actifs, `setMultiFilterSelection(true)` permet à l'utilisateur de combiner les filtres et `setAllFilesFilterEnabled(false)` supprime l'option implicite "Tous les fichiers".

Certains de ces paramètres ne s'appliquent qu'au sélecteur standard. Lorsque l'API d'accès au système de fichiers est utilisée, le sélecteur natif gère la sélection des filtres lui-même, donc `setFiltersVisible(false)` est ignoré et `setMultiFilterSelection(true)` n'a aucun effet (le sélecteur natif n'accepte qu'un seul filtre à la fois). Désactivez l'API d'accès au système de fichiers avec `setFileSystemAccess(false)` pour rendre ces paramètres fiables à travers les navigateurs.

### Zone de dépôt {#drop-zone}

Les fichiers peuvent être glissés depuis le bureau et déposés sur le composant. L'étiquette de dépôt change lorsqu'un fichier le survole, signalant que le dépôt sera accepté. Le dépôt est activé par défaut et peut être désactivé lorsque le sélecteur doit uniquement accepter des fichiers depuis la boîte de dialogue de fichiers.

```java
upload.setDrop(false);
```

## Validation et limites {#validation-and-limits}

`setMaxFileSize` limite la taille en octets d'un seul fichier, et `setMaxFiles` limite le nombre total de fichiers dans un lot. Les deux s'exécutent avant que des octets soient transférés, donc un fichier surdimensionné est rejeté côté client sans consommer de bande passante.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 Mo
upload.setMaxFiles(10);
```

Lorsqu'un fichier choisi ou déposé dépasse une limite, l'événement `UploadRejectEvent` se déclenche avec la raison. La propriété côté serveur `webforj.fileUpload.maxSize` s'applique toujours et agit comme un plafond strict, indépendamment de la limite côté client.

:::warning Validation côté serveur
Les filtres, la taille maximale et le nombre maximal de fichiers sont appliqués dans l'interface utilisateur pour guider l'utilisateur, et non pour protéger le serveur. Chaque fichier téléchargé doit être revérifié sur le serveur avant d'être stocké, et les fichiers temporaires doivent être déplacés ou supprimés peu après la fin du téléchargement.
:::

## Comportement de téléchargement {#upload-behavior}

Une fois que les fichiers sont choisis, deux décisions restent à prendre : quand débute le téléchargement et ce qui arrive aux entrées existantes lorsque l'utilisateur choisit à nouveau. Par défaut, l'utilisateur clique sur **Télécharger** pour commencer le transfert, et les entrées existantes restent dans la liste jusqu'à ce qu'elles soient explicitement effacées.

### Téléchargement automatique {#auto-upload}

Le mode par défaut est `NONE`, où l'utilisateur clique sur **Télécharger** pour commencer le transfert. `setAutoUpload()` supprime ce clic et démarre le transfert dès que des fichiers sont choisis, déposés ou les deux.

- **`NONE`** laisse le téléchargement à l'utilisateur, qui clique sur **Télécharger**.
- **`ON_SELECT`** télécharge dès que des fichiers sont choisis via la boîte de dialogue de fichiers.
- **`ON_DROP`** télécharge dès que des fichiers sont déposés sur le composant.
- **`ALWAYS`** couvre les deux chemins.

:::tip Association avec des préréglages
Le téléchargement automatique s'associe bien avec les préréglages `BUTTON_ONLY` ou `INLINE`, où il n'y a pas de bouton de téléchargement pour que l'utilisateur clique. Pour les flux de travail où l'utilisateur doit examiner la sélection avant d'envoyer, laissez le téléchargement automatique désactivé.
:::

### Effacement automatique {#auto-clear}

Lorsque l'utilisateur choisit un nouveau lot, l'effacement automatique détermine quoi faire avec les entrées déjà dans la liste. L'effacement se produit au moment du prochain choix, pas à l'achèvement du téléchargement, donc les téléchargements terminés restent visibles jusqu'à ce que l'utilisateur choisisse à nouveau.

- **`COMPLETED`** efface les entrées téléchargées avec succès.
- **`IN_PROGRESS`** annule et efface les entrées toujours en transfert.
- **`ALL`** efface tout.
Les entrées en attente qui n'ont pas commencé le téléchargement sont conservées indépendamment de la configuration.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning L'effacement automatique a des déclencheurs subtils
L'effacement automatique ne prend effet qu'une fois qu'un fichier précédemment choisi a réellement commencé à être téléchargé ou a été terminé. Sans un téléchargement entre les choix, aucun fichier ne correspond au filtre et la liste continue de croître.
:::

Privilégiez `COMPLETED` dans des chargeurs qui vivent à l'écran au cours de plusieurs actions, comme un compositeur de chat où chaque message a ses propres pièces jointes, ou un formulaire de commentaire qui est réutilisé pour chaque réponse. Sans cela, la liste des succès précédents s'accumule au fur et à mesure que l'utilisateur travaille.

### Actions programmatiques {#programmatic-actions}

La plupart des téléchargements commencent par un clic de l'utilisateur, mais les mêmes actions sont disponibles depuis le code du serveur. Les deux agissent sur les fichiers que l'utilisateur a déjà choisis ; il n'y a pas moyen de sélectionner des fichiers au nom de l'utilisateur depuis le serveur.

```java
// Télécharger la sélection actuelle, comme si l'utilisateur avait cliqué sur Télécharger
upload.upload();

// Annuler tous les transferts en cours
upload.cancel();
```

Appelez `upload()` pour déclencher le transfert depuis un contrôle extérieur au composant, comme un seul bouton de soumission partagé par un formulaire plus large. Appelez `cancel()` depuis un bouton "arrêter" externe au composant, ou depuis un garde de route lorsque l'utilisateur navigue ailleurs en cours de transfert.

## Capture mobile {#mobile-capture}

Sur les appareils mobiles, la capture ouvre la caméra ou le microphone comme source du sélecteur au lieu du navigateur de fichiers. `USER` cible la caméra ou le microphone avant, `ENVIRONMENT` cible la caméra arrière, et `NONE` (le défaut) utilise le sélecteur de fichiers standard.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Photo", "*.jpg;*.png");
```

:::tip Capture et filtres
Restreindre la sélection aux extensions d'image afin que la caméra s'ouvre en mode photo, ou aux extensions vidéo afin qu'elle s'ouvre en mode enregistrement. En l'absence d'un filtre correspondant, un mode de capture revient au sélecteur standard sur la plupart des plates-formes. Les navigateurs de bureau ignorent complètement le paramètre de capture.
:::

Pour les applications axées sur le mobile, la capture s'associe bien avec [les applications installables](/docs/configuration/installable-apps), où la caméra et le microphone deviennent une partie naturelle de l'expérience de l'écran d'accueil.

## Accès natif au système de fichiers {#native-file-system-access}

Le composant utilise l'[API d'accès au système de fichiers](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) du navigateur lorsque la plate-forme la prend en charge. Le sélecteur natif peut accorder à la page une autorisation persistante pour un dossier, de sorte que l'utilisateur choisit une fois et que les téléchargements ultérieurs depuis le même dossier ignorent la boîte de dialogue. Sur les navigateurs sans prise en charge, le composant retombe automatiquement sur le sélecteur standard.

```java
upload.setFileSystemAccess(false); // forcer le sélecteur standard
```

Désactivez-le lorsque chaque téléchargement doit commencer par une nouvelle boîte de dialogue, ou lorsque le comportement cohérent à travers chaque navigateur est plus important que la commodité de la permission persistante.

## Personnalisation de la mise en page {#customizing-the-layout}

Le composant est construit à partir de cinq parties : le bouton de sélection, l'étiquette de dépôt, la liste des fichiers, le bouton de téléchargement et le bouton d'annulation. Les quatre premières sont visibles par défaut ; le bouton d'annulation est caché et peut être affiché avec `setVisible(true, Upload.Part.CANCEL_BUTTON)`. La mise en page peut être remodelée avec des préréglages pour des formes de sélection courantes, ou avec des contrôles de visibilité par partie pour un ajustement plus précis.

### Préréglages {#presets}

Les préréglages regroupent plusieurs paramètres de visibilité de partie en formes de sélecteur nommées. C'est un moyen plus rapide d'atteindre une configuration courante que de basculer les parties individuellement.

- **`FULL`** : Bouton de sélection, étiquette de dépôt, liste de fichiers et bouton de téléchargement. Le défaut.
- **`INLINE`** : Bouton de sélection et étiquette de dépôt, avec la sélection actuelle rendue sous forme de texte à côté du sélecteur. Utile pour les champs de formulaire compacts.
- **`BUTTON_ONLY`** : Le bouton de sélection seul. Utile lorsque l'interface utilisateur environnante affiche déjà les fichiers sélectionnés.
- **`DROPZONE`** : Étiquette de dépôt et liste de fichiers, sans bouton de sélection. Utile lorsque le glisser-déposer doit être la seule façon d'ajouter des fichiers.
- **`HEADLESS`** : Chaque partie cachée, avec la bordure extérieure, le rayon et le rembourrage réduits afin que le contenu projeté soit bien encastré à l'intérieur des limites du composant.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
  'src/main/frontend/css/upload/uploadPresets.css'
]}
height='650px'
/>

### Visibilité des parties {#part-visibility}

Lorsque qu'un préréglage est proche mais pas tout à fait de la forme désirée, des parties individuelles peuvent être affichées ou cachées. Cela est utile pour des ajustements mineurs, comme cacher le bouton d'annulation sur un sélecteur de fichiers unique qui se télécharge instantanément, ou cacher l'étiquette de dépôt sur un champ de bouton unique qui permet toujours des dépôts. Lors de l'utilisation de `setPreset()` et `setVisible()` ensemble, appelez d'abord `setPreset()`.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Emplacement par défaut {#default-slot}

`Upload` implémente `HasComponents`. Les enfants ajoutés par le biais de `add()` se rendent à l'intérieur de la zone de dépôt, par-dessus le chrome standard. Associé au préréglage `HEADLESS`, l'emplacement vous permet de prendre entièrement la surface visuelle tout en maintenant le comportement de sélection, de dépôt et de téléchargement intacts.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

Dans l'exemple suivant, le préréglage `HEADLESS` est utilisé pour projeter un `Table` dans les limites de l'Upload. Déposez un CSV et ses lignes se rendent directement à l'intérieur du composant, avec des colonnes construites à partir de la ligne d'en-tête du fichier.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Événements {#events}

`Upload` émet des événements à trois niveaux : les actions que l'utilisateur effectue sur l'ensemble du composant, l'état de transfert d'un fichier unique et le cycle de vie du lot dans son ensemble. La plupart des applications enregistrent quelques auditeurs à travers ces niveaux en fonction de ce à quoi elles doivent réagir. Un formulaire pourrait n'avoir besoin que de `onUpload` pour savoir quand les fichiers atteignent le serveur ; un chargeur avec une interface utilisateur de progression a besoin de `onListProgress` et `onComplete` ; une zone de dépôt qui doit remonter des rejets a besoin de `onReject`.

La plupart des événements portant des fichiers exposent à la fois `getFile()` (le premier ou le seul fichier dans la charge utile) et `getFiles()` (la liste complète). Utilisez `getFile()` pour des événements d'un seul fichier comme `onReject`, et `getFiles()` lorsque vous attendez un batch. `UploadCompleteEvent` est l'exception ; il a son propre `getUploadedFiles()` et `getFailedFiles()` car le résultat du batch est divisé entre réussites et échecs.

### Actions de l'utilisateur {#user-actions}

Celles-ci se déclenchent en réponse à quelque chose que l'utilisateur fait sur le composant dans son ensemble. Elles ne disent rien sur la progression du transfert, juste que l'utilisateur a fait quelque chose à quoi l'application pourrait vouloir réagir.

| Événement | Déclenche |
| --- | --- |
| `UploadChangeEvent` | Lorsque la liste des fichiers choisis change |
| `UploadEvent` | Lorsque l'utilisateur clique sur **Télécharger** et que les fichiers atteignent le serveur |
| `UploadCancelEvent` | Lorsque l'utilisateur clique sur **Annuler** |
| `UploadFilterChangeEvent` | Lorsque le filtre actif change |

```java
upload.onChange(e -> {
    // Se déclenche chaque fois que la liste des fichiers choisis change.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Se déclenche lorsque le téléchargement est déclenché ; les fichiers ont atteint le serveur.
});
```

`UploadEvent` et `UploadCompleteEvent` se ressemblent à première vue, mais ils répondent à des questions différentes. `UploadEvent` se déclenche lorsque l'utilisateur déclenche explicitement le téléchargement (ou `setAutoUpload()` le déclenche pour son compte), et c'est l'endroit naturel pour conserver ou transmettre les fichiers téléchargés. `UploadCompleteEvent` se déclenche une fois le transfert de chaque fichier enfile fini, et c'est le bon crochet pour les mises à jour d'interface de "le batch est terminé".

### Transfert par fichier {#per-file-transfer}

Celles-ci se déclenchent une fois par fichier, pendant qu'un transfert est en cours ou juste après qu'il ait échoué. Utilisez-les lorsque l'interface utilisateur doit refléter l'état de fichiers individuels plutôt que du batch.

| Événement | Déclenche |
| --- | --- |
| `UploadProgressEvent` | Pendant qu'un fichier unique est transféré |
| `UploadErrorEvent` | Lorsque le transfert d'un fichier unique échoue |
| `UploadRejectEvent` | Lorsque un fichier choisi ou déposé ne respecte pas les contraintes configurées |

```java
upload.onProgress(e -> {
    // Se déclenche de manière répétée pendant le transfert d'un fichier unique.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Se déclenche lorsque un fichier est rejeté pour des raisons de taille, de nombre ou de filtre.
    String reason = e.getMessage();
});
```

Dans ce groupe, `UploadRejectEvent` est l'intrus. Il se déclenche avant que des octets ne soient déplacés, lorsqu'un fichier échoue à une contrainte côté client comme `setMaxFileSize` ou `setMaxFiles`. `UploadErrorEvent`, en revanche, se déclenche après que le transfert ait démarré et que quelque chose se soit mal passé en route vers le serveur.

### Lot entier {#whole-batch}

Celles-ci se déclenchent sur le lot plutôt que sur un fichier individuel. Utilisez-les pour des interfaces utilisateur agrégées comme une barre de progression globale ou un message "terminé" qui résume l'ensemble du choix.

| Événement | Déclenche |
| --- | --- |
| `UploadListProgressEvent` | Aux côtés de `UploadProgressEvent`, avec l'état de l'ensemble de la liste |
| `UploadCompleteEvent` | Une fois par lot, lorsque chaque fichier a fini de se transférer |

```java
upload.onComplete(e -> {
    // Se déclenche une fois lorsque l'ensemble du lot est terminé.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` et `onListProgress` couvrent le même transfert sous deux angles. `onProgress` est par fichier, et c'est le crochet approprié lorsque chaque fichier a sa propre interface de progression. `onListProgress` se déclenche à côté avec des compteurs agrégés (`getListTotal`, `getListRemaining`, `getListProgress`) pour un indicateur global à l'échelle d'un lot.

Dans l'exemple suivant, `onChange`, `onListProgress` et `onComplete` pilotent une barre de progression et une ligne de statut qui se mettent à jour à mesure que la liste des fichiers change et que les fichiers se transfèrent.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
  'src/main/frontend/css/upload/uploadEvents.css'
]}
height='450px'
/>

## Internationalisation (i18n) {#internationalization-i18n}

Les étiquettes et messages à l'intérieur du composant sont personnalisables via le bundle `FileUploadI18n`. Le type de bundle conserve le nom `FileUploadI18n` car il est partagé avec le modal [`FileUploadDialog`](/docs/components/option-dialogs/file-upload).

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Envoyer");
bundle.setCancel("Discard");
bundle.setDropFile("Déposez le fichier ici");
upload.setI18n(bundle);
```

## Thèmes {#themes}

`UploadTheme` reflète la palette de thèmes standard de DWC et inclut des variantes en contour pour un poids visuel plus léger. Les thèmes s'appliquent au bouton de sélecteur, au bouton de téléchargement et au bouton d'annulation. La liste et la zone de dépôt conservent un style neutre indépendamment du thème.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

La démo ci-dessous montre le thème `PRIMARY` combiné avec le préréglage `INLINE`.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Stylisation {#styling}

<TableBuilder name="Upload" />
