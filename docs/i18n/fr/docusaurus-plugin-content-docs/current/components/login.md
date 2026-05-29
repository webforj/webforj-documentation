---
title: Login
sidebar_position: 70
_i18n_hash: 929bacbc38791adc906102078bdd6bfa
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Le composant `Login` simplifie l'authentification des utilisateurs en fournissant une boîte de dialogue de connexion prête à l'emploi avec des champs pour le nom d'utilisateur et le mot de passe. Il inclut des fonctionnalités telles que la validation des entrées, des étiquettes et des messages personnalisables, des contrôles de visibilité du mot de passe et la prise en charge de champs personnalisés supplémentaires.

<!-- INTRO_END -->

## Création d'une boîte de dialogue `Login` {#creating-a-login-dialog}

Créez une boîte de dialogue `Login` en instanciant le composant et en appelant `open()` pour l'afficher. La boîte de dialogue inclut par défaut des champs pour le nom d'utilisateur et le mot de passe, une validation des entrées et un bouton de connexion.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Soumission de login {#login-submission}

Lorsque les utilisateurs saisissent leur nom d'utilisateur et leur mot de passe, le composant `Login` valide ces entrées en tant que champs requis. Une fois la validation réussie, un événement de soumission de formulaire est déclenché, livrant les informations d'identification saisies. Pour éviter les soumissions multiples, le bouton [Sign in] est immédiatement désactivé.

Ce qui suit illustre un composant `Login` de base. Si le nom d'utilisateur et le mot de passe sont tous deux définis sur `"admin"`, la boîte de dialogue de connexion se ferme et un bouton [Logout] apparaît. Si les informations d'identification ne correspondent pas, le message d'erreur par défaut est affiché.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info Désactivation du bouton [Sign in]
Par défaut, `Login` désactive immédiatement le bouton [Sign in] une fois que le composant valide les entrées de connexion comme correctes, afin de prévenir les soumissions multiples. Vous pouvez réactiver le bouton [Sign in] en utilisant la méthode `setEnabled(true)`.
:::

:::tip Autoriser les mots de passe vides
Vous pouvez permettre aux utilisateurs de se connecter avec uniquement un nom d'utilisateur en utilisant la méthode `setEmptyPassword(true)`.
:::

## Action de formulaire <DocChip chip='since' label='25.10' />{#form-action}

Le composant `Login` peut soumettre des données de formulaire directement à une URL spécifiée au lieu de gérer la soumission via l'événement de soumission. Lorsqu'une URL d'action est définie, le formulaire effectue une requête POST standard avec le nom d'utilisateur et le mot de passe comme paramètres du formulaire.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Lors de l'utilisation de `setAction()`, la soumission du formulaire contourne le `LoginSubmitEvent` et effectue plutôt une requête HTTP POST traditionnelle à l'endpoint spécifié. Le nom d'utilisateur et le mot de passe sont envoyés en tant que paramètres de formulaire nommés `"username"` et `"password"`, respectivement. Les champs personnalisés avec un attribut name sont également inclus dans la requête POST.

:::tip 
Si aucune URL d'action n'est définie, la soumission du formulaire est gérée via le `LoginSubmitEvent`, ce qui vous permet de traiter les informations d'identification de manière programmatique côté serveur.
:::

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages au sein du composant `Login` sont entièrement personnalisables en utilisant la classe `LoginI18n`. Cette flexibilité vous permet d'adapter l'interface de connexion pour répondre à des exigences spécifiques de localisation ou de préférences de personnalisation.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Champs personnalisés {#custom-fields}

Le composant `Login` inclut plusieurs emplacements qui vous permettent d'ajouter des champs supplémentaires si nécessaire. Les champs personnalisés sont automatiquement collectés lors de la soumission du formulaire et peuvent être accédés via la carte de données de l'événement de soumission.

Le login suivant a un champ personnalisé ajouté pour un ID de client. Cela peut vous aider à gérer des entreprises ou des départements avec un contenu partagé entre plusieurs utilisateurs.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/resources/static/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Nom requis
Les champs personnalisés doivent avoir un nom défini à l'aide de `setName()` pour être inclus dans la soumission du formulaire. Le nom est utilisé comme clé pour récupérer la valeur du champ depuis `event.getData()`.
:::

## Bouton Annuler {#cancel-button}

`Login` comprend un bouton [Cancel] qui est caché par défaut. Cela est particulièrement utile lorsqu'un utilisateur tente d'accéder à une zone restreinte de l'application et a besoin d'une option pour revenir à son emplacement précédent sans compléter le login.

Pour rendre le bouton annuler visible, fournissez-lui une étiquette. Vous pouvez également écouter les événements d'annulation pour gérer l'annulation de manière appropriée.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Cacher des éléments
Pour cacher un élément, définissez son label sur une chaîne vide. Cela vous permet de basculer la visibilité sans retirer le composant de votre code.
:::

## Gestionnaires de mots de passe {#password-managers}

Ce composant fonctionne avec des gestionnaires de mots de passe basés sur les navigateurs pour simplifier le processus de connexion. Sur les navigateurs basés sur Chromium, il s'intègre avec l'API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential), qui fournit :

- **Remplissage automatique** : Le navigateur peut automatiquement remplir les champs pour le nom d'utilisateur et le mot de passe si l'utilisateur a enregistré des informations d'identification pour le site.
- **Gestion des informations d'identification** : Après la connexion, le navigateur peut inviter l'utilisateur à enregistrer de nouvelles informations d'identification, ce qui rend les connexions futures plus rapides et plus faciles.
- **Sélection d'informations d'identification** : Si plusieurs informations d'identification sont enregistrées, le navigateur peut offrir un choix à l'utilisateur pour sélectionner un des ensembles enregistrés.

## Style {#styling}

<TableBuilder name="Login" />
