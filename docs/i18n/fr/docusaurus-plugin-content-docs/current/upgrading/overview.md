---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 31ec5b4108bae52597797c3add587e4c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Le cycle de publication de webforJ suit un modèle structuré et prévisible pour garantir la stabilité, la performance et l'innovation continue. Ce document fournit un aperçu de la manière dont les versions sont planifiées, des types de versions à attendre et de la façon dont les utilisateurs peuvent rester informés et préparés.

<AISkillTip skill="webforj-upgrading-versions" />

## Types de publications webforJ {#types-of-webforj-releases}

webforJ suit un modèle de publication structuré qui comprend les types de publications suivants :

### 1. Publications majeures {#1-major-releases}
- Se produisent chaque année.
- Introduisent de nouvelles fonctionnalités significatives, des améliorations et des optimisations.
- Peuvent nécessiter des modifications de configuration ou l'adaptation d'applications existantes.
- Identifiées par un versionnement tel que **webforJ 20.00, webforJ 21.00, etc.**

### 2. Publications mineures {#2-minor-releases}
- Se produisent plusieurs fois au cours de l'année (environ toutes les six à huit semaines).
- Fournissent des améliorations incrémentielles, des optimisations et de petites nouvelles fonctionnalités.
- Identifiées par un versionnement tel que **webforJ 20.01, webforJ 20.02, etc.**

### 3. Corrections et publications de correction de bogues {#3-patches-and-bug-fix-releases}
- Publiées si nécessaire.
- Traitent les bogues critiques, les problèmes de performance et les vulnérabilités de sécurité.
- Identifiées par un numérotage supplémentaire tel que **webforJ 20.01.1, webforJ 20.01.2, etc.**

## À quoi s'attendre avec chaque publication {#what-to-expect-with-each-release}

### Améliorations des fonctionnalités {#feature-enhancements}
- Les publications majeures et mineures introduisent de nouvelles capacités, optimisations et intégrations.
- Les feuilles de route des fonctionnalités sont partagées dans les notes de publication pour aider les utilisateurs à planifier à l'avance.

:::info Compatibilité ascendante
Bien que des efforts soient déployés pour maintenir la compatibilité, les publications majeures peuvent inclure des changements nécessitant des ajustements des applications. Les utilisateurs sont encouragés à consulter les notes de publication pour les fonctionnalités obsolètes.
:::

### Mises à jour de sécurité {#security-updates}
- La sécurité est une priorité, et les vulnérabilités critiques sont traitées dans les publications de correction dès que possible.

:::tip Versions instantanées
Les versions instantanées sont disponibles avant la plupart des publications. Les utilisateurs sont encouragés à les tester pour identifier les problèmes tôt et fournir des retours. Consultez l'article sur les [Versions instantanées](/docs/configuration/snapshots) pour apprendre comment utiliser les versions instantanées de webforJ et où les obtenir.
:::

## Comment rester informé {#how-to-stay-updated}

### Notes de publication et annonces {#release-notes-and-announcements}
- Chaque publication est accompagnée de [notes de publication](https://github.com/webforj/webforj/releases) détaillées qui décrivent les nouvelles fonctionnalités, les corrections de bogues et toute action requise.
- Les utilisateurs devraient s'abonner au [blog](../../blog) de webforJ pour des mises à jour en temps voulu.

:::tip Recommandations de mise à niveau
Les clients doivent planifier les mises à niveau en fonction des besoins commerciaux et des exigences de stabilité. Les utilisateurs sont encouragés à rester sur la dernière version pour bénéficier des améliorations de performance et des nouvelles fonctionnalités.
:::

### Support et compatibilité {#support-and-compatibility}
- webforJ fournit une documentation et des guides de mise à niveau pour les publications majeures.
- Des forums communautaires et des canaux de support client sont disponibles pour le dépannage et l'assistance.

<DocCardList className="topics-section" />
