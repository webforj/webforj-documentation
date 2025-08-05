---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: b37158687b15dc2b94a7543624eaa09f
---
webforJ prend en charge des protocoles d'assets personnalisés qui permettent un accès structuré et efficace aux ressources. Ces protocoles abstraient les complexités de la récupération des ressources statiques et dynamiques, garantissant que les assets sont récupérés et intégrés sans problème dans l'application.

## Le protocole du serveur web {#the-webserver-protocol}

Le protocole **`ws://`** vous permet de récupérer des assets hébergés dans le dossier statique d'une application webforJ. Tous les fichiers situés dans le classpath de l'application `src/main/resources/static` sont directement accessibles depuis le navigateur. Par exemple, si vous avez un fichier nommé **css/app.css** dans **resources/static**, il peut être accessible à l'adresse : **`/static/css/app.css`**  

Le protocole **ws://** supprime le besoin de coder en dur le préfixe `static` dans vos URL de ressources, évitant ainsi les problèmes liés aux préfixes modifiés en fonction du contexte de déploiement. Si l'application web est déployée sous un contexte autre que la racine, comme **/mycontext**, l'URL pour **css/app.css** serait : **`/mycontext/static/css/app.css`**  

:::tip Configuration du préfixe statique
Vous pouvez contrôler le préfixe `static` en utilisant l'option de configuration [webforj](../configuration/properties#configuration-options) `webforj.assetsDir`. Ce paramètre spécifie le nom de la route utilisée pour servir les fichiers statiques, tandis que **le dossier physique reste nommé `static`**. Cela est particulièrement utile si la route statique par défaut entre en conflit avec une route de votre application, car cela vous permet de changer le nom de la route sans renommer le dossier.
:::

Vous pouvez utiliser la classe utilitaire <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> pour résoudre une URL de serveur web donnée. Cela peut être utile si vous avez un composant personnalisé qui doit prendre en charge ce protocole.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Le protocole de contexte {#the-context-protocol}

Le protocole de contexte correspond aux ressources situées dans le classpath de l'application à `src/main/resources`. Certaines méthodes et annotations de l'API de ressource prennent en charge ce protocole pour lire le contenu d'un fichier situé dans le dossier de ressources et envoyer son contenu au navigateur. Par exemple, l'annotation `InlineJavaScript` permet de lire le contenu d'un fichier JavaScript depuis le dossier de ressources et de l'incorporer côté client.

Par exemple :

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Le protocole des icônes {#the-icons-protocol}

Le protocole **`icons://`** fournit une approche dynamique de la gestion des icônes, rendant la génération et la fourniture d'icônes dans des [applications installables](../configuration/installable-apps) efficaces et flexibles. Ce protocole vous permet de générer des icônes à la volée en fonction du nom de fichier demandé et des paramètres, éliminant ainsi le besoin d'icônes pré-générées dans de nombreux cas.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Icône de base {#base-icon}

Lorsqu'une icône est demandée à l'aide du protocole `icons://`, une image de base est dérivée dynamiquement du nom de fichier de l'icône demandée. Cela garantit la cohérence dans la conception des icônes et permet une extraction automatique d'une image par défaut si aucune icône de base n'est fournie.

- **Exemple 1:** Demande : `/icons/icon-192x192.png` → Icône de base : `resources/icons/icon.png`
- **Exemple 2:** Demande : `/icons/icon-different-192x192.png` → Icône de base : `resources/icons/icon-different.png`

Si une image de base n'existe pas pour l'icône demandée, le logo par défaut de webforJ est utilisé comme solution de repli.

:::tip `webforj.iconsDir`
Par défaut, webforJ sert des icônes à partir du répertoire `resources/icons/`. Vous pouvez changer le nom de point de terminaison en définissant la propriété `webforj.iconsDir` dans le fichier de configuration webforJ. Cela ne change que l'URL utilisée pour accéder aux icônes, pas le nom réel du dossier. Le point de terminaison par défaut est `icons/`. 
:::

### Remplacer les icônes {#overriding-icons}

Vous pouvez remplacer des tailles d'icônes spécifiques en plaçant des images pré-générées dans le répertoire `resources/icons/`. Cela offre un meilleur contrôle sur l'apparence des icônes lorsque certaines tailles ou styles ont besoin d'être personnalisés.

- **Exemple :** Si `resources/icons/icon-192x192.png` existe, il sera servi directement au lieu d'être généré dynamiquement.

### Personnaliser l'apparence des icônes {#customizing-icon-appearance}

Le protocole `icons://` prend en charge des paramètres supplémentaires qui vous permettent de personnaliser l'apparence des icônes générées dynamiquement. Cela inclut des options pour le rembourrage et la couleur de fond.

- **Rembourrage** : Le paramètre `padding` peut être utilisé pour contrôler le rembourrage autour de l'icône. Les valeurs acceptées varient de `0`, ce qui signifie aucun rembourrage, à `1`, ce qui signifie un rembourrage maximal.
  - **Exemple :** `/icons/icon-192x192.png?padding=0.3`
  
- **Couleur de fond** : Le paramètre `background` vous permet de définir la couleur de fond de l'icône. Il prend en charge les valeurs suivantes :
  - **`transparent`** : Pas de couleur de fond.
  <!-- vale off -->
  - **Codes de couleur hexadécimaux** : Couleurs de fond personnalisées (par exemple, `FFFFFF` pour le blanc).
  <!-- vale on -->
  - **`auto`** : Tente de détecter automatiquement la couleur de fond de l'icône.

  Par exemple : 
  
  - **Exemple 1:** `/icons/icon-192x192.png?background=000000`
  - **Exemple 2:** `/icons/icon-192x192.png?background=auto`
