---
title: Configuration
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: f7e73354e4d9548e79122405c21ae275
---
craftforJ est configuré dans `webforj.conf`. Les noms des propriétés sont les mêmes que sur [Spring](/docs/integrations/spring/overview), donc définissez-les dans `application.properties` si c'est là que se trouve votre configuration.

## Propriétés requises {#required-properties}

| Propriété | Type | Par défaut | Description |
|-----------|------|------------|-------------|
| **`webforj.debug`** | Boolean | `false` | Active le mode débogage. craftforJ en a besoin |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Active craftforJ |

Les deux propriétés doivent être activées. Consultez [Sécurité](/docs/craftforj/security#two-required-settings) pour comprendre pourquoi craftforJ nécessite deux paramètres plutôt qu'un.

## Accès {#access}

| Propriété | Type | Par défaut | Description |
|-----------|------|------------|-------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Liste ou Chaîne | boucle locale seulement | Adresses des clients autorisées au-delà de la machine exécutant l'application |

Par défaut, seul un navigateur sur la même machine que l'application peut accéder à craftforJ. Pour autoriser d'autres machines, listez leurs adresses. Une entrée se terminant par `*` correspond à un préfixe, et un seul `*` supprime complètement la restriction.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Un caractère générique permet à quiconque de rejoindre votre application
craftforJ lit et écrit vos sources de projet. N'utilisez `*` que sur un réseau où vous êtes certain de savoir qui peut atteindre le port, comme dans un conteneur que vous utilisez uniquement. Ne l'utilisez jamais sur un réseau partagé.
:::

## Racine du projet {#project-root}

| Propriété | Type | Par défaut | Description |
|-----------|------|------------|-------------|
| **`webforj.devtools.project-root`** | Chaîne | détectée | Le répertoire où se trouvent vos sources |

craftforJ détermine où se trouve votre projet en fonction de la manière dont l'application a été lancée. Des dispositions de projet inhabituelles et certains paramètres de conteneur contournent cette détection. Si [Info sur l'application](/docs/craftforj/app-info) signale la mauvaise racine de projet, définissez-la ici.

```ini title="webforj.conf"
webforj.devtools.project-root = "/home/me/projects/my-app"
```

Sur Spring, définissez la même clé dans `application.properties`.

:::info Renommé le 26.03
Ce paramètre était auparavant `webforj.devtools.craftforj.project-root`. L'ancienne clé fonctionne toujours comme un secours, mais elle est obsolète et génère un avertissement lorsqu'elle est utilisée. Si les deux clés sont définies, `webforj.devtools.project-root` a la priorité.
:::

## Drapeaux de fonctionnalités {#feature-flags}

Chacune de ces fonctionnalités est activée par défaut. Désactiver l'une d'elles restreint ce que craftforJ est autorisé à faire.

| Propriété | Désactiver supprime |
|-----------|---------------------|
| **`webforj.devtools.craftforj.source-changes`** | L'écriture des changements de propriété en Java, et la modification de l'accès aux routes |
| **`webforj.devtools.craftforj.stylesheet-changes`** | L'enregistrement des thèmes et styles dans votre feuille de style |
| **`webforj.devtools.craftforj.ai.enabled`** | L'assistant IA |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | L'assistant écrivant du Java de manière autonome |

Désactiver un drapeau désactive la fonctionnalité pour tous les utilisateurs de cette application. Les paramètres de craftforJ sont par développeur et peuvent seulement restreindre davantage, donc un développeur ne peut pas réactiver une capacité que l'application a désactivée.

:::info Les fonctionnalités que vous désactivez restent visibles
Lorsque l'un des drapeaux est désactivé, le contrôle reste dans craftforJ et est marqué comme non pris en charge par l'application connectée.
:::

:::warning En production
Laissez `webforj.devtools.craftforj.enabled` non défini. Consultez [Sécurité](/docs/craftforj/security#in-production) pour la liste complète de contrôle.
:::
