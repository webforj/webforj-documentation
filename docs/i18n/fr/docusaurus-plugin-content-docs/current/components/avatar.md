---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 3a915fc4eb3ca5d51dc1909a34eb5bd1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Le composant `Avatar` fournit une représentation visuelle d'un utilisateur ou d'une entité. Il peut afficher une image, des initiales auto-calculées, des initiales personnalisées ou une icône. Les avatars sont couramment utilisés pour identifier les utilisateurs dans les sections de commentaires, les menus de navigation, les applications de chat et les listes de contacts.

## Création d'avatars {#creating-avatars}

Pour créer un `Avatar`, passez une étiquette qui sert de nom accessible. Le composant calcule automatiquement les initiales en extrayant la première lettre de chaque mot dans l'étiquette.

```java
// Crée un avatar affichant "JD" à partir de l'étiquette
Avatar avatar = new Avatar("John Doe");
```

Vous pouvez également fournir des initiales explicites si vous préférez plus de contrôle sur ce qui est affiché :

```java
// Crée un avatar avec des initiales personnalisées
Avatar avatar = new Avatar("John Doe", "J");
```

L'exemple ci-dessous montre des avatars dans le contexte d'un panneau d'équipe. Chaque `Avatar` affiche soit une image de profil, soit des initiales générées automatiquement en fonction du nom de l'utilisateur. En cliquant sur un `Avatar`, une boîte de dialogue s'ouvre avec une vue agrandie.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Affichage d'images {#displaying-images}

Le composant `Avatar` peut afficher une image au lieu d'initiales en insérant un composant `Img` comme enfant. Lorsqu'une image est fournie, elle prend le pas sur les initiales.

```java
import com.webforj.component.html.elements.Img;

// Avatar avec une image
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Redimensionnement de l'image
L'image s'ajuste automatiquement pour s'adapter aux dimensions de l'avatar en fonction du paramètre d'expansion actuel.
:::

## Affichage d'icônes {#displaying-icons}

Vous pouvez afficher une icône à l'intérieur de l'`Avatar` en ajoutant un composant `Icon` comme enfant :

```java
import com.webforj.component.icons.TablerIcon;

// Avatar avec une icône
Avatar avatar = new Avatar("Utilisateur Invité", TablerIcon.create("user"));
```

## Étiquette et initiales {#label-and-initials}

Le composant `Avatar` utilise l'étiquette pour l'accessibilité et la génération d'info-bulles. Les méthodes `setLabel()` et `setText()` sont des alias qui définissent tous deux l'étiquette accessible pour l'`Avatar`.

:::info Initiales auto-calculées
Lorsque vous créez un `Avatar` uniquement avec une étiquette, les initiales sont automatiquement calculées en prenant le premier caractère de chaque mot. Par exemple, "John Doe" devient "JD".
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Définit l'étiquette et génère automatiquement l'info-bulle
avatar.setInitials("JS");       // Remplace les initiales auto-calculées
```

:::tip Info-bulle automatique
Le composant génère automatiquement une info-bulle à partir de l'étiquette, ce qui permet de voir facilement le nom complet au survol. Ce comportement est désactivé lors de l'utilisation de l'étiquette par défaut `"Avatar"`.
:::

## Événements de clic {#click-events}

Le composant `Avatar` implémente `HasElementClickListener`, vous permettant de répondre aux clics des utilisateurs. Cela est utile pour déclencher des actions telles que l'ouverture d'un profil utilisateur ou l'affichage d'un menu.

```java
avatar.onClick(event -> {
  // Gérer le clic sur l'avatar - par exemple, ouvrir le profil utilisateur
  System.out.println("Avatar cliqué !");
});
```

## Formes {#shapes}

Les avatars peuvent être affichés sous forme de cercles ou de carrés. La forme par défaut est `CIRCLE`, qui est standard pour les avatars d'utilisateur. Utilisez `SQUARE` pour des entités comme des équipes, des entreprises ou des applications.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Thèmes {#themes}

Les thèmes transmettent une signification ou un statut ; vous pouvez les utiliser pour indiquer la disponibilité, mettre en évidence des utilisateurs importants ou correspondre au design de votre application.

Les thèmes suivants sont disponibles :

- `DEFAULT` : Apparence standard
- `GRAY` : Apparence neutre et atténuée
- `PRIMARY` : Met l'accent sur les actions ou utilisateurs principaux
- `SUCCESS` : Indique un statut positif (par exemple, en ligne)
- `WARNING` : Indique une mise en garde (par exemple, absent)
- `DANGER` : Indique une erreur ou un statut occupé
- `INFO` : Fournit un contexte informatif

Chaque thème a également une variante contour pour un traitement visuel plus léger :

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Expanses {#expanses}

Contrôlez la taille de l'avatar à l'aide de la méthode `setExpanse()`. Le composant prend en charge neuf options de taille allant de `XXXSMALL` à `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## Style {#styling}

<TableBuilder name="Avatar" />
