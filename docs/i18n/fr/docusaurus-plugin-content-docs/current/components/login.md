---
title: Login
sidebar_position: 70
_i18n_hash: f2f1f96cfde1dbbede5bfdaafd3f0a92
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Le composant Login est conçu pour fournir une interface conviviale pour l'authentification, permettant aux utilisateurs de se connecter en utilisant un nom d'utilisateur et un mot de passe. Il prend en charge diverses personnalisations pour améliorer l'expérience utilisateur sur différents appareils et dans différents locales.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usages {#usages}

Le composant Login fournit une interface de formulaire de connexion conviviale au sein d'une boîte de dialogue pour entrer les informations d'identification d'authentification. Il améliore l'expérience utilisateur en offrant :
   >- Des champs de saisie clairs pour le nom d'utilisateur et le mot de passe.
   >- Un basculement de visibilité pour le mot de passe afin de vérifier la saisie.
   >- Un retour d'information de validation de saisie pour inviter au bon format avant soumission.

## Soumission de connexion {#login-submission}

Lorsque les utilisateurs saisissent leur nom d'utilisateur et leur mot de passe, le composant de connexion valide ces entrées comme des champs requis. Une fois la validation réussie, un événement de soumission de formulaire est déclenché, livrant les informations d'identification saisies. Pour éviter les soumissions multiples, le bouton `Signin` est immédiatement désactivé.

La démo ci-dessous illustre un processus de soumission de formulaire basique. Si le nom d'utilisateur et le mot de passe sont tous deux définis sur `"admin"` respectivement, la boîte de dialogue de connexion se ferme et un bouton de déconnexion apparaît. Si les informations d'identification ne correspondent pas, le message d'erreur par défaut du formulaire de connexion est affiché.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Désactivation du bouton Signin
Par défaut, le formulaire de connexion désactive immédiatement le bouton `Signin` une fois que le composant valide les entrées de connexion comme correctes, pour éviter les soumissions multiples. Vous pouvez réactiver le bouton `Signin` en utilisant la méthode `setEnabled(true)`.
:::

:::tip Autorisation des mots de passe vides
Dans certaines situations, des mots de passe vides peuvent être acceptables, permettant aux utilisateurs de se connecter uniquement avec un nom d'utilisateur. La boîte de dialogue de connexion peut être configurée pour accepter des mots de passe vides en définissant `setEmptyPassword(true)`.
:::

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages au sein du composant de connexion sont entièrement personnalisables à l'aide de la classe `LoginI18n`. Cette flexibilité vous permet de personnaliser l'interface de connexion pour répondre aux exigences spécifiques de localisation ou aux préférences de personnalisation.

La démo ci-dessous illustre comment fournir une traduction allemande pour la boîte de dialogue de connexion, en veillant à ce que tous les éléments de l'interface soient adaptés à la langue allemande pour améliorer l'expérience utilisateur pour les utilisateurs germanophones.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Champs personnalisés {#custom-fields}

Le composant de connexion inclut plusieurs emplacements, ce qui vous permet d'ajouter des champs supplémentaires si nécessaire. Cette fonctionnalité offre un meilleur contrôle sur les informations requises pour une authentification réussie.

Dans l'exemple ci-dessous, un champ d'identification client est ajouté au formulaire de connexion. Les utilisateurs doivent fournir un ID valide pour compléter l'authentification, renforçant la sécurité et garantissant que l'accès n'est accordé qu'après vérification de toutes les informations d'identification requises.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Payload de soumission
Notez que le composant de connexion ne reconnaît pas automatiquement ou n'inclut pas les champs supplémentaires ajoutés au formulaire dans son payload de soumission. Cela signifie que les développeurs doivent explicitement récupérer la valeur des champs supplémentaires côté client et la gérer conformément aux exigences de l'application pour compléter le processus d'authentification.
:::

## Bouton Annuler {#cancel-button}

Dans certaines situations, il peut être souhaitable d'ajouter un bouton Annuler aux côtés du bouton `Signin`. Cette fonctionnalité est particulièrement utile lorsque l'utilisateur tente d'accéder à une zone restreinte de l'application et a besoin d'une option pour annuler l'action et revenir à son emplacement précédent. Le formulaire de connexion comprend un bouton Annuler par défaut, mais il est masqué.

Pour rendre le bouton Annuler visible, vous devez fournir une étiquette pour celui-ci - une fois étiqueté, il apparaîtra à l'écran. Vous pouvez également écouter les événements d'annulation pour répondre de manière appropriée aux actions des utilisateurs, garantissant une expérience fluide et conviviale pour naviguer dans l'application.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Masquer des éléments
Pour masquer un élément de l'écran de connexion, il suffit de définir son étiquette sur une chaîne vide. Cette approche est particulièrement utile pour retirer temporairement des composants de l'interface sans modifier de façon permanente le code.
:::

## Gestionnaires de mots de passe {#password-managers}

Le composant de connexion est conçu pour être compatible avec les gestionnaires de mots de passe basés sur les navigateurs, améliorant ainsi l'expérience utilisateur en simplifiant le processus de connexion. Pour les utilisateurs de navigateurs basés sur Chromium, le composant s'intègre parfaitement à l'API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential). Cette intégration permet plusieurs fonctionnalités pratiques :

- **Auto-remplissage** : Le navigateur peut automatiquement remplir les champs de nom d'utilisateur et de mot de passe si l'utilisateur a enregistré des informations d'identification pour le site.
- **Gestion des informations d'identification** : Après s'être connecté, le navigateur peut inviter l'utilisateur à enregistrer de nouvelles informations d'identification, rendant les connexions futures plus rapides et plus faciles.
- **Sélection des informations d'identification** : Si plusieurs informations d'identification sont enregistrées, le navigateur peut offrir un choix à l'utilisateur pour sélectionner l'un des ensembles enregistrés.

## Style {#styling}

<TableBuilder name="Login" />
