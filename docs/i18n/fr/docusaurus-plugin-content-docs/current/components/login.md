---
title: Login
sidebar_position: 70
description: >-
  Display an authentication dialog with the Login component, handling
  submission, validation, custom fields, and form action URLs.
_i18n_hash: 5016fc4d15ba24b16c61eed8e6e272ee
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Le composant `Login` simplifie l'authentification des utilisateurs en fournissant une boîte de dialogue de connexion prête à l'emploi avec des champs de nom d'utilisateur et de mot de passe. Il inclut des fonctionnalités telles que la validation des entrées, des étiquettes et messages personnalisables, des contrôles de visibilité des mots de passe, ainsi que le support pour des champs personnalisés supplémentaires.

<!-- INTRO_END -->

## Création d'une boîte de dialogue `Login` {#creating-a-login-dialog}

Créez une boîte de dialogue `Login` en instanciant le composant et en appelant `open()` pour l'afficher. La boîte de dialogue comprend par défaut des champs de nom d'utilisateur et de mot de passe, une validation des entrées et un bouton de connexion.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Soumission du login {#login-submission}

Lorsque les utilisateurs saisissent leur nom d'utilisateur et leur mot de passe, le composant `Login` valide ces entrées en tant que champs obligatoires. Une fois la validation effectuée avec succès, un événement de soumission de formulaire est déclenché, délivrant les identifiants saisis. Pour éviter les soumissions multiples, le bouton [Se connecter] est immédiatement désactivé.

Ce qui suit illustre un composant `Login` basique. Si le nom d'utilisateur et le mot de passe sont tous deux définis sur `"admin"`, la boîte de dialogue de connexion se ferme et un bouton [Déconnexion] apparaît. Si les identifiants ne correspondent pas, le message d'erreur par défaut s'affiche.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info Désactivation du bouton [Se connecter]
Par défaut, `Login` désactive immédiatement le bouton [Se connecter] une fois que le composant valide les entrées de connexion comme correctes, afin de prévenir les soumissions multiples. Vous pouvez réactiver le bouton [Se connecter] en utilisant la méthode `setEnabled(true)`.
:::

:::tip Autoriser les mots de passe vides
Vous pouvez permettre aux utilisateurs de se connecter avec uniquement un nom d'utilisateur en utilisant la méthode `setEmptyPassword(true)`.
:::

## Action du formulaire <DocChip chip='since' label='25.10' />{#form-action}

Le composant `Login` peut soumettre des données de formulaire directement à une URL spécifiée au lieu de traiter la soumission via l'événement de soumission. Lorsque une URL d'action est définie, le formulaire effectue une requête POST standard avec le nom d'utilisateur et le mot de passe comme paramètres de formulaire.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Lorsque vous utilisez `setAction()`, la soumission du formulaire contourne l'événement `LoginSubmitEvent` et effectue plutôt une requête HTTP POST traditionnelle vers le point de terminaison spécifié. Le nom d'utilisateur et le mot de passe sont envoyés en tant que paramètres de formulaire nommés `"username"` et `"password"`, respectivement. Des champs personnalisés avec un attribut name sont également inclus dans la requête POST.

:::tip
Si aucune URL d'action n'est définie, la soumission du formulaire est gérée via l'événement `LoginSubmitEvent`, vous permettant de traiter les identifiants de manière programmée côté serveur.
:::

## Internationalisation (i18n) {#internationalization-i18n}

Les titres, descriptions, étiquettes et messages au sein du composant `Login` sont entièrement personnalisables à l'aide de la classe `LoginI18n`. Cette flexibilité vous permet d'adapter l'interface de connexion pour répondre à des exigences de localisation spécifiques ou à des préférences de personnalisation.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Champs personnalisés {#custom-fields}

Le composant `Login` inclut plusieurs emplacements qui vous permettent d'ajouter des champs supplémentaires selon vos besoins. Les champs personnalisés sont automatiquement collectés lors de la soumission du formulaire et peuvent être accédés via la carte de données de l'événement de soumission.

Le login suivant comporte un champ personnalisé ajouté pour un identifiant client. Cela peut vous aider à gérer des entreprises ou des départements avec un contenu partagé entre plusieurs utilisateurs.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/frontend/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Nom requis
Les champs personnalisés doivent avoir un nom défini à l'aide de `setName()` pour être inclus dans la soumission du formulaire. Le nom est utilisé comme clé pour récupérer la valeur du champ via `event.getData()`.
:::

## Bouton Annuler {#cancel-button}

`Login` inclut un bouton [Annuler] qui est caché par défaut. Cela est particulièrement utile lorsqu'un utilisateur tente d'accéder à une zone restreinte de l'application et a besoin d'une option pour revenir à son emplacement précédent sans compléter la connexion.

Pour rendre le bouton annuler visible, fournissez-lui une étiquette. Vous pouvez également écouter les événements d'annulation pour gérer l'annulation de manière appropriée.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Masquage des éléments
Pour cacher un élément, définissez son étiquette sur une chaîne vide. Cela vous permet de basculer la visibilité sans enlever le composant de votre code.
:::

## Gestionnaires de mots de passe {#password-managers}

Ce composant fonctionne avec des gestionnaires de mots de passe basés sur le navigateur pour simplifier le processus de connexion. Dans les navigateurs basés sur Chromium, il s'intègre à l'API [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential), qui fournit :

- **Remplissage automatique** : Le navigateur peut automatiquement remplir les champs de nom d'utilisateur et de mot de passe si l'utilisateur a enregistré des identifiants pour le site.
- **Gestion des identifiants** : Après s'être connecté, le navigateur peut inviter l'utilisateur à enregistrer de nouveaux identifiants, rendant les connexions futures plus rapides et plus faciles.
- **Sélection des identifiants** : Si plusieurs identifiants sont enregistrés, le navigateur peut offrir à l'utilisateur le choix de sélectionner l'un des ensembles enregistrés.

## Style {#styling}

<TableBuilder name="Login" />
