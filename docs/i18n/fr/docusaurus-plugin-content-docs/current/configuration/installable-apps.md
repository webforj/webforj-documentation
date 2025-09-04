---
title: Installable Apps
sidebar_position: 10
_i18n_hash: 76c2d63a5d5ea13f5ce55431108e6a3d
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

L'annotation `@AppProfile` dans webforJ vous permet de rendre votre application installable sur les plateformes supportées. 
Les applications web installables s'intègrent parfaitement au système d'exploitation de l'appareil. 
Lorsqu'elles sont installées, elles apparaissent sur l'écran d'accueil ou le menu des applications, similaire aux applications natives. 
Pour y parvenir, certaines métadonnées telles que le nom, la description et les icônes doivent être fournies. 
Ces détails aident le système d'exploitation à identifier et à afficher l'application.

:::info Exigence d'origine sécurisée
Pour qu'une application soit installable, elle doit être servie depuis une origine sécurisée, comme `https`. 
Cette exigence garantit que l'application respecte les normes de sécurité nécessaires à l'installation. Cependant, cette règle ne s'applique pas lorsque l'application est servie localement à partir de `localhost` pendant le développement.

<!-- vale off -->
Pour plus de détails sur les contextes sécurisés et leur importance, consultez la [documentation MDN sur les Contextes Sécurisés](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## Annotation `@AppProfile` {#appprofile-annotation}

L'annotation `@AppProfile` est appliquée à la classe principale de l'application et nécessite une configuration minimale. Au minimum, vous devez fournir :

- **name** : Le nom complet de l'application.
- **shortName** : Une version concise du nom à utiliser dans des contextes à espace limité.

Des propriétés optionnelles supplémentaires permettent de personnaliser l'apparence et le comportement de l'application.

Lorsque l'annotation `@AppProfile` est présente, webforJ :

- Configure automatiquement les balises méta nécessaires.
- Génère un [Manifest d'Application Web](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Serre les ressources associées telles que les icônes et captures d'écran.

### Exemple : Application de `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Banque Zyntric",
  shortName = "ZBank",
  description = "La Banque Zyntric est une application bancaire simple construite avec webforJ",
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

| **Propriété**      | **Type**                                           | **Description**                                                                                           | **Valeur par Défaut**     |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | Le nom complet de l'application, affiché dans les menus d'applications et les boîtes de dialogue d'installation.                                | **Obligatoire**         |
| `shortName`       | `String`                                           | Une version courte du nom, utilisée dans des contextes avec espace limité. Ne doit pas dépasser 12 caractères.         | **Obligatoire**         |
| `description`     | `String`                                           | Une brève description de l'application, affichée lors de l'installation et dans les paramètres de l'application.                        | `""`                  |
| `themeColor`      | `String`                                           | La couleur du thème pour l'application, appliquée à l'interface du navigateur lorsque l'application est lancée.                          | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | La couleur de fond initiale de l'application pendant le chargement.                                                  | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | L'URL à ouvrir lorsque l'application est lancée.                                                                 | `"."`                 |
| `display`         | `Display` **_Enum_**                               | Le mode d'affichage de l'application (e.g., `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | L'orientation par défaut de l'application (e.g., `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | Un tableau d'icônes représentant l'application à différentes résolutions.                                          | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Spécifie une icône par défaut pour l'application. Génère automatiquement des chemins d'icônes dans plusieurs tailles si configuré. | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | Un tableau de captures d'écran pour l'application, utilisé dans les boîtes de dialogue d'installation.                                        | `[]`                  |
| `categories`      | `String[]`                                         | Catégories pour classer l'application (e.g., `Finance`, `Shopping`).                                             | `[]`                  |

### Propriétés `@AppProfile.Icon` {#appprofileicon-properties}

Les icônes définissent la représentation visuelle de votre application dans les menus et les écrans d'accueil. L'annotation `@AppProfile.Icon` prend en charge les propriétés suivantes :

| **Propriété**                                                                     | **Type** | **Description**                                                                                        | **Valeur par Défaut** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | Le chemin vers l'icône. Cela peut être une URL absolue ou un chemin `ws://`.                                     | **Obligatoire**     |
| `sizes`                                                                          | `String` | Une chaîne qui spécifie une ou plusieurs tailles de l'image au format `LargeurxHauteur` (e.g., `512x512`). | **Obligatoire**     |
| `type`                                                                           | `String` | Le type MIME de l'icône (e.g., `image/png`, `image/jpeg`). S'il n'est pas fourni, il sera détecté  | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | Le but de l'icône (e.g., `any`, `maskable`, `monochrome`).                                       | `""`              |

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
Ceci est particulièrement utile pour garantir la compatibilité avec des appareils ayant des résolutions variées.

| **Propriété** | **Type** | **Description**                                                                 | **Valeur par Défaut** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | Le chemin vers le fichier d'icône de base. Cela peut être une URL absolue ou un chemin `ws://`. | **Obligatoire**     |
| `sizes`      | `int[]`  | Un tableau de tailles à générer, spécifié en entiers (e.g., `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Exigences Relatives aux Fichiers d'Icônes
Cette configuration ne génère pas automatiquement les fichiers d'icônes réels pour l'application. Au lieu de cela, elle utilise l'annotation `@AppProfile.DefaultIcon` pour générer les entrées correspondantes [`@AppProfile.Icon`](#appprofileicon-properties) pour chaque taille spécifiée.

#### Si vous utilisez le [protocole du serveur web](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Vous devez fournir un fichier d'icône de base `icon.png` dans le dossier `static/icons`.
- Vous êtes censé inclure des variations supplémentaires nommées `icon-144x144.png`, `icon-192x192.png` et `icon-512x512.png`.
- Ces tailles spécifiques garantissent la compatibilité avec différents appareils et résolutions.

#### Si vous utilisez le [protocole des icônes](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Vous êtes censé fournir un fichier d'icône de base `icon.png` dans le dossier `/icons`.
- Le point de terminaison `icons` fournit dynamiquement différentes tailles d'icônes à la demande lorsqu'elles sont demandées.
:::

### Propriétés `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Les captures d'écran fournissent un aperçu de l'application dans les boîtes de dialogue d'installation ou les boutiques d'applications. L'annotation `@AppProfile.Screenshot` prend en charge les propriétés suivantes :

| **Propriété**                                                                                  | **Type** | **Description**                                                                                             | **Valeur par Défaut** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | Le chemin vers la capture d'écran. Cela peut être une URL absolue ou un chemin `ws://`.                                 | **Obligatoire**     |
| `sizes`                                                                                       | `String` | Une chaîne qui spécifie une ou plusieurs tailles de l'image au format `LargeurxHauteur` (e.g., `1080x1920`).    | **Obligatoire**     |
| `type`                                                                                        | `String` | Le type MIME de la capture d'écran (e.g., `image/png`, `image/jpeg`). S'il n'est pas fourni, il sera détecté | `""`              |
| `label`                                                                                       | `String` | Une étiquette descriptive pour la capture d'écran.                                                                     | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | Le facteur de forme de la capture d'écran (e.g., `narrow`, `wide`).                                                 | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | La plateforme pour laquelle la capture d'écran est destiné(e) (e.g., `ios`, `android`).                                 | `""`              |

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
