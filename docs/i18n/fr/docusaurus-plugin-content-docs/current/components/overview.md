---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: f66c1b418ace12cb1945ab33fd272362
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Composants UI | Composants de construction d'applications d'interface utilisateur</title>
</Head>

Dans webforJ, les applications sont créées à l'aide d'unités modulaires connues sous le nom de Composants, qui facilitent le développement rapide et efficace de l'interface utilisateur. Le cadre propose une gamme de composants essentiels tels que des boutons, des éléments de saisie et des conteneurs de mise en page. Après avoir maîtrisé les concepts fondamentaux, vous pouvez consulter les [JavaDocs](https://javadoc.io/doc/com.webforj) pour un aperçu détaillé de tous les composants et de leurs fonctionnalités.

## Mises en page {#layouts}

Les composants de mise en page fournissent la base pour structurer les interfaces utilisateur, permettant aux développeurs d'organiser efficacement le contenu. Ces composants offrent diverses manières de contrôler l'agencement des composants enfants, que ce soit pour des mises en page simples ou complexes.

Les composants de mise en page suivants sont conçus pour gérer un large éventail de cas d'utilisation, allant de la conception réactive à la gestion de contenu avancée.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Un composant conteneur qui fournit une mise en page structurée pour la navigation principale de l'application et l'organisation du contenu.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Un composant conteneur horizontal qui contient un ensemble de boutons d'action, d'icônes ou d'autres contrôles, généralement utilisé pour effectuer des tâches liées au contexte actuel.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Un composant de mise en page qui arrange ses enfants à l'aide de règles de boîte flexible (flexbox) pour une conception réactive et un alignement.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Un composant de mise en page qui arrange ses enfants en plusieurs colonnes verticales, utile pour créer des formulaires et des structures en grille.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Un composant de mise en page qui divise l'espace disponible entre deux composants enfants, permettant aux utilisateurs de les redimensionner en faisant glisser la barre du splitter.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Un composant de panneau coulissant généralement utilisé pour la navigation latérale ou pour héberger un contenu supplémentaire qui peut être affiché ou masqué.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Un composant de fenêtre modale qui superpose le contenu pour afficher des informations importantes ou inviter à une interaction utilisateur, nécessitant souvent une action de la part de l'utilisateur pour se fermer.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Un composant qui fournit une interface utilisateur préconçue pour l'authentification des utilisateurs, généralement incluant des champs pour le nom d'utilisateur et le mot de passe ainsi qu'un bouton de soumission.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Un composant conteneur qui organise le contenu en plusieurs onglets, permettant aux utilisateurs de passer d'une vue ou section à une autre.</p>
  </GalleryCard>
</GalleryGrid>

## Saisie de données {#data-entry}

Les composants de saisie de données fournissent des outils essentiels pour capturer l'entrée utilisateur et gérer les interactions au sein de votre application. Ces composants sont polyvalents, facilitant la création de formulaires interactifs et la collecte de divers types de données.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Un composant d'entrée à une seule ligne pour saisir et modifier des données textuelles.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Un composant d'entrée de texte qui restreint l'entrée de l'utilisateur à un format ou un motif spécifique, généralement utilisé pour des champs comme les numéros de téléphone, les dates ou les numéros de carte de crédit.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Un composant qui fournit un champ d'entrée basé sur le navigateur par défaut pour entrer des valeurs numériques, avec des contrôles intégrés pour incrémenter ou décrémenter la valeur.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Un composant d'entrée numérique qui restreint l'entrée de l'utilisateur à un format ou un motif numérique spécifique, garantissant une entrée valide de nombre, comme pour la monnaie, les pourcentages ou d'autres nombres formatés.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Un composant d'entrée à une seule ligne pour saisir et masquer en toute sécurité des données de mot de passe.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Un composant qui fournit un sélecteur de date basé sur le navigateur par défaut pour sélectionner une date via un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Un composant d'entrée de date qui impose un format ou un motif de date spécifique, garantissant que l'utilisateur entre une date valide selon le masque défini.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Un composant qui fournit un sélecteur de temps basé sur le navigateur par défaut pour sélectionner une valeur de temps via un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Un composant d'entrée de temps qui impose un format ou un motif de temps spécifique, garantissant que l'utilisateur saisit une heure valide selon le masque défini.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Un composant qui fournit un sélecteur de date et d'heure basé sur le navigateur par défaut pour sélectionner à la fois une date et une heure via un seul champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Un composant qui fournit un sélecteur de couleur basé sur le navigateur par défaut, permettant aux utilisateurs de sélectionner une couleur dans un champ d'entrée.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Un composant d'entrée de texte multi-lignes qui permet aux utilisateurs d'entrer ou de modifier des blocs de texte plus grands.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Un composant qui représente une option binaire, permettant aux utilisateurs de basculer entre un état coché (vrai) ou non coché (faux).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Un composant qui permet aux utilisateurs de sélectionner une seule option parmi un groupe de choix mutuellement exclusifs.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Un composant toggle qui permet aux utilisateurs de basculer entre deux états, comme activé/désactivé ou vrai/faux, avec une action de glissement.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Un composant qui fournit une liste déroulante d'options prédéfinies, permettant aux utilisateurs de sélectionner une option parmi la liste.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Un composant qui combine une liste déroulante avec un champ de texte modifiable, permettant aux utilisateurs soit de sélectionner une option dans la liste, soit d'entrer une valeur personnalisée.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Un composant qui affiche une liste déroulante d'options, permettant aux utilisateurs de sélectionner un ou plusieurs éléments de la liste.</p>
  </GalleryCard>
</GalleryGrid>

## Dialogues d'options {#option-dialogs}

Les dialogues d'options offrent un moyen de présenter aux utilisateurs des choix ou de leur demander confirmation avant de procéder à une action. Ces composants sont essentiels pour créer des flux de travail interactifs axés sur les décisions, permettant aux utilisateurs de confirmer, d'annuler ou de choisir parmi diverses options de manière claire et structurée.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Un composant de dialogue utilisé pour afficher des messages d'information ou des alertes à l'utilisateur, généralement avec un bouton `OK` unique pour accuser réception du message.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Un composant de dialogue qui demande à l'utilisateur de confirmer ou d'annuler une action, fournissant généralement des boutons `Oui` et `Non` ou `OK` et `Annuler`.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Un composant de dialogue qui invite l'utilisateur à saisir du texte ou des données, fournissant généralement un champ d'entrée accompagné de boutons d'action tels que `OK` et `Annuler`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Un composant de dialogue qui permet aux utilisateurs de parcourir et de sélectionner des fichiers depuis le système de fichiers du serveur.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Un composant de dialogue qui permet aux utilisateurs de télécharger des fichiers depuis leur système de fichiers local vers l'application.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Un composant de dialogue qui permet aux utilisateurs de sauvegarder un fichier à un emplacement spécifié sur le système de fichiers du serveur.</p>
  </GalleryCard>
</GalleryGrid>

## Interaction et affichage {#interaction-and-display}

Cette catégorie comprend des composants qui facilitent les interactions utilisateur et affichent visuellement les données ou les états de l'application. Ces composants aident les utilisateurs à naviguer dans l'application, à déclencher des actions et à comprendre les progrès ou les résultats à l'aide d'éléments visuels dynamiques.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>Un composant utilisé pour afficher des données dans un format tabulaire structuré avec des lignes et des colonnes, prenant en charge des fonctionnalités telles que le tri et la pagination.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Un composant qui s'intègre avec Google Charts pour afficher divers types de graphiques et de représentations de données visuelles dans l'application.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Un composant cliquable qui déclenche une action ou un événement lorsqu'il est pressé.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png"  effect="slideUp">
    <p>Un composant de notification léger et non-bloquant qui affiche brièvement un message à l'utilisateur avant de disparaître automatiquement.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Un composant qui affiche des messages importants ou des avertissements dans un format noticeable pour capter l'attention de l'utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Un composant qui utilise l'API Notification native du navigateur pour alerter les utilisateurs avec des notifications de bureau personnalisées.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Un composant de pagination personnalisable pour naviguer à travers des ensembles de données, prenant en charge des mises en page avec des boutons premier, dernier, suivant, précédent, et des champs de saut rapide.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Un composant qui représente visuellement les progrès d'une tâche ou d'un processus, généralement affiché sous forme de barre horizontale qui se remplit à mesure que le progrès est réalisé.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Un composant qui permet aux utilisateurs de sélectionner une valeur dans une plage définie en faisant glisser un curseur le long d'une piste.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Un indicateur visuel dans toute l'application, généralement un spinner, signalant qu'un processus global est en cours.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Un indicateur de chargement spécifique qui se montre dans un composant parent particulier, indiquant que le contenu ou les données sont en cours de chargement dans cette section.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Un composant qui affiche une animation de rotation, généralement utilisé pour indiquer qu'un processus ou une action est en cours.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Un composant qui fournit un menu de navigation pour l'application, généralement utilisé pour lister des liens ou des éléments de navigation pour passer entre différentes sections ou vues.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Un composant qui affiche un symbole graphique ou une image, souvent utilisé pour représenter une action, un état ou une catégorie dans l'interface utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Un composant qui simule une interface en ligne de commande (CLI) au sein de l'application, permettant aux utilisateurs d'entrer et d'exécuter des commandes textuelles.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Un composant qui charge plus d'éléments lors du défilement, affiche un chargeur et suit quand tout le contenu est récupéré.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Un composant qui permet une interaction de rafraîchissement par tirage dans des conteneurs défilables, idéal pour le chargement de données dynamiques.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Un composant pour afficher des données hiérarchiques, permettant aux utilisateurs d'étendre, de réduire et d'interagir avec des éléments imbriqués.</p>
  </GalleryCard>
</GalleryGrid>
