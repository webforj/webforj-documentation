---
title: Composants d'interface
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Parcourez le catalogue de composants d’interface webforJ : mise en page,
  saisie de données, navigation, retour d’information et visualisation.
_i18n_title_hash: 45ac037def4dc03161f6bedffcc3b307
_i18n_description_hash: 97182b12d746c9ed301eeeced3480321
_i18n_hash: 9bc06f09a5141c15a7f4f21d22457571
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Composants d’interface Java | webforJ</title>
</Head>

Dans webforJ, les applications sont créées à l’aide d’unités modulaires appelées composants, qui facilitent le développement rapide et efficace d’interfaces utilisateur. Le framework propose une gamme de composants essentiels, tels que des boutons, des champs de saisie et des conteneurs de mise en page. Une fois les notions fondamentales maîtrisées, consultez les [JavaDocs](https://javadoc.io/doc/com.webforj) pour obtenir une présentation détaillée de tous les composants et de leurs fonctionnalités.

## Mises en page {#layouts}

Les composants de mise en page constituent la base de la structuration des interfaces utilisateur et permettent aux développeurs d’organiser efficacement le contenu. Ils offrent différentes possibilités de contrôler la disposition des composants enfants, qu’il s’agisse de mises en page simples ou complexes.

Les composants de mise en page suivants sont conçus pour répondre à un large éventail de besoins, de la conception adaptative à la gestion avancée du contenu.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Un composant conteneur qui fournit une mise en page structurée pour la navigation principale de l’application et l’organisation du contenu.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Un composant conteneur horizontal qui regroupe un ensemble de boutons d’action, d’icônes ou d’autres commandes, généralement utilisé pour effectuer des tâches liées au contexte actuel.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Un composant de mise en page qui dispose ses enfants selon les règles de la boîte flexible (flexbox), pour une conception adaptative et un alignement optimal.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Un composant de mise en page qui répartit ses enfants en plusieurs colonnes verticales, utile pour créer des formulaires et des structures en grille.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Un composant de mise en page qui répartit l’espace disponible entre deux composants enfants et permet aux utilisateurs de les redimensionner en faisant glisser la barre de séparation.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Un panneau coulissant généralement utilisé pour la navigation latérale ou pour héberger du contenu supplémentaire, qui peut être affiché ou masqué.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Une fenêtre modale qui s’affiche par-dessus le contenu pour présenter des informations importantes ou solliciter une interaction de l’utilisateur, et dont la fermeture nécessite souvent une action de sa part.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Un composant qui fournit une interface utilisateur préconçue pour l’authentification, généralement composée de champs pour le nom d’utilisateur et le mot de passe, ainsi que d’un bouton de soumission.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Un ensemble de panneaux repliables empilés verticalement, chacun doté d’un en-tête cliquable qui permet d’afficher ou de masquer son contenu.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Un composant conteneur qui organise le contenu en plusieurs onglets, permettant aux utilisateurs de passer d’une vue ou d’une section à l’autre.</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Une surface qui regroupe du contenu et des actions liés, avec des zones dédiées aux médias, aux en-têtes, au contenu principal et aux pieds de page.</p>
  </GalleryCard>
</GalleryGrid>

## Saisie de données {#data-entry}

Les composants de saisie de données fournissent les outils essentiels pour recueillir les saisies des utilisateurs et gérer les interactions dans votre application. Polyvalents, ils facilitent la création de formulaires interactifs et la collecte de différents types de données.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Un composant de saisie sur une seule ligne permettant de saisir et de modifier du texte.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masqué</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Un composant de saisie de texte qui limite les saisies à un format ou à un modèle spécifique, généralement utilisé pour des champs tels que les numéros de téléphone, les dates ou les numéros de carte de crédit.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Un composant qui fournit un champ de saisie natif du navigateur pour entrer des valeurs numériques, avec des commandes intégrées permettant d’augmenter ou de diminuer la valeur.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masqué</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Un composant de saisie numérique qui limite les saisies à un format ou à un modèle numérique spécifique, garantissant ainsi la validité des nombres entrés, par exemple pour des devises, des pourcentages ou d’autres nombres formatés.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Un composant de saisie sur une seule ligne permettant de saisir un mot de passe de manière sécurisée et d’en masquer le contenu.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Un composant qui fournit un sélecteur de date natif du navigateur pour choisir une date dans un champ de saisie.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masqué</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Un composant de saisie de date qui impose un format ou un modèle spécifique, garantissant que l’utilisateur saisit une date valide conformément au masque défini.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Un composant qui fournit un sélecteur d’heure natif du navigateur pour choisir une heure dans un champ de saisie.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masqué</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Un composant de saisie de l’heure qui impose un format ou un modèle spécifique, garantissant que l’utilisateur saisit une heure valide conformément au masque défini.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Un composant qui fournit un sélecteur de date et d’heure natif du navigateur pour choisir une date et une heure dans un seul champ de saisie.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Un composant qui fournit un sélecteur de couleur natif du navigateur et permet aux utilisateurs de choisir une couleur dans un champ de saisie.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Un composant de saisie de texte multiligne qui permet aux utilisateurs de saisir ou de modifier de longs blocs de texte.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Un composant qui représente une option binaire et permet aux utilisateurs de basculer entre l’état coché (true) et l’état décoché (false).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Un composant qui permet aux utilisateurs de sélectionner une seule option parmi plusieurs choix mutuellement exclusifs.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Un composant à bascule qui permet aux utilisateurs de passer d’un état à l’autre, par exemple activé/désactivé ou vrai/faux, au moyen d’un curseur.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Un composant qui affiche une liste déroulante d’options prédéfinies et permet aux utilisateurs d’en sélectionner une.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Un composant qui associe une liste déroulante à un champ de texte modifiable, permettant aux utilisateurs de sélectionner une option dans la liste ou de saisir une valeur personnalisée.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Un composant qui affiche une liste déroulante d’options et permet aux utilisateurs d’en sélectionner un ou plusieurs éléments.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Un sélecteur de fichiers intégré qui permet aux utilisateurs de sélectionner un ou plusieurs fichiers sur leur appareil local et de les envoyer au serveur. Il prend en charge le glisser-déposer, les filtres et le suivi des événements pour chaque fichier.</p>
  </GalleryCard>
</GalleryGrid>

## Boîtes de dialogue proposant des options {#option-dialogs}

Les boîtes de dialogue proposant des options permettent de présenter des choix aux utilisateurs ou de leur demander de confirmer une action avant de continuer. Ces composants sont essentiels pour créer des flux de travail interactifs reposant sur des décisions : ils permettent aux utilisateurs de confirmer, d’annuler ou de choisir parmi différentes options de manière claire et structurée.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Un composant de boîte de dialogue utilisé pour afficher des messages d’information ou des alertes, généralement avec un seul bouton `OK` pour accuser réception du message.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Un composant de boîte de dialogue qui demande à l’utilisateur de confirmer ou d’annuler une action, généralement à l’aide des boutons `Yes` et `No` ou `OK` et `Cancel`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Un composant de boîte de dialogue qui invite l’utilisateur à saisir du texte ou des données, généralement à l’aide d’un champ de saisie et de boutons d’action tels que `OK` et `Cancel`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Un composant de boîte de dialogue qui permet aux utilisateurs de parcourir le système de fichiers du serveur et d’y sélectionner des fichiers.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Un composant de boîte de dialogue qui permet aux utilisateurs d’envoyer des fichiers depuis leur système de fichiers local vers l’application.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Un composant de boîte de dialogue qui permet aux utilisateurs d’enregistrer un fichier à l’emplacement de leur choix dans le système de fichiers du serveur.</p>
  </GalleryCard>
</GalleryGrid>

## Interaction et affichage {#interaction-and-display}

Cette catégorie regroupe des composants qui facilitent les interactions des utilisateurs et affichent visuellement des données ou l’état de l’application. Ils aident les utilisateurs à parcourir l’application, à déclencher des actions et à comprendre la progression ou les résultats grâce à des éléments visuels dynamiques.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p> Un composant utilisé pour afficher des données sous forme de tableau structuré en lignes et en colonnes, avec des fonctionnalités comme le tri et la pagination.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Un composant qui intègre Google Charts pour afficher différents types de graphiques et de représentations visuelles de données dans l’application.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Un composant cliquable qui déclenche une action ou un événement lorsqu’on appuie dessus.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Un composant de notification léger et non bloquant qui affiche brièvement un message à l’utilisateur avant de disparaître automatiquement.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Un composant qui affiche des messages importants ou des avertissements de manière bien visible afin d’attirer l’attention de l’utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Un petit composant d’étiquette permettant d’afficher des nombres, des états ou de brèves métadonnées, avec prise en charge des thèmes, des tailles et des icônes.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Un composant qui utilise l’API de notification native du navigateur pour avertir les utilisateurs au moyen de notifications de bureau personnalisées.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Un composant de pagination personnalisable pour parcourir des ensembles de données. Il prend en charge des mises en page comportant des boutons de première page, de dernière page, de page suivante et de page précédente, ainsi que des champs permettant d’accéder rapidement à une page.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Un composant qui représente visuellement la progression d’une tâche ou d’un processus, généralement sous la forme d’une barre horizontale qui se remplit au fur et à mesure de l’avancement.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Un composant qui permet aux utilisateurs de sélectionner une valeur dans une plage définie en faisant glisser une poignée le long d’une piste.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p> Un indicateur visuel qui s’applique à toute l’application, généralement une roue animée, signalant qu’un processus global est en cours.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Un indicateur de chargement qui s’affiche dans un composant parent spécifique pour signaler que le contenu ou les données de cette section sont en cours de chargement.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Un composant qui affiche une animation rotative, généralement utilisée pour indiquer qu’un processus ou une action est en cours.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Un composant qui fournit un menu de navigation pour l’application, généralement utilisé pour répertorier des liens ou des éléments de navigation permettant de passer d’une section ou d’une vue à l’autre.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Un composant qui affiche un symbole graphique ou une image, souvent utilisé pour représenter une action, un état ou une catégorie dans l’interface utilisateur.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Un composant qui simule une interface en ligne de commande (CLI) dans l’application et permet aux utilisateurs de saisir et d’exécuter des commandes textuelles.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Un composant qui charge des éléments supplémentaires au fil du défilement, affiche un indicateur de chargement et signale lorsque tout le contenu a été récupéré.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Un composant qui permet d’actualiser le contenu en tirant vers le bas dans des conteneurs défilables, idéal pour le chargement dynamique de données.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Un composant d’affichage de données hiérarchiques qui permet aux utilisateurs de développer, de réduire et de parcourir des éléments imbriqués.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Un composant permettant d’afficher des photos de profil ou les initiales des utilisateurs, avec prise en charge de différentes tailles, formes et thèmes.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Un composant permettant d’afficher du contenu Markdown avec un rendu progressif, caractère par caractère, idéal pour les interfaces de chat avec IA et l’affichage de texte en continu.</p>
  </GalleryCard>

</GalleryGrid>


