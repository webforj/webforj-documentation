---
title: Login
sidebar_position: 70
_i18n_hash: b95b5a072de318071d9d7ecae890a883
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Le composant Login est conçu pour fournir une interface conviviale pour l'authentification, permettant aux utilisateurs de se connecter à l'aide d'un nom d'utilisateur et d'un mot de passe. Il supporte diverses personnalisations pour améliorer l'expérience utilisateur sur différents appareils et locales.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Usages {#usages}

Le composant Login fournit une interface de formulaire de connexion conviviale dans une boîte de dialogue pour entrer les informations d'authentification. Il améliore l'expérience utilisateur en offrant :
   >- Des champs d'entrée clairs pour le nom d'utilisateur et le mot de passe.
   >- Un bouton de visibilité pour le mot de passe afin de vérifier l'entrée.
   >- Un retour d'information sur la validation des entrées pour indiquer le format correct avant la soumission.

## Soumission de connexion {#login-submission}

Lorsque les utilisateurs saisissent leur nom d'utilisateur et leur mot de passe, le composant de connexion valide ces entrées en tant que champs requis. Une fois la validation réussie, un événement de soumission de formulaire est déclenché, livrant les informations d'identification saisies. Pour éviter les soumissions multiples, le bouton `Signin` est immédiatement désactivé.

La démonstration ci-dessous illustre un processus de soumission de formulaire de base. Si le nom d'utilisateur et le mot de passe sont tous deux définis sur "admin", la boîte de dialogue de connexion se ferme et un bouton de déconnexion apparaît. Si les informations d'identification ne correspondent pas, le message d'erreur par défaut du formulaire de connexion est affiché.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Désactivation du bouton Signin
Par défaut, le formulaire de connexion désactive immédiatement le bouton `Signin` une fois que le composant valide les entrées de connexion comme correctes, afin d'éviter les soumissions multiples. Vous pouvez réactiver le bouton `Signin` en utilisant la méthode `setEnabled(true)`.
:::

:::tip Autoriser les mots de passe vides
Dans certains scénarios, les mots de passe vides peuvent être permis, permettant aux utilisateurs de se connecter uniquement avec un nom d'utilisateur. La boîte de dialogue de connexion peut être configurée pour accepter des mots de passe vides en définissant `setEmptyPassword(true)`.
:::

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages à l'intérieur du composant de connexion sont entièrement personnalisables à l'aide de la classe `LoginI18n`. Cette flexibilité vous permet d'adapter l'interface de connexion pour répondre à des exigences de localisation spécifiques ou à des préférences de personnalisation.

La démonstration ci-dessous illustre comment fournir une traduction allemande pour la boîte de dialogue de connexion, garantissant que tous les éléments de l'interface sont adaptés à la langue allemande pour améliorer l'expérience utilisateur pour les utilisateurs germanophones.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Champs personnalisés {#custom-fields}

Le composant de connexion inclut plusieurs emplacements, qui permettent d'ajouter des champs supplémentaires si nécessaire. Cette fonction donne plus de contrôle sur les informations requises pour une authentification réussie.

Dans l'exemple ci-dessous, un champ ID client est ajouté au formulaire de connexion. Les utilisateurs doivent fournir un ID valide pour compléter l'authentification, renforçant la sécurité et garantissant que l'accès est accordé uniquement après vérification de toutes les informations d'identification requises.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Charge utile de soumission
Notez que le composant de connexion ne reconnaît pas automatiquement ou n'inclut pas les champs supplémentaires ajoutés au formulaire dans sa charge utile de soumission. Cela signifie que les développeurs doivent explicitement récupérer la valeur de tout champ supplémentaire côté client et la gérer conformément aux exigences de l'application pour compléter le processus d'authentification.
:::

## Bouton d'annulation {#cancel-button}

Dans certains scénarios, il peut être souhaitable d'ajouter un bouton d'annulation à côté du bouton `Signin`. Cette fonctionnalité est particulièrement utile lorsqu'un utilisateur essaie d'accéder à une zone restreinte de l'application et a besoin d'une option pour annuler l'action et revenir à son emplacement précédent. Le formulaire de connexion inclut un bouton d'annulation par défaut, mais il est masqué à la vue.

Pour rendre le bouton d'annulation visible, vous devez fournir une étiquette pour celui-ci - une fois étiqueté, il apparaîtra à l'écran. Vous pouvez également écouter les événements d'annulation pour répondre de manière appropriée aux actions des utilisateurs, garantissant une expérience fluide et conviviale pour naviguer dans l'application.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Masquer des éléments
Pour masquer un élément de l'écran de connexion, il suffit de définir son étiquette sur une chaîne vide. Cette approche est particulièrement utile pour retirer temporairement des composants d'interface sans modifier définitivement le code.
:::

## Gestionnaires de mots de passe {#password-managers}

Le composant de connexion est conçu pour être compatible avec les gestionnaires de mots de passe basés sur le navigateur, améliorant l'expérience utilisateur en simplifiant le processus de connexion. Pour les utilisateurs de navigateurs basés sur Chromium, le composant s'intègre parfaitement avec l'API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential). Cette intégration permet plusieurs fonctionnalités pratiques :

- **Auto-remplissage** : Le navigateur peut automatiquement remplir les champs de nom d'utilisateur et de mot de passe si l'utilisateur a enregistré des identifiants pour le site.
- **Gestion des identifiants** : Après s'être connecté, le navigateur peut inviter l'utilisateur à enregistrer de nouveaux identifiants, rendant les connexions futures plus rapides et plus faciles.
- **Sélection d'identifiants** : Si plusieurs identifiants sont enregistrés, le navigateur peut offrir un choix à l'utilisateur pour sélectionner l'un des ensembles enregistrés.

## Style {#styling}

<TableBuilder name="Login" />
