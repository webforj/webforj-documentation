---
sidebar_position: 2
title: Assets Protocols
description: >-
  Reference app resources with the webforJ ws, context, and icons protocols to
  load static files, classpath content, and dynamic icons.
_i18n_hash: 3928ba255cb9887c80c20f904baf62b8
---
webforJ prend en charge des protocoles d'actifs personnalisés qui permettent un accès structuré et efficace aux ressources. Ces protocoles abstraient les complexités de la récupération de ressources statiques et dynamiques, garantissant que les actifs soient récupérés et intégrés de manière transparente dans l'application.

## Le protocole webserver {#the-webserver-protocol}

Le **`ws://`** protocole vous permet de récupérer des actifs hébergés dans le dossier statique d'une application webforJ. Tous les fichiers situés dans le chemin de classe de l'application `src/main/resources/static` sont directement accessibles depuis le navigateur. Par exemple, si vous avez un fichier nommé **css/app.css** dans **resources/static**, il peut être accessible à : **`/static/css/app.css`**

Le protocole **ws://** élimine la nécessité de coder en dur le préfixe `static` dans vos URL de ressources, protégeant contre les préfixes modifiés en fonction du contexte de déploiement. Si l'application web est déployée sous un contexte autre que la racine, comme **/mycontext**, l'URL pour **css/app.css** serait : **`/mycontext/static/css/app.css`**

:::tip Configuration du préfixe statique
Vous pouvez contrôler le préfixe `static` en utilisant l'option de configuration [webforj](../configuration/properties#configuration-options) `webforj.assetsDir`. Ce paramètre spécifie le nom de route utilisé pour servir les fichiers statiques, tandis que **le dossier physique reste nommé `static`**. C'est particulièrement utile si la route statique par défaut entre en conflit avec une route dans votre application, car cela vous permet de changer le nom de route sans renommer le dossier.
:::

Vous pouvez utiliser la classe utilitaire <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> pour résoudre une URL de serveur web donnée. Cela peut être utile si vous avez un composant personnalisé qui doit prendre en charge ce protocole.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Le protocole context {#the-context-protocol}

Le protocole context fait correspondre les ressources dans le chemin de classe de l'application à `src/main/resources`. Certaines méthodes et annotations de l'API des ressources prennent en charge ce protocole pour lire le contenu d'un fichier situé dans le dossier des ressources et envoyer son contenu au navigateur. Par exemple, l'annotation `InlineJavaScript` permet de lire le contenu d'un fichier JavaScript depuis le dossier des ressources et de l'insérer côté client.

Par exemple :

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Le protocole icons {#the-icons-protocol}

Le **`icons://`** protocole fournit une approche dynamique pour la gestion des icônes, rendant efficace et flexible la génération et la distribution d'icônes dans les [applications installables](../configuration/installable-apps). Ce protocole vous permet de générer des icônes à la volée en fonction du nom de fichier et des paramètres demandés, éliminant ainsi le besoin d'icônes pré-générées dans de nombreux cas.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Icône de base {#base-icon}

Lorsqu'une icône est demandée à l'aide du protocole `icons://`, une image de base est dérivée dynamiquement du nom de fichier d'icône demandé. Cela garantit la cohérence dans la conception des icônes et permet un retour automatique à une image par défaut si aucune icône de base n'est fournie.

- **Exemple 1 :** Demande : `/icons/icon-192x192.png` → Icône de base : `resources/icons/icon.png`
- **Exemple 2 :** Demande : `/icons/icon-different-192x192.png` → Icône de base : `resources/icons/icon-different.png`

Si une image de base n'existe pas pour l'icône demandée, le logo webforJ par défaut est utilisé comme solution de secours.

:::tip `webforj.iconsDir`
Par défaut, webforJ sert des icônes depuis le répertoire `resources/icons/`. Vous pouvez changer le nom du point de terminaison en définissant la propriété `webforj.iconsDir` dans le fichier de configuration de webforJ. Cela ne change que l'URL du point de terminaison utilisé pour accéder aux icônes, pas le nom réel du dossier. Le point de terminaison par défaut est `icons/`.
:::

### Remplacement des icônes {#overriding-icons}

Vous pouvez remplacer des tailles d'icônes spécifiques en plaçant des images pré-générées dans le répertoire `resources/icons/`. Cela permet un meilleur contrôle sur l'apparence des icônes lorsque certaines tailles ou styles doivent être personnalisés.

- **Exemple :** Si `resources/icons/icon-192x192.png` existe, il sera servi directement plutôt que d'être généré dynamiquement.

### Personnalisation de l'apparence des icônes {#customizing-icon-appearance}

Le protocole `icons://` prend en charge des paramètres supplémentaires qui vous permettent de personnaliser l'apparence des icônes générées dynamiquement. Cela inclut des options pour le rembourrage et la couleur de fond.

- **Rembourrage** : Le paramètre `padding` peut être utilisé pour contrôler le rembourrage autour de l'icône. Les valeurs acceptées vont de `0`, ce qui signifie pas de rembourrage, à `1`, ce qui signifie un rembourrage maximal.
  - **Exemple :** `/icons/icon-192x192.png?padding=0.3`

- **Couleur de fond** : Le paramètre `background` vous permet de définir la couleur de fond de l'icône. Il prend en charge les valeurs suivantes :
  - **`transparent`** : Pas de couleur de fond.
  <!-- vale off -->
  - **Codes de couleur Hexadécimaux** : Couleurs de fond personnalisées (par exemple, `FFFFFF` pour blanc).
  <!-- vale on -->
  - **`auto`** : Tente de détecter automatiquement la couleur de fond de l'icône.

  Par exemple :

  - **Exemple 1 :** `/icons/icon-192x192.png?background=000000`
  - **Exemple 2 :** `/icons/icon-192x192.png?background=auto`
