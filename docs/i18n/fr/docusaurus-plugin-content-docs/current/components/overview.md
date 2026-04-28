---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 2af867ffb7bb39ed4624efa14b81d452
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Composants UI | Composants pour la création d'applications d'interface utilisateur</title>
</Head>

Dans webforJ, les applications sont créées à l'aide d'unités modulaires connues sous le nom de composants, qui facilitent le développement rapide et efficace de l'interface utilisateur. Le cadre offre une gamme de composants essentiels tels que des boutons, des éléments de saisie et des conteneurs de mise en page. Après avoir maîtrisé les fondamentaux, vous pouvez consulter les [JavaDocs](https://javadoc.io/doc/com.webforj) pour un aperçu détaillé de tous les composants et de leurs fonctionnalités.

## Mise en page {#layouts}

Les composants de mise en page fournissent la base pour structurer les interfaces utilisateur, permettant aux développeurs d'organiser le contenu de manière efficace. Ces composants offrent diverses façons de contrôler l'arrangement des composants enfants, que ce soit pour des mises en page simples ou complexes.

Les composants de mise en page suivants sont conçus pour gérer un large éventail de cas d'utilisation, de la conception réactive à la gestion avancée du contenu.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Un composant conteneur qui fournit une mise en page structurée pour la navigation et l'organisation du contenu de niveau supérieur de l'application.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Un composant conteneur horizontal qui contient un ensemble de boutons d'action, icônes ou autres contrôles, généralement utilisés pour effectuer des tâches liées au contexte actuel.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Un composant de mise en page qui arrange ses enfants en utilisant des règles de boîte flexible (flexbox) pour une conception réactive et un alignement.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Un composant de mise en page qui dispose ses enfants en plusieurs colonnes verticales, utile pour créer des formulaires et des structures de type grille.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Un composant de mise en page qui divise l'espace disponible entre deux composants enfants, permettant aux utilisateurs de les redimensionner en faisant glisser la barre de séparation.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Un composant de panneau coulissant généralement utilisé pour la navigation latérale ou héberger du contenu supplémentaire pouvant être affiché ou masqué.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Un composant de fenêtre modale qui superpose le contenu pour afficher des informations importantes ou inciter à l'interaction utilisateur, nécessitant souvent une action de l'utilisateur pour se fermer.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Un composant qui fournit une interface utilisateur préconstruite pour l'authentification des utilisateurs, incluant généralement des champs pour le nom d'utilisateur et le mot de passe avec un bouton de soumission.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Un ensemble de panneaux empilés verticalement et pliables, chacun avec un en-tête cliquable qui active la visibilité de son contenu.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Un composant conteneur qui organise le contenu en plusieurs onglets, permettant aux utilisateurs de passer d'une vue ou section à une autre.</p>
  </GalleryCard>
</GalleryGrid>

## Saisie de données {#data-entry}

Les composants de saisie de données fournissent des outils essentiels pour capturer les saisies des utilisateurs et gérer les interactions au sein de votre application. Ces composants sont polyvalents, facilitant la création de formulaires interactifs et la collecte de divers types de données.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Un composant d'entrée à une ligne pour saisir et modifier des données textuelles.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Un composant de saisie de texte qui limite la saisie de l'utilisateur à un format ou un modèle spécifique, généralement utilisé pour des champs comme les numéros de téléphone, les dates ou les numéros de carte de crédit.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Un composant qui fournit un champ d'entrée basé sur le navigateur pour entrer des valeurs numériques, avec des contrôles intégrés pour incrémenter ou décrémenter la valeur.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Un composant de saisie numérique qui limite la saisie de l'utilisateur à un format ou un modèle numérique spécifique, garantissant une saisie valide, comme pour la monnaie, les pourcentages ou d'autres nombres formatés.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Un composant d'entrée à une ligne pour saisir et masquer en toute sécurité des données de mot de passe.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Un composant qui fournit un sélecteur de date basé sur le navigateur pour sélectionner une date via un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Un composant de saisie de date qui impose un format ou un modèle de date spécifique, garantissant que l'utilisateur saisit une date valide selon le masque défini.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Un composant qui fournit un sélecteur de temps basé sur le navigateur pour sélectionner une valeur horaire via un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Un composant de saisie horaire qui impose un format ou un modèle temporel spécifique, garantissant que l'utilisateur saisit une heure valide selon le masque défini.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Un composant qui fournit un sélecteur de date et d'heure basé sur le navigateur pour sélectionner à la fois une date et une heure via un champ d'entrée unique.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Un composant qui fournit un sélecteur de couleur basé sur le navigateur, permettant aux utilisateurs de sélectionner une couleur via un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Un composant d'entrée de texte multi-lignes qui permet aux utilisateurs d'entrer ou de modifier de plus grands blocs de texte.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Un composant qui représente une option binaire, permettant aux utilisateurs de basculer entre un état coché (vrai) ou non coché (faux).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Un composant qui permet aux utilisateurs de sélectionner une seule option parmi un groupe de choix mutuellement exclusifs.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Un composant de basculement qui permet aux utilisateurs de passer entre deux états, comme activé/désactivé ou vrai/faux, avec une action glissante.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Un composant qui fournit une liste déroulante d'options prédéfinies, permettant aux utilisateurs de sélectionner une option dans la liste.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Un composant qui combine une liste déroulante avec une saisie de texte modifiable, permettant aux utilisateurs de soit sélectionner une option dans la liste, soit entrer une valeur personnalisée.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Un composant qui affiche une liste déroulante d'options, permettant aux utilisateurs de sélectionner un ou plusieurs éléments de la liste.</p>
  </GalleryCard>
</GalleryGrid>

## Dialogues d'option {#option-dialogs}

Les dialogues d'option offrent un moyen de présenter des choix aux utilisateurs ou de les inciter à confirmer avant de procéder à une action. Ces composants sont essentiels pour créer des workflows interactifs axés sur la décision, permettant aux utilisateurs de confirmer, d'annuler ou de choisir parmi diverses options de manière claire et structurée.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Un composant de dialogue utilisé pour afficher des messages ou des alertes informatives à l'utilisateur, généralement avec un seul bouton `OK` pour reconnaitre le message.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Un composant de dialogue qui demande à l'utilisateur de confirmer ou d'annuler une action, fournissant généralement des boutons `Oui` et `Non` ou `OK` et `Annuler`.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Un composant de dialogue qui invite l'utilisateur à saisir du texte ou des données, fournissant généralement un champ d'entrée avec des boutons d'action comme `OK` et `Annuler`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Un composant de dialogue qui permet aux utilisateurs de parcourir et sélectionner des fichiers depuis le système de fichiers du serveur.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Un composant de dialogue qui permet aux utilisateurs de télécharger des fichiers depuis leur système de fichiers local vers l'application.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Un composant de dialogue qui permet aux utilisateurs de sauvegarder un fichier à un emplacement spécifié sur le système de fichiers du serveur.</p>
  </GalleryCard>
</GalleryGrid>

## Interaction et affichage {#interaction-and-display}

Cette catégorie comprend des composants qui facilitent les interactions utilisateur et affichent visuellement des données ou des états d'application. Ces composants aident les utilisateurs à naviguer dans l'application, à déclencher des actions et à comprendre les progrès ou les résultats à travers des éléments visuels dynamiques.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Un composant utilisé pour afficher des données dans un format tabulaire structuré avec des lignes et des colonnes, prenant en charge des fonctionnalités telles que le tri et la pagination.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Un composant qui s'intègre à Google Charts pour afficher divers types de graphiques et de représentations de données visuelles dans l'application.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Un composant cliquable qui déclenche une action ou un événement lorsqu'il est pressé.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Un composant de notification léger et non bloquant qui affiche brièvement un message à l'utilisateur avant de disparaître automatiquement.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Un composant qui affiche des messages ou des alertes importants dans un format saisissant pour attirer l'attention de l'utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Un petit composant d'étiquette pour afficher des comptes, des états ou de courtes métadonnées, avec support pour des thèmes, tailles et icônes.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Un composant qui utilise l’API de notification native du navigateur pour alerter les utilisateurs avec des notifications de bureau personnalisées.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Un composant de pagination personnalisable pour naviguer dans des ensembles de données, prenant en charge des mises en page avec des boutons premier, dernier, suivant, précédent et des champs de saut rapide.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Un composant qui représente visuellement le progrès d'une tâche ou d'un processus, généralement affiché sous la forme d'une barre horizontale qui se remplit au fur et à mesure des progrès réalisés.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Un composant qui permet aux utilisateurs de sélectionner une valeur dans une plage définie en faisant glisser un curseur le long d'une piste.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Un indicateur visuel à l'échelle de l'application, généralement un chargement, signalant qu'un processus global est en cours.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Un indicateur de chargement de portée qui montre à l'intérieur d'un composant parent spécifique, indiquant que le contenu ou les données sont en cours de chargement dans cette section.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Un composant qui affiche une animation de rotation, généralement utilisé pour indiquer qu'un processus ou une action est en cours.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Un composant qui fournit un menu de navigation pour l'application, généralement utilisé pour lister des liens ou des éléments de navigation pour passer entre différentes sections ou vues.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Un composant qui affiche un symbole graphique ou une image, souvent utilisé pour représenter une action, un état ou une catégorie dans l'interface utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Un composant qui simule une interface de ligne de commande (CLI) au sein de l'application, permettant aux utilisateurs d'entrer et d'exécuter des commandes sous forme textuelle.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Un composant qui charge plus d'éléments lors du défilement, montre un indicateur de chargement et suit quand tout le contenu est récupéré.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Un composant qui permet une interaction de tirage pour rafraîchir au sein de conteneurs défilables, idéal pour le chargement dynamique de données.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Un composant pour afficher des données hiérarchiques, permettant aux utilisateurs d'élargir, de réduire et d'interagir avec des éléments imbriqués.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Un composant pour afficher les photos de profil des utilisateurs ou leurs initiales, avec prise en charge de différentes tailles, formes et thèmes.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Un composant pour afficher du contenu markdown avec un rendu progressif caractère par caractère, idéal pour les interfaces de chat IA et le texte en streaming.</p>
  </GalleryCard>
  
</GalleryGrid>
