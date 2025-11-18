---
title: Login
sidebar_position: 70
sidebar_class_name: updated-content
_i18n_hash: cdcad4b5ef5d3ba0bd84e4d9deac49b5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Le composant `Login` simplifie l'authentification des utilisateurs en fournissant une boîte de dialogue de connexion prête à l'emploi avec des champs de nom d'utilisateur et de mot de passe. Il comprend des fonctionnalités telles que la validation des saisies, des étiquettes et des messages personnalisables, des contrôles de visibilité des mots de passe et le support de champs personnalisés supplémentaires.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Soumission de connexion {#login-submission}

Lorsque les utilisateurs saisissent leur nom d'utilisateur et leur mot de passe, le composant `Login` valide ces saisies comme des champs requis. Une fois la validation réussie, un événement de soumission de formulaire est déclenché, livrant les identifiants saisis. Pour éviter des soumissions multiples, le bouton [Se connecter] est immédiatement désactivé.

Ce qui suit illustre un composant `Login` de base. Si le nom d'utilisateur et le mot de passe sont tous deux définis sur `"admin"` respectivement, la boîte de dialogue de connexion se ferme et un bouton [Déconnexion] apparaît. Si les identifiants ne correspondent pas, le message d'erreur par défaut est affiché.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Désactivation du bouton [Se connecter]
Par défaut, `Login` désactive immédiatement le bouton [Se connecter] une fois que le composant valide les saisies de connexion comme correctes, afin de prévenir les soumissions multiples. Vous pouvez réactiver le bouton [Se connecter] en utilisant la méthode `setEnabled(true)`.
:::

:::tip Autoriser les mots de passe vides
Vous pouvez permettre aux utilisateurs de se connecter avec seulement un nom d'utilisateur en utilisant la méthode `setEmptyPassword(true)`.
:::

## Action de formulaire <DocChip chip='since' label='25.10' />{#form-action}

Le composant `Login` peut soumettre les données de formulaire directement à une URL spécifiée au lieu de traiter la soumission via l'événement de soumission. Lorsqu'une URL d'action est définie, le formulaire effectue une requête POST standard avec le nom d'utilisateur et le mot de passe comme paramètres de formulaire.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Lors de l'utilisation de `setAction()`, la soumission de formulaire contourne `LoginSubmitEvent` et effectue plutôt une requête HTTP POST traditionnelle vers le point de terminaison spécifié. Le nom d'utilisateur et le mot de passe sont envoyés en tant que paramètres de formulaire nommés "username" et "password", respectivement. Les champs personnalisés avec un attribut name sont également inclus dans la requête POST.

:::tip 
Si aucune URL d'action n'est définie, la soumission du formulaire est gérée par `LoginSubmitEvent`, vous permettant de traiter les identifiants programmatique sur le côté serveur.
:::

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages au sein du composant `Login` sont entièrement personnalisables à l'aide de la classe `LoginI18n`. Cette flexibilité vous permet d'adapter l'interface de connexion pour répondre à des exigences spécifiques de localisation ou de préférences de personnalisation.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Champs personnalisés {#custom-fields}

Le composant `Login` comprend plusieurs slots qui vous permettent d'ajouter des champs supplémentaires selon vos besoins. Les champs personnalisés sont automatiquement collectés lors de la soumission du formulaire et peuvent être accédés via la carte de données de l'événement de soumission.

La connexion suivante a un champ personnalisé ajouté pour un identifiant client. Cela peut vous aider à gérer des entreprises ou des départements avec un contenu partagé entre plusieurs utilisateurs.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Nom requis
Les champs personnalisés doivent avoir un nom défini en utilisant `setName()` pour être inclus dans la soumission du formulaire. Le nom est utilisé comme clé pour récupérer la valeur du champ à partir de `event.getData()`.
:::

## Bouton Annuler {#cancel-button}

`Login` inclut un bouton [Annuler] qui est caché par défaut. Ceci est particulièrement utile lorsqu'un utilisateur tente d'accéder à une zone restreinte de l'application et a besoin d'une option pour revenir à son emplacement précédent sans compléter la connexion.

Pour rendre le bouton d'annulation visible, fournissez-lui une étiquette. Vous pouvez également écouter les événements d'annulation pour gérer l'annulation de manière appropriée.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Cacher les éléments
Pour cacher un élément, définissez son étiquette sur une chaîne vide. Cela vous permet de basculer la visibilité sans retirer le composant de votre code.
:::

## Gestionnaires de mots de passe {#password-managers}

Ce composant fonctionne avec les gestionnaires de mots de passe basés sur les navigateurs pour simplifier le processus de connexion. Sur les navigateurs basés sur Chromium, il s'intègre à l'API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential), qui fournit :

- **Remplissage automatique** : Le navigateur peut automatiquement remplir les champs de nom d'utilisateur et de mot de passe si l'utilisateur a enregistré des identifiants pour le site.
- **Gestion des identifiants** : Après s'être connecté, le navigateur peut demander à l'utilisateur d'enregistrer de nouveaux identifiants, rendant les futures connexions plus rapides et plus faciles.
- **Sélection des identifiants** : Si plusieurs identifiants sont enregistrés, le navigateur peut offrir un choix à l'utilisateur parmi l'un des ensembles enregistrés.

## Style {#styling}

<TableBuilder name="Login" />
