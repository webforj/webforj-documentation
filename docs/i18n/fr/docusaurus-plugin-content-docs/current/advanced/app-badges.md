---
sidebar_position: 38
sidebar_class_name: new-content
title: App badges
description: >-
  Paint notification badges onto the operating system app icon and the browser
  tab favicon.
_i18n_hash: ff5a388432db849aa6d7b7ac1f48aa89
---
# Badges d'application <DocChip chip='since' label='26.01' />

webforJ expose deux API de badge complémentaires. <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>`.setBadge` peint l'icône de l'application sur le dock, la barre des tâches ou l'écran d'accueil du système d'exploitation. <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>`.setIconBadge` peint le favicon du document affiché dans la barre d'onglets du navigateur. Elles ciblent des surfaces différentes et ont des prérequis différents, donc la plupart des applications appellent les deux pour une visibilité maximale.

<!-- INTRO_END -->

## Badge d'icône d'application {#app-icon-badge}

`App.setBadge` rend le badge sur l'icône utilisée par le système d'exploitation pour l'application : le dock macOS, la barre des tâches Windows, l'étagère Chrome OS ou l'écran d'accueil Android.

![Badge d'icône d'application dans le dock macOS](/img/app-badges/app-badge.png)

### Prérequis {#app-prerequisites}

Le badge est visible uniquement lorsque toutes les conditions suivantes sont remplies :

- Le navigateur prend en charge le dessin de badges sur les icônes d'application.
- La page est servie depuis un contexte sécurisé (HTTPS, ou `http://localhost` pendant le développement). Les origines HTTP non sécurisées rejettent l'appel.
- L'application a été installée sur l'appareil. Le flux d'installation varie selon le navigateur : les navigateurs basés sur Chromium proposent une invite d'installation pour toute page qui embarque un manifeste, Safari sur macOS utilise **Fichier → Ajouter au Dock**, et Safari sur iOS utilise **Partager → Ajouter à l'écran d'accueil**.

Pour rendre l'application installable lorsqu'elle fonctionne sous Spring Boot ou un serveur webforJ autonome, annotez la sous-classe de <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> avec <JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" code='true'>AppProfile</JavadocLink>. Le processeur d'annotations génère le manifeste, les balises de lien d'icône d'application et les balises meta nécessaires pour que le navigateur offre l'invite d'installation.

```java
@AppProfile(name = "Inbox", shortName = "Inbox")
public class Application extends App {}
```

Consultez la page [Applications installables](../configuration/installable-apps) pour la liste complète des membres de `@AppProfile`, la taille des icônes et les conseils spécifiques à chaque plateforme sur le flux d'installation.

### Support des navigateurs {#app-browser-support}

Après l'installation, le rendu du badge dépend du navigateur. Le support d'installation lui-même est couvert sur la page [Applications installables](../configuration/installable-apps#browser-support).

| Navigateur | Badge rendu après installation |
| --- | --- |
| Chrome, Edge, Opera et autres navigateurs basés sur Chromium (desktop et Android) | Oui |
| Safari sur macOS Sonoma (Safari 17) et ultérieur | Oui |
| Safari sur iOS 16.4 et ultérieur | Oui |
| Firefox (toutes plateformes) | Non. L'appel retourne sans rendu. |

### Définir et supprimer le badge {#app-setting-clearing}

Passez un entier positif pour afficher un badge numérique. Passez `null` ou `0` pour le supprimer. Appelez la surcharge sans arguments pour afficher l'indicateur du drapeau (un petit point, le visuel exact est défini par la plateforme).

```java
App.setBadge(5);     // badge numérique
App.setBadge();      // indicateur de drapeau sans numéro
App.setBadge(0);     // supprimer
App.setBadge(null);  // supprimer
```

`App.setBadge` retourne immédiatement. Le navigateur écrit le badge sur la surface du système d'exploitation de manière asynchrone, et le changement n'est pas rapporté à l'application.

## Badge d'icône d'onglet de navigateur {#browser-tab-icon-badge}

`Page.setIconBadge` peint le compte sur le favicon du document. Il fonctionne dans n'importe quel onglet sans installation et ne nécessite aucun manifeste. Le badge est visible dans la barre d'onglets du navigateur et dans tout autre emplacement qui rend le favicon, comme les favoris ou les pages récentes.

![Favicon d'onglet de navigateur avec un badge numérique superposé](/img/app-badges/icon-badge.png)

### Définir et supprimer le badge {#tab-setting-clearing}

```java
Page page = Page.getCurrent();

page.setIconBadge(5);     // badge numérique
page.setIconBadge();      // indicateur de drapeau sans numéro
page.setIconBadge(0);     // supprimer
page.setIconBadge(null);  // supprimer
```

Supprimer le badge restaure le favicon original.

:::info Fonctionnement avec `BBjServices`
Lorsque l'application est servie par `BBjServices`, le favicon est l'**Image de raccourci** configurée pour l'application dans le Gestionnaire d'entreprise. Le badge est peint sur l'icône que le Gestionnaire d'entreprise sert. Si aucune image de raccourci n'est configurée, `Page.setIconBadge` n'a pas de favicon à superposer et ne fait rien silencieusement.
:::

### Styliser le badge {#styling-the-badge}

Passez un <JavadocLink type="foundation" location="com/webforj/IconBadgeOptions" code='true'>IconBadgeOptions</JavadocLink> pour contrôler la couleur, la forme et la taille :

```java
IconBadgeOptions options = new IconBadgeOptions()
    .setColor(new Color(0x2e, 0x7d, 0x32))
    .setShape(IconBadgeOptions.Shape.SQUARE)
    .setSize(1.25);

Page.getCurrent().setIconBadge(5, options);
```

L'objet options est un conteneur de valeur. Tous les setters retournent `this`, ce qui permet de chaîner les appels.

| Option | Type | Par défaut | Remarques |
|---|---|---|---|
| `color` | `java.awt.Color` | `#e53935` | Couleur de fond du badge. La couleur du texte est dérivée automatiquement pour le contraste, afin que les chiffres restent lisibles sur n'importe quelle couleur choisie. |
| `shape` | `Shape` | `CIRCLE` | `CIRCLE` ou `SQUARE`. |
| `size` | `double` | `1.0` | Taille relative. `0.5` est la moitié du diamètre par défaut ; `1.5` est 50 % plus grand. Le badge est limité pour s'adapter à l'intérieur de la toile du favicon. |

### Avertissement concernant le navigateur {#browser-caveat}

Safari ne rafraîchit pas le favicon après le chargement initial de la page. Les appels à `Page.setIconBadge` se terminent sans erreur, mais Safari continue d'afficher l'icône originale. Utilisez `Page.setTitle` pour également ajouter le compte au titre du document si vous avez besoin d'un indice visible dans Safari.

```java
int unread = 5;
Page page = Page.getCurrent();
page.setIconBadge(unread);
page.setTitle("(" + unread + ") Inbox");
```

## Choisir entre les deux {#choosing-between-the-two}

| Surface | API | Nécessite une installation | Visible dans Safari |
|---|---|---|---|
| Icône d'application du système d'exploitation | `App.setBadge` | Oui | Oui (macOS Sonoma / iOS 16.4 et ultérieur) |
| Favicon d'onglet de navigateur | `Page.setIconBadge` | Non | Non. L'appel se termine sans erreur, mais la barre d'onglets ne se rafraîchit pas. |

La plupart des applications appellent les deux pour que le badge soit visible que l'utilisateur soit dans une fenêtre installée ou dans un onglet de navigateur normal.
