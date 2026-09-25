---
title: Composants d'interface
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Browse the webforJ UI component catalog covering layouts, data entry,
  navigation, feedback, and visualization components.
_i18n_hash: 200027a33988025dba52cd07c34d2e27
---
<Head>
  <title>Composants UI | Composants de construction d'applications d'interface utilisateur</title>
</Head>

Dans webforJ, les applications sont créées à l'aide d'unités modulaires connues sous le nom de Composants, qui facilitent le développement rapide et efficace de l'UI. Le cadre offre une gamme de composants essentiels tels que des boutons, des éléments d'entrée et des conteneurs de mise en page. Après avoir maîtrisé les fondamentaux, vous pouvez consulter les [JavaDocs](https://javadoc.io/doc/com.webforj) pour un aperçu détaillé de tous les composants et de leurs fonctionnalités.

## Mise en page {#layouts}

Les composants de mise en page fournissent la base pour structurer les interfaces utilisateur, permettant aux développeurs d'organiser le contenu de manière efficace. Ces composants offrent diverses façons de contrôler l'agencement des composants enfants, que ce soit pour des mises en page simples ou complexes.

Les composants de mise en page suivants sont conçus pour gérer un large éventail de cas d'utilisation, du design réactif à la gestion avancée de contenu.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Un composant conteneur qui fournit une mise en page structurée pour la navigation et l'organisation du contenu de l'application de niveau supérieur.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Un composant conteneur horizontal qui contient un ensemble de boutons d'action, d'icônes ou d'autres contrôles, généralement utilisé pour effectuer des tâches liées au contexte actuel.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Un composant de mise en page qui arrange ses enfants en utilisant des règles de boîte flexible (flexbox) pour un design réactif et un alignement.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Un composant de mise en page qui arrange ses enfants en plusieurs colonnes verticales, utile pour créer des formulaires et des structures en grille.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Un composant de mise en page qui divise l'espace disponible entre deux composants enfants, permettant aux utilisateurs de les redimensionner en faisant glisser la barre de séparation.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Un composant de panneau coulissant généralement utilisé pour la navigation latérale ou pour abriter un contenu supplémentaire pouvant être affiché ou masqué.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Un composant de fenêtre modale qui superpose du contenu pour afficher des informations importantes ou inciter à l'interaction de l'utilisateur, nécessitant souvent une action de la part de l'utilisateur pour se fermer.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Un composant qui fournit une interface utilisateur préconçue pour l'authentification des utilisateurs, comprenant généralement des champs pour le nom d'utilisateur et le mot de passe avec un bouton de soumission.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Un ensemble empilé verticalement de panneaux réductibles, chacun avec un en-tête cliquable qui alterne la visibilité de son contenu corporel.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Un composant conteneur qui organise le contenu en plusieurs onglets, permettant aux utilisateurs de passer d'une vue ou section à une autre.</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Une surface qui regroupe du contenu et des actions connexes, avec des régions pour les médias, les en-têtes, le contenu principal et les pieds de page.</p>
  </GalleryCard>
</GalleryGrid>

## Saisie de données {#data-entry}

Les composants de saisie de données fournissent des outils essentiels pour capturer les entrées des utilisateurs et gérer les interactions au sein de votre application. Ces composants sont polyvalents, facilitant la création de formulaires interactifs et la collecte de divers types de données.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Un composant d'entrée à une seule ligne pour saisir et modifier des données textuelles.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Un composant d'entrée de texte qui limite la saisie de l'utilisateur à un format ou à un motif spécifique, généralement utilisé pour des champs tels que les numéros de téléphone, les dates ou les numéros de carte de crédit.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Un composant qui fournit un champ d'entrée basé sur le navigateur pour saisir des valeurs numériques, avec des contrôles intégrés pour incrémenter ou décrémenter la valeur.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Un composant d'entrée numérique qui limite la saisie de l'utilisateur à un format ou à un motif numérique spécifique, garantissant une saisie valide de nombre tel que pour la monnaie, les pourcentages ou d'autres nombres formatés.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Un composant d'entrée à une seule ligne pour saisir et masquer les données de mot de passe de manière sécurisée.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Un composant qui fournit un sélecteur de date basé sur le navigateur pour sélectionner une date par le biais d'un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Un composant d'entrée de date qui impose un format ou un motif de date spécifique, garantissant que l'utilisateur saisit une date valide selon le masque défini.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Un composant qui fournit un sélecteur d'heure basé sur le navigateur pour sélectionner une valeur horaire par le biais d'un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Un composant d'entrée horaire qui impose un format ou un motif horaire spécifique, garantissant que l'utilisateur saisit une heure valide selon le masque défini.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Un composant qui fournit un sélecteur de date et d'heure basé sur le navigateur pour sélectionner à la fois la date et l'heure via un seul champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Un composant qui fournit un sélecteur de couleur basé sur le navigateur, permettant aux utilisateurs de sélectionner une couleur à partir d'un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Un composant d'entrée de texte multi-lignes qui permet aux utilisateurs de saisir ou de modifier de plus grands blocs de texte.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Un composant qui représente une option binaire, permettant aux utilisateurs de basculer entre un état coché (vrai) ou non coché (faux).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Un composant qui permet aux utilisateurs de sélectionner une seule option parmi un groupe de choix mutuellement exclusifs.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Un composant à bascule qui permet aux utilisateurs de passer entre deux états, tels que activé/désactivé ou vrai/faux, avec une action de glissement.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Un composant qui fournit une liste déroulante d'options prédéfinies, permettant aux utilisateurs de sélectionner une option parmi la liste.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Un composant qui combine une liste déroulante avec un champ de texte éditable, permettant aux utilisateurs de sélectionner une option de la liste ou d'entrer une valeur personnalisée.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Un composant qui affiche une liste déroulante d'options, permettant aux utilisateurs de sélectionner un ou plusieurs éléments de la liste.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Un sélecteur de fichiers en ligne qui permet aux utilisateurs de sélectionner un ou plusieurs fichiers de leur machine locale et de les télécharger sur le serveur, avec des fonctionnalités de glisser-déposer, des filtres et un suivi des événements par fichier.</p>
  </GalleryCard>
</GalleryGrid>

## Boîtes de dialogue d'options {#option-dialogs}

Les boîtes de dialogue d'options fournissent un moyen de présenter aux utilisateurs des choix ou de les inviter à confirmer avant de procéder à une action. Ces composants sont essentiels pour créer des flux de travail interactifs orientés vers la décision, permettant aux utilisateurs de confirmer, d'annuler ou de choisir parmi diverses options de manière claire et structurée.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Un composant de dialogue utilisé pour afficher des messages ou des alertes d'information à l'utilisateur, généralement avec un seul bouton `OK` pour reconnaître le message.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Un composant de dialogue qui demande à l'utilisateur de confirmer ou d'annuler une action, fournissant généralement des boutons `Oui` et `Non` ou `OK` et `Annuler`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Un composant de dialogue qui invite l'utilisateur à saisir du texte ou des données, fournissant généralement un champ d'entrée ainsi que des boutons d'action comme `OK` et `Annuler`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Un composant de dialogue qui permet aux utilisateurs de parcourir et de sélectionner des fichiers dans le système de fichiers du serveur.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Un composant de dialogue qui permet aux utilisateurs de télécharger des fichiers depuis leur système de fichiers local vers l'application.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Un composant de dialogue qui permet aux utilisateurs d'enregistrer un fichier à un emplacement spécifié sur le système de fichiers du serveur.</p>
  </GalleryCard>
</GalleryGrid>

## Interaction et affichage {#interaction-and-display}

Cette catégorie comprend des composants qui facilitent les interactions utilisateur et affichent visuellement des données ou des états de l'application. Ces composants aident les utilisateurs à naviguer dans l'application, à déclencher des actions et à comprendre les progrès ou les résultats par le biais d'éléments visuels dynamiques.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Un composant utilisé pour afficher des données dans un format structuré et tabulaire avec des lignes et des colonnes, prenant en charge des fonctionnalités telles que le tri et la pagination.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Un composant qui s'intègre avec Google Charts pour afficher divers types de graphiques et de représentations visuelles de données dans l'application.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Un composant cliquable qui déclenche une action ou un événement lorsqu'il est pressé.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Un composant de notification léger et non bloquant qui affiche brièvement un message à l'utilisateur avant de disparaître automatiquement.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Un composant qui affiche des messages ou avertissements importants dans un format perceptible pour attirer l'attention de l'utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Un petit composant d'étiquette pour afficher des décomptes, des statuts ou des métadonnées courtes, avec prise en charge des thèmes, des tailles et des icônes.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Un composant qui utilise l'API de Notification native du navigateur pour alerter les utilisateurs avec des notifications de bureau personnalisées.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Un composant de pagination personnalisable pour naviguer dans des ensembles de données, prenant en charge des mises en page avec des boutons premier, dernier, suivant, précédent, et des champs de saut rapide.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Un composant qui représente visuellement la progression d'une tâche ou d'un processus, généralement affiché sous forme de barre horizontale qui se remplit au fur et à mesure des progrès.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Un composant qui permet aux utilisateurs de sélectionner une valeur dans une plage définie en faisant glisser une poignée le long d'une piste.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Un indicateur visuel global de l'application, généralement un spinner, signalant qu'un processus global est en cours.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Un indicateur de chargement contextualisé qui montre dans un composant parent spécifique, indiquant que du contenu ou des données sont en cours de chargement dans cette section.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Un composant qui affiche une animation de rotation, généralement utilisé pour indiquer qu'un processus ou une action est en cours.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Un composant qui fournit un menu de navigation pour l'application, généralement utilisé pour lister des liens ou des éléments de navigation pour passer entre différentes sections ou vues.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Un composant qui affiche un symbole graphique ou une image, souvent utilisé pour représenter une action, un statut ou une catégorie dans l'interface utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Un composant qui simule une interface de ligne de commande (CLI) au sein de l'application, permettant aux utilisateurs de saisir et d'exécuter des commandes basées sur du texte.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Un composant qui charge plus d'éléments lors du défilement, affiche un chargeur et suit lorsque tout le contenu est récupéré.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Un composant qui permet une interaction de tirage pour actualiser dans des conteneurs déroulants, idéal pour le chargement dynamique de données.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Un composant pour afficher des données hiérarchiques, permettant aux utilisateurs d'étendre, de réduire et d'interagir avec des éléments imbriqués.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Un composant pour afficher des photos de profil d'utilisateur ou des initiales, avec prise en charge de différentes tailles, shapes et thèmes.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Un composant pour afficher du contenu markdown avec un rendu progressif caractère par caractère, idéal pour les interfaces de chat AI et le texte en streaming.</p>
  </GalleryCard>

</GalleryGrid>
