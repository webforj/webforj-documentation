---
title: Configuration
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: 025fb1766af8cbcb741f6f353bdf6523
---
craftforJ est configuré dans `webforj.conf`. Les noms des propriétés sont les mêmes sur [Spring](/docs/integrations/spring/overview), donc définissez-les dans `application.properties` si c'est là que se trouve votre configuration.

## Propriétés requises {#required-properties}

| Propriété | Type | Par défaut | Description |
|-----------|------|------------|-------------|
| **`webforj.debug`** | Boolean | `false` | Active le mode débogage. craftforJ en a besoin |
| **`webforj.devtools.craftforj.enabled`** | Boolean | `false` | Active craftforJ |

Les deux propriétés doivent être activées. Consultez [Sécurité](/docs/craftforj/security#two-required-settings) pour comprendre pourquoi craftforJ nécessite deux réglages plutôt qu'un.

## Accès {#access}

| Propriété | Type | Par défaut | Description |
|-----------|------|------------|-------------|
| **`webforj.devtools.craftforj.hosts-allowed`** | Liste ou Chaîne | uniquement loopback | Adresses clients autorisées au-delà de la machine exécutant l'application |

Par défaut, seul un navigateur sur la même machine que l'application peut atteindre craftforJ. Pour autoriser d'autres machines, listez leurs adresses. Une entrée se terminant par `*` correspond à un préfixe, et un seul `*` supprime entièrement la restriction.

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning Un caractère générique permet à quiconque ayant accès à votre application
craftforJ lit et écrit vos sources de projet. N'utilisez `*` que sur un réseau dont vous êtes certain de qui peut accéder au port, comme un conteneur que vous utilisez seul. Ne l'utilisez jamais sur un réseau partagé.
:::

## Racine du projet {#project-root}

| Propriété | Type | Par défaut | Description |
|-----------|------|------------|-------------|
| **`webforj.devtools.craftforj.project-root`** | Chaîne | détectée | Le répertoire où se trouvent vos sources |

craftforJ détermine où se trouve votre projet en fonction de la façon dont l'application a été démarrée. Des mises en page de projet inhabituelles et certaines configurations de conteneurs peuvent empêcher cette détection. Si [Infos sur l'application](/docs/craftforj/app-info) signale la mauvaise racine de projet, définissez-la ici.

## Drapeaux de fonctionnalités {#feature-flags}

Chacun de ces drapeaux est activé par défaut. Désactiver l'un d'eux limite ce que craftforJ est autorisé à faire.

| Propriété | Désactiver retire |
|-----------|-------------------|
| **`webforj.devtools.craftforj.source-changes`** | Enregistrement des modifications de propriétés dans Java, et changement d'accès aux routes |
| **`webforj.devtools.craftforj.stylesheet-changes`** | Enregistrement des thèmes et styles dans votre feuille de style |
| **`webforj.devtools.craftforj.ai.enabled`** | L'assistant IA |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | L'assistant écrit son propre Java |

Désactiver un drapeau désactive la fonctionnalité pour tous les utilisateurs de cette application. Les paramètres de craftforJ sont propres à chaque développeur et peuvent uniquement restreindre davantage, donc un développeur ne peut pas réactiver une capacité que l'application a désactivée.

:::info Les fonctionnalités que vous désactivez restent visibles
Lorsque un drapeau est désactivé, le contrôle reste dans craftforJ et est marqué comme non pris en charge par l'application connectée.
:::

:::warning En production
Laissez `webforj.devtools.craftforj.enabled` non défini. Consultez [Sécurité](/docs/craftforj/security#in-production) pour la liste de contrôle complète.
:::
