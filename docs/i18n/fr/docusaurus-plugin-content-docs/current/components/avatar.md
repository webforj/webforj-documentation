---
title: Avatar
sidebar_position: 7
description: >-
  Represent users with the Avatar component, showing profile images,
  auto-computed initials, custom initials, or icons for identification.
_i18n_hash: a19b6cefc7a422d075f42ddedfcddfce
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Le composant `Avatar` fournit une représentation visuelle d'un utilisateur ou d'une entité. Il peut afficher une image, des initiales auto-computées, des initiales personnalisées ou une icône. Les avatars sont couramment utilisés pour identifier les utilisateurs dans les sections de commentaires, les menus de navigation, les applications de chat et les listes de contacts.

<!-- INTRO_END -->

## Création d'avatars {#creating-avatars}

Pour créer un `Avatar`, passez un label servant de nom accessible. Le composant calcule automatiquement les initiales en extrayant la première lettre de chaque mot dans le label.

```java
// Crée un avatar affichant "JD" à partir du label
Avatar avatar = new Avatar("John Doe");
```

Vous pouvez également fournir des initiales explicites si vous préférez avoir plus de contrôle sur ce qui est affiché :

```java
// Crée un avatar avec des initiales personnalisées
Avatar avatar = new Avatar("John Doe", "J");
```

L'exemple ci-dessous présente des avatars dans le contexte d'un panneau d'équipe. Chaque `Avatar` affiche soit une image de profil, soit des initiales générées automatiquement basées sur le nom de l'utilisateur. Cliquer sur un `Avatar` ouvre une boîte de dialogue avec une vue agrandie.

<ComponentDemo
path='/webforj/avatar'
files={[
  'src/main/java/com/webforj/samples/views/avatar/AvatarView.java',
  'src/main/frontend/css/avatar/avatar.css',
]}
height='500px'
/>

## Affichage des images {#displaying-images}

Le composant `Avatar` peut afficher une image au lieu d'initiales en insérant un composant `Img` comme enfant. Lorsqu'une image est fournie, elle prend le pas sur les initiales.

```java
import com.webforj.component.html.elements.Img;

// Avatar avec une image
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Dimensionnement des images
L'image se met automatiquement à l'échelle pour s'adapter aux dimensions de l'avatar en fonction du paramètre d'expansion actuel.
:::

## Affichage des icônes {#displaying-icons}

Vous pouvez afficher une icône à l'intérieur de l'`Avatar` en ajoutant un composant `Icon` comme enfant :

```java
import com.webforj.component.icons.TablerIcon;

// Avatar avec une icône
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## Label et initiales {#label-and-initials}

Le composant `Avatar` utilise le label pour l'accessibilité et la génération d'info-bulles. Les méthodes `setLabel()` et `setText()` sont des alias qui définissent tous deux le label accessible pour l'`Avatar`.

:::info Initiales auto-computées
Lorsque vous créez un `Avatar` avec juste un label, les initiales sont automatiquement calculées en prenant le premier caractère de chaque mot. Par exemple, un `Avatar` avec le label "John Doe" affiche automatiquement "JD" dans l'UI.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Définit le label et génère automatiquement l'info-bulle
avatar.setInitials("JS");       // Remplace les initiales auto-computées
```

:::tip Info-bulle automatique
Le composant génère automatiquement une info-bulle à partir du label, ce qui facilite la visualisation du nom complet au survol. Ce comportement est désactivé lorsque l'on utilise le label par défaut `"Avatar"`.
:::

## Événements de clic {#click-events}

Le composant `Avatar` implémente `HasElementClickListener`, vous permettant de répondre aux clics des utilisateurs. Ceci est utile pour déclencher des actions comme l'ouverture d'un profil utilisateur ou l'affichage d'un menu.

```java
avatar.onClick(event -> {
  // Gérer le clic sur l'avatar - par exemple, ouvrir le profil utilisateur
  System.out.println("Avatar cliqué !");
});
```

## Formes {#shapes}

Les avatars peuvent être affichés sous forme de cercles ou de carrés. La forme par défaut est `CIRCLE`, qui est standard pour les avatars d'utilisateur. Utilisez `SQUARE` pour des entités comme des équipes, des entreprises ou des applications.

<ComponentDemo
path='/webforj/avatarshapes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java']}
height='100px'
/>

## Thèmes {#themes}

Les thèmes transmettent une signification ou un statut ; vous pouvez les utiliser pour indiquer la disponibilité, mettre en évidence des utilisateurs importants ou assortir le design de votre application.

Les thèmes suivants sont disponibles :

- `DEFAULT` : Apparence standard
- `GRAY` : Apparence neutre et atténuée
- `PRIMARY` : Met l'accent sur les actions ou utilisateurs principaux
- `SUCCESS` : Indique un statut positif (par exemple, en ligne)
- `WARNING` : Indique une mise en garde (par exemple, absent)
- `DANGER` : Indique une erreur ou un statut occupé
- `INFO` : Fournit un contexte informatif

Chaque thème a également une variante outlined pour un traitement visuel plus léger :

<ComponentDemo
path='/webforj/avatarthemes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java']}
height='120px'
/>

## Expanses {#expanses}

Contrôlez la taille de l'avatar en utilisant la méthode `setExpanse()`. Le composant prend en charge neuf options de taille allant de `XXXSMALL` à `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java']}
height='100px'
/>

## Style {#styling}

<TableBuilder name="Avatar" />
