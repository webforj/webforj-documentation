---
title: Applications installables
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 9aa99cc9da864a6808d72602b6e5c572
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

L’annotation `@AppProfile` de webforJ vous permet de rendre votre application installable sur les plateformes prises en charge.
Les applications web installables s’intègrent au système d’exploitation de l’appareil.
Une fois installées, elles apparaissent sur l’écran d’accueil ou dans le menu des applications, comme les applications natives.
Pour cela, certaines métadonnées, telles que le nom, la description et les icônes, doivent être fournies.
Ces informations aident le système d’exploitation à identifier et à afficher l’application.

:::info Exigence d’origine sécurisée
Pour qu’une application soit installable, elle doit être diffusée depuis une origine sécurisée, telle que `https`.
Les navigateurs refusent les tentatives d’installation depuis des origines non sécurisées. Cette règle ne s’applique toutefois pas lorsque l’application est diffusée localement depuis `localhost` pendant le développement.

<!-- vale off -->
Pour en savoir plus sur les contextes sécurisés et leur importance, consultez la [documentation MDN sur les contextes sécurisés](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<VideoPlayer video="installable-apps/install-chrome" width={1908} height={1052} title="Install chrome" />

## Prise en charge par les navigateurs {#browser-support}

La prise en charge de l’installation d’une application web varie selon le navigateur et la plateforme.

### Ordinateur de bureau {#browser-support-desktop}

- Les **navigateurs Chromium** (Chrome, Edge, Opera, Brave et autres) installent toute application incluant un fichier manifeste sur tous les systèmes d’exploitation de bureau pris en charge.
- **Safari** prend en charge **Fichier → Ajouter au Dock** sur macOS Sonoma (Safari 17) et les versions ultérieures. Cette procédure fonctionne pour toute application web, avec ou sans fichier manifeste.
- **Firefox** ne prend pas en charge l’installation d’applications web à partir d’un fichier manifeste sur ordinateur de bureau.

### Appareils mobiles {#browser-support-mobile}

- Sur **Android**, Chrome, Edge, Firefox, Opera et Samsung Internet prennent tous en charge l’installation d’applications web.
- Sur **iOS 16.3 et les versions antérieures**, les applications web ne peuvent être installées que depuis Safari (**Partager → Ajouter à l’écran d’accueil**).
- Sur **iOS 16.4 et les versions ultérieures**, les applications web peuvent être installées depuis le menu Partager de Safari, Chrome, Edge, Firefox et Orion.

## Annotation `@AppProfile` {#appprofile-annotation}

L’annotation `@AppProfile` s’applique à la classe principale de l’application et ne nécessite qu’une configuration minimale. Vous devez au minimum fournir les éléments suivants :

- **name** : le nom complet de l’application.
- **shortName** : une version abrégée du nom, destinée aux espaces limités.

D’autres propriétés facultatives permettent de personnaliser l’apparence et le comportement de l’application.

Lorsque l’annotation `@AppProfile` est présente, webforJ :

- configure automatiquement les balises meta nécessaires ;
- génère un [manifeste d’application web](https://developer.mozilla.org/en-US/docs/Web/Manifest) ;
- diffuse les ressources associées, telles que les icônes et les captures d’écran.

### Exemple : appliquer `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank is a simple banking application built with webforJ",
  screenshots = {
    @AppProfile.Screenshot(
      src = "ws://img/screenshots/s1.jpg",
      sizes = "1080x1920"
    )
  }
)
public class Application extends App {
}
```

## Propriétés de `@AppProfile` {#appprofile-properties}

Le tableau suivant répertorie toutes les propriétés prises en charge par l’annotation `@AppProfile` :

| **Propriété**     | **Type**                                           | **Description**                                                                                                                        | **Valeur par défaut** |
| ----------------- | -------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | Le nom complet de l’application, affiché dans les menus et les boîtes de dialogue d’installation.                                       | **Obligatoire**       |
| `shortName`       | `String`                                           | Une version abrégée du nom, utilisée lorsque l’espace est limité. Elle ne doit pas dépasser 12 caractères.                              | **Obligatoire**       |
| `description`     | `String`                                           | Une brève description de l’application, affichée lors de l’installation et dans les paramètres de l’application.                        | `""`                  |
| `themeColor`      | `String`                                           | La couleur du thème de l’application, appliquée à l’interface du navigateur au lancement de l’application.                              | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | La couleur d’arrière-plan initiale de l’application pendant son chargement.                                                             | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | L’URL à ouvrir au lancement de l’application.                                                                                          | `"."`                 |
| `display`         | `Display` **_Enum_**                               | Le mode d’affichage de l’application (par exemple, `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                              | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | L’orientation par défaut de l’application (par exemple, `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Un tableau d’icônes représentant l’application à différentes résolutions.                                                              | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Spécifie une icône par défaut pour l’application. Génère automatiquement des chemins d’icône de plusieurs tailles si cette propriété est configurée. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Un tableau de captures d’écran de l’application, utilisé dans les boîtes de dialogue d’installation.                                    | `[]`                  |
| `categories`      | `String[]`                                         | Les catégories servant à classer l’application (par exemple, `Finance`, `Shopping`).                                                   | `[]`                  |

### Propriétés de `@AppProfile.Icon` {#appprofileicon-properties}

Les icônes définissent la représentation visuelle de votre application dans les menus et sur les écrans d’accueil. L’annotation `@AppProfile.Icon` prend en charge les propriétés suivantes :

| **Propriété**                                                                     | **Type** | **Description**                                                                                         | **Valeur par défaut** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------- | -------------------- |
| `src`                                                                            | `String` | Le chemin de l’icône. Il peut s’agir d’une URL absolue ou d’un chemin `ws://`.                          | **Obligatoire**      |
| `sizes`                                                                          | `String` | Une chaîne indiquant une ou plusieurs tailles d’image au format `LargeurxHauteur` (par exemple, `512x512`). | **Obligatoire**  |
| `type`                                                                           | `String` | Le type MIME de l’icône (par exemple, `image/png`, `image/jpeg`). S’il n’est pas fourni, il sera détecté. | `""`                 |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | L’objectif de l’icône (par exemple, `any`, `maskable`, `monochrome`).                                   | `""`                 |

### Exemple {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Propriétés de `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

L’annotation `DefaultIcon` simplifie la configuration des icônes de l’application en générant plusieurs variantes de taille à partir d’une icône de base.
Elle produit des icônes aux résolutions couramment demandées par les appareils.

| **Propriété** | **Type** | **Description**                                                                                | **Valeur par défaut** |
| ------------ | -------- | ---------------------------------------------------------------------------------------------- | -------------------- |
| `value`      | `String` | Le chemin du fichier d’icône de base. Il peut s’agir d’une URL absolue ou d’un chemin `ws://`. | **Obligatoire**      |
| `sizes`      | `int[]`  | Un tableau de tailles à générer, indiquées sous forme d’entiers (par exemple, `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Exigences relatives aux fichiers d’icône
Cette configuration ne génère pas automatiquement les fichiers d’icône de l’application. Elle utilise plutôt l’annotation `@AppProfile.DefaultIcon` pour générer les entrées [`@AppProfile.Icon`](#appprofileicon-properties) correspondantes pour chaque taille spécifiée.

#### Si vous utilisez le [protocole webserver](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Vous devez fournir un fichier `icon.png` de base dans le dossier `static/icons`.
- Vous devez également inclure des variantes d’icône supplémentaires nommées `icon-144x144.png`, `icon-192x192.png` et `icon-512x512.png`.
- Ces tailles couvrent les résolutions couramment demandées par les appareils.

#### Si vous utilisez le [protocole icons](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Vous devez fournir un fichier `icon.png` de base dans le dossier `/icons`.
- Le point de terminaison `icons` fournit dynamiquement différentes tailles d’icône à la demande.
:::

### Propriétés de `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Les captures d’écran donnent un aperçu de l’application dans les boîtes de dialogue d’installation ou les boutiques d’applications. L’annotation `@AppProfile.Screenshot` prend en charge les propriétés suivantes :

| **Propriété**                                                                                  | **Type** | **Description**                                                                                                   | **Valeur par défaut** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------------- | -------------------- |
| `src`                                                                                         | `String` | Le chemin de la capture d’écran. Il peut s’agir d’une URL absolue ou d’un chemin `ws://`.                         | **Obligatoire**      |
| `sizes`                                                                                       | `String` | Une chaîne indiquant une ou plusieurs tailles d’image au format `LargeurxHauteur` (par exemple, `1080x1920`).    | **Obligatoire**      |
| `type`                                                                                        | `String` | Le type MIME de la capture d’écran (par exemple, `image/png`, `image/jpeg`). S’il n’est pas fourni, il sera détecté. | `""`               |
| `label`                                                                                       | `String` | Une description de la capture d’écran.                                                                            | `""`                 |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | Le format de la capture d’écran (par exemple, `narrow`, `wide`).                                                  | `""`                 |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | La plateforme à laquelle est destinée la capture d’écran (par exemple, `ios`, `android`).                        | `""`                 |

### Exemple {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<VideoPlayer video="installable-apps/install-android" width={374} height={824} title="Install android" />
