---
title: Installable Apps
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 60a6bf616536e9c202af684e9a505af6
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

L'annotation `@AppProfile` dans webforJ vous permet de rendre votre application installable sur les plateformes prises en charge.  
Les applications web installables s'intègrent au système d'exploitation de l'appareil.  
Lorsqu'elles sont installées, elles apparaissent sur l'écran d'accueil ou dans le menu des applications, de manière similaire aux applications natives.  
Pour y parvenir, certaines métadonnées telles que le nom, la description et les icônes doivent être fournies.  
Ces détails aident le système d'exploitation à identifier et à afficher l'application.

:::info Exigence de provenance sécurisée  
Pour qu'une application soit installable, elle doit être servie depuis une provenance sécurisée, comme `https`.  
Les navigateurs rejettent les tentatives d'installation sur des provenances non sécurisées. Cependant, cette règle ne s'applique pas lors du service de l'application localement depuis `localhost` pendant le développement.

<!-- vale off -->  
Pour plus de détails sur les contextes sécurisés et leur importance, reportez-vous à la [documentation MDN sur les contextes sécurisés](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).  
<!-- vale on -->  
:::

<div class="videos-container">  
  <video controls>  
    <source src="/video/install-chrome.mp4" type="video/mp4"/>  
  </video>  
</div>

## Support des navigateurs {#browser-support}

Le support pour l'installation d'une application web varie en fonction du navigateur et de la plateforme.

### Bureau {#browser-support-desktop}

- **Navigateurs Chromium** (Chrome, Edge, Opera, Brave, et autres) installent toute application qui fournit un fichier manifeste sur tous les systèmes d'exploitation de bureau pris en charge.
- **Safari** prend en charge **Fichier → Ajouter au Dock** sur macOS Sonoma (Safari 17) et versions ultérieures. Le processus fonctionne pour toute application web, avec ou sans fichier manifeste.
- **Firefox** ne prend pas en charge l'installation d'applications web à partir d'un fichier manifeste sur bureau.

### Mobile {#browser-support-mobile}

- Sur **Android**, Chrome, Edge, Firefox, Opera et Internet Samsung prennent tous en charge l'installation d'applications web.
- Sur **iOS 16.3 et versions antérieures**, les applications web ne peuvent être installées que depuis Safari (**Partager → Ajouter à l'écran d'accueil**).
- Sur **iOS 16.4 et versions ultérieures**, les applications web peuvent être installées depuis le menu Partager dans Safari, Chrome, Edge, Firefox et Orion.

## Annotation `@AppProfile` {#appprofile-annotation}

L'annotation `@AppProfile` est appliquée à la classe principale de l'application et nécessite une configuration minimale. Au minimum, vous devez fournir :

- **name** : Le nom complet de l'application.
- **shortName** : Une version concise du nom à utiliser dans des contextes à espace limité.

Des propriétés optionnelles supplémentaires permettent de personnaliser l'apparence et le comportement de l'application.

Lorsque l'annotation `@AppProfile` est présente, webforJ :

- Configure automatiquement les balises méta nécessaires.
- Génère un [Manifeste d'application Web](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Fournit des ressources connexes telles que des icônes et des captures d'écran.

### Exemple : Application de `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank est une application bancaire simple construite avec webforJ",
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

## Propriétés `@AppProfile` {#appprofile-properties}

Le tableau suivant répertorie toutes les propriétés prises en charge par l'annotation `@AppProfile` :

| **Propriété**      | **Type**                                           | **Description**                                                                                           | **Valeur par défaut**     |
| ------------------ | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | -------------------------- |
| `name`             | `String`                                          | Le nom complet de l'application, affiché dans les menus d'application et les dialogues d'installation.    | **Obligatoire**            |
| `shortName`        | `String`                                          | Une version courte du nom, utilisée dans des contextes où l'espace est limité. Ne doit pas dépasser 12 caractères. | **Obligatoire**            |
| `description`      | `String`                                          | Une brève description de l'application, affichée lors de l'installation et dans les paramètres de l'application. | `""`                       |
| `themeColor`       | `String`                                          | La couleur thème de l'application, appliquée à l'interface du navigateur lorsque l'application est lancée. | `"#ffffff"`                |
| `backgroundColor`  | `String`                                          | La couleur de fond initiale de l'application pendant le chargement.                                       | `"#f8fafc"`                |
| `startUrl`         | `String`                                          | L'URL à ouvrir lorsque l'application est lancée.                                                          | `"."`                      |
| `display`          | `Display` **_Enum_**                             | Le mode d'affichage de l'application (par exemple, `FULLSCREEN`, `STANDALONE`, `BROWSER`).                  | `STANDALONE`               |
| `orientation`      | `Orientation` **_Enum_**                         | L'orientation par défaut de l'application (par exemple, `PORTRAIT`, `LANDSCAPE`, `NATURAL`).              | `NATURAL`                  |
| `icons`            | [`Icon[]`](#appprofileicon-properties)            | Un tableau d'icônes représentant l'application à différentes résolutions.                                 | `[]`                       |
| `defaultIcon`      | [`DefaultIcon`](#appprofiledefaulticon-properties) | Spécifie une icône par défaut pour l'application. Génère automatiquement des chemins d'icônes dans plusieurs tailles si configuré. | `icons://icon.png`      |
| `screenshots`      | [`Screenshot[]`](#appprofilescreenshot-properties) | Un tableau de captures d'écran pour l'application, utilisé dans les dialogues d'installation.              | `[]`                       |
| `categories`       | `String[]`                                        | Catégories pour classer l'application (par exemple, `Finance`, `Shopping`).                               | `[]`                       |

### Propriétés `@AppProfile.Icon` {#appprofileicon-properties}

Les icônes définissent la représentation visuelle de votre application dans les menus et sur les écrans d'accueil. L'annotation `@AppProfile.Icon` prend en charge les propriétés suivantes :

| **Propriété**                                                                     | **Type** | **Description**                                                                                        | **Valeur par défaut** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | --------------------- |
| `src`                                                                            | `String` | Le chemin vers l'icône. Cela peut être une URL absolue ou un chemin `ws://`.                            | **Obligatoire**       |
| `sizes`                                                                          | `String` | Une chaîne qui spécifie une ou plusieurs tailles de l'image au format `LargeurxHauteur` (par exemple, `512x512`). | **Obligatoire**       |
| `type`                                                                           | `String` | Le type média de l'icône (par exemple, `image/png`, `image/jpeg`). S'il n'est pas fourni, il sera détecté | `""`                  |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Le but de l'icône (par exemple, `any`, `maskable`, `monochrome`).                                    | `""`                  |

### Exemple {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### Propriétés `@AppProfile.DefaultIcon` {#appprofiledefaulticon-properties}

L'annotation `DefaultIcon` simplifie la configuration des icônes d'application en générant plusieurs variantes de taille à partir d'une icône de base.  
Elle produit des icônes aux résolutions que les appareils demandent couramment.

| **Propriété** | **Type** | **Description**                                                                 | **Valeur par défaut** |
| --------------| -------- | ------------------------------------------------------------------------------- | ---------------------- |
| `value`       | `String` | Le chemin vers le fichier d'icône de base. Cela peut être une URL absolue ou un chemin `ws://`.| **Obligatoire**       |
| `sizes`       | `int[]`  | Un tableau de tailles à générer, spécifié sous forme d'entiers (par exemple, `{144, 192, 512}`). | `{144, 192, 512}`   |

:::info Exigences des fichiers d'icônes  
Cette configuration ne génère pas automatiquement les fichiers d'icônes réels pour l'application. Au lieu de cela, elle utilise l'annotation `@AppProfile.DefaultIcon` pour générer des entrées [`@AppProfile.Icon`](#appprofileicon-properties) correspondantes pour chaque taille spécifiée.

#### Si vous utilisez le [protocole du serveur web](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Vous devez fournir un fichier d'icône de base `icon.png` dans le dossier `static/icons`.
- Vous devez inclure des variations d'icônes supplémentaires nommées `icon-144x144.png`, `icon-192x192.png`, et `icon-512x512.png`.
- Ces tailles spécifiques couvrent les résolutions que les appareils demandent couramment.

#### Si vous utilisez le [protocole des icônes](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Vous devez fournir un fichier d'icône de base `icon.png` dans le dossier `/icons`.
- Le point de terminaison `icons` fournit dynamiquement différentes tailles d'icônes à la demande lorsqu'elles sont demandées.  
:::

### Propriétés `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Les captures d'écran fournissent un aperçu de l'application dans les dialogues d'installation ou les magasins d'applications. L'annotation `@AppProfile.Screenshot` prend en charge les propriétés suivantes :

| **Propriété**                                                                                  | **Type** | **Description**                                                                                             | **Valeur par défaut** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | --------------------- |
| `src`                                                                                         | `String` | Le chemin vers la capture d'écran. Cela peut être une URL absolue ou un chemin `ws://`.                     | **Obligatoire**       |
| `sizes`                                                                                       | `String` | Une chaîne qui spécifie une ou plusieurs tailles de l'image au format `LargeurxHauteur` (par exemple, `1080x1920`). | **Obligatoire**       |
| `type`                                                                                        | `String` | Le type média de la capture d'écran (par exemple, `image/png`, `image/jpeg`). S'il n'est pas fourni, il sera détecté | `""`                  |
| `label`                                                                                       | `String` | Une étiquette descriptive pour la capture d'écran.                                                          | `""`                  |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | Le facteur de forme de la capture d'écran (par exemple, `narrow`, `wide`).                                   | `""`                  |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | La plateforme pour laquelle la capture d'écran est destinée (par exemple, `ios`, `android`).                 | `""`                  |

### Exemple {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<div class="videos-container">  
  <video controls>  
    <source src="/video/install-android.mp4" type="video/mp4"/>  
  </video>  
</div>
