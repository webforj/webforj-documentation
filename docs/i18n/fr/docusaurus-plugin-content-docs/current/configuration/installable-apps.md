---
title: Installable Apps
sidebar_position: 10
_i18n_hash: 611c70817a57e6cad940081f90d4e0a2
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

L'annotation `@AppProfile` dans webforJ vous permet de rendre votre application installable sur les plateformes prises en charge. 
Les applications web installables s'intègrent parfaitement au système d'exploitation de l'appareil. 
Une fois installées, elles apparaissent sur l'écran d'accueil ou dans le menu des applications, semblables aux applications natives. 
Pour ce faire, certaines métadonnées telles que le nom, la description et les icônes doivent être fournies. 
Ces informations aident le système d'exploitation à identifier et à afficher l'application.

:::info Exigence d'origine sécurisée
Pour qu'une application soit installable, elle doit être servie à partir d'une origine sécurisée, comme `https`. 
Cette exigence garantit que l'application respecte les normes de sécurité nécessaires à son installation. Cependant, cette règle ne s'applique pas lors du service de l'application localement à partir de `localhost` pendant le développement.

<!-- vale off -->
Pour plus de détails sur les contextes sécurisés et leur importance, consultez la [documentation MDN des contextes sécurisés](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
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
- Génère un [Manifest d'application web](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Sert des ressources connexes telles que des icônes et des captures d'écran.

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
| `name`             | `String`                                           | Le nom complet de l'application, affiché dans les menus d'applications et les boîtes de dialogue d'installation. | **Obligatoire**           |
| `shortName`        | `String`                                           | Une version courte du nom, utilisée dans des contextes à espace limité. Ne doit pas dépasser 12 caractères.  | **Obligatoire**           |
| `description`      | `String`                                           | Une brève description de l'application, affichée lors de l'installation et dans les paramètres de l'application. | `""`                      |
| `themeColor`       | `String`                                           | La couleur thème de l'application, appliquée à l'interface utilisateur du navigateur lorsque l'application est lancée. | `"#ffffff"`               |
| `backgroundColor`  | `String`                                           | La couleur de fond initiale de l'application lors du chargement.                                          | `"#f8fafc"`               |
| `startUrl`         | `String`                                           | L'URL à ouvrir lorsque l'application est lancée.                                                        | `"."`                     |
| `display`          | `Display` **_Enum_**                              | Le mode d'affichage de l'application (par exemple, `FULLSCREEN`, `STANDALONE`, `BROWSER`).                | `STANDALONE`              |
| `orientation`      | `Orientation` **_Enum_**                          | L'orientation par défaut de l'application (par exemple, `PORTRAIT`, `LANDSCAPE`, `NATURAL`).              | `NATURAL`                 |
| `icons`            | [`Icon[]`](#appprofileicon-properties)            | Un tableau d'icônes représentant l'application à différentes résolutions.                                 | `[]`                      |
| `defaultIcon`      | [`DefaultIcon`](#appprofiledefaulticon-properties) | Spécifie une icône par défaut pour l'application. Génère automatiquement des chemins d'icône de différentes tailles si configuré. | `icons://icon.png`       |
| `screenshots`      | [`Screenshot[]`](#appprofilescreenshot-properties) | Un tableau de captures d'écran pour l'application, utilisé dans les boîtes de dialogue d'installation.     | `[]`                      |
| `categories`       | `String[]`                                         | Catégories pour classifier l'application (par exemple, `Finance`, `Shopping`).                            | `[]`                      |

### Propriétés `@AppProfile.Icon` {#appprofileicon-properties}

Les icônes définissent la représentation visuelle de votre application dans les menus et sur l'écran d'accueil. L'annotation `@AppProfile.Icon` prend en charge les propriétés suivantes :

| **Propriété**                                                                     | **Type** | **Description**                                                                                        | **Valeur par défaut** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ---------------------- |
| `src`                                                                            | `String` | Le chemin vers l'icône. Cela peut être une URL absolue ou un chemin `ws://`.                           | **Obligatoire**        |
| `sizes`                                                                          | `String` | Une chaîne qui spécifie une ou plusieurs tailles de l'image au format `LargeurxHauteur` (par exemple, `512x512`). | **Obligatoire**        |
| `type`                                                                           | `String` | Le type MIME de l'icône (par exemple, `image/png`, `image/jpeg`). Si non fourni, il sera détecté     | `""`                   |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | L'objectif de l'icône (par exemple, `any`, `maskable`, `monochrome`).                                 | `""`                   |

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
Cela est particulièrement utile pour assurer la compatibilité sur des appareils avec des résolutions variées.

| **Propriété** | **Type** | **Description**                                                                 | **Valeur par défaut** |
| --------------| -------- | -------------------------------------------------------------------------------- | ---------------------- |
| `value`       | `String` | Le chemin vers le fichier icône de base. Cela peut être une URL absolue ou un chemin `ws://`. | **Obligatoire**        |
| `sizes`       | `int[]`  | Un tableau de tailles à générer, spécifié comme entiers (par exemple, `{144, 192, 512}`). | `{144, 192, 512}`     |

:::info Exigences sur les fichiers d'icônes
Cette configuration ne génère pas automatiquement les fichiers d'icônes réels pour l'application. Au lieu de cela, elle utilise l'annotation `@AppProfile.DefaultIcon` pour générer les entrées `@AppProfile.Icon` correspondantes pour chaque taille spécifiée.

#### Si vous utilisez le [protocole webserver](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- Vous devez fournir un fichier `icon.png` de base dans le dossier `static/icons`.
- Vous êtes censé inclure des variations d'icônes nommées `icon-144x144.png`, `icon-192x192.png`, et `icon-512x512.png`.
- Ces tailles spécifiques garantissent la compatibilité avec divers appareils et résolutions.

#### Si vous utilisez le [protocole icons](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- Vous êtes censé fournir un fichier `icon.png` de base dans le dossier `/icons`.
- Le point de terminaison `icons` fournit dynamiquement des tailles d'icônes différentes sur demande lorsqu'elles sont demandées.
:::

### Propriétés `@AppProfile.Screenshot` {#appprofilescreenshot-properties}

Les captures d'écran fournissent un aperçu de l'application dans les boîtes de dialogue d'installation ou les magasins d'applications. L'annotation `@AppProfile.Screenshot` prend en charge les propriétés suivantes :

| **Propriété**                                                                                  | **Type** | **Description**                                                                                             | **Valeur par défaut** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ---------------------- |
| `src`                                                                                         | `String` | Le chemin vers la capture d'écran. Cela peut être une URL absolue ou un chemin `ws://`.                    | **Obligatoire**        |
| `sizes`                                                                                       | `String` | Une chaîne qui spécifie une ou plusieurs tailles de l'image au format `LargeurxHauteur` (par exemple, `1080x1920`). | **Obligatoire**        |
| `type`                                                                                        | `String` | Le type MIME de la capture d'écran (par exemple, `image/png`, `image/jpeg`). Si non fourni, il sera détecté | `""`                   |
| `label`                                                                                       | `String` | Un label descriptif pour la capture d'écran.                                                                | `""`                   |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | Le facteur de forme de la capture d'écran (par exemple, `narrow`, `wide`).                                   | `""`                   |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | La plateforme pour laquelle la capture d'écran est destinée (par exemple, `ios`, `android`).                | `""`                   |

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
