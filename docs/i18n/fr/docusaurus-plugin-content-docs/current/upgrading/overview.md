---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 5b67f3c7842c20cbef9c77df8f3dd69a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Le cycle de version de webforJ suit un modèle structuré et prévisible pour garantir la stabilité, la performance et l'innovation continue. Ce document fournit un aperçu de la manière dont les versions sont planifiées, des types de versions attendues et de la manière dont les utilisateurs peuvent rester informés et préparés.

## Types de versions de webforJ {#types-of-webforj-releases}

webforJ suit un modèle de version structuré qui comprend les types de versions suivants :

### 1. Versions majeures {#1-major-releases}
- Ont lieu chaque année.
- Introduisent des fonctionnalités nouvelles, des améliorations et des optimisations significatives.
- Peuvent nécessiter des modifications de configuration ou l'adaptation d'applications existantes.
- Identifiées par un versionnement tel que **webforJ 20.00, webforJ 21.00, etc.**

### 2. Versions mineures {#2-minor-releases}
- Ont lieu plusieurs fois au cours de l'année (environ tous les six à huit semaines).
- Fournissent des améliorations incrémentales, des optimisations et des fonctionnalités mineures nouvelles.
- Identifiées par un versionnement tel que **webforJ 20.01, webforJ 20.02, etc.**

### 3. Correctifs et versions de correctifs de bugs {#3-patches-and-bug-fix-releases}
- Publiées si nécessaire.
- Traitent les bugs critiques, les problèmes de performance et les vulnérabilités de sécurité.
- Identifiées par un numérotage supplémentaire tel que **webforJ 20.01.1, webforJ 20.01.2, etc.**

## Que attendre de chaque version {#what-to-expect-with-each-release}

### Améliorations des fonctionnalités {#feature-enhancements}
- Les versions majeures et mineures introduisent de nouvelles capacités, des optimisations et des intégrations.
- Les feuilles de route des fonctionnalités sont partagées dans les notes de version pour aider les utilisateurs à planifier à l'avance.

:::info Compatibilité descendante
Bien que des efforts soient faits pour maintenir la compatibilité, les versions majeures peuvent inclure des changements nécessitant des ajustements dans les applications. Les utilisateurs sont encouragés à consulter les notes de version pour les fonctionnalités obsolètes.
:::

### Mises à jour de sécurité {#security-updates}
- La sécurité est une priorité, et les vulnérabilités critiques sont traitées dans les versions de correctifs dès que possible.

:::tip Versions instantanées
Des versions instantanées sont disponibles avant la plupart des versions. Les utilisateurs sont encouragés à les tester pour identifier les problèmes tôt et fournir des retours. Consultez l'article sur les [Versions instantanées](/docs/configuration/snapshots) pour savoir comment utiliser les versions instantanées de webforJ et où les obtenir.
:::

## Comment rester informé {#how-to-stay-updated}

### Notes de version et annonces {#release-notes-and-announcements}
- Chaque version est accompagnée de [notes de version](https://github.com/webforj/webforj/releases) détaillées qui décrivent les nouvelles fonctionnalités, les corrections de bugs et les actions requises.
- Les utilisateurs doivent s'abonner au [blog](../../blog) de webforJ pour des mises à jour en temps utile.

:::tip Recommandations de mise à niveau
Les clients doivent planifier les mises à niveau en fonction des besoins commerciaux et des exigences de stabilité. Les utilisateurs sont encouragés à rester sur la dernière version pour bénéficier des améliorations de performance et des nouvelles fonctionnalités.
:::

### Support et compatibilité {#support-and-compatibility}
- webforJ fournit une documentation et des guides de mise à niveau pour les versions majeures.
- Des forums communautaires et des canaux de support client sont disponibles pour le dépannage et l'assistance.

<DocCardList className="topics-section" />
